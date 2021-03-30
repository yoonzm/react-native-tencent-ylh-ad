import * as React from 'react';

import { StyleSheet, View, Button } from 'react-native';
import TencentAd, { Splash, Banner } from 'react-native-tencent-ad';
import config from '../config.json';

export default function App() {
  return (
    <View style={styles.container}>
      <Button
        title="开屏广告"
        onPress={() => {
          Splash.show({
            appInfo: {
              appId: config.appId,
              posId: config.splashPosId,
            },
          });
        }}
      />
      <Button
        title="banner广告"
        onPress={() => {
          Banner.show({
            appInfo: {
              appId: config.appId,
              posId: config.bannerPosId,
            },
          });
        }}
      />
      <Button
        title="插屏半屏"
        onPress={() => {
          TencentAd.showInterstitialAD(
            config.appId,
            config.interstitialPosId,
            false
          );
        }}
      />
      <Button
        title="插屏全屏"
        onPress={() => {
          TencentAd.showFullScreenAD(config.appId, config.fullScreenAdPosId);
        }}
      />
      <Button
        title="H5激励视频广告"
        onPress={() => {
          TencentAd.openWeb(config.appId, 'https://www.baidu.com', {});
        }}
      />
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    alignItems: 'center',
    justifyContent: 'center',
  },
});
