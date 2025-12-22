package starter.step_definitions.client.topup;

import io.cucumber.java.PendingException;
import io.cucumber.java.en.And;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.htmlunit.html.Html;
import starter.services.commonUtils.CommonUtil;
import starter.utils.HttpApiUtils;
import starter.utils.TestDataLoader;
import starter.utils.model.requestModel.client.topup.Topup;

import static starter.utils.PropertiesReader.getParameterProperties;
import static starter.utils.TestGlobalVariables.ContextEnum.*;
import static starter.utils.TestGlobalVariables.getContext;
import static starter.utils.TestGlobalVariables.setContext;

public class TopupStepDef {
    @And("the client sends {string} request with {string} to perform topup with ethiotelecom")
    public void theClientSendsRequestWithToPerformTopupWithEthiotelecom(String endpoint, String id) {
        CommonUtil.performCommonTransaction("POST",endpoint,id,null);

    }
    @And("the client sends {string} request with {string} to perform topup with Safaricom")
    public void theClientSendsRequestWithToPerformTopupWithSafaricom(String endpoint, String id) {
      CommonUtil.performCommonTransaction("POST",endpoint,id,null);
    }
}
