import { NativeModules } from 'react-native';
import { EventHandlingHelper } from "react-native-common-utils";

const openTok = NativeModules.OpentokSdk;

export default class Session {
   static async create(apiKey, sessionId, connectionEventsSuppressed = false) {
      const session = new Session();
      
      session._id = sessionId;
      
      session._eventHandlingHelper = new EventHandlingHelper({
         object: session,
         nativeModule: openTok,
         eventGroups: [ "session" ],
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
   
   getId() {
      return this._id;
   }
   
   removeAllListeners() {
      this._eventHandlingHelper.removeListeners();
      this._eventHandlingHelper.removeInnerListeners();
   }
   
   _innerListener(data) {
      if (data.id.valueOf() == this.getId()) {
         this._eventHandlingHelper.invokeListeners(data);
      }
   }
}
