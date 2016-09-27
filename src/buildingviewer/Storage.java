package buildingviewer;

import processing.core.PApplet;
import processing.core.PVector;

public class Storage {
	
	PVector location;
	
	public Storage(float x, float y) {
		location = new PVector(x, y, 100);
	}
	
	public void draw(PApplet applet) {
		
		applet.stroke(153, 51, 0);
		applet.strokeWeight(10);
		
		applet.beginShape();
		applet.vertex(location.x - 20, location.y - 20, location.z);
		applet.vertex(location.x - 20, location.y + 20, location.z);
		applet.vertex(location.x + 20, location.y + 20, location.z);
		applet.vertex(location.x + 20, location.y - 20, location.z);
		applet.vertex(location.x - 20, location.y - 20, location.z);
		applet.endShape();
		
	}

}
