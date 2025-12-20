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
        String budgetId = response.jsonPath().getString("data.budget_id");

        setContext(BUDGET_ID.name(), budgetId);

        System.out.println("Created Budget ID = " + budgetId);

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

        System.out.println("Updated Budget Response: " + response.getBody().asString());
    }




    @When("I send a POST request to {string} with {string} to create aa budget on app")
    public void iSendAPOSTRequestToWithToCreateAaBudgetOnApp(String endpoint, String id) {
        BudgetData user= TestDataLoader.getBudgetData(id);
        String installation=user.getInstallationdate();
        String deviceuuid= user.getDeviceuuid();
        String budgetType=user.getBudget_type();
//        String customStartDate=user.getCustom_start_date();
//        String customEndDate=user.getCustom_end_date();
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
        String budgetId = response.jsonPath().getString("data.budget_id");

        setContext(BUDGET_ID.name(), budgetId);

        System.out.println("Created Budget ID = " + budgetId);


    }



//    @When("I send a DELETE request to {string} with a {string} to delete a budget")
//    public void iSendADELETERequestToWithAToDeleteABudget(String endpoint, String id) {
//        LoginData data=TestDataLoader.getLoginData(id);
//        String deviceuuid=data.getDeviceuuid();
//        String installationdate= data.getInstallationdate();
//        String budgetId = getContext(BUDGET_ID.name());
//
//        if (budgetId == null) {
//            throw new RuntimeException("budgetId is null! Make sure you created a budget before DELETE.");
//        }
//        String url = getParameterProperties(endpoint)+"/" + budgetId;
//        System.out.println("DELETE URL = " + url);
//        Response response = HttpApiUtils.requestWithStandardHeaderst(
//                "DELETE",
//                getContext(ACCESS_TOKEN.name()),
//                url,
//                deviceuuid,   // deviceuuid if required
//                installationdate,   // installation if required
//                null,   // body for DELETE usually null
//                null
//        );
//        System.out.println("DELETE Response: " + response.getBody().asString());
//        // store response in context if needed
//    }

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

    }


//    @When("I send a POST request to {string} for user {string} category")
//    public void iSendAPOSTRequestToForUserCategory(String arg0, String arg1) {
//    }
}
