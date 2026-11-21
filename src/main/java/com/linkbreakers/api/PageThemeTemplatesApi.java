/*
 * Linkbreakers API
 * The Linkbreakers REST API: links, QR codes, workflows, visitors and analytics, as JSON over HTTPS. Send a workspace API token as `Authorization: Bearer <token>`.  ## Entry points  `GET https://api.linkbreakers.com/` and `GET https://api.linkbreakers.com/v1` answer without credentials with the API's name, version and links. This document is served at `https://api.linkbreakers.com/openapi.json`.  ## Rate limits  Every response describes where the caller stands in the IETF `RateLimit-Policy` and `RateLimit` header fields (draft-ietf-httpapi-ratelimit-headers), and in `RateLimit-Limit`, `RateLimit-Remaining`, `RateLimit-Reset` and `X-RateLimit-Limit`, `X-RateLimit-Remaining`, `X-RateLimit-Reset` (a Unix timestamp) for clients that read those. A request past the limit gets `429 Too Many Requests` with `Retry-After` in seconds.  - Workspace API token: 720 requests per 60 seconds, per token. - Dashboard session or OAuth token: 600 requests per 60 seconds, per token. - No credentials: 300 requests per 60 seconds, per client IP.  Each plan also includes a number of API calls per billing cycle. Past it, calls answer `429` with the code `MAX_PUBLIC_API_QUERIED_REACHED` until the cycle renews or the plan changes.  ## Pagination  Every list pages with opaque cursors (AIP-158). Pass `pageSize`, then send the `nextPageToken` of each response back as `pageToken` (or its alias `cursor`) to get the next page. An empty or absent `nextPageToken` means the list is complete. Each list operation describes this in `x-pagination`.  ## Batches and background jobs  `POST /v1/links/bulk` creates up to 250 links in one transactional request; `POST /v1/links/batch` takes the same links as a bare JSON array. `POST /v1/links/bulk/jobs` accepts the same batch and answers `202 Accepted` at once with a job and a `Location` header; poll `GET /v1/jobs/{id}` until `status` is `JOB_STATUS_SUCCEEDED` (the created link ids are in `linksBulkCreate.linkIds`) or `JOB_STATUS_FAILED` (`error` says why, and nothing was created). Jobs can be read for 24 hours after they finish.  ## Paused workspaces  A free workspace whose members have not verified any email address is paused 7 days after sign-up. Its calls then answer `403` with the code `WORKSPACE_UNVERIFIED` and change nothing; QR codes and links keep working for visitors. To lift the pause, ask the workspace owner to verify their email: `POST /v1/members/email-verification` sends the verification email again, and works while the workspace is paused. `unverifiedPauseAt` on the workspace says when the pause starts.  ## Versioning and deprecation  The version is in the path: every operation lives under `/v1`. Within a version, changes are additive only: new operations, new optional request fields, new response fields and new enum values. Clients should ignore fields and enum values they do not know. Anything that would break an existing client ships under a new path version instead.  An operation is retired in two steps. First it is deprecated: it keeps working, is flagged `deprecated: true` in this document, and every response carries a `Deprecation` header field (RFC 9745) with the time it was deprecated and a `Link` with `rel=\"deprecation\"` to the policy. Then, at least 180 days later, it may be removed; that date is announced in advance in a `Sunset` header field (RFC 8594) and as `x-sunset` on the operation. The full policy: https://linkbreakers.com/help/article/api-versioning-and-deprecation-policy  A renamed operation keeps answering its former path while clients still call it, until that path's sunset at the latest: a former path no client calls for 14 days is removed. Responses on a former path carry the same `Deprecation`, `Sunset` and `Link` header fields; this document lists only the current path, and the policy lists the former paths that still answer.  ## Idempotent retries  Every POST, PUT and PATCH accepts an optional `Idempotency-Key` header (draft-ietf-httpapi-idempotency-key-header). Send a new unique value, such as a UUID, with each logical request and reuse it only to retry that request. A retry with the same key and the same request returns the first response, marked `Idempotent-Replayed: true`, instead of running again. Keys are scoped to the token and kept for 24 hours. The same key with a different request is refused with `422`, and a retry while the first request is still running gets `409` with `Retry-After`. Server errors (5xx) and refusals such as 401, 403 and 429 are not stored, so retrying them runs the request again.  ## Identifying your client  Send a `Linkbreakers-Client` request header saying which client made the call: `<client>/<version> (<details>)`, for example `my-integration/1.2.0 (python 3.12; linux)`. The official SDKs, the CLI and the MCP bridge set it for you, as `sdk-python/2.3.1 (python 3.12.4; linux)` or `cli/1.4.0 (darwin arm64)`. It is optional and changes nothing about how a request is handled; it tells us which clients and versions are in use, so we know what can be retired and what to fix first. Keep the details short and free of personal data. Browsers may send it too: it is allowed by CORS.
 *
 * The version of the OpenAPI document: 1.202.0
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


import com.linkbreakers.model.CreatePageThemeTemplateRequest;
import com.linkbreakers.model.CreatePageThemeTemplateResponse;
import com.linkbreakers.model.ErrorResponse;
import com.linkbreakers.model.ImportPageThemeTemplateRequest;
import com.linkbreakers.model.ImportPageThemeTemplateResponse;
import com.linkbreakers.model.ListPageThemeTemplatesResponse;
import com.linkbreakers.model.PageThemeTemplate;
import com.linkbreakers.model.PageThemeTemplatesServiceUpdateBody;
import com.linkbreakers.model.PageThemeTemplatesServiceUpdateStylingBody;
import com.linkbreakers.model.PublicListPageThemeTemplatesResponse;
import com.linkbreakers.model.PublicPageThemeTemplate;
import com.linkbreakers.model.PublishPageThemeTemplateResponse;
import com.linkbreakers.model.UpdatePageThemeTemplateResponse;
import com.linkbreakers.model.UpdatePageThemeTemplateStylingResponse;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PageThemeTemplatesApi {
    private ApiClient localVarApiClient;
    private int localHostIndex;
    private String localCustomBaseUrl;

    public PageThemeTemplatesApi() {
        this(Configuration.getDefaultApiClient());
    }

    public PageThemeTemplatesApi(ApiClient apiClient) {
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
     * Build call for pageThemeTemplatesServiceCreate
     * @param createPageThemeTemplateRequest  (required)
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
    public okhttp3.Call pageThemeTemplatesServiceCreateCall(@javax.annotation.Nonnull CreatePageThemeTemplateRequest createPageThemeTemplateRequest, @javax.annotation.Nullable String idempotencyKey, final ApiCallback _callback) throws ApiException {
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

        Object localVarPostBody = createPageThemeTemplateRequest;

        // create path and map variables
        String localVarPath = "/v1/page-theme-templates";

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
    private okhttp3.Call pageThemeTemplatesServiceCreateValidateBeforeCall(@javax.annotation.Nonnull CreatePageThemeTemplateRequest createPageThemeTemplateRequest, @javax.annotation.Nullable String idempotencyKey, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'createPageThemeTemplateRequest' is set
        if (createPageThemeTemplateRequest == null) {
            throw new ApiException("Missing the required parameter 'createPageThemeTemplateRequest' when calling pageThemeTemplatesServiceCreate(Async)");
        }

        return pageThemeTemplatesServiceCreateCall(createPageThemeTemplateRequest, idempotencyKey, _callback);

    }

    /**
     * Create a new page theme template
     * Create a page theme template by copying the styling of an existing page theme into a fresh detached theme owned by the template.  Requires the &#x60;page_themes:write&#x60; scope.
     * @param createPageThemeTemplateRequest  (required)
     * @param idempotencyKey A unique value per request, such as a UUID, that makes retrying it safe: a retry with the same key and body returns the first response instead of acting twice. (optional)
     * @return CreatePageThemeTemplateResponse
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
    public CreatePageThemeTemplateResponse pageThemeTemplatesServiceCreate(@javax.annotation.Nonnull CreatePageThemeTemplateRequest createPageThemeTemplateRequest, @javax.annotation.Nullable String idempotencyKey) throws ApiException {
        ApiResponse<CreatePageThemeTemplateResponse> localVarResp = pageThemeTemplatesServiceCreateWithHttpInfo(createPageThemeTemplateRequest, idempotencyKey);
        return localVarResp.getData();
    }

    /**
     * Create a new page theme template
     * Create a page theme template by copying the styling of an existing page theme into a fresh detached theme owned by the template.  Requires the &#x60;page_themes:write&#x60; scope.
     * @param createPageThemeTemplateRequest  (required)
     * @param idempotencyKey A unique value per request, such as a UUID, that makes retrying it safe: a retry with the same key and body returns the first response instead of acting twice. (optional)
     * @return ApiResponse&lt;CreatePageThemeTemplateResponse&gt;
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
    public ApiResponse<CreatePageThemeTemplateResponse> pageThemeTemplatesServiceCreateWithHttpInfo(@javax.annotation.Nonnull CreatePageThemeTemplateRequest createPageThemeTemplateRequest, @javax.annotation.Nullable String idempotencyKey) throws ApiException {
        okhttp3.Call localVarCall = pageThemeTemplatesServiceCreateValidateBeforeCall(createPageThemeTemplateRequest, idempotencyKey, null);
        Type localVarReturnType = new TypeToken<CreatePageThemeTemplateResponse>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Create a new page theme template (asynchronously)
     * Create a page theme template by copying the styling of an existing page theme into a fresh detached theme owned by the template.  Requires the &#x60;page_themes:write&#x60; scope.
     * @param createPageThemeTemplateRequest  (required)
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
    public okhttp3.Call pageThemeTemplatesServiceCreateAsync(@javax.annotation.Nonnull CreatePageThemeTemplateRequest createPageThemeTemplateRequest, @javax.annotation.Nullable String idempotencyKey, final ApiCallback<CreatePageThemeTemplateResponse> _callback) throws ApiException {

        okhttp3.Call localVarCall = pageThemeTemplatesServiceCreateValidateBeforeCall(createPageThemeTemplateRequest, idempotencyKey, _callback);
        Type localVarReturnType = new TypeToken<CreatePageThemeTemplateResponse>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for pageThemeTemplatesServiceDelete
     * @param id  (required)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table border="1">
       <caption>Response Details</caption>
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Everything worked as expected. </td><td>  * RateLimit -  <br>  * RateLimit-Policy -  <br>  * RateLimit-Remaining -  <br>  </td></tr>
        <tr><td> 204 </td><td> Page theme template deleted successfully </td><td>  * RateLimit -  <br>  * RateLimit-Policy -  <br>  * RateLimit-Remaining -  <br>  </td></tr>
        <tr><td> 429 </td><td> Too many requests. Wait the number of seconds in Retry-After, or until the RateLimit window resets, then retry. </td><td>  * RateLimit -  <br>  * RateLimit-Limit -  <br>  * RateLimit-Policy -  <br>  * RateLimit-Remaining -  <br>  * RateLimit-Reset -  <br>  * Retry-After -  <br>  * X-RateLimit-Limit -  <br>  * X-RateLimit-Remaining -  <br>  * X-RateLimit-Reset -  <br>  </td></tr>
        <tr><td> 0 </td><td> Unexpected error </td><td>  * RateLimit -  <br>  * RateLimit-Policy -  <br>  * RateLimit-Remaining -  <br>  </td></tr>
     </table>
     */
    public okhttp3.Call pageThemeTemplatesServiceDeleteCall(@javax.annotation.Nonnull String id, final ApiCallback _callback) throws ApiException {
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
        String localVarPath = "/v1/page-theme-templates/{id}"
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
    private okhttp3.Call pageThemeTemplatesServiceDeleteValidateBeforeCall(@javax.annotation.Nonnull String id, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new ApiException("Missing the required parameter 'id' when calling pageThemeTemplatesServiceDelete(Async)");
        }

        return pageThemeTemplatesServiceDeleteCall(id, _callback);

    }

    /**
     * Delete a workspace page theme template
     * Remove a page theme template belonging to the authenticated caller&#39;s workspace. Pages already styled from it are unaffected.  Requires the &#x60;page_themes:write&#x60; scope.
     * @param id  (required)
     * @return Object
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table border="1">
       <caption>Response Details</caption>
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Everything worked as expected. </td><td>  * RateLimit -  <br>  * RateLimit-Policy -  <br>  * RateLimit-Remaining -  <br>  </td></tr>
        <tr><td> 204 </td><td> Page theme template deleted successfully </td><td>  * RateLimit -  <br>  * RateLimit-Policy -  <br>  * RateLimit-Remaining -  <br>  </td></tr>
        <tr><td> 429 </td><td> Too many requests. Wait the number of seconds in Retry-After, or until the RateLimit window resets, then retry. </td><td>  * RateLimit -  <br>  * RateLimit-Limit -  <br>  * RateLimit-Policy -  <br>  * RateLimit-Remaining -  <br>  * RateLimit-Reset -  <br>  * Retry-After -  <br>  * X-RateLimit-Limit -  <br>  * X-RateLimit-Remaining -  <br>  * X-RateLimit-Reset -  <br>  </td></tr>
        <tr><td> 0 </td><td> Unexpected error </td><td>  * RateLimit -  <br>  * RateLimit-Policy -  <br>  * RateLimit-Remaining -  <br>  </td></tr>
     </table>
     */
    public Object pageThemeTemplatesServiceDelete(@javax.annotation.Nonnull String id) throws ApiException {
        ApiResponse<Object> localVarResp = pageThemeTemplatesServiceDeleteWithHttpInfo(id);
        return localVarResp.getData();
    }

    /**
     * Delete a workspace page theme template
     * Remove a page theme template belonging to the authenticated caller&#39;s workspace. Pages already styled from it are unaffected.  Requires the &#x60;page_themes:write&#x60; scope.
     * @param id  (required)
     * @return ApiResponse&lt;Object&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table border="1">
       <caption>Response Details</caption>
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Everything worked as expected. </td><td>  * RateLimit -  <br>  * RateLimit-Policy -  <br>  * RateLimit-Remaining -  <br>  </td></tr>
        <tr><td> 204 </td><td> Page theme template deleted successfully </td><td>  * RateLimit -  <br>  * RateLimit-Policy -  <br>  * RateLimit-Remaining -  <br>  </td></tr>
        <tr><td> 429 </td><td> Too many requests. Wait the number of seconds in Retry-After, or until the RateLimit window resets, then retry. </td><td>  * RateLimit -  <br>  * RateLimit-Limit -  <br>  * RateLimit-Policy -  <br>  * RateLimit-Remaining -  <br>  * RateLimit-Reset -  <br>  * Retry-After -  <br>  * X-RateLimit-Limit -  <br>  * X-RateLimit-Remaining -  <br>  * X-RateLimit-Reset -  <br>  </td></tr>
        <tr><td> 0 </td><td> Unexpected error </td><td>  * RateLimit -  <br>  * RateLimit-Policy -  <br>  * RateLimit-Remaining -  <br>  </td></tr>
     </table>
     */
    public ApiResponse<Object> pageThemeTemplatesServiceDeleteWithHttpInfo(@javax.annotation.Nonnull String id) throws ApiException {
        okhttp3.Call localVarCall = pageThemeTemplatesServiceDeleteValidateBeforeCall(id, null);
        Type localVarReturnType = new TypeToken<Object>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Delete a workspace page theme template (asynchronously)
     * Remove a page theme template belonging to the authenticated caller&#39;s workspace. Pages already styled from it are unaffected.  Requires the &#x60;page_themes:write&#x60; scope.
     * @param id  (required)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table border="1">
       <caption>Response Details</caption>
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Everything worked as expected. </td><td>  * RateLimit -  <br>  * RateLimit-Policy -  <br>  * RateLimit-Remaining -  <br>  </td></tr>
        <tr><td> 204 </td><td> Page theme template deleted successfully </td><td>  * RateLimit -  <br>  * RateLimit-Policy -  <br>  * RateLimit-Remaining -  <br>  </td></tr>
        <tr><td> 429 </td><td> Too many requests. Wait the number of seconds in Retry-After, or until the RateLimit window resets, then retry. </td><td>  * RateLimit -  <br>  * RateLimit-Limit -  <br>  * RateLimit-Policy -  <br>  * RateLimit-Remaining -  <br>  * RateLimit-Reset -  <br>  * Retry-After -  <br>  * X-RateLimit-Limit -  <br>  * X-RateLimit-Remaining -  <br>  * X-RateLimit-Reset -  <br>  </td></tr>
        <tr><td> 0 </td><td> Unexpected error </td><td>  * RateLimit -  <br>  * RateLimit-Policy -  <br>  * RateLimit-Remaining -  <br>  </td></tr>
     </table>
     */
    public okhttp3.Call pageThemeTemplatesServiceDeleteAsync(@javax.annotation.Nonnull String id, final ApiCallback<Object> _callback) throws ApiException {

        okhttp3.Call localVarCall = pageThemeTemplatesServiceDeleteValidateBeforeCall(id, _callback);
        Type localVarReturnType = new TypeToken<Object>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for pageThemeTemplatesServiceGet
     * @param id  (required)
     * @param include  (optional)
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
    public okhttp3.Call pageThemeTemplatesServiceGetCall(@javax.annotation.Nonnull String id, @javax.annotation.Nullable List<String> include, final ApiCallback _callback) throws ApiException {
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
        String localVarPath = "/v1/page-theme-templates/{id}"
            .replace("{" + "id" + "}", localVarApiClient.escapeString(id.toString()));

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
        Map<String, String> localVarHeaderParams = new HashMap<String, String>();
        Map<String, String> localVarCookieParams = new HashMap<String, String>();
        Map<String, Object> localVarFormParams = new HashMap<String, Object>();

        if (include != null) {
            localVarCollectionQueryParams.addAll(localVarApiClient.parameterToPairs("multi", "include", include));
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
    private okhttp3.Call pageThemeTemplatesServiceGetValidateBeforeCall(@javax.annotation.Nonnull String id, @javax.annotation.Nullable List<String> include, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new ApiException("Missing the required parameter 'id' when calling pageThemeTemplatesServiceGet(Async)");
        }

        return pageThemeTemplatesServiceGetCall(id, include, _callback);

    }

    /**
     * Get a workspace page theme template
     * Retrieve a page theme template that belongs to the authenticated caller&#39;s workspace.  Requires the &#x60;page_themes:read&#x60; scope.
     * @param id  (required)
     * @param include  (optional)
     * @return PageThemeTemplate
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
    public PageThemeTemplate pageThemeTemplatesServiceGet(@javax.annotation.Nonnull String id, @javax.annotation.Nullable List<String> include) throws ApiException {
        ApiResponse<PageThemeTemplate> localVarResp = pageThemeTemplatesServiceGetWithHttpInfo(id, include);
        return localVarResp.getData();
    }

    /**
     * Get a workspace page theme template
     * Retrieve a page theme template that belongs to the authenticated caller&#39;s workspace.  Requires the &#x60;page_themes:read&#x60; scope.
     * @param id  (required)
     * @param include  (optional)
     * @return ApiResponse&lt;PageThemeTemplate&gt;
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
    public ApiResponse<PageThemeTemplate> pageThemeTemplatesServiceGetWithHttpInfo(@javax.annotation.Nonnull String id, @javax.annotation.Nullable List<String> include) throws ApiException {
        okhttp3.Call localVarCall = pageThemeTemplatesServiceGetValidateBeforeCall(id, include, null);
        Type localVarReturnType = new TypeToken<PageThemeTemplate>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Get a workspace page theme template (asynchronously)
     * Retrieve a page theme template that belongs to the authenticated caller&#39;s workspace.  Requires the &#x60;page_themes:read&#x60; scope.
     * @param id  (required)
     * @param include  (optional)
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
    public okhttp3.Call pageThemeTemplatesServiceGetAsync(@javax.annotation.Nonnull String id, @javax.annotation.Nullable List<String> include, final ApiCallback<PageThemeTemplate> _callback) throws ApiException {

        okhttp3.Call localVarCall = pageThemeTemplatesServiceGetValidateBeforeCall(id, include, _callback);
        Type localVarReturnType = new TypeToken<PageThemeTemplate>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for pageThemeTemplatesServiceGetPublic
     * @param slug  (required)
     * @param include Related objects to include in the response  List of related objects to include. Supported values: &#39;workspace&#39; (optional)
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
    public okhttp3.Call pageThemeTemplatesServiceGetPublicCall(@javax.annotation.Nonnull String slug, @javax.annotation.Nullable List<String> include, final ApiCallback _callback) throws ApiException {
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
        String localVarPath = "/v1/public/page-theme-templates/{slug}"
            .replace("{" + "slug" + "}", localVarApiClient.escapeString(slug.toString()));

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
        Map<String, String> localVarHeaderParams = new HashMap<String, String>();
        Map<String, String> localVarCookieParams = new HashMap<String, String>();
        Map<String, Object> localVarFormParams = new HashMap<String, Object>();

        if (include != null) {
            localVarCollectionQueryParams.addAll(localVarApiClient.parameterToPairs("multi", "include", include));
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

        String[] localVarAuthNames = new String[] {  };
        return localVarApiClient.buildCall(basePath, localVarPath, "GET", localVarQueryParams, localVarCollectionQueryParams, localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAuthNames, _callback);
    }

    @SuppressWarnings("rawtypes")
    private okhttp3.Call pageThemeTemplatesServiceGetPublicValidateBeforeCall(@javax.annotation.Nonnull String slug, @javax.annotation.Nullable List<String> include, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'slug' is set
        if (slug == null) {
            throw new ApiException("Missing the required parameter 'slug' when calling pageThemeTemplatesServiceGetPublic(Async)");
        }

        return pageThemeTemplatesServiceGetPublicCall(slug, include, _callback);

    }

    /**
     * Get a public page theme template
     * Retrieve a single published page theme template that is visible in the public gallery.
     * @param slug  (required)
     * @param include Related objects to include in the response  List of related objects to include. Supported values: &#39;workspace&#39; (optional)
     * @return PublicPageThemeTemplate
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
    public PublicPageThemeTemplate pageThemeTemplatesServiceGetPublic(@javax.annotation.Nonnull String slug, @javax.annotation.Nullable List<String> include) throws ApiException {
        ApiResponse<PublicPageThemeTemplate> localVarResp = pageThemeTemplatesServiceGetPublicWithHttpInfo(slug, include);
        return localVarResp.getData();
    }

    /**
     * Get a public page theme template
     * Retrieve a single published page theme template that is visible in the public gallery.
     * @param slug  (required)
     * @param include Related objects to include in the response  List of related objects to include. Supported values: &#39;workspace&#39; (optional)
     * @return ApiResponse&lt;PublicPageThemeTemplate&gt;
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
    public ApiResponse<PublicPageThemeTemplate> pageThemeTemplatesServiceGetPublicWithHttpInfo(@javax.annotation.Nonnull String slug, @javax.annotation.Nullable List<String> include) throws ApiException {
        okhttp3.Call localVarCall = pageThemeTemplatesServiceGetPublicValidateBeforeCall(slug, include, null);
        Type localVarReturnType = new TypeToken<PublicPageThemeTemplate>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Get a public page theme template (asynchronously)
     * Retrieve a single published page theme template that is visible in the public gallery.
     * @param slug  (required)
     * @param include Related objects to include in the response  List of related objects to include. Supported values: &#39;workspace&#39; (optional)
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
    public okhttp3.Call pageThemeTemplatesServiceGetPublicAsync(@javax.annotation.Nonnull String slug, @javax.annotation.Nullable List<String> include, final ApiCallback<PublicPageThemeTemplate> _callback) throws ApiException {

        okhttp3.Call localVarCall = pageThemeTemplatesServiceGetPublicValidateBeforeCall(slug, include, _callback);
        Type localVarReturnType = new TypeToken<PublicPageThemeTemplate>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for pageThemeTemplatesServiceImport
     * @param importPageThemeTemplateRequest  (required)
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
    public okhttp3.Call pageThemeTemplatesServiceImportCall(@javax.annotation.Nonnull ImportPageThemeTemplateRequest importPageThemeTemplateRequest, @javax.annotation.Nullable String idempotencyKey, final ApiCallback _callback) throws ApiException {
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

        Object localVarPostBody = importPageThemeTemplateRequest;

        // create path and map variables
        String localVarPath = "/v1/page-theme-templates/import";

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
    private okhttp3.Call pageThemeTemplatesServiceImportValidateBeforeCall(@javax.annotation.Nonnull ImportPageThemeTemplateRequest importPageThemeTemplateRequest, @javax.annotation.Nullable String idempotencyKey, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'importPageThemeTemplateRequest' is set
        if (importPageThemeTemplateRequest == null) {
            throw new ApiException("Missing the required parameter 'importPageThemeTemplateRequest' when calling pageThemeTemplatesServiceImport(Async)");
        }

        return pageThemeTemplatesServiceImportCall(importPageThemeTemplateRequest, idempotencyKey, _callback);

    }

    /**
     * Import a public page theme template
     * Import a public page theme template into your workspace by slug. Creates a private copy owned by your workspace.  Requires the &#x60;page_themes:write&#x60; scope.
     * @param importPageThemeTemplateRequest  (required)
     * @param idempotencyKey A unique value per request, such as a UUID, that makes retrying it safe: a retry with the same key and body returns the first response instead of acting twice. (optional)
     * @return ImportPageThemeTemplateResponse
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
    public ImportPageThemeTemplateResponse pageThemeTemplatesServiceImport(@javax.annotation.Nonnull ImportPageThemeTemplateRequest importPageThemeTemplateRequest, @javax.annotation.Nullable String idempotencyKey) throws ApiException {
        ApiResponse<ImportPageThemeTemplateResponse> localVarResp = pageThemeTemplatesServiceImportWithHttpInfo(importPageThemeTemplateRequest, idempotencyKey);
        return localVarResp.getData();
    }

    /**
     * Import a public page theme template
     * Import a public page theme template into your workspace by slug. Creates a private copy owned by your workspace.  Requires the &#x60;page_themes:write&#x60; scope.
     * @param importPageThemeTemplateRequest  (required)
     * @param idempotencyKey A unique value per request, such as a UUID, that makes retrying it safe: a retry with the same key and body returns the first response instead of acting twice. (optional)
     * @return ApiResponse&lt;ImportPageThemeTemplateResponse&gt;
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
    public ApiResponse<ImportPageThemeTemplateResponse> pageThemeTemplatesServiceImportWithHttpInfo(@javax.annotation.Nonnull ImportPageThemeTemplateRequest importPageThemeTemplateRequest, @javax.annotation.Nullable String idempotencyKey) throws ApiException {
        okhttp3.Call localVarCall = pageThemeTemplatesServiceImportValidateBeforeCall(importPageThemeTemplateRequest, idempotencyKey, null);
        Type localVarReturnType = new TypeToken<ImportPageThemeTemplateResponse>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Import a public page theme template (asynchronously)
     * Import a public page theme template into your workspace by slug. Creates a private copy owned by your workspace.  Requires the &#x60;page_themes:write&#x60; scope.
     * @param importPageThemeTemplateRequest  (required)
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
    public okhttp3.Call pageThemeTemplatesServiceImportAsync(@javax.annotation.Nonnull ImportPageThemeTemplateRequest importPageThemeTemplateRequest, @javax.annotation.Nullable String idempotencyKey, final ApiCallback<ImportPageThemeTemplateResponse> _callback) throws ApiException {

        okhttp3.Call localVarCall = pageThemeTemplatesServiceImportValidateBeforeCall(importPageThemeTemplateRequest, idempotencyKey, _callback);
        Type localVarReturnType = new TypeToken<ImportPageThemeTemplateResponse>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for pageThemeTemplatesServiceList
     * @param include  (optional)
     * @param search  (optional)
     * @param pageSize  (optional)
     * @param pageToken  (optional)
     * @param scope Which templates to return. WORKSPACE (default) returns only templates owned by the caller&#39;s workspace. AVAILABLE also returns built-in templates and templates other workspaces have published, ordered by the workspace&#39;s own usage first, then its own templates, then the rest.   - PAGE_THEME_TEMPLATE_SCOPE_UNSPECIFIED: Defaults to PAGE_THEME_TEMPLATE_SCOPE_WORKSPACE.  - PAGE_THEME_TEMPLATE_SCOPE_WORKSPACE: Only templates owned by the caller&#39;s workspace.  - PAGE_THEME_TEMPLATE_SCOPE_AVAILABLE: Every template the workspace may use: its own, built-in ones, and templates published by other workspaces. (optional)
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
    public okhttp3.Call pageThemeTemplatesServiceListCall(@javax.annotation.Nullable List<String> include, @javax.annotation.Nullable String search, @javax.annotation.Nullable Long pageSize, @javax.annotation.Nullable String pageToken, @javax.annotation.Nullable String scope, @javax.annotation.Nullable String cursor, final ApiCallback _callback) throws ApiException {
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
        String localVarPath = "/v1/page-theme-templates";

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
        Map<String, String> localVarHeaderParams = new HashMap<String, String>();
        Map<String, String> localVarCookieParams = new HashMap<String, String>();
        Map<String, Object> localVarFormParams = new HashMap<String, Object>();

        if (include != null) {
            localVarCollectionQueryParams.addAll(localVarApiClient.parameterToPairs("multi", "include", include));
        }

        if (search != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("search", search));
        }

        if (pageSize != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("pageSize", pageSize));
        }

        if (pageToken != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("pageToken", pageToken));
        }

        if (scope != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("scope", scope));
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
    private okhttp3.Call pageThemeTemplatesServiceListValidateBeforeCall(@javax.annotation.Nullable List<String> include, @javax.annotation.Nullable String search, @javax.annotation.Nullable Long pageSize, @javax.annotation.Nullable String pageToken, @javax.annotation.Nullable String scope, @javax.annotation.Nullable String cursor, final ApiCallback _callback) throws ApiException {
        return pageThemeTemplatesServiceListCall(include, search, pageSize, pageToken, scope, cursor, _callback);

    }

    /**
     * List my page theme templates
     * Retrieve page theme templates that belong to the authenticated caller&#39;s workspace.  Requires the &#x60;page_themes:read&#x60; scope.
     * @param include  (optional)
     * @param search  (optional)
     * @param pageSize  (optional)
     * @param pageToken  (optional)
     * @param scope Which templates to return. WORKSPACE (default) returns only templates owned by the caller&#39;s workspace. AVAILABLE also returns built-in templates and templates other workspaces have published, ordered by the workspace&#39;s own usage first, then its own templates, then the rest.   - PAGE_THEME_TEMPLATE_SCOPE_UNSPECIFIED: Defaults to PAGE_THEME_TEMPLATE_SCOPE_WORKSPACE.  - PAGE_THEME_TEMPLATE_SCOPE_WORKSPACE: Only templates owned by the caller&#39;s workspace.  - PAGE_THEME_TEMPLATE_SCOPE_AVAILABLE: Every template the workspace may use: its own, built-in ones, and templates published by other workspaces. (optional)
     * @param cursor Opaque cursor from the previous page&#39;s nextPageToken. Same as pageToken; when both are sent, pageToken wins. (optional)
     * @return ListPageThemeTemplatesResponse
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
    public ListPageThemeTemplatesResponse pageThemeTemplatesServiceList(@javax.annotation.Nullable List<String> include, @javax.annotation.Nullable String search, @javax.annotation.Nullable Long pageSize, @javax.annotation.Nullable String pageToken, @javax.annotation.Nullable String scope, @javax.annotation.Nullable String cursor) throws ApiException {
        ApiResponse<ListPageThemeTemplatesResponse> localVarResp = pageThemeTemplatesServiceListWithHttpInfo(include, search, pageSize, pageToken, scope, cursor);
        return localVarResp.getData();
    }

    /**
     * List my page theme templates
     * Retrieve page theme templates that belong to the authenticated caller&#39;s workspace.  Requires the &#x60;page_themes:read&#x60; scope.
     * @param include  (optional)
     * @param search  (optional)
     * @param pageSize  (optional)
     * @param pageToken  (optional)
     * @param scope Which templates to return. WORKSPACE (default) returns only templates owned by the caller&#39;s workspace. AVAILABLE also returns built-in templates and templates other workspaces have published, ordered by the workspace&#39;s own usage first, then its own templates, then the rest.   - PAGE_THEME_TEMPLATE_SCOPE_UNSPECIFIED: Defaults to PAGE_THEME_TEMPLATE_SCOPE_WORKSPACE.  - PAGE_THEME_TEMPLATE_SCOPE_WORKSPACE: Only templates owned by the caller&#39;s workspace.  - PAGE_THEME_TEMPLATE_SCOPE_AVAILABLE: Every template the workspace may use: its own, built-in ones, and templates published by other workspaces. (optional)
     * @param cursor Opaque cursor from the previous page&#39;s nextPageToken. Same as pageToken; when both are sent, pageToken wins. (optional)
     * @return ApiResponse&lt;ListPageThemeTemplatesResponse&gt;
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
    public ApiResponse<ListPageThemeTemplatesResponse> pageThemeTemplatesServiceListWithHttpInfo(@javax.annotation.Nullable List<String> include, @javax.annotation.Nullable String search, @javax.annotation.Nullable Long pageSize, @javax.annotation.Nullable String pageToken, @javax.annotation.Nullable String scope, @javax.annotation.Nullable String cursor) throws ApiException {
        okhttp3.Call localVarCall = pageThemeTemplatesServiceListValidateBeforeCall(include, search, pageSize, pageToken, scope, cursor, null);
        Type localVarReturnType = new TypeToken<ListPageThemeTemplatesResponse>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * List my page theme templates (asynchronously)
     * Retrieve page theme templates that belong to the authenticated caller&#39;s workspace.  Requires the &#x60;page_themes:read&#x60; scope.
     * @param include  (optional)
     * @param search  (optional)
     * @param pageSize  (optional)
     * @param pageToken  (optional)
     * @param scope Which templates to return. WORKSPACE (default) returns only templates owned by the caller&#39;s workspace. AVAILABLE also returns built-in templates and templates other workspaces have published, ordered by the workspace&#39;s own usage first, then its own templates, then the rest.   - PAGE_THEME_TEMPLATE_SCOPE_UNSPECIFIED: Defaults to PAGE_THEME_TEMPLATE_SCOPE_WORKSPACE.  - PAGE_THEME_TEMPLATE_SCOPE_WORKSPACE: Only templates owned by the caller&#39;s workspace.  - PAGE_THEME_TEMPLATE_SCOPE_AVAILABLE: Every template the workspace may use: its own, built-in ones, and templates published by other workspaces. (optional)
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
    public okhttp3.Call pageThemeTemplatesServiceListAsync(@javax.annotation.Nullable List<String> include, @javax.annotation.Nullable String search, @javax.annotation.Nullable Long pageSize, @javax.annotation.Nullable String pageToken, @javax.annotation.Nullable String scope, @javax.annotation.Nullable String cursor, final ApiCallback<ListPageThemeTemplatesResponse> _callback) throws ApiException {

        okhttp3.Call localVarCall = pageThemeTemplatesServiceListValidateBeforeCall(include, search, pageSize, pageToken, scope, cursor, _callback);
        Type localVarReturnType = new TypeToken<ListPageThemeTemplatesResponse>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for pageThemeTemplatesServiceListPublic
     * @param include Related objects to include in the response  List of related objects to include. Supported values: &#39;workspace&#39; (optional)
     * @param search Search query applied to template name/description (optional)
     * @param pageSize Maximum number of templates to return (defaults to 20) (optional)
     * @param pageToken Cursor token to retrieve the next page of results (optional)
     * @param workspaceId Optional workspace ID to filter templates by workspace  Filter public templates by workspace. Only published templates for that workspace will be returned. (optional)
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
    public okhttp3.Call pageThemeTemplatesServiceListPublicCall(@javax.annotation.Nullable List<String> include, @javax.annotation.Nullable String search, @javax.annotation.Nullable Long pageSize, @javax.annotation.Nullable String pageToken, @javax.annotation.Nullable String workspaceId, @javax.annotation.Nullable String cursor, final ApiCallback _callback) throws ApiException {
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
        String localVarPath = "/v1/public/page-theme-templates";

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
        Map<String, String> localVarHeaderParams = new HashMap<String, String>();
        Map<String, String> localVarCookieParams = new HashMap<String, String>();
        Map<String, Object> localVarFormParams = new HashMap<String, Object>();

        if (include != null) {
            localVarCollectionQueryParams.addAll(localVarApiClient.parameterToPairs("multi", "include", include));
        }

        if (search != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("search", search));
        }

        if (pageSize != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("pageSize", pageSize));
        }

        if (pageToken != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("pageToken", pageToken));
        }

        if (workspaceId != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("workspaceId", workspaceId));
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

        String[] localVarAuthNames = new String[] {  };
        return localVarApiClient.buildCall(basePath, localVarPath, "GET", localVarQueryParams, localVarCollectionQueryParams, localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAuthNames, _callback);
    }

    @SuppressWarnings("rawtypes")
    private okhttp3.Call pageThemeTemplatesServiceListPublicValidateBeforeCall(@javax.annotation.Nullable List<String> include, @javax.annotation.Nullable String search, @javax.annotation.Nullable Long pageSize, @javax.annotation.Nullable String pageToken, @javax.annotation.Nullable String workspaceId, @javax.annotation.Nullable String cursor, final ApiCallback _callback) throws ApiException {
        return pageThemeTemplatesServiceListPublicCall(include, search, pageSize, pageToken, workspaceId, cursor, _callback);

    }

    /**
     * List page theme templates
     * Retrieve available public page theme templates, optionally filtered by search terms.
     * @param include Related objects to include in the response  List of related objects to include. Supported values: &#39;workspace&#39; (optional)
     * @param search Search query applied to template name/description (optional)
     * @param pageSize Maximum number of templates to return (defaults to 20) (optional)
     * @param pageToken Cursor token to retrieve the next page of results (optional)
     * @param workspaceId Optional workspace ID to filter templates by workspace  Filter public templates by workspace. Only published templates for that workspace will be returned. (optional)
     * @param cursor Opaque cursor from the previous page&#39;s nextPageToken. Same as pageToken; when both are sent, pageToken wins. (optional)
     * @return PublicListPageThemeTemplatesResponse
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
    public PublicListPageThemeTemplatesResponse pageThemeTemplatesServiceListPublic(@javax.annotation.Nullable List<String> include, @javax.annotation.Nullable String search, @javax.annotation.Nullable Long pageSize, @javax.annotation.Nullable String pageToken, @javax.annotation.Nullable String workspaceId, @javax.annotation.Nullable String cursor) throws ApiException {
        ApiResponse<PublicListPageThemeTemplatesResponse> localVarResp = pageThemeTemplatesServiceListPublicWithHttpInfo(include, search, pageSize, pageToken, workspaceId, cursor);
        return localVarResp.getData();
    }

    /**
     * List page theme templates
     * Retrieve available public page theme templates, optionally filtered by search terms.
     * @param include Related objects to include in the response  List of related objects to include. Supported values: &#39;workspace&#39; (optional)
     * @param search Search query applied to template name/description (optional)
     * @param pageSize Maximum number of templates to return (defaults to 20) (optional)
     * @param pageToken Cursor token to retrieve the next page of results (optional)
     * @param workspaceId Optional workspace ID to filter templates by workspace  Filter public templates by workspace. Only published templates for that workspace will be returned. (optional)
     * @param cursor Opaque cursor from the previous page&#39;s nextPageToken. Same as pageToken; when both are sent, pageToken wins. (optional)
     * @return ApiResponse&lt;PublicListPageThemeTemplatesResponse&gt;
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
    public ApiResponse<PublicListPageThemeTemplatesResponse> pageThemeTemplatesServiceListPublicWithHttpInfo(@javax.annotation.Nullable List<String> include, @javax.annotation.Nullable String search, @javax.annotation.Nullable Long pageSize, @javax.annotation.Nullable String pageToken, @javax.annotation.Nullable String workspaceId, @javax.annotation.Nullable String cursor) throws ApiException {
        okhttp3.Call localVarCall = pageThemeTemplatesServiceListPublicValidateBeforeCall(include, search, pageSize, pageToken, workspaceId, cursor, null);
        Type localVarReturnType = new TypeToken<PublicListPageThemeTemplatesResponse>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * List page theme templates (asynchronously)
     * Retrieve available public page theme templates, optionally filtered by search terms.
     * @param include Related objects to include in the response  List of related objects to include. Supported values: &#39;workspace&#39; (optional)
     * @param search Search query applied to template name/description (optional)
     * @param pageSize Maximum number of templates to return (defaults to 20) (optional)
     * @param pageToken Cursor token to retrieve the next page of results (optional)
     * @param workspaceId Optional workspace ID to filter templates by workspace  Filter public templates by workspace. Only published templates for that workspace will be returned. (optional)
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
    public okhttp3.Call pageThemeTemplatesServiceListPublicAsync(@javax.annotation.Nullable List<String> include, @javax.annotation.Nullable String search, @javax.annotation.Nullable Long pageSize, @javax.annotation.Nullable String pageToken, @javax.annotation.Nullable String workspaceId, @javax.annotation.Nullable String cursor, final ApiCallback<PublicListPageThemeTemplatesResponse> _callback) throws ApiException {

        okhttp3.Call localVarCall = pageThemeTemplatesServiceListPublicValidateBeforeCall(include, search, pageSize, pageToken, workspaceId, cursor, _callback);
        Type localVarReturnType = new TypeToken<PublicListPageThemeTemplatesResponse>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for pageThemeTemplatesServicePublish
     * @param id The ID of the page theme template to publish (UUID) (required)
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
    public okhttp3.Call pageThemeTemplatesServicePublishCall(@javax.annotation.Nonnull String id, @javax.annotation.Nonnull Object body, @javax.annotation.Nullable String idempotencyKey, final ApiCallback _callback) throws ApiException {
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
        String localVarPath = "/v1/page-theme-templates/{id}/publish"
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
    private okhttp3.Call pageThemeTemplatesServicePublishValidateBeforeCall(@javax.annotation.Nonnull String id, @javax.annotation.Nonnull Object body, @javax.annotation.Nullable String idempotencyKey, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new ApiException("Missing the required parameter 'id' when calling pageThemeTemplatesServicePublish(Async)");
        }

        // verify the required parameter 'body' is set
        if (body == null) {
            throw new ApiException("Missing the required parameter 'body' when calling pageThemeTemplatesServicePublish(Async)");
        }

        return pageThemeTemplatesServicePublishCall(id, body, idempotencyKey, _callback);

    }

    /**
     * Publish a workspace page theme template
     * Publish a page theme template to make it available in the public gallery. Requires workspace to be public and template to have slug, description, and name.  Requires the &#x60;page_themes:write&#x60; scope.
     * @param id The ID of the page theme template to publish (UUID) (required)
     * @param body  (required)
     * @param idempotencyKey A unique value per request, such as a UUID, that makes retrying it safe: a retry with the same key and body returns the first response instead of acting twice. (optional)
     * @return PublishPageThemeTemplateResponse
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
    public PublishPageThemeTemplateResponse pageThemeTemplatesServicePublish(@javax.annotation.Nonnull String id, @javax.annotation.Nonnull Object body, @javax.annotation.Nullable String idempotencyKey) throws ApiException {
        ApiResponse<PublishPageThemeTemplateResponse> localVarResp = pageThemeTemplatesServicePublishWithHttpInfo(id, body, idempotencyKey);
        return localVarResp.getData();
    }

    /**
     * Publish a workspace page theme template
     * Publish a page theme template to make it available in the public gallery. Requires workspace to be public and template to have slug, description, and name.  Requires the &#x60;page_themes:write&#x60; scope.
     * @param id The ID of the page theme template to publish (UUID) (required)
     * @param body  (required)
     * @param idempotencyKey A unique value per request, such as a UUID, that makes retrying it safe: a retry with the same key and body returns the first response instead of acting twice. (optional)
     * @return ApiResponse&lt;PublishPageThemeTemplateResponse&gt;
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
    public ApiResponse<PublishPageThemeTemplateResponse> pageThemeTemplatesServicePublishWithHttpInfo(@javax.annotation.Nonnull String id, @javax.annotation.Nonnull Object body, @javax.annotation.Nullable String idempotencyKey) throws ApiException {
        okhttp3.Call localVarCall = pageThemeTemplatesServicePublishValidateBeforeCall(id, body, idempotencyKey, null);
        Type localVarReturnType = new TypeToken<PublishPageThemeTemplateResponse>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Publish a workspace page theme template (asynchronously)
     * Publish a page theme template to make it available in the public gallery. Requires workspace to be public and template to have slug, description, and name.  Requires the &#x60;page_themes:write&#x60; scope.
     * @param id The ID of the page theme template to publish (UUID) (required)
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
    public okhttp3.Call pageThemeTemplatesServicePublishAsync(@javax.annotation.Nonnull String id, @javax.annotation.Nonnull Object body, @javax.annotation.Nullable String idempotencyKey, final ApiCallback<PublishPageThemeTemplateResponse> _callback) throws ApiException {

        okhttp3.Call localVarCall = pageThemeTemplatesServicePublishValidateBeforeCall(id, body, idempotencyKey, _callback);
        Type localVarReturnType = new TypeToken<PublishPageThemeTemplateResponse>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for pageThemeTemplatesServiceUpdate
     * @param id The ID of the page theme template to update (UUID) (required)
     * @param pageThemeTemplatesServiceUpdateBody  (required)
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
    public okhttp3.Call pageThemeTemplatesServiceUpdateCall(@javax.annotation.Nonnull String id, @javax.annotation.Nonnull PageThemeTemplatesServiceUpdateBody pageThemeTemplatesServiceUpdateBody, @javax.annotation.Nullable String idempotencyKey, final ApiCallback _callback) throws ApiException {
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

        Object localVarPostBody = pageThemeTemplatesServiceUpdateBody;

        // create path and map variables
        String localVarPath = "/v1/page-theme-templates/{id}"
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
        return localVarApiClient.buildCall(basePath, localVarPath, "PATCH", localVarQueryParams, localVarCollectionQueryParams, localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAuthNames, _callback);
    }

    @SuppressWarnings("rawtypes")
    private okhttp3.Call pageThemeTemplatesServiceUpdateValidateBeforeCall(@javax.annotation.Nonnull String id, @javax.annotation.Nonnull PageThemeTemplatesServiceUpdateBody pageThemeTemplatesServiceUpdateBody, @javax.annotation.Nullable String idempotencyKey, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new ApiException("Missing the required parameter 'id' when calling pageThemeTemplatesServiceUpdate(Async)");
        }

        // verify the required parameter 'pageThemeTemplatesServiceUpdateBody' is set
        if (pageThemeTemplatesServiceUpdateBody == null) {
            throw new ApiException("Missing the required parameter 'pageThemeTemplatesServiceUpdateBody' when calling pageThemeTemplatesServiceUpdate(Async)");
        }

        return pageThemeTemplatesServiceUpdateCall(id, pageThemeTemplatesServiceUpdateBody, idempotencyKey, _callback);

    }

    /**
     * Update a workspace page theme template
     * Modify the metadata of a page theme template owned by the authenticated workspace.  Requires the &#x60;page_themes:write&#x60; scope.
     * @param id The ID of the page theme template to update (UUID) (required)
     * @param pageThemeTemplatesServiceUpdateBody  (required)
     * @param idempotencyKey A unique value per request, such as a UUID, that makes retrying it safe: a retry with the same key and body returns the first response instead of acting twice. (optional)
     * @return UpdatePageThemeTemplateResponse
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
    public UpdatePageThemeTemplateResponse pageThemeTemplatesServiceUpdate(@javax.annotation.Nonnull String id, @javax.annotation.Nonnull PageThemeTemplatesServiceUpdateBody pageThemeTemplatesServiceUpdateBody, @javax.annotation.Nullable String idempotencyKey) throws ApiException {
        ApiResponse<UpdatePageThemeTemplateResponse> localVarResp = pageThemeTemplatesServiceUpdateWithHttpInfo(id, pageThemeTemplatesServiceUpdateBody, idempotencyKey);
        return localVarResp.getData();
    }

    /**
     * Update a workspace page theme template
     * Modify the metadata of a page theme template owned by the authenticated workspace.  Requires the &#x60;page_themes:write&#x60; scope.
     * @param id The ID of the page theme template to update (UUID) (required)
     * @param pageThemeTemplatesServiceUpdateBody  (required)
     * @param idempotencyKey A unique value per request, such as a UUID, that makes retrying it safe: a retry with the same key and body returns the first response instead of acting twice. (optional)
     * @return ApiResponse&lt;UpdatePageThemeTemplateResponse&gt;
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
    public ApiResponse<UpdatePageThemeTemplateResponse> pageThemeTemplatesServiceUpdateWithHttpInfo(@javax.annotation.Nonnull String id, @javax.annotation.Nonnull PageThemeTemplatesServiceUpdateBody pageThemeTemplatesServiceUpdateBody, @javax.annotation.Nullable String idempotencyKey) throws ApiException {
        okhttp3.Call localVarCall = pageThemeTemplatesServiceUpdateValidateBeforeCall(id, pageThemeTemplatesServiceUpdateBody, idempotencyKey, null);
        Type localVarReturnType = new TypeToken<UpdatePageThemeTemplateResponse>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Update a workspace page theme template (asynchronously)
     * Modify the metadata of a page theme template owned by the authenticated workspace.  Requires the &#x60;page_themes:write&#x60; scope.
     * @param id The ID of the page theme template to update (UUID) (required)
     * @param pageThemeTemplatesServiceUpdateBody  (required)
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
    public okhttp3.Call pageThemeTemplatesServiceUpdateAsync(@javax.annotation.Nonnull String id, @javax.annotation.Nonnull PageThemeTemplatesServiceUpdateBody pageThemeTemplatesServiceUpdateBody, @javax.annotation.Nullable String idempotencyKey, final ApiCallback<UpdatePageThemeTemplateResponse> _callback) throws ApiException {

        okhttp3.Call localVarCall = pageThemeTemplatesServiceUpdateValidateBeforeCall(id, pageThemeTemplatesServiceUpdateBody, idempotencyKey, _callback);
        Type localVarReturnType = new TypeToken<UpdatePageThemeTemplateResponse>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for pageThemeTemplatesServiceUpdateStyling
     * @param id The ID of the page theme template whose styling to update (UUID) (required)
     * @param pageThemeTemplatesServiceUpdateStylingBody  (required)
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
    public okhttp3.Call pageThemeTemplatesServiceUpdateStylingCall(@javax.annotation.Nonnull String id, @javax.annotation.Nonnull PageThemeTemplatesServiceUpdateStylingBody pageThemeTemplatesServiceUpdateStylingBody, @javax.annotation.Nullable String idempotencyKey, final ApiCallback _callback) throws ApiException {
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

        Object localVarPostBody = pageThemeTemplatesServiceUpdateStylingBody;

        // create path and map variables
        String localVarPath = "/v1/page-theme-templates/{id}/styling"
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
        return localVarApiClient.buildCall(basePath, localVarPath, "PATCH", localVarQueryParams, localVarCollectionQueryParams, localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAuthNames, _callback);
    }

    @SuppressWarnings("rawtypes")
    private okhttp3.Call pageThemeTemplatesServiceUpdateStylingValidateBeforeCall(@javax.annotation.Nonnull String id, @javax.annotation.Nonnull PageThemeTemplatesServiceUpdateStylingBody pageThemeTemplatesServiceUpdateStylingBody, @javax.annotation.Nullable String idempotencyKey, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new ApiException("Missing the required parameter 'id' when calling pageThemeTemplatesServiceUpdateStyling(Async)");
        }

        // verify the required parameter 'pageThemeTemplatesServiceUpdateStylingBody' is set
        if (pageThemeTemplatesServiceUpdateStylingBody == null) {
            throw new ApiException("Missing the required parameter 'pageThemeTemplatesServiceUpdateStylingBody' when calling pageThemeTemplatesServiceUpdateStyling(Async)");
        }

        return pageThemeTemplatesServiceUpdateStylingCall(id, pageThemeTemplatesServiceUpdateStylingBody, idempotencyKey, _callback);

    }

    /**
     * Update a page theme template&#39;s styling
     * Edit the colours, typography, container and media of a page theme template. Queues a thumbnail regeneration.  Requires the &#x60;page_themes:write&#x60; scope.
     * @param id The ID of the page theme template whose styling to update (UUID) (required)
     * @param pageThemeTemplatesServiceUpdateStylingBody  (required)
     * @param idempotencyKey A unique value per request, such as a UUID, that makes retrying it safe: a retry with the same key and body returns the first response instead of acting twice. (optional)
     * @return UpdatePageThemeTemplateStylingResponse
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
    public UpdatePageThemeTemplateStylingResponse pageThemeTemplatesServiceUpdateStyling(@javax.annotation.Nonnull String id, @javax.annotation.Nonnull PageThemeTemplatesServiceUpdateStylingBody pageThemeTemplatesServiceUpdateStylingBody, @javax.annotation.Nullable String idempotencyKey) throws ApiException {
        ApiResponse<UpdatePageThemeTemplateStylingResponse> localVarResp = pageThemeTemplatesServiceUpdateStylingWithHttpInfo(id, pageThemeTemplatesServiceUpdateStylingBody, idempotencyKey);
        return localVarResp.getData();
    }

    /**
     * Update a page theme template&#39;s styling
     * Edit the colours, typography, container and media of a page theme template. Queues a thumbnail regeneration.  Requires the &#x60;page_themes:write&#x60; scope.
     * @param id The ID of the page theme template whose styling to update (UUID) (required)
     * @param pageThemeTemplatesServiceUpdateStylingBody  (required)
     * @param idempotencyKey A unique value per request, such as a UUID, that makes retrying it safe: a retry with the same key and body returns the first response instead of acting twice. (optional)
     * @return ApiResponse&lt;UpdatePageThemeTemplateStylingResponse&gt;
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
    public ApiResponse<UpdatePageThemeTemplateStylingResponse> pageThemeTemplatesServiceUpdateStylingWithHttpInfo(@javax.annotation.Nonnull String id, @javax.annotation.Nonnull PageThemeTemplatesServiceUpdateStylingBody pageThemeTemplatesServiceUpdateStylingBody, @javax.annotation.Nullable String idempotencyKey) throws ApiException {
        okhttp3.Call localVarCall = pageThemeTemplatesServiceUpdateStylingValidateBeforeCall(id, pageThemeTemplatesServiceUpdateStylingBody, idempotencyKey, null);
        Type localVarReturnType = new TypeToken<UpdatePageThemeTemplateStylingResponse>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Update a page theme template&#39;s styling (asynchronously)
     * Edit the colours, typography, container and media of a page theme template. Queues a thumbnail regeneration.  Requires the &#x60;page_themes:write&#x60; scope.
     * @param id The ID of the page theme template whose styling to update (UUID) (required)
     * @param pageThemeTemplatesServiceUpdateStylingBody  (required)
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
    public okhttp3.Call pageThemeTemplatesServiceUpdateStylingAsync(@javax.annotation.Nonnull String id, @javax.annotation.Nonnull PageThemeTemplatesServiceUpdateStylingBody pageThemeTemplatesServiceUpdateStylingBody, @javax.annotation.Nullable String idempotencyKey, final ApiCallback<UpdatePageThemeTemplateStylingResponse> _callback) throws ApiException {

        okhttp3.Call localVarCall = pageThemeTemplatesServiceUpdateStylingValidateBeforeCall(id, pageThemeTemplatesServiceUpdateStylingBody, idempotencyKey, _callback);
        Type localVarReturnType = new TypeToken<UpdatePageThemeTemplateStylingResponse>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
}
