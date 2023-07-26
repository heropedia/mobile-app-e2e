package org.github.heropedia.e2e;

import io.appium.java_client.AppiumBy;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebElement;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class HomePageTests extends AbstractTest {

    @Test
    public void shouldBeAbleToClickButton() {
        WebElement button = driver.findElement(AppiumBy.id("com.bugdiver.mobileapp:id/colourChangingButton"));
        button.click();
    }
}
