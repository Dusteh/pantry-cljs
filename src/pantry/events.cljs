(ns pantry.events
  (:require
   [re-frame.core :refer [reg-event-db after]]
   [clojure.spec.alpha :as s]
   [pantry.db :as db :refer [app-db]]))

;; -- Interceptors ------------------------------------------------------------
;;
;; See https://github.com/Day8/re-frame/blob/master/docs/Interceptors.md
;;
(defn check-and-throw
  "Throw an exception if db doesn't have a valid spec."
  [spec db [event]]
  ; (when-not (s/valid? spec db)
  ;   (let [explain-data (s/explain-data spec db)]
  ;     (throw (ex-info (str "Spec check after " event " failed: " explain-data) explain-data)))))
  true)

(def validate-spec
  (if goog.DEBUG
    (after (partial check-and-throw ::db/app-db))
    []))

;; -- Handlers --------------------------------------------------------------

(reg-event-db
 :initialize-db
 validate-spec
 (fn [_ _]
   app-db))

(reg-event-db
 :set-greeting
 validate-spec
 (fn [db [_ value]]
   (assoc db :greeting value)))

(reg-event-db
  :nav-state
  validate-spec
  (fn [db _]
    (println :nav/state)
    db)) 

(reg-event-db
  :set-title
  validate-spec
  (fn [db [_ val]]
    (println ::set-title db)
    (assoc db :title val)))