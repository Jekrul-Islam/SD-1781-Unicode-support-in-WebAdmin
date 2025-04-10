package org.example;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.sql.*;
import java.util.LinkedHashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class UnicodeSupport {
    private String jsessionID;
    private String bearerToken;
    private String conn_url = ReadDataFromConfig.get("dbUrl");

    private String testUnicodeInDB = "é à ö ñ 漢字 こんにちは 안녕하세요 به متنی (C) (R) € £ µ ¥ ©, ™, 😊";


    private String unicodeText = "Arabic characters (ع, ش, غ, ق). Cyrillic characters (Ж, Я, ч, ы). Latin characters with diacritics (é, à, ö, ñ). Chinese characters (汉字, 中文). Japanese characters (日本語, 漢字). Korean characters (한국어, 한자). Special symbols and emojis (©, ™, 😊)";

    @BeforeTest
    public void login() {
        RestAssured.useRelaxedHTTPSValidation();
        RestAssured.baseURI = ReadDataFromConfig.get("baseUrl") + ReadDataFromConfig.get("loginEndpoint");

        JSONObject loginPayload = new JSONObject()
                .put("UserName", ReadDataFromConfig.get("username"))
                .put("UserPassword", ReadDataFromConfig.get("password"));

        Response response = given()
                .header("Content-Type", "application/json")
                .body(loginPayload.toString())
                .when()
                .post()
                .then()
                .extract()
                .response();

        jsessionID = response.getDetailedCookie("JSESSIONID").getValue();
        bearerToken = response.jsonPath().getString("data.token");
    }

    @Test (priority = 1)
    public void testOfferingDetailDescription() throws SQLException {
        String updateQuery = "UPDATE dev_hir_test.dbo.NTM_Offering SET DetailDescription = N'" + testUnicodeInDB + "' WHERE OfferingID = 81675;";
        try (Connection conn = DriverManager.getConnection(conn_url);
             Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(updateQuery);
        }

        String dbValue = null;
        try (Connection conn = DriverManager.getConnection(conn_url);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT DetailDescription FROM dev_hir_test.dbo.NTM_Offering WHERE OfferingID = 81675;")) {

            if (rs.next()) {
                dbValue = rs.getString("DetailDescription");
            }
        }
        if (testUnicodeInDB.equals(dbValue)) {
            System.out.println("✅ DB supports Unicode for Offering DetailDescription — proceeding with API request..............");
            Map<String, Object> params = new LinkedHashMap<>();
            params.put("OfferingID", 81675);
            params.put("DetailDescription", unicodeText);


            String query = "SELECT DetailDescription FROM dev_hir_test.dbo.NTM_Offering WHERE OfferingID = 81675;";
            runUnicodeTest("OfferingService", "updateDetailDescription", params, unicodeText, query, "DetailDescription");
        }
        else {
            System.out.println("❌ DB does not support Unicode for Offering DetailDescription — skipping API request.");
        }
    }

    @Test (priority = 2)
    public void testSectionDescription() throws SQLException {
        String updateQuery = "UPDATE dev_hir_test.dbo.NTM_Section SET Description = N'" + testUnicodeInDB + "' WHERE SectionID = 81689;";
        try (Connection conn = DriverManager.getConnection(conn_url);
             Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(updateQuery);
        }

        String dbValue = null;
        try (Connection conn = DriverManager.getConnection(conn_url);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT Description FROM dev_hir_test.dbo.NTM_Section WHERE SectionID = 81689;")) {

            if (rs.next()) {
                dbValue = rs.getString("Description");
            }
        }
        if (testUnicodeInDB.equals(dbValue)) {
            System.out.println("✅ DB supports Unicode for Section Description — proceeding with API request..............");
        Map<String, Object> params = new LinkedHashMap<>();
        params.put("SectionID", 81689);
        params.put("SectionDescription", unicodeText);

        String query = "SELECT Description FROM dev_hir_test.dbo.NTM_Section WHERE SectionID = 81689;";
        runUnicodeTest("SectionService", "updateDescription", params, unicodeText, query, "Description");
        }
        else {
            System.out.println("❌ DB does not support Unicode for Section Description — skipping API request.");
        }
    }

    @Test(priority = 3)
    public void testProgramDetailDescription() throws SQLException {
        String updateQuery = "UPDATE dev_hir_test.dbo.NTM_Program SET DetailDescription = N'" + testUnicodeInDB + "' WHERE ProgramID = 1006;";
        try (Connection conn = DriverManager.getConnection(conn_url);
             Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(updateQuery);
        }

        String dbValue = null;
        try (Connection conn = DriverManager.getConnection(conn_url);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT DetailDescription FROM dev_hir_test.dbo.NTM_Program WHERE ProgramID = 1006;")) {

            if (rs.next()) {
                dbValue = rs.getString("DetailDescription");
            }
        }
        if (testUnicodeInDB.equals(dbValue)) {
            System.out.println("✅ DB supports Unicode for Program DetailDescription — proceeding with API request..............");
            Map<String, Object> params = new LinkedHashMap<>();
            params.put("ProgramID", 1006);
            params.put("DetailDescription", unicodeText);

            String query = "SELECT DetailDescription FROM dev_hir_test.dbo.NTM_Program WHERE ProgramID = 1006;";
            runUnicodeTest("programService", "updateDetailDescription", params, unicodeText, query, "DetailDescription");
        }
        else {
            System.out.println("❌ DB does not support Unicode for Program DetailDescription — skipping API request.");
        }
    }

    @Test (priority = 4)
    public void testCatalogDescription () throws SQLException {
        String updateQuery = "UPDATE dev_hir_test.dbo.NTM_Catalog SET Description = N'" + testUnicodeInDB + "' WHERE CatalogID = 5;";
        try (Connection conn = DriverManager.getConnection(conn_url);
             Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(updateQuery);
        }

        String dbValue = null;
        try (Connection conn = DriverManager.getConnection(conn_url);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT Description FROM dev_hir_test.dbo.NTM_Catalog WHERE CatalogID = 5;")) {

            if (rs.next()) {
                dbValue = rs.getString("Description");
            }
        }

        if (testUnicodeInDB.equals(dbValue)) {
            System.out.println("✅ DB supports Unicode for the Catalog Description — proceeding with API request........");
        Map<String, Object> params = new LinkedHashMap<>();
        params.put("Description",unicodeText);
        params.put("SortTypeName", "Default");
        params.put("IsActive", false);
        params.put("CatalogID", 5);
        params.put("EndDate", "2012-02-27T23:59:59+06:00");
        params.put("Name", "Health Professions");
        params.put("StartDate", "2008-08-01T00:00:00+06:00");

        String query = "SELECT Description FROM dev_hir_test.dbo.NTM_Catalog WHERE CatalogID = 5;";
        runUnicodeTest("CatalogService", "saveCatalog", params, unicodeText, query, "Description");
        } else{
            System.out.println("❌ DB does not support Unicode for the Catalog Description — skipping API request.");
        }
    }

    private void runUnicodeTest(String service, String action, Map<String, Object> params, String expectedText, String query, String columnName) throws SQLException {
        RestAssured.useRelaxedHTTPSValidation();
        RestAssured.baseURI = ReadDataFromConfig.get("baseUrl") + ReadDataFromConfig.get("hirEndpoint");

        JSONObject body = new JSONObject()
                .put("Module", "hir")
                .put("Service", service)
                .put("Action", action)
                .put("Params", new JSONObject(params));

        Response response = given()
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + bearerToken)
                .header("Cookie", "JSESSIONID=" + jsessionID)
                .body(body.toString())
                .when()
                .post()
                .then()
                .extract().response();

        String dbValue = fetchDescriptionFromDB(query, columnName);
        Assert.assertEquals(dbValue, expectedText, "Unicode support FAILED! Sent and received text does not match.");
        System.out.println("Unicode support is VERIFIED.");
    }

    private String fetchDescriptionFromDB(String query, String columnName) throws SQLException {

        try (Connection conn = DriverManager.getConnection(conn_url);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            if (rs.next()) {
                return rs.getString(columnName);
            }
        }
        return null;
    }

}
