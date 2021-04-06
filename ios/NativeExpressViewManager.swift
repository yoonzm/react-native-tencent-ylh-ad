//
//  NativeExpressViewManager.swift
//  react-native-tencent-ylh-ad
//
//  Created by swifthealth on 2021/4/6.
//

import Foundation

@objc(NativeExpressViewManager)
class NativeExpressViewManager: RCTViewManager {
    override func view() -> UIView! {
        let view = NativeExpressView()
        return view
    }

    override class func requiresMainQueueSetup() -> Bool {
        return true
    }

    override var methodQueue: DispatchQueue! {
        return DispatchQueue.main
    }
}

@objc(NativeExpressView)
class NativeExpressView: UIView, GDTNativeExpressProAdManagerDelegate, GDTNativeExpressProAdViewDelegate {
    private var expressAdViews:Array<GDTNativeExpressProAdView>!
    private var nativeExpressAd:GDTNativeExpressProAdManager!
    
    @objc var onReceived: RCTBubblingEventBlock?
    @objc var onFailToReceived: RCTBubblingEventBlock?
    @objc var onViewWillClose: RCTBubblingEventBlock?
    @objc var onClicked: RCTBubblingEventBlock?
    
    @objc var posId: String = "" {
        didSet {
            print(#function, posId)
            let adParams = GDTAdParams()
            adParams.adSize = CGSize.init(width: (UIApplication.shared.keyWindow?.bounds.width)!, height: 0)
            nativeExpressAd = GDTNativeExpressProAdManager.init(placementId: posId, adPrams: adParams)
            nativeExpressAd.delegate = self
            nativeExpressAd.loadAd(1)
        }
    }
    
    func gdt_nativeExpressProAdSuccess(toLoad adManager: GDTNativeExpressProAdManager, views: [GDTNativeExpressProAdView]) {
        expressAdViews = Array.init(views)
        if expressAdViews.count > 0 {
            for obj in expressAdViews {
                let expressView:GDTNativeExpressProAdView = obj
                expressView.controller = UIApplication.shared.keyWindow?.rootViewController
                expressView.delegate = self
                self.addSubview(expressView)
                expressView.render()
                if self.onReceived != nil {
                    self.onReceived!(nil)
                }
            }
        }
    }
    
    func gdt_nativeExpressProAdFail(toLoad adManager: GDTNativeExpressProAdManager, error: Error) {
        print(#function, error)
        if self.onFailToReceived != nil {
            self.onFailToReceived!([
                "message": error.localizedDescription
            ])
        }
    }
    
    func gdt_NativeExpressProAdViewClosed(_ nativeExpressProAdView: GDTNativeExpressProAdView) {
        if self.onViewWillClose != nil {
            self.onViewWillClose!(nil)
        }
    }
    
    func gdt_NativeExpressProAdViewExposure(_ nativeExpressProAdView: GDTNativeExpressProAdView) {
        print(#function)
    }
    
    func gdt_NativeExpressProAdViewClicked(_ nativeExpressProAdView: GDTNativeExpressProAdView) {
        print(#function)
        if self.onClicked != nil {
            self.onClicked!(nil)
        }
    }
}
