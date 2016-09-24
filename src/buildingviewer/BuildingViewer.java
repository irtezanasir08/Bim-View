package buildingviewer;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.awt.*;
import java.awt.Rectangle;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.event.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import processing.core.*;
import remixlab.proscene.*;

import wblut.math.*;
import wblut.processing.*;
import wblut.core.*;
import wblut.hemesh.*;
import wblut.geom.*;

public class BuildingViewer extends PApplet 
{
	Scene scene;
	WB_Render renderer;
	//float x_offset = -80;
	//float y_offset = -80;
	
	//input XML files
	File floor_geom_file, roof_geom_file, wall_geom_file, simple_wall_geom_file, column_geom_file, framing_geom_file;
	DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
	Document wallDom = null;
	Document floorDom = null;
	Document roofDom = null;
	Document simpleWallCom = null;
	Document columnDom = null;
	Document framingDom = null;
	
	//Building object classes
	public ArrayList <Wall> walls = new ArrayList<>(); 
	public ArrayList <Floor> floors = new ArrayList<>();
	public ArrayList <Roof> roofs = new ArrayList<>();
	public ArrayList <Column> columns = new ArrayList<>();
	public ArrayList <Framing> framings = new ArrayList<>();

	UIFrame ui_frame;
	public ArrayList<Crane> cranes = new ArrayList<>();
	public ArrayList<Storage> storages = new ArrayList<>();
	
	//for visualization (animation)
	Visualizer visualizer;
	boolean animationOn = false;
	int animationStartFrame = 0;
	
	public void setup() 
	{
		//set up scene
		//size(1324, 576, OPENGL);
		size (1240, 840, P3D);  
		scene = new Scene(this);
		this.frameRate(30);
		//scene.setGridIsDrawn(true);
		//scene.setAxisIsDrawn(true);		

		
		//read XML files
		//floor_geom_file = new File ("data\\floor_geom.xml");
		//roof_geom_file = new File ("data\\roof_geom.xml");
		
		//simple_wall_geom_file = new File ("data\\simple_wall_geom.xml");
		simple_wall_geom_file = new File ("data/wall_geom3.xml");
		floor_geom_file = new File ("data/floor_geom3.xml");
		roof_geom_file = new File ("data/roof_geom3.xml");
		column_geom_file = new File ("data/column_geom3.xml");
		framing_geom_file = new File ("data/framing_geom3.xml");
		//make class instances
		get_simple_wall_info();
		get_floor_info();	
		get_roof_info();
		get_column_info();	
		get_framing_info();
		
		//predefined crane number and locations
		Crane crane1 = new Crane (120, -200);
		Crane crane2 = new Crane (180, -100);
		Crane crane3 = new Crane (-50, -200);
		Crane crane4 = new Crane (-80, -20);
		Crane crane5 = new Crane (100, 10);
		
		cranes.add(crane1);
		cranes.add(crane2);
		cranes.add(crane3);
		cranes.add(crane4);
		cranes.add(crane5);
		
		//predefined material storage number and locations
		Storage storage1 = new Storage(-70, 50);
		Storage storage2 = new Storage(130, -100);
		Storage storage3 = new Storage(-30, -80);
		
		storages.add(storage1);
		storages.add(storage2);
		storages.add(storage3);
		ui_frame = new UIFrame();
		
		
		visualizer = new Visualizer (this);
		
	}

	public void draw() 
	{
		System.out.println(this.frameCount);
		//view setting
		background(255, 255, 255, 65);
		directionalLight(192, 160, 128, 0, -1000, 100f);
		directionalLight(192, 160, 128, 0, 1000, 100f);
		directionalLight(192, 160, 128, 1000, 0, 100f);
		directionalLight(192, 160, 128, -1000, 0, 100f);
		directionalLight(255, 64, 0, 0.5f, -0.1f, 0.5f);
		this.scale((float) 0.2);
		
		
		
		//draw building geometry
		walls.forEach(wall -> wall.draw(this));
		floors.forEach(floor -> floor.draw(this));
		columns.forEach(column -> column.draw(this));
		framings.forEach(framing -> framing.draw(this));
		
		// draw cranes and storage
		cranes.forEach(crane -> crane.draw(this));
		storages.forEach(storage -> storage.draw(this));
		
		//mouse location
		this.ellipse(mouseX, mouseY, 3, 3);
		
		if (animationOn)
		{
			visualizer.showAnimation();
		}
		
		
	}
	
	public void keyReleased()
	{
		if (key == '1')
		{
			this.animationOn = !animationOn;
			this.animationStartFrame = this.frameCount;
		}
	}
		
	
	public void mouseClicked() {
		
		if (cranes.size() < ui_frame.getNumCranes()) {
			float x = mouseX * 0.2f;
			float y = mouseY * 0.2f;
			cranes.add(new Crane(x, y));
			System.out.println("Crane " + cranes.size() + " location: " + x + ", " + y);
			redraw();
		}
		else if (storages.size() < ui_frame.getNumStorage()) {
			float x = mouseX * 0.2f;
			float y = mouseY * 0.2f;
			storages.add(new Storage(x, y));
			System.out.println("Storage " + storages.size() + " location: " + x + ", " + y);
			redraw();
			
		}
			
	}
	
	private void get_simple_wall_info()
	{
		try 
		{
			//Using factory get an instance of document builder
			DocumentBuilder db = dbf.newDocumentBuilder();
			//parse using builder to get DOM representation of the XML file
			wallDom = db.parse(simple_wall_geom_file);
		}catch(ParserConfigurationException pce) {
			pce.printStackTrace();
		}catch(SAXException se) {
			se.printStackTrace();
		}catch(IOException ioe) {
			ioe.printStackTrace();
		}
		
		//get the root element
		Element docEle = wallDom.getDocumentElement();
		//get a nodelist of elements
		NodeList wall_nodes = docEle.getElementsByTagName("Wall");
		if(wall_nodes != null && wall_nodes.getLength() > 0) 
		{
			for(int i = 0 ; i < wall_nodes.getLength();i++) 
			{
				//get the wall element
				Element wall_node = (Element)wall_nodes.item(i);
				Wall wall = new Wall();
				int id = getIntValue(wall_node, "ID");
				String type = getTextValue(wall_node, "type");
				double height = getDoubleValue(wall_node, "height");
				wall.id = id;
				wall.type = type;
				wall.height = height;
				
				NodeList polygon_nodes = wall_node.getElementsByTagName("polygon");
				for (int j = 0; j < polygon_nodes.getLength(); j++)
				{
					Element polygon = (Element)polygon_nodes.item(j);
					ArrayList<PVector> wall_polygon = new ArrayList<PVector>();
					NodeList point_nodes = polygon.getElementsByTagName("point");
					for (int k = 0; k < point_nodes.getLength(); k++)
					{
						Element point = (Element)point_nodes.item(k);
						float X = getFloatValue(point, "X");
						float Y = -getFloatValue(point, "Y");
						float Z = getFloatValue(point, "Z");
						PVector p_vector = new PVector (X, Y, Z);
						wall_polygon.add(p_vector);
					}
					wall.polygons.add(wall_polygon);
				}
				wall.startPoint = wall.polygons.get(0).get(0);
				wall.endPoint = wall.polygons.get(0).get(1);
				//HEMESH conversion
				//wall.H_geom_convert();
				walls.add(wall);
			}
		}	
	}
	
	private void get_floor_info()
	{
		try 
		{
			//Using factory get an instance of document builder
			DocumentBuilder db = dbf.newDocumentBuilder();
			//parse using builder to get DOM representation of the XML file
			floorDom = db.parse(floor_geom_file);
		}catch(ParserConfigurationException pce) {
			pce.printStackTrace();
		}catch(SAXException se) {
			se.printStackTrace();
		}catch(IOException ioe) {
			ioe.printStackTrace();
		}
		
		//get the root element
		Element docEle = floorDom.getDocumentElement();
		//get a nodelist of elements
		NodeList floor_nodes = docEle.getElementsByTagName("floor");
		if(floor_nodes != null && floor_nodes.getLength() > 0) 
		{
			for(int i = 0 ; i < floor_nodes.getLength();i++) 
			{
				//get the wall element
				Element floor_node = (Element)floor_nodes.item(i);
				Floor floor = new Floor();
				int id = getIntValue(floor_node, "ID");
				//String type = getTextValue(floor_node, "type");
				floor.id = id;
				floor.type = "floor";
				//wall.type = type;
				
				NodeList polygon_nodes = floor_node.getElementsByTagName("polygon");
				for (int j = 0; j < polygon_nodes.getLength(); j++)
				{
					Element polygon = (Element)polygon_nodes.item(j);
					ArrayList<PVector> floor_polygon = new ArrayList<PVector>();
					NodeList point_nodes = polygon.getElementsByTagName("point");
					for (int k = 0; k < point_nodes.getLength(); k++)
					{
						Element point = (Element)point_nodes.item(k);
						float X = getFloatValue(point, "X");
						float Y = -getFloatValue(point, "Y");
						float Z = getFloatValue(point, "Z");
						PVector p_vector = new PVector (X, Y, Z);
						floor_polygon.add(p_vector);
					}
					floor.polygons.add(floor_polygon);
				}
				
				
				//floor.H_geom_convert();
				floors.add(floor);
			}
		}	
	}
	
	private void get_roof_info()
	{
		try 
		{
			//Using factory get an instance of document builder
			DocumentBuilder db = dbf.newDocumentBuilder();
			//parse using builder to get DOM representation of the XML file
			roofDom = db.parse(roof_geom_file);
		}catch(ParserConfigurationException pce) {
			pce.printStackTrace();
		}catch(SAXException se) {
			se.printStackTrace();
		}catch(IOException ioe) {
			ioe.printStackTrace();
		}
		
		//get the root element
		Element docEle = roofDom.getDocumentElement();
		//get a nodelist of elements
		NodeList roof_nodes = docEle.getElementsByTagName("roof");
		if(roof_nodes != null && roof_nodes.getLength() > 0) 
		{
			for(int i = 0 ; i < roof_nodes.getLength();i++) 
			{
				//get the wall element
				Element roof_node = (Element)roof_nodes.item(i);
				Roof roof = new Roof();
				int id = getIntValue(roof_node, "ID");
				//String type = getTextValue(floor_node, "type");
				roof.id = id;
				roof.type = "roof";
				
				NodeList polygon_nodes = roof_node.getElementsByTagName("polygon");
				for (int j = 0; j < polygon_nodes.getLength(); j++)
				{
					Element polygon = (Element)polygon_nodes.item(j);
					ArrayList<PVector> floor_polygon = new ArrayList<PVector>();
					NodeList point_nodes = polygon.getElementsByTagName("point");
					for (int k = 0; k < point_nodes.getLength(); k++)
					{
						Element point = (Element)point_nodes.item(k);
						float X = getFloatValue(point, "X");
						float Y = -getFloatValue(point, "Y");
						float Z = getFloatValue(point, "Z");
						PVector p_vector = new PVector (X, Y, Z);
						floor_polygon.add(p_vector);
					}
					roof.polygons.add(floor_polygon);
				}
				//roof.H_geom_convert();
				roofs.add(roof);
			}
		}	
	}
	
	private void get_column_info()
	{
		try 
		{
			//Using factory get an instance of document builder
			DocumentBuilder db = dbf.newDocumentBuilder();
			//parse using builder to get DOM representation of the XML file
			columnDom = db.parse(column_geom_file);
		}catch(ParserConfigurationException pce) {
			pce.printStackTrace();
		}catch(SAXException se) {
			se.printStackTrace();
		}catch(IOException ioe) {
			ioe.printStackTrace();
		}
		
		//get the root element
		Element docEle = columnDom.getDocumentElement();
		//get a nodelist of elements
		NodeList column_nodes = docEle.getElementsByTagName("Column");
		if(column_nodes != null && column_nodes.getLength() > 0) 
		{
			for(int i = 0 ; i < column_nodes.getLength();i++) 
			{
				//get the wall element
				Element column_node = (Element)column_nodes.item(i);
				Column column = new Column();
				int id = getIntValue(column_node, "ID");
				String type = getTextValue(column_node, "type");
				double length = getDoubleValue(column_node, "length");
				column.id = id;
				column.type = type;
				column.height = height;
				
				float X1 = getFloatValue(column_node, "X1");
				float Y1 = -getFloatValue(column_node, "Y1");
				float Z1 = getFloatValue(column_node, "Z1");
				float X2 = getFloatValue(column_node, "X2");
				float Y2 = -getFloatValue(column_node, "Y2");
				float Z2 = getFloatValue(column_node, "Z2");
				column.startPoint = new PVector (X1, Y1, Z1);
				column.endPoint = new PVector (X2, Y2, Z2);
				column.materialType = Constants.MATERIAL_TYPE_COLUMN;
				
				columns.add(column);
			}
		}	
	}
	
	private void get_framing_info()
	{
		try 
		{
			//Using factory get an instance of document builder
			DocumentBuilder db = dbf.newDocumentBuilder();
			//parse using builder to get DOM representation of the XML file
			framingDom = db.parse(framing_geom_file);
		}catch(ParserConfigurationException pce) {
			pce.printStackTrace();
		}catch(SAXException se) {
			se.printStackTrace();
		}catch(IOException ioe) {
			ioe.printStackTrace();
		}
		
		//get the root element
		Element docEle = framingDom.getDocumentElement();
		//get a nodelist of elements
		NodeList framing_nodes = docEle.getElementsByTagName("framing");
		if(framing_nodes != null && framing_nodes.getLength() > 0) 
		{
			for(int i = 0 ; i < framing_nodes.getLength();i++) 
			{
				//get the wall element
				Element framing_node = (Element)framing_nodes.item(i);
				Framing framing = new Framing();
				int id = getIntValue(framing_node, "ID");
				String type = getTextValue(framing_node, "type");
				
				framing.id = id;
				framing.type = type;
				
				
				float X1 = getFloatValue(framing_node, "X1");
				float Y1 = -getFloatValue(framing_node, "Y1");
				float Z1 = getFloatValue(framing_node, "Z1");
				float X2 = getFloatValue(framing_node, "X2");
				float Y2 = -getFloatValue(framing_node, "Y2");
				float Z2 = getFloatValue(framing_node, "Z2");
				framing.startPoint = new PVector (X1, Y1, Z1);
				framing.endPoint = new PVector (X2, Y2, Z2);
				framing.materialType = Constants.MATERIAL_TYPE_FRAMING;
				
				framings.add(framing);
			}
		}	
	}
	
	
	
	private String getTextValue(Element ele, String tagName) {
		String textVal = null;
		NodeList nl = ele.getElementsByTagName(tagName);
		if(nl != null && nl.getLength() > 0) {
			Element el = (Element)nl.item(0);
			textVal = el.getFirstChild().getNodeValue();
		}

		return textVal;
	}
	
	private int getIntValue(Element ele, String tagName) {
		//in production application you would catch the exception
		return Integer.parseInt(getTextValue(ele,tagName));
	}
	
	private double getDoubleValue(Element ele, String tagName) {
		//in production application you would catch the exception
		return Double.parseDouble(getTextValue(ele,tagName));
	}
	
	private float getFloatValue(Element ele, String tagName) {
		//in production application you would catch the exception
		return Float.parseFloat (getTextValue(ele,tagName));
	}
	
	
}
