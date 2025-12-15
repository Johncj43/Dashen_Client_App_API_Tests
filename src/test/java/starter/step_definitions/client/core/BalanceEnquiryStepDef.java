package starter.step_definitions.client.core;

import io.cucumber.java.PendingException;
import io.cucumber.java.en.When;
import starter.services.commonUtils.CommonUtil;

public class BalanceEnquiryStepDef {
    @When("the client sends {string} request with {string} to perform a balance enquiry")
    public void theClientSendsRequestWithToPerformABalanceEnquiry(String endpoint, String id) {
        CommonUtil.performAccountLookup(endpoint,id);

    }
}
