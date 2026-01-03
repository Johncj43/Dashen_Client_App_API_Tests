package starter.step_definitions.client.chat;

import com.sun.jna.platform.win32.WinNT;
import io.cucumber.java.PendingException;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import starter.utils.HttpApiUtils;
import starter.utils.TestDataLoader;
import starter.utils.model.requestModel.client.chat.Chat;

import static starter.utils.PropertiesReader.getParameterProperties;
import static starter.utils.TestGlobalVariables.ContextEnum.*;
import static starter.utils.TestGlobalVariables.getContext;
import static starter.utils.TestGlobalVariables.setContext;

public class GetMyChatStepDef {
    @When("I send a GET request to {string} with {string} to get my chat")
    public void iSendAGETRequestToWithToGetMyChat(String endpoint, String id) {
        Chat data = TestDataLoader.getChatData(id);
        Response response = HttpApiUtils.requestWithStandardHeadersSimples(
                "GET",
                getContext(ACCESS_TOKEN.name()),
                getParameterProperties(endpoint),
                data.getDeviceuuid(),
                data.getInstallationdate(),
                getContext(SESSION_ID.name()),
                null
        );
        setContext(HTTP_RESPONSE.name(), response);


    }


}
