import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class Assignments3 {
	
	public static void main(String[] args) {
		
		// Set the path to your ChromeDriver executable
        System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+ "\\drivers\\chromedriver.exe");

        // Initialize ChromeDriver & Launch the browser
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();        
        
        driver.get("https://money.rediff.com/losers/bse/daily/groupall");
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(7));
        
        
        // Get the data from DataTable via Xpath
        List<WebElement> StockNameList = driver.findElements(By.xpath("//table[@class='dataTable']//tr/td[1]"));
        List<WebElement> StockCurrentPriceList = driver.findElements(By.xpath("//table[@class='dataTable']//tr/td[4]"));
        
        
        // Declare variables
        int size = StockNameList.size();
        Map<String,Double> StockListMapping1 = new HashMap<String,Double>();
        Map<String,Double> StockListMapping2 = new HashMap<String,Double>();
       
        // Create a new workbook & Create a new sheet
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Data Sheet");
        int rowNum=0;
        
        
        for(WebElement StockName : StockNameList) {
        	
        	// Create the new Row
        	Row row = sheet.createRow(rowNum);
        	
        	// Create the columns
        	Cell cell = row.createCell(0);
            cell.setCellValue(StockName.getText());
            
            Cell cell2 = row.createCell(1);
            cell2.setCellValue(StockCurrentPriceList.get(rowNum).getText().replace(",",""));
        	
        	
            // Add the data to HashMap1
            System.out.println( rowNum + " : " + StockNameList.get(rowNum).getText()+ " : " + StockCurrentPriceList.get(rowNum).getText().replace(",","") );
        	StockListMapping1.put(StockNameList.get(rowNum).getText(), Double.parseDouble(StockCurrentPriceList.get(rowNum).getText().replace(",","")));
        	
        	rowNum++;
        }
        
        // write the data to Excel File
        
        try (FileOutputStream fos = new FileOutputStream( System.getProperty("user.dir") + "\\input\\ExcelData.xlsx")) {
            workbook.write(fos);
            System.out.println("Excel file written successfully.");
        } catch (IOException e) {
            System.err.println("Error writing Excel file: " + e.getMessage());
        } finally {
            try {
                workbook.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        
        // Read the data from excel file and add the data to HashMap2
        
        try {FileInputStream fis = new FileInputStream(System.getProperty("user.dir") + "\\input\\ExcelData.xlsx");
                Workbook workbook2 = new XSSFWorkbook(fis); {

               // Get the first sheet of the workbook
               Sheet sheet2 = workbook.getSheetAt(0);

               // Iterate through each row in the sheet
               for (Row row : sheet2) {
                   // Get cell values from each row & Add it to Hashmap2
                   String companyName = row.getCell(0).getStringCellValue();
                   String stockPrice =  row.getCell(1).getStringCellValue();
                   StockListMapping2.put(companyName, Double.parseDouble(stockPrice));

                   // Print the data
                   System.out.println( row.getRowNum() + " : " + companyName + " : " + stockPrice);
                   
               }
           }} catch (IOException e) {
               System.err.println("Error reading Excel file: " + e.getMessage());
           } 
        
        
        // Compare the 2 Hashmaps
        
        if(StockListMapping1.equals(StockListMapping2)) {
        	
        	System.out.println("Both the Hashmaps are equal");
        	
        }else {
        	
        	System.out.println("Both the Hashmaps are not equal");
        	
        }
        
        
        
        
        
        driver.quit();
        
        
        
		
	}

}
