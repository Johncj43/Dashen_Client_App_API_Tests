package starter.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import starter.utils.model.requestModel.auth.HeaderData;
import starter.utils.model.requestModel.auth.PinStrength;
import starter.utils.model.requestModel.client.*;
import starter.utils.model.requestModel.client.budget.BudgetData;
import starter.utils.model.requestModel.client.chat.Chat;
import starter.utils.model.requestModel.client.chat.ChatMoneyRequest;
import starter.utils.model.requestModel.client.core.*;
import starter.utils.model.requestModel.client.core_money_request.MoneyRequest;
import starter.utils.model.requestModel.client.services.GetFees;
import starter.utils.model.requestModel.client.topup.Topup;
import starter.utils.model.requestModel.client.transaction.*;
import starter.utils.model.requestModel.client.users.LoginData;
import starter.utils.model.requestModel.client.utilities.*;
import starter.utils.model.requestModel.client.wallet.Wallet;

import java.io.File;
import java.security.PublicKey;
import java.util.Arrays;


public class TestDataLoader {

    private static final String BASE_PATH = "src/test/resources/test_data/client/";

    private static final ObjectMapper mapper = new ObjectMapper();

    /**
     * Generic method to load test data from JSON.
     */
    private static <T> T loadTestData(String fileName, String id, Class<T[]> clazz) {
        try {
            //#src/test/resources/test_data/actions_data.json
            String path = BASE_PATH + fileName;
            T[] allData = mapper.readValue(new File(path), clazz);
            System.out.println("🔎 Looking for id: [" + id + "]");
            return Arrays.stream(allData)
                    .filter(d -> {
                        try {
                            return d.getClass().getMethod("getId")
                                    .invoke(d).toString().equalsIgnoreCase(id);
                        } catch (Exception e) {
                            throw new RuntimeException("Missing getProfileName() method in class: " + d.getClass(), e);
                        }
                    })
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("Test data not found for profile: " + id));
        } catch (Exception e) {
            throw new RuntimeException("Failed to load test data: " + id, e);
        }
    }
    public static HeaderData getDeviceLookData(String id){
        return loadTestData("device_look_data.json",id,HeaderData[].class);
    }
    public static PinStrength getCheckPinStrength(String id){
        return loadTestData("check_pin_strength.json",id, PinStrength[].class);
    }
    public static LoginData getLoginData(String id){
        return loadTestData("login_data.json",id, LoginData[].class);

    }
    public static GetFees getGetFeesData(String id){
        return loadTestData("get_fees_data.json",id,GetFees[].class);
    }
    public static VerifyTransaction getVerifyTransactionData(String id){
        return loadTestData("verify_transaction_data.json",id, VerifyTransaction[].class);
    }
public static Topup getTopupData(String id){
        return loadTestData("topup_data.json",id,Topup[].class);
}
public static AccountLookup getAccountLookupData(String id){
        return loadTestData("account_lookup_data.json",id, AccountLookup[].class);
}
public static Wallet getWalletData(String id){
        return loadTestData("wallet_data.json",id, Wallet[].class);
}
public static Chat getChatData(String id){
        return loadTestData("chat_data.json",id, Chat[].class);
}
public static ChatMoneyRequest getChatMoneyRequestData(String id){
        return loadTestData("chat_money_request.json",id,ChatMoneyRequest[].class);
}
    public static BudgetData getBudgetData(String id){
        return loadTestData("budget_data.json",id, BudgetData[].class);
    }
    public static MoneyRequest getMoneyRequestData(String id){
        return loadTestData("money_request_data.json",id, MoneyRequest[].class);
    }
    public static MinistatementData getMiniStatementData(String id) {
        return loadTestData("mini_statement_data.json", id, MinistatementData[].class);
    }
    public static LinkOwnAccount getLinkOwnAccountData(String id) {
        return loadTestData("link_own_account_data.json", id, LinkOwnAccount[].class);
    }

    public static GetIpsAccountList getGetIpsAccountListData(String id) {
        return loadTestData("get_ips_bank_list_data.json", id, GetIpsAccountList[].class);
    }
    public static EncryptedAccount getEncryptedAccountData(String id) {
        return loadTestData("encrypted_account_data.json", id, EncryptedAccount[].class);
    }
    public static InputValidationData getInputValidationData(String id) {
        return loadTestData("input_validation_data.json", id, InputValidationData[].class);
    }

    public static PaginateAdvertData getPaginateAdvertData(String id) {
        return loadTestData("paginate_advert_data.json", id, PaginateAdvertData[].class);
    }
    public static AccountLookupp getAccountLookuppData(String id) {
        return loadTestData("account_lookupp_data.json", id, AccountLookupp[].class);
    }

    public static GetFee getGetFeeData(String id) {
        return loadTestData("get_fee_data.json", id, GetFee[].class);
    }
    public static WithInDashenTransfer getTransferData(String id) {
        return loadTestData("with_in _dashen_transfer_data.json", id, WithInDashenTransfer[].class);
    }
    public static FetchMethod getTransactionData(String id) {
        return loadTestData("fetch_methods_data.json", id, FetchMethod[].class);
    }
    public static PinVerify getPinVerifyData(String id) {
        return loadTestData("pin_verify_data.json", id, PinVerify[].class);
    }
    public static VerifyQrData getVerifyQrData(String id) {
        return loadTestData("verify_qr_data.json", id, VerifyQrData[].class);
    }
    public static FetchTransaction getFetchTransaction(String id) {
        return loadTestData("fetch_transactions_data.json", id, FetchTransaction[].class);
    }
    public static FetchCbsTransaction getFetchCbsTransactionData(String id) {
        return loadTestData("fetch_cbs_transactions_data.json", id, FetchCbsTransaction[].class);
    }
    public static Beneficiaries getBeneficiariesData(String id) {
        return loadTestData("dashen_get_beneficiaries_data.json", id, Beneficiaries[].class);
    }
    public static FeedbackRequest getFeedbackRequestData(String id) {
        return loadTestData("send_feed_back_data.json", id, FeedbackRequest[].class);
    }

    public static QueryStudentData getQueryStudentData(String id) {
        return loadTestData("query_student_data.json", id, QueryStudentData[].class);
    }
    public static FetchStudentUnpaidDetailsData getFetchStudentUnpaidDetailsData(String id) {
        return loadTestData("student_unpaid_query_payment_details_data.json", id, FetchStudentUnpaidDetailsData[].class);
    }
    public static FetchStudentPaidDetailsData getFetchStudentPaidDetailsData(String id) {
        return loadTestData("student_paid_query_payment_details_data.json", id, FetchStudentPaidDetailsData[].class);
    }
    public static FlyGateData getFlyGateDataData(String id) {
        return loadTestData("fly_gate_data.json", id, FlyGateData[].class);
    }

    public static PaybillsData getPaybillsData(String id) {
        return loadTestData("pay_bills_data.json", id, PaybillsData[].class);
    }

    public static FetchDstvData getFetchDstvData(String id) {
        return loadTestData("fetch_dstv_data.json", id, FetchDstvData[].class);
    }
    public static DsTvData getDstvDataData(String id) {
        return loadTestData("dstv_lookup_data.json", id, DsTvData[].class);
    }
    public static OwnTransfer getOwnTransferData(String id) {
        return loadTestData("own_account_data.json", id, OwnTransfer[].class);
    }

    public static DstvUpgradePaymentData getDstvUpgradePaymentData(String id) {
        return loadTestData("dstv_upgrade_payment_data.json", id, DstvUpgradePaymentData[].class);
    }








}