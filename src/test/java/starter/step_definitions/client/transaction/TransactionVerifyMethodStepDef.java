package starter.step_definitions.client.transaction;

import io.cucumber.java.PendingException;
import io.cucumber.java.en.And;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import starter.utils.HttpApiUtils;
import starter.utils.TestDataLoader;
import starter.utils.model.requestModel.client.transaction.RequestVerifyTransaction;
import starter.utils.model.requestModel.client.transaction.VerifyTransaction;

import static starter.utils.HelperUtils.convertObjectToJson;
import static starter.utils.PropertiesReader.getParameterProperties;
import static starter.utils.TestGlobalVariables.ContextEnum.*;
import static starter.utils.TestGlobalVariables.getContext;
import static starter.utils.TestGlobalVariables.setContext;

public class TransactionVerifyMethodStepDef {

    @When("the client sends a POST request to {string} to verify the transaction with a {string}")
    public void theClientSendsAPOSTRequestToToVerifyTheTransactionWithA(String endpoint, String id) {
        Response response = sendVerifyRequest(endpoint, id, getContext(ACCESS_TOKEN.name()), getContext(DATA_TOKEN.name()));
        String dataToken = response.jsonPath().getString("data.datatoken");
        if (dataToken != null) {
            setContext(DATA_TOKEN.name(), dataToken);
        } else {
            System.out.println("⚠️ dataToken is null – negative test case, skipping context storage");
        }
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
        return HttpApiUtils.requestWithHeaders(
                "POST",
                getParameterProperties(endpoint),
                accessToken,
                deviceuuid,
                installationdate,
                null,
                true,
                dataToken,
                true,
                false,
                getContext(SESSION_ID.name()));
    }

    @And("the client sends a POST request to {string} to verify the transaction with {string}")
    public void theClientSendsAPOSTRequestToToVerifyTheTransactionWith(String endpoint, String id) {
        VerifyTransaction data = TestDataLoader.getVerifyTransactionData(id);
        RequestVerifyTransaction requestBody= new RequestVerifyTransaction();
        requestBody.setCredit_accountnumber(data.getCredit_accountnumber());
        Response response = HttpApiUtils.requestWithHeaders(
                "POST",
                getParameterProperties(endpoint),
                getContext(ACCESS_TOKEN.name()),
                data.getDeviceuuid(),
                data.getInstallationdate(),
                convertObjectToJson(requestBody),
                true,
                getContext(DATA_TOKEN.name()),
                true,
                false,
                getContext(SESSION_ID.name())
        );
        String dataToken= response.jsonPath().getString("data.datatoken");
        if (dataToken != null) {
            setContext(DATA_TOKEN.name(), dataToken);
        } else {
            System.out.println("⚠️ dataToken is null – negative test case, skipping context storage");
        }






    }

    @And("the client sends a POST request to {string} using test data {string} to verify the transaction for chat send money")
    public void theClientSendsAPOSTRequestToUsingTestDataToVerifyTheTransactionForChatSendMoney(String endpoint, String id) {
        VerifyTransaction data = TestDataLoader.getVerifyTransactionData(id);
        RequestVerifyTransaction requestBody= new RequestVerifyTransaction();
        requestBody.setCredit_accountnumber(data.getCredit_accountnumber());
        Response response = HttpApiUtils.requestWithHeaders(
                "POST",
                getParameterProperties(endpoint),
                getContext(ACCESS_TOKEN.name()),
                data.getDeviceuuid(),
                data.getInstallationdate(),
                convertObjectToJson(requestBody),
                true,
                getContext(DATA_TOKEN.name()),
                false,
                false,
                getContext(SESSION_ID.name())
        );
        String dataToken= response.jsonPath().getString("data.datatoken");
        if (dataToken != null) {
            setContext(DATA_TOKEN.name(), dataToken);
        } else {
            System.out.println("⚠️ dataToken is null – negative test case, skipping context storage");
        }

    }

    @And("the client sends a POST request to {string} using test data {string} to verify the transaction for chat money request")
    public void theClientSendsAPOSTRequestToUsingTestDataToVerifyTheTransactionForChatMoneyRequest(String endpoint, String id) {
        VerifyTransaction data = TestDataLoader.getVerifyTransactionData(id);
        RequestVerifyTransaction requestBody= new RequestVerifyTransaction();
        requestBody.setCredit_accountnumber(data.getCredit_accountnumber());
        Response response = HttpApiUtils.requestHeaders(
                "POST",
                getParameterProperties(endpoint),
                getContext(ACCESS_TOKEN.name()),
                data.getDeviceuuid(),
                data.getInstallationdate(),
                convertObjectToJson(requestBody),
                true,
                true,
                getContext(DATA_TOKEN.name()),
                false,
                false,
                getContext(SESSION_ID.name())
        );
        String dataToken= response.jsonPath().getString("data.datatoken");
        if (dataToken != null) {
            setContext(DATA_TOKEN.name(), dataToken);
        } else {
            System.out.println("⚠️ dataToken is null – negative test case, skipping context storage");
        }



    }
}

