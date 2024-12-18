(ns tic-tac-toe.game)

(defn create-game [{:keys [size]}]
  {:next-player :x
   :size size})

(def next-player {:x :o, :o :x})

(defn get-winning-path [{:keys [size tics]} y x]
  (let [row (mapv #(vector y %) (range 0 size))]
    (when (= 1 (count (set (map tics row))))
      row)))

(defn maybe-conclude [game y x]
  (if-let [path (get-winning-path game y x)]
    (-> (dissoc game :next-player)
        (assoc :over? true
               :victory {:player (get-in game [:tics [y x]])
                         :path path}))
    game))

(defn tic [game y x]
  (let [player (:next-player game)]
    (if (or (get-in game [:tics [y x]])
            (<= (:size game) x)
            (<= (:size game) y))
      game
      (-> game
          (assoc-in [:tics [y x]] player)
          (assoc :next-player (next-player player))
          (maybe-conclude y x)))))
