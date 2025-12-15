package starter.utils.model.requestModel.client.topup;

import lombok.Data;

@Data
public class Topup {
    private String id;
    private String appversion;
    private String deviceuuid;
    private String installationdate;
    private String platform;
    private String sourceapp;
}
