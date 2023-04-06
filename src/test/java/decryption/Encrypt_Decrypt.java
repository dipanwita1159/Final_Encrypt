package decryption;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Base64;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class Encrypt_Decrypt{

    public static WebDriver driver;

    @BeforeTest
    public void setup() {
        System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
        driver = new ChromeDriver();
        driver.get("https://opensource-demo.orangehrmlive.com/web/index.php/auth/login");
    }

    @Test
    public void loginWithCredentialsFromExcel() throws IOException, InterruptedException {
        // Open the input Excel file
        File inputFile = new File("C:\\Users\\DELL\\eclipse-workspace\\S.Grid\\Book.xlsx");
        FileInputStream fis = new FileInputStream(inputFile);

        // Create a Workbook object
        Workbook workbook = WorkbookFactory.create(fis);

        // Get the sheet with the user credentials
        Sheet sheet = workbook.getSheet("Sheet1");

        // Loop through all the rows in the sheet starting from the second row (index 1)
        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);

            // Get the user id and encrypted password from the row
            Cell userIdCell = row.getCell(0);
            String userId = userIdCell.getStringCellValue();
            Cell encryptedPasswordCell = row.getCell(1);
            String encryptedPassword = encryptedPasswordCell.getStringCellValue();

            // Encode the data
            byte[] encodedBytes = Base64.getEncoder().encode(encryptedPassword.getBytes());
            String encryptedData = new String(encodedBytes);

            // Decode the encrypted password
            byte[] decodedBytes = Base64.getDecoder().decode(encryptedPassword.getBytes());
            String decryptedPassword = new String(decodedBytes);
            
            // Print the user id, encrypted password, and decrypted password
            System.out.println("User ID: " + userId);
            System.out.println("Encrypted Password: " + encryptedPassword);
            System.out.println("Decrypted Password: " + decryptedPassword);
            
            // Enter the user id and decrypted password in the respective fields
            WebElement userIdField = driver.findElement(By.xpath("//input[@placeholder='Username']"));
            userIdField.sendKeys(userId);
            WebElement passwordField = driver.findElement(By.xpath("//input[@placeholder='Password']"));
            passwordField.sendKeys(decryptedPassword);

            // Click the login button
            WebElement loginButton = driver.findElement(By.xpath("//button[normalize-space()='Login']"));
            loginButton.click();

            // Wait for the home page to load
     
//
//            // Verify if user is logged in successfully
//            WebElement welcomeMessage = driver.findElement(By.id("welcome-message"));
//            String expectedWelcomeMessage = "Welcome " + userId + "!";
//            String actualWelcomeMessage = welcomeMessage.getText();
//            if (!actualWelcomeMessage.equals(expectedWelcomeMessage)) {
//                throw new AssertionError("Failed to login as " + userId);
//            }
//
//            // Logout from the application
//            WebElement logoutButton = driver.findElement(By.xpath("//img[@class='oxd-userdropdown-img']"));
//            logoutButton.click();
//            WebElement logoutButton2 = driver.findElement(By.xpath("//a[normalize-space()='Logout']"));
//            logoutButton2.click();

            // Wait for the logout page to load
            Thread.sleep(5000);

//            // Clear the fields for the next iteration
//            userIdField.clear();
//            passwordField.clear();
        }

        // Close the workbook and stream

    }


    @AfterTest
    public void teardown() {
        // Close the browser
        driver.quit();
    }
}
