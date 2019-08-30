(ns pedestal-lesson.server
  (:require [io.pedestal.http :as http]
            [io.pedestal.interceptor.chain :as chain]
            [io.pedestal.interceptor.error :as err]))


(def routes #{})

(defn build-interceptor
  [name & {:keys [enter leave error]
           :or   {enter nil
                  leave nil
                  error nil}}])

()
(build-interceptor ::hello
                   :enter
                   #(assoc %
                      :response {:body   "hi"
                                 :status 200}))

(defn start []
  (-> {::http/port   8822
       ::http/join?  false
       ::http/type   :jetty
       ::http/routes routes}
      http/create-server
      http/start))