package starter.step_definitions.client.utilities;

import io.cucumber.java.en.When;
import io.restassured.response.Response;
import starter.utils.HttpApiUtils;
import starter.utils.TestDataLoader;
import starter.utils.model.requestModel.client.utilities.QueryStudentData;

import java.util.HashMap;
import java.util.Map;

import static starter.utils.PropertiesReader.getParameterProperties;
import static starter.utils.TestGlobalVariables.ContextEnum.*;
import static starter.utils.TestGlobalVariables.getContext;
import static starter.utils.TestGlobalVariables.setContext;

public class QueryUnpaidPayments {
    @When("device user {string} sends {string} for unpaid payment lookup")
    public void deviceUserSendsForUnpaidPaymentLookup(String id, String endpoint) {

        QueryStudentData queryStudent = TestDataLoader.getQueryStudentData(id);

        String deviceUuid = queryStudent.getDeviceuuid();

        String installation = queryStudent.getInstallationdate();
        Response response = HttpApiUtils.requestWithCoresHeaders4(
                "GET",
                getParameterProperties(endpoint),
                deviceUuid,
                getContext(ACCESS_TOKEN.name()),
                installation,
                getContext(SESSION_ID.name()),
                getContext(DATA_TOKEN.name()),
                true,
                0,
                null
        );

        setContext(HTTP_RESPONSE.name(), response);

    }
}
