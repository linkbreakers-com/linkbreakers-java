/*
 * Linkbreakers API
 * The Linkbreakers REST API: links, QR codes, workflows, visitors and analytics, as JSON over HTTPS. Send a workspace API token as `Authorization: Bearer <token>`.  ## Entry points  `GET https://api.linkbreakers.com/` and `GET https://api.linkbreakers.com/v1` answer without credentials with the API's name, version and links. This document is served at `https://api.linkbreakers.com/openapi.json`.  ## Rate limits  Every response describes where the caller stands in the IETF `RateLimit-Policy` and `RateLimit` header fields (draft-ietf-httpapi-ratelimit-headers), and in `RateLimit-Limit`, `RateLimit-Remaining`, `RateLimit-Reset` and `X-RateLimit-Limit`, `X-RateLimit-Remaining`, `X-RateLimit-Reset` (a Unix timestamp) for clients that read those. A request past the limit gets `429 Too Many Requests` with `Retry-After` in seconds.  - Workspace API token: 720 requests per 60 seconds, per token. - Dashboard session or OAuth token: 600 requests per 60 seconds, per token. - No credentials: 300 requests per 60 seconds, per client IP.  Each plan also includes a number of API calls per billing cycle. Past it, calls answer `429` with the code `MAX_PUBLIC_API_QUERIED_REACHED` until the cycle renews or the plan changes.  ## Pagination  Every list pages with opaque cursors (AIP-158). Pass `pageSize`, then send the `nextPageToken` of each response back as `pageToken` (or its alias `cursor`) to get the next page. An empty or absent `nextPageToken` means the list is complete. Each list operation describes this in `x-pagination`.  ## Batches and background jobs  `POST /v1/links/bulk` creates up to 250 links in one transactional request; `POST /v1/links/batch` takes the same links as a bare JSON array. `POST /v1/links/bulk/jobs` accepts the same batch and answers `202 Accepted` at once with a job and a `Location` header; poll `GET /v1/jobs/{id}` until `status` is `JOB_STATUS_SUCCEEDED` (the created link ids are in `linksBulkCreate.linkIds`) or `JOB_STATUS_FAILED` (`error` says why, and nothing was created). Jobs can be read for 24 hours after they finish.  ## Paused workspaces  A free workspace whose members have not verified any email address is paused 7 days after sign-up. Its calls then answer `403` with the code `WORKSPACE_UNVERIFIED` and change nothing; QR codes and links keep working for visitors. To lift the pause, ask the workspace owner to verify their email: `POST /v1/members/email-verification` sends the verification email again, and works while the workspace is paused. `unverifiedPauseAt` on the workspace says when the pause starts.  ## Versioning and deprecation  The version is in the path: every operation lives under `/v1`. Within a version, changes are additive only: new operations, new optional request fields, new response fields and new enum values. Clients should ignore fields and enum values they do not know. Anything that would break an existing client ships under a new path version instead.  An operation is retired in two steps. First it is deprecated: it keeps working, is flagged `deprecated: true` in this document, and every response carries a `Deprecation` header field (RFC 9745) with the time it was deprecated and a `Link` with `rel=\"deprecation\"` to the policy. Then, at least 180 days later, it may be removed; that date is announced in advance in a `Sunset` header field (RFC 8594) and as `x-sunset` on the operation. The full policy: https://linkbreakers.com/help/article/api-versioning-and-deprecation-policy  A renamed operation keeps answering its former path while clients still call it, until that path's sunset at the latest: a former path no client calls for 14 days is removed. Responses on a former path carry the same `Deprecation`, `Sunset` and `Link` header fields; this document lists only the current path, and the policy lists the former paths that still answer.  ## Idempotent retries  Every POST, PUT and PATCH accepts an optional `Idempotency-Key` header (draft-ietf-httpapi-idempotency-key-header). Send a new unique value, such as a UUID, with each logical request and reuse it only to retry that request. A retry with the same key and the same request returns the first response, marked `Idempotent-Replayed: true`, instead of running again. Keys are scoped to the token and kept for 24 hours. The same key with a different request is refused with `422`, and a retry while the first request is still running gets `409` with `Retry-After`. Server errors (5xx) and refusals such as 401, 403 and 429 are not stored, so retrying them runs the request again.  ## Identifying your client  Send a `Linkbreakers-Client` request header saying which client made the call: `<client>/<version> (<details>)`, for example `my-integration/1.2.0 (python 3.12; linux)`. The official SDKs, the CLI and the MCP bridge set it for you, as `sdk-python/2.3.1 (python 3.12.4; linux)` or `cli/1.4.0 (darwin arm64)`. It is optional and changes nothing about how a request is handled; it tells us which clients and versions are in use, so we know what can be retired and what to fix first. Keep the details short and free of personal data. Browsers may send it too: it is allowed by CORS.
 *
 * The version of the OpenAPI document: 1.203.0
 * 
 *
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */


package com.linkbreakers.model;

import java.util.Objects;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.linkbreakers.model.TypeDate;
import java.io.IOException;
import java.time.OffsetDateTime;
import java.util.Arrays;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.linkbreakers.JSON;

/**
 * One limit the workspace&#39;s plan sets, with what the workspace has used of it.
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", comments = "Generator version: 7.21.0")
public class WorkspaceAllowance {
  public static final String SERIALIZED_NAME_CHEAPEST_ACTION_COST = "cheapestActionCost";
  @SerializedName(SERIALIZED_NAME_CHEAPEST_ACTION_COST)
  @javax.annotation.Nullable
  private Integer cheapestActionCost;

  public static final String SERIALIZED_NAME_CYCLE_END_AT = "cycleEndAt";
  @SerializedName(SERIALIZED_NAME_CYCLE_END_AT)
  @javax.annotation.Nullable
  private OffsetDateTime cycleEndAt;

  public static final String SERIALIZED_NAME_CYCLE_START_AT = "cycleStartAt";
  @SerializedName(SERIALIZED_NAME_CYCLE_START_AT)
  @javax.annotation.Nullable
  private OffsetDateTime cycleStartAt;

  /**
   * - PLAN_LIMIT_KEY_UNSPECIFIED: Not set  - PLAN_LIMIT_KEY_CUSTOM_DOMAINS: Custom domains connected to the workspace  - PLAN_LIMIT_KEY_TEAM_MEMBERS: Members of the workspace  - PLAN_LIMIT_KEY_PLATFORM_CREDITS: Platform credits, spent by creating links and by AI features  - PLAN_LIMIT_KEY_TRACKED_VISITS: Tracked visits (scans and clicks)  - PLAN_LIMIT_KEY_PUBLIC_API_CALLS: Public API calls  - PLAN_LIMIT_KEY_WEBHOOKS: Webhooks configured in the workspace  - PLAN_LIMIT_KEY_ASSISTANT_MESSAGES_PER_MINUTE: Assistant messages per minute  - PLAN_LIMIT_KEY_LINKS_INCLUDED: Links the monthly platform credits pay for. Links are not capped on their own: each one spends credits.  - PLAN_LIMIT_KEY_MEDIA_STORAGE_MB: Megabytes of uploaded media (media for Open media steps, QR code logos, page theme images) the workspace may hold at once. A standing total: deleting media frees it.
   */
  @JsonAdapter(KeyEnum.Adapter.class)
  public enum KeyEnum {
    PLAN_LIMIT_KEY_UNSPECIFIED("PLAN_LIMIT_KEY_UNSPECIFIED"),
    
    PLAN_LIMIT_KEY_CUSTOM_DOMAINS("PLAN_LIMIT_KEY_CUSTOM_DOMAINS"),
    
    PLAN_LIMIT_KEY_TEAM_MEMBERS("PLAN_LIMIT_KEY_TEAM_MEMBERS"),
    
    PLAN_LIMIT_KEY_PLATFORM_CREDITS("PLAN_LIMIT_KEY_PLATFORM_CREDITS"),
    
    PLAN_LIMIT_KEY_TRACKED_VISITS("PLAN_LIMIT_KEY_TRACKED_VISITS"),
    
    PLAN_LIMIT_KEY_PUBLIC_API_CALLS("PLAN_LIMIT_KEY_PUBLIC_API_CALLS"),
    
    PLAN_LIMIT_KEY_WEBHOOKS("PLAN_LIMIT_KEY_WEBHOOKS"),
    
    PLAN_LIMIT_KEY_ASSISTANT_MESSAGES_PER_MINUTE("PLAN_LIMIT_KEY_ASSISTANT_MESSAGES_PER_MINUTE"),
    
    PLAN_LIMIT_KEY_LINKS_INCLUDED("PLAN_LIMIT_KEY_LINKS_INCLUDED"),
    
    PLAN_LIMIT_KEY_MEDIA_STORAGE_MB("PLAN_LIMIT_KEY_MEDIA_STORAGE_MB");

    private String value;

    KeyEnum(String value) {
      this.value = value;
    }

    public String getValue() {
      return value;
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }

    public static KeyEnum fromValue(String value) {
      for (KeyEnum b : KeyEnum.values()) {
        if (b.value.equals(value)) {
          return b;
        }
      }
      throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }

    public static class Adapter extends TypeAdapter<KeyEnum> {
      @Override
      public void write(final JsonWriter jsonWriter, final KeyEnum enumeration) throws IOException {
        jsonWriter.value(enumeration.getValue());
      }

      @Override
      public KeyEnum read(final JsonReader jsonReader) throws IOException {
        String value =  jsonReader.nextString();
        return KeyEnum.fromValue(value);
      }
    }

    public static void validateJsonElement(JsonElement jsonElement) throws IOException {
      String value = jsonElement.getAsString();
      KeyEnum.fromValue(value);
    }
  }

  public static final String SERIALIZED_NAME_KEY = "key";
  @SerializedName(SERIALIZED_NAME_KEY)
  @javax.annotation.Nullable
  private KeyEnum key;

  /**
   * - PLAN_LIMIT_KIND_UNSPECIFIED: Not set  - PLAN_LIMIT_KIND_TOTAL: A standing total that does not refill, such as connected domains  - PLAN_LIMIT_KIND_MONTHLY: An allowance that refills every month  - PLAN_LIMIT_KIND_PER_MINUTE: A rate that refills every minute  - PLAN_LIMIT_KIND_UNLIMITED: No ceiling  - PLAN_LIMIT_KIND_METERED: No ceiling; every unit is billed
   */
  @JsonAdapter(KindEnum.Adapter.class)
  public enum KindEnum {
    PLAN_LIMIT_KIND_UNSPECIFIED("PLAN_LIMIT_KIND_UNSPECIFIED"),
    
    PLAN_LIMIT_KIND_TOTAL("PLAN_LIMIT_KIND_TOTAL"),
    
    PLAN_LIMIT_KIND_MONTHLY("PLAN_LIMIT_KIND_MONTHLY"),
    
    PLAN_LIMIT_KIND_PER_MINUTE("PLAN_LIMIT_KIND_PER_MINUTE"),
    
    PLAN_LIMIT_KIND_UNLIMITED("PLAN_LIMIT_KIND_UNLIMITED"),
    
    PLAN_LIMIT_KIND_METERED("PLAN_LIMIT_KIND_METERED");

    private String value;

    KindEnum(String value) {
      this.value = value;
    }

    public String getValue() {
      return value;
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }

    public static KindEnum fromValue(String value) {
      for (KindEnum b : KindEnum.values()) {
        if (b.value.equals(value)) {
          return b;
        }
      }
      throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }

    public static class Adapter extends TypeAdapter<KindEnum> {
      @Override
      public void write(final JsonWriter jsonWriter, final KindEnum enumeration) throws IOException {
        jsonWriter.value(enumeration.getValue());
      }

      @Override
      public KindEnum read(final JsonReader jsonReader) throws IOException {
        String value =  jsonReader.nextString();
        return KindEnum.fromValue(value);
      }
    }

    public static void validateJsonElement(JsonElement jsonElement) throws IOException {
      String value = jsonElement.getAsString();
      KindEnum.fromValue(value);
    }
  }

  public static final String SERIALIZED_NAME_KIND = "kind";
  @SerializedName(SERIALIZED_NAME_KIND)
  @javax.annotation.Nullable
  private KindEnum kind;

  public static final String SERIALIZED_NAME_LIMIT = "limit";
  @SerializedName(SERIALIZED_NAME_LIMIT)
  @javax.annotation.Nullable
  private Integer limit;

  public static final String SERIALIZED_NAME_LIMIT_BYTES = "limitBytes";
  @SerializedName(SERIALIZED_NAME_LIMIT_BYTES)
  @javax.annotation.Nullable
  private Long limitBytes;

  public static final String SERIALIZED_NAME_LOCKED_COUNT = "lockedCount";
  @SerializedName(SERIALIZED_NAME_LOCKED_COUNT)
  @javax.annotation.Nullable
  private Integer lockedCount;

  public static final String SERIALIZED_NAME_LOCKED_TOTAL_COUNT = "lockedTotalCount";
  @SerializedName(SERIALIZED_NAME_LOCKED_TOTAL_COUNT)
  @javax.annotation.Nullable
  private Integer lockedTotalCount;

  public static final String SERIALIZED_NAME_NOTICE_DISMISSED = "noticeDismissed";
  @SerializedName(SERIALIZED_NAME_NOTICE_DISMISSED)
  @javax.annotation.Nullable
  private Boolean noticeDismissed;

  public static final String SERIALIZED_NAME_REACHED = "reached";
  @SerializedName(SERIALIZED_NAME_REACHED)
  @javax.annotation.Nullable
  private Boolean reached;

  public static final String SERIALIZED_NAME_RECORDED_COUNT = "recordedCount";
  @SerializedName(SERIALIZED_NAME_RECORDED_COUNT)
  @javax.annotation.Nullable
  private Integer recordedCount;

  public static final String SERIALIZED_NAME_REMAINING = "remaining";
  @SerializedName(SERIALIZED_NAME_REMAINING)
  @javax.annotation.Nullable
  private Integer remaining;

  public static final String SERIALIZED_NAME_RESETS_ON = "resetsOn";
  @SerializedName(SERIALIZED_NAME_RESETS_ON)
  @javax.annotation.Nullable
  private TypeDate resetsOn;

  public static final String SERIALIZED_NAME_USED = "used";
  @SerializedName(SERIALIZED_NAME_USED)
  @javax.annotation.Nullable
  private Integer used;

  public static final String SERIALIZED_NAME_USED_BYTES = "usedBytes";
  @SerializedName(SERIALIZED_NAME_USED_BYTES)
  @javax.annotation.Nullable
  private Long usedBytes;

  public WorkspaceAllowance() {
  }

  public WorkspaceAllowance cheapestActionCost(@javax.annotation.Nullable Integer cheapestActionCost) {
    this.cheapestActionCost = cheapestActionCost;
    return this;
  }

  /**
   * Get cheapestActionCost
   * @return cheapestActionCost
   */
  @javax.annotation.Nullable
  public Integer getCheapestActionCost() {
    return cheapestActionCost;
  }

  public void setCheapestActionCost(@javax.annotation.Nullable Integer cheapestActionCost) {
    this.cheapestActionCost = cheapestActionCost;
  }


  public WorkspaceAllowance cycleEndAt(@javax.annotation.Nullable OffsetDateTime cycleEndAt) {
    this.cycleEndAt = cycleEndAt;
    return this;
  }

  /**
   * The last second of the current cycle; the allowance refills one second later. Absent for totals that never refill and when nothing has been counted yet.
   * @return cycleEndAt
   */
  @javax.annotation.Nullable
  public OffsetDateTime getCycleEndAt() {
    return cycleEndAt;
  }

  public void setCycleEndAt(@javax.annotation.Nullable OffsetDateTime cycleEndAt) {
    this.cycleEndAt = cycleEndAt;
  }


  public WorkspaceAllowance cycleStartAt(@javax.annotation.Nullable OffsetDateTime cycleStartAt) {
    this.cycleStartAt = cycleStartAt;
    return this;
  }

  /**
   * Start of the current cycle. Absent when nothing has been counted yet.
   * @return cycleStartAt
   */
  @javax.annotation.Nullable
  public OffsetDateTime getCycleStartAt() {
    return cycleStartAt;
  }

  public void setCycleStartAt(@javax.annotation.Nullable OffsetDateTime cycleStartAt) {
    this.cycleStartAt = cycleStartAt;
  }


  public WorkspaceAllowance key(@javax.annotation.Nullable KeyEnum key) {
    this.key = key;
    return this;
  }

  /**
   * - PLAN_LIMIT_KEY_UNSPECIFIED: Not set  - PLAN_LIMIT_KEY_CUSTOM_DOMAINS: Custom domains connected to the workspace  - PLAN_LIMIT_KEY_TEAM_MEMBERS: Members of the workspace  - PLAN_LIMIT_KEY_PLATFORM_CREDITS: Platform credits, spent by creating links and by AI features  - PLAN_LIMIT_KEY_TRACKED_VISITS: Tracked visits (scans and clicks)  - PLAN_LIMIT_KEY_PUBLIC_API_CALLS: Public API calls  - PLAN_LIMIT_KEY_WEBHOOKS: Webhooks configured in the workspace  - PLAN_LIMIT_KEY_ASSISTANT_MESSAGES_PER_MINUTE: Assistant messages per minute  - PLAN_LIMIT_KEY_LINKS_INCLUDED: Links the monthly platform credits pay for. Links are not capped on their own: each one spends credits.  - PLAN_LIMIT_KEY_MEDIA_STORAGE_MB: Megabytes of uploaded media (media for Open media steps, QR code logos, page theme images) the workspace may hold at once. A standing total: deleting media frees it.
   * @return key
   */
  @javax.annotation.Nullable
  public KeyEnum getKey() {
    return key;
  }

  public void setKey(@javax.annotation.Nullable KeyEnum key) {
    this.key = key;
  }


  public WorkspaceAllowance kind(@javax.annotation.Nullable KindEnum kind) {
    this.kind = kind;
    return this;
  }

  /**
   * - PLAN_LIMIT_KIND_UNSPECIFIED: Not set  - PLAN_LIMIT_KIND_TOTAL: A standing total that does not refill, such as connected domains  - PLAN_LIMIT_KIND_MONTHLY: An allowance that refills every month  - PLAN_LIMIT_KIND_PER_MINUTE: A rate that refills every minute  - PLAN_LIMIT_KIND_UNLIMITED: No ceiling  - PLAN_LIMIT_KIND_METERED: No ceiling; every unit is billed
   * @return kind
   */
  @javax.annotation.Nullable
  public KindEnum getKind() {
    return kind;
  }

  public void setKind(@javax.annotation.Nullable KindEnum kind) {
    this.kind = kind;
  }


  public WorkspaceAllowance limit(@javax.annotation.Nullable Integer limit) {
    this.limit = limit;
    return this;
  }

  /**
   * The ceiling. Absent when kind is PLAN_LIMIT_KIND_UNLIMITED or PLAN_LIMIT_KIND_METERED.
   * @return limit
   */
  @javax.annotation.Nullable
  public Integer getLimit() {
    return limit;
  }

  public void setLimit(@javax.annotation.Nullable Integer limit) {
    this.limit = limit;
  }


  public WorkspaceAllowance limitBytes(@javax.annotation.Nullable Long limitBytes) {
    this.limitBytes = limitBytes;
    return this;
  }

  /**
   * Media storage only: the most media the workspace may hold, in bytes. Absent for other limits and when there is no ceiling.
   * @return limitBytes
   */
  @javax.annotation.Nullable
  public Long getLimitBytes() {
    return limitBytes;
  }

  public void setLimitBytes(@javax.annotation.Nullable Long limitBytes) {
    this.limitBytes = limitBytes;
  }


  public WorkspaceAllowance lockedCount(@javax.annotation.Nullable Integer lockedCount) {
    this.lockedCount = lockedCount;
    return this;
  }

  /**
   * Tracked visits only: visits recorded past the limit in the current cycle that the workspace&#39;s plan still locks. Absent for other limits.
   * @return lockedCount
   */
  @javax.annotation.Nullable
  public Integer getLockedCount() {
    return lockedCount;
  }

  public void setLockedCount(@javax.annotation.Nullable Integer lockedCount) {
    this.lockedCount = lockedCount;
  }


  public WorkspaceAllowance lockedTotalCount(@javax.annotation.Nullable Integer lockedTotalCount) {
    this.lockedTotalCount = lockedTotalCount;
    return this;
  }

  /**
   * Tracked visits only: every visit the workspace&#39;s plan still locks, across all cycles. Upgrading to a plan with a higher limit reveals them. Absent for other limits.
   * @return lockedTotalCount
   */
  @javax.annotation.Nullable
  public Integer getLockedTotalCount() {
    return lockedTotalCount;
  }

  public void setLockedTotalCount(@javax.annotation.Nullable Integer lockedTotalCount) {
    this.lockedTotalCount = lockedTotalCount;
  }


  public WorkspaceAllowance noticeDismissed(@javax.annotation.Nullable Boolean noticeDismissed) {
    this.noticeDismissed = noticeDismissed;
    return this;
  }

  /**
   * Get noticeDismissed
   * @return noticeDismissed
   */
  @javax.annotation.Nullable
  public Boolean getNoticeDismissed() {
    return noticeDismissed;
  }

  public void setNoticeDismissed(@javax.annotation.Nullable Boolean noticeDismissed) {
    this.noticeDismissed = noticeDismissed;
  }


  public WorkspaceAllowance reached(@javax.annotation.Nullable Boolean reached) {
    this.reached = reached;
    return this;
  }

  /**
   * Whether the limit is used up for the current cycle. Platform credits count as reached once neither the monthly credits nor the purchased top-up credits hold cheapestActionCost, so nothing that spends credits can run. Never reached on a plan billed per unit used.
   * @return reached
   */
  @javax.annotation.Nullable
  public Boolean getReached() {
    return reached;
  }

  public void setReached(@javax.annotation.Nullable Boolean reached) {
    this.reached = reached;
  }


  public WorkspaceAllowance recordedCount(@javax.annotation.Nullable Integer recordedCount) {
    this.recordedCount = recordedCount;
    return this;
  }

  /**
   * Tracked visits only: every visit recorded in the current cycle, used plus lockedCount. Absent for other limits.
   * @return recordedCount
   */
  @javax.annotation.Nullable
  public Integer getRecordedCount() {
    return recordedCount;
  }

  public void setRecordedCount(@javax.annotation.Nullable Integer recordedCount) {
    this.recordedCount = recordedCount;
  }


  public WorkspaceAllowance remaining(@javax.annotation.Nullable Integer remaining) {
    this.remaining = remaining;
    return this;
  }

  /**
   * Units left in the current cycle. For platform credits, the monthly credits left plus the purchased top-up credits left. Absent when there is no ceiling.
   * @return remaining
   */
  @javax.annotation.Nullable
  public Integer getRemaining() {
    return remaining;
  }

  public void setRemaining(@javax.annotation.Nullable Integer remaining) {
    this.remaining = remaining;
  }


  public WorkspaceAllowance resetsOn(@javax.annotation.Nullable TypeDate resetsOn) {
    this.resetsOn = resetsOn;
    return this;
  }

  /**
   * The day the allowance refills, which is the first day of the next cycle in the workspace&#39;s timezone. Absent for totals that never refill and when nothing has been counted yet.
   * @return resetsOn
   */
  @javax.annotation.Nullable
  public TypeDate getResetsOn() {
    return resetsOn;
  }

  public void setResetsOn(@javax.annotation.Nullable TypeDate resetsOn) {
    this.resetsOn = resetsOn;
  }


  public WorkspaceAllowance used(@javax.annotation.Nullable Integer used) {
    this.used = used;
    return this;
  }

  /**
   * Units used in the current cycle. For a total such as custom domains, what the workspace holds.
   * @return used
   */
  @javax.annotation.Nullable
  public Integer getUsed() {
    return used;
  }

  public void setUsed(@javax.annotation.Nullable Integer used) {
    this.used = used;
  }


  public WorkspaceAllowance usedBytes(@javax.annotation.Nullable Long usedBytes) {
    this.usedBytes = usedBytes;
    return this;
  }

  /**
   * Media storage only: the bytes the workspace&#39;s media holds, exact. used is the same figure in megabytes, rounded up. Absent for other limits.
   * @return usedBytes
   */
  @javax.annotation.Nullable
  public Long getUsedBytes() {
    return usedBytes;
  }

  public void setUsedBytes(@javax.annotation.Nullable Long usedBytes) {
    this.usedBytes = usedBytes;
  }



  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    WorkspaceAllowance workspaceAllowance = (WorkspaceAllowance) o;
    return Objects.equals(this.cheapestActionCost, workspaceAllowance.cheapestActionCost) &&
        Objects.equals(this.cycleEndAt, workspaceAllowance.cycleEndAt) &&
        Objects.equals(this.cycleStartAt, workspaceAllowance.cycleStartAt) &&
        Objects.equals(this.key, workspaceAllowance.key) &&
        Objects.equals(this.kind, workspaceAllowance.kind) &&
        Objects.equals(this.limit, workspaceAllowance.limit) &&
        Objects.equals(this.limitBytes, workspaceAllowance.limitBytes) &&
        Objects.equals(this.lockedCount, workspaceAllowance.lockedCount) &&
        Objects.equals(this.lockedTotalCount, workspaceAllowance.lockedTotalCount) &&
        Objects.equals(this.noticeDismissed, workspaceAllowance.noticeDismissed) &&
        Objects.equals(this.reached, workspaceAllowance.reached) &&
        Objects.equals(this.recordedCount, workspaceAllowance.recordedCount) &&
        Objects.equals(this.remaining, workspaceAllowance.remaining) &&
        Objects.equals(this.resetsOn, workspaceAllowance.resetsOn) &&
        Objects.equals(this.used, workspaceAllowance.used) &&
        Objects.equals(this.usedBytes, workspaceAllowance.usedBytes);
  }

  @Override
  public int hashCode() {
    return Objects.hash(cheapestActionCost, cycleEndAt, cycleStartAt, key, kind, limit, limitBytes, lockedCount, lockedTotalCount, noticeDismissed, reached, recordedCount, remaining, resetsOn, used, usedBytes);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class WorkspaceAllowance {\n");
    sb.append("    cheapestActionCost: ").append(toIndentedString(cheapestActionCost)).append("\n");
    sb.append("    cycleEndAt: ").append(toIndentedString(cycleEndAt)).append("\n");
    sb.append("    cycleStartAt: ").append(toIndentedString(cycleStartAt)).append("\n");
    sb.append("    key: ").append(toIndentedString(key)).append("\n");
    sb.append("    kind: ").append(toIndentedString(kind)).append("\n");
    sb.append("    limit: ").append(toIndentedString(limit)).append("\n");
    sb.append("    limitBytes: ").append(toIndentedString(limitBytes)).append("\n");
    sb.append("    lockedCount: ").append(toIndentedString(lockedCount)).append("\n");
    sb.append("    lockedTotalCount: ").append(toIndentedString(lockedTotalCount)).append("\n");
    sb.append("    noticeDismissed: ").append(toIndentedString(noticeDismissed)).append("\n");
    sb.append("    reached: ").append(toIndentedString(reached)).append("\n");
    sb.append("    recordedCount: ").append(toIndentedString(recordedCount)).append("\n");
    sb.append("    remaining: ").append(toIndentedString(remaining)).append("\n");
    sb.append("    resetsOn: ").append(toIndentedString(resetsOn)).append("\n");
    sb.append("    used: ").append(toIndentedString(used)).append("\n");
    sb.append("    usedBytes: ").append(toIndentedString(usedBytes)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    return o == null ? "null" : o.toString().replace("\n", "\n    ");
  }


  public static HashSet<String> openapiFields;
  public static HashSet<String> openapiRequiredFields;

  static {
    // a set of all properties/fields (JSON key names)
    openapiFields = new HashSet<String>(Arrays.asList("cheapestActionCost", "cycleEndAt", "cycleStartAt", "key", "kind", "limit", "limitBytes", "lockedCount", "lockedTotalCount", "noticeDismissed", "reached", "recordedCount", "remaining", "resetsOn", "used", "usedBytes"));

    // a set of required properties/fields (JSON key names)
    openapiRequiredFields = new HashSet<String>(0);
  }

  /**
   * Validates the JSON Element and throws an exception if issues found
   *
   * @param jsonElement JSON Element
   * @throws IOException if the JSON Element is invalid with respect to WorkspaceAllowance
   */
  public static void validateJsonElement(JsonElement jsonElement) throws IOException {
      if (jsonElement == null) {
        if (!WorkspaceAllowance.openapiRequiredFields.isEmpty()) { // has required fields but JSON element is null
          throw new IllegalArgumentException(String.format(java.util.Locale.ROOT, "The required field(s) %s in WorkspaceAllowance is not found in the empty JSON string", WorkspaceAllowance.openapiRequiredFields.toString()));
        }
      }

      Set<Map.Entry<String, JsonElement>> entries = jsonElement.getAsJsonObject().entrySet();
      // check to see if the JSON string contains additional fields
      for (Map.Entry<String, JsonElement> entry : entries) {
        if (!WorkspaceAllowance.openapiFields.contains(entry.getKey())) {
          throw new IllegalArgumentException(String.format(java.util.Locale.ROOT, "The field `%s` in the JSON string is not defined in the `WorkspaceAllowance` properties. JSON: %s", entry.getKey(), jsonElement.toString()));
        }
      }
        JsonObject jsonObj = jsonElement.getAsJsonObject();
      if ((jsonObj.get("key") != null && !jsonObj.get("key").isJsonNull()) && !jsonObj.get("key").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format(java.util.Locale.ROOT, "Expected the field `key` to be a primitive type in the JSON string but got `%s`", jsonObj.get("key").toString()));
      }
      // validate the optional field `key`
      if (jsonObj.get("key") != null && !jsonObj.get("key").isJsonNull()) {
        KeyEnum.validateJsonElement(jsonObj.get("key"));
      }
      if ((jsonObj.get("kind") != null && !jsonObj.get("kind").isJsonNull()) && !jsonObj.get("kind").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format(java.util.Locale.ROOT, "Expected the field `kind` to be a primitive type in the JSON string but got `%s`", jsonObj.get("kind").toString()));
      }
      // validate the optional field `kind`
      if (jsonObj.get("kind") != null && !jsonObj.get("kind").isJsonNull()) {
        KindEnum.validateJsonElement(jsonObj.get("kind"));
      }
      // validate the optional field `resetsOn`
      if (jsonObj.get("resetsOn") != null && !jsonObj.get("resetsOn").isJsonNull()) {
        TypeDate.validateJsonElement(jsonObj.get("resetsOn"));
      }
  }

  public static class CustomTypeAdapterFactory implements TypeAdapterFactory {
    @SuppressWarnings("unchecked")
    @Override
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
       if (!WorkspaceAllowance.class.isAssignableFrom(type.getRawType())) {
         return null; // this class only serializes 'WorkspaceAllowance' and its subtypes
       }
       final TypeAdapter<JsonElement> elementAdapter = gson.getAdapter(JsonElement.class);
       final TypeAdapter<WorkspaceAllowance> thisAdapter
                        = gson.getDelegateAdapter(this, TypeToken.get(WorkspaceAllowance.class));

       return (TypeAdapter<T>) new TypeAdapter<WorkspaceAllowance>() {
           @Override
           public void write(JsonWriter out, WorkspaceAllowance value) throws IOException {
             JsonObject obj = thisAdapter.toJsonTree(value).getAsJsonObject();
             elementAdapter.write(out, obj);
           }

           @Override
           public WorkspaceAllowance read(JsonReader in) throws IOException {
             JsonElement jsonElement = elementAdapter.read(in);
             validateJsonElement(jsonElement);
             return thisAdapter.fromJsonTree(jsonElement);
           }

       }.nullSafe();
    }
  }

  /**
   * Create an instance of WorkspaceAllowance given an JSON string
   *
   * @param jsonString JSON string
   * @return An instance of WorkspaceAllowance
   * @throws IOException if the JSON string is invalid with respect to WorkspaceAllowance
   */
  public static WorkspaceAllowance fromJson(String jsonString) throws IOException {
    return JSON.getGson().fromJson(jsonString, WorkspaceAllowance.class);
  }

  /**
   * Convert an instance of WorkspaceAllowance to an JSON string
   *
   * @return JSON string
   */
  public String toJson() {
    return JSON.getGson().toJson(this);
  }
}

