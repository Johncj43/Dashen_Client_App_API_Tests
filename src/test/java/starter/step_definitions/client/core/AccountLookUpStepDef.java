package starter.step_definitions.client.core;
import io.cucumber.java.PendingException;
import io.cucumber.java.en.When;
import starter.services.commonUtils.CommonUtil;
public class AccountLookUpStepDef {
    @When("the client sends {string} request with {string} to perform an account lookup")
    public void theClientSendsRequestWithToPerformAnAccountLookup(String endpoint, String id) {
        CommonUtil.performAccountLookup(endpoint,id);
}


}
