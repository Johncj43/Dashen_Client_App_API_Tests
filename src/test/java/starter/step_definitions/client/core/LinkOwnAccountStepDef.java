package starter.step_definitions.client.core;

import io.cucumber.java.en.When;
import io.restassured.response.Response;
import starter.utils.HttpApiUtils;
import starter.utils.TestDataLoader;
import starter.utils.model.requestModel.client.core.LinkOwnAccount;
import starter.utils.model.requestModel.client.core.RequestLinkOwnAccount;


import static starter.utils.HelperUtils.convertObjectToJson;
import static starter.utils.PropertiesReader.getParameterProperties;
import static starter.utils.TestGlobalVariables.ContextEnum.*;
import static starter.utils.TestGlobalVariables.getContext;
import static starter.utils.TestGlobalVariables.setContext;

public class LinkOwnAccountStepDef {

    @When("device user {string} sends {string} for linking own account")
    public void userCreateOwnAccountLink(String id, String endpoint){
        LinkOwnAccount linkOwnAccount = TestDataLoader.getLinkOwnAccountData(id);
        String deviceUuid = linkOwnAccount.getDeviceuuid();
        String installation = linkOwnAccount.getInstallationdate();
        RequestLinkOwnAccount requestBody = new RequestLinkOwnAccount();
        requestBody.setAccount_number(linkOwnAccount.getAccount_number());
        Response response = HttpApiUtils.requestWithCoresHeaders3(
               "POST",
                getParameterProperties(endpoint),
                deviceUuid,
                getContext(ACCESS_TOKEN.name()),
                installation,
                getContext(SESSION_ID.name()),
                true,
                convertObjectToJson(requestBody)
        );

        setContext(HTTP_RESPONSE.name(), response);
        System.out.println("Link Own Account response: " + response.getBody().asString());
    }
}
