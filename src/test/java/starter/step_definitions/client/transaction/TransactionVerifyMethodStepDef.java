package starter.step_definitions.client.transaction;

import io.cucumber.java.PendingException;
import io.cucumber.java.en.And;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import starter.utils.HttpApiUtils;
import starter.utils.TestDataLoader;
import starter.utils.model.requestModel.client.transaction.VerifyTransaction;

import static starter.utils.PropertiesReader.getParameterProperties;
import static starter.utils.TestGlobalVariables.ContextEnum.*;
import static starter.utils.TestGlobalVariables.getContext;
import static starter.utils.TestGlobalVariables.setContext;

public class TransactionVerifyMethodStepDef {

    @When("the client sends a POST request to {string} to verify the transaction with a {string}")
    public void theClientSendsAPOSTRequestToToVerifyTheTransactionWithA(String endpoint, String id) {
        Response response = sendVerifyRequest(endpoint, id, getContext(ACCESS_TOKEN.name()), getContext(DATA_TOKEN.name()));
        setContext(HTTP_RESPONSE.name(), response);
    }

    @And("the client sends a POST request to {string} to verify the transaction with a {string} and an invalid data token")
    public void theClientSendsAPOSTRequestToToVerifyTheTransactionWithAAndAnInvalidDataToken(String endpoint, String id) {
        Response response = sendVerifyRequest(endpoint, id, getContext(ACCESS_TOKEN.name()), null);
        setContext(HTTP_RESPONSE.name(), response);
    }

    @And("the client sends a POST request to {string} to verify the transaction with a {string} and an invalid access token")
    public void theClientSendsAPOSTRequestToToVerifyTheTransactionWithAAndAnInvalidAccessToken(String endpoint, String id) {
        Response response = sendVerifyRequest(endpoint, id, null, getContext(DATA_TOKEN.name()));
        setContext(HTTP_RESPONSE.name(), response);
    }


    private Response sendVerifyRequest(String endpoint, String id, String accessToken, String dataToken) {
        VerifyTransaction data = TestDataLoader.getVerifyTransactionData(id);
        String installationdate = data.getInstallationdate();
        String deviceuuid = data.getDeviceuuid();
        return HttpApiUtils.requestWithHeaders("POST", getParameterProperties(endpoint), accessToken, deviceuuid, installationdate, null, false, dataToken, true, false, getContext(SESSION_ID.name()));
    }
}
