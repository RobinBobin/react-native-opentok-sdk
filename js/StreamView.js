import React from "react";
import { requireNativeComponent } from "react-native";

const RCTStreamView = requireNativeComponent(
   "RCTStreamView",
   StreamView);

export default class StreamView extends React.Component {
   render() {
      return <RCTStreamView { ...this.props } />;
   }
}
