package ru.rshalimov.reactnative.opentoksdk;

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
   
   @ReactProp(name = "publisherId")
   public void setPublisherId(FrameLayout view, ReadableMap publisherId) {
      view.addView(OpentokSdkModule.getInstance().getSessionData(publisherId.getString("sessionId")).getPublisher(publisherId.getString("publisherName")).getView());
   }
   
   @Override
   protected FrameLayout createViewInstance(ThemedReactContext context) {
      return new FrameLayout(context);
   }
}
