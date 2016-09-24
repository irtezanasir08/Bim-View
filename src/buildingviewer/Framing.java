package buildingviewer;

import processing.core.PApplet;
import processing.core.PVector;

public class Framing extends SteelMaterial{
	
	
	public Framing(){}
	
	@Override
	public void draw(PApplet applet)
	{
		applet.stroke(0, 0, 255);
		applet.strokeWeight(30);
		
		applet.beginShape();
		
		applet.line(startPoint.x, startPoint.y, startPoint.z, endPoint.x, endPoint.y, endPoint.z);
		
		applet.endShape();
		
	}

}
