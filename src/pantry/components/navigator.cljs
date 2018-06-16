(ns pantry.components.navigator
    (:require [reagent.core :as r :refer [atom]]
              [re-frame.core :refer [subscribe dispatch dispatch-sync]]
              [pantry.events]
              [pantry.models :as models]
              [pantry.react-requires :refer [DrawerNav
                                             StackNav]]
              [pantry.screens.components :as comps]
              [pantry.subs]))

; (defn build-drawer-routing-map
;   [this]
;   {:Recipes {:screen (comps/nav-wrapper #(comps/display-list models/recipe) "Recipes" this)}
;    :Ingredients {:screen (comps/nav-wrapper #(comps/display-list models/ingredient) "Ingredients" this)}
;    :Meals {:screen (comps/nav-wrapper #(comps/display-list models/meal) "Meals" this)}
;    :Utensils {:screen (comps/nav-wrapper #(comps/display-list models/utensil) "Utensils" this)}})

(defn build-drawer-routing-map
  [this]
  {:Recipes {:screen (comps/model-screen this models/recipe "Recipes")} 
   :Ingredients {:screen (comps/model-screen this models/ingredient "Ingredients")} 
   :Meals {:screen (comps/model-screen this models/meal "Meals")} 
   :Utensils {:screen (comps/model-screen this models/utensil "Utensils")}}) 

(defn drawer 
  [this]
  (r/reactify-component (DrawerNav (clj->js (build-drawer-routing-map this)))))

(defn build-navigator
  [this]
  (let [rute {:Drawer {:screen (drawer this)}}]
    (StackNav (clj->js rute) (clj->js {:headerMode "float"
                                        :navigationOptions (fn [{:keys [navigation] :as parms}]
                                                               (let [parms (js->clj parms)
                                                                     navigation (get-in parms ["navigation"])
                                                                     indx (get-in parms ["navigation" "state" "index"])
                                                                     state (if indx
                                                                               (nth (get-in parms ["navigation" "state" "routes"])
                                                                                   indx)
                                                                               {"key" "Details"})]
                                                                 (clj->js {:headerStyle {:backgroundColor "#ddd"}
                                                                           :title (get state "key")
                                                                           :headerLeft (r/as-component [comps/hamburger-btn navigation])
                                                                           :headerTint "white"})))}))))