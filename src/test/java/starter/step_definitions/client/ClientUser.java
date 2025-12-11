package starter.step_definitions.client;

import io.cucumber.java.PendingException;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.junit.Test;
import starter.utils.HttpApiUtils;
import starter.utils.TestDataLoader;
import starter.utils.model.requestModel.client.users.LoginData;

import static starter.utils.PropertiesReader.getParameterProperties;
import static starter.utils.TestGlobalVariables.ContextEnum.ACCESS_TOKEN;
import static starter.utils.TestGlobalVariables.ContextEnum.HTTP_RESPONSE;
import static starter.utils.TestGlobalVariables.getContext;


public class ClientUser {

//    @When("I send a POST request to {string} with a {string} to upload the profile avatar")
//    public void iSendAPOSTRequestToWithAToUploadTheProfileAvatar(String endpoint, String id) {
//        LoginData user= TestDataLoader.getLoginData(id);
//        String installationdate=user.getInstallationdate();
//        String deviceuuid=user.getDeviceuuid();
//        String imageFile=src/test/resources/test_data/images/download.png;
//        Response response = HttpApiUtils.requestImage(
//                ""
//        )
//    }

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
}
