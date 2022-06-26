import React from 'react';
import {Button, Text, View} from 'react-native';
import NativeMyDeviceInfo from './js/NativeMyDeviceInfo';

const App = () => {
  const onPress = () => {
    console.log('I GOT THE IP ADDRESS', NativeMyDeviceInfo.getIpAddress());
  };

  return (
    <View>
      <Text>ITS WORKING!!!!</Text>

      <Button title="CLICK ME!" onPress={onPress} />
    </View>
  );
};

export default App;
