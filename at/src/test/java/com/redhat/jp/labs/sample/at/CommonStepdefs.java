package com.redhat.jp.labs.sample.at;

import static com.codeborne.selenide.Selenide.$$;
import static com.codeborne.selenide.Selenide.open;

import java.util.List;
import java.util.Map;

import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import org.dbunit.IDatabaseTester;
import org.dbunit.database.DatabaseDataSet;
import org.dbunit.dataset.IDataSet;
import org.dbunit.operation.DatabaseOperation;
import org.dbunit.util.fileloader.CsvDataFileLoader;
import org.dbunit.util.fileloader.DataFileLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class CommonStepdefs {

    private static final Logger LOGGER = LoggerFactory.getLogger("CommonStepdefs");

    private IDatabaseTester dbTester = AcceptanceTest.databaseTester;

    @Given("以下の Product Backlog Item が登録されている")
    public void 以下のProductBacklogItemが登録されている(DataTable dataTable) throws Exception {
        DataFileLoader loader = new CsvDataFileLoader();
        dbTester.setDataSet(loader.load("/dbunit/ProductBacklogItem/"));
        dbTester.setSetUpOperation(DatabaseOperation.CLEAN_INSERT);
        dbTester.onSetup();
    }

    @When("Product Backlog 画面を開く")
    public void productBacklog画面を開く() {
        open("/backlog");
    }

    @Then("Product Backlog 画面にて以下の通りに Product Backlog Item が一覧で表示される")
    public void productBacklog画面にて以下の通りにProductBacklogItemが一覧で表示される(DataTable dataTable) {
        productBacklog画面を開く();

        List<Map<String, String>> dataTableRows = dataTable.asMaps();
        ElementsCollection trCollection = $$("#product-backlog tbody tr");
        trCollection.shouldHaveSize(dataTableRows.size());
        for (int i = 0; i < dataTableRows.size(); i++) {
            SelenideElement tr = trCollection.get(i);
            Map<String, String> dataTableMap = dataTableRows.get(i);

            tr.$("td.name").should(Condition.text(dataTableMap.get("name")));
            tr.$("td.story-point").should(Condition.text(dataTableMap.get("story point")));
        }
    }
}
