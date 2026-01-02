package starter.utils.model.requestModel.client.transaction;

import lombok.Data;

@Data
public class Beneficiaries {
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
    private String qr_value;


}
