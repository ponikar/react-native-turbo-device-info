//
//  MyDeviceInfo.m
//  DeviceTurbo
//
//  Created by Darshan Ponikar on 26/06/22.
//

#import <Foundation/Foundation.h>
#import <MyDeviceInfo/MyDeviceInfo.h>

@interface MyDeviceInfo: NSObject<NativeMyDeviceInfoSpec>
@end

using namespace facebook;

@implementation MyDeviceInfo

RCT_EXPORT_MODULE()

- (NSString *)getIpAddress {
  return @("Hello world");
}

- (NSString *)getMacAddress {
  return @("I am mac address");
}

- (void)isEmulator:(RCTPromiseResolveBlock)resolve reject:(RCTPromiseRejectBlock)reject {
  resolve(@YES);
}

- (std::shared_ptr<react::TurboModule>)getTurboModule:(const react::ObjCTurboModule::InitParams &)params {
  return std::make_shared<react::NativeMyDeviceInfoSpecJSI>(params);
}

@end
