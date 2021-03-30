import { NativeModules } from 'react-native';

type TencentAdType = {
  multiply(a: number, b: number): Promise<number>;
};

const { TencentAd } = NativeModules;

export default TencentAd as TencentAdType;
