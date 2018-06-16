(ns pantry.components.core-view
  (:require [reagent.core :as r :refer [atom]]
            [pantry.react-requires :refer [Platform Image Button TouchableOpacity InteractionManager View ScrollView Text TouchableHighlight]]
            [re-frame.core :refer [subscribe dispatch dispatch-sync]]
            ; [cljs-react-navigation.reagent :refer [stack-navigator stack-screen router] :as rnav]
            [pantry.events]
            [pantry.components.navigator :as nav]
            [pantry.screens.components :as comps]
            [pantry.model_specs :as ms]
            [pantry.subs]))

(def ReactNative (js/require "react-native"))

(defn app-root
  [& args]
  (let [nav-state (subscribe [:nav-state])]
    (r/create-class                 ;; <-- expects a map of functions 
      {:component-did-mount               ;; the name of a lifecycle function
      #(println "component-did-mount")   ;; your implementation
      :component-will-mount              ;; the name of a lifecycle function
      #(println "component-will-mount")  ;; your implementation
      ;; other lifecycle funcs can go in here
      :display-name  "pantry"  ;; for more helpful warnings & errors
      :reagent-render (fn [] [(this-as this (r/adapt-react-class (nav/build-navigator this)))])})))