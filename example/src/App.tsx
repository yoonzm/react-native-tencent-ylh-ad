import * as React from 'react';

import { StyleSheet, View, Button, Platform } from 'react-native';
import TencentYlhAd, {
  Splash,
  Banner,
  Flow,
} from 'react-native-tencent-ylh-ad';
import config from '../config.json';

const platformConfig = config[Platform.OS as 'android' | 'ios'];

export default function App() {
  React.useEffect(() => {
    TencentYlhAd.registerAppId(config.appId)
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
            posId: platformConfig.splashPosId,
          });
        }}
      />
      <Button
        title="banner广告"
        onPress={() => {
          Banner.show({
            posId: platformConfig.bannerPosId,
          });
        }}
      />
      <Button
        title="插屏半屏"
        onPress={() => {
          TencentYlhAd.showInterstitialAD(
            platformConfig.interstitialPosId,
            false
          );
        }}
      />
      <Button
        title="插屏全屏"
        onPress={() => {
          TencentYlhAd.showFullScreenAD(platformConfig.fullScreenAdPosId);
        }}
      />
      <Button
        title="H5激励视频广告"
        onPress={() => {
          TencentYlhAd.openWeb('https://www.baidu.com', {});
        }}
      />
      <Button
        title="信息流广告"
        onPress={() => {
          Flow.show({
            posId: platformConfig.flowAdPosId,
          });
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
