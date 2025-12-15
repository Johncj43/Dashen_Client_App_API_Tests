package starter.step_definitions.client.topup;

import io.cucumber.java.PendingException;
import io.cucumber.java.en.And;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.htmlunit.html.Html;
import starter.utils.HttpApiUtils;
import starter.utils.TestDataLoader;
import starter.utils.model.requestModel.client.topup.Topup;

import static starter.utils.PropertiesReader.getParameterProperties;
import static starter.utils.TestGlobalVariables.ContextEnum.*;
import static starter.utils.TestGlobalVariables.getContext;

public class TopupStepDef {
    @And("the client sends {string} request with {string} to perform topup with ethiotelecom")
    public void theClientSendsRequestWithToPerformTopupWithEthiotelecom(String endpoint, String id) {
        Topup data= TestDataLoader.getTopupData(id);
        String installationdate=data.getInstallationdate();
        String deviceuuid=data.getDeviceuuid();
        Response response = HttpApiUtils.requestWithHeaders(
                "POST",
                getParameterProperties(endpoint),
                getContext(ACCESS_TOKEN.name()),
                deviceuuid,
                installationdate,
                null,
                false,
                getContext(DATA_TOKEN.name()),
                true,
                true,
                getContext(SESSION_ID.name())



        );




    }


}
