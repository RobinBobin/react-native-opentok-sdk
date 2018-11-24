export default class Subscriber {
   constructor(sessionId, streamId) {
      this._sessionId = sessionId;
      this._streamId = streamId;
   }
   
   getSessionId() {
      return this._sessionId;
   }
   
   getStreamId() {
      return this._streamId;
   }
}
