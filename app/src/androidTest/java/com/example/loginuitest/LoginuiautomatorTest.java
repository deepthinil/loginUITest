package com.example.loginuitest;

import android.content.Context;
import android.content.Intent;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.filters.SdkSuppress;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.runner.AndroidJUnit4;
import androidx.test.uiautomator.By;
import androidx.test.uiautomator.UiDevice;
import androidx.test.uiautomator.UiObject;
import androidx.test.uiautomator.UiObjectNotFoundException;
import androidx.test.uiautomator.UiSelector;
import androidx.test.uiautomator.Until;

import org.junit.Before;
import org.junit.runner.RunWith;

import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;


@RunWith(AndroidJUnit4.class)
@SdkSuppress(minSdkVersion = 18)
public class LoginuiautomatorTest {

    private static final String LOGIN_UI_PACKAGE
            = "com.example.loginuitest";
    private static final int LAUNCH_TIMEOUT = 5000;
    private static final String SUCCESS_TEXT = "Successful sign in!";
    private UiDevice device;

    @Before
    public void startMainActivityFromHomeScreen() throws UiObjectNotFoundException {
        // Initialize UiDevice instance
        device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());

        // Start from the home screen
        device.pressHome();

        // Wait for launcher
        final String launcherPackage = device.getLauncherPackageName();
        assertThat(launcherPackage, notNullValue());
        device.wait(Until.hasObject(By.pkg(launcherPackage).depth(0)),
                LAUNCH_TIMEOUT);

        // Launch the app
        Context context = ApplicationProvider.getApplicationContext();
        final Intent intent = context.getPackageManager()
                .getLaunchIntentForPackage(LOGIN_UI_PACKAGE);
        // Clear out any previous instances
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);

        // Wait for the app to appear
        device.wait(Until.hasObject(By.pkg(LOGIN_UI_PACKAGE).depth(0)),
                LAUNCH_TIMEOUT);

        UiObject signinButton = device.findObject(new UiSelector()
                .text("SIGN IN")
                .resourceId("test.sdet.com.simpleapp:id/btn_signin")
                .className("android.widget.Button"));
        UiObject emailTextbox = device.findObject(new UiSelector()
                .resourceId("test.sdet.com.simpleapp:id/btn_signin")
                .className("android.widget.EditText"));
        UiObject passwordTextbox = device.findObject(new UiSelector()
                .resourceId("test.sdet.com.simpleapp:id/et_password")
                .className("android.widget.EditText"));
        UiObject signSuccessText = device.findObject(new UiSelector()
                .resourceId("test.sdet.com.simpleapp:id/tv_message")
                .className("android.widget.TextView"));

// Simulate a user-click on the Sign In button, if found.
        if (signinButton.exists()) {
            emailTextbox.setText("admin@ctm.com");
            passwordTextbox.setText("password123");
            signinButton.click();
            assertThat(signSuccessText.getText(), is(equalTo(SUCCESS_TEXT)));
        }
    }
}