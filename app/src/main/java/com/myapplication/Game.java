package com.myapplication;

class Game {

    private int filledBoxes = 0;
    private String winner;
    private static final int CROSS_BOX = R.drawable.cross_box_bg;
    private static final int ZERO_BOX = R.drawable.zero_box_bg;
    private static final int EMPTY_BOX = R.drawable.empty_box_bg;

    private int[] boxes = { EMPTY_BOX, EMPTY_BOX, EMPTY_BOX,
            EMPTY_BOX, EMPTY_BOX, EMPTY_BOX,
            EMPTY_BOX, EMPTY_BOX, EMPTY_BOX };

    private int[][] winCombinations = { { 0, 3, 6 }, { 1, 4, 7 }, { 2, 5, 8 }, { 0, 1, 2 },
                                        { 3, 4, 5 }, { 6, 7, 8 }, { 0, 4, 8 }, { 2, 4, 6 } };

    void onGame(int position, int BOX) {
        boxes[position] = BOX;
        filledBoxes++;
    }

    boolean checkWin() {
        if (filledBoxes <= 9) {
            int winCross;
            int winZero;
            for (int[] winC : winCombinations) {
                winCross = 0;
                winZero = 0;
                for (int index : winC) {
                    if (boxes[index] == CROSS_BOX) winCross++;
                    if (boxes[index] == ZERO_BOX) winZero++;
                }
                if (winZero == 3) {
                    winner = "ZERO";
                    return true;
                }
                if (winCross == 3){
                    winner = "CROSS";
                    return true;
                }
            }
            if (filledBoxes == 9) {
                winner = "НИЧЬЯ";
                return true;
            }
        }
        return false;
    }

    String finishMiniGameWinner() {
        return winner;
    }

    int[] getBoxes() {
        return boxes;
    }

    void setBoxes(int[] bs) {
        boxes = bs;
    }


    void resetTable(){
        filledBoxes = 0;
        for (int i = 0; i < 9; i++) {
            boxes[i] = EMPTY_BOX;
        }
    }
}
