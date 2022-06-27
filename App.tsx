import React from 'react';
import {Button, Text, View} from 'react-native';
import NativeMyDeviceInfo from './js/NativeMyDeviceInfo';

const App = () => {
  const onPress = async () => {
    console.log('I GOT THE IP ADDRESS', NativeMyDeviceInfo.getIpAddress());
    console.log('GOT THE MAC ADDRESS', NativeMyDeviceInfo.getMacAddress());

    const isEmulator = await NativeMyDeviceInfo.isEmulator();
    console.log('IS EMULATOR', isEmulator);
  };

  return (
    <View>
      <Text>ITS WORKING!!!!</Text>

      <Button title="CLICK ME!" onPress={onPress} />
    </View>
  );
};

export default App;
