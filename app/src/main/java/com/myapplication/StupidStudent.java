package com.myapplication;

import java.util.Random;

class StupidStudent {

    private Random random = new Random();
    private int position;

    private static final int EMPTY_BOX_BG = R.drawable.empty_box_bg;
    private static final int ZERO_BOX_BG = R.drawable.zero_box_bg;
    private static final int CROSS_BOX_BG = R.drawable.cross_box_bg;

    private static final int EMPTY_BOX = R.drawable.empty_box;
    private static final int ZERO_BOX = R.drawable.zero_box;
    private static final int CROSS_BOX = R.drawable.cross_box;
    private static final int NO_WIN_BOX = R.drawable.nowin_box;

    private int[][] winCombinations = { { 0, 3, 6 }, { 1, 4, 7 }, { 2, 5, 8 }, { 0, 1, 2 },
            { 3, 4, 5 }, { 6, 7, 8 }, { 0, 4, 8 }, { 2, 4, 6 } };

    private int[][] boxes = { { EMPTY_BOX_BG, EMPTY_BOX_BG, EMPTY_BOX_BG, EMPTY_BOX_BG, EMPTY_BOX_BG, EMPTY_BOX_BG, EMPTY_BOX_BG, EMPTY_BOX_BG, EMPTY_BOX_BG },
                              { EMPTY_BOX_BG, EMPTY_BOX_BG, EMPTY_BOX_BG, EMPTY_BOX_BG, EMPTY_BOX_BG, EMPTY_BOX_BG, EMPTY_BOX_BG, EMPTY_BOX_BG, EMPTY_BOX_BG },
                              { EMPTY_BOX_BG, EMPTY_BOX_BG, EMPTY_BOX_BG, EMPTY_BOX_BG, EMPTY_BOX_BG, EMPTY_BOX_BG, EMPTY_BOX_BG, EMPTY_BOX_BG, EMPTY_BOX_BG },
                              { EMPTY_BOX_BG, EMPTY_BOX_BG, EMPTY_BOX_BG, EMPTY_BOX_BG, EMPTY_BOX_BG, EMPTY_BOX_BG, EMPTY_BOX_BG, EMPTY_BOX_BG, EMPTY_BOX_BG },
                              { EMPTY_BOX_BG, EMPTY_BOX_BG, EMPTY_BOX_BG, EMPTY_BOX_BG, EMPTY_BOX_BG, EMPTY_BOX_BG, EMPTY_BOX_BG, EMPTY_BOX_BG, EMPTY_BOX_BG },
                              { EMPTY_BOX_BG, EMPTY_BOX_BG, EMPTY_BOX_BG, EMPTY_BOX_BG, EMPTY_BOX_BG, EMPTY_BOX_BG, EMPTY_BOX_BG, EMPTY_BOX_BG, EMPTY_BOX_BG },
                              { EMPTY_BOX_BG, EMPTY_BOX_BG, EMPTY_BOX_BG, EMPTY_BOX_BG, EMPTY_BOX_BG, EMPTY_BOX_BG, EMPTY_BOX_BG, EMPTY_BOX_BG, EMPTY_BOX_BG },
                              { EMPTY_BOX_BG, EMPTY_BOX_BG, EMPTY_BOX_BG, EMPTY_BOX_BG, EMPTY_BOX_BG, EMPTY_BOX_BG, EMPTY_BOX_BG, EMPTY_BOX_BG, EMPTY_BOX_BG },
                              { EMPTY_BOX_BG, EMPTY_BOX_BG, EMPTY_BOX_BG, EMPTY_BOX_BG, EMPTY_BOX_BG, EMPTY_BOX_BG, EMPTY_BOX_BG, EMPTY_BOX_BG, EMPTY_BOX_BG } };

    private int[] globalBoxes;

    int move() {
//        int step = random.nextInt(9);
//        while(boxes[position][step] != EMPTY_BOX_BG){
//            step = random.nextInt(9);
//        }
//        return step;

        int step = checkChance(position);
        if (step == -1) step = checkUserWin(position);
        if (step == -1) {
            step = random.nextInt(9);
            while (boxes[position][step]  != EMPTY_BOX_BG){
                step = random.nextInt(9);
            }
        }
        return step;
    }

    private int checkUserWin(int gPos) {
            for (int[] comb : winCombinations) {
                int c = 0;
                for (int pos : comb) {
                    if (boxes[gPos][pos] == CROSS_BOX_BG ) c++;
                }
                if (c == 2) {
                    for (int pos : comb) {
                        if (boxes[gPos][pos] == EMPTY_BOX_BG ) return pos;
                    }
                }
            }
        return -1;
    }

    private int checkChance(int gPos) {
        for (int[] comb : winCombinations) {
            int c = 0;
            for (int pos : comb) {
                if (boxes[gPos][pos] == ZERO_BOX_BG ) c++;
            }
            if (c == 2) {
                for (int pos : comb) {
                    if (boxes[gPos][pos] == EMPTY_BOX_BG ) return pos;
                }
            }
        }
        return -1;
    }

    void setBoxes(int[] boxes, int pos) {
        this.boxes[pos] = boxes;
    }

    int[] getBoxes(int index) {
        return boxes[index];
    }

    void setGlobalBoxes(int[] gBoxes) {
        globalBoxes = gBoxes;
    }

    void setPosition(int pos) {
        position = pos;
    }
}
