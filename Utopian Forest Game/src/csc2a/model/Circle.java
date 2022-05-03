package csc2a.model;

import csc2a.designpatterns.iRenderVisitor;
import csc2a.designpatterns.iRenderable;
import javafx.scene.paint.Color;


public class Circle extends GameObject{
	
	public double x;
	public double y;
	public double r;
	public Color c;
	
	public Circle(double d, double e, double r, Color c) {
		this.x = d; 
		this.y = e;
		this.r = r;
		this.c = c;
	}
	
	

	public double getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public double getR() {
		return r;
	}

	public void setR(double r) {
		this.r = r;
	}

	public Color getC() {
		return c;
	}

	public void setC(Color c) {
		this.c = c;
	}



	@Override
	public void accept(iRenderVisitor visitor) {
		visitor.render(this);
		
	}
	
	
	
}