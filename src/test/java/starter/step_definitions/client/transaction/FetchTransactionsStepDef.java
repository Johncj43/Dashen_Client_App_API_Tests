package starter.step_definitions.client.transaction;

import io.cucumber.java.en.When;
import io.restassured.response.Response;
import starter.utils.HttpApiUtils;
import starter.utils.TestDataLoader;
import starter.utils.model.requestModel.client.transaction.FetchTransaction;


import static starter.utils.PropertiesReader.getParameterProperties;
import static starter.utils.TestGlobalVariables.ContextEnum.*;
import static starter.utils.TestGlobalVariables.getContext;
import static starter.utils.TestGlobalVariables.setContext;

public class FetchTransactionsStepDef {

    @When("device user {string} sends {string} for fetch transactions")
    public void deviceUserSendsForFetchTransactions(String id, String endpoint) {
        FetchTransaction transaction = TestDataLoader.getFetchTransaction(id);
        String deviceUuid = transaction.getDeviceuuid();
        String installation = transaction.getInstallationdate();
        Response response = HttpApiUtils.requestWithHeaders(
                "GET",
                  getParameterProperties(endpoint),
                getContext(ACCESS_TOKEN.name()),
                deviceUuid,
                installation,
                null,
                true,
                null,
                true,
                true,
                getContext(SESSION_ID.name())

        );


        setContext(HTTP_RESPONSE.name(), response);

    }
}
