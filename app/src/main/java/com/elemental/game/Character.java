package elemental.com.game;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

import java.util.Random;

public class Character {
    // direction = 0 up, 1 left, 2 down, 3 right,
    // animation = 3 back, 1 left, 0 front, 2 right
    int[] DIRECTION_TO_ANIMATION_MAP = { 3, 1, 0, 2 };
    private static final int BMP_ROWS = 4;
    private static final int BMP_COLUMNS = 3;
    private static final int MAX_SPEED = 5;
    private Bitmap bmp;
    private int x = 0;
    private int y = 0;
    private int xSpeed;
    private int ySpeed;
    private int currentFrame = 0;
    private int width;
    private int height;
    private int screenWidth;
    private int screenHeight;

    public Character(int screenWidth, int screenHeight, Bitmap bmp) {

        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;

        this.width = bmp.getWidth() / BMP_COLUMNS;
        this.height = bmp.getHeight() / BMP_ROWS;
        this.bmp = bmp;

        Random rnd = new Random();
        x = rnd.nextInt(screenWidth - width);
        y = rnd.nextInt(screenHeight - height);
        xSpeed = rnd.nextInt(MAX_SPEED * 2) - MAX_SPEED;
        ySpeed = rnd.nextInt(MAX_SPEED * 2) - MAX_SPEED;
    }

    private void update() {
        if (x >= screenWidth - width - xSpeed || x + xSpeed <= 0) {
            changeXDirection();
        }
        x = x + xSpeed;
        if (y >= screenHeight - height - ySpeed || y + ySpeed <= 0) {
            changeYDirection();
        }
        y = y + ySpeed;
        currentFrame = ++currentFrame % BMP_COLUMNS;
    }

    public void changeXDirection() {
        xSpeed = -xSpeed;
    }

    public void changeYDirection() {
        ySpeed = -ySpeed;
    }

    public  void changeDirection() {
        changeXDirection();
        changeYDirection();
    }

    public void onDraw(Canvas canvas) {
        update();
        int srcX = currentFrame * width;
        int srcY = getAnimationRow() * height;
        Rect src = new Rect(srcX, srcY, srcX + width, srcY + height);
        Rect dst = new Rect(x, y, x + width, y + height);
        canvas.drawBitmap(bmp, src, dst, null);
    }

    private int getAnimationRow() {
        double dirDouble = (Math.atan2(xSpeed, ySpeed) / (Math.PI / 2) + 2);
        int direction = (int) Math.round(dirDouble) % BMP_ROWS;
        return DIRECTION_TO_ANIMATION_MAP[direction];
    }

    public boolean checkColision(float x2, float y2) {
        return x2 > x && x2 < x + width && y2 > y && y2 < y + height;
    }

    public boolean checkColision2(float x2, float y2) {
        return (x >= x2 && x <= x2 + width) || (y >= y2 && y <= y2 + height);
    }


    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}