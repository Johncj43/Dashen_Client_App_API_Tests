package starter.utils.model.requestModel.client.chat;

import lombok.Data;

@Data
public class RequestChatMoneyRequest {
    private String phone_number;
    private String account_number;
    private int amount;
}
