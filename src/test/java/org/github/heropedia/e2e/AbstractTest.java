package org.github.heropedia.e2e;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.options.XCUITestOptions;
import org.apache.commons.io.FilenameUtils;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Time;
import java.time.Duration;


public abstract class AbstractTest {

    public static AppiumDriver driver;

    @BeforeAll
    public static void setUp() throws URISyntaxException, MalformedURLException {
        String appPath = System.getenv("TEST_APP_PATH");
        String simUdid = System.getenv("SIMULATOR_UDID");
        String cwd = System.getProperty("user.dir");
        Path path = Paths.get(cwd, appPath);
        System.out.printf("Application Path: %s%n", path);
        if(Files.exists(path)) {
            URI uri = new URI("http://127.0.0.1:4723/");
            driver = createDriver(uri, simUdid, path.toString());
            System.out.printf("Successfully created driver %s", driver.getClass().getName());
        }else{
            throw new RuntimeException(String.format("%s does not exist", path));
        }
    }

    private static AppiumDriver createDriver(URI uri, String simUdid, String appPath) throws MalformedURLException {
        return switch (FilenameUtils.getExtension(appPath)) {
            case "apk" -> getAndroidDriver(uri, appPath);
            case "app" -> getIosDriver(uri, simUdid, appPath);
            default -> throw new IllegalArgumentException("Unrecognized application");
        };
    }

    private static IOSDriver getIosDriver(URI appiumUri, String udid, String appPath) throws MalformedURLException {
        XCUITestOptions options = new XCUITestOptions()
                .setWdaStartupRetries(4)
                .setWdaStartupRetryInterval(Duration.ofSeconds(20))
                .setIsHeadless(true)
                .setUdid(udid)
                .setApp(appPath);
        options.setCapability("iosInstallPause","8000" );
        System.out.println("Creating a new iOS Driver");
        return new IOSDriver(appiumUri.toURL(), options);
    }

    private static AndroidDriver getAndroidDriver(URI uri, String appPath) throws MalformedURLException {
        var options = new UiAutomator2Options()
                .setApp(appPath);
        System.out.println("Creating a new Android Driver");
        return new AndroidDriver(uri.toURL(), options);
    }

    @AfterAll
    public static void cleanUp() {
        driver.quit();
    }
}
