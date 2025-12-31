package starter.step_definitions.client.hq;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import starter.utils.HttpApiUtils;
import starter.utils.TestDataLoader;
import starter.utils.model.requestModel.client.core.InputValidationData;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static starter.utils.PropertiesReader.getParameterProperties;
import static starter.utils.TestGlobalVariables.ContextEnum.*;
import static starter.utils.TestGlobalVariables.getContext;
import static starter.utils.TestGlobalVariables.setContext;


public class InputValidationStepDef {
    @When("device user {string} sends {string} for input validation")
    public void deviceUserSendsForInputValidation(String id, String endpoint) {

        InputValidationData inputValidationData = TestDataLoader.getInputValidationData(id);

        String deviceUuid = inputValidationData.getDeviceuuid();
        String entityType = inputValidationData.getEntity_type();
        String validationFor = inputValidationData.getValidation_for();
        String installation = inputValidationData.getInstallationdate();

        Map<String, Object> queryParams = new HashMap<>();
        queryParams.put("entity_type", entityType);
        queryParams.put("validation_for", validationFor);

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
        System.out.println("Encrypted account lookup response: " + response.getBody().asString());
    }

    @Then("the response should contain a non-empty field named {string}")
    public void the_response_should_contain_a_non_empty_field_named(String fieldName) {
        Response response = getContext(HTTP_RESPONSE.name());

        response.then().statusCode(200);

        Object fieldValue = response.jsonPath().get(fieldName);

        assertNotNull(fieldValue, "Field '" + fieldName + "' is missing in response");

        if (fieldValue instanceof List) {
            assertFalse(((List<?>) fieldValue).isEmpty(),
                    "Field '" + fieldName + "' is empty");
        } else if (fieldValue instanceof Map) {
            assertFalse(((Map<?, ?>) fieldValue).isEmpty(),
                    "Field '" + fieldName + "' is empty");
        } else if (fieldValue instanceof String) {
            assertFalse(((String) fieldValue).trim().isEmpty(),
                    "Field '" + fieldName + "' is empty");
        } else {
            // for numbers / booleans / objects
            assertNotNull(fieldValue,
                    "Field '" + fieldName + "' is null");
        }
    }

}
