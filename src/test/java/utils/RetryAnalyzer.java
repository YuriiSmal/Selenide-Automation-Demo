package utils;

import com.automation.common.Config;
import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class RetryAnalyzer implements IRetryAnalyzer {

    private int count = 0;

    @Override
    public boolean retry(ITestResult iTestResult) {
        MaxRetryCount maxRetryCount = iTestResult.getMethod()
                .getConstructorOrMethod()
                .getMethod()
                .getAnnotation(MaxRetryCount.class);

        int maxRetry;
        if (Config.DISABLE_RETRY_ANALYZER) {
            maxRetry = 0;
        } else {
            maxRetry = maxRetryCount == null ? 0 : maxRetryCount.value();
        }

        if (!iTestResult.isSuccess()) {                      //Check if test not succeed
            if (count < maxRetry) {                          //Check if max try count is reached
                count++;                                     //Increase the maxTry count by 1
                iTestResult.setStatus(ITestResult.FAILURE);  //Mark test as failed
                return true;                                 //Tells TestNG to re-run the test
            } else {
                iTestResult.setStatus(ITestResult.FAILURE);  //If maxRetry reached, test marked as failed
            }
        } else {
            iTestResult.setStatus(ITestResult.SUCCESS);      //If test passes, TestNG marks it as passed
        }
        return false;
    }
}
