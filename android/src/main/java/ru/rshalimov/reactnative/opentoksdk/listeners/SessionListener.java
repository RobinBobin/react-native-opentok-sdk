package ru.rshalimov.reactnative.opentoksdk.listeners;

import android.util.Log;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.WritableMap;
import com.opentok.android.OpentokError;
import com.opentok.android.Session;
import com.opentok.android.Stream;

import ru.rshalimov.reactnative.opentoksdk.OpentokSdkModule;

public class SessionListener implements Session.SessionListener {
   public static final String SESSION_CONNECTED = "SESSION_CONNECTED";
   public static final String SESSION_DISCONNECTED = "SESSION_DISCONNECTED";
   public static final String SESSION_ERROR = "SESSION_ERROR";
   public static final String SESSION_STREAM_DROPPED = "SESSION_STREAM_DROPPED";
   public static final String SESSION_STREAM_RECEIVED = "SESSION_STREAM_RECEIVED";
   
   private static final String TAG = "SessionListener";
   
   @Override
   public void onConnected(Session session) {
      Log.d(TAG, String.format("onConnected(%s).", session.getSessionId()));
      
      final WritableMap params = Arguments.createMap();
      
      params.putString("id", session.getSessionId());
      
      OpentokSdkModule.getInstance().emit(SESSION_CONNECTED, params);
   }
   
   @Override
   public void onDisconnected(Session session) {
      Log.d(TAG, String.format("onDisconnected(%s).", session.getSessionId()));
      
      final WritableMap params = Arguments.createMap();
      
      params.putString("id", session.getSessionId());
      
      OpentokSdkModule.getInstance().emit(SESSION_DISCONNECTED, params);
   }
   
   @Override
   public void onError(Session session, OpentokError error) {
      final String sessionId = session.getSessionId();
      final String errorCode = error.getErrorCode().toString();
      final String domain = error.getErrorDomain().toString();
      final String message = error.getMessage();
      
      Log.d(TAG, String.format("onError(%s, %s, %s, %s).", sessionId, errorCode, domain, message));
      
      final WritableMap params = Arguments.createMap();
      
      params.putString("id", sessionId);
      params.putString("errorCode", errorCode);
      params.putString("domain", domain);
      params.putString("message", message);
      
      OpentokSdkModule.getInstance().emit(SESSION_ERROR, params);
   }
   
   @Override
   public void onStreamDropped(Session session, Stream stream) {
      final String sessionId = session.getSessionId();
      final String streamId = stream.getStreamId();
      
      Log.d(TAG, String.format("onStreamDropped(%s, %s)", sessionId, streamId));
      
      final WritableMap params = Arguments.createMap();
      
      params.putString("id", sessionId);
      params.putString("streamId", streamId);
      
      OpentokSdkModule.getInstance().emit(SESSION_STREAM_DROPPED, params);
   }
   
   @Override
   public void onStreamReceived(Session session, Stream stream) {
      final String sessionId = session.getSessionId();
      final String streamId = stream.getStreamId();
      
      Log.d(TAG, String.format("onStreamReceived(%s, %s)", sessionId, streamId));
      
      final WritableMap params = Arguments.createMap();
      
      params.putString("id", sessionId);
      params.putString("streamId", streamId);
      
      OpentokSdkModule.getInstance().emit(SESSION_STREAM_RECEIVED, params);
   }
}
