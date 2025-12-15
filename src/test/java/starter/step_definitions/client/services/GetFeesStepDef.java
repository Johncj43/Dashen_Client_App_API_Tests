package starter.step_definitions.client.services;

import io.cucumber.java.PendingException;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import starter.utils.HttpApiUtils;
import starter.utils.TestDataLoader;
import starter.utils.model.requestModel.client.services.GetFees;
import starter.utils.model.requestModel.client.services.GetFeesRequest;

import static starter.utils.HelperUtils.convertObjectToJson;
import static starter.utils.PropertiesReader.getParameterProperties;
import static starter.utils.TestGlobalVariables.ContextEnum.*;
import static starter.utils.TestGlobalVariables.getContext;
import static starter.utils.TestGlobalVariables.setContext;

public class GetFeesStepDef {


    @When("I send a POST request to {string} with a {string} to create get fees")
    public void iSendAPOSTRequestToWithAToCreateGetFees(String endpoint, String id) {
        Response response = sendGetFeesRequest("POST", endpoint, id, getContext(ACCESS_TOKEN.name()), getContext(SESSION_ID.name()));
        String dataToken=response.jsonPath().getString("data.datatoken");
//        if (dataToken == null || dataToken.isEmpty()) {
//            throw new AssertionError("Response did not contain a 'data.datatoken' field.");
//        }
        setContext(DATA_TOKEN.name(),dataToken);
    }

    @When("I send a POST request to {string} with a {string} to get fees with an invalid access token")
    public void iSendAPOSTRequestToWithAToGetFeesWithAnInvalidAccessToken(String endpoint, String id) {
        Response response = sendGetFeesRequest("POST", endpoint, id, null,getContext(SESSION_ID.name()));

    }
    @When("I send a POST request to {string} with a {string} to get fees with expired session id")
    public void iSendAPOSTRequestToWithAToGetFeesWithExpiredSessionId(String enpoint, String id) {
        Response response = sendGetFeesRequest("POST",enpoint,id,getContext(ACCESS_TOKEN.name()),null);
    }

    /**
     * Sends a POST request to get fees, using a DTO with setters for the request body.
     * This method is now flexible to accept different authentication tokens.
     *
     * @param httpMethod The HTTP method, e.g., "POST".
     * @param endpointKey The properties key for the API endpoint.
     * @param id The ID to load the specific test data.
     * @param accessToken The access token to use for authentication. Can be null.
     * @param sessionId The session ID to use. Can be null.
     * @return The Response from the API call.
     */

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


}

