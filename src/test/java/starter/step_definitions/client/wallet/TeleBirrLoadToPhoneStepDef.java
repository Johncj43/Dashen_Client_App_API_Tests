package starter.step_definitions.client.wallet;

import io.cucumber.java.PendingException;
import io.cucumber.java.en.And;
import io.restassured.response.Response;
import starter.utils.HttpApiUtils;
import starter.utils.TestDataLoader;
import starter.utils.model.requestModel.client.wallet.Wallet;

import static starter.utils.PropertiesReader.getParameterProperties;
import static starter.utils.TestGlobalVariables.ContextEnum.*;
import static starter.utils.TestGlobalVariables.getContext;
import static starter.utils.TestGlobalVariables.setContext;

public class TeleBirrLoadToPhoneStepDef {
    @And("the client sends a POST request to {string} with {string} to transfer funds from the account to the TeleBirr wallet")
    public void theClientSendsAPOSTRequestToWithToTransferFundsFromTheAccountToTheTeleBirrWallet(String endpoint, String id) {
        Wallet data = TestDataLoader.getWalletData(id);
        Response response = HttpApiUtils.requestWithHeaders(
                "POST",
                getParameterProperties(endpoint),
                getContext(ACCESS_TOKEN.name()),
                data.getDeviceuuid(),
                data.getInstallationdate(),
                null,
                true,
                getContext(DATA_TOKEN.name()),
                true,
                true,
                getContext(SESSION_ID.name())

        );
        setContext(HTTP_RESPONSE.name(), response);



    }
}
