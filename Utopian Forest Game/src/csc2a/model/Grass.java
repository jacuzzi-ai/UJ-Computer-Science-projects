package csc2a.model;

import csc2a.designpatterns.iRenderVisitor;
import javafx.scene.image.Image;

public class Grass  extends GameObject{
	
	private int x;
	private int y;
	private int h;
	private int w;
	private Image image;
	
    public Grass(int x, int y, int h, int w, Image image) {
		super();
		this.x = x;
		this.y = y;
		this.h = h;
		this.w = w;
		this.image = image;
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

	public int getH() {
		return h;
	}

	public void setH(int h) {
		this.h = h;
	}

	public int getW() {
		return w;
	}

	public void setW(int w) {
		this.w = w;
	}

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}

	@Override
	public void accept(iRenderVisitor visitor) {
		visitor.render(this);
		
	}
	
	



}
