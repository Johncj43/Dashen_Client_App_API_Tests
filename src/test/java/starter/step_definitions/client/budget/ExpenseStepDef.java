package starter.step_definitions.client.budget;

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

public class ExpenseStepDef {


    @When("I send a POST request to {string} with {string} to create an expense on app")
    public void iSendAPOSTRequestToWithToCreateAnExpenseOnApp(String endpoint, String id) {
        BudgetData user = TestDataLoader.getBudgetData(id);
        String installation = user.getInstallationdate();
        String deviceuuid = user.getDeviceuuid();
        String categoryId = getContext(CATEGORY_ID.name());
        String expenseType = user.getExpense_type();
        int amount = user.getAmount();

        Map<String, Object> body = new HashMap<>();
        body.put("category_id", categoryId);
        body.put("expense_type", expenseType);
        body.put("amount", amount);

        Response response = HttpApiUtils.requestWithStandardHeaderst(
                "POST",
                getContext(ACCESS_TOKEN.name()),
                getParameterProperties(endpoint),
                deviceuuid,
                installation,
                convertObjectToJson(body),
                null

        );

        String expenseId = response.jsonPath().getString("data._id");
        assertNotNull(expenseId, "expense ID is null");


        setContext(EXPENSE_ID.name(), expenseId);

        System.out.println("Created expense ID = " + expenseId);

    }

    @When("I send a DELETE request to {string} with a {string} to delete an expense")
    public void iSendADELETERequestToWithAToDeleteAnExpense(String endpoint, String id) {
        LoginData data = TestDataLoader.getLoginData(id);
        String deviceuuid = data.getDeviceuuid();
        String installationdate = data.getInstallationdate();
        String expenseId = getContext(EXPENSE_ID.name());
        String url = getParameterProperties(endpoint) + "/" + expenseId;
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

    @When("I send a POST request to {string} with {string} to create an expense on app without category id")
    public void iSendAPOSTRequestToWithToCreateAnExpenseOnAppWithoutCategoryId(String endpoint, String id) {

        BudgetData user = TestDataLoader.getBudgetData(id);
        String installation = user.getInstallationdate();
        String deviceuuid = user.getDeviceuuid();
        String expenseType = user.getExpense_type();
        int amount = user.getAmount();

        Map<String, Object> body = new HashMap<>();
        // ❌ category_id intentionally omitted
        body.put("expense_type", expenseType);
        body.put("amount", amount);

        Response response = HttpApiUtils.requestWithStandardHeaderst(
                "POST",
                getContext(ACCESS_TOKEN.name()),
                getParameterProperties(endpoint),
                deviceuuid,
                installation,
                convertObjectToJson(body),
                null
        );


    }



//    @When("I send a POST request to {string} with {string} to create an expense again")
//    public void iSendAPOSTRequestToWithToCreateAnExpenseAgain(String endpoint, String id) {
//
//        BudgetData user = TestDataLoader.getBudgetData(id);
//        String installation = user.getInstallationdate();
//        String deviceuuid = user.getDeviceuuid();
//        String categoryId = getContext(CATEGORY_ID.name());
//        String expenseType = user.getExpense_type();
//        int amount = user.getAmount();
//
//        Map<String, Object> body = new HashMap<>();
//        body.put("category_id", categoryId);
//        body.put("expense_type", expenseType);
//        body.put("amount", amount);
//
//        Response response = HttpApiUtils.requestWithStandardHeaderst(
//                "POST",
//                getContext(ACCESS_TOKEN.name()),
//                getParameterProperties(endpoint),
//                deviceuuid,
//                installation,
//                convertObjectToJson(body),
//                null
//        );
//
//    }

    @When("I send a PUT request to {string} with {string} to update an expense on app")
    public void iSendAPUTRequestToWithToUpdateAnExpenseOnApp(String endpoint, String id) {
        BudgetData user = TestDataLoader.getBudgetData(id);
        String installation = user.getInstallationdate();
        String deviceuuid = user.getDeviceuuid();
        String categoryId = getContext(CATEGORY_ID.name());
        String expenseId = getContext(EXPENSE_ID.name());
        String url = getParameterProperties(endpoint) + "/" + expenseId;
        String expenseType = user.getExpense_type();
        int amount = user.getAmount();

        Map<String, Object> body = new HashMap<>();
        body.put("category_id", categoryId);
        body.put("expense_type", expenseType);
        body.put("amount", amount);

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

