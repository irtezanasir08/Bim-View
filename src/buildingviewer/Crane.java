package buildingviewer;

import processing.core.PApplet;

public class Crane {
	
	float xLocation;
	float yLocation;
		
	public Crane(float x, float y) {
		xLocation = x;
		yLocation = y;
	}
	
	public void draw(PApplet applet) {
		
		applet.stroke(0, 200, 0);
		applet.strokeWeight(30);
		
		//applet.beginShape();
		applet.line(xLocation, yLocation, 100, xLocation, yLocation, 190);
		//applet.endShape();
		
	}
	
	

}