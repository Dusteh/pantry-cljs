(ns pantry.screens.details
  (:require [pantry.react-requires :refer [Platform
                                           Image
                                           Button
                                           TouchableOpacity
                                           InteractionManager
                                           View
                                           FlatList
                                           Picker
                                           PickerItem
                                           vIcon
                                           Modal
                                           Icon
                                           ScrollView
                                           Button
                                           Alert
                                           Text
                                           TextInput
                                           StackNav
                                           DrawerActions
                                           log
                                           TouchableHighlight]]
            [clojure.walk :as walk]
            [reagent.core :as r :refer [atom]]))

(def default-itm-avatar (js/require "./images/cupcake.png"))

(defn edit-img-btn
  [base-elm click-fn]
  (fn [props click-fn]
    [:> View
     base-elm
     [:> View {:style {:flex-direction "row"
                       :top "75%"
                       :left "90%"
                       :position "absolute"}}
       [:> TouchableHighlight {:on-press click-fn
                               :underlay-color "white"}
        [:> Icon {:name "edit" :size 35 :color "#dddd"}]]]]))


(defn img-modal
  [itm]
  (let [img-state-atm (r/atom false)]
    (r/create-class
       {:reagent-render 
        (fn []
          [:> View
           [:> TouchableHighlight {:on-press (fn [] (reset! img-state-atm true))
                                   :underlay-color "#dddd"}
              [:> Image {:source {:uri (:avatar-url @itm)}
                         :style {:height 160
                                 :width "100%"}}]]
           [:> Modal {:animation-type "fade"
                      :transparent true
                      :on-request-close (fn [] (println "Closing"))
                      :visible @img-state-atm}
            [:> TouchableHighlight {:on-press (fn [] (reset! img-state-atm false))
                                    :underlay-color "#dddd"}
              [:> View {:style {:height "100%" :width "100%" :background-color "#dddd"}}                     
                [:> Image {:source {:uri (:avatar-url @itm)}
                           :style {:top "10%"
                                   :height 320
                                   :width "100%"}}]]]]])})))
(defn directions-textbox
  [props itm]
  (let [show-edit-atm (r/atom false)]
    (fn [props]
      [:> View
          [:> View {:style {:flex-direction "row"}}
            [:> Text {:style {:font-size 10
                              :width "90%"
                              :margin-top 5
                              :margin-left 5
                              :margin-bottom 5}} "Directions"]
            [:> View {:style {:flex-direction "row"}}
             [:> TouchableHighlight {:on-press (fn [] (reset! show-edit-atm true)) 
                                     :justify-content "center" 
                                     :align-content "center"
                                     :underlay-color "#dddd"}
              [:> Icon {:name "edit" :color "black" :size 15}]]]]
          [:> Modal {:animation-type "fade"
                     :transparent false
                     :on-request-close (fn [])
                     :visible @show-edit-atm}
            [:> Text {:style {:margin-left 5
                              :font-size 20}} 
                "Edit directions"]
            [:> View {:style {:height "87%" :width "100%"}}
              [:> TextInput {:multiline true
                             :on-end-editing (fn [txt]
                                               (swap! itm assoc :directions (.-text (.-nativeEvent txt))))
                             :number-of-lines 50}
                (:directions @itm)]]
            [:> View {:style {:height "5%" :width "100%" :padding-left 5 :padding-right 5}}
             [:> Button {:on-press (fn [] (reset! show-edit-atm false)) 
                         :title "Done"} "Done"]]]
          [:> Text {:style {:margin-left 10}}
           (:directions @itm)]])))

(defn recipe-details
  [itm props this]
  (fn [props]
    (let [itm-atm (r/atom (walk/keywordize-keys itm))]
      [:> View {:style {:flex-direction "column"}}
        [:> View {:style {:flex-direction "row"}}
          [:> TextInput {:style {:font-weight "bold"}
                         :width "90%"
                         :font-size 25}
            (:title @itm-atm)]
          [:> View {:style {:flex-direction "row" :align-items "center" :justify-content "center"}}
            [:> Icon {:name "more-vert" :size 35}]
            [:> Picker ]]]
            
       [edit-img-btn
         [img-modal itm-atm] 
        (fn [] (println ::edit "click"))]
       [:> ScrollView {:style {:margin-top 5
                               :height "50%"}}
          [:> View {:style {:flex-direction "row"}}
            [:> Text {:style {:font-size 10
                              :margin-bottom 5
                              :width "90%"
                              :margin-left 5}} "Ingredients"]
            [:> View {:style {:flex-direction "row" :justify-content "center" :align-items "center"}}
              [:> Icon {:name "library-add" :color "black" :size 15}]]
            [:> FlatList {:data (:ingredients @itm-atm)
                                     ;(into [] (repeat 20 {"key" "x" "name" "test-ingredient" "title" "Test Ingredient" "qty" "10qt"})))
                          :renderItem (fn [sitm]
                                         (let [pr-itm (get (js->clj sitm) "item")
                                               index (get (js->clj sitm) "index")]
                                           (r/as-element
                                             [:> TouchableHighlight {:on-long-press (fn [] (println ::lp "Loooong"))
                                                                     :underlay-color "#dddd"}
                                              [:> View {:style {:flex-direction "row"
                                                                :margin-left 10}}
                                               (if (:avatar-url pr-itm)
                                                 [:> Image {:uri (:avatar-url pr-itm) 
                                                            :style {:width 30 :height 30}}]
                                                 [:> Icon {:name "cake"
                                                           :size 30
                                                           :color "black"}])
                                               [:> Text {:style {:width "80%"
                                                                 :padding-left 5}} (:title pr-itm)]
                                               [:> Text (:qty pr-itm)]]])))}]]
          [directions-textbox props itm-atm]]
       [:> View {:style {:flex-direction "row" :width "100%" :height 40 :justify-content "center"}}
          [:> View {:style {:width "48%" :margin-right 5}}
           [:> Button {:title "Save"
                       :on-press (fn [])}
            "Save"]]
          [:> View {:style {:width "48%"}}
           [:> Button {:title "Cancel"
                       :on-press (fn [])}
            "Cancel"]]]])))
           
          
        
       
          
          
