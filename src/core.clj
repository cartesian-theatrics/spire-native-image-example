(ns core
  (:require
   [spire.core]
   [spire.config :as config]
   [spire.module.shell :refer [shell]]
   [spire.transport :refer [ssh]])
  (:gen-class))

(defn -main [& args]
  (config/init!)  ;; when running under graal, extracts the shared library to users home directory and sets up the java library path. You will need to have the .so or .dynlib file bundled in your resources for this extraction to work.
  (clojure.lang.RT/loadLibrary "spire")  ;; load the C library
  (ssh "brain@sandbox" (shell {:cmd "echo 'hello'"})))
