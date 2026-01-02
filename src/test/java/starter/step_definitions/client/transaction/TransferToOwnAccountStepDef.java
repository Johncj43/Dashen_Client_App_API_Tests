package starter.step_definitions.client.transaction;

import io.cucumber.java.PendingException;
import io.cucumber.java.en.And;
import io.cucumber.java.en.When;
import io.restassured.response.Response;


import starter.utils.HttpApiUtils;
import starter.utils.TestDataLoader;
import starter.utils.model.requestModel.client.core.AccountLookupp;
import starter.utils.model.requestModel.client.core.RequestAccountLookupp;
import starter.utils.model.requestModel.client.transaction.OwnTransfer;

import java.util.HashMap;
import java.util.Map;

import static starter.utils.HelperUtils.convertObjectToJson;
import static starter.utils.PropertiesReader.getParameterProperties;
import static starter.utils.TestGlobalVariables.ContextEnum.*;
import static starter.utils.TestGlobalVariables.getContext;
import static starter.utils.TestGlobalVariables.setContext;

public class TransferToOwnAccountStepDef {
    @And("device user {string} sends {string} for dashen user own")
    public void device_user_sends_for_dashen_user_own(String id, String endpoint) {

        OwnTransfer ownTransfer = TestDataLoader.getOwnTransferData(id);

        String deviceUuid = ownTransfer.getDeviceuuid();
        String narrative = ownTransfer.getNarrative();
        String installation = ownTransfer.getInstallationdate();


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

    @When("device user {string} sends {string} for account lookup to transfer own account")
    public void deviceUserSendsForAccountLookupToTransferOwnAccount(String id, String endpoint) {
        AccountLookupp data = TestDataLoader.getAccountLookuppData(id);
        RequestAccountLookupp requestBody = new RequestAccountLookupp();
                requestBody.setAccount_number(data.getAccount_number());
        Response response = HttpApiUtils.requestWithCoresHeaders333(
                "POST",
                getParameterProperties(endpoint),
                data.getDeviceuuid(),
                getContext(ACCESS_TOKEN.name()),
                data.getInstallationdate(),
                getContext(SESSION_ID.name()),
                true,
                true,
                convertObjectToJson(requestBody)
        );
        setContext(HTTP_RESPONSE.name(), response);
        String dataToken = response.jsonPath().getString("data.datatoken");
        if (dataToken != null) {
            setContext(DATA_TOKEN.name(), dataToken);
        } else {
            System.out.println("Datatoken is null, negative scenario, skipping store context");
        }

    }
}
