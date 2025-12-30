package starter.step_definitions.client.chat;

import io.cucumber.java.PendingException;
import io.cucumber.java.en.And;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import lombok.Data;
import starter.utils.HttpApiUtils;
import starter.utils.TestDataLoader;
import starter.utils.model.requestModel.client.chat.Chat;
import starter.utils.model.requestModel.client.chat.RequestChat;

import static starter.utils.HelperUtils.convertObjectToJson;
import static starter.utils.PropertiesReader.getParameterProperties;
import static starter.utils.TestGlobalVariables.ContextEnum.*;
import static starter.utils.TestGlobalVariables.getContext;

@Data
public class ChatSendMoneyStepDef {
    @And("the client sends {string} request with {string} to perform chat send money")
    public void theClientSendsRequestWithToPerformChatSendMoney(String endpoint, String id) {
        Chat data = TestDataLoader.getChatData(id);
        RequestChat requestBody = new RequestChat();
        requestBody.setPhone_number(data.getPhone_number());
        Response response = HttpApiUtils.requestHeaders(
                "POST",
                getParameterProperties(endpoint),
                getContext(ACCESS_TOKEN.name()),
                data.getDeviceuuid(),
                data.getInstallationdate(),
                convertObjectToJson(requestBody),
                false,
                true,
                getContext(DATA_TOKEN.name()),
                false,
                true,
                getContext(SESSION_ID.name()),
                false

        );

    }
}
