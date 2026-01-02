package starter.step_definitions.client.utilities;

import io.cucumber.java.en.When;
import io.restassured.response.Response;
import starter.utils.HttpApiUtils;
import starter.utils.TestDataLoader;
import starter.utils.model.requestModel.client.utilities.FeedbackRequest;

import java.util.HashMap;
import java.util.Map;

import static starter.utils.HelperUtils.convertObjectToJson;
import static starter.utils.PropertiesReader.getParameterProperties;
import static starter.utils.TestGlobalVariables.ContextEnum.*;
import static starter.utils.TestGlobalVariables.setContext;

public class SendFeedbackStepDef {


    @When("device user {string} sends {string} for feedback")
    public void device_user_sends_for_feedback(String id, String endpoint) {


        FeedbackRequest feedbackRequest = TestDataLoader.getFeedbackRequestData(id);

        String deviceUuid = feedbackRequest.getDeviceuuid();
        String installation = feedbackRequest.getInstallationdate();
        String userName = feedbackRequest.getUsername();
        String feedBack = feedbackRequest.getFeedback();
        Integer rating = feedbackRequest.getRating();

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("username", userName);
        requestBody.put("feedback", feedBack);
        requestBody.put("rating", rating);
        requestBody.put("survey_response", feedbackRequest.getSurvey_response());


        Response response = HttpApiUtils.requestWithCoresHeaders3(
                "POST",
                getParameterProperties(endpoint),
                deviceUuid,
                null,
                installation,
                null,
                false,
                convertObjectToJson(requestBody)
        );

        setContext(HTTP_RESPONSE.name(), response);


    }

}
