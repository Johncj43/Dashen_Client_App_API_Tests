package starter.step_definitions.client.budget;

import io.cucumber.java.en.And;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import starter.utils.HttpApiUtils;
import starter.utils.TestDataLoader;
import starter.utils.model.requestModel.client.budget.BudgetData;
import starter.utils.model.requestModel.client.users.LoginData;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertNotNull;
import static starter.utils.HelperUtils.convertObjectToJson;
import static starter.utils.PropertiesReader.getParameterProperties;
import static starter.utils.TestGlobalVariables.ContextEnum.*;
import static starter.utils.TestGlobalVariables.getContext;
import static starter.utils.TestGlobalVariables.setContext;

public class CategoryStepDef {

    @When("I send a POST request to {string} with {string} to create a category on app")
    public void iSendAPOSTRequestToWithToCreateACATEGORYOnApp(String endpoint, String id) {
        BudgetData user= TestDataLoader.getBudgetData(id);
        String installation=user.getInstallationdate();
        String deviceuuid= user.getDeviceuuid();
        String budgetId = getContext(BUDGET_ID.name());
        String name = user.getName();
        String icon = user.getIcon();
        String color = user.getColor();
        int expenseLimit= user.getExpense_limit();
        Map<String, Object> body=Map.of(
                "budget_id", budgetId,
                "name", name,
                "icon", icon,
                "color", color,
                "expense_limit", expenseLimit
        );

        Response response= HttpApiUtils.requestWithStandardHeaderst(
                "POST",
                getContext(ACCESS_TOKEN.name()),
                getParameterProperties(endpoint),
                deviceuuid,
                installation,
                convertObjectToJson(body),
                null

        );

        String categoryId = response.jsonPath().getString("data._id");
        assertNotNull(categoryId, "Category ID is null");


        setContext(CATEGORY_ID.name(), categoryId);

        System.out.println("Created category ID = " + categoryId);
    }

    @When("I send a GET request to {string} for user {string} category")
    public void iSendAGETRequestTo(String endpoint, String id) {

        LoginData data=TestDataLoader.getLoginData(id);
        String deviceuuid=data.getDeviceuuid();
        String installationdate= data.getInstallationdate();
        String url = getParameterProperties(endpoint);
        System.out.println("FETCH_SINGLE_CATEGORY_URL = " + url);

        Response response = HttpApiUtils.requestWithStandardHeaderst(
                "GET",
                getContext(ACCESS_TOKEN.name()),
                url,
                deviceuuid,   // deviceuuid if required
                installationdate,   // installation if required
                null,   // body for DELETE usually null
                null
        );

        // store response in context if needed
    }



//    @When("I send a POST request to {string} for user {string} category")
//    public void iSendAPOSTRequestToForUserCategory(String arg0, String arg1) {
//    }


    @When("I send aa GET request to {string} for user {string}")
    public void iSendAaGETRequestToForUser(String endpoint, String id){

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

    @When("I send aa POST request to {string} with {string} to create a category on app")
    public void iSendAAPOSTRequestToWithToCreateACATEGORYOnApp(String endpoint, String id) {
        BudgetData user= TestDataLoader.getBudgetData(id);
        String installation=user.getInstallationdate();
        String deviceuuid= user.getDeviceuuid();
        String budgetId = getContext(BUDGET_ID.name());
        String name = user.getName();
        String icon = user.getIcon();
        String color = user.getColor();
        int expenseLimit= user.getExpense_limit();
        Map<String, Object> body = new HashMap<>();
        body.put("budget_id", null);   // this is now allowed
        body.put("name", name);
        body.put("icon", icon);
        body.put("color", color);
        body.put("expense_limit", expenseLimit);


        Response response= HttpApiUtils.requestWithStandardHeaderst(
                "POST",
                getContext(ACCESS_TOKEN.name()),
                getParameterProperties(endpoint),
                deviceuuid,
                installation,
                convertObjectToJson(body),
                null

        );


    }




    @And("I send aa DELETE request to {string} with a {string} to delete a budget")
    public void iSendAaDELETERequestToWithAToDeleteABudget(String endpoint, String id) {

        LoginData data=TestDataLoader.getLoginData(id);
        String deviceuuid=data.getDeviceuuid();
        String installationdate= data.getInstallationdate();
        String categoryId = getContext(CATEGORY_ID.name());
        String url = getParameterProperties(endpoint)+"/" + categoryId;
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

    @When("I send a PUT request to {string} with {string} to update a category on app")
    public void iSendAPUTRequestToWithToUpdateACategoryOnApp(String endpoint, String id) {
        BudgetData user = TestDataLoader.getBudgetData(id);
        String installation = user.getInstallationdate();
        String deviceuuid = user.getDeviceuuid();
        String categoryId = getContext(CATEGORY_ID.name());
        String budgetId = getContext(BUDGET_ID.name());
        String url = getParameterProperties(endpoint) + "/" + categoryId;
        String name = user.getName();
        String icon = user.getIcon();
        String color = user.getColor();
        int expenseLimit= user.getExpense_limit();

        Map<String, Object> body = new HashMap<>();
        body.put("budget_id", budgetId);
        body.put("name", name);
        body.put("icon", icon);
        body.put("color", color);
        body.put("expense_limit", expenseLimit);



        Response response = HttpApiUtils.requestWithStandardHeaderst(
                "PUT",
                getContext(ACCESS_TOKEN.name()),
                url, // ✅ use the URL with expenseId
                deviceuuid,
                installation,
                convertObjectToJson(body),
                null
        );
    }
}



