package starter.step_definitions.client.transaction;

import io.cucumber.java.PendingException;
import io.cucumber.java.en.And;
import io.restassured.response.Response;
import starter.utils.HttpApiUtils;
import starter.utils.TestDataLoader;
import starter.utils.model.requestModel.client.transaction.RequestPinVerify;
import starter.utils.model.requestModel.client.transaction.VerifyTransaction;

import static starter.utils.HelperUtils.convertObjectToJson;
import static starter.utils.PropertiesReader.getParameterProperties;
import static starter.utils.TestGlobalVariables.ContextEnum.*;
import static starter.utils.TestGlobalVariables.getContext;
import static starter.utils.TestGlobalVariables.setContext;

public class PinVerifyMethodStepDef {
    @And("the client sends a POST request to {string} with {string} to verify the PIN for the transaction")
    public void theClientSendsAPOSTRequestToWithToVerifyThePINForTheTransaction(String endpoint, String id) {
        VerifyTransaction data= TestDataLoader.getVerifyTransactionData(id);
        RequestPinVerify pinBody = new RequestPinVerify();
        pinBody.setPincode(data.getPincode());
        Response response = HttpApiUtils.requestWithHeaders(
                "POST",
                getParameterProperties(endpoint),
                getContext(ACCESS_TOKEN.name()),
                data.getDeviceuuid(),
                data.getInstallationdate(),
                convertObjectToJson(pinBody),
                false,
                getContext(DATA_TOKEN.name()),
                true,
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
}
