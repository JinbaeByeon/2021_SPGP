package kr.ac.kpu.game.s2015182013.cookierun;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import kr.ac.kpu.game.s2015182013.cookierun.framework.view.GameView;
import kr.ac.kpu.game.s2015182013.cookierun.game.MainGame;

public class MainActivity extends AppCompatActivity {

    private MainGame mainGame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mainGame = new MainGame();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onPause() {
        super.onPause();
        GameView.view.pauseGame();
    }
}