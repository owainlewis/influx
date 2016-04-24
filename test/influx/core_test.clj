(ns influx.core-test
  (:require [clojure.test :refer :all]
            [influx.core :refer :all]))

(def local-conf
  { :host "http://192.168.99.100"
    :port 8086
    :username nil
    :password nil
  })

(deftest endpoint-from-conf-test
  (testing "should extract correct endpoint"
    (is (= "http://192.168.99.100:8086/query"
          (endpoint-from-conf local-conf "/query")))))
