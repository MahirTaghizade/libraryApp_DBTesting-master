package com.cydeo.steps;

import com.cydeo.pages.DashBoardPage;
import com.cydeo.pages.LoginPage;
import com.cydeo.utility.BrowserUtil;
import com.cydeo.utility.DB_Util;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;

public class DashboardStepDefs
{
    String actualUserNumbers;
    String actualBookNumbers;
    String actualBorrowedBookNumbers;
    LoginPage loginPage=new LoginPage();
    DashBoardPage dashBoardPage=new DashBoardPage();


    @Given("the user logged in as {string}")
    public void the_user_logged_in_as(String user) {
        loginPage.login(user);
         BrowserUtil.waitFor(4);
    }
    @When("user gets all information from modules")
    public void user_gets_all_information_from_modules() {

        actualUserNumbers = dashBoardPage.usersNumber.getText();
        System.out.println("actualUserNumbers = " + actualUserNumbers);
        actualBookNumbers = dashBoardPage.booksNumber.getText();
        System.out.println("actualBookNumbers = " + actualBookNumbers);
        actualBorrowedBookNumbers = dashBoardPage.borrowedBooksNumber.getText();
        System.out.println("actualBorrowedBookNumbers = " + actualBorrowedBookNumbers);

    }

    @Then("the informations should be same with database")
    public void the_informations_should_be_same_with_database() {

        // make a connection
        //DB_Util.createConnection();- we added before after custom hooks
        //run the query
        DB_Util.runQuery("select count(*) from users");
        // store data
        String expectedUsers = DB_Util.getFirstRowFirstColumn();
        //make a assertion
        Assert.assertEquals(expectedUsers,actualUserNumbers);

        // books
        DB_Util.runQuery("select count(*) from books");
        // store data
        String expectedBooks = DB_Util.getCellValue(1,1);
        //make a assertion
        Assert.assertEquals(expectedBooks,actualBookNumbers);

        //BorrowedBook
        DB_Util.runQuery("select count(*) from book_borrow where is_returned=0");
        // store data
        String BorrowedBookNumbers = DB_Util.getFirstRowFirstColumn();
        //make a assertion
        Assert.assertEquals(BorrowedBookNumbers,actualBorrowedBookNumbers);


        // close the connection
       // DB_Util.destroy();--we added before after custom hooks
    }


}
