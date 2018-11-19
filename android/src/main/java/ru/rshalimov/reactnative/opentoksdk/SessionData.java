package ru.rshalimov.reactnative.opentoksdk;

import java.util.Map;
import java.util.HashMap;

import com.opentok.android.PublisherKit;
import com.opentok.android.SubscriberKit;
import com.opentok.android.Session;

public class SessionData {
   public final Session session;
   
   private final Map <String, PublisherKit> publishers = new HashMap <> ();
   private final Map <String, SubscriberKit> subscribers = new HashMap <> ();
   
   SessionData(Session session) {
      this.session = session;
   }
   
   PublisherKit getPublisher(String name) {
      final PublisherKit publisher = publishers.get(name);
      
      if (publisher == null) {
         throw new IllegalArgumentException(String.format("No publisher with name \"%s\".", name));
      }
      
      return publisher;
   }
   
   SubscriberKit getSubscriber(String streamId) {
      final SubscriberKit subscriber = subscribers.get(streamId);
      
      if (subscriber == null) {
         throw new IllegalArgumentException(String.format("No subscriber for stream id \"%s\".", streamId));
      }
      
      return subscriber;
   }
   
   void publish(PublisherKit publisher) {
      publishers.put(publisher.getName(), publisher);
      
      session.publish(publisher);
   }
   
   void unpublish() {
      for (final PublisherKit publisher : publishers.values()) {
         session.unpublish(publisher);
         
         publisher.destroy();
      }
      
      publishers.clear();
   }
   
   void unpublish(String name) {
      final PublisherKit publisher = getPublisher(name);
      
      session.unpublish(publisher);
      
      publisher.destroy();
      
      publishers.remove(name);
   }
   
   void subscribe(SubscriberKit subscriber) {
      subscribers.put(subscriber.getStream().getStreamId(), subscriber);
      
      session.subscribe(subscriber);
   }
   
   void unsubscribe() {
      for (final SubscriberKit subscriber : subscribers.values()) {
         session.unsubscribe(subscriber);
         
         subscriber.destroy();
      }
      
      subscribers.clear();
   }
   
   void unsubscribe(String streamId) {
      final SubscriberKit subscriber = getSubscriber(streamId);
      
      session.unsubscribe(subscriber);
      
      subscriber.destroy();
      
      subscribers.remove(streamId);
   }
}
