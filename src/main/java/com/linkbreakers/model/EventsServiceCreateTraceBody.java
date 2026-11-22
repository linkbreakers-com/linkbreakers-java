/*
 * Linkbreakers API
 * The Linkbreakers REST API: links, QR codes, workflows, visitors and analytics, as JSON over HTTPS. Send a workspace API token as `Authorization: Bearer <token>`.  ## Entry points  `GET https://api.linkbreakers.com/` and `GET https://api.linkbreakers.com/v1` answer without credentials with the API's name, version and links. This document is served at `https://api.linkbreakers.com/openapi.json`.  ## Rate limits  Every response describes where the caller stands in the IETF `RateLimit-Policy` and `RateLimit` header fields (draft-ietf-httpapi-ratelimit-headers), and in `RateLimit-Limit`, `RateLimit-Remaining`, `RateLimit-Reset` and `X-RateLimit-Limit`, `X-RateLimit-Remaining`, `X-RateLimit-Reset` (a Unix timestamp) for clients that read those. A request past the limit gets `429 Too Many Requests` with `Retry-After` in seconds.  - Workspace API token: 720 requests per 60 seconds, per token. - Dashboard session or OAuth token: 600 requests per 60 seconds, per token. - No credentials: 300 requests per 60 seconds, per client IP.  Each plan also includes a number of API calls per billing cycle. Past it, calls answer `429` with the code `MAX_PUBLIC_API_QUERIED_REACHED` until the cycle renews or the plan changes.  ## Pagination  Every list pages with opaque cursors (AIP-158). Pass `pageSize`, then send the `nextPageToken` of each response back as `pageToken` (or its alias `cursor`) to get the next page. An empty or absent `nextPageToken` means the list is complete. Each list operation describes this in `x-pagination`.  ## Batches and background jobs  `POST /v1/links/bulk` creates up to 250 links in one transactional request; `POST /v1/links/batch` takes the same links as a bare JSON array. `POST /v1/links/bulk/jobs` accepts the same batch and answers `202 Accepted` at once with a link job and a `Location` header; poll `GET /v1/links/jobs/{id}` until `status` is `LINK_JOB_STATUS_SUCCEEDED` (the created link ids are in `linksBulkCreate.linkIds`) or `LINK_JOB_STATUS_FAILED` (`error` says why, and nothing was created). Link jobs can be read for 24 hours after they finish.  ## Paused workspaces  A free workspace whose members have not verified any email address is paused 7 days after sign-up. Its calls then answer `403` with the code `WORKSPACE_UNVERIFIED` and change nothing; QR codes and links keep working for visitors. To lift the pause, ask the workspace owner to verify their email: `POST /v1/members/email-verification` sends the verification email again, and works while the workspace is paused. `unverifiedPauseAt` on the workspace says when the pause starts.  ## Versioning and deprecation  The version is in the path: every operation lives under `/v1`. Within a version, changes are additive only: new operations, new optional request fields, new response fields and new enum values. Clients should ignore fields and enum values they do not know. Anything that would break an existing client ships under a new path version instead.  An operation is retired in two steps. First it is deprecated: it keeps working, is flagged `deprecated: true` in this document, and every response carries a `Deprecation` header field (RFC 9745) with the time it was deprecated and a `Link` with `rel=\"deprecation\"` to the policy. Then, at least 180 days later, it may be removed; that date is announced in advance in a `Sunset` header field (RFC 8594) and as `x-sunset` on the operation. The full policy: https://linkbreakers.com/help/article/api-versioning-and-deprecation-policy  A renamed operation keeps answering its former path while clients still call it, until that path's sunset at the latest: a former path no client calls for 14 days is removed. Responses on a former path carry the same `Deprecation`, `Sunset` and `Link` header fields; this document lists only the current path, and the policy lists the former paths that still answer.  ## Idempotent retries  Every POST, PUT and PATCH accepts an optional `Idempotency-Key` header (draft-ietf-httpapi-idempotency-key-header). Send a new unique value, such as a UUID, with each logical request and reuse it only to retry that request. A retry with the same key and the same request returns the first response, marked `Idempotent-Replayed: true`, instead of running again. Keys are scoped to the token and kept for 24 hours. The same key with a different request is refused with `422`, and a retry while the first request is still running gets `409` with `Retry-After`. Server errors (5xx) and refusals such as 401, 403 and 429 are not stored, so retrying them runs the request again.  ## Identifying your client  Send a `Linkbreakers-Client` request header saying which client made the call: `<client>/<version> (<details>)`, for example `my-integration/1.2.0 (python 3.12; linux)`. The official SDKs, the CLI and the MCP bridge set it for you, as `sdk-python/2.3.1 (python 3.12.4; linux)` or `cli/1.4.0 (darwin arm64)`. It is optional and changes nothing about how a request is handled; it tells us which clients and versions are in use, so we know what can be retired and what to fix first. Keep the details short and free of personal data. Browsers may send it too: it is allowed by CORS.
 *
 * The version of the OpenAPI document: 1.204.1
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
import com.linkbreakers.model.EventTraceData;
import java.io.IOException;
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
 * EventsServiceCreateTraceBody
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", comments = "Generator version: 7.21.0")
public class EventsServiceCreateTraceBody {
  public static final String SERIALIZED_NAME_STEP_ACTION = "stepAction";
  @SerializedName(SERIALIZED_NAME_STEP_ACTION)
  @javax.annotation.Nullable
  private String stepAction;

  public static final String SERIALIZED_NAME_STEP_DATA = "stepData";
  @SerializedName(SERIALIZED_NAME_STEP_DATA)
  @javax.annotation.Nullable
  private EventTraceData stepData;

  public static final String SERIALIZED_NAME_STEP_KIND = "stepKind";
  @SerializedName(SERIALIZED_NAME_STEP_KIND)
  @javax.annotation.Nullable
  private String stepKind;

  public EventsServiceCreateTraceBody() {
  }

  public EventsServiceCreateTraceBody stepAction(@javax.annotation.Nullable String stepAction) {
    this.stepAction = stepAction;
    return this;
  }

  /**
   * Get stepAction
   * @return stepAction
   */
  @javax.annotation.Nullable
  public String getStepAction() {
    return stepAction;
  }

  public void setStepAction(@javax.annotation.Nullable String stepAction) {
    this.stepAction = stepAction;
  }


  public EventsServiceCreateTraceBody stepData(@javax.annotation.Nullable EventTraceData stepData) {
    this.stepData = stepData;
    return this;
  }

  /**
   * Get stepData
   * @return stepData
   */
  @javax.annotation.Nullable
  public EventTraceData getStepData() {
    return stepData;
  }

  public void setStepData(@javax.annotation.Nullable EventTraceData stepData) {
    this.stepData = stepData;
  }


  public EventsServiceCreateTraceBody stepKind(@javax.annotation.Nullable String stepKind) {
    this.stepKind = stepKind;
    return this;
  }

  /**
   * Get stepKind
   * @return stepKind
   */
  @javax.annotation.Nullable
  public String getStepKind() {
    return stepKind;
  }

  public void setStepKind(@javax.annotation.Nullable String stepKind) {
    this.stepKind = stepKind;
  }



  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    EventsServiceCreateTraceBody eventsServiceCreateTraceBody = (EventsServiceCreateTraceBody) o;
    return Objects.equals(this.stepAction, eventsServiceCreateTraceBody.stepAction) &&
        Objects.equals(this.stepData, eventsServiceCreateTraceBody.stepData) &&
        Objects.equals(this.stepKind, eventsServiceCreateTraceBody.stepKind);
  }

  @Override
  public int hashCode() {
    return Objects.hash(stepAction, stepData, stepKind);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class EventsServiceCreateTraceBody {\n");
    sb.append("    stepAction: ").append(toIndentedString(stepAction)).append("\n");
    sb.append("    stepData: ").append(toIndentedString(stepData)).append("\n");
    sb.append("    stepKind: ").append(toIndentedString(stepKind)).append("\n");
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
    openapiFields = new HashSet<String>(Arrays.asList("stepAction", "stepData", "stepKind"));

    // a set of required properties/fields (JSON key names)
    openapiRequiredFields = new HashSet<String>(0);
  }

  /**
   * Validates the JSON Element and throws an exception if issues found
   *
   * @param jsonElement JSON Element
   * @throws IOException if the JSON Element is invalid with respect to EventsServiceCreateTraceBody
   */
  public static void validateJsonElement(JsonElement jsonElement) throws IOException {
      if (jsonElement == null) {
        if (!EventsServiceCreateTraceBody.openapiRequiredFields.isEmpty()) { // has required fields but JSON element is null
          throw new IllegalArgumentException(String.format(java.util.Locale.ROOT, "The required field(s) %s in EventsServiceCreateTraceBody is not found in the empty JSON string", EventsServiceCreateTraceBody.openapiRequiredFields.toString()));
        }
      }

      Set<Map.Entry<String, JsonElement>> entries = jsonElement.getAsJsonObject().entrySet();
      // check to see if the JSON string contains additional fields
      for (Map.Entry<String, JsonElement> entry : entries) {
        if (!EventsServiceCreateTraceBody.openapiFields.contains(entry.getKey())) {
          throw new IllegalArgumentException(String.format(java.util.Locale.ROOT, "The field `%s` in the JSON string is not defined in the `EventsServiceCreateTraceBody` properties. JSON: %s", entry.getKey(), jsonElement.toString()));
        }
      }
        JsonObject jsonObj = jsonElement.getAsJsonObject();
      if ((jsonObj.get("stepAction") != null && !jsonObj.get("stepAction").isJsonNull()) && !jsonObj.get("stepAction").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format(java.util.Locale.ROOT, "Expected the field `stepAction` to be a primitive type in the JSON string but got `%s`", jsonObj.get("stepAction").toString()));
      }
      // validate the optional field `stepData`
      if (jsonObj.get("stepData") != null && !jsonObj.get("stepData").isJsonNull()) {
        EventTraceData.validateJsonElement(jsonObj.get("stepData"));
      }
      if ((jsonObj.get("stepKind") != null && !jsonObj.get("stepKind").isJsonNull()) && !jsonObj.get("stepKind").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format(java.util.Locale.ROOT, "Expected the field `stepKind` to be a primitive type in the JSON string but got `%s`", jsonObj.get("stepKind").toString()));
      }
  }

  public static class CustomTypeAdapterFactory implements TypeAdapterFactory {
    @SuppressWarnings("unchecked")
    @Override
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
       if (!EventsServiceCreateTraceBody.class.isAssignableFrom(type.getRawType())) {
         return null; // this class only serializes 'EventsServiceCreateTraceBody' and its subtypes
       }
       final TypeAdapter<JsonElement> elementAdapter = gson.getAdapter(JsonElement.class);
       final TypeAdapter<EventsServiceCreateTraceBody> thisAdapter
                        = gson.getDelegateAdapter(this, TypeToken.get(EventsServiceCreateTraceBody.class));

       return (TypeAdapter<T>) new TypeAdapter<EventsServiceCreateTraceBody>() {
           @Override
           public void write(JsonWriter out, EventsServiceCreateTraceBody value) throws IOException {
             JsonObject obj = thisAdapter.toJsonTree(value).getAsJsonObject();
             elementAdapter.write(out, obj);
           }

           @Override
           public EventsServiceCreateTraceBody read(JsonReader in) throws IOException {
             JsonElement jsonElement = elementAdapter.read(in);
             validateJsonElement(jsonElement);
             return thisAdapter.fromJsonTree(jsonElement);
           }

       }.nullSafe();
    }
  }

  /**
   * Create an instance of EventsServiceCreateTraceBody given an JSON string
   *
   * @param jsonString JSON string
   * @return An instance of EventsServiceCreateTraceBody
   * @throws IOException if the JSON string is invalid with respect to EventsServiceCreateTraceBody
   */
  public static EventsServiceCreateTraceBody fromJson(String jsonString) throws IOException {
    return JSON.getGson().fromJson(jsonString, EventsServiceCreateTraceBody.class);
  }

  /**
   * Convert an instance of EventsServiceCreateTraceBody to an JSON string
   *
   * @return JSON string
   */
  public String toJson() {
    return JSON.getGson().toJson(this);
  }
}

