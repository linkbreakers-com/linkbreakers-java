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
import com.linkbreakers.model.AttributeCapture;
import com.linkbreakers.model.BackButton;
import com.linkbreakers.model.CanvasPosition;
import com.linkbreakers.model.WorkflowStepPayload;
import com.linkbreakers.model.WorkflowStepsDocumentMedia;
import com.linkbreakers.model.WorkflowStepsDocumentStepMedia;
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
 * One step of a document.
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", comments = "Generator version: 7.21.0")
public class WorkflowStepsDocumentStep {
  public static final String SERIALIZED_NAME_AGENT_NAME = "agentName";
  @SerializedName(SERIALIZED_NAME_AGENT_NAME)
  @javax.annotation.Nullable
  private String agentName;

  public static final String SERIALIZED_NAME_ATTRIBUTE_CAPTURE = "attributeCapture";
  @SerializedName(SERIALIZED_NAME_ATTRIBUTE_CAPTURE)
  @javax.annotation.Nullable
  private AttributeCapture attributeCapture;

  public static final String SERIALIZED_NAME_BACK_BUTTON = "backButton";
  @SerializedName(SERIALIZED_NAME_BACK_BUTTON)
  @javax.annotation.Nullable
  private BackButton backButton;

  /**
   * - EVENT_ACTION_UNSPECIFIED: Event action not specified  - EVENT_ACTION_CLIENT_REDIRECT: Redirect visitors to a destination URL  - EVENT_ACTION_LINK_VISIT: Entry step triggered when the link is visited.  - EVENT_ACTION_PASSWORD_VERIFY: Require visitors to enter a password before proceeding  - EVENT_ACTION_FORM_SUBMIT: Present a data collection form to visitors  - EVENT_ACTION_CONTACT_CARD: Generate a vCard (VCF) response with contact information  - EVENT_ACTION_MULTI_LINK: A page of buttons where each one continues into more workflow: the button leads to a next step, not straight out. Records which button the visitor pressed, so use it over SOCIAL_LINKS_PAGE when that choice is worth measuring or when a branch needs to do more than redirect. This is the menu step: for several documents or PDFs, give each one a button connected to an OPEN_MEDIA step for media uploaded to the media library, or to a CLIENT_REDIRECT when the document is already hosted at a URL.  - EVENT_ACTION_VISIT_TYPE_CONDITION: Route visitors based on whether it&#39;s a first or returning visit  - EVENT_ACTION_COUNTRY_CONDITION: Route visitors based on their country location  - EVENT_ACTION_DAY_OF_WEEK_CONDITION: Route visitors based on day of week  - EVENT_ACTION_TIME_OF_DAY_CONDITION: Route visitors based on time of day  - EVENT_ACTION_DAY_OF_MONTH_CONDITION: Route visitors based on day of month  - EVENT_ACTION_SPECIFIC_DATE_CONDITION: Route visitors based on specific calendar dates  - EVENT_ACTION_SOCIAL_LINKS_PAGE: A page of buttons that each leave for an external URL. Social profiles are the common case, not the only one: app stores, docs, booking pages and storefronts all work, and the name is the only thing about it that is social. It is an exit step, so the visitor leaves from here.  - EVENT_ACTION_DEVICE_TYPE_CONDITION: Route visitors based on device type (e.g. MOBILE, DESKTOP, TABLET)  - EVENT_ACTION_DEVICE_PLATFORM_CONDITION: Route visitors based on device platform / operating system (e.g. iOS, Android, macOS)  - EVENT_ACTION_DEVICE_BROWSER_CONDITION: Route visitors based on their browser: a browser family (Chrome, Safari, Firefox...) or the in-app browser of the app a link was opened in (Instagram, TikTok, Facebook, LinkedIn...)  - EVENT_ACTION_DEVICE_BRAND_CONDITION: Route visitors based on hardware brand (e.g. Apple, Samsung, Google)  - EVENT_ACTION_DEVICE_ACTOR_CONDITION: Route visitors based on actor classification: HUMAN, BOT, AGENT, AUTOMATED or UNKNOWN. AGENT is an AI agent that names itself (GPTBot, Claude-User, Perplexity-User...). AUTOMATED is a browser driven by software, whoever is behind it: headless Chrome from its request, and a visible browser under automation once a link with the Advanced data collection level has read navigator.webdriver, from that device&#39;s next visit on.  - EVENT_ACTION_VISITOR_DATA_CONDITION: Route visitors based on their profile data or custom attributes  - EVENT_ACTION_THANK_YOU_PAGE: Display a final thank you / completion message (exit step)  - EVENT_ACTION_MESSAGE_PAGE: Text with ONE way onward: a single button, or a timer that moves the visitor automatically. It carries no list of destinations, so a page that should offer a choice between two or more places is not this step. Use SOCIAL_LINKS_PAGE when the choices leave for external URLs, or MULTI_LINK when each choice continues into more workflow. Never put links, documents or PDFs on it: it is a message, nothing else.  - EVENT_ACTION_OPEN_MEDIA: Hand the visitor one piece of uploaded media, opened in their browser (exit step): a PDF, an image (PNG, JPEG, WebP, SVG) or an MP4 video. The visitor is sent straight to the media, with no page of its own. Any media of the library can be opened: it is uploaded once and referenced by its mediaId; swapping it means pointing the step at another media item, and the QR code never changes.  - EVENT_ACTION_LOCATION_REQUEST: Ask the visitor for their precise location (GPS) through the browser, with their permission (composite: two branches, \&quot;location\&quot; when it was shared, \&quot;else\&quot; when it was refused, skipped or is unavailable).  - EVENT_ACTION_LANGUAGE_CONDITION: Route visitors based on their phone or browser language (the first language it sends, matched on its two-letter ISO 639-1 base, so \&quot;fr\&quot; catches fr-FR and fr-CA)  - EVENT_ACTION_CHAT_BOT: A conversation with one of the workspace&#39;s agents that collects visitor attributes (composite: two branches, \&quot;completed\&quot; when every required objective is known, \&quot;else\&quot; otherwise)  - EVENT_ACTION_TRAFFIC_SPLIT: Split visitors between 2 to 20 variants by percentage, for an A/B test (composite: one branch per variant, no \&quot;else\&quot;). Assignment is deterministic and sticky per device by default. See TrafficSplitPayload.
   */
  @JsonAdapter(EventActionEnum.Adapter.class)
  public enum EventActionEnum {
    EVENT_ACTION_UNSPECIFIED("EVENT_ACTION_UNSPECIFIED"),
    
    EVENT_ACTION_CLIENT_REDIRECT("EVENT_ACTION_CLIENT_REDIRECT"),
    
    EVENT_ACTION_LINK_VISIT("EVENT_ACTION_LINK_VISIT"),
    
    EVENT_ACTION_PASSWORD_VERIFY("EVENT_ACTION_PASSWORD_VERIFY"),
    
    EVENT_ACTION_FORM_SUBMIT("EVENT_ACTION_FORM_SUBMIT"),
    
    EVENT_ACTION_CONTACT_CARD("EVENT_ACTION_CONTACT_CARD"),
    
    EVENT_ACTION_MULTI_LINK("EVENT_ACTION_MULTI_LINK"),
    
    EVENT_ACTION_VISIT_TYPE_CONDITION("EVENT_ACTION_VISIT_TYPE_CONDITION"),
    
    EVENT_ACTION_COUNTRY_CONDITION("EVENT_ACTION_COUNTRY_CONDITION"),
    
    EVENT_ACTION_DAY_OF_WEEK_CONDITION("EVENT_ACTION_DAY_OF_WEEK_CONDITION"),
    
    EVENT_ACTION_TIME_OF_DAY_CONDITION("EVENT_ACTION_TIME_OF_DAY_CONDITION"),
    
    EVENT_ACTION_DAY_OF_MONTH_CONDITION("EVENT_ACTION_DAY_OF_MONTH_CONDITION"),
    
    EVENT_ACTION_SPECIFIC_DATE_CONDITION("EVENT_ACTION_SPECIFIC_DATE_CONDITION"),
    
    EVENT_ACTION_SOCIAL_LINKS_PAGE("EVENT_ACTION_SOCIAL_LINKS_PAGE"),
    
    EVENT_ACTION_DEVICE_TYPE_CONDITION("EVENT_ACTION_DEVICE_TYPE_CONDITION"),
    
    EVENT_ACTION_DEVICE_PLATFORM_CONDITION("EVENT_ACTION_DEVICE_PLATFORM_CONDITION"),
    
    EVENT_ACTION_DEVICE_BROWSER_CONDITION("EVENT_ACTION_DEVICE_BROWSER_CONDITION"),
    
    EVENT_ACTION_DEVICE_BRAND_CONDITION("EVENT_ACTION_DEVICE_BRAND_CONDITION"),
    
    EVENT_ACTION_DEVICE_ACTOR_CONDITION("EVENT_ACTION_DEVICE_ACTOR_CONDITION"),
    
    EVENT_ACTION_VISITOR_DATA_CONDITION("EVENT_ACTION_VISITOR_DATA_CONDITION"),
    
    EVENT_ACTION_THANK_YOU_PAGE("EVENT_ACTION_THANK_YOU_PAGE"),
    
    EVENT_ACTION_MESSAGE_PAGE("EVENT_ACTION_MESSAGE_PAGE"),
    
    EVENT_ACTION_OPEN_MEDIA("EVENT_ACTION_OPEN_MEDIA"),
    
    EVENT_ACTION_LOCATION_REQUEST("EVENT_ACTION_LOCATION_REQUEST"),
    
    EVENT_ACTION_LANGUAGE_CONDITION("EVENT_ACTION_LANGUAGE_CONDITION"),
    
    EVENT_ACTION_CHAT_BOT("EVENT_ACTION_CHAT_BOT"),
    
    EVENT_ACTION_TRAFFIC_SPLIT("EVENT_ACTION_TRAFFIC_SPLIT");

    private String value;

    EventActionEnum(String value) {
      this.value = value;
    }

    public String getValue() {
      return value;
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }

    public static EventActionEnum fromValue(String value) {
      for (EventActionEnum b : EventActionEnum.values()) {
        if (b.value.equals(value)) {
          return b;
        }
      }
      throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }

    public static class Adapter extends TypeAdapter<EventActionEnum> {
      @Override
      public void write(final JsonWriter jsonWriter, final EventActionEnum enumeration) throws IOException {
        jsonWriter.value(enumeration.getValue());
      }

      @Override
      public EventActionEnum read(final JsonReader jsonReader) throws IOException {
        String value =  jsonReader.nextString();
        return EventActionEnum.fromValue(value);
      }
    }

    public static void validateJsonElement(JsonElement jsonElement) throws IOException {
      String value = jsonElement.getAsString();
      EventActionEnum.fromValue(value);
    }
  }

  public static final String SERIALIZED_NAME_EVENT_ACTION = "eventAction";
  @SerializedName(SERIALIZED_NAME_EVENT_ACTION)
  @javax.annotation.Nonnull
  private EventActionEnum eventAction;

  public static final String SERIALIZED_NAME_KEY = "key";
  @SerializedName(SERIALIZED_NAME_KEY)
  @javax.annotation.Nullable
  private String key;

  public static final String SERIALIZED_NAME_MEDIA = "media";
  @SerializedName(SERIALIZED_NAME_MEDIA)
  @javax.annotation.Nullable
  private WorkflowStepsDocumentMedia media;

  public static final String SERIALIZED_NAME_NAME = "name";
  @SerializedName(SERIALIZED_NAME_NAME)
  @javax.annotation.Nullable
  private String name;

  public static final String SERIALIZED_NAME_PAYLOAD = "payload";
  @SerializedName(SERIALIZED_NAME_PAYLOAD)
  @javax.annotation.Nullable
  private WorkflowStepPayload payload;

  public static final String SERIALIZED_NAME_POSITION = "position";
  @SerializedName(SERIALIZED_NAME_POSITION)
  @javax.annotation.Nullable
  private CanvasPosition position;

  public static final String SERIALIZED_NAME_STEP_MEDIA = "stepMedia";
  @SerializedName(SERIALIZED_NAME_STEP_MEDIA)
  @javax.annotation.Nullable
  private WorkflowStepsDocumentStepMedia stepMedia;

  public WorkflowStepsDocumentStep() {
  }

  public WorkflowStepsDocumentStep agentName(@javax.annotation.Nullable String agentName) {
    this.agentName = agentName;
    return this;
  }

  /**
   * The name of the agent a chat bot step talks through, in place of its id, which the payload leaves empty. Imported, the step talks through the workspace&#39;s agent of that name, or else its first agent.
   * @return agentName
   */
  @javax.annotation.Nullable
  public String getAgentName() {
    return agentName;
  }

  public void setAgentName(@javax.annotation.Nullable String agentName) {
    this.agentName = agentName;
  }


  public WorkflowStepsDocumentStep attributeCapture(@javax.annotation.Nullable AttributeCapture attributeCapture) {
    this.attributeCapture = attributeCapture;
    return this;
  }

  /**
   * What the step saves to the visitor&#39;s profile.
   * @return attributeCapture
   */
  @javax.annotation.Nullable
  public AttributeCapture getAttributeCapture() {
    return attributeCapture;
  }

  public void setAttributeCapture(@javax.annotation.Nullable AttributeCapture attributeCapture) {
    this.attributeCapture = attributeCapture;
  }


  public WorkflowStepsDocumentStep backButton(@javax.annotation.Nullable BackButton backButton) {
    this.backButton = backButton;
    return this;
  }

  /**
   * The page&#39;s back button.
   * @return backButton
   */
  @javax.annotation.Nullable
  public BackButton getBackButton() {
    return backButton;
  }

  public void setBackButton(@javax.annotation.Nullable BackButton backButton) {
    this.backButton = backButton;
  }


  public WorkflowStepsDocumentStep eventAction(@javax.annotation.Nonnull EventActionEnum eventAction) {
    this.eventAction = eventAction;
    return this;
  }

  /**
   * - EVENT_ACTION_UNSPECIFIED: Event action not specified  - EVENT_ACTION_CLIENT_REDIRECT: Redirect visitors to a destination URL  - EVENT_ACTION_LINK_VISIT: Entry step triggered when the link is visited.  - EVENT_ACTION_PASSWORD_VERIFY: Require visitors to enter a password before proceeding  - EVENT_ACTION_FORM_SUBMIT: Present a data collection form to visitors  - EVENT_ACTION_CONTACT_CARD: Generate a vCard (VCF) response with contact information  - EVENT_ACTION_MULTI_LINK: A page of buttons where each one continues into more workflow: the button leads to a next step, not straight out. Records which button the visitor pressed, so use it over SOCIAL_LINKS_PAGE when that choice is worth measuring or when a branch needs to do more than redirect. This is the menu step: for several documents or PDFs, give each one a button connected to an OPEN_MEDIA step for media uploaded to the media library, or to a CLIENT_REDIRECT when the document is already hosted at a URL.  - EVENT_ACTION_VISIT_TYPE_CONDITION: Route visitors based on whether it&#39;s a first or returning visit  - EVENT_ACTION_COUNTRY_CONDITION: Route visitors based on their country location  - EVENT_ACTION_DAY_OF_WEEK_CONDITION: Route visitors based on day of week  - EVENT_ACTION_TIME_OF_DAY_CONDITION: Route visitors based on time of day  - EVENT_ACTION_DAY_OF_MONTH_CONDITION: Route visitors based on day of month  - EVENT_ACTION_SPECIFIC_DATE_CONDITION: Route visitors based on specific calendar dates  - EVENT_ACTION_SOCIAL_LINKS_PAGE: A page of buttons that each leave for an external URL. Social profiles are the common case, not the only one: app stores, docs, booking pages and storefronts all work, and the name is the only thing about it that is social. It is an exit step, so the visitor leaves from here.  - EVENT_ACTION_DEVICE_TYPE_CONDITION: Route visitors based on device type (e.g. MOBILE, DESKTOP, TABLET)  - EVENT_ACTION_DEVICE_PLATFORM_CONDITION: Route visitors based on device platform / operating system (e.g. iOS, Android, macOS)  - EVENT_ACTION_DEVICE_BROWSER_CONDITION: Route visitors based on their browser: a browser family (Chrome, Safari, Firefox...) or the in-app browser of the app a link was opened in (Instagram, TikTok, Facebook, LinkedIn...)  - EVENT_ACTION_DEVICE_BRAND_CONDITION: Route visitors based on hardware brand (e.g. Apple, Samsung, Google)  - EVENT_ACTION_DEVICE_ACTOR_CONDITION: Route visitors based on actor classification: HUMAN, BOT, AGENT, AUTOMATED or UNKNOWN. AGENT is an AI agent that names itself (GPTBot, Claude-User, Perplexity-User...). AUTOMATED is a browser driven by software, whoever is behind it: headless Chrome from its request, and a visible browser under automation once a link with the Advanced data collection level has read navigator.webdriver, from that device&#39;s next visit on.  - EVENT_ACTION_VISITOR_DATA_CONDITION: Route visitors based on their profile data or custom attributes  - EVENT_ACTION_THANK_YOU_PAGE: Display a final thank you / completion message (exit step)  - EVENT_ACTION_MESSAGE_PAGE: Text with ONE way onward: a single button, or a timer that moves the visitor automatically. It carries no list of destinations, so a page that should offer a choice between two or more places is not this step. Use SOCIAL_LINKS_PAGE when the choices leave for external URLs, or MULTI_LINK when each choice continues into more workflow. Never put links, documents or PDFs on it: it is a message, nothing else.  - EVENT_ACTION_OPEN_MEDIA: Hand the visitor one piece of uploaded media, opened in their browser (exit step): a PDF, an image (PNG, JPEG, WebP, SVG) or an MP4 video. The visitor is sent straight to the media, with no page of its own. Any media of the library can be opened: it is uploaded once and referenced by its mediaId; swapping it means pointing the step at another media item, and the QR code never changes.  - EVENT_ACTION_LOCATION_REQUEST: Ask the visitor for their precise location (GPS) through the browser, with their permission (composite: two branches, \&quot;location\&quot; when it was shared, \&quot;else\&quot; when it was refused, skipped or is unavailable).  - EVENT_ACTION_LANGUAGE_CONDITION: Route visitors based on their phone or browser language (the first language it sends, matched on its two-letter ISO 639-1 base, so \&quot;fr\&quot; catches fr-FR and fr-CA)  - EVENT_ACTION_CHAT_BOT: A conversation with one of the workspace&#39;s agents that collects visitor attributes (composite: two branches, \&quot;completed\&quot; when every required objective is known, \&quot;else\&quot; otherwise)  - EVENT_ACTION_TRAFFIC_SPLIT: Split visitors between 2 to 20 variants by percentage, for an A/B test (composite: one branch per variant, no \&quot;else\&quot;). Assignment is deterministic and sticky per device by default. See TrafficSplitPayload.
   * @return eventAction
   */
  @javax.annotation.Nonnull
  public EventActionEnum getEventAction() {
    return eventAction;
  }

  public void setEventAction(@javax.annotation.Nonnull EventActionEnum eventAction) {
    this.eventAction = eventAction;
  }


  public WorkflowStepsDocumentStep key(@javax.annotation.Nullable String key) {
    this.key = key;
    return this;
  }

  /**
   * The step&#39;s name within the document, which edges use.
   * @return key
   */
  @javax.annotation.Nullable
  public String getKey() {
    return key;
  }

  public void setKey(@javax.annotation.Nullable String key) {
    this.key = key;
  }


  public WorkflowStepsDocumentStep media(@javax.annotation.Nullable WorkflowStepsDocumentMedia media) {
    this.media = media;
    return this;
  }

  /**
   * Get media
   * @return media
   */
  @javax.annotation.Nullable
  public WorkflowStepsDocumentMedia getMedia() {
    return media;
  }

  public void setMedia(@javax.annotation.Nullable WorkflowStepsDocumentMedia media) {
    this.media = media;
  }


  public WorkflowStepsDocumentStep name(@javax.annotation.Nullable String name) {
    this.name = name;
    return this;
  }

  /**
   * A readable name for the step, such as \&quot;Form: Join the list\&quot;. Ignored on import.
   * @return name
   */
  @javax.annotation.Nullable
  public String getName() {
    return name;
  }

  public void setName(@javax.annotation.Nullable String name) {
    this.name = name;
  }


  public WorkflowStepsDocumentStep payload(@javax.annotation.Nullable WorkflowStepPayload payload) {
    this.payload = payload;
    return this;
  }

  /**
   * Get payload
   * @return payload
   */
  @javax.annotation.Nullable
  public WorkflowStepPayload getPayload() {
    return payload;
  }

  public void setPayload(@javax.annotation.Nullable WorkflowStepPayload payload) {
    this.payload = payload;
  }


  public WorkflowStepsDocumentStep position(@javax.annotation.Nullable CanvasPosition position) {
    this.position = position;
    return this;
  }

  /**
   * Where the step sits relative to the top left corner of the copied group.
   * @return position
   */
  @javax.annotation.Nullable
  public CanvasPosition getPosition() {
    return position;
  }

  public void setPosition(@javax.annotation.Nullable CanvasPosition position) {
    this.position = position;
  }


  public WorkflowStepsDocumentStep stepMedia(@javax.annotation.Nullable WorkflowStepsDocumentStepMedia stepMedia) {
    this.stepMedia = stepMedia;
    return this;
  }

  /**
   * The page&#39;s own badge and cover images, as files in place of their media ids. Imported into another workspace, each is added to its media library.
   * @return stepMedia
   */
  @javax.annotation.Nullable
  public WorkflowStepsDocumentStepMedia getStepMedia() {
    return stepMedia;
  }

  public void setStepMedia(@javax.annotation.Nullable WorkflowStepsDocumentStepMedia stepMedia) {
    this.stepMedia = stepMedia;
  }



  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    WorkflowStepsDocumentStep workflowStepsDocumentStep = (WorkflowStepsDocumentStep) o;
    return Objects.equals(this.agentName, workflowStepsDocumentStep.agentName) &&
        Objects.equals(this.attributeCapture, workflowStepsDocumentStep.attributeCapture) &&
        Objects.equals(this.backButton, workflowStepsDocumentStep.backButton) &&
        Objects.equals(this.eventAction, workflowStepsDocumentStep.eventAction) &&
        Objects.equals(this.key, workflowStepsDocumentStep.key) &&
        Objects.equals(this.media, workflowStepsDocumentStep.media) &&
        Objects.equals(this.name, workflowStepsDocumentStep.name) &&
        Objects.equals(this.payload, workflowStepsDocumentStep.payload) &&
        Objects.equals(this.position, workflowStepsDocumentStep.position) &&
        Objects.equals(this.stepMedia, workflowStepsDocumentStep.stepMedia);
  }

  @Override
  public int hashCode() {
    return Objects.hash(agentName, attributeCapture, backButton, eventAction, key, media, name, payload, position, stepMedia);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class WorkflowStepsDocumentStep {\n");
    sb.append("    agentName: ").append(toIndentedString(agentName)).append("\n");
    sb.append("    attributeCapture: ").append(toIndentedString(attributeCapture)).append("\n");
    sb.append("    backButton: ").append(toIndentedString(backButton)).append("\n");
    sb.append("    eventAction: ").append(toIndentedString(eventAction)).append("\n");
    sb.append("    key: ").append(toIndentedString(key)).append("\n");
    sb.append("    media: ").append(toIndentedString(media)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    payload: ").append(toIndentedString(payload)).append("\n");
    sb.append("    position: ").append(toIndentedString(position)).append("\n");
    sb.append("    stepMedia: ").append(toIndentedString(stepMedia)).append("\n");
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
    openapiFields = new HashSet<String>(Arrays.asList("agentName", "attributeCapture", "backButton", "eventAction", "key", "media", "name", "payload", "position", "stepMedia"));

    // a set of required properties/fields (JSON key names)
    openapiRequiredFields = new HashSet<String>(Arrays.asList("eventAction"));
  }

  /**
   * Validates the JSON Element and throws an exception if issues found
   *
   * @param jsonElement JSON Element
   * @throws IOException if the JSON Element is invalid with respect to WorkflowStepsDocumentStep
   */
  public static void validateJsonElement(JsonElement jsonElement) throws IOException {
      if (jsonElement == null) {
        if (!WorkflowStepsDocumentStep.openapiRequiredFields.isEmpty()) { // has required fields but JSON element is null
          throw new IllegalArgumentException(String.format(java.util.Locale.ROOT, "The required field(s) %s in WorkflowStepsDocumentStep is not found in the empty JSON string", WorkflowStepsDocumentStep.openapiRequiredFields.toString()));
        }
      }

      Set<Map.Entry<String, JsonElement>> entries = jsonElement.getAsJsonObject().entrySet();
      // check to see if the JSON string contains additional fields
      for (Map.Entry<String, JsonElement> entry : entries) {
        if (!WorkflowStepsDocumentStep.openapiFields.contains(entry.getKey())) {
          throw new IllegalArgumentException(String.format(java.util.Locale.ROOT, "The field `%s` in the JSON string is not defined in the `WorkflowStepsDocumentStep` properties. JSON: %s", entry.getKey(), jsonElement.toString()));
        }
      }

      // check to make sure all required properties/fields are present in the JSON string
      for (String requiredField : WorkflowStepsDocumentStep.openapiRequiredFields) {
        if (jsonElement.getAsJsonObject().get(requiredField) == null) {
          throw new IllegalArgumentException(String.format(java.util.Locale.ROOT, "The required field `%s` is not found in the JSON string: %s", requiredField, jsonElement.toString()));
        }
      }
        JsonObject jsonObj = jsonElement.getAsJsonObject();
      if ((jsonObj.get("agentName") != null && !jsonObj.get("agentName").isJsonNull()) && !jsonObj.get("agentName").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format(java.util.Locale.ROOT, "Expected the field `agentName` to be a primitive type in the JSON string but got `%s`", jsonObj.get("agentName").toString()));
      }
      // validate the optional field `attributeCapture`
      if (jsonObj.get("attributeCapture") != null && !jsonObj.get("attributeCapture").isJsonNull()) {
        AttributeCapture.validateJsonElement(jsonObj.get("attributeCapture"));
      }
      // validate the optional field `backButton`
      if (jsonObj.get("backButton") != null && !jsonObj.get("backButton").isJsonNull()) {
        BackButton.validateJsonElement(jsonObj.get("backButton"));
      }
      if (!jsonObj.get("eventAction").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format(java.util.Locale.ROOT, "Expected the field `eventAction` to be a primitive type in the JSON string but got `%s`", jsonObj.get("eventAction").toString()));
      }
      // validate the required field `eventAction`
      EventActionEnum.validateJsonElement(jsonObj.get("eventAction"));
      if ((jsonObj.get("key") != null && !jsonObj.get("key").isJsonNull()) && !jsonObj.get("key").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format(java.util.Locale.ROOT, "Expected the field `key` to be a primitive type in the JSON string but got `%s`", jsonObj.get("key").toString()));
      }
      // validate the optional field `media`
      if (jsonObj.get("media") != null && !jsonObj.get("media").isJsonNull()) {
        WorkflowStepsDocumentMedia.validateJsonElement(jsonObj.get("media"));
      }
      if ((jsonObj.get("name") != null && !jsonObj.get("name").isJsonNull()) && !jsonObj.get("name").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format(java.util.Locale.ROOT, "Expected the field `name` to be a primitive type in the JSON string but got `%s`", jsonObj.get("name").toString()));
      }
      // validate the optional field `payload`
      if (jsonObj.get("payload") != null && !jsonObj.get("payload").isJsonNull()) {
        WorkflowStepPayload.validateJsonElement(jsonObj.get("payload"));
      }
      // validate the optional field `position`
      if (jsonObj.get("position") != null && !jsonObj.get("position").isJsonNull()) {
        CanvasPosition.validateJsonElement(jsonObj.get("position"));
      }
      // validate the optional field `stepMedia`
      if (jsonObj.get("stepMedia") != null && !jsonObj.get("stepMedia").isJsonNull()) {
        WorkflowStepsDocumentStepMedia.validateJsonElement(jsonObj.get("stepMedia"));
      }
  }

  public static class CustomTypeAdapterFactory implements TypeAdapterFactory {
    @SuppressWarnings("unchecked")
    @Override
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
       if (!WorkflowStepsDocumentStep.class.isAssignableFrom(type.getRawType())) {
         return null; // this class only serializes 'WorkflowStepsDocumentStep' and its subtypes
       }
       final TypeAdapter<JsonElement> elementAdapter = gson.getAdapter(JsonElement.class);
       final TypeAdapter<WorkflowStepsDocumentStep> thisAdapter
                        = gson.getDelegateAdapter(this, TypeToken.get(WorkflowStepsDocumentStep.class));

       return (TypeAdapter<T>) new TypeAdapter<WorkflowStepsDocumentStep>() {
           @Override
           public void write(JsonWriter out, WorkflowStepsDocumentStep value) throws IOException {
             JsonObject obj = thisAdapter.toJsonTree(value).getAsJsonObject();
             elementAdapter.write(out, obj);
           }

           @Override
           public WorkflowStepsDocumentStep read(JsonReader in) throws IOException {
             JsonElement jsonElement = elementAdapter.read(in);
             validateJsonElement(jsonElement);
             return thisAdapter.fromJsonTree(jsonElement);
           }

       }.nullSafe();
    }
  }

  /**
   * Create an instance of WorkflowStepsDocumentStep given an JSON string
   *
   * @param jsonString JSON string
   * @return An instance of WorkflowStepsDocumentStep
   * @throws IOException if the JSON string is invalid with respect to WorkflowStepsDocumentStep
   */
  public static WorkflowStepsDocumentStep fromJson(String jsonString) throws IOException {
    return JSON.getGson().fromJson(jsonString, WorkflowStepsDocumentStep.class);
  }

  /**
   * Convert an instance of WorkflowStepsDocumentStep to an JSON string
   *
   * @return JSON string
   */
  public String toJson() {
    return JSON.getGson().toJson(this);
  }
}

