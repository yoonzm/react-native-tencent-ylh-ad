/**
 * Created by JetBrains WebStorm.
 * @Author: yoon
 * @Date: 2021/3/30
 * @Time: 下午3:20
 * @Desc: banner广告
 */

import React, { PureComponent } from 'react';
import { requireNativeComponent, StyleSheet, ViewProps } from 'react-native';
import RootSiblings from 'react-native-root-siblings';

const NativeExpressView = requireNativeComponent('NativeExpressView');

type Props = {
  /**
   * 广告id
   */
  posId: string;
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
   *  被用户关闭时调用
   *  详解:当打开showCloseBtn开关时，用户有可能点击关闭按钮从而把广告条关闭
   */
  onViewWillClose?: Function;
  /**
   *  点击回调
   */
  onClicked?: Function;
} & ViewProps;

export default class Flow extends PureComponent<Props> {
  /**
   * 静态调用
   */
  static show(options: Props) {
    const sibling = new RootSiblings(
      (
        <Flow
          style={styles.container}
          {...options}
          onViewWillClose={() => {
            options.onViewWillClose && options.onViewWillClose();
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
    return (
      <NativeExpressView
        {...this.props}
        // @ts-ignore
        onFailToReceived={this._onFailToReceived.bind(this)}
      />
    );
  }
}

const styles = StyleSheet.create({
  container: {
    width: 375,
    position: 'absolute',
    top: 0,
    bottom: 0,
    left: 0,
    right: 0,
    zIndex: 10,
    backgroundColor: '#000',
  },
});
