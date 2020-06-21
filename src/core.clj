(ns core
  (:require
   [spire.state]
   [spire.config :as config]
   [spire.output.core :as output]
   [spire.output.default]
   [spire.output.events]
   [spire.output.quiet]
   [puget.printer :as puget]
   [clojure.tools.cli :as cli]
   [spire.module.shell :refer [shell]]
   [spire.transport :refer [ssh]]
   [clojure.core.async :refer [go-loop <!! <! timeout]])
  (:gen-class))

(def cli-options
  [[nil "--help" "Print the command line help"]
   [nil "--target TARGET" "SSH target. Ex. --target '{:username \"sarah\" :hostname \"sandbox\"}'"]])

(defn -main [& args]
  (let [{:keys [options summary]} (cli/parse-opts args cli-options)]
    (cond (:help options) (println summary)
          (:target options)
          (do (config/init!)
              (output/print-thread :default)
              (clojure.lang.RT/loadLibrary "spire")
              (let [ssh-target (-> options :target clojure.edn/read-string)]
                (<!! (go-loop [x 0]
                       (puget/cprint (ssh ssh-target
                                          (shell {:cmd (format "echo 'hello %s'" (str x))})))
                       (<! (timeout 2000))
                       (recur (inc x))))))
          :else
          (do (println "Missing ssh target")
              (println summary)))))
