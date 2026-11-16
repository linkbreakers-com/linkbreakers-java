/*
 * Linkbreakers API
 * The Linkbreakers REST API: links, QR codes, workflows, visitors and analytics, as JSON over HTTPS. Send a workspace API token as `Authorization: Bearer <token>`.  ## Entry points  `GET https://api.linkbreakers.com/` and `GET https://api.linkbreakers.com/v1` answer without credentials with the API's name, version and links. This document is served at `https://api.linkbreakers.com/openapi.json`.  ## Rate limits  Every response describes where the caller stands in the IETF `RateLimit-Policy` and `RateLimit` header fields (draft-ietf-httpapi-ratelimit-headers), and in `RateLimit-Limit`, `RateLimit-Remaining`, `RateLimit-Reset` and `X-RateLimit-Limit`, `X-RateLimit-Remaining`, `X-RateLimit-Reset` (a Unix timestamp) for clients that read those. A request past the limit gets `429 Too Many Requests` with `Retry-After` in seconds.  - Workspace API token: 720 requests per 60 seconds, per token. - Dashboard session or OAuth token: 600 requests per 60 seconds, per token. - No credentials: 300 requests per 60 seconds, per client IP.  Each plan also includes a number of API calls per billing cycle. Past it, calls answer `429` with the code `MAX_PUBLIC_API_QUERIED_REACHED` until the cycle renews or the plan changes.  ## Pagination  Every list pages with opaque cursors (AIP-158). Pass `pageSize`, then send the `nextPageToken` of each response back as `pageToken` (or its alias `cursor`) to get the next page. An empty or absent `nextPageToken` means the list is complete. Each list operation describes this in `x-pagination`.  ## Batches and background jobs  `POST /v1/links/bulk` creates up to 250 links in one transactional request; `POST /v1/links/batch` takes the same links as a bare JSON array. `POST /v1/links/bulk/jobs` accepts the same batch and answers `202 Accepted` at once with a job and a `Location` header; poll `GET /v1/jobs/{id}` until `status` is `JOB_STATUS_SUCCEEDED` (the created link ids are in `linksBulkCreate.linkIds`) or `JOB_STATUS_FAILED` (`error` says why, and nothing was created). Jobs can be read for 24 hours after they finish.  ## Paused workspaces  A free workspace whose members have not verified any email address is paused 7 days after sign-up. Its calls then answer `403` with the code `WORKSPACE_UNVERIFIED` and change nothing; QR codes and links keep working for visitors. To lift the pause, ask the workspace owner to verify their email: `POST /v1/members/email-verification` sends the verification email again, and works while the workspace is paused. `unverifiedPauseAt` on the workspace says when the pause starts.  ## Versioning and deprecation  The version is in the path: every operation lives under `/v1`. Within a version, changes are additive only: new operations, new optional request fields, new response fields and new enum values. Clients should ignore fields and enum values they do not know. Anything that would break an existing client ships under a new path version instead.  An operation is retired in two steps. First it is deprecated: it keeps working, is flagged `deprecated: true` in this document, and every response carries a `Deprecation` header field (RFC 9745) with the time it was deprecated and a `Link` with `rel=\"deprecation\"` to the policy. Then, at least 180 days later, it may be removed; that date is announced in advance in a `Sunset` header field (RFC 8594) and as `x-sunset` on the operation. The full policy: https://linkbreakers.com/help/article/api-versioning-and-deprecation-policy  A renamed operation keeps answering its former path until that path's sunset. Responses on a former path carry the same `Deprecation`, `Sunset` and `Link` header fields; this document lists only the current path, and the policy lists the former ones.  ## Idempotent retries  Every POST, PUT and PATCH accepts an optional `Idempotency-Key` header (draft-ietf-httpapi-idempotency-key-header). Send a new unique value, such as a UUID, with each logical request and reuse it only to retry that request. A retry with the same key and the same request returns the first response, marked `Idempotent-Replayed: true`, instead of running again. Keys are scoped to the token and kept for 24 hours. The same key with a different request is refused with `422`, and a retry while the first request is still running gets `409` with `Retry-After`. Server errors (5xx) and refusals such as 401, 403 and 429 are not stored, so retrying them runs the request again.  ## Identifying your client  Send a `Linkbreakers-Client` request header saying which client made the call: `<client>/<version> (<details>)`, for example `my-integration/1.2.0 (python 3.12; linux)`. The official SDKs, the CLI and the MCP bridge set it for you, as `sdk-python/2.3.1 (python 3.12.4; linux)` or `cli/1.4.0 (darwin arm64)`. It is optional and changes nothing about how a request is handled; it tells us which clients and versions are in use, so we know what can be retired and what to fix first. Keep the details short and free of personal data. Browsers may send it too: it is allowed by CORS.
 *
 * The version of the OpenAPI document: 1.199.0
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


import com.linkbreakers.model.AnalyticsDashboardsServiceGenerateWidgetSummaryBody;
import com.linkbreakers.model.AnalyticsDashboardsServiceUpdateBody;
import com.linkbreakers.model.CreateDashboardRequest;
import com.linkbreakers.model.CreateDashboardResponse;
import com.linkbreakers.model.ErrorResponse;
import com.linkbreakers.model.GenerateWidgetSummaryResponse;
import com.linkbreakers.model.GetWidgetSummaryResponse;
import com.linkbreakers.model.ListDashboardsResponse;
import com.linkbreakers.model.UpdateDashboardResponse;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AnalyticsDashboardsApi {
    private ApiClient localVarApiClient;
    private int localHostIndex;
    private String localCustomBaseUrl;

    public AnalyticsDashboardsApi() {
        this(Configuration.getDefaultApiClient());
    }

    public AnalyticsDashboardsApi(ApiClient apiClient) {
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
     * Build call for analyticsDashboardsServiceCreate
     * @param createDashboardRequest  (required)
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
    public okhttp3.Call analyticsDashboardsServiceCreateCall(@javax.annotation.Nonnull CreateDashboardRequest createDashboardRequest, @javax.annotation.Nullable String idempotencyKey, final ApiCallback _callback) throws ApiException {
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

        Object localVarPostBody = createDashboardRequest;

        // create path and map variables
        String localVarPath = "/v1/analytics/dashboards";

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
    private okhttp3.Call analyticsDashboardsServiceCreateValidateBeforeCall(@javax.annotation.Nonnull CreateDashboardRequest createDashboardRequest, @javax.annotation.Nullable String idempotencyKey, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'createDashboardRequest' is set
        if (createDashboardRequest == null) {
            throw new ApiException("Missing the required parameter 'createDashboardRequest' when calling analyticsDashboardsServiceCreate(Async)");
        }

        return analyticsDashboardsServiceCreateCall(createDashboardRequest, idempotencyKey, _callback);

    }

    /**
     * Create analytics dashboard
     * Create a dashboard with its widgets, for the whole workspace or scoped to one link.  Requires the &#x60;analytics:write&#x60; scope.
     * @param createDashboardRequest  (required)
     * @param idempotencyKey A unique value per request, such as a UUID, that makes retrying it safe: a retry with the same key and body returns the first response instead of acting twice. (optional)
     * @return CreateDashboardResponse
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
    public CreateDashboardResponse analyticsDashboardsServiceCreate(@javax.annotation.Nonnull CreateDashboardRequest createDashboardRequest, @javax.annotation.Nullable String idempotencyKey) throws ApiException {
        ApiResponse<CreateDashboardResponse> localVarResp = analyticsDashboardsServiceCreateWithHttpInfo(createDashboardRequest, idempotencyKey);
        return localVarResp.getData();
    }

    /**
     * Create analytics dashboard
     * Create a dashboard with its widgets, for the whole workspace or scoped to one link.  Requires the &#x60;analytics:write&#x60; scope.
     * @param createDashboardRequest  (required)
     * @param idempotencyKey A unique value per request, such as a UUID, that makes retrying it safe: a retry with the same key and body returns the first response instead of acting twice. (optional)
     * @return ApiResponse&lt;CreateDashboardResponse&gt;
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
    public ApiResponse<CreateDashboardResponse> analyticsDashboardsServiceCreateWithHttpInfo(@javax.annotation.Nonnull CreateDashboardRequest createDashboardRequest, @javax.annotation.Nullable String idempotencyKey) throws ApiException {
        okhttp3.Call localVarCall = analyticsDashboardsServiceCreateValidateBeforeCall(createDashboardRequest, idempotencyKey, null);
        Type localVarReturnType = new TypeToken<CreateDashboardResponse>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Create analytics dashboard (asynchronously)
     * Create a dashboard with its widgets, for the whole workspace or scoped to one link.  Requires the &#x60;analytics:write&#x60; scope.
     * @param createDashboardRequest  (required)
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
    public okhttp3.Call analyticsDashboardsServiceCreateAsync(@javax.annotation.Nonnull CreateDashboardRequest createDashboardRequest, @javax.annotation.Nullable String idempotencyKey, final ApiCallback<CreateDashboardResponse> _callback) throws ApiException {

        okhttp3.Call localVarCall = analyticsDashboardsServiceCreateValidateBeforeCall(createDashboardRequest, idempotencyKey, _callback);
        Type localVarReturnType = new TypeToken<CreateDashboardResponse>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for analyticsDashboardsServiceDelete
     * @param id  (required)
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
    public okhttp3.Call analyticsDashboardsServiceDeleteCall(@javax.annotation.Nonnull String id, final ApiCallback _callback) throws ApiException {
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
        String localVarPath = "/v1/analytics/dashboards/{id}"
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
    private okhttp3.Call analyticsDashboardsServiceDeleteValidateBeforeCall(@javax.annotation.Nonnull String id, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new ApiException("Missing the required parameter 'id' when calling analyticsDashboardsServiceDelete(Async)");
        }

        return analyticsDashboardsServiceDeleteCall(id, _callback);

    }

    /**
     * Delete analytics dashboard
     * Delete a dashboard and its associated assistant conversation.  Requires the &#x60;analytics:write&#x60; scope.
     * @param id  (required)
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
    public Object analyticsDashboardsServiceDelete(@javax.annotation.Nonnull String id) throws ApiException {
        ApiResponse<Object> localVarResp = analyticsDashboardsServiceDeleteWithHttpInfo(id);
        return localVarResp.getData();
    }

    /**
     * Delete analytics dashboard
     * Delete a dashboard and its associated assistant conversation.  Requires the &#x60;analytics:write&#x60; scope.
     * @param id  (required)
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
    public ApiResponse<Object> analyticsDashboardsServiceDeleteWithHttpInfo(@javax.annotation.Nonnull String id) throws ApiException {
        okhttp3.Call localVarCall = analyticsDashboardsServiceDeleteValidateBeforeCall(id, null);
        Type localVarReturnType = new TypeToken<Object>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Delete analytics dashboard (asynchronously)
     * Delete a dashboard and its associated assistant conversation.  Requires the &#x60;analytics:write&#x60; scope.
     * @param id  (required)
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
    public okhttp3.Call analyticsDashboardsServiceDeleteAsync(@javax.annotation.Nonnull String id, final ApiCallback<Object> _callback) throws ApiException {

        okhttp3.Call localVarCall = analyticsDashboardsServiceDeleteValidateBeforeCall(id, _callback);
        Type localVarReturnType = new TypeToken<Object>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for analyticsDashboardsServiceGenerateWidgetSummary
     * @param dashboardId Dashboard holding the widget (required)
     * @param widgetId Widget whose values should be analysed (required)
     * @param analyticsDashboardsServiceGenerateWidgetSummaryBody  (required)
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
    public okhttp3.Call analyticsDashboardsServiceGenerateWidgetSummaryCall(@javax.annotation.Nonnull String dashboardId, @javax.annotation.Nonnull String widgetId, @javax.annotation.Nonnull AnalyticsDashboardsServiceGenerateWidgetSummaryBody analyticsDashboardsServiceGenerateWidgetSummaryBody, @javax.annotation.Nullable String idempotencyKey, final ApiCallback _callback) throws ApiException {
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

        Object localVarPostBody = analyticsDashboardsServiceGenerateWidgetSummaryBody;

        // create path and map variables
        String localVarPath = "/v1/analytics/dashboards/{dashboardId}/widgets/{widgetId}/summary"
            .replace("{" + "dashboardId" + "}", localVarApiClient.escapeString(dashboardId.toString()))
            .replace("{" + "widgetId" + "}", localVarApiClient.escapeString(widgetId.toString()));

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
    private okhttp3.Call analyticsDashboardsServiceGenerateWidgetSummaryValidateBeforeCall(@javax.annotation.Nonnull String dashboardId, @javax.annotation.Nonnull String widgetId, @javax.annotation.Nonnull AnalyticsDashboardsServiceGenerateWidgetSummaryBody analyticsDashboardsServiceGenerateWidgetSummaryBody, @javax.annotation.Nullable String idempotencyKey, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'dashboardId' is set
        if (dashboardId == null) {
            throw new ApiException("Missing the required parameter 'dashboardId' when calling analyticsDashboardsServiceGenerateWidgetSummary(Async)");
        }

        // verify the required parameter 'widgetId' is set
        if (widgetId == null) {
            throw new ApiException("Missing the required parameter 'widgetId' when calling analyticsDashboardsServiceGenerateWidgetSummary(Async)");
        }

        // verify the required parameter 'analyticsDashboardsServiceGenerateWidgetSummaryBody' is set
        if (analyticsDashboardsServiceGenerateWidgetSummaryBody == null) {
            throw new ApiException("Missing the required parameter 'analyticsDashboardsServiceGenerateWidgetSummaryBody' when calling analyticsDashboardsServiceGenerateWidgetSummary(Async)");
        }

        return analyticsDashboardsServiceGenerateWidgetSummaryCall(dashboardId, widgetId, analyticsDashboardsServiceGenerateWidgetSummaryBody, idempotencyKey, _callback);

    }

    /**
     * Generate a widget assistant summary
     * Analyses the widget&#39;s current values and stores the result. Consumes platform credits, so it runs only when requested.  Requires the &#x60;analytics:write&#x60; scope.
     * @param dashboardId Dashboard holding the widget (required)
     * @param widgetId Widget whose values should be analysed (required)
     * @param analyticsDashboardsServiceGenerateWidgetSummaryBody  (required)
     * @param idempotencyKey A unique value per request, such as a UUID, that makes retrying it safe: a retry with the same key and body returns the first response instead of acting twice. (optional)
     * @return GenerateWidgetSummaryResponse
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
    public GenerateWidgetSummaryResponse analyticsDashboardsServiceGenerateWidgetSummary(@javax.annotation.Nonnull String dashboardId, @javax.annotation.Nonnull String widgetId, @javax.annotation.Nonnull AnalyticsDashboardsServiceGenerateWidgetSummaryBody analyticsDashboardsServiceGenerateWidgetSummaryBody, @javax.annotation.Nullable String idempotencyKey) throws ApiException {
        ApiResponse<GenerateWidgetSummaryResponse> localVarResp = analyticsDashboardsServiceGenerateWidgetSummaryWithHttpInfo(dashboardId, widgetId, analyticsDashboardsServiceGenerateWidgetSummaryBody, idempotencyKey);
        return localVarResp.getData();
    }

    /**
     * Generate a widget assistant summary
     * Analyses the widget&#39;s current values and stores the result. Consumes platform credits, so it runs only when requested.  Requires the &#x60;analytics:write&#x60; scope.
     * @param dashboardId Dashboard holding the widget (required)
     * @param widgetId Widget whose values should be analysed (required)
     * @param analyticsDashboardsServiceGenerateWidgetSummaryBody  (required)
     * @param idempotencyKey A unique value per request, such as a UUID, that makes retrying it safe: a retry with the same key and body returns the first response instead of acting twice. (optional)
     * @return ApiResponse&lt;GenerateWidgetSummaryResponse&gt;
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
    public ApiResponse<GenerateWidgetSummaryResponse> analyticsDashboardsServiceGenerateWidgetSummaryWithHttpInfo(@javax.annotation.Nonnull String dashboardId, @javax.annotation.Nonnull String widgetId, @javax.annotation.Nonnull AnalyticsDashboardsServiceGenerateWidgetSummaryBody analyticsDashboardsServiceGenerateWidgetSummaryBody, @javax.annotation.Nullable String idempotencyKey) throws ApiException {
        okhttp3.Call localVarCall = analyticsDashboardsServiceGenerateWidgetSummaryValidateBeforeCall(dashboardId, widgetId, analyticsDashboardsServiceGenerateWidgetSummaryBody, idempotencyKey, null);
        Type localVarReturnType = new TypeToken<GenerateWidgetSummaryResponse>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Generate a widget assistant summary (asynchronously)
     * Analyses the widget&#39;s current values and stores the result. Consumes platform credits, so it runs only when requested.  Requires the &#x60;analytics:write&#x60; scope.
     * @param dashboardId Dashboard holding the widget (required)
     * @param widgetId Widget whose values should be analysed (required)
     * @param analyticsDashboardsServiceGenerateWidgetSummaryBody  (required)
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
    public okhttp3.Call analyticsDashboardsServiceGenerateWidgetSummaryAsync(@javax.annotation.Nonnull String dashboardId, @javax.annotation.Nonnull String widgetId, @javax.annotation.Nonnull AnalyticsDashboardsServiceGenerateWidgetSummaryBody analyticsDashboardsServiceGenerateWidgetSummaryBody, @javax.annotation.Nullable String idempotencyKey, final ApiCallback<GenerateWidgetSummaryResponse> _callback) throws ApiException {

        okhttp3.Call localVarCall = analyticsDashboardsServiceGenerateWidgetSummaryValidateBeforeCall(dashboardId, widgetId, analyticsDashboardsServiceGenerateWidgetSummaryBody, idempotencyKey, _callback);
        Type localVarReturnType = new TypeToken<GenerateWidgetSummaryResponse>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for analyticsDashboardsServiceGetWidgetSummary
     * @param dashboardId  (required)
     * @param widgetId  (required)
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
    public okhttp3.Call analyticsDashboardsServiceGetWidgetSummaryCall(@javax.annotation.Nonnull String dashboardId, @javax.annotation.Nonnull String widgetId, final ApiCallback _callback) throws ApiException {
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
        String localVarPath = "/v1/analytics/dashboards/{dashboardId}/widgets/{widgetId}/summary"
            .replace("{" + "dashboardId" + "}", localVarApiClient.escapeString(dashboardId.toString()))
            .replace("{" + "widgetId" + "}", localVarApiClient.escapeString(widgetId.toString()));

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
    private okhttp3.Call analyticsDashboardsServiceGetWidgetSummaryValidateBeforeCall(@javax.annotation.Nonnull String dashboardId, @javax.annotation.Nonnull String widgetId, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'dashboardId' is set
        if (dashboardId == null) {
            throw new ApiException("Missing the required parameter 'dashboardId' when calling analyticsDashboardsServiceGetWidgetSummary(Async)");
        }

        // verify the required parameter 'widgetId' is set
        if (widgetId == null) {
            throw new ApiException("Missing the required parameter 'widgetId' when calling analyticsDashboardsServiceGetWidgetSummary(Async)");
        }

        return analyticsDashboardsServiceGetWidgetSummaryCall(dashboardId, widgetId, _callback);

    }

    /**
     * Get a stored widget assistant summary
     * Returns the last generated analysis, and whether the underlying values have changed since.  Requires the &#x60;analytics:read&#x60; scope.
     * @param dashboardId  (required)
     * @param widgetId  (required)
     * @return GetWidgetSummaryResponse
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
    public GetWidgetSummaryResponse analyticsDashboardsServiceGetWidgetSummary(@javax.annotation.Nonnull String dashboardId, @javax.annotation.Nonnull String widgetId) throws ApiException {
        ApiResponse<GetWidgetSummaryResponse> localVarResp = analyticsDashboardsServiceGetWidgetSummaryWithHttpInfo(dashboardId, widgetId);
        return localVarResp.getData();
    }

    /**
     * Get a stored widget assistant summary
     * Returns the last generated analysis, and whether the underlying values have changed since.  Requires the &#x60;analytics:read&#x60; scope.
     * @param dashboardId  (required)
     * @param widgetId  (required)
     * @return ApiResponse&lt;GetWidgetSummaryResponse&gt;
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
    public ApiResponse<GetWidgetSummaryResponse> analyticsDashboardsServiceGetWidgetSummaryWithHttpInfo(@javax.annotation.Nonnull String dashboardId, @javax.annotation.Nonnull String widgetId) throws ApiException {
        okhttp3.Call localVarCall = analyticsDashboardsServiceGetWidgetSummaryValidateBeforeCall(dashboardId, widgetId, null);
        Type localVarReturnType = new TypeToken<GetWidgetSummaryResponse>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Get a stored widget assistant summary (asynchronously)
     * Returns the last generated analysis, and whether the underlying values have changed since.  Requires the &#x60;analytics:read&#x60; scope.
     * @param dashboardId  (required)
     * @param widgetId  (required)
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
    public okhttp3.Call analyticsDashboardsServiceGetWidgetSummaryAsync(@javax.annotation.Nonnull String dashboardId, @javax.annotation.Nonnull String widgetId, final ApiCallback<GetWidgetSummaryResponse> _callback) throws ApiException {

        okhttp3.Call localVarCall = analyticsDashboardsServiceGetWidgetSummaryValidateBeforeCall(dashboardId, widgetId, _callback);
        Type localVarReturnType = new TypeToken<GetWidgetSummaryResponse>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for analyticsDashboardsServiceList
     * @param linkId Restricts the result to the dashboard scoped to this link. Takes precedence over scope when both are set. (optional)
     * @param scope Restricts the result by scope. Unset returns every dashboard.   - DASHBOARD_SCOPE_UNSPECIFIED: Every dashboard in the workspace, link-scoped or not.  - DASHBOARD_SCOPE_WORKSPACE: Only the workspace-wide dashboard, the one with no link.  - DASHBOARD_SCOPE_LINK: Only dashboards tied to a link. (optional)
     * @param pageSize Maximum number to return. Defaults to 100, capped at 1000. (optional)
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
        <tr><td> 429 </td><td> Too many requests. Wait the number of seconds in Retry-After, or until the RateLimit window resets, then retry. </td><td>  * RateLimit -  <br>  * RateLimit-Limit -  <br>  * RateLimit-Policy -  <br>  * RateLimit-Remaining -  <br>  * RateLimit-Reset -  <br>  * Retry-After -  <br>  * X-RateLimit-Limit -  <br>  * X-RateLimit-Remaining -  <br>  * X-RateLimit-Reset -  <br>  </td></tr>
        <tr><td> 0 </td><td> Unexpected error </td><td>  * RateLimit -  <br>  * RateLimit-Policy -  <br>  * RateLimit-Remaining -  <br>  </td></tr>
     </table>
     */
    public okhttp3.Call analyticsDashboardsServiceListCall(@javax.annotation.Nullable String linkId, @javax.annotation.Nullable String scope, @javax.annotation.Nullable Long pageSize, @javax.annotation.Nullable String pageToken, @javax.annotation.Nullable String cursor, final ApiCallback _callback) throws ApiException {
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
        String localVarPath = "/v1/analytics/dashboards";

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
        Map<String, String> localVarHeaderParams = new HashMap<String, String>();
        Map<String, String> localVarCookieParams = new HashMap<String, String>();
        Map<String, Object> localVarFormParams = new HashMap<String, Object>();

        if (linkId != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("linkId", linkId));
        }

        if (scope != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("scope", scope));
        }

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
    private okhttp3.Call analyticsDashboardsServiceListValidateBeforeCall(@javax.annotation.Nullable String linkId, @javax.annotation.Nullable String scope, @javax.annotation.Nullable Long pageSize, @javax.annotation.Nullable String pageToken, @javax.annotation.Nullable String cursor, final ApiCallback _callback) throws ApiException {
        return analyticsDashboardsServiceListCall(linkId, scope, pageSize, pageToken, cursor, _callback);

    }

    /**
     * List analytics dashboards
     * List the workspace&#39;s analytics dashboards: the workspace-wide one and those scoped to a link. Filter by link or by scope.  Requires the &#x60;analytics:read&#x60; scope.
     * @param linkId Restricts the result to the dashboard scoped to this link. Takes precedence over scope when both are set. (optional)
     * @param scope Restricts the result by scope. Unset returns every dashboard.   - DASHBOARD_SCOPE_UNSPECIFIED: Every dashboard in the workspace, link-scoped or not.  - DASHBOARD_SCOPE_WORKSPACE: Only the workspace-wide dashboard, the one with no link.  - DASHBOARD_SCOPE_LINK: Only dashboards tied to a link. (optional)
     * @param pageSize Maximum number to return. Defaults to 100, capped at 1000. (optional)
     * @param pageToken Cursor from a previous response&#39;s nextPageToken. Empty starts at the first page. Pages are ordered by (createdAt, id), which never change, so a record cannot move between pages while a client is walking them. (optional)
     * @param cursor Opaque cursor from the previous page&#39;s nextPageToken. Same as pageToken; when both are sent, pageToken wins. (optional)
     * @return ListDashboardsResponse
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
    public ListDashboardsResponse analyticsDashboardsServiceList(@javax.annotation.Nullable String linkId, @javax.annotation.Nullable String scope, @javax.annotation.Nullable Long pageSize, @javax.annotation.Nullable String pageToken, @javax.annotation.Nullable String cursor) throws ApiException {
        ApiResponse<ListDashboardsResponse> localVarResp = analyticsDashboardsServiceListWithHttpInfo(linkId, scope, pageSize, pageToken, cursor);
        return localVarResp.getData();
    }

    /**
     * List analytics dashboards
     * List the workspace&#39;s analytics dashboards: the workspace-wide one and those scoped to a link. Filter by link or by scope.  Requires the &#x60;analytics:read&#x60; scope.
     * @param linkId Restricts the result to the dashboard scoped to this link. Takes precedence over scope when both are set. (optional)
     * @param scope Restricts the result by scope. Unset returns every dashboard.   - DASHBOARD_SCOPE_UNSPECIFIED: Every dashboard in the workspace, link-scoped or not.  - DASHBOARD_SCOPE_WORKSPACE: Only the workspace-wide dashboard, the one with no link.  - DASHBOARD_SCOPE_LINK: Only dashboards tied to a link. (optional)
     * @param pageSize Maximum number to return. Defaults to 100, capped at 1000. (optional)
     * @param pageToken Cursor from a previous response&#39;s nextPageToken. Empty starts at the first page. Pages are ordered by (createdAt, id), which never change, so a record cannot move between pages while a client is walking them. (optional)
     * @param cursor Opaque cursor from the previous page&#39;s nextPageToken. Same as pageToken; when both are sent, pageToken wins. (optional)
     * @return ApiResponse&lt;ListDashboardsResponse&gt;
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
    public ApiResponse<ListDashboardsResponse> analyticsDashboardsServiceListWithHttpInfo(@javax.annotation.Nullable String linkId, @javax.annotation.Nullable String scope, @javax.annotation.Nullable Long pageSize, @javax.annotation.Nullable String pageToken, @javax.annotation.Nullable String cursor) throws ApiException {
        okhttp3.Call localVarCall = analyticsDashboardsServiceListValidateBeforeCall(linkId, scope, pageSize, pageToken, cursor, null);
        Type localVarReturnType = new TypeToken<ListDashboardsResponse>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * List analytics dashboards (asynchronously)
     * List the workspace&#39;s analytics dashboards: the workspace-wide one and those scoped to a link. Filter by link or by scope.  Requires the &#x60;analytics:read&#x60; scope.
     * @param linkId Restricts the result to the dashboard scoped to this link. Takes precedence over scope when both are set. (optional)
     * @param scope Restricts the result by scope. Unset returns every dashboard.   - DASHBOARD_SCOPE_UNSPECIFIED: Every dashboard in the workspace, link-scoped or not.  - DASHBOARD_SCOPE_WORKSPACE: Only the workspace-wide dashboard, the one with no link.  - DASHBOARD_SCOPE_LINK: Only dashboards tied to a link. (optional)
     * @param pageSize Maximum number to return. Defaults to 100, capped at 1000. (optional)
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
        <tr><td> 429 </td><td> Too many requests. Wait the number of seconds in Retry-After, or until the RateLimit window resets, then retry. </td><td>  * RateLimit -  <br>  * RateLimit-Limit -  <br>  * RateLimit-Policy -  <br>  * RateLimit-Remaining -  <br>  * RateLimit-Reset -  <br>  * Retry-After -  <br>  * X-RateLimit-Limit -  <br>  * X-RateLimit-Remaining -  <br>  * X-RateLimit-Reset -  <br>  </td></tr>
        <tr><td> 0 </td><td> Unexpected error </td><td>  * RateLimit -  <br>  * RateLimit-Policy -  <br>  * RateLimit-Remaining -  <br>  </td></tr>
     </table>
     */
    public okhttp3.Call analyticsDashboardsServiceListAsync(@javax.annotation.Nullable String linkId, @javax.annotation.Nullable String scope, @javax.annotation.Nullable Long pageSize, @javax.annotation.Nullable String pageToken, @javax.annotation.Nullable String cursor, final ApiCallback<ListDashboardsResponse> _callback) throws ApiException {

        okhttp3.Call localVarCall = analyticsDashboardsServiceListValidateBeforeCall(linkId, scope, pageSize, pageToken, cursor, _callback);
        Type localVarReturnType = new TypeToken<ListDashboardsResponse>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for analyticsDashboardsServiceUpdate
     * @param id ID of the dashboard to update. (required)
     * @param analyticsDashboardsServiceUpdateBody  (required)
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
    public okhttp3.Call analyticsDashboardsServiceUpdateCall(@javax.annotation.Nonnull String id, @javax.annotation.Nonnull AnalyticsDashboardsServiceUpdateBody analyticsDashboardsServiceUpdateBody, @javax.annotation.Nullable String idempotencyKey, final ApiCallback _callback) throws ApiException {
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

        Object localVarPostBody = analyticsDashboardsServiceUpdateBody;

        // create path and map variables
        String localVarPath = "/v1/analytics/dashboards/{id}"
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
    private okhttp3.Call analyticsDashboardsServiceUpdateValidateBeforeCall(@javax.annotation.Nonnull String id, @javax.annotation.Nonnull AnalyticsDashboardsServiceUpdateBody analyticsDashboardsServiceUpdateBody, @javax.annotation.Nullable String idempotencyKey, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new ApiException("Missing the required parameter 'id' when calling analyticsDashboardsServiceUpdate(Async)");
        }

        // verify the required parameter 'analyticsDashboardsServiceUpdateBody' is set
        if (analyticsDashboardsServiceUpdateBody == null) {
            throw new ApiException("Missing the required parameter 'analyticsDashboardsServiceUpdateBody' when calling analyticsDashboardsServiceUpdate(Async)");
        }

        return analyticsDashboardsServiceUpdateCall(id, analyticsDashboardsServiceUpdateBody, idempotencyKey, _callback);

    }

    /**
     * Update analytics dashboard
     * Replace a dashboard&#39;s name and its widget configuration. Each widget names its own data source and visualization.  Requires the &#x60;analytics:write&#x60; scope.
     * @param id ID of the dashboard to update. (required)
     * @param analyticsDashboardsServiceUpdateBody  (required)
     * @param idempotencyKey A unique value per request, such as a UUID, that makes retrying it safe: a retry with the same key and body returns the first response instead of acting twice. (optional)
     * @return UpdateDashboardResponse
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
    public UpdateDashboardResponse analyticsDashboardsServiceUpdate(@javax.annotation.Nonnull String id, @javax.annotation.Nonnull AnalyticsDashboardsServiceUpdateBody analyticsDashboardsServiceUpdateBody, @javax.annotation.Nullable String idempotencyKey) throws ApiException {
        ApiResponse<UpdateDashboardResponse> localVarResp = analyticsDashboardsServiceUpdateWithHttpInfo(id, analyticsDashboardsServiceUpdateBody, idempotencyKey);
        return localVarResp.getData();
    }

    /**
     * Update analytics dashboard
     * Replace a dashboard&#39;s name and its widget configuration. Each widget names its own data source and visualization.  Requires the &#x60;analytics:write&#x60; scope.
     * @param id ID of the dashboard to update. (required)
     * @param analyticsDashboardsServiceUpdateBody  (required)
     * @param idempotencyKey A unique value per request, such as a UUID, that makes retrying it safe: a retry with the same key and body returns the first response instead of acting twice. (optional)
     * @return ApiResponse&lt;UpdateDashboardResponse&gt;
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
    public ApiResponse<UpdateDashboardResponse> analyticsDashboardsServiceUpdateWithHttpInfo(@javax.annotation.Nonnull String id, @javax.annotation.Nonnull AnalyticsDashboardsServiceUpdateBody analyticsDashboardsServiceUpdateBody, @javax.annotation.Nullable String idempotencyKey) throws ApiException {
        okhttp3.Call localVarCall = analyticsDashboardsServiceUpdateValidateBeforeCall(id, analyticsDashboardsServiceUpdateBody, idempotencyKey, null);
        Type localVarReturnType = new TypeToken<UpdateDashboardResponse>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Update analytics dashboard (asynchronously)
     * Replace a dashboard&#39;s name and its widget configuration. Each widget names its own data source and visualization.  Requires the &#x60;analytics:write&#x60; scope.
     * @param id ID of the dashboard to update. (required)
     * @param analyticsDashboardsServiceUpdateBody  (required)
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
    public okhttp3.Call analyticsDashboardsServiceUpdateAsync(@javax.annotation.Nonnull String id, @javax.annotation.Nonnull AnalyticsDashboardsServiceUpdateBody analyticsDashboardsServiceUpdateBody, @javax.annotation.Nullable String idempotencyKey, final ApiCallback<UpdateDashboardResponse> _callback) throws ApiException {

        okhttp3.Call localVarCall = analyticsDashboardsServiceUpdateValidateBeforeCall(id, analyticsDashboardsServiceUpdateBody, idempotencyKey, _callback);
        Type localVarReturnType = new TypeToken<UpdateDashboardResponse>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
}
