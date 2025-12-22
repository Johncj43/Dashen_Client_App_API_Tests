package starter.utils.model.requestModel.client.chat;

import lombok.Data;

@Data
public class ChatMoneyRequest {
    private String id;
    private String platform;
    private String appversion;
    private String installationdate;
    private String deviceuuid;
    private String phone_number;
    private String account_number;
    private int amount;
    private String sourceapp;
    private String reason;

}
