package ru.rshalimov.reactnative.opentoksdk;

import android.view.View;
import android.widget.FrameLayout;

import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.uimanager.SimpleViewManager;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.annotations.ReactProp;

class StreamViewManager extends SimpleViewManager <FrameLayout> {
   private static final String TAG = "RCTStreamView";
   
   @Override
   public String getName() {
      return TAG;
   }
   
   @ReactProp(name = "source")
   public void setSource(FrameLayout streamView, ReadableMap source) {
      final SessionData sessionData = OpenTokSdkModule.getInstance().getSessionData(source.getString("sessionId"));
      
      streamView.addView(source.hasKey("publisherName") ? sessionData.getPublisher(source.getString("publisherName")).getView() : sessionData.getSubscriber(source.getString("streamId")).getView());
   }
   
   @Override
   protected FrameLayout createViewInstance(ThemedReactContext context) {
      return new FrameLayout(context);
   }
}
