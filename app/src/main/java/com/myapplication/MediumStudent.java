package com.myapplication;

import java.util.Arrays;
import java.util.Random;

public class MediumStudent {
    private Random random = new Random();
    private int position;
    private int countOfSteps = 0;

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
    private int[] winCompbination = {-1, -1, -1};

    int move() {

        int step = -1;

        if (countOfSteps > 6){
            checkGlobalChance();

            step = random.nextInt(9);
            while(boxes[position][step] != EMPTY_BOX_BG && step != winCompbination[0] && step != winCompbination[1] && step != winCompbination[2]){
                step = random.nextInt(9);
            }
        }
        else {
            step = checkChance(position);
            if (step == -1) step = checkUserWin(position);
            if (step == -1) {
                step = random.nextInt(9);
                while (boxes[position][step]  != EMPTY_BOX_BG){
                    step = random.nextInt(9);
                }
            }
            countOfSteps++;
        }
        return step;
    }

    private int checkUserWin(int gPos) {
        for (int[] comb : winCombinations) {
            int c = 0;
            for (int pos : comb) {
                if (boxes[gPos][pos] == CROSS_BOX_BG ) c++;
                if (boxes[gPos][pos] == ZERO_BOX_BG ) c = -100;
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
                if (boxes[gPos][pos] == CROSS_BOX_BG ) c = -100;

            }
            if (c >= 2) {
                for ( int pos : comb ) {
                    if (boxes[gPos][pos] == EMPTY_BOX_BG ) return pos;
                }
            }
        }
        return -1;
    }

    private void checkGlobalUserWin() {
        for (int[] comb : winCombinations) {
            int chance = 0;
            for (int globalPosition : comb) {
                if (checkUserWin(globalPosition) != -1) chance++;
            }
            if ( chance >= 2 ) {
                winCompbination = comb;
                break;
            }
        }
    }

    private void checkGlobalChance() {
        for (int[] comb : winCombinations) {
            int chance = 0;
            for (int globalPosition : comb) {
                if (checkChance(globalPosition) != -1 || globalBoxes[globalPosition] == ZERO_BOX) chance++;
            }
            if ( chance >= 2 ) {
                winCompbination = comb;
                break;
            }
        }
    }

    void setBoxes(int[] boxes, int pos) {
        this.boxes[pos] = boxes;
    }

    int[] getBoxes(int index) {
        return boxes[index];
    }

    String getWins(){
        StringBuilder sb = new StringBuilder("");
        for (int x : winCompbination)
            sb.append(x + " ");
        return sb.toString();
    }

    void setGlobalBoxes(int[] gBoxes) {
        globalBoxes = gBoxes;
    }

    void setPosition(int pos) {
        position = pos;
    }
}
