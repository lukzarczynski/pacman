package Game;

import AI.AITask;
import Handlers.GhostAIHandler;
import Handlers.AbstractHandler;
import Handlers.GameHandler;
import Handlers.GhostHandler;
import Handlers.GhostInteractionHandler;
import Handlers.MapHandler;
import Handlers.PlayerHandler;
import Handlers.PlayerInteractionHandler;
import Handlers.PointHandler;
import Handlers.ScoreHandler;
import Objects.Direction;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferStrategy;
import static java.lang.System.exit;
import java.util.Date;
import java.util.concurrent.ExecutionException;

/**
 *
 * @author lukasz
 */
public class GameCore
        extends Canvas
        implements KeyListener {

    private boolean runMainThread;
    private final Frame frame = new Frame("Pacman");
    public static final int WINDOW_WIDTH = 800;
    public static final int WINDOW_HEIGHT = 600;
    public static final int SQUARE_SIZE = 18;
    public static final int HALF_SQUARE = 9;
    private BufferStrategy bufferStrategy;
    private AbstractHandler handler;
    private final GameState state = new GameState();
    public static final double fps = 30;
    private final long frameDelay = (long) (1000 / fps);
    private final boolean AI = true;

    public void init() {
        frame.setLayout(null);
        setBounds(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT);
        frame.add(this);
        frame.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        frame.setResizable(false);
        frame.setVisible(true);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                runMainThread = false;
                exit(0);
            }
        });
        addKeyListener(this);
        setIgnoreRepaint(true);
        createBufferStrategy(2);
        bufferStrategy = getBufferStrategy();
        runMainThread = true;

        GameInitializer.initialazeGameState(state);
        this.handler = new MapHandler(state);
        this.handler.attachHandler(new PointHandler());
        this.handler.attachHandler(new ScoreHandler());
        this.handler.attachHandler(new GameHandler());
        this.handler.attachHandler(new PlayerInteractionHandler());
        this.handler.attachHandler(new GhostInteractionHandler());
        this.handler.attachHandler(new GhostAIHandler());
        this.handler.attachHandler(new PlayerHandler());
        this.handler.attachHandler(new GhostHandler());

    }

    public void mainThreadLoop() throws InterruptedException, CloneNotSupportedException, ExecutionException {
        AITask.getInstance().withPlayer(state.getPlayer()).withHandler(handler);
        while (runMainThread) {
            Long start = new Date().getTime();
            Graphics2D g = getGraphicsContext();

            g.setColor(Color.black);
            g.fillRect(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT);
            g.translate(10, 30);

            handler.behave(state);
            handler.draw(g, state);

            g.dispose();
            bufferStrategy.show();
            Long time = new Date().getTime() - start;
            state.setFps(time);
            if (AI) {
                AITask.getInstance().withState(state).withTimeout(frameDelay - time - 2).simulate();
            } else {
                if (frameDelay - time > 0) {
                    Thread.sleep(frameDelay - time);
                }
            }
        }
    }

    public Graphics2D getGraphicsContext() {
        return (Graphics2D) bufferStrategy.getDrawGraphics();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (state.getPlayer() == null) {
            return;
        }

        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP:
                state.getPlayer().setNextDirection(Direction.UP);
                break;
            case KeyEvent.VK_RIGHT:
                state.getPlayer().setNextDirection(Direction.RIGHT);
                break;
            case KeyEvent.VK_DOWN:
                state.getPlayer().setNextDirection(Direction.DOWN);
                break;
            case KeyEvent.VK_LEFT:
                state.getPlayer().setNextDirection(Direction.LEFT);
                break;
            default:
                break;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    public boolean isRunMainThread() {
        return runMainThread;
    }

    public void setRunMainThread(boolean runMainThread) {
        this.runMainThread = runMainThread;
    }

    public AbstractHandler getHandler() {
        return handler;
    }

    public void setHandler(AbstractHandler handler) {
        this.handler = handler;
    }

    public GameState getState() {
        return state;
    }

    public Frame getFrame() {
        return frame;
    }

    public static int getWINDOW_WIDTH() {
        return WINDOW_WIDTH;
    }

    public static int getWINDOW_HEIGHT() {
        return WINDOW_HEIGHT;
    }

    public static int getSQUARE_SIZE() {
        return SQUARE_SIZE;
    }

    public static int getHALF_SQUARE() {
        return HALF_SQUARE;
    }

    public static double getFps() {
        return fps;
    }

    public boolean isAI() {
        return AI;
    }

}
