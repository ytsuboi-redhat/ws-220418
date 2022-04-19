package com.redhat.jp.labs.sample.at;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.WebDriverRunner;
import com.codeborne.selenide.junit.TextReport;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import io.cucumber.junit.CucumberOptions.SnippetType;

import javax.sql.DataSource;
import org.dbunit.DefaultDatabaseTester;
import org.dbunit.IDatabaseTester;
import org.dbunit.IOperationListener;
import org.dbunit.database.DatabaseConfig;
import org.dbunit.database.DatabaseDataSourceConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.ext.mysql.MySqlDataTypeFactory;
import org.dbunit.ext.mysql.MySqlMetadataHandler;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.rules.TestRule;
import org.junit.runner.RunWith;
import org.slf4j.bridge.SLF4JBridgeHandler;

@RunWith(Cucumber.class)
@CucumberOptions(plugin = {"pretty", "html:target/cucumber-reports.html"}, monochrome = true,
    snippets = SnippetType.UNDERSCORE, tags = "not @Ignore")
public class AcceptanceTest {

  private static final String DB_URL_TEMPLATE = "jdbc:mysql://%s/%s";

  private static final String DB_HOST =
      System.getProperty("at.db.host", "localhost:3306");
  private static final String DB_SCHEMA =
      System.getProperty("at.db.schema", "backlog");
  private static final String DB_USER = System.getProperty("at.db.user", "backlog");
  private static final String DB_PASS = System.getProperty("at.db.pass", "password");

  public static IDatabaseTester databaseTester;

  @ClassRule
  public static TestRule report = new TextReport().onFailedTest(true).onSucceededTest(true);

  @BeforeClass
  public static void beforeClass() throws Exception {
    configureLogging();
    configureE2ETester();
    configureDatabaseTester();
  }

  private static void configureLogging() {
    // JUL -> SLF4J
    SLF4JBridgeHandler.removeHandlersForRootLogger();
    SLF4JBridgeHandler.install();
  }

  private static void configureE2ETester() {
    Configuration.reportsFolder = "target/surefire-reports";
    if (System.getProperty("selenide.browser") == null) {
      Configuration.browser = WebDriverRunner.CHROME;
    }
    Configuration.headless = false;
  }

  private static void configureDatabaseTester() throws Exception {
    IDatabaseConnection databaseConnection = new DatabaseDataSourceConnection(configureDataSource(), DB_SCHEMA);
    DatabaseConfig databaseConfig = databaseConnection.getConfig();
    databaseConfig.setProperty(DatabaseConfig.FEATURE_ALLOW_EMPTY_FIELDS, true);
    databaseConfig.setProperty(DatabaseConfig.PROPERTY_DATATYPE_FACTORY,
        new MySqlDataTypeFactory());
    databaseConfig.setProperty(DatabaseConfig.PROPERTY_METADATA_HANDLER,
        new MySqlMetadataHandler());
    databaseTester = new DefaultDatabaseTester(databaseConnection);
    databaseTester.setOperationListener(IOperationListener.NO_OP_OPERATION_LISTENER);
  }

  private static DataSource configureDataSource() {
    HikariConfig config = new HikariConfig();
    config.setJdbcUrl(String.format(DB_URL_TEMPLATE, DB_HOST, DB_SCHEMA));
    config.addDataSourceProperty("user", DB_USER);
    config.addDataSourceProperty("password", DB_PASS);
    config.setConnectionInitSql("SELECT 1");
    return new HikariDataSource(config);
  }

}
