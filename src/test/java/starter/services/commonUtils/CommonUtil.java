package starter.services.commonUtils;

import io.restassured.response.Response;
import starter.utils.HttpApiUtils;
import starter.utils.TestDataLoader;
import starter.utils.model.requestModel.client.core.AccountLookup;
import starter.utils.model.requestModel.client.core.RequestAccountLookup;
import starter.utils.model.requestModel.client.topup.Topup;

import static starter.utils.HelperUtils.convertObjectToJson;
import static starter.utils.PropertiesReader.getParameterProperties;
import static starter.utils.TestGlobalVariables.ContextEnum.*;
import static starter.utils.TestGlobalVariables.getContext;

public class CommonUtil {

    /**
     * Performs an account lookup request and stores the response in context.
     * @param endpoint The endpoint string (e.g., from Gherkin).
     * @param id The ID for loading test data.
     */
    public static void performAccountLookup(String endpoint, String id) {
        AccountLookup data= TestDataLoader.getAccountLookupData(id);
        RequestAccountLookup requestBody = new RequestAccountLookup();
        requestBody.setAccount_number(data.getAccount_number());
        Response response = HttpApiUtils.requestWithStandardHeadersSimples(
                "POST",
                getContext(ACCESS_TOKEN.name()),
                getParameterProperties(endpoint),
                data.getDeviceuuid(),
                data.getInstallationdate(),
                getContext(SESSION_ID.name()),
                convertObjectToJson(requestBody)
        );

}
public static void performCommonTransaction(String method,
                                            String endpoint,
                                            String id,
                                            Object body
                                           ){
    Topup data= TestDataLoader.getTopupData(id);
    String installationdate=data.getInstallationdate();
    String deviceuuid=data.getDeviceuuid();
    Response response = HttpApiUtils.requestWithHeaders(
            "POST",
            getParameterProperties(endpoint),
            getContext(ACCESS_TOKEN.name()),
            deviceuuid,
            installationdate,
            null,
            false,
            getContext(DATA_TOKEN.name()),
            true,
            true,
            getContext(SESSION_ID.name())



    );


}



}