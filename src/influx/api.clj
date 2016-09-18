(ns influx.api)

(defn version [])

(defn write-metric
  ([metric-name value timestamp])
  ([metric-name tags value timestamp]))

(defn read-metrics [query])

(defn create-database [db-name])

(defn delete-database [db-name])
