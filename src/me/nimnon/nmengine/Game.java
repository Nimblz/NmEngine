package me.nimnon.nmengine;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.JFrame;

import me.nimnon.nmengine.core.Camera;
import me.nimnon.nmengine.core.Font;
import me.nimnon.nmengine.core.GameThread;
import me.nimnon.nmengine.core.KeyboardListener;
import me.nimnon.nmengine.core.MousePadListener;
import me.nimnon.nmengine.state.State;

/**
 * The heart of all NmEngine games, this handles the window and state, this
 * class should be instanced to create a game.
 * 
 * @author Nimnon
 * 
 */

public class Game {

	/**
	 * Actual window
	 */
	private static JFrame window;

	/**
	 * GameThread component housed in the window, controls update and render
	 * loop
	 */
	private static GameThread thread;

	/**
	 * Keyboard Listener that takes keyboard inputs
	 */
	private static KeyboardListener keyListener;
	/**
	 * Mouse Listener that takes mouse inputs and movements
	 */
	private MousePadListener mouseListener;

	/**
	 * Game updates per second
	 */
	public static int ticksPerSecond = 60;

	/**
	 * Static Mouse instance for getting mouse properties
	 */
	public static Mouse mouse = new Mouse();
	
	/**
	 * Static Keys instance for getting key properties
	 */
	public static Keys keys = new Keys();

	/**
	 * Text writing utility class
	 */
	public static Font fonts = new Font();

	/**
	 * Current focused camera
	 */
	public static Camera activeCamera;

	/**
	 * List of total cameras
	 */
	public static ArrayList<Camera> cameras = new ArrayList<Camera>();

	/**
	 * Background color
	 */
	public static Color backgroundColor = Color.white;

	/**
	 * Is debug output enabled?
	 */
	public static boolean debug = false;

	/**
	 * Active state
	 */
	public static State currentState;

	/**
	 * Game width
	 */
	public static int gameWidth;

	/**
	 * Game height
	 */
	public static int gameHeight;

	private static Cursor blankCursor;
	private static Cursor systemCursor;

	private static double zoom = 1;

	/**
	 * Creates a new Game class, it is advised you only create one of these,
	 * else weird things happen.
	 * 
	 * @param width
	 *            Game width in pixels
	 * @param height
	 *            Game height in pixels
	 * @param title
	 *            Window title
	 * @param state
	 *            Game starting state, if null is treated as a new State()
	 * @param tps
	 *            Ticks per second, how many times a second the update method is
	 *            called.
	 * @param zoom
	 *            Zoom of the initial main camera object
	 * @param backgroundColor
	 *            Background color of the application.
	 */
	public Game(int width, int height, String title, State state, int tps, double zoom, Color backgroundColor, Image icon) {
		createGame(width, height, title, state, tps, zoom, backgroundColor, icon);
	}
	
	/**
	 * Creates a new Game class, it is advised you only create one of these,
	 * else weird things happen.
	 * 
	 * @param width
	 *            Game width in pixels
	 * @param height
	 *            Game height in pixels
	 * @param title
	 *            Window title
	 * @param state
	 *            Game starting state, if null is treated as a new State()
	 * @param tps
	 *            Ticks per second, how many times a second the update method is
	 *            called.
	 * @param zoom
	 *            Zoom of the initial main camera object
	 * @param backgroundColor
	 *            Background color of the application.
	 */
	public Game(int width, int height, String title, State state, int tps, double zoom, Color backgroundColor) {
		createGame(width, height, title, state, tps, zoom, backgroundColor, new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB));
	}

	/**
	 * Creates a new Game class, it is advised you only create one of these,
	 * else weird things happen.
	 * 
	 * @param width
	 *            Game width in pixels
	 * @param height
	 *            Game height in pixels
	 * @param title
	 *            Window title
	 * @param state
	 *            Game starting state, if null is treated as a new State()
	 * @param tps
	 *            Ticks per second, how many times a second the update method is
	 *            called.
	 * @param backgroundColor
	 *            Background color of the application.
	 */
	public Game(int width, int height, String title, State state, int tps, Color backgroundColor) {
		createGame(width, height, title, state, tps, 2, backgroundColor, new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB));
	}

	/**
	 * Creates a new Game class, it is advised you only create one of these,
	 * else weird things happen.
	 * 
	 * @param width
	 *            Game width in pixels
	 * @param height
	 *            Game height in pixels
	 * @param title
	 *            Window title
	 * @param state
	 *            Game starting state
	 * @param tps
	 *            Ticks per second, how many times a second the update method is
	 *            called.
	 */
	public Game(int width, int height, String title, State state, int tps) {
		createGame(width, height, title, state, tps, 2, Color.white, new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB));
	}

	/**
	 * Creates a new game with specified width, height, title, and state, the
	 * background color is white
	 * 
	 * @param width
	 *            Game width in pixels
	 * @param height
	 *            Game height in pixels
	 * @param title
	 *            Window title
	 * @param state
	 *            Game starting state
	 */
	public Game(int width, int height, String title, State state) {
		createGame(width, height, title, state, 60, 2, Color.white, new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB));
	}

	/**
	 * Creates game window of specified width, height and title.
	 * 
	 * @param width
	 *            Game width in pixels
	 * @param height
	 *            Game height in pixels
	 * @param title
	 *            Window title
	 * 
	 */
	public Game(int width, int height, String title) {
		createGame(width, height, title, new State(), 2, 60, Color.white, new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB));
	}

	/**
	 * Creates game window of specified width and height.
	 * 
	 * @param width
	 *            Game width in pixels
	 * @param height
	 *            Game height in pixels
	 */
	public Game(int width, int height) {
		createGame(width, height, "Game", new State(), 2, 60, Color.white, new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB));
	}

	/**
	 * Creates empty game window of a blank state
	 */
	public Game() {
		createGame(400, 300, "Game", new State(), 2, 60, Color.white, new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB));
	}

	/**
	 * Called by constructor.
	 * 
	 * @param width
	 * @param height
	 * @param title
	 * @param state
	 * @param tps
	 */
	private void createGame(int width, int height, String title, State state, int tps, double zoom, Color backgroundColor, Image icon) {
		if (state == null)
			state = new State();

		ticksPerSecond = tps;

		gameWidth = (int) (width / zoom);
		gameHeight = (int) (height / zoom);

		mouseListener = new MousePadListener();
		keyListener = new KeyboardListener();

		window = new JFrame(title);
		thread = new GameThread();

		window.setTitle(title);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setFocusable(true);
		window.setResizable(false);
		
		if(icon!=null)
			window.setIconImage(icon);

		thread.setPreferredSize(new Dimension(width, height));
		Game.backgroundColor = backgroundColor;
		thread.setBackground(backgroundColor);
		thread.setFocusable(true);

		window.add(thread);
		

		window.pack();
		window.setMinimumSize(new Dimension(window.getWidth(), window.getHeight()));
		window.setLocationRelativeTo(null);
		window.setVisible(true);

		Game.zoom  = zoom;
		

		thread.addMouseListener(mouseListener);
		thread.addMouseMotionListener(mouseListener);
		thread.addMouseWheelListener(mouseListener);
		thread.addKeyListener(keyListener);

		blankCursor = window.getToolkit().createCustomCursor(new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB), new Point(), null);
		systemCursor = Cursor.getDefaultCursor();

		while (!thread.hasFocus())
			thread.grabFocus();
		switchState(state);
		

		new Thread(thread).start();

	}

	/**
	 * Switch from current state to a new state
	 * 
	 * @param state
	 *            Which state to switch to.<code> null </code> will be treated
	 *            as a new State()
	 */
	public static void switchState(State state) {
		if (state == null)
			state = new State();
		if (currentState != null)
			currentState.destroy();
		//showCursor();
		
		currentState = state;
		
		Game.cameras.clear();

		Game.activeCamera = new Camera(0, 0, thread.getWidth(), thread.getHeight(), Game.zoom);

		Game.cameras.add(Game.activeCamera);
		
		state.create();
	}

	

	public static void hideCursor() {
		window.setCursor(blankCursor);
	}

	public static void showCursor() {
		window.setCursor(systemCursor);
	}
	
	public static void setIconImage(Image icon) {
		window.setIconImage(icon);
	}
	

}
