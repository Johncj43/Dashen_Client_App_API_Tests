package starter.step_definitions.client.utilities;

import io.cucumber.java.en.When;
import io.restassured.response.Response;
import starter.utils.HttpApiUtils;
import starter.utils.TestDataLoader;
import starter.utils.model.requestModel.client.utilities.FetchStudentPaidDetailsData;

import java.util.HashMap;
import java.util.Map;

import static starter.utils.PropertiesReader.getParameterProperties;
import static starter.utils.TestGlobalVariables.ContextEnum.*;
import static starter.utils.TestGlobalVariables.getContext;
import static starter.utils.TestGlobalVariables.setContext;

public class QueryPaidPaymentsStepDef {
    @When("device user {string} sends {string} for paid payment lookup")
    public void deviceUserSendsForPaidPaymentLookup(String id, String endpoint) {
        FetchStudentPaidDetailsData fetchStudentPaidDetailsData = TestDataLoader.getFetchStudentPaidDetailsData(id);

        String deviceUuid = fetchStudentPaidDetailsData.getDeviceuuid();
        String studentId = fetchStudentPaidDetailsData.getStudent_id();
        String installation = fetchStudentPaidDetailsData.getInstallationdate();

        Map<String, Object> queryParams = new HashMap<>();
        queryParams.put("student_id", studentId);

        Response response = HttpApiUtils.requestWithCoresHeaders33(
                "GET",
                getParameterProperties(endpoint),
                deviceUuid,
                getContext(ACCESS_TOKEN.name()),
                installation,
                getContext(SESSION_ID.name()),
                true,
                queryParams
        );

        setContext(HTTP_RESPONSE.name(), response);

    }
}


