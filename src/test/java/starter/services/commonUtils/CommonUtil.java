package starter.services.commonUtils;

import io.restassured.response.Response;
import starter.utils.HttpApiUtils;
import starter.utils.TestDataLoader;
import starter.utils.model.requestModel.client.core.AccountLookup;
import starter.utils.model.requestModel.client.core.RequestAccountLookup;

import static starter.utils.HelperUtils.convertObjectToJson;
import static starter.utils.PropertiesReader.getParameterProperties;
import static starter.utils.TestGlobalVariables.ContextEnum.ACCESS_TOKEN;
import static starter.utils.TestGlobalVariables.ContextEnum.SESSION_ID;
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

}}