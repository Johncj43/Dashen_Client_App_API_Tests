package starter.step_definitions.client.chat;

import io.cucumber.java.PendingException;
import io.cucumber.java.en.And;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import starter.utils.HttpApiUtils;
import starter.utils.TestDataLoader;
import starter.utils.model.requestModel.client.chat.Chat;

import static starter.utils.PropertiesReader.getParameterProperties;
import static starter.utils.TestGlobalVariables.ContextEnum.*;
import static starter.utils.TestGlobalVariables.getContext;

public class ChatTopupStepDef {
    @And("the client sends {string} request with {string} to perform chat topup with ethiotelecom")
    public void theClientSendsRequestWithToPerformChatTopupWithEthiotelecom(String endpoint, String id) {
        Chat data = TestDataLoader.getChatData(id);
        Response response = HttpApiUtils.requestWithHeaders(
                "POST",
                getParameterProperties(endpoint),
                getContext(ACCESS_TOKEN.name()),
                data.getDeviceuuid(),
                data.getInstallationdate(),
                null,
                false,
                getContext(DATA_TOKEN.name()),
                false,
                true,
                getContext(SESSION_ID.name())

        );

    }

    @And("the client sends {string} request with {string} to perform chat topup with safaricom")
    public void theClientSendsRequestWithToPerformChatTopupWithSafaricom(String endpoint, String id) {
        Chat data = TestDataLoader.getChatData(id);
        Response response = HttpApiUtils.requestWithHeaders(
                "POST",
                getParameterProperties(endpoint),
                getContext(ACCESS_TOKEN.name()),
                data.getDeviceuuid(),
                data.getInstallationdate(),
                null,
                false,
                getContext(DATA_TOKEN.name()),
                false,
                true,
                getContext(SESSION_ID.name())

        );
    }

}
