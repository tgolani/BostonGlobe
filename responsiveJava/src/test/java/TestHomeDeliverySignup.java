package test.java;

import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import com.perfecto.reportium.client.ReportiumClient;
import com.perfecto.reportium.client.ReportiumClientFactory;
import com.perfecto.reportium.exception.ReportiumException;
import com.perfecto.reportium.model.PerfectoExecutionContext;
import com.perfecto.reportium.model.Project;
import com.perfecto.reportium.test.TestContext;
import com.perfecto.reportium.test.result.TestResultFactory;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeClass;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import ru.yandex.qatools.allure.annotations.Attachment;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class TestHomeDeliverySignup {

	public RemoteWebDriver driver;
	public boolean device;
	public boolean fast;
	ReportiumClient reportiumClient;
	String OS;
	int retry = 1; //number of times to retry
	int retryInterval = 5000; //retry in MS

	public RemoteWebDriver createDriver(String targetEnvironment) throws MalformedURLException {
		DesiredCapabilities capabilities = new DesiredCapabilities();

		//capabilities.setCapability("openDeviceTimeout", 5);

		switch (targetEnvironment) {
		case "Galaxy S7":
			device = true;
			fast = false;
			capabilities.setCapability("platformName", "Android");
			capabilities.setCapability("deviceName", "E0CF1F7B");
			capabilities.setCapability("browserName", "mobileChrome");
			break;

		case "iPhone 6":
			device = true;
			fast = false;
			capabilities.setCapability("platformName", "iOS");
			capabilities.setCapability("deviceName", "6D0250B1CECE7730CE322AD9CD7209665AB909BB");
			capabilities.setCapability("browserName", "mobileSafari");
			break;
		
		case "Chrome 56 Fast":
			device = false;
			fast = true;
			capabilities.setCapability("platformName", "Windows");
			capabilities.setCapability("platformVersion", "10");
			capabilities.setCapability("browserName", "Chrome");
			capabilities.setCapability("browserVersion", "56");
			capabilities.setCapability("resolution", "1280x1024");
			capabilities.setCapability("location", "US East");
			capabilities.setCapability("deviceType", "WEB");
			break;

		case "Internet Explorer 11":
			device = false;
			fast = false;
			capabilities.setCapability("platformName", "Windows");
			capabilities.setCapability("platformVersion", "8.1");
			capabilities.setCapability("browserName", "Internet Explorer");
			capabilities.setCapability("browserVersion", "11");
			capabilities.setCapability("resolution", "1366x768");
			capabilities.setCapability("location", "US East");
			capabilities.setCapability("deviceType", "WEB");
			break;

		case "Firefox 43":
			device = false;
			fast = false;
			capabilities.setCapability("platformName", "Windows");
			capabilities.setCapability("platformVersion", "8.1");
			capabilities.setCapability("browserName", "Firefox");
			capabilities.setCapability("browserVersion", "43");
			capabilities.setCapability("resolution", "1366x768");
			capabilities.setCapability("location", "US East");
			capabilities.setCapability("deviceType", "WEB");
			break;

		case "Firefox 49":
			device = false;
			fast = true;
			capabilities.setCapability("platformName", "Windows");
			capabilities.setCapability("platformVersion", "7");
			capabilities.setCapability("browserName", "Firefox");
			capabilities.setCapability("browserVersion", "49");
			capabilities.setCapability("resolution", "1280x1024");
			capabilities.setCapability("location", "US East");
			capabilities.setCapability("deviceType", "WEB");
			break;

		case "Chrome 48":
			device = false;
			fast = false;
			capabilities.setCapability("platformName", "Windows");
			capabilities.setCapability("platformVersion", "7");
			capabilities.setCapability("browserName", "Chrome");
			capabilities.setCapability("browserVersion", "48");
			capabilities.setCapability("resolution", "1366x768");
			capabilities.setCapability("location", "US East");
			capabilities.setCapability("deviceType", "WEB");
			break;
		
		case "Chrome Beta":
			device = false;
			fast = true;
			capabilities.setCapability("platformName", "Windows");
			capabilities.setCapability("platformVersion", "7");
			capabilities.setCapability("browserName", "Chrome");
			capabilities.setCapability("browserVersion", "beta");
			capabilities.setCapability("resolution", "1280x1024");
			capabilities.setCapability("location", "US East");
			capabilities.setCapability("deviceType", "WEB");
			break;
		case "Safari 10 Sierra":
			device = false;
			fast = false;
			capabilities.setCapability("platformName", "Mac");
			capabilities.setCapability("platformVersion", "macOS Sierra");
			capabilities.setCapability("browserName", "Safari");
			capabilities.setCapability("browserVersion", "10");
			capabilities.setCapability("resolution", "1440x900");
			capabilities.setCapability("location", "NA-US-BOS");
			break;	
			
		case "Safari 9 Yosemite":
			device = false;
			fast = false;
			capabilities.setCapability("platformName", "Mac");
			capabilities.setCapability("platformVersion", "OS X Yosemite");
			capabilities.setCapability("browserName", "Safari");
			capabilities.setCapability("browserVersion", "9");
			capabilities.setCapability("resolution", "1440x900");
			capabilities.setCapability("location", "NA-US-BOS");
			break;
		
		case "Safari 8 Yosemite":
			device = false;
			fast = false;
			capabilities.setCapability("platformName", "Mac");
			capabilities.setCapability("platformVersion", "OS X Yosemite");
			capabilities.setCapability("browserName", "Safari");
			capabilities.setCapability("browserVersion", "8");
			capabilities.setCapability("resolution", "1440x900");
			capabilities.setCapability("location", "NA-US-BOS");
			break;
		
		case "Safari 9 Capitan":
			device = false;
			fast = false;
			capabilities.setCapability("platformName", "Mac");
			capabilities.setCapability("platformVersion", "OS X El Capitan");
			capabilities.setCapability("browserName", "Safari");
			capabilities.setCapability("browserVersion", "9");
			capabilities.setCapability("resolution", "1440x900");
			capabilities.setCapability("location", "NA-US-BOS");
			break;
		
		case "Firefox Beta":
			device = false;
			fast = true;
			capabilities.setCapability("platformName", "Windows");
			capabilities.setCapability("platformVersion", "10");
			capabilities.setCapability("browserName", "Firefox");
			capabilities.setCapability("browserVersion", "beta");
			capabilities.setCapability("resolution", "1280x1024");
			capabilities.setCapability("location", "US East");
			break;
			
		}

		capabilities.setCapability("user", System.getProperty("PerfectoUsername"));
		capabilities.setCapability("password", System.getProperty("PerfectoPassword"));		
		if(fast) { 
			//capabilities.setCapability("offline-token", System.getProperty("PerfectoToken"));
			capabilities.setCapability("securityToken", System.getProperty("PerfectoToken"));
			//capabilities.setCapability("securityToken", "eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJzbFV4OFFBdjdVellIajd4YWstR0tTbE43UjFNSllDbC1TRVJiTlU1RFlFIn0.eyJqdGkiOiJlNWQ4MTI2Ny1mOTZhLTQ3NWUtYTNiYi04MDY0NmIwNGY5MmUiLCJleHAiOjAsIm5iZiI6MCwiaWF0IjoxNTMzODQ2OTc3LCJpc3MiOiJodHRwczovL2F1dGgucGVyZmVjdG9tb2JpbGUuY29tL2F1dGgvcmVhbG1zL2RlbW8tcGVyZmVjdG9tb2JpbGUtY29tIiwiYXVkIjoib2ZmbGluZS10b2tlbi1nZW5lcmF0b3IiLCJzdWIiOiI2NDAwZjJjNS1hMTE1LTQ5ZDQtOGNlMy0yOGNjMDZiNzVkN2EiLCJ0eXAiOiJPZmZsaW5lIiwiYXpwIjoib2ZmbGluZS10b2tlbi1nZW5lcmF0b3IiLCJub25jZSI6ImVkYjZlNzE3LWNhNjMtNDU4OS1iOWUzLWE4YTgyMTk1OGI2NyIsImF1dGhfdGltZSI6MCwic2Vzc2lvbl9zdGF0ZSI6ImQ0NzZjNTA2LTllNzYtNDQyMi1iMjE5LTQ0NTQzNDIzNDE4ZCIsInJlYWxtX2FjY2VzcyI6eyJyb2xlcyI6WyJvZmZsaW5lX2FjY2VzcyIsInVtYV9hdXRob3JpemF0aW9uIl19LCJyZXNvdXJjZV9hY2Nlc3MiOnsiYWNjb3VudCI6eyJyb2xlcyI6WyJtYW5hZ2UtYWNjb3VudCIsIm1hbmFnZS1hY2NvdW50LWxpbmtzIiwidmlldy1wcm9maWxlIl19fX0.kFjPkNfbiot592lK5E43S_VqIyDmP-hKnKVbJAI6QSGoo8DgLVcG-9MUlokrdo90V6Zdqt60Lsa_h9fa97W9LgYFpSy7frcu3LKAl1otunfZz0YXSn1hxn-_jm71kaoMIlToTjeAULACCi7-nsoptYvb4l5i0Kf-jg4N3EVC5ZwyeBNpm9LsKMfOlxcp8Pz_b_hnNzb3sWijtphUthZfa-wwhR4tdZ0OLXG1gbBDk3d5Y52qk8qEnjobj5vI6kOwsSwUNkocP8A2Gy9J9ncQogitUcCZG9jh-eEUXs4tlYZn-DKetrTQpX5ycv-1DFI5gKV6gcibO-dn53d1VIMGyw");

		}
		capabilities.setCapability("newCommandTimeout", "30");
		if (device) { capabilities.setCapability("windTunnelPersona", "Georgia"); }
		//	if (device) { capabilities.setCapability("windTunnelPersona", "Ross"); }
		
		capabilities.setCapability("scriptName", "Boston Globe - " + targetEnvironment);
		
		long startTime; 
		while(retry > 0 && driver == null) {
			startTime = System.nanoTime();
			try {
				System.out.println("Trying to aquire session: " + targetEnvironment);
				if(fast) {
					
					driver = new RemoteWebDriver(new URL("https://demo.perfectomobile.com/nexperience/perfectomobile/wd/hub/fast"),
						capabilities);
					System.out.println(targetEnvironment + ": " + (System.nanoTime() - startTime) / 1000000);
				} else {
					driver = new RemoteWebDriver(new URL("https://demo.perfectomobile.com/nexperience/perfectomobile/wd/hub"),
						capabilities);
					System.out.println(targetEnvironment + ": " + (System.nanoTime() - startTime) / 1000000);
				}
				
			} catch (Exception e) {
				retry--;
				e.printStackTrace();
				System.out.println("Failed to aquire browser session: " + targetEnvironment + ". Retrying...");
				sleep(retryInterval);
			}		
		}
		
		OS = capabilities.getCapability("platformName").toString();
		driver.manage().timeouts().implicitlyWait(40, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		if(device) {startLogging();}
	

		
		
		
		return driver;
	}

	@Attachment
	public byte[] takeScreenshot() {
		//System.out.println("Taking screenshot");
		return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
	}

	@Test
	public void BostonGlobeTest() {
		openHomepage();
		enterZipCode();
		selectLength();
		enterDetails();
	}

	public void openHomepage() {
		reportiumClient.testStep("Open Homepage");
		//System.out.println("### Opening homepage ###");
		driver.get(
				"http://subscribe.bostonglobe.com/B0004/?rc=WW011964&globe_rc=WW011964&p1=BGHeader_HomeDeliverySubscription");
		//wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@name='txtZip']")));
		takeScreenshot();



	}

	public void enterZipCode() {
		reportiumClient.testStep("Enter Zip Code");
		//System.out.println("### Entering zipcode ###");
		driver.findElement(By.xpath("//input[@name='txtZip']")).clear();
		driver.findElement(By.xpath("//input[@name='txtZip']")).sendKeys("02116");
		driver.findElement(By.xpath("//input[@id='cmdSubmit']")).click();
		takeScreenshot();
	}

	public void selectLength() {
		reportiumClient.testStep("Select Subscription Length");
		//System.out.println("### Selecting subscription length ###");
		//wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//label[1]/strong[1])[1]")));

		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("$('input:radio[name=rdSubscription][value=4]').trigger('click');");
		driver.findElement(By.xpath("//input[@id='continue_btn']")).click();
		takeScreenshot();

		
		
		
	}

	public void enterDetails() {
		reportiumClient.testStep("Enter Subscription Details");
		sleep(1000);
		//System.out.println("### Entering subscription details ###");
		//wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='txtDeliveryFirstName']")));
		driver.findElement(By.xpath("//input[@id='txtDeliveryFirstName']")).sendKeys("Tomer");
		driver.findElement(By.xpath("//input[@id='txtDeliveryLastName']")).sendKeys("Golani");
		driver.findElement(By.xpath("//input[@id='txtDeliveryAddress1']")).sendKeys("28 Main St");
		driver.findElement(By.xpath("//input[@id='txtDeliveryAddress2']")).sendKeys("Apt. 2");
		driver.findElement(By.xpath("//input[@id='txtDeliveryAreaCode']")).sendKeys("781");
		driver.findElement(By.xpath("//input[@id='txtDeliveryPhone3']")).sendKeys("847");
		driver.findElement(By.xpath("//input[@id='txtDeliveryPhone4']")).sendKeys("4433");
		driver.findElement(By.xpath("//input[@id='txtDeliveryEMail']")).sendKeys("tomerg@perfectomobile.com");
		takeScreenshot();
		
	}

	@BeforeClass(alwaysRun = true)
	public void baseBeforeClass(ITestContext context) throws MalformedURLException {
		Map<String, String> params = context.getCurrentXmlTest().getAllParameters();

		driver = createDriver(params.get("targetEnvironment"));
		reportiumClient = getReportiumClient(driver);
	}

	@AfterClass(alwaysRun = true)
	public void baseAfterClass() {
		try{ System.out.println("Report url = " + reportiumClient.getReportUrl()); }
		catch (Exception e) {}
		
		if (driver != null) {
			//if(device){endLogging();}
			driver.quit();
		}
	}

	@BeforeMethod(alwaysRun = true)
	public void beforeTest(Method method) {
		String testName = method.getDeclaringClass().getSimpleName() + "." + method.getName();
		reportiumClient.testStart(testName, new TestContext());
	}

	@AfterMethod(alwaysRun = true)
	public void afterTest(ITestResult testResult) {
		int status = testResult.getStatus();
		
		switch (status) {
		case ITestResult.FAILURE:
			reportiumClient.testStop(TestResultFactory.createFailure("An error occurred", testResult.getThrowable()));
			break;
		case ITestResult.SUCCESS_PERCENTAGE_FAILURE:
		case ITestResult.SUCCESS:
			reportiumClient.testStop(TestResultFactory.createSuccess());
			break;
		case ITestResult.SKIP:
			// Ignore
			break;
		default:
			throw new ReportiumException("Unexpected status " + status);
		}
		
		if (driver != null) {
			if(device){endLogging();}
			driver.close();
		}
	}

	protected static ReportiumClient getReportiumClient(RemoteWebDriver driver) {
		
		
		
		PerfectoExecutionContext perfectoExecutionContext = new PerfectoExecutionContext.PerfectoExecutionContextBuilder()
				.withProject(new Project("Boston Globe", "1.0")) // Optional
				.withContextTags("Build " + System.getProperty("BuildNumber"), "Software Version: 1.6", "Responsive Build Validation", "Tomer") // Optional
				.withWebDriver(driver).build();

		return new ReportiumClientFactory().createPerfectoReportiumClient(perfectoExecutionContext);
	}

	private static void sleep(long millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {}
	}
	
	private void startLogging() {
		Map<String, Object> params = new HashMap<>();
		driver.executeScript("mobile:logs:start", params);
		
		params.clear();
		List<String> vitals3 = new ArrayList<>();
		vitals3.add("all");
		params.put("vitals", vitals3);
		params.put("interval", "1");
		driver.executeScript("mobile:monitor:start", params);
		
		
		Map<String, Object> params1 = new HashMap<>();
		params1.put("profile", "4g_lte_average");
		Object result1 = driver.executeScript("mobile:vnetwork:start", params1);
	}
	
	private void endLogging() {
		Map<String, Object> params2 = new HashMap<>();
		Object result2 = driver.executeScript("mobile:logs:stop", params2);
		
		Map<String, Object> params3 = new HashMap<>();
		Object result3 = driver.executeScript("mobile:vnetwork:stop", params3);
		
		
	}

}
