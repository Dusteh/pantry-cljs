(ns pantry.components.list-item
    (:require [reagent.core :as r :refer [atom]]
              [re-frame.core :refer [subscribe dispatch dispatch-sync]]
              [pantry.events]
              [pantry.model_specs :as ms]
              [pantry.subs]))


(def ReactNative (js/require "react-native"))

(def text (r/adapt-react-class (.-Text ReactNative)))
(def view (r/adapt-react-class (.-View ReactNative)))
(def image (r/adapt-react-class (.-Image ReactNative)))
(def touchable-highlight (r/adapt-react-class (.-TouchableHighlight ReactNative)))
(def flat-list (r/adapt-react-class (.-FlatList ReactNative)))

(def title "duh")

(def example-list
    (repeat 20
     {"avatar-url" "https://pbs.twimg.com/profile_images/423527243554365440/4V3jhw7N_400x400.jpeg"
      "title" "Test"
    ;   "key" "some random number" 
      "onclick" (fn [] (.alert (.-Alert ReactNative) "Shows item detail screen"))
      "name" "Dustin Hittenmiller"}))

(defn list-item
    "A list item consists of a image of the recipe, 
     if avaliable, a title, and a list of main ingredients"
    [list]
    [flat-list {:data list
                :renderItem (fn [itm] 
                                (let [pr-itm (get (js->clj itm) "item")
                                    indx (get (js->clj itm) "index")
                                    url (get pr-itm "avatar-url")
                                    title (get pr-itm "title")
                                    onclick (get pr-itm "onclick")
                                    name (get pr-itm "name")]
                                (r/as-element
                                        [touchable-highlight {:key indx
                                                                :on-press onclick
                                                                :underlay-color "#dddd"}
                                            [view {:style {:flex-direction "row" 
                                                        :align-items "flex-start"}}

                                                [image {:source {:uri url}
                                                        :style {:width 50 :height 50}}]
                                                [view {:style {:flex-direction "column" :marginLeft 20}}
                                                    [text {:style {:font-size 16}} name]
                                                    [text {:style {:font-size 12}} title]]]]
                                        )))}])