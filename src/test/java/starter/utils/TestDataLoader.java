package starter.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import starter.utils.model.requestModel.auth.HeaderData;
import starter.utils.model.requestModel.auth.PinStrength;
import starter.utils.model.requestModel.client.*;
import starter.utils.model.requestModel.client.budget.BudgetData;
import starter.utils.model.requestModel.client.chat.Chat;
import starter.utils.model.requestModel.client.chat.ChatMoneyRequest;
import starter.utils.model.requestModel.client.core.AccountLookup;
import starter.utils.model.requestModel.client.core_money_request.MoneyRequest;
import starter.utils.model.requestModel.client.services.GetFees;
import starter.utils.model.requestModel.client.topup.Topup;
import starter.utils.model.requestModel.client.transaction.VerifyTransaction;
import starter.utils.model.requestModel.client.users.LoginData;
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





//    public static LoginTestData getLoginTestData(String id) {
//        return loadTestData("login_test_data.json", id, LoginTestData[].class);
//    }
//
//    public static LoginTestData getLoginTestData(String id, String dataFile) {
//        return loadTestData(dataFile, id, LoginTestData[].class);
//    }
//
//    public static Login getLoginData(String id) {
//        return loadTestData("login_data.json", id, Login[].class);
//    }
//
//    public static ActionTestData getActionTestData(String id) {
//        return loadTestData("actions_data.json", id, ActionTestData[].class);
//    }
//
//    public static ActionTestData getActionTestData(String id, String dataFile) {
//        return loadTestData(dataFile, id, ActionTestData[].class);
//    }
//
//    public static UserCodeData getEnableDisableUserCode(String id) {
//        return loadTestData("enable_disable_user.json", id, UserCodeData[].class);
//    }
//
//    public static UserCodeData getChangeEmailUserCode(String id) {
//        return loadTestData("change_email_data.json", id, UserCodeData[].class);
//    }
//
//    public static LinkUnlinkTestData getLinkUnlinkTestData(String id) {
//        return loadTestData("link_unlink_account.json", id, LinkUnlinkTestData[].class);
//    }
//
//    public static MinCoreData getAccountDetailFromCore(String id) {
//        return loadTestData("core_data.json", id, MinCoreData[].class);
//    }
//
//    public static Voucher getVoucherData(String id) {
//        return loadTestData("voucher_data.json", id, Voucher[].class);
//    }
//
//    public static VoucherSession getVoucherSessionData(String id) {
//        return loadTestData("voucher_session_data.json", id, VoucherSession[].class);
//    }
//
//    public static Fayda getFaydaData(String id) {
//        return loadTestData("fayda_data.json", id, Fayda[].class);
//    }
//
//    public static CBEToWalletLookup getCBEToWalletLookupData(String id) {
//        return loadTestData("cbe_to_wallet_lookup_data.json", id, CBEToWalletLookup[].class);
//    }
//
//    public static TopUp getTopUpData(String sessionId) {
////        System.out.println("Looking for session ID: " + sessionId);
//        return loadTestData("topup_data.json", sessionId, TopUp[].class);
//    }
//
//    public static TopupData getTopupData(String id) {
//        return loadTestData("topupdata.json", id, TopupData[].class);
//    }
//
//    public static UserLoginData getUserLoginData(String id) {
//        return loadTestData("user_login_data.json", id, UserLoginData[].class);
//    }
//
//    public static CBEToWallet getCBEToWalletSessionData(String sessionId) {
//        return loadTestData("cbe_to_wallet_session_data.json", sessionId, CBEToWallet[].class);
//    }
//
//    public static CBEToWalletSelfTransaction getCBEToWalletSelfTransactionData(String wallet_id) {
//        return loadTestData("cbe_to_wallet_data.json", wallet_id, CBEToWalletSelfTransaction[].class);
//    }
//
//    public static CBEToWalletOtherTransaction getCBEToWalletOtherTransactionData(String wallet_id) {
//        return loadTestData("cbe_to_wallet_other_txn_data.json", wallet_id, CBEToWalletOtherTransaction[].class);
//    }
//
//    public static CBEToWalletAgentTxn getCBEToWalletAgentTxnData(String wallet_id) {
//        return loadTestData("cbe_to_wallet_agent_txn_data.json", wallet_id, CBEToWalletAgentTxn[].class);
//    }
//
//
//    public static ChatMoneyRequest getChatMoneyRequestData(String id) {
//        return loadTestData("chat_money_request_data.json", id, ChatMoneyRequest[].class);
//    }
//
//    public static TopUpRequest getTopUpRequestData(String id) {
//        return loadTestData("chat_topup_data.json", id, TopUpRequest[].class);
//    }
//
//    public static MoneyRequestDataWithSession getMoneyRequestDataWithSession(String id) {
//        return loadTestData("money_request_session_data.json", id, MoneyRequestDataWithSession[].class);
//    }
//
//    public static ChatMoneyRequestSession getChatMoneyRequestSessionData(String id) {
//        return loadTestData("chat_money_request_session_data.json", id, ChatMoneyRequestSession[].class);
//    }
//
//    public static MoneyRequestByBeneficiaryID getMoneyRequestByBeneficiaryID(String id) {
//        return loadTestData("money_request_by_beneficiary_data.json", id, MoneyRequestByBeneficiaryID[].class);
//    }
//
//    public static DonationTransaction getDonationTransactionData(String session_id) {
//        return loadTestData("donation_transaction_data.json", session_id, DonationTransaction[].class);
//    }
//
//    public static CBEToCBEFTDataWithSession getCBEToCBEFTDataWithSession(String session_id) {
//        return loadTestData("cbe_to_cbe_ft_session_data.json", session_id, CBEToCBEFTDataWithSession[].class);
//    }
//
//    public static CBEToOtherBanks getCBEToOtherBanksData(String session_id) {
//        return loadTestData("cbe_to_other_banks_data.json", session_id, CBEToOtherBanks[].class);
//    }
//
//    public static Equb getEqubData(String id) {
//        return loadTestData("equb_data.json", id, Equb[].class);
//    }
//
//    public static EqubMember getEqubMemberData(String id) {
//        return loadTestData("equbmembers_data.json", id, EqubMember[].class);
//    }
//
//    public static EqubPayment getEqubPaymentData(String id) {
//        return loadTestData("equb_payment_data.json", id, EqubPayment[].class);
//    }
//
//    public static EqubAmountBasedVerification getEqubAmountBasedVerificationData(String id) {
//        return loadTestData("equb_amount_based_verification_data.json", id, EqubAmountBasedVerification[].class);
//    }
//
//    public static ChatUser getChatUserData(String id) {
//        return loadTestData("chat_user_data.json", id, ChatUser[].class);
//    }
//
//    public static ChatUser getChatInitiationData(String id) {
//        return loadTestData("chat_initiation_data.json", id, ChatUser[].class);
//    }
//
//    public static BudgetCategories getBudgetCategoriesData(String id) {
//        return loadTestData("budget_categories_data.json", id, BudgetCategories[].class);
//    }
//
//    public static CBEToCBEFundTransferAccount getCBEToCBEFundTransferAccountData(String id) {
//        return loadTestData("cbe_2_cbe_fund_transfer_accounts.json", id, CBEToCBEFundTransferAccount[].class);
//    }
//
//    public static DeviceLoginTestData getLoginDeviceLoginTestData(String id) {
//        return loadTestData("auth/login_data.json", id, DeviceLoginTestData[].class);
//    }
//
//    public static AddAccount getAddAccountData(String id) {
//        return loadTestData("add_account.json", id, AddAccount[].class);
//    }
//
//    public static Beneficiary getBeneficiaryData(String id) {
//        return loadTestData("beneficiary_data.json", id, Beneficiary[].class);
//    }
//    public static UserHeader getHeaderData(String id){
//        return loadTestData("header_data.json",id,UserHeader[].class);
//    }



}