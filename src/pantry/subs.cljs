(ns pantry.subs
  (:require [re-frame.core :refer [reg-sub]]))

(reg-sub
  :get-greeting
  (fn [db _]
    (:greeting db)))

(reg-sub
  :nav-state
  (fn [db _]
    (println ::reg-sub)
    db))

(reg-sub
  :title
  (fn [db _]
    (:title db)))