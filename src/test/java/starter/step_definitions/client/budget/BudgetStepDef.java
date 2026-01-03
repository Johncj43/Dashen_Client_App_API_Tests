package starter.step_definitions.client.budget;

import io.cucumber.java.en.When;
import io.restassured.response.Response;
import starter.utils.HttpApiUtils;
import starter.utils.TestDataLoader;
import starter.utils.model.requestModel.client.budget.BudgetData;
import starter.utils.model.requestModel.client.users.LoginData;

import java.util.HashMap;
import java.util.Map;

import static starter.utils.HelperUtils.convertObjectToJson;
import static starter.utils.PropertiesReader.getParameterProperties;
import static starter.utils.TestGlobalVariables.ContextEnum.*;
import static starter.utils.TestGlobalVariables.ContextEnum.BUDGET_ID;
import static starter.utils.TestGlobalVariables.getContext;
import static starter.utils.TestGlobalVariables.setContext;

public class BudgetStepDef {

    @When("I send a POST request to {string} with {string} to create a budget on app")
    public void iSendAPOSTRequestToWithToCreateABudgetOnApp(String endpoint, String id) {
        BudgetData user= TestDataLoader.getBudgetData(id);
        String installation=user.getInstallationdate();
        String deviceuuid= user.getDeviceuuid();
        String budgetType=user.getBudget_type();
        String customStartDate=user.getCustom_start_date();
        String customEndDate=user.getCustom_end_date();
        int amount= user.getAmount();
        Map<String, Object>body=Map.of("budget_type",budgetType,
                "amount",amount,
                "custom_start_date",customStartDate,
                "custom_end_date",customEndDate
        );

        Response response = HttpApiUtils.requestWithStandardHeaderst(
                "POST",
                getContext(ACCESS_TOKEN.name()),
                getParameterProperties(endpoint),
                deviceuuid,
                installation,
                convertObjectToJson(body),
                null

        );
        setContext(HTTP_RESPONSE.name(), response);
        String budgetId = response.jsonPath().getString("data.budget_id");
        if(budgetId!=null) {

            setContext(BUDGET_ID.name(), budgetId);
        }else {
            System.out.println("budgetID is null, negative test cases, skipping context storage");
        }


    }


    @When("I send a PUT request to {string} with {string} to update a budget on app")
    public void iSendAPUTRequestToWithToUpdateABudgetOnApp(String endpoint, String id) {
        BudgetData user = TestDataLoader.getBudgetData(id);
        String budgetId = getContext(BUDGET_ID.name());
        String url = getParameterProperties(endpoint) + "/" + budgetId;
        String installation = user.getInstallationdate();
        String deviceuuid = user.getDeviceuuid();
        String budgetType=user.getBudget_type();
        int amount= user.getAmount();
        Map<String, Object> body = new HashMap<>();
        body.put("budget_type", budgetType);
        body.put("amount", amount);
        Response response = HttpApiUtils.requestWithStandardHeaderst(
                "PUT",
                getContext(ACCESS_TOKEN.name()),
                url,
                deviceuuid,
                installation,
                convertObjectToJson(body),
                null
        );
        setContext(HTTP_RESPONSE.name(), response);

        System.out.println("Updated Budget Response: " + response.getBody().asString());
    }




    @When("I send a POST request to {string} with {string} to create aa budget on app")
    public void iSendAPOSTRequestToWithToCreateAaBudgetOnApp(String endpoint, String id) {
        BudgetData user= TestDataLoader.getBudgetData(id);
        String installation=user.getInstallationdate();
        String deviceuuid= user.getDeviceuuid();
        String budgetType=user.getBudget_type();
        int amount= user.getAmount();
        Map<String, Object>body=Map.of("budget_type",budgetType,
                "amount",amount
//                "custom_start_date",customStartDate,
//                "custom_end_date",customEndDate
        );

        Response response = HttpApiUtils.requestWithStandardHeaderst(
                "POST",
                getContext(ACCESS_TOKEN.name()),
                getParameterProperties(endpoint),
                deviceuuid,
                installation,
                convertObjectToJson(body),
                null

        );
        setContext(HTTP_RESPONSE.name(), response);
        String budgetId = response.jsonPath().getString("data.budget_id");
        if(budgetId!=null) {

            setContext(BUDGET_ID.name(), budgetId);
        }else {
            System.out.println("budgetID is null, negative test cases, skipping context storage");
        }



    }

    @When("I send a DELETE request to {string} with a {string} to delete a budget")
    public void iSendADELETERequestToWithAToDeleteABudget(String endpoint, String id) {

        LoginData data=TestDataLoader.getLoginData(id);
        String deviceuuid=data.getDeviceuuid();
        String installationdate= data.getInstallationdate();
        String budgetId = getContext(BUDGET_ID.name());
        String url = getParameterProperties(endpoint)+"/" + budgetId;
        Response response = HttpApiUtils.requestWithStandardHeaderst(
                "DELETE",
                getContext(ACCESS_TOKEN.name()),
                url,
                deviceuuid,
                installationdate,
                null,
                null
        );
        setContext(HTTP_RESPONSE.name(), response);

    }


    @When("I send a GET request to {string} for user {string}")
    public void iSendAGETRequestTo(String endpoint, String id) {


        LoginData data=TestDataLoader.getLoginData(id);
        String deviceuuid=data.getDeviceuuid();
        String installationdate= data.getInstallationdate();
        String categoryId = getContext(CATEGORY_ID.name());
        String url = getParameterProperties(endpoint) + "/" + categoryId;
        Response response = HttpApiUtils.requestWithStandardHeaderst(
                "GET",
                getContext(ACCESS_TOKEN.name()),
                url,
                deviceuuid,
                installationdate,
                null,
                null
        );
        setContext(HTTP_RESPONSE.name(), response);

    }

    @When("I send a GET request to {string} for single user {string}")
    public void iSendAGETRequestToForSingleUser( String endpoint, String id) {

        LoginData data=TestDataLoader.getLoginData(id);
        String deviceuuid=data.getDeviceuuid();
        String installationdate= data.getInstallationdate();
        String budgetId = getContext(BUDGET_ID.name());
        String url = getParameterProperties(endpoint) + "/" + budgetId;
        Response response = HttpApiUtils.requestWithStandardHeaderst(
                "GET",
                getContext(ACCESS_TOKEN.name()),
                url,
                deviceuuid,
                installationdate,
                null,
                null
        );
        setContext(HTTP_RESPONSE.name(), response);

    }

    @When("I send a GET request to {string} with {string} to fetch the created budget")
    public void iSendAGETRequestToWithToFetchTheCreatedBudget(String endpoint, String id) {
        LoginData data=TestDataLoader.getLoginData(id);
        String deviceuuid=data.getDeviceuuid();
        String installationdate= data.getInstallationdate();
        Response response = HttpApiUtils.requestWithStandardHeaderst(
                "GET",
                getContext(ACCESS_TOKEN.name()),
                getParameterProperties(endpoint),
                deviceuuid,
                installationdate,
                null,
                null
        );
        setContext(HTTP_RESPONSE.name(), response);
    }

    @When("I send a GET request to {string} with {string} to get the history of created budget")
    public void iSendAGETRequestToWithToGetTheHistoryOfCreatedBudget(String endpoint, String id) {
        LoginData data=TestDataLoader.getLoginData(id);
        String deviceuuid=data.getDeviceuuid();
        String installationdate= data.getInstallationdate();
        Response response = HttpApiUtils.requestWithStandardHeaderst(
                "GET",
                getContext(ACCESS_TOKEN.name()),
                getParameterProperties(endpoint),
                deviceuuid,
                installationdate,
                null,
                null
        );
        setContext(HTTP_RESPONSE.name(), response);
    }

    @When("I send a GET request to {string} with {string} to fetch icons of categories")
    public void iSendAGETRequestToWithToFetchIconsOfCategories(String endpoint, String id) {
        LoginData data=TestDataLoader.getLoginData(id);
        String deviceuuid=data.getDeviceuuid();
        String installationdate= data.getInstallationdate();
        Response response = HttpApiUtils.requestWithStandardHeaderst(
                "GET",
                getContext(ACCESS_TOKEN.name()),
                getParameterProperties(endpoint),
                deviceuuid,
                installationdate,
                null,
                null
        );
        setContext(HTTP_RESPONSE.name(), response);

    }

//    }
}
