Feature: Validate end to end scenarios for Cucumber plus Rest Assured assignment

  @assignment001
  Scenario Outline: post call CelsiusToFahrenheit validation
    Given The Celsius value "<celsius>"
    Then user create CelsiusToFahrenheit request body
    When User sends post call with "<url>"
    Then user gets status code from CelsiusToFahrenheit site as "<status_code>"
    And User validate Fahrenheit value from response as "<fahrenheit>"

    Examples:
      |celsius|url|status_code|fahrenheit|
      |1      |https://www.w3schools.com/xml/tempconvert.asmx|200|33.8|
      |2      |https://www.w3schools.com/xml/tempconvert.asmx|200|35.6|
      |3      |https://www.w3schools.com/xml/tempconvert.asmx|200|37.4|
      |4     |https://www.w3schools.com/xml/tempconvert.asmx|200|39.2|
      |5      |https://www.w3schools.com/xml/tempconvert.asmx|200|41|
      |6     |https://www.w3schools.com/xml/tempconvert.asmx|200|42.8|
      |7     |https://www.w3schools.com/xml/tempconvert.asmx|200|44.6|
      |8     |https://www.w3schools.com/xml/tempconvert.asmx|200|46.4|
      |37     |https://www.w3schools.com/xml/tempconvert.asmx|200|98.6|
      |100    |https://www.w3schools.com/xml/tempconvert.asmx|200|212|

    @assignment002
    Scenario Outline: post call to "https://api.restful-api.dev/objects" and validate response
      Given a product with name as "<name>"
      Then user creates request body of that product
      And user sends post call with json body to "<url>"
      When user gets status code as "<status>" in response
      Then user validates that year in response is "<year>"
      Then user validates that price in response is "<price>"
      And user validate createdAt tag is present and its value is not null

      Examples:
      |name|url|status|year|price|
      |Apple MacBook Pro 16|https://api.restful-api.dev/objects|200|2019|1849.99|

  @assignment003
  Scenario Outline: get call to "https://api.restful-api.dev/objects" and validate response
    Given a user sends get call request to "<url>"
    When user gets status code as "<status>" in response
    Then user validates if id equals to "<id>" then mobile name is "<mobileName>"

    Examples:
      |url|status|id|mobileName|
      |https://api.restful-api.dev/objects|200|8|Apple Watch Series 8|
      |https://api.restful-api.dev/objects|200|11|Apple iPad Mini 5th Gen|
      |https://api.restful-api.dev/objects|200|2|Apple iPhone 12 Mini, 256GB, Blue|

  @assignment004
  Scenario Outline: get call to "https://www.xignite.com/xcurrencies.asmx?wsdl" and validate response
    Given a user sends get call request to "<url>"
    When user gets status code as "<status>" in response
    Then user capture all currency then prints
    And also capture all forwardTypes then prints
    Then validate total outcomeType count is "<count>" and "<outcomeType>" is one of them


    Examples:
      |url|status|id|mobileName|
      |https://www.xignite.com/xcurrencies.asmx?wsdl|200|4|SystemError|