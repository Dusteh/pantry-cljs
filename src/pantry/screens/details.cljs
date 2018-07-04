(ns pantry.screens.details
  (:require [pantry.react-requires :refer [Platform
                                           Image
                                           Button
                                           TouchableOpacity
                                           InteractionManager
                                           View
                                           FlatList
                                           vIcon
                                           Modal
                                           Icon
                                           ScrollView
                                           Alert
                                           Text
                                           StackNav
                                           DrawerActions
                                           TouchableHighlight]]
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
        (fn [itm]
          [:> View
           [:> TouchableHighlight {:on-press (fn [] (reset! img-state-atm true))
                                   :underlay-color "#dddd"}
              [:> Image {:source {:uri (get itm "avatar-url")}
                         :style {:height 160
                                 :width "100%"}}]]
           [:> Modal {:animation-type "fade"
                      :transparent true
                      :on-request-close (fn [] (println "Closing"))
                      :visible @img-state-atm}
            [:> TouchableHighlight {:on-press (fn [] (reset! img-state-atm false))
                                    :underlay-color "#dddd"}
              [:> View {:style {:height "100%" :width "100%" :background-color "#dddd"}}                     
                [:> Image {:source {:uri (get itm "avatar-url")}
                           :style {:top "10%"
                                   :height 320
                                   :width "100%"}}]]]]])})))

(defn recipe-details
  [itm props this]
  (fn [props]
    (let [img-state-at (r/atom "flex")]
      [:> View {:style {:flex-direction "column"}}
       [:> View {:style {:flex-direction "row"}}
         [:> Text {:style {:font-weight "bold"
                           :width "90%"
                           :font-size 25
                           :margin-left 5}}
             (get itm "title")]
         [:> Icon {:name "edit" :size 15 :color "black" :style {:margin-top 10}}]]
       [edit-img-btn
         [img-modal itm] 
        (fn [] (println ::edit "click"))]
       [:> ScrollView {:style {:margin-top 5
                               :height "60%"}}
          [:> View {:style {:flex-direction "row"}}
            [:> Text {:style {:font-size 10
                              :margin-bottom 5
                              :width "90%"
                              :margin-left 5}} "Ingredients"]
            [:> View {:style {:flex-direction "row"}}
              [:> Icon {:name "library-add" :color "black" :size 15}]]]
          [:> FlatList {:data (get itm "ingredients")
                                   ;(into [] (repeat 20 {"key" "x" "name" "test-ingredient" "title" "Test Ingredient" "qty" "10qt"})))
                        :renderItem (fn [sitm]
                                       (let [pr-itm (get (js->clj sitm) "item")
                                             index (get (js->clj sitm) "index")]
                                         (r/as-element
                                           [:> TouchableHighlight {:on-long-press (fn [] (println ::lp "Loooong"))
                                                                   :underlay-color "#dddd"}
                                            [:> View {:style {:flex-direction "row"
                                                              :margin-left 10}}
                                             (if (get pr-itm "avatar-url")
                                               [:> Image {:uri (get pr-itm "avatar-url")
                                                          :style {:width 30 :height 30}}]
                                               [:> Icon {:name "cake"
                                                         :size 30
                                                         :color "black"}])
                                             [:> Text {:style {:width "80%"
                                                               :padding-left 5}} (get pr-itm "title")]
                                             [:> Text (get pr-itm "qty")]]])))}]
          [:> View {:style {:flex-direction "row"}}
            [:> Text {:style {:font-size 10
                              :width "90%"
                              :margin-top 5
                              :margin-left 5
                              :margin-bottom 5}} "Directions"]
            [:> View {:style {:flex-direction "row"}}
              [:> Icon {:name "edit" :color "black" :size 15}]]]
          [:> Text {:style {:margin-left 10}}
           "Blah blah blah blah\nblah blahblahblbhahahhaha"]]])))
