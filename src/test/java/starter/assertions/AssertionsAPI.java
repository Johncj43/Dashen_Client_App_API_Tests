package starter.assertions;


import io.restassured.response.Response;
import org.junit.Assert;
import starter.utils.HelperUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasSize;
import static starter.utils.TestGlobalVariables.ContextEnum.HTTP_RESPONSE;
import static starter.utils.TestGlobalVariables.getContext;

public class AssertionsAPI {

    public static void checkResponseStatusCode(int statusCode) {
        Response response = getContext(HTTP_RESPONSE.name());
        response.then().assertThat().statusCode(statusCode);
    }

    public static void checkResponseContains(String jsonFieldName, String expectedValue) {
        String field = HelperUtils.resolvePath(expectedValue);
        Response response = getContext(HTTP_RESPONSE.name());
        String value = response.then().extract().body().jsonPath().getString(jsonFieldName);
        Assert.assertEquals(field, value);
    }
    public static void checkResponseContains(String jsonFieldName, int expectedValue) {
        Response response = getContext(HTTP_RESPONSE.name());
        int value = response.then().extract().body().jsonPath().getInt(jsonFieldName);
        Assert.assertEquals(expectedValue, value);
    }
    public static void checkResponseContainsStringValue(String jsonFieldName, String expectedValue) {
        String field = HelperUtils.resolvePath(expectedValue);
        Response response = getContext(HTTP_RESPONSE.name());
        String value = response.then().extract().body().jsonPath().getString(jsonFieldName);
        value.contains(field);
    }


    public static void checkResponseContainsNonEmpty(String jsonFieldName) {
        Response response = getContext(HTTP_RESPONSE.name());
        String value = response.then().extract().body().jsonPath().getString(jsonFieldName);

        // Assert the field is not null and not empty
        Assert.assertNotNull("Field '" + jsonFieldName + "' should not be null", value);
        Assert.assertFalse("Field '" + jsonFieldName + "' should not be empty", value.isEmpty());
    }


    /**
     * Validate each value in the response list
     *
     * @param jsonPath      - json path to the 'list' response element
     * @param expectedValue
     */
    public static void checkEachItemInResponseEquals(String jsonPath, String expectedValue) {
        String field = HelperUtils.resolvePath(expectedValue);
        Response response = getContext(HTTP_RESPONSE.name());
        List<String> value = response.then().extract().body().jsonPath().getList(jsonPath, String.class);
        value.stream().forEach(s -> Assert.assertEquals(field, s));
    }

    public static void checkEachItemInResponseContains(String jsonPath, String expectedValue) {
        String field = HelperUtils.resolvePath(expectedValue);
        Response response = getContext(HTTP_RESPONSE.name());
        List<String> value = response.then().extract().body().jsonPath().getList(jsonPath, String.class);
        value.stream().forEach(s -> Assert.assertTrue("Current value: " + s,
                s.toLowerCase().contains(field.toLowerCase())));
    }

    public static boolean filterResponse(String jsonPath, String expectedValue) {
        String field = HelperUtils.resolvePath(expectedValue);
        Response response = getContext(HTTP_RESPONSE.name());
        List<String> value = response.then().extract().body().jsonPath().getList(jsonPath, String.class);
        if (value == null || value.isEmpty()) {
            return false;
        }
        return value.stream().anyMatch(s -> s.equalsIgnoreCase(field));
    }

    public static void checkResponseContainOneAndMoreThanOneItem(String jsonPath) {
        Response response = getContext(HTTP_RESPONSE.name());
        int countedValue = response.then().extract().body().jsonPath().getList(jsonPath).size();
        Assert.assertTrue(countedValue >= 1);
    }

    /**
     * Validate response list of items contains value
     *
     * @param jsonPath      - json path to the list
     * @param expectedValue
     */
    public static void checkResponseContainsItem(String jsonPath, String expectedValue) {
        String field = HelperUtils.resolvePath(expectedValue);
        Response response = getContext(HTTP_RESPONSE.name());
        List<String> items = response.then().extract().response().jsonPath().getList(jsonPath, String.class);
        Assert.assertTrue(items.contains(field));
    }

    public static void checkResponseHeaderKey(String expectedKey) {
        Response response = getContext(HTTP_RESPONSE.name());
        Assert.assertTrue("There is no header - " + expectedKey,
                response.getHeaders().hasHeaderWithName("x-total-count"));
    }

    public static void checkResponseHeader(String expectedKey, String expectedValue) {
        String field = HelperUtils.resolvePath(expectedValue);
        Response response = getContext(HTTP_RESPONSE.name());
        Assert.assertEquals(response.getHeaders().get(expectedKey).getValue(), field);
    }

    public static void checkResponseHeaderHasValueMoreThan(String expectedKey, int value) {
        Response response = getContext(HTTP_RESPONSE.name());
        Assert.assertTrue("There is no header - " + expectedKey,
                value < Integer.valueOf(response.getHeaders().get(expectedKey).getValue()));
    }

    public static void checkResponseHeaderHasValueIs(String expectedKey, int value) {
        Response response = getContext(HTTP_RESPONSE.name());
        int actualValue = Integer.valueOf(response.getHeaders().get(expectedKey).getValue());
        Assert.assertTrue("Current is header " + expectedKey + " value is: " + actualValue,
                value == actualValue);
    }

    public static void checkResponseHasSizeOfItems(String jsonPath, int expectedSize) {
        Response response = getContext(HTTP_RESPONSE.name());
        response.then().body(jsonPath, hasSize(expectedSize));
    }

    public static void checkResponseSize(String jsonPath, int expectedSize) {
        Response response = getContext(HTTP_RESPONSE.name());
        int result = response.then().extract().body().jsonPath().getList(jsonPath).size();
        Assert.assertEquals(result, expectedSize);
    }

    public static void checkResponseContainsField(String path, String fieldName) {
        String field = HelperUtils.resolvePath(fieldName);
        Response response = getContext(HTTP_RESPONSE.name());
        response.then().body(path, containsString(field));
    }

    public static boolean checkDateAfter(String jsonPath, Date refDate) {
        Response response = getContext(HTTP_RESPONSE.name());
        List<String> value = response.then().extract().body().jsonPath().getList(jsonPath);
        SimpleDateFormat outputsFormat = new SimpleDateFormat("yyyy-MM-dd");
        return value.stream().map(dateStr -> {
                    try {
                        return outputsFormat.parse(dateStr);
                    } catch (ParseException ex) {
                        throw new RuntimeException("invalid date:" + dateStr);
                    }
                }

        ).noneMatch(date -> date.after(refDate));
    }

    public static boolean checkBeforeDate(String jsonPath, Date refDate) {
        Response response = getContext(HTTP_RESPONSE.name());
        List<String> value = response.then().extract().body().jsonPath().getList(jsonPath);
        SimpleDateFormat outputsFormat = new SimpleDateFormat("yyyy-MM-dd");
        return value.stream().map(dateStr -> {
                    try {
                        return outputsFormat.parse(dateStr);
                    } catch (ParseException ex) {
                        throw new RuntimeException("invalid date:" + dateStr);
                    }
                }

        ).noneMatch(date -> date.before(refDate));
    }

    public static boolean checkADateInRange(String jsonPath, Date startDate, Date endDate) {
        Response response = getContext(HTTP_RESPONSE.name());
        List<String> value = response.then().extract().body().jsonPath().getList(jsonPath);
        SimpleDateFormat outputsFormat = new SimpleDateFormat("yyyy-MM-dd");
        return value.stream().map(dateStr -> {
                    try {
                        return outputsFormat.parse(dateStr);
                    } catch (ParseException ex) {
                        throw new RuntimeException("invalid date:" + dateStr);
                    }
                }

        ).allMatch(date -> !date.before(startDate) && !date.after(endDate));
    }
}

