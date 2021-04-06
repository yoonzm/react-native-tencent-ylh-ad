#import <React/RCTBridgeModule.h>

@interface RCT_EXTERN_MODULE(TencentYlhAd, NSObject)

RCT_EXTERN_METHOD(registerAppId:(NSString)appId
                  resolve:(RCTPromiseResolveBlock)resolve
                  reject:(RCTPromiseRejectBlock)reject)

RCT_EXTERN_METHOD(showInterstitialAD:(NSString)podId
                  asPopup:(BOOL)asPopup)

RCT_EXTERN_METHOD(showFullScreenAD:(NSString)podId)

RCT_EXTERN_METHOD(openWeb:(NSString)url
                  settings:(NSDictionary)settings)

@end
