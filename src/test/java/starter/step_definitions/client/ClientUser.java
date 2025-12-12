package starter.step_definitions.client;

import io.cucumber.java.PendingException;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.junit.Test;
import starter.utils.HttpApiUtils;
import starter.utils.TestDataLoader;
import starter.utils.model.requestModel.client.users.LoginData;

import java.io.File;
import java.util.Map;

import static starter.utils.HelperUtils.convertObjectToJson;
import static starter.utils.PropertiesReader.getParameterProperties;
import static starter.utils.TestGlobalVariables.ContextEnum.ACCESS_TOKEN;
import static starter.utils.TestGlobalVariables.ContextEnum.HTTP_RESPONSE;
import static starter.utils.TestGlobalVariables.getContext;


public class ClientUser {

    @When("I send a POST request to {string} with a {string} to upload the profile avatar")
    public void iSendAPOSTRequestToWithAToUploadTheProfileAvatar(String endpoint, String id) {
        LoginData user= TestDataLoader.getLoginData(id);
        String installationdate=user.getInstallationdate();
        String deviceuuid=user.getDeviceuuid();
        File profilepic=new File( "src/test/resources/test_data/images/download.png");
        Response response = HttpApiUtils.uploadMultipart(
                "POST",
                getParameterProperties(endpoint),
                getContext(ACCESS_TOKEN.name()),
                profilepic,
                deviceuuid,
                installationdate


        );
    }

    @When("I send a POST request to {string} with a {string} to get an upload avatar")
    public void iSendAPOSTRequestToWithAToGetAnUploadAvatar(String endpoint, String id) {
    LoginData user=TestDataLoader.getLoginData(id);
    String installationdate=user.getInstallationdate();
    String deviceuuid=user.getDeviceuuid();
    Response response = HttpApiUtils.requestWithStandardHeaderst(
            "GET",
            getContext(ACCESS_TOKEN.name()),
            getParameterProperties(endpoint),
            deviceuuid,
            installationdate,
            null,
            null


    );
    }

    @When("I send a POST request to {string} with a {string} to remove an upload avatar")
    public void iSendAPOSTRequestToWithAToRemoveAnUploadAvatar(String endpoint, String id) {
       LoginData user=TestDataLoader.getLoginData(id);
       String installationdate= user.getInstallationdate();
       String deviceuuid=user.getDeviceuuid();
        Response response = HttpApiUtils.requestWithStandardHeaderst(
                "DELETE",
                getContext(ACCESS_TOKEN.name()),
                getParameterProperties(endpoint),
                deviceuuid,
                installationdate,
                null,
                null

        );

    }

    @When("I send a POST request to {string} with a {string} to remove an upload avatars")
    public void iSendAPOSTRequestToWithAToRemoveAnUploadAvatars(String endpoint, String id) {
     LoginData user=TestDataLoader.getLoginData(id);
     String installationdate=user.getInstallationdate();
     String deviceuuid=user.getDeviceuuid();
     String token= user.getToken();
        Response response = HttpApiUtils.requestWithStandardHeaderst(
                "DELETE",
                token,
                getParameterProperties(endpoint),
                deviceuuid,
                installationdate,
                null,
                null

        );


    }

    @When("I send a POST request to {string} with {string} to fetch their own user details")
    public void iSendAPOSTRequestToWithToFetchTheirOwnUserDetails(String endpoint, String id) {
       LoginData user=TestDataLoader.getLoginData(id);
       String installationdate=user.getInstallationdate();
       String deviceuuid=user.getDeviceuuid();
       Response response = HttpApiUtils.requestWithStandardHeaderst(
               "GET",
               getContext(ACCESS_TOKEN.name()),
               getParameterProperties(endpoint),
               deviceuuid,
               installationdate,
               null,
               null
       );
    }

    @When("I send a POST request to {string} with {string} to fetch their own user detail")
    public void iSendAPOSTRequestToWithToFetchTheirOwnUserDetail(String endpoint, String id) {
        LoginData user=TestDataLoader.getLoginData(id);
        String installationdate=user.getInstallationdate();
        String deviceuuid=user.getDeviceuuid();
        String token= user.getToken();
        Response response = HttpApiUtils.requestWithStandardHeaderst(
                "GET",
                token,
                getParameterProperties(endpoint),
                deviceuuid,
                installationdate,
                null,
                null
        );

    }

    @When("I send a POST request to {string} with {string} to set primary notification through email")
    public void iSendAPOSTRequestToWithToSetPrimaryNotificationThroughEmail(String endpoint, String id) {
       LoginData data=TestDataLoader.getLoginData(id);
       String installationdate=data.getInstallationdate();
       String deviceuuid=data.getDeviceuuid();
       String emailOrPhone=data.getEmailOrPhone();
        Map<String,Object>body=Map.of("emailOrPhone",emailOrPhone);
        Response response = HttpApiUtils.requestWithStandardHeaderst(
                "POST",
                getContext(ACCESS_TOKEN.name()),
                getParameterProperties(endpoint),
                deviceuuid,
                installationdate,
                convertObjectToJson(body),
                null
        );

    }

    @When("I send a POST request to {string} with {string} to set primary notification through emails")
    public void iSendAPOSTRequestToWithToSetPrimaryNotificationThroughEmails(String endpoint, String id) {
        LoginData data=TestDataLoader.getLoginData(id);
        String installationdate=data.getInstallationdate();
        String deviceuuid=data.getDeviceuuid();
        String emailOrPhone=data.getEmailOrPhone();
        String token=data.getToken();
        Map<String,Object>body=Map.of("emailOrPhone",emailOrPhone);
        Response response = HttpApiUtils.requestWithStandardHeaderst(
                "POST",
                token,
                getParameterProperties(endpoint),
                deviceuuid,
                installationdate,
                convertObjectToJson(body),
                null
        );

    }

    @When("I send a POST request to {string} with {string} to get linked account details")
    public void iSendAPOSTRequestToWithToGetLinkedAccountDetails(String endpoint, String id) {
       LoginData data=TestDataLoader.getLoginData(id);
       String installationdate= data.getInstallationdate();
       String deviceuuid=data.getDeviceuuid();
       Response response = HttpApiUtils.requestWithStandardHeaderst(
               "GET",
               getContext(ACCESS_TOKEN.name()),
               getParameterProperties(endpoint),
               deviceuuid,
               installationdate,
               null,
               null

       );
    }

    @When("I send a POST request to {string} with {string} to get linked account detail")
    public void iSendAPOSTRequestToWithToGetLinkedAccountDetail(String endpoint, String id) {
        LoginData data=TestDataLoader.getLoginData(id);
        String installationdate= data.getInstallationdate();
        String deviceuuid=data.getDeviceuuid();
        String token=data.getToken();
        Response response = HttpApiUtils.requestWithStandardHeaderst(
                "GET",
                token,
                getParameterProperties(endpoint),
                deviceuuid,
                installationdate,
                null,
                null

        );

    }

    @When("I send a POST request to {string} with {string} to set a default account")
    public void iSendAPOSTRequestToWithToSetADefaultAccount(String endpoint, String id) {
        LoginData data=TestDataLoader.getLoginData(id);
        String installationdate= data.getInstallationdate();
        String deviceuuid=data.getDeviceuuid();
        String account=data.getAccount_number();
        Map<String,Object>body=Map.of("account_number",account);
        Response response = HttpApiUtils.requestWithStandardHeaderst(
                "POST",
                getContext(ACCESS_TOKEN.name()),
                getParameterProperties(endpoint),
                deviceuuid,
                installationdate,
                convertObjectToJson(body),
                null
        );
    }

    @When("I send a POST request to {string} with {string} to set a default accounts")
    public void iSendAPOSTRequestToWithToSetADefaultAccounts(String endpoint, String id) {
        LoginData data=TestDataLoader.getLoginData(id);
        String installationdate= data.getInstallationdate();
        String deviceuuid=data.getDeviceuuid();
        String account=data.getAccount_number();
        String token=data.getToken();
        Map<String,Object>body=Map.of("account_number",account);
        Response response = HttpApiUtils.requestWithStandardHeaderst(
                "POST",
                 token,
                getParameterProperties(endpoint),
                deviceuuid,
                installationdate,
                convertObjectToJson(body),
                null
        );

    }

    @When("I send a POST request to {string} with {string} to add an email to the app")
    public void iSendAPOSTRequestToWithToAddAnEmailToTheApp(String endpoint, String id) {
        LoginData data=TestDataLoader.getLoginData(id);
        String installationdate=data.getInstallationdate();
        String deviceuuid=data.getDeviceuuid();
        String email=data.getEmail();
        Map<String,Object>body=Map.of("email",email);
        Response response = HttpApiUtils.requestWithStandardHeaderst(
                "POST",
                getContext(ACCESS_TOKEN.name()),
                getParameterProperties(endpoint),
                deviceuuid,
                installationdate,
                convertObjectToJson(body),
                null
        );


    }

    @When("I send a POST request to {string} with {string} to add an email with an invalid access token")
    public void iSendAPOSTRequestToWithToAddAnEmailWithAnInvalidAccessToken(String endpoint, String id) {
        LoginData data=TestDataLoader.getLoginData(id);
        String installationdate=data.getInstallationdate();
        String deviceuuid=data.getDeviceuuid();
        String token=data.getToken();
        String email=data.getEmail();
        Map<String,Object>body=Map.of("email",email);
        Response response = HttpApiUtils.requestWithStandardHeaderst(
                "POST",
                  token,
                getParameterProperties(endpoint),
                deviceuuid,
                installationdate,
                convertObjectToJson(body),
                null
        );

    }
}
