package starter.utils.model.requestModel.auth;

import lombok.Data;

@Data

public class HeaderData {
    private String id;
    private String deviceuuid;
    private String installationdate;
    private String sourceapp;
    private String platform;
    private String appversion;
    private String pincode;
    private String oldpin;
    private String newpin;
    private String phonenumber;
    private String otpfor;
    private String token;

}
