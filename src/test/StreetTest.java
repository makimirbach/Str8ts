package test;

import application.service.Helper;
import application.service.Str8tSolver;
import application.service.Street;

public class StreetTest {
	public static void test() {
		/*int[][] m = {{-4,6,5,0,1,2},{5,4,6,3,2,1},{6,5,0,4,3,0},{0,1,2,0,6,5},{2,3,1,-6,5,4},{3,2,0,5,4,0}};
		//int[][] state = {{4,6,5,0,1,2},{5,4,6,3,2,1},{0,0,0,4,3,0},{0,0,0,0,0,0},{2,3,1,6,5,4},{3,2,0,5,4,0}};
		//int[][] state = {{4,6,5,0,1,2},{5,4,6,3,2,1},{6,5,0,4,3,0},{0,1,2,0,6,5},{2,3,1,6,5,4},{3,2,0,5,4,0}};
		int[][] state = {{4,6,0,0,0,0},{0,0,0,0,0,1},{0,0,0,4,3,0},{0,0,0,0,0,0},{0,3,1,6,0,0},{3,0,0,0,4,0}};*/
		
		int[][] m = {
				{-4,2,3,0,0,8,7,9,6},
				{3,4,5,0,7,9,6,8,0},
				{2,3,4,6,9,7,8,5,0},
				{0,0,0,3,5,4,-9,6,7},
				{6,5,0,2,4,3,0,7,8},
				{7,8,0,4,6,5,0,0,-3},
				{0,9,7,8,3,6,2,4,5},
				{0,7,6,5,8,-1,3,2,4},
				{9,6,8,7,-2,0,4,3,0}};
		
		int[][] state = {
				{4,0,0,0,0,0,7,0,0},
				{0,0,5,0,0,9,0,8,0},
				{0,0,4,6,0,0,0,0,0},
				{0,0,0,0,0,0,9,0,0},
				{0,5,0,0,0,3,0,0,0},
				{0,0,0,0,0,0,0,0,3},
				{0,0,0,0,0,0,2,0,0},
				{0,7,6,5,8,1,0,0,0},
				{9,0,8,7,2,0,4,0,0}};
		Str8tSolver str8t = new Str8tSolver(Helper.cellMatrixFromEntries(state, m), Helper.cellMatrixFromEntries(m,m), m.length);
		str8t.printState();
		//System.out.println(str8t.getStreets());
		
		for (Street s: str8t.getStreets()) {
			s.findMissing();
		}
		
		System.out.println(str8t.getStreets());
		
		for (Street s: str8t.getStreets()) {
			s.checkCanEnterMissing();
		}
		
		System.out.println(str8t.getStreets());
		
		for (Street s: str8t.getStreets()) {
			str8t.blockedInStreet(s);
		}
		
		System.out.println(str8t.getStreets());
		
		System.out.println("find possible entries");
		for (Street s: str8t.getStreets()) {
			str8t.possibleInStreet(s);
		}
		
		System.out.println(str8t.getStreets());
		System.out.println("check can enter missing");
		
		for (Street s: str8t.getStreets()) {
			s.checkCanEnterMissing();
		}
		
		System.out.println(str8t.getStreets());
		
		str8t.printState();
	}
}
