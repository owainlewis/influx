(ns influx.core
  (:require [org.httpkit.client :as http]))

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

(defn get-with-conf [conf path opts]
  (http/get (endpoint-from-conf conf path)
    opts))

(defn create-db [db]
  (let [query {:q (format "CREATE DATABASE %s" db)}]
    (get-with-conf local-conf "/query"
      {:query-params query})))

(defn run-query [db query]
  (http/post "http://192.168.99.100:8086/write"
    {:query-params {:db db}
     :body "cpu_load_short,host=server01,region=us-west value=0.64 1434055562000000000"}))

(defn raw-query [db query]
  (http/get "http://192.168.99.100:8086/query?pretty=true"
    {:query-params {:db db :q query}}))

(def example "SELECT value FROM cpu_load_short WHERE region='us-west'")
