package starter.step_definitions.client.wallet;

import io.cucumber.java.PendingException;
import io.cucumber.java.en.And;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.junit.Test;
import starter.utils.HttpApiUtils;
import starter.utils.TestDataLoader;
import starter.utils.model.requestModel.client.wallet.RequestPhoneNumber;
import starter.utils.model.requestModel.client.wallet.Wallet;

import static starter.utils.HelperUtils.convertObjectToJson;
import static starter.utils.PropertiesReader.getParameterProperties;
import static starter.utils.TestGlobalVariables.ContextEnum.*;
import static starter.utils.TestGlobalVariables.getContext;
import static starter.utils.TestGlobalVariables.setContext;

public class TeleBirrAccountLookupStepDef {
    @When("the client sends a POST request to {string} with {string} to perform account lookup for Tele Birr")
    public void theClientSendsAPOSTRequestToWithToPerformAccountLookupForTeleBirr(String endpoint, String id) {
        Response response = sendRequest("POST", endpoint, id, true);
        String dataToken = response.jsonPath().getString("data.datatoken");
        if (dataToken != null) {
            setContext(DATA_TOKEN.name(), dataToken);
        } else {
            System.out.println("⚠️ dataToken is null – negative test case, skipping context storage");
        }

    }

    @When("the client sends a POST request to {string} with {string} without a valid access token")
    public void theClientSendsAPOSTRequestToWithWithoutAValidAccessToken(String endpoint, String id) {
        Response response = sendRequest("POST", endpoint, id, false);
    }

    @When("the client sends a GET request to {string} with {string}")
    public void theClientSendsAPATCHRequestToWith(String endpoint, String id) {
        Response response = sendRequest("GET", endpoint, id, true);
    }

    private Response sendRequest(String method, String endpoint, String id, boolean useValidToken) {
        Wallet data = TestDataLoader.getWalletData(id);
        String installationdate = data.getInstallationdate();
        String deviceuuid = data.getDeviceuuid();
        String token = useValidToken ? getContext(ACCESS_TOKEN.name()) : data.getToken();
        RequestPhoneNumber requestBody = new RequestPhoneNumber();
        requestBody.setPhone_number(data.getPhone_number());
        return HttpApiUtils.requestWithHeaders(
                method,
                getParameterProperties(endpoint),
                token,
                deviceuuid,
                installationdate,
                convertObjectToJson(requestBody),
                false,
                null,
                false,
                true,
                getContext(SESSION_ID.name())
        );
    }

}
