import { NativeModules } from 'react-native';
import { EventHandlingHelper } from "react-native-common-utils";

const openTok = NativeModules.OpenTokSdk;

export default class Session {
   static async create(apiKey, sessionId, connectionEventsSuppressed = false) {
      const session = new Session();
      
      session._id = sessionId;
      
      session._eventHandlingHelper = new EventHandlingHelper({
         object: session,
         nativeModule: openTok,
         eventGroups: [ "session", "signal" ],
         innerListener: session._innerListener
      });
      
      await openTok.createSession(apiKey, sessionId, connectionEventsSuppressed);
      
      return session;
   }
   
   async connect(token) {
      await openTok.connectSession(this.getId(), token);
   }
   
   async disconnect() {
      await openTok.disconnectSession(this.getId());
   }
   
   async publish(publisher) {
      await openTok.publishToSession(this.getId(), {
         name: publisher.getName(),
         style: publisher.getStyle()
      });
   }
   
   async unpublish(publisher) {
      await openTok.unpublishFromSession(this.getId(), publisher.getName());
   }
   
   async subscribe(subscriber) {
      await openTok.subscribeToSession(this.getId(), {
         streamId: subscriber.getStreamId(),
         style: subscriber.getStyle()
      });
   }
   
   async unsubscribe(subscriber) {
      await openTok.unsubscribeFromSession(this.getId(), subscriber.getStreamId());
   }
   
   async sendSignal(type, data, retryAfterReconnect) {
      await openTok.sendSignal(this.getId(), type, data, retryAfterReconnect);
   }
   
   getId() {
      return this._id;
   }
   
   removeAllListeners() {
      this._eventHandlingHelper.removeListeners();
      this._eventHandlingHelper.removeInnerListeners();
   }
   
   _innerListener(data) {
      if (data.sessionId.valueOf() == this.getId()) {
         this._eventHandlingHelper.invokeListeners(data);
      }
   }
}
