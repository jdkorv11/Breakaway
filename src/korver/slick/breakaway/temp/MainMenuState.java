package korver.slick.breakaway.temp;

import org.newdawn.slick.*;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

/**
 * Created by jdkorv11 on 5/19/2016.
 */
public class MainMenuState extends BasicGameState {

    public static final int ID = 0;

    private StateBasedGame game;

    @Override
    public int getID() {
        return ID;
    }

    @Override
    public void init(GameContainer gameContainer, StateBasedGame game) throws SlickException {
        this.game = game;
    }

    @Override
    public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) throws SlickException {
        graphics.setColor(Color.white);
        graphics.drawString("State Based Game Test", 100, 100);
        graphics.drawString("Numbers 1-2 will switch between states.", 150, 300);
        graphics.setColor(Color.red);
        graphics.drawString("This is State 1, the main menu state!", 200, 50);
    }

    @Override
    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int i) throws SlickException {

    }

    public void keyReleased(int key, char c) {
        if (key == Input.KEY_2) {
            game.enterState(PlayState.ID, new FadeOutTransition(Color.black), new FadeInTransition(Color.black));
        }
    }
}