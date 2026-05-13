package ru.samsung.gamestudio.screens;

import static ru.samsung.gamestudio.GameState.PAUSED;
import static ru.samsung.gamestudio.GameState.PLAYING;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;

import ru.samsung.gamestudio.GameResources;
import ru.samsung.gamestudio.GameSession;
import ru.samsung.gamestudio.GameState;
import ru.samsung.gamestudio.MyGdxGame;
import ru.samsung.gamestudio.components.ButtonView;
import ru.samsung.gamestudio.components.ImageView;
import ru.samsung.gamestudio.components.MovingBackgroundView;
import ru.samsung.gamestudio.components.RecordsListView;
import ru.samsung.gamestudio.components.TextView;
import ru.samsung.gamestudio.managers.MemoryManager;

public class MenuScreen extends ScreenAdapter {

    MyGdxGame myGdxGame;
    MovingBackgroundView backgroundView;
    TextView titleView;
    GameSession gameSession;

    ButtonView startButtonView;
    ButtonView settingsButtonView;
    ButtonView recordsButtonView;
    ButtonView exitButtonView;

    ImageView fullBlackoutView;
    TextView recordsTextView;
    RecordsListView recordsListView;
    ButtonView homeButton;

    public MenuScreen (MyGdxGame myGdxGame) {
        this.myGdxGame = myGdxGame;
        backgroundView = new MovingBackgroundView(GameResources.BACKGROUND_IMG_PATH);
        titleView = new TextView(myGdxGame.largeWhiteFont, 180, 960, "Space Cleaner");
        gameSession = new GameSession();

        startButtonView = new ButtonView(140, 646, 440, 70, myGdxGame.commonBlackFont, GameResources.BUTTON_LONG_BG_IMG_PATH, "start");
        settingsButtonView = new ButtonView(140, 551, 440, 70, myGdxGame.commonBlackFont, GameResources.BUTTON_LONG_BG_IMG_PATH, "settings");
        recordsButtonView = new ButtonView(140, 456, 440, 70, myGdxGame.commonBlackFont, GameResources.BUTTON_LONG_BG_IMG_PATH, "records");
        exitButtonView = new ButtonView(140, 361, 440, 70, myGdxGame.commonBlackFont, GameResources.BUTTON_LONG_BG_IMG_PATH, "exit");

        fullBlackoutView = new ImageView(0, 0, GameResources.BLACKOUT_FULL_IMG_PATH);
        recordsListView = new RecordsListView(myGdxGame.commonWhiteFont, 690);
        recordsTextView = new TextView(myGdxGame.largeWhiteFont, 206, 842, "Last records");
        homeButton = new ButtonView(280, 365, 160, 70, myGdxGame.commonBlackFont, GameResources.BUTTON_SHORT_BG_IMG_PATH, "Home");
    }

    @Override
    public void show() {
        gameSession.resumeGame();
    }

    @Override
    public void render(float delta) {

        handleInput();

        myGdxGame.camera.update();
        myGdxGame.batch.setProjectionMatrix(myGdxGame.camera.combined);
        ScreenUtils.clear(Color.CLEAR);

        myGdxGame.batch.begin();

        backgroundView.draw(myGdxGame.batch);

        if (gameSession.state == PLAYING) {
            titleView.draw(myGdxGame.batch);
            exitButtonView.draw(myGdxGame.batch);
            settingsButtonView.draw(myGdxGame.batch);
            recordsButtonView.draw(myGdxGame.batch);
            startButtonView.draw(myGdxGame.batch);
        } else if (gameSession.state == GameState.ENDED) {
            fullBlackoutView.draw(myGdxGame.batch);
            recordsTextView.draw(myGdxGame.batch);
            recordsListView.draw(myGdxGame.batch);
            homeButton.draw(myGdxGame.batch);
        }

        myGdxGame.batch.end();
    }

    private void handleInput() {
        if (Gdx.input.justTouched()) {
            myGdxGame.touch = myGdxGame.camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));

            switch (gameSession.state) {
                case PLAYING:
                    if (startButtonView.isHit(myGdxGame.touch.x, myGdxGame.touch.y)) {
                        myGdxGame.setScreen(myGdxGame.gameScreen);
                    }
                    if (exitButtonView.isHit(myGdxGame.touch.x, myGdxGame.touch.y)) {
                        Gdx.app.exit();
                    }
                    if (recordsButtonView.isHit(myGdxGame.touch.x, myGdxGame.touch.y)) {
                        gameSession.endGame();
                        recordsListView.setRecords(MemoryManager.loadRecordsTable());
                    }
                    if (settingsButtonView.isHit(myGdxGame.touch.x, myGdxGame.touch.y)) {
                        myGdxGame.setScreen(myGdxGame.settingsScreen);
                    }
                    break;
                case ENDED:
                    if (homeButton.isHit(myGdxGame.touch.x, myGdxGame.touch.y)) {
                        gameSession.resumeGame();
                    }
                    break;
            }
        }
    }
}
