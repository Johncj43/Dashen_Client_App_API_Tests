package starter.step_definitions.client.wallet;
import io.cucumber.java.PendingException;
import io.cucumber.java.en.And;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import starter.utils.HttpApiUtils;
import starter.utils.TestDataLoader;
import starter.utils.model.requestModel.client.wallet.RequestPhoneNumber;
import starter.utils.model.requestModel.client.wallet.Wallet;


import static starter.utils.HelperUtils.convertObjectToJson;
import static starter.utils.PropertiesReader.getParameterProperties;
import static starter.utils.TestGlobalVariables.ContextEnum.ACCESS_TOKEN;
import static starter.utils.TestGlobalVariables.ContextEnum.SESSION_ID;
import static starter.utils.TestGlobalVariables.getContext;

public class MpesaAccountLookupStepDef {
    @When("the client sends a POST request to {string} with {string} to perform account lookup for Mpesa wallet")
    public void theClientSendsAPOSTRequestToWithToPerformAccountLookupForMpesaWallet(String endpoint, String id) {
        Wallet data= TestDataLoader.getWalletData(id);
        RequestPhoneNumber body = new RequestPhoneNumber();
        body.setPhone_number(data.getPhone_number());
        Response response = HttpApiUtils.requestWithHeaders(
                "POST",
                getParameterProperties(endpoint),
                getContext(ACCESS_TOKEN.name()),
                data.getDeviceuuid(),
                data.getInstallationdate(),
                convertObjectToJson(body),
                false,
                null,
                false,
                true,
                getContext(SESSION_ID.name())

        );

    }

}
