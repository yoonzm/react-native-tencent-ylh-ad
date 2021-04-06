//
//  BannerViewManager.swift
//  react-native-tencent-ylh-ad
//
//  Created by swifthealth on 2021/4/6.
//

import Foundation

@objc(BannerViewManager)
class BannerViewManager: RCTViewManager {
    override func view() -> UIView! {
        let view = BannerView()
        return view
    }

    override class func requiresMainQueueSetup() -> Bool {
        return true
    }

    override var methodQueue: DispatchQueue! {
        return DispatchQueue.main
    }
}

@objc(BannerView)
class BannerView: UIView, GDTUnifiedBannerViewDelegate {
    private var bannerAd: GDTUnifiedBannerView!
    
    @objc var onDismissed: RCTBubblingEventBlock?
    @objc var onReceived: RCTBubblingEventBlock?
    @objc var onFailToReceived: RCTBubblingEventBlock?
    @objc var onClicked: RCTBubblingEventBlock?
    @objc var onViewWillClose: RCTBubblingEventBlock?
    
    @objc var posId: String = "" {
        didSet {
            print(#function, posId)
            let rect = CGRect.init(origin: .zero, size:CGSize.init(width: 375, height: 60))
            self.bannerAd = GDTUnifiedBannerView.init(frame: rect, placementId: posId, viewController: (UIApplication.shared.keyWindow?.rootViewController)!)
            self.bannerAd.delegate = self
            self.addSubview(self.bannerAd)
            self.bannerAd.loadAdAndShow()
        }
    }
    
    @objc var interval:Int32 = 30 {
        didSet {
            print(#function, interval)
            self.bannerAd?.autoSwitchInterval = interval
            self.bannerAd?.loadAdAndShow()
        }
    }
    
    func unifiedBannerViewClicked(_ unifiedBannerView: GDTUnifiedBannerView) {
        if self.onClicked != nil {
            self.onClicked!(nil)
        }
    }
    
    func unifiedBannerViewDidDismissFullScreenModal(_ unifiedBannerView: GDTUnifiedBannerView) {
        if self.onDismissed != nil {
            self.onDismissed!(nil)
        }
    }
    
    func unifiedBannerViewWillClose(_ unifiedBannerView: GDTUnifiedBannerView) {
        if self.onViewWillClose != nil {
            self.onViewWillClose!(nil)
        }
    }
    
    func unifiedBannerViewFailed(toLoad unifiedBannerView: GDTUnifiedBannerView, error: Error) {
        print(#function, error)
        if self.onFailToReceived != nil {
            self.onFailToReceived!(nil)
        }
    }
    
    func unifiedBannerViewDidLoad(_ unifiedBannerView: GDTUnifiedBannerView) {
        if self.onReceived != nil {
            self.onReceived!(nil)
        }
    }
}
