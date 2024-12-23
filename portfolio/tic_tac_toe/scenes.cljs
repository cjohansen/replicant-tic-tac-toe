(ns tic-tac-toe.scenes
  (:require [portfolio.replicant :refer-macros [defscene]]
            [portfolio.ui :as portfolio]
            [tic-tac-toe.ui :as ui]))

(defscene empty-cell
  [ui/cell {:class :clickable}])

(defscene cell-with-x
  [ui/cell ui/mark-x])

(defscene cell-with-o
  [ui/cell ui/mark-o])

(defscene interactive-cell
  "Click the cell to toggle the tic on/off"
  :params (atom nil)
  [store]
  [ui/cell
   {:class "clickable"
    :on {:click (fn [_]
                  (swap! store #(if % nil ui/mark-x)))}}
   @store])

(defscene dimmed-cell
  [::ui/cell.cell-dim
   ui/mark-o])

(defscene highlighted-cell
  [::ui/cell.cell-highlight
   ui/mark-o])

(defscene empty-board
  [:div.board
   [:div.row [ui/cell] [ui/cell] [ui/cell]]
   [:div.row [ui/cell] [ui/cell] [ui/cell]]
   [:div.row [ui/cell] [ui/cell] [ui/cell]]])

(defscene partial-board
  [:div.board
   [:div.row [ui/cell ui/mark-o] [ui/cell] [ui/cell]]
   [:div.row [ui/cell ui/mark-x] [ui/cell ui/mark-o] [ui/cell]]
   [:div.row [ui/cell] [ui/cell] [ui/cell]]])

(defscene winning-board
  [:div.board
   [:div.row
    [ui/cell {:class :cell-dim}]
    [ui/cell {:class :cell-highlight} ui/mark-o]
    [ui/cell {:class :cell-dim}]]
   [:div.row
    [ui/cell {:class :cell-dim} ui/mark-x]
    [ui/cell {:class :cell-highlight} ui/mark-o]
    [ui/cell {:class :cell-dim}]]
   [:div.row
    [ui/cell {:class :cell-dim}]
    [ui/cell {:class :cell-highlight} ui/mark-o]
    [ui/cell {:class :cell-dim} ui/mark-x]]])

(defn main []
  (portfolio/start!
   {:config
    {:css-paths ["/styles.css"]
     :viewport/defaults
     {:background/background-color "#fdeddd"}}}))
