//
//  BannerViewManagerBridge.m
//  react-native-tencent-ylh-ad
//
//  Created by swifthealth on 2021/4/6.
//

#import <React/RCTUIManager.h>
#import <React/RCTBridge.h>

@interface RCT_EXTERN_MODULE(BannerViewManager, NSObject)

RCT_EXPORT_VIEW_PROPERTY(posId, NSString)
RCT_EXPORT_VIEW_PROPERTY(interval, NSInteger)

RCT_EXPORT_VIEW_PROPERTY(onDismissed, RCTBubblingEventBlock);
RCT_EXPORT_VIEW_PROPERTY(onReceived, RCTBubblingEventBlock);
RCT_EXPORT_VIEW_PROPERTY(onFailToReceived, RCTBubblingEventBlock);
RCT_EXPORT_VIEW_PROPERTY(onClicked, RCTBubblingEventBlock);
RCT_EXPORT_VIEW_PROPERTY(onViewWillClose, RCTBubblingEventBlock);

@end
