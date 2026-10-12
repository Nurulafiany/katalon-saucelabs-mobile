import com.mydemoapp.MobileCommon

import internal.GlobalVariable as GlobalVariable

/*
 * TC05 - Products catalog is displayed after login (POSITIVE)
 * Verifies the inventory screen and that the first catalog product is rendered.
 */
MobileCommon.startApplication()

MobileCommon.loginWithValidUser()

MobileCommon.verifyProductsScreenShown()

MobileCommon.verifyTextVisible(GlobalVariable.FIRST_PRODUCT_NAME)

MobileCommon.stopApplication()
