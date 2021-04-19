import * as React from 'react';

import {
  StyleSheet,
  View,
  TouchableOpacity,
  Platform,
  Text,
  GestureResponderEvent,
} from 'react-native';
import TencentYlhAd, { Splash } from 'react-native-tencent-ylh-ad';
import config from '../config.json';
import { showBanner, showNativeExpress } from './ExampleBuilder';

const platformConfig = config[Platform.OS as 'android' | 'ios'];

function Button({
  title,
  onPress,
}: {
  title: string;
  onPress: (event: GestureResponderEvent) => void;
}) {
  return (
    <TouchableOpacity
      style={{
        height: 50,
        width: '100%',
        borderRadius: 8,
        justifyContent: 'center',
        alignItems: 'center',
        backgroundColor: '#888',
        marginTop: 10,
      }}
      onPress={onPress}
    >
      <Text
        style={{
          color: 'white',
        }}
      >
        {title}
      </Text>
    </TouchableOpacity>
  );
}

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
            onPresent: () => {
              console.log('Splash.onPresent()');
            },
            onFailToReceived: (err: any) => {
              console.log('Splash.onFailToReceived()', err);
            },
            onNextAction: () => {
              console.log('Splash.onNextAction()');
            },
          });
        }}
      />
      <Button
        title="banner广告"
        onPress={() => {
          showBanner({
            posId: platformConfig.bannerPosId,
          });
        }}
      />
      <Button
        title="信息流广告"
        onPress={() => {
          showNativeExpress({
            posId: platformConfig.flowAdPosId,
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
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    alignItems: 'center',
    justifyContent: 'center',
    paddingHorizontal: 15,
  },
});
