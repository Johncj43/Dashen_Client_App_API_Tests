package starter.utils;

import net.serenitybdd.core.di.SerenityInfrastructure;
import net.thucydides.model.util.EnvironmentVariables;

public class EnvConfig {

    private static final EnvironmentVariables variables = SerenityInfrastructure.getEnvironmentVariables();

    // Read from system properties or serenity.conf default
    private static final String environment = variables.getProperty("serenity.environment", "dev");
    private static final String platform = System.getProperty("platform", "android").toLowerCase();
    private static final String branch = System.getProperty("branch", "").toLowerCase();

    // Helper to read nested config values
    private static String get(String key) {
        return variables.getProperty("environments." + environment + "." + key);
    }

    private static String getPlatformProperty(String key) {
        return variables.getProperty("environments." + environment + ".platforms." + platform + "." + key);
    }

    private static String getBranchProperty(String role, String key) {
        return variables.getProperty("environments." + environment + ".branch." + role + "." + key);
    }

    private static String getCpsProperty(String role, String key) {
        return variables.getProperty("environments." + environment + ".cps." + role + "." + key);
    }

    private static String getDbProperty(String key) {
        return variables.getProperty("environments." + environment + ".mongo_db." + key);
    }

    // Public getters
    public static String getEnvironmentName() {
        return environment;
    }

    public static String getPlatformName() {
        return platform;
    }

    public static String getBaseUrl() {
        return get("base.url");
    }

    public static String getPhoneNumber() {
        return get("phone_number");
    }

    public static String getPin() {
        return get("pin");
    }

    public static String getAccountNumber() {
        return get("accountNumber");
    }

    public static String getAppVersion() {
        return getPlatformProperty("appversion");
    }

    public static String getDeviceUUID() {
        return getPlatformProperty("deviceuuid");
    }

    public static String getInstallationDate() {
        return getPlatformProperty("installationdate");
    }

    public static String getPlatformType() {
        return getPlatformProperty("platform");
    }
    public static String getSourceApp() {
        return getPlatformProperty("sourceapp");
    }
    public static String getOtpFor(){
        return getPlatformProperty("otpfor");
    }


    public static String getMakerUsername() {
        return getBranchProperty("Maker", "username");
    }

    public static String getMakerPassword() {
        return getBranchProperty("Maker", "password");
    }

    public static String getCheckerUsername() {
        return getBranchProperty("Checker", "username");
    }

    public static String getCheckerPassword() {
        return getBranchProperty("Checker", "password");
    }


    public static String getCpsMakerUsername() {
        return getCpsProperty("Maker", "username");
    }

    public static String getCpsMakerPassword() {
        return getCpsProperty("Maker", "password");
    }

    public static String getCpsCheckerUsername() {
        return getCpsProperty("Checker", "username");
    }

    public static String getCpsCheckerPassword() {
        return getCpsProperty("Checker", "password");
    }

    public static String getDbUrl() {
        return getDbProperty("db_url");
    }

    public static String getDbName() {
        return getDbProperty("db_name");
    }
    public static String getMongoDbUrl() {
        return getDbProperty("db_url");
    }

    public static String getMongoDbName() {
        return getDbProperty("db_name");
    }

}


