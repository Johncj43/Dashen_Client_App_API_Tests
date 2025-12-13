package starter.utils.model.requestModel.client.services;

import lombok.Data;

@Data
public class GetFees {
    private String id;
    private String appversion;
    private String installationdate;
    private String deviceuuid;
    private int amount;
    private String service_name;
    private String debit_accountnumber;
    private String credit_accountnumber;
    private String platform;
    private String sourceapp;
}
