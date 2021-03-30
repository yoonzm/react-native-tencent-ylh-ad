package com.reactnativetencentad.view;

import android.util.Log;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.WritableMap;
import com.qq.e.ads.interstitial2.UnifiedInterstitialAD;
import com.qq.e.ads.interstitial2.UnifiedInterstitialADListener;
import com.qq.e.comm.managers.GDTADManager;
import com.qq.e.comm.util.AdError;

/**
 * 插屏广告
 */
public class Interstitial implements UnifiedInterstitialADListener {
  private static final String TAG = Interstitial.class.getSimpleName();

  private static Interstitial mInstance;
  private UnifiedInterstitialAD iad;
  private String posID;
  private boolean asPopup;
  private boolean fullScreen;

  private ReactApplicationContext mContext;

  public static synchronized Interstitial getInstance(ReactApplicationContext reactContext) {
    if (mInstance == null) {
      mInstance = new Interstitial(reactContext);
    }
    return mInstance;
  }

  private Interstitial(ReactApplicationContext reactContext){
    this.mContext = reactContext;
  }


  @Override
  public void onNoAD(AdError adError) {
    Log.e(TAG,"onNoAD: eCode=" + adError.getErrorCode() + ",eMsg=" + adError.getErrorMsg());
    WritableMap event = Arguments.createMap();
    event.putString("error", "BannerNoAD，eCode=" + adError);
  }

  @Override
  public void onADReceive() {
    Log.e(TAG,"onADReceive");
    if (this.iad != null){
      if (fullScreen) {
        this.iad.showFullScreenAD(mContext.getCurrentActivity());
      } else {
        if (asPopup){
          this.iad.showAsPopupWindow();
        } else {
          this.iad.show();
        }
      }
    }
  }

  @Override
  public void onVideoCached() {

  }

  @Override
  public void onADOpened() {
    Log.e(TAG,"onADOpened");
  }

  @Override
  public void onADExposure() {
    Log.e(TAG,"onADExposure");
  }

  @Override
  public void onADClosed() {
    Log.e(TAG,"onADClosed");
  }

  @Override
  public void onADClicked() {
    Log.e(TAG,"onADClicked");
  }

  @Override
  public void onADLeftApplication() {
    Log.e(TAG,"onADLeftApplication");
  }

  public void showInterstitialAD(String appID, String posID, boolean fullScreen, boolean asPopup) {
    Log.e(TAG, "showInterstitialAD");
    this.asPopup = asPopup;
    this.fullScreen = fullScreen;
    GDTADManager.getInstance().initWith(mContext, appID);
    UnifiedInterstitialAD iad = getIAD(posID);
    if (fullScreen) {
      iad.loadFullScreenAD();
    } else {
      iad.loadAD();
    }
  }

  private UnifiedInterstitialAD getIAD(String posID) {
    if (iad != null && this.posID.equals(posID)) {
      Log.e(TAG,"======相同IAD无需创建新的======");
      return iad;
    }
    this.posID = posID;
    if (this.iad != null) {
      iad.close();
      iad.destroy();
      iad = null;
    }
    iad = new UnifiedInterstitialAD(mContext.getCurrentActivity(), posID, this);
    return iad;
  }
}
