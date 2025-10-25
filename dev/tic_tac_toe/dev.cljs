(ns tic-tac-toe.dev
  (:require [dataspex.core :as dataspex]
            [tic-tac-toe.core :as tic-tac-toe]))

(def store (atom nil))

(defn main []
  (dataspex/inspect "Game state" store)
  (tic-tac-toe/main store))
