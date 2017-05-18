package com.sai.drawer;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Util {

	public int[][] readFile(String filepath) throws IOException {
		
		BufferedReader br = new BufferedReader(new FileReader(filepath));
		
		int rows = 0;
		int cols = 0;
		String line ="";
		while((line=br.readLine()) != null) {
			rows++;
			if(cols==0) {
				cols = line.split(",").length;
			}
		}
		br.close();
		int [][]a = new int[rows][cols];
		int rowInc = 0;
		br = new BufferedReader(new FileReader(filepath));
		while((line=br.readLine()) != null) {
			String []splits = line.split(",");
			for(int i=0; i<splits.length; i++) {
				a[rowInc][i] = Integer.parseInt(splits[i]);
			}
			rowInc++;
		}
		
		br.close();
		
		return a;
	}
	
}
