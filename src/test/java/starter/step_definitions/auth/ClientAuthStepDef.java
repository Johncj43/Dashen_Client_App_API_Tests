package starter.step_definitions.auth;
import io.cucumber.java.PendingException;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import starter.utils.HelperUtils;
import starter.utils.HttpApiUtils;
import starter.utils.TestDataLoader;
import starter.utils.model.requestModel.auth.HeaderData;
import starter.utils.model.requestModel.auth.PinStrength;

import java.util.HashMap;
import java.util.Map;

import static starter.utils.HelperUtils.convertObjectToJson;
import static starter.utils.HelperUtils.generateRandomPIN;
import static starter.utils.PropertiesReader.getParameterProperties;
import static starter.utils.TestGlobalVariables.ContextEnum.*;
import static starter.utils.TestGlobalVariables.getContext;
import static starter.utils.TestGlobalVariables.setContext;


public class ClientAuthStepDef {

    @Given("I send a request to {string} with a {string} to lookup user's device")
    public void iSendARequestToWithAToLookupUserSDevice(String endpoint, String id) {
        HeaderData userData = TestDataLoader.getDeviceLookData(id);
        String deviceuuid = userData.getDeviceuuid();
        String installationdate = userData.getInstallationdate();
        Response response =HttpApiUtils.requestWithStandardHeaders(
                "GET",
                null,
                getParameterProperties(endpoint),
                deviceuuid,
                installationdate,
                null,
                null,
                null


        );
        setContext(HTTP_RESPONSE.name(), response);
    }

    @Given("I send a POST request to {string} with a {string} and a valid phone number to register the user")
    public void iSendAPOSTRequestToWithAAndAValidPhoneNumberToRegisterTheUser(String endpoint, String id) {

        HeaderData userData = TestDataLoader.getDeviceLookData(id);
        String installationDate = userData.getInstallationdate();

        String deviceUUID = HelperUtils.generateRandomDeviceUUID();
        setContext("DEVICE_UUID", deviceUUID);
        Map<String, Object> body = new HashMap<>();
        String phoneNumber = HelperUtils.randomPhoneNumber();
        body.put("phonenumber",phoneNumber);
        Response response = HttpApiUtils.requestWithStandardHeaders(
                "POST",
                null,
                getParameterProperties(endpoint),
                deviceUUID,
                installationDate,
                null,
                null,
                convertObjectToJson(body)
        );
        setContext(HTTP_RESPONSE.name(), response);

        String otpCode = response.jsonPath().getString("data.otpcode");
        String accessToken = response.jsonPath().getString("data.accessToken");
        setContext("OTP_CODE", otpCode);
        setContext("ACCESS_TOKEN", accessToken);

    }


    @When("I send a POST request to {string} with a {string} valid OTP to approve the signup request")
    public void iSendAPOSTRequestToWithAValidOTPToApproveTheSignupRequest(String endpoint, String id) {
        HeaderData userData=TestDataLoader.getDeviceLookData(id);
        String instaallationDate=userData.getInstallationdate();
        String deviceUUID=(String)getContext("DEVICE_UUID");
        Map<String,Object> body=new HashMap<>();
        body.put("otpcode",(String)getContext("OTP_CODE"));
        body.put("fullname",HelperUtils.generateRandomFullName());
        Response response=HttpApiUtils.requestWithStandardHeaders(
                "POST",
                getContext("ACCESS_TOKEN"),
                getParameterProperties(endpoint),
                deviceUUID,
                instaallationDate,
                null,
                null,
                convertObjectToJson(body)

        );
        String accesstoken = response.jsonPath().getString("data.accessToken");
        setContext("ACCESS_TOKENNN", accesstoken);



    }

    @And("I send a POST request to {string} with a {string} and password")
    public void iSendAPOSTRequestToWithAAndPassword(String endpoint, String id) {
        HeaderData userData=TestDataLoader.getDeviceLookData(id);
        String installationDate=userData.getInstallationdate();
        String deviceUUID=(String)getContext("DEVICE_UUID");
        String otpFor=userData.getOtpfor();
        Map<String, Object>setBody=new HashMap<>();
        setBody.put("pincode","434353");
        Response response=HttpApiUtils.requestWithStandardHeaderst(
                "POST",
                getContext("ACCESS_TOKENNN"),
                getParameterProperties(endpoint),
                deviceUUID,
                installationDate,
                convertObjectToJson(setBody),
                otpFor



        );
        setContext(HTTP_RESPONSE.name(),response);
        String token=response.jsonPath().getString("data.accessToken");
        String session=response.jsonPath().getString("data.sessionID");
        setContext(SESSION_ID.name(), session);
        setContext("ACCESS_TOKEN",token);



    }


    @Given("I send a POST request to {string} with a {string} and a valid phone number to register the client app")
    public void iSendAPOSTRequestToWithAAndAValidPhoneNumberToRegisterTheClientApp(String endpoint, String id) {

        HeaderData userData = TestDataLoader.getDeviceLookData(id);
        String installationDate = userData.getInstallationdate();
        String deviceuuid=userData.getDeviceuuid();
        Map<String, Object> body = new HashMap<>();
        String phoneNumber = HelperUtils.randomPhoneNumber();
        body.put("phonenumber", phoneNumber);
        setContext("PHONE_NUMBER", phoneNumber);

        Response response = HttpApiUtils.requestWithStandardHeaders(
                "POST",
                null,
                getParameterProperties(endpoint),
                deviceuuid,
                installationDate,
                null,
                null,
                convertObjectToJson(body)
        );


    }

    @When("I send a POST request to {string} with a {string} and a registered phone number to register the client app")
    public void iSendAPOSTRequestToWithAAndARegisteredPhoneNumberToRegisterTheClientApp(String endpoint, String id) {
        HeaderData userData = TestDataLoader.getDeviceLookData(id);
        String installationDate = userData.getInstallationdate();
        String deviceuuid=userData.getDeviceuuid();
        Map<String, Object> body = new HashMap<>();
        String phoneNumber = HelperUtils.randomPhoneNumber();
        body.put("phonenumber", phoneNumber);
        setContext("PHONE_NUMBER", phoneNumber);

        Response response = HttpApiUtils.requestWithStandardHeaders(
                "POST",
                null,
                getParameterProperties(endpoint),
                deviceuuid,
                installationDate,
                null,
                null,
                convertObjectToJson(body)
        );
    }

    @When("I send a POST request to {string} with a {string} valid OTP to approve the signup request of user")
    public void iSendAPOSTRequestToWithAValidOTPToApproveTheSignupRequestOfUser(String endpoint, String id) {
        HeaderData userData=TestDataLoader.getDeviceLookData(id);
        String instaallationDate=userData.getInstallationdate();
        String deviceUUID=(String)getContext("DEVICE_UUID");
        Map<String,Object> body=new HashMap<>();
        body.put("otpcode",(String)getContext("OTP_CODE"));
        body.put("fullname",HelperUtils.generateRandomFullName());
        Response response=HttpApiUtils.requestWithStandardHeaders(
                "POST",
                null,
                getParameterProperties(endpoint),
                deviceUUID,
                instaallationDate,
                null,
                null,
                convertObjectToJson(body)

        );

    }

    @When("I send a POST request to {string} using an {string} to approve the user’s signup request")
    public void iSendAPOSTRequestToUsingAnToApproveTheUserSSignupRequest(String endpoint, String id) {
            HeaderData userData=TestDataLoader.getDeviceLookData(id);
            String instaallationDate=userData.getInstallationdate();
            String deviceUUID=(String)getContext("DEVICE_UUID");
            Map<String,Object> body=new HashMap<>();
            body.put("otpcode","646373");
            body.put("fullname",HelperUtils.generateRandomFullName());
            Response response=HttpApiUtils.requestWithStandardHeaders(
                    "POST",
                    getContext("ACCESS_TOKEN"),
                    getParameterProperties(endpoint),
                    deviceUUID,
                    instaallationDate,
                    null,
                    null,
                    convertObjectToJson(body)

            );

    }

    @And("I send a POST request to {string} with a {string} and an incorrectly formatted PIN")
    public void iSendAPOSTRequestToWithAAndAnIncorrectlyFormattedPIN(String endpoint, String id) {
        HeaderData userData=TestDataLoader.getDeviceLookData(id);
        String installationDate=userData.getInstallationdate();
        String deviceUUID=(String)getContext("DEVICE_UUID");
        Map<String, Object>setBody=new HashMap<>();
        setBody.put("pincode","4343");
        Response response=HttpApiUtils.requestWithStandardHeaders(
                "POST",
                getContext("ACCESS_TOKEN"),
                getParameterProperties(endpoint),
                deviceUUID,
                installationDate,
                null,
                null,
                convertObjectToJson(setBody)

        );

    }

    @When("I send a POST request to {string} with a {string} to access client app")
    public void iSendAPOSTRequestToWithAToAccessClientApp(String endpoint, String id) {
        HeaderData user=TestDataLoader.getDeviceLookData(id);
        String deviceuuid=user.getDeviceuuid();
        String installationDate= user.getInstallationdate();
        String pinCode= user.getPincode();
        Map<String,Object>body=new HashMap<>();
        body.put("pincode",(String)pinCode);
        Response response=HttpApiUtils.requestWithStandardHeadersSimple(
                "POST",
                null,
                getParameterProperties(endpoint),
                deviceuuid,
                installationDate,
                convertObjectToJson(body)
        );
        String accessToken= response.jsonPath().getString("data.accessToken");
        String sessionID= response.jsonPath().getString("data.sessionID");
        if (accessToken != null) setContext("ACCESS_TOKEN", accessToken);
        if (sessionID != null) setContext(SESSION_ID.name(), sessionID);
    }

    @And("I send a POST request to {string} with a {string} payload")
    public void iSendAPOSTRequestToWithAPayload(String endpoint, String id) {
        HeaderData user=TestDataLoader.getDeviceLookData(id);
      String installationDate= user.getInstallationdate();
      String deviceuuid= user.getDeviceuuid();
      String newPin=HelperUtils.generatePins(6,"strong");
      String oldPin="025256";
      Map<String,Object> body=new HashMap<>();
        body.put("oldpin", (String)oldPin);  // FIXED: ensure string
        body.put("newpin", (String)newPin);
      Response response=HttpApiUtils.requestWithStandardHeadersSimples(
              "POST",
              getContext("ACCESS_TOKEN"),
              getParameterProperties(endpoint),
              deviceuuid,
              installationDate,
              getContext(SESSION_ID.name()),
              convertObjectToJson(body)


      );
        System.out.println("PIN changed successfully! Old PIN: " + oldPin + " → New PIN: " + newPin);
        setContext("NEW_PIN",newPin);



    }

    @And("I send a POST request to {string} with a {string} payloads")
    public void iSendAPOSTRequestToWithAPayloads(String endpoint, String id) {
        HeaderData user=TestDataLoader.getDeviceLookData(id);
        String installationDate= user.getInstallationdate();
        String deviceuuid= user.getDeviceuuid();
        String newPin=HelperUtils.generatePins(6,"repeated");
        String oldPin=getContext("NEW_PIN");
        Map<String,Object> body=new HashMap<>();
        body.put("oldpin", (String)oldPin);  // FIXED: ensure string
        body.put("newpin", (String)newPin);
        Response response=HttpApiUtils.requestWithStandardHeadersSimples(
                "POST",
                getContext("ACCESS_TOKEN"),
                getParameterProperties(endpoint),
                deviceuuid,
                installationDate,
                getContext(SESSION_ID.name()),
                convertObjectToJson(body)


        );

    }

    @And("I send a POST request to {string} with an {string} payload")
    public void iSendAPOSTRequestToWithAnPayload(String endpoint, String id) {
        HeaderData user=TestDataLoader.getDeviceLookData(id);
        String installationDate= user.getInstallationdate();
        String deviceuuid= user.getDeviceuuid();
        String newPin=HelperUtils.generatePins(6,"strong");
        String oldPin="718940";
        Map<String,Object> body=new HashMap<>();
        body.put("oldpin", (String)oldPin);  // FIXED: ensure string
        body.put("newpin", (String)newPin);
        Response response=HttpApiUtils.requestWithStandardHeadersSimples(
                "POST",
                getContext("ACCESS_TOKEN"),
                getParameterProperties(endpoint),
                deviceuuid,
                installationDate,
                getContext(SESSION_ID.name()),
                convertObjectToJson(body)


        );

    }

    @And("I send a POST request to {string} with  {string} payload")
    public void iSendAPOSTRequestToWithPayload(String endpoint, String id) {
        HeaderData user=TestDataLoader.getDeviceLookData(id);
        String installationDate= user.getInstallationdate();
        String deviceuuid= user.getDeviceuuid();
        String newPin=HelperUtils.generatePins(6,"strong");
        String oldPin="718939";
        Map<String,Object> body=new HashMap<>();
        body.put("oldpin", (String)oldPin);  // FIXED: ensure string
        body.put("newpin", (String)newPin);
        Response response=HttpApiUtils.requestWithStandardHeadersSimples(
                "POST",
                null,
                getParameterProperties(endpoint),
                deviceuuid,
                installationDate,
                getContext(SESSION_ID.name()),
                convertObjectToJson(body)


        );

    }


    @And("I send a POST request to {string} with  a {string} to change PIN")
    public void iSendAPOSTRequestToWithAToChangePIN(String endpoint, String id) {
        HeaderData user=TestDataLoader.getDeviceLookData(id);
        String installationDate= user.getInstallationdate();
        String deviceuuid= user.getDeviceuuid();
        String newPin=HelperUtils.generatePins(6,"strong");
        String oldPin="718939";
        Map<String,Object> body=new HashMap<>();
        body.put("oldpin", (String)oldPin);  // FIXED: ensure string
        body.put("newpin", (String)newPin);
        Response response=HttpApiUtils.requestWithStandardHeadersSimples(
                "POST",
                getContext("ACCESS_TOKEN"),
                getParameterProperties(endpoint),
                deviceuuid,
                installationDate,
                getContext(SESSION_ID.name()),
                convertObjectToJson(body)


        );


    }

    @And("I send a POST request to {string} using a {string} to verify the PIN strength")
    public void iSendAPOSTRequestToUsingAToVerifyThePINStrength(String endpoint, String id) {
        HeaderData check=TestDataLoader.getDeviceLookData(id);
        String deviceuuid=check.getDeviceuuid();
        String installationdate=check.getInstallationdate();
        String newPin=check.getNewpin();
        Map<String,Object>body=new HashMap<>();
        body.put("newpin",newPin);
        Response response=HttpApiUtils.requestWithStandardHeadersSimple(
                "POST",
                getContext("ACCESS_TOKEN"),
                getParameterProperties(endpoint),
                deviceuuid,
                installationdate,
                convertObjectToJson(body)
        );

    }

    @Given("I send a POST request to {string} with a {string} to look up the user")
    public void iSendAPOSTRequestToWithAToLookUpTheUser(String endpoint, String id) {
        HeaderData reset=TestDataLoader.getDeviceLookData(id);
        String installationdate=reset.getInstallationdate();
        String deviceuuid=reset.getDeviceuuid();
        String otpFor= reset.getOtpfor();
        String phoneNumber=reset.getPhonenumber();

        Map<String,Object>body=Map.of(
                "phonenumber",phoneNumber);
        Response response = HttpApiUtils.requestWithStandardHeaderst(
                "POST",
                null,
                getParameterProperties(endpoint),
                deviceuuid,
                installationdate,
                convertObjectToJson(body),
                otpFor
        );
        String token=response.jsonPath().getString("data.accessToken");
        String otp = response.jsonPath().getString("data.otpcode");
        setContext("Token", token);
        setContext("OTP", otp);
        setContext(HTTP_RESPONSE.name(),response);



    }

    @When("I send a POST request to {string} with a {string} valid OTP to approve the pin reset request")
    public void iSendAPOSTRequestToWithAValidOTPToApproveThePinResetRequest(String endpoint, String id) {
        HeaderData confirm=TestDataLoader.getDeviceLookData(id);
        String installationdate=confirm.getInstallationdate();
        String deviceuuid=confirm.getDeviceuuid();
        String otpFor= confirm.getOtpfor();
        Map<String,Object> body=Map.of("otpcode",getContext("OTP"));
      Response response = HttpApiUtils.requestWithStandardHeaderst(
              "POST",
              getContext("Token"),
              getParameterProperties(endpoint),
              deviceuuid,
              installationdate,
              convertObjectToJson(body),
              otpFor
      );
      setContext(HTTP_RESPONSE.name(), response);
      String token=response.jsonPath().getString("data.accessToken");
      setContext("TOKEN",token);

    }

    @And("I send a POST request to {string} with a {string} and new password")
    public void iSendAPOSTRequestToWithAAndNewPassword(String endpoint, String id) {
        HeaderData user=TestDataLoader.getDeviceLookData(id);
        String installationdate=user.getInstallationdate();
        String deviceuuid= user.getDeviceuuid();
        String pinCode= HelperUtils.generateRandomPIN();
        String otpFor= user.getOtpfor();
        Map<String,Object> otpBody=Map.of("pincode",pinCode);
        Response response = HttpApiUtils.requestWithStandardHeaderst(
                "POST",
                getContext("TOKEN"),
                getParameterProperties(endpoint),
                deviceuuid,
                installationdate,
                convertObjectToJson(otpBody),
                otpFor
        );


    }

    @When("I send a POST request to {string} with a {string}  to approve the pin reset request")
    public void iSendAPOSTRequestToWithAToApproveThePinResetRequest(String endpoint, String id) {
        HeaderData confirm=TestDataLoader.getDeviceLookData(id);
        String installationdate=confirm.getInstallationdate();
        String deviceuuid=confirm.getDeviceuuid();
        String otpFor= confirm.getOtpfor();
        Map<String,Object> body=Map.of("otpcode","473837");
        Response response = HttpApiUtils.requestWithStandardHeaderst(
                "POST",
                getContext("Token"),
                getParameterProperties(endpoint),
                deviceuuid,
                installationdate,
                convertObjectToJson(body),
                otpFor
        );
    }

    @Given("I send a POST request to {string} with a {string} to to set a PIN for the user’s registered phone number")
    public void iSendAPOSTRequestToWithAToToSetAPINForTheUserSRegisteredPhoneNumber(String endpoint, String id) {
       HeaderData setPin=TestDataLoader.getDeviceLookData(id);
       String installatiodate=setPin.getInstallationdate();
       String deviceuuid= HelperUtils.generateRandomDeviceUUID();
       String otpFor=setPin.getOtpfor();
       Map<String,Object>body=Map.of("phonenumber","+251943285670");
       Response response = HttpApiUtils.requestWithStandardHeaderst(
               "POST",
               null,
               getParameterProperties(endpoint),
               deviceuuid,
               installatiodate,
               convertObjectToJson(body),
               otpFor


       );
       String accessToekn=response.jsonPath().getString("data.accessToken");
       String otpCode=response.jsonPath().getString("data.otpcode");
       setContext("TOKEN",accessToekn);
       setContext("OTP",otpCode);
       setContext("DEVICE_UUIDs",deviceuuid);
    }

    @When("I send a POST request to {string} with a {string} valid OTP to approve the pin set request")
    public void iSendAPOSTRequestToWithAValidOTPToApproveThePinSetRequest(String endpoint, String id) {
       HeaderData setPin=TestDataLoader.getDeviceLookData(id);
       String installationdate= setPin.getInstallationdate();;
       String deviceuuid=getContext("DEVICE_UUIDs");
       String otpFor=setPin.getOtpfor();
       String otpCode=getContext("OTP");
       Map<String,Object>body=Map.of("otpcode",otpCode);
       Response response = HttpApiUtils.requestWithStandardHeaderst(
               "POST",
               getContext("TOKEN"),
               getParameterProperties(endpoint),
               deviceuuid,
               installationdate,
               convertObjectToJson(body),
               otpFor

       );
       String accessToken= response.jsonPath().getString("data.accessToken");
       setContext("ACCESS_TOKEN",accessToken);

    }

    @And("I send a POST request to {string} with a {string} and new password with new device UUID")
    public void iSendAPOSTRequestToWithAAndNewPasswordWithNewDeviceUUID(String endpoint, String id) {
       HeaderData user=TestDataLoader.getDeviceLookData(id);
       String installationdate=user.getInstallationdate();
       String deviceuuid=getContext("DEVICE_UUIDs");
       String otpFor=user.getOtpfor();
       String pinCode=HelperUtils.generateRandomPIN();
//       String pinCode=user.getPincode();
       Map<String,Object>body=Map.of("pincode",pinCode);
       Response response = HttpApiUtils.requestWithStandardHeaderst(
               "POST",
               getContext("ACCESS_TOKEN"),
               getParameterProperties(endpoint),
               deviceuuid,
               installationdate,
               convertObjectToJson(body),
               otpFor

       );


    }

    @When("I send a POST request to {string} with a {string} valid OTP to approve the pin set requests")
    public void iSendAPOSTRequestToWithAValidOTPToApproveThePinSetRequests(String endpoint, String id) {
       HeaderData user=TestDataLoader.getDeviceLookData(id);
       String installationdate=user.getInstallationdate();
       String deviceuuid=getContext("DEVICE_UUIDs");
       String otpFor= user.getOtpfor();
       Map<String,Object> body=Map.of("otpcode","123456");
       Response response = HttpApiUtils.requestWithStandardHeaderst(
               "POST",
               getContext("TOKEN"),
               getParameterProperties(endpoint),
               deviceuuid,
               installationdate,
               convertObjectToJson(body),
               otpFor
       );
    }

    @And("I send a POST request to {string} with a {string} and new password with new device UUIDs")
    public void iSendAPOSTRequestToWithAAndNewPasswordWithNewDeviceUUIDs(String endpoint, String id) {
        HeaderData user=TestDataLoader.getDeviceLookData(id);
        String installationdate=user.getInstallationdate();
        String deviceuuid=getContext("DEVICE_UUIDs");
        String otpFor=user.getOtpfor();
        String pinCode=user.getPincode();
        String token=user.getToken();
        Map<String,Object>body=Map.of("pincode",pinCode);
        Response response = HttpApiUtils.requestWithStandardHeaderst(
                "POST",
                token,
                getParameterProperties(endpoint),
                deviceuuid,
                installationdate,
                convertObjectToJson(body),
                otpFor

        );

    }

    @And("I send a POST request to {string} with a {string} to change expired pin")
    public void iSendAPOSTRequestToWithAToChangeExpiredPin(String endpoint, String id) {
      HeaderData user=TestDataLoader.getDeviceLookData(id);
      String installationdate =user.getInstallationdate();
      String otpFor= user.getOtpfor();
      String newPin=HelperUtils.generateRandomPIN();
      String deviceuuid=getContext("DEVICE_UUID");
      Map<String,Object>body=Map.of("oldpin","434353",
                                         "newpin",newPin);
      Response response = HttpApiUtils.requestWithStandardHeaderst(
              "POST",
              null,
              getParameterProperties(endpoint),
              deviceuuid,
              installationdate,
              convertObjectToJson(body),
              otpFor
      );


    }

    @And("I send a POST request to {string} with a {string} to change expired pin with weak password")
    public void iSendAPOSTRequestToWithAToChangeExpiredPinWithWeakPassword(String endpoint, String id) {
    HeaderData user=TestDataLoader.getDeviceLookData(id);
    String installationdate =user.getInstallationdate();
    String otpFor= user.getOtpfor();
    String deviceuuid=getContext("DEVICE_UUID");
        Map<String,Object>body=Map.of("oldpin","434353",
                "newpin","123456");
        Response response = HttpApiUtils.requestWithStandardHeaderst(
                "POST",
                null,
                getParameterProperties(endpoint),
                deviceuuid,
                installationdate,
                convertObjectToJson(body),
                otpFor
        );


    }
    @And("I send a POST request to {string} with a {string} to change expired pin with empty field pin set")
    public void iSendAPOSTRequestToWithAToChangeExpiredPinWithEmptyFieldPinSet(String endpoint, String id) {
        HeaderData user=TestDataLoader.getDeviceLookData(id);
        String installationdate=user.getInstallationdate();
        String otpFor= user.getOtpfor();
        String deviceuuid=getContext("DEVICE_UUID");
        Map<String,Object>body=Map.of("oldpin","434353",
                                       "newpin","");
        Response response = HttpApiUtils.requestWithStandardHeaderst(
                "POST",
                null,
                getParameterProperties(endpoint),
                deviceuuid,
                installationdate,
                convertObjectToJson(body),
                otpFor
        );



    }

    @And("I send a POST request to {string} with a {string} to change expired pin without deviceUUID")
    public void iSendAPOSTRequestToWithAToChangeExpiredPinWithoutDeviceUUID(String endpoint, String id) {
        HeaderData user=TestDataLoader.getDeviceLookData(id);
        String installationdate=user.getInstallationdate();
        String otpFor= user.getOtpfor();
        String newPin=HelperUtils.generateRandomPIN();
        Map<String,Object>body=Map.of("oldpin","434353",
                "newpin",generateRandomPIN());
        Response response = HttpApiUtils.requestWithStandardHeaderst(
                "POST",
                null,
                getParameterProperties(endpoint),
                null,
                installationdate,
                convertObjectToJson(body),
                otpFor
        );
    }

    @And("I send a POST request to {string} with a {string} with access token")
    public void iSendAPOSTRequestToWithAWithAccessToken(String endpoint, String id) {
        HeaderData user=TestDataLoader.getDeviceLookData(id);
        String installationdate=user.getInstallationdate();
        String otpFor= user.getOtpfor();
        String newPin=HelperUtils.generateRandomPIN();
        String xRequestId=getContext(SESSION_ID.name());
        String deviceuuid = getContext("DEVICE_UUID");
        Response response = HttpApiUtils.requestWithStandardHeadersSimples(
                "DELETE",
                getContext("ACCESS_TOKEN"),
                getParameterProperties(endpoint),
                deviceuuid,
                installationdate,
                xRequestId,
                null
        );

    }

}
