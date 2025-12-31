package starter.utils.model.requestModel.client.core;

import lombok.Data;


@Data
public class InputValidationData {
    private String id;
    private String installationdate;
    private String deviceuuid;
    private String entity_type;
    private String validation_for;


}
