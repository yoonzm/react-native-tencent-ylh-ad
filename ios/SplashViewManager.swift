//
//  SplashViewManager.swift
//  react-native-tencent-ylh-ad
//
//  Created by swifthealth on 2021/4/2.
//

import Foundation

@objc(SplashViewManager)
class SplashViewManager: RCTViewManager, GDTSplashAdDelegate {
    override func view() -> UIView! {
        let view = SplashView()
        return view
    }

    override class func requiresMainQueueSetup() -> Bool {
        return true
    }

    override var methodQueue: DispatchQueue! {
        return DispatchQueue.main
    }
}

@objc(SplashView)
class SplashView: UIView, GDTSplashAdDelegate {
    private var splashAd: GDTSplashAd!
    
    @objc var onDismissed: RCTBubblingEventBlock?
    @objc var onPresent: RCTBubblingEventBlock?
    @objc var onFailToReceived: RCTBubblingEventBlock?
    
    @objc var posId: String = "" {
        didSet {
            self.splashAd = GDTSplashAd.init(placementId: posId)
            
            self.splashAd.delegate = self
            self.splashAd.fetchDelay = 5
            var splashImage = UIImage.init(named: "SplashNormal")
            if isIPhoneXSeries() {
                splashImage = UIImage.init(named: "SplashX")
            } else if (UIScreen.main.bounds.size.height == 400) {
                splashImage = UIImage.init(named: "SplashSmall")
            }
            self.splashAd.backgroundImage = splashImage
            self.splashAd.load()
        }
    }
    
    func splashAdClosed(_ splashAd: GDTSplashAd!) {
        print(#function)
        if self.onDismissed != nil {
            self.onDismissed!(nil)
        }
    }
    
    func splashAdDidLoad(_ splashAd: GDTSplashAd!) {
        print(#function)
        if self.splashAd.isAdValid() {
            // splash show逻辑
            RCTExecuteOnMainQueue {
                let window = UIApplication.shared.keyWindow
                self.splashAd.show(in: window, withBottomView: nil, skip: nil)
                window?.makeKeyAndVisible()
            }
        } else {
            print("unvalid")
        }
        
        if self.onPresent != nil {
            self.onPresent!(nil)
        }
    }
    
    func splashAdFail(toPresent splashAd: GDTSplashAd!, withError error: Error!) {
        print(#function, error)
        if self.onFailToReceived != nil {
            self.onFailToReceived!(nil)
        }
    }
}

