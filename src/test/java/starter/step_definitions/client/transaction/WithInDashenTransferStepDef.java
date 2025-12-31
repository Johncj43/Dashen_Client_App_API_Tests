package starter.step_definitions.client.transaction;
import io.cucumber.java.en.And;
import io.restassured.response.Response;
import starter.utils.HttpApiUtils;
import starter.utils.TestDataLoader;
import starter.utils.model.requestModel.client.transaction.PinVerify;
import starter.utils.model.requestModel.client.transaction.RequestPinVerify;
import starter.utils.model.requestModel.client.transaction.WithInDashenTransfer;

import java.util.HashMap;
import java.util.Map;

import static starter.utils.HelperUtils.convertObjectToJson;
import static starter.utils.PropertiesReader.getParameterProperties;
import static starter.utils.TestGlobalVariables.ContextEnum.*;
import static starter.utils.TestGlobalVariables.ContextEnum.HTTP_RESPONSE;
import static starter.utils.TestGlobalVariables.getContext;
import static starter.utils.TestGlobalVariables.setContext;

public class WithInDashenTransferStepDef {



        @And("device user {string} sends {string} for dashen user")
        public void device_user_sends_for_dashen_user(String id, String endpoint) {
            WithInDashenTransfer transfer = TestDataLoader.getTransferData(id);
            String deviceUuid = transfer.getDeviceuuid();
            String narrative = transfer.getNarrative();
            String installation = transfer.getInstallationdate();

            Map<String, String> requestBody = new HashMap<>();

            requestBody.put("narrative", narrative);


            Response response = HttpApiUtils.requestWithHeaders(
                    "POST",
                    getParameterProperties(endpoint),
                    getContext(ACCESS_TOKEN.name()),
                    deviceUuid,
                    installation,
                    convertObjectToJson(requestBody),
                    true,
                    getContext(DATA_TOKEN.name()),
                    true,
                    true,
                    getContext(SESSION_ID.name())
            );

            setContext(HTTP_RESPONSE.name(), response);

        }
    @And("device user {string} sends {string} for the pin verify methods")
    public void deviceUserSendsForPinVerifyMethods(String id, String endpoint) {

        PinVerify pinVerify = TestDataLoader.getPinVerifyData(id);

        String deviceUuid = pinVerify.getDeviceuuid();
        String installation = pinVerify.getInstallationdate();
        RequestPinVerify pinBody = new RequestPinVerify();
        pinBody.setPincode(pinVerify.getPincode());

        Response response = HttpApiUtils.requestWithHeaders(
                "POST",
                getParameterProperties(endpoint),
                getContext(ACCESS_TOKEN.name()),
                deviceUuid,
                installation,
                convertObjectToJson(pinBody),
                false,
                getContext(DATA_TOKEN.name()),
                true,
                false,
                getContext(SESSION_ID.name())

        );
        setContext(HTTP_RESPONSE.name(), response);
        String dataToken = response.jsonPath().getString("data.datatoken");
        if (dataToken != null) {
            setContext(DATA_TOKEN.name(), dataToken);
        } else {
            System.out.println("Datatoken is null, negative scenarion, skipping store context");
        }


    }
    }


