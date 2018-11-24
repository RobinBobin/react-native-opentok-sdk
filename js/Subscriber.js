import { NativeModules } from 'react-native';
import { autobind } from "core-decorators";

const openTok = NativeModules.OpenTokSdk;

@autobind
export default class Subscriber {
   static style = openTok.publisher.style;
   
   constructor(sessionId, streamId, style) {
      this._sessionId = sessionId;
      this._streamId = streamId;
      this._style = style;
   }
   
   getSessionId() {
      return this._sessionId;
   }
   
   getStreamId() {
      return this._streamId;
   }
   
   getStyle() {
      return this._style;
   }
}
