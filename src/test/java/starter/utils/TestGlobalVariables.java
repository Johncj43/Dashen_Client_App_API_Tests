package starter.utils;

import io.restassured.response.Response;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

import static starter.utils.HelperUtils.extractJsonValue;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TestGlobalVariables {

    private static final Map<String, Object> context = new ConcurrentHashMap<>();

    private TestGlobalVariables() {
        // Utility class: prevent instantiation
    }


    public static void addUserCode(String userCode) {
        addToListContext(ContextEnum.LIST_USER_CODE, userCode);
    }

    public static List<String> getUserCodes() {
        return getListFromContext(ContextEnum.LIST_USER_CODE);
    }


    public static void setContext(String key, Object value) {
        context.put(key, value);
    }

    @SuppressWarnings("unchecked")
    public static <T> T getContext(String key) {
        return (T) Objects.requireNonNullElse(context.get(key), null);
    }

    public static List<Object> getContextWithSubKey(String subKey) {
        return context.entrySet().stream()
                .filter(entry -> entry.getKey().contains(subKey))
                .map(Map.Entry::getValue)
                .collect(Collectors.toList());
    }

    public static void clearContextWithSubKey(String subKey) {
        context.keySet().removeIf(key -> key.contains(subKey));
    }

    public static void storeContextFromResponse(Response res, String jsonPath, Enum<?> key) {
        String value = extractJsonValue(res, jsonPath);
        if (value != null && !value.isEmpty()) {
            setContext(key.name(), value);
        } else {
            log.warn("Key '{}' not found in response at path '{}'", key.name(), jsonPath);
        }
    }

    public static void addToListContext(Enum<?> key, String value) {
        List<String> list = (List<String>) context.get(key.name());
        if (list == null) {
            list = new CopyOnWriteArrayList<>();
            context.put(key.name(), list);
        }
        list.add(value);
    }

    // Retrieve the list from context
    @SuppressWarnings("unchecked")
    public static List<String> getListFromContext(Enum<?> key) {
        return (List<String>) context.getOrDefault(key.name(), new CopyOnWriteArrayList<String>());
    }

    public enum ContextEnum {
        otp,
        otpcode,
        INVALID_SHORT,
        INVALID_LONG,
        otp_for,
        ACCESS_TOKEN,
        DATA_TOKEN,
        HTTP_RESPONSE,
        USER_ID,
        USER_CODE,
        OTP,
        SESSION_ID,
        TEMP_TOKEN,
        PHONE_NUMBER,
        DEVICE_UUID,
        BRANCH_TOKEN,
        ACTION_CODE,
        DEVICE_UUI,
        PIN,
        AMOUNT,
        NEW_PIN,
        MAKER_ACCESS_TOKEN,
        CHECKER_ACCESS_TOKEN,
        VOUCHER_CODE,
        FAYDA_CODE,
        FAYDA_STATE,
        REQUEST_ID,
        TOKEN_ID,
        ID,
        USERS, // users for equb membership acceptance
        PAYER_TOKEN,
        REQUESTED_DATE,
        CPS_MAKER_ACCESS_TOKEN,
        CPS_CHECKER_ACCESS_TOKEN,
        REQUEST_ACTION,
        FAYDA_RESPONSE_DATA,
        FAYDA_REDIRECTED_URL,
        FAYDA_USER_INFO_RESPONSE,
        FAYDA_SUB_ID,
        PIN_RESPONSE_FULL_NAME,
        PIN_RESPONSE_PHONE_NUMBER,
        LIST_USER_CODE,
        GENERATED_FULL_NAME,
        GENERATED_PHONE_NUMBER,
        SINGLE_MEMBER_ID,
        ACCOUNT_NUMBER,
        GROUP_ID,
        BENEFICIARY_IDS,
        accessToken,
        BUDGET_ID,
        CATEGORY_ID,
        EXPENSE_ID
        ;


    }
}
