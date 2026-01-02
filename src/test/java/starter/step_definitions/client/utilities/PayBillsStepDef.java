package starter.step_definitions.client.utilities;

import io.cucumber.java.en.And;
import io.restassured.response.Response;

import starter.utils.HttpApiUtils;
import starter.utils.TestDataLoader;
import starter.utils.model.requestModel.client.utilities.PaybillsData;

import java.util.HashMap;
import java.util.Map;

import static starter.utils.HelperUtils.convertObjectToJson;
import static starter.utils.PropertiesReader.getParameterProperties;
import static starter.utils.TestGlobalVariables.ContextEnum.*;
import static starter.utils.TestGlobalVariables.getContext;
import static starter.utils.TestGlobalVariables.setContext;

public class PayBillsStepDef {
    @And("device user {string} sends {string} for pay bills payment")
    public void device_user_sends_for_pay_bills_payment(String id, String endpoint) {

        PaybillsData paybillsData = TestDataLoader.getPaybillsData(id);

        String deviceUuid = paybillsData.getDeviceuuid();
        String narrative = paybillsData.getNarrative();
        String installation = paybillsData.getInstallationdate();


        Map<String, String> requestBody = new HashMap<>();

        requestBody.put("narrative", narrative);


        Response response = HttpApiUtils.requestWithHeaders(
                "POST",
                getParameterProperties(endpoint),
                getContext(ACCESS_TOKEN.name()),
                deviceUuid,
                installation,
                convertObjectToJson(requestBody),
                true,
                getContext(DATA_TOKEN.name()),
                true,
                true,
                getContext(SESSION_ID.name())
        );

        setContext(HTTP_RESPONSE.name(), response);

    }
}
