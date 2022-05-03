/**
 * 
 */
package csc2a.model;

import csc2a.designpatterns.iRenderVisitor;
import javafx.scene.paint.Color;

/**
 * @author Mr JC Chambuara
 *
 */
public class TargetMarker  extends GameObject{
	
	private int x1;
	private int y1;
	private double r1;
	private Color color1;
	private int x2;
	private int y2;
	private double r2;
	private Color color2;

	
	
	
	
	
	
	public TargetMarker(int x1, int y1, double r1, Color color1, int x2, int y2, double r2, Color color2) {
		
		this.x1 = x1;
		this.y1 = y1;
		this.r1 = r1;
		this.color1 = color1;
		this.x2 = x2;
		this.y2 = y2;
		this.r2 = r2;
		this.color2 = color2;
		
		
	}


	public int getX1() {
		return x1;
	}


	public void setX1(int x1) {
		this.x1 = x1;
	}


	public int getY1() {
		return y1;
	}



	public void setY1(int y1) {
		this.y1 = y1;
	}



	public double getR1() {
		return r1;
	}



	public void setR1(double r1) {
		this.r1 = r1;
	}



	public Color getColor1() {
		return color1;
	}



	public void setColor1(Color color1) {
		this.color1 = color1;
	}



	public int getX2() {
		return x2;
	}







	public void setX2(int x2) {
		this.x2 = x2;
	}







	public int getY2() {
		return y2;
	}







	public void setY2(int y2) {
		this.y2 = y2;
	}







	public double getR2() {
		return r2;
	}




	public void setR2(double r2) {
		this.r2 = r2;
	}







	



	public Color getColor2() {
		return color2;
	}




	public void setColor2(Color color2) {
		this.color2 = color2;
	}







	@Override
	public void accept(iRenderVisitor visitor) {
		
		visitor.render(this);
		
	}

}
