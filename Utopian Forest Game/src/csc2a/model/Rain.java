package csc2a.model;

import csc2a.designpatterns.iRenderVisitor;

public class Rain  extends GameObject{
	
	private Circle c;
	private int rainDimension;
	
	

	public Rain(Circle c, int rainDimension) {
		this.c = c;
		this.rainDimension = rainDimension;
	}



	public Circle getC() {
		return c;
	}



	public void setC(Circle c) {
		this.c = c;
	}



	public int getRainDimension() {
		return rainDimension;
	}



	public void setRainDimension(int rainDimension) {
		this.rainDimension = rainDimension;
	}



	@Override
	public void accept(iRenderVisitor visitor) {
		
		visitor.render(this);
		
	}

}
