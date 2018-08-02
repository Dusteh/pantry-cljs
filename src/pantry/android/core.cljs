(ns pantry.android.core
  (:require [reagent.core :as r :refer [atom]]
            [re-frame.core :refer [subscribe dispatch dispatch-sync]]
            [pantry.components.core-view :as cv]
            [pantry.events]
            [pantry.subs]))

(def ReactNative (js/require "react-native"))

(def app-registry (.-AppRegistry ReactNative))

(defn app-root []
  (cv/app-root {}))

(defn init []
      (dispatch-sync [:initialize-db])
      (.registerComponent app-registry "pantry" #(r/reactify-component app-root)))
