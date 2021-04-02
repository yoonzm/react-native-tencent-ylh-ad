@objc(TencentAd)
class TencentAd: NSObject {
    @objc(registerAppId:resolve:reject:)
    func registerAppId(appId: String, resolve: RCTPromiseResolveBlock, reject: RCTPromiseRejectBlock) -> Void {
        let result: Bool = GDTSDKConfig.registerAppId(appId);
        if result {
            resolve("")
        } else {
            reject("-1", "", nil)
        }
    }
}
