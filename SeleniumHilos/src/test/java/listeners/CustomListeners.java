package listeners;


import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class CustomListeners implements ITestListener {
	// This belongs to ITestListener and will execute before starting of Test
	// set/batch
	public void onStart(ITestContext arg0) {
		System.out.println("Starts Test execution........ " + arg0.getName());
		System.out.println("-------------------------------------------------------------");

	}

	// This belongs to ITestListener and will execute after starting of Test
	// set/batch
	public void onFinish(ITestContext arg0) {
		System.out.println("Finish Test execution........ " + arg0.getName());
		System.out.println("=============================================================");
		System.out.println();
	}

	// This belongs to ITestListener and will execute before the main test start
	// i.e. @Test
	public void onTestStart(ITestResult arg0) {
		System.out.println("Starts test ........ " + arg0.getName());

	}

	// This belongs to ITestListener and will execute when a test is skipped
	public void onTestSkipped(ITestResult arg0) {
		System.out.println("Skipped test........ " + arg0.getName());

	}

	// This belongs to ITestListener and will execute when a test is passed
	public void onTestSuccess(ITestResult arg0) {
		System.out.println("Passed test......... " + arg0.getName());

	}

	// This belongs to ITestListener and will execute when a test is failed
	public void onTestFailure(ITestResult arg0) {
		System.out.println("Failed test......... " + arg0.getName());

	}

	// Not so important..ignore this as of now
	public void onTestFailedButWithinSuccessPercentage(ITestResult arg0) {
		// TODO Auto-generated method stub
	}
}
