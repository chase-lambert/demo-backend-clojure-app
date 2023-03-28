(ns server.handler
  (:require
    [compojure.core           :refer [defroutes GET POST]]
    [compojure.route          :as route]
    [ring.adapter.jetty       :as jetty]
    [ring.middleware.defaults :refer [wrap-defaults site-defaults]]
    [server.views             :as views])
  (:gen-class))


(defroutes app-routes
  (GET "/"
    []
    (views/home-page))
  (GET "/add-location"
    []
    (views/add-location-page))
  (POST "/add-location"
    {params :params}
    (views/add-location-results-page params))
  (GET "/location/:loc-id"
    [loc-id]
    (views/location-page loc-id))
  (GET "/all-locations"
    []
    (views/all-locations-page))
  (route/resources "/")
  (route/not-found "Not Found"))

(def app
  (wrap-defaults #'app-routes site-defaults))

(defn -main [& [port]]
  (let [port (parse-long (or port (System/getenv "PORT") "3000"))]
    (jetty/run-jetty #'app {:port port})))
