package starter.step_definitions.common;

import com.github.javafaker.Faker;
import io.cucumber.java.PendingException;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.restassured.response.Response;
import org.jetbrains.annotations.NotNull;
import starter.utils.HelperUtils;
import starter.utils.HttpApiUtils;
import starter.utils.TestDataLoader;
import starter.utils.model.requestModel.auth.HeaderData;
import starter.utils.model.requestModel.client.users.LoginData;

import java.util.Map;

import static org.junit.Assert.assertEquals;
import static starter.assertions.AssertionsAPI.*;
import static starter.utils.HelperUtils.*;
import static starter.utils.HttpApiUtils.request;
import static starter.utils.PropertiesReader.getParameterProperties;
import static starter.utils.TestGlobalVariables.ContextEnum.*;
import static starter.utils.TestGlobalVariables.getContext;
import static starter.utils.TestGlobalVariables.setContext;

public class CommonStepDefinitions {
//    private final AuthService authService = new AuthService();

    @Then("the response status code should be {int}")
    public void responseShouldHaveStatusCode(int code) {
        checkResponseStatusCode(code);
    }

    @And("the response {string} should include the role {string}")
    public void theResponseShouldIncludeTheRole(String path, String expected) {
        checkResponseContains(path, expected);
    }

    @And("the response {string} should contain {string}")
    public void theResponseShouldContain(String path, String expected) {
        checkResponseContains(path, expected);
    }

    @And("the response body should contain user not found")
    public void theResponseBodyShouldContainUserNotFound() {
        checkResponseContains("data.user_found", "false");
    }

    @And("I receive a response where {string} is {string}")
    public void iReceiveAResponseWithAs(String filed, String jsonfield) {
        checkResponseContains(filed, jsonfield);
    }

//    @And("I set my profile themex to {string}")
//    public void iSetMyProfileThemeTox(String profileTheme) {
//        String token = getContext(ACCESS_TOKEN.name());
//        String url = getParameterProperties("SET_PROFILE_THEME_URL");
//        ThemeType themeType = new ThemeType();
//        themeType.setTheme_type(profileTheme);
//        Response res = request("POST", url, token, null, null, convertObjectToJson(themeType));
//    }


    @And("the response should contain a field named {string} with the value {string}")
    public void responseShouldContainAFieldNamedWithValue(String jsonPath, String fieldValue) {
        if (fieldValue.equals("null")) {
            checkResponseContains(jsonPath, null);
        } else {
            String field = HelperUtils.resolvePath(fieldValue);
//            checkResponseContains(jsonPath, field);
            checkResponseContains(jsonPath, field.trim());
        }
    }


    @And("the response should contain a field named {string} with a non-empty value")
    public void theResponseShouldContainAFieldNamedWithANonEmptyValue(String jsonFieldName) {
        checkResponseContainsNonEmpty(jsonFieldName);
    }

    @And("the response should contain the message {string}")
    public void theResponseShouldContainTheMessage(String message) {
        Response response = getContext(HTTP_RESPONSE.name());
        String actualResponse = response.getBody().asString().replace("\"", "").trim();
        assertEquals("Response message does not match", actualResponse.trim(), message);
    }


//    @Given("the {string} user logs in and obtains an access token")
//    public void theUserLogsInAndObtainsAnAccessToken(String user) {
//        Response response = loginAndGetAccessToken(user);
//        String accessToken = extractJsonValue(response, "data.access_token");
//        setContext(ACCESS_TOKEN.name(), accessToken);
//        System.out.printf("%s logged in successfully, access_token stored", user);
//
//    }

//    public static Response loginAndGetAccessToken(String user) {
//        UserLoginData userLoginData = TestDataLoader.getUserLoginData(user);
//        String device_uuid = userLoginData.getDevice_uuid();
//
//        Response lookupResponse = HttpApiUtils.request(
//                "GET",
//                getParameterProperties("DEVICE_LOOKUP_URL"),
//                null,
//                device_uuid,
//                null,
//                null
//
//        );
//        // 2. save temp_token from lookup
//        String tempToken = extractJsonValue(lookupResponse, "data.temp_token");
//        setContext(TEMP_TOKEN.name(), tempToken);
//
//        // 3. login: Load phone + pin from TestDataLoader (per actor)
////        String phone = TestDataLoader.getLoginData(user).getPhone();
////        String pin = TestDataLoader.getLoginData(user).getPin();
//        String loginPin = userLoginData.getPin();
//
//        Login loginPayload = new Login();
////        loginPayload.setPhone(phone);
////        loginPayload.setPin(pin);
////        loginPayload.setPin("331000");
//        loginPayload.setPin(loginPin);
//        Response response = HttpApiUtils.request(
//                "POST",
//                getParameterProperties("LOGIN_URL"),
//                getContext(TEMP_TOKEN.name()),
//                device_uuid,
//                null,
//                convertObjectToJson(loginPayload)
//        );
//        return response;
//    }

//    public static Response loginAndGetAccessToken(String device_uuid, String pin) {
//
//        Response lookupResponse = HttpApiUtils.request(
//                "GET",
//                getParameterProperties("DEVICE_LOOKUP_URL"),
//                null,
//                device_uuid,
//                null,
//                null
//
//        );
//        // 2. save temp_token from lookup
//        String tempToken = extractJsonValue(lookupResponse, "data.temp_token");
//        setContext(TEMP_TOKEN.name(), tempToken);
//
//        Login loginPayload = new Login();
//        loginPayload.setPin(pin);
//        Response response = HttpApiUtils.request(
//                "POST",
//                getParameterProperties("LOGIN_URL"),
//                getContext(TEMP_TOKEN.name()),
//                device_uuid,
//                null,
//                convertObjectToJson(loginPayload)
//        );
//        return response;
//    }

//    @Given("the {string} user registers and obtains an access token")
//    public void theUserRegistersAndObtainsAnAccessToken(String user) {
//        // generate random device_uuid
//        String device_uuid = generateDeviceUUID();
//
//        // register user
//        Response registerResponse = deviceLookup(device_uuid);
//
//        // extract temp_token and otp
//        String tempToken = extractJsonValue(registerResponse, "data.temp_token");
//        String otp = extractJsonValue(registerResponse, "data.otp");
//
//        // verify otp
//        Response verifyOtpResponse = verifyOtp(otp, tempToken, device_uuid);
//
//        //extract verifyOtpTempToken
//        String verifyOtpTempToken = extractJsonValue(verifyOtpResponse, "data.temp_token");
//
//        // set pin
//        // load user from test data just for the pin-- we can use the same pin repeatedly
//        Login loginData = TestDataLoader.getLoginData(user);
//        Pin pin = new Pin();
////        pin.setNew_pin(loginData.getPin()); // pin is in the test data: We can also use autogenerated pin of six digits.
//        pin.setNew_pin(generateRandomPIN());
//        System.out.println("Generated PIN: " + pin.getNew_pin());
//        Response setPinResponse = request(
//                "POST",
//                getParameterProperties("SET_PIN_URL"),
//                verifyOtpTempToken,
//                device_uuid,
//                null,
//                convertObjectToJson(pin)
//        );
//
//        // extract access_token and save it in context
//        setContext(ACCESS_TOKEN.name(), extractJsonValue(setPinResponse, "data.access_token"));
////        setContext(HTTP_RESPONSE.name(), setPinResponse);
//
//    }
//
//    @NotNull
//    public static Response deviceLookup(String device_uuid) {
//        Faker faker=new Faker();
//
//        Register registerPayload = new Register();
//        registerPayload.setFull_name(faker.name().fullName());
//        registerPayload.setPhone(generateRandomPhoneNumber());
//        // send register request
//        Response registerResponse = HttpApiUtils.request(
//                "POST",
//                getParameterProperties("REGISTER_URL"),
//                null,
//                device_uuid,
//                null,
//                convertObjectToJson(registerPayload)
//        );
//        return registerResponse;
//    }
//
//    @NotNull
//    public static Response verifyOtp(String otp, String tempToken, String device_uuid) {
//        VerifyOTP body = new VerifyOTP();
//        body.setOtp(otp);
//        body.setOtp_for("REGISTRATION");
//
//        Response verifyOtpResponse = request(
//                "POST",
//                getParameterProperties("VERIFY_OTP_URL"),
//                tempToken,
//                device_uuid,
//                null,
//                convertObjectToJson(body)
//        );
//        return verifyOtpResponse;
//    }
//
//    @Given("a {string} user logs in and obtains an access token")
//    public void aUserLogsInAndObtainsAnAccessToken(String userRole) {
//        String accessToken = authService.userGetToken(userRole);
//        String key = "maker".equalsIgnoreCase(userRole)
//                ? MAKER_ACCESS_TOKEN.name()
//                : CHECKER_ACCESS_TOKEN.name();
//        setContext(key, accessToken);
//    }
@And("the response should contain named {string} with the value {string}")
public void theResponseShouldContainNamedWithTheValue(String filed, String jsonfield) {
    checkResponseContainsStringValue(filed, jsonfield);
}



    @Given("the {string} user logs in and obtains an access token")
    public void theUserLogsInAndObtainsAnAccessToken(String id) {
    LoginData user=TestDataLoader.getLoginData(id);
       String installationdate=user.getInstallationdate();
        String deviceuuid=user.getDeviceuuid();
        String pinCode=user.getPincode();
        Map<String,Object> body=Map.of("pincode",pinCode);
        Response response = HttpApiUtils.requestWithCoresHeaders(
                "POST",
                getParameterProperties("PIN_LOGIN_URL"),
                deviceuuid,
                installationdate,
                convertObjectToJson(body)

        );
        String accessToken=response.jsonPath().getString("data.accessToken");
        setContext(ACCESS_TOKEN.name(),accessToken);
        String session= response.jsonPath().getString("data.sessionID");
        setContext(SESSION_ID.name(),session);

    }


}


