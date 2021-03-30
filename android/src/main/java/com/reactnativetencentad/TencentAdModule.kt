package com.reactnativetencentad

import com.facebook.react.bridge.*
import com.reactnativetencentad.view.Hybrid
import com.reactnativetencentad.view.Interstitial


class TencentAdModule(reactContext: ReactApplicationContext) : ReactContextBaseJavaModule(reactContext) {

  override fun getName(): String {
    return "TencentAd"
  }

  /**
   * 半屏广告
   */
  @ReactMethod
  fun showInterstitialAD(appID: String, posID: String, asPopup: Boolean) {
    Interstitial.getInstance(reactApplicationContext).showInterstitialAD(appID, posID, false, asPopup);
  }

  /**
   * 全屏广告
   */
  @ReactMethod
  fun showFullScreenAD(appID: String, posID: String) {
    Interstitial.getInstance(reactApplicationContext).showInterstitialAD(appID, posID, true, false);
  }

  /***
   *
   * @param appID
   * @param url
   * @param settings ["titleBarHeight", "titleBarColor", "title", "titleColor", "titleSize", "backButtonImage", "closeButtonImage", "separatorColor", "backSeparatorLength"]
   * titleColor='#ff0000ff',titleBarHeight=45 dp, titleSize=20 sp, backButtonImage='gdt_ic_back'
   */
  @ReactMethod
  fun openWeb(appID: String, url: String, settings: ReadableMap) {
    val hybrid = Hybrid.getInstance(reactApplicationContext)
    hybrid.openWeb(appID, url, settings)
  }
}
