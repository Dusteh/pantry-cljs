(ns pantry.react-requires
  (:require [reagent.core :as r]))
  

    ; react-native
(set! js/ReactNative (js/require "react-native"))
(defonce Modal (.-Modal js/ReactNative))
(defonce Animated ^js/ReactNative.Animated (.-Animated js/ReactNative))
(defonce AnimatedValue ^js/ReactNative.Animated.Value (.-Value Animated))
(defonce AnimatedImage (aget Animated "Image"))
(defonce Dimensions (aget js/ReactNative "Dimensions"))
(defonce ListView (aget js/ReactNative "ListView"))
(defonce DataSource (aget ListView "DataSource"))
(defonce Image (aget js/ReactNative "Image"))
(defonce AppRegistry (aget js/ReactNative "AppRegistry"))
(defonce InteractionManager (aget js/ReactNative "InteractionManager"))
(defonce Platform (aget js/ReactNative "Platform"))
(defonce Linking (aget js/ReactNative "Linking"))
(defonce LayoutAnimation (aget js/ReactNative "LayoutAnimation"))
(defonce Keyboard (aget js/ReactNative "Keyboard"))
(defonce KeyboardAvoidingView (aget js/ReactNative "KeyboardAvoidingView"))
(defonce Picker (aget js/ReactNative "Picker"))
(defonce PickerItem (aget Picker "Item"))
(defonce Text (aget js/ReactNative "Text"))
(defonce TextInput (aget js/ReactNative "TextInput"))
(defonce Button (aget js/ReactNative "Button"))
(defonce View (aget js/ReactNative "View"))
(defonce ScrollView (aget js/ReactNative "ScrollView"))
(defonce Image (aget js/ReactNative "Image"))
(defonce AnimatedImage (aget js/ReactNative "AnimatedImage"))
(defonce TouchableOpacity (aget js/ReactNative "TouchableOpacity"))
(defonce TouchableHighlight (aget js/ReactNative "TouchableHighlight"))
(defonce AsyncStorage (aget js/ReactNative "AsyncStorage"))
(defonce ListView (aget js/ReactNative "ListView"))
(defonce TouchableOpacity (aget js/ReactNative "TouchableOpacity"))
(defonce Alert (aget js/ReactNative "Alert"))
(defonce AppState ^js/ReactNative.AppState (.-AppState js/ReactNative))
(defonce ActivityIndicator (aget js/ReactNative "ActivityIndicator"))
(defonce VirtualizedList (.-VirtualizedList js/ReactNative))
(defonce DatePickerIOS (aget js/ReactNative "DatePickerIOS"))
(defonce DatePickerAndroid (aget js/ReactNative "DatePickerAndroid"))
(defonce FlatList (aget js/ReactNative "FlatList"))

; react-native-vector-icons
(defonce vIcon (js->clj (js/require "react-native-vector-icons/MaterialIcons")))
(defonce Icon (get vIcon "default"))

; react-navigation
(def ReactNavigation (js/require "react-navigation"))
(def DrawerActions (.-DrawerActions ReactNavigation))
(def StackNav (.-createStackNavigator ReactNavigation))
(def StackActions (.-StackActions ReactNavigation))
(def DrawerNav (.-createDrawerNavigator ReactNavigation))
(def StackScreen (.-StackScreen ReactNavigation))

; react-native-popup-menu
(defonce ReactNativePopupMenu (js/require "react-native-popup-menu"))
(defonce MenuProvider (.-MenuProvider ReactNativePopupMenu))
(defonce Menu (r/adapt-react-class (.-Menu ReactNativePopupMenu)))
(defonce MenuOptions (r/adapt-react-class (.-MenuOptions ReactNativePopupMenu)))
(defonce MenuOption (r/adapt-react-class (.-MenuOption ReactNativePopupMenu)))
(defonce MenuTrigger (r/adapt-react-class (.-MenuTrigger ReactNativePopupMenu)))

; react-native-popup-dialog
(defonce ReactNativePopupDialog (js/require "react-native_popup-dialog"))
(defonce PopupDialog (.-PopupDialog ReactNativePopupDialog))

(defn log 
  [msg]
  (.log js/console msg))
  
  
