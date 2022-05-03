package csc2a.util;

import java.util.BitSet;

import javafx.geometry.Point2D;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

/*
 * 
 * YOU SHOULD NOT NEED TO MAKE CHANGES TO THIS CLASS
 *     (Unless you want to add functionality???)
 * 
 */

/**
 * 
 * Class for a keyboard and mouse buffer that can be used in your GamePane or GameCanvas
 * Used to check if a key was pressed during the last frame of the AnimationTimer
 * Used to check which interactions were performed by the mouse
 * @author Mr Greaves
 *
 */
public class KMBuffer{
	
	//Keyboard-related attribute
	private static BitSet keys = new BitSet();			//Container for all of the keys on the keyboard
	
	//Mouse-related attributes
	private static boolean mouseInWindow = false;
	private static boolean mouseLeftPressed = false;
	private static boolean mouseRightPressed = false;
	private static boolean mouseMiddlePressed = false;
	private static Point2D mouseNodeLocation = new Point2D(0,0); 	//Location relative to the node (which uses the methods below)
	private static Point2D mouseSceneLocation = new Point2D(0,0); 	//Location relative to the root node of the SceneGraph
	private static Point2D mouseScreenLocation = new Point2D(0,0); 	//Location relative to the monitor screen
	
	/*
	 * Keyboard Methods (For use in Event Dispatchers)
	 */
	
	/**
	 * Method to handle a key press
	 * @param event A KeyEvent (from dispatcher)
	 */
	public static void handleKeyPressed(KeyEvent event) {
		//Set the key to pressed
		//System.out.println("Testing");
		keys.set(event.getCode().ordinal(), true);
	}	
	/**
	 * Method to handle a key release
	 * @param event A KeyEvent  (from dispatcher)
	 */
	public static void handleKeyReleased(KeyEvent event) {
		//Set the key to not pressed
		keys.set(event.getCode().ordinal(), false);
	}
	
	/*
	 * Mouse Methods (For use in Event Dispatchers)
	 */
	
	/**
	 * Method to handle mouse movement
	 * @param event A MouseEvent (from dispatcher)
	 */
	public static void handleMouseMoved(MouseEvent event) {
		//Get mouse location
		//System.out.println("Testing1");
		mouseNodeLocation = new Point2D(event.getX(), event.getY());
		mouseSceneLocation = new Point2D(event.getSceneX(), event.getSceneY());
		mouseScreenLocation = new Point2D(event.getScreenX(), event.getScreenY());
	}	
	/**
	 * Method to handle mouse presses
	 * @param event A MouseEvent (from dispatcher)
	 */
	public static void handleMousePressed(MouseEvent event) {
		//System.out.println("Testing2");
		//Left press
		if(event.getButton() == MouseButton.PRIMARY) {
			mouseLeftPressed = true;
		}
		//Right Press
		if(event.getButton() == MouseButton.SECONDARY) {
			mouseRightPressed = true;
		}
		//Middle Press
		if(event.getButton() == MouseButton.MIDDLE) {
			mouseMiddlePressed = true;
		}
		//handleMouseMoved(event);
	}	
	/**
	 * Method to handle mouse releases
	 * @param event A MouseEvent (from dispatcher)
	 */
	public static void handleMouseReleased(MouseEvent event) {
		//Left press
		if(event.getButton() == MouseButton.PRIMARY) {
			mouseLeftPressed = false;
		}
		//Right Press
		if(event.getButton() == MouseButton.SECONDARY) {
			mouseRightPressed = false;
		}
		//Middle Press
		if(event.getButton() == MouseButton.MIDDLE) {
			mouseMiddlePressed = false;
		}
		//handleMouseMoved(event);
	}	
	/**
	 * Method to handle click and drag mouse events
	 * @param event A MouseEvent (from dispatcher)
	 */
	public static void handleMouseDragged(MouseEvent event) {
		handleMousePressed(event);
		handleMouseMoved(event);		
	}
	/**
	 * Method to handle mouse exiting the Application window
	 * @param event A MouseEvent (from dispatcher)
	 */
	public static void handleMouseExited(MouseEvent event) {
		//System.out.println("Testing3");
		mouseInWindow = false;
	}	
	/**
	 * Method to handle mouse entering the Application window
	 * @param event A MouseEvent (from dispatcher)
	 */
	public static void handleMouseEntered(MouseEvent event) {
		//System.out.println("Testing4");
		mouseInWindow = true;
	}
	
	/*
	 * 
	 * Utility methods for use in your game logic
	 * 
	 */
	
	/**
	 * Method to test if a key has been pressed
	 * (Use by passing KeyCode.KEYCODE to pass the key you want to see if is pressed)
	 * @see <a href="https://docs.oracle.com/javase/8/javafx/api/javafx/scene/input/KeyCode.html">this link for more information</a>
	 * @param code javafx.scene.input.KeyCode for the key to test 
	 * @return Boolean whether or not the key is pressed
	 */
	public static boolean isKeyPressed(KeyCode code) {
		return keys.get(code.ordinal());
	}
	/**
	 * Method to test if the mouse is inside of the JavaFX Application
	 * @return Whether or not the mouse is in the Application window
	 */
	public static boolean isMouseInWindow() {
		return mouseInWindow;
	}
	/**
	 * Method to get the mouse location relative to the node that has
	 * the .setOnMouseMoved event dispatcher set 
	 * @return The mouse location relative to calling node
	 */
	public static Point2D getMouseNodeLocation() {
		return mouseNodeLocation;
	}
	/**
	 * Method to get the mouse location relative to the scene
	 * @return The mouse loaction relative to the scene
	 */
	public static Point2D getMouseSceneLocation() {
		return mouseSceneLocation;
	}
	/**
	 * Method to get the mouse location relative to the monitor screen
	 * @return The mouse location relative to the monitor screen
	 */
	public static Point2D getMouseScreenLocation() {
		return mouseScreenLocation;
	}
	/**
	 * Method to check if the Left mouse is pressed
	 * @return Whether or not the Left mouse is pressed
	 */
	public static boolean isLeftMousePressed() {
		return mouseLeftPressed;
	}
	/**
	 * Method to check if the Right mouse is pressed
	 * @return Whether or not the Right mouse is pressed
	 */
	public static boolean isRightMousePressed() {
		return mouseRightPressed;
	}
	/**
	 * Method to check if the Middle mouse is pressed
	 * @return Whether or not the Right mouse is pressed
	 */
	public static boolean isMiddleMousePressed() {
		return mouseMiddlePressed;
	}
}

/*
 * 
 * YOU SHOULD NOT NEED TO MAKE CHANGES TO THIS CLASS
 *     (Unless you want to add functionality???)
 * 
 */
