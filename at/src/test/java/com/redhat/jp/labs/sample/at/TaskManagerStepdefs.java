package com.redhat.jp.labs.sample.at;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class TaskManagerStepdefs {
    @When("Product Backlog Itemの詳細画面を開く")
    public void ProductBacklogItemの詳細画面を開く() {
        $("#item-1").click();
    }

    @When("タスクの登録ボタンをクリックする")
    public void タスクの登録ボタンをクリックする() {
        $("#registration-task").click();
    }

    @When("以下の情報を登録する")
    public void 以下の情報を登録する(DataTable dataTable) {
        List<Map<String, String>> dataTableRows = dataTable.asMaps();
        for (int i = 0; i < dataTableRows.size(); i++) {
            Map<String, String> dataTableMap = dataTableRows.get(i);
            $("#name").setValue(dataTableMap.get("name"));
            $("#description").setValue(dataTableMap.get("description"));
            $("#estimate-time").setValue(dataTableMap.get("estimate time"));
            $("#status").setValue(dataTableMap.get("status"));
            $("#memo").setValue(dataTableMap.get("memo"));
        }
    }

    @When("登録ボタンをクリックする")
    public void 登録ボタンをクリックする() {
        $("#registration-task").click();
    }

    @Then("以下の内容でタスクが登録されていること")
    public void 以下の内容でタスクが登録されていること(DataTable dataTable) {
        List<Map<String, String>> dataTableRows = dataTable.asMaps();
        ElementsCollection trCollection = $$("#tbl-task tbody tr");
        trCollection.shouldHaveSize(dataTableRows.size());
        for (int i = 0; i < dataTableRows.size(); i++) {
            SelenideElement tr = trCollection.get(i);
            Map<String, String> dataTableMap = dataTableRows.get(i);

            tr.$("#name").should(Condition.text(dataTableMap.get("name")));
            tr.$("#description").should(Condition.text(dataTableMap.get("description")));
            tr.$("#estimate-time").should(Condition.text(dataTableMap.get("estimate time")));
            tr.$("#status").should(Condition.text(dataTableMap.get("status")));
            tr.$("#memo").should(Condition.text(dataTableMap.get("memo")));
        }
    }

}
