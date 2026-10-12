package com.mydemoapp

import java.io.File

import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.configuration.RunConfiguration
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.testobject.TestObject

import internal.GlobalVariable

/**
 * MobileCommon - reusable custom keyword library for the Sauce Labs "Swag Labs" Android app
 * (package com.swaglabsmobileapp, the APK shipped in ./Apps).
 *
 * Design rules honoured here:
 *  - The APK location is resolved DYNAMICALLY from the Katalon project directory
 *    (RunConfiguration.getProjectDir()) - there is no absolute/hardcoded path anywhere.
 *  - Locators are built at runtime from the app's "test-" accessibility ids, so no fragile
 *    Mobile Recorder artifacts are required (Node is created programmatically).
 *  - Every value that could change (credentials, expected texts, timeouts) comes from Global
 *    Variables (execution profile), never from the Test Case body.
 *
 * Android note: this React Native app exposes each automation id as the native
 * accessibility label, i.e. content-desc = "test-" + label. Katalon's 'accessibilityId'
 * locator strategy maps to content-desc on Android, which is exactly what we need.
 */
public class MobileCommon {

    /** 'test-Username', 'test-Password', 'test-LOGIN', 'test-PRODUCTS', ... */
    private static final String PREFIX = 'test-'

    /**
     * Resolves the APK path from the project directory at runtime (project-relative, no hardcode).
     * @throws FileNotFoundException if the APK is missing from the project.
     */
    @Keyword
    static String getAppPath() {
        File apk = new File(RunConfiguration.getProjectDir(),
                GlobalVariable.APP_FOLDER + File.separator + GlobalVariable.APP_FILE)
        if (!apk.exists()) {
            throw new FileNotFoundException('APK not found. Expected it at: ' + apk.getAbsolutePath())
        }
        return apk.getAbsolutePath()
    }

    /** Installs (if needed) and launches the app under test using the dynamic project-relative APK path. */
    @Keyword
    static void startApplication() {
        Mobile.startApplication(getAppPath(), false)
    }

    /** Terminates the current application session. */
    @Keyword
    static void stopApplication() {
        Mobile.closeApplication()
    }

    /** Builds a mobile TestObject located by accessibility id (content-desc on Android). */
    @Keyword
    static TestObject byAccessibilityId(String id) {
        TestObject object = new TestObject(id)
        object.addProperty('accessibilityId', ConditionType.EQUALS, id)
        return object
    }

    /** Builds a mobile TestObject located by raw XPath (used for text assertions / indexed elements). */
    @Keyword
    static TestObject byXpath(String xpath) {
        TestObject object = new TestObject(xpath)
        object.addProperty('xpath', ConditionType.EQUALS, xpath)
        return object
    }

    /** Shortcut for a "test-<id>" object. */
    @Keyword
    static TestObject byTestId(String id) {
        return byAccessibilityId(PREFIX + id)
    }

    /** TestObject that matches a visible text/caption regardless of the exact widget. */
    @Keyword
    static TestObject byText(String text) {
        return byXpath("//*[@text=\"" + text + "\" or @content-desc=\"" + text + "\"]")
    }

    /** XPath that targets the first matching element with the given "test-" id. */
    @Keyword
    static TestObject firstByTestId(String id) {
        return byXpath("(//*[@content-desc=\"" + PREFIX + id + "\"])[1]")
    }

    /**
     * Performs a login through the UI. Fields are only filled when a value is supplied, so the same
     * helper also drives the "empty credentials" negative scenario ("" values).
     */
    @Keyword
    static void login(String username, String password) {
        int timeout = (GlobalVariable.TIMEOUT as int)

        Mobile.waitForElementPresent(byTestId('Login'), timeout, FailureHandling.OPTIONAL)

        if (username != null && !username.isEmpty()) {
            Mobile.setText(byTestId('Username'), username, timeout)
            hideKeyboardSafely()
        }
        if (password != null && !password.isEmpty()) {
            Mobile.setText(byTestId('Password'), password, timeout)
            hideKeyboardSafely()
        }

        Mobile.tap(byTestId('LOGIN'), timeout)
    }

    /** Convenience: login with the standard (valid) user from the execution profile. */
    @Keyword
    static void loginWithValidUser() {
        login(GlobalVariable.VALID_USERNAME, GlobalVariable.VALID_PASSWORD)
    }

    /** Verifies that the app has navigated to the logged-in Products (inventory) screen. */
    @Keyword
    static void verifyProductsScreenShown() {
        Mobile.verifyElementVisible(byTestId('PRODUCTS'), (GlobalVariable.TIMEOUT as int))
    }

    /** Verifies the login screen is still displayed (used by the negative login scenarios). */
    @Keyword
    static void verifyLoginScreenShown() {
        Mobile.verifyElementVisible(byTestId('Login'), (GlobalVariable.TIMEOUT as int))
    }

    /** Verifies a message text is visible on screen. */
    @Keyword
    static void verifyTextVisible(String text) {
        Mobile.verifyElementVisible(byText(text), (GlobalVariable.TIMEOUT as int))
    }

    /** Best-effort keyboard dismissal; never fails the test if there is no keyboard. */
    private static void hideKeyboardSafely() {
        try {
            Mobile.hideKeyboard()
        } catch (Exception ignored) {
            // No soft keyboard present / already hidden - safe to continue.
        }
    }
}
