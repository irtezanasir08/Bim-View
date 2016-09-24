package buildingviewer;


import java.util.ArrayList;
import java.util.List;

import processing.core.*;

//import wblut.math.*;
//import wblut.processing.*;
//import wblut.core.*;
import wblut.hemesh.*;
import wblut.geom.*;

public class Floor
{
	public boolean active = false;
	public int id;
	public String type;
	//public double height;
	//public String type;
	//public int level;
	
	//processing geom
	public ArrayList <ArrayList<PVector>> polygons = new ArrayList<ArrayList<PVector>>();
	
	
	//HEMESH geom
	public ArrayList<HE_Face> HE_faces = new ArrayList<HE_Face>();
	public List<List <WB_Point3d>> basepoints_set = new ArrayList <List<WB_Point3d>>();
	public List <WB_ExplicitPolygon> WB_polygons = new ArrayList <WB_ExplicitPolygon>(); 
	public List<List <WB_IndexedTriangle>> triangles_set = new ArrayList<List <WB_IndexedTriangle>>();
		
	
	public Floor ()
	{}
	
	public void draw(PApplet applet)
	{
		//applet.tint(255, 127);
		applet.fill(77, 150, 225, 65);
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
	
	public void highlight(PApplet applet)
	{
		/*
		applet.fill(0, 255, 0, 191);
		applet.stroke(0, 255, 0);
		applet.strokeWeight(10);
		*/
		applet.fill(0, 204, 204, 65);
		//applet.fill(0, 204, 204);
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
	
//	public void H_geom_convert()
//	{
//		for (int i = 0; i < polygons.size(); i++)
//		{
//			ArrayList <WB_Point3d> basepoints = new ArrayList <WB_Point3d>();
//			for (int j = 0; j < polygons.get(i).size(); j++)
//			{
//				PVector point = polygons.get(i).get(j);
//				basepoints.add(new WB_Point3d(point.x, point.y, point.z));
//			}
//			basepoints_set.add(basepoints);
//			WB_ExplicitPolygon WB_polygon = new WB_ExplicitPolygon(basepoints);
//			
//			WB_polygons.add(WB_polygon);
//			List <WB_IndexedTriangle> triangles = WB_polygon.triangulate();
//			
//			triangles_set.add(triangles);
//			
//		}
//	}
	
	public ArrayList<PVector> get_points_in_polygons()
	{
		ArrayList<PVector> all_points = new ArrayList<PVector>();
		for (int i = 0; i < polygons.size(); i++)
		{
			for (int j = 0; j < polygons.get(i).size(); j++)
			{
				PVector p1, p2;
				p1 = polygons.get(i).get(j);
				if (j != polygons.get(i).size() - 1)
				{
					p2 = polygons.get(i).get(j + 1);
				}
				else
				{
					p2 = polygons.get(i).get(0);
				}
				
				float distance = PVector.dist(p1, p2);
				PVector direction = PVector.sub(p2, p1);
				direction.normalize();
				float current_dist = 0;
				int point_count = 0;
				
				while (current_dist <= distance)
				{
					PVector newPoint = new PVector (p1.x + point_count * 10 * direction.x, p1.y + point_count * 10 * direction.y, p1.z + point_count * 10 * direction.z);
					current_dist = PVector.dist(p1, newPoint);
					all_points.add(newPoint);
					point_count++;
				}
				
				
			}
		}
		return all_points;
	}
	
	

}

