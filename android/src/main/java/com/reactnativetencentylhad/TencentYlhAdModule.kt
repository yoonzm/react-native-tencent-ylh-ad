package com.reactnativetencentylhad

import com.facebook.react.bridge.*
import com.qq.e.comm.managers.GDTADManager
import com.reactnativetencentylhad.view.Hybrid
import com.reactnativetencentylhad.view.Interstitial


class TencentYlhAdModule(reactContext: ReactApplicationContext) : ReactContextBaseJavaModule(reactContext) {
  private var appId: String = "";

  override fun getName(): String {
    return "TencentYlhAd"
  }

  /**
   * 半屏广告
   */
  @ReactMethod
  fun registerAppId(appId: String, promise: Promise) {
    this.appId = appId;
    val result = GDTADManager.getInstance().initWith(reactApplicationContext, appId)
    if (result) {
      promise.resolve(null)
    } else{
      promise.reject("-1", "")
    }
  }

  /**
   * 半屏广告
   */
  @ReactMethod
  fun showInterstitialAD(posID: String, asPopup: Boolean) {
    Interstitial.getInstance(reactApplicationContext).showInterstitialAD(posID, false, asPopup);
  }

  /**
   * 全屏广告
   */
  @ReactMethod
  fun showFullScreenAD(posID: String) {
    Interstitial.getInstance(reactApplicationContext).showInterstitialAD(posID, true, false);
  }

  /***
   * h5
   * @param url
   * @param settings ["titleBarHeight", "titleBarColor", "title", "titleColor", "titleSize", "backButtonImage", "closeButtonImage", "separatorColor", "backSeparatorLength"]
   * titleColor='#ff0000ff',titleBarHeight=45 dp, titleSize=20 sp, backButtonImage='gdt_ic_back'
   */
  @ReactMethod
  fun openWeb(url: String, settings: ReadableMap) {
    Hybrid.getInstance(reactApplicationContext).openWeb(url, settings)
  }
}
