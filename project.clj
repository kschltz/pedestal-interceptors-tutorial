(defproject pedestal-lesson "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure "1.10.0"]
                 [io.pedestal/pedestal.service "0.5.7"]
                 [io.pedestal/pedestal.jetty "0.5.7"]
                 [org.slf4j/slf4j-api "1.7.22"]]
  :main ^:skip-aot pedestal-lesson.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
