(ns influx.core
  (:require [clojure.string :refer [join]]
            [org.httpkit.client :as http]))

(defrecord Conf [host port user pass])

(defonce local-conf
  { :host "http://192.168.99.100"
    :port 8086
    :user nil
    :password nil
  })

(defn endpoint-from-conf
  [conf path]
  (apply (partial format "%s:%s%s")
    (conj
      (into [] ((comp vals select-keys) conf [:host :port]))
      path)))

(defn- get-with-conf
  [conf path opts]
  (http/get (endpoint-from-conf conf path)
    opts))

(defn- post-with-conf
  [conf path opts]
  (http/post (endpoint-from-conf conf path) opts))

;; General stuff

(defn create-db [db]
  (let [query {:q (format "CREATE DATABASE %s" db)}]
    (get-with-conf local-conf "/query"
      {:query-params query})))

;; Writing data

(defn write-batch
  "Post multiple points to multiple series at the same time by separating each point with a new line.
   Batching points in this manner results in much higher performance."
  [conf db lines]
  (when (sequential? lines)
    (post-with-conf conf "/write"
      {:query-params {:db db}
       :body (join lines "\n")})))

(defn write [conf db line]
  (write-batch conf db [line]))

;; Query data

(defn raw-query [conf db query]
  (get-with-conf conf "/query?pretty=true"
    {:query-params {:db db :q query}}))
