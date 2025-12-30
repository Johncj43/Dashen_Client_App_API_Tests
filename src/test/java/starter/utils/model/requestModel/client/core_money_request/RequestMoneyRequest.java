package starter.utils.model.requestModel.client.core_money_request;

import lombok.Data;

@Data
public class RequestMoneyRequest {
    private String request_sender_account;
    private String amount;

}
