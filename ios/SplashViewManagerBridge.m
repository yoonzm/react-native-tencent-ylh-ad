//
//  SplashViewManager.m
//  react-native-tencent-ylh-ad
//
//  Created by swifthealth on 2021/4/2.
//

#import <React/RCTUIManager.h>
#import <React/RCTBridge.h>

@interface RCT_EXTERN_MODULE(SplashViewManager, NSObject)

RCT_EXPORT_VIEW_PROPERTY(posId, NSString)

RCT_EXPORT_VIEW_PROPERTY(onDismissed, RCTBubblingEventBlock);
RCT_EXPORT_VIEW_PROPERTY(onPresent, RCTBubblingEventBlock);
RCT_EXPORT_VIEW_PROPERTY(onFailToReceived, RCTBubblingEventBlock);
RCT_EXPORT_VIEW_PROPERTY(onNextAction, RCTBubblingEventBlock);

@end
