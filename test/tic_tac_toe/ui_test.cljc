(ns tic-tac-toe.ui-test
  (:require [tic-tac-toe.ui :as ui]
            [clojure.test :refer [deftest is testing]]
            [tic-tac-toe.game :as game]))

(deftest game->ui-data-test
  (testing "Converts game data to UI data"
    (is (= (ui/game->ui-data
            {:size 3
             :tics {[0 0] :x
                    [0 1] :o}
             :next-player :x})
           {:rows [[{:content ui/mark-x}
                    {:content ui/mark-o}
                    {:clickable? true
                     :on-click [:tic 0 2]}]

                   [{:clickable? true, :on-click [:tic 1 0]}
                    {:clickable? true, :on-click [:tic 1 1]}
                    {:clickable? true, :on-click [:tic 1 2]}]

                   [{:clickable? true, :on-click [:tic 2 0]}
                    {:clickable? true, :on-click [:tic 2 1]}
                    {:clickable? true, :on-click [:tic 2 2]}]]})))

  (testing "Highlights winning path"
    (is (= (-> (game/create-game {:size 3})
               (game/tic 0 0) ;; x
               (game/tic 1 0) ;; o
               (game/tic 0 1) ;; x
               (game/tic 1 1) ;; o
               (game/tic 0 2) ;; x
               ui/game->ui-data
               :rows)
           [[{:content ui/mark-x, :highlight? true}
             {:content ui/mark-x, :highlight? true}
             {:content ui/mark-x, :highlight? true}]

            [{:content ui/mark-o, :dim? true}
             {:content ui/mark-o, :dim? true}
             {:dim? true}]

            [{:dim? true}
             {:dim? true}
             {:dim? true}]])))

  (testing "Dims tied game"
    (is (= (-> (game/create-game {:size 3})
               (game/tic 0 0) ;; x
               (game/tic 0 1) ;; o
               (game/tic 0 2) ;; x
               (game/tic 1 0) ;; o
               (game/tic 1 1) ;; x
               (game/tic 2 2) ;; o
               (game/tic 2 1) ;; x
               (game/tic 2 0) ;; o
               (game/tic 1 2) ;; x
               ui/game->ui-data
               :rows
               (->> (mapcat #(map :dim? %)))
               set)
           #{true}))))
