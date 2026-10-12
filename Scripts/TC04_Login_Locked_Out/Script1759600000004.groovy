import com.mydemoapp.MobileCommon

import internal.GlobalVariable as GlobalVariable

/*
 * TC04 - Login with a locked-out user (NEGATIVE)
 * The locked-out account must surface the dedicated error and the user must stay signed out.
 */
MobileCommon.startApplication()

MobileCommon.login(GlobalVariable.LOCKED_USERNAME, GlobalVariable.VALID_PASSWORD)

MobileCommon.verifyTextVisible(GlobalVariable.MSG_LOCKED_OUT)

MobileCommon.verifyLoginScreenShown()

MobileCommon.stopApplication()
