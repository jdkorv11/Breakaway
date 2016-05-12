package korver.breakaway.logic;

import korver.breakaway.engine.Utilities;
import korver.breakaway.entities.Ball;
import korver.breakaway.entities.Block;
import korver.breakaway.entities.Bumper;
import korver.breakaway.physics.Vector;

import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jdkorv11 on 3/28/2016.
 */
public class Game {

    private static final int STARTING_LIVES = 3;
    private final int BOARD_WIDTH = 1500;
    private final int BOARD_HEIGHT = 840;
    private final int WALL_THICKNESS = 20;
    private final int BUMPER_SPEED_LIMIT = 20;
    private final int DEFAULT_BALL_SPEED = 10;
    private final int OFF_SCREEN_MARGIN = 50;
    private final InputHandler inputHandler;
    private GameState gameState;
    private Point testPoint = new Point(0, 0);
    private List<Block> hitBlocks = new ArrayList();


    public Game() {
        gameState = new GameState();
        inputHandler = new InputHandler();
        // create a gameState.bumper and center it on the bottom of the board
        initializeGameState();
    }
    public Game(GameState gameState){
        this.gameState = gameState;
        inputHandler = new InputHandler();
    }

    private void initializeGameState() {

        // initialize bumper
        gameState.bumper = new Bumper(testPoint);
        gameState.bumper.setLocation(new Point((int) ((BOARD_WIDTH / 2) - (gameState.bumper.getWidth() / 2)),
                                               (int) (BOARD_HEIGHT - gameState.bumper.getHeight())));
        gameState.livesLeft = STARTING_LIVES;
        // initialize blocks
        int y = 0;
        int x = 0;
        Block b = new Block(0, 0);
        List<Block> blockList = gameState.blockList;
        while (x < BOARD_WIDTH) {
            b = new Block(x, y);
            blockList.add(b);
            x = x + (b.width * 2);
        }
        x = b.width;
        y = b.height * 2;
        while (x < BOARD_WIDTH) {
            b = new Block(x, y);
            blockList.add(b);
            x = x + (b.width * 2);
        }
        // initialize ball
        initializeBall();


    }


    public void update() {
        if (isEndOfLevel()) {
            endGame();
            return;
        }
        if (isAlive()) {
            if (inputHandler.isBumperMove()) {
                submitBumperMove(inputHandler.consumeBumperMove());
            }
            if (inputHandler.isLaunchQueued()) {
                attemptBallLaunch();
            }
            processBallMove();
        } else {
            looseLife();
            initializeBall();
        }
    }

    private void initializeBall() {
        gameState.ball = new Ball(testPoint);
        gameState.ball.setLocation(
                new Point(gameState.bumper.x + (gameState.bumper.width / 2) - gameState.ball.width / 2,
                          gameState.bumper.y - (int) (gameState.ball.height * 0.9)));
        gameState.ball.setSpeed(0);
        gameState.bumper.hasBall = true;
    }

    private void looseLife() {
        gameState.livesLeft--;
    }

    private void endGame() {
        inputHandler.getInputReader().unlockMouse();
    }

    private boolean isEndOfLevel() {
        return gameState.blockList.size() == 0 || gameState.livesLeft < 0;
    }

    private boolean isAlive() {
        return gameState.ball.y < BOARD_HEIGHT + OFF_SCREEN_MARGIN;
    }

    private void attemptBallLaunch() {
        // verify the ball is on the gameState.bumper and launch if it is
        if (gameState.bumper.hasBall) {
            launchBall();
        }
    }

    private void launchBall() {
        // get a random direction for the ball
        int xVel;
        int xVel1 = Utilities.randomWithRange(-5, -2);
        int xVel2 = Utilities.randomWithRange(2, 5);
        int xVelSelector = Utilities.randomWithRange(1, 2);
        if (xVelSelector == 1) {
            xVel = xVel1;
        } else {
            xVel = xVel2;
        }
        int yVel = Utilities.randomWithRange(-5, -2);
        // set the ball's vector
        gameState.ball.setVector(new Vector(xVel, yVel, DEFAULT_BALL_SPEED));

        // remove ball from gameState.bumper
        gameState.bumper.hasBall = false;
    }

    private void processBallMove() {
        int dx = gameState.ball.getVector().getXVelocity();
        int dy = gameState.ball.getVector().getYVelocity();

        if (willCollideWithWall(gameState.ball, dx, dy)) {
            reflectBallOffWall();
        }
        if (willCollide(gameState.ball, dx, dy, gameState.bumper)) {
            reflectBallOffBumper();
        }
        for (Block block : gameState.blockList) {
            if (willCollide(gameState.ball, dx, dy, block)) {
                reflectBallOffObject(dx, dy, block);
                recordHit(block);
            }
        }
        fatigueHitBlocks();
        moveBall();
    }

    private void recordHit(Block block) {
        hitBlocks.add(block);
    }

    private void fatigueHitBlocks() {
        while (hitBlocks.size() > 0) {
            gameState.blockList.remove(hitBlocks.get(0));
            hitBlocks.remove(0);
        }
    }

    private void moveBall() {
        gameState.ball.x += gameState.ball.getVector().getXVelocity();
        gameState.ball.y += gameState.ball.getVector().getYVelocity();
    }

    private void reflectBallOffBumper() {
        // reflect the ball
        int dx = gameState.ball.getVector().getXVelocity();
        int dy = gameState.ball.getVector().getYVelocity();
        reflectBallOffObject(dx, dy, gameState.bumper);

        // prevent deadlock by moving the ball up so the next step will not collide with the gameState.bumper
        dy = gameState.ball.getVector().getYVelocity();
        dx = gameState.ball.getVector().getXVelocity();
        while (!gameState.bumper.hasBall && willCollide(gameState.ball, dx, dy, gameState.bumper)) {
            gameState.ball.x += dx;
            gameState.ball.y += dy;
        }
    }

    private void reflectBallOffObject(int dx, int dy, Rectangle rectangle) {
        Vector ballDir = gameState.ball.getVector();
        boolean collidesNow = willCollide(gameState.ball, 0, 0, rectangle);
        if (willCollide(gameState.ball, dx, 0, rectangle) && !collidesNow) {
            dx = dx * -1;
        }
        if (willCollide(gameState.ball, 0, dy, rectangle) && !collidesNow) {
            dy = dy * -1;
        }
        ballDir.setRelXVelocity(dx);
        ballDir.setRelYVelocity(dy);

    }

    private void reflectBallOffWall() {
        Vector ballDir = gameState.ball.getVector();
        int dx = ballDir.getXVelocity();
        int dy = ballDir.getYVelocity();
        if (willHitLeftWall(gameState.ball, dx) || willHitRightWall(gameState.ball, dx)) {
            ballDir.setRelXVelocity(-1 * ballDir.getRelXVelocity());
        }
        if (willHitTopWall(gameState.ball, dy)) {
            ballDir.setRelYVelocity(-1 * ballDir.getRelYVelocity());
        }
    }

    /**
     * Checks to see if the projectile will collide with the object when it is moved by specified amounts
     *
     * @param projectile
     *         the rectangle to see if will collide when moved
     * @param dx
     *         the amount to move in the x direction
     * @param dy
     *         the amount to move in the y direction
     * @param object
     *         the object to see if the projectile collides with
     * @return whether the projectile will collide with the object when moved by dx and dy
     */
    private boolean willCollide(Rectangle projectile, int dx, int dy, Rectangle object) {
        projectile.x += dx;
        projectile.y += dy;
        boolean collides = projectile.intersects(object);
        projectile.x -= dx;
        projectile.y -= dy;
        return collides;
    }

    public int getHeight() {
        return BOARD_HEIGHT;
    }

    public int getWidth() {
        return BOARD_WIDTH;
    }

    public int getWallThickness() {
        return WALL_THICKNESS;
    }

    public Bumper getBumper() {
        return gameState.bumper;
    }

    public Ball getBall() {
        return gameState.ball;
    }

    public void submitBumperMove(int move) {
        if (move < -1 * BUMPER_SPEED_LIMIT) {
            move = -1 * BUMPER_SPEED_LIMIT;
        } else if (move > BUMPER_SPEED_LIMIT) {
            move = BUMPER_SPEED_LIMIT;
        }

        testPoint.x = gameState.bumper.x + move;
        testPoint.y = gameState.bumper.y;

        while (testPoint.x != gameState.bumper.x) {
            if (!willCollideWithWall(gameState.bumper, move, 0)) {
                gameState.bumper.x = gameState.bumper.x + move;
                if (gameState.bumper.hasBall) {
                    gameState.ball.x = gameState.ball.x + move;
                }
            } else {
                move = shrinkStep(move);
                testPoint.x = gameState.bumper.x + move;
            }
        }

    }

    private int shrinkStep(int step) {
        if (step < 0) {
            return step + 1;
        } else if (step > 0) {
            return step - 1;
        }
        return step;

    }

    private boolean willCollideWithWall(Rectangle rect, int dx, int dy) {

        return willHitLeftWall(rect, dx) || willHitTopWall(rect, dy) || willHitRightWall(rect, dx);
    }

    private boolean willHitRightWall(Rectangle rect, int dx) {
        return (rect.x + dx + rect.width) > BOARD_WIDTH;
    }

    private boolean willHitTopWall(Rectangle rect, int dy) {
        return (rect.y + dy) < 0;
    }

    private boolean willHitLeftWall(Rectangle rect, int dx) {
        return (rect.x + dx) < 0;
    }

    public InputHandler getInputHandler() {
        return inputHandler;
    }

    public List<Block> getBlocks() {
        return gameState.blockList;
    }

    public GameState getState() {
        return this.gameState;
    }
}
