package starter.step_definitions.client.transaction;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import starter.utils.HttpApiUtils;
import starter.utils.TestDataLoader;
import starter.utils.model.requestModel.client.transaction.FetchMethod;
import static starter.utils.PropertiesReader.getParameterProperties;
import static starter.utils.TestGlobalVariables.ContextEnum.*;
import static starter.utils.TestGlobalVariables.getContext;
import static starter.utils.TestGlobalVariables.setContext;

public class FetchMethodStepDef {

    @When("device user {string} sends {string} for fetch methods")
    public void deviceUserSendsForFetchMethods(String id, String endpoint) {
        FetchMethod transaction = TestDataLoader.getTransactionData(id);
        String deviceUuid = transaction.getDeviceuuid();
        String installation = transaction.getInstallationdate();
        Response response = HttpApiUtils.requestWithHeaders(
               "POST",
                getParameterProperties(endpoint),
                getContext(ACCESS_TOKEN.name()),
                deviceUuid,
                installation,
                null,
                true,
                getContext(DATA_TOKEN.name()),
                true,
                true,
                getContext(SESSION_ID.name())
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
