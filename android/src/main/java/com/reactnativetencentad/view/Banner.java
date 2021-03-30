package com.reactnativetencentad.view;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.FrameLayout;
import com.qq.e.ads.banner2.UnifiedBannerView;
import com.qq.e.ads.banner2.UnifiedBannerADListener;
import com.qq.e.comm.managers.GDTADManager;
import com.reactnativetencentad.R;

/**
 * banner广告
 */
public class Banner extends FrameLayout {

  private UnifiedBannerView mBanner;
  private Runnable mLayoutRunnable;

  public Banner(Context context, String appID, String posID, UnifiedBannerADListener listener) {
    super(context);
    // 把布局加载到这个View里面
    inflate(context, R.layout.layout_banner,this);
    GDTADManager.getInstance().initWith(context, appID);
    initView(posID, listener);
  }

  /**
   * 初始化View
   */
  private void initView(String posID, UnifiedBannerADListener listener) {
    closeBanner();
    mBanner = new UnifiedBannerView((Activity) this.getContext(), posID, listener);
    mBanner.setRefresh(30);
    addView(mBanner);
    getBanner().loadAD();
  }

  public void closeBanner() {
    removeAllViews();
    if (mBanner != null) {
      mBanner.destroy();
      mBanner = null;
      Log.e("UnifiedBanner","关闭广告");
    }
    if (mLayoutRunnable != null){
      removeCallbacks(mLayoutRunnable);
    }
  }

  public UnifiedBannerView getBanner() {
    return mBanner;
  }

  public void setInterval(int interval) {
    getBanner().setRefresh(interval);
  }

  @Override
  protected void onDetachedFromWindow() {
    closeBanner();
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
}
