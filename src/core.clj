(ns core
  (:require
   [spire.core]
   [spire.config :as config]
   [spire.module.shell :refer [shell]]
   [spire.transport :refer [ssh]])
  (:gen-class))

(defn -main [& args]
  (config/init!)
  (clojure.lang.RT/loadLibrary "spire")
  (ssh "brain@sandbox" (shell {:cmd "echo 'hello'"}))
  (flush)
  (Thread/sleep 1000)
  (println "Finished!"))
