package starter.utils.model.requestModel.client.transaction;

import lombok.Data;

@Data
public class VerifyTransaction {
    private String id;
    private String installationdate;
    private String deviceuuid;
    private String sourceapp;
    private String appversion;
    private String platform;
    private String pincode;
    private String credit_accountnumber;

}