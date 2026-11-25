/*
 * Linkbreakers API
 * The Linkbreakers REST API: links, QR codes, workflows, visitors and analytics, as JSON over HTTPS. Send a workspace API token as `Authorization: Bearer <token>`.  ## Entry points  `GET https://api.linkbreakers.com/` and `GET https://api.linkbreakers.com/v1` answer without credentials with the API's name, version and links. This document is served at `https://api.linkbreakers.com/openapi.json`.  ## Rate limits  Every response describes where the caller stands in the IETF `RateLimit-Policy` and `RateLimit` header fields (draft-ietf-httpapi-ratelimit-headers), and in `RateLimit-Limit`, `RateLimit-Remaining`, `RateLimit-Reset` and `X-RateLimit-Limit`, `X-RateLimit-Remaining`, `X-RateLimit-Reset` (a Unix timestamp) for clients that read those. A request past the limit gets `429 Too Many Requests` with `Retry-After` in seconds.  - Workspace API token: 720 requests per 60 seconds, per token. - Dashboard session or OAuth token: 600 requests per 60 seconds, per token. - No credentials: 300 requests per 60 seconds, per client IP.  Each plan also includes a number of API calls per billing cycle. Past it, calls answer `429` with the code `MAX_PUBLIC_API_QUERIED_REACHED` until the cycle renews or the plan changes.  ## Pagination  Every list pages with opaque cursors (AIP-158). Pass `pageSize`, then send the `nextPageToken` of each response back as `pageToken` (or its alias `cursor`) to get the next page. An empty or absent `nextPageToken` means the list is complete. Each list operation describes this in `x-pagination`.  ## Batches and background jobs  `POST /v1/links/bulk` creates up to 250 links in one transactional request; `POST /v1/links/batch` takes the same links as a bare JSON array. `POST /v1/links/bulk/jobs` accepts the same batch and answers `202 Accepted` at once with a link job and a `Location` header; poll `GET /v1/links/jobs/{id}` until `status` is `LINK_JOB_STATUS_SUCCEEDED` (the created link ids are in `linksBulkCreate.linkIds`) or `LINK_JOB_STATUS_FAILED` (`error` says why, and nothing was created). Link jobs can be read for 24 hours after they finish.  ## Paused workspaces  A free workspace whose members have not verified any email address is paused 7 days after sign-up. Its calls then answer `403` with the code `WORKSPACE_UNVERIFIED` and change nothing; QR codes and links keep working for visitors. To lift the pause, ask the workspace owner to verify their email: `POST /v1/members/email-verification` sends the verification email again, and works while the workspace is paused. `unverifiedPauseAt` on the workspace says when the pause starts.  ## Versioning and deprecation  The version is in the path: every operation lives under `/v1`. Within a version, changes are additive only: new operations, new optional request fields, new response fields and new enum values. Clients should ignore fields and enum values they do not know. Anything that would break an existing client ships under a new path version instead.  An operation is retired in two steps. First it is deprecated: it keeps working, is flagged `deprecated: true` in this document, and every response carries a `Deprecation` header field (RFC 9745) with the time it was deprecated and a `Link` with `rel=\"deprecation\"` to the policy. Then, at least 180 days later, it may be removed; that date is announced in advance in a `Sunset` header field (RFC 8594) and as `x-sunset` on the operation. The full policy: https://linkbreakers.com/help/article/api-versioning-and-deprecation-policy  A renamed operation keeps answering its former path while clients still call it, until that path's sunset at the latest: a former path no client calls for 14 days is removed. Responses on a former path carry the same `Deprecation`, `Sunset` and `Link` header fields; this document lists only the current path, and the policy lists the former paths that still answer.  ## Idempotent retries  Every POST, PUT and PATCH accepts an optional `Idempotency-Key` header (draft-ietf-httpapi-idempotency-key-header). Send a new unique value, such as a UUID, with each logical request and reuse it only to retry that request. A retry with the same key and the same request returns the first response, marked `Idempotent-Replayed: true`, instead of running again. Keys are scoped to the token and kept for 24 hours. The same key with a different request is refused with `422`, and a retry while the first request is still running gets `409` with `Retry-After`. Server errors (5xx) and refusals such as 401, 403 and 429 are not stored, so retrying them runs the request again.  ## Identifying your client  Send a `Linkbreakers-Client` request header saying which client made the call: `<client>/<version> (<details>)`, for example `my-integration/1.2.0 (python 3.12; linux)`. The official SDKs, the CLI and the MCP bridge set it for you, as `sdk-python/2.3.1 (python 3.12.4; linux)` or `cli/1.4.0 (darwin arm64)`. It is optional and changes nothing about how a request is handled; it tells us which clients and versions are in use, so we know what can be retired and what to fix first. Keep the details short and free of personal data. Browsers may send it too: it is allowed by CORS.
 *
 * The version of the OpenAPI document: 1.205.2
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
import com.linkbreakers.model.ContainerConfig;
import com.linkbreakers.model.FontConfig;
import com.linkbreakers.model.MediaSelection;
import com.linkbreakers.model.PageThemeElementStyleOverride;
import com.linkbreakers.model.PageThemeShape;
import com.linkbreakers.model.PageThemeSmallText;
import com.linkbreakers.model.ThemeColor;
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
 * The set of visual values a page theme carries.  It exists so styling can travel on its own, detached from any stored theme: the editor sends what is currently on screen, which is what the user means by \&quot;this look\&quot;, whether or not it has been saved yet.
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", comments = "Generator version: 7.21.0")
public class PageThemeStyling {
  public static final String SERIALIZED_NAME_BADGE = "badge";
  @SerializedName(SERIALIZED_NAME_BADGE)
  @javax.annotation.Nullable
  private MediaSelection badge;

  public static final String SERIALIZED_NAME_BADGE_ENABLED = "badgeEnabled";
  @SerializedName(SERIALIZED_NAME_BADGE_ENABLED)
  @javax.annotation.Nullable
  private Boolean badgeEnabled;

  public static final String SERIALIZED_NAME_BADGE_ICON_COLOR = "badgeIconColor";
  @SerializedName(SERIALIZED_NAME_BADGE_ICON_COLOR)
  @javax.annotation.Nullable
  private ThemeColor badgeIconColor;

  /**
   * Gets or Sets badgeShape
   */
  @JsonAdapter(BadgeShapeEnum.Adapter.class)
  public enum BadgeShapeEnum {
    PAGE_THEME_BADGE_SHAPE_UNSPECIFIED("PAGE_THEME_BADGE_SHAPE_UNSPECIFIED"),
    
    PAGE_THEME_BADGE_SHAPE_CIRCLE("PAGE_THEME_BADGE_SHAPE_CIRCLE"),
    
    PAGE_THEME_BADGE_SHAPE_SQUARE("PAGE_THEME_BADGE_SHAPE_SQUARE"),
    
    PAGE_THEME_BADGE_SHAPE_ROUNDED("PAGE_THEME_BADGE_SHAPE_ROUNDED");

    private String value;

    BadgeShapeEnum(String value) {
      this.value = value;
    }

    public String getValue() {
      return value;
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }

    public static BadgeShapeEnum fromValue(String value) {
      for (BadgeShapeEnum b : BadgeShapeEnum.values()) {
        if (b.value.equals(value)) {
          return b;
        }
      }
      throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }

    public static class Adapter extends TypeAdapter<BadgeShapeEnum> {
      @Override
      public void write(final JsonWriter jsonWriter, final BadgeShapeEnum enumeration) throws IOException {
        jsonWriter.value(enumeration.getValue());
      }

      @Override
      public BadgeShapeEnum read(final JsonReader jsonReader) throws IOException {
        String value =  jsonReader.nextString();
        return BadgeShapeEnum.fromValue(value);
      }
    }

    public static void validateJsonElement(JsonElement jsonElement) throws IOException {
      String value = jsonElement.getAsString();
      BadgeShapeEnum.fromValue(value);
    }
  }

  public static final String SERIALIZED_NAME_BADGE_SHAPE = "badgeShape";
  @SerializedName(SERIALIZED_NAME_BADGE_SHAPE)
  @javax.annotation.Nullable
  private BadgeShapeEnum badgeShape;

  /**
   * How body text is aligned: descriptions, messages, helper text, and answer and button labels. Unset: centered.
   */
  @JsonAdapter(BodyAlignEnum.Adapter.class)
  public enum BodyAlignEnum {
    PAGE_THEME_TEXT_ALIGN_UNSPECIFIED("PAGE_THEME_TEXT_ALIGN_UNSPECIFIED"),
    
    PAGE_THEME_TEXT_ALIGN_CENTER("PAGE_THEME_TEXT_ALIGN_CENTER"),
    
    PAGE_THEME_TEXT_ALIGN_LEFT("PAGE_THEME_TEXT_ALIGN_LEFT");

    private String value;

    BodyAlignEnum(String value) {
      this.value = value;
    }

    public String getValue() {
      return value;
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }

    public static BodyAlignEnum fromValue(String value) {
      for (BodyAlignEnum b : BodyAlignEnum.values()) {
        if (b.value.equals(value)) {
          return b;
        }
      }
      throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }

    public static class Adapter extends TypeAdapter<BodyAlignEnum> {
      @Override
      public void write(final JsonWriter jsonWriter, final BodyAlignEnum enumeration) throws IOException {
        jsonWriter.value(enumeration.getValue());
      }

      @Override
      public BodyAlignEnum read(final JsonReader jsonReader) throws IOException {
        String value =  jsonReader.nextString();
        return BodyAlignEnum.fromValue(value);
      }
    }

    public static void validateJsonElement(JsonElement jsonElement) throws IOException {
      String value = jsonElement.getAsString();
      BodyAlignEnum.fromValue(value);
    }
  }

  public static final String SERIALIZED_NAME_BODY_ALIGN = "bodyAlign";
  @SerializedName(SERIALIZED_NAME_BODY_ALIGN)
  @javax.annotation.Nullable
  private BodyAlignEnum bodyAlign;

  public static final String SERIALIZED_NAME_BODY_FONT = "bodyFont";
  @SerializedName(SERIALIZED_NAME_BODY_FONT)
  @javax.annotation.Nullable
  private FontConfig bodyFont;

  public static final String SERIALIZED_NAME_CANVAS_COLOR = "canvasColor";
  @SerializedName(SERIALIZED_NAME_CANVAS_COLOR)
  @javax.annotation.Nullable
  private ThemeColor canvasColor;

  public static final String SERIALIZED_NAME_CANVAS_COVER = "canvasCover";
  @SerializedName(SERIALIZED_NAME_CANVAS_COVER)
  @javax.annotation.Nullable
  private MediaSelection canvasCover;

  /**
   * Gets or Sets canvasCoverMode
   */
  @JsonAdapter(CanvasCoverModeEnum.Adapter.class)
  public enum CanvasCoverModeEnum {
    PAGE_THEME_BACKGROUND_COVER_MODE_UNSPECIFIED("PAGE_THEME_BACKGROUND_COVER_MODE_UNSPECIFIED"),
    
    PAGE_THEME_BACKGROUND_COVER_MODE_FULL("PAGE_THEME_BACKGROUND_COVER_MODE_FULL"),
    
    PAGE_THEME_BACKGROUND_COVER_MODE_PROGRESSIVE_TOP("PAGE_THEME_BACKGROUND_COVER_MODE_PROGRESSIVE_TOP"),
    
    PAGE_THEME_BACKGROUND_COVER_MODE_PROGRESSIVE_BOTTOM("PAGE_THEME_BACKGROUND_COVER_MODE_PROGRESSIVE_BOTTOM");

    private String value;

    CanvasCoverModeEnum(String value) {
      this.value = value;
    }

    public String getValue() {
      return value;
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }

    public static CanvasCoverModeEnum fromValue(String value) {
      for (CanvasCoverModeEnum b : CanvasCoverModeEnum.values()) {
        if (b.value.equals(value)) {
          return b;
        }
      }
      throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }

    public static class Adapter extends TypeAdapter<CanvasCoverModeEnum> {
      @Override
      public void write(final JsonWriter jsonWriter, final CanvasCoverModeEnum enumeration) throws IOException {
        jsonWriter.value(enumeration.getValue());
      }

      @Override
      public CanvasCoverModeEnum read(final JsonReader jsonReader) throws IOException {
        String value =  jsonReader.nextString();
        return CanvasCoverModeEnum.fromValue(value);
      }
    }

    public static void validateJsonElement(JsonElement jsonElement) throws IOException {
      String value = jsonElement.getAsString();
      CanvasCoverModeEnum.fromValue(value);
    }
  }

  public static final String SERIALIZED_NAME_CANVAS_COVER_MODE = "canvasCoverMode";
  @SerializedName(SERIALIZED_NAME_CANVAS_COVER_MODE)
  @javax.annotation.Nullable
  private CanvasCoverModeEnum canvasCoverMode;

  /**
   * A texture over the page background: NONE, BRUSHED (paint streaks), GRAIN (film grain) or STARFIELD (stars and Milky Way dust, for dark backgrounds).
   */
  @JsonAdapter(CanvasTextureEnum.Adapter.class)
  public enum CanvasTextureEnum {
    PAGE_THEME_CANVAS_TEXTURE_UNSPECIFIED("PAGE_THEME_CANVAS_TEXTURE_UNSPECIFIED"),
    
    PAGE_THEME_CANVAS_TEXTURE_NONE("PAGE_THEME_CANVAS_TEXTURE_NONE"),
    
    PAGE_THEME_CANVAS_TEXTURE_BRUSHED("PAGE_THEME_CANVAS_TEXTURE_BRUSHED"),
    
    PAGE_THEME_CANVAS_TEXTURE_GRAIN("PAGE_THEME_CANVAS_TEXTURE_GRAIN"),
    
    PAGE_THEME_CANVAS_TEXTURE_STARFIELD("PAGE_THEME_CANVAS_TEXTURE_STARFIELD");

    private String value;

    CanvasTextureEnum(String value) {
      this.value = value;
    }

    public String getValue() {
      return value;
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }

    public static CanvasTextureEnum fromValue(String value) {
      for (CanvasTextureEnum b : CanvasTextureEnum.values()) {
        if (b.value.equals(value)) {
          return b;
        }
      }
      throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }

    public static class Adapter extends TypeAdapter<CanvasTextureEnum> {
      @Override
      public void write(final JsonWriter jsonWriter, final CanvasTextureEnum enumeration) throws IOException {
        jsonWriter.value(enumeration.getValue());
      }

      @Override
      public CanvasTextureEnum read(final JsonReader jsonReader) throws IOException {
        String value =  jsonReader.nextString();
        return CanvasTextureEnum.fromValue(value);
      }
    }

    public static void validateJsonElement(JsonElement jsonElement) throws IOException {
      String value = jsonElement.getAsString();
      CanvasTextureEnum.fromValue(value);
    }
  }

  public static final String SERIALIZED_NAME_CANVAS_TEXTURE = "canvasTexture";
  @SerializedName(SERIALIZED_NAME_CANVAS_TEXTURE)
  @javax.annotation.Nullable
  private CanvasTextureEnum canvasTexture;

  /**
   * How a choice question lays out its answers: AUTO (pills or tiles) or ROWS (full-width rows).
   */
  @JsonAdapter(ChoiceLayoutEnum.Adapter.class)
  public enum ChoiceLayoutEnum {
    PAGE_THEME_CHOICE_LAYOUT_UNSPECIFIED("PAGE_THEME_CHOICE_LAYOUT_UNSPECIFIED"),
    
    PAGE_THEME_CHOICE_LAYOUT_AUTO("PAGE_THEME_CHOICE_LAYOUT_AUTO"),
    
    PAGE_THEME_CHOICE_LAYOUT_ROWS("PAGE_THEME_CHOICE_LAYOUT_ROWS");

    private String value;

    ChoiceLayoutEnum(String value) {
      this.value = value;
    }

    public String getValue() {
      return value;
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }

    public static ChoiceLayoutEnum fromValue(String value) {
      for (ChoiceLayoutEnum b : ChoiceLayoutEnum.values()) {
        if (b.value.equals(value)) {
          return b;
        }
      }
      throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }

    public static class Adapter extends TypeAdapter<ChoiceLayoutEnum> {
      @Override
      public void write(final JsonWriter jsonWriter, final ChoiceLayoutEnum enumeration) throws IOException {
        jsonWriter.value(enumeration.getValue());
      }

      @Override
      public ChoiceLayoutEnum read(final JsonReader jsonReader) throws IOException {
        String value =  jsonReader.nextString();
        return ChoiceLayoutEnum.fromValue(value);
      }
    }

    public static void validateJsonElement(JsonElement jsonElement) throws IOException {
      String value = jsonElement.getAsString();
      ChoiceLayoutEnum.fromValue(value);
    }
  }

  public static final String SERIALIZED_NAME_CHOICE_LAYOUT = "choiceLayout";
  @SerializedName(SERIALIZED_NAME_CHOICE_LAYOUT)
  @javax.annotation.Nullable
  private ChoiceLayoutEnum choiceLayout;

  public static final String SERIALIZED_NAME_CONTAINER_CONFIG = "containerConfig";
  @SerializedName(SERIALIZED_NAME_CONTAINER_CONFIG)
  @javax.annotation.Nullable
  private ContainerConfig containerConfig;

  public static final String SERIALIZED_NAME_CONTAINER_ENABLED = "containerEnabled";
  @SerializedName(SERIALIZED_NAME_CONTAINER_ENABLED)
  @javax.annotation.Nullable
  private Boolean containerEnabled;

  public static final String SERIALIZED_NAME_ELEMENT_COLOR = "elementColor";
  @SerializedName(SERIALIZED_NAME_ELEMENT_COLOR)
  @javax.annotation.Nullable
  private ThemeColor elementColor;

  public static final String SERIALIZED_NAME_ELEMENT_FONT = "elementFont";
  @SerializedName(SERIALIZED_NAME_ELEMENT_FONT)
  @javax.annotation.Nullable
  private FontConfig elementFont;

  public static final String SERIALIZED_NAME_ELEMENT_STYLES = "elementStyles";
  @SerializedName(SERIALIZED_NAME_ELEMENT_STYLES)
  @javax.annotation.Nullable
  private List<PageThemeElementStyleOverride> elementStyles = new ArrayList<>();

  public static final String SERIALIZED_NAME_FOOTER_LOGO = "footerLogo";
  @SerializedName(SERIALIZED_NAME_FOOTER_LOGO)
  @javax.annotation.Nullable
  private MediaSelection footerLogo;

  public static final String SERIALIZED_NAME_FOOTER_LOGO_ENABLED = "footerLogoEnabled";
  @SerializedName(SERIALIZED_NAME_FOOTER_LOGO_ENABLED)
  @javax.annotation.Nullable
  private Boolean footerLogoEnabled;

  /**
   * How titles and headline questions are aligned. Unset: centered titles, questions as the question style draws them.
   */
  @JsonAdapter(HeadingAlignEnum.Adapter.class)
  public enum HeadingAlignEnum {
    PAGE_THEME_TEXT_ALIGN_UNSPECIFIED("PAGE_THEME_TEXT_ALIGN_UNSPECIFIED"),
    
    PAGE_THEME_TEXT_ALIGN_CENTER("PAGE_THEME_TEXT_ALIGN_CENTER"),
    
    PAGE_THEME_TEXT_ALIGN_LEFT("PAGE_THEME_TEXT_ALIGN_LEFT");

    private String value;

    HeadingAlignEnum(String value) {
      this.value = value;
    }

    public String getValue() {
      return value;
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }

    public static HeadingAlignEnum fromValue(String value) {
      for (HeadingAlignEnum b : HeadingAlignEnum.values()) {
        if (b.value.equals(value)) {
          return b;
        }
      }
      throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }

    public static class Adapter extends TypeAdapter<HeadingAlignEnum> {
      @Override
      public void write(final JsonWriter jsonWriter, final HeadingAlignEnum enumeration) throws IOException {
        jsonWriter.value(enumeration.getValue());
      }

      @Override
      public HeadingAlignEnum read(final JsonReader jsonReader) throws IOException {
        String value =  jsonReader.nextString();
        return HeadingAlignEnum.fromValue(value);
      }
    }

    public static void validateJsonElement(JsonElement jsonElement) throws IOException {
      String value = jsonElement.getAsString();
      HeadingAlignEnum.fromValue(value);
    }
  }

  public static final String SERIALIZED_NAME_HEADING_ALIGN = "headingAlign";
  @SerializedName(SERIALIZED_NAME_HEADING_ALIGN)
  @javax.annotation.Nullable
  private HeadingAlignEnum headingAlign;

  /**
   * How a form&#39;s questions are written: LABEL (small, in the text style) or HEADLINE (centered, in the title style).
   */
  @JsonAdapter(QuestionStyleEnum.Adapter.class)
  public enum QuestionStyleEnum {
    PAGE_THEME_QUESTION_STYLE_UNSPECIFIED("PAGE_THEME_QUESTION_STYLE_UNSPECIFIED"),
    
    PAGE_THEME_QUESTION_STYLE_LABEL("PAGE_THEME_QUESTION_STYLE_LABEL"),
    
    PAGE_THEME_QUESTION_STYLE_HEADLINE("PAGE_THEME_QUESTION_STYLE_HEADLINE");

    private String value;

    QuestionStyleEnum(String value) {
      this.value = value;
    }

    public String getValue() {
      return value;
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }

    public static QuestionStyleEnum fromValue(String value) {
      for (QuestionStyleEnum b : QuestionStyleEnum.values()) {
        if (b.value.equals(value)) {
          return b;
        }
      }
      throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }

    public static class Adapter extends TypeAdapter<QuestionStyleEnum> {
      @Override
      public void write(final JsonWriter jsonWriter, final QuestionStyleEnum enumeration) throws IOException {
        jsonWriter.value(enumeration.getValue());
      }

      @Override
      public QuestionStyleEnum read(final JsonReader jsonReader) throws IOException {
        String value =  jsonReader.nextString();
        return QuestionStyleEnum.fromValue(value);
      }
    }

    public static void validateJsonElement(JsonElement jsonElement) throws IOException {
      String value = jsonElement.getAsString();
      QuestionStyleEnum.fromValue(value);
    }
  }

  public static final String SERIALIZED_NAME_QUESTION_STYLE = "questionStyle";
  @SerializedName(SERIALIZED_NAME_QUESTION_STYLE)
  @javax.annotation.Nullable
  private QuestionStyleEnum questionStyle;

  public static final String SERIALIZED_NAME_SHAPE = "shape";
  @SerializedName(SERIALIZED_NAME_SHAPE)
  @javax.annotation.Nullable
  private PageThemeShape shape;

  public static final String SERIALIZED_NAME_SMALL_TEXT = "smallText";
  @SerializedName(SERIALIZED_NAME_SMALL_TEXT)
  @javax.annotation.Nullable
  private PageThemeSmallText smallText;

  public static final String SERIALIZED_NAME_TEXT_COLOR = "textColor";
  @SerializedName(SERIALIZED_NAME_TEXT_COLOR)
  @javax.annotation.Nullable
  private ThemeColor textColor;

  public static final String SERIALIZED_NAME_TITLE_FONT = "titleFont";
  @SerializedName(SERIALIZED_NAME_TITLE_FONT)
  @javax.annotation.Nullable
  private FontConfig titleFont;

  /**
   * How visitor pages pace their content: CLASSIC shows a whole form at once and a chat as a transcript, FOCUSED one question per screen and a chat as a short fading trail.
   */
  @JsonAdapter(VisitorExperienceEnum.Adapter.class)
  public enum VisitorExperienceEnum {
    PAGE_THEME_VISITOR_EXPERIENCE_UNSPECIFIED("PAGE_THEME_VISITOR_EXPERIENCE_UNSPECIFIED"),
    
    PAGE_THEME_VISITOR_EXPERIENCE_CLASSIC("PAGE_THEME_VISITOR_EXPERIENCE_CLASSIC"),
    
    PAGE_THEME_VISITOR_EXPERIENCE_FOCUSED("PAGE_THEME_VISITOR_EXPERIENCE_FOCUSED");

    private String value;

    VisitorExperienceEnum(String value) {
      this.value = value;
    }

    public String getValue() {
      return value;
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }

    public static VisitorExperienceEnum fromValue(String value) {
      for (VisitorExperienceEnum b : VisitorExperienceEnum.values()) {
        if (b.value.equals(value)) {
          return b;
        }
      }
      throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }

    public static class Adapter extends TypeAdapter<VisitorExperienceEnum> {
      @Override
      public void write(final JsonWriter jsonWriter, final VisitorExperienceEnum enumeration) throws IOException {
        jsonWriter.value(enumeration.getValue());
      }

      @Override
      public VisitorExperienceEnum read(final JsonReader jsonReader) throws IOException {
        String value =  jsonReader.nextString();
        return VisitorExperienceEnum.fromValue(value);
      }
    }

    public static void validateJsonElement(JsonElement jsonElement) throws IOException {
      String value = jsonElement.getAsString();
      VisitorExperienceEnum.fromValue(value);
    }
  }

  public static final String SERIALIZED_NAME_VISITOR_EXPERIENCE = "visitorExperience";
  @SerializedName(SERIALIZED_NAME_VISITOR_EXPERIENCE)
  @javax.annotation.Nullable
  private VisitorExperienceEnum visitorExperience;

  public PageThemeStyling() {
  }

  public PageThemeStyling badge(@javax.annotation.Nullable MediaSelection badge) {
    this.badge = badge;
    return this;
  }

  /**
   * Get badge
   * @return badge
   */
  @javax.annotation.Nullable
  public MediaSelection getBadge() {
    return badge;
  }

  public void setBadge(@javax.annotation.Nullable MediaSelection badge) {
    this.badge = badge;
  }


  public PageThemeStyling badgeEnabled(@javax.annotation.Nullable Boolean badgeEnabled) {
    this.badgeEnabled = badgeEnabled;
    return this;
  }

  /**
   * Get badgeEnabled
   * @return badgeEnabled
   */
  @javax.annotation.Nullable
  public Boolean getBadgeEnabled() {
    return badgeEnabled;
  }

  public void setBadgeEnabled(@javax.annotation.Nullable Boolean badgeEnabled) {
    this.badgeEnabled = badgeEnabled;
  }


  public PageThemeStyling badgeIconColor(@javax.annotation.Nullable ThemeColor badgeIconColor) {
    this.badgeIconColor = badgeIconColor;
    return this;
  }

  /**
   * Get badgeIconColor
   * @return badgeIconColor
   */
  @javax.annotation.Nullable
  public ThemeColor getBadgeIconColor() {
    return badgeIconColor;
  }

  public void setBadgeIconColor(@javax.annotation.Nullable ThemeColor badgeIconColor) {
    this.badgeIconColor = badgeIconColor;
  }


  public PageThemeStyling badgeShape(@javax.annotation.Nullable BadgeShapeEnum badgeShape) {
    this.badgeShape = badgeShape;
    return this;
  }

  /**
   * Get badgeShape
   * @return badgeShape
   */
  @javax.annotation.Nullable
  public BadgeShapeEnum getBadgeShape() {
    return badgeShape;
  }

  public void setBadgeShape(@javax.annotation.Nullable BadgeShapeEnum badgeShape) {
    this.badgeShape = badgeShape;
  }


  public PageThemeStyling bodyAlign(@javax.annotation.Nullable BodyAlignEnum bodyAlign) {
    this.bodyAlign = bodyAlign;
    return this;
  }

  /**
   * How body text is aligned: descriptions, messages, helper text, and answer and button labels. Unset: centered.
   * @return bodyAlign
   */
  @javax.annotation.Nullable
  public BodyAlignEnum getBodyAlign() {
    return bodyAlign;
  }

  public void setBodyAlign(@javax.annotation.Nullable BodyAlignEnum bodyAlign) {
    this.bodyAlign = bodyAlign;
  }


  public PageThemeStyling bodyFont(@javax.annotation.Nullable FontConfig bodyFont) {
    this.bodyFont = bodyFont;
    return this;
  }

  /**
   * Get bodyFont
   * @return bodyFont
   */
  @javax.annotation.Nullable
  public FontConfig getBodyFont() {
    return bodyFont;
  }

  public void setBodyFont(@javax.annotation.Nullable FontConfig bodyFont) {
    this.bodyFont = bodyFont;
  }


  public PageThemeStyling canvasColor(@javax.annotation.Nullable ThemeColor canvasColor) {
    this.canvasColor = canvasColor;
    return this;
  }

  /**
   * Get canvasColor
   * @return canvasColor
   */
  @javax.annotation.Nullable
  public ThemeColor getCanvasColor() {
    return canvasColor;
  }

  public void setCanvasColor(@javax.annotation.Nullable ThemeColor canvasColor) {
    this.canvasColor = canvasColor;
  }


  public PageThemeStyling canvasCover(@javax.annotation.Nullable MediaSelection canvasCover) {
    this.canvasCover = canvasCover;
    return this;
  }

  /**
   * Get canvasCover
   * @return canvasCover
   */
  @javax.annotation.Nullable
  public MediaSelection getCanvasCover() {
    return canvasCover;
  }

  public void setCanvasCover(@javax.annotation.Nullable MediaSelection canvasCover) {
    this.canvasCover = canvasCover;
  }


  public PageThemeStyling canvasCoverMode(@javax.annotation.Nullable CanvasCoverModeEnum canvasCoverMode) {
    this.canvasCoverMode = canvasCoverMode;
    return this;
  }

  /**
   * Get canvasCoverMode
   * @return canvasCoverMode
   */
  @javax.annotation.Nullable
  public CanvasCoverModeEnum getCanvasCoverMode() {
    return canvasCoverMode;
  }

  public void setCanvasCoverMode(@javax.annotation.Nullable CanvasCoverModeEnum canvasCoverMode) {
    this.canvasCoverMode = canvasCoverMode;
  }


  public PageThemeStyling canvasTexture(@javax.annotation.Nullable CanvasTextureEnum canvasTexture) {
    this.canvasTexture = canvasTexture;
    return this;
  }

  /**
   * A texture over the page background: NONE, BRUSHED (paint streaks), GRAIN (film grain) or STARFIELD (stars and Milky Way dust, for dark backgrounds).
   * @return canvasTexture
   */
  @javax.annotation.Nullable
  public CanvasTextureEnum getCanvasTexture() {
    return canvasTexture;
  }

  public void setCanvasTexture(@javax.annotation.Nullable CanvasTextureEnum canvasTexture) {
    this.canvasTexture = canvasTexture;
  }


  public PageThemeStyling choiceLayout(@javax.annotation.Nullable ChoiceLayoutEnum choiceLayout) {
    this.choiceLayout = choiceLayout;
    return this;
  }

  /**
   * How a choice question lays out its answers: AUTO (pills or tiles) or ROWS (full-width rows).
   * @return choiceLayout
   */
  @javax.annotation.Nullable
  public ChoiceLayoutEnum getChoiceLayout() {
    return choiceLayout;
  }

  public void setChoiceLayout(@javax.annotation.Nullable ChoiceLayoutEnum choiceLayout) {
    this.choiceLayout = choiceLayout;
  }


  public PageThemeStyling containerConfig(@javax.annotation.Nullable ContainerConfig containerConfig) {
    this.containerConfig = containerConfig;
    return this;
  }

  /**
   * Get containerConfig
   * @return containerConfig
   */
  @javax.annotation.Nullable
  public ContainerConfig getContainerConfig() {
    return containerConfig;
  }

  public void setContainerConfig(@javax.annotation.Nullable ContainerConfig containerConfig) {
    this.containerConfig = containerConfig;
  }


  public PageThemeStyling containerEnabled(@javax.annotation.Nullable Boolean containerEnabled) {
    this.containerEnabled = containerEnabled;
    return this;
  }

  /**
   * Get containerEnabled
   * @return containerEnabled
   */
  @javax.annotation.Nullable
  public Boolean getContainerEnabled() {
    return containerEnabled;
  }

  public void setContainerEnabled(@javax.annotation.Nullable Boolean containerEnabled) {
    this.containerEnabled = containerEnabled;
  }


  public PageThemeStyling elementColor(@javax.annotation.Nullable ThemeColor elementColor) {
    this.elementColor = elementColor;
    return this;
  }

  /**
   * Get elementColor
   * @return elementColor
   */
  @javax.annotation.Nullable
  public ThemeColor getElementColor() {
    return elementColor;
  }

  public void setElementColor(@javax.annotation.Nullable ThemeColor elementColor) {
    this.elementColor = elementColor;
  }


  public PageThemeStyling elementFont(@javax.annotation.Nullable FontConfig elementFont) {
    this.elementFont = elementFont;
    return this;
  }

  /**
   * Get elementFont
   * @return elementFont
   */
  @javax.annotation.Nullable
  public FontConfig getElementFont() {
    return elementFont;
  }

  public void setElementFont(@javax.annotation.Nullable FontConfig elementFont) {
    this.elementFont = elementFont;
  }


  public PageThemeStyling elementStyles(@javax.annotation.Nullable List<PageThemeElementStyleOverride> elementStyles) {
    this.elementStyles = elementStyles;
    return this;
  }

  public PageThemeStyling addElementStylesItem(PageThemeElementStyleOverride elementStylesItem) {
    if (this.elementStyles == null) {
      this.elementStyles = new ArrayList<>();
    }
    this.elementStyles.add(elementStylesItem);
    return this;
  }

  /**
   * The look of each element job (SURFACE, SELECTED, ACTION, QUIET). A job not listed follows the shape, the colours, or the look it always had.
   * @return elementStyles
   */
  @javax.annotation.Nullable
  public List<PageThemeElementStyleOverride> getElementStyles() {
    return elementStyles;
  }

  public void setElementStyles(@javax.annotation.Nullable List<PageThemeElementStyleOverride> elementStyles) {
    this.elementStyles = elementStyles;
  }


  public PageThemeStyling footerLogo(@javax.annotation.Nullable MediaSelection footerLogo) {
    this.footerLogo = footerLogo;
    return this;
  }

  /**
   * Get footerLogo
   * @return footerLogo
   */
  @javax.annotation.Nullable
  public MediaSelection getFooterLogo() {
    return footerLogo;
  }

  public void setFooterLogo(@javax.annotation.Nullable MediaSelection footerLogo) {
    this.footerLogo = footerLogo;
  }


  public PageThemeStyling footerLogoEnabled(@javax.annotation.Nullable Boolean footerLogoEnabled) {
    this.footerLogoEnabled = footerLogoEnabled;
    return this;
  }

  /**
   * Small company or brand logo centered at the bottom of every page using the theme.
   * @return footerLogoEnabled
   */
  @javax.annotation.Nullable
  public Boolean getFooterLogoEnabled() {
    return footerLogoEnabled;
  }

  public void setFooterLogoEnabled(@javax.annotation.Nullable Boolean footerLogoEnabled) {
    this.footerLogoEnabled = footerLogoEnabled;
  }


  public PageThemeStyling headingAlign(@javax.annotation.Nullable HeadingAlignEnum headingAlign) {
    this.headingAlign = headingAlign;
    return this;
  }

  /**
   * How titles and headline questions are aligned. Unset: centered titles, questions as the question style draws them.
   * @return headingAlign
   */
  @javax.annotation.Nullable
  public HeadingAlignEnum getHeadingAlign() {
    return headingAlign;
  }

  public void setHeadingAlign(@javax.annotation.Nullable HeadingAlignEnum headingAlign) {
    this.headingAlign = headingAlign;
  }


  public PageThemeStyling questionStyle(@javax.annotation.Nullable QuestionStyleEnum questionStyle) {
    this.questionStyle = questionStyle;
    return this;
  }

  /**
   * How a form&#39;s questions are written: LABEL (small, in the text style) or HEADLINE (centered, in the title style).
   * @return questionStyle
   */
  @javax.annotation.Nullable
  public QuestionStyleEnum getQuestionStyle() {
    return questionStyle;
  }

  public void setQuestionStyle(@javax.annotation.Nullable QuestionStyleEnum questionStyle) {
    this.questionStyle = questionStyle;
  }


  public PageThemeStyling shape(@javax.annotation.Nullable PageThemeShape shape) {
    this.shape = shape;
    return this;
  }

  /**
   * The radius and border every job follows unless it has its own.
   * @return shape
   */
  @javax.annotation.Nullable
  public PageThemeShape getShape() {
    return shape;
  }

  public void setShape(@javax.annotation.Nullable PageThemeShape shape) {
    this.shape = shape;
  }


  public PageThemeStyling smallText(@javax.annotation.Nullable PageThemeSmallText smallText) {
    this.smallText = smallText;
    return this;
  }

  /**
   * Small text: hints, counters, consent. Unset: the body text, smaller and muted.
   * @return smallText
   */
  @javax.annotation.Nullable
  public PageThemeSmallText getSmallText() {
    return smallText;
  }

  public void setSmallText(@javax.annotation.Nullable PageThemeSmallText smallText) {
    this.smallText = smallText;
  }


  public PageThemeStyling textColor(@javax.annotation.Nullable ThemeColor textColor) {
    this.textColor = textColor;
    return this;
  }

  /**
   * The text colour headings, body and small text follow when their own font has none.
   * @return textColor
   */
  @javax.annotation.Nullable
  public ThemeColor getTextColor() {
    return textColor;
  }

  public void setTextColor(@javax.annotation.Nullable ThemeColor textColor) {
    this.textColor = textColor;
  }


  public PageThemeStyling titleFont(@javax.annotation.Nullable FontConfig titleFont) {
    this.titleFont = titleFont;
    return this;
  }

  /**
   * Get titleFont
   * @return titleFont
   */
  @javax.annotation.Nullable
  public FontConfig getTitleFont() {
    return titleFont;
  }

  public void setTitleFont(@javax.annotation.Nullable FontConfig titleFont) {
    this.titleFont = titleFont;
  }


  public PageThemeStyling visitorExperience(@javax.annotation.Nullable VisitorExperienceEnum visitorExperience) {
    this.visitorExperience = visitorExperience;
    return this;
  }

  /**
   * How visitor pages pace their content: CLASSIC shows a whole form at once and a chat as a transcript, FOCUSED one question per screen and a chat as a short fading trail.
   * @return visitorExperience
   */
  @javax.annotation.Nullable
  public VisitorExperienceEnum getVisitorExperience() {
    return visitorExperience;
  }

  public void setVisitorExperience(@javax.annotation.Nullable VisitorExperienceEnum visitorExperience) {
    this.visitorExperience = visitorExperience;
  }



  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    PageThemeStyling pageThemeStyling = (PageThemeStyling) o;
    return Objects.equals(this.badge, pageThemeStyling.badge) &&
        Objects.equals(this.badgeEnabled, pageThemeStyling.badgeEnabled) &&
        Objects.equals(this.badgeIconColor, pageThemeStyling.badgeIconColor) &&
        Objects.equals(this.badgeShape, pageThemeStyling.badgeShape) &&
        Objects.equals(this.bodyAlign, pageThemeStyling.bodyAlign) &&
        Objects.equals(this.bodyFont, pageThemeStyling.bodyFont) &&
        Objects.equals(this.canvasColor, pageThemeStyling.canvasColor) &&
        Objects.equals(this.canvasCover, pageThemeStyling.canvasCover) &&
        Objects.equals(this.canvasCoverMode, pageThemeStyling.canvasCoverMode) &&
        Objects.equals(this.canvasTexture, pageThemeStyling.canvasTexture) &&
        Objects.equals(this.choiceLayout, pageThemeStyling.choiceLayout) &&
        Objects.equals(this.containerConfig, pageThemeStyling.containerConfig) &&
        Objects.equals(this.containerEnabled, pageThemeStyling.containerEnabled) &&
        Objects.equals(this.elementColor, pageThemeStyling.elementColor) &&
        Objects.equals(this.elementFont, pageThemeStyling.elementFont) &&
        Objects.equals(this.elementStyles, pageThemeStyling.elementStyles) &&
        Objects.equals(this.footerLogo, pageThemeStyling.footerLogo) &&
        Objects.equals(this.footerLogoEnabled, pageThemeStyling.footerLogoEnabled) &&
        Objects.equals(this.headingAlign, pageThemeStyling.headingAlign) &&
        Objects.equals(this.questionStyle, pageThemeStyling.questionStyle) &&
        Objects.equals(this.shape, pageThemeStyling.shape) &&
        Objects.equals(this.smallText, pageThemeStyling.smallText) &&
        Objects.equals(this.textColor, pageThemeStyling.textColor) &&
        Objects.equals(this.titleFont, pageThemeStyling.titleFont) &&
        Objects.equals(this.visitorExperience, pageThemeStyling.visitorExperience);
  }

  @Override
  public int hashCode() {
    return Objects.hash(badge, badgeEnabled, badgeIconColor, badgeShape, bodyAlign, bodyFont, canvasColor, canvasCover, canvasCoverMode, canvasTexture, choiceLayout, containerConfig, containerEnabled, elementColor, elementFont, elementStyles, footerLogo, footerLogoEnabled, headingAlign, questionStyle, shape, smallText, textColor, titleFont, visitorExperience);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class PageThemeStyling {\n");
    sb.append("    badge: ").append(toIndentedString(badge)).append("\n");
    sb.append("    badgeEnabled: ").append(toIndentedString(badgeEnabled)).append("\n");
    sb.append("    badgeIconColor: ").append(toIndentedString(badgeIconColor)).append("\n");
    sb.append("    badgeShape: ").append(toIndentedString(badgeShape)).append("\n");
    sb.append("    bodyAlign: ").append(toIndentedString(bodyAlign)).append("\n");
    sb.append("    bodyFont: ").append(toIndentedString(bodyFont)).append("\n");
    sb.append("    canvasColor: ").append(toIndentedString(canvasColor)).append("\n");
    sb.append("    canvasCover: ").append(toIndentedString(canvasCover)).append("\n");
    sb.append("    canvasCoverMode: ").append(toIndentedString(canvasCoverMode)).append("\n");
    sb.append("    canvasTexture: ").append(toIndentedString(canvasTexture)).append("\n");
    sb.append("    choiceLayout: ").append(toIndentedString(choiceLayout)).append("\n");
    sb.append("    containerConfig: ").append(toIndentedString(containerConfig)).append("\n");
    sb.append("    containerEnabled: ").append(toIndentedString(containerEnabled)).append("\n");
    sb.append("    elementColor: ").append(toIndentedString(elementColor)).append("\n");
    sb.append("    elementFont: ").append(toIndentedString(elementFont)).append("\n");
    sb.append("    elementStyles: ").append(toIndentedString(elementStyles)).append("\n");
    sb.append("    footerLogo: ").append(toIndentedString(footerLogo)).append("\n");
    sb.append("    footerLogoEnabled: ").append(toIndentedString(footerLogoEnabled)).append("\n");
    sb.append("    headingAlign: ").append(toIndentedString(headingAlign)).append("\n");
    sb.append("    questionStyle: ").append(toIndentedString(questionStyle)).append("\n");
    sb.append("    shape: ").append(toIndentedString(shape)).append("\n");
    sb.append("    smallText: ").append(toIndentedString(smallText)).append("\n");
    sb.append("    textColor: ").append(toIndentedString(textColor)).append("\n");
    sb.append("    titleFont: ").append(toIndentedString(titleFont)).append("\n");
    sb.append("    visitorExperience: ").append(toIndentedString(visitorExperience)).append("\n");
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
    openapiFields = new HashSet<String>(Arrays.asList("badge", "badgeEnabled", "badgeIconColor", "badgeShape", "bodyAlign", "bodyFont", "canvasColor", "canvasCover", "canvasCoverMode", "canvasTexture", "choiceLayout", "containerConfig", "containerEnabled", "elementColor", "elementFont", "elementStyles", "footerLogo", "footerLogoEnabled", "headingAlign", "questionStyle", "shape", "smallText", "textColor", "titleFont", "visitorExperience"));

    // a set of required properties/fields (JSON key names)
    openapiRequiredFields = new HashSet<String>(0);
  }

  /**
   * Validates the JSON Element and throws an exception if issues found
   *
   * @param jsonElement JSON Element
   * @throws IOException if the JSON Element is invalid with respect to PageThemeStyling
   */
  public static void validateJsonElement(JsonElement jsonElement) throws IOException {
      if (jsonElement == null) {
        if (!PageThemeStyling.openapiRequiredFields.isEmpty()) { // has required fields but JSON element is null
          throw new IllegalArgumentException(String.format(java.util.Locale.ROOT, "The required field(s) %s in PageThemeStyling is not found in the empty JSON string", PageThemeStyling.openapiRequiredFields.toString()));
        }
      }

      Set<Map.Entry<String, JsonElement>> entries = jsonElement.getAsJsonObject().entrySet();
      // check to see if the JSON string contains additional fields
      for (Map.Entry<String, JsonElement> entry : entries) {
        if (!PageThemeStyling.openapiFields.contains(entry.getKey())) {
          throw new IllegalArgumentException(String.format(java.util.Locale.ROOT, "The field `%s` in the JSON string is not defined in the `PageThemeStyling` properties. JSON: %s", entry.getKey(), jsonElement.toString()));
        }
      }
        JsonObject jsonObj = jsonElement.getAsJsonObject();
      // validate the optional field `badge`
      if (jsonObj.get("badge") != null && !jsonObj.get("badge").isJsonNull()) {
        MediaSelection.validateJsonElement(jsonObj.get("badge"));
      }
      // validate the optional field `badgeIconColor`
      if (jsonObj.get("badgeIconColor") != null && !jsonObj.get("badgeIconColor").isJsonNull()) {
        ThemeColor.validateJsonElement(jsonObj.get("badgeIconColor"));
      }
      if ((jsonObj.get("badgeShape") != null && !jsonObj.get("badgeShape").isJsonNull()) && !jsonObj.get("badgeShape").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format(java.util.Locale.ROOT, "Expected the field `badgeShape` to be a primitive type in the JSON string but got `%s`", jsonObj.get("badgeShape").toString()));
      }
      // validate the optional field `badgeShape`
      if (jsonObj.get("badgeShape") != null && !jsonObj.get("badgeShape").isJsonNull()) {
        BadgeShapeEnum.validateJsonElement(jsonObj.get("badgeShape"));
      }
      if ((jsonObj.get("bodyAlign") != null && !jsonObj.get("bodyAlign").isJsonNull()) && !jsonObj.get("bodyAlign").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format(java.util.Locale.ROOT, "Expected the field `bodyAlign` to be a primitive type in the JSON string but got `%s`", jsonObj.get("bodyAlign").toString()));
      }
      // validate the optional field `bodyAlign`
      if (jsonObj.get("bodyAlign") != null && !jsonObj.get("bodyAlign").isJsonNull()) {
        BodyAlignEnum.validateJsonElement(jsonObj.get("bodyAlign"));
      }
      // validate the optional field `bodyFont`
      if (jsonObj.get("bodyFont") != null && !jsonObj.get("bodyFont").isJsonNull()) {
        FontConfig.validateJsonElement(jsonObj.get("bodyFont"));
      }
      // validate the optional field `canvasColor`
      if (jsonObj.get("canvasColor") != null && !jsonObj.get("canvasColor").isJsonNull()) {
        ThemeColor.validateJsonElement(jsonObj.get("canvasColor"));
      }
      // validate the optional field `canvasCover`
      if (jsonObj.get("canvasCover") != null && !jsonObj.get("canvasCover").isJsonNull()) {
        MediaSelection.validateJsonElement(jsonObj.get("canvasCover"));
      }
      if ((jsonObj.get("canvasCoverMode") != null && !jsonObj.get("canvasCoverMode").isJsonNull()) && !jsonObj.get("canvasCoverMode").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format(java.util.Locale.ROOT, "Expected the field `canvasCoverMode` to be a primitive type in the JSON string but got `%s`", jsonObj.get("canvasCoverMode").toString()));
      }
      // validate the optional field `canvasCoverMode`
      if (jsonObj.get("canvasCoverMode") != null && !jsonObj.get("canvasCoverMode").isJsonNull()) {
        CanvasCoverModeEnum.validateJsonElement(jsonObj.get("canvasCoverMode"));
      }
      if ((jsonObj.get("canvasTexture") != null && !jsonObj.get("canvasTexture").isJsonNull()) && !jsonObj.get("canvasTexture").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format(java.util.Locale.ROOT, "Expected the field `canvasTexture` to be a primitive type in the JSON string but got `%s`", jsonObj.get("canvasTexture").toString()));
      }
      // validate the optional field `canvasTexture`
      if (jsonObj.get("canvasTexture") != null && !jsonObj.get("canvasTexture").isJsonNull()) {
        CanvasTextureEnum.validateJsonElement(jsonObj.get("canvasTexture"));
      }
      if ((jsonObj.get("choiceLayout") != null && !jsonObj.get("choiceLayout").isJsonNull()) && !jsonObj.get("choiceLayout").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format(java.util.Locale.ROOT, "Expected the field `choiceLayout` to be a primitive type in the JSON string but got `%s`", jsonObj.get("choiceLayout").toString()));
      }
      // validate the optional field `choiceLayout`
      if (jsonObj.get("choiceLayout") != null && !jsonObj.get("choiceLayout").isJsonNull()) {
        ChoiceLayoutEnum.validateJsonElement(jsonObj.get("choiceLayout"));
      }
      // validate the optional field `containerConfig`
      if (jsonObj.get("containerConfig") != null && !jsonObj.get("containerConfig").isJsonNull()) {
        ContainerConfig.validateJsonElement(jsonObj.get("containerConfig"));
      }
      // validate the optional field `elementColor`
      if (jsonObj.get("elementColor") != null && !jsonObj.get("elementColor").isJsonNull()) {
        ThemeColor.validateJsonElement(jsonObj.get("elementColor"));
      }
      // validate the optional field `elementFont`
      if (jsonObj.get("elementFont") != null && !jsonObj.get("elementFont").isJsonNull()) {
        FontConfig.validateJsonElement(jsonObj.get("elementFont"));
      }
      if (jsonObj.get("elementStyles") != null && !jsonObj.get("elementStyles").isJsonNull()) {
        JsonArray jsonArrayelementStyles = jsonObj.getAsJsonArray("elementStyles");
        if (jsonArrayelementStyles != null) {
          // ensure the json data is an array
          if (!jsonObj.get("elementStyles").isJsonArray()) {
            throw new IllegalArgumentException(String.format(java.util.Locale.ROOT, "Expected the field `elementStyles` to be an array in the JSON string but got `%s`", jsonObj.get("elementStyles").toString()));
          }

          // validate the optional field `elementStyles` (array)
          for (int i = 0; i < jsonArrayelementStyles.size(); i++) {
            PageThemeElementStyleOverride.validateJsonElement(jsonArrayelementStyles.get(i));
          };
        }
      }
      // validate the optional field `footerLogo`
      if (jsonObj.get("footerLogo") != null && !jsonObj.get("footerLogo").isJsonNull()) {
        MediaSelection.validateJsonElement(jsonObj.get("footerLogo"));
      }
      if ((jsonObj.get("headingAlign") != null && !jsonObj.get("headingAlign").isJsonNull()) && !jsonObj.get("headingAlign").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format(java.util.Locale.ROOT, "Expected the field `headingAlign` to be a primitive type in the JSON string but got `%s`", jsonObj.get("headingAlign").toString()));
      }
      // validate the optional field `headingAlign`
      if (jsonObj.get("headingAlign") != null && !jsonObj.get("headingAlign").isJsonNull()) {
        HeadingAlignEnum.validateJsonElement(jsonObj.get("headingAlign"));
      }
      if ((jsonObj.get("questionStyle") != null && !jsonObj.get("questionStyle").isJsonNull()) && !jsonObj.get("questionStyle").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format(java.util.Locale.ROOT, "Expected the field `questionStyle` to be a primitive type in the JSON string but got `%s`", jsonObj.get("questionStyle").toString()));
      }
      // validate the optional field `questionStyle`
      if (jsonObj.get("questionStyle") != null && !jsonObj.get("questionStyle").isJsonNull()) {
        QuestionStyleEnum.validateJsonElement(jsonObj.get("questionStyle"));
      }
      // validate the optional field `shape`
      if (jsonObj.get("shape") != null && !jsonObj.get("shape").isJsonNull()) {
        PageThemeShape.validateJsonElement(jsonObj.get("shape"));
      }
      // validate the optional field `smallText`
      if (jsonObj.get("smallText") != null && !jsonObj.get("smallText").isJsonNull()) {
        PageThemeSmallText.validateJsonElement(jsonObj.get("smallText"));
      }
      // validate the optional field `textColor`
      if (jsonObj.get("textColor") != null && !jsonObj.get("textColor").isJsonNull()) {
        ThemeColor.validateJsonElement(jsonObj.get("textColor"));
      }
      // validate the optional field `titleFont`
      if (jsonObj.get("titleFont") != null && !jsonObj.get("titleFont").isJsonNull()) {
        FontConfig.validateJsonElement(jsonObj.get("titleFont"));
      }
      if ((jsonObj.get("visitorExperience") != null && !jsonObj.get("visitorExperience").isJsonNull()) && !jsonObj.get("visitorExperience").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format(java.util.Locale.ROOT, "Expected the field `visitorExperience` to be a primitive type in the JSON string but got `%s`", jsonObj.get("visitorExperience").toString()));
      }
      // validate the optional field `visitorExperience`
      if (jsonObj.get("visitorExperience") != null && !jsonObj.get("visitorExperience").isJsonNull()) {
        VisitorExperienceEnum.validateJsonElement(jsonObj.get("visitorExperience"));
      }
  }

  public static class CustomTypeAdapterFactory implements TypeAdapterFactory {
    @SuppressWarnings("unchecked")
    @Override
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
       if (!PageThemeStyling.class.isAssignableFrom(type.getRawType())) {
         return null; // this class only serializes 'PageThemeStyling' and its subtypes
       }
       final TypeAdapter<JsonElement> elementAdapter = gson.getAdapter(JsonElement.class);
       final TypeAdapter<PageThemeStyling> thisAdapter
                        = gson.getDelegateAdapter(this, TypeToken.get(PageThemeStyling.class));

       return (TypeAdapter<T>) new TypeAdapter<PageThemeStyling>() {
           @Override
           public void write(JsonWriter out, PageThemeStyling value) throws IOException {
             JsonObject obj = thisAdapter.toJsonTree(value).getAsJsonObject();
             elementAdapter.write(out, obj);
           }

           @Override
           public PageThemeStyling read(JsonReader in) throws IOException {
             JsonElement jsonElement = elementAdapter.read(in);
             validateJsonElement(jsonElement);
             return thisAdapter.fromJsonTree(jsonElement);
           }

       }.nullSafe();
    }
  }

  /**
   * Create an instance of PageThemeStyling given an JSON string
   *
   * @param jsonString JSON string
   * @return An instance of PageThemeStyling
   * @throws IOException if the JSON string is invalid with respect to PageThemeStyling
   */
  public static PageThemeStyling fromJson(String jsonString) throws IOException {
    return JSON.getGson().fromJson(jsonString, PageThemeStyling.class);
  }

  /**
   * Convert an instance of PageThemeStyling to an JSON string
   *
   * @return JSON string
   */
  public String toJson() {
    return JSON.getGson().toJson(this);
  }
}

