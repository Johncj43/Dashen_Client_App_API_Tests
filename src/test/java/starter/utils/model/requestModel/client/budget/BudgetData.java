package starter.utils.model.requestModel.client.budget;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;

@Data
public class BudgetData {
    private String id;
    @JsonAlias({"budget_type", "budgetType"})

    private String budget_type;

    private int amount;
    @JsonAlias({"custom_start_date", "customStartDate"})

    private String custom_start_date;
    @JsonAlias({"custom_end_date", "customEndDate"})


    private String custom_end_date;
    private String installationdate;
    private String deviceuuid;
    private String budgetID;
    private String name;
    private String icon;
    private String color;
    private int expense_limit;
    private String appversion;
    private String platform;
    private String sourceapp;
    private String pincode;
    private String expense_type;

}
