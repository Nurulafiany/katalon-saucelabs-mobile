import com.mydemoapp.MobileCommon

/*
 * TC01 - Login with valid credentials (POSITIVE)
 * The APK is launched from the project-relative path resolved at runtime by MobileCommon,
 * then the standard user (from the execution profile) signs in and the Products screen
 * must be displayed.
 */
MobileCommon.startApplication()

MobileCommon.loginWithValidUser()

MobileCommon.verifyProductsScreenShown()

MobileCommon.stopApplication()
