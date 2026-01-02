package starter.step_definitions.client.utilities;

import io.cucumber.java.en.When;
import io.restassured.response.Response;
import starter.utils.HttpApiUtils;
import starter.utils.TestDataLoader;
import starter.utils.model.requestModel.client.utilities.DsTvData;

import java.util.HashMap;
import java.util.Map;

import static starter.utils.HelperUtils.convertObjectToJson;
import static starter.utils.PropertiesReader.getParameterProperties;
import static starter.utils.TestGlobalVariables.ContextEnum.*;
import static starter.utils.TestGlobalVariables.getContext;
import static starter.utils.TestGlobalVariables.setContext;

public class DsTvLookupStepDef {
    @When("device user {string} sends {string} for DSTV ticket lookup")
    public void device_user_sends_for_dstv_ticket_lookup(String id, String endpoint) {

        String accessToken = getContext(ACCESS_TOKEN.name());
        String sessionId = getContext("SESSION_ID");

        DsTvData dstvData = TestDataLoader.getDstvDataData(id);

        String deviceUuid = dstvData.getDeviceuuid();
        String cardNumber = dstvData.getCard_number();
        String installation = dstvData.getInstallationdate();

        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("card_number", cardNumber);

        Response response = HttpApiUtils.requestWithCoresHeaders3(
                "POST",
                getParameterProperties(endpoint),
                deviceUuid,
                accessToken,
                installation,
                sessionId,
                true,
                convertObjectToJson(requestBody)

        );

        setContext(HTTP_RESPONSE.name(), response);
        String dataToken = response.jsonPath().getString("data.datatoken");

        setContext(DATA_TOKEN.name(), dataToken);
        System.out.println("Account lookup response: " + response.getBody().asString());

    }

    @When("device user {string} sends {string} for DSTV product lookup with invalid card")
    public void device_user_sends_for_DSTV_product_lookup_invalid_card(String id, String endpoint) {

        String accessToken = getContext(ACCESS_TOKEN.name());
        String sessionId = getContext("SESSION_ID");

        DsTvData dstvData = TestDataLoader.getDstvDataData(id);

        String deviceUuid = dstvData.getDeviceuuid();
        String cardNumber = dstvData.getCard_number();
        String installation = dstvData.getInstallationdate();

        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("card_number", cardNumber);

        Response response = HttpApiUtils.requestWithCoresHeaders3(
                "POST",
                getParameterProperties(endpoint),
                deviceUuid,
                accessToken,
                installation,
                sessionId,
                true,
                convertObjectToJson(requestBody)

        );

        setContext(HTTP_RESPONSE.name(), response);

    }


}
