(ns pantry.screens.components
    (:require [reagent.core :as r :refer [atom]]
              [re-frame.core :refer [subscribe dispatch dispatch-sync]]
              [pantry.events :as evts]
              [pantry.react-requires :refer [Platform 
                                             Image 
                                             Button 
                                             TouchableOpacity 
                                             InteractionManager 
                                             View 
                                             ScrollView 
                                             Alert
                                             Text 
                                             StackNav
                                             DrawerActions
                                             TouchableHighlight]]
              [pantry.subs :as subs]
              [pantry.components.list-item :as li]))

(defn alert
    [msg]
    (.alert Alert msg))

(def hamburger (js/require "./images/hamburger.png"))

(defn nav-wrapper 
  [component title this]
  (let [comp (r/reactify-component
                (fn [{:keys [navigation] :as props}]
                  [component props this]))]
    (aset comp "navigationOptions" #js {"title" title})
  comp))

(def example-model
    {:title "Example Model"
     :data-provider li/example-list})

(defn hamburger-btn
  [navigation]
  (fn [] 
    [:> TouchableHighlight {:underlay-color "#ddd"
                            :on-press (fn [] (let [nav-dispatch (get navigation "dispatch")]
                                                  (nav-dispatch (.toggleDrawer DrawerActions))))}
      [:> Image {:source hamburger
                 :style {:margin-left 10 :margin-top 5 :width 30 :height 30}}]]))

(defn back-btn
  [navigation]
  (fn [] 
    [:> TouchableHighlight {:underlay-color "#ddd"
                            :on-press (fn [] (let [nav-dispatch (get navigation "dispatch")]
                                                  (nav-dispatch (.toggleDrawer DrawerActions))))}
      [:> Image {:source hamburger
                 :style {:margin-left 10 :margin-top 5 :width 30 :height 30}}]]))

(defn action-button
    []
    (fn [args]
        [:> View {:style {:flex-direction "row"
                          :position "absolute"
                          :top "90%"
                          :left "85%"}}
            [:> TouchableHighlight {:underlay-color "#dddd"
                                     :on-press (fn [] (alert "Shows action buttons"))}
                [:> Image {:source hamburger
                           :style {:width 30 :height 30 :z-index 10}}]]]))


(defn get-rn-el
    [k entry]
    (println ::entry entry)
    (cond 
        (map? entry)
        [:> Text "Map entry"]
        (= entry :text)
        [:> Text k]
        :else
        [:> Text "Else"]))                           

(defn detail-screen
    [model]
    (fn [{:keys [navigation] :as props} this]
        (let [navigation (js->clj navigation)
              detail-view (:detail-view model)
              detail-view-keys (keys detail-view)
              _ (println detail-view-keys)
              itm (get-in navigation ["state" "params" "itm"] (:new-instance model))
              base [:> View {:style {:flex-direction "column"}}]]
             (reduce (fn [b-view k]
                        (let [entry (get-rn-el k (get detail-view k))]
                            (conj b-view entry)))
                base
                detail-view-keys))))

(defn display-list
    [model]
    (fn [{:keys [navigation] :as props} this]
        (let [data-provider (:data-provider model)
              title (:title model)]
            [:> View {:style {:flex-direction "column"}}
                [:> View {:style {:width "100%" :height "100%"}}
                    [li/list-item model this props]]
                [action-button]])))

(defn model-screen
    [this model title]
    (r/reactify-component (StackNav (clj->js {(keyword (str (:title model) "/List")) {:screen (nav-wrapper #(display-list model) title this)}
                                              (keyword (str (:title model) "/Detail")) {:screen (nav-wrapper #(detail-screen model) title this)}}) (clj->js {:headerMode "none"}))))