(ns pantry.models
    (:require [pantry.db :as db]
              [pantry.screens.recipe-details :as rd]
              [pantry.react-requires :refer [StackActions View Text]]))

(defn generic-provider
    [collection clause rslt-fn]
    (db/find collection clause rslt-fn))

(defn generic-list-item-converter
    [props]
    (fn [itm]
        (let [itm (js->clj itm)]
          (merge
            {"avatar-url" (get itm "avatar-url" "https://pbs.twimg.com/profile_images/423527243554365440/4V3jhw7N_400x400.jpeg")
             "title" (get itm "name") 
             "_id" (get itm "_id")
             "key" (get itm "_id") 
             "onclick" (fn [this props] 
                          (let [props (js->clj props)
                                itm (:itm props)
                                model (:model props)
                                navigation (get props :navigation)
                                nav-dispatch (get navigation "dispatch")
                                route (keyword (str (:title model) "/Detail"))
                                push (.push StackActions (clj->js {:routeName route :params {:itm (:itm props)}})) 
                                navigate (get navigation "navigate")]
                            (nav-dispatch push)))
             "name" (get itm "name")}
            itm))))
            

(defn generic-parser
    [doc props]
    (let [js-doc (js->clj doc)
          converter (generic-list-item-converter props)               
          rslt (map converter js-doc)]
        rslt))

(def base-view
    {:name :text
     :obtained :date
     :expire-date :date})

(def base-view-order 
  [:name
   :obtained
   :expire-date])

(def recipe 
  {:title "Recipes"
   :data-provider #(generic-provider :recipes %1 %2)
   :new-instance {}
   :detail-view rd/recipe-details 
   :spec :recipe/model 
   :parser #(generic-parser %1 %2)})

(def ingredient {
    :title "Ingredients"
    :data-provider #(generic-provider :ingredients %1 %2)
    :new-instance {}
    :spec :ingredient/model
    :parser #(generic-parser %1 %2)})

(def meal {
    :title "Meals"
    :data-provider #(generic-provider :meals %1 %2)
    :new-instance {}
    :spec :meal/model
    :parser #(generic-parser %1 %2)})

(def utensil {
    :title "Utensiles"
    :data-provider #(generic-provider :meals %1 %2)
    :new-instance {}
    :spec :utensil/model
    :parser #(generic-parser %1 %2)})

(defn test-db
    []
    (println "test")
    (db/find :recipes {:name "Chicken Pie"} (fn [err rslt] (println rslt))))
