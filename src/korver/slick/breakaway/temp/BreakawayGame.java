package korver.slick.breakaway.temp;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

/**
 * Created by jdkorv11 on 5/19/2016.
 */
public class BreakawayGame extends StateBasedGame {

    public BreakawayGame() {
        super("Breakaway");
    }

    @Override
    public void initStatesList(GameContainer gameContainer) throws SlickException {
        this.addState(new MainMenuState());
        this.addState(new PlayState());
        this.addState((new PauseState()));
    }

    public static void main(String[] args){
        try{
            AppGameContainer container = new AppGameContainer(new BreakawayGame());
            container.setDisplayMode(1200,840,false);
            container.start();
        } catch (SlickException e){
            e.printStackTrace();
        }
    }
}
