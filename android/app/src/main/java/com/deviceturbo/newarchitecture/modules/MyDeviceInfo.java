package com.deviceturbo.newarchitecture.modules;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;

import androidx.annotation.NonNull;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.Promise;

import com.deviceturbo.codegen.NativeMyDeviceInfoSpec;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class MyDeviceInfo extends NativeMyDeviceInfoSpec {
    public MyDeviceInfo(ReactApplicationContext ct) {
        super(ct);
    }

    public final static String NAME = "MyDeviceInfo";
    @Override
    public String getIpAddress() {

        try {
            return
                    InetAddress.getByAddress(
                            ByteBuffer
                                    .allocate(4)
                                    .order(ByteOrder.LITTLE_ENDIAN)
                                    .putInt(getWifiInfo().getIpAddress())
                                    .array())
                            .getHostAddress();
        } catch (Exception e) {
            return "unknown";
        }

    }

    @SuppressWarnings("MissingPermission")
    private WifiInfo getWifiInfo() {
        WifiManager manager = (WifiManager) getReactApplicationContext().getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        if (manager != null) {
            return manager.getConnectionInfo();
        }
        return null;
    }

    @SuppressLint({"HardwareIds", "MissingPermission"})
    @Override
    public String getMacAddress() {

        WifiInfo wifiInfo = getWifiInfo();
        String macAddress = "";
        if (wifiInfo != null) {
            macAddress = wifiInfo.getMacAddress();
        }

        String permission = "android.permission.INTERNET";
        int res = getReactApplicationContext().checkCallingOrSelfPermission(permission);

        if (res == PackageManager.PERMISSION_GRANTED) {
            try {
                List<NetworkInterface> all = Collections.list(NetworkInterface.getNetworkInterfaces());
                for (NetworkInterface nif : all) {
                    if (!nif.getName().equalsIgnoreCase("wlan0")) continue;

                    byte[] macBytes = nif.getHardwareAddress();
                    if (macBytes == null) {
                        macAddress = "";
                    } else {

                        StringBuilder res1 = new StringBuilder();
                        for (byte b : macBytes) {
                            res1.append(String.format("%02X:",b));
                        }

                        if (res1.length() > 0) {
                            res1.deleteCharAt(res1.length() - 1);
                        }

                        macAddress = res1.toString();
                    }
                }
            } catch (Exception ex) {
                // do nothing
            }
        }

        return macAddress;
    }

    @Override
    public void isEmulator(Promise promise) {

        boolean isEmulatorSync = Build.FINGERPRINT.startsWith("generic")
                || Build.FINGERPRINT.startsWith("unknown")
                || Build.MODEL.contains("google_sdk")
                || Build.MODEL.toLowerCase(Locale.ROOT).contains("droid4x")
                || Build.MODEL.contains("Emulator")
                || Build.MODEL.contains("Android SDK built for x86")
                || Build.MANUFACTURER.contains("Genymotion")
                || Build.HARDWARE.contains("goldfish")
                || Build.HARDWARE.contains("ranchu")
                || Build.HARDWARE.contains("vbox86")
                || Build.PRODUCT.contains("sdk")
                || Build.PRODUCT.contains("google_sdk")
                || Build.PRODUCT.contains("sdk_google")
                || Build.PRODUCT.contains("sdk_x86")
                || Build.PRODUCT.contains("vbox86p")
                || Build.PRODUCT.contains("emulator")
                || Build.PRODUCT.contains("simulator")
                || Build.BOARD.toLowerCase(Locale.ROOT).contains("nox")
                || Build.BOOTLOADER.toLowerCase(Locale.ROOT).contains("nox")
                || Build.HARDWARE.toLowerCase(Locale.ROOT).contains("nox")
                || Build.PRODUCT.toLowerCase(Locale.ROOT).contains("nox")
                || Build.SERIAL.toLowerCase(Locale.ROOT).contains("nox")
                || (Build.BRAND.startsWith("generic") && Build.DEVICE.startsWith("generic"));
        
        promise.resolve(isEmulatorSync);
    }

    @NonNull
    @Override
    public String getName() {
        return NAME;
    }

}
