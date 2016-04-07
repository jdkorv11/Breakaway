package korver.breakaway.engine;

import korver.breakaway.logic.Game;

import java.awt.*;
import java.text.DecimalFormat;

/**
 * Created by jdkorv11 on 3/28/2016.
 */
public class Loop implements Runnable {

    private static final int NO_DELAYS_PER_YIELD = 16;
    // private static long MAX_STATS_INTERVAL = 1000L;
    // record stats every 1 second (roughly)
    private static long MAX_STATS_INTERVAL = 1000000000L;
    /*
	 * Number of frames with a delay of 0 ms before the animation thread yields
	 * to other running threads.
	 */
    private static int MAX_FRAME_SKIPS = 4; // was 2;
    // no. of frames that can be skipped in any one animation loop
    // i.e the games state is updated but not rendered

    private Application displayApp;

    private static int NUM_FPS = 10;
    // number of FPS values stored to get an average

    // used for gathering statistics
    private long statsInterval = 0L; // in ns
    private long prevStatsTime;
    private long totalElapsedTime = 0L;
    private long gameStartTime;
    private int timeSpentInGame = 0; // in seconds

    private long frameCount = 0;
    private double fpsStore[];
    private long statsCount = 0;
    private double averageFPS = 0.0;

    private long framesSkipped = 0L;
    private long totalFramesSkipped = 0L;
    private double upsStore[];
    private double averageUPS = 0.0;

    private DecimalFormat df = new DecimalFormat("0.##"); // 2 dp
    private DecimalFormat timedf = new DecimalFormat("0.####"); // 4 dp

    private Thread animator; // the thread that performs the animation
    private volatile boolean running = false; // used to stop the animation
    // thread
    private volatile boolean isPaused = false;

    private long period; // period between drawing in _nanosecs_

    // used at game termination
    private volatile boolean gameOver = false;
    private int score = 0;
    private Font font;
    private FontMetrics metrics;


    private Game game;

    public Loop(long period, Application display, Game game) {
//        wcTop = wc;
        // create game components
        this.game = game;
        this.displayApp = display;
        gameOver = false;

        this.period = period;
        // the JPanel now has focus, so receives key events
        readyForTermination();


        // set up message font
        font = new Font("SansSerif", Font.BOLD, 24);
      //  metrics = this.getFontMetrics(font);

        // initialise timing elements
        fpsStore = new double[NUM_FPS];
        upsStore = new double[NUM_FPS];
        for (int i = 0; i < NUM_FPS; i++) {
            fpsStore[i] = 0.0;
            upsStore[i] = 0.0;
        }
    }

    private void readyForTermination() {
//        addKeyListener(new KeyAdapter() {
//            // listen for esc, q, end, ctrl-c on the gamePanel to
//            // allow a convenient exit from the full screen configuration
//            public void keyPressed(KeyEvent e) {
//                int keyCode = e.getKeyCode();
//                if ((keyCode == KeyEvent.VK_ESCAPE) || (keyCode == KeyEvent.VK_Q)
//                        || (keyCode == KeyEvent.VK_END)
//                        || ((keyCode == KeyEvent.VK_C) && e.isControlDown())) {
//                    running = false;
//                }
//                if (keyCode == KeyEvent.VK_ENTER){
//                    gameOver = true;
//                }
//            }
//        });
    }

    public void run()
	/* The frames of the animation are drawn inside the while loop. */ {
        long beforeTime, afterTime, timeDiff, sleepTime;
        long overSleepTime = 0L;
        int noDelays = 0;
        long excess = 0L;

        gameStartTime = System.nanoTime();
        prevStatsTime = gameStartTime;
        beforeTime = gameStartTime;

        running = true;

        while (running) {
            gameUpdate();
            gameDisplay();

            afterTime = System.nanoTime();
            timeDiff = afterTime - beforeTime;
            sleepTime = (period - timeDiff) - overSleepTime;

            if (sleepTime > 0) { // some time left in this cycle
                try {
                    Thread.sleep(sleepTime / 1000000L); // nano -> ms
                } catch (InterruptedException ex) {
                }
                overSleepTime = (System.nanoTime() - afterTime) - sleepTime;
            } else { // sleepTime <= 0; the frame took longer than the period
                excess -= sleepTime; // store excess time value
                overSleepTime = 0L;

                if (++noDelays >= NO_DELAYS_PER_YIELD) {
                    Thread.yield(); // give another thread a chance to run
                    noDelays = 0;
                }
            }

            beforeTime = System.nanoTime();

			/*
			 * If frame animation is taking too long, update the game state
			 * without rendering it, to get the updates/sec nearer to the
			 * required FPS.
			 */
            int skips = 0;
            while ((excess > period) && (skips < MAX_FRAME_SKIPS)) {
                excess -= period;
                gameUpdate(); // update state but don't render
                skips++;
            }
            excess = 0;
            framesSkipped += skips;

            storeStats();
        }

        printStats();
        System.exit(0); // so window disappears
    } // end of run()

    private void gameUpdate() {
        if (!isPaused && !gameOver) {
            game.update();
        }
    }

    private void gameDisplay() {

        displayApp.renderGame(game);
        displayApp.paintScreen();
    }

//    private void gameOverMessage(Graphics g)
//    // center the game-over message in the panel
//    {
//        String msg = "Game Over. Your Score: " + score;
//        int x = (pWidth - metrics.stringWidth(msg)) / 2;
//        int y = (pHeight - metrics.getHeight()) / 2;
//        g.setColor(Color.red);
//        g.setFont(font);
//        g.drawString(msg, x, y);
//    }

    private void storeStats()
	/*
	 * The statistics: - the summed periods for all the iterations in this
	 * interval (period is the amount of time a single frame iteration should
	 * take), the actual elapsed time in this interval, the error between these
	 * two numbers;
	 *
	 * - the total frame count, which is the total number of calls to run();
	 *
	 * - the frames skipped in this interval, the total number of frames
	 * skipped. A frame skip is a game update without a corresponding render;
	 *
	 * - the FPS (frames/sec) and UPS (updates/sec) for this interval, the
	 * average FPS & UPS over the last NUM_FPSs intervals.
	 *
	 * The data is collected every MAX_STATS_INTERVAL (1 sec).
	 */ {
        frameCount++;
        statsInterval += period;

        if (statsInterval >= MAX_STATS_INTERVAL) { // record stats every
            // MAX_STATS_INTERVAL
            long timeNow = System.nanoTime();
            timeSpentInGame = (int) ((timeNow - gameStartTime) / 1000000000L); // ns
            // -->
            // secs
            //wcTop.setTimeSpent(timeSpentInGame);

            long realElapsedTime = timeNow - prevStatsTime; // time since last
            // stats collection
            totalElapsedTime += realElapsedTime;

            double timingError = ((double) (realElapsedTime - statsInterval) / statsInterval)
                    * 100.0;

            totalFramesSkipped += framesSkipped;

            double actualFPS = 0; // calculate the latest FPS and UPS
            double actualUPS = 0;
            if (totalElapsedTime > 0) {
                actualFPS = (((double) frameCount / totalElapsedTime) * 1000000000L);
                actualUPS = (((double) (frameCount + totalFramesSkipped) / totalElapsedTime)
                        * 1000000000L);
            }

            // store the latest FPS and UPS
            fpsStore[(int) statsCount % NUM_FPS] = actualFPS;
            upsStore[(int) statsCount % NUM_FPS] = actualUPS;
            statsCount = statsCount + 1;

            double totalFPS = 0.0; // total the stored FPSs and UPSs
            double totalUPS = 0.0;
            for (int i = 0; i < NUM_FPS; i++) {
                totalFPS += fpsStore[i];
                totalUPS += upsStore[i];
            }

            if (statsCount < NUM_FPS) { // obtain the average FPS and UPS
                averageFPS = totalFPS / statsCount;
                averageUPS = totalUPS / statsCount;
            } else {
                averageFPS = totalFPS / NUM_FPS;
                averageUPS = totalUPS / NUM_FPS;
            }
			/*
			 * System.out.println(timedf.format( (double)
			 * statsInterval/1000000000L) + " " + timedf.format((double)
			 * realElapsedTime/1000000000L) + "s " + df.format(timingError) +
			 * "% " + frameCount + "c " + framesSkipped + "/" +
			 * totalFramesSkipped + " skip; " + df.format(actualFPS) + " " +
			 * df.format(averageFPS) + " afps; " + df.format(actualUPS) + " " +
			 * df.format(averageUPS) + " aups" );
			 */
            framesSkipped = 0;
            prevStatsTime = timeNow;
            statsInterval = 0L; // reset
        }
    } // end of storeStats()

    private void printStats() {
        System.out.println("Frame Count/Loss: " + frameCount + " / " + totalFramesSkipped);
        System.out.println("Average FPS: " + df.format(averageFPS));
        System.out.println("Average UPS: " + df.format(averageUPS));
        System.out.println("Time Spent: " + timeSpentInGame + " secs");
    }

    // ------------- game life cycle methods ------------
    // called by the JFrame's window listener methods

    public void addNotify()
    // only start the animation once the JPanel has been added to the JFrame
    {
       // super.addNotify(); // creates the peer
        startGame(); // start the thread
    }

    public void startGame()
    // initialise and start the thread
    {
        if (animator == null || !running) {
            animator = new Thread(this);
            animator.start();
        }
    }

    public void resumeGame()
    // called when the JFrame is activated / deiconified
    {
        isPaused = false;
    }

    public void pauseGame()
    // called when the JFrame is deactivated / iconified
    {
        isPaused = true;
    }

    public void stopGame()
    // called when the JFrame is closing
    {
        running = false;
    }
}
