/**
 * Created by JetBrains WebStorm.
 * @Author: yoon
 * @Date: 2021/3/30
 * @Time: 下午3:20
 * @Desc: 开屏广告
 */

import React, { PureComponent } from 'react';
import {
  requireNativeComponent,
  StatusBar,
  ViewProps,
  StyleSheet,
} from 'react-native';
import RootSiblings from 'react-native-root-siblings';

const SplashView = requireNativeComponent('SplashView');

type Props = {
  /**
   * 广告id
   */
  posId: string;
  /**
   *  请求广告条数据成功后调用
   *  详解:当接收服务器返回的广告数据成功后调用该函数
   */
  onPresent?: Function;
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
   *  splash曝光回调
   */
  onViewWillExposure?: Function;
  /**
   *  splash点击回调
   */
  onClicked?: Function;
  /**
   *  splash广告关闭消失
   */
  onDismissed?: Function;
  /**
   *  splash广告下一步路由动作
   */
  onNextAction?: Function;
  /**
   *  splash广告倒计时回调
   */
  onTick?: Function;
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

export default class Splash extends PureComponent<Props> {
  /**
   * 静态调用
   */
  static show(options: Props) {
    let sibling = new RootSiblings(
      (
        <Splash
          style={styles.container}
          {...options}
          onDismissed={() => {
            options.onDismissed && options.onDismissed();
            sibling.destroy();
          }}
        />
      )
    );
  }

  _onFailToReceived(event: any) {
    this.props.onFailToReceived &&
      this.props.onFailToReceived(new Error(event.nativeEvent.error));
  }

  render() {
    StatusBar.setHidden(true);
    return (
      <SplashView
        {...this.props}
        // @ts-ignore
        onFailToReceived={this._onFailToReceived.bind(this)}
      />
    );
  }
}

const styles = StyleSheet.create({
  container: {
    height: '100%',
    width: '100%',
    position: 'absolute',
    top: 0,
    bottom: 0,
    left: 0,
    right: 0,
    zIndex: 10,
  },
});
