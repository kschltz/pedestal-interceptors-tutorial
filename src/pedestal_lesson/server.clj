
;as seem on http://pedestal.io/guides/what-is-an-interceptor
(ns pedestal-lesson.server
  (:require [io.pedestal.http :as http]
            [io.pedestal.interceptor.chain :as chain]
            [io.pedestal.interceptor.error :as err]
            [io.pedestal.interceptor :as i]))


(defn assoc-hooks [map & kvs]
  (->> (apply hash-map kvs)
       (filter (fn [[k v]] (some? v)))
       (into map)
       ))
(defn build-interceptor
  [name & {:keys [enter leave error]
           :or   {enter nil
                  leave nil
                  error nil}}]
  (let [base {:name name}]
    (i/map->Interceptor (assoc-hooks base
                                     :enter enter
                                     :leave leave
                                     :error error))))




(def hello (build-interceptor
             ::hello :enter (fn [context]
                              (assoc context
                                :response
                                {:body   "Hello, world!"
                                 :status 200}))))

(def odds (build-interceptor
            ::odds
            :enter #(assoc % :response {:body   "I handle odds"
                                        :status 200})))
(def evens (build-interceptor
             ::evens
             :enter #(assoc % :response {:body   "I handle evens"
                                         :status 200})))

(def chooser (build-interceptor
               ::chooser
               :enter #(let [param (get-in % [:request :query-params :n])
                             n (Integer/parseInt param)
                             nxt (if (even? n) evens odds)]
                         (chain/enqueue % [nxt]))))

(def number-format-handler
  (err/error-dispatch
    [ctx ex]
    [{:exception-type :java.lang.NumberFormatException}]
    (assoc ctx :response {:body "Not a number" :status 404})))

(def routes #{["/hello" :get hello]
              ["/data-science" :get [number-format-handler chooser]]
              ["/odds" :get odds]
              ["/evens" :get evens]})

(defn start []
  (-> {::http/port   8822
       ::http/join?  false
       ::http/type   :jetty
       ::http/routes routes}
      http/create-server
      http/start))