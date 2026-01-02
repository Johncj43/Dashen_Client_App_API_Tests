package starter.step_definitions.client.transaction;

import io.cucumber.java.en.When;
import io.restassured.response.Response;
import starter.utils.HttpApiUtils;
import starter.utils.TestDataLoader;
import starter.utils.model.requestModel.client.transaction.FetchCbsTransaction;

import java.util.HashMap;
import java.util.Map;

import static starter.utils.PropertiesReader.getParameterProperties;
import static starter.utils.TestGlobalVariables.ContextEnum.*;
import static starter.utils.TestGlobalVariables.getContext;
import static starter.utils.TestGlobalVariables.setContext;

public class FetchCbsTransactionStepDef {
    @When("device user {string} sends {string} for fetch Cbs transaction")
    public void deviceUserSendsForFetchCbsTransaction(String id, String endpoint) {
        FetchCbsTransaction fetchCbsTransaction = TestDataLoader.getFetchCbsTransactionData(id);

        String deviceUuid = fetchCbsTransaction.getDeviceuuid();
        String searchKey = fetchCbsTransaction.getSearch_key();
        String installation = fetchCbsTransaction.getInstallationdate();

        Map<String, Object> queryParams = new HashMap<>();
        queryParams.put("search_key", searchKey);

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
