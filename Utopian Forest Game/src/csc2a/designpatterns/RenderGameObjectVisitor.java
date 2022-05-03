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
import javafx.scene.canvas.GraphicsContext;



/**
 * ConcreteVisitor class
 * Used to visit each GameObject and render them onto the GameCanvas
 * @author  <YOUR DETAILS HERE>
 *
 */
public class RenderGameObjectVisitor implements iRenderVisitor{
	
	//Attributes
	GraphicsContext gc = null;
	
	/**
	 * Mutator for the GraphicsContext from the GameCanvas
	 * Used to set gc so the Visitor can draw things on the Canvas
	 * @param gc the GraphicsContext from the GameCanvas
	 */
	public void setGraphicsContext(GraphicsContext gc) {
		this.gc = gc;
	}

	@Override
	public void render(Rectangle r) {
		gc.setFill(r.getC());
		gc.fillRect(r.getX(), r.getY(), r.getW(), r.getH());
		
		
	}

	@Override
	public void render(Circle c) {
		gc.setFill(c.getC());
		
		gc.fillOval(c.getX(), c.getY(),2*c.getR() , 2*c.getR());
		
	}

	@Override
	public void render(Triangle t) {

       gc.setFill(t.getC());
       gc.fillPolygon(t.getXCoords(), t.getYCoords(), 3);
		
	}
	
	@Override
	public void render(Soldier s) {
		
        gc.drawImage(s.getImage(), s.getX(), s.getY(), s.getW(), s.getH());
		
	}

	@Override
	public void render(Grass grass) {
		
		 gc.drawImage(grass.getImage(), grass.getX(), grass.getY(), grass.getW(), grass.getH());
	}

	@Override
	public void render(Sun sun) {
		
		gc.drawImage(sun.getImage(), sun.getX(), sun.getY(), sun.getW(), sun.getH());
	}

	@Override
	public void render(Aeroplane aeroplane) {
		gc.drawImage(aeroplane.getImage(), aeroplane.getX(), aeroplane.getY(), aeroplane.getW(), aeroplane.getH());
		
	}

	@Override
	public void render(Rain rain) {
		
		for(int r = 0;r < rain.getRainDimension();r++) {
			
			for(int c = 0;c < rain.getRainDimension();c++) {
				gc.setFill(rain.getC().getC());
				gc.fillOval(c*10, r*10, rain.getC().getR(), rain.getC().getR()*2);
			}
		}
		
	}

	@Override
	public void render(Enemy enemy) {
		
		gc.drawImage(enemy.getImage(), enemy.getX(), enemy.getY(), enemy.getW(), enemy.getH());
		
	}

	@Override
	public void render(TargetMarker targetMarker) {
		
		gc.setStroke(targetMarker.getColor1());
		
		gc.strokeOval(targetMarker.getX1(), targetMarker.getY1(), targetMarker.getR1(), targetMarker.getR1());
		
		gc.setFill(targetMarker.getColor2());
		gc.fillOval(targetMarker.getX2(),targetMarker.getY2(),targetMarker.getR2(),targetMarker.getR2());
		
	}
	

	

	
	
	
	
}
