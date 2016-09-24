package buildingviewer;

import processing.core.PApplet;
import processing.core.PVector;

public abstract class SteelMaterial {
	
	public int id;
	public PVector startPoint;
	public PVector endPoint;
	public double height;
	public String type;
	//To identify if it's a Column or a Framing
	//Values should be get and set from Constant class
	public int materialType;
	
	public abstract void draw(PApplet applet); 
	
}
