package buildingviewer;

import processing.core.PApplet;

public class Storage {
	
	float xLocation;
	float yLocation;
	
	public Storage(float x, float y) {
		xLocation = x;
		yLocation = y;
	}
	
	public void draw(PApplet applet) {
		
		applet.stroke(153, 51, 0);
		applet.strokeWeight(10);
		
		applet.beginShape();
		applet.vertex(xLocation - 20, yLocation - 20, 100);
		applet.vertex(xLocation - 20, yLocation + 20, 100);
		applet.vertex(xLocation + 20, yLocation + 20, 100);
		applet.vertex(xLocation + 20, yLocation - 20, 100);
		applet.vertex(xLocation - 20, yLocation - 20, 100);
		applet.endShape();
		
	}

}
