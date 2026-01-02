
package starter.step_definitions.client.transaction;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import starter.utils.HttpApiUtils;
import starter.utils.TestDataLoader;
import starter.utils.model.requestModel.client.transaction.Beneficiaries;
import static starter.utils.PropertiesReader.getParameterProperties;
import static starter.utils.TestGlobalVariables.ContextEnum.*;
import static starter.utils.TestGlobalVariables.getContext;
import static starter.utils.TestGlobalVariables.setContext;

public class GetBeneficiaries {

    @When("device user {string} sends {string} for benefactor account lookup")
    public void deviceUserSendsForBenefactorAccountLookup(String id, String endpoint) {

        Beneficiaries beneficiaries = TestDataLoader.getBeneficiariesData(id);

        String deviceUuid = beneficiaries.getDeviceuuid();
        String installation = beneficiaries.getInstallationdate();

        Response response = HttpApiUtils.requestWithCoresHeaders3(
                "GET",
                getParameterProperties(endpoint),
                deviceUuid,
                getContext(ACCESS_TOKEN.name()),
                installation,
                getContext(SESSION_ID.name()),
                true,
                null
        );

        setContext(HTTP_RESPONSE.name(), response);
    }

}
