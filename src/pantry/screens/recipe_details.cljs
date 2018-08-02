(ns pantry.screens.recipe-details
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
                                           Menu
                                           MenuTrigger
                                           MenuOptions
                                           MenuOption
                                           log
                                           TouchableHighlight]]
            [pantry.db :as db]
            [clojure.walk :as walk]
            [reagent.core :as r :refer [atom]]))

(defn cancel
  [props]
  ((get (-> props :navigation js->clj) "goBack")))

(defn save [itm-atm props]
  (db/update :recipes
             (walk/stringify-keys @itm-atm)
             (fn [err numAffected affectedDocuments upsert]
                (println ::result-fn err numAffected affectedDocuments upsert)))
  (cancel props))
  

(defn edit-img-btn
  [base-elm]
  (fn [props]
    [:> View
     base-elm
     [:> View {:style {:flex-direction "row"
                       :top "75%"
                       :left "90%"
                       :position "absolute"}}
       [:> TouchableHighlight {:on-press (fn [] (println "edit")) 
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

(defn add-ingredient
  [itm]
  (let [add-itm-modal (atom false)]
    (fn []
      [:> View
;       [:> Modal {:animation-type "fade"
;                  :transparent true
;                  :on-request-close (fn [] (println "Closing"))
;                  :visible @add-itm-modal
;        [:> View {:style {:height "100%" :width "100%" :background-color "#dddd" 
;                          :justify-content "center" 
;                          :align-items "center"}}
;          [:> View {:style {:width "80%" 
;                            :border-style "solid" 
;                            :border-top-width 1
;                            :border-bottom-width 1
;                            :border-left-width 1
;                            :border-right-width 1
;                            :background-color "white"}}
;           [:> Text "Hello"]]]
           
            
       [:> View {:style {:flex-direction "row"}}
            [:> Text {:style {:font-size 10
                              :margin-bottom 5
                              :width "90%"
                              :margin-left 5}} "Ingredients"]
            [:> View {:style {:flex-direction "row" :justify-content "center" :align-items "center"}}
              [:> TouchableHighlight {:on-press (fn [] 
                                                  (reset! add-itm-modal true))
                                      :underlay-color "#dddd"}
               [:> Icon {:name "library-add" :color "black" :size 15}]]]
        [:> FlatList {:data (:ingredients @itm)
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
                                           [:> Text (:qty pr-itm)]]])))}]]])))

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

(defn save-cancel-btns
  [props itm-atm]
  (fn []
    [:> View {:style {:flex-direction "row" :width "100%" :height 40 :justify-content "center" :margin-bottom 5}}
      [:> View {:style {:width "48%" :margin-right 5}}
       [:> Button {:title "Save"
                   :on-press (fn [] (save itm-atm props))}
        "Save"]]
      [:> View {:style {:width "48%"}}
       [:> Button {:title "Cancel"
                   :on-press (fn [] (cancel props))}
        "Cancel"]]]))

(defn title-text
  [itm-atm]
  (fn []
    [:> TextInput {:style {:font-weight "bold"}
                   :width "90%"
                   :on-change-text (fn [txt] (swap! itm-atm assoc :title txt)
                                             (swap! itm-atm assoc :name txt))
                   :font-size 25}
      (:title @itm-atm)]))

(defn recipe-details
  [itm props this]
  (fn [props]
    (let [itm-atm (r/atom (walk/keywordize-keys itm))
          ingredients-atm (r/atom (:ingredients @itm-atm []))]
      [:> View {:style {:flex-direction "column"}}
        [:> View {:style {:flex-direction "row"}}
          [title-text itm-atm]
          [:> View {:style {:flex-direction "row" :align-items "center" :justify-content "center"}}
           [Menu
            [MenuTrigger [:> Icon {:name "more-vert" :size 35}]]
            [MenuOptions
             [MenuOption [:> Text "Print"]]
             [MenuOption [:> Text "Share"]]]]]]
        [edit-img-btn
           [img-modal itm-atm]]
        [:> ScrollView {:style {:margin-top 5
                                :height "48%"}}
          [add-ingredient itm-atm]
          [directions-textbox props itm-atm]]
        [save-cancel-btns props itm-atm]])))
