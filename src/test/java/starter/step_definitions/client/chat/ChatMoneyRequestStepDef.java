package starter.step_definitions.client.chat;

import io.cucumber.java.PendingException;
import io.cucumber.java.en.And;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import lombok.Data;
import starter.utils.HttpApiUtils;
import starter.utils.TestDataLoader;
import starter.utils.model.requestModel.client.chat.ChatMoneyRequest;
import starter.utils.model.requestModel.client.chat.RequestChatMoneyRequest;
import starter.utils.model.requestModel.client.chat.RequestMoneyRequestAccept;
import starter.utils.model.requestModel.client.services.GetFees;
import starter.utils.model.requestModel.client.services.GetFeesRequest;

import java.util.Map;

import static starter.utils.HelperUtils.convertObjectToJson;
import static starter.utils.PropertiesReader.getParameterProperties;
import static starter.utils.TestGlobalVariables.ContextEnum.*;
import static starter.utils.TestGlobalVariables.getContext;
import static starter.utils.TestGlobalVariables.setContext;

@Data
public class ChatMoneyRequestStepDef {
    @When("the client sends a POST request to {string} using test data {string} to request money via chat")
    public void theClientSendsAPOSTRequestToUsingTestDataToRequestMoneyViaChat(String endpoint, String id) {
        ChatMoneyRequest data = TestDataLoader.getChatMoneyRequestData(id);
        RequestChatMoneyRequest requestBody = new RequestChatMoneyRequest();
        requestBody.setPhone_number(data.getPhone_number());
        requestBody.setAccount_number(data.getAccount_number());
        requestBody.setAmount(data.getAmount());
        Response response = HttpApiUtils.requestHeaders(
                "POST",
                getParameterProperties(endpoint),
                getContext(ACCESS_TOKEN.name()),
                data.getDeviceuuid(),
                data.getInstallationdate(),
                convertObjectToJson(requestBody),
                false,
                false,
                null,
                false,
                false,
                getContext(SESSION_ID.name()),
                false


        );
        setContext(HTTP_RESPONSE.name(), response);
        String requestId= response.jsonPath().getString("data.request_id");
        if (requestId!= null) {
            setContext(REQUEST_ID.name(), requestId);
            System.out.println("✅ Request ID: " + requestId);
        } else {
            System.out.println("⚠️ dataToken is null – negative test case, skipping context storage");
        }


    }

    @And("the client sends {string} request with {string} to perform chat money request")
    public void theClientSendsRequestWithToPerformChatMoneyRequest(String endpoint, String id) {
        ChatMoneyRequest data = TestDataLoader.getChatMoneyRequestData(id);
        RequestMoneyRequestAccept requestBody = new RequestMoneyRequestAccept();
        requestBody.setReason(data.getReason());
        Response response = HttpApiUtils.requestHeaders(
                "POST",
                getParameterProperties(endpoint),
                getContext(ACCESS_TOKEN.name()),
                data.getDeviceuuid(),
                data.getInstallationdate(),
                convertObjectToJson(requestBody),
                false,
                false,
                getContext(DATA_TOKEN.name()),
                false,
                true,
                getContext(SESSION_ID.name()),
                false


        );
        setContext(HTTP_RESPONSE.name(), response);

    }

    @When("the recipient sends a POST request to {string} using test data {string}")
    public void theRecipientSendsAPOSTRequestToUsingTestData(String endpoint, String id) {
        ChatMoneyRequest data = TestDataLoader.getChatMoneyRequestData(id);
        Map<String,Object> body =Map.of("request_id",getContext(REQUEST_ID.name()),
                                          "reason","i rejected it");
        Response response = HttpApiUtils.requestHeaders(
                "POST",
                getParameterProperties(endpoint),
                getContext(ACCESS_TOKEN.name()),
                data.getDeviceuuid(),
                data.getInstallationdate(),
                convertObjectToJson(body),
                false,
                false,
                null,
                false,
                false,
                getContext(SESSION_ID.name()),
                false
        );
        setContext(HTTP_RESPONSE.name(), response);




    }

    @And("the requester sends a POST request to {string} using test data {string}")
    public void theRequesterSendsAPOSTRequestToUsingTestData(String endpoint, String id) {
        ChatMoneyRequest data = TestDataLoader.getChatMoneyRequestData(id);
        String endpointkey= getParameterProperties(endpoint)+"/"+getContext(REQUEST_ID.name());
        Response response = HttpApiUtils.requestHeaders(
                "DELETE",
                endpointkey,
                getContext(ACCESS_TOKEN.name()),
                data.getDeviceuuid(),
                data.getInstallationdate(),
                null,
                false,
                false,
                null,
                false,
                false,
                getContext(SESSION_ID.name()),
                false
        );
        setContext(HTTP_RESPONSE.name(), response);

    }
}
