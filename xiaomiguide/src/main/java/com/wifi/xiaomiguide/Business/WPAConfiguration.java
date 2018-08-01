package com.wifi.xiaomiguide.Business;

import android.net.wifi.WifiConfiguration;
import android.util.Log;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class WPAConfiguration {

    private static final String INT_PRIVATE_KEY = "private_key";
    private static final String INT_PHASE2 = "phase2";
    private static final String INT_PASSWORD = "password";
    private static final String INT_IDENTITY = "identity";
    private static final String INT_EAP = "eap";
    private static final String INT_CLIENT_CERT = "client_cert";
    private static final String INT_CA_CERT = "ca_cert";
    private static final String INT_ANONYMOUS_IDENTITY = "anonymous_identity";
    private static final String INT_ENTERPRISEFIELD_NAME = "android.net.wifi.WifiConfiguration$EnterpriseField";
    private static final String INT_IPASSIGNMENT_NAME = "android.net.wifi.WifiConfiguration$IpAssignment";
    private static final String INT_PROXYSETTINGS_NAME = "android.net.wifi.WifiConfiguration$ProxySettings";
    //change this to TLS,PEAP, or other options that are listed when you connect via a device
    private static final String ENTERPRISE_EAP = "PEAP";
    private static final String INT_IP_ASSIGNMENT = "ipAssignment";
    private static final String INT_PROXY_SETTINGS = "proxySettings";


    public static WifiConfiguration setWifiConfigurations(String SSID, String userName, String userPass, WifiConfiguration wifiConfig) {
       // WifiConfiguration wifiConfig =new WifiConfiguration();
        wifiConfig.SSID = "\"" + SSID + "\"";

        /*Access Point*/
//        wifiConfig.SSID = SSID;
        wifiConfig.networkId = 0;

        /*Priority*/
        wifiConfig.priority = 0;

        /*Enable Hidden SSID's*/
        wifiConfig.hiddenSSID = false;

        /*Key Management*/
        wifiConfig.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.IEEE8021X);
        wifiConfig.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.WPA_EAP);

        /*Set Group Ciphers*/
        wifiConfig.allowedGroupCiphers.clear();
        wifiConfig.allowedGroupCiphers.clear();
        wifiConfig.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP);
        wifiConfig.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP);
        wifiConfig.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.WEP104);
        wifiConfig.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.WEP40);


         /*Set Pairwise Ciphers*/
        wifiConfig.allowedPairwiseCiphers.clear();
        wifiConfig.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.CCMP);
        wifiConfig.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.TKIP);

        /*Set Protocols*/
        wifiConfig.allowedProtocols.clear();
        wifiConfig.allowedProtocols.set(WifiConfiguration.Protocol.RSN);
        wifiConfig.allowedProtocols.set(WifiConfiguration.Protocol.WPA);


        /*Set Enterprise Settings Using Reflection*/
        try {
            Class<?> wifiEnterpriseField = null;
            Class<?> wifiIpAssignmentField = null;
            Class<?> wifiProxySettingsField = null;

            boolean enterpriseFieldType = true;
            boolean ipAssignmentFieldType = true;
            boolean proxySettingsFieldType = true;

            Field anonymousId = null, caCert = null, clientCert = null, eap = null, identity = null, password = null, phase2 = null, privateKey = null, ipAssignment = null, proxy = null;

            Method setValue = null;

            Class<?>[] wifiClasses = WifiConfiguration.class.getClasses();

            /*Get Enterprise/IP Assignment/Proxy Setting Field Class to Modify*/
            for(Class<?> wifiClass : wifiClasses) {
                if(wifiClass.getName().equals(INT_ENTERPRISEFIELD_NAME)) {
                    wifiEnterpriseField = wifiClass;
                } else if(wifiClass.getName().equals(INT_IPASSIGNMENT_NAME)) {
                    wifiIpAssignmentField = wifiClass;
                } else if(wifiClass.getName().equals(INT_PROXYSETTINGS_NAME)) {
                    wifiProxySettingsField = wifiClass;
                }
            }

            /*Certain OS (Cupcake & Doughnut) access the enterprise field directly*/
            if(wifiEnterpriseField == null) {
                enterpriseFieldType = false;
            }
            if(wifiIpAssignmentField == null) {
                ipAssignmentFieldType = false;
            }
            if(wifiProxySettingsField == null) {
                proxySettingsFieldType = false;
            }

            /*Get Fields*/
            Log.d("Enterprise Setting", "Getting Fields ");
            Field[] wifiFields = WifiConfiguration.class.getFields();
            for(Field wifiField : wifiFields) {
                if(wifiField.getName().equals(INT_ANONYMOUS_IDENTITY)) {
                    anonymousId = wifiField;
                    Log.d("Enterprise Setting", "Getting Field: " + wifiField);
                } else if(wifiField.getName().equals(INT_CA_CERT)) {
                    caCert = wifiField;
                    Log.d("Enterprise Setting", "Getting Field: " + wifiField);
                } else if(wifiField.getName().equals(INT_CLIENT_CERT)) {
                    clientCert = wifiField;
                    Log.d("Enterprise Setting", "Getting Field: " + wifiField);
                } else if(wifiField.getName().equals(INT_EAP)) {
                    eap = wifiField;
                    Log.d("Enterprise Setting", "Getting Field: " + wifiField);
                } else if(wifiField.getName().equals(INT_IDENTITY)) {
                    identity = wifiField;
                    Log.d("Enterprise Setting", "Getting Field: " + wifiField);
                } else if(wifiField.getName().equals(INT_PASSWORD)) {
                    password = wifiField;
                    Log.d("Enterprise Setting", "Getting Field: " + wifiField);
                } else if(wifiField.getName().equals(INT_PHASE2)) {
                    phase2 = wifiField;
                    Log.d("Enterprise Setting", "Getting Field: " + wifiField);
                } else if(wifiField.getName().equals(INT_PRIVATE_KEY)) {
                    privateKey = wifiField;
                    Log.d("Enterprise Setting", "Getting Field: " + wifiField);
                } else if(wifiField.getName().equals(INT_IP_ASSIGNMENT)) {
                    ipAssignment = wifiField;
                    Log.d("Enterprise Setting", "Getting Field: " + wifiField);
                } else if(wifiField.getName().equals(INT_PROXY_SETTINGS)) {
                    proxy = wifiField;
                    Log.d("Enterprise Setting", "Getting Field: " + wifiField);
                }

            }

            /*Get method to set value of enterprise fields*/
            if(enterpriseFieldType) {
                for(Method method : wifiEnterpriseField.getMethods()) {
                    Log.d("Get Methods", "Enterprise Method: " + method);
                    if(method.getName().trim().equals("setValue")) {
                        setValue = method;
                        break;
                    }
                }
            }

            /*Set EAP*/
            if(enterpriseFieldType) {
                setValue.invoke(eap.get(wifiConfig), ENTERPRISE_EAP);
                Log.d("Enterprise Setting", "Setting " + ENTERPRISE_EAP);
            } else {
                eap.set(wifiConfig, ENTERPRISE_EAP);
            }

            /*Set Identity*/
            if(enterpriseFieldType) {
                setValue.invoke(identity.get(wifiConfig), userName);
                Log.d("Enterprise Setting", "Setting " + userName);
            } else {
                identity.set(wifiConfig, userName);
            }

            /*Set user password*/
            if(enterpriseFieldType) {
                setValue.invoke(password.get(wifiConfig), userPass);
                Log.d("Enterprise Setting", "Setting " + userPass);
            } else {
                password.set(wifiConfig, userPass);
            }

            /*Set IP Protocol*/
            if(ipAssignmentFieldType) {
                /*Change the literal string in here to change the value of obtaining and IP address*/
                ipAssignment.set(wifiConfig, Enum.valueOf((Class<Enum>) ipAssignment.getType().asSubclass(Enum.class), "DHCP"));
                Log.d("Enterprise Setting", "Setting " + ipAssignment);
            } else {
                ipAssignment.set(wifiConfig, "DHCP");
            }

            /*Set Proxy Protocol*/
            if(proxySettingsFieldType) {
                /*Change the literal string in here to change the value of proxy settingss*/
                proxy.set(wifiConfig, Enum.valueOf((Class<Enum>) proxy.getType().asSubclass(Enum.class), "NONE"));
                Log.d("Enterprise Setting", "Setting " + proxy);
            } else {
                proxy.set(wifiConfig, "NONE");
            }
        } catch(Exception e) {
            Log.e("ERROR!!!", "Error: " + e);

        }
        Log.d("WIFI", "Configuration Settings " + wifiConfig.toString());
        return wifiConfig;
    }
}
