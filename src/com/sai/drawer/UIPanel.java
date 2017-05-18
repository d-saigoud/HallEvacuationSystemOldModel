package com.sai.drawer;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.Timer;

public class UIPanel extends JPanel {

	private int [][]a;
	
	public UIPanel(int [][]a) {
		this.a = a;
	}
	
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		for(int i =0; i<a.length; i++) {
			for(int j=0; j<a[0].length;j++) {
				if(a[i][j] == 1) {
					g.setColor(Color.RED);
					
				}
				else if(a[i][j] == 0) {
					g.setColor(Color.GREEN);
				}
				else if(a[i][j] == 2) {
					g.setColor(Color.BLUE);
				}
				else if(a[i][j] == 8) {
					g.setColor(Color.YELLOW);
				}
				//g.fillRect(j*5, i*5, 5, 5);
				g.setFont(new Font(Font.SANS_SERIF, 20, 20));
				g.drawString("" + a[i][j], j * 20,  i * 20);
			}
		}

	}

	public Dimension getPreferredSize() {
		return new Dimension(500, 500); // appropriate constants
	}
}