package ru.rshalimov.reactnative.opentoksdk;

import android.util.Log;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;
import com.opentok.android.Publisher;
import com.opentok.android.PublisherKit;
import com.opentok.android.Session;

import java.util.Map;
import java.util.HashMap;

import ru.rshalimov.reactnative.common.BaseModule;
import ru.rshalimov.reactnative.common.MapBuilder;
import ru.rshalimov.reactnative.opentoksdk.SessionData;
import ru.rshalimov.reactnative.opentoksdk.listeners.SessionListener;
import ru.rshalimov.reactnative.opentoksdk.listeners.PublisherListener;

public class OpentokSdkModule extends BaseModule {
   private static final String TAG = "OpentokSdk";
   
   private static OpentokSdkModule instance;
   
   private final Map <String, SessionData> sessionData = new HashMap <> ();
   private final SessionListener sessionListener = new SessionListener();
   private final PublisherListener publisherListener = new PublisherListener();
   
   OpentokSdkModule(ReactApplicationContext reactContext) {
      super(reactContext);
      
      OpentokSdkModule.instance = this;
   }
   
   @Override
   public String getName() {
      return TAG;
   }
   
   @Override
   public Map <String, Object> getConstants() {
      return new MapBuilder()
         .push("events")
            .push("session")
               .put(SessionListener.SESSION_CONNECTED)
               .put(SessionListener.SESSION_DISCONNECTED)
               .put(SessionListener.SESSION_ERROR)
               .put(SessionListener.SESSION_STREAM_DROPPED)
               .put(SessionListener.SESSION_STREAM_RECEIVED)
            .pop()
            .push("publisher")
               .put(PublisherListener.PUBLISHER_ERROR)
               .put(PublisherListener.PUBLISHER_STREAM_CREATED)
               .put(PublisherListener.PUBLISHER_STREAM_DESTROYED)
            .pop()
         .build();
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
      
      session.setSessionListener(sessionListener);
      
      sessionData.put(session.getSessionId(), new SessionData(session));
      
      promise.resolve(null);
   }
   
   @ReactMethod
   public void connectSession(String sessionId, String token, Promise promise) {
      try {
         getSessionData(sessionId).session.connect(token);
         
         promise.resolve(null);
      } catch (IllegalArgumentException e) {
         promise.reject("", e);
      }
   }
   
   @ReactMethod
   public void disconnectSession(String sessionId, Promise promise) {
      try {
         final SessionData sessionData = getSessionData(sessionId);
         
         sessionData.unpublish();
         sessionData.unsubscribe();
         sessionData.session.disconnect();
         
         this.sessionData.remove(sessionId);
         
         promise.resolve(null);
      } catch (IllegalArgumentException e) {
         promise.reject("", e);
      }
   }
   
   @ReactMethod
   public void publishToSession(
      String sessionId,
      ReadableMap params,
      Promise promise)
   {
      try {
         Log.d(TAG, String.format("publishToSession(%s, %s)", sessionId, params.toHashMap()));
         
         final Publisher.Builder builder = new Publisher.Builder(getReactApplicationContext());
         
         builder.name(params.getString("name"));
         
         final Publisher publisher = builder.build();
         
         publisher.setPublisherListener(publisherListener);
         
         getSessionData(sessionId).publish(publisher);
         
         promise.resolve(null);
      } catch (IllegalArgumentException e) {
         promise.reject("", e);
      }
   }
   
   @ReactMethod
   public void unpublishFromSession(
      String sessionId,
      String publisherName,
      Promise promise)
   {
      try {
         Log.d(TAG, String.format("unpublishFromSession(%s, %s)", sessionId, publisherName));
         
         getSessionData(sessionId).unpublish(publisherName);
         
         promise.resolve(null);
      } catch (IllegalArgumentException e) {
         promise.reject("", e);
      }
   }
   
   public SessionData getSessionData(String sessionId) {
      final SessionData sessionData = this.sessionData.get(sessionId);
      
      if (sessionData == null) {
         throw new IllegalArgumentException(String.format("Invalid sessionId: %s.", sessionId));
      }
      
      return sessionData;
   }
   
   public static void onPause() {
      if (instance != null) {
         for (final SessionData data : instance.sessionData.values()) {
            Log.d(TAG, String.format("Session %s paused.", data.session.getSessionId()));
            
            data.session.onPause();
         }
      }
   }
   
   public static void onResume() {
      if (instance != null) {
         for (final SessionData data : instance.sessionData.values()) {
            Log.d(TAG, String.format("Session %s resumed.", data.session.getSessionId()));
            
            data.session.onResume();
         }
      }
   }
   
   public static OpentokSdkModule getInstance() {
      return instance;
   }
}