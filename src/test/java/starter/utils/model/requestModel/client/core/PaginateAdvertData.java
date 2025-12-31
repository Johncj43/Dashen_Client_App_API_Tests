package starter.utils.model.requestModel.client.core;

import lombok.Data;

@Data
public class PaginateAdvertData {
    private String id;
    private String installationdate;
    private String deviceuuid;
    private String page;
    private String limit;


}
