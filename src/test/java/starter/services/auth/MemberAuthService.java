package starter.services.auth;

import io.restassured.response.Response;
import org.jetbrains.annotations.NotNull;
import starter.utils.HelperUtils;
import starter.utils.HttpApiUtils;
import starter.utils.TestDataLoader;
import starter.utils.model.requestModel.auth.HeaderData;

import java.util.HashMap;
import java.util.Map;

import static starter.utils.HelperUtils.convertObjectToJson;
import static starter.utils.PropertiesReader.getParameterProperties;
import static starter.utils.TestGlobalVariables.getContext;

public class MemberAuthService {

    @NotNull
    public static Response pinChange(String endpoint, String id) {
        HeaderData user= TestDataLoader.getDeviceLookData(id);

        String installationDate= user.getInstallationdate();
        String deviceuuid= user.getDeviceuuid();
        String newPin= HelperUtils.generatePins(6,"strong");
        String oldPin="718940";

        Map<String,Object> body=new HashMap<>();
        body.put("oldpin", oldPin);  // FIXED: ensure string
        body.put("newpin", newPin);
        Response response = HttpApiUtils.requestWithStandardHeadersSimples(
                "POST",
                getContext("ACCESS_TOKEN"),
                getParameterProperties(endpoint),
                deviceuuid,
                installationDate,
                getContext("SESSION_ID"),
                convertObjectToJson(body)


        );
        return response;
    }

}
