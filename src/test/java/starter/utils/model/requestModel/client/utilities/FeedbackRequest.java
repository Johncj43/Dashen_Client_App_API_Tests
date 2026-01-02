package starter.utils.model.requestModel.client.utilities;

import java.util.List;

import lombok.Data;

@Data
public class FeedbackRequest {

    private String id;
    private String deviceuuid;
    private String installationdate;
    private String username;
    private String feedback;
    private Integer rating;
    private List<SurveyResponse> survey_response;
}
