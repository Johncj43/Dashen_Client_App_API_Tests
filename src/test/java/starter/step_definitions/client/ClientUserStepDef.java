package starter.step_definitions.client;

import io.cucumber.java.en.When;
import io.restassured.response.Response;
import starter.utils.HttpApiUtils;
import starter.utils.TestDataLoader;
import starter.utils.model.requestModel.client.users.LoginData;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import static starter.utils.HelperUtils.convertObjectToJson;
import static starter.utils.PropertiesReader.getParameterProperties;
import static starter.utils.TestGlobalVariables.ContextEnum.ACCESS_TOKEN;
import static starter.utils.TestGlobalVariables.getContext;

public class ClientUserStepDef {

    @When("I send a POST request to {string} with a {string} to upload the profile avatar")
    public void iSendAPOSTRequestToWithAToUploadTheProfileAvatar(String endpoint, String id) {
        Response response = performUploadRequest(endpoint, id);
    }

    @When("I send a POST request to {string} with a {string} to get an upload avatar")
    public void iSendAPOSTRequestToWithAToGetAnUploadAvatar(String endpoint, String id) {
        Response response = performStandardRequest("GET", endpoint, id, null, null, true);
    }

    @When("I send a POST request to {string} with a {string} to remove an upload avatar")
    public void iSendAPOSTRequestToWithAToRemoveAnUploadAvatar(String endpoint, String id) {
        Response response = performStandardRequest("DELETE", endpoint, id, null, null, true);
    }

    @When("I send a POST request to {string} with a {string} to remove an upload avatars")
    public void iSendAPOSTRequestToWithAToRemoveAnUploadAvatars(String endpoint, String id) {
        Response response = performStandardRequest("DELETE", endpoint, id, null, null, false);
    }

    @When("I send a POST request to {string} with {string} to fetch their own user details")
    public void iSendAPOSTRequestToWithToFetchTheirOwnUserDetails(String endpoint, String id) {
        Response response = performStandardRequest("GET", endpoint, id, null, null, true);
    }

    @When("I send a POST request to {string} with {string} to fetch their own user detail")
    public void iSendAPOSTRequestToWithToFetchTheirOwnUserDetail(String endpoint, String id) {
        Response response = performStandardRequest("GET", endpoint, id, null, null, false);
    }

    @When("I send a POST request to {string} with {string} to set primary notification through email")
    public void iSendAPOSTRequestToWithToSetPrimaryNotificationThroughEmail(String endpoint, String id) {
        LoginData data = TestDataLoader.getLoginData(id);
        String emailOrPhone = data.getEmailOrPhone();
        Map<String, Object> body = Map.of("emailOrPhone", emailOrPhone);
        Response response = performStandardRequest("POST", endpoint, id, convertObjectToJson(body), null, true);
    }

    @When("I send a POST request to {string} with {string} to set primary notification through emails")
    public void iSendAPOSTRequestToWithToSetPrimaryNotificationThroughEmails(String endpoint, String id) {
        LoginData data = TestDataLoader.getLoginData(id);
        String emailOrPhone = data.getEmailOrPhone();
        Map<String, Object> body = Map.of("emailOrPhone", emailOrPhone);
        Response response = performStandardRequest("POST", endpoint, id, convertObjectToJson(body), null, false);
    }

    @When("I send a POST request to {string} with {string} to get linked account details")
    public void iSendAPOSTRequestToWithToGetLinkedAccountDetails(String endpoint, String id) {
        Response response = performStandardRequest("GET", endpoint, id, null, null, true);
    }

    @When("I send a POST request to {string} with {string} to get linked account detail")
    public void iSendAPOSTRequestToWithToGetLinkedAccountDetail(String endpoint, String id) {
        Response response = performStandardRequest("GET", endpoint, id, null, null, false);
    }

    @When("I send a POST request to {string} with {string} to set a default account")
    public void iSendAPOSTRequestToWithToSetADefaultAccount(String endpoint, String id) {
        LoginData data = TestDataLoader.getLoginData(id);
        String account = data.getAccount_number();
        Map<String, Object> body = Map.of("account_number", account);
        Response response = performStandardRequest("POST", endpoint, id, convertObjectToJson(body), null, true);
    }

    @When("I send a POST request to {string} with {string} to set a default accounts")
    public void iSendAPOSTRequestToWithToSetADefaultAccounts(String endpoint, String id) {
        LoginData data = TestDataLoader.getLoginData(id);
        String account = data.getAccount_number();
        Map<String, Object> body = Map.of("account_number", account);
        Response response = performStandardRequest("POST", endpoint, id, convertObjectToJson(body), null, false);
    }

    @When("I send a POST request to {string} with {string} to add an email to the app")
    public void iSendAPOSTRequestToWithToAddAnEmailToTheApp(String endpoint, String id) {
        LoginData data = TestDataLoader.getLoginData(id);
        String email = data.getEmail();
        Map<String, Object> body = Map.of("email", email);
        Response response = performStandardRequest("POST", endpoint, id, convertObjectToJson(body), null, true);
    }

    @When("I send a POST request to {string} with {string} to add an email with an invalid access token")
    public void iSendAPOSTRequestToWithToAddAnEmailWithAnInvalidAccessToken(String endpoint, String id) {
        LoginData data = TestDataLoader.getLoginData(id);
        String email = data.getEmail();
        Map<String, Object> body = Map.of("email", email);
        Response response = performStandardRequest("POST", endpoint, id, convertObjectToJson(body), null, false);
    }

    @When("I send  {string} with a {string} to get encrypted account")
    public void iSendWithAToGetEncryptedAccount(String endpoint, String id) {
        LoginData data = TestDataLoader.getLoginData(id);
        String account = data.getAccount_number();
        Map<String, Object> queryParams = new HashMap<>();
        queryParams.put("account_number", account);
        Response response = performStandardRequest("GET", endpoint, id, null, queryParams, true);
    }

    @When("I send {string} with a {string} to get encrypted accounts")
    public void iSendWithAToGetEncryptedAccounts(String endpoint, String id) {
        LoginData data = TestDataLoader.getLoginData(id);
        String account = data.getAccount_number();
        Map<String, Object> queryParams = new HashMap<>();
        queryParams.put("account_number", account);
        Response response = performStandardRequest("GET", endpoint, id, null, queryParams, false);
    }

    private Response performStandardRequest(String method, String endpoint, String id, Object body, Map<String, Object> queryParams, boolean useGlobalToken) {
        LoginData user = TestDataLoader.getLoginData(id);
        String token = useGlobalToken ? getContext(ACCESS_TOKEN.name()) : user.getToken();
        return HttpApiUtils.requestWithStandardHeaders(
                method,
                token,
                getParameterProperties(endpoint),
                user.getDeviceuuid(),
                user.getInstallationdate(),
                (Map<String, String>) body,  // Cast to String if needed, assuming the method expects String
                queryParams,
                null
        );
    }


    private Response performUploadRequest(String endpoint, String id) {
        LoginData user = TestDataLoader.getLoginData(id);
        File profilepic = new File("src/test/resources/test_data/images/images.png");
        return HttpApiUtils.uploadMultipart(
                "POST",
                getParameterProperties(endpoint),
                getContext(ACCESS_TOKEN.name()),
                profilepic,
                user.getDeviceuuid(),
                user.getInstallationdate()
        );
    }
}
