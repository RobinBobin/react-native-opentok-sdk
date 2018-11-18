package ru.rshalimov.reactnative.opentoksdk;

import android.util.Log;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;
import com.opentok.android.Session;

import java.util.Map;
import java.util.HashMap;

import ru.rshalimov.reactnative.common.BaseModule;

public class OpenTokSdkModule extends BaseModule {
   private static final String TAG = "OpenTokSdk";
   private static final Map <String, Session> sessions = new HashMap <> ();
   
   private static OpenTokSdkModule instance;
   
   OpenTokSdkModule(ReactApplicationContext reactContext) {
      super(reactContext);
      
      OpenTokSdkModule.instance = this;
   }
   
   @Override
   public String getName() {
      return TAG;
   }
   
   @ReactMethod
   public void createSession(
      String apiKey,
      String sessionId,
      Boolean connectionEventsSuppressed,
      Promise promise)
   {
      final Session session = new Session.Builder(
         getReactApplicationContext(),
         apiKey,
         sessionId)
         .connectionEventsSuppressed(connectionEventsSuppressed)
         .build();
         
         sessions.put(session.getSessionId(), session);
         
         promise.resolve(null);
   }
   
   public static void onPause() {
      for (final Session session : sessions.values()) {
         session.onPause();
      }
   }
   
   public static void onResume() {
      for (final Session session : sessions.values()) {
         session.onResume();
      }
   }
   
   public static OpenTokSdkModule getInstance() {
      return instance;
   }
}
