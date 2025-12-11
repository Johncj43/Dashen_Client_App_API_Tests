package starter.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Assert;

import java.security.PublicKey;
import java.security.SecureRandom;
import java.util.*;
import java.util.regex.Matcher;

import static starter.utils.Constants.PATTERN_NAMED_PARAM_TO_UPPER_CASE;
import static starter.utils.TestGlobalVariables.getContext;

@Slf4j
public class HelperUtils {

    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final SecureRandom secureRandom = new SecureRandom();
    private static final String HEX_CHARS = "0123456789abcdef";
    private static final SecureRandom random = new SecureRandom();

    // =====================================================================================
    // Path Resolver Utilities
    // =====================================================================================

    public static String resolvePath(final String apiPath) {
        if (apiPath == null) return null;

        StringBuilder resolvedPath = new StringBuilder();
        Matcher matcher = Constants.PATTERN_NAMED_PARAM_HINT_PROPERTY.matcher(apiPath);

        while (matcher.find()) {
            String propertyName = matcher.group(1);

            if (propertyName.startsWith(Constants.NAMED_PARAM_HINT_NUMBER_OF_LENGTH)) {
                int length = extractLength(propertyName);
                matcher.appendReplacement(resolvedPath, RandomStringUtils.randomNumeric(length));

            } else if (propertyName.startsWith(Constants.NAMED_PARAM_HINT_STRING_OF_LENGTH)) {
                int length = extractLength(propertyName);
                matcher.appendReplacement(resolvedPath, RandomStringUtils.randomAlphanumeric(length));

            } else if (propertyName.startsWith(Constants.NAMED_PARAM_HINT_GENERATED_UUID)) {
                matcher.appendReplacement(resolvedPath, UUID.randomUUID().toString());

            } else if (propertyName.startsWith(Constants.TO_UPPER_CASE)) {
                matcher.appendReplacement(resolvedPath, resolvePathToUpperCase(propertyName));

            } else {
                Object value = getContext(propertyName);
                Assert.assertNotNull(propertyName + " not persisted.", value);
                matcher.appendReplacement(resolvedPath, value.toString());
            }
        }

        matcher.appendTail(resolvedPath);
        return resolvedPath.toString();
    }

    public static String resolvePathToUpperCase(final String apiPath) {
        Matcher matcher = PATTERN_NAMED_PARAM_TO_UPPER_CASE.matcher(apiPath);
        return matcher.find() ? matcher.group(1).toUpperCase() : apiPath;
    }

    public static int extractLength(String propertyName) {
        return Integer.parseInt(propertyName.substring(propertyName.lastIndexOf(":") + 1));
    }

//    public static String generateDeviceUUID() {
//        return UUID.randomUUID().toString();
//    }

    public static String generateRandomPhoneNumber() {
        Random random = new Random();
        StringBuilder sb = new StringBuilder("+2519");
        for (int i = 0; i < 8; i++) {
            sb.append(random.nextInt(10));
        }
        return sb.toString();
    }
    public static String generateLocalPhoneNumber() {
        Random random = new Random();

        // Start with "09"
        StringBuilder phone = new StringBuilder("09");

        // Generate the remaining 8 digits
        for (int i = 0; i < 8; i++) {
            phone.append(random.nextInt(10)); // random digit 0–9
        }

        return phone.toString();
    }

    public static String generateRandomDeviceUUID() {
        StringBuilder sb = new StringBuilder(16); // 16 characters
        for (int i = 0; i < 16; i++) {
            int index = random.nextInt(HEX_CHARS.length());
            sb.append(HEX_CHARS.charAt(index));
        }
        return sb.toString();
    }


    public static String generateRandomSafaricomPhoneNumber() {
        Random random = new Random();
        StringBuilder sb = new StringBuilder("+2517");
        for (int i = 0; i < 8; i++) {
            sb.append(random.nextInt(10));
        }
        return sb.toString();
    }
    public static String generateSixDigitPin() {
        Random random = new Random();
        // Generate a number between 0 and 999999
        int pin = random.nextInt(1_000_000);
        // Format it as 6 digits, adding leading zeros if necessary
        return String.format("%06d", pin);
    }

    public static String generateRandomPIN() {
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            sb.append(random.nextInt(10)); // generates digit 0-9
        }
        return sb.toString();
    }

    public static String generatePin(int length) {
        Random random = new Random();
        StringBuilder pin = new StringBuilder();
        for (int i = 0; i < length; i++) {
            pin.append(random.nextInt(10));
        }
        return pin.toString();
    }

    public static String generatePins(int length, String type) {
        Random random = new Random();
        String t = type.toLowerCase().replace("-", "").trim();

        switch (t) {
            case "repeated":
                // Random digit repeated
                int repeatedDigit = random.nextInt(10);
                return String.valueOf(repeatedDigit).repeat(length);

            case "sequential":
                // Random start digit ascending sequence
                int startAsc = random.nextInt(10);
                StringBuilder seqAsc = new StringBuilder();
                for (int i = 0; i < length; i++) {
                    seqAsc.append((startAsc + i) % 10);
                }
                return seqAsc.toString();

            case "sequentialdescending":
                // Random start digit descending sequence
                int startDesc = random.nextInt(10);
                StringBuilder seqDesc = new StringBuilder();
                for (int i = 0; i < length; i++) {
                    seqDesc.append((startDesc - i + 10) % 10);
                }
                return seqDesc.toString();

            case "strong":
                // Random non-pattern digits (no immediate repeats)
                StringBuilder strongPin = new StringBuilder();
                int lastDigit = -1;
                for (int i = 0; i < length; i++) {
                    int digit;
                    do {
                        digit = random.nextInt(10);
                    } while (digit == lastDigit); // avoid immediate repeats
                    strongPin.append(digit);
                    lastDigit = digit;
                }
                return strongPin.toString();

            default:
                throw new IllegalArgumentException("Invalid type. Use 'repeated', 'sequential', 'sequentialdescending', or 'strong'");
        }
    }

    /** Convenience method for 6-digit PINs */
    public static String generateSixDigitPin(String type) {
        return generatePins(6, type);
    }


    public static String generateRandomFullName() {
        String[] firstNames = {"John", "Mary", "Michael", "Sara", "David", "Linda", "James", "Emma", "Henok", "Tom", "Abebe"};
        String[] lastNames = {"Smith", "Johnson", "Brown", "Williams", "Jones", "Garcia", "Miller", "Davis"};

        Random random = new Random();
        String firstName = firstNames[random.nextInt(firstNames.length)];
        String lastName = lastNames[random.nextInt(lastNames.length)];

        return firstName + " " + lastName;
    }

    public static String generateRandomNickName() {
        String[] nickNames = {"John", "Mary", "Michael", "Sara", "David", "Linda", "James", "Emma", "Henok", "Tom", "Abebe"};
        Random random = new Random();
        return nickNames[random.nextInt(nickNames.length)];
    }
    // =====================================================================================
    // JSON Conversion Utilities
    // =====================================================================================

    public static <T> T convertJsonToObject(String json, Class<T> clazz) {
        try {
            return objectMapper.readValue(json, clazz);
        } catch (Exception e) {
            // log.error("Failed to convert JSON to object for class: {}", clazz.getSimpleName(), e);
            return null;
        }
    }

    public static <T> String convertObjectToJson(T object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (Exception e) {
            //log.error("Failed to convert object to JSON: {}", object, e);
            return null;
        }
    }

    public static String extractJsonValue(Response response, String key) {
        return response.then().extract().jsonPath().getString(key);
    }

    // =====================================================================================
    // Password Generators
    // =====================================================================================

    public static String generateStrongPassword(int length) {
        final String UPPER = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        final String LOWER = "abcdefghijklmnopqrstuvwxyz";
        final String NUMBERS = "0123456789";
        final String SPECIAL = "!@#$%^&*()_+";
        final String ALL = UPPER + LOWER + NUMBERS + SPECIAL;

        StringBuilder password = new StringBuilder();

        // Ensure at least one character from each group
        password.append(randomChar(UPPER));
        password.append(randomChar(LOWER));
        password.append(randomChar(NUMBERS));
        password.append(randomChar(SPECIAL));

        // Fill remaining characters
        for (int i = 4; i < length; i++) {
            password.append(randomChar(ALL));
        }

        return shuffleString(password.toString());
    }

    public static String generateWeakPassword(int length) {
        final String WEAK_CHARS = "abcdefghijklmnopqrstuvwxyz1234567890";
        StringBuilder password = new StringBuilder();

        for (int i = 0; i < length; i++) {
            password.append(randomChar(WEAK_CHARS));
        }

        return password.toString();
    }

    private static char randomChar(String chars) {
        return chars.charAt(secureRandom.nextInt(chars.length()));
    }

    private static String shuffleString(String input) {
        List<Character> characters = new ArrayList<>();
        for (char c : input.toCharArray()) {
            characters.add(c);
        }
        Collections.shuffle(characters, secureRandom);

        StringBuilder result = new StringBuilder();
        for (char c : characters) {
            result.append(c);
        }
        return result.toString();
    }

    public static String InvalidToken() {
        return "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJkYXRhIjoiNjczYTYxZjA0YzA4YTY0Y2NlY2RlM2NjNGM0MjhiYWEyMDYxNTgxYjhmYjQ2ZTIxMjhhMDE3MGNlOGU2MzE1M2ExMjE3NDMyYjZkMDU4YWQ1ZGE5NGY5ODYyMTRkZTViNDE4ZDlhNGU2NjQ3ZDI2OTc1NmRjNDE0MmIzYzU1YmExZjJmODAxYjJlNjIwNzRhNTkyYmFjNTIwZDAwMWE0YmMwNDgyODVmN2IyODY5MjU2NWNmZWY3NWU0ODI3NmUwZmRhM2ViNzg2MjcyODhkYjNiOWM5OTkyNzA0OTQzZDY3MzQ2NTQwNWY0ZWNhYjNlOGNmODQ4ZGViOTAwNDVkMmJjM2RiYTA0MmE0MmNkMmZjZjZiMjQ5YTBlNmFmMGY3MDBhNmJjZDA0ZGVjMDI4YzIyZGRjOWQyN2M5YWYxYzZlNTdkYmQ5NDZlODUxN2E1MzBkNjZhMmJkMDlhM2U4OGMxYjg4MGFmNGFlNDNjMTU3MDE1ZDA4NDZhM2E4YWJjOWQwZTc5YzJmOWI3YmQzY2Q2YzA3OGNkNTEzYmVhMzNlMDRhYmZlZWE1MThkZGUyYjhiYzYwOTM1NTdlMmRkMDUxMjNlZDM0ZmI2MTkwNGJhMmEyNTEzOTViZTgzOTYyNGJlMDk5ZjA4NGNiZjRkYmFhYTUzYjFjYWE4NGIxYWUwNWQ3OGFkZjFjNDRkODlhYzhiN2YzNjQ1Yjg0MDE5Nzc3OWI4MzUyYTAyNzNiYWRlMDYyNWFiZjJjOTEwMzgyYzU1M2EzZWEyMDllZTFmOWY1MWE0Mzg5M2FmZTY2YWJkOWU2Y2YxMWQ3YjQ4ODBkZDg5YzU2OWIwYjM4NWVmMTMzNDQ3NTI3ZThjM2IwYjNlYjcxOWU5NzhkN2MyYjYyZTg0YmZmYjllMDExZWIxOTUwMGFkNTBjNmNiZjdkMGRkZjQ1NWI3MDVlMDgyMzQ2MWYxMmQxNmQ0NWRhYTNiNSJ9.6XiQaDTz4ZOj2v2cKI9qM1M92nnNNQRgFn0l4HEJPt4";
    }

    private static String cachedDeviceUUID;

    public static void setDeviceUUID(String deviceUUID) {
        cachedDeviceUUID = deviceUUID;
    }

    public static String getDeviceUUID() {
        if (cachedDeviceUUID == null) {
            throw new IllegalStateException("Device UUID has not been initialized. Call performDeviceLookup first.");
        }
        return cachedDeviceUUID;
    }

    public static String generateRandomEqubName() {
        String[] prefixes = {
                "EAGLELION", "UNITY", "FAMILY", "SUNRISE", "PROGRESS",
                "VICTORY", "HARMONY", "FREEDOM", "HOPE", "DREAM"
        };

        String[] suffixes = {
                "Fund", "Group", "Savings", "Circle", "Club",
                "Union", "Plan", "Alliance", "Network", "Society"
        };

        java.util.Random random = new java.util.Random();
        String prefix = prefixes[random.nextInt(prefixes.length)];
        String suffix = suffixes[random.nextInt(suffixes.length)];
        int number = 100 + random.nextInt(900); // adds a 3-digit number

        return prefix + " " + suffix + " " + number;
    }
    public static String generateDeviceUUID() {
        String chars = "abcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder uuid = new StringBuilder();
        Random random = new Random();

        for (int i = 0; i < 16; i++) {  // 16 characters long
            uuid.append(chars.charAt(random.nextInt(chars.length())));
        }

        return uuid.toString();
    }



    public static String randomPhoneNumber() {
            Random random = new Random();

            // Ethiopian mobile numbers start with +2519
            StringBuilder phone = new StringBuilder("+2519");

            // Generate remaining 8 digits
            for (int i = 0; i < 8; i++) {
                phone.append(random.nextInt(10)); // 0-9
            }

            return phone.toString();
        }




    public static String getPhoneBasedOnType(String type) {
        switch (type.toUpperCase()) {

            case "VALID":
                return randomPhoneNumber();

            case "INVALID_SHORT":
                return "09123";   // too short

            case "INVALID_LONG":
                return "091234567890";  // too long

            case "INVALID_FORMAT":
                return "123456789"; // does not start with 09

            case "INVALID_ALPHANUMERIC":
                return "09A23B567"; // letters inside

            case "EMPTY":
                return "";

            case "NULL":
                return null;

            default:
                throw new IllegalArgumentException("Unknown phone type: " + type);
        }
    }


}
