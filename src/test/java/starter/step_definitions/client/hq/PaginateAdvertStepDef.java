package starter.step_definitions.client.hq;

import io.cucumber.java.en.When;
import io.restassured.response.Response;
import starter.utils.HttpApiUtils;
import starter.utils.TestDataLoader;
import starter.utils.model.requestModel.client.core.PaginateAdvertData;

import java.util.HashMap;
import java.util.Map;

import static starter.utils.PropertiesReader.getParameterProperties;
import static starter.utils.TestGlobalVariables.ContextEnum.*;
import static starter.utils.TestGlobalVariables.getContext;
import static starter.utils.TestGlobalVariables.setContext;

public class PaginateAdvertStepDef {
    @When("device user {string} sends {string} for paginate advert")
    public void deviceUserSendsForPaginateAdvert(String id, String endpoint) {

        PaginateAdvertData paginateAdvertData = TestDataLoader.getPaginateAdvertData(id);

        String deviceUuid = paginateAdvertData.getDeviceuuid();
        String page = paginateAdvertData.getPage();
        String limit = paginateAdvertData.getLimit();
        String installation = paginateAdvertData.getInstallationdate();

        Map<String, Object> queryParams = new HashMap<>();
        queryParams.put("page", page);
        queryParams.put("limit", limit);

        Response response = HttpApiUtils.requestWithCoresHeaders33(
                "GET",
                getParameterProperties(endpoint),
                deviceUuid,
                getContext(ACCESS_TOKEN.name()),
                installation,
                getContext(SESSION_ID.name()),
                true,
                queryParams
        );

        setContext(HTTP_RESPONSE.name(), response);
    }
}

