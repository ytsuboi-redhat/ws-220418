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
    @Then("{int} 個の Product Backlog Item が一覧で表示される")
    public void n個のProductBacklogItemが一覧で表示される(int count) {
        $$("#product-backlog tbody tr").shouldHaveSize(count);
    }

    @Then("以下の name の順序通りに Product Backlog Item が表示される")
    public void 以下のnameの順序通りにProductBacklogItemが表示される(DataTable dataTable) {
        List<Map<String, String>> dataTableMaps = dataTable.asMaps();
        ElementsCollection trCollection = $$("#product-backlog tbody tr");
        trCollection.shouldHaveSize(dataTableMaps.size());
        for (int i = 0; i < dataTableMaps.size(); i++) {
            trCollection.get(i).$("td.name")
                    .should(Condition.text(dataTableMaps.get(i).get("name")));
        }
    }

    @And("Product Backlog Item の id が自動的に重複無く採番され表示されていること")
    public void productBacklogItemのIdが自動的に重複無く採番され表示されていること() {
        Set<String> itemId = new HashSet<>();
        for (SelenideElement itemIdElement : $$("#product-backlog tbody tr td.itemId")) {
            itemIdElement.shouldNot(Condition.empty);
            Assert.assertTrue(itemId.add(itemIdElement.text()));
        }
    }
}
