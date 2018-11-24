package ru.rshalimov.reactnative.opentoksdk.listeners;

import android.util.Log;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.WritableMap;
import com.opentok.android.Session;
import com.opentok.android.Connection;

import ru.rshalimov.reactnative.opentoksdk.OpenTokSdkModule;

public class SignalListener implements Session.SignalListener {
   public static final String SIGNAL_RECEIVED = "SIGNAL_RECEIVED";
   
   private static final String TAG = "SignalListener";
   
   @Override
   public void onSignalReceived(
      Session session,
      String type,
      String data,
      Connection connection)
   {
      final String sessionId = session.getSessionId();
      final String connectionId = connection == null ? null : connection.getConnectionId();
      
      Log.d(TAG, String.format("onSignalReceived(%s, %s, %s, %s).", sessionId, type, data, connectionId));
      
      final WritableMap params = Arguments.createMap();
      
      params.putString("sessionId", session.getSessionId());
      params.putString("type", type);
      params.putString("data", data);
      params.putString("connectionId", connectionId);
      
      OpenTokSdkModule.getInstance().emit(SIGNAL_RECEIVED, params);
   }
}
