(ns pantry.screens.list
    (:require [pantry.events :as evts]
              [reagent.core :as r :refer [atom]]
              [re-frame.core :refer [subscribe dispatch dispatch-sync]]
              [pantry.subs :as subs]
              [pantry.components.list-item :as li]))

(def ReactNative (js/require "react-native"))
(def text (r/adapt-react-class (.-Text ReactNative)))
(def view (r/adapt-react-class (.-View ReactNative)))
(def image (r/adapt-react-class (.-Image ReactNative)))
(def button (r/adapt-react-class (.-Button ReactNative)))
(def touchable-highlight (r/adapt-react-class (.-TouchableHighlight ReactNative)))
(def style-sheet (r/adapt-react-class (.-StyleSheet ReactNative)))

; (def RNActionButton (js/require "react-native-action-button"))


(def hamburger (js/require "./images/hamburger.png"))

(def example-model
    {:title "Example Model"
     :data-provider li/example-list})

(defn display-list
    [model]
    (fn [{:keys [navigation] :as props}]
        (let [{:keys [navigate openDrawer]} navigation
              data-provider (:data-provider model)]
            [view {:style {:flex-direction "column"}}
                [view {:style {:flex-direction "row" :background-color "#dddd"}}  
                    [touchable-highlight 
                                 {:underlay-color "#dddd"
                                  :on-press openDrawer}
                        [image {:source hamburger
                                :style {:margin-left 10 :margin-top 5 :width 30 :height 30}}]]
                    [text {:style {:font-size 28 :margin-left 20}} (:title model)]]
                (li/list-item data-provider)
                [view {:style {:flex-direction "row"
                               :position "absolute"
                               :top "85%"
                               :left "80%"}}
                    [touchable-highlight {:underlay-color "#dddd"
                                          :on-press (fn [] (.alert (.-Alert ReactNative) "Shows action buttons"))}
                        [image {:source hamburger
                                :style {:width 30 :height 30 :z-index 10}}]]]])))