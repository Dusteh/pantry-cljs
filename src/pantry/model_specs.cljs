(ns pantry.model-specs
    (:require [cljs.spec.alpha :as s]
              [cljs-time.core :as date]))

(s/def :base/id int?)
(s/def :base/name string?)
(s/def :base/obtained date/date?)
(s/def :base/expire-date date/date?)

(s/def ::base (s/keys :req-un [:base/name]
                      :opt-un [:base/obtained
                               :base/expire-date]))

(s/def :ingredient/model (s/merge ::base 
                         (s/keys :req-un []
                                 :opt-un [:ingredient/quantity])))

(s/def :utensil/model (s/merge ::base 
                      (s/keys :req-un [:utensil/quantity])))

(s/def :recipe/ingredients coll?)
(s/def :recipe/instructions string?)

(s/def :recipe/model (s/merge ::base
                     (s/keys :req-un [:recipe/ingredients
                                      :recipe/instructions])))

(def valid-meal-types #{:leftover :pre-made})
(s/def :meal/type (fn [inp] (contains? valid-meal-types inp)))

(s/def :meal/model (s/merge ::base
                   (s/keys :req-un [:meal/type]
                           :opt-un [:meal/source-recipe])))

