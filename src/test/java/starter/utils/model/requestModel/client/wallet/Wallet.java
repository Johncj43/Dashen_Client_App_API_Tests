package starter.utils.model.requestModel.client.wallet;

import lombok.Data;

@Data
public class Wallet {
    private String id;
    private String phone_number;
    private String deviceuuid;
    private String installationdate;
    private String platform;
    private String appversion;
    private String sourceapp;
    private String token;
}
