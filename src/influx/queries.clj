(ns influx.queries)

;; Query Templates
;; ******************************************************************

(def show-databases-query "SHOW DATABASES")

(defn create-database-query [db] (format "CREATE DATABASE %s" db))

(defn drop-database-query [db] (format "DROP DATABASE %s" db))

(def show-users-query "SHOW USERS")

(defn create-user-query [username password]
  (format "CREATE USER \"%s\" WITH PASSWORD '%s'" username password))

(defn create-admin-user-query [username password]
  (format "CREATE USER \"%s\" WITH PASSWORD '%s' WITH ALL PRIVILEGES" username password))

(defn show-retention-policy-query [db]
  (format "SHOW RETENTION POLICIES ON \"%s\"" db))
