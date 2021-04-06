@objc(TencentYlhAd)
class TencentYlhAd: NSObject {
    @objc(registerAppId:resolve:reject:)
    func registerAppId(appId: String, resolve: RCTPromiseResolveBlock, reject: RCTPromiseRejectBlock) -> Void {
        let result: Bool = GDTSDKConfig.registerAppId(appId);
        if result {
            resolve("")
        } else {
            reject("-1", "", nil)
        }
    }
    
    @objc(showInterstitialAD:asPopup:)
    func showInterstitialAD(podId: String, asPopup: Bool) -> Void {
        Interstitial.getInstance().showInterstitialAD(posID: podId, fullScreen: false);
    }
    
    @objc(showFullScreenAD:)
    func showFullScreenAD(podId: String) -> Void {
        Interstitial.getInstance().showInterstitialAD(posID: podId, fullScreen: true);
    }
    
    /***
       * h5
       * @param url
       * @param settings ["titleBarHeight", "titleBarColor", "title", "titleColor", "titleSize", "backButtonImage", "closeButtonImage", "separatorColor", "backSeparatorLength"]
       * titleColor='#ff0000ff',titleBarHeight=45 dp, titleSize=20 sp, backButtonImage='gdt_ic_back'
       */
    @objc(openWeb:settings:)
      func openWeb(url: String, settings: NSDictionary) {
        Hybrid.getInstance().openWeb(url: url, settings: settings)
      }
}
