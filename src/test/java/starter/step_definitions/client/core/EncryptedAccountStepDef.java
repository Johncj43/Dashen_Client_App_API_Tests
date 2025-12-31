package starter.step_definitions.client.core;

import io.cucumber.java.en.When;
import io.restassured.response.Response;
import starter.utils.HttpApiUtils;
import starter.utils.TestDataLoader;
import starter.utils.model.requestModel.client.core.EncryptedAccount;

import java.util.HashMap;
import java.util.Map;

import static starter.utils.PropertiesReader.getParameterProperties;
import static starter.utils.TestGlobalVariables.ContextEnum.*;
import static starter.utils.TestGlobalVariables.getContext;
import static starter.utils.TestGlobalVariables.setContext;

public class EncryptedAccountStepDef {

    @When("device user {string} sends {string} for encrypted account lookup")
    public void deviceUserSendsForEncryptedAccountLookup(String id, String endpoint) {

        EncryptedAccount encryptedAccount = TestDataLoader.getEncryptedAccountData(id);
        String deviceUuid = encryptedAccount.getDeviceuuid();
        String qrValue = encryptedAccount.getQr_value();
        String installation = encryptedAccount.getInstallationdate();

        Map<String, Object> queryParams = new HashMap<>();
        queryParams.put("qr_value", qrValue);
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
