package elemental.com.game;

import android.view.SurfaceHolder;

/**
 * Created by Fernando on 8/27/2015.
 */
public class FightHolderCallback implements SurfaceHolder.Callback {

    private FightGameView fightGameView;

    public FightHolderCallback(FightGameView fightGameView) {
        this.fightGameView = fightGameView;
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        GameLoopThread thread = fightGameView.getThread();
        fightGameView.createCharacters();
        thread.setRunning(true);
        thread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        GameLoopThread thread = fightGameView.getThread();
        boolean retry = true;
        thread.setRunning(false);
        while (retry) {
            try {
                thread.join();
                retry = false;
            } catch (InterruptedException e) {
            }
        }
    }
}
