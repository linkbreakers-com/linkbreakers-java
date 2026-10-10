#!/usr/bin/env bash
# Compiles every Java block in README.md against the generated SDK, so the
# hand-written README cannot call methods the generator does not produce.
# Each block must be a complete compilation unit with one public class.
set -euo pipefail

root="$(cd "$(dirname "$0")/.." && pwd)"
work="$root/target/docs-check"
rm -rf "$work"
mkdir -p "$work"

awk -v dir="$work" '
  /^```java$/ { n++; inside = 1; file = sprintf("%s/block%d.java.tmp", dir, n); next }
  /^```$/ && inside { inside = 0; close(file); next }
  inside { print > file }
' "$root/README.md"

count=0
for tmp in "$work"/block*.java.tmp; do
  [ -e "$tmp" ] || continue
  class=$(sed -n 's/^public class \([A-Za-z0-9_]*\).*/\1/p' "$tmp" | head -1)
  if [ -z "$class" ]; then
    echo "$(basename "$tmp" .java.tmp) in README.md has no public class" >&2
    exit 1
  fi
  dir="${tmp%.java.tmp}"
  mkdir -p "$dir"
  mv "$tmp" "$dir/$class.java"
  count=$((count + 1))
done
if [ "$count" = "0" ]; then
  echo "No Java blocks found in README.md" >&2
  exit 1
fi

cd "$root"
mvn -q -B compile dependency:build-classpath -Dmdep.outputFile="$work/classpath.txt"
classpath="$root/target/classes:$(cat "$work/classpath.txt")"
for dir in "$work"/block*/; do
  if ! javac -nowarn -d "$work/classes" -cp "$classpath" "$dir"*.java; then
    echo "A README.md Java block no longer compiles against the generated SDK." >&2
    exit 1
  fi
done
echo "Compiled $count README.md Java blocks against the generated SDK."
