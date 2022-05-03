package csc2a.model;

import java.util.ArrayList;
import java.util.Iterator;

import javafx.geometry.Point2D;

/**
 * 
 * A generic GameObjectContainer container used to store all GameObjects in one data structure
 * (For example create a GameObjectContainer to store all of your Enemies or Powerup Items, provided Enemy and Powerup extend GameObject)
 * @author  Mr Greaves
 *
 */
public class GameObjectContainer<T extends GameObject> implements Iterable<T>{
	
	//Attributes
	ArrayList<T> gameObjects;
	
	/**
	 * Default constructor
	 */
	public GameObjectContainer(){
		gameObjects = new ArrayList<>();
	}
	
	/**
	 * Method to add a GameObject to the container
	 * @param go the GameObject to be added
	 */
	public void addGameObject(T go) {
		gameObjects.add(go);
	}
	
	/**
	 * Method to remove a particular GameObject from the container by index
	 * @param index The index of the GameObject to be removed
	 */
	public void removeGameObject(int index) {
		gameObjects.remove(index);
	}
	
	/**
	 * Method to remove a particular GameObject from the container by reference
	 * @param go The GameObject reference to be removed 
	 */
	public void removeGameObject(T go) {
		gameObjects.remove(go);
	}
	
	/**
	 * Method to remove all GameObjects from the container
	 * (eg. for use when moving to a different level)
	 */
	public void clearGameObjects() {
		gameObjects.removeAll(null);
	}
	
	/**
	 * Method to get the number of GameObjects in the container
	 * @return The integer number of GameObjects in the container
	 */
	public int getSize() {
		return gameObjects.size();
	}
	
	/**
	 * Utility method to return any GameObject at a given location (if there is one)
	 * @param location A Point2D location
	 * @return null or a GameObject at the specified location
	 */
	public GameObject getGameObjectAtLocation(Point2D location) {
		GameObject temp = null;
		for(GameObject g: gameObjects) {
			Point2D goLocation = g.getLocation();
			if((goLocation.getX() == location.getX()) && (goLocation.getY() == location.getY())) {
				temp = g;
			}
		}
		return temp;
	}

	/**
	 * Method from the Iterable interface that allows iteration through the generic data structure
	 * (i.e. you can use the GameObjectContainer in for each loops)
	 * @return Returns an iterator for iteration through the container
	 */
	@Override
	public Iterator<T> iterator() {
		return gameObjects.iterator();
	}
	
	/*
	 * TODO: (Optional) Add your own methods to deal with the logic you want in your container 
	 * (e.g. searching for a particular GameObject by ID or other criteria)
	 */
}
