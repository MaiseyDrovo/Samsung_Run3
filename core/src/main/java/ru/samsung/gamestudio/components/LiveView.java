package ru.samsung.gamestudio.components;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import ru.samsung.gamestudio.GameResources;
import ru.samsung.gamestudio.GameSettings;

public class LiveView extends View {

    private final static int livePadding = 6;

    private Texture texture;

    private int leftLives;

    public LiveView(float x, float y) {
        super(x, y);
        texture = new Texture(GameResources.LIVE_IMG_PATH);
        this.width = texture.getWidth();
        this.height = texture.getHeight();
        leftLives = 0;
    }

    public void setLeftLives(int leftLives) {
        this.leftLives = leftLives;
    }

    @Override
    public void draw(SpriteBatch batch) {
        for (int i = 0 ; i < leftLives ; i++) {
            if (i % 2 == 0) batch.draw(texture, x = GameSettings.SCREEN_WIDTH / 2 + i * (texture.getWidth() + livePadding) / 2, y, width, height);
            if (i % 2 != 0) batch.draw(texture, x = GameSettings.SCREEN_WIDTH / 2 - (i + 1) * (texture.getWidth() + livePadding) / 2, y, width, height);
        }
    }

    @Override
    public void dispose() {
        texture.dispose();
    }


}
