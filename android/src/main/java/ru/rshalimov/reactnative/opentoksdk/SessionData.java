package ru.rshalimov.reactnative.opentoksdk;

import java.lang.ref.WeakReference;
import java.util.Map;
import java.util.HashMap;

import com.opentok.android.Publisher;
import com.opentok.android.Session;
import com.opentok.android.Stream;
import com.opentok.android.Subscriber;

public class SessionData {
   final Session session;
   
   private final Map <String, Publisher> publishers = new HashMap <> ();
   private final Map <String, Subscriber> subscribers = new HashMap <> ();
   private final Map <String, WeakReference <Stream>> streams = new HashMap <> ();
   
   SessionData(Session session) {
      this.session = session;
   }
   
   public Publisher getPublisher(String name) {
      final Publisher publisher = publishers.get(name);
      
      if (publisher == null) {
         throw new IllegalArgumentException(String.format("No publisher with name \"%s\".", name));
      }
      
      return publisher;
   }
   
   public Subscriber getSubscriber(String streamId) {
      final Subscriber subscriber = subscribers.get(streamId);
      
      if (subscriber == null) {
         throw new IllegalArgumentException(String.format("No subscriber for stream id \"%s\".", streamId));
      }
      
      return subscriber;
   }
   
   public void addStream(Stream stream) {
      streams.put(stream.getStreamId(), new WeakReference <> (stream));
   }
   
   public void removeStream(String streamId) {
      streams.remove(streamId);
   }
   
   void publish(Publisher publisher) {
      publishers.put(publisher.getName(), publisher);
      
      session.publish(publisher);
   }
   
   void unpublish() {
      for (final Publisher publisher : publishers.values()) {
         session.unpublish(publisher);
         
         publisher.destroy();
      }
      
      publishers.clear();
   }
   
   void unpublish(String name) {
      final Publisher publisher = getPublisher(name);
      
      session.unpublish(publisher);
      
      publisher.destroy();
      
      publishers.remove(name);
   }
   
   Stream getStream(String streamId) {
      final Stream stream = streams.get(streamId).get();
      
      if (stream == null) {
         throw new IllegalArgumentException(String.format("No stream for stream id \"%s\".", streamId));
      }
      
      return stream;
   }
   
   void subscribe(Subscriber subscriber) {
      subscribers.put(subscriber.getStream().getStreamId(), subscriber);
      
      session.subscribe(subscriber);
   }
   
   void unsubscribe() {
      for (final Subscriber subscriber : subscribers.values()) {
         if (subscriber.getStream() != null) {
            session.unsubscribe(subscriber);
         }
         
         subscriber.destroy();
      }
      
      subscribers.clear();
   }
   
   void unsubscribe(String streamId) {
      final Subscriber subscriber = getSubscriber(streamId);
      
      if (subscriber.getStream() != null) {
         session.unsubscribe(subscriber);
      }
      
      subscriber.destroy();
      
      subscribers.remove(streamId);
   }
}
