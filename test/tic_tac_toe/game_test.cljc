(ns tic-tac-toe.game-test
  (:require [clojure.test :refer [deftest is testing]]
            [tic-tac-toe.game :as game]))

(deftest tic-tac-toe-test
  (testing "X is the starting player"
    (is (= (game/create-game {:size 3})
           {:next-player :x
            :size 3})))

  (testing "X places a tic"
    (is (= (-> (game/create-game {:size 3})
               (game/tic 0 0))
           {:size 3
            :tics {[0 0] :x}
            :next-player :o})))

  (testing "O places a tic"
    (is (= (-> (game/create-game {:size 3})
               (game/tic 0 0)
               (game/tic 0 1))
           {:size 3
            :tics {[0 0] :x
                   [0 1] :o}
            :next-player :x})))

  (testing "Can't tic-tac on the same spot"
    (is (= (-> (game/create-game {:size 3})
               (game/tic 0 0)
               (game/tic 0 0))
           {:size 3
            :next-player :o
            :tics {[0 0] :x}})))

  (testing "Can't tic outside the board"
    (is (= (-> (game/create-game {:size 3})
               (game/tic 3 0))
           {:size 3
            :next-player :x}))

    (is (= (-> (game/create-game {:size 3})
               (game/tic 0 3))
           {:size 3
            :next-player :x})))

  (testing "Detects row victory"
    (is (= (-> (game/create-game {:size 3})
               (game/tic 0 0) ;; x
               (game/tic 1 0) ;; o
               (game/tic 0 1) ;; x
               (game/tic 1 1) ;; o
               (game/tic 0 2) ;; x
               (select-keys [:over? :victory]))
           {:over? true
            :victory {:player :x
                      :path [[0 0] [0 1] [0 2]]}})))

  (testing "Detects column victory"
    (is (= (-> (game/create-game {:size 3})
               (game/tic 0 0) ;; x
               (game/tic 0 1) ;; o
               (game/tic 1 0) ;; x
               (game/tic 1 1) ;; o
               (game/tic 2 0) ;; x
               (select-keys [:over? :victory]))
           {:over? true
            :victory {:player :x
                      :path [[0 0] [1 0] [2 0]]}})))

  (testing "Detects diagonal victory"
    (is (= (-> (game/create-game {:size 3})
               (game/tic 0 0) ;; x
               (game/tic 0 1) ;; o
               (game/tic 1 1) ;; x
               (game/tic 1 0) ;; o
               (game/tic 2 2) ;; x
               (select-keys [:over? :victory]))
           {:over? true
            :victory {:player :x
                      :path [[0 0] [1 1] [2 2]]}})))

  (testing "Detects reverse diagonal victory"
    (is (= (-> (game/create-game {:size 3})
               (game/tic 0 2) ;; x
               (game/tic 0 1) ;; o
               (game/tic 1 1) ;; x
               (game/tic 1 0) ;; o
               (game/tic 2 0) ;; x
               (select-keys [:over? :victory]))
           {:over? true
            :victory {:player :x
                      :path [[0 2] [1 1] [2 0]]}})))

  (testing "Detects tied game"
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
               (select-keys [:over? :victory]))
           {:over? true}))))
