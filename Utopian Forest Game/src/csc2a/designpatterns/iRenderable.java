package csc2a.designpatterns;

/*
 * 
 * DO NOT EDIT THIS INTERFACE
 * 
 */

/**
 * 
 * AbstractVisitable interface for the Visitor Design Pattern
 * Used to make GameObjects renderable by the RenderGameObjectsVisitor
 * @author Mr Greaves
 *
 */
public interface iRenderable {
	public void accept(iRenderVisitor visitor);
}

/*
 * 
 * DO NOT EDIT THIS INTERFACE
 * 
 */