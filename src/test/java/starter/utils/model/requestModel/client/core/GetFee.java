package starter.utils.model.requestModel.client.core;

import lombok.Data;

@Data
public class GetFee {
    private String id;
    private String installationdate;
    private String appversion;
    private String deviceuuid;
    private String platform;
    private String sourceapp;
    private String pincode;
    private String token;
    private String emailOrPhone;
    private String account_number;
    private String email;
    private Integer amount;
    private String service_name;
    private String debit_accountnumber;
    private String credit_accountnumber;


}
