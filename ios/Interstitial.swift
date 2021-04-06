//
//  Interstitial.swift
//  react-native-tencent-ylh-ad
//
//  Created by swifthealth on 2021/4/6.
//

import Foundation

class Interstitial: NSObject, GDTUnifiedInterstitialAdDelegate {
    private static var mInstance:Interstitial?;
    private var iad: GDTUnifiedInterstitialAd?;
    private var posID: String = "";
    private var fullScreen: Bool = false;

    public static func getInstance()->Interstitial {
        if (mInstance == nil) {
          mInstance = Interstitial();
        }
        return mInstance!;
    }

    private override init() {
        
    }

    func showInterstitialAD(posID: String, fullScreen: Bool) {
        self.fullScreen = fullScreen;
        let iad = getIAD(posID: posID);
        RCTExecuteOnMainQueue {
            if (fullScreen) {
              iad.loadFullScreenAd();
            } else {
              iad.load();
            }
        }
    }

    private func getIAD(posID: String) -> GDTUnifiedInterstitialAd {
      if (iad != nil && self.posID == posID) {
        print(#function, "======相同IAD无需创建新的======")
        return iad!;
      }
      self.posID = posID;
      if (self.iad != nil) {
        iad = nil;
      }
      iad = GDTUnifiedInterstitialAd.init(placementId: posID)
      iad!.delegate = self
      return iad!;
    }

    func unifiedInterstitialFail(toLoad unifiedInterstitial: GDTUnifiedInterstitialAd, error: Error) {
        print(#function, error)
    }

    func unifiedInterstitialSuccess(toLoad unifiedInterstitial: GDTUnifiedInterstitialAd) {
        print(#function)
        if (self.iad != nil){
            RCTExecuteOnMainQueue {
              let rootViewController = (UIApplication.shared.keyWindow?.rootViewController)!
              if (self.fullScreen) {
                self.iad!.presentFullScreenAd(fromRootViewController: rootViewController)
              } else {
                self.iad!.present(fromRootViewController: rootViewController)
              }
            }
        }
    }
}
