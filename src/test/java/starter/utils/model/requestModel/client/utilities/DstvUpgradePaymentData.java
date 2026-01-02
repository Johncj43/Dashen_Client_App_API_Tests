package starter.utils.model.requestModel.client.utilities;

import lombok.Data;


@Data

public class DstvUpgradePaymentData {
    private String id;
    private String installationdate;
    private String deviceuuid;
    private String product_code;
    private Integer package_time;

}
