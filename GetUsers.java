package getUserList;

import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.get;

public class GetUsers {

    @BeforeClass
    public void setup()
    {
        System.out.println("I'm before class");
    }
    @Test
    public void do_reqres_get_userlist_validation()
    {
        System.out.println("I'm inside test");
        Response response = get("https://reqres.in/api/users?page=2");
        //validate the status code
        String status_code = String.valueOf(response.statusCode()); // converting response.statusCode from int to String
        Assert.assertEquals(status_code,"200");
        System.out.println(response.getBody().asString());
    }
    @Test
    public void do_booklibrary_sope_call_validation()
    {
        System.out.println("This is book library test");
    }
    @AfterClass
    public void close_connection()
    {
        System.out.println("Connection is closed");
    }


}
