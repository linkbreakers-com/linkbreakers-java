/*
 * Linkbreakers API
 * The Linkbreakers REST API: links, QR codes, workflows, visitors and analytics, as JSON over HTTPS. Send a workspace API token as `Authorization: Bearer <token>`.  ## Entry points  `GET https://api.linkbreakers.com/` and `GET https://api.linkbreakers.com/v1` answer without credentials with the API's name, version and links. This document is served at `https://api.linkbreakers.com/openapi.json`.  ## Rate limits  Every response describes where the caller stands in the IETF `RateLimit-Policy` and `RateLimit` header fields (draft-ietf-httpapi-ratelimit-headers), and in `RateLimit-Limit`, `RateLimit-Remaining`, `RateLimit-Reset` and `X-RateLimit-Limit`, `X-RateLimit-Remaining`, `X-RateLimit-Reset` (a Unix timestamp) for clients that read those. A request past the limit gets `429 Too Many Requests` with `Retry-After` in seconds.  - Workspace API token: 720 requests per 60 seconds, per token. - Dashboard session or OAuth token: 600 requests per 60 seconds, per token. - No credentials: 300 requests per 60 seconds, per client IP.  Each plan also includes a number of API calls per billing cycle. Past it, calls answer `429` with the code `MAX_PUBLIC_API_QUERIED_REACHED` until the cycle renews or the plan changes.  ## Pagination  Every list pages with opaque cursors (AIP-158). Pass `pageSize`, then send the `nextPageToken` of each response back as `pageToken` (or its alias `cursor`) to get the next page. An empty or absent `nextPageToken` means the list is complete. Each list operation describes this in `x-pagination`.  ## Batches and background jobs  `POST /v1/links/bulk` creates up to 250 links in one transactional request; `POST /v1/links/batch` takes the same links as a bare JSON array. `POST /v1/links/bulk/jobs` accepts the same batch and answers `202 Accepted` at once with a job and a `Location` header; poll `GET /v1/jobs/{id}` until `status` is `JOB_STATUS_SUCCEEDED` (the created link ids are in `linksBulkCreate.linkIds`) or `JOB_STATUS_FAILED` (`error` says why, and nothing was created). Jobs can be read for 24 hours after they finish.  ## Paused workspaces  A free workspace whose members have not verified any email address is paused 7 days after sign-up. Its calls then answer `403` with the code `WORKSPACE_UNVERIFIED` and change nothing; QR codes and links keep working for visitors. To lift the pause, ask the workspace owner to verify their email: `POST /v1/members/email-verification` sends the verification email again, and works while the workspace is paused. `unverifiedPauseAt` on the workspace says when the pause starts.  ## Versioning and deprecation  The version is in the path: every operation lives under `/v1`. Within a version, changes are additive only: new operations, new optional request fields, new response fields and new enum values. Clients should ignore fields and enum values they do not know. Anything that would break an existing client ships under a new path version instead.  An operation is retired in two steps. First it is deprecated: it keeps working, is flagged `deprecated: true` in this document, and every response carries a `Deprecation` header field (RFC 9745) with the time it was deprecated and a `Link` with `rel=\"deprecation\"` to the policy. Then, at least 180 days later, it may be removed; that date is announced in advance in a `Sunset` header field (RFC 8594) and as `x-sunset` on the operation. The full policy: https://linkbreakers.com/help/article/api-versioning-and-deprecation-policy  A renamed operation keeps answering its former path while clients still call it, until that path's sunset at the latest: a former path no client calls for 14 days is removed. Responses on a former path carry the same `Deprecation`, `Sunset` and `Link` header fields; this document lists only the current path, and the policy lists the former paths that still answer.  ## Idempotent retries  Every POST, PUT and PATCH accepts an optional `Idempotency-Key` header (draft-ietf-httpapi-idempotency-key-header). Send a new unique value, such as a UUID, with each logical request and reuse it only to retry that request. A retry with the same key and the same request returns the first response, marked `Idempotent-Replayed: true`, instead of running again. Keys are scoped to the token and kept for 24 hours. The same key with a different request is refused with `422`, and a retry while the first request is still running gets `409` with `Retry-After`. Server errors (5xx) and refusals such as 401, 403 and 429 are not stored, so retrying them runs the request again.  ## Identifying your client  Send a `Linkbreakers-Client` request header saying which client made the call: `<client>/<version> (<details>)`, for example `my-integration/1.2.0 (python 3.12; linux)`. The official SDKs, the CLI and the MCP bridge set it for you, as `sdk-python/2.3.1 (python 3.12.4; linux)` or `cli/1.4.0 (darwin arm64)`. It is optional and changes nothing about how a request is handled; it tells us which clients and versions are in use, so we know what can be retired and what to fix first. Keep the details short and free of personal data. Browsers may send it too: it is allowed by CORS.
 *
 * The version of the OpenAPI document: 1.200.2
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
import com.linkbreakers.model.EventsMetric;
import com.linkbreakers.model.GetAttributeDistributionRequest;
import com.linkbreakers.model.GetAttributeDistributionResponse;
import com.linkbreakers.model.GetAttributeMetricRequest;
import com.linkbreakers.model.GetAttributeMetricResponse;
import com.linkbreakers.model.GetAttributeValuesRequest;
import com.linkbreakers.model.GetAttributeValuesResponse;
import com.linkbreakers.model.GetBreakdownRequest;
import com.linkbreakers.model.GetBreakdownResponse;
import com.linkbreakers.model.GetDeviceDistributionRequest;
import com.linkbreakers.model.GetDeviceDistributionResponse;
import com.linkbreakers.model.GetGeoBreakdownRequest;
import com.linkbreakers.model.GetGeoBreakdownResponse;
import com.linkbreakers.model.GetGroupedTimeSeriesRequest;
import com.linkbreakers.model.GetGroupedTimeSeriesResponse;
import com.linkbreakers.model.GetHourOfDayDistributionRequest;
import com.linkbreakers.model.GetHourOfDayDistributionResponse;
import com.linkbreakers.model.GetSharedLocationsResponse;
import com.linkbreakers.model.GetStepFunnelRequest;
import com.linkbreakers.model.GetStepFunnelResponse;
import com.linkbreakers.model.GetTopLinksRequest;
import com.linkbreakers.model.GetTopLinksResponse;
import com.linkbreakers.model.GetTrafficSplitResultsRequest;
import com.linkbreakers.model.GetTrafficSplitResultsResponse;
import com.linkbreakers.model.GetVisitorTrendsRequest;
import com.linkbreakers.model.GetVisitorTrendsResponse;
import com.linkbreakers.model.ListVisitorCountriesResponse;
import java.time.OffsetDateTime;
import com.linkbreakers.model.WorkspaceMetric;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AnalyticsApi {
    private ApiClient localVarApiClient;
    private int localHostIndex;
    private String localCustomBaseUrl;

    public AnalyticsApi() {
        this(Configuration.getDefaultApiClient());
    }

    public AnalyticsApi(ApiClient apiClient) {
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
     * Build call for analyticsServiceGetAttributeDistribution
     * @param getAttributeDistributionRequest  (required)
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
    public okhttp3.Call analyticsServiceGetAttributeDistributionCall(@javax.annotation.Nonnull GetAttributeDistributionRequest getAttributeDistributionRequest, @javax.annotation.Nullable String idempotencyKey, final ApiCallback _callback) throws ApiException {
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

        Object localVarPostBody = getAttributeDistributionRequest;

        // create path and map variables
        String localVarPath = "/v1/analytics/attributes/distribution";

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
    private okhttp3.Call analyticsServiceGetAttributeDistributionValidateBeforeCall(@javax.annotation.Nonnull GetAttributeDistributionRequest getAttributeDistributionRequest, @javax.annotation.Nullable String idempotencyKey, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'getAttributeDistributionRequest' is set
        if (getAttributeDistributionRequest == null) {
            throw new ApiException("Missing the required parameter 'getAttributeDistributionRequest' when calling analyticsServiceGetAttributeDistribution(Async)");
        }

        return analyticsServiceGetAttributeDistributionCall(getAttributeDistributionRequest, idempotencyKey, _callback);

    }

    /**
     * Get visitor attribute distribution
     * Returns how visitors are spread across the values of one attribute, for example how many gave each rating.  Requires the &#x60;analytics:read&#x60; scope.
     * @param getAttributeDistributionRequest  (required)
     * @param idempotencyKey A unique value per request, such as a UUID, that makes retrying it safe: a retry with the same key and body returns the first response instead of acting twice. (optional)
     * @return GetAttributeDistributionResponse
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
    public GetAttributeDistributionResponse analyticsServiceGetAttributeDistribution(@javax.annotation.Nonnull GetAttributeDistributionRequest getAttributeDistributionRequest, @javax.annotation.Nullable String idempotencyKey) throws ApiException {
        ApiResponse<GetAttributeDistributionResponse> localVarResp = analyticsServiceGetAttributeDistributionWithHttpInfo(getAttributeDistributionRequest, idempotencyKey);
        return localVarResp.getData();
    }

    /**
     * Get visitor attribute distribution
     * Returns how visitors are spread across the values of one attribute, for example how many gave each rating.  Requires the &#x60;analytics:read&#x60; scope.
     * @param getAttributeDistributionRequest  (required)
     * @param idempotencyKey A unique value per request, such as a UUID, that makes retrying it safe: a retry with the same key and body returns the first response instead of acting twice. (optional)
     * @return ApiResponse&lt;GetAttributeDistributionResponse&gt;
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
    public ApiResponse<GetAttributeDistributionResponse> analyticsServiceGetAttributeDistributionWithHttpInfo(@javax.annotation.Nonnull GetAttributeDistributionRequest getAttributeDistributionRequest, @javax.annotation.Nullable String idempotencyKey) throws ApiException {
        okhttp3.Call localVarCall = analyticsServiceGetAttributeDistributionValidateBeforeCall(getAttributeDistributionRequest, idempotencyKey, null);
        Type localVarReturnType = new TypeToken<GetAttributeDistributionResponse>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Get visitor attribute distribution (asynchronously)
     * Returns how visitors are spread across the values of one attribute, for example how many gave each rating.  Requires the &#x60;analytics:read&#x60; scope.
     * @param getAttributeDistributionRequest  (required)
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
    public okhttp3.Call analyticsServiceGetAttributeDistributionAsync(@javax.annotation.Nonnull GetAttributeDistributionRequest getAttributeDistributionRequest, @javax.annotation.Nullable String idempotencyKey, final ApiCallback<GetAttributeDistributionResponse> _callback) throws ApiException {

        okhttp3.Call localVarCall = analyticsServiceGetAttributeDistributionValidateBeforeCall(getAttributeDistributionRequest, idempotencyKey, _callback);
        Type localVarReturnType = new TypeToken<GetAttributeDistributionResponse>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for analyticsServiceGetAttributeMetric
     * @param getAttributeMetricRequest  (required)
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
    public okhttp3.Call analyticsServiceGetAttributeMetricCall(@javax.annotation.Nonnull GetAttributeMetricRequest getAttributeMetricRequest, @javax.annotation.Nullable String idempotencyKey, final ApiCallback _callback) throws ApiException {
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

        Object localVarPostBody = getAttributeMetricRequest;

        // create path and map variables
        String localVarPath = "/v1/analytics/attributes/metric";

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
    private okhttp3.Call analyticsServiceGetAttributeMetricValidateBeforeCall(@javax.annotation.Nonnull GetAttributeMetricRequest getAttributeMetricRequest, @javax.annotation.Nullable String idempotencyKey, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'getAttributeMetricRequest' is set
        if (getAttributeMetricRequest == null) {
            throw new ApiException("Missing the required parameter 'getAttributeMetricRequest' when calling analyticsServiceGetAttributeMetric(Async)");
        }

        return analyticsServiceGetAttributeMetricCall(getAttributeMetricRequest, idempotencyKey, _callback);

    }

    /**
     * Get visitor attribute metric
     * Returns count, average, minimum and maximum for a visitor attribute, plus how much of the audience carries it.  Requires the &#x60;analytics:read&#x60; scope.
     * @param getAttributeMetricRequest  (required)
     * @param idempotencyKey A unique value per request, such as a UUID, that makes retrying it safe: a retry with the same key and body returns the first response instead of acting twice. (optional)
     * @return GetAttributeMetricResponse
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
    public GetAttributeMetricResponse analyticsServiceGetAttributeMetric(@javax.annotation.Nonnull GetAttributeMetricRequest getAttributeMetricRequest, @javax.annotation.Nullable String idempotencyKey) throws ApiException {
        ApiResponse<GetAttributeMetricResponse> localVarResp = analyticsServiceGetAttributeMetricWithHttpInfo(getAttributeMetricRequest, idempotencyKey);
        return localVarResp.getData();
    }

    /**
     * Get visitor attribute metric
     * Returns count, average, minimum and maximum for a visitor attribute, plus how much of the audience carries it.  Requires the &#x60;analytics:read&#x60; scope.
     * @param getAttributeMetricRequest  (required)
     * @param idempotencyKey A unique value per request, such as a UUID, that makes retrying it safe: a retry with the same key and body returns the first response instead of acting twice. (optional)
     * @return ApiResponse&lt;GetAttributeMetricResponse&gt;
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
    public ApiResponse<GetAttributeMetricResponse> analyticsServiceGetAttributeMetricWithHttpInfo(@javax.annotation.Nonnull GetAttributeMetricRequest getAttributeMetricRequest, @javax.annotation.Nullable String idempotencyKey) throws ApiException {
        okhttp3.Call localVarCall = analyticsServiceGetAttributeMetricValidateBeforeCall(getAttributeMetricRequest, idempotencyKey, null);
        Type localVarReturnType = new TypeToken<GetAttributeMetricResponse>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Get visitor attribute metric (asynchronously)
     * Returns count, average, minimum and maximum for a visitor attribute, plus how much of the audience carries it.  Requires the &#x60;analytics:read&#x60; scope.
     * @param getAttributeMetricRequest  (required)
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
    public okhttp3.Call analyticsServiceGetAttributeMetricAsync(@javax.annotation.Nonnull GetAttributeMetricRequest getAttributeMetricRequest, @javax.annotation.Nullable String idempotencyKey, final ApiCallback<GetAttributeMetricResponse> _callback) throws ApiException {

        okhttp3.Call localVarCall = analyticsServiceGetAttributeMetricValidateBeforeCall(getAttributeMetricRequest, idempotencyKey, _callback);
        Type localVarReturnType = new TypeToken<GetAttributeMetricResponse>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for analyticsServiceGetAttributeValues
     * @param getAttributeValuesRequest  (required)
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
    public okhttp3.Call analyticsServiceGetAttributeValuesCall(@javax.annotation.Nonnull GetAttributeValuesRequest getAttributeValuesRequest, @javax.annotation.Nullable String idempotencyKey, final ApiCallback _callback) throws ApiException {
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

        Object localVarPostBody = getAttributeValuesRequest;

        // create path and map variables
        String localVarPath = "/v1/analytics/attributes/values";

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
    private okhttp3.Call analyticsServiceGetAttributeValuesValidateBeforeCall(@javax.annotation.Nonnull GetAttributeValuesRequest getAttributeValuesRequest, @javax.annotation.Nullable String idempotencyKey, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'getAttributeValuesRequest' is set
        if (getAttributeValuesRequest == null) {
            throw new ApiException("Missing the required parameter 'getAttributeValuesRequest' when calling analyticsServiceGetAttributeValues(Async)");
        }

        return analyticsServiceGetAttributeValuesCall(getAttributeValuesRequest, idempotencyKey, _callback);

    }

    /**
     * List visitor attribute values
     * Returns the individual recorded values of one attribute, most recent first. Intended for free text such as comments.  Requires the &#x60;analytics:read&#x60; scope.
     * @param getAttributeValuesRequest  (required)
     * @param idempotencyKey A unique value per request, such as a UUID, that makes retrying it safe: a retry with the same key and body returns the first response instead of acting twice. (optional)
     * @return GetAttributeValuesResponse
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
    public GetAttributeValuesResponse analyticsServiceGetAttributeValues(@javax.annotation.Nonnull GetAttributeValuesRequest getAttributeValuesRequest, @javax.annotation.Nullable String idempotencyKey) throws ApiException {
        ApiResponse<GetAttributeValuesResponse> localVarResp = analyticsServiceGetAttributeValuesWithHttpInfo(getAttributeValuesRequest, idempotencyKey);
        return localVarResp.getData();
    }

    /**
     * List visitor attribute values
     * Returns the individual recorded values of one attribute, most recent first. Intended for free text such as comments.  Requires the &#x60;analytics:read&#x60; scope.
     * @param getAttributeValuesRequest  (required)
     * @param idempotencyKey A unique value per request, such as a UUID, that makes retrying it safe: a retry with the same key and body returns the first response instead of acting twice. (optional)
     * @return ApiResponse&lt;GetAttributeValuesResponse&gt;
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
    public ApiResponse<GetAttributeValuesResponse> analyticsServiceGetAttributeValuesWithHttpInfo(@javax.annotation.Nonnull GetAttributeValuesRequest getAttributeValuesRequest, @javax.annotation.Nullable String idempotencyKey) throws ApiException {
        okhttp3.Call localVarCall = analyticsServiceGetAttributeValuesValidateBeforeCall(getAttributeValuesRequest, idempotencyKey, null);
        Type localVarReturnType = new TypeToken<GetAttributeValuesResponse>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * List visitor attribute values (asynchronously)
     * Returns the individual recorded values of one attribute, most recent first. Intended for free text such as comments.  Requires the &#x60;analytics:read&#x60; scope.
     * @param getAttributeValuesRequest  (required)
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
    public okhttp3.Call analyticsServiceGetAttributeValuesAsync(@javax.annotation.Nonnull GetAttributeValuesRequest getAttributeValuesRequest, @javax.annotation.Nullable String idempotencyKey, final ApiCallback<GetAttributeValuesResponse> _callback) throws ApiException {

        okhttp3.Call localVarCall = analyticsServiceGetAttributeValuesValidateBeforeCall(getAttributeValuesRequest, idempotencyKey, _callback);
        Type localVarReturnType = new TypeToken<GetAttributeValuesResponse>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for analyticsServiceGetBreakdown
     * @param getBreakdownRequest  (required)
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
    public okhttp3.Call analyticsServiceGetBreakdownCall(@javax.annotation.Nonnull GetBreakdownRequest getBreakdownRequest, @javax.annotation.Nullable String idempotencyKey, final ApiCallback _callback) throws ApiException {
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

        Object localVarPostBody = getBreakdownRequest;

        // create path and map variables
        String localVarPath = "/v1/analytics/breakdown";

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
    private okhttp3.Call analyticsServiceGetBreakdownValidateBeforeCall(@javax.annotation.Nonnull GetBreakdownRequest getBreakdownRequest, @javax.annotation.Nullable String idempotencyKey, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'getBreakdownRequest' is set
        if (getBreakdownRequest == null) {
            throw new ApiException("Missing the required parameter 'getBreakdownRequest' when calling analyticsServiceGetBreakdown(Async)");
        }

        return analyticsServiceGetBreakdownCall(getBreakdownRequest, idempotencyKey, _callback);

    }

    /**
     * Get dimension breakdown
     * Returns a ranked breakdown of events by any dimension (browser, platform, device type, country, city, visit type, in-app browser, in-app browser version).  Requires the &#x60;analytics:read&#x60; scope.
     * @param getBreakdownRequest  (required)
     * @param idempotencyKey A unique value per request, such as a UUID, that makes retrying it safe: a retry with the same key and body returns the first response instead of acting twice. (optional)
     * @return GetBreakdownResponse
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
    public GetBreakdownResponse analyticsServiceGetBreakdown(@javax.annotation.Nonnull GetBreakdownRequest getBreakdownRequest, @javax.annotation.Nullable String idempotencyKey) throws ApiException {
        ApiResponse<GetBreakdownResponse> localVarResp = analyticsServiceGetBreakdownWithHttpInfo(getBreakdownRequest, idempotencyKey);
        return localVarResp.getData();
    }

    /**
     * Get dimension breakdown
     * Returns a ranked breakdown of events by any dimension (browser, platform, device type, country, city, visit type, in-app browser, in-app browser version).  Requires the &#x60;analytics:read&#x60; scope.
     * @param getBreakdownRequest  (required)
     * @param idempotencyKey A unique value per request, such as a UUID, that makes retrying it safe: a retry with the same key and body returns the first response instead of acting twice. (optional)
     * @return ApiResponse&lt;GetBreakdownResponse&gt;
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
    public ApiResponse<GetBreakdownResponse> analyticsServiceGetBreakdownWithHttpInfo(@javax.annotation.Nonnull GetBreakdownRequest getBreakdownRequest, @javax.annotation.Nullable String idempotencyKey) throws ApiException {
        okhttp3.Call localVarCall = analyticsServiceGetBreakdownValidateBeforeCall(getBreakdownRequest, idempotencyKey, null);
        Type localVarReturnType = new TypeToken<GetBreakdownResponse>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Get dimension breakdown (asynchronously)
     * Returns a ranked breakdown of events by any dimension (browser, platform, device type, country, city, visit type, in-app browser, in-app browser version).  Requires the &#x60;analytics:read&#x60; scope.
     * @param getBreakdownRequest  (required)
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
    public okhttp3.Call analyticsServiceGetBreakdownAsync(@javax.annotation.Nonnull GetBreakdownRequest getBreakdownRequest, @javax.annotation.Nullable String idempotencyKey, final ApiCallback<GetBreakdownResponse> _callback) throws ApiException {

        okhttp3.Call localVarCall = analyticsServiceGetBreakdownValidateBeforeCall(getBreakdownRequest, idempotencyKey, _callback);
        Type localVarReturnType = new TypeToken<GetBreakdownResponse>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for analyticsServiceGetDeviceDistribution
     * @param getDeviceDistributionRequest  (required)
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
    public okhttp3.Call analyticsServiceGetDeviceDistributionCall(@javax.annotation.Nonnull GetDeviceDistributionRequest getDeviceDistributionRequest, @javax.annotation.Nullable String idempotencyKey, final ApiCallback _callback) throws ApiException {
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

        Object localVarPostBody = getDeviceDistributionRequest;

        // create path and map variables
        String localVarPath = "/v1/analytics/devices";

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
    private okhttp3.Call analyticsServiceGetDeviceDistributionValidateBeforeCall(@javax.annotation.Nonnull GetDeviceDistributionRequest getDeviceDistributionRequest, @javax.annotation.Nullable String idempotencyKey, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'getDeviceDistributionRequest' is set
        if (getDeviceDistributionRequest == null) {
            throw new ApiException("Missing the required parameter 'getDeviceDistributionRequest' when calling analyticsServiceGetDeviceDistribution(Async)");
        }

        return analyticsServiceGetDeviceDistributionCall(getDeviceDistributionRequest, idempotencyKey, _callback);

    }

    /**
     * Get device distribution
     * Retrieve event distribution across device types.  Requires the &#x60;analytics:read&#x60; scope.
     * @param getDeviceDistributionRequest  (required)
     * @param idempotencyKey A unique value per request, such as a UUID, that makes retrying it safe: a retry with the same key and body returns the first response instead of acting twice. (optional)
     * @return GetDeviceDistributionResponse
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
    public GetDeviceDistributionResponse analyticsServiceGetDeviceDistribution(@javax.annotation.Nonnull GetDeviceDistributionRequest getDeviceDistributionRequest, @javax.annotation.Nullable String idempotencyKey) throws ApiException {
        ApiResponse<GetDeviceDistributionResponse> localVarResp = analyticsServiceGetDeviceDistributionWithHttpInfo(getDeviceDistributionRequest, idempotencyKey);
        return localVarResp.getData();
    }

    /**
     * Get device distribution
     * Retrieve event distribution across device types.  Requires the &#x60;analytics:read&#x60; scope.
     * @param getDeviceDistributionRequest  (required)
     * @param idempotencyKey A unique value per request, such as a UUID, that makes retrying it safe: a retry with the same key and body returns the first response instead of acting twice. (optional)
     * @return ApiResponse&lt;GetDeviceDistributionResponse&gt;
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
    public ApiResponse<GetDeviceDistributionResponse> analyticsServiceGetDeviceDistributionWithHttpInfo(@javax.annotation.Nonnull GetDeviceDistributionRequest getDeviceDistributionRequest, @javax.annotation.Nullable String idempotencyKey) throws ApiException {
        okhttp3.Call localVarCall = analyticsServiceGetDeviceDistributionValidateBeforeCall(getDeviceDistributionRequest, idempotencyKey, null);
        Type localVarReturnType = new TypeToken<GetDeviceDistributionResponse>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Get device distribution (asynchronously)
     * Retrieve event distribution across device types.  Requires the &#x60;analytics:read&#x60; scope.
     * @param getDeviceDistributionRequest  (required)
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
    public okhttp3.Call analyticsServiceGetDeviceDistributionAsync(@javax.annotation.Nonnull GetDeviceDistributionRequest getDeviceDistributionRequest, @javax.annotation.Nullable String idempotencyKey, final ApiCallback<GetDeviceDistributionResponse> _callback) throws ApiException {

        okhttp3.Call localVarCall = analyticsServiceGetDeviceDistributionValidateBeforeCall(getDeviceDistributionRequest, idempotencyKey, _callback);
        Type localVarReturnType = new TypeToken<GetDeviceDistributionResponse>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for analyticsServiceGetEventsMetric
     * @param startDate Inclusive start timestamp for the aggregation (optional)
     * @param endDate Inclusive end timestamp for the aggregation (optional)
     * @param linkId Optional link filter (UUID); when omitted aggregates workspace-wide  UUID format (optional)
     * @param directoryId Optional directory filter (UUID): the links of this directory and of every directory below it. Send linkId or directoryId, not both; when both are omitted aggregates workspace-wide (optional)
     * @param tagId Optional tag filter (UUID): every link carrying this tag. Send at most one of linkId, directoryId and tagId (optional)
     * @param countries Optional country filter: visits from any of these countries (ISO 3166-1 alpha-2 codes, e.g. \&quot;FR\&quot;). Combines with the link, directory or tag filter (optional)
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
    public okhttp3.Call analyticsServiceGetEventsMetricCall(@javax.annotation.Nullable OffsetDateTime startDate, @javax.annotation.Nullable OffsetDateTime endDate, @javax.annotation.Nullable String linkId, @javax.annotation.Nullable String directoryId, @javax.annotation.Nullable String tagId, @javax.annotation.Nullable List<String> countries, final ApiCallback _callback) throws ApiException {
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
        String localVarPath = "/v1/metrics/events";

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
        Map<String, String> localVarHeaderParams = new HashMap<String, String>();
        Map<String, String> localVarCookieParams = new HashMap<String, String>();
        Map<String, Object> localVarFormParams = new HashMap<String, Object>();

        if (startDate != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("startDate", startDate));
        }

        if (endDate != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("endDate", endDate));
        }

        if (linkId != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("linkId", linkId));
        }

        if (directoryId != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("directoryId", directoryId));
        }

        if (tagId != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("tagId", tagId));
        }

        if (countries != null) {
            localVarCollectionQueryParams.addAll(localVarApiClient.parameterToPairs("multi", "countries", countries));
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
    private okhttp3.Call analyticsServiceGetEventsMetricValidateBeforeCall(@javax.annotation.Nullable OffsetDateTime startDate, @javax.annotation.Nullable OffsetDateTime endDate, @javax.annotation.Nullable String linkId, @javax.annotation.Nullable String directoryId, @javax.annotation.Nullable String tagId, @javax.annotation.Nullable List<String> countries, final ApiCallback _callback) throws ApiException {
        return analyticsServiceGetEventsMetricCall(startDate, endDate, linkId, directoryId, tagId, countries, _callback);

    }

    /**
     * Get events metric
     * Retrieve aggregated event analytics for the provided time window and optional link filter.  Requires the &#x60;analytics:read&#x60; scope.
     * @param startDate Inclusive start timestamp for the aggregation (optional)
     * @param endDate Inclusive end timestamp for the aggregation (optional)
     * @param linkId Optional link filter (UUID); when omitted aggregates workspace-wide  UUID format (optional)
     * @param directoryId Optional directory filter (UUID): the links of this directory and of every directory below it. Send linkId or directoryId, not both; when both are omitted aggregates workspace-wide (optional)
     * @param tagId Optional tag filter (UUID): every link carrying this tag. Send at most one of linkId, directoryId and tagId (optional)
     * @param countries Optional country filter: visits from any of these countries (ISO 3166-1 alpha-2 codes, e.g. \&quot;FR\&quot;). Combines with the link, directory or tag filter (optional)
     * @return EventsMetric
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
    public EventsMetric analyticsServiceGetEventsMetric(@javax.annotation.Nullable OffsetDateTime startDate, @javax.annotation.Nullable OffsetDateTime endDate, @javax.annotation.Nullable String linkId, @javax.annotation.Nullable String directoryId, @javax.annotation.Nullable String tagId, @javax.annotation.Nullable List<String> countries) throws ApiException {
        ApiResponse<EventsMetric> localVarResp = analyticsServiceGetEventsMetricWithHttpInfo(startDate, endDate, linkId, directoryId, tagId, countries);
        return localVarResp.getData();
    }

    /**
     * Get events metric
     * Retrieve aggregated event analytics for the provided time window and optional link filter.  Requires the &#x60;analytics:read&#x60; scope.
     * @param startDate Inclusive start timestamp for the aggregation (optional)
     * @param endDate Inclusive end timestamp for the aggregation (optional)
     * @param linkId Optional link filter (UUID); when omitted aggregates workspace-wide  UUID format (optional)
     * @param directoryId Optional directory filter (UUID): the links of this directory and of every directory below it. Send linkId or directoryId, not both; when both are omitted aggregates workspace-wide (optional)
     * @param tagId Optional tag filter (UUID): every link carrying this tag. Send at most one of linkId, directoryId and tagId (optional)
     * @param countries Optional country filter: visits from any of these countries (ISO 3166-1 alpha-2 codes, e.g. \&quot;FR\&quot;). Combines with the link, directory or tag filter (optional)
     * @return ApiResponse&lt;EventsMetric&gt;
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
    public ApiResponse<EventsMetric> analyticsServiceGetEventsMetricWithHttpInfo(@javax.annotation.Nullable OffsetDateTime startDate, @javax.annotation.Nullable OffsetDateTime endDate, @javax.annotation.Nullable String linkId, @javax.annotation.Nullable String directoryId, @javax.annotation.Nullable String tagId, @javax.annotation.Nullable List<String> countries) throws ApiException {
        okhttp3.Call localVarCall = analyticsServiceGetEventsMetricValidateBeforeCall(startDate, endDate, linkId, directoryId, tagId, countries, null);
        Type localVarReturnType = new TypeToken<EventsMetric>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Get events metric (asynchronously)
     * Retrieve aggregated event analytics for the provided time window and optional link filter.  Requires the &#x60;analytics:read&#x60; scope.
     * @param startDate Inclusive start timestamp for the aggregation (optional)
     * @param endDate Inclusive end timestamp for the aggregation (optional)
     * @param linkId Optional link filter (UUID); when omitted aggregates workspace-wide  UUID format (optional)
     * @param directoryId Optional directory filter (UUID): the links of this directory and of every directory below it. Send linkId or directoryId, not both; when both are omitted aggregates workspace-wide (optional)
     * @param tagId Optional tag filter (UUID): every link carrying this tag. Send at most one of linkId, directoryId and tagId (optional)
     * @param countries Optional country filter: visits from any of these countries (ISO 3166-1 alpha-2 codes, e.g. \&quot;FR\&quot;). Combines with the link, directory or tag filter (optional)
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
    public okhttp3.Call analyticsServiceGetEventsMetricAsync(@javax.annotation.Nullable OffsetDateTime startDate, @javax.annotation.Nullable OffsetDateTime endDate, @javax.annotation.Nullable String linkId, @javax.annotation.Nullable String directoryId, @javax.annotation.Nullable String tagId, @javax.annotation.Nullable List<String> countries, final ApiCallback<EventsMetric> _callback) throws ApiException {

        okhttp3.Call localVarCall = analyticsServiceGetEventsMetricValidateBeforeCall(startDate, endDate, linkId, directoryId, tagId, countries, _callback);
        Type localVarReturnType = new TypeToken<EventsMetric>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for analyticsServiceGetGeoBreakdown
     * @param getGeoBreakdownRequest  (required)
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
    public okhttp3.Call analyticsServiceGetGeoBreakdownCall(@javax.annotation.Nonnull GetGeoBreakdownRequest getGeoBreakdownRequest, @javax.annotation.Nullable String idempotencyKey, final ApiCallback _callback) throws ApiException {
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

        Object localVarPostBody = getGeoBreakdownRequest;

        // create path and map variables
        String localVarPath = "/v1/analytics/geo";

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
    private okhttp3.Call analyticsServiceGetGeoBreakdownValidateBeforeCall(@javax.annotation.Nonnull GetGeoBreakdownRequest getGeoBreakdownRequest, @javax.annotation.Nullable String idempotencyKey, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'getGeoBreakdownRequest' is set
        if (getGeoBreakdownRequest == null) {
            throw new ApiException("Missing the required parameter 'getGeoBreakdownRequest' when calling analyticsServiceGetGeoBreakdown(Async)");
        }

        return analyticsServiceGetGeoBreakdownCall(getGeoBreakdownRequest, idempotencyKey, _callback);

    }

    /**
     * Get geographic breakdown
     * Retrieve geographic distribution of events grouped by country or city.  Requires the &#x60;analytics:read&#x60; scope.
     * @param getGeoBreakdownRequest  (required)
     * @param idempotencyKey A unique value per request, such as a UUID, that makes retrying it safe: a retry with the same key and body returns the first response instead of acting twice. (optional)
     * @return GetGeoBreakdownResponse
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
    public GetGeoBreakdownResponse analyticsServiceGetGeoBreakdown(@javax.annotation.Nonnull GetGeoBreakdownRequest getGeoBreakdownRequest, @javax.annotation.Nullable String idempotencyKey) throws ApiException {
        ApiResponse<GetGeoBreakdownResponse> localVarResp = analyticsServiceGetGeoBreakdownWithHttpInfo(getGeoBreakdownRequest, idempotencyKey);
        return localVarResp.getData();
    }

    /**
     * Get geographic breakdown
     * Retrieve geographic distribution of events grouped by country or city.  Requires the &#x60;analytics:read&#x60; scope.
     * @param getGeoBreakdownRequest  (required)
     * @param idempotencyKey A unique value per request, such as a UUID, that makes retrying it safe: a retry with the same key and body returns the first response instead of acting twice. (optional)
     * @return ApiResponse&lt;GetGeoBreakdownResponse&gt;
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
    public ApiResponse<GetGeoBreakdownResponse> analyticsServiceGetGeoBreakdownWithHttpInfo(@javax.annotation.Nonnull GetGeoBreakdownRequest getGeoBreakdownRequest, @javax.annotation.Nullable String idempotencyKey) throws ApiException {
        okhttp3.Call localVarCall = analyticsServiceGetGeoBreakdownValidateBeforeCall(getGeoBreakdownRequest, idempotencyKey, null);
        Type localVarReturnType = new TypeToken<GetGeoBreakdownResponse>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Get geographic breakdown (asynchronously)
     * Retrieve geographic distribution of events grouped by country or city.  Requires the &#x60;analytics:read&#x60; scope.
     * @param getGeoBreakdownRequest  (required)
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
    public okhttp3.Call analyticsServiceGetGeoBreakdownAsync(@javax.annotation.Nonnull GetGeoBreakdownRequest getGeoBreakdownRequest, @javax.annotation.Nullable String idempotencyKey, final ApiCallback<GetGeoBreakdownResponse> _callback) throws ApiException {

        okhttp3.Call localVarCall = analyticsServiceGetGeoBreakdownValidateBeforeCall(getGeoBreakdownRequest, idempotencyKey, _callback);
        Type localVarReturnType = new TypeToken<GetGeoBreakdownResponse>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for analyticsServiceGetGroupedTimeSeries
     * @param getGroupedTimeSeriesRequest  (required)
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
    public okhttp3.Call analyticsServiceGetGroupedTimeSeriesCall(@javax.annotation.Nonnull GetGroupedTimeSeriesRequest getGroupedTimeSeriesRequest, @javax.annotation.Nullable String idempotencyKey, final ApiCallback _callback) throws ApiException {
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

        Object localVarPostBody = getGroupedTimeSeriesRequest;

        // create path and map variables
        String localVarPath = "/v1/analytics/timeseries";

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
    private okhttp3.Call analyticsServiceGetGroupedTimeSeriesValidateBeforeCall(@javax.annotation.Nonnull GetGroupedTimeSeriesRequest getGroupedTimeSeriesRequest, @javax.annotation.Nullable String idempotencyKey, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'getGroupedTimeSeriesRequest' is set
        if (getGroupedTimeSeriesRequest == null) {
            throw new ApiException("Missing the required parameter 'getGroupedTimeSeriesRequest' when calling analyticsServiceGetGroupedTimeSeries(Async)");
        }

        return analyticsServiceGetGroupedTimeSeriesCall(getGroupedTimeSeriesRequest, idempotencyKey, _callback);

    }

    /**
     * Get grouped time series
     * Retrieve time-bucketed event counts grouped by a dimension like country, city, device type, or visit type.  Requires the &#x60;analytics:read&#x60; scope.
     * @param getGroupedTimeSeriesRequest  (required)
     * @param idempotencyKey A unique value per request, such as a UUID, that makes retrying it safe: a retry with the same key and body returns the first response instead of acting twice. (optional)
     * @return GetGroupedTimeSeriesResponse
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
    public GetGroupedTimeSeriesResponse analyticsServiceGetGroupedTimeSeries(@javax.annotation.Nonnull GetGroupedTimeSeriesRequest getGroupedTimeSeriesRequest, @javax.annotation.Nullable String idempotencyKey) throws ApiException {
        ApiResponse<GetGroupedTimeSeriesResponse> localVarResp = analyticsServiceGetGroupedTimeSeriesWithHttpInfo(getGroupedTimeSeriesRequest, idempotencyKey);
        return localVarResp.getData();
    }

    /**
     * Get grouped time series
     * Retrieve time-bucketed event counts grouped by a dimension like country, city, device type, or visit type.  Requires the &#x60;analytics:read&#x60; scope.
     * @param getGroupedTimeSeriesRequest  (required)
     * @param idempotencyKey A unique value per request, such as a UUID, that makes retrying it safe: a retry with the same key and body returns the first response instead of acting twice. (optional)
     * @return ApiResponse&lt;GetGroupedTimeSeriesResponse&gt;
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
    public ApiResponse<GetGroupedTimeSeriesResponse> analyticsServiceGetGroupedTimeSeriesWithHttpInfo(@javax.annotation.Nonnull GetGroupedTimeSeriesRequest getGroupedTimeSeriesRequest, @javax.annotation.Nullable String idempotencyKey) throws ApiException {
        okhttp3.Call localVarCall = analyticsServiceGetGroupedTimeSeriesValidateBeforeCall(getGroupedTimeSeriesRequest, idempotencyKey, null);
        Type localVarReturnType = new TypeToken<GetGroupedTimeSeriesResponse>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Get grouped time series (asynchronously)
     * Retrieve time-bucketed event counts grouped by a dimension like country, city, device type, or visit type.  Requires the &#x60;analytics:read&#x60; scope.
     * @param getGroupedTimeSeriesRequest  (required)
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
    public okhttp3.Call analyticsServiceGetGroupedTimeSeriesAsync(@javax.annotation.Nonnull GetGroupedTimeSeriesRequest getGroupedTimeSeriesRequest, @javax.annotation.Nullable String idempotencyKey, final ApiCallback<GetGroupedTimeSeriesResponse> _callback) throws ApiException {

        okhttp3.Call localVarCall = analyticsServiceGetGroupedTimeSeriesValidateBeforeCall(getGroupedTimeSeriesRequest, idempotencyKey, _callback);
        Type localVarReturnType = new TypeToken<GetGroupedTimeSeriesResponse>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for analyticsServiceGetHourOfDayDistribution
     * @param getHourOfDayDistributionRequest  (required)
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
    public okhttp3.Call analyticsServiceGetHourOfDayDistributionCall(@javax.annotation.Nonnull GetHourOfDayDistributionRequest getHourOfDayDistributionRequest, @javax.annotation.Nullable String idempotencyKey, final ApiCallback _callback) throws ApiException {
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

        Object localVarPostBody = getHourOfDayDistributionRequest;

        // create path and map variables
        String localVarPath = "/v1/analytics/hours";

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
    private okhttp3.Call analyticsServiceGetHourOfDayDistributionValidateBeforeCall(@javax.annotation.Nonnull GetHourOfDayDistributionRequest getHourOfDayDistributionRequest, @javax.annotation.Nullable String idempotencyKey, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'getHourOfDayDistributionRequest' is set
        if (getHourOfDayDistributionRequest == null) {
            throw new ApiException("Missing the required parameter 'getHourOfDayDistributionRequest' when calling analyticsServiceGetHourOfDayDistribution(Async)");
        }

        return analyticsServiceGetHourOfDayDistributionCall(getHourOfDayDistributionRequest, idempotencyKey, _callback);

    }

    /**
     * Get hour of day distribution
     * Retrieve event counts aggregated by hour of day, in the workspace&#39;s time zone, for identifying peak activity times.  Requires the &#x60;analytics:read&#x60; scope.
     * @param getHourOfDayDistributionRequest  (required)
     * @param idempotencyKey A unique value per request, such as a UUID, that makes retrying it safe: a retry with the same key and body returns the first response instead of acting twice. (optional)
     * @return GetHourOfDayDistributionResponse
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
    public GetHourOfDayDistributionResponse analyticsServiceGetHourOfDayDistribution(@javax.annotation.Nonnull GetHourOfDayDistributionRequest getHourOfDayDistributionRequest, @javax.annotation.Nullable String idempotencyKey) throws ApiException {
        ApiResponse<GetHourOfDayDistributionResponse> localVarResp = analyticsServiceGetHourOfDayDistributionWithHttpInfo(getHourOfDayDistributionRequest, idempotencyKey);
        return localVarResp.getData();
    }

    /**
     * Get hour of day distribution
     * Retrieve event counts aggregated by hour of day, in the workspace&#39;s time zone, for identifying peak activity times.  Requires the &#x60;analytics:read&#x60; scope.
     * @param getHourOfDayDistributionRequest  (required)
     * @param idempotencyKey A unique value per request, such as a UUID, that makes retrying it safe: a retry with the same key and body returns the first response instead of acting twice. (optional)
     * @return ApiResponse&lt;GetHourOfDayDistributionResponse&gt;
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
    public ApiResponse<GetHourOfDayDistributionResponse> analyticsServiceGetHourOfDayDistributionWithHttpInfo(@javax.annotation.Nonnull GetHourOfDayDistributionRequest getHourOfDayDistributionRequest, @javax.annotation.Nullable String idempotencyKey) throws ApiException {
        okhttp3.Call localVarCall = analyticsServiceGetHourOfDayDistributionValidateBeforeCall(getHourOfDayDistributionRequest, idempotencyKey, null);
        Type localVarReturnType = new TypeToken<GetHourOfDayDistributionResponse>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Get hour of day distribution (asynchronously)
     * Retrieve event counts aggregated by hour of day, in the workspace&#39;s time zone, for identifying peak activity times.  Requires the &#x60;analytics:read&#x60; scope.
     * @param getHourOfDayDistributionRequest  (required)
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
    public okhttp3.Call analyticsServiceGetHourOfDayDistributionAsync(@javax.annotation.Nonnull GetHourOfDayDistributionRequest getHourOfDayDistributionRequest, @javax.annotation.Nullable String idempotencyKey, final ApiCallback<GetHourOfDayDistributionResponse> _callback) throws ApiException {

        okhttp3.Call localVarCall = analyticsServiceGetHourOfDayDistributionValidateBeforeCall(getHourOfDayDistributionRequest, idempotencyKey, _callback);
        Type localVarReturnType = new TypeToken<GetHourOfDayDistributionResponse>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for analyticsServiceGetSharedLocations
     * @param startDate Inclusive start timestamp. Without startDate and endDate the request covers all time. (optional)
     * @param endDate Exclusive end timestamp (optional)
     * @param limit Maximum number of positions returned, newest first. Defaults to and may not exceed 2000. total still counts every match. (optional)
     * @param linkId Optional link filter (UUID); when omitted covers the whole workspace (optional)
     * @param directoryId Optional directory filter (UUID): the links of this directory and of every directory below it. Send at most one of linkId, directoryId and tagId (optional)
     * @param tagId Optional tag filter (UUID): every link carrying this tag. Send at most one of linkId, directoryId and tagId (optional)
     * @param countries Optional country filter: visits from any of these countries (ISO 3166-1 alpha-2 codes, e.g. \&quot;FR\&quot;). Combines with the link, directory or tag filter (optional)
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
    public okhttp3.Call analyticsServiceGetSharedLocationsCall(@javax.annotation.Nullable OffsetDateTime startDate, @javax.annotation.Nullable OffsetDateTime endDate, @javax.annotation.Nullable Integer limit, @javax.annotation.Nullable String linkId, @javax.annotation.Nullable String directoryId, @javax.annotation.Nullable String tagId, @javax.annotation.Nullable List<String> countries, final ApiCallback _callback) throws ApiException {
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
        String localVarPath = "/v1/analytics/shared-locations";

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
        Map<String, String> localVarHeaderParams = new HashMap<String, String>();
        Map<String, String> localVarCookieParams = new HashMap<String, String>();
        Map<String, Object> localVarFormParams = new HashMap<String, Object>();

        if (startDate != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("startDate", startDate));
        }

        if (endDate != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("endDate", endDate));
        }

        if (limit != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("limit", limit));
        }

        if (linkId != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("linkId", linkId));
        }

        if (directoryId != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("directoryId", directoryId));
        }

        if (tagId != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("tagId", tagId));
        }

        if (countries != null) {
            localVarCollectionQueryParams.addAll(localVarApiClient.parameterToPairs("multi", "countries", countries));
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
    private okhttp3.Call analyticsServiceGetSharedLocationsValidateBeforeCall(@javax.annotation.Nullable OffsetDateTime startDate, @javax.annotation.Nullable OffsetDateTime endDate, @javax.annotation.Nullable Integer limit, @javax.annotation.Nullable String linkId, @javax.annotation.Nullable String directoryId, @javax.annotation.Nullable String tagId, @javax.annotation.Nullable List<String> countries, final ApiCallback _callback) throws ApiException {
        return analyticsServiceGetSharedLocationsCall(startDate, endDate, limit, linkId, directoryId, tagId, countries, _callback);

    }

    /**
     * List shared locations
     * Returns the precise positions visitors shared through a Location request workflow step, newest first, capped at 2000, with the total count. Without startDate and endDate it covers all time.  Requires the &#x60;analytics:read&#x60; scope.
     * @param startDate Inclusive start timestamp. Without startDate and endDate the request covers all time. (optional)
     * @param endDate Exclusive end timestamp (optional)
     * @param limit Maximum number of positions returned, newest first. Defaults to and may not exceed 2000. total still counts every match. (optional)
     * @param linkId Optional link filter (UUID); when omitted covers the whole workspace (optional)
     * @param directoryId Optional directory filter (UUID): the links of this directory and of every directory below it. Send at most one of linkId, directoryId and tagId (optional)
     * @param tagId Optional tag filter (UUID): every link carrying this tag. Send at most one of linkId, directoryId and tagId (optional)
     * @param countries Optional country filter: visits from any of these countries (ISO 3166-1 alpha-2 codes, e.g. \&quot;FR\&quot;). Combines with the link, directory or tag filter (optional)
     * @return GetSharedLocationsResponse
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
    public GetSharedLocationsResponse analyticsServiceGetSharedLocations(@javax.annotation.Nullable OffsetDateTime startDate, @javax.annotation.Nullable OffsetDateTime endDate, @javax.annotation.Nullable Integer limit, @javax.annotation.Nullable String linkId, @javax.annotation.Nullable String directoryId, @javax.annotation.Nullable String tagId, @javax.annotation.Nullable List<String> countries) throws ApiException {
        ApiResponse<GetSharedLocationsResponse> localVarResp = analyticsServiceGetSharedLocationsWithHttpInfo(startDate, endDate, limit, linkId, directoryId, tagId, countries);
        return localVarResp.getData();
    }

    /**
     * List shared locations
     * Returns the precise positions visitors shared through a Location request workflow step, newest first, capped at 2000, with the total count. Without startDate and endDate it covers all time.  Requires the &#x60;analytics:read&#x60; scope.
     * @param startDate Inclusive start timestamp. Without startDate and endDate the request covers all time. (optional)
     * @param endDate Exclusive end timestamp (optional)
     * @param limit Maximum number of positions returned, newest first. Defaults to and may not exceed 2000. total still counts every match. (optional)
     * @param linkId Optional link filter (UUID); when omitted covers the whole workspace (optional)
     * @param directoryId Optional directory filter (UUID): the links of this directory and of every directory below it. Send at most one of linkId, directoryId and tagId (optional)
     * @param tagId Optional tag filter (UUID): every link carrying this tag. Send at most one of linkId, directoryId and tagId (optional)
     * @param countries Optional country filter: visits from any of these countries (ISO 3166-1 alpha-2 codes, e.g. \&quot;FR\&quot;). Combines with the link, directory or tag filter (optional)
     * @return ApiResponse&lt;GetSharedLocationsResponse&gt;
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
    public ApiResponse<GetSharedLocationsResponse> analyticsServiceGetSharedLocationsWithHttpInfo(@javax.annotation.Nullable OffsetDateTime startDate, @javax.annotation.Nullable OffsetDateTime endDate, @javax.annotation.Nullable Integer limit, @javax.annotation.Nullable String linkId, @javax.annotation.Nullable String directoryId, @javax.annotation.Nullable String tagId, @javax.annotation.Nullable List<String> countries) throws ApiException {
        okhttp3.Call localVarCall = analyticsServiceGetSharedLocationsValidateBeforeCall(startDate, endDate, limit, linkId, directoryId, tagId, countries, null);
        Type localVarReturnType = new TypeToken<GetSharedLocationsResponse>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * List shared locations (asynchronously)
     * Returns the precise positions visitors shared through a Location request workflow step, newest first, capped at 2000, with the total count. Without startDate and endDate it covers all time.  Requires the &#x60;analytics:read&#x60; scope.
     * @param startDate Inclusive start timestamp. Without startDate and endDate the request covers all time. (optional)
     * @param endDate Exclusive end timestamp (optional)
     * @param limit Maximum number of positions returned, newest first. Defaults to and may not exceed 2000. total still counts every match. (optional)
     * @param linkId Optional link filter (UUID); when omitted covers the whole workspace (optional)
     * @param directoryId Optional directory filter (UUID): the links of this directory and of every directory below it. Send at most one of linkId, directoryId and tagId (optional)
     * @param tagId Optional tag filter (UUID): every link carrying this tag. Send at most one of linkId, directoryId and tagId (optional)
     * @param countries Optional country filter: visits from any of these countries (ISO 3166-1 alpha-2 codes, e.g. \&quot;FR\&quot;). Combines with the link, directory or tag filter (optional)
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
    public okhttp3.Call analyticsServiceGetSharedLocationsAsync(@javax.annotation.Nullable OffsetDateTime startDate, @javax.annotation.Nullable OffsetDateTime endDate, @javax.annotation.Nullable Integer limit, @javax.annotation.Nullable String linkId, @javax.annotation.Nullable String directoryId, @javax.annotation.Nullable String tagId, @javax.annotation.Nullable List<String> countries, final ApiCallback<GetSharedLocationsResponse> _callback) throws ApiException {

        okhttp3.Call localVarCall = analyticsServiceGetSharedLocationsValidateBeforeCall(startDate, endDate, limit, linkId, directoryId, tagId, countries, _callback);
        Type localVarReturnType = new TypeToken<GetSharedLocationsResponse>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for analyticsServiceGetStepFunnel
     * @param getStepFunnelRequest  (required)
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
    public okhttp3.Call analyticsServiceGetStepFunnelCall(@javax.annotation.Nonnull GetStepFunnelRequest getStepFunnelRequest, @javax.annotation.Nullable String idempotencyKey, final ApiCallback _callback) throws ApiException {
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

        Object localVarPostBody = getStepFunnelRequest;

        // create path and map variables
        String localVarPath = "/v1/analytics/funnel";

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
    private okhttp3.Call analyticsServiceGetStepFunnelValidateBeforeCall(@javax.annotation.Nonnull GetStepFunnelRequest getStepFunnelRequest, @javax.annotation.Nullable String idempotencyKey, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'getStepFunnelRequest' is set
        if (getStepFunnelRequest == null) {
            throw new ApiException("Missing the required parameter 'getStepFunnelRequest' when calling analyticsServiceGetStepFunnel(Async)");
        }

        return analyticsServiceGetStepFunnelCall(getStepFunnelRequest, idempotencyKey, _callback);

    }

    /**
     * Get workflow step funnel
     * Returns the workflow steps visitors reached, in distinct visitors, ordered from arrival to exit, with the conversion rate and drop-off at each step.  Requires the &#x60;analytics:read&#x60; scope.
     * @param getStepFunnelRequest  (required)
     * @param idempotencyKey A unique value per request, such as a UUID, that makes retrying it safe: a retry with the same key and body returns the first response instead of acting twice. (optional)
     * @return GetStepFunnelResponse
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
    public GetStepFunnelResponse analyticsServiceGetStepFunnel(@javax.annotation.Nonnull GetStepFunnelRequest getStepFunnelRequest, @javax.annotation.Nullable String idempotencyKey) throws ApiException {
        ApiResponse<GetStepFunnelResponse> localVarResp = analyticsServiceGetStepFunnelWithHttpInfo(getStepFunnelRequest, idempotencyKey);
        return localVarResp.getData();
    }

    /**
     * Get workflow step funnel
     * Returns the workflow steps visitors reached, in distinct visitors, ordered from arrival to exit, with the conversion rate and drop-off at each step.  Requires the &#x60;analytics:read&#x60; scope.
     * @param getStepFunnelRequest  (required)
     * @param idempotencyKey A unique value per request, such as a UUID, that makes retrying it safe: a retry with the same key and body returns the first response instead of acting twice. (optional)
     * @return ApiResponse&lt;GetStepFunnelResponse&gt;
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
    public ApiResponse<GetStepFunnelResponse> analyticsServiceGetStepFunnelWithHttpInfo(@javax.annotation.Nonnull GetStepFunnelRequest getStepFunnelRequest, @javax.annotation.Nullable String idempotencyKey) throws ApiException {
        okhttp3.Call localVarCall = analyticsServiceGetStepFunnelValidateBeforeCall(getStepFunnelRequest, idempotencyKey, null);
        Type localVarReturnType = new TypeToken<GetStepFunnelResponse>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Get workflow step funnel (asynchronously)
     * Returns the workflow steps visitors reached, in distinct visitors, ordered from arrival to exit, with the conversion rate and drop-off at each step.  Requires the &#x60;analytics:read&#x60; scope.
     * @param getStepFunnelRequest  (required)
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
    public okhttp3.Call analyticsServiceGetStepFunnelAsync(@javax.annotation.Nonnull GetStepFunnelRequest getStepFunnelRequest, @javax.annotation.Nullable String idempotencyKey, final ApiCallback<GetStepFunnelResponse> _callback) throws ApiException {

        okhttp3.Call localVarCall = analyticsServiceGetStepFunnelValidateBeforeCall(getStepFunnelRequest, idempotencyKey, _callback);
        Type localVarReturnType = new TypeToken<GetStepFunnelResponse>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for analyticsServiceGetTopLinks
     * @param getTopLinksRequest  (required)
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
    public okhttp3.Call analyticsServiceGetTopLinksCall(@javax.annotation.Nonnull GetTopLinksRequest getTopLinksRequest, @javax.annotation.Nullable String idempotencyKey, final ApiCallback _callback) throws ApiException {
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

        Object localVarPostBody = getTopLinksRequest;

        // create path and map variables
        String localVarPath = "/v1/analytics/top-links";

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
    private okhttp3.Call analyticsServiceGetTopLinksValidateBeforeCall(@javax.annotation.Nonnull GetTopLinksRequest getTopLinksRequest, @javax.annotation.Nullable String idempotencyKey, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'getTopLinksRequest' is set
        if (getTopLinksRequest == null) {
            throw new ApiException("Missing the required parameter 'getTopLinksRequest' when calling analyticsServiceGetTopLinks(Async)");
        }

        return analyticsServiceGetTopLinksCall(getTopLinksRequest, idempotencyKey, _callback);

    }

    /**
     * Get top performing links
     * Retrieve links ranked by event count for identifying best performing QR codes and links.  Requires the &#x60;analytics:read&#x60; scope.
     * @param getTopLinksRequest  (required)
     * @param idempotencyKey A unique value per request, such as a UUID, that makes retrying it safe: a retry with the same key and body returns the first response instead of acting twice. (optional)
     * @return GetTopLinksResponse
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
    public GetTopLinksResponse analyticsServiceGetTopLinks(@javax.annotation.Nonnull GetTopLinksRequest getTopLinksRequest, @javax.annotation.Nullable String idempotencyKey) throws ApiException {
        ApiResponse<GetTopLinksResponse> localVarResp = analyticsServiceGetTopLinksWithHttpInfo(getTopLinksRequest, idempotencyKey);
        return localVarResp.getData();
    }

    /**
     * Get top performing links
     * Retrieve links ranked by event count for identifying best performing QR codes and links.  Requires the &#x60;analytics:read&#x60; scope.
     * @param getTopLinksRequest  (required)
     * @param idempotencyKey A unique value per request, such as a UUID, that makes retrying it safe: a retry with the same key and body returns the first response instead of acting twice. (optional)
     * @return ApiResponse&lt;GetTopLinksResponse&gt;
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
    public ApiResponse<GetTopLinksResponse> analyticsServiceGetTopLinksWithHttpInfo(@javax.annotation.Nonnull GetTopLinksRequest getTopLinksRequest, @javax.annotation.Nullable String idempotencyKey) throws ApiException {
        okhttp3.Call localVarCall = analyticsServiceGetTopLinksValidateBeforeCall(getTopLinksRequest, idempotencyKey, null);
        Type localVarReturnType = new TypeToken<GetTopLinksResponse>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Get top performing links (asynchronously)
     * Retrieve links ranked by event count for identifying best performing QR codes and links.  Requires the &#x60;analytics:read&#x60; scope.
     * @param getTopLinksRequest  (required)
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
    public okhttp3.Call analyticsServiceGetTopLinksAsync(@javax.annotation.Nonnull GetTopLinksRequest getTopLinksRequest, @javax.annotation.Nullable String idempotencyKey, final ApiCallback<GetTopLinksResponse> _callback) throws ApiException {

        okhttp3.Call localVarCall = analyticsServiceGetTopLinksValidateBeforeCall(getTopLinksRequest, idempotencyKey, _callback);
        Type localVarReturnType = new TypeToken<GetTopLinksResponse>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for analyticsServiceGetTrafficSplitResults
     * @param getTrafficSplitResultsRequest  (required)
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
    public okhttp3.Call analyticsServiceGetTrafficSplitResultsCall(@javax.annotation.Nonnull GetTrafficSplitResultsRequest getTrafficSplitResultsRequest, @javax.annotation.Nullable String idempotencyKey, final ApiCallback _callback) throws ApiException {
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

        Object localVarPostBody = getTrafficSplitResultsRequest;

        // create path and map variables
        String localVarPath = "/v1/analytics/traffic-split";

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
    private okhttp3.Call analyticsServiceGetTrafficSplitResultsValidateBeforeCall(@javax.annotation.Nonnull GetTrafficSplitResultsRequest getTrafficSplitResultsRequest, @javax.annotation.Nullable String idempotencyKey, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'getTrafficSplitResultsRequest' is set
        if (getTrafficSplitResultsRequest == null) {
            throw new ApiException("Missing the required parameter 'getTrafficSplitResultsRequest' when calling analyticsServiceGetTrafficSplitResults(Async)");
        }

        return analyticsServiceGetTrafficSplitResultsCall(getTrafficSplitResultsRequest, idempotencyKey, _callback);

    }

    /**
     * Get traffic split results
     * Returns visits and visitors per variant of a traffic split step, leaving out bots, link previews and automated browsers, with whether there is enough data (100 per variant) and a sample ratio mismatch warning. A sticky split, the default, compares visitors against the weights. With sticky off every visit is assigned afresh, so it compares visits and one person may appear under several variants. It does not test significance or name a winner.  Requires the &#x60;analytics:read&#x60; scope.
     * @param getTrafficSplitResultsRequest  (required)
     * @param idempotencyKey A unique value per request, such as a UUID, that makes retrying it safe: a retry with the same key and body returns the first response instead of acting twice. (optional)
     * @return GetTrafficSplitResultsResponse
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
    public GetTrafficSplitResultsResponse analyticsServiceGetTrafficSplitResults(@javax.annotation.Nonnull GetTrafficSplitResultsRequest getTrafficSplitResultsRequest, @javax.annotation.Nullable String idempotencyKey) throws ApiException {
        ApiResponse<GetTrafficSplitResultsResponse> localVarResp = analyticsServiceGetTrafficSplitResultsWithHttpInfo(getTrafficSplitResultsRequest, idempotencyKey);
        return localVarResp.getData();
    }

    /**
     * Get traffic split results
     * Returns visits and visitors per variant of a traffic split step, leaving out bots, link previews and automated browsers, with whether there is enough data (100 per variant) and a sample ratio mismatch warning. A sticky split, the default, compares visitors against the weights. With sticky off every visit is assigned afresh, so it compares visits and one person may appear under several variants. It does not test significance or name a winner.  Requires the &#x60;analytics:read&#x60; scope.
     * @param getTrafficSplitResultsRequest  (required)
     * @param idempotencyKey A unique value per request, such as a UUID, that makes retrying it safe: a retry with the same key and body returns the first response instead of acting twice. (optional)
     * @return ApiResponse&lt;GetTrafficSplitResultsResponse&gt;
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
    public ApiResponse<GetTrafficSplitResultsResponse> analyticsServiceGetTrafficSplitResultsWithHttpInfo(@javax.annotation.Nonnull GetTrafficSplitResultsRequest getTrafficSplitResultsRequest, @javax.annotation.Nullable String idempotencyKey) throws ApiException {
        okhttp3.Call localVarCall = analyticsServiceGetTrafficSplitResultsValidateBeforeCall(getTrafficSplitResultsRequest, idempotencyKey, null);
        Type localVarReturnType = new TypeToken<GetTrafficSplitResultsResponse>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Get traffic split results (asynchronously)
     * Returns visits and visitors per variant of a traffic split step, leaving out bots, link previews and automated browsers, with whether there is enough data (100 per variant) and a sample ratio mismatch warning. A sticky split, the default, compares visitors against the weights. With sticky off every visit is assigned afresh, so it compares visits and one person may appear under several variants. It does not test significance or name a winner.  Requires the &#x60;analytics:read&#x60; scope.
     * @param getTrafficSplitResultsRequest  (required)
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
    public okhttp3.Call analyticsServiceGetTrafficSplitResultsAsync(@javax.annotation.Nonnull GetTrafficSplitResultsRequest getTrafficSplitResultsRequest, @javax.annotation.Nullable String idempotencyKey, final ApiCallback<GetTrafficSplitResultsResponse> _callback) throws ApiException {

        okhttp3.Call localVarCall = analyticsServiceGetTrafficSplitResultsValidateBeforeCall(getTrafficSplitResultsRequest, idempotencyKey, _callback);
        Type localVarReturnType = new TypeToken<GetTrafficSplitResultsResponse>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for analyticsServiceGetVisitorTrends
     * @param getVisitorTrendsRequest  (required)
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
    public okhttp3.Call analyticsServiceGetVisitorTrendsCall(@javax.annotation.Nonnull GetVisitorTrendsRequest getVisitorTrendsRequest, @javax.annotation.Nullable String idempotencyKey, final ApiCallback _callback) throws ApiException {
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

        Object localVarPostBody = getVisitorTrendsRequest;

        // create path and map variables
        String localVarPath = "/v1/analytics/visitors";

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
    private okhttp3.Call analyticsServiceGetVisitorTrendsValidateBeforeCall(@javax.annotation.Nonnull GetVisitorTrendsRequest getVisitorTrendsRequest, @javax.annotation.Nullable String idempotencyKey, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'getVisitorTrendsRequest' is set
        if (getVisitorTrendsRequest == null) {
            throw new ApiException("Missing the required parameter 'getVisitorTrendsRequest' when calling analyticsServiceGetVisitorTrends(Async)");
        }

        return analyticsServiceGetVisitorTrendsCall(getVisitorTrendsRequest, idempotencyKey, _callback);

    }

    /**
     * Get visitor trends
     * Retrieve new vs returning visitor counts over time.  Requires the &#x60;analytics:read&#x60; scope.
     * @param getVisitorTrendsRequest  (required)
     * @param idempotencyKey A unique value per request, such as a UUID, that makes retrying it safe: a retry with the same key and body returns the first response instead of acting twice. (optional)
     * @return GetVisitorTrendsResponse
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
    public GetVisitorTrendsResponse analyticsServiceGetVisitorTrends(@javax.annotation.Nonnull GetVisitorTrendsRequest getVisitorTrendsRequest, @javax.annotation.Nullable String idempotencyKey) throws ApiException {
        ApiResponse<GetVisitorTrendsResponse> localVarResp = analyticsServiceGetVisitorTrendsWithHttpInfo(getVisitorTrendsRequest, idempotencyKey);
        return localVarResp.getData();
    }

    /**
     * Get visitor trends
     * Retrieve new vs returning visitor counts over time.  Requires the &#x60;analytics:read&#x60; scope.
     * @param getVisitorTrendsRequest  (required)
     * @param idempotencyKey A unique value per request, such as a UUID, that makes retrying it safe: a retry with the same key and body returns the first response instead of acting twice. (optional)
     * @return ApiResponse&lt;GetVisitorTrendsResponse&gt;
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
    public ApiResponse<GetVisitorTrendsResponse> analyticsServiceGetVisitorTrendsWithHttpInfo(@javax.annotation.Nonnull GetVisitorTrendsRequest getVisitorTrendsRequest, @javax.annotation.Nullable String idempotencyKey) throws ApiException {
        okhttp3.Call localVarCall = analyticsServiceGetVisitorTrendsValidateBeforeCall(getVisitorTrendsRequest, idempotencyKey, null);
        Type localVarReturnType = new TypeToken<GetVisitorTrendsResponse>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Get visitor trends (asynchronously)
     * Retrieve new vs returning visitor counts over time.  Requires the &#x60;analytics:read&#x60; scope.
     * @param getVisitorTrendsRequest  (required)
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
    public okhttp3.Call analyticsServiceGetVisitorTrendsAsync(@javax.annotation.Nonnull GetVisitorTrendsRequest getVisitorTrendsRequest, @javax.annotation.Nullable String idempotencyKey, final ApiCallback<GetVisitorTrendsResponse> _callback) throws ApiException {

        okhttp3.Call localVarCall = analyticsServiceGetVisitorTrendsValidateBeforeCall(getVisitorTrendsRequest, idempotencyKey, _callback);
        Type localVarReturnType = new TypeToken<GetVisitorTrendsResponse>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for analyticsServiceGetWorkspaceMetric
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
    public okhttp3.Call analyticsServiceGetWorkspaceMetricCall(final ApiCallback _callback) throws ApiException {
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
        String localVarPath = "/v1/metrics/workspace";

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
    private okhttp3.Call analyticsServiceGetWorkspaceMetricValidateBeforeCall(final ApiCallback _callback) throws ApiException {
        return analyticsServiceGetWorkspaceMetricCall(_callback);

    }

    /**
     * Get workspace metric
     * Return high-level workspace totals covering members, links, and activity volume.  Requires the &#x60;analytics:read&#x60; scope.
     * @return WorkspaceMetric
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
    public WorkspaceMetric analyticsServiceGetWorkspaceMetric() throws ApiException {
        ApiResponse<WorkspaceMetric> localVarResp = analyticsServiceGetWorkspaceMetricWithHttpInfo();
        return localVarResp.getData();
    }

    /**
     * Get workspace metric
     * Return high-level workspace totals covering members, links, and activity volume.  Requires the &#x60;analytics:read&#x60; scope.
     * @return ApiResponse&lt;WorkspaceMetric&gt;
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
    public ApiResponse<WorkspaceMetric> analyticsServiceGetWorkspaceMetricWithHttpInfo() throws ApiException {
        okhttp3.Call localVarCall = analyticsServiceGetWorkspaceMetricValidateBeforeCall(null);
        Type localVarReturnType = new TypeToken<WorkspaceMetric>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Get workspace metric (asynchronously)
     * Return high-level workspace totals covering members, links, and activity volume.  Requires the &#x60;analytics:read&#x60; scope.
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
    public okhttp3.Call analyticsServiceGetWorkspaceMetricAsync(final ApiCallback<WorkspaceMetric> _callback) throws ApiException {

        okhttp3.Call localVarCall = analyticsServiceGetWorkspaceMetricValidateBeforeCall(_callback);
        Type localVarReturnType = new TypeToken<WorkspaceMetric>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for analyticsServiceListVisitorCountries
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
    public okhttp3.Call analyticsServiceListVisitorCountriesCall(final ApiCallback _callback) throws ApiException {
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
        String localVarPath = "/v1/analytics/visitor-countries";

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
    private okhttp3.Call analyticsServiceListVisitorCountriesValidateBeforeCall(final ApiCallback _callback) throws ApiException {
        return analyticsServiceListVisitorCountriesCall(_callback);

    }

    /**
     * List visitor countries
     * Every country the workspace&#39;s visits came from, all time, most visited first, without counts. Meant for country filters. For visit numbers per country, use the geo breakdown.  Requires the &#x60;analytics:read&#x60; scope.
     * @return ListVisitorCountriesResponse
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
    public ListVisitorCountriesResponse analyticsServiceListVisitorCountries() throws ApiException {
        ApiResponse<ListVisitorCountriesResponse> localVarResp = analyticsServiceListVisitorCountriesWithHttpInfo();
        return localVarResp.getData();
    }

    /**
     * List visitor countries
     * Every country the workspace&#39;s visits came from, all time, most visited first, without counts. Meant for country filters. For visit numbers per country, use the geo breakdown.  Requires the &#x60;analytics:read&#x60; scope.
     * @return ApiResponse&lt;ListVisitorCountriesResponse&gt;
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
    public ApiResponse<ListVisitorCountriesResponse> analyticsServiceListVisitorCountriesWithHttpInfo() throws ApiException {
        okhttp3.Call localVarCall = analyticsServiceListVisitorCountriesValidateBeforeCall(null);
        Type localVarReturnType = new TypeToken<ListVisitorCountriesResponse>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * List visitor countries (asynchronously)
     * Every country the workspace&#39;s visits came from, all time, most visited first, without counts. Meant for country filters. For visit numbers per country, use the geo breakdown.  Requires the &#x60;analytics:read&#x60; scope.
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
    public okhttp3.Call analyticsServiceListVisitorCountriesAsync(final ApiCallback<ListVisitorCountriesResponse> _callback) throws ApiException {

        okhttp3.Call localVarCall = analyticsServiceListVisitorCountriesValidateBeforeCall(_callback);
        Type localVarReturnType = new TypeToken<ListVisitorCountriesResponse>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
}
