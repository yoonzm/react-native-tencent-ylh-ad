package com.reactnativetencentylhad.view;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.events.RCTEventEmitter;
import com.google.gson.Gson;
import com.qq.e.ads.nativ.express2.AdEventListener;
import com.qq.e.ads.nativ.express2.NativeExpressAD2;
import com.qq.e.ads.nativ.express2.NativeExpressADData2;
import com.qq.e.ads.nativ.express2.VideoOption2;
import com.qq.e.comm.util.AdError;
import com.reactnativetencentylhad.NativeExpressViewManager;
import com.reactnativetencentylhad.R;

import java.util.List;

/**
 * 流广告
 */
public class NativeExpress extends FrameLayout implements NativeExpressAD2.AdLoadListener, AdEventListener {
  private static final String TAG = "NativeExpress";
  private NativeExpressAD2 mNativeExpressAD2;
  private RCTEventEmitter mEventEmitter;
  private Runnable mLayoutRunnable;
  private NativeExpressADData2 mExpressADData2;
  private ThemedReactContext mThemedReactContext;
  private FrameLayout mContainer;

  public NativeExpress(Context context, String posID, RCTEventEmitter mEventEmitter, FrameLayout mContainer) {
    super(context);
    mThemedReactContext = (ThemedReactContext) context;
    this.mEventEmitter = mEventEmitter;
    this.mContainer = mContainer;
    // 把布局加载到这个View里面
    inflate(context, R.layout.layout_native_express,this);
    initView(posID);
  }

  /**
   * 初始化View
   */
  private void initView(String posID) {
    closeNativeExpress();
    mNativeExpressAD2 = new NativeExpressAD2(this.getContext(), posID, this);
    mNativeExpressAD2.setAdSize(375, 0); // 单位dp
    // 如果您在平台上新建平台模板2.0广告位时，选择了支持视频，那么可以进行个性化设置（可选）
    VideoOption2.Builder builder = new VideoOption2.Builder();

    /**
     * 如果广告位支持视频广告，强烈建议在调用loadData请求广告前设置setAutoPlayPolicy，有助于提高视频广告的eCPM值 <br/>
     * 如果广告位仅支持图文广告，则无需调用
     */
    builder.setAutoPlayPolicy(VideoOption2.AutoPlayPolicy.WIFI) // WIFI 环境下可以自动播放视频
      .setAutoPlayMuted(true) // 自动播放时为静音
      .setDetailPageMuted(false)  // 视频详情页播放时不静音
      .setMaxVideoDuration(0) // 设置返回视频广告的最大视频时长（闭区间，可单独设置），单位:秒，默认为 0 代表无限制，合法输入为：5<=maxVideoDuration<=61. 此设置会影响广告填充，请谨慎设置
      .setMinVideoDuration(0); // 设置返回视频广告的最小视频时长（闭区间，可单独设置），单位:秒，默认为 0 代表无限制， 此设置会影响广告填充，请谨慎设置
    mNativeExpressAD2.setVideoOption2(builder.build());
    mNativeExpressAD2.loadAd(1);
  }

  public void closeNativeExpress() {
    removeAllViews();
    if (mNativeExpressAD2 != null) {
      mNativeExpressAD2 = null;
      Log.e(TAG,"关闭广告");
    }
    if (mLayoutRunnable != null){
      removeCallbacks(mLayoutRunnable);
    }
  }

  public NativeExpressAD2 getNativeExpressAD2() {
    return mNativeExpressAD2;
  }

  @Override
  protected void onDetachedFromWindow() {
    super.onDetachedFromWindow();
  }

  @Override
  public void requestLayout() {
    super.requestLayout();
    if (mLayoutRunnable != null){
      removeCallbacks(mLayoutRunnable);
    }
    mLayoutRunnable = new Runnable() {
      @Override
      public void run() {
        measure(
          MeasureSpec.makeMeasureSpec(getWidth(), MeasureSpec.EXACTLY),
          MeasureSpec.makeMeasureSpec(getHeight(), MeasureSpec.EXACTLY));
        layout(getLeft(), getTop(), getRight(), getBottom());
      }
    };
    post(mLayoutRunnable);
  }

  @Override
  public void onNoAD(AdError adError) {
    Log.e(TAG,"onNoAD: eCode=" + adError.getErrorCode() + ",eMsg=" + adError.getErrorMsg());
    WritableMap event = Arguments.createMap();
    event.putString("error", new Gson().toJson(adError));
    mEventEmitter.receiveEvent(mContainer.getId(), NativeExpressViewManager.Events.EVENT_FAIL_TO_RECEIVED.toString(), event);
  }

  @Override
  public void onLoadSuccess(List<NativeExpressADData2> list) {
    Log.e(TAG,"onADReceive");
    mEventEmitter.receiveEvent(mContainer.getId(), NativeExpressViewManager.Events.EVENT_RECEIVED.toString(), null);
    if (mExpressADData2 != null) {
      return;
    }
    if (list.size() > 0) {
      NativeExpressADData2 expressADData2 = list.get(0);
      mExpressADData2 = expressADData2;
      expressADData2.setAdEventListener(this);
      expressADData2.render();
    }
  }

  @Override
  public void onClick() {
    mEventEmitter.receiveEvent(mContainer.getId(), NativeExpressViewManager.Events.EVENT_ON_CLICK.toString(), null);
  }

  @Override
  public void onExposed() {
    mEventEmitter.receiveEvent(mContainer.getId(), NativeExpressViewManager.Events.EVENT_WILL_EXPOSURE.toString(), null);
  }

  @Override
  public void onRenderSuccess() {
    Log.e(TAG,"onRenderSuccess");
    if (mExpressADData2.getAdView() != null) {
      removeAllViews();
      addView(mExpressADData2.getAdView());
      mExpressADData2.getAdView().addOnLayoutChangeListener(new OnLayoutChangeListener() {
        @Override
        public void onLayoutChange(View view, int i, int i1, int i2, int i3, int i4, int i5, int i6, int i7) {
          WritableMap arguments = Arguments.createMap();
          arguments.putInt("height", view.getHeight());
          arguments.putInt("width", view.getWidth());
          mEventEmitter.receiveEvent(mContainer.getId(), NativeExpressViewManager.Events.EVENT_ON_RENDER.toString(), arguments);
        }
      });
    }
  }

  @Override
  public void onRenderFail() {
    Log.e(TAG,"onRenderFail");
  }

  @Override
  public void onAdClosed() {
    Log.e(TAG,"onAdClosed");
    mEventEmitter.receiveEvent(mContainer.getId(), NativeExpressViewManager.Events.EVENT_WILL_CLOSE.toString(), null);
  }
}
