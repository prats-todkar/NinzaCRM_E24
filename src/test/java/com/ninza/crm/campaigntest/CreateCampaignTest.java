package com.ninza.crm.campaigntest;

import java.io.IOException;
import java.time.Duration;

import org.apache.poi.EncryptedDocumentException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.ninza.crm.baseclass.BaseClass;
import com.ninza.crm.objectrepository.CampaignsPage;
import com.ninza.crm.objectrepository.CreateCampaignPage;
import com.ninza.crm.objectrepository.HomePage;

@Listeners(com.ninza.crm.listenerutility.ListenerImplementation.class)
public class CreateCampaignTest extends BaseClass{

	@Test(groups = "Smoke")
	public void createCamapignWithMandataoryFieldsTest() throws EncryptedDocumentException, IOException {

		String campaignName = ex.toReadDataFromExcel("Campaigns", 1, 1);
		String targetSize = ex.toReadDataFromExcel("Campaigns", 1, 2);

		// Click on campaign link in home page
		HomePage hp = new HomePage(driver);
		hp.getCampaignsLink().click();

		// Click on create campaign
		CampaignsPage cp = new CampaignsPage(driver);
		cp.getCreateCampaign().click();

		// Create campaign with Mandatory fields
		CreateCampaignPage ccp = new CreateCampaignPage(driver);
		ccp.getCampaignNameTF().sendKeys(campaignName);
		ccp.getTargetSizeTF().clear();
		ccp.getTargetSizeTF().sendKeys(targetSize);
		ccp.getCreateCampaignSubmitBtn().click();

		// Verify the successfull message
		WebElement toastMsg = hp.getToastMsg();
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
		wait.until(ExpectedConditions.visibilityOf(toastMsg));
		String msg = toastMsg.getText();
		System.out.println(msg);
	    Assert.assertEquals(msg, "Campaign "+campaignName +" Successfully Added","Both the not equal" );
		hp.getCloseToastMsgBtn().click();
	}

	@Test(groups = "Regression")
	public void createCampaignWithExpectedCloseDateTest() throws EncryptedDocumentException, IOException {

		String campaignName = ex.toReadDataFromExcel("Campaigns", 1, 1);
		String targetSize = ex.toReadDataFromExcel("Campaigns", 1, 2);

		// Click on campaign link in home page
		HomePage hp = new HomePage(driver);
		hp.getCampaignsLink().click();

		// Click on create campaign
		CampaignsPage cp = new CampaignsPage(driver);
		cp.getCreateCampaign().click();

		// Create campaign with Mandatory fields
		CreateCampaignPage ccp = new CreateCampaignPage(driver);
		ccp.getCampaignNameTF().sendKeys(campaignName);
		ccp.getTargetSizeTF().clear();
		ccp.getTargetSizeTF().sendKeys(targetSize);

		// Get the date after 30 days
		String expectedDate = ju.togetRequiredDate(20);
		ccp.getExpectedCloseDateTF().sendKeys(expectedDate);
		ccp.getCreateCampaignSubmitBtn().click();

		// Verify the succesfull message
		WebElement toastMsg = hp.getToastMsg();

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
		wait.until(ExpectedConditions.visibilityOf(toastMsg));
		String msg = toastMsg.getText();
		hp.getCloseToastMsgBtn().click();
        Assert.assertTrue(msg.contains("Successfully Added"));
	
	}
	
}
