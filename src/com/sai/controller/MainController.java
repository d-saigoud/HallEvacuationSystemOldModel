package com.sai.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import com.sai.dto.HallModelDTO;
import com.sai.util.Utility;

//In a given second a person can move from four feet to eight feet ()
public class MainController {

	public static void main(String[] args) throws IOException, InterruptedException {

		for(int run = 0; run < 50; run++) {
		
		Map<Character, Integer> exitIdentifierToExitCountMap = new HashMap<Character, Integer>();
		Map<Character, Integer> totalsExitIdentifierToExitCountMap = new HashMap<Character, Integer>();
		Random random = new Random();
		HallModelDTO hallModelDto = Utility.loadModelFromFile(
				"C:\\Users\\sai goud\\Documents\\Softwares\\eclipse-jee-mars-2-win32-x86_64\\eclipse\\workspace\\VamsiCivilUakron\\src\\com\\sai\\resource\\Model4-7000.txt");

		Utility.printHall2DMatrix(hallModelDto);
		Utility.printTotalPersonsPositions(hallModelDto);
		Utility.printHallTotals(hallModelDto);
		// Utility.printExitPositions(hallModelDto);
		System.out.println();
		System.out.println();

		/*if(1==1) {
			return;
		}*/
		
		
		// Thread.sleep(5000);

		int secondsCounter = 0;

		for (; hallModelDto.getTotalNumberOfPeopleStillInside() > 0;) {

			secondsCounter++;

			// Change this later to loop from last
			for (int i = hallModelDto.getPeopleInside().size() - 1; i >= 0 ; i--) {

				if (hallModelDto.getPeopleInside().get(i).isPersonInside()) {

					for (int j = 0; hallModelDto.getPeopleInside().get(i).isPersonInside()
							&& j < hallModelDto.getPeopleInside().get(i).getFeetThatPersonCanMoveInSecond(); j++) {

						if (Utility.canThisPersonExit(hallModelDto, i)) {
							int randomExit = random.nextInt(3);
							if (randomExit == 1) {
								int row = hallModelDto.getPeopleInside().get(i).getPersonPosition().getRow();
								int col = hallModelDto.getPeopleInside().get(i).getPersonPosition().getCol();
								hallModelDto.getHallMatrix()[row][col] = 0;
								hallModelDto.getPeopleInside().get(i).setPersonInside(false);
								hallModelDto.setTotalNumberOfPeopleStillInside(
										hallModelDto.getTotalNumberOfPeopleStillInside() - 1);

								
								char exitIdentifier = hallModelDto.getColumnPositionToExitIdentifierMap().get(col);
								if (!(exitIdentifierToExitCountMap.containsKey(exitIdentifier))) {

									exitIdentifierToExitCountMap.put(exitIdentifier, 0);

								}
								
								if (!(totalsExitIdentifierToExitCountMap.containsKey(exitIdentifier))) {

									totalsExitIdentifierToExitCountMap.put(exitIdentifier, 0);

								}

								exitIdentifierToExitCountMap.put(exitIdentifier,
										exitIdentifierToExitCountMap.get(exitIdentifier) + 1);
								
								totalsExitIdentifierToExitCountMap.put(exitIdentifier,
										totalsExitIdentifierToExitCountMap.get(exitIdentifier) + 1);
								
								
								hallModelDto.getPeopleInside().get(i).setExittedFrom(exitIdentifier);
								hallModelDto.getPeopleInside().get(i).setSecondsTakenToExit(secondsCounter);
								
								
							}
						} else {
							// replace this logic later to consider exit
							// anywhere

							// if person in exit column and not obstacle ahead
							// in same column then keep moving in same column to
							// approach exit
							if ((Utility.isPersonInSameColumnAsExit(hallModelDto, i) && hallModelDto
									.getHallMatrix()[hallModelDto.getPeopleInside().get(i).getPersonPosition().getRow()
											+ 1][hallModelDto.getPeopleInside().get(i).getPersonPosition()
													.getCol()] == 0)
									|| (hallModelDto.getHallMatrix()[hallModelDto.getPeopleInside().get(i)
											.getPersonPosition().getRow() + 1][hallModelDto.getPeopleInside().get(i)
													.getPersonPosition().getCol()] == 0
											&& random.nextInt(2) == 1)) {

								int row = hallModelDto.getPeopleInside().get(i).getPersonPosition().getRow();
								int col = hallModelDto.getPeopleInside().get(i).getPersonPosition().getCol();

								hallModelDto.getHallMatrix()[row + 1][col] = 2;
								hallModelDto.getHallMatrix()[row][col] = 0;

								hallModelDto.getPeopleInside().get(i).getPersonPosition().setRow(row + 1);

								hallModelDto.getPeopleInside().get(i).getPathToExit().add((row + 1) + "," + col);
								
							} else {

								int colIncrementer = Utility.getNearestForwardMovableColumnIncrementer(hallModelDto, i);
								
								if (hallModelDto.getHallMatrix()[hallModelDto.getPeopleInside().get(i)
										.getPersonPosition().getRow()][hallModelDto.getPeopleInside().get(i)
												.getPersonPosition().getCol() + colIncrementer] == 0) {

									int row = hallModelDto.getPeopleInside().get(i).getPersonPosition().getRow();
									int col = hallModelDto.getPeopleInside().get(i).getPersonPosition().getCol();

									hallModelDto.getHallMatrix()[row][col + colIncrementer] = 2;
									hallModelDto.getHallMatrix()[row][col] = 0;

									hallModelDto.getPeopleInside().get(i).getPersonPosition()
											.setCol(col + colIncrementer);
									
									hallModelDto.getPeopleInside().get(i).getPathToExit().add((row) + "," + (col + colIncrementer));
									
									

								} else if (hallModelDto.getHallMatrix()[hallModelDto.getPeopleInside().get(i)
										.getPersonPosition().getRow()][hallModelDto.getPeopleInside().get(i)
												.getPersonPosition().getCol() + (-1 * colIncrementer)] == 0) {

									int row = hallModelDto.getPeopleInside().get(i).getPersonPosition().getRow();
									int col = hallModelDto.getPeopleInside().get(i).getPersonPosition().getCol();

									colIncrementer = -1 * colIncrementer;

									hallModelDto.getHallMatrix()[row][col + colIncrementer] = 2;
									hallModelDto.getHallMatrix()[row][col] = 0;

									hallModelDto.getPeopleInside().get(i).getPersonPosition()
											.setCol(col + colIncrementer);
									
									hallModelDto.getPeopleInside().get(i).getPathToExit().add((row) + "," + (col + colIncrementer));
									
								}
								
								//Utility.writeHall2DMatrix("C:\\Users\\sai goud\\Desktop\\ModelOut.txt", hallModelDto);

							}

						}
					}

				}

			}

			if(secondsCounter%5 == 0) {
				//Utility.printHall2DMatrix(hallModelDto);
				//System.out.println();
				//System.out.println();
				System.out.println("Current Second : " + secondsCounter);
				System.out.println("Total People Inside : " + hallModelDto.getTotalNumberOfPeopleStillInside());
				System.out.println("Total People Evacuated : " + (hallModelDto.getTotalNumberOfPeople() - hallModelDto.getTotalNumberOfPeopleStillInside()));
				System.out.println("People left inside : " + hallModelDto.getTotalNumberOfPeopleStillInside());
				System.out.println("Total number of people Evacuated : " + (hallModelDto.getTotalNumberOfPeople() - hallModelDto.getTotalNumberOfPeopleStillInside()));
				//System.out.println("hallModelDto.getTotalNumberOfPeople() : " + hallModelDto.getTotalNumberOfPeople());
				//Utility.printExitIdentifierToExitCountMap(exitIdentifierToExitCountMap);
				//System.out.println();
				//System.out.println();
				//Thread.sleep(1000);
				
				Utility.writeTOOutFile("C:\\Users\\sai goud\\Desktop\\Out.txt", secondsCounter,
						exitIdentifierToExitCountMap);
				
				exitIdentifierToExitCountMap = new HashMap<Character, Integer>();
				
				Utility.writeHall2DMatrix("C:\\Users\\sai goud\\Desktop\\ModelOut.txt", hallModelDto);
				
				
				
			}
			
			

		}
		
		//if(secondsCounter%5 != 0) {
			Utility.printHall2DMatrix(hallModelDto);
			System.out.println();
			System.out.println();
			System.out.println("Current Second : " + secondsCounter);
			Utility.printExitIdentifierToExitCountMap(exitIdentifierToExitCountMap);
			Utility.printExitIdentifierToExitCountMap(totalsExitIdentifierToExitCountMap);
			System.out.println("People left inside : " + hallModelDto.getTotalNumberOfPeopleStillInside());
			System.out.println("Total number of people Evacuated : " + (hallModelDto.getTotalNumberOfPeople() - hallModelDto.getTotalNumberOfPeopleStillInside()));
			System.out.println();
			System.out.println();
			
			Utility.writeTOOutFile("C:\\Users\\sai goud\\Desktop\\Out.txt", secondsCounter,
					exitIdentifierToExitCountMap);
		//}
		
			Utility.writeTOFinalFile("C:\\Users\\sai goud\\Desktop\\FinalOut.txt", secondsCounter,
					totalsExitIdentifierToExitCountMap);
			
			Utility.writeTOPersonsInfoFile("C:\\Users\\sai goud\\Desktop\\PersonsInfo.txt", hallModelDto);
			
			Utility.writePersonPathToFile("C:\\Users\\sai goud\\Desktop\\PersonsPath.txt", hallModelDto, 1, 1);
			
		}
		

	}	
	public static void main1(String[] args) throws IOException {
		
		HallModelDTO hallModelDto = new HallModelDTO();
		
		hallModelDto.setTotalStallsCount(13);
		int rows = 208 + 2; //3 extra rows because of wall
		int cols = 360;
		
		
		hallModelDto.setRows(rows);
		hallModelDto.setCols(cols);
		hallModelDto.setHallMatrix(new int[rows][cols]);
		
		hallModelDto.setEmptySpaceToLeaveAtTop(15);
		hallModelDto.setEmptySpaceToLeaveAtBottom(15);
		hallModelDto.setEmptySpaceToLeaveAtLeft(15);
		hallModelDto.setEmptySpaceToLeaveAtRight(15);
		
		hallModelDto.setUpperDeckStallHeight(80);
		hallModelDto.setUpperDeckStallWidth(16);
		
		hallModelDto.setLowerDeckStallHeight(88);
		hallModelDto.setLowerDeckStallWidth(16);
		
		hallModelDto.setGapBetweeenAisles(10);
		
		hallModelDto.setTotalExitsCount(3);
		hallModelDto.setExitLength(12);
		
		hallModelDto.setStaffCountAtEachStall(2);
		hallModelDto.setStallUnitHeight(8);
		hallModelDto.setStallUnitWidth(8);
		
		hallModelDto.setPeopleCountAtEachStall(8);
		
		Utility.createWall(hallModelDto);
		Utility.createModel(hallModelDto);
		Utility.printHall2DMatrix(hallModelDto);
		Utility.fillStaffAtStallsIntoHallMatrix(hallModelDto);
		Utility.fillPeopleAtStallsIntoHallMatrix(hallModelDto);
		Utility.fillPeopleIntoHallMatrix(hallModelDto, 540);
		Utility.printHall2DMatrix(hallModelDto);
		Utility.fillExitsIntoHallMatrix(hallModelDto);
		Utility.writeHall2DMatrix("C:\\Users\\sai goud\\Desktop\\ModelNew.txt", hallModelDto);
		
	}

	//Main for 3000 People Matric
	public static void main2(String[] args) throws IOException {
		
		HallModelDTO hallModelDto = new HallModelDTO();
		
		hallModelDto.setTotalStallsCount(13);
		int rows = 208 + 2; //3 extra rows because of wall
		int cols = 360;
		
		
		hallModelDto.setRows(rows);
		hallModelDto.setCols(cols);
		hallModelDto.setHallMatrix(new int[rows][cols]);
		
		hallModelDto.setEmptySpaceToLeaveAtTop(15);
		hallModelDto.setEmptySpaceToLeaveAtBottom(15);
		hallModelDto.setEmptySpaceToLeaveAtLeft(15);
		hallModelDto.setEmptySpaceToLeaveAtRight(15);
		
		hallModelDto.setUpperDeckStallHeight(80);
		hallModelDto.setUpperDeckStallWidth(16);
		
		hallModelDto.setLowerDeckStallHeight(88);
		hallModelDto.setLowerDeckStallWidth(16);
		
		hallModelDto.setGapBetweeenAisles(10);
		
		hallModelDto.setTotalExitsCount(3);
		hallModelDto.setExitLength(12);
		
		hallModelDto.setStaffCountAtEachStall(8);
		hallModelDto.setStallUnitHeight(8);
		hallModelDto.setStallUnitWidth(8);
		
		hallModelDto.setPeopleCountAtEachStall(0);
		
		Utility.createWall(hallModelDto);
		Utility.createModel(hallModelDto);
		Utility.printHall2DMatrix(hallModelDto);
		Utility.fillStaffAtStallsIntoHallMatrix(hallModelDto);
		//Utility.fillPeopleAtStallsIntoHallMatrix(hallModelDto);
		Utility.fillPeopleIntoHallMatrix(hallModelDto, 132);
		Utility.printHall2DMatrix(hallModelDto);
		Utility.fillExitsIntoHallMatrix(hallModelDto);
		Utility.writeHall2DMatrix("C:\\Users\\sai goud\\Desktop\\ModelNew.txt", hallModelDto);
		
	}
	

	
	public static void main3(String[] args) throws IOException {
		
		BufferedReader br = new BufferedReader(new FileReader(new File("C:\\Users\\sai goud\\Desktop\\ModelNew.txt")));
		br.readLine();
		br.readLine();
		br.readLine();
		String line = br.readLine();
		String[] splits = line.split(",");
		
		System.out.println("Total Characters : " + splits.length);
		
		String previousString = "";
		
		for(int i=0; i<splits.length; i++) {
			
			
			if(previousString.equals("")) {
				
				System.out.println("EQ");
				
			}
			
		}
		
		br.close();
		
	}
	
	//Main for 6000 Two Bottom and One right
	public static void main4(String[] args) throws IOException {
		
		HallModelDTO hallModelDto = new HallModelDTO();
		
		hallModelDto.setTotalStallsCount(13);
		int rows = 208 + 2; //3 extra rows because of wall
		int cols = 360;
		
		
		hallModelDto.setRows(rows);
		hallModelDto.setCols(cols);
		hallModelDto.setHallMatrix(new int[rows][cols]);
		
		hallModelDto.setEmptySpaceToLeaveAtTop(15);
		hallModelDto.setEmptySpaceToLeaveAtBottom(15);
		hallModelDto.setEmptySpaceToLeaveAtLeft(15);
		hallModelDto.setEmptySpaceToLeaveAtRight(15);
		
		hallModelDto.setUpperDeckStallHeight(80);
		hallModelDto.setUpperDeckStallWidth(16);
		
		hallModelDto.setLowerDeckStallHeight(88);
		hallModelDto.setLowerDeckStallWidth(16);
		
		hallModelDto.setGapBetweeenAisles(10);
		
		hallModelDto.setTotalExitsCount(3);
		hallModelDto.setExitLength(12);
		
		hallModelDto.setStaffCountAtEachStall(2);
		hallModelDto.setStallUnitHeight(8);
		hallModelDto.setStallUnitWidth(8);
		
		hallModelDto.setPeopleCountAtEachStall(8);
		
		Utility.createWall(hallModelDto);
		Utility.createModel(hallModelDto);
		Utility.printHall2DMatrix(hallModelDto);
		Utility.fillStaffAtStallsIntoHallMatrix(hallModelDto);
		Utility.fillPeopleAtStallsIntoHallMatrix(hallModelDto);
		Utility.fillPeopleIntoHallMatrix(hallModelDto, 540);
		Utility.printHall2DMatrix(hallModelDto);
		Utility.fillExitsIntoHallMatrixTwoDownOneRight(hallModelDto);
		Utility.writeHall2DMatrix("C:\\Users\\sai goud\\Desktop\\ModelNewTwoDownOneRight.txt", hallModelDto);
		
	}
	
	//Main for 6000 Three right
	public static void main5(String[] args) throws IOException {
		
		HallModelDTO hallModelDto = new HallModelDTO();
		
		hallModelDto.setTotalStallsCount(13);
		int rows = 208 + 2; //3 extra rows because of wall
		int cols = 360;
		
		
		hallModelDto.setRows(rows);
		hallModelDto.setCols(cols);
		hallModelDto.setHallMatrix(new int[rows][cols]);
		
		hallModelDto.setEmptySpaceToLeaveAtTop(15);
		hallModelDto.setEmptySpaceToLeaveAtBottom(15);
		hallModelDto.setEmptySpaceToLeaveAtLeft(15);
		hallModelDto.setEmptySpaceToLeaveAtRight(15);
		
		hallModelDto.setUpperDeckStallHeight(80);
		hallModelDto.setUpperDeckStallWidth(16);
		
		hallModelDto.setLowerDeckStallHeight(88);
		hallModelDto.setLowerDeckStallWidth(16);
		
		hallModelDto.setGapBetweeenAisles(10);
		
		hallModelDto.setTotalExitsCount(3);
		hallModelDto.setExitLength(12);
		
		hallModelDto.setStaffCountAtEachStall(2);
		hallModelDto.setStallUnitHeight(8);
		hallModelDto.setStallUnitWidth(8);
		
		hallModelDto.setPeopleCountAtEachStall(8);
		
		Utility.createWall(hallModelDto);
		Utility.createModel(hallModelDto);
		Utility.printHall2DMatrix(hallModelDto);
		Utility.fillStaffAtStallsIntoHallMatrix(hallModelDto);
		Utility.fillPeopleAtStallsIntoHallMatrix(hallModelDto);
		Utility.fillPeopleIntoHallMatrix(hallModelDto, 540);
		Utility.printHall2DMatrix(hallModelDto);
		Utility.fillExitsIntoHallMatrixThreeRight(hallModelDto);
		Utility.writeHall2DMatrix("C:\\Users\\sai goud\\Desktop\\ModelNewThreeRight.txt", hallModelDto);
		
	}
	
	//For Filling 1000 memebers into Model3 or Model4 For Making Total People 7000
	public static void main11(String[] args) throws IOException {
		
		HallModelDTO hallModelDto = Utility.loadModelFromFile(
				"C:\\Users\\sai goud\\Documents\\Softwares\\eclipse-jee-mars-2-win32-x86_64\\eclipse\\workspace\\VamsiCivilUakron\\src\\com\\sai\\resource\\Model4.txt");
		Utility.fillPeopleIntoHallMatrix(hallModelDto, 1000);
		Utility.writeHall2DMatrix("C:\\Users\\sai goud\\Desktop\\Model4-7000.txt", hallModelDto);
	}


}
