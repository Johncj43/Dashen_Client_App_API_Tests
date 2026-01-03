package starter.step_definitions.client.core_money_request;

import io.cucumber.java.en.And;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import starter.utils.HttpApiUtils;
import starter.utils.TestDataLoader;
import starter.utils.model.requestModel.client.core.AccountLookup;
import starter.utils.model.requestModel.client.core.RequestAccountLookup;
import starter.utils.model.requestModel.client.core_money_request.MoneyRequest;
import starter.utils.model.requestModel.client.core_money_request.RequestMoneyRequest;
import starter.utils.model.requestModel.client.core_money_request.RequestReject;
import starter.utils.model.requestModel.client.services.GetFees;
import starter.utils.model.requestModel.client.services.GetFeesRequest;
import starter.utils.model.requestModel.client.transaction.RequestVerifyTransaction;
import starter.utils.model.requestModel.client.transaction.VerifyTransaction;

import java.util.HashMap;
import java.util.Map;

import static starter.utils.HelperUtils.convertObjectToJson;
import static starter.utils.PropertiesReader.getParameterProperties;
import static starter.utils.TestGlobalVariables.ContextEnum.*;
import static starter.utils.TestGlobalVariables.getContext;
import static starter.utils.TestGlobalVariables.setContext;

public class MoneyRequestStepDef {

    @When("the client sends a POST request to {string} using the payload {string} to lookup the recipient account for the money request")
    public void theClientSendsAPOSTRequestToUsingThePayloadToLookupTheRecipientAccountForTheMoneyRequest(String endpoint, String id) {
        AccountLookup data= TestDataLoader.getAccountLookupData(id);
        RequestAccountLookup requestBody = new RequestAccountLookup();
        requestBody.setAccount_number(data.getAccount_number());
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
                false,
                true,
                getContext(SESSION_ID.name()),
                true
        );
        setContext(HTTP_RESPONSE.name(), response);

        String dataToken = response.jsonPath().getString("data.datatoken");
        if (dataToken != null) {
            setContext(DATA_TOKEN.name(), dataToken);
        } else {
            System.out.println("⚠️ dataToken is null – negative test case, skipping context storage");
        }


    }


    @And("the client sends a POST request to {string} using the payload {string} to request money")
    public void theClientSendsAPOSTRequestToUsingThePayloadToRequestMoney(String endpoint, String id) {
        MoneyRequest data = TestDataLoader.getMoneyRequestData(id);
        RequestMoneyRequest requestBody = new RequestMoneyRequest();
        requestBody.setRequest_sender_account(data.getRequest_sender_account());
        requestBody.setAmount(data.getAmount());
        Response response = HttpApiUtils.requestHeaders(
                "POST",
                getParameterProperties(endpoint),
                getContext(ACCESS_TOKEN.name()),
                data.getDeviceuuid(),
                data.getInstallationdate(),
                convertObjectToJson(requestBody),
                false,
                false,
                getContext(DATA_TOKEN.name()),
                false,
                false,
                getContext(SESSION_ID.name()),
                false


        );
        setContext(HTTP_RESPONSE.name(), response);

        String requestId= response.jsonPath().getString("data.requestId");
        if (requestId!= null) {
            setContext(REQUEST_ID.name(), requestId);
            System.out.println("✅ Request ID: " + requestId);
        } else {
            System.out.println("⚠️ dataToken is null – negative test case, skipping context storage");
        }


    }

    @When("the client sends a POST request to {string} using the payload {string} to retrieve fees for accepting the money request")
    public void theClientSendsAPOSTRequestToUsingThePayloadToRetrieveFeesForAcceptingTheMoneyRequest(String endpoint, String id) {
        GetFees data = TestDataLoader.getGetFeesData(id);

        GetFeesRequest requestBody = new GetFeesRequest();
        requestBody.setAmount(data.getAmount());
        requestBody.setService_name(data.getService_name());
        requestBody.setDebit_accountnumber(data.getDebit_accountnumber());
        String requestID=getContext(REQUEST_ID.name());
        requestBody.setCredit_accountnumber(requestID);
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
                getContext(SESSION_ID.name()),
                false


        );
        setContext(HTTP_RESPONSE.name(), response);

        String dataToken = response.jsonPath().getString("data.datatoken");
        if (dataToken != null) {
            setContext(DATA_TOKEN.name(), dataToken);
        } else {
            System.out.println("⚠️ dataToken is null – negative test case, skipping context storage");
        }

    }

    @And("the client sends a POST request to {string} using the payload {string} to verify the transaction")
    public void theClientSendsAPOSTRequestToUsingThePayloadToVerifyTheTransaction(String endpoint, String id) {
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
                false,
                getContext(DATA_TOKEN.name()),
                false,
                false,
                getContext(SESSION_ID.name())
        );
        setContext(HTTP_RESPONSE.name(), response);

        String dataToken= response.jsonPath().getString("data.datatoken");
        if (dataToken != null) {
            setContext(DATA_TOKEN.name(), dataToken);
        } else {
            System.out.println("⚠️ dataToken is null – negative test case, skipping context storage");
        }

    }

    @And("the client sends a POST request to {string} with payload {string} to accept the money request")
    public void theClientSendsAPOSTRequestToWithPayloadToAcceptTheMoneyRequest(String endpoint, String id) {
        MoneyRequest data = TestDataLoader.getMoneyRequestData(id);
        Response response = HttpApiUtils.requestWithHeaders(
                "POST",
                getParameterProperties(endpoint),
                getContext(ACCESS_TOKEN.name()),
                data.getDeviceuuid(),
                data.getInstallationdate(),
                null,
                false,
                getContext(DATA_TOKEN.name()),
                false,
                false,
                getContext(SESSION_ID.name())
        );
        setContext(HTTP_RESPONSE.name(), response);



    }

    @And("the client sends a DELETE request to {string} with payload {string} to cancel the pending action")
    public void theClientSendsADELETERequestToWithPayloadToCancelThePendingAction(String endpoint, String id) {
        MoneyRequest data = TestDataLoader.getMoneyRequestData(id);
        String endpointKey= getParameterProperties(endpoint)+"/"+getContext(REQUEST_ID.name());
        Response response = HttpApiUtils.requestWithHeaders(
                "DELETE",
                endpointKey,
                getContext(ACCESS_TOKEN.name()),
                data.getDeviceuuid(),
                data.getInstallationdate(),
                null,
                false,
                null,
                false,
                false,
                getContext(SESSION_ID.name())
        );
        setContext(HTTP_RESPONSE.name(), response);


    }

    @When("the recipient sends a DELETE request to {string} with {string} to reject the request money")
    public void theRecipientSendsADELETERequestToWithToRejectTheRequestMoney(String endpoint, String id) {
        MoneyRequest data = TestDataLoader.getMoneyRequestData(id);
        String endpointKey= getParameterProperties(endpoint)+"/"+getContext(REQUEST_ID.name());
        RequestReject requestBody = new RequestReject();
        requestBody.setReason(data.getReason());
        Response response = HttpApiUtils.requestWithHeaders(
                "DELETE",
                endpointKey,
                getContext(ACCESS_TOKEN.name()),
                data.getDeviceuuid(),
                data.getInstallationdate(),
                requestBody,
                false,
                null,
                false,
                false,
                getContext(SESSION_ID.name())
        );
        setContext(HTTP_RESPONSE.name(), response);


    }

    @And("the client sends a GET request to {string} with test data {string} to fetch the list of beneficiaries")
    public void theClientSendsAGETRequestToWithTestDataToFetchTheListOfBeneficiaries(String endpoint, String id) {
              MoneyRequest data = TestDataLoader.getMoneyRequestData(id);
        Map<String,Object> queryParams = new HashMap<>();
        queryParams.put("request_source","CORE");
        Response response = HttpApiUtils.requestwithCoreTransaction(
                "GET",
                getContext(ACCESS_TOKEN.name()),
                getParameterProperties(endpoint),
                data.getDeviceuuid(),
                data.getInstallationdate(),
                null,
                queryParams,
                null,
                getContext(SESSION_ID.name())

        );
        setContext(HTTP_RESPONSE.name(), response);

    }

    @And("the client sends a GET request to {string} with test data {string} to get the list of sent requests")
    public void theClientSendsAGETRequestToWithTestDataToGetTheListOfSentRequests(String endpoint, String id) {
        MoneyRequest data = TestDataLoader.getMoneyRequestData(id);
        Response response = HttpApiUtils.requestwithCoreTransaction(
                "GET",
                getContext(ACCESS_TOKEN.name()),
                getParameterProperties(endpoint),
                data.getDeviceuuid(),
                data.getInstallationdate(),
                null,
                null,
                null,
                getContext(SESSION_ID.name())

        );
        setContext(HTTP_RESPONSE.name(), response);


    }

    @And("the client sends a GET request to {string} with test data {string} to get the list of received requests")
    public void theClientSendsAGETRequestToWithTestDataToGetTheListOfReceivedRequests(String endpoint, String id) {
        MoneyRequest data = TestDataLoader.getMoneyRequestData(id);
        Response response = HttpApiUtils.requestwithCoreTransaction(
                "GET",
                getContext(ACCESS_TOKEN.name()),
                getParameterProperties(endpoint),
                data.getDeviceuuid(),
                data.getInstallationdate(),
                null,
                null,
                null,
                getContext(SESSION_ID.name())

        );
        setContext(HTTP_RESPONSE.name(), response);

    }
}
