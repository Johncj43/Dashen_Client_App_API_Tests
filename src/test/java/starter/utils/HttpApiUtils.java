//package starter.utils;
//
//import io.restassured.http.ContentType;
//import io.restassured.http.Header;
//import io.restassured.http.Headers;
//import io.restassured.response.Response;
//import io.restassured.specification.RequestSpecification;
//import lombok.extern.slf4j.Slf4j;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Map;
//import java.util.function.Supplier;
//
//import static net.serenitybdd.rest.SerenityRest.given;
//import static starter.utils.TestGlobalVariables.ContextEnum.HTTP_RESPONSE;
//import static starter.utils.TestGlobalVariables.setContext;
//
//@Slf4j
//public class HttpApiUtils {
//
//    private static final String BASE_API_URL = EnvConfig.getBaseUrl();
//    private static final int MAX_RETRIES = 3;
//    private static final long RETRY_DELAY_MS = 5000;
//    private static final List<Integer> RETRY_STATUS_CODES = List.of(500, 502, 503);
//
//    private static String cachedDeviceUUID = null;
//
//    /**
//     * Generic HTTP request with built-in header creation and retry logic.
//     *
//     * @param method          HTTP verb (GET, POST, PUT, DELETE, PATCH)
//     * @param path            API endpoint path (relative to BASE_API_URL)
//     * @param token           Optional access token (null = no Authorization header)
//     * @param generateNewUUID If true, generates a new device UUID
//     * @param queryParams     Optional query parameters
//     * @param body            Optional request body
//     * @return Response
//     */
//    public static Response request(String method,
//                                   String path,
//                                   String token,
//                                   boolean generateNewUUID,
//                                   Map<String, Object> queryParams,
//                                   Object body) {
//
//        return executeWithRetry(() -> {
//            RequestSpecification requestSpec = baseRequest()
//                    .basePath(path)
//                    .headers(buildHeaders(token, generateNewUUID));
//
//            if (queryParams != null && !queryParams.isEmpty()) {
//                requestSpec.queryParams(queryParams);
//            }
//
//            if (body != null) {
//                requestSpec.body(body);
//            }
//
//            Response response = switch (method.toUpperCase()) {
//                case "GET" -> requestSpec.get();
//                case "POST" -> requestSpec.post();
//                case "PUT" -> requestSpec.put();
//                case "DELETE" -> requestSpec.delete();
//                case "PATCH" -> requestSpec.patch();
//                default -> throw new IllegalArgumentException("Unsupported HTTP method: " + method);
//            };
//
//            return response.then().log().all().extract().response();
//        });
//    }
//
//    /**
//     * HTTP request using minimal headers (Authorization only if token provided).
//     *
//     * @param method      HTTP method
//     * @param path        API endpoint path
//     * @param token       Optional access token
//     * @param queryParams Optional query parameters
//     * @param body        Optional request body
//     * @return Response
//     */
//    public static Response minimalHeadersRequest(String method,
//                                                 String path,
//                                                 String token,
//                                                 Map<String, Object> queryParams,
//                                                 Object body) {
//
//        return executeWithRetry(() -> {
//            RequestSpecification requestSpec = baseRequest()
//                    .basePath(path)
//                    .headers(buildMinimalHeaders(token));
//
//            if (queryParams != null && !queryParams.isEmpty()) {
//                requestSpec.queryParams(queryParams);
//            }
//
//            if (body != null) {
//                requestSpec.body(body);
//            }
//
//            Response response = switch (method.toUpperCase()) {
//                case "GET" -> requestSpec.get();
//                case "POST" -> requestSpec.post();
//                case "PUT" -> requestSpec.put();
//                case "DELETE" -> requestSpec.delete();
//                case "PATCH" -> requestSpec.patch();
//                default -> throw new IllegalArgumentException("Unsupported HTTP method: " + method);
//            };
//
//            return response.then().log().all().extract().response();
//        });
//    }
//
//    // ---------------- Retry Logic ---------------- //
//
//    private static Response executeWithRetry(Supplier<Response> requestSupplier) {
//        int attempts = 0;
//
//        while (attempts < MAX_RETRIES) {
//            Response response = requestSupplier.get();
//
//            if (!RETRY_STATUS_CODES.contains(response.getStatusCode())) {
//                setContext(HTTP_RESPONSE.name(), response);
//                return response;
//            }
//
//            log.warn("Request failed with status {}. Retrying {}/{}", response.getStatusCode(), attempts + 1, MAX_RETRIES);
//            sleepBeforeRetry();
//            attempts++;
//        }
//
//        throw new RuntimeException("Request failed after " + MAX_RETRIES + " attempts.");
//    }
//
//    private static void sleepBeforeRetry() {
//        try {
//            Thread.sleep(RETRY_DELAY_MS);
//        } catch (InterruptedException e) {
//            log.error("Retry delay interrupted", e);
//            Thread.currentThread().interrupt();
//        }
//    }
//
//    // ---------------- Base Request ---------------- //
//
//    private static RequestSpecification baseRequest() {
//        return given()
//                .contentType(ContentType.JSON)
//                .baseUri(BASE_API_URL)
//                .relaxedHTTPSValidation();
//    }
//
//    // ---------------- Header Builders ---------------- //
//
//    private static Headers buildHeaders(String token, boolean generateNewUUID) {
//        List<Header> headers = new ArrayList<>();
//        // If a new UUID is requested but we already have one cached → reuse cached
//        if (generateNewUUID) {
//            cachedDeviceUUID = HelperUtils.generateDeviceUUID();
//            log.info("Generated new device_uuid: {}", cachedDeviceUUID);
//        } else if (cachedDeviceUUID != null) {
//            log.info("Using cached device_uuid: {}", cachedDeviceUUID);
//        } else {
//            cachedDeviceUUID = EnvConfig.getDeviceUUID();
//            log.info("Using default device_uuid from EnvConfig: {}", cachedDeviceUUID);
//        }
//
//        if (token != null) {
//            headers.add(new Header("Authorization", "Bearer " + resolveToken(token)));
//        }
//
//        headers.add(new Header("platform", EnvConfig.getPlatformType()));
//        headers.add(new Header("app_version", EnvConfig.getAppVersion()));
//        headers.add(new Header("device_uuid", cachedDeviceUUID));
//        headers.add(new Header("installation_date", EnvConfig.getInstallationDate()));
//        headers.add(new Header("Content-Type", "application/json"));
//
//        return new Headers(headers);
//    }
//
//    private static Headers buildMinimalHeaders(String token) {
//        List<Header> headers = new ArrayList<>();
//        if (token != null) {
//            headers.add(new Header("Authorization", "Bearer " + resolveToken(token)));
//        }
//        return new Headers(headers);
//    }
//
//    // ---------------- Token Helper ---------------- //
//
//    private static String resolveToken(String accessToken) {
//        return accessToken != null
//                ? accessToken
//                : "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9"; // default fallback token
//    }
//}



package starter.utils;

import io.restassured.http.ContentType;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import java.io.File;

import static net.serenitybdd.rest.SerenityRest.given;
import static starter.utils.HelperUtils.convertObjectToJson;
import static starter.utils.TestGlobalVariables.ContextEnum.HTTP_RESPONSE;
import static starter.utils.TestGlobalVariables.setContext;

@Slf4j
public class HttpApiUtils {

    private static final String BASE_API_URL = "https://qaapp.dashenbanksc.com/v2.0/chatbirrapi/";
    private static final int MAX_RETRIES = 3;
    private static final long RETRY_DELAY_MS = 5000;
    private static final List<Integer> RETRY_STATUS_CODES = List.of(500, 502, 503);

    private static String cachedDeviceUUID;

    // -------------------- Public API -------------------- //
    public static Response requestWithCoresHeaders(String method,
                                                  String path,
                                                  String deviceUUID,
                                                  String installationDate,
                                                  Object body) {

        String uuidToUse = (deviceUUID != null) ? deviceUUID : HelperUtils.getDeviceUUID();
        String installationToUse = (installationDate != null)
                ? installationDate
                : EnvConfig.getInstallationDate();

        return sendWithRetry(() ->
                sendRequest(
                        method,
                        path,
                        buildCoreHeaders(uuidToUse, installationToUse),
                        null,      // No query params
                        body
                )
        );
    }


    public static Response requestWithStandardHeaderst(String method,
                                                      String token,
                                                      String path,
                                                      String deviceUUID,
                                                      String installationDate,
                                                      Object body,
                                                      String otpFor) {

        String uuidToUse = (deviceUUID != null) ? deviceUUID : HelperUtils.getDeviceUUID();
        String installationToUse = (installationDate != null)
                ? installationDate
                : EnvConfig.getInstallationDate();

        return sendWithRetry(() ->
                sendRequest(
                        method,
                        path,
                        buildStandardHeaderst(token, uuidToUse, installationToUse, otpFor),
                        null,        // queryParams removed
                        body
                )
        );
    }


    public static Response request(String method,
                                   String path,
                                   String token,
                                   String generateNewUUID,
                                   Map<String, Object> queryParams,
                                   Object body) {
        String uuidToUse=(generateNewUUID!=null)?generateNewUUID: HelperUtils.getDeviceUUID();
        return sendWithRetry(() -> sendRequest(method, path, buildHeaders(token, uuidToUse), queryParams, body));
    }


    public static Response minimalHeadersRequest(String method,
                                                 String path,
                                                 String token,
                                                 Map<String, Object> queryParams,
                                                 Object body) {
        return sendWithRetry(() -> sendRequest(method, path, buildMinimalHeaders(token), queryParams, body));
    }
    public static Response requestWithStandardHeaders(String method,
                                                      String token,
                                                      String path,
                                                      String deviceUUID,
                                                      String installationDate,
                                                      Map<String, String> pathParams,
                                                      Map<String, Object> queryParams,
                                                      Object body) {
        String uuidToUse = (deviceUUID != null) ? deviceUUID : HelperUtils.getDeviceUUID();
        String installationToUse = (installationDate != null)
                ? installationDate
                : EnvConfig.getInstallationDate();
        // Resolve placeholders in the path template
        String resolvedPath = path;
        if (pathParams != null) {
            for (Map.Entry<String, String> entry : pathParams.entrySet()) {
                resolvedPath = resolvedPath.replace("{" + entry.getKey() + "}", entry.getValue());
            }
        }

        String finalResolvedPath = resolvedPath;
        return sendWithRetry(() ->
                sendRequest(method, finalResolvedPath, buildStandardHeaders(token, uuidToUse, installationToUse), queryParams, body)
        );
    }
    public static Response requestWithStandardHeadersSimple(String method,
                                                            String token,
                                                            String path,
                                                            String deviceUUID,
                                                            String installationDate,
                                                            Object body) {

        String uuidToUse = (deviceUUID != null) ? deviceUUID : HelperUtils.getDeviceUUID();
        String installationToUse = (installationDate != null)
                ? installationDate
                : EnvConfig.getInstallationDate();

        return sendWithRetry(() ->
                sendRequest(
                        method,
                        path, // Path already resolved
                        buildStandardHeader(token, uuidToUse, installationToUse),
                        null,  // No query params used
                        body
                )
        );
    }

    public static Response requestWithStandardHeadersSimples(String method,
                                                            String token,
                                                            String path,
                                                            String deviceUUID,
                                                            String installationDate,
                                                            String xRequestId,   // <-- added
                                                            Object body) {

        String uuidToUse = (deviceUUID != null) ? deviceUUID : HelperUtils.getDeviceUUID();
        String installationToUse = (installationDate != null)
                ? installationDate
                : EnvConfig.getInstallationDate();

        return sendWithRetry(() ->
                sendRequest(
                        method,
                        path,
                        buildStandardHeaderss(token, uuidToUse, installationToUse, xRequestId),
                        null,
                        body
                )
        );
    }




    // -------------------- Core Send Logic -------------------- //

    private static Response sendRequest(String method,
                                        String path,
                                        Headers headers,
                                        Map<String, Object> queryParams,
                                        Object body) {

        RequestSpecification req = baseRequest()
                .basePath(path)
                .headers(headers);

        if (queryParams != null && !queryParams.isEmpty()) {
            req.queryParams(queryParams);
        }
        if (body != null) {
            req.body(body);
        }

        return executeMethod(req, method)
                .then()
                .log().all()
                .extract()
                .response();
    }
    public static Response reequest(String method,
                                    String path,
                                    Headers headers,
                                    Map<String, Object> queryParams,
                                    Object body,
                                    String installationdate) {

        // Ensure headers is not null
        Map<String, String> headerMap = new HashMap<>();

        if (headers != null) {
            for (Header h : headers) {
                headerMap.put(h.getName(), h.getValue());
            }
        }

        // Add installation-date header (if provided)
        if (installationdate != null && !installationdate.isEmpty()) {
            headerMap.put("installation-date", installationdate);
        }

        // Convert map back to RestAssured Headers list
        List<Header> headerList = new ArrayList<>();
        headerMap.forEach((k, v) -> headerList.add(new Header(k, v)));
        Headers updatedHeaders = new Headers(headerList);

        // Build request
        RequestSpecification req = baseRequest()
                .basePath(path)
                .headers(updatedHeaders);

        if (queryParams != null && !queryParams.isEmpty()) {
            req.queryParams(queryParams);
        }

        if (body != null) {
            req.body(body);
        }

        // Execute the request
        return executeMethod(req, method)
                .then()
                .log().all()
                .extract()
                .response();
    }



    private static Response executeMethod(RequestSpecification req, String method) {
        return switch (method.toUpperCase()) {
            case "GET" -> req.get();
            case "POST" -> req.post();
            case "PUT" -> req.put();
            case "DELETE" -> req.delete();
            case "PATCH" -> req.patch();
            default -> throw new IllegalArgumentException("Unsupported HTTP method: " + method);
        };
    }

// -------------------- file upload -------------------- //

    public static Response uploadFile(String path,
                                      String token,
                                      File file,
                                      String deviceUUID,
                                      String formFieldName,
                                      Map<String, Object> extraParams) {

        return sendWithRetry(() -> {
            RequestSpecification req = given()
                    .baseUri(BASE_API_URL)
                    .basePath(path)
                    .headers(buildHeaders(token, deviceUUID))
                    .relaxedHTTPSValidation()
                    .contentType(ContentType.MULTIPART);

            // Attach file
            req.multiPart(formFieldName, file);

            // Add extra form-data params
            if (extraParams != null && !extraParams.isEmpty()) {
                extraParams.forEach(req::multiPart);
            }

            Response response = req.post();
            return response.then().log().all().extract().response();
        });
    }
    public static Response MultiRequest(String method,
                                        String path,
                                        String token,
                                        File file,
                                        String deviceUUID,
                                        String formFieldName,
                                        Map<String, String> pathParams,
                                        Map<String, Object> extraParams) {

        // Resolve placeholders in the path template
        String resolvedPath = path;
        if (pathParams != null) {
            for (Map.Entry<String, String> entry : pathParams.entrySet()) {
                resolvedPath = resolvedPath.replace("{" + entry.getKey() + "}", entry.getValue());
            }
        }
        String finalResolvedPath = resolvedPath;

        return sendWithRetry(() -> {
            RequestSpecification req = given()
                    .baseUri(BASE_API_URL)
                    .basePath(finalResolvedPath)
                    .headers(buildHeaders(token, deviceUUID))
                    .relaxedHTTPSValidation()
                    .contentType(ContentType.MULTIPART);

            // file part
            req.multiPart(formFieldName, file);

            // extra form-data fields
            if (extraParams != null && !extraParams.isEmpty()) {
                extraParams.forEach(req::multiPart);
            }

            Response response = switch (method.toUpperCase()) {
                case "POST" -> req.post();
                case "PUT" -> req.put();
                case "PATCH" -> req.patch();
                default -> throw new IllegalArgumentException("Unsupported multipart method: " + method);
            };

            return response.then().log().all().extract().response();
        });
    }


    // -------------------- Retry Wrapper -------------------- //

    static Response sendWithRetry(Supplier<Response> supplier) {
        for (int attempt = 1; attempt <= MAX_RETRIES; attempt++) {
            Response res = supplier.get();
            if (!RETRY_STATUS_CODES.contains(res.getStatusCode())) {
                setContext(HTTP_RESPONSE.name(), res);
                return res;
            }
            log.warn("Request failed with status {}. Retrying {}/{}...",
                    res.getStatusCode(), attempt, MAX_RETRIES);
            sleep(RETRY_DELAY_MS);
        }
        throw new RuntimeException("Request failed after " + MAX_RETRIES + " attempts.");
    }

    private static void sleep(long ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            log.error("Retry delay interrupted", e);
            Thread.currentThread().interrupt();
        }
    }

    // -------------------- Base Config -------------------- //

    private static RequestSpecification baseRequest() {
        return given()
                .contentType(ContentType.JSON)
                .baseUri(BASE_API_URL)
                .relaxedHTTPSValidation();
    }

    // -------------------- Headers -------------------- //
    private static Headers buildStandardHeaderss(String token,
                                               String deviceUUID,
                                               String installationDate,
                                               String xRequestId) {   // <-- added

        List<Header> headers = new ArrayList<>();

        if (token != null) {
            headers.add(new Header("Authorization", "Bearer " + resolveToken(token)));
        }

        headers.add(new Header("platform", "ios"));
        headers.add(new Header("appversion", "1.0.2"));
        headers.add(new Header("deviceuuid", deviceUUID));
        headers.add(new Header("installationdate", installationDate));
        headers.add(new Header("sourceapp", "memberapp"));
        headers.add(new Header("Content-Type", "application/json"));
        headers.add(new Header("otpfor","pinreset"));

        // ✅ Add x-request-id from method parameter (you get it from previous response)
        if (xRequestId != null) {
            headers.add(new Header("x-request-id", xRequestId));
        }

        return new Headers(headers);
    }


    private static Headers buildHeaders(String token, String deviceUUID) {
        // Simply use the provided deviceUUID without generating it here
        List<Header> headers = new ArrayList<>();

        if (token != null) {
            headers.add(new Header("Authorization", "Bearer " + resolveToken(token)));
        }

        headers.add(new Header("platform", EnvConfig.getPlatformType()));
        headers.add(new Header("app_version", EnvConfig.getAppVersion()));
        headers.add(new Header("device_uuid", deviceUUID));
        headers.add(new Header("installation_date", EnvConfig.getInstallationDate()));
        headers.add(new Header("Content-Type", "application/json"));

        return new Headers(headers);
    }
    private static Headers buildCoreHeaders(String deviceUUID,
                                            String installationDate) {

        List<Header> headers = new ArrayList<>();

        headers.add(new Header("platform","ios"));
        headers.add(new Header("appversion", "1.0.2"));
        headers.add(new Header("deviceuuid", deviceUUID));
        headers.add(new Header("installationdate", installationDate));
        headers.add(new Header("sourceapp", "memberapp"));
        headers.add(new Header("Content-Type", "application/json"));

        return new Headers(headers);
    }

    private static Headers buildStandardHeader(String token, String deviceUUID, String installationDate) {

        List<Header> headers = new ArrayList<>();

        if (token != null) {
            headers.add(new Header("Authorization", "Bearer " + resolveToken(token)));
        }

        headers.add(new Header("platform", "ios"));
        headers.add(new Header("appversion", "1.0.2"));
        headers.add(new Header("deviceuuid", deviceUUID));
        headers.add(new Header("installationdate", installationDate));
        headers.add(new Header("sourceapp", "memberapp"));
        headers.add(new Header("otpfor","pinreset"));
        headers.add(new Header("Content-Type", "application/json"));

        return new Headers(headers);
    }


    private static Headers buildHeaders(String token, boolean generateNewUUID) {
        if (generateNewUUID) {
            cachedDeviceUUID = HelperUtils.generateDeviceUUID();
            log.info("Generated new device_uuid: {}", cachedDeviceUUID);
        } else if (cachedDeviceUUID == null) {
            cachedDeviceUUID = EnvConfig.getDeviceUUID();
            log.info("Using default device_uuid from EnvConfig: {}", cachedDeviceUUID);
        } else {
            log.info("Using cached device_uuid: {}", cachedDeviceUUID);
        }


        List<Header> headers = new ArrayList<>();
        if (token != null) {
            headers.add(new Header("Authorization", "Bearer " + resolveToken(token)));
        }
        headers.add(new Header("platform", EnvConfig.getPlatformType()));
        headers.add(new Header("appversion", EnvConfig.getAppVersion()));
        headers.add(new Header("deviceuuid", cachedDeviceUUID));
        headers.add(new Header("installationdate", EnvConfig.getInstallationDate()));
        headers.add(new Header("Content-Type", "application/json"));
        return new Headers(headers);
    }
    private static Headers buildStandardHeaders(String token, String deviceUUID, String installationDate) {
        // Simply use the provided deviceUUID without generating it here
        List<Header> headers = new ArrayList<>();

        if (token != null) {
            headers.add(new Header("Authorization", "Bearer " + resolveToken(token)));
        }

        headers.add(new Header("platform", EnvConfig.getPlatformType()));
        headers.add(new Header("appversion", EnvConfig.getAppVersion()));
        headers.add(new Header("deviceuuid", deviceUUID));
        headers.add(new Header("installationdate", installationDate));
        headers.add(new Header("sourceapp",EnvConfig.getSourceApp()));
        headers.add(new Header("otpfor",EnvConfig.getOtpFor()));
        headers.add(new Header("Content-Type", "application/json"));


        return new Headers(headers);
    }
    private static Headers buildStandardHeaderst(String token,
                                                String deviceUUID,
                                                String installationDate,
                                                String otpFor) {

        List<Header> headers = new ArrayList<>();

        if (token != null) {
            headers.add(new Header("Authorization", "Bearer " + resolveToken(token)));
        }

        headers.add(new Header("platform", EnvConfig.getPlatformType()));
        headers.add(new Header("appversion", EnvConfig.getAppVersion()));
        headers.add(new Header("deviceuuid", deviceUUID));
        headers.add(new Header("installationdate", installationDate));
        headers.add(new Header("sourceapp", EnvConfig.getSourceApp()));
        headers.add(new Header("otpfor", otpFor));

        headers.add(new Header("Content-Type", "application/json"));

        return new Headers(headers);
    }
    private static Headers buildCoreHeaders(String deviceUUID,
                                            String installationDate,
                                            File file) { // Added file param to decide Content-Type

        List<Header> headers = new ArrayList<>();

        headers.add(new Header("platform", "ios"));
        headers.add(new Header("appversion", "1.0.2"));
        headers.add(new Header("deviceuuid", deviceUUID));
        headers.add(new Header("installationdate", installationDate));
        headers.add(new Header("sourceapp", "memberapp"));

        // Content-Type depends on whether file is included
        headers.add(new Header("Content-Type", file != null ? "multipart/form-data" : "application/json"));

        return new Headers(headers);
    }

    private static Headers buildMinimalHeaders(String token) {
        List<Header> headers = new ArrayList<>();
        if (token != null) {
            headers.add(new Header("Authorization", "Bearer " + resolveToken(token)));
        }
        return new Headers(headers);
    }

    // -------------------- Token -------------------- //

    private static String resolveToken(String token) {
        return token != null ? token : "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9"; // default fallback token
    }


    // ---------------------for path variables --------------
    /**
     * Send a request with path parameters.
     * Example:
     *   pathTemplate = "fayda/add_account/{id}/{user_code}"
     *   pathParams = Map.of("id", "12345", "user_code", "u001")
     *
     * Will resolve into: "fayda/add_account/12345/u001"
     */
    public static Response requestWithPathParams(String method,
                                                 String token,
                                                 String path,
                                                 Map<String, String> pathParams,
                                                 Map<String, Object> queryParams,
                                                 Object body) {
        // Resolve placeholders in the path template
        String resolvedPath = path;
        if (pathParams != null) {
            for (Map.Entry<String, String> entry : pathParams.entrySet()) {
                resolvedPath = resolvedPath.replace("{" + entry.getKey() + "}", entry.getValue());
            }
        }

        String finalResolvedPath = resolvedPath;
        return sendWithRetry(() ->
                sendRequest(method, finalResolvedPath, buildMinimalHeaders(token), queryParams, body)
        );
    }
    public static Response requestMethod(String method,
                                         String path,
                                         String token,
                                         String generateNewUUID,
                                         Map<String, Object> queryParams,
                                         Object body) {

        String uuidToUse = (generateNewUUID != null) ? generateNewUUID : HelperUtils.getDeviceUUID();

        return sendWithRetry(() -> {

            RequestSpecification req = given()
                    .baseUri(BASE_API_URL)
                    .basePath(path)
                    .headers(buildHeaders(token, uuidToUse))
                    .relaxedHTTPSValidation();

            // Detect file upload
            if (body instanceof File) {
                req.contentType("multipart/form-data");
                req.multiPart("profile_picture", (File) body);
            } else {
                req.contentType(ContentType.JSON);
                if (body != null) {
                    req.body(body);
                }
            }

            if (queryParams != null) {
                req.queryParams(queryParams);
            }

            Response response = switch (method.toUpperCase()) {
                case "GET" -> req.get();
                case "POST" -> req.post();
                case "PATCH" -> req.patch();
                case "PUT" -> req.put();
                case "DELETE" -> req.delete();
                default -> throw new IllegalArgumentException("Unsupported HTTP method: " + method);
            };

            return response.then().log().all().extract().response();
        });
    }

}
