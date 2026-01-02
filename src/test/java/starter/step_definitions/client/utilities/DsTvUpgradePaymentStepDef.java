package starter.step_definitions.client.utilities;

import io.cucumber.java.en.When;
import io.restassured.response.Response;
import starter.utils.HttpApiUtils;
import starter.utils.TestDataLoader;

import starter.utils.model.requestModel.client.utilities.DstvUpgradePaymentData;

import java.util.HashMap;
import java.util.Map;

import static starter.utils.HelperUtils.convertObjectToJson;
import static starter.utils.PropertiesReader.getParameterProperties;
import static starter.utils.TestGlobalVariables.ContextEnum.*;
import static starter.utils.TestGlobalVariables.getContext;
import static starter.utils.TestGlobalVariables.setContext;

public class DsTvUpgradePaymentStepDef {
    @When("device user {string} sends {string} for UPGRADE dstv payment")
    public void device_user_sends_for_UPGRADE_dstv_payment(String id, String endpoint) {

        DstvUpgradePaymentData dstvUpgradePaymentData = TestDataLoader.getDstvUpgradePaymentData(id);
        String deviceUuid = dstvUpgradePaymentData.getDeviceuuid();
        String installation = dstvUpgradePaymentData.getInstallationdate();
        String productCode = dstvUpgradePaymentData.getProduct_code();
        Integer packageTime = dstvUpgradePaymentData.getPackage_time();

        Map<String, Object> requestBody = new HashMap<>();

        requestBody.put("product_code", productCode);
        requestBody.put("package_time", packageTime);


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
