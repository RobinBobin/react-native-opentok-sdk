import { NativeModules } from 'react-native';

const openTok = NativeModules.OpentokSdk;

export default class Publisher {
   constructor(sessionId, name) {
      this._sessionId = sessionId;
      this._name = name || "publisher";
   }
   
   async publish() {
      await openTok.publishToSession(this.getSessionId(), {
         name: this.getName()
      });
   }
   
   async unpublish() {
      await openTok.unpublishFromSession(this.getSessionId(), this.getName());
   }
   
   getSessionId() {
      return this._sessionId;
   }
   
   getName() {
      return this._name;
   }
}
