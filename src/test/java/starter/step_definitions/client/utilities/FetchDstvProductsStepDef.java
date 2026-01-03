package starter.step_definitions.client.utilities;

import io.cucumber.java.en.When;
import io.restassured.response.Response;
import starter.utils.HttpApiUtils;
import starter.utils.TestDataLoader;
import starter.utils.model.requestModel.client.utilities.FetchDstvData;

import java.util.HashMap;
import java.util.Map;

import static starter.utils.HelperUtils.convertObjectToJson;
import static starter.utils.PropertiesReader.getParameterProperties;
import static starter.utils.TestGlobalVariables.ContextEnum.*;
import static starter.utils.TestGlobalVariables.getContext;
import static starter.utils.TestGlobalVariables.setContext;

public class FetchDstvProductsStepDef {
    @When("device user {string} sends {string} for fetch DSTV product lookup")
    public void device_user_sends_for_fetch_DSTV_product_lookup(String id, String endpoint) {

        FetchDstvData fetchDstvProducts = TestDataLoader.getFetchDstvData(id);

        String deviceUuid = fetchDstvProducts.getDeviceuuid();
        String customerNumber = fetchDstvProducts.getCustomer_number();
        String installation = fetchDstvProducts.getInstallationdate();

        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("customer_number", customerNumber);

        Response response = HttpApiUtils.requestWithCoresHeaders3(
                "POST",
                getParameterProperties(endpoint),
                deviceUuid,
                getContext(ACCESS_TOKEN.name()),
                installation,
                getContext(SESSION_ID.name()),
                true,
                convertObjectToJson(requestBody)

        );

        setContext(HTTP_RESPONSE.name(), response);

    }

    @When("device user {string} sends {string} for fetch DSTV product lookup with invalid card")
    public void device_user_sends_for_fetch_DSTV_product_lookup_invalid_card(String id, String endpoint) {
        FetchDstvData fetchDstvProducts = TestDataLoader.getFetchDstvData(id);

        String deviceUuid = fetchDstvProducts.getDeviceuuid();
        String invalidCustomerNumber = fetchDstvProducts.getCustomer_number();
        String installation = fetchDstvProducts.getInstallationdate();

        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("customer_number", invalidCustomerNumber);

        Response response = HttpApiUtils.requestWithCoresHeaders3(
                "POST",
                getParameterProperties(endpoint),
                deviceUuid,
                getContext(ACCESS_TOKEN.name()),
                installation,
                getContext(SESSION_ID.name()),
                true,
                convertObjectToJson(requestBody)

        );


        setContext(HTTP_RESPONSE.name(), response);
        response.then().log().all();
    }


}
