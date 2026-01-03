package starter.step_definitions.client.utilities;

import io.cucumber.java.en.When;
import io.restassured.response.Response;
import starter.utils.HttpApiUtils;
import starter.utils.TestDataLoader;
import starter.utils.model.requestModel.client.utilities.FlyGateData;

import java.util.HashMap;
import java.util.Map;

import static starter.utils.PropertiesReader.getParameterProperties;
import static starter.utils.TestGlobalVariables.ContextEnum.*;
import static starter.utils.TestGlobalVariables.getContext;
import static starter.utils.TestGlobalVariables.setContext;

public class GetFlyGateStepDef {
    @When("device user {string} sends {string} for flyGate ticket lookup")
    public void deviceUserSendsForFlyGateTicketLookup(String id, String endpoint) {


        FlyGateData flyGateData = TestDataLoader.getFlyGateDataData(id);

        String deviceUuid = flyGateData.getDeviceuuid();
        String pnr = flyGateData.getPnr();
        String installation = flyGateData.getInstallationdate();

        Map<String, Object> queryParams = new HashMap<>();
        queryParams.put("pnr", pnr);

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
        System.out.println("FlyGate account lookup response: " + response.getBody().asString());
        String dataToken = response.jsonPath().getString("data.datatoken");
        Integer amount = response.jsonPath().getInt("data.amount");
        setContext(AMOUNT.name(), amount);
        setContext(DATA_TOKEN.name(), dataToken);
        System.out.println("Account lookup response: " + response.getBody().asString());
    }

}
