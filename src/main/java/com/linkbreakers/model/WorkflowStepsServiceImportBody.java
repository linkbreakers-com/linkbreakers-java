/*
 * Linkbreakers API
 * The Linkbreakers REST API: links, QR codes, workflows, visitors and analytics, as JSON over HTTPS. Send a workspace API token as `Authorization: Bearer <token>`.  ## Entry points  `GET https://api.linkbreakers.com/` and `GET https://api.linkbreakers.com/v1` answer without credentials with the API's name, version and links. This document is served at `https://api.linkbreakers.com/openapi.json`.  ## Rate limits  Every response describes where the caller stands in the IETF `RateLimit-Policy` and `RateLimit` header fields (draft-ietf-httpapi-ratelimit-headers), and in `RateLimit-Limit`, `RateLimit-Remaining`, `RateLimit-Reset` and `X-RateLimit-Limit`, `X-RateLimit-Remaining`, `X-RateLimit-Reset` (a Unix timestamp) for clients that read those. A request past the limit gets `429 Too Many Requests` with `Retry-After` in seconds.  - Workspace API token: 720 requests per 60 seconds, per token. - Dashboard session or OAuth token: 600 requests per 60 seconds, per token. - No credentials: 300 requests per 60 seconds, per client IP.  Each plan also includes a number of API calls per billing cycle. Past it, calls answer `429` with the code `MAX_PUBLIC_API_QUERIED_REACHED` until the cycle renews or the plan changes.  ## Pagination  Every list pages with opaque cursors (AIP-158). Pass `pageSize`, then send the `nextPageToken` of each response back as `pageToken` (or its alias `cursor`) to get the next page. An empty or absent `nextPageToken` means the list is complete. Each list operation describes this in `x-pagination`.  ## Batches and background jobs  `POST /v1/links/bulk` creates up to 250 links in one transactional request; `POST /v1/links/batch` takes the same links as a bare JSON array. `POST /v1/links/bulk/jobs` accepts the same batch and answers `202 Accepted` at once with a link job and a `Location` header; poll `GET /v1/links/jobs/{id}` until `status` is `LINK_JOB_STATUS_SUCCEEDED` (the created link ids are in `linksBulkCreate.linkIds`) or `LINK_JOB_STATUS_FAILED` (`error` says why, and nothing was created). Link jobs can be read for 24 hours after they finish.  ## Paused workspaces  A free workspace whose members have not verified any email address is paused 7 days after sign-up. Its calls then answer `403` with the code `WORKSPACE_UNVERIFIED` and change nothing; QR codes and links keep working for visitors. To lift the pause, ask the workspace owner to verify their email: `POST /v1/members/email-verification` sends the verification email again, and works while the workspace is paused. `unverifiedPauseAt` on the workspace says when the pause starts.  ## Versioning and deprecation  The version is in the path: every operation lives under `/v1`. Within a version, changes are additive only: new operations, new optional request fields, new response fields and new enum values. Clients should ignore fields and enum values they do not know. Anything that would break an existing client ships under a new path version instead.  An operation is retired in two steps. First it is deprecated: it keeps working, is flagged `deprecated: true` in this document, and every response carries a `Deprecation` header field (RFC 9745) with the time it was deprecated and a `Link` with `rel=\"deprecation\"` to the policy. Then, at least 180 days later, it may be removed; that date is announced in advance in a `Sunset` header field (RFC 8594) and as `x-sunset` on the operation. The full policy: https://linkbreakers.com/help/article/api-versioning-and-deprecation-policy  A renamed operation keeps answering its former path while clients still call it, until that path's sunset at the latest: a former path no client calls for 14 days is removed. Responses on a former path carry the same `Deprecation`, `Sunset` and `Link` header fields; this document lists only the current path, and the policy lists the former paths that still answer.  ## Idempotent retries  Every POST, PUT and PATCH accepts an optional `Idempotency-Key` header (draft-ietf-httpapi-idempotency-key-header). Send a new unique value, such as a UUID, with each logical request and reuse it only to retry that request. A retry with the same key and the same request returns the first response, marked `Idempotent-Replayed: true`, instead of running again. Keys are scoped to the token and kept for 24 hours. The same key with a different request is refused with `422`, and a retry while the first request is still running gets `409` with `Retry-After`. Server errors (5xx) and refusals such as 401, 403 and 429 are not stored, so retrying them runs the request again.  ## Identifying your client  Send a `Linkbreakers-Client` request header saying which client made the call: `<client>/<version> (<details>)`, for example `my-integration/1.2.0 (python 3.12; linux)`. The official SDKs, the CLI and the MCP bridge set it for you, as `sdk-python/2.3.1 (python 3.12.4; linux)` or `cli/1.4.0 (darwin arm64)`. It is optional and changes nothing about how a request is handled; it tells us which clients and versions are in use, so we know what can be retired and what to fix first. Keep the details short and free of personal data. Browsers may send it too: it is allowed by CORS.
 *
 * The version of the OpenAPI document: 1.205.1
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
import com.linkbreakers.model.CanvasPosition;
import com.linkbreakers.model.WorkflowStepsDocument;
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
 * Imports a document into a link&#39;s workflow.
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", comments = "Generator version: 7.21.0")
public class WorkflowStepsServiceImportBody {
  public static final String SERIALIZED_NAME_CANVAS_POSITION = "canvasPosition";
  @SerializedName(SERIALIZED_NAME_CANVAS_POSITION)
  @javax.annotation.Nullable
  private CanvasPosition canvasPosition;

  public static final String SERIALIZED_NAME_DOCUMENT = "document";
  @SerializedName(SERIALIZED_NAME_DOCUMENT)
  @javax.annotation.Nullable
  private WorkflowStepsDocument document;

  public static final String SERIALIZED_NAME_IGNORE_ENTRY = "ignoreEntry";
  @SerializedName(SERIALIZED_NAME_IGNORE_ENTRY)
  @javax.annotation.Nullable
  private Boolean ignoreEntry;

  public static final String SERIALIZED_NAME_SOURCE_LINK_ID = "sourceLinkId";
  @SerializedName(SERIALIZED_NAME_SOURCE_LINK_ID)
  @javax.annotation.Nullable
  private String sourceLinkId;

  public static final String SERIALIZED_NAME_SOURCE_STEP_IDS = "sourceStepIds";
  @SerializedName(SERIALIZED_NAME_SOURCE_STEP_IDS)
  @javax.annotation.Nullable
  private List<String> sourceStepIds = new ArrayList<>();

  public WorkflowStepsServiceImportBody() {
  }

  public WorkflowStepsServiceImportBody canvasPosition(@javax.annotation.Nullable CanvasPosition canvasPosition) {
    this.canvasPosition = canvasPosition;
    return this;
  }

  /**
   * Where the top left corner of the group goes on the canvas. Left out, the steps go next to the existing ones.
   * @return canvasPosition
   */
  @javax.annotation.Nullable
  public CanvasPosition getCanvasPosition() {
    return canvasPosition;
  }

  public void setCanvasPosition(@javax.annotation.Nullable CanvasPosition canvasPosition) {
    this.canvasPosition = canvasPosition;
  }


  public WorkflowStepsServiceImportBody document(@javax.annotation.Nullable WorkflowStepsDocument document) {
    this.document = document;
    return this;
  }

  /**
   * The steps to create: a document made by Export or copied from the dashboard. Leave it out to copy from &#x60;sourceLinkId&#x60; instead.
   * @return document
   */
  @javax.annotation.Nullable
  public WorkflowStepsDocument getDocument() {
    return document;
  }

  public void setDocument(@javax.annotation.Nullable WorkflowStepsDocument document) {
    this.document = document;
  }


  public WorkflowStepsServiceImportBody ignoreEntry(@javax.annotation.Nullable Boolean ignoreEntry) {
    this.ignoreEntry = ignoreEntry;
    return this;
  }

  /**
   * Leave this link&#39;s entry as it is, whatever the document&#39;s entry led to: for steps copied from this same link. Copying from &#x60;sourceLinkId&#x60; equal to &#x60;linkId&#x60; does it on its own.
   * @return ignoreEntry
   */
  @javax.annotation.Nullable
  public Boolean getIgnoreEntry() {
    return ignoreEntry;
  }

  public void setIgnoreEntry(@javax.annotation.Nullable Boolean ignoreEntry) {
    this.ignoreEntry = ignoreEntry;
  }


  public WorkflowStepsServiceImportBody sourceLinkId(@javax.annotation.Nullable String sourceLinkId) {
    this.sourceLinkId = sourceLinkId;
    return this;
  }

  /**
   * Instead of a document, the link of this workspace to copy steps from, with &#x60;sourceStepIds&#x60;: the same as importing what Export returns for them.
   * @return sourceLinkId
   */
  @javax.annotation.Nullable
  public String getSourceLinkId() {
    return sourceLinkId;
  }

  public void setSourceLinkId(@javax.annotation.Nullable String sourceLinkId) {
    this.sourceLinkId = sourceLinkId;
  }


  public WorkflowStepsServiceImportBody sourceStepIds(@javax.annotation.Nullable List<String> sourceStepIds) {
    this.sourceStepIds = sourceStepIds;
    return this;
  }

  public WorkflowStepsServiceImportBody addSourceStepIdsItem(String sourceStepIdsItem) {
    if (this.sourceStepIds == null) {
      this.sourceStepIds = new ArrayList<>();
    }
    this.sourceStepIds.add(sourceStepIdsItem);
    return this;
  }

  /**
   * The steps of &#x60;sourceLinkId&#x60; to copy, up to 100. Include its entry to connect this link&#39;s entry to the copy of what it leads to.
   * @return sourceStepIds
   */
  @javax.annotation.Nullable
  public List<String> getSourceStepIds() {
    return sourceStepIds;
  }

  public void setSourceStepIds(@javax.annotation.Nullable List<String> sourceStepIds) {
    this.sourceStepIds = sourceStepIds;
  }



  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    WorkflowStepsServiceImportBody workflowStepsServiceImportBody = (WorkflowStepsServiceImportBody) o;
    return Objects.equals(this.canvasPosition, workflowStepsServiceImportBody.canvasPosition) &&
        Objects.equals(this.document, workflowStepsServiceImportBody.document) &&
        Objects.equals(this.ignoreEntry, workflowStepsServiceImportBody.ignoreEntry) &&
        Objects.equals(this.sourceLinkId, workflowStepsServiceImportBody.sourceLinkId) &&
        Objects.equals(this.sourceStepIds, workflowStepsServiceImportBody.sourceStepIds);
  }

  @Override
  public int hashCode() {
    return Objects.hash(canvasPosition, document, ignoreEntry, sourceLinkId, sourceStepIds);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class WorkflowStepsServiceImportBody {\n");
    sb.append("    canvasPosition: ").append(toIndentedString(canvasPosition)).append("\n");
    sb.append("    document: ").append(toIndentedString(document)).append("\n");
    sb.append("    ignoreEntry: ").append(toIndentedString(ignoreEntry)).append("\n");
    sb.append("    sourceLinkId: ").append(toIndentedString(sourceLinkId)).append("\n");
    sb.append("    sourceStepIds: ").append(toIndentedString(sourceStepIds)).append("\n");
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
    openapiFields = new HashSet<String>(Arrays.asList("canvasPosition", "document", "ignoreEntry", "sourceLinkId", "sourceStepIds"));

    // a set of required properties/fields (JSON key names)
    openapiRequiredFields = new HashSet<String>(0);
  }

  /**
   * Validates the JSON Element and throws an exception if issues found
   *
   * @param jsonElement JSON Element
   * @throws IOException if the JSON Element is invalid with respect to WorkflowStepsServiceImportBody
   */
  public static void validateJsonElement(JsonElement jsonElement) throws IOException {
      if (jsonElement == null) {
        if (!WorkflowStepsServiceImportBody.openapiRequiredFields.isEmpty()) { // has required fields but JSON element is null
          throw new IllegalArgumentException(String.format(java.util.Locale.ROOT, "The required field(s) %s in WorkflowStepsServiceImportBody is not found in the empty JSON string", WorkflowStepsServiceImportBody.openapiRequiredFields.toString()));
        }
      }

      Set<Map.Entry<String, JsonElement>> entries = jsonElement.getAsJsonObject().entrySet();
      // check to see if the JSON string contains additional fields
      for (Map.Entry<String, JsonElement> entry : entries) {
        if (!WorkflowStepsServiceImportBody.openapiFields.contains(entry.getKey())) {
          throw new IllegalArgumentException(String.format(java.util.Locale.ROOT, "The field `%s` in the JSON string is not defined in the `WorkflowStepsServiceImportBody` properties. JSON: %s", entry.getKey(), jsonElement.toString()));
        }
      }
        JsonObject jsonObj = jsonElement.getAsJsonObject();
      // validate the optional field `canvasPosition`
      if (jsonObj.get("canvasPosition") != null && !jsonObj.get("canvasPosition").isJsonNull()) {
        CanvasPosition.validateJsonElement(jsonObj.get("canvasPosition"));
      }
      // validate the optional field `document`
      if (jsonObj.get("document") != null && !jsonObj.get("document").isJsonNull()) {
        WorkflowStepsDocument.validateJsonElement(jsonObj.get("document"));
      }
      if ((jsonObj.get("sourceLinkId") != null && !jsonObj.get("sourceLinkId").isJsonNull()) && !jsonObj.get("sourceLinkId").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format(java.util.Locale.ROOT, "Expected the field `sourceLinkId` to be a primitive type in the JSON string but got `%s`", jsonObj.get("sourceLinkId").toString()));
      }
      // ensure the optional json data is an array if present
      if (jsonObj.get("sourceStepIds") != null && !jsonObj.get("sourceStepIds").isJsonNull() && !jsonObj.get("sourceStepIds").isJsonArray()) {
        throw new IllegalArgumentException(String.format(java.util.Locale.ROOT, "Expected the field `sourceStepIds` to be an array in the JSON string but got `%s`", jsonObj.get("sourceStepIds").toString()));
      }
  }

  public static class CustomTypeAdapterFactory implements TypeAdapterFactory {
    @SuppressWarnings("unchecked")
    @Override
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
       if (!WorkflowStepsServiceImportBody.class.isAssignableFrom(type.getRawType())) {
         return null; // this class only serializes 'WorkflowStepsServiceImportBody' and its subtypes
       }
       final TypeAdapter<JsonElement> elementAdapter = gson.getAdapter(JsonElement.class);
       final TypeAdapter<WorkflowStepsServiceImportBody> thisAdapter
                        = gson.getDelegateAdapter(this, TypeToken.get(WorkflowStepsServiceImportBody.class));

       return (TypeAdapter<T>) new TypeAdapter<WorkflowStepsServiceImportBody>() {
           @Override
           public void write(JsonWriter out, WorkflowStepsServiceImportBody value) throws IOException {
             JsonObject obj = thisAdapter.toJsonTree(value).getAsJsonObject();
             elementAdapter.write(out, obj);
           }

           @Override
           public WorkflowStepsServiceImportBody read(JsonReader in) throws IOException {
             JsonElement jsonElement = elementAdapter.read(in);
             validateJsonElement(jsonElement);
             return thisAdapter.fromJsonTree(jsonElement);
           }

       }.nullSafe();
    }
  }

  /**
   * Create an instance of WorkflowStepsServiceImportBody given an JSON string
   *
   * @param jsonString JSON string
   * @return An instance of WorkflowStepsServiceImportBody
   * @throws IOException if the JSON string is invalid with respect to WorkflowStepsServiceImportBody
   */
  public static WorkflowStepsServiceImportBody fromJson(String jsonString) throws IOException {
    return JSON.getGson().fromJson(jsonString, WorkflowStepsServiceImportBody.class);
  }

  /**
   * Convert an instance of WorkflowStepsServiceImportBody to an JSON string
   *
   * @return JSON string
   */
  public String toJson() {
    return JSON.getGson().toJson(this);
  }
}

