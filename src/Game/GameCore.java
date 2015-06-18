package Game;

import Handlers.GhostAIHandler;
import Handlers.AbstractHandler;
import Handlers.GhostHandler;
import Handlers.InteractionHandler;
import Handlers.MapHandler;
import Handlers.PlayerHandler;
import Handlers.PointHandler;
import Handlers.ScoreHandler;
import Objects.Direction;
import Objects.MapStatic;
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
import java.util.logging.Logger;

/**
 *
 * @author lukasz
 */
public class GameCore
        extends Canvas
        implements KeyListener {

    private static final Logger LOG = Logger.getLogger(GameCore.class.getName());

    private boolean runMainThread;
    private Frame frame;
    private final int WINDOW_WIDTH;
    private final int WINDOW_HEIGHT;
    private final int SQUARE_SIZE;
    private BufferStrategy bufferStrategy;
    private AbstractHandler handler;
    private GameState state;
    private final double fps = 30;
    private final long frameDelay = (long) (1000 / fps);

    public GameCore(int width, int height) {
        this.WINDOW_WIDTH = width;
        this.WINDOW_HEIGHT = height;
        int squares = Math.max(MapStatic.map.length, MapStatic.map[0].length);
        this.SQUARE_SIZE = Math.min((width - 40) / squares, (height - 40) / squares);
    }

    public void init() {
        frame = new Frame("Pacman");
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
        this.state = new GameState(SQUARE_SIZE, WINDOW_WIDTH, WINDOW_HEIGHT);
        state.initGameState();
        this.handler = new MapHandler(state);
        this.handler.attachHandler(new PointHandler(state));
        this.handler.attachHandler(new InteractionHandler());
        this.handler.attachHandler(new PlayerHandler());
        this.handler.attachHandler(new GhostHandler(state));
        this.handler.attachHandler(new ScoreHandler());
        this.handler.attachHandler(new GhostAIHandler());

    }

    public void mainThreadLoop() throws InterruptedException {
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
//            LOG.log(Level.INFO, "+ {0} ms", time);
            if (frameDelay > time) {
                Thread.sleep(frameDelay - time);
            }
//            LOG.log(Level.INFO, "- {0} ms", (new Date().getTime() - start));

        }
    }

    public Graphics2D getGraphicsContext() {
        return (Graphics2D) bufferStrategy.getDrawGraphics();
    }

    public boolean isRunMainThread() {
        return runMainThread;
    }

    public void setRunMainThread(boolean runMainThread) {
        this.runMainThread = runMainThread;
    }

    public Frame getFrame() {
        return frame;
    }

    public void setFrame(Frame frame) {
        this.frame = frame;
    }

    public int getWIDTH() {
        return WINDOW_WIDTH;
    }

    public int getHEIGHT() {
        return WINDOW_HEIGHT;
    }

    public int getWINDOW_WIDTH() {
        return WINDOW_WIDTH;
    }

    public int getWINDOW_HEIGHT() {
        return WINDOW_HEIGHT;
    }

    public int getSQUARE_SIZE() {
        return SQUARE_SIZE;
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
}
