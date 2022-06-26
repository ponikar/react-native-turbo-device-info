package com.deviceturbo.newarchitecture.modules;

import androidx.annotation.NonNull;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.Promise;

import com.deviceturbo.codegen.NativeMyDeviceInfoSpec;

public class MyDeviceInfo extends NativeMyDeviceInfoSpec {
    public MyDeviceInfo(ReactApplicationContext ct) {
        super(ct);
    }

    public final static String NAME = "MyDeviceInfo";
    @Override
    public String getIpAddress() {
        return "192.168.12.1";
    }

    @Override
    public String getMacAddress() {
        return null;
    }

    @Override
    public void isEmulator(Promise promise) {

        promise.resolve(true);
    }

    @NonNull
    @Override
    public String getName() {
        return NAME;
    }

}
