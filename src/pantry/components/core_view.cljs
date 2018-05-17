(ns pantry.components.core-view
  (:require [reagent.core :as r :refer [atom]]
            [re-frame.core :refer [subscribe dispatch dispatch-sync]]
            [cljs-react-navigation.reagent :refer [stack-screen]]
            [pantry.events]
            [pantry.components.navigator :as nav]
            [pantry.screens.list :as li]
            [pantry.model_specs :as ms]
            [pantry.subs]))

(defn build-routing-map
  []
  {:Recipes {:screen (stack-screen #(li/display-list li/example-model) {:title "Recipes"})}
   :Ingredients {:screen (stack-screen #(li/display-list li/example-model) {:title "Ingredients"})}
   :Meals {:screen (stack-screen #(li/display-list li/example-model) {:title "Meals"})}
   :Utensils {:screen (stack-screen #(li/display-list li/example-model) {:title "Utensils"})}})

(defn app-root
  [& args]
  ; (fn [] (li/list-item (clj->js li/example-list))))
  (fn [args] (this-as this
              (nav/get-component {:routing-map (build-routing-map)
                                  :this this}))))