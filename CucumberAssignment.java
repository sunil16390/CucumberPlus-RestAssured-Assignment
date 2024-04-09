package stepDef;

import io.cucumber.java.en.*;
import io.restassured.http.ContentType;
import io.restassured.path.xml.XmlPath;
import io.restassured.response.Response;
import org.junit.Assert;

import java.util.ArrayList;

import static io.restassured.RestAssured.*;
import static io.restassured.path.xml.config.XmlPathConfig.xmlPathConfig;
import static org.hamcrest.core.StringContains.containsString;


public class CucumberAssignment {
    public String celsius;
    public String request_body;
    Response response;
    public XmlPath xml_path_obj;
    String productName = "";
    String Baseurl = null;

    @Given("The Celsius value {string}")
    public void the_celsius_value(String res_celsius) {
        celsius =  res_celsius;
    }
    @Then("user create CelsiusToFahrenheit request body")
    public void user_create_celsius_to_fahrenheit_request_body() {
        request_body = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                "<soap12:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap12=\"http://www.w3.org/2003/05/soap-envelope\">\n" +
                "  <soap12:Body>\n" +
                "    <CelsiusToFahrenheit xmlns=\"https://www.w3schools.com/xml/\">\n" +
                "      <Celsius>"+celsius+"</Celsius>\n" +
                "    </CelsiusToFahrenheit>\n" +
                "  </soap12:Body>\n" +
                "</soap12:Envelope>";
    }
    @When("User sends post call with {string}")
    public void user_sends_post_call_with(String url) {
        response = given().contentType(ContentType.XML).header("Content-Type","text/xml; charset=utf-8")
                .body(request_body).when().post(url);
        System.out.println("Response body: " +response.getBody().asString());
    }
    @Then("user gets status code from CelsiusToFahrenheit site as {string}")
    public void user_gets_status_code_from_celsius_to_fahrenheit_site_as(String expactedstatus) {
        int statusCode = response.statusCode();
        Assert.assertEquals(String.valueOf(statusCode), expactedstatus);
    }
    @Then("User validate Fahrenheit value from response as {string}")
    public void user_validate_fahrenheit_value_from_response_as(String expactedFahrenheit) {
        xml_path_obj = new XmlPath(response.getBody().asString()).using(xmlPathConfig().namespaceAware(false));
        String response_Fahrenheit =xml_path_obj.getString("soap:Envelope.soap:Body.CelsiusToFahrenheitResponse.CelsiusToFahrenheitResult").trim();
        System.out.println("Celsius value in post request is: "+celsius);
        System.out.println("Fahrenheit value received in response is: "+response_Fahrenheit);
        Assert.assertEquals(String.valueOf(response_Fahrenheit),expactedFahrenheit);
    }
    //for output run this C:\Cucumber_Maven>mvn clean test -Dcucumber.filter.tags="@assignment001"

    // Assignment002
    @Given("a product with name as {string}")
    public void a_product_with_name_as(String expectedName) {
       productName =  expectedName;
    }
    @Then("user creates request body of that product")
    public void user_creates_request_body_of_that_product() {
    request_body = "{\n" +
            "    \"name\": \""+productName+"\",\n" +
            "    \"data\": {\n" +
            "        \"year\": 2019,\n" +
            "        \"price\": 1849.99,\n" +
            "        \"CPU model\": \"Intel Core i9\",\n" +
            "        \"Hard disk size\": \"1 TB\"\n" +
            "    }\n" +
            "}";
    }
    @Then("user sends post call with json body to {string}")
    public void user_sends_post_call_with_json_body_to(String url) {
        response = given().contentType(ContentType.JSON).body(request_body).when().post(url);
        System.out.println("Response body: " +response.getBody().asString());
    }
    @When("user gets status code as {string} in response")
    public void user_gets_status_code_as_in_response(String expactedStatus) {
        int statusCode = response.statusCode();
        Assert.assertEquals(String.valueOf(statusCode), expactedStatus);
    }
    @Then("user validates that year in response is {string}")
    public void user_validates_that_year_in_response_is(String expectedYear) {
        String yearInResponse = response.getBody().jsonPath().getString("data.year");
        Assert.assertEquals(yearInResponse,expectedYear);
        System.out.println("Year In Response: " +yearInResponse);
    }
    @Then("user validates that price in response is {string}")
    public void user_validates_that_price_in_response_is(String expectedPrice) {
        String priceInResponse = response.getBody().jsonPath().getString("data.price");
        Assert.assertEquals(priceInResponse,expectedPrice);
        System.out.println("Price in Response:" +priceInResponse);
    }
    @Then("user validate createdAt tag is present and its value is not null")
    public void user_validate_created_at_tag_is_present_and_its_value_is_not_null() {
        if(response.body().asString().contains("createdAt")) {
            String createAt = response.getBody().jsonPath().getString("createdAt");
            Assert.assertNotNull(createAt);
        }
    }
    //for output run this C:\Cucumber_Maven>mvn clean test -Dcucumber.filter.tags="@assignment002"

    // Assignment003
    @Given("a user sends get call request to {string}")
    public void a_user_sends_get_call_request_to(String url) {
        Baseurl = url;
        response = get(Baseurl);
    }
    @Then("user validates if id equals to {string} then mobile name is {string}")
    public void user_validates_if_id_equals_to_then_mobile_name_is(String expected_id, String expectedMobileName) {
        int id_path_count = response.getBody().jsonPath().getList("id").size();

        for(int i =0; i <=id_path_count; i++)
        {
            String idOf_i =response.getBody().jsonPath().getString("id["+i+"]");
            if(idOf_i.equals(expected_id))
            {
               // Assert.assertEquals(idOf_i,expected_id);
                String nameOf_i = response.getBody().jsonPath().getString("name["+i+"]");
                Assert.assertEquals(nameOf_i,expectedMobileName);
                System.out.println("The expected id is " +idOf_i+ " and expected name is: "+nameOf_i);
                break;
            }
        }
    }

    //for output run this C:\Cucumber_Maven>mvn clean test -Dcucumber.filter.tags="@assignment003"

    // Assignment004

    @Then("user capture all currency then prints")
    public void user_capture_all_currency_then_prints() {
       ArrayList<String> currenciesNodes = RestAssured.given().when().contentType("application/xml")
                .get(Baseurl).then().extract().path("definitions.types.schema.simpleType.find{it.@name=='Currencies'}.restriction.enumeration.@value");

        for (String currency : currenciesNodes) {
            System.out.println(currency + "\n");
        }
    }
    @Then("also capture all forwardTypes then prints")
    public void also_capture_all_forward_types_then_prints() {
        ArrayList<String> forwardTypesNodes = RestAssured.given().when().contentType("application/xml")
                .get(Baseurl).then().extract().path("definitions.types.schema.simpleType.find{it.@name=='ForwardTypes'}.restriction.enumeration.@value");

        for (String forwardType : forwardTypesNodes) {
            System.out.println(forwardType + "\n");
        }
    }
    @Then("validate total outcomeType count is {string} and {string} is one of them")
    public void validate_total_outcome_type_count_is_and_is_one_of_them(String expectedSizeOfOutcomeType, String expectedOutcomeType) {
        ArrayList<String> outcomeTypesNodes = RestAssured.given().when().contentType("application/xml")
                .get(Baseurl).then().extract().path("definitions.types.schema.simpleType.find{it.@name=='OutcomeTypes'}.restriction.enumeration.@value");

        Assert.assertEquals(String.valueOf(outcomeTypesNodes.size()), expectedSizeOfOutcomeType);

        for (String outcomeTypes : outcomeTypesNodes) {
            if(outcomeTypes.equals(expectedOutcomeType))
            {
                System.out.println(outcomeTypes + "is one of them");
                Assert.assertEquals(outcomeTypes,expectedOutcomeType);
            }
        }
    }
    //for output run this C:\Cucumber_Maven>mvn clean test -Dcucumber.filter.tags="@assignment004"
}

