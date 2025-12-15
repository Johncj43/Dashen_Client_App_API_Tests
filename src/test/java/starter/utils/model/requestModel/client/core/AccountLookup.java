package starter.utils.model.requestModel.client.core;

import lombok.Data;

@Data
public class AccountLookup {
    private String id;
    private String platform;
    private String sourceapp;
    private String account_number;
    private String deviceuuid;
    private String installationdate;
    private String appversion;
}
