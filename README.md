# Linkbreakers Java SDK

Official Java SDK for the Linkbreakers API, auto-generated from the OpenAPI specification.

[![Maven Central](https://img.shields.io/maven-central/v/com.linkbreakers/linkbreakers-sdk)](https://central.sonatype.com/artifact/com.linkbreakers/linkbreakers-sdk)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)

## Requirements

Java 1.8+ and Maven 3.8.3+ or Gradle 7.2+.

## Installation

The SDK is published to Maven Central as `com.linkbreakers:linkbreakers-sdk`, with the same version number as the API it was generated from (see [OPENAPI_VERSION](./OPENAPI_VERSION)). Replace `VERSION` below with the latest version shown on the badge above.

### Maven

```xml
<dependency>
  <groupId>com.linkbreakers</groupId>
  <artifactId>linkbreakers-sdk</artifactId>
  <version>VERSION</version>
</dependency>
```

### Gradle

```groovy
repositories {
  mavenCentral()
}

dependencies {
  implementation "com.linkbreakers:linkbreakers-sdk:VERSION"
}
```

## Usage

```java
import com.linkbreakers.ApiClient;
import com.linkbreakers.ApiException;
import com.linkbreakers.Configuration;
import com.linkbreakers.api.LinksApi;
import com.linkbreakers.auth.HttpBearerAuth;
import com.linkbreakers.model.CreateLinkRequest;
import com.linkbreakers.model.CreateLinkResponse;
import com.linkbreakers.model.GetLinkResponse;

public class Example {
  public static void main(String[] args) {
    ApiClient client = Configuration.getDefaultApiClient();
    client.setBasePath("https://api.linkbreakers.com");

    // Bearer token authentication with your workspace API token
    HttpBearerAuth bearerAuth = (HttpBearerAuth) client.getAuthentication("bearerAuth");
    bearerAuth.setBearerToken("your-api-token");

    LinksApi linksApi = new LinksApi(client);
    try {
      // Create a shortened link
      CreateLinkResponse created = linksApi.linksServiceCreate(
          new CreateLinkRequest()
              .destination("https://example.com")
              .name("My Link"),
          null); // optional Idempotency-Key for safe retries
      System.out.println("Short link: " + created.getLink().getShortlink());

      // Get it back by ID
      GetLinkResponse fetched = linksApi.linksServiceGet(created.getLink().getId(), null);
      System.out.println("Name: " + fetched.getLink().getName());
    } catch (ApiException e) {
      System.err.println("Status code: " + e.getCode());
      System.err.println("Reason: " + e.getResponseBody());
    }
  }
}
```

Each API is a class in `com.linkbreakers.api` (`LinksApi`, `VisitorsApi`, ...), and each operation is a method named after the API's operation ID (`linksServiceCreate`, `linksServiceList`, `visitorsServiceIdentify`, ...). Path parameters, query parameters and the request body are positional arguments, with `null` for optional ones. Models live in `com.linkbreakers.model` and have fluent setters.

Get your workspace API token from the [Linkbreakers dashboard](https://app.linkbreakers.com/settings/api).

## API Documentation

For the full API reference, visit https://docs.linkbreakers.com

## Auto-Generated

This SDK is automatically generated from the Linkbreakers OpenAPI specification and published when the API is updated. This README is written by hand: `scripts/check-docs.sh` compiles every Java block in it against the generated code, on pull requests and before every release.

## Issues

Report issues at: https://github.com/linkbreakers-com/linkbreakers-java/issues

## License

MIT License
