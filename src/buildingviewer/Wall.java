package buildingviewer;


import java.util.ArrayList;
import java.util.List;

import processing.core.*;
import wblut.geom.*;


public class Wall
{
	public int id;
	public PVector startPoint;
	public PVector endPoint;
	public double height;
	public String type;
	public int level;
	
	//Processing geom
	public ArrayList <ArrayList<PVector>> polygons = new ArrayList<ArrayList<PVector>>();
	
	public Wall ()
	{}
	
	public void draw(PApplet applet)
	{
		//applet.tint(255, 127);
		if (type.contains("Curtain"))
		{
			//KK: "fill" until we change this again, it is going to fill objects with this color
			applet.fill(0, 255, 255, 63);
			//applet.tint(0, 255, 255, 126);
		}
		else
		{
			applet.fill(128, 128, 128, 63);
			//applet.tint(128, 128, 128, 126);
		}
		
		//KK: "stroke" is used to color or widen lines
		applet.stroke(255, 255, 255);
		applet.strokeWeight(4);
		
		for (int i = 0; i < polygons.size(); i++)
		{
			applet.beginShape();
			
			for (int j = 0; j < polygons.get(i).size(); j++)
			{
				PVector point = polygons.get(i).get(j);
				applet.vertex(point.x, point.y, point.z);
			}
			
			applet.endShape();
		}
	}
	
	//KK: We can highlight if we want
	public void highlight(PApplet applet)
	{
		applet.fill(0, 204, 204);
		//applet.fill(0, 204, 204, 191);
		applet.stroke(0, 102, 102);
		applet.strokeWeight(18);
		
		for (int i = 0; i < polygons.size(); i++)
		{
			applet.beginShape();
			
			for (int j = 0; j < polygons.get(i).size(); j++)
			{
				PVector point = polygons.get(i).get(j);
				applet.vertex(point.x, point.y, point.z);
			}
			
			applet.endShape();
		}
	}

}

