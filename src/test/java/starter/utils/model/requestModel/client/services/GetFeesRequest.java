package starter.utils.model.requestModel.client.services;

import lombok.Data;

@Data
public class GetFeesRequest {
    private int amount;
    private String service_name;
    private String debit_accountnumber;
    private String credit_accountnumber;

}
