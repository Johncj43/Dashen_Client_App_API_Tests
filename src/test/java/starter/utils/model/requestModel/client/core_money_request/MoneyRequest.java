package starter.utils.model.requestModel.client.core_money_request;

import lombok.Data;

@Data
public class MoneyRequest {
    private String id;
    private String sourceapp;
    private String deviceuuid;
    private String installationdate;
    private String appversion;
    private String platform;
    private String request_sender_account;
    private String amount;
    private String reason;

}
