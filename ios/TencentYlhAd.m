#import <React/RCTBridgeModule.h>

@interface RCT_EXTERN_MODULE(TencentYlhAd, NSObject)

RCT_EXTERN_METHOD(registerAppId:(NSString)appId
                  resolve:(RCTPromiseResolveBlock)resolve
                  reject:(RCTPromiseRejectBlock)reject)

@end
