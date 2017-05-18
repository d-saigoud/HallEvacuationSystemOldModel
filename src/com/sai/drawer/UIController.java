package com.sai.drawer;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class UIController {

	public static void main(String[] args) {

		try {
			// UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
			UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
		} catch (UnsupportedLookAndFeelException ex) {
			ex.printStackTrace();
		} catch (IllegalAccessException ex) {
			ex.printStackTrace();
		} catch (InstantiationException ex) {
			ex.printStackTrace();
		} catch (ClassNotFoundException ex) {
			ex.printStackTrace();
		}
		/* Turn off metal's use bold fonts */
		UIManager.put("swing.boldMetal", Boolean.FALSE);

		// Schedule a job for the event dispatch thread:
		// creating and showing this application's GUI.
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				
				try {
					new UIController().createUserInterface();
				} catch (IOException e) {
					e.printStackTrace();
				}

			}
				
		});
		
	}

	public void createUserInterface() throws IOException {

		JFrame frame = new JFrame();
		frame.setTitle("Model Display");

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		addComponentsToPane(frame.getContentPane());

		frame.pack();

		frame.setVisible(true);

		frame.setLocationRelativeTo(null);
		frame.setSize(new Dimension(1000, 1000));
		
	}

	public void addComponentsToPane(Container pane) throws IOException {

		JPanel panel = new JPanel(new GridLayout(1, 1));
		
		Util util = new Util();
		
		int [][]a = util.readFile("C:\\Users\\sai goud\\Desktop\\Vamsi\\Model Data\\Case 2.2.txt");
		
		UIPanel uiPanel = new UIPanel(a);
		uiPanel.setFocusable(true);
		uiPanel.requestFocusInWindow();
		
		panel.add(uiPanel);
		pane.add(panel);
		
	}
	

}





