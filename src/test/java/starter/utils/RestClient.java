package starter.utils;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static net.serenitybdd.rest.SerenityRest.given;
import static starter.utils.HttpApiUtils.sendWithRetry;
import static starter.utils.TestGlobalVariables.ContextEnum.HTTP_RESPONSE;
import static starter.utils.TestGlobalVariables.setContext;

@Slf4j
public class RestClient {

    public static String BASE_API_URL = EnvConfig.getBaseUrl();

    // -------------------- Public API -------------------- //

    /** Use for first request (login/start) with full headers */
    public static Response minRequest(String method,
                                      String token,
                                      String path,
                                      Object body,
                                      Map<String, Object> queryParams
    ) {
        return sendRequest(method, path, buildFullHeaders(token), queryParams, body);
    }
    public static Response request(String method,
                                   String path,
                                   String token,
                                   String deviceUuid,
                                   File file,
                                   String multipartFieldName) {

        // Build headers
        Map<String, String> headers = buildHeader(token, deviceUuid);

        RequestSpecification requestSpec = RestAssured
                .given()
                .baseUri(BASE_API_URL)
                .headers(headers)
                .multiPart(multipartFieldName, file) // <-- EXACTLY like Postman
                .log().all(); // debug

        Response response;

        if ("POST".equalsIgnoreCase(method)) {
            response = requestSpec
                    .post(path)
                    .then()
                    .log().all()
                    .extract()
                    .response();
        } else {
            throw new IllegalArgumentException("Only POST is supported for multipart.");
        }

        return response;
    }



    /** Use for subsequent requests (only Authorization header) */
    public static Response  request(String method,
                                    String token,
                                    String path,
                                    Object body,
                                    Map<String, Object> queryParams) {
        return sendRequest(method, path, buildAuthOnlyHeaders(token), queryParams, body);
    }

    // -------------------- Core Request Handling -------------------- //

    private static Response sendRequest(String method,
                                        String path,
                                        Headers headers,
                                        Map<String, Object> queryParams,
                                        Object body) {

        RequestSpecification req = baseRequest()
                .basePath(path)
                .headers(headers);

        if (queryParams != null && !queryParams.isEmpty()) req.queryParams(queryParams);
        if (body != null) req.body(body);

        Response res = executeMethod(req, method)
                .then()
                .log().all()
                .extract()
                .response();

        // Save response in global context
        setContext(HTTP_RESPONSE.name(), res);
        return res;
    }

    private static RequestSpecification baseRequest() {
        return given()
                .contentType(ContentType.JSON)
                .baseUri(BASE_API_URL)
                .relaxedHTTPSValidation();
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

    // -------------------- Headers -------------------- //

    private static Headers buildFullHeaders(String token) {
        List<Header>  headers=new ArrayList<>();

        headers.add(new Header("device_uuid", "uuid-12345678"));
        headers.add(new Header("platform", "laptop"));
        headers.add(new Header("source_app", "cbeportal"));
        headers.add(new Header("Content-Type", "application/json"));
        return new Headers(headers);
    }
    private static Headers buildMinimalHeaders(String token) {
        return new Headers(
                new Header("Authorization", "Bearer " + resolveToken(token))
        );
    }
    public static Map<String, String> buildHeader(String token, String deviceUuid) {
        Map<String, String> headers = new HashMap<>();

        if (token != null) {
            headers.put("Authorization", "Bearer " + resolveToken(token));
        }

        headers.put("platform", EnvConfig.getPlatformType());
        headers.put("appversion", EnvConfig.getAppVersion());
        headers.put("deviceuuid", deviceUuid);
        headers.put("installationdate", EnvConfig.getInstallationDate());
//        headers.put("Content-Type", "application/json");

        return headers;
    }



    /** Auth only headers (subsequent requests) */
    private static Headers buildAuthOnlyHeaders(String token) {
        List<Header> headers = new ArrayList<>();
        addAuthHeader(headers, token);
        return new Headers(headers);
    }

    private static void addAuthHeader(List<Header> headers, String token) {
        if (token != null) {
            headers.add(new Header("Authorization", "Bearer " + resolveToken(token)));
        }
    }

    private static String resolveToken(String token) {
        return (token != null) ? token : "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9"; // fallback
    }
}
