package starter.utils.model.requestModel.client.chat;

import lombok.Data;

@Data
public class Chat {
    private String id;
    private String installationdate;
    private String deviceuuid;
    private String sourceapp;
    private String platform;
    private String appversion;
    private String phone_number;
}
