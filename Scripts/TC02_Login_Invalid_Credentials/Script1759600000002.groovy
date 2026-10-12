import com.mydemoapp.MobileCommon

import internal.GlobalVariable as GlobalVariable

/*
 * TC02 - Login with invalid credentials (NEGATIVE)
 * A non-existing user must trigger the service error message and must NOT be logged in.
 */
MobileCommon.startApplication()

MobileCommon.login(GlobalVariable.INVALID_USERNAME, GlobalVariable.INVALID_PASSWORD)

MobileCommon.verifyTextVisible(GlobalVariable.MSG_INVALID_CREDENTIALS)

MobileCommon.verifyLoginScreenShown()

MobileCommon.stopApplication()
