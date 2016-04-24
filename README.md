# Influx

An async client for Influx DB.

Internally this client uses http-kit for async requests.

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

## Creating A Database

## Writing Data

If you want to write a single line of data to influx

```clojure
(influx/write local-conf "mydb"
  "cpu_load_short,host=server01,region=us-west value=0.64 1434055562000000000")
```

If you want to write multiple lines of data use the write-batch method

```clojure
(influx/write-batch local-conf "mydb"
  ["cpu_load_short,host=server01,region=us-west value=0.64 1434055562000000000"])
```

## Reading Data

## License

Copyright © 2016 Owain Lewis

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
