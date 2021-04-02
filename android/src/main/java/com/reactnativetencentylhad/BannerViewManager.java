package com.reactnativetencentylhad;

import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.common.MapBuilder;
import com.facebook.react.uimanager.SimpleViewManager;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.annotations.ReactProp;
import com.facebook.react.uimanager.events.RCTEventEmitter;
import com.google.gson.Gson;
import com.qq.e.ads.banner2.UnifiedBannerADListener;
import com.qq.e.comm.util.AdError;
import com.reactnativetencentylhad.view.Banner;

import java.util.Map;

public class BannerViewManager extends SimpleViewManager implements UnifiedBannerADListener {
  private static final String TAG = "BannerView";

  @NonNull
  @Override
  public String getName() {
    return TAG;
  }
  @Override
  public void onNoAD(AdError adError) {
    Log.e(TAG,"onNoAD: eCode=" + adError.getErrorCode() + ",eMsg=" + adError.getErrorMsg());
    WritableMap event = Arguments.createMap();
    event.putString("error", new Gson().toJson(adError));
    mEventEmitter.receiveEvent(mContainer.getId(), Events.EVENT_FAIL_TO_RECEIVED.toString(), event);
  }

  @Override
  public void onADReceive() {
    Log.e(TAG,"onADReceive");
    mEventEmitter.receiveEvent(mContainer.getId(), Events.EVENT_RECEIVED.toString(), null);
  }

  @Override
  public void onADExposure() {
    Log.e(TAG,"onADExposure");
    mEventEmitter.receiveEvent(mContainer.getId(), Events.EVENT_WILL_EXPOSURE.toString(), null);
  }

  @Override
  public void onADClosed() {
    Log.e(TAG,"onADClosed");
    mEventEmitter.receiveEvent(mContainer.getId(), Events.EVENT_WILL_CLOSE.toString(), null);
  }

  @Override
  public void onADClicked() {
    Log.e(TAG,"onADClicked");
    mEventEmitter.receiveEvent(mContainer.getId(), Events.EVENT_ON_CLICK.toString(), null);
  }

  @Override
  public void onADLeftApplication() {
    Log.e(TAG,"onADLeftApplication");
    mEventEmitter.receiveEvent(mContainer.getId(), Events.EVENT_WILL_LEAVE_APP.toString(), null);
  }

  @Override
  public void onADOpenOverlay() {
    Log.e(TAG,"onADOpenOverlay");
    mEventEmitter.receiveEvent(mContainer.getId(), Events.EVENT_WILL_OPEN_FULL_SCREEN.toString(), null);
    mEventEmitter.receiveEvent(mContainer.getId(), Events.EVENT_DID_OPEN_FULL_SCREEN.toString(), null);
  }

  @Override
  public void onADCloseOverlay() {
    Log.e(TAG,"onADCloseOverlay");
    mEventEmitter.receiveEvent(mContainer.getId(), Events.EVENT_WILL_CLOSE_FULL_SCREEN.toString(), null);
    mEventEmitter.receiveEvent(mContainer.getId(), Events.EVENT_DID_CLOSE_FULL_SCREEN.toString(), null);
  }

  public enum Events {
    EVENT_FAIL_TO_RECEIVED("onFailToReceived"),
    EVENT_RECEIVED("onReceived"),
    EVENT_WILL_LEAVE_APP("onViewWillLeaveApplication"),
    EVENT_WILL_CLOSE("onViewWillClose"),
    EVENT_WILL_EXPOSURE("onViewWillExposure"),
    EVENT_ON_CLICK("onClicked"),
    EVENT_WILL_OPEN_FULL_SCREEN("onViewWillPresentFullScreenModal"),
    EVENT_DID_OPEN_FULL_SCREEN("onViewDidPresentFullScreenModal"),
    EVENT_WILL_CLOSE_FULL_SCREEN("onViewWillDismissFullScreenModal"),
    EVENT_DID_CLOSE_FULL_SCREEN("onViewDidDismissFullScreenModal");

    private final String mName;

    Events(final String name) {
      mName = name;
    }

    @Override
    public String toString() {
      return mName;
    }
  }

  private FrameLayout mContainer;
  private RCTEventEmitter mEventEmitter;
  private ThemedReactContext mThemedReactContext;
  private Banner mBanner;

  @Override
  protected View createViewInstance(ThemedReactContext reactContext) {
    mThemedReactContext = reactContext;
    mEventEmitter = reactContext.getJSModule(RCTEventEmitter.class);
    FrameLayout viewGroup = new FrameLayout(reactContext);
    mContainer = viewGroup;
    return viewGroup;
  }

  @Override
  public Map<String, Object> getExportedCustomDirectEventTypeConstants() {
    MapBuilder.Builder<String, Object> builder = MapBuilder.builder();
    for (Events event : Events.values()) {
      builder.put(event.toString(), MapBuilder.of("registrationName", event.toString()));
    }
    return builder.build();
  }

  // 其中，可以通过@ReactProp（或@ReactPropGroup）注解来导出属性的设置方法。
  // 该方法有两个参数，第一个参数是泛型View的实例对象，第二个参数是要设置的属性值。
  // 方法的返回值类型必须为void，而且访问控制必须被声明为public。
  // 组件的每一个属性的设置都会调用Java层被对应ReactProp注解的方法
  @ReactProp(name = "posId")
  public void setPosId(FrameLayout view, final String posId) {
    Banner banner = new Banner(mThemedReactContext.getCurrentActivity(), posId , this);
    mBanner = banner;
    view.addView(banner);
  }

  /***
   * 必须写在appInfo属性之前才能生效
   * @param view
   * @param interval
   */
  @ReactProp(name = "interval")
  public void setInterval(FrameLayout view, final int interval) {
    if (mBanner != null) mBanner.setInterval(interval);
  }
}
