(ns pantry.components.navigator
    (:require [reagent.core :as r :refer [atom]]
              [cljs-react-navigation.re-frame :refer [drawer-navigator]]
              [re-frame.core :refer [subscribe dispatch dispatch-sync]]
              [pantry.events]
              [pantry.model_specs :as ms]
              [pantry.subs]))

(def react-navigation (js/require "react-navigation"))

(defn Navigator [routing-map]
    ; (drawer-navigator {:Home {:screen (stack-screen home {:title "Home"})}
    ;                    :About {:screen (stack-screen home {:title "About"})}}))
    (drawer-navigator routing-map))
    
(defn get-component
    [{:keys [routing-map this args]}]
    (fn [args]
        (let [navigator (Navigator routing-map)]
            [(r/adapt-react-class navigator)])))
