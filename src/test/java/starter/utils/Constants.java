package starter.utils;

import java.util.regex.Pattern;

public class Constants {
    public static final String NAMED_PARAM_HINT_PROPERTY = "!(.*?)!";
    public static final String NAMED_PARAM_HINT_STRING_OF_LENGTH = "string-of-length:";
    public static final String NAMED_PARAM_HINT_NUMBER_OF_LENGTH = "number-of-length:";
    public static final String NAMED_PARAM_HINT_GENERATED_UUID = "generated_uuid";
    public static final String TO_UPPER_CASE = "@to-upper-case:";
    public static final Pattern PATTERN_NAMED_PARAM_HINT_PROPERTY = Pattern.compile(NAMED_PARAM_HINT_PROPERTY);
    public static final Pattern PATTERN_NAMED_PARAM_TO_UPPER_CASE = Pattern.compile("@to-upper-case:(.*?)@");


}
