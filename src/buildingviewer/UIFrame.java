package buildingviewer;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class UIFrame extends JFrame implements ActionListener {
	
	// Define constants
    public static final int WIDTH = 400;
    public static final int HEIGHT = 300;
    public static final int NUMBER_OF_CHARS = 2;
    
    private JTextField txtCrane;
	private JTextField txtStorage;
    
    private int numCranes = 0;
	private int numStorage = 0;
	
	public int getNumCranes() {
		return numCranes;
	}

	public int getNumStorage() {
		return numStorage;
	}
    
    public UIFrame() {
    	super();
    	setSize(WIDTH, HEIGHT);
    	
    	setLayout(new BorderLayout());
    	setVisible(true);
    	
    	JPanel cranePanel = new JPanel();
    	cranePanel.setLayout(new FlowLayout());
    	
    	JLabel lblCrane = new JLabel();
    	lblCrane.setText("Number of cranes: ");    	
    	txtCrane = new JTextField("1", NUMBER_OF_CHARS);
    	
    	cranePanel.add(lblCrane);
    	cranePanel.add(txtCrane);
    	    	
    	JPanel storagePanel = new JPanel();
    	storagePanel.setLayout(new FlowLayout());
    	   	
		JLabel lblStorage = new JLabel();
		lblStorage.setText("Number of storage locations: ");
		txtStorage = new JTextField("1", NUMBER_OF_CHARS);
		
		storagePanel.add(lblStorage);
		storagePanel.add(txtStorage);
		
		JPanel buttonPanel = new JPanel();
		JButton startButton = new JButton("OK");
		startButton.addActionListener(this);
		buttonPanel.add(startButton);
		
		
		add(cranePanel, BorderLayout.NORTH);
		add(storagePanel, BorderLayout.CENTER);
    	add(buttonPanel, BorderLayout.SOUTH);
    }
    
    public void actionPerformed(ActionEvent e)
    {
    	
    	try {
    		numCranes = stringToInt(txtCrane.getText());
    		numStorage = stringToInt(txtStorage.getText());
    		
    		if (numCranes < 0 || numStorage < 0) {
    			throw new IllegalArgumentException();
    		}
    		
    		System.out.println("Number of cranes: " + numCranes);
    		System.out.println("Number of storage: " + numStorage);
    		
    		setVisible(false);
    		JOptionPane.showMessageDialog(null, "Enter the locations for cranes and storage.");
    		
    	} catch (NumberFormatException ex) {
    		System.out.println("Enter integer numbers.");
    	} catch (IllegalArgumentException ex) {
    		System.out.println("Only positive numbers.");
    	}
      
    } 
    
    //Throws NumberFormatException.
    private static int stringToInt(String stringObject)
    {
        return Integer.parseInt(stringObject.trim());
    }

}
