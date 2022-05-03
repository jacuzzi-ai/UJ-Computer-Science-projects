package csc2a.model;

import csc2a.designpatterns.iRenderVisitor;
import csc2a.designpatterns.iRenderable;
import javafx.scene.paint.Color;


public class Rectangle extends GameObject {
	//You are going to make these private and use accessors
	private int x;
	private int y;
	private double w;
	private double h;
	private Color c;
	
	public Rectangle(int x, int y, double w, double h, Color c) {
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		this.c = c;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public double getW() {
		return w;
	}

	public void setW(double w) {
		this.w = w;
	}

	public double getH() {
		return h;
	}

	public void setH(double h) {
		this.h = h;
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