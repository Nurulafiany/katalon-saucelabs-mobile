import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile

import com.mydemoapp.MobileCommon

import internal.GlobalVariable as GlobalVariable

/*
 * TC06 - Add the first product to the cart (POSITIVE)
 * After tapping "ADD TO CART" on the first item, that item's button flips to "REMOVE",
 * which proves the product was added to the cart.
 */
MobileCommon.startApplication()

MobileCommon.loginWithValidUser()

MobileCommon.verifyProductsScreenShown()

Mobile.tap(MobileCommon.firstByTestId('ADD TO CART'), GlobalVariable.TIMEOUT as int)

Mobile.verifyElementVisible(MobileCommon.byTestId('REMOVE'), GlobalVariable.TIMEOUT as int)

MobileCommon.stopApplication()
