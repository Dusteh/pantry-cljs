(ns pantry.components.list-item
    (:require [reagent.core :as r :refer [atom]]
              [re-frame.core :refer [subscribe dispatch dispatch-sync]]
              [pantry.react-requires :refer [Platform 
                                             Alert
                                             Image 
                                             Button 
                                             TouchableOpacity 
                                             InteractionManager 
                                             View 
                                             ScrollView 
                                             Text 
                                             FlatList
                                             TouchableHighlight]]
              [pantry.events]
              [pantry.model_specs :as ms]
              [pantry.subs]))


(def title "duh")

(def example-list
    (repeat 20
     {"avatar-url" "https://pbs.twimg.com/profile_images/423527243554365440/4V3jhw7N_400x400.jpeg"
      "title" "Test"
    ;   "key" "some random number" 
      "onclick" (fn [])
      "name" "Dustin Hittenmiller"}))

(defn result-handler
    [list-atm model props]
    (fn [err doc]
        (if err
            (do (println "An error occured" err)
                (reset! list-atm []))
            (reset! list-atm ((:parser model) doc props)))))

(defn list-item
    "A list item consists of a image of the recipe, 
     if avaliable, a title, and a list of main ingredients"
    [model this props]
    (let [prvdr (:data-provider model)
          list-atm (atom (if (coll? prvdr) prvdr []))
          rslt-handler (result-handler list-atm model props)] 
        (when (not (coll? prvdr)) (prvdr {} rslt-handler));;Dispatches in the prvdr call if it's not a raw collection
        (fn [args]
            [:> FlatList {:data @list-atm
                          :renderItem (fn [itm] 
                                        (let [pr-itm (get (js->clj itm) "item")
                                              indx (get (js->clj itm) "index")
                                              url (get pr-itm "avatar-url")
                                              title (get pr-itm "title")
                                              click (get pr-itm "onclick")
                                              onclick #(click this (assoc props :itm itm
                                                                                :model model))
                                              name (get pr-itm "name")]
                                          (r/as-element
                                                  [:> TouchableHighlight {:key indx
                                                                          :on-press onclick
                                                                          :underlay-color "#dddd"}
                                                      [:> View {:style {:flex-direction "row"} 
                                                                :align-items "flex-start"}
                                                          [:> Image {:source {:uri url}
                                                                     :style {:width 50 :height 50}}]
                                                          [:> View {:style {:flex-direction "column" :marginLeft 20}}
                                                              [:> Text {:style {:font-size 16}} name]
                                                              [:> Text {:style {:font-size 12}} title]]]])))}])))
