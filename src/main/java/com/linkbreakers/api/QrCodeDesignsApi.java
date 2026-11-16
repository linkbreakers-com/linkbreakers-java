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


import com.linkbreakers.model.ApplyQrcodeTemplateRequest;
import com.linkbreakers.model.ApplyQrcodeTemplateResponse;
import com.linkbreakers.model.CreateQrcodeDesignRequest;
import com.linkbreakers.model.CreateQrcodeDesignResponse;
import com.linkbreakers.model.ErrorResponse;
import com.linkbreakers.model.GetQrcodeDesignResponse;
import com.linkbreakers.model.QrcodeDesignsServiceUpdateBody;
import com.linkbreakers.model.UpdateQrcodeDesignResponse;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QrCodeDesignsApi {
    private ApiClient localVarApiClient;
    private int localHostIndex;
    private String localCustomBaseUrl;

    public QrCodeDesignsApi() {
        this(Configuration.getDefaultApiClient());
    }

    public QrCodeDesignsApi(ApiClient apiClient) {
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
     * Build call for qrcodeDesignsServiceApplyTemplate
     * @param applyQrcodeTemplateRequest  (required)
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
    public okhttp3.Call qrcodeDesignsServiceApplyTemplateCall(@javax.annotation.Nonnull ApplyQrcodeTemplateRequest applyQrcodeTemplateRequest, @javax.annotation.Nullable String idempotencyKey, final ApiCallback _callback) throws ApiException {
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

        Object localVarPostBody = applyQrcodeTemplateRequest;

        // create path and map variables
        String localVarPath = "/v1/qrcode-designs/apply-template";

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
    private okhttp3.Call qrcodeDesignsServiceApplyTemplateValidateBeforeCall(@javax.annotation.Nonnull ApplyQrcodeTemplateRequest applyQrcodeTemplateRequest, @javax.annotation.Nullable String idempotencyKey, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'applyQrcodeTemplateRequest' is set
        if (applyQrcodeTemplateRequest == null) {
            throw new ApiException("Missing the required parameter 'applyQrcodeTemplateRequest' when calling qrcodeDesignsServiceApplyTemplate(Async)");
        }

        return qrcodeDesignsServiceApplyTemplateCall(applyQrcodeTemplateRequest, idempotencyKey, _callback);

    }

    /**
     * Apply a QR code template to a link
     * Copy all design settings from a community QR code template to an existing link&#39;s QR code design. This replaces the current design with the template&#39;s styling.  Requires the &#x60;qrcodes:write&#x60; scope.
     * @param applyQrcodeTemplateRequest  (required)
     * @param idempotencyKey A unique value per request, such as a UUID, that makes retrying it safe: a retry with the same key and body returns the first response instead of acting twice. (optional)
     * @return ApplyQrcodeTemplateResponse
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
    public ApplyQrcodeTemplateResponse qrcodeDesignsServiceApplyTemplate(@javax.annotation.Nonnull ApplyQrcodeTemplateRequest applyQrcodeTemplateRequest, @javax.annotation.Nullable String idempotencyKey) throws ApiException {
        ApiResponse<ApplyQrcodeTemplateResponse> localVarResp = qrcodeDesignsServiceApplyTemplateWithHttpInfo(applyQrcodeTemplateRequest, idempotencyKey);
        return localVarResp.getData();
    }

    /**
     * Apply a QR code template to a link
     * Copy all design settings from a community QR code template to an existing link&#39;s QR code design. This replaces the current design with the template&#39;s styling.  Requires the &#x60;qrcodes:write&#x60; scope.
     * @param applyQrcodeTemplateRequest  (required)
     * @param idempotencyKey A unique value per request, such as a UUID, that makes retrying it safe: a retry with the same key and body returns the first response instead of acting twice. (optional)
     * @return ApiResponse&lt;ApplyQrcodeTemplateResponse&gt;
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
    public ApiResponse<ApplyQrcodeTemplateResponse> qrcodeDesignsServiceApplyTemplateWithHttpInfo(@javax.annotation.Nonnull ApplyQrcodeTemplateRequest applyQrcodeTemplateRequest, @javax.annotation.Nullable String idempotencyKey) throws ApiException {
        okhttp3.Call localVarCall = qrcodeDesignsServiceApplyTemplateValidateBeforeCall(applyQrcodeTemplateRequest, idempotencyKey, null);
        Type localVarReturnType = new TypeToken<ApplyQrcodeTemplateResponse>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Apply a QR code template to a link (asynchronously)
     * Copy all design settings from a community QR code template to an existing link&#39;s QR code design. This replaces the current design with the template&#39;s styling.  Requires the &#x60;qrcodes:write&#x60; scope.
     * @param applyQrcodeTemplateRequest  (required)
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
    public okhttp3.Call qrcodeDesignsServiceApplyTemplateAsync(@javax.annotation.Nonnull ApplyQrcodeTemplateRequest applyQrcodeTemplateRequest, @javax.annotation.Nullable String idempotencyKey, final ApiCallback<ApplyQrcodeTemplateResponse> _callback) throws ApiException {

        okhttp3.Call localVarCall = qrcodeDesignsServiceApplyTemplateValidateBeforeCall(applyQrcodeTemplateRequest, idempotencyKey, _callback);
        Type localVarReturnType = new TypeToken<ApplyQrcodeTemplateResponse>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for qrcodeDesignsServiceCreate
     * @param createQrcodeDesignRequest  (required)
     * @param idempotencyKey A unique value per request, such as a UUID, that makes retrying it safe: a retry with the same key and body returns the first response instead of acting twice. (optional)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table border="1">
       <caption>Response Details</caption>
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Everything worked as expected. </td><td>  * Idempotent-Replayed -  <br>  * RateLimit -  <br>  * RateLimit-Policy -  <br>  * RateLimit-Remaining -  <br>  </td></tr>
        <tr><td> 201 </td><td> QR code design created successfully </td><td>  * Idempotent-Replayed -  <br>  * RateLimit -  <br>  * RateLimit-Policy -  <br>  * RateLimit-Remaining -  <br>  </td></tr>
        <tr><td> 429 </td><td> Too many requests. Wait the number of seconds in Retry-After, or until the RateLimit window resets, then retry. </td><td>  * RateLimit -  <br>  * RateLimit-Limit -  <br>  * RateLimit-Policy -  <br>  * RateLimit-Remaining -  <br>  * RateLimit-Reset -  <br>  * Retry-After -  <br>  * X-RateLimit-Limit -  <br>  * X-RateLimit-Remaining -  <br>  * X-RateLimit-Reset -  <br>  </td></tr>
        <tr><td> 0 </td><td> Unexpected error </td><td>  * RateLimit -  <br>  * RateLimit-Policy -  <br>  * RateLimit-Remaining -  <br>  </td></tr>
     </table>
     */
    public okhttp3.Call qrcodeDesignsServiceCreateCall(@javax.annotation.Nonnull CreateQrcodeDesignRequest createQrcodeDesignRequest, @javax.annotation.Nullable String idempotencyKey, final ApiCallback _callback) throws ApiException {
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

        Object localVarPostBody = createQrcodeDesignRequest;

        // create path and map variables
        String localVarPath = "/v1/qrcode-designs";

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


        String[] localVarAuthNames = new String[] {  };
        return localVarApiClient.buildCall(basePath, localVarPath, "POST", localVarQueryParams, localVarCollectionQueryParams, localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAuthNames, _callback);
    }

    @SuppressWarnings("rawtypes")
    private okhttp3.Call qrcodeDesignsServiceCreateValidateBeforeCall(@javax.annotation.Nonnull CreateQrcodeDesignRequest createQrcodeDesignRequest, @javax.annotation.Nullable String idempotencyKey, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'createQrcodeDesignRequest' is set
        if (createQrcodeDesignRequest == null) {
            throw new ApiException("Missing the required parameter 'createQrcodeDesignRequest' when calling qrcodeDesignsServiceCreate(Async)");
        }

        return qrcodeDesignsServiceCreateCall(createQrcodeDesignRequest, idempotencyKey, _callback);

    }

    /**
     * Create a new QR code design
     * Provision a QR code styling preset that can be reused across links or templates.
     * @param createQrcodeDesignRequest  (required)
     * @param idempotencyKey A unique value per request, such as a UUID, that makes retrying it safe: a retry with the same key and body returns the first response instead of acting twice. (optional)
     * @return CreateQrcodeDesignResponse
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table border="1">
       <caption>Response Details</caption>
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Everything worked as expected. </td><td>  * Idempotent-Replayed -  <br>  * RateLimit -  <br>  * RateLimit-Policy -  <br>  * RateLimit-Remaining -  <br>  </td></tr>
        <tr><td> 201 </td><td> QR code design created successfully </td><td>  * Idempotent-Replayed -  <br>  * RateLimit -  <br>  * RateLimit-Policy -  <br>  * RateLimit-Remaining -  <br>  </td></tr>
        <tr><td> 429 </td><td> Too many requests. Wait the number of seconds in Retry-After, or until the RateLimit window resets, then retry. </td><td>  * RateLimit -  <br>  * RateLimit-Limit -  <br>  * RateLimit-Policy -  <br>  * RateLimit-Remaining -  <br>  * RateLimit-Reset -  <br>  * Retry-After -  <br>  * X-RateLimit-Limit -  <br>  * X-RateLimit-Remaining -  <br>  * X-RateLimit-Reset -  <br>  </td></tr>
        <tr><td> 0 </td><td> Unexpected error </td><td>  * RateLimit -  <br>  * RateLimit-Policy -  <br>  * RateLimit-Remaining -  <br>  </td></tr>
     </table>
     */
    public CreateQrcodeDesignResponse qrcodeDesignsServiceCreate(@javax.annotation.Nonnull CreateQrcodeDesignRequest createQrcodeDesignRequest, @javax.annotation.Nullable String idempotencyKey) throws ApiException {
        ApiResponse<CreateQrcodeDesignResponse> localVarResp = qrcodeDesignsServiceCreateWithHttpInfo(createQrcodeDesignRequest, idempotencyKey);
        return localVarResp.getData();
    }

    /**
     * Create a new QR code design
     * Provision a QR code styling preset that can be reused across links or templates.
     * @param createQrcodeDesignRequest  (required)
     * @param idempotencyKey A unique value per request, such as a UUID, that makes retrying it safe: a retry with the same key and body returns the first response instead of acting twice. (optional)
     * @return ApiResponse&lt;CreateQrcodeDesignResponse&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table border="1">
       <caption>Response Details</caption>
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Everything worked as expected. </td><td>  * Idempotent-Replayed -  <br>  * RateLimit -  <br>  * RateLimit-Policy -  <br>  * RateLimit-Remaining -  <br>  </td></tr>
        <tr><td> 201 </td><td> QR code design created successfully </td><td>  * Idempotent-Replayed -  <br>  * RateLimit -  <br>  * RateLimit-Policy -  <br>  * RateLimit-Remaining -  <br>  </td></tr>
        <tr><td> 429 </td><td> Too many requests. Wait the number of seconds in Retry-After, or until the RateLimit window resets, then retry. </td><td>  * RateLimit -  <br>  * RateLimit-Limit -  <br>  * RateLimit-Policy -  <br>  * RateLimit-Remaining -  <br>  * RateLimit-Reset -  <br>  * Retry-After -  <br>  * X-RateLimit-Limit -  <br>  * X-RateLimit-Remaining -  <br>  * X-RateLimit-Reset -  <br>  </td></tr>
        <tr><td> 0 </td><td> Unexpected error </td><td>  * RateLimit -  <br>  * RateLimit-Policy -  <br>  * RateLimit-Remaining -  <br>  </td></tr>
     </table>
     */
    public ApiResponse<CreateQrcodeDesignResponse> qrcodeDesignsServiceCreateWithHttpInfo(@javax.annotation.Nonnull CreateQrcodeDesignRequest createQrcodeDesignRequest, @javax.annotation.Nullable String idempotencyKey) throws ApiException {
        okhttp3.Call localVarCall = qrcodeDesignsServiceCreateValidateBeforeCall(createQrcodeDesignRequest, idempotencyKey, null);
        Type localVarReturnType = new TypeToken<CreateQrcodeDesignResponse>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Create a new QR code design (asynchronously)
     * Provision a QR code styling preset that can be reused across links or templates.
     * @param createQrcodeDesignRequest  (required)
     * @param idempotencyKey A unique value per request, such as a UUID, that makes retrying it safe: a retry with the same key and body returns the first response instead of acting twice. (optional)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table border="1">
       <caption>Response Details</caption>
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Everything worked as expected. </td><td>  * Idempotent-Replayed -  <br>  * RateLimit -  <br>  * RateLimit-Policy -  <br>  * RateLimit-Remaining -  <br>  </td></tr>
        <tr><td> 201 </td><td> QR code design created successfully </td><td>  * Idempotent-Replayed -  <br>  * RateLimit -  <br>  * RateLimit-Policy -  <br>  * RateLimit-Remaining -  <br>  </td></tr>
        <tr><td> 429 </td><td> Too many requests. Wait the number of seconds in Retry-After, or until the RateLimit window resets, then retry. </td><td>  * RateLimit -  <br>  * RateLimit-Limit -  <br>  * RateLimit-Policy -  <br>  * RateLimit-Remaining -  <br>  * RateLimit-Reset -  <br>  * Retry-After -  <br>  * X-RateLimit-Limit -  <br>  * X-RateLimit-Remaining -  <br>  * X-RateLimit-Reset -  <br>  </td></tr>
        <tr><td> 0 </td><td> Unexpected error </td><td>  * RateLimit -  <br>  * RateLimit-Policy -  <br>  * RateLimit-Remaining -  <br>  </td></tr>
     </table>
     */
    public okhttp3.Call qrcodeDesignsServiceCreateAsync(@javax.annotation.Nonnull CreateQrcodeDesignRequest createQrcodeDesignRequest, @javax.annotation.Nullable String idempotencyKey, final ApiCallback<CreateQrcodeDesignResponse> _callback) throws ApiException {

        okhttp3.Call localVarCall = qrcodeDesignsServiceCreateValidateBeforeCall(createQrcodeDesignRequest, idempotencyKey, _callback);
        Type localVarReturnType = new TypeToken<CreateQrcodeDesignResponse>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for qrcodeDesignsServiceDelete
     * @param id The ID of the QR code design to delete (required)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table border="1">
       <caption>Response Details</caption>
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Everything worked as expected. </td><td>  * RateLimit -  <br>  * RateLimit-Policy -  <br>  * RateLimit-Remaining -  <br>  </td></tr>
        <tr><td> 204 </td><td> QR code design deleted successfully </td><td>  * RateLimit -  <br>  * RateLimit-Policy -  <br>  * RateLimit-Remaining -  <br>  </td></tr>
        <tr><td> 429 </td><td> Too many requests. Wait the number of seconds in Retry-After, or until the RateLimit window resets, then retry. </td><td>  * RateLimit -  <br>  * RateLimit-Limit -  <br>  * RateLimit-Policy -  <br>  * RateLimit-Remaining -  <br>  * RateLimit-Reset -  <br>  * Retry-After -  <br>  * X-RateLimit-Limit -  <br>  * X-RateLimit-Remaining -  <br>  * X-RateLimit-Reset -  <br>  </td></tr>
        <tr><td> 0 </td><td> Unexpected error </td><td>  * RateLimit -  <br>  * RateLimit-Policy -  <br>  * RateLimit-Remaining -  <br>  </td></tr>
     </table>
     */
    public okhttp3.Call qrcodeDesignsServiceDeleteCall(@javax.annotation.Nonnull String id, final ApiCallback _callback) throws ApiException {
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
        String localVarPath = "/v1/qrcode-designs/{id}"
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
    private okhttp3.Call qrcodeDesignsServiceDeleteValidateBeforeCall(@javax.annotation.Nonnull String id, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new ApiException("Missing the required parameter 'id' when calling qrcodeDesignsServiceDelete(Async)");
        }

        return qrcodeDesignsServiceDeleteCall(id, _callback);

    }

    /**
     * Delete a QR code design
     * Remove a QR code design that is no longer in use by any assets.  Requires the &#x60;qrcodes:write&#x60; scope.
     * @param id The ID of the QR code design to delete (required)
     * @return Object
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table border="1">
       <caption>Response Details</caption>
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Everything worked as expected. </td><td>  * RateLimit -  <br>  * RateLimit-Policy -  <br>  * RateLimit-Remaining -  <br>  </td></tr>
        <tr><td> 204 </td><td> QR code design deleted successfully </td><td>  * RateLimit -  <br>  * RateLimit-Policy -  <br>  * RateLimit-Remaining -  <br>  </td></tr>
        <tr><td> 429 </td><td> Too many requests. Wait the number of seconds in Retry-After, or until the RateLimit window resets, then retry. </td><td>  * RateLimit -  <br>  * RateLimit-Limit -  <br>  * RateLimit-Policy -  <br>  * RateLimit-Remaining -  <br>  * RateLimit-Reset -  <br>  * Retry-After -  <br>  * X-RateLimit-Limit -  <br>  * X-RateLimit-Remaining -  <br>  * X-RateLimit-Reset -  <br>  </td></tr>
        <tr><td> 0 </td><td> Unexpected error </td><td>  * RateLimit -  <br>  * RateLimit-Policy -  <br>  * RateLimit-Remaining -  <br>  </td></tr>
     </table>
     */
    public Object qrcodeDesignsServiceDelete(@javax.annotation.Nonnull String id) throws ApiException {
        ApiResponse<Object> localVarResp = qrcodeDesignsServiceDeleteWithHttpInfo(id);
        return localVarResp.getData();
    }

    /**
     * Delete a QR code design
     * Remove a QR code design that is no longer in use by any assets.  Requires the &#x60;qrcodes:write&#x60; scope.
     * @param id The ID of the QR code design to delete (required)
     * @return ApiResponse&lt;Object&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table border="1">
       <caption>Response Details</caption>
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Everything worked as expected. </td><td>  * RateLimit -  <br>  * RateLimit-Policy -  <br>  * RateLimit-Remaining -  <br>  </td></tr>
        <tr><td> 204 </td><td> QR code design deleted successfully </td><td>  * RateLimit -  <br>  * RateLimit-Policy -  <br>  * RateLimit-Remaining -  <br>  </td></tr>
        <tr><td> 429 </td><td> Too many requests. Wait the number of seconds in Retry-After, or until the RateLimit window resets, then retry. </td><td>  * RateLimit -  <br>  * RateLimit-Limit -  <br>  * RateLimit-Policy -  <br>  * RateLimit-Remaining -  <br>  * RateLimit-Reset -  <br>  * Retry-After -  <br>  * X-RateLimit-Limit -  <br>  * X-RateLimit-Remaining -  <br>  * X-RateLimit-Reset -  <br>  </td></tr>
        <tr><td> 0 </td><td> Unexpected error </td><td>  * RateLimit -  <br>  * RateLimit-Policy -  <br>  * RateLimit-Remaining -  <br>  </td></tr>
     </table>
     */
    public ApiResponse<Object> qrcodeDesignsServiceDeleteWithHttpInfo(@javax.annotation.Nonnull String id) throws ApiException {
        okhttp3.Call localVarCall = qrcodeDesignsServiceDeleteValidateBeforeCall(id, null);
        Type localVarReturnType = new TypeToken<Object>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Delete a QR code design (asynchronously)
     * Remove a QR code design that is no longer in use by any assets.  Requires the &#x60;qrcodes:write&#x60; scope.
     * @param id The ID of the QR code design to delete (required)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table border="1">
       <caption>Response Details</caption>
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Everything worked as expected. </td><td>  * RateLimit -  <br>  * RateLimit-Policy -  <br>  * RateLimit-Remaining -  <br>  </td></tr>
        <tr><td> 204 </td><td> QR code design deleted successfully </td><td>  * RateLimit -  <br>  * RateLimit-Policy -  <br>  * RateLimit-Remaining -  <br>  </td></tr>
        <tr><td> 429 </td><td> Too many requests. Wait the number of seconds in Retry-After, or until the RateLimit window resets, then retry. </td><td>  * RateLimit -  <br>  * RateLimit-Limit -  <br>  * RateLimit-Policy -  <br>  * RateLimit-Remaining -  <br>  * RateLimit-Reset -  <br>  * Retry-After -  <br>  * X-RateLimit-Limit -  <br>  * X-RateLimit-Remaining -  <br>  * X-RateLimit-Reset -  <br>  </td></tr>
        <tr><td> 0 </td><td> Unexpected error </td><td>  * RateLimit -  <br>  * RateLimit-Policy -  <br>  * RateLimit-Remaining -  <br>  </td></tr>
     </table>
     */
    public okhttp3.Call qrcodeDesignsServiceDeleteAsync(@javax.annotation.Nonnull String id, final ApiCallback<Object> _callback) throws ApiException {

        okhttp3.Call localVarCall = qrcodeDesignsServiceDeleteValidateBeforeCall(id, _callback);
        Type localVarReturnType = new TypeToken<Object>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for qrcodeDesignsServiceGet
     * @param id The ID of the QR code design to get (UUID) (required)
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
    public okhttp3.Call qrcodeDesignsServiceGetCall(@javax.annotation.Nonnull String id, final ApiCallback _callback) throws ApiException {
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
        String localVarPath = "/v1/qrcode-designs/{id}"
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
    private okhttp3.Call qrcodeDesignsServiceGetValidateBeforeCall(@javax.annotation.Nonnull String id, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new ApiException("Missing the required parameter 'id' when calling qrcodeDesignsServiceGet(Async)");
        }

        return qrcodeDesignsServiceGetCall(id, _callback);

    }

    /**
     * Get a QR code design
     * Fetch a QR code design by its identifier including computed metadata.  Requires the &#x60;qrcodes:read&#x60; scope.
     * @param id The ID of the QR code design to get (UUID) (required)
     * @return GetQrcodeDesignResponse
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
    public GetQrcodeDesignResponse qrcodeDesignsServiceGet(@javax.annotation.Nonnull String id) throws ApiException {
        ApiResponse<GetQrcodeDesignResponse> localVarResp = qrcodeDesignsServiceGetWithHttpInfo(id);
        return localVarResp.getData();
    }

    /**
     * Get a QR code design
     * Fetch a QR code design by its identifier including computed metadata.  Requires the &#x60;qrcodes:read&#x60; scope.
     * @param id The ID of the QR code design to get (UUID) (required)
     * @return ApiResponse&lt;GetQrcodeDesignResponse&gt;
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
    public ApiResponse<GetQrcodeDesignResponse> qrcodeDesignsServiceGetWithHttpInfo(@javax.annotation.Nonnull String id) throws ApiException {
        okhttp3.Call localVarCall = qrcodeDesignsServiceGetValidateBeforeCall(id, null);
        Type localVarReturnType = new TypeToken<GetQrcodeDesignResponse>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Get a QR code design (asynchronously)
     * Fetch a QR code design by its identifier including computed metadata.  Requires the &#x60;qrcodes:read&#x60; scope.
     * @param id The ID of the QR code design to get (UUID) (required)
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
    public okhttp3.Call qrcodeDesignsServiceGetAsync(@javax.annotation.Nonnull String id, final ApiCallback<GetQrcodeDesignResponse> _callback) throws ApiException {

        okhttp3.Call localVarCall = qrcodeDesignsServiceGetValidateBeforeCall(id, _callback);
        Type localVarReturnType = new TypeToken<GetQrcodeDesignResponse>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for qrcodeDesignsServiceUpdate
     * @param id The ID of the QR code design to update (UUID) (required)
     * @param qrcodeDesignsServiceUpdateBody  (required)
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
    public okhttp3.Call qrcodeDesignsServiceUpdateCall(@javax.annotation.Nonnull String id, @javax.annotation.Nonnull QrcodeDesignsServiceUpdateBody qrcodeDesignsServiceUpdateBody, @javax.annotation.Nullable String idempotencyKey, final ApiCallback _callback) throws ApiException {
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

        Object localVarPostBody = qrcodeDesignsServiceUpdateBody;

        // create path and map variables
        String localVarPath = "/v1/qrcode-designs/{id}"
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
    private okhttp3.Call qrcodeDesignsServiceUpdateValidateBeforeCall(@javax.annotation.Nonnull String id, @javax.annotation.Nonnull QrcodeDesignsServiceUpdateBody qrcodeDesignsServiceUpdateBody, @javax.annotation.Nullable String idempotencyKey, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new ApiException("Missing the required parameter 'id' when calling qrcodeDesignsServiceUpdate(Async)");
        }

        // verify the required parameter 'qrcodeDesignsServiceUpdateBody' is set
        if (qrcodeDesignsServiceUpdateBody == null) {
            throw new ApiException("Missing the required parameter 'qrcodeDesignsServiceUpdateBody' when calling qrcodeDesignsServiceUpdate(Async)");
        }

        return qrcodeDesignsServiceUpdateCall(id, qrcodeDesignsServiceUpdateBody, idempotencyKey, _callback);

    }

    /**
     * Update a QR code design
     * Apply styling tweaks or media changes to an existing QR code design.  Requires the &#x60;qrcodes:write&#x60; scope.
     * @param id The ID of the QR code design to update (UUID) (required)
     * @param qrcodeDesignsServiceUpdateBody  (required)
     * @param idempotencyKey A unique value per request, such as a UUID, that makes retrying it safe: a retry with the same key and body returns the first response instead of acting twice. (optional)
     * @return UpdateQrcodeDesignResponse
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
    public UpdateQrcodeDesignResponse qrcodeDesignsServiceUpdate(@javax.annotation.Nonnull String id, @javax.annotation.Nonnull QrcodeDesignsServiceUpdateBody qrcodeDesignsServiceUpdateBody, @javax.annotation.Nullable String idempotencyKey) throws ApiException {
        ApiResponse<UpdateQrcodeDesignResponse> localVarResp = qrcodeDesignsServiceUpdateWithHttpInfo(id, qrcodeDesignsServiceUpdateBody, idempotencyKey);
        return localVarResp.getData();
    }

    /**
     * Update a QR code design
     * Apply styling tweaks or media changes to an existing QR code design.  Requires the &#x60;qrcodes:write&#x60; scope.
     * @param id The ID of the QR code design to update (UUID) (required)
     * @param qrcodeDesignsServiceUpdateBody  (required)
     * @param idempotencyKey A unique value per request, such as a UUID, that makes retrying it safe: a retry with the same key and body returns the first response instead of acting twice. (optional)
     * @return ApiResponse&lt;UpdateQrcodeDesignResponse&gt;
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
    public ApiResponse<UpdateQrcodeDesignResponse> qrcodeDesignsServiceUpdateWithHttpInfo(@javax.annotation.Nonnull String id, @javax.annotation.Nonnull QrcodeDesignsServiceUpdateBody qrcodeDesignsServiceUpdateBody, @javax.annotation.Nullable String idempotencyKey) throws ApiException {
        okhttp3.Call localVarCall = qrcodeDesignsServiceUpdateValidateBeforeCall(id, qrcodeDesignsServiceUpdateBody, idempotencyKey, null);
        Type localVarReturnType = new TypeToken<UpdateQrcodeDesignResponse>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Update a QR code design (asynchronously)
     * Apply styling tweaks or media changes to an existing QR code design.  Requires the &#x60;qrcodes:write&#x60; scope.
     * @param id The ID of the QR code design to update (UUID) (required)
     * @param qrcodeDesignsServiceUpdateBody  (required)
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
    public okhttp3.Call qrcodeDesignsServiceUpdateAsync(@javax.annotation.Nonnull String id, @javax.annotation.Nonnull QrcodeDesignsServiceUpdateBody qrcodeDesignsServiceUpdateBody, @javax.annotation.Nullable String idempotencyKey, final ApiCallback<UpdateQrcodeDesignResponse> _callback) throws ApiException {

        okhttp3.Call localVarCall = qrcodeDesignsServiceUpdateValidateBeforeCall(id, qrcodeDesignsServiceUpdateBody, idempotencyKey, _callback);
        Type localVarReturnType = new TypeToken<UpdateQrcodeDesignResponse>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
}
