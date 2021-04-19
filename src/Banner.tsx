/**
 * Created by JetBrains WebStorm.
 * @Author: yoon
 * @Date: 2021/3/30
 * @Time: 下午3:20
 * @Desc: banner广告
 */

import React, { PureComponent } from 'react';
import { requireNativeComponent, ViewProps } from 'react-native';

const BannerView = requireNativeComponent('BannerView');

export type Props = {
  /**
   * 广告id
   */
  posId: string;
  /**
   *  广告刷新间隔 [可选][30-120] 单位为 s, 0表示不自动轮播,默认30S
   */
  interval?: number; // 需要写在appInfo属性之前才能生效
  /**
   *  请求广告条数据成功后调用
   *  详解:当接收服务器返回的广告数据成功后调用该函数
   */
  onReceived?: Function;
  /**
   *  请求广告条数据失败后调用
   *  详解:当接收服务器返回的广告数据失败后调用该函数
   */
  onFailToReceived?: Function;
  /**
   *  应用进入后台时调用
   *  详解:当点击应用下载或者广告调用系统程序打开，应用将被自动切换到后台
   */
  onViewWillLeaveApplication?: Function;
  /**
   *  banner条被用户关闭时调用
   *  详解:当打开showCloseBtn开关时，用户有可能点击关闭按钮从而把广告条关闭
   */
  onViewWillClose?: Function;
  /**
   *  banner曝光回调
   */
  onViewWillExposure?: Function;
  /**
   *  banner条点击回调
   */
  onClicked?: Function;
  /**
   *  splash广告点击以后即将弹出全屏广告页
   */
  onViewWillPresentFullScreenModal?: Function;
  /**
   *  splash广告点击以后弹出全屏广告页完毕
   */
  onViewDidPresentFullScreenModal?: Function;
  /**
   *  全屏广告页即将被关闭
   */
  onViewWillDismissFullScreenModal?: Function;
  /**
   *  全屏广告页已经被关闭
   */
  onViewDidDismissFullScreenModal?: Function;
} & ViewProps;

export default class Banner extends PureComponent<Props> {
  _onFailToReceived(event: any) {
    this.props.onFailToReceived &&
      this.props.onFailToReceived(new Error(event.nativeEvent.error));
  }

  render() {
    return (
      <BannerView
        {...this.props}
        // @ts-ignore
        onFailToReceived={this._onFailToReceived.bind(this)}
      />
    );
  }
}
