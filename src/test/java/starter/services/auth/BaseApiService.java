package starter.services.auth;

import io.restassured.response.Response;

import java.io.File;
import java.util.Map;
import java.util.Random;

import static starter.utils.HttpApiUtils.*;
import static starter.utils.HelperUtils.convertObjectToJson;
import static starter.utils.TestGlobalVariables.ContextEnum.DEVICE_UUID;
import static starter.utils.TestGlobalVariables.getContext;
import static starter.utils.TestGlobalVariables.setContext;
import static starter.utils.TestGlobalVariables.ContextEnum.HTTP_RESPONSE;

public abstract class BaseApiService {

    protected Response get(String url, String token, String deviceUUID) {
        Response response = request("GET", url, token, deviceUUID, null, null);
        setContext(HTTP_RESPONSE.name(), response);
        return response;
    }
    protected Response get(String url, String token, String deviceUUID, Map<String, Object> queryParams, Object body ) {
        Response response = request("GET", url, token, deviceUUID, queryParams, body);
        setContext(HTTP_RESPONSE.name(), response);
        return response;
    }
    protected Response post(String url, Object body) {
        return post(url, null, body);
    }


    protected Response post(String url, String token, Object body) {
        String jsonBody = body != null ? convertObjectToJson(body) : null;
        Response response = request("POST", url, token, getContext(DEVICE_UUID.name()), null, jsonBody);
        setContext(HTTP_RESPONSE.name(), response);
        return response;
    }

    protected Response post(String url, String token, Object body, String deviceUUID) {
        String jsonBody = body != null ? convertObjectToJson(body) : null;
        Response response = request("POST", url, token, deviceUUID, null, jsonBody);
        setContext(HTTP_RESPONSE.name(), response);
        return response;
    }

    protected Response upload(String url, String token, java.io.File file, String fieldName) {
        Response response = uploadFile(url, token, file,getContext(DEVICE_UUID.name()), fieldName, null);
        setContext(HTTP_RESPONSE.name(), response);
        return response;
    }
    public static String getRandomImagePath(String directoryPath) {
        File directory = new File(directoryPath);
        File[] files = directory.listFiles((dir, name) -> name.endsWith(".png") || name.endsWith(".jpg") || name.endsWith(".jpeg"));
        if (files != null && files.length > 0) {
            return files[new Random().nextInt(files.length)].getAbsolutePath();
        }
        return null;
    }
}
