package starter.utils.model.requestModel.client.transaction;


import lombok.Data;

@Data
public class FetchCbsTransaction {
    private String id;
    private String installationdate;
    private String deviceuuid;
    private String search_key;


}
