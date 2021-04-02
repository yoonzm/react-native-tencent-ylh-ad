package com.reactnativetencentylhad.view;

import android.app.Activity;
import android.content.Context;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.qq.e.ads.splash.SplashAD;
import com.qq.e.ads.splash.SplashADListener;
import com.reactnativetencentylhad.R;

/**
 * 开屏广告
 */
public class Splash extends RelativeLayout {
  private SplashAD mSplashAD;
  private ViewGroup container;
  private Runnable mLayoutRunnable;

  public Splash(Context context, String posID, SplashADListener listener) {
    super(context);
    // 把布局加载到这个View里面
    inflate(context, R.layout.layout_splash, this);
    initView(posID, listener);
    mSplashAD.fetchAndShowIn(container);
  }

  /**
   * 初始化View
   */
  private void initView(String posID, SplashADListener listener) {
    container = (ViewGroup) this.findViewById(R.id.splash_container);
    mSplashAD = new SplashAD((Activity) this.getContext(), posID, listener);
  }

  @Override
  public void requestLayout() {
    super.requestLayout();
    if (mLayoutRunnable != null) {
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
