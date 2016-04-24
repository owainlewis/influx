(ns influx.queries)

(def show-databases-query "SHOW DATABASES")
(def show-users-query "SHOW USERS")

(defn create-database-query [db] 
  (format "CREATE DATABASE %s" db))

(defn drop-database-query [db] 
  (format "DROP DATABASE %s" db))

(defn create-user-query 
  [username password]
  (format "CREATE USER \"%s\" WITH PASSWORD '%s'" username password))

(defn create-admin-user-query 
  [username password]
  (format "CREATE USER \"%s\" WITH PASSWORD '%s' WITH ALL PRIVILEGES" username password))

(defn show-retention-policy-query 
  [db]
  (format "SHOW RETENTION POLICIES ON \"%s\"" db))
