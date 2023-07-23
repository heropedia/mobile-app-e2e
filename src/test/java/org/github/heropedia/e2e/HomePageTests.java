package org.github.heropedia.e2e;

import io.appium.java_client.AppiumBy;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebElement;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class HomePageTests extends AbstractTest{

    @Test
    public void shouldBeAbleToClickButton() {
        WebElement button = driver.findElement(AppiumBy.id("colour-changing-button"));
        assertTrue(button.getText().equalsIgnoreCase("PRESS TO PAINT IT RED"));
        button.click();
        assertTrue(button.getText().equalsIgnoreCase("PRESS TO PAINT IT BLUE"));
    }
}
