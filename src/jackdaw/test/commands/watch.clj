(ns jackdaw.test.commands.watch
  (:require
   [jackdaw.test.journal :as j]
   [clojure.tools.logging :as log]
   [manifold.time :as t]))

;; Circle CI times out after a minute, so make sure this is less than 60,000
(def ^:dynamic *default-watch-timeout* 10000)
(def ^:dynamic *default-watch-tick* (t/milliseconds 250))

(defn- watch-timeout [t]
  (if (or (nil? t) (= t :default-timeout))
    *default-watch-timeout*
    t))

(defn- watch-tick [t]
  (if (or (nil? t) (= t :default-timeout))
    *default-watch-tick*
    t))

(defn- watch-params
  ([watch-query] [watch-query "Watcher" *default-watch-timeout* *default-watch-tick*])
  ([watch-query opts] [watch-query (:info opts)
                       (watch-timeout (:timeout opts))
                       (watch-tick (:tick opts))]))

(defn handle-watch-cmd
  [machine cmd params]
  (let [[query info timeout tick] (apply watch-params params)
        condition? query]
    (j/watch-for machine condition? timeout info tick)))

(def command-map
  {:watch handle-watch-cmd})
