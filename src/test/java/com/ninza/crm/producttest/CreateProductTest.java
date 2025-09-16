package com.ninza.crm.producttest;

import java.io.IOException;
import java.time.Duration;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import com.ninza.crm.baseclass.BaseClass;
import com.ninza.crm.objectrepository.AddProductPage;
import com.ninza.crm.objectrepository.HomePage;
import com.ninza.crm.objectrepository.ProductsPage;

public class CreateProductTest extends BaseClass{
	
	@Test(groups = {"Smoke","Regression"})
	public void createProductWithMandatoryFieldTest() throws IOException, InterruptedException {
	
		int ranNum = ju.getRandomNumber();

		String productName = ex.toReadDataFromExcel("Product", 1, 0) + ranNum;
		String qty = ex.toReadDataFromExcel("Product", 1, 1);
		String price = ex.toReadDataFromExcel("Product", 1, 2);

		// Click on product link
		HomePage hp = new HomePage(driver);
		hp.getProductsLink().click();
		// click add product button
		ProductsPage pp = new ProductsPage(driver);
		pp.getaddProduct().click();

		// Create product
		AddProductPage cpp = new AddProductPage(driver);
		cpp.getProductName().sendKeys(productName);
		WebElement quantity = cpp.getQuantity();
		quantity.clear();
		quantity.sendKeys(qty);
		WebElement pricepp = cpp.getPrice();
		pricepp.clear();
		pricepp.sendKeys(price);

		wLib.select(cpp.getProdCategory(), 3);
		wLib.select(cpp.getVendorId(), 2);
		cpp.getAddProdButton().click();

		// Verify the succesfull message
		WebElement toastMsg = hp.getToastMsg();
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.visibilityOf(toastMsg));

		String msg = toastMsg.getText();

		if (msg.contains("Successfully Added")) {
			System.out.println("Product created successfully");
		} else {
			System.out.println("Product is not created");
		}
		hp.getCloseToastMsgBtn().click();
	}


}
