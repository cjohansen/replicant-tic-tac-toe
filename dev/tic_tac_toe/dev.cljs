(ns tic-tac-toe.dev
  (:require [dataspex.core :as dataspex]
            [tic-tac-toe.core :as tic-tac-toe]))

(def store (atom nil))

(defn ^:dev/after-load configure []
  (dataspex/inspect "Game state" store)
  (tic-tac-toe/main store))

(defn main []
  (configure)
  ;; Trigger the first render by initializing the game.
  (tic-tac-toe/start-new-game store))
