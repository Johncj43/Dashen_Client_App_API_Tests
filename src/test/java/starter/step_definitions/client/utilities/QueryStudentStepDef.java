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

public class QueryStudentStepDef {
    @When("device user {string} sends {string} for student lookup")
    public void deviceUserSendsForStudentLookup(String id, String endpoint) {

        QueryStudentData queryStudent = TestDataLoader.getQueryStudentData(id);
        String deviceUuid = queryStudent.getDeviceuuid();
        String studentId = queryStudent.getStudent_id();
        String installation = queryStudent.getInstallationdate();

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
        String dataToken = response.jsonPath().getString("data.datatoken");

        setContext(DATA_TOKEN.name(), dataToken);

    }
}
