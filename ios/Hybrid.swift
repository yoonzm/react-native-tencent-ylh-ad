//
//  Hybrid.swift
//  react-native-tencent-ylh-ad
//
//  Created by swifthealth on 2021/4/6.
//

import Foundation

class Hybrid: NSObject, GDTHybridAdDelegate {
    private static var mInstance:Hybrid?;
    
    public static func getInstance()->Hybrid {
        if (mInstance == nil) {
          mInstance = Hybrid();
        }
        return mInstance!;
    }
    
    private override init() {
        
    }
    
    func openWeb(url: String, settings: NSDictionary) -> Void {
        // 初始化不需要 placementId，混合页面类型选择 GDTHybridAdOptionRewardVideo 表示激励视频。
        let hybridAD = GDTHybridAd.init(type: GDTHybridAdOptions.rewardVideo)
        hybridAD.delegate = self
        // 加载合作的 H5 页面地址
        RCTExecuteOnMainQueue {
            hybridAD.load(withUrl: url)
            // 在指定的 VC present 混合页面，开发者可以在合适的时机调用此方法，一般为用户点击开发者设置的跳转入口时。
            let rootViewController = (UIApplication.shared.keyWindow?.rootViewController)!
            hybridAD.show(withRootViewController: rootViewController)
        }
    }
}
