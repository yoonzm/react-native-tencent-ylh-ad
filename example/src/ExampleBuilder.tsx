import RootSiblings from 'react-native-root-siblings';
import React from 'react';
import {
  Banner,
  BannerProps,
  NativeExpress,
  NativeExpressProps,
} from 'react-native-tencent-ylh-ad';
import { StyleSheet, View, Button, ScrollView } from 'react-native';

export function showBanner(options: BannerProps) {
  let sibling = new RootSiblings(
    (
      <View style={styles.container}>
        <Button
          title="close"
          onPress={() => {
            sibling.destroy();
          }}
        />
        {[...new Array(2)].map((_, index) => (
          <Banner
            key={index}
            style={styles.banner}
            onReceived={() => {
              console.log('showBanner.onReceived()', index);
            }}
            onFailToReceived={() => {
              console.log('showBanner.onFailToReceived()', index);
            }}
            {...options}
          />
        ))}
      </View>
    )
  );
}

export function showNativeExpress(options: NativeExpressProps) {
  const sibling = new RootSiblings(
    (
      <ScrollView style={styles.container}>
        <Button
          title="close"
          onPress={() => {
            sibling.destroy();
          }}
        />
        {[...new Array(2)].map((_, index) => (
          <NativeExpress
            key={index}
            onReceived={() => {
              console.log('showNativeExpress.onReceived()', index);
            }}
            onFailToReceived={() => {
              console.log('showNativeExpress.onFailToReceived()', index);
            }}
            onRender={() => {
              console.log('showNativeExpress.onRender()', index);
            }}
            {...options}
          />
        ))}
      </ScrollView>
    )
  );
}

const styles = StyleSheet.create({
  container: {
    position: 'absolute',
    top: 0,
    bottom: 0,
    left: 0,
    right: 0,
    zIndex: 999,
    backgroundColor: '#000',
  },
  banner: {
    height: 375 / 6.4, // Banner 规定banner宽高比应该为6.4:1 , 开发者可自行设置符合规定宽高比的具体宽度和高度值
    width: '100%',
  },
});
