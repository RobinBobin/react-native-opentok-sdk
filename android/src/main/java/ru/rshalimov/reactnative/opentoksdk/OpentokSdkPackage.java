package ru.rshalimov.reactnative.opentoksdk;

import java.util.Arrays;
import java.util.List;

import com.facebook.react.ReactPackage;
import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.uimanager.ViewManager;

public class OpentokSdkPackage implements ReactPackage {
   @Override
   public List <ViewManager> createViewManagers(
      ReactApplicationContext reactContext)
   {
      return Arrays. <ViewManager> asList(new StreamViewManager());
   }
   
   @Override
   public List <NativeModule> createNativeModules(
      ReactApplicationContext reactContext)
   {
      return Arrays. <NativeModule> asList(new OpentokSdkModule(reactContext));
   }
}
