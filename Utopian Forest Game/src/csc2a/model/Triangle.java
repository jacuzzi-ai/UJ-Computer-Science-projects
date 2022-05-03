package csc2a.model;

import csc2a.designpatterns.iRenderVisitor;
import csc2a.designpatterns.iRenderable;
import javafx.scene.paint.Color;

public class Triangle extends GameObject{
	
	private int x;
	private int y;
	private int x2;
	private int y2;
	private int x3;
	private int y3;
	private Color c;
	
	
	public Triangle(int x, int y, int x2, int y2, int x3, int y3, Color c) {
		
		this.x = x;
		this.y = y;
		this.x2 = x2;
		this.y2 = y2;
		this.x3 = x3;
		this.y3 = y3;
		this.c = c;
	}
	
	
	public Color getC() {
		return c;
	}


	public void setC(Color c) {
		this.c = c;
	}


	public double[] getXCoords() {
		
		return new double[] { x,x2,x3};
	}
    public double[] getYCoords() {
		
		return new double[] { y,y2,y3};
	}


	@Override
	public void accept(iRenderVisitor visitor) {
		
		visitor.render(this);
		
	}

}
