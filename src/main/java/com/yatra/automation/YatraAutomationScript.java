package com.yatra.automation;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeoutException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class YatraAutomationScript {

	public static void main(String[] args) throws InterruptedException {

		ChromeOptions chromeOptions = new ChromeOptions();
		chromeOptions.addArguments("--disable-Notifications");

		// Launching the browser...
		WebDriver wd = new ChromeDriver(chromeOptions);
		WebDriverWait wait = new WebDriverWait(wd, Duration.ofSeconds(20)); // synchronizing the Webdriver!!

		// loading the page...
		wd.get("https://www.yatra.com");

		// maximize the browser window...
		wd.manage().window().maximize();

		// Check for PopUp!!
		closePopup(wait);

		By departureDateButtonLocator = By.xpath("//div[@aria-label=\"Departure Date inputbox\" and @role='button']");
//	    WebElement departureDateButton = wd.findElement(departureDateButtonLocator); instead of findelement, we are using waits for better synchronization

		WebElement departureDateButton = wait
				.until(ExpectedConditions.elementToBeClickable(departureDateButtonLocator));
		departureDateButton.click();

//		List<WebElement> calendarMonthWebElement = wd.findElements(calendarMonthsLocator); not synchronized!!

		WebElement currentMonthCalenderWebElement = selectTheMonthFromCalender(wait, 0);
		WebElement nextMonthCalenderWebElement = selectTheMonthFromCalender(wait, 1);

		Thread.sleep(3000);
		
		String lowestPriceForCurrentMonth = getMeLowestPrice(currentMonthCalenderWebElement);
		String lowestPriceForNextMonth = getMeLowestPrice(nextMonthCalenderWebElement);

		System.out.println(lowestPriceForCurrentMonth);
		System.out.println(lowestPriceForNextMonth);
		compareTwoMonthsPrice(lowestPriceForCurrentMonth, lowestPriceForNextMonth);
		wd.quit();
	}

	public static void closePopup(WebDriverWait wait) {
		By popUpLocator = By.xpath("//div[contains(@class,\"style_popup\")][1]");
		try {
			WebElement popUpElement = wait.until(ExpectedConditions.visibilityOfElementLocated(popUpLocator));
			WebElement crossButton = popUpElement.findElement(By.xpath(".//img[@alt=\"cross\"]"));
			crossButton.click();
		} catch (Exception e) {
			System.out.println("PopUp not shown on the screen!!!");
		}
	}

	public static String getMeLowestPrice(WebElement monthWebElement) {
		By priceLocator = By.xpath(".//span[contains(@class,\"custom-day-content\")]");
		List<WebElement> julyPriceList = monthWebElement.findElements(priceLocator);

		int lowestPrice = Integer.MAX_VALUE;
		WebElement priceElement = null;
		for (WebElement price : julyPriceList) {
//			System.out.println(price.getText());

			String priceString = price.getText();
			if (priceString.length() > 0) {

				priceString = priceString.replace("₹", "").replace(",", "");
//			System.out.println(priceString);

				// finding the lowest price
				// converting the String value into Integer
				int priceInt = Integer.parseInt(priceString);

				if (priceInt < lowestPrice) {
					lowestPrice = priceInt;
					priceElement = price;
				}

			}
		}

		WebElement dateElement = priceElement.findElement(By.xpath(".//../.."));

		String result = dateElement.getAttribute("aria-label") + "--price is Rs." + lowestPrice;
		return result;
	}

	public static WebElement selectTheMonthFromCalender(WebDriverWait wait, int index) {
		By calendarMonthsLocator = By.xpath("//div[@class=\"react-datepicker__month-container\"]");
		List<WebElement> calendarMonthsList = wait
				.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(calendarMonthsLocator));

//		System.out.println(calendarMonthsList.size());
		WebElement monthCalenderWebElement = calendarMonthsList.get(index);
		return monthCalenderWebElement;
	}

	public static void compareTwoMonthsPrice(String currentMonthPrice, String nextMonthPrice) {

		int currentMonthRsIndex = currentMonthPrice.indexOf("Rs");
		int nextMonthRsIndex = nextMonthPrice.indexOf("Rs");

		String currentPrice = currentMonthPrice.substring(currentMonthRsIndex + 3);
		String nextPrice = nextMonthPrice.substring(nextMonthRsIndex + 3);

		int current = Integer.parseInt(currentPrice);
		int next = Integer.parseInt(nextPrice);

		if (current < next) {
			System.out.println("The lowest price for two months is " + current);
		} else if (current == next) {
			System.out.println("Price is similar for both the months, Choose as preferable");
		}

		else {
			System.out.println("The lowest price for two months is Rs." + next);
		}

	}

}
