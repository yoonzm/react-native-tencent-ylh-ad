/**
 * Created by JetBrains WebStorm.
 * @Author: yoon
 * @Date: 2021/3/30
 * @Time: 下午3:20
 * @Desc: 原生广告
 */

import React, { PureComponent } from 'react';
import {
  requireNativeComponent,
  StyleSheet,
  ViewProps,
  ScrollView,
  Dimensions,
} from 'react-native';
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
  /**
   *  渲染完成
   */
  onRender?: Function;
} & ViewProps;

type State = {
  remove: boolean;
};

export default class NativeExpress extends PureComponent<Props, State> {
  /**
   * 静态调用
   */
  static show(options: Props) {
    const sibling = new RootSiblings(
      (
        <ScrollView style={styles.container}>
          {[...new Array(10)].map(() => (
            <NativeExpress
              {...options}
              onViewWillClose={() => {
                options.onViewWillClose && options.onViewWillClose();
                sibling.destroy();
              }}
            />
          ))}
        </ScrollView>
      )
    );
  }

  instance: any;

  constructor(props: Props) {
    super(props);
    this.state = {
      remove: false,
    };
  }

  _onFailToReceived(event: any) {
    this.props.onFailToReceived &&
      this.props.onFailToReceived(new Error(event.nativeEvent.error));
  }

  _onRender(event: any) {
    const { width } = event.nativeEvent;
    const screenWith = Dimensions.get('window').width;
    this.instance.setNativeProps({
      style: {
        width: screenWith,
        height: (event.nativeEvent.height * screenWith) / width,
      },
    });
    this.props.onRender && this.props.onRender(event.nativeEvent);
  }

  _onViewWillClose() {
    this.setState({
      remove: true,
    });
    this.props.onViewWillClose && this.props.onViewWillClose();
  }

  render() {
    if (this.state.remove) {
      return null;
    }

    return (
      <NativeExpressView
        {...this.props}
        ref={(ref) => {
          this.instance = ref;
        }}
        // @ts-ignore
        onFailToReceived={this._onFailToReceived.bind(this)}
        onRender={this._onRender.bind(this)}
        onViewWillClose={this._onViewWillClose.bind(this)}
      />
    );
  }
}

const styles = StyleSheet.create({
  container: {
    width: '100%',
    position: 'absolute',
    top: 0,
    bottom: 0,
    left: 0,
    right: 0,
    zIndex: 10,
    backgroundColor: '#000',
  },
});
