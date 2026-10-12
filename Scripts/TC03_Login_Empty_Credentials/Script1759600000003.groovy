import com.mydemoapp.MobileCommon

import internal.GlobalVariable as GlobalVariable

/*
 * TC03 - Login with empty credentials (NEGATIVE)
 * Submitting the form with no data must show the required-field validation.
 */
MobileCommon.startApplication()

MobileCommon.login('', '')

MobileCommon.verifyTextVisible(GlobalVariable.MSG_USERNAME_REQUIRED)

MobileCommon.verifyLoginScreenShown()

MobileCommon.stopApplication()
