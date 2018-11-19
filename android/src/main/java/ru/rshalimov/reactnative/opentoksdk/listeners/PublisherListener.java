package ru.rshalimov.reactnative.opentoksdk.listeners;

// import android.util.Log;

// import com.facebook.react.bridge.Arguments;
// import com.facebook.react.bridge.WritableMap;
import com.opentok.android.OpentokError;
import com.opentok.android.PublisherKit;
import com.opentok.android.Stream;

// import ru.rshalimov.reactnative.opentoksdk.OpentokSdkModule;

public class PublisherListener implements PublisherKit.PublisherListener {
   public static final String PUBLISHER_ERROR;
   public static final String PUBLISHER_STREAM_CREATED;
   public static final String PUBLISHER_STREAM_DESTROYED;
   
   private static final String TAG = "PublisherListener";
   
   static {
      PUBLISHER_ERROR = "PUBLISHER_ERROR";
      PUBLISHER_STREAM_CREATED = "PUBLISHER_STREAM_CREATED";
      PUBLISHER_STREAM_DESTROYED = "PUBLISHER_STREAM_DESTROYED";
   }
   
   @Override
   public void onError(PublisherKit publisher, OpentokError error) {
      // TODO streamId == null?
      
      // final String sessionId = publisher.getSession().getSessionId();
      // final String streamId = publisher.getStream().getStreamId();
      // final String errorCode = error.getErrorCode().toString();
      // final String domain = error.getErrorDomain().toString();
      // final String message = error.getMessage();
      
      // Log.d(TAG, String.format("onError(%s, %s, %s, %s, %s).", sessionId, streamId, errorCode, domain, message));
      
      // final WritableMap params = Arguments.createMap();
      
      // params.putString("sessionId", sessionId);
      // params.putString("streamId", streamId);
      // params.putString("errorCode", errorCode);
      // params.putString("domain", domain);
      // params.putString("message", message);
      
      // OpentokSdkModule.getInstance().emit(PUBLISHER_ERROR, params);
   }
   
   @Override
   public void onStreamCreated(PublisherKit publisher, Stream stream) {
      // final String sessionId = publisher.getSession().getSessionId();
      // final String streamId = stream.getStreamId();
      
      // Log.d(TAG, String.format("onStreamCreated(%s, %s)", sessionId, streamId));
      
      // final WritableMap params = Arguments.createMap();
      
      // params.putString("sessionId", sessionId);
      // params.putString("streamId", streamId);
      
      // OpentokSdkModule.getInstance().emit(PUBLISHER_STREAM_CREATED, params);
   }
   
   @Override
   public void onStreamDestroyed(PublisherKit publisher, Stream stream) {
      // TODO getSession() is null.
      
      // final String sessionId = publisher.getSession().getSessionId();
      // final String streamId = stream.getStreamId();
      
      // Log.d(TAG, String.format("onStreamDestroyed(%s, %s)", sessionId, streamId));
      
      // final WritableMap params = Arguments.createMap();
      
      // params.putString("sessionId", sessionId);
      // params.putString("streamId", streamId);
         
      // OpentokSdkModule.getInstance().emit(PUBLISHER_STREAM_DESTROYED, params);
   }
}
