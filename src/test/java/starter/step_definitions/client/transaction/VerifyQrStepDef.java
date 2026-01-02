package starter.step_definitions.client.transaction;

import io.cucumber.java.en.When;
import io.restassured.response.Response;
import starter.utils.HttpApiUtils;
import starter.utils.TestDataLoader;
import starter.utils.model.requestModel.client.transaction.VerifyQrData;

import java.util.HashMap;
import java.util.Map;

import static starter.utils.PropertiesReader.getParameterProperties;
import static starter.utils.TestGlobalVariables.ContextEnum.*;
import static starter.utils.TestGlobalVariables.getContext;
import static starter.utils.TestGlobalVariables.setContext;

public class VerifyQrStepDef {

    @When("device user {string} sends {string} for Qr verification")
    public void deviceUserSendsForQrVerification(String id, String endpoint) {

        VerifyQrData verifyQrData = TestDataLoader.getVerifyQrData(id);

        String deviceUuid = verifyQrData.getDeviceuuid();
        String qr_value = verifyQrData.getQr_value();
        String installation = verifyQrData.getInstallationdate();

        Map<String, Object> queryParams = new HashMap<>();
        queryParams.put("qr_value", qr_value);

        Response response = HttpApiUtils.requestWithCoresHeaders33(
                "GET",
                getParameterProperties(endpoint),
                deviceUuid,
                getContext(ACCESS_TOKEN.name()),
                installation,
                getContext(SESSION_ID.name()),
                true,
                queryParams
        );

        setContext(HTTP_RESPONSE.name(), response);
    }
}
