# Linkbreakers Java SDK

Official Java SDK for the Linkbreakers API, auto-generated from OpenAPI specification.

## Installation

### Maven

Add this dependency to your `pom.xml`:

```xml
<dependency>
    <groupId>com.linkbreakers</groupId>
    <artifactId>linkbreakers-sdk</artifactId>
    <version>1.32.0</version>
</dependency>
```

### Gradle

Add this to your `build.gradle`:

```gradle
implementation 'com.linkbreakers:linkbreakers-sdk:1.32.0'
```

## Requirements

- Java 11 or higher

## Usage

```java
import com.linkbreakers.*;
import com.linkbreakers.api.*;
import com.linkbreakers.model.*;

public class Example {
    public static void main(String[] args) {
        // Create API client
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        defaultClient.setBasePath("https://api.linkbreakers.com");

        // Configure Bearer token authentication
        HttpBearerAuth bearerAuth = (HttpBearerAuth) defaultClient.getAuthentication("bearerAuth");
        bearerAuth.setBearerToken("your-api-token");

        // Create API instance
        LinksApi apiInstance = new LinksApi(defaultClient);

        try {
            // Example: List links
            Integer pageSize = 10;
            LinksList200Response result = apiInstance.linksList(pageSize, null, null, null, null, null, null, null, null);
            System.out.println("Found " + result.getLinks().size() + " links");
        } catch (ApiException e) {
            System.err.println("Exception when calling LinksApi#linksList");
            System.err.println("Status code: " + e.getCode());
            System.err.println("Reason: " + e.getResponseBody());
            System.err.println("Response headers: " + e.getResponseHeaders());
            e.printStackTrace();
        }
    }
}
```

## Authentication

The SDK supports Bearer token authentication:

```java
ApiClient defaultClient = Configuration.getDefaultApiClient();
HttpBearerAuth bearerAuth = (HttpBearerAuth) defaultClient.getAuthentication("bearerAuth");
bearerAuth.setBearerToken("your-api-token");
```

## API Documentation

For detailed API documentation, visit: https://docs.linkbreakers.com

## Auto-Generated

This SDK is automatically generated from the Linkbreakers OpenAPI specification and published when the API is updated.

Current API version: See [OPENAPI_VERSION](./OPENAPI_VERSION)

## Issues

Report issues at: https://github.com/linkbreakers-com/linkbreakers-java/issues

## License

MIT License
