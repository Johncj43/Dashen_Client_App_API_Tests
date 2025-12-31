package starter.step_definitions.client.hq;

import io.cucumber.java.en.When;
import io.restassured.response.Response;
import starter.utils.HttpApiUtils;
import starter.utils.TestDataLoader;
import starter.utils.model.requestModel.client.core.AccountLookupp;
import starter.utils.model.requestModel.client.core.GetFee;
import starter.utils.model.requestModel.client.core.RequestAccountLookupp;
import starter.utils.model.requestModel.client.services.GetFeesRequest;

import static starter.utils.HelperUtils.convertObjectToJson;
import static starter.utils.PropertiesReader.getParameterProperties;
import static starter.utils.TestGlobalVariables.ContextEnum.*;

import static starter.utils.TestGlobalVariables.getContext;
import static starter.utils.TestGlobalVariables.setContext;

public class HQStepDef {

    @When("device user {string} sends {string} request for HQ")
    public void device_user_sends_request_for_hq(String id, String endpoint) {

        GetFee hqData = TestDataLoader.getGetFeeData(id);
        String deviceUuid = hqData.getDeviceuuid();
        String installation = hqData.getInstallationdate();
        GetFeesRequest requestBody = new GetFeesRequest();
        requestBody.setAmount(hqData.getAmount());
        requestBody.setService_name(hqData.getService_name());
        requestBody.setDebit_accountnumber(hqData.getDebit_accountnumber());
        requestBody.setCredit_accountnumber(hqData.getCredit_accountnumber());


        Response response = HttpApiUtils.dataToken(
                "POST",
                getParameterProperties(endpoint),
                deviceUuid,
                getContext(ACCESS_TOKEN.name()),
                installation,
                getContext(SESSION_ID.name()),
                getContext(DATA_TOKEN.name()),
                true,
                convertObjectToJson(requestBody)


        );

        setContext(HTTP_RESPONSE.name(), response);
        String dataToken = response.jsonPath().getString("data.datatoken");
        if (dataToken != null) {
            setContext(DATA_TOKEN.name(), dataToken);
        } else {
            System.out.println("Datatoken is null, negative scenarion, skipping store context");
        }

    }
    @When("device user {string} sends {string} for account lookup check")
    public void deviceUserSendsForAccountLookupCheck(String id, String endpoint) {

        AccountLookupp accountLookup = TestDataLoader.getAccountLookuppData(id);

        String deviceUuid = accountLookup.getDeviceuuid();
        String installation = accountLookup.getInstallationdate();
        RequestAccountLookupp requestBody = new RequestAccountLookupp();
        requestBody.setAccount_number(accountLookup.getAccount_number());
        Response response = HttpApiUtils.requestWithCoresHeaders3(
                "POST",
                getParameterProperties(endpoint),
                deviceUuid,
                getContext(ACCESS_TOKEN.name()),
                installation,
                getContext(SESSION_ID.name()),
                true,
                convertObjectToJson(requestBody)

        );

        setContext(HTTP_RESPONSE.name(), response);
        String dataToken = response.jsonPath().getString("data.datatoken");
        if (dataToken != null) {
            setContext(DATA_TOKEN.name(), dataToken);
        } else {
            System.out.println("Datatoken is null, negative scenarion, skipping store context");
        }
    }
}
