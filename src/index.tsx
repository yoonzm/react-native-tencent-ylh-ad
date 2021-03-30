import { NativeModules } from 'react-native';

type TencentAdType = {
  /**
   * 半屏广告
   */
  showInterstitialAD(appID: string, posID: string, asPopup: boolean): void;
  /**
   * 全屏广告
   */
  showFullScreenAD(appID: string, posID: string): void;
  /**
   * H5-SDK激励视频广告
   */
  openWeb(
    appID: string,
    url: string,
    settings: {
      titleBarHeight?: number; // 自定义标题栏高度，单位dp
      titleBarColor?: string; // 自定义标题栏颜色
      titleColor?: string; // 自定义标题字体颜色
      titleSize?: string; // 自定义标题字体大小，单位sp
      backButtonImage?: string; // 自定义返回键图标，SDK将根据传入的资源名查找图片，例如apk中有资源icon.png,这里设置时传入“icon”即可
      closeButtonImage?: string; // 自定义关闭键图标，SDK将根据传入的资源名查找图片，例如apk中有资源icon.png,这里设置时传入“icon”即可
      separatorColor?: string; // 自定义分割线颜色
      backSeparatorLength?: number; // 自定义返回键和关闭键中间的分割线的长度，单位dp
    }
  ): void;
};

const { TencentAd } = NativeModules;

export default TencentAd as TencentAdType;

export { default as Splash } from './Splash';
export { default as Banner } from './Banner';
