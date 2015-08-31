package elemental.com.game;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.elemental.game.R;

import java.util.ArrayList;
import java.util.List;

public class FightGameView extends SurfaceView {
    public static final int MOVE_STEP = 30;
    private final GameLoopThread gameLoopThread;
    private Bitmap bmp;
    private SurfaceHolder holder;
    private int x = 0;
    private int y = 0;
    private boolean changeHorizontal = false;
    private boolean changeVertical = false;
    private List<Character> charactersList = new ArrayList<Character>();
    private long lastClick;

    public FightGameView(Context context) {
        super(context);
        gameLoopThread = new GameLoopThread(this);
        holder = getHolder();
        holder.addCallback(new FightHolderCallback(this));
    }

    public GameLoopThread getThread() {
        return gameLoopThread;
    }

    public void createCharacters() {
        charactersList.add(createCharacter(R.drawable.char1));
        charactersList.add(createCharacter(R.drawable.char2));
        charactersList.add(createCharacter(R.drawable.char3));
        charactersList.add(createCharacter(R.drawable.char4));
        charactersList.add(createCharacter(R.drawable.char5));
        charactersList.add(createCharacter(R.drawable.char6));
        charactersList.add(createCharacter(R.drawable.char7));
        charactersList.add(createCharacter(R.drawable.char8));
        charactersList.add(createCharacter(R.drawable.char9));
        charactersList.add(createCharacter(R.drawable.char10));
        charactersList.add(createCharacter(R.drawable.char11));
        charactersList.add(createCharacter(R.drawable.char12));
    }

    public void checkCharacterColision() {
        for (Character character : charactersList) {
            for (Character character2 : charactersList) {
                if(character != character2) {
                    if (character.checkColision2(character2.getX(), character2.getY())) {
                        character.changeDirection();
                        character2.changeDirection();
                    }
                }
            }
        }
    }

    private Character createCharacter(int resouce) {
        Bitmap bmp = BitmapFactory.decodeResource(getResources(), resouce);
        return new Character(this.getWidth(), this.getHeight(), bmp);
    }

    public void draw(Canvas canvas) {
        canvas.drawColor(Color.BLACK);
        for (Character character : charactersList) {
            character.onDraw(canvas);
            checkCharacterColision();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (System.currentTimeMillis() - lastClick > 500) {
            lastClick = System.currentTimeMillis();
            synchronized (getHolder()) {
                for (int i = charactersList.size() - 1; i >= 0; i--) {
                    Character character = charactersList.get(i);
                    if (character.checkColision(event.getX(), event.getY())) {
                        charactersList.remove(character);
                        break;
                    }
                }
            }
        }
        return true;
    }
}
