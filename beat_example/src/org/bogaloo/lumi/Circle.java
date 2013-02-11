package org.bogaloo.lumi;

public class Circle {
	private float _radius;
	private int _color;
	private int _x;
	private int _y;
	
	public Circle(float radius, int color, int x, int y){
		_radius = radius;
		_color = color;
		_x = x;
		_y = y;
	}
	
	
	public void setRadius(float r){
		_radius = r;
	}
	
	public float getRadius(){
		return _radius;
	}
	
	public int getColor(){
		return _color;
	}
	
	public int getX(){
		return _x;
	}
	
	public int getY(){
		return _y;
	}

}
