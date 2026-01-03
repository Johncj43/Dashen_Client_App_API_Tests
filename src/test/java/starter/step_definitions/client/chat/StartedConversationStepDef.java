package starter.step_definitions.client.chat;

import io.cucumber.java.PendingException;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import starter.utils.HttpApiUtils;
import starter.utils.TestDataLoader;
import starter.utils.model.requestModel.client.chat.Chat;

import java.util.Map;

import static starter.utils.HelperUtils.convertObjectToJson;
import static starter.utils.PropertiesReader.getParameterProperties;
import static starter.utils.TestGlobalVariables.ContextEnum.*;
import static starter.utils.TestGlobalVariables.getContext;
import static starter.utils.TestGlobalVariables.setContext;

public class StartedConversationStepDef {
    @When("the user start to conversation through {string} with {string} to chat each other through super app")
    public void theUserStartToConversationThroughWithToChatEachOtherThroughSuperApp(String endpoint, String id) {
        Chat data = TestDataLoader.getChatData(id);
        Map<String, Object> body =Map.of("user_code",getContext(USER_CODE.name()));
        Response response = HttpApiUtils.requestWithStandardHeadersSimples(
                "PUT",
                getContext(ACCESS_TOKEN.name()),
                getParameterProperties(endpoint),
                data.getDeviceuuid(),
                data.getInstallationdate(),
                getContext(SESSION_ID.name()),
                convertObjectToJson(body)

        );
        setContext(HTTP_RESPONSE.name(), response);


    }
}
