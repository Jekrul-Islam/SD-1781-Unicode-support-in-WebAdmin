package org.example;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.sql.*;

import static io.restassured.RestAssured.given;

public class Unicode {
    private String jsessionID;
    private String bearerToken;
    private String unicodeText1 =
                    "Latin characters with diacritics (é, à, ö, ñ).\n" +
                    "Cyrillic characters (Ж, Я, ч, ы).\n" +
                    "Arabic characters (ع, ش, غ, ق).";

    private String unicodeText2 =
                    "Chinese characters (汉字, 中文).\n" +
                    "Japanese characters (日本語, 漢字).";
    private String unicodeText3 =
                     "Korean characters (한국어, 한자).\n" +
                    "Special symbols and emojis (©, ™, 😊).";

    @BeforeTest
    public void login() {
        RestAssured.useRelaxedHTTPSValidation();
        RestAssured.baseURI = "https://localhost:8443/api/login";

        JSONObject loginPayload = new JSONObject()
                .put("UserName", "superAdmin")
                .put("UserPassword", "campus123");

        Response response =
                given()
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

    @Test
    public void addOfferingDescription() throws SQLException {
        RestAssured.useRelaxedHTTPSValidation();
        RestAssured.baseURI = "https://localhost:8443/api/hirServlet";


        JSONObject requestBody = new JSONObject()
                .put("Module", "hir")
                .put("Service", "OfferingService")
                .put("Action", "updateDetailDescription")
                .put("Params", new JSONObject()
                        .put("OfferingID", 81675)
                        .put("DetailDescription", unicodeText1)
                );
        Response response = given()
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + bearerToken)
                .header("Cookie", "JSESSIONID=" + jsessionID)
                .body(requestBody.toString())
                .when()
                .post()
                .then()
                .extract().response();

        String valueFromDB = getDetailDescription();
//        System.out.println(unicodeText1);
//        System.out.println(valueFromDB);
        Assert.assertEquals(valueFromDB, unicodeText1, "Unicode support FAILED! Sent and received texts do not match.\n");
        System.out.println("Unicode support is VERIFIED.");

    }


    public String getDetailDescription() throws SQLException {
        String connectionUrl = "jdbc:sqlserver://192.168.68.241:1433;databaseName=dev_hir_test;instanceName=SQLEXPRESS; encrypt=true;trustServerCertificate=true;user=shahnewaz;password=Yl8Mvxy2$]3uT}5DRoBya80D!+@-)39Y";
        Connection conn = DriverManager.getConnection(connectionUrl);
        Statement statement = conn.createStatement();

        String DetailDescriptionQuery = "SELECT  DetailDescription FROM dev_hir_test.dbo.NTM_Offering WHERE OfferingID = 81675;";


        ResultSet resultSet = statement.executeQuery(DetailDescriptionQuery);

        String detailDescription = null;
        if (resultSet.next()) {
            detailDescription = resultSet.getString("DetailDescription");
        }

        resultSet.close();
        statement.close();
        conn.close();
//        é à ö ñ 漢 こんにちは به متنی (C) (R) € £ µ ¥
        return detailDescription;
    }

    @Test
    public void sectionDescription() throws SQLException {

        RestAssured.useRelaxedHTTPSValidation();
        RestAssured.baseURI = "https://localhost:8443/api/hirServlet";


        JSONObject requestBody = new JSONObject()
                .put("Module", "hir")
                .put("Service", "SectionService")
                .put("Action", "updateDescription")
                .put("Params", new JSONObject()
                        .put("SectionID", 81689)
                        .put("SectionDescription", unicodeText2)
                );

        Response response = given()
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + bearerToken)
                .header("Cookie", "JSESSIONID=" + jsessionID)
                .body(requestBody.toString())
                .when()
                .post()
                .then()
                .extract().response();

        String valueFromDB = getSectionDescription();
//        System.out.println(unicodeText2);
//        System.out.println(valueFromDB);
        Assert.assertEquals(valueFromDB, unicodeText2, "Unicode support FAILED! Sent and received texts do not match.");
        System.out.println("Unicode support is VERIFIED.");


    }

    public String getSectionDescription() throws SQLException {

        String connectionUrl = "jdbc:sqlserver://192.168.68.241:1433;databaseName=dev_hir_test;instanceName=SQLEXPRESS; encrypt=true;trustServerCertificate=true;user=shahnewaz;password=Yl8Mvxy2$]3uT}5DRoBya80D!+@-)39Y";
        Connection conn = DriverManager.getConnection(connectionUrl);
        Statement statement = conn.createStatement();

        String query = "SELECT Description FROM dev_hir_test.dbo.NTM_Section WHERE SectionID = 81689;";


        ResultSet resultSet = statement.executeQuery(query);

        String sectionDescription = null;
        if (resultSet.next()) {
            sectionDescription = resultSet.getString("Description");

        }

        resultSet.close();
        statement.close();
        conn.close();


        return sectionDescription;
    }

    @Test
    public void programDetailDescription() throws SQLException {
        RestAssured.useRelaxedHTTPSValidation();
        RestAssured.baseURI = "https://localhost:8443/api/hirServlet";


        JSONObject requestBody = new JSONObject()
                .put("Module", "hir")
                .put("Service", "programService")
                .put("Action", "updateDetailDescription")
                .put("Params", new JSONObject()
                        .put("ProgramID", 1006)
                        .put("DetailDescription", unicodeText3)
                );

        Response response = given()
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + bearerToken)
                .header("Cookie", "JSESSIONID=" + jsessionID)
                .body(requestBody.toString())
                .when()
                .post()
                .then()
                .extract().response();

        String valueFromDB = getProgramDetailDescription();
//        System.out.println(unicodeText3);
//        System.out.println(valueFromDB);
        Assert.assertEquals(valueFromDB, unicodeText3, "Unicode support FAILED! Sent and received texts do not match.");
        System.out.println("Unicode support is VERIFIED.");


    }

    public String getProgramDetailDescription() throws SQLException {

        String connectionUrl = "jdbc:sqlserver://192.168.68.241:1433;databaseName=dev_hir_test;instanceName=SQLEXPRESS; encrypt=true;trustServerCertificate=true;user=shahnewaz;password=Yl8Mvxy2$]3uT}5DRoBya80D!+@-)39Y";
        Connection conn = DriverManager.getConnection(connectionUrl);
        Statement statement = conn.createStatement();

        String query = "SELECT DetailDescription FROM dev_hir_test.dbo.NTM_Program WHERE ProgramID = 1006;";


        ResultSet resultSet = statement.executeQuery(query);

        String programDetailDescription = null;
        if (resultSet.next()) {
            programDetailDescription = resultSet.getString("DetailDescription");

        }

        resultSet.close();
        statement.close();
        conn.close();

        return programDetailDescription;
    }
}

