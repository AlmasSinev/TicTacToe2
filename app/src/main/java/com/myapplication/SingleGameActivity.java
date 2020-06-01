package com.myapplication;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class SingleGameActivity extends AppCompatActivity {

    int steps = 1;
    int filledBoxes = 0;
    Random random = new Random();
    StupidStudent bot = new StupidStudent();

    private static final int NO_WIN_BOX = R.drawable.nowin_box;
    private static final int CROSS_BOX = R.drawable.cross_box;
    private static final int ZERO_BOX = R.drawable.zero_box;
    private static final int EMPTY_BOX = R.drawable.empty_box;
    private static final int CROSS_BOX_BG = R.drawable.cross_box_bg;
    private static final int ZERO_BOX_BG = R.drawable.zero_box_bg;
    private static final int EMPTY_BOX_BG = R.drawable.empty_box_bg;

    private int[][] winCombinations = { { 0, 3, 6 }, { 1, 4, 7 }, { 2, 5, 8 }, { 0, 1, 2 },
            { 3, 4, 5 }, { 6, 7, 8 }, { 0, 4, 8 }, { 2, 4, 6 } };

    private int[] globalBoxes = { EMPTY_BOX, EMPTY_BOX, EMPTY_BOX,
            EMPTY_BOX, EMPTY_BOX, EMPTY_BOX,
            EMPTY_BOX, EMPTY_BOX, EMPTY_BOX };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_game);

        final TextView scoreText = findViewById(R.id.textView);
        Button btnHome = findViewById(R.id.home_btn);
        Button btnReset = findViewById(R.id.reset_btn);

        final int GAME_COLOR = ContextCompat.getColor(this, R.color.colorGame);
        final int GAME_RED_COLOR = ContextCompat.getColor(this, R.color.colorRedGame);
        final int GAME_BLUE_COLOR = ContextCompat.getColor(this, R.color.colorBlueGame);
        final int DEFAULT_COLOR = ContextCompat.getColor(this, R.color.colorDefault);

        final Game[] games = { new Game(), new Game(), new Game(),
                               new Game(), new Game(), new Game(),
                               new Game(), new Game(), new Game() };

        final GridView[] grids = { findViewById(R.id.gridView0), findViewById(R.id.gridView1),
                                   findViewById(R.id.gridView2), findViewById(R.id.gridView3),
                                   findViewById(R.id.gridView4), findViewById(R.id.gridView5),
                                   findViewById(R.id.gridView6), findViewById(R.id.gridView7),
                                   findViewById(R.id.gridView8)};

        final ImageView[] images = { findViewById(R.id.img0), findViewById(R.id.img1),
                                     findViewById(R.id.img2), findViewById(R.id.img3),
                                     findViewById(R.id.img4), findViewById(R.id.img5),
                                     findViewById(R.id.img6), findViewById(R.id.img7),
                                     findViewById(R.id.img8) };

        final ItemAdapter[] adapters = { new ItemAdapter(this, games[0].getBoxes()),
                                         new ItemAdapter(this, games[1].getBoxes()),
                                         new ItemAdapter(this, games[2].getBoxes()),
                                         new ItemAdapter(this, games[3].getBoxes()),
                                         new ItemAdapter(this, games[4].getBoxes()),
                                         new ItemAdapter(this, games[5].getBoxes()),
                                         new ItemAdapter(this, games[6].getBoxes()),
                                         new ItemAdapter(this, games[7].getBoxes()),
                                         new ItemAdapter(this, games[8].getBoxes()) };

        for (int i = 0; i < 9; i++) {
            grids[i].setAdapter(adapters[i]);
        }

        for (int i = 0; i < 9; i++) {
            final int finalI = i;
            grids[i].setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    if (games[finalI].getBoxes()[position] == EMPTY_BOX_BG){
                        games[finalI].onGame(position, steps++ % 2 != 0 ? CROSS_BOX_BG : ZERO_BOX_BG);
                        filledBoxes++;
                        scoreText.setText(steps % 2 != 0 ? "ХОД КРЕСТИКОВ" : "ХОД НОЛИКОВ");
                        if (games[finalI].checkWin()){
                            if (games[finalI].finishMiniGameWinner().equals("НИЧЬЯ")){
                                Toast.makeText(SingleGameActivity.this, "Ничья", Toast.LENGTH_SHORT).show();
                                images[finalI].setImageResource(NO_WIN_BOX);
                                globalBoxes[finalI] = NO_WIN_BOX;
                                for (int x : games[finalI].getBoxes()){
                                    if (x == EMPTY_BOX_BG)
                                        filledBoxes++;
                                }
                            }
                            else {
                                Toast.makeText(SingleGameActivity.this, ":)", Toast.LENGTH_SHORT).show();
                                if (games[finalI].finishMiniGameWinner().equals("CROSS")) {
                                    images[finalI].setImageResource(CROSS_BOX);
                                    globalBoxes[finalI] = CROSS_BOX;
                                    for (int x : games[finalI].getBoxes()){
                                        if (x == EMPTY_BOX_BG)
                                            filledBoxes++;
                                    }
                                }
                                else {
                                    images[finalI].setImageResource(ZERO_BOX);
                                    globalBoxes[finalI] = ZERO_BOX;
                                    for (int x : games[finalI].getBoxes()){
                                        if (x == EMPTY_BOX_BG)
                                            filledBoxes++;
                                    }
                                }
                            }
                        }
                        adapters[finalI].setBoxes(games[finalI].getBoxes());
                        grids[finalI].setAdapter(adapters[finalI]);

                        /* Проверка на глобальную выигрышную комбинацию */

                        for (int[] winC : winCombinations) {
                            int winCross = 0;
                            int winZero = 0;
                            for (int index : winC) {
                                if (globalBoxes[index] == CROSS_BOX) winCross++;
                                if (globalBoxes[index] == ZERO_BOX) winZero++;
                            }
                            if (winZero == 3) {
                                scoreText.setText("НОЛИКИ ПОБЕДИЛИ");
                                for (int j = 0; j < 9; j++) {
                                    grids[j].setBackgroundColor(GAME_BLUE_COLOR);
                                    grids[j].setEnabled(false);
                                }
                                return;
                            }
                            if (winCross == 3){
                                scoreText.setText("КРЕСТИКИ ПОБЕДИЛИ");
                                for (int j = 0; j < 9; j++) {
                                    grids[j].setBackgroundColor(GAME_RED_COLOR);
                                    grids[j].setEnabled(false);
                                }
                                return;
                            }
                        }
                        if (filledBoxes > 80) {
                            scoreText.setText("НИЧЬЯ");
                            for (int j = 0; j < 9; j++) {
                                grids[j].setBackgroundColor(GAME_COLOR);
                                grids[j].setEnabled(false);
                            }
                            return;
                        }

                        /* Обновление игровой сетки */

                        for (int j = 0; j < 9; j++) {
                            grids[j].setBackgroundColor(DEFAULT_COLOR);
                            grids[j].setEnabled(false);
                        }
                        int posG = position;
                        if (globalBoxes[posG] ==  EMPTY_BOX){
                            if (steps % 2 == 1) grids[posG].setBackgroundColor(GAME_RED_COLOR);
                            else                grids[posG].setBackgroundColor(GAME_BLUE_COLOR);
                            grids[posG].setEnabled(true);
                        }
                        else {
                            posG = random.nextInt(9);
                            while (globalBoxes[posG] != EMPTY_BOX){
                                posG = random.nextInt(9);
                            }
                            if (steps % 2 == 1) grids[posG].setBackgroundColor(GAME_RED_COLOR);
                            else                grids[posG].setBackgroundColor(GAME_BLUE_COLOR);
                            grids[posG].setEnabled(true);
                        }

                        /* Ход компьютера */

                        bot.setPosition(posG);
                        bot.setBoxes(games[finalI].getBoxes(), finalI);
                        bot.setBoxes(games[posG].getBoxes(), posG);
                        bot.setGlobalBoxes(globalBoxes);
                        int pos = bot.move();

                        /* Проверка на выигрышную комбинацию */

                        games[posG].onGame(pos, steps++ % 2 != 0 ? CROSS_BOX_BG : ZERO_BOX_BG);
                        scoreText.setText(steps % 2 != 0 ? "ХОД КРЕСТИКОВ" : "ХОД НОЛИКОВ");
                        filledBoxes++;
                        if (games[posG].checkWin()){
                            if (games[posG].finishMiniGameWinner().equals("НИЧЬЯ")){
                                Toast.makeText(SingleGameActivity.this, "Ничья", Toast.LENGTH_SHORT).show();
                                images[posG].setImageResource(NO_WIN_BOX);
                                globalBoxes[posG] = NO_WIN_BOX;
                                for (int x : games[posG].getBoxes()){
                                    if (x == EMPTY_BOX_BG)
                                        filledBoxes++;
                                }
                            }
                            else {
                                Toast.makeText(SingleGameActivity.this, ":)", Toast.LENGTH_SHORT).show();
                                if (games[posG].finishMiniGameWinner().equals("CROSS")) {
                                    images[posG].setImageResource(CROSS_BOX);
                                    globalBoxes[posG] = CROSS_BOX;
                                    for (int x : games[posG].getBoxes()){
                                        if (x ==EMPTY_BOX_BG)
                                            filledBoxes++;
                                    }
                                }
                                else {
                                    images[posG].setImageResource(ZERO_BOX);
                                    globalBoxes[posG] = ZERO_BOX;
                                    for (int x : games[posG].getBoxes()){
                                        if (x == EMPTY_BOX_BG)
                                            filledBoxes++;
                                    }
                                }
                            }
                        }
                        adapters[posG].setBoxes(games[posG].getBoxes());
                        grids[posG].setAdapter(adapters[posG]);

                        /* Проверка на глобальную выигрышную комбинацию */

                        int winCrossBot;
                        int winZeroBot;
                        for (int[] winC : winCombinations) {
                            winCrossBot = 0;
                            winZeroBot = 0;
                               for (int index : winC) {
                                   if (globalBoxes[index] == CROSS_BOX) winCrossBot++;
                                   if (globalBoxes[index] == ZERO_BOX) winZeroBot++;
                               }
                               if (winZeroBot == 3) {
                                   scoreText.setText("НОЛИКИ ПОБЕДИЛИ");
                                   for (int j = 0; j < 9; j++) {
                                       grids[j].setBackgroundColor(GAME_BLUE_COLOR);
                                       grids[j].setEnabled(false);
                                   }
                                   return;
                               }
                               if (winCrossBot == 3){
                                   scoreText.setText("КРЕСТИКИ ПОБЕДИЛИ");
                                   for (int j = 0; j < 9; j++) {
                                       grids[j].setBackgroundColor(GAME_RED_COLOR);
                                       grids[j].setEnabled(false);
                                   }
                                   return;
                               }
                        }
                        if (filledBoxes > 80) {
                            scoreText.setText("НИЧЬЯ");
                            for (int j = 0; j < 9; j++) {
                                grids[j].setBackgroundColor(GAME_COLOR);
                                grids[j].setEnabled(false);
                            }
                            return;
                        }

                        /* Обновление игровой сетки */

                        for (int j = 0; j < 9; j++) {
                            grids[j].setBackgroundColor(DEFAULT_COLOR);
                            grids[j].setEnabled(false);
                        }

                        if (globalBoxes[pos] ==  EMPTY_BOX){
                            if (steps % 2 == 1) grids[pos].setBackgroundColor(GAME_RED_COLOR);
                            else                grids[pos].setBackgroundColor(GAME_BLUE_COLOR);
                            grids[pos].setEnabled(true);
                        }
                        else {
                            posG = random.nextInt(9);
                            while (globalBoxes[posG] != EMPTY_BOX){
                                posG = random.nextInt(9);
                            }
                            if (steps % 2 == 1) grids[posG].setBackgroundColor(GAME_RED_COLOR);
                            else                grids[posG].setBackgroundColor(GAME_BLUE_COLOR);
                            grids[posG].setEnabled(true);
                        }
                    }
                }
            });
        }

        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SingleGameActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < 9; i++) {
                    steps = 1;
                    filledBoxes = 0;
                    games[i].resetTable();
                    bot = new StupidStudent();
                    globalBoxes[i] = EMPTY_BOX;
                    adapters[i].setBoxes(games[i].getBoxes());
                    grids[i].setEnabled(true);
                    grids[i].setAdapter(adapters[i]);
                    grids[i].setBackgroundColor(DEFAULT_COLOR);
                    scoreText.setText("НАЧИНАЮТ КРЕСТИКИ");
                    images[i].setImageResource(EMPTY_BOX);


                }
            }
        });
    }
}
