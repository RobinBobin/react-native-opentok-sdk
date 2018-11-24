import { NativeModules } from 'react-native';
import { autobind } from "core-decorators";

const openTok = NativeModules.OpenTokSdk;

@autobind
export default class Publisher {
   static Builder = class {
      static style = openTok.publisher.style;
      
      constructor(sessionId) {
         this._sessionId = sessionId;
         
         this.setName();
      }
      
      setName(name) {
         this._name = name || "publisher";
         
         return this;
      }
      
      setStyle(style) {
         this._style = style;
         
         return this;
      }
      
      build() {
         return new Publisher(this);
      }
   }
   
   constructor(builder) {
      this._builder = builder;
   }
   
   cycleCamera() {
      openTok.cycleCamera(this.getSessionId(), this.getName());
   }
   
   getSessionId() {
      return this._builder._sessionId;
   }
   
   getName() {
      return this._builder._name;
   }
   
   getStyle() {
      return this._builder._style;
   }
}
