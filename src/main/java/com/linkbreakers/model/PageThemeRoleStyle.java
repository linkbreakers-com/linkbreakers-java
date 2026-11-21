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
import com.linkbreakers.model.FontConfig;
import com.linkbreakers.model.ThemeColor;
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
 * The look of one role: one kind of visible thing, on every page that shows it.  Every setting is optional: one left unset keeps what the role inherits from the theme&#39;s base styles (titleFont, bodyFont, elementFont, elementColor) or, for fields and chat bubbles, the look visitor pages always had. A role with nothing set is the role as it was before roles existed.
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", comments = "Generator version: 7.21.0")
public class PageThemeRoleStyle {
  public static final String SERIALIZED_NAME_ACCENT = "accent";
  @SerializedName(SERIALIZED_NAME_ACCENT)
  @javax.annotation.Nullable
  private ThemeColor accent;

  public static final String SERIALIZED_NAME_BORDER_COLOR = "borderColor";
  @SerializedName(SERIALIZED_NAME_BORDER_COLOR)
  @javax.annotation.Nullable
  private ThemeColor borderColor;

  public static final String SERIALIZED_NAME_BORDER_SIZE = "borderSize";
  @SerializedName(SERIALIZED_NAME_BORDER_SIZE)
  @javax.annotation.Nullable
  private Integer borderSize;

  public static final String SERIALIZED_NAME_FILL = "fill";
  @SerializedName(SERIALIZED_NAME_FILL)
  @javax.annotation.Nullable
  private ThemeColor fill;

  public static final String SERIALIZED_NAME_FONT = "font";
  @SerializedName(SERIALIZED_NAME_FONT)
  @javax.annotation.Nullable
  private FontConfig font;

  public static final String SERIALIZED_NAME_PLACEHOLDER_COLOR = "placeholderColor";
  @SerializedName(SERIALIZED_NAME_PLACEHOLDER_COLOR)
  @javax.annotation.Nullable
  private ThemeColor placeholderColor;

  public static final String SERIALIZED_NAME_RADIUS = "radius";
  @SerializedName(SERIALIZED_NAME_RADIUS)
  @javax.annotation.Nullable
  private Integer radius;

  public PageThemeRoleStyle() {
  }

  public PageThemeRoleStyle accent(@javax.annotation.Nullable ThemeColor accent) {
    this.accent = accent;
    return this;
  }

  /**
   * Choices: the fill of a picked answer and its indicator. Fields: the focus ring.
   * @return accent
   */
  @javax.annotation.Nullable
  public ThemeColor getAccent() {
    return accent;
  }

  public void setAccent(@javax.annotation.Nullable ThemeColor accent) {
    this.accent = accent;
  }


  public PageThemeRoleStyle borderColor(@javax.annotation.Nullable ThemeColor borderColor) {
    this.borderColor = borderColor;
    return this;
  }

  /**
   * Border colour.
   * @return borderColor
   */
  @javax.annotation.Nullable
  public ThemeColor getBorderColor() {
    return borderColor;
  }

  public void setBorderColor(@javax.annotation.Nullable ThemeColor borderColor) {
    this.borderColor = borderColor;
  }


  public PageThemeRoleStyle borderSize(@javax.annotation.Nullable Integer borderSize) {
    this.borderSize = borderSize;
    return this;
  }

  /**
   * Border width in pixels, 0 to 12. 0 removes the border.
   * @return borderSize
   */
  @javax.annotation.Nullable
  public Integer getBorderSize() {
    return borderSize;
  }

  public void setBorderSize(@javax.annotation.Nullable Integer borderSize) {
    this.borderSize = borderSize;
  }


  public PageThemeRoleStyle fill(@javax.annotation.Nullable ThemeColor fill) {
    this.fill = fill;
    return this;
  }

  /**
   * Its background. An rgba colour makes it translucent (glass).
   * @return fill
   */
  @javax.annotation.Nullable
  public ThemeColor getFill() {
    return fill;
  }

  public void setFill(@javax.annotation.Nullable ThemeColor fill) {
    this.fill = fill;
  }


  public PageThemeRoleStyle font(@javax.annotation.Nullable FontConfig font) {
    this.font = font;
    return this;
  }

  /**
   * Its text: family, bold, italic, size and colour.
   * @return font
   */
  @javax.annotation.Nullable
  public FontConfig getFont() {
    return font;
  }

  public void setFont(@javax.annotation.Nullable FontConfig font) {
    this.font = font;
  }


  public PageThemeRoleStyle placeholderColor(@javax.annotation.Nullable ThemeColor placeholderColor) {
    this.placeholderColor = placeholderColor;
    return this;
  }

  /**
   * Fields only: the colour of the placeholder text.
   * @return placeholderColor
   */
  @javax.annotation.Nullable
  public ThemeColor getPlaceholderColor() {
    return placeholderColor;
  }

  public void setPlaceholderColor(@javax.annotation.Nullable ThemeColor placeholderColor) {
    this.placeholderColor = placeholderColor;
  }


  public PageThemeRoleStyle radius(@javax.annotation.Nullable Integer radius) {
    this.radius = radius;
    return this;
  }

  /**
   * Corner radius in pixels, 0 to 999 (999 draws a pill).
   * @return radius
   */
  @javax.annotation.Nullable
  public Integer getRadius() {
    return radius;
  }

  public void setRadius(@javax.annotation.Nullable Integer radius) {
    this.radius = radius;
  }



  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    PageThemeRoleStyle pageThemeRoleStyle = (PageThemeRoleStyle) o;
    return Objects.equals(this.accent, pageThemeRoleStyle.accent) &&
        Objects.equals(this.borderColor, pageThemeRoleStyle.borderColor) &&
        Objects.equals(this.borderSize, pageThemeRoleStyle.borderSize) &&
        Objects.equals(this.fill, pageThemeRoleStyle.fill) &&
        Objects.equals(this.font, pageThemeRoleStyle.font) &&
        Objects.equals(this.placeholderColor, pageThemeRoleStyle.placeholderColor) &&
        Objects.equals(this.radius, pageThemeRoleStyle.radius);
  }

  @Override
  public int hashCode() {
    return Objects.hash(accent, borderColor, borderSize, fill, font, placeholderColor, radius);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class PageThemeRoleStyle {\n");
    sb.append("    accent: ").append(toIndentedString(accent)).append("\n");
    sb.append("    borderColor: ").append(toIndentedString(borderColor)).append("\n");
    sb.append("    borderSize: ").append(toIndentedString(borderSize)).append("\n");
    sb.append("    fill: ").append(toIndentedString(fill)).append("\n");
    sb.append("    font: ").append(toIndentedString(font)).append("\n");
    sb.append("    placeholderColor: ").append(toIndentedString(placeholderColor)).append("\n");
    sb.append("    radius: ").append(toIndentedString(radius)).append("\n");
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
    openapiFields = new HashSet<String>(Arrays.asList("accent", "borderColor", "borderSize", "fill", "font", "placeholderColor", "radius"));

    // a set of required properties/fields (JSON key names)
    openapiRequiredFields = new HashSet<String>(0);
  }

  /**
   * Validates the JSON Element and throws an exception if issues found
   *
   * @param jsonElement JSON Element
   * @throws IOException if the JSON Element is invalid with respect to PageThemeRoleStyle
   */
  public static void validateJsonElement(JsonElement jsonElement) throws IOException {
      if (jsonElement == null) {
        if (!PageThemeRoleStyle.openapiRequiredFields.isEmpty()) { // has required fields but JSON element is null
          throw new IllegalArgumentException(String.format(java.util.Locale.ROOT, "The required field(s) %s in PageThemeRoleStyle is not found in the empty JSON string", PageThemeRoleStyle.openapiRequiredFields.toString()));
        }
      }

      Set<Map.Entry<String, JsonElement>> entries = jsonElement.getAsJsonObject().entrySet();
      // check to see if the JSON string contains additional fields
      for (Map.Entry<String, JsonElement> entry : entries) {
        if (!PageThemeRoleStyle.openapiFields.contains(entry.getKey())) {
          throw new IllegalArgumentException(String.format(java.util.Locale.ROOT, "The field `%s` in the JSON string is not defined in the `PageThemeRoleStyle` properties. JSON: %s", entry.getKey(), jsonElement.toString()));
        }
      }
        JsonObject jsonObj = jsonElement.getAsJsonObject();
      // validate the optional field `accent`
      if (jsonObj.get("accent") != null && !jsonObj.get("accent").isJsonNull()) {
        ThemeColor.validateJsonElement(jsonObj.get("accent"));
      }
      // validate the optional field `borderColor`
      if (jsonObj.get("borderColor") != null && !jsonObj.get("borderColor").isJsonNull()) {
        ThemeColor.validateJsonElement(jsonObj.get("borderColor"));
      }
      // validate the optional field `fill`
      if (jsonObj.get("fill") != null && !jsonObj.get("fill").isJsonNull()) {
        ThemeColor.validateJsonElement(jsonObj.get("fill"));
      }
      // validate the optional field `font`
      if (jsonObj.get("font") != null && !jsonObj.get("font").isJsonNull()) {
        FontConfig.validateJsonElement(jsonObj.get("font"));
      }
      // validate the optional field `placeholderColor`
      if (jsonObj.get("placeholderColor") != null && !jsonObj.get("placeholderColor").isJsonNull()) {
        ThemeColor.validateJsonElement(jsonObj.get("placeholderColor"));
      }
  }

  public static class CustomTypeAdapterFactory implements TypeAdapterFactory {
    @SuppressWarnings("unchecked")
    @Override
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
       if (!PageThemeRoleStyle.class.isAssignableFrom(type.getRawType())) {
         return null; // this class only serializes 'PageThemeRoleStyle' and its subtypes
       }
       final TypeAdapter<JsonElement> elementAdapter = gson.getAdapter(JsonElement.class);
       final TypeAdapter<PageThemeRoleStyle> thisAdapter
                        = gson.getDelegateAdapter(this, TypeToken.get(PageThemeRoleStyle.class));

       return (TypeAdapter<T>) new TypeAdapter<PageThemeRoleStyle>() {
           @Override
           public void write(JsonWriter out, PageThemeRoleStyle value) throws IOException {
             JsonObject obj = thisAdapter.toJsonTree(value).getAsJsonObject();
             elementAdapter.write(out, obj);
           }

           @Override
           public PageThemeRoleStyle read(JsonReader in) throws IOException {
             JsonElement jsonElement = elementAdapter.read(in);
             validateJsonElement(jsonElement);
             return thisAdapter.fromJsonTree(jsonElement);
           }

       }.nullSafe();
    }
  }

  /**
   * Create an instance of PageThemeRoleStyle given an JSON string
   *
   * @param jsonString JSON string
   * @return An instance of PageThemeRoleStyle
   * @throws IOException if the JSON string is invalid with respect to PageThemeRoleStyle
   */
  public static PageThemeRoleStyle fromJson(String jsonString) throws IOException {
    return JSON.getGson().fromJson(jsonString, PageThemeRoleStyle.class);
  }

  /**
   * Convert an instance of PageThemeRoleStyle to an JSON string
   *
   * @return JSON string
   */
  public String toJson() {
    return JSON.getGson().toJson(this);
  }
}

