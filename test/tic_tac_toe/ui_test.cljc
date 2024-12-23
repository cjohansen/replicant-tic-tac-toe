(ns tic-tac-toe.ui-test
  (:require [clojure.test :refer [deftest is testing]]
            [lookup.core :as lookup]
            [replicant.alias :as alias]
            [tic-tac-toe.game :as game]
            [tic-tac-toe.ui :as ui]))

(deftest render-game-test
  (testing "Renders board"
    (is (= (->> (ui/render-game
                 {:size 3
                  :tics {[0 0] :x
                         [0 1] :o}
                  :next-player :x})
                alias/expand-1
                (lookup/select-one :div.board))
           [:div {:class #{"board"}}
            [:div {:class #{"row"}}
             [::ui/cell ui/mark-x]
             [::ui/cell ui/mark-o]
             [::ui/cell {:on {:click [:tic 0 2]}, :class #{"clickable"}}]]
            [:div {:class #{"row"}}
             [::ui/cell {:on {:click [:tic 1 0]}, :class #{"clickable"}}]
             [::ui/cell {:on {:click [:tic 1 1]}, :class #{"clickable"}}]
             [::ui/cell {:on {:click [:tic 1 2]}, :class #{"clickable"}}]]
            [:div {:class #{"row"}}
             [::ui/cell {:on {:click [:tic 2 0]}, :class #{"clickable"}}]
             [::ui/cell {:on {:click [:tic 2 1]}, :class #{"clickable"}}]
             [::ui/cell {:on {:click [:tic 2 2]}, :class #{"clickable"}}]]])))

  (testing "Highlights winning path"
    (is (= (-> (game/create-game {:size 3})
               (game/tic 0 0) ;; x
               (game/tic 1 0) ;; o
               (game/tic 0 1) ;; x
               (game/tic 1 1) ;; o
               (game/tic 0 2) ;; x
               ui/render-game
               alias/expand-1
               (->> (lookup/select '.cell-highlight)))
           [[:tic-tac-toe.ui/cell {:class #{"cell-highlight"}} ui/mark-x]
            [:tic-tac-toe.ui/cell {:class #{"cell-highlight"}} ui/mark-x]
            [:tic-tac-toe.ui/cell {:class #{"cell-highlight"}} ui/mark-x]])))

  (testing "Dims everything besides the winning path"
    (is (= (-> (game/create-game {:size 3})
               (game/tic 0 0) ;; x
               (game/tic 1 0) ;; o
               (game/tic 0 1) ;; x
               (game/tic 1 1) ;; o
               (game/tic 0 2) ;; x
               ui/render-game
               alias/expand-1
               (->> (lookup/select '.cell-dim))
               count)
           6)))

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
               ui/render-game
               alias/expand-1
               (->> (lookup/select '.cell-dim))
               count)
           9))))
