package com.reactnativetencentad.view;

import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.facebook.react.bridge.Dynamic;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.ReadableType;
import com.qq.e.ads.hybrid.HybridAD;
import com.qq.e.ads.hybrid.HybridADListener;
import com.qq.e.ads.hybrid.HybridADSetting;
import com.qq.e.comm.managers.GDTADManager;
import com.qq.e.comm.util.AdError;
import com.reactnativetencentad.util.ReflectionUtils;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class Hybrid implements HybridADListener {
  private static final String TAG = Hybrid.class.getSimpleName();

  private static Hybrid mInstance;
  private ReactApplicationContext mContext;

  private Hybrid(ReactApplicationContext reactContext) {
    this.mContext = reactContext;
  }

  public static synchronized Hybrid getInstance(ReactApplicationContext reactContext) {
    if (mInstance == null) {
      mInstance = new Hybrid(reactContext);
    }
    return mInstance;
  }

  @Override
  public void onPageShow() {
    Log.e(TAG, "onPageShow");
  }

  @Override
  public void onLoadFinished() {
    Log.e(TAG, "onLoadFinished");
  }

  @Override
  public void onClose() {
    Log.e(TAG, "onClose");
  }

  @Override
  public void onError(AdError adError) {
    String msg = String.format(Locale.getDefault(), "onError, error code: %d, error msg: %s",
      adError.getErrorCode(), adError.getErrorMsg());
    Toast.makeText(mContext.getCurrentActivity(), msg, Toast.LENGTH_LONG).show();
  }

  public void openWeb(String appID, String url, ReadableMap settings) {
    if (!TextUtils.isEmpty(url)) {
      HybridADSetting setting = fillHybridADSetting(settings);
      if (setting != null) {
        GDTADManager.getInstance().initWith(mContext, appID);
        HybridAD hybridAD = new HybridAD(mContext, setting, this);
        hybridAD.loadUrl(url);
      }
    } else {
      Toast.makeText(mContext.getCurrentActivity(), "请输入url地址！", Toast.LENGTH_LONG).show();
    }
  }

  private static Map<String, Method> cacheMethodMap = null;

  private static Map<String, Method> cacheMethodByName(Class cls, List<String> names) {
    Map<String, Method> map = new HashMap<>();
    if (cacheMethodMap == null) {
      Method[] allMethod = cls.getDeclaredMethods();
      for (Method method : allMethod) {
        String methodName = method.getName();
        if (names.contains(methodName)) {
          map.put(methodName, method);
        }
      }
      cacheMethodMap = map;
    }
    return cacheMethodMap;
  }

  private Object getValueByName(ReadableMap settings, String name) {
    Object obj = null;

    ReadableType type = settings.getType(name);
    Dynamic dynamic = settings.getDynamic(name);
    if (type == ReadableType.Number) {
      obj = dynamic.asInt();
    } else if (type == ReadableType.Boolean) {
      obj = dynamic.asBoolean();
    } else if (type == ReadableType.String) {
      if (name.toLowerCase().contains("color")) {
        obj = Long.valueOf(dynamic.asString().replace("#", ""), 16).intValue();
      } else {
        obj = dynamic.asString();
      }
    } else {
      Log.e(TAG, "定义属性类型不正确，请自行检查！");
    }
    return obj;
  }

  private HybridADSetting fillHybridADSetting(ReadableMap settings) {
    HybridADSetting setting = new HybridADSetting().type(HybridADSetting.TYPE_REWARD_VIDEO);
    if (settings == null) return setting;
    List<String> keys = Arrays.asList("titleBarHeight", "titleBarColor", "title", "titleColor", "titleSize", "backButtonImage", "closeButtonImage", "separatorColor", "backSeparatorLength");
    try {
      for (String key : keys) {
        boolean hasKey = settings.hasKey(key);
        if (hasKey) {
          Method method = cacheMethodByName(HybridADSetting.class, keys).get(key);
          Object args = getValueByName(settings, key);
          if (args != null) {
            ReflectionUtils.invokeMethod(method, setting, args);
          }
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
      Log.e(TAG, e.getLocalizedMessage());
    }

    return setting;
  }

}
