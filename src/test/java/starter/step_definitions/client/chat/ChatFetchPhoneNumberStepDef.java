package starter.step_definitions.client.chat;

import io.cucumber.java.PendingException;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import starter.utils.HttpApiUtils;
import starter.utils.TestDataLoader;
import starter.utils.model.requestModel.client.chat.Chat;

import java.io.File;

import static starter.utils.PropertiesReader.getParameterProperties;
import static starter.utils.TestGlobalVariables.ContextEnum.ACCESS_TOKEN;
import static starter.utils.TestGlobalVariables.ContextEnum.SESSION_ID;
import static starter.utils.TestGlobalVariables.getContext;

public class ChatFetchPhoneNumberStepDef {

    @When("the client sends a POST request to {string} using test data {string} to lookup a contact registered in the Super App")
    public void theClientSendsAPOSTRequestToUsingTestDataToLookupAContactRegisteredInTheSuperApp(String endpoint, String id) {
        Chat data = TestDataLoader.getChatData(id);
        File  phoneNumbersJson = new File("src/test/resources/test_data/client/phoneNumberRegistered.json");
        Response response = HttpApiUtils.uploadMultipart(
                "POST",
                getParameterProperties(endpoint),
                getContext(ACCESS_TOKEN.name()),
                null,
                phoneNumbersJson,
                data.getDeviceuuid(),
                data.getInstallationdate(),
                getContext(SESSION_ID.name())
        );




    }

    @When("the client sends a POST request to {string} using test data {string} to lookup a contact unregistered in the Super App")
    public void theClientSendsAPOSTRequestToUsingTestDataToLookupAContactUnregisteredInTheSuperApp(String endpoint, String id) {
        Chat data = TestDataLoader.getChatData(id);
        File  phoneNumbersJson = new File("src/test/resources/test_data/client/phoneNumberUnregistered.json");
        Response response = HttpApiUtils.uploadMultipart(
                "POST",
                getParameterProperties(endpoint),
                getContext(ACCESS_TOKEN.name()),
                null,
                phoneNumbersJson,
                data.getDeviceuuid(),
                data.getInstallationdate(),
                getContext(SESSION_ID.name())
        );

    }

    @When("the client sends a POST request to {string} using test data {string} to lookup with empty contacts")
    public void theClientSendsAPOSTRequestToUsingTestDataToLookupWithEmptyContacts(String endpoint, String id) {
        Chat data = TestDataLoader.getChatData(id);
        File  phoneNumbersJson = new File("src/test/resources/test_data/client/emptyContacts.json");
        Response response = HttpApiUtils.uploadMultipart(
                "POST",
                getParameterProperties(endpoint),
                getContext(ACCESS_TOKEN.name()),
                null,
                phoneNumbersJson,
                data.getDeviceuuid(),
                data.getInstallationdate(),
                getContext(SESSION_ID.name())
        );

    }
}
