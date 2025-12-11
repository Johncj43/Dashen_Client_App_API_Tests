package starter.utils.model.requestModel.auth;

import lombok.Data;

@Data
public class PinStrength {
    private String id;
    private String deviceuuid;
    private String installationdate;
    private String newpin;
}
