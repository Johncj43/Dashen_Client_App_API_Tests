package starter.step_definitions.client.core;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import starter.utils.HttpApiUtils;
import starter.utils.TestDataLoader;
import starter.utils.model.requestModel.client.core.GetIpsAccountList;

import static org.junit.Assert.*;
import static starter.utils.HelperUtils.convertObjectToJson;
import static starter.utils.PropertiesReader.getParameterProperties;
import static starter.utils.TestGlobalVariables.ContextEnum.*;
import static starter.utils.TestGlobalVariables.getContext;
import static starter.utils.TestGlobalVariables.setContext;

public class FetchIpsListStepDef {

    @When("device user {string} sends {string} request to fetch IPs list")
    public void deviceUserSendsRequestToFetchIpsList(String id, String endpoint) {

        GetIpsAccountList fetchIps = TestDataLoader.getGetIpsAccountListData(id);
        String deviceUuid = fetchIps .getDeviceuuid();
        String installation = fetchIps .getInstallationdate();
        Response response = HttpApiUtils.requestWithCoresHeaders3(
                "GET",
                getParameterProperties(endpoint),
                deviceUuid,
                getContext(ACCESS_TOKEN.name()),
                installation,
                getContext(SESSION_ID.name()),
                true,
                null

        );

        setContext(HTTP_RESPONSE.name(), response);
        System.out.println("Fetch IPs list response: " + response.getBody().asString());
    }



}
