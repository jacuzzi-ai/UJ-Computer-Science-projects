package csc2a.designpatterns;


import csc2a.model.Triangle;
import csc2a.model.Rectangle;
import csc2a.model.Soldier;
import csc2a.model.Sun;
import csc2a.model.TargetMarker;
import csc2a.model.Aeroplane;
import csc2a.model.Circle;
import csc2a.model.Enemy;
import csc2a.model.Grass;
import csc2a.model.Rain;

/**
 * 
 * AbstractVisitor interface
 * Used to define all of the render functions for your different GameObjects
 * @author  <YOUR DETAILS HERE>
 *
 */
public interface iRenderVisitor {
	
	public void render(Rectangle r);
	
	public void render(Circle c);
	
	public void render(Triangle t);
	

	public void render(Soldier s);

	public void render(Grass grass);

	public void render(Sun sun);

	public void render(Aeroplane aeroplane);

	public void render(Rain rain);
	
	public void render(Enemy enemy);

	public void render(TargetMarker targetMarker);
	
}
