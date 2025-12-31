package starter.step_definitions.client.core;

import io.cucumber.java.en.When;
import io.restassured.response.Response;
import starter.utils.HttpApiUtils;
import starter.utils.TestDataLoader;
import starter.utils.model.requestModel.client.core.MinistatementData;

import java.util.HashMap;
import java.util.Map;

import static starter.utils.HelperUtils.convertObjectToJson;
import static starter.utils.PropertiesReader.getParameterProperties;
import static starter.utils.TestGlobalVariables.ContextEnum.ACCESS_TOKEN;
import static starter.utils.TestGlobalVariables.ContextEnum.HTTP_RESPONSE;
import static starter.utils.TestGlobalVariables.getContext;
import static starter.utils.TestGlobalVariables.setContext;

public class MiniStatementStepDef {

    @When("device user {string} sends {string} for mini statement")
    public void deviceUserMiniStatement(String id, String endpoint) { {

    }

        String accessToken = getContext(ACCESS_TOKEN.name());
        String sessionId = getContext("SESSION_ID");
        String accountNumber=getContext("mainaccountnumber");

        MinistatementData miniStatementData = TestDataLoader.getMiniStatementData(id);
        String deviceUuid=miniStatementData.getDeviceuuid();
        String installation=miniStatementData.getInstallationdate();
        String date_from = miniStatementData.getDate_from();
        String date_to=miniStatementData.getDate_to();
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("account_number", accountNumber);
        requestBody.put("date_from",date_from);
        requestBody.put ("date_to",date_to);

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
        System.out.println("Mini Statement response: " + response.getBody().asString());
    }
}
