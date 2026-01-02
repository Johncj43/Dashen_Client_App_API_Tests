package starter.utils.model.requestModel.client.transaction;

import lombok.Data;

@Data
public class VerifyQrData {
    private String id;
    private String installationdate;
    private String deviceuuid;
    private String qr_value;


}