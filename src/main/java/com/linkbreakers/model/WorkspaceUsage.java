/*
 * Linkbreakers API
 * The Linkbreakers REST API: links, QR codes, workflows, visitors and analytics, as JSON over HTTPS. Send a workspace API token as `Authorization: Bearer <token>`.  ## Entry points  `GET https://api.linkbreakers.com/` and `GET https://api.linkbreakers.com/v1` answer without credentials with the API's name, version and links. This document is served at `https://api.linkbreakers.com/openapi.json`.  ## Rate limits  Every response describes where the caller stands in the IETF `RateLimit-Policy` and `RateLimit` header fields (draft-ietf-httpapi-ratelimit-headers), and in `RateLimit-Limit`, `RateLimit-Remaining`, `RateLimit-Reset` and `X-RateLimit-Limit`, `X-RateLimit-Remaining`, `X-RateLimit-Reset` (a Unix timestamp) for clients that read those. A request past the limit gets `429 Too Many Requests` with `Retry-After` in seconds.  - Workspace API token: 720 requests per 60 seconds, per token. - Dashboard session or OAuth token: 600 requests per 60 seconds, per token. - No credentials: 300 requests per 60 seconds, per client IP.  Each plan also includes a number of API calls per billing cycle. Past it, calls answer `429` with the code `MAX_PUBLIC_API_QUERIED_REACHED` until the cycle renews or the plan changes.  ## Pagination  Every list pages with opaque cursors (AIP-158). Pass `pageSize`, then send the `nextPageToken` of each response back as `pageToken` (or its alias `cursor`) to get the next page. An empty or absent `nextPageToken` means the list is complete. Each list operation describes this in `x-pagination`.  ## Batches and background jobs  `POST /v1/links/bulk` creates up to 250 links in one transactional request; `POST /v1/links/batch` takes the same links as a bare JSON array. `POST /v1/links/bulk/jobs` accepts the same batch and answers `202 Accepted` at once with a job and a `Location` header; poll `GET /v1/jobs/{id}` until `status` is `JOB_STATUS_SUCCEEDED` (the created link ids are in `linksBulkCreate.linkIds`) or `JOB_STATUS_FAILED` (`error` says why, and nothing was created). Jobs can be read for 24 hours after they finish.  ## Paused workspaces  A free workspace whose members have not verified any email address is paused 7 days after sign-up. Its calls then answer `403` with the code `WORKSPACE_UNVERIFIED` and change nothing; QR codes and links keep working for visitors. To lift the pause, ask the workspace owner to verify their email: `POST /v1/members/email-verification` sends the verification email again, and works while the workspace is paused. `unverifiedPauseAt` on the workspace says when the pause starts.  ## Versioning and deprecation  The version is in the path: every operation lives under `/v1`. Within a version, changes are additive only: new operations, new optional request fields, new response fields and new enum values. Clients should ignore fields and enum values they do not know. Anything that would break an existing client ships under a new path version instead.  An operation is retired in two steps. First it is deprecated: it keeps working, is flagged `deprecated: true` in this document, and every response carries a `Deprecation` header field (RFC 9745) with the time it was deprecated and a `Link` with `rel=\"deprecation\"` to the policy. Then, at least 180 days later, it may be removed; that date is announced in advance in a `Sunset` header field (RFC 8594) and as `x-sunset` on the operation. The full policy: https://linkbreakers.com/help/article/api-versioning-and-deprecation-policy  A renamed operation keeps answering its former path while clients still call it, until that path's sunset at the latest: a former path no client calls for 14 days is removed. Responses on a former path carry the same `Deprecation`, `Sunset` and `Link` header fields; this document lists only the current path, and the policy lists the former paths that still answer.  ## Idempotent retries  Every POST, PUT and PATCH accepts an optional `Idempotency-Key` header (draft-ietf-httpapi-idempotency-key-header). Send a new unique value, such as a UUID, with each logical request and reuse it only to retry that request. A retry with the same key and the same request returns the first response, marked `Idempotent-Replayed: true`, instead of running again. Keys are scoped to the token and kept for 24 hours. The same key with a different request is refused with `422`, and a retry while the first request is still running gets `409` with `Retry-After`. Server errors (5xx) and refusals such as 401, 403 and 429 are not stored, so retrying them runs the request again.  ## Identifying your client  Send a `Linkbreakers-Client` request header saying which client made the call: `<client>/<version> (<details>)`, for example `my-integration/1.2.0 (python 3.12; linux)`. The official SDKs, the CLI and the MCP bridge set it for you, as `sdk-python/2.3.1 (python 3.12.4; linux)` or `cli/1.4.0 (darwin arm64)`. It is optional and changes nothing about how a request is handled; it tells us which clients and versions are in use, so we know what can be retired and what to fix first. Keep the details short and free of personal data. Browsers may send it too: it is allowed by CORS.
 *
 * The version of the OpenAPI document: 1.202.1
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
import com.linkbreakers.model.PlanLimit;
import com.linkbreakers.model.WorkspaceAllowance;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
 * What the workspace has used of each limit its plan sets.
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", comments = "Generator version: 7.21.0")
public class WorkspaceUsage {
  public static final String SERIALIZED_NAME_ALLOWANCES = "allowances";
  @SerializedName(SERIALIZED_NAME_ALLOWANCES)
  @javax.annotation.Nullable
  private List<WorkspaceAllowance> allowances = new ArrayList<>();

  public static final String SERIALIZED_NAME_LIMITS = "limits";
  @SerializedName(SERIALIZED_NAME_LIMITS)
  @javax.annotation.Nullable
  private List<PlanLimit> limits = new ArrayList<>();

  /**
   * The plan the limits come from. A trial or sponsored workspace reports the plan it is allowed what of.  - PRICING_PLAN_ID_UNSPECIFIED: Not set  - PRICING_PLAN_ID_FREE: Free plan, no payment  - PRICING_PLAN_ID_LIGHT: Light plan  - PRICING_PLAN_ID_PRO: Pro plan. Trials and sponsored workspaces are allowed what Pro allows unless a limit says otherwise.  - PRICING_PLAN_ID_ENTERPRISE: Enterprise plan, billed on usage and priced on request through sales
   */
  @JsonAdapter(PlanEnum.Adapter.class)
  public enum PlanEnum {
    PRICING_PLAN_ID_UNSPECIFIED("PRICING_PLAN_ID_UNSPECIFIED"),
    
    PRICING_PLAN_ID_FREE("PRICING_PLAN_ID_FREE"),
    
    PRICING_PLAN_ID_LIGHT("PRICING_PLAN_ID_LIGHT"),
    
    PRICING_PLAN_ID_PRO("PRICING_PLAN_ID_PRO"),
    
    PRICING_PLAN_ID_ENTERPRISE("PRICING_PLAN_ID_ENTERPRISE");

    private String value;

    PlanEnum(String value) {
      this.value = value;
    }

    public String getValue() {
      return value;
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }

    public static PlanEnum fromValue(String value) {
      for (PlanEnum b : PlanEnum.values()) {
        if (b.value.equals(value)) {
          return b;
        }
      }
      throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }

    public static class Adapter extends TypeAdapter<PlanEnum> {
      @Override
      public void write(final JsonWriter jsonWriter, final PlanEnum enumeration) throws IOException {
        jsonWriter.value(enumeration.getValue());
      }

      @Override
      public PlanEnum read(final JsonReader jsonReader) throws IOException {
        String value =  jsonReader.nextString();
        return PlanEnum.fromValue(value);
      }
    }

    public static void validateJsonElement(JsonElement jsonElement) throws IOException {
      String value = jsonElement.getAsString();
      PlanEnum.fromValue(value);
    }
  }

  public static final String SERIALIZED_NAME_PLAN = "plan";
  @SerializedName(SERIALIZED_NAME_PLAN)
  @javax.annotation.Nullable
  private PlanEnum plan;

  public WorkspaceUsage() {
  }

  public WorkspaceUsage allowances(@javax.annotation.Nullable List<WorkspaceAllowance> allowances) {
    this.allowances = allowances;
    return this;
  }

  public WorkspaceUsage addAllowancesItem(WorkspaceAllowance allowancesItem) {
    if (this.allowances == null) {
      this.allowances = new ArrayList<>();
    }
    this.allowances.add(allowancesItem);
    return this;
  }

  /**
   * Get allowances
   * @return allowances
   */
  @javax.annotation.Nullable
  public List<WorkspaceAllowance> getAllowances() {
    return allowances;
  }

  public void setAllowances(@javax.annotation.Nullable List<WorkspaceAllowance> allowances) {
    this.allowances = allowances;
  }


  public WorkspaceUsage limits(@javax.annotation.Nullable List<PlanLimit> limits) {
    this.limits = limits;
    return this;
  }

  public WorkspaceUsage addLimitsItem(PlanLimit limitsItem) {
    if (this.limits == null) {
      this.limits = new ArrayList<>();
    }
    this.limits.add(limitsItem);
    return this;
  }

  /**
   * Every limit this workspace is allowed, as enforced for its subscription, including the ones not counted per cycle such as webhooks and the Assistant rate. A trial or sponsored workspace can be allowed less than the plan it reports, and these are what it gets.
   * @return limits
   */
  @javax.annotation.Nullable
  public List<PlanLimit> getLimits() {
    return limits;
  }

  public void setLimits(@javax.annotation.Nullable List<PlanLimit> limits) {
    this.limits = limits;
  }


  public WorkspaceUsage plan(@javax.annotation.Nullable PlanEnum plan) {
    this.plan = plan;
    return this;
  }

  /**
   * The plan the limits come from. A trial or sponsored workspace reports the plan it is allowed what of.  - PRICING_PLAN_ID_UNSPECIFIED: Not set  - PRICING_PLAN_ID_FREE: Free plan, no payment  - PRICING_PLAN_ID_LIGHT: Light plan  - PRICING_PLAN_ID_PRO: Pro plan. Trials and sponsored workspaces are allowed what Pro allows unless a limit says otherwise.  - PRICING_PLAN_ID_ENTERPRISE: Enterprise plan, billed on usage and priced on request through sales
   * @return plan
   */
  @javax.annotation.Nullable
  public PlanEnum getPlan() {
    return plan;
  }

  public void setPlan(@javax.annotation.Nullable PlanEnum plan) {
    this.plan = plan;
  }



  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    WorkspaceUsage workspaceUsage = (WorkspaceUsage) o;
    return Objects.equals(this.allowances, workspaceUsage.allowances) &&
        Objects.equals(this.limits, workspaceUsage.limits) &&
        Objects.equals(this.plan, workspaceUsage.plan);
  }

  @Override
  public int hashCode() {
    return Objects.hash(allowances, limits, plan);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class WorkspaceUsage {\n");
    sb.append("    allowances: ").append(toIndentedString(allowances)).append("\n");
    sb.append("    limits: ").append(toIndentedString(limits)).append("\n");
    sb.append("    plan: ").append(toIndentedString(plan)).append("\n");
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
    openapiFields = new HashSet<String>(Arrays.asList("allowances", "limits", "plan"));

    // a set of required properties/fields (JSON key names)
    openapiRequiredFields = new HashSet<String>(0);
  }

  /**
   * Validates the JSON Element and throws an exception if issues found
   *
   * @param jsonElement JSON Element
   * @throws IOException if the JSON Element is invalid with respect to WorkspaceUsage
   */
  public static void validateJsonElement(JsonElement jsonElement) throws IOException {
      if (jsonElement == null) {
        if (!WorkspaceUsage.openapiRequiredFields.isEmpty()) { // has required fields but JSON element is null
          throw new IllegalArgumentException(String.format(java.util.Locale.ROOT, "The required field(s) %s in WorkspaceUsage is not found in the empty JSON string", WorkspaceUsage.openapiRequiredFields.toString()));
        }
      }

      Set<Map.Entry<String, JsonElement>> entries = jsonElement.getAsJsonObject().entrySet();
      // check to see if the JSON string contains additional fields
      for (Map.Entry<String, JsonElement> entry : entries) {
        if (!WorkspaceUsage.openapiFields.contains(entry.getKey())) {
          throw new IllegalArgumentException(String.format(java.util.Locale.ROOT, "The field `%s` in the JSON string is not defined in the `WorkspaceUsage` properties. JSON: %s", entry.getKey(), jsonElement.toString()));
        }
      }
        JsonObject jsonObj = jsonElement.getAsJsonObject();
      if (jsonObj.get("allowances") != null && !jsonObj.get("allowances").isJsonNull()) {
        JsonArray jsonArrayallowances = jsonObj.getAsJsonArray("allowances");
        if (jsonArrayallowances != null) {
          // ensure the json data is an array
          if (!jsonObj.get("allowances").isJsonArray()) {
            throw new IllegalArgumentException(String.format(java.util.Locale.ROOT, "Expected the field `allowances` to be an array in the JSON string but got `%s`", jsonObj.get("allowances").toString()));
          }

          // validate the optional field `allowances` (array)
          for (int i = 0; i < jsonArrayallowances.size(); i++) {
            WorkspaceAllowance.validateJsonElement(jsonArrayallowances.get(i));
          };
        }
      }
      if (jsonObj.get("limits") != null && !jsonObj.get("limits").isJsonNull()) {
        JsonArray jsonArraylimits = jsonObj.getAsJsonArray("limits");
        if (jsonArraylimits != null) {
          // ensure the json data is an array
          if (!jsonObj.get("limits").isJsonArray()) {
            throw new IllegalArgumentException(String.format(java.util.Locale.ROOT, "Expected the field `limits` to be an array in the JSON string but got `%s`", jsonObj.get("limits").toString()));
          }

          // validate the optional field `limits` (array)
          for (int i = 0; i < jsonArraylimits.size(); i++) {
            PlanLimit.validateJsonElement(jsonArraylimits.get(i));
          };
        }
      }
      if ((jsonObj.get("plan") != null && !jsonObj.get("plan").isJsonNull()) && !jsonObj.get("plan").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format(java.util.Locale.ROOT, "Expected the field `plan` to be a primitive type in the JSON string but got `%s`", jsonObj.get("plan").toString()));
      }
      // validate the optional field `plan`
      if (jsonObj.get("plan") != null && !jsonObj.get("plan").isJsonNull()) {
        PlanEnum.validateJsonElement(jsonObj.get("plan"));
      }
  }

  public static class CustomTypeAdapterFactory implements TypeAdapterFactory {
    @SuppressWarnings("unchecked")
    @Override
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
       if (!WorkspaceUsage.class.isAssignableFrom(type.getRawType())) {
         return null; // this class only serializes 'WorkspaceUsage' and its subtypes
       }
       final TypeAdapter<JsonElement> elementAdapter = gson.getAdapter(JsonElement.class);
       final TypeAdapter<WorkspaceUsage> thisAdapter
                        = gson.getDelegateAdapter(this, TypeToken.get(WorkspaceUsage.class));

       return (TypeAdapter<T>) new TypeAdapter<WorkspaceUsage>() {
           @Override
           public void write(JsonWriter out, WorkspaceUsage value) throws IOException {
             JsonObject obj = thisAdapter.toJsonTree(value).getAsJsonObject();
             elementAdapter.write(out, obj);
           }

           @Override
           public WorkspaceUsage read(JsonReader in) throws IOException {
             JsonElement jsonElement = elementAdapter.read(in);
             validateJsonElement(jsonElement);
             return thisAdapter.fromJsonTree(jsonElement);
           }

       }.nullSafe();
    }
  }

  /**
   * Create an instance of WorkspaceUsage given an JSON string
   *
   * @param jsonString JSON string
   * @return An instance of WorkspaceUsage
   * @throws IOException if the JSON string is invalid with respect to WorkspaceUsage
   */
  public static WorkspaceUsage fromJson(String jsonString) throws IOException {
    return JSON.getGson().fromJson(jsonString, WorkspaceUsage.class);
  }

  /**
   * Convert an instance of WorkspaceUsage to an JSON string
   *
   * @return JSON string
   */
  public String toJson() {
    return JSON.getGson().toJson(this);
  }
}

