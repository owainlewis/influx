(ns influx.core
  (:require [clojure.string :refer [join]]
            [influx.queries :refer :all]
            [cheshire.core :as json]
            [org.httpkit.client :as http]))

(defrecord Conf [host port username password])

(def ^{:private true} docker-conf
  (Conf. "http://192.168.99.100" 8086 nil nil))

(def ^{:private true} sample-data
  [ "cpu_load_short,host=server02 value=0.67"
    "cpu_load_short,host=server02,region=us-west value=0.55 1422568543702900257"
    "cpu_load_short,direction=in,host=server01,region=us-west value=2.0 1422568543702900257" ])

(defn endpoint-from-conf
  "Constructs the full endpoint of the InfluxDB instance"
  [conf path]
  (apply (partial format "%s:%s%s")
    (conj
      (into [] ((comp vals select-keys) conf [:host :port]))
      path)))

(defn- build-request
  "Constructs the low level HTTP request information that can be dispatched
   to http-kit. This makes testing easier"
  [method conf path opts]
  (let [{:keys [username password]} conf]
    (merge
      { :method method
        :url (endpoint-from-conf conf path)
        :basic-auth [username password] }
      opts)))

(defn run-request!
  "Runs a HTTP request returning only the body"
  [req]
  (http/request req
    (fn [{:keys [status headers body error]}]
     {:status status :body (json/parse-string body true)})))

(defn raw-query-request
  "Performs a simple raw query against InfluxDB"
  [conf q]
  (build-request :get conf "/query"
    {:query-params {:q q}}))

(defn write-data-request
  [conf db data]
  (build-request :post conf "/write"
     {:query-params {:db db}
      :body data}))

(defn db-query-request
  "Appending pretty=true to the URL enables pretty-printed JSON output.
   While this is useful for debugging or when querying directly with tools like curl,
   it is not recommended for production use as it consumes unnecessary network bandwidth."
  ([conf db query pretty]
  (build-request :get conf "/query"
    {:query-params {:db db :q query :pretty pretty}}))
  ([conf db query]
    (raw-query-request conf db query false)))

(def write-batch-metrics
  ^{:doc "Write a batch of data to InfluxDB"}
  (comp run-request! write-data-request))

(defn write-metric
  ^{:doc "Write data to InfluxDB"}
  [conf db data]
  (run-request!
    (write-data-request conf db data)))

(def db-query
  ^{:doc "Perform a query against a given database"}
  (comp run-request! db-query-request))

(def query
  ^{:doc "Runs a simple query against InfluxDB"}
  (comp run-request! raw-query-request))
