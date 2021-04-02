import * as React from 'react';

import { StyleSheet, View, Button, Platform } from 'react-native';
import TencentAd, { Splash, Banner } from 'react-native-tencent-ad';
import config from '../config.json';

export default function App() {
  React.useEffect(() => {
    TencentAd.registerAppId(config.appId)
      .then(() => {
        console.log('init success');
      })
      .catch(() => {
        console.log('init fail');
      });
  });

  return (
    <View style={styles.container}>
      <Button
        title="开屏广告"
        onPress={() => {
          Splash.show({
            posId:
              Platform.OS === 'ios'
                ? config.splashIosPosId
                : config.splashPosId,
            onDismissed: () => {
              console.log('onDismissed');
            },
            onPresent: () => {
              console.log('onPresent');
            },
          });
        }}
      />
      <Button
        title="banner广告"
        onPress={() => {
          Banner.show({
            posId: config.bannerPosId,
          });
        }}
      />
      <Button
        title="插屏半屏"
        onPress={() => {
          TencentAd.showInterstitialAD(config.interstitialPosId, false);
        }}
      />
      <Button
        title="插屏全屏"
        onPress={() => {
          TencentAd.showFullScreenAD(config.fullScreenAdPosId);
        }}
      />
      <Button
        title="H5激励视频广告"
        onPress={() => {
          TencentAd.openWeb('https://www.baidu.com', {});
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
