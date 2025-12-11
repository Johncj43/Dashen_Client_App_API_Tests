package starter.utils.model.requestModel.client;

import lombok.Data;

@Data
public class ValidUser {
    private String id;
    private String deviceuuid;
    private String platform;
    private String appversion;
    private String installationdate;
    private String sourceapp;
}
