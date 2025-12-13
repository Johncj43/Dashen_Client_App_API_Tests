package starter.step_definitions.client.services;

import io.cucumber.java.en.When;
import io.restassured.response.Response;
import starter.utils.HttpApiUtils;
import starter.utils.TestDataLoader;
import starter.utils.model.requestModel.client.services.GetFees;
import starter.utils.model.requestModel.client.services.GetFeesRequest;

import static starter.utils.HelperUtils.convertObjectToJson;
import static starter.utils.PropertiesReader.getParameterProperties;
import static starter.utils.TestGlobalVariables.ContextEnum.ACCESS_TOKEN;
import static starter.utils.TestGlobalVariables.getContext;

public class GetFeesStepDef {



        @When("I send a POST request to {string} with a {string} to create get fees")
        public void iSendAPOSTRequestToWithAToCreateGetFees(String endpoint, String id) {
            Response response = sendGetFeesRequest(endpoint, id);

        }


    /**
     * Sends a POST request to get fees, using a DTO with setters for the request body.
     *
     * @param endpointKey The properties key for the API endpoint.
     * @param id          The ID to load the specific test data.
     * @return The Response from the API call.
     */
    private Response sendGetFeesRequest(String endpointKey, String id) {
        GetFees data = TestDataLoader.getGetFeesData(id);
        GetFeesRequest requestBody = new GetFeesRequest();
        requestBody.setAmount(data.getAmount());
        requestBody.setService_name(data.getService_name());
        requestBody.setDebit_accountnumber(data.getDebit_accountnumber());
        requestBody.setCredit_accountnumber(data.getCredit_accountnumber());

        String jsonBody = convertObjectToJson(requestBody);

        return HttpApiUtils.requestWithStandardHeadersSimples(
                "POST",
                getContext(ACCESS_TOKEN.name()),
                getParameterProperties(endpointKey),
                data.getDeviceuuid(),
                data.getInstallationdate(),
                getContext("SESSION_ID"),
                jsonBody
        );
    }

}

