(ns tic-tac-toe.core
  (:require [replicant.dom :as r]
            [tic-tac-toe.game :as game]
            [tic-tac-toe.ui :as ui]))

(defn main []
  ;; Set up the atom
  (let [store (atom nil)
        el (js/document.getElementById "app")]

    ;; Globally handle DOM events
    (r/set-dispatch!
     (fn [_ [action & args]]
       (prn args)
       (case action
         :tic (apply swap! store game/tic args))))

    ;; Render on every change
    (add-watch store ::render
               (fn [_ _ _ game]
                 (->> (ui/game->ui-data game)
                      ui/render-board
                      (r/render el))))

    ;; Trigger the first render by initializing the game.
    (reset! store (game/create-game {:size 3}))))
