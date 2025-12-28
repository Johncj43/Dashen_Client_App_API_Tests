package starter.step_definitions.client.services;

import io.cucumber.java.PendingException;
import io.cucumber.java.en.And;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import starter.utils.HttpApiUtils;
import starter.utils.TestDataLoader;
import starter.utils.model.requestModel.client.services.GetFees;
import starter.utils.model.requestModel.client.services.GetFeesRequest;

import java.util.Map;

import static starter.utils.HelperUtils.convertObjectToJson;
import static starter.utils.HelperUtils.extractJsonValue;
import static starter.utils.PropertiesReader.getParameterProperties;
import static starter.utils.TestGlobalVariables.ContextEnum.*;
import static starter.utils.TestGlobalVariables.getContext;
import static starter.utils.TestGlobalVariables.setContext;

public class GetFeesStepDef {


    @When("I send a POST request to {string} with a {string} to create get fees")
    public void iSendAPOSTRequestToWithAToCreateGetFees(String endpoint, String id) {
        Response response = sendGetFeesRequest("POST", endpoint, id, getContext(ACCESS_TOKEN.name()), getContext(SESSION_ID.name()));
        String dataToken = response.jsonPath().getString("data.datatoken");
        if (dataToken != null) {
            setContext(DATA_TOKEN.name(), dataToken);
        } else {
            System.out.println("⚠️ dataToken is null – negative test case, skipping context storage");
        }
    }

    @When("I send a POST request to {string} with a {string} to get fees with an invalid access token")
    public void iSendAPOSTRequestToWithAToGetFeesWithAnInvalidAccessToken(String endpoint, String id) {
        Response response = sendGetFeesRequest("POST", endpoint, id, null,getContext(SESSION_ID.name()));

    }
    @When("I send a POST request to {string} with a {string} to get fees with expired session id")
    public void iSendAPOSTRequestToWithAToGetFeesWithExpiredSessionId(String enpoint, String id) {
        Response response = sendGetFeesRequest("POST",enpoint,id,getContext(ACCESS_TOKEN.name()),null);
    }

    private Response sendGetFeesRequest(String httpMethod, String endpointKey, String id, String accessToken, String sessionId) {
        GetFees data = TestDataLoader.getGetFeesData(id);

        GetFeesRequest requestBody = new GetFeesRequest();
        requestBody.setAmount(data.getAmount());
        requestBody.setService_name(data.getService_name());
        requestBody.setDebit_accountnumber(data.getDebit_accountnumber());
        requestBody.setCredit_accountnumber(data.getCredit_accountnumber());

        String jsonBody = convertObjectToJson(requestBody);

        return HttpApiUtils.requestWithStandardHeadersSimples(
                httpMethod,
                accessToken,
                getParameterProperties(endpointKey),
                data.getDeviceuuid(),
                data.getInstallationdate(),
                sessionId,
                jsonBody
        );
    }


    @And("I send a POST request to {string} with a {string} to create get fees for tele birr wallet")
    public void iSendAPOSTRequestToWithAToCreateGetFeesForTeleBirrWallet(String endpoint, String id) {
        GetFees data = TestDataLoader.getGetFeesData(id);

        GetFeesRequest requestBody = new GetFeesRequest();
        requestBody.setAmount(data.getAmount());
        requestBody.setService_name(data.getService_name());
        requestBody.setDebit_accountnumber(data.getDebit_accountnumber());
        requestBody.setCredit_accountnumber(data.getCredit_accountnumber());
        String jsonBody = convertObjectToJson(requestBody);
        Response response = HttpApiUtils.requestWithHeaders(
                "POST",
                getParameterProperties(endpoint),
                getContext(ACCESS_TOKEN.name()),
                data.getDeviceuuid(),
                data.getInstallationdate(),
                jsonBody,
                false,
                getContext(DATA_TOKEN.name()),
                false,
        false,
                getContext(SESSION_ID.name())

        );


    }

    @When("I send a POST request to {string} with a {string} to get fees")
    public void iSendAPOSTRequestToWithAToGetFees(String endpoint, String id) {
        GetFees data = TestDataLoader.getGetFeesData(id);

        GetFeesRequest requestBody = new GetFeesRequest();
        requestBody.setAmount(data.getAmount());
        requestBody.setService_name(data.getService_name());
        requestBody.setDebit_accountnumber(data.getDebit_accountnumber());
        requestBody.setCredit_accountnumber(data.getCredit_accountnumber());
        String jsonBody = convertObjectToJson(requestBody);
        Response response = HttpApiUtils.requestHeaders(
                "POST",
                getParameterProperties(endpoint),
                getContext(ACCESS_TOKEN.name()),
                data.getDeviceuuid(),
                data.getInstallationdate(),
                jsonBody,
                false,
                true,
                null,
                false,
                false,
                getContext(SESSION_ID.name())


        );
        String dataToken = response.jsonPath().getString("data.datatoken");
        if (dataToken != null) {
            setContext(DATA_TOKEN.name(), dataToken);
        } else {
            System.out.println("⚠️ dataToken is null – negative test case, skipping context storage");
        }

    }

    @When("the client sends a POST request to {string} using test data {string}")
    public void theClientSendsAPOSTRequestToUsingTestData(String endpoint, String id) {
        GetFees data = TestDataLoader.getGetFeesData(id);
        GetFeesRequest requestBody = new GetFeesRequest();
        requestBody.setAmount(data.getAmount());
        requestBody.setService_name(data.getService_name());
        requestBody.setDebit_accountnumber(data.getDebit_accountnumber());
        requestBody.setCredit_accountnumber(data.getCredit_accountnumber());
        String jsonBody = convertObjectToJson(requestBody);
        Response response = HttpApiUtils.requestHeaders(
                "POST",
                getParameterProperties(endpoint),
                getContext(ACCESS_TOKEN.name()),
                data.getDeviceuuid(),
                data.getInstallationdate(),
                jsonBody,
                false,
                true,
                null,
                false,
                false,
                getContext(SESSION_ID.name())
        );
        String dataToken = response.jsonPath().getString("data.datatoken");
        if (dataToken != null) {
            setContext(DATA_TOKEN.name(), dataToken);
        } else {
            System.out.println("⚠️ dataToken is null – negative test case, skipping context storage");
        }

    }


    @When("the client sends a POST request to {string} using test data {string} to accept the money request")
    public void theClientSendsAPOSTRequestToUsingTestDataToAcceptTheMoneyRequest(String endpoint, String id) {
        GetFees data = TestDataLoader.getGetFeesData(id);
        GetFeesRequest requestBody = new GetFeesRequest();
        requestBody.setAmount(data.getAmount());
        requestBody.setService_name(data.getService_name());
        requestBody.setDebit_accountnumber(data.getDebit_accountnumber());
        String requestID=getContext(REQUEST_ID.name());
        requestBody.setCredit_accountnumber(requestID);
////        String jsonBody = convertObjectToJson(requestBody);
//        Map<String,Object> requestBody = Map.of(  "amount",data.getAmount(),
//                                                  "service_name",data.getService_name(),
//                                                  "debit_accountnumber",data.getDebit_accountnumber(),
//                                                  "credit_accountnumber",getContext(REQUEST_ID.name()));
        Response response = HttpApiUtils.requestHeaders(
                "POST",
                getParameterProperties(endpoint),
                getContext(ACCESS_TOKEN.name()),
                data.getDeviceuuid(),
                data.getInstallationdate(),
                convertObjectToJson(requestBody),
                false,
                false,
                null,
                true,
                false,
                getContext(SESSION_ID.name())
        );
        String dataToken = response.jsonPath().getString("data.datatoken");
        if (dataToken != null) {
            setContext(DATA_TOKEN.name(), dataToken);
        } else {
            System.out.println("⚠️ dataToken is null – negative test case, skipping context storage");
        }



    }

}

