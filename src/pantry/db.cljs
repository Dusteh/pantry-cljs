(ns pantry.db
  (:require [clojure.spec.alpha :as s]
            [clojure.core.async :as as]
            [pantry.model-specs :as ms]))

(def MongoDB (js/require "react-native-local-mongodb"))

(def mongo-collections 
  {:recipes {:database (new MongoDB (clj->js {:filename "recipe-db" :autoload true}))
             :validator :recipe/model}
   :meals {:database (new MongoDB (clj->js {:filename "meal-db" :autoload true}))
           :validator :meal/model}
   :utensils {:database (new MongoDB (clj->js {:filename "utensil-db" :autoload true}))
              :validator :utensil/model}
   :ingredients {:database (new MongoDB (clj->js {:filename "ingredient-db" :autoload true}))
                 :validator :ingredient/model}})

(defn run-validate
  [model inp]
  (or (s/valid? model inp) 
    (throw (js/Error (str "Input not valid, does not match model" 
                          (s/explain model inp))))))

(defn get-collection
  [collection]
  (or (get mongo-collections collection) 
      (throw (js/Error (str "No collection found for " collection)))))

(defn insert
  [collection document actor-function]
  (let [coll (get-collection collection)]
    ; (run-validate (:validator coll) document)
    (.insert (:database coll) 
             (clj->js document) 
             actor-function)))

(defn update
  [collection document actor-function]
  (.update (:database (get-collection collection))
           (clj->js {"_id" (get document "_id")})
           (clj->js (dissoc document "onclick"))
           (clj->js {"returnUpdatedDocs" true})
           actor-function))

(defn find
  [collection clause actor-function]
  (.find (:database (get-collection collection))
         (clj->js clause)
         actor-function))

(defn find-one
  [collection clause actor-function]
  (.findOne (:database (get-collection collection))
            (clj->js clause)
            actor-function))

;; spec of app-db
(s/def ::greeting string?)
(s/def ::app-db
  (s/keys :req-un [::greeting]))

;; initial state of app-db
(def app-db {:greeting "Hello Clojure in iOS and Android!"})

(defn test-db
  []
  (let [rslt-chan (as/chan)
        _ (as/go (println ::as-go-println (as/<! rslt-chan)))]
  (insert :recipes {:name "Chicken Pie"} (fn [err doc] (println doc) (as/go (as/<! doc))))))
