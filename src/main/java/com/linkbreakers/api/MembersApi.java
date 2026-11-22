/*
 * Linkbreakers API
 * The Linkbreakers REST API: links, QR codes, workflows, visitors and analytics, as JSON over HTTPS. Send a workspace API token as `Authorization: Bearer <token>`.  ## Entry points  `GET https://api.linkbreakers.com/` and `GET https://api.linkbreakers.com/v1` answer without credentials with the API's name, version and links. This document is served at `https://api.linkbreakers.com/openapi.json`.  ## Rate limits  Every response describes where the caller stands in the IETF `RateLimit-Policy` and `RateLimit` header fields (draft-ietf-httpapi-ratelimit-headers), and in `RateLimit-Limit`, `RateLimit-Remaining`, `RateLimit-Reset` and `X-RateLimit-Limit`, `X-RateLimit-Remaining`, `X-RateLimit-Reset` (a Unix timestamp) for clients that read those. A request past the limit gets `429 Too Many Requests` with `Retry-After` in seconds.  - Workspace API token: 720 requests per 60 seconds, per token. - Dashboard session or OAuth token: 600 requests per 60 seconds, per token. - No credentials: 300 requests per 60 seconds, per client IP.  Each plan also includes a number of API calls per billing cycle. Past it, calls answer `429` with the code `MAX_PUBLIC_API_QUERIED_REACHED` until the cycle renews or the plan changes.  ## Pagination  Every list pages with opaque cursors (AIP-158). Pass `pageSize`, then send the `nextPageToken` of each response back as `pageToken` (or its alias `cursor`) to get the next page. An empty or absent `nextPageToken` means the list is complete. Each list operation describes this in `x-pagination`.  ## Batches and background jobs  `POST /v1/links/bulk` creates up to 250 links in one transactional request; `POST /v1/links/batch` takes the same links as a bare JSON array. `POST /v1/links/bulk/jobs` accepts the same batch and answers `202 Accepted` at once with a link job and a `Location` header; poll `GET /v1/links/jobs/{id}` until `status` is `LINK_JOB_STATUS_SUCCEEDED` (the created link ids are in `linksBulkCreate.linkIds`) or `LINK_JOB_STATUS_FAILED` (`error` says why, and nothing was created). Link jobs can be read for 24 hours after they finish.  ## Paused workspaces  A free workspace whose members have not verified any email address is paused 7 days after sign-up. Its calls then answer `403` with the code `WORKSPACE_UNVERIFIED` and change nothing; QR codes and links keep working for visitors. To lift the pause, ask the workspace owner to verify their email: `POST /v1/members/email-verification` sends the verification email again, and works while the workspace is paused. `unverifiedPauseAt` on the workspace says when the pause starts.  ## Versioning and deprecation  The version is in the path: every operation lives under `/v1`. Within a version, changes are additive only: new operations, new optional request fields, new response fields and new enum values. Clients should ignore fields and enum values they do not know. Anything that would break an existing client ships under a new path version instead.  An operation is retired in two steps. First it is deprecated: it keeps working, is flagged `deprecated: true` in this document, and every response carries a `Deprecation` header field (RFC 9745) with the time it was deprecated and a `Link` with `rel=\"deprecation\"` to the policy. Then, at least 180 days later, it may be removed; that date is announced in advance in a `Sunset` header field (RFC 8594) and as `x-sunset` on the operation. The full policy: https://linkbreakers.com/help/article/api-versioning-and-deprecation-policy  A renamed operation keeps answering its former path while clients still call it, until that path's sunset at the latest: a former path no client calls for 14 days is removed. Responses on a former path carry the same `Deprecation`, `Sunset` and `Link` header fields; this document lists only the current path, and the policy lists the former paths that still answer.  ## Idempotent retries  Every POST, PUT and PATCH accepts an optional `Idempotency-Key` header (draft-ietf-httpapi-idempotency-key-header). Send a new unique value, such as a UUID, with each logical request and reuse it only to retry that request. A retry with the same key and the same request returns the first response, marked `Idempotent-Replayed: true`, instead of running again. Keys are scoped to the token and kept for 24 hours. The same key with a different request is refused with `422`, and a retry while the first request is still running gets `409` with `Retry-After`. Server errors (5xx) and refusals such as 401, 403 and 429 are not stored, so retrying them runs the request again.  ## Identifying your client  Send a `Linkbreakers-Client` request header saying which client made the call: `<client>/<version> (<details>)`, for example `my-integration/1.2.0 (python 3.12; linux)`. The official SDKs, the CLI and the MCP bridge set it for you, as `sdk-python/2.3.1 (python 3.12.4; linux)` or `cli/1.4.0 (darwin arm64)`. It is optional and changes nothing about how a request is handled; it tells us which clients and versions are in use, so we know what can be retired and what to fix first. Keep the details short and free of personal data. Browsers may send it too: it is allowed by CORS.
 *
 * The version of the OpenAPI document: 1.205.0
 * 
 *
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */


package com.linkbreakers.api;

import com.linkbreakers.ApiCallback;
import com.linkbreakers.ApiClient;
import com.linkbreakers.ApiException;
import com.linkbreakers.ApiResponse;
import com.linkbreakers.Configuration;
import com.linkbreakers.Pair;
import com.linkbreakers.ProgressRequestBody;
import com.linkbreakers.ProgressResponseBody;

import com.google.gson.reflect.TypeToken;

import java.io.IOException;


import com.linkbreakers.model.ErrorResponse;
import com.linkbreakers.model.InviteMemberRequest;
import com.linkbreakers.model.ListResponse;
import com.linkbreakers.model.Member;
import com.linkbreakers.model.MembersServiceUpdateBody;
import com.linkbreakers.model.RequestEmailVerificationRequest;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MembersApi {
    private ApiClient localVarApiClient;
    private int localHostIndex;
    private String localCustomBaseUrl;

    public MembersApi() {
        this(Configuration.getDefaultApiClient());
    }

    public MembersApi(ApiClient apiClient) {
        this.localVarApiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return localVarApiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.localVarApiClient = apiClient;
    }

    public int getHostIndex() {
        return localHostIndex;
    }

    public void setHostIndex(int hostIndex) {
        this.localHostIndex = hostIndex;
    }

    public String getCustomBaseUrl() {
        return localCustomBaseUrl;
    }

    public void setCustomBaseUrl(String customBaseUrl) {
        this.localCustomBaseUrl = customBaseUrl;
    }

    /**
     * Build call for membersServiceAllowAccess
     * @param id Member identifier (UUID) whose access should be restored (required)
     * @param body  (required)
     * @param idempotencyKey A unique value per request, such as a UUID, that makes retrying it safe: a retry with the same key and body returns the first response instead of acting twice. (optional)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table border="1">
       <caption>Response Details</caption>
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Everything worked as expected. </td><td>  * Idempotent-Replayed -  <br>  * RateLimit -  <br>  * RateLimit-Policy -  <br>  * RateLimit-Remaining -  <br>  </td></tr>
        <tr><td> 429 </td><td> Too many requests. Wait the number of seconds in Retry-After, or until the RateLimit window resets, then retry. </td><td>  * RateLimit -  <br>  * RateLimit-Limit -  <br>  * RateLimit-Policy -  <br>  * RateLimit-Remaining -  <br>  * RateLimit-Reset -  <br>  * Retry-After -  <br>  * X-RateLimit-Limit -  <br>  * X-RateLimit-Remaining -  <br>  * X-RateLimit-Reset -  <br>  </td></tr>
        <tr><td> 0 </td><td> Unexpected error </td><td>  * RateLimit -  <br>  * RateLimit-Policy -  <br>  * RateLimit-Remaining -  <br>  </td></tr>
     </table>
     */
    public okhttp3.Call membersServiceAllowAccessCall(@javax.annotation.Nonnull String id, @javax.annotation.Nonnull Object body, @javax.annotation.Nullable String idempotencyKey, final ApiCallback _callback) throws ApiException {
        String basePath = null;
        // Operation Servers
        String[] localBasePaths = new String[] {  };

        // Determine Base Path to Use
        if (localCustomBaseUrl != null){
            basePath = localCustomBaseUrl;
        } else if ( localBasePaths.length > 0 ) {
            basePath = localBasePaths[localHostIndex];
        } else {
            basePath = null;
        }

        Object localVarPostBody = body;

        // create path and map variables
        String localVarPath = "/v1/members/{id}/allow"
            .replace("{" + "id" + "}", localVarApiClient.escapeString(id.toString()));

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
        Map<String, String> localVarHeaderParams = new HashMap<String, String>();
        Map<String, String> localVarCookieParams = new HashMap<String, String>();
        Map<String, Object> localVarFormParams = new HashMap<String, Object>();

        final String[] localVarAccepts = {
            "application/json"
        };
        final String localVarAccept = localVarApiClient.selectHeaderAccept(localVarAccepts);
        if (localVarAccept != null) {
            localVarHeaderParams.put("Accept", localVarAccept);
        }

        final String[] localVarContentTypes = {
            "application/json"
        };
        final String localVarContentType = localVarApiClient.selectHeaderContentType(localVarContentTypes);
        if (localVarContentType != null) {
            localVarHeaderParams.put("Content-Type", localVarContentType);
        }

        if (idempotencyKey != null) {
            localVarHeaderParams.put("Idempotency-Key", localVarApiClient.parameterToString(idempotencyKey));
        }


        String[] localVarAuthNames = new String[] { "oauth2", "bearerAuth" };
        return localVarApiClient.buildCall(basePath, localVarPath, "POST", localVarQueryParams, localVarCollectionQueryParams, localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAuthNames, _callback);
    }

    @SuppressWarnings("rawtypes")
    private okhttp3.Call membersServiceAllowAccessValidateBeforeCall(@javax.annotation.Nonnull String id, @javax.annotation.Nonnull Object body, @javax.annotation.Nullable String idempotencyKey, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new ApiException("Missing the required parameter 'id' when calling membersServiceAllowAccess(Async)");
        }

        // verify the required parameter 'body' is set
        if (body == null) {
            throw new ApiException("Missing the required parameter 'body' when calling membersServiceAllowAccess(Async)");
        }

        return membersServiceAllowAccessCall(id, body, idempotencyKey, _callback);

    }

    /**
     * Allow access for a member
     * Restore workspace access for a previously suspended member.  Requires the &#x60;members:write&#x60; scope.
     * @param id Member identifier (UUID) whose access should be restored (required)
     * @param body  (required)
     * @param idempotencyKey A unique value per request, such as a UUID, that makes retrying it safe: a retry with the same key and body returns the first response instead of acting twice. (optional)
     * @return Member
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table border="1">
       <caption>Response Details</caption>
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Everything worked as expected. </td><td>  * Idempotent-Replayed -  <br>  * RateLimit -  <br>  * RateLimit-Policy -  <br>  * RateLimit-Remaining -  <br>  </td></tr>
        <tr><td> 429 </td><td> Too many requests. Wait the number of seconds in Retry-After, or until the RateLimit window resets, then retry. </td><td>  * RateLimit -  <br>  * RateLimit-Limit -  <br>  * RateLimit-Policy -  <br>  * RateLimit-Remaining -  <br>  * RateLimit-Reset -  <br>  * Retry-After -  <br>  * X-RateLimit-Limit -  <br>  * X-RateLimit-Remaining -  <br>  * X-RateLimit-Reset -  <br>  </td></tr>
        <tr><td> 0 </td><td> Unexpected error </td><td>  * RateLimit -  <br>  * RateLimit-Policy -  <br>  * RateLimit-Remaining -  <br>  </td></tr>
     </table>
     */
    public Member membersServiceAllowAccess(@javax.annotation.Nonnull String id, @javax.annotation.Nonnull Object body, @javax.annotation.Nullable String idempotencyKey) throws ApiException {
        ApiResponse<Member> localVarResp = membersServiceAllowAccessWithHttpInfo(id, body, idempotencyKey);
        return localVarResp.getData();
    }

    /**
     * Allow access for a member
     * Restore workspace access for a previously suspended member.  Requires the &#x60;members:write&#x60; scope.
     * @param id Member identifier (UUID) whose access should be restored (required)
     * @param body  (required)
     * @param idempotencyKey A unique value per request, such as a UUID, that makes retrying it safe: a retry with the same key and body returns the first response instead of acting twice. (optional)
     * @return ApiResponse&lt;Member&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table border="1">
       <caption>Response Details</caption>
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Everything worked as expected. </td><td>  * Idempotent-Replayed -  <br>  * RateLimit -  <br>  * RateLimit-Policy -  <br>  * RateLimit-Remaining -  <br>  </td></tr>
        <tr><td> 429 </td><td> Too many requests. Wait the number of seconds in Retry-After, or until the RateLimit window resets, then retry. </td><td>  * RateLimit -  <br>  * RateLimit-Limit -  <br>  * RateLimit-Policy -  <br>  * RateLimit-Remaining -  <br>  * RateLimit-Reset -  <br>  * Retry-After -  <br>  * X-RateLimit-Limit -  <br>  * X-RateLimit-Remaining -  <br>  * X-RateLimit-Reset -  <br>  </td></tr>
        <tr><td> 0 </td><td> Unexpected error </td><td>  * RateLimit -  <br>  * RateLimit-Policy -  <br>  * RateLimit-Remaining -  <br>  </td></tr>
     </table>
     */
    public ApiResponse<Member> membersServiceAllowAccessWithHttpInfo(@javax.annotation.Nonnull String id, @javax.annotation.Nonnull Object body, @javax.annotation.Nullable String idempotencyKey) throws ApiException {
        okhttp3.Call localVarCall = membersServiceAllowAccessValidateBeforeCall(id, body, idempotencyKey, null);
        Type localVarReturnType = new TypeToken<Member>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Allow access for a member (asynchronously)
     * Restore workspace access for a previously suspended member.  Requires the &#x60;members:write&#x60; scope.
     * @param id Member identifier (UUID) whose access should be restored (required)
     * @param body  (required)
     * @param idempotencyKey A unique value per request, such as a UUID, that makes retrying it safe: a retry with the same key and body returns the first response instead of acting twice. (optional)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table border="1">
       <caption>Response Details</caption>
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Everything worked as expected. </td><td>  * Idempotent-Replayed -  <br>  * RateLimit -  <br>  * RateLimit-Policy -  <br>  * RateLimit-Remaining -  <br>  </td></tr>
        <tr><td> 429 </td><td> Too many requests. Wait the number of seconds in Retry-After, or until the RateLimit window resets, then retry. </td><td>  * RateLimit -  <br>  * RateLimit-Limit -  <br>  * RateLimit-Policy -  <br>  * RateLimit-Remaining -  <br>  * RateLimit-Reset -  <br>  * Retry-After -  <br>  * X-RateLimit-Limit -  <br>  * X-RateLimit-Remaining -  <br>  * X-RateLimit-Reset -  <br>  </td></tr>
        <tr><td> 0 </td><td> Unexpected error </td><td>  * RateLimit -  <br>  * RateLimit-Policy -  <br>  * RateLimit-Remaining -  <br>  </td></tr>
     </table>
     */
    public okhttp3.Call membersServiceAllowAccessAsync(@javax.annotation.Nonnull String id, @javax.annotation.Nonnull Object body, @javax.annotation.Nullable String idempotencyKey, final ApiCallback<Member> _callback) throws ApiException {

        okhttp3.Call localVarCall = membersServiceAllowAccessValidateBeforeCall(id, body, idempotencyKey, _callback);
        Type localVarReturnType = new TypeToken<Member>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for membersServiceDelete
     * @param id Member identifier (UUID) to delete (required)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table border="1">
       <caption>Response Details</caption>
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Everything worked as expected. </td><td>  * RateLimit -  <br>  * RateLimit-Policy -  <br>  * RateLimit-Remaining -  <br>  </td></tr>
        <tr><td> 429 </td><td> Too many requests. Wait the number of seconds in Retry-After, or until the RateLimit window resets, then retry. </td><td>  * RateLimit -  <br>  * RateLimit-Limit -  <br>  * RateLimit-Policy -  <br>  * RateLimit-Remaining -  <br>  * RateLimit-Reset -  <br>  * Retry-After -  <br>  * X-RateLimit-Limit -  <br>  * X-RateLimit-Remaining -  <br>  * X-RateLimit-Reset -  <br>  </td></tr>
        <tr><td> 0 </td><td> Unexpected error </td><td>  * RateLimit -  <br>  * RateLimit-Policy -  <br>  * RateLimit-Remaining -  <br>  </td></tr>
     </table>
     */
    public okhttp3.Call membersServiceDeleteCall(@javax.annotation.Nonnull String id, final ApiCallback _callback) throws ApiException {
        String basePath = null;
        // Operation Servers
        String[] localBasePaths = new String[] {  };

        // Determine Base Path to Use
        if (localCustomBaseUrl != null){
            basePath = localCustomBaseUrl;
        } else if ( localBasePaths.length > 0 ) {
            basePath = localBasePaths[localHostIndex];
        } else {
            basePath = null;
        }

        Object localVarPostBody = null;

        // create path and map variables
        String localVarPath = "/v1/members/{id}"
            .replace("{" + "id" + "}", localVarApiClient.escapeString(id.toString()));

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
        Map<String, String> localVarHeaderParams = new HashMap<String, String>();
        Map<String, String> localVarCookieParams = new HashMap<String, String>();
        Map<String, Object> localVarFormParams = new HashMap<String, Object>();

        final String[] localVarAccepts = {
            "application/json"
        };
        final String localVarAccept = localVarApiClient.selectHeaderAccept(localVarAccepts);
        if (localVarAccept != null) {
            localVarHeaderParams.put("Accept", localVarAccept);
        }

        final String[] localVarContentTypes = {
        };
        final String localVarContentType = localVarApiClient.selectHeaderContentType(localVarContentTypes);
        if (localVarContentType != null) {
            localVarHeaderParams.put("Content-Type", localVarContentType);
        }

        String[] localVarAuthNames = new String[] { "oauth2", "bearerAuth" };
        return localVarApiClient.buildCall(basePath, localVarPath, "DELETE", localVarQueryParams, localVarCollectionQueryParams, localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAuthNames, _callback);
    }

    @SuppressWarnings("rawtypes")
    private okhttp3.Call membersServiceDeleteValidateBeforeCall(@javax.annotation.Nonnull String id, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new ApiException("Missing the required parameter 'id' when calling membersServiceDelete(Async)");
        }

        return membersServiceDeleteCall(id, _callback);

    }

    /**
     * Delete a member
     * Permanently remove a member from the workspace roster.  Requires the &#x60;members:write&#x60; scope.
     * @param id Member identifier (UUID) to delete (required)
     * @return Object
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table border="1">
       <caption>Response Details</caption>
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Everything worked as expected. </td><td>  * RateLimit -  <br>  * RateLimit-Policy -  <br>  * RateLimit-Remaining -  <br>  </td></tr>
        <tr><td> 429 </td><td> Too many requests. Wait the number of seconds in Retry-After, or until the RateLimit window resets, then retry. </td><td>  * RateLimit -  <br>  * RateLimit-Limit -  <br>  * RateLimit-Policy -  <br>  * RateLimit-Remaining -  <br>  * RateLimit-Reset -  <br>  * Retry-After -  <br>  * X-RateLimit-Limit -  <br>  * X-RateLimit-Remaining -  <br>  * X-RateLimit-Reset -  <br>  </td></tr>
        <tr><td> 0 </td><td> Unexpected error </td><td>  * RateLimit -  <br>  * RateLimit-Policy -  <br>  * RateLimit-Remaining -  <br>  </td></tr>
     </table>
     */
    public Object membersServiceDelete(@javax.annotation.Nonnull String id) throws ApiException {
        ApiResponse<Object> localVarResp = membersServiceDeleteWithHttpInfo(id);
        return localVarResp.getData();
    }

    /**
     * Delete a member
     * Permanently remove a member from the workspace roster.  Requires the &#x60;members:write&#x60; scope.
     * @param id Member identifier (UUID) to delete (required)
     * @return ApiResponse&lt;Object&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table border="1">
       <caption>Response Details</caption>
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Everything worked as expected. </td><td>  * RateLimit -  <br>  * RateLimit-Policy -  <br>  * RateLimit-Remaining -  <br>  </td></tr>
        <tr><td> 429 </td><td> Too many requests. Wait the number of seconds in Retry-After, or until the RateLimit window resets, then retry. </td><td>  * RateLimit -  <br>  * RateLimit-Limit -  <br>  * RateLimit-Policy -  <br>  * RateLimit-Remaining -  <br>  * RateLimit-Reset -  <br>  * Retry-After -  <br>  * X-RateLimit-Limit -  <br>  * X-RateLimit-Remaining -  <br>  * X-RateLimit-Reset -  <br>  </td></tr>
        <tr><td> 0 </td><td> Unexpected error </td><td>  * RateLimit -  <br>  * RateLimit-Policy -  <br>  * RateLimit-Remaining -  <br>  </td></tr>
     </table>
     */
    public ApiResponse<Object> membersServiceDeleteWithHttpInfo(@javax.annotation.Nonnull String id) throws ApiException {
        okhttp3.Call localVarCall = membersServiceDeleteValidateBeforeCall(id, null);
        Type localVarReturnType = new TypeToken<Object>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Delete a member (asynchronously)
     * Permanently remove a member from the workspace roster.  Requires the &#x60;members:write&#x60; scope.
     * @param id Member identifier (UUID) to delete (required)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table border="1">
       <caption>Response Details</caption>
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Everything worked as expected. </td><td>  * RateLimit -  <br>  * RateLimit-Policy -  <br>  * RateLimit-Remaining -  <br>  </td></tr>
        <tr><td> 429 </td><td> Too many requests. Wait the number of seconds in Retry-After, or until the RateLimit window resets, then retry. </td><td>  * RateLimit -  <br>  * RateLimit-Limit -  <br>  * RateLimit-Policy -  <br>  * RateLimit-Remaining -  <br>  * RateLimit-Reset -  <br>  * Retry-After -  <br>  * X-RateLimit-Limit -  <br>  * X-RateLimit-Remaining -  <br>  * X-RateLimit-Reset -  <br>  </td></tr>
        <tr><td> 0 </td><td> Unexpected error </td><td>  * RateLimit -  <br>  * RateLimit-Policy -  <br>  * RateLimit-Remaining -  <br>  </td></tr>
     </table>
     */
    public okhttp3.Call membersServiceDeleteAsync(@javax.annotation.Nonnull String id, final ApiCallback<Object> _callback) throws ApiException {

        okhttp3.Call localVarCall = membersServiceDeleteValidateBeforeCall(id, _callback);
        Type localVarReturnType = new TypeToken<Object>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for membersServiceForceSignout
     * @param id Member identifier (UUID) to sign out of all sessions (required)
     * @param body  (required)
     * @param idempotencyKey A unique value per request, such as a UUID, that makes retrying it safe: a retry with the same key and body returns the first response instead of acting twice. (optional)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table border="1">
       <caption>Response Details</caption>
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Everything worked as expected. </td><td>  * Idempotent-Replayed -  <br>  * RateLimit -  <br>  * RateLimit-Policy -  <br>  * RateLimit-Remaining -  <br>  </td></tr>
        <tr><td> 429 </td><td> Too many requests. Wait the number of seconds in Retry-After, or until the RateLimit window resets, then retry. </td><td>  * RateLimit -  <br>  * RateLimit-Limit -  <br>  * RateLimit-Policy -  <br>  * RateLimit-Remaining -  <br>  * RateLimit-Reset -  <br>  * Retry-After -  <br>  * X-RateLimit-Limit -  <br>  * X-RateLimit-Remaining -  <br>  * X-RateLimit-Reset -  <br>  </td></tr>
        <tr><td> 0 </td><td> Unexpected error </td><td>  * RateLimit -  <br>  * RateLimit-Policy -  <br>  * RateLimit-Remaining -  <br>  </td></tr>
     </table>
     */
    public okhttp3.Call membersServiceForceSignoutCall(@javax.annotation.Nonnull String id, @javax.annotation.Nonnull Object body, @javax.annotation.Nullable String idempotencyKey, final ApiCallback _callback) throws ApiException {
        String basePath = null;
        // Operation Servers
        String[] localBasePaths = new String[] {  };

        // Determine Base Path to Use
        if (localCustomBaseUrl != null){
            basePath = localCustomBaseUrl;
        } else if ( localBasePaths.length > 0 ) {
            basePath = localBasePaths[localHostIndex];
        } else {
            basePath = null;
        }

        Object localVarPostBody = body;

        // create path and map variables
        String localVarPath = "/v1/members/{id}/signout"
            .replace("{" + "id" + "}", localVarApiClient.escapeString(id.toString()));

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
        Map<String, String> localVarHeaderParams = new HashMap<String, String>();
        Map<String, String> localVarCookieParams = new HashMap<String, String>();
        Map<String, Object> localVarFormParams = new HashMap<String, Object>();

        final String[] localVarAccepts = {
            "application/json"
        };
        final String localVarAccept = localVarApiClient.selectHeaderAccept(localVarAccepts);
        if (localVarAccept != null) {
            localVarHeaderParams.put("Accept", localVarAccept);
        }

        final String[] localVarContentTypes = {
            "application/json"
        };
        final String localVarContentType = localVarApiClient.selectHeaderContentType(localVarContentTypes);
        if (localVarContentType != null) {
            localVarHeaderParams.put("Content-Type", localVarContentType);
        }

        if (idempotencyKey != null) {
            localVarHeaderParams.put("Idempotency-Key", localVarApiClient.parameterToString(idempotencyKey));
        }


        String[] localVarAuthNames = new String[] { "oauth2", "bearerAuth" };
        return localVarApiClient.buildCall(basePath, localVarPath, "POST", localVarQueryParams, localVarCollectionQueryParams, localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAuthNames, _callback);
    }

    @SuppressWarnings("rawtypes")
    private okhttp3.Call membersServiceForceSignoutValidateBeforeCall(@javax.annotation.Nonnull String id, @javax.annotation.Nonnull Object body, @javax.annotation.Nullable String idempotencyKey, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new ApiException("Missing the required parameter 'id' when calling membersServiceForceSignout(Async)");
        }

        // verify the required parameter 'body' is set
        if (body == null) {
            throw new ApiException("Missing the required parameter 'body' when calling membersServiceForceSignout(Async)");
        }

        return membersServiceForceSignoutCall(id, body, idempotencyKey, _callback);

    }

    /**
     * Force a member to sign out
     * Terminate all active sessions for the specified member.  Requires the &#x60;members:write&#x60; scope.
     * @param id Member identifier (UUID) to sign out of all sessions (required)
     * @param body  (required)
     * @param idempotencyKey A unique value per request, such as a UUID, that makes retrying it safe: a retry with the same key and body returns the first response instead of acting twice. (optional)
     * @return Member
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table border="1">
       <caption>Response Details</caption>
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Everything worked as expected. </td><td>  * Idempotent-Replayed -  <br>  * RateLimit -  <br>  * RateLimit-Policy -  <br>  * RateLimit-Remaining -  <br>  </td></tr>
        <tr><td> 429 </td><td> Too many requests. Wait the number of seconds in Retry-After, or until the RateLimit window resets, then retry. </td><td>  * RateLimit -  <br>  * RateLimit-Limit -  <br>  * RateLimit-Policy -  <br>  * RateLimit-Remaining -  <br>  * RateLimit-Reset -  <br>  * Retry-After -  <br>  * X-RateLimit-Limit -  <br>  * X-RateLimit-Remaining -  <br>  * X-RateLimit-Reset -  <br>  </td></tr>
        <tr><td> 0 </td><td> Unexpected error </td><td>  * RateLimit -  <br>  * RateLimit-Policy -  <br>  * RateLimit-Remaining -  <br>  </td></tr>
     </table>
     */
    public Member membersServiceForceSignout(@javax.annotation.Nonnull String id, @javax.annotation.Nonnull Object body, @javax.annotation.Nullable String idempotencyKey) throws ApiException {
        ApiResponse<Member> localVarResp = membersServiceForceSignoutWithHttpInfo(id, body, idempotencyKey);
        return localVarResp.getData();
    }

    /**
     * Force a member to sign out
     * Terminate all active sessions for the specified member.  Requires the &#x60;members:write&#x60; scope.
     * @param id Member identifier (UUID) to sign out of all sessions (required)
     * @param body  (required)
     * @param idempotencyKey A unique value per request, such as a UUID, that makes retrying it safe: a retry with the same key and body returns the first response instead of acting twice. (optional)
     * @return ApiResponse&lt;Member&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table border="1">
       <caption>Response Details</caption>
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Everything worked as expected. </td><td>  * Idempotent-Replayed -  <br>  * RateLimit -  <br>  * RateLimit-Policy -  <br>  * RateLimit-Remaining -  <br>  </td></tr>
        <tr><td> 429 </td><td> Too many requests. Wait the number of seconds in Retry-After, or until the RateLimit window resets, then retry. </td><td>  * RateLimit -  <br>  * RateLimit-Limit -  <br>  * RateLimit-Policy -  <br>  * RateLimit-Remaining -  <br>  * RateLimit-Reset -  <br>  * Retry-After -  <br>  * X-RateLimit-Limit -  <br>  * X-RateLimit-Remaining -  <br>  * X-RateLimit-Reset -  <br>  </td></tr>
        <tr><td> 0 </td><td> Unexpected error </td><td>  * RateLimit -  <br>  * RateLimit-Policy -  <br>  * RateLimit-Remaining -  <br>  </td></tr>
     </table>
     */
    public ApiResponse<Member> membersServiceForceSignoutWithHttpInfo(@javax.annotation.Nonnull String id, @javax.annotation.Nonnull Object body, @javax.annotation.Nullable String idempotencyKey) throws ApiException {
        okhttp3.Call localVarCall = membersServiceForceSignoutValidateBeforeCall(id, body, idempotencyKey, null);
        Type localVarReturnType = new TypeToken<Member>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Force a member to sign out (asynchronously)
     * Terminate all active sessions for the specified member.  Requires the &#x60;members:write&#x60; scope.
     * @param id Member identifier (UUID) to sign out of all sessions (required)
     * @param body  (required)
     * @param idempotencyKey A unique value per request, such as a UUID, that makes retrying it safe: a retry with the same key and body returns the first response instead of acting twice. (optional)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table border="1">
       <caption>Response Details</caption>
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Everything worked as expected. </td><td>  * Idempotent-Replayed -  <br>  * RateLimit -  <br>  * RateLimit-Policy -  <br>  * RateLimit-Remaining -  <br>  </td></tr>
        <tr><td> 429 </td><td> Too many requests. Wait the number of seconds in Retry-After, or until the RateLimit window resets, then retry. </td><td>  * RateLimit -  <br>  * RateLimit-Limit -  <br>  * RateLimit-Policy -  <br>  * RateLimit-Remaining -  <br>  * RateLimit-Reset -  <br>  * Retry-After -  <br>  * X-RateLimit-Limit -  <br>  * X-RateLimit-Remaining -  <br>  * X-RateLimit-Reset -  <br>  </td></tr>
        <tr><td> 0 </td><td> Unexpected error </td><td>  * RateLimit -  <br>  * RateLimit-Policy -  <br>  * RateLimit-Remaining -  <br>  </td></tr>
     </table>
     */
    public okhttp3.Call membersServiceForceSignoutAsync(@javax.annotation.Nonnull String id, @javax.annotation.Nonnull Object body, @javax.annotation.Nullable String idempotencyKey, final ApiCallback<Member> _callback) throws ApiException {

        okhttp3.Call localVarCall = membersServiceForceSignoutValidateBeforeCall(id, body, idempotencyKey, _callback);
        Type localVarReturnType = new TypeToken<Member>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for membersServiceGet
     * @param id Member identifier (UUID) to retrieve (required)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table border="1">
       <caption>Response Details</caption>
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Everything worked as expected. </td><td>  * RateLimit -  <br>  * RateLimit-Policy -  <br>  * RateLimit-Remaining -  <br>  </td></tr>
        <tr><td> 429 </td><td> Too many requests. Wait the number of seconds in Retry-After, or until the RateLimit window resets, then retry. </td><td>  * RateLimit -  <br>  * RateLimit-Limit -  <br>  * RateLimit-Policy -  <br>  * RateLimit-Remaining -  <br>  * RateLimit-Reset -  <br>  * Retry-After -  <br>  * X-RateLimit-Limit -  <br>  * X-RateLimit-Remaining -  <br>  * X-RateLimit-Reset -  <br>  </td></tr>
        <tr><td> 0 </td><td> Unexpected error </td><td>  * RateLimit -  <br>  * RateLimit-Policy -  <br>  * RateLimit-Remaining -  <br>  </td></tr>
     </table>
     */
    public okhttp3.Call membersServiceGetCall(@javax.annotation.Nonnull String id, final ApiCallback _callback) throws ApiException {
        String basePath = null;
        // Operation Servers
        String[] localBasePaths = new String[] {  };

        // Determine Base Path to Use
        if (localCustomBaseUrl != null){
            basePath = localCustomBaseUrl;
        } else if ( localBasePaths.length > 0 ) {
            basePath = localBasePaths[localHostIndex];
        } else {
            basePath = null;
        }

        Object localVarPostBody = null;

        // create path and map variables
        String localVarPath = "/v1/members/{id}"
            .replace("{" + "id" + "}", localVarApiClient.escapeString(id.toString()));

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
        Map<String, String> localVarHeaderParams = new HashMap<String, String>();
        Map<String, String> localVarCookieParams = new HashMap<String, String>();
        Map<String, Object> localVarFormParams = new HashMap<String, Object>();

        final String[] localVarAccepts = {
            "application/json"
        };
        final String localVarAccept = localVarApiClient.selectHeaderAccept(localVarAccepts);
        if (localVarAccept != null) {
            localVarHeaderParams.put("Accept", localVarAccept);
        }

        final String[] localVarContentTypes = {
        };
        final String localVarContentType = localVarApiClient.selectHeaderContentType(localVarContentTypes);
        if (localVarContentType != null) {
            localVarHeaderParams.put("Content-Type", localVarContentType);
        }

        String[] localVarAuthNames = new String[] { "oauth2", "bearerAuth" };
        return localVarApiClient.buildCall(basePath, localVarPath, "GET", localVarQueryParams, localVarCollectionQueryParams, localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAuthNames, _callback);
    }

    @SuppressWarnings("rawtypes")
    private okhttp3.Call membersServiceGetValidateBeforeCall(@javax.annotation.Nonnull String id, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new ApiException("Missing the required parameter 'id' when calling membersServiceGet(Async)");
        }

        return membersServiceGetCall(id, _callback);

    }

    /**
     * Get a member by ID
     * Fetch a single workspace member by the provided identifier.  Requires the &#x60;members:read&#x60; scope.
     * @param id Member identifier (UUID) to retrieve (required)
     * @return Member
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table border="1">
       <caption>Response Details</caption>
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Everything worked as expected. </td><td>  * RateLimit -  <br>  * RateLimit-Policy -  <br>  * RateLimit-Remaining -  <br>  </td></tr>
        <tr><td> 429 </td><td> Too many requests. Wait the number of seconds in Retry-After, or until the RateLimit window resets, then retry. </td><td>  * RateLimit -  <br>  * RateLimit-Limit -  <br>  * RateLimit-Policy -  <br>  * RateLimit-Remaining -  <br>  * RateLimit-Reset -  <br>  * Retry-After -  <br>  * X-RateLimit-Limit -  <br>  * X-RateLimit-Remaining -  <br>  * X-RateLimit-Reset -  <br>  </td></tr>
        <tr><td> 0 </td><td> Unexpected error </td><td>  * RateLimit -  <br>  * RateLimit-Policy -  <br>  * RateLimit-Remaining -  <br>  </td></tr>
     </table>
     */
    public Member membersServiceGet(@javax.annotation.Nonnull String id) throws ApiException {
        ApiResponse<Member> localVarResp = membersServiceGetWithHttpInfo(id);
        return localVarResp.getData();
    }

    /**
     * Get a member by ID
     * Fetch a single workspace member by the provided identifier.  Requires the &#x60;members:read&#x60; scope.
     * @param id Member identifier (UUID) to retrieve (required)
     * @return ApiResponse&lt;Member&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table border="1">
       <caption>Response Details</caption>
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Everything worked as expected. </td><td>  * RateLimit -  <br>  * RateLimit-Policy -  <br>  * RateLimit-Remaining -  <br>  </td></tr>
        <tr><td> 429 </td><td> Too many requests. Wait the number of seconds in Retry-After, or until the RateLimit window resets, then retry. </td><td>  * RateLimit -  <br>  * RateLimit-Limit -  <br>  * RateLimit-Policy -  <br>  * RateLimit-Remaining -  <br>  * RateLimit-Reset -  <br>  * Retry-After -  <br>  * X-RateLimit-Limit -  <br>  * X-RateLimit-Remaining -  <br>  * X-RateLimit-Reset -  <br>  </td></tr>
        <tr><td> 0 </td><td> Unexpected error </td><td>  * RateLimit -  <br>  * RateLimit-Policy -  <br>  * RateLimit-Remaining -  <br>  </td></tr>
     </table>
     */
    public ApiResponse<Member> membersServiceGetWithHttpInfo(@javax.annotation.Nonnull String id) throws ApiException {
        okhttp3.Call localVarCall = membersServiceGetValidateBeforeCall(id, null);
        Type localVarReturnType = new TypeToken<Member>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Get a member by ID (asynchronously)
     * Fetch a single workspace member by the provided identifier.  Requires the &#x60;members:read&#x60; scope.
     * @param id Member identifier (UUID) to retrieve (required)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table border="1">
       <caption>Response Details</caption>
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Everything worked as expected. </td><td>  * RateLimit -  <br>  * RateLimit-Policy -  <br>  * RateLimit-Remaining -  <br>  </td></tr>
        <tr><td> 429 </td><td> Too many requests. Wait the number of seconds in Retry-After, or until the RateLimit window resets, then retry. </td><td>  * RateLimit -  <br>  * RateLimit-Limit -  <br>  * RateLimit-Policy -  <br>  * RateLimit-Remaining -  <br>  * RateLimit-Reset -  <br>  * Retry-After -  <br>  * X-RateLimit-Limit -  <br>  * X-RateLimit-Remaining -  <br>  * X-RateLimit-Reset -  <br>  </td></tr>
        <tr><td> 0 </td><td> Unexpected error </td><td>  * RateLimit -  <br>  * RateLimit-Policy -  <br>  * RateLimit-Remaining -  <br>  </td></tr>
     </table>
     */
    public okhttp3.Call membersServiceGetAsync(@javax.annotation.Nonnull String id, final ApiCallback<Member> _callback) throws ApiException {

        okhttp3.Call localVarCall = membersServiceGetValidateBeforeCall(id, _callback);
        Type localVarReturnType = new TypeToken<Member>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for membersServiceInvite
     * @param inviteMemberRequest  (required)
     * @param idempotencyKey A unique value per request, such as a UUID, that makes retrying it safe: a retry with the same key and body returns the first response instead of acting twice. (optional)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table border="1">
       <caption>Response Details</caption>
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Everything worked as expected. </td><td>  * Idempotent-Replayed -  <br>  * RateLimit -  <br>  * RateLimit-Policy -  <br>  * RateLimit-Remaining -  <br>  </td></tr>
        <tr><td> 201 </td><td> Member invited successfully </td><td>  * Idempotent-Replayed -  <br>  * RateLimit -  <br>  * RateLimit-Policy -  <br>  * RateLimit-Remaining -  <br>  </td></tr>
        <tr><td> 429 </td><td> Too many requests. Wait the number of seconds in Retry-After, or until the RateLimit window resets, then retry. </td><td>  * RateLimit -  <br>  * RateLimit-Limit -  <br>  * RateLimit-Policy -  <br>  * RateLimit-Remaining -  <br>  * RateLimit-Reset -  <br>  * Retry-After -  <br>  * X-RateLimit-Limit -  <br>  * X-RateLimit-Remaining -  <br>  * X-RateLimit-Reset -  <br>  </td></tr>
        <tr><td> 0 </td><td> Unexpected error </td><td>  * RateLimit -  <br>  * RateLimit-Policy -  <br>  * RateLimit-Remaining -  <br>  </td></tr>
     </table>
     */
    public okhttp3.Call membersServiceInviteCall(@javax.annotation.Nonnull InviteMemberRequest inviteMemberRequest, @javax.annotation.Nullable String idempotencyKey, final ApiCallback _callback) throws ApiException {
        String basePath = null;
        // Operation Servers
        String[] localBasePaths = new String[] {  };

        // Determine Base Path to Use
        if (localCustomBaseUrl != null){
            basePath = localCustomBaseUrl;
        } else if ( localBasePaths.length > 0 ) {
            basePath = localBasePaths[localHostIndex];
        } else {
            basePath = null;
        }

        Object localVarPostBody = inviteMemberRequest;

        // create path and map variables
        String localVarPath = "/v1/members/invite";

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
        Map<String, String> localVarHeaderParams = new HashMap<String, String>();
        Map<String, String> localVarCookieParams = new HashMap<String, String>();
        Map<String, Object> localVarFormParams = new HashMap<String, Object>();

        final String[] localVarAccepts = {
            "application/json"
        };
        final String localVarAccept = localVarApiClient.selectHeaderAccept(localVarAccepts);
        if (localVarAccept != null) {
            localVarHeaderParams.put("Accept", localVarAccept);
        }

        final String[] localVarContentTypes = {
            "application/json"
        };
        final String localVarContentType = localVarApiClient.selectHeaderContentType(localVarContentTypes);
        if (localVarContentType != null) {
            localVarHeaderParams.put("Content-Type", localVarContentType);
        }

        if (idempotencyKey != null) {
            localVarHeaderParams.put("Idempotency-Key", localVarApiClient.parameterToString(idempotencyKey));
        }


        String[] localVarAuthNames = new String[] { "oauth2", "bearerAuth" };
        return localVarApiClient.buildCall(basePath, localVarPath, "POST", localVarQueryParams, localVarCollectionQueryParams, localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAuthNames, _callback);
    }

    @SuppressWarnings("rawtypes")
    private okhttp3.Call membersServiceInviteValidateBeforeCall(@javax.annotation.Nonnull InviteMemberRequest inviteMemberRequest, @javax.annotation.Nullable String idempotencyKey, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'inviteMemberRequest' is set
        if (inviteMemberRequest == null) {
            throw new ApiException("Missing the required parameter 'inviteMemberRequest' when calling membersServiceInvite(Async)");
        }

        return membersServiceInviteCall(inviteMemberRequest, idempotencyKey, _callback);

    }

    /**
     * Invite a new member
     * Create a pending member and dispatch an invitation email. A person who already has a Linkbreakers account is added to the workspace right away and told by email; inviting someone already in the workspace fails with MEMBER_ALREADY_IN_WORKSPACE.  Requires the &#x60;members:write&#x60; scope.
     * @param inviteMemberRequest  (required)
     * @param idempotencyKey A unique value per request, such as a UUID, that makes retrying it safe: a retry with the same key and body returns the first response instead of acting twice. (optional)
     * @return Member
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table border="1">
       <caption>Response Details</caption>
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Everything worked as expected. </td><td>  * Idempotent-Replayed -  <br>  * RateLimit -  <br>  * RateLimit-Policy -  <br>  * RateLimit-Remaining -  <br>  </td></tr>
        <tr><td> 201 </td><td> Member invited successfully </td><td>  * Idempotent-Replayed -  <br>  * RateLimit -  <br>  * RateLimit-Policy -  <br>  * RateLimit-Remaining -  <br>  </td></tr>
        <tr><td> 429 </td><td> Too many requests. Wait the number of seconds in Retry-After, or until the RateLimit window resets, then retry. </td><td>  * RateLimit -  <br>  * RateLimit-Limit -  <br>  * RateLimit-Policy -  <br>  * RateLimit-Remaining -  <br>  * RateLimit-Reset -  <br>  * Retry-After -  <br>  * X-RateLimit-Limit -  <br>  * X-RateLimit-Remaining -  <br>  * X-RateLimit-Reset -  <br>  </td></tr>
        <tr><td> 0 </td><td> Unexpected error </td><td>  * RateLimit -  <br>  * RateLimit-Policy -  <br>  * RateLimit-Remaining -  <br>  </td></tr>
     </table>
     */
    public Member membersServiceInvite(@javax.annotation.Nonnull InviteMemberRequest inviteMemberRequest, @javax.annotation.Nullable String idempotencyKey) throws ApiException {
        ApiResponse<Member> localVarResp = membersServiceInviteWithHttpInfo(inviteMemberRequest, idempotencyKey);
        return localVarResp.getData();
    }

    /**
     * Invite a new member
     * Create a pending member and dispatch an invitation email. A person who already has a Linkbreakers account is added to the workspace right away and told by email; inviting someone already in the workspace fails with MEMBER_ALREADY_IN_WORKSPACE.  Requires the &#x60;members:write&#x60; scope.
     * @param inviteMemberRequest  (required)
     * @param idempotencyKey A unique value per request, such as a UUID, that makes retrying it safe: a retry with the same key and body returns the first response instead of acting twice. (optional)
     * @return ApiResponse&lt;Member&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table border="1">
       <caption>Response Details</caption>
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Everything worked as expected. </td><td>  * Idempotent-Replayed -  <br>  * RateLimit -  <br>  * RateLimit-Policy -  <br>  * RateLimit-Remaining -  <br>  </td></tr>
        <tr><td> 201 </td><td> Member invited successfully </td><td>  * Idempotent-Replayed -  <br>  * RateLimit -  <br>  * RateLimit-Policy -  <br>  * RateLimit-Remaining -  <br>  </td></tr>
        <tr><td> 429 </td><td> Too many requests. Wait the number of seconds in Retry-After, or until the RateLimit window resets, then retry. </td><td>  * RateLimit -  <br>  * RateLimit-Limit -  <br>  * RateLimit-Policy -  <br>  * RateLimit-Remaining -  <br>  * RateLimit-Reset -  <br>  * Retry-After -  <br>  * X-RateLimit-Limit -  <br>  * X-RateLimit-Remaining -  <br>  * X-RateLimit-Reset -  <br>  </td></tr>
        <tr><td> 0 </td><td> Unexpected error </td><td>  * RateLimit -  <br>  * RateLimit-Policy -  <br>  * RateLimit-Remaining -  <br>  </td></tr>
     </table>
     */
    public ApiResponse<Member> membersServiceInviteWithHttpInfo(@javax.annotation.Nonnull InviteMemberRequest inviteMemberRequest, @javax.annotation.Nullable String idempotencyKey) throws ApiException {
        okhttp3.Call localVarCall = membersServiceInviteValidateBeforeCall(inviteMemberRequest, idempotencyKey, null);
        Type localVarReturnType = new TypeToken<Member>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Invite a new member (asynchronously)
     * Create a pending member and dispatch an invitation email. A person who already has a Linkbreakers account is added to the workspace right away and told by email; inviting someone already in the workspace fails with MEMBER_ALREADY_IN_WORKSPACE.  Requires the &#x60;members:write&#x60; scope.
     * @param inviteMemberRequest  (required)
     * @param idempotencyKey A unique value per request, such as a UUID, that makes retrying it safe: a retry with the same key and body returns the first response instead of acting twice. (optional)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table border="1">
       <caption>Response Details</caption>
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Everything worked as expected. </td><td>  * Idempotent-Replayed -  <br>  * RateLimit -  <br>  * RateLimit-Policy -  <br>  * RateLimit-Remaining -  <br>  </td></tr>
        <tr><td> 201 </td><td> Member invited successfully </td><td>  * Idempotent-Replayed -  <br>  * RateLimit -  <br>  * RateLimit-Policy -  <br>  * RateLimit-Remaining -  <br>  </td></tr>
        <tr><td> 429 </td><td> Too many requests. Wait the number of seconds in Retry-After, or until the RateLimit window resets, then retry. </td><td>  * RateLimit -  <br>  * RateLimit-Limit -  <br>  * RateLimit-Policy -  <br>  * RateLimit-Remaining -  <br>  * RateLimit-Reset -  <br>  * Retry-After -  <br>  * X-RateLimit-Limit -  <br>  * X-RateLimit-Remaining -  <br>  * X-RateLimit-Reset -  <br>  </td></tr>
        <tr><td> 0 </td><td> Unexpected error </td><td>  * RateLimit -  <br>  * RateLimit-Policy -  <br>  * RateLimit-Remaining -  <br>  </td></tr>
     </table>
     */
    public okhttp3.Call membersServiceInviteAsync(@javax.annotation.Nonnull InviteMemberRequest inviteMemberRequest, @javax.annotation.Nullable String idempotencyKey, final ApiCallback<Member> _callback) throws ApiException {

        okhttp3.Call localVarCall = membersServiceInviteValidateBeforeCall(inviteMemberRequest, idempotencyKey, _callback);
        Type localVarReturnType = new TypeToken<Member>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for membersServiceList
     * @param pageSize Maximum number of members to return. Defaults to 100, capped at 1000. (optional)
     * @param pageToken Cursor from a previous response&#39;s nextPageToken. Empty starts at the first page. Pages are ordered by (createdAt, id), which never change, so a record cannot move between pages while a client is walking them. (optional)
     * @param cursor Opaque cursor from the previous page&#39;s nextPageToken. Same as pageToken; when both are sent, pageToken wins. (optional)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table border="1">
       <caption>Response Details</caption>
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Everything worked as expected. </td><td>  * RateLimit -  <br>  * RateLimit-Policy -  <br>  * RateLimit-Remaining -  <br>  </td></tr>
        <tr><td> 201 </td><td> Members listed successfully </td><td>  * RateLimit -  <br>  * RateLimit-Policy -  <br>  * RateLimit-Remaining -  <br>  </td></tr>
        <tr><td> 429 </td><td> Too many requests. Wait the number of seconds in Retry-After, or until the RateLimit window resets, then retry. </td><td>  * RateLimit -  <br>  * RateLimit-Limit -  <br>  * RateLimit-Policy -  <br>  * RateLimit-Remaining -  <br>  * RateLimit-Reset -  <br>  * Retry-After -  <br>  * X-RateLimit-Limit -  <br>  * X-RateLimit-Remaining -  <br>  * X-RateLimit-Reset -  <br>  </td></tr>
        <tr><td> 0 </td><td> Unexpected error </td><td>  * RateLimit -  <br>  * RateLimit-Policy -  <br>  * RateLimit-Remaining -  <br>  </td></tr>
     </table>
     */
    public okhttp3.Call membersServiceListCall(@javax.annotation.Nullable Long pageSize, @javax.annotation.Nullable String pageToken, @javax.annotation.Nullable String cursor, final ApiCallback _callback) throws ApiException {
        String basePath = null;
        // Operation Servers
        String[] localBasePaths = new String[] {  };

        // Determine Base Path to Use
        if (localCustomBaseUrl != null){
            basePath = localCustomBaseUrl;
        } else if ( localBasePaths.length > 0 ) {
            basePath = localBasePaths[localHostIndex];
        } else {
            basePath = null;
        }

        Object localVarPostBody = null;

        // create path and map variables
        String localVarPath = "/v1/members";

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
        Map<String, String> localVarHeaderParams = new HashMap<String, String>();
        Map<String, String> localVarCookieParams = new HashMap<String, String>();
        Map<String, Object> localVarFormParams = new HashMap<String, Object>();

        if (pageSize != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("pageSize", pageSize));
        }

        if (pageToken != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("pageToken", pageToken));
        }

        if (cursor != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("cursor", cursor));
        }

        final String[] localVarAccepts = {
            "application/json"
        };
        final String localVarAccept = localVarApiClient.selectHeaderAccept(localVarAccepts);
        if (localVarAccept != null) {
            localVarHeaderParams.put("Accept", localVarAccept);
        }

        final String[] localVarContentTypes = {
        };
        final String localVarContentType = localVarApiClient.selectHeaderContentType(localVarContentTypes);
        if (localVarContentType != null) {
            localVarHeaderParams.put("Content-Type", localVarContentType);
        }

        String[] localVarAuthNames = new String[] { "oauth2", "bearerAuth" };
        return localVarApiClient.buildCall(basePath, localVarPath, "GET", localVarQueryParams, localVarCollectionQueryParams, localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAuthNames, _callback);
    }

    @SuppressWarnings("rawtypes")
    private okhttp3.Call membersServiceListValidateBeforeCall(@javax.annotation.Nullable Long pageSize, @javax.annotation.Nullable String pageToken, @javax.annotation.Nullable String cursor, final ApiCallback _callback) throws ApiException {
        return membersServiceListCall(pageSize, pageToken, cursor, _callback);

    }

    /**
     * List members
     * Retrieve a paginated list of members in the current workspace.  Requires the &#x60;members:read&#x60; scope.
     * @param pageSize Maximum number of members to return. Defaults to 100, capped at 1000. (optional)
     * @param pageToken Cursor from a previous response&#39;s nextPageToken. Empty starts at the first page. Pages are ordered by (createdAt, id), which never change, so a record cannot move between pages while a client is walking them. (optional)
     * @param cursor Opaque cursor from the previous page&#39;s nextPageToken. Same as pageToken; when both are sent, pageToken wins. (optional)
     * @return ListResponse
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table border="1">
       <caption>Response Details</caption>
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Everything worked as expected. </td><td>  * RateLimit -  <br>  * RateLimit-Policy -  <br>  * RateLimit-Remaining -  <br>  </td></tr>
        <tr><td> 201 </td><td> Members listed successfully </td><td>  * RateLimit -  <br>  * RateLimit-Policy -  <br>  * RateLimit-Remaining -  <br>  </td></tr>
        <tr><td> 429 </td><td> Too many requests. Wait the number of seconds in Retry-After, or until the RateLimit window resets, then retry. </td><td>  * RateLimit -  <br>  * RateLimit-Limit -  <br>  * RateLimit-Policy -  <br>  * RateLimit-Remaining -  <br>  * RateLimit-Reset -  <br>  * Retry-After -  <br>  * X-RateLimit-Limit -  <br>  * X-RateLimit-Remaining -  <br>  * X-RateLimit-Reset -  <br>  </td></tr>
        <tr><td> 0 </td><td> Unexpected error </td><td>  * RateLimit -  <br>  * RateLimit-Policy -  <br>  * RateLimit-Remaining -  <br>  </td></tr>
     </table>
     */
    public ListResponse membersServiceList(@javax.annotation.Nullable Long pageSize, @javax.annotation.Nullable String pageToken, @javax.annotation.Nullable String cursor) throws ApiException {
        ApiResponse<ListResponse> localVarResp = membersServiceListWithHttpInfo(pageSize, pageToken, cursor);
        return localVarResp.getData();
    }

    /**
     * List members
     * Retrieve a paginated list of members in the current workspace.  Requires the &#x60;members:read&#x60; scope.
     * @param pageSize Maximum number of members to return. Defaults to 100, capped at 1000. (optional)
     * @param pageToken Cursor from a previous response&#39;s nextPageToken. Empty starts at the first page. Pages are ordered by (createdAt, id), which never change, so a record cannot move between pages while a client is walking them. (optional)
     * @param cursor Opaque cursor from the previous page&#39;s nextPageToken. Same as pageToken; when both are sent, pageToken wins. (optional)
     * @return ApiResponse&lt;ListResponse&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table border="1">
       <caption>Response Details</caption>
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Everything worked as expected. </td><td>  * RateLimit -  <br>  * RateLimit-Policy -  <br>  * RateLimit-Remaining -  <br>  </td></tr>
        <tr><td> 201 </td><td> Members listed successfully </td><td>  * RateLimit -  <br>  * RateLimit-Policy -  <br>  * RateLimit-Remaining -  <br>  </td></tr>
        <tr><td> 429 </td><td> Too many requests. Wait the number of seconds in Retry-After, or until the RateLimit window resets, then retry. </td><td>  * RateLimit -  <br>  * RateLimit-Limit -  <br>  * RateLimit-Policy -  <br>  * RateLimit-Remaining -  <br>  * RateLimit-Reset -  <br>  * Retry-After -  <br>  * X-RateLimit-Limit -  <br>  * X-RateLimit-Remaining -  <br>  * X-RateLimit-Reset -  <br>  </td></tr>
        <tr><td> 0 </td><td> Unexpected error </td><td>  * RateLimit -  <br>  * RateLimit-Policy -  <br>  * RateLimit-Remaining -  <br>  </td></tr>
     </table>
     */
    public ApiResponse<ListResponse> membersServiceListWithHttpInfo(@javax.annotation.Nullable Long pageSize, @javax.annotation.Nullable String pageToken, @javax.annotation.Nullable String cursor) throws ApiException {
        okhttp3.Call localVarCall = membersServiceListValidateBeforeCall(pageSize, pageToken, cursor, null);
        Type localVarReturnType = new TypeToken<ListResponse>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * List members (asynchronously)
     * Retrieve a paginated list of members in the current workspace.  Requires the &#x60;members:read&#x60; scope.
     * @param pageSize Maximum number of members to return. Defaults to 100, capped at 1000. (optional)
     * @param pageToken Cursor from a previous response&#39;s nextPageToken. Empty starts at the first page. Pages are ordered by (createdAt, id), which never change, so a record cannot move between pages while a client is walking them. (optional)
     * @param cursor Opaque cursor from the previous page&#39;s nextPageToken. Same as pageToken; when both are sent, pageToken wins. (optional)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table border="1">
       <caption>Response Details</caption>
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Everything worked as expected. </td><td>  * RateLimit -  <br>  * RateLimit-Policy -  <br>  * RateLimit-Remaining -  <br>  </td></tr>
        <tr><td> 201 </td><td> Members listed successfully </td><td>  * RateLimit -  <br>  * RateLimit-Policy -  <br>  * RateLimit-Remaining -  <br>  </td></tr>
        <tr><td> 429 </td><td> Too many requests. Wait the number of seconds in Retry-After, or until the RateLimit window resets, then retry. </td><td>  * RateLimit -  <br>  * RateLimit-Limit -  <br>  * RateLimit-Policy -  <br>  * RateLimit-Remaining -  <br>  * RateLimit-Reset -  <br>  * Retry-After -  <br>  * X-RateLimit-Limit -  <br>  * X-RateLimit-Remaining -  <br>  * X-RateLimit-Reset -  <br>  </td></tr>
        <tr><td> 0 </td><td> Unexpected error </td><td>  * RateLimit -  <br>  * RateLimit-Policy -  <br>  * RateLimit-Remaining -  <br>  </td></tr>
     </table>
     */
    public okhttp3.Call membersServiceListAsync(@javax.annotation.Nullable Long pageSize, @javax.annotation.Nullable String pageToken, @javax.annotation.Nullable String cursor, final ApiCallback<ListResponse> _callback) throws ApiException {

        okhttp3.Call localVarCall = membersServiceListValidateBeforeCall(pageSize, pageToken, cursor, _callback);
        Type localVarReturnType = new TypeToken<ListResponse>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for membersServiceRequestEmailVerification
     * @param requestEmailVerificationRequest  (required)
     * @param idempotencyKey A unique value per request, such as a UUID, that makes retrying it safe: a retry with the same key and body returns the first response instead of acting twice. (optional)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table border="1">
       <caption>Response Details</caption>
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Everything worked as expected. </td><td>  * Idempotent-Replayed -  <br>  * RateLimit -  <br>  * RateLimit-Policy -  <br>  * RateLimit-Remaining -  <br>  </td></tr>
        <tr><td> 429 </td><td> Too many requests. Wait the number of seconds in Retry-After, or until the RateLimit window resets, then retry. </td><td>  * RateLimit -  <br>  * RateLimit-Limit -  <br>  * RateLimit-Policy -  <br>  * RateLimit-Remaining -  <br>  * RateLimit-Reset -  <br>  * Retry-After -  <br>  * X-RateLimit-Limit -  <br>  * X-RateLimit-Remaining -  <br>  * X-RateLimit-Reset -  <br>  </td></tr>
        <tr><td> 0 </td><td> Unexpected error </td><td>  * RateLimit -  <br>  * RateLimit-Policy -  <br>  * RateLimit-Remaining -  <br>  </td></tr>
     </table>
     */
    public okhttp3.Call membersServiceRequestEmailVerificationCall(@javax.annotation.Nonnull RequestEmailVerificationRequest requestEmailVerificationRequest, @javax.annotation.Nullable String idempotencyKey, final ApiCallback _callback) throws ApiException {
        String basePath = null;
        // Operation Servers
        String[] localBasePaths = new String[] {  };

        // Determine Base Path to Use
        if (localCustomBaseUrl != null){
            basePath = localCustomBaseUrl;
        } else if ( localBasePaths.length > 0 ) {
            basePath = localBasePaths[localHostIndex];
        } else {
            basePath = null;
        }

        Object localVarPostBody = requestEmailVerificationRequest;

        // create path and map variables
        String localVarPath = "/v1/members/email-verification";

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
        Map<String, String> localVarHeaderParams = new HashMap<String, String>();
        Map<String, String> localVarCookieParams = new HashMap<String, String>();
        Map<String, Object> localVarFormParams = new HashMap<String, Object>();

        final String[] localVarAccepts = {
            "application/json"
        };
        final String localVarAccept = localVarApiClient.selectHeaderAccept(localVarAccepts);
        if (localVarAccept != null) {
            localVarHeaderParams.put("Accept", localVarAccept);
        }

        final String[] localVarContentTypes = {
            "application/json"
        };
        final String localVarContentType = localVarApiClient.selectHeaderContentType(localVarContentTypes);
        if (localVarContentType != null) {
            localVarHeaderParams.put("Content-Type", localVarContentType);
        }

        if (idempotencyKey != null) {
            localVarHeaderParams.put("Idempotency-Key", localVarApiClient.parameterToString(idempotencyKey));
        }


        String[] localVarAuthNames = new String[] { "oauth2", "bearerAuth" };
        return localVarApiClient.buildCall(basePath, localVarPath, "POST", localVarQueryParams, localVarCollectionQueryParams, localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAuthNames, _callback);
    }

    @SuppressWarnings("rawtypes")
    private okhttp3.Call membersServiceRequestEmailVerificationValidateBeforeCall(@javax.annotation.Nonnull RequestEmailVerificationRequest requestEmailVerificationRequest, @javax.annotation.Nullable String idempotencyKey, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'requestEmailVerificationRequest' is set
        if (requestEmailVerificationRequest == null) {
            throw new ApiException("Missing the required parameter 'requestEmailVerificationRequest' when calling membersServiceRequestEmailVerification(Async)");
        }

        return membersServiceRequestEmailVerificationCall(requestEmailVerificationRequest, idempotencyKey, _callback);

    }

    /**
     * Resend a member&#39;s verification email
     * Sends the verification email again to a member of the workspace whose email address is not verified yet. Pass the member&#39;s email address.  A free workspace with no verified address is paused 7 days after sign-up: its calls answer &#x60;WORKSPACE_UNVERIFIED&#x60; until a member opens the link in that email. Verifying one member&#39;s address, usually the owner&#39;s, lifts the pause for the whole workspace. This operation keeps working while the workspace is paused.  The address must belong to a member of the workspace. One email per address per minute, and 5 per day.  Requires the &#x60;members:write&#x60; scope.
     * @param requestEmailVerificationRequest  (required)
     * @param idempotencyKey A unique value per request, such as a UUID, that makes retrying it safe: a retry with the same key and body returns the first response instead of acting twice. (optional)
     * @return Object
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table border="1">
       <caption>Response Details</caption>
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Everything worked as expected. </td><td>  * Idempotent-Replayed -  <br>  * RateLimit -  <br>  * RateLimit-Policy -  <br>  * RateLimit-Remaining -  <br>  </td></tr>
        <tr><td> 429 </td><td> Too many requests. Wait the number of seconds in Retry-After, or until the RateLimit window resets, then retry. </td><td>  * RateLimit -  <br>  * RateLimit-Limit -  <br>  * RateLimit-Policy -  <br>  * RateLimit-Remaining -  <br>  * RateLimit-Reset -  <br>  * Retry-After -  <br>  * X-RateLimit-Limit -  <br>  * X-RateLimit-Remaining -  <br>  * X-RateLimit-Reset -  <br>  </td></tr>
        <tr><td> 0 </td><td> Unexpected error </td><td>  * RateLimit -  <br>  * RateLimit-Policy -  <br>  * RateLimit-Remaining -  <br>  </td></tr>
     </table>
     */
    public Object membersServiceRequestEmailVerification(@javax.annotation.Nonnull RequestEmailVerificationRequest requestEmailVerificationRequest, @javax.annotation.Nullable String idempotencyKey) throws ApiException {
        ApiResponse<Object> localVarResp = membersServiceRequestEmailVerificationWithHttpInfo(requestEmailVerificationRequest, idempotencyKey);
        return localVarResp.getData();
    }

    /**
     * Resend a member&#39;s verification email
     * Sends the verification email again to a member of the workspace whose email address is not verified yet. Pass the member&#39;s email address.  A free workspace with no verified address is paused 7 days after sign-up: its calls answer &#x60;WORKSPACE_UNVERIFIED&#x60; until a member opens the link in that email. Verifying one member&#39;s address, usually the owner&#39;s, lifts the pause for the whole workspace. This operation keeps working while the workspace is paused.  The address must belong to a member of the workspace. One email per address per minute, and 5 per day.  Requires the &#x60;members:write&#x60; scope.
     * @param requestEmailVerificationRequest  (required)
     * @param idempotencyKey A unique value per request, such as a UUID, that makes retrying it safe: a retry with the same key and body returns the first response instead of acting twice. (optional)
     * @return ApiResponse&lt;Object&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table border="1">
       <caption>Response Details</caption>
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Everything worked as expected. </td><td>  * Idempotent-Replayed -  <br>  * RateLimit -  <br>  * RateLimit-Policy -  <br>  * RateLimit-Remaining -  <br>  </td></tr>
        <tr><td> 429 </td><td> Too many requests. Wait the number of seconds in Retry-After, or until the RateLimit window resets, then retry. </td><td>  * RateLimit -  <br>  * RateLimit-Limit -  <br>  * RateLimit-Policy -  <br>  * RateLimit-Remaining -  <br>  * RateLimit-Reset -  <br>  * Retry-After -  <br>  * X-RateLimit-Limit -  <br>  * X-RateLimit-Remaining -  <br>  * X-RateLimit-Reset -  <br>  </td></tr>
        <tr><td> 0 </td><td> Unexpected error </td><td>  * RateLimit -  <br>  * RateLimit-Policy -  <br>  * RateLimit-Remaining -  <br>  </td></tr>
     </table>
     */
    public ApiResponse<Object> membersServiceRequestEmailVerificationWithHttpInfo(@javax.annotation.Nonnull RequestEmailVerificationRequest requestEmailVerificationRequest, @javax.annotation.Nullable String idempotencyKey) throws ApiException {
        okhttp3.Call localVarCall = membersServiceRequestEmailVerificationValidateBeforeCall(requestEmailVerificationRequest, idempotencyKey, null);
        Type localVarReturnType = new TypeToken<Object>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Resend a member&#39;s verification email (asynchronously)
     * Sends the verification email again to a member of the workspace whose email address is not verified yet. Pass the member&#39;s email address.  A free workspace with no verified address is paused 7 days after sign-up: its calls answer &#x60;WORKSPACE_UNVERIFIED&#x60; until a member opens the link in that email. Verifying one member&#39;s address, usually the owner&#39;s, lifts the pause for the whole workspace. This operation keeps working while the workspace is paused.  The address must belong to a member of the workspace. One email per address per minute, and 5 per day.  Requires the &#x60;members:write&#x60; scope.
     * @param requestEmailVerificationRequest  (required)
     * @param idempotencyKey A unique value per request, such as a UUID, that makes retrying it safe: a retry with the same key and body returns the first response instead of acting twice. (optional)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table border="1">
       <caption>Response Details</caption>
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Everything worked as expected. </td><td>  * Idempotent-Replayed -  <br>  * RateLimit -  <br>  * RateLimit-Policy -  <br>  * RateLimit-Remaining -  <br>  </td></tr>
        <tr><td> 429 </td><td> Too many requests. Wait the number of seconds in Retry-After, or until the RateLimit window resets, then retry. </td><td>  * RateLimit -  <br>  * RateLimit-Limit -  <br>  * RateLimit-Policy -  <br>  * RateLimit-Remaining -  <br>  * RateLimit-Reset -  <br>  * Retry-After -  <br>  * X-RateLimit-Limit -  <br>  * X-RateLimit-Remaining -  <br>  * X-RateLimit-Reset -  <br>  </td></tr>
        <tr><td> 0 </td><td> Unexpected error </td><td>  * RateLimit -  <br>  * RateLimit-Policy -  <br>  * RateLimit-Remaining -  <br>  </td></tr>
     </table>
     */
    public okhttp3.Call membersServiceRequestEmailVerificationAsync(@javax.annotation.Nonnull RequestEmailVerificationRequest requestEmailVerificationRequest, @javax.annotation.Nullable String idempotencyKey, final ApiCallback<Object> _callback) throws ApiException {

        okhttp3.Call localVarCall = membersServiceRequestEmailVerificationValidateBeforeCall(requestEmailVerificationRequest, idempotencyKey, _callback);
        Type localVarReturnType = new TypeToken<Object>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for membersServiceRevokeAccess
     * @param id Member identifier (UUID) whose access should be suspended (required)
     * @param body  (required)
     * @param idempotencyKey A unique value per request, such as a UUID, that makes retrying it safe: a retry with the same key and body returns the first response instead of acting twice. (optional)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table border="1">
       <caption>Response Details</caption>
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Everything worked as expected. </td><td>  * Idempotent-Replayed -  <br>  * RateLimit -  <br>  * RateLimit-Policy -  <br>  * RateLimit-Remaining -  <br>  </td></tr>
        <tr><td> 429 </td><td> Too many requests. Wait the number of seconds in Retry-After, or until the RateLimit window resets, then retry. </td><td>  * RateLimit -  <br>  * RateLimit-Limit -  <br>  * RateLimit-Policy -  <br>  * RateLimit-Remaining -  <br>  * RateLimit-Reset -  <br>  * Retry-After -  <br>  * X-RateLimit-Limit -  <br>  * X-RateLimit-Remaining -  <br>  * X-RateLimit-Reset -  <br>  </td></tr>
        <tr><td> 0 </td><td> Unexpected error </td><td>  * RateLimit -  <br>  * RateLimit-Policy -  <br>  * RateLimit-Remaining -  <br>  </td></tr>
     </table>
     */
    public okhttp3.Call membersServiceRevokeAccessCall(@javax.annotation.Nonnull String id, @javax.annotation.Nonnull Object body, @javax.annotation.Nullable String idempotencyKey, final ApiCallback _callback) throws ApiException {
        String basePath = null;
        // Operation Servers
        String[] localBasePaths = new String[] {  };

        // Determine Base Path to Use
        if (localCustomBaseUrl != null){
            basePath = localCustomBaseUrl;
        } else if ( localBasePaths.length > 0 ) {
            basePath = localBasePaths[localHostIndex];
        } else {
            basePath = null;
        }

        Object localVarPostBody = body;

        // create path and map variables
        String localVarPath = "/v1/members/{id}/revoke"
            .replace("{" + "id" + "}", localVarApiClient.escapeString(id.toString()));

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
        Map<String, String> localVarHeaderParams = new HashMap<String, String>();
        Map<String, String> localVarCookieParams = new HashMap<String, String>();
        Map<String, Object> localVarFormParams = new HashMap<String, Object>();

        final String[] localVarAccepts = {
            "application/json"
        };
        final String localVarAccept = localVarApiClient.selectHeaderAccept(localVarAccepts);
        if (localVarAccept != null) {
            localVarHeaderParams.put("Accept", localVarAccept);
        }

        final String[] localVarContentTypes = {
            "application/json"
        };
        final String localVarContentType = localVarApiClient.selectHeaderContentType(localVarContentTypes);
        if (localVarContentType != null) {
            localVarHeaderParams.put("Content-Type", localVarContentType);
        }

        if (idempotencyKey != null) {
            localVarHeaderParams.put("Idempotency-Key", localVarApiClient.parameterToString(idempotencyKey));
        }


        String[] localVarAuthNames = new String[] { "oauth2", "bearerAuth" };
        return localVarApiClient.buildCall(basePath, localVarPath, "POST", localVarQueryParams, localVarCollectionQueryParams, localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAuthNames, _callback);
    }

    @SuppressWarnings("rawtypes")
    private okhttp3.Call membersServiceRevokeAccessValidateBeforeCall(@javax.annotation.Nonnull String id, @javax.annotation.Nonnull Object body, @javax.annotation.Nullable String idempotencyKey, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new ApiException("Missing the required parameter 'id' when calling membersServiceRevokeAccess(Async)");
        }

        // verify the required parameter 'body' is set
        if (body == null) {
            throw new ApiException("Missing the required parameter 'body' when calling membersServiceRevokeAccess(Async)");
        }

        return membersServiceRevokeAccessCall(id, body, idempotencyKey, _callback);

    }

    /**
     * Revoke access for a member
     * Suspend a member&#39;s access without removing their account.  Requires the &#x60;members:write&#x60; scope.
     * @param id Member identifier (UUID) whose access should be suspended (required)
     * @param body  (required)
     * @param idempotencyKey A unique value per request, such as a UUID, that makes retrying it safe: a retry with the same key and body returns the first response instead of acting twice. (optional)
     * @return Member
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table border="1">
       <caption>Response Details</caption>
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Everything worked as expected. </td><td>  * Idempotent-Replayed -  <br>  * RateLimit -  <br>  * RateLimit-Policy -  <br>  * RateLimit-Remaining -  <br>  </td></tr>
        <tr><td> 429 </td><td> Too many requests. Wait the number of seconds in Retry-After, or until the RateLimit window resets, then retry. </td><td>  * RateLimit -  <br>  * RateLimit-Limit -  <br>  * RateLimit-Policy -  <br>  * RateLimit-Remaining -  <br>  * RateLimit-Reset -  <br>  * Retry-After -  <br>  * X-RateLimit-Limit -  <br>  * X-RateLimit-Remaining -  <br>  * X-RateLimit-Reset -  <br>  </td></tr>
        <tr><td> 0 </td><td> Unexpected error </td><td>  * RateLimit -  <br>  * RateLimit-Policy -  <br>  * RateLimit-Remaining -  <br>  </td></tr>
     </table>
     */
    public Member membersServiceRevokeAccess(@javax.annotation.Nonnull String id, @javax.annotation.Nonnull Object body, @javax.annotation.Nullable String idempotencyKey) throws ApiException {
        ApiResponse<Member> localVarResp = membersServiceRevokeAccessWithHttpInfo(id, body, idempotencyKey);
        return localVarResp.getData();
    }

    /**
     * Revoke access for a member
     * Suspend a member&#39;s access without removing their account.  Requires the &#x60;members:write&#x60; scope.
     * @param id Member identifier (UUID) whose access should be suspended (required)
     * @param body  (required)
     * @param idempotencyKey A unique value per request, such as a UUID, that makes retrying it safe: a retry with the same key and body returns the first response instead of acting twice. (optional)
     * @return ApiResponse&lt;Member&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table border="1">
       <caption>Response Details</caption>
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Everything worked as expected. </td><td>  * Idempotent-Replayed -  <br>  * RateLimit -  <br>  * RateLimit-Policy -  <br>  * RateLimit-Remaining -  <br>  </td></tr>
        <tr><td> 429 </td><td> Too many requests. Wait the number of seconds in Retry-After, or until the RateLimit window resets, then retry. </td><td>  * RateLimit -  <br>  * RateLimit-Limit -  <br>  * RateLimit-Policy -  <br>  * RateLimit-Remaining -  <br>  * RateLimit-Reset -  <br>  * Retry-After -  <br>  * X-RateLimit-Limit -  <br>  * X-RateLimit-Remaining -  <br>  * X-RateLimit-Reset -  <br>  </td></tr>
        <tr><td> 0 </td><td> Unexpected error </td><td>  * RateLimit -  <br>  * RateLimit-Policy -  <br>  * RateLimit-Remaining -  <br>  </td></tr>
     </table>
     */
    public ApiResponse<Member> membersServiceRevokeAccessWithHttpInfo(@javax.annotation.Nonnull String id, @javax.annotation.Nonnull Object body, @javax.annotation.Nullable String idempotencyKey) throws ApiException {
        okhttp3.Call localVarCall = membersServiceRevokeAccessValidateBeforeCall(id, body, idempotencyKey, null);
        Type localVarReturnType = new TypeToken<Member>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Revoke access for a member (asynchronously)
     * Suspend a member&#39;s access without removing their account.  Requires the &#x60;members:write&#x60; scope.
     * @param id Member identifier (UUID) whose access should be suspended (required)
     * @param body  (required)
     * @param idempotencyKey A unique value per request, such as a UUID, that makes retrying it safe: a retry with the same key and body returns the first response instead of acting twice. (optional)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table border="1">
       <caption>Response Details</caption>
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Everything worked as expected. </td><td>  * Idempotent-Replayed -  <br>  * RateLimit -  <br>  * RateLimit-Policy -  <br>  * RateLimit-Remaining -  <br>  </td></tr>
        <tr><td> 429 </td><td> Too many requests. Wait the number of seconds in Retry-After, or until the RateLimit window resets, then retry. </td><td>  * RateLimit -  <br>  * RateLimit-Limit -  <br>  * RateLimit-Policy -  <br>  * RateLimit-Remaining -  <br>  * RateLimit-Reset -  <br>  * Retry-After -  <br>  * X-RateLimit-Limit -  <br>  * X-RateLimit-Remaining -  <br>  * X-RateLimit-Reset -  <br>  </td></tr>
        <tr><td> 0 </td><td> Unexpected error </td><td>  * RateLimit -  <br>  * RateLimit-Policy -  <br>  * RateLimit-Remaining -  <br>  </td></tr>
     </table>
     */
    public okhttp3.Call membersServiceRevokeAccessAsync(@javax.annotation.Nonnull String id, @javax.annotation.Nonnull Object body, @javax.annotation.Nullable String idempotencyKey, final ApiCallback<Member> _callback) throws ApiException {

        okhttp3.Call localVarCall = membersServiceRevokeAccessValidateBeforeCall(id, body, idempotencyKey, _callback);
        Type localVarReturnType = new TypeToken<Member>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for membersServiceUpdate
     * @param id Member identifier (UUID) to update (required)
     * @param membersServiceUpdateBody  (required)
     * @param idempotencyKey A unique value per request, such as a UUID, that makes retrying it safe: a retry with the same key and body returns the first response instead of acting twice. (optional)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table border="1">
       <caption>Response Details</caption>
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Everything worked as expected. </td><td>  * Idempotent-Replayed -  <br>  * RateLimit -  <br>  * RateLimit-Policy -  <br>  * RateLimit-Remaining -  <br>  </td></tr>
        <tr><td> 429 </td><td> Too many requests. Wait the number of seconds in Retry-After, or until the RateLimit window resets, then retry. </td><td>  * RateLimit -  <br>  * RateLimit-Limit -  <br>  * RateLimit-Policy -  <br>  * RateLimit-Remaining -  <br>  * RateLimit-Reset -  <br>  * Retry-After -  <br>  * X-RateLimit-Limit -  <br>  * X-RateLimit-Remaining -  <br>  * X-RateLimit-Reset -  <br>  </td></tr>
        <tr><td> 0 </td><td> Unexpected error </td><td>  * RateLimit -  <br>  * RateLimit-Policy -  <br>  * RateLimit-Remaining -  <br>  </td></tr>
     </table>
     */
    public okhttp3.Call membersServiceUpdateCall(@javax.annotation.Nonnull String id, @javax.annotation.Nonnull MembersServiceUpdateBody membersServiceUpdateBody, @javax.annotation.Nullable String idempotencyKey, final ApiCallback _callback) throws ApiException {
        String basePath = null;
        // Operation Servers
        String[] localBasePaths = new String[] {  };

        // Determine Base Path to Use
        if (localCustomBaseUrl != null){
            basePath = localCustomBaseUrl;
        } else if ( localBasePaths.length > 0 ) {
            basePath = localBasePaths[localHostIndex];
        } else {
            basePath = null;
        }

        Object localVarPostBody = membersServiceUpdateBody;

        // create path and map variables
        String localVarPath = "/v1/members/{id}"
            .replace("{" + "id" + "}", localVarApiClient.escapeString(id.toString()));

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
        Map<String, String> localVarHeaderParams = new HashMap<String, String>();
        Map<String, String> localVarCookieParams = new HashMap<String, String>();
        Map<String, Object> localVarFormParams = new HashMap<String, Object>();

        final String[] localVarAccepts = {
            "application/json"
        };
        final String localVarAccept = localVarApiClient.selectHeaderAccept(localVarAccepts);
        if (localVarAccept != null) {
            localVarHeaderParams.put("Accept", localVarAccept);
        }

        final String[] localVarContentTypes = {
            "application/json"
        };
        final String localVarContentType = localVarApiClient.selectHeaderContentType(localVarContentTypes);
        if (localVarContentType != null) {
            localVarHeaderParams.put("Content-Type", localVarContentType);
        }

        if (idempotencyKey != null) {
            localVarHeaderParams.put("Idempotency-Key", localVarApiClient.parameterToString(idempotencyKey));
        }


        String[] localVarAuthNames = new String[] { "oauth2", "bearerAuth" };
        return localVarApiClient.buildCall(basePath, localVarPath, "PUT", localVarQueryParams, localVarCollectionQueryParams, localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAuthNames, _callback);
    }

    @SuppressWarnings("rawtypes")
    private okhttp3.Call membersServiceUpdateValidateBeforeCall(@javax.annotation.Nonnull String id, @javax.annotation.Nonnull MembersServiceUpdateBody membersServiceUpdateBody, @javax.annotation.Nullable String idempotencyKey, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new ApiException("Missing the required parameter 'id' when calling membersServiceUpdate(Async)");
        }

        // verify the required parameter 'membersServiceUpdateBody' is set
        if (membersServiceUpdateBody == null) {
            throw new ApiException("Missing the required parameter 'membersServiceUpdateBody' when calling membersServiceUpdate(Async)");
        }

        return membersServiceUpdateCall(id, membersServiceUpdateBody, idempotencyKey, _callback);

    }

    /**
     * Update a member
     * Change a teammate&#39;s name, role, display language or email. Only the fields you send are changed: a field left out keeps its value, so &#x60;{\&quot;role\&quot;: \&quot;ROLE_ADMIN\&quot;}&#x60; promotes a teammate and leaves the rest of their profile as it is.  A role other than &#x60;ROLE_ADMIN&#x60; or &#x60;ROLE_VIEWER&#x60; fails with &#x60;MEMBER_ROLE_NOT_ALLOWED&#x60;; a language not offered fails with &#x60;MEMBER_LANGUAGE_NOT_ALLOWED&#x60;.  Requires the &#x60;members:write&#x60; scope.
     * @param id Member identifier (UUID) to update (required)
     * @param membersServiceUpdateBody  (required)
     * @param idempotencyKey A unique value per request, such as a UUID, that makes retrying it safe: a retry with the same key and body returns the first response instead of acting twice. (optional)
     * @return Member
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table border="1">
       <caption>Response Details</caption>
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Everything worked as expected. </td><td>  * Idempotent-Replayed -  <br>  * RateLimit -  <br>  * RateLimit-Policy -  <br>  * RateLimit-Remaining -  <br>  </td></tr>
        <tr><td> 429 </td><td> Too many requests. Wait the number of seconds in Retry-After, or until the RateLimit window resets, then retry. </td><td>  * RateLimit -  <br>  * RateLimit-Limit -  <br>  * RateLimit-Policy -  <br>  * RateLimit-Remaining -  <br>  * RateLimit-Reset -  <br>  * Retry-After -  <br>  * X-RateLimit-Limit -  <br>  * X-RateLimit-Remaining -  <br>  * X-RateLimit-Reset -  <br>  </td></tr>
        <tr><td> 0 </td><td> Unexpected error </td><td>  * RateLimit -  <br>  * RateLimit-Policy -  <br>  * RateLimit-Remaining -  <br>  </td></tr>
     </table>
     */
    public Member membersServiceUpdate(@javax.annotation.Nonnull String id, @javax.annotation.Nonnull MembersServiceUpdateBody membersServiceUpdateBody, @javax.annotation.Nullable String idempotencyKey) throws ApiException {
        ApiResponse<Member> localVarResp = membersServiceUpdateWithHttpInfo(id, membersServiceUpdateBody, idempotencyKey);
        return localVarResp.getData();
    }

    /**
     * Update a member
     * Change a teammate&#39;s name, role, display language or email. Only the fields you send are changed: a field left out keeps its value, so &#x60;{\&quot;role\&quot;: \&quot;ROLE_ADMIN\&quot;}&#x60; promotes a teammate and leaves the rest of their profile as it is.  A role other than &#x60;ROLE_ADMIN&#x60; or &#x60;ROLE_VIEWER&#x60; fails with &#x60;MEMBER_ROLE_NOT_ALLOWED&#x60;; a language not offered fails with &#x60;MEMBER_LANGUAGE_NOT_ALLOWED&#x60;.  Requires the &#x60;members:write&#x60; scope.
     * @param id Member identifier (UUID) to update (required)
     * @param membersServiceUpdateBody  (required)
     * @param idempotencyKey A unique value per request, such as a UUID, that makes retrying it safe: a retry with the same key and body returns the first response instead of acting twice. (optional)
     * @return ApiResponse&lt;Member&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table border="1">
       <caption>Response Details</caption>
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Everything worked as expected. </td><td>  * Idempotent-Replayed -  <br>  * RateLimit -  <br>  * RateLimit-Policy -  <br>  * RateLimit-Remaining -  <br>  </td></tr>
        <tr><td> 429 </td><td> Too many requests. Wait the number of seconds in Retry-After, or until the RateLimit window resets, then retry. </td><td>  * RateLimit -  <br>  * RateLimit-Limit -  <br>  * RateLimit-Policy -  <br>  * RateLimit-Remaining -  <br>  * RateLimit-Reset -  <br>  * Retry-After -  <br>  * X-RateLimit-Limit -  <br>  * X-RateLimit-Remaining -  <br>  * X-RateLimit-Reset -  <br>  </td></tr>
        <tr><td> 0 </td><td> Unexpected error </td><td>  * RateLimit -  <br>  * RateLimit-Policy -  <br>  * RateLimit-Remaining -  <br>  </td></tr>
     </table>
     */
    public ApiResponse<Member> membersServiceUpdateWithHttpInfo(@javax.annotation.Nonnull String id, @javax.annotation.Nonnull MembersServiceUpdateBody membersServiceUpdateBody, @javax.annotation.Nullable String idempotencyKey) throws ApiException {
        okhttp3.Call localVarCall = membersServiceUpdateValidateBeforeCall(id, membersServiceUpdateBody, idempotencyKey, null);
        Type localVarReturnType = new TypeToken<Member>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Update a member (asynchronously)
     * Change a teammate&#39;s name, role, display language or email. Only the fields you send are changed: a field left out keeps its value, so &#x60;{\&quot;role\&quot;: \&quot;ROLE_ADMIN\&quot;}&#x60; promotes a teammate and leaves the rest of their profile as it is.  A role other than &#x60;ROLE_ADMIN&#x60; or &#x60;ROLE_VIEWER&#x60; fails with &#x60;MEMBER_ROLE_NOT_ALLOWED&#x60;; a language not offered fails with &#x60;MEMBER_LANGUAGE_NOT_ALLOWED&#x60;.  Requires the &#x60;members:write&#x60; scope.
     * @param id Member identifier (UUID) to update (required)
     * @param membersServiceUpdateBody  (required)
     * @param idempotencyKey A unique value per request, such as a UUID, that makes retrying it safe: a retry with the same key and body returns the first response instead of acting twice. (optional)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table border="1">
       <caption>Response Details</caption>
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Everything worked as expected. </td><td>  * Idempotent-Replayed -  <br>  * RateLimit -  <br>  * RateLimit-Policy -  <br>  * RateLimit-Remaining -  <br>  </td></tr>
        <tr><td> 429 </td><td> Too many requests. Wait the number of seconds in Retry-After, or until the RateLimit window resets, then retry. </td><td>  * RateLimit -  <br>  * RateLimit-Limit -  <br>  * RateLimit-Policy -  <br>  * RateLimit-Remaining -  <br>  * RateLimit-Reset -  <br>  * Retry-After -  <br>  * X-RateLimit-Limit -  <br>  * X-RateLimit-Remaining -  <br>  * X-RateLimit-Reset -  <br>  </td></tr>
        <tr><td> 0 </td><td> Unexpected error </td><td>  * RateLimit -  <br>  * RateLimit-Policy -  <br>  * RateLimit-Remaining -  <br>  </td></tr>
     </table>
     */
    public okhttp3.Call membersServiceUpdateAsync(@javax.annotation.Nonnull String id, @javax.annotation.Nonnull MembersServiceUpdateBody membersServiceUpdateBody, @javax.annotation.Nullable String idempotencyKey, final ApiCallback<Member> _callback) throws ApiException {

        okhttp3.Call localVarCall = membersServiceUpdateValidateBeforeCall(id, membersServiceUpdateBody, idempotencyKey, _callback);
        Type localVarReturnType = new TypeToken<Member>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
}
