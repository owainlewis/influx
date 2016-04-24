# Influx

An async client for Influx DB.

Internally this client uses http-kit for async requests. Use the @ operator to block requests. See http-kit for more info.

## Usage

## Getting Started

```clojure
(ns my-ns
  (:require [influx.core :as influx]))
```

All requests require configuration which is just a map of information needed to connect to InfluxDB

```clojure
(def local-conf
  { :host "http://192.168.99.100"
    :port 8086
    :user nil
    :password nil
  })
```

## Basic Queries

```clojure
@(influx/query local-conf "SHOW DATABASES")

;; Create a database

```

## Writing Data

If you want to write a single line of data to influx

```clojure

```

Or a batch of data

```clojure
(def sample-data
  [ "cpu_load_short,host=server02 value=0.67"
    "cpu_load_short,host=server02,region=us-west value=0.55 1422568543702900257"
    "cpu_load_short,direction=in,host=server01,region=us-west value=2.0 1422568543702900257" ])



```

## License

Copyright © 2016 Owain Lewis

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
