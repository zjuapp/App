package nearu;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Insert {
	ArrayList<Student> studentList;
	Insert(){
		try {
			GradingSystem.init();
		} catch (IOException e) {
			System.out.println("Error: grading system failed init!");
		}
		studentList = GradingSystem.studentList;
	}
	void insert(String[] input) throws IOException{
		String stuName 	  = input[0];
		String courseName = input[1];
		Double grade  	  = new Double(input[2]);
		Student stu = GradingSystem.get_stu_by_name(stuName);
		if(stu == null){
			stu = new Student(stuName);
			GradingSystem.studentList.add(stu);
		}
		stu.insert_course_info(courseName, grade);
		GradingSystem.save();
		System.out.println("insert successfully!");
	}	
	/**
	 * the pattern of csv file : stuName, 
	 * @param fileName
	 * @throws IOException 
	 */
	void insert(String fileName) throws IOException{
		FileInputStream sourse;
		sourse = new FileInputStream(fileName);
		Scanner scanner = new Scanner(sourse);	
		String stuName;
		String courseName;
		double grade;
		Student stu;
		while(scanner.hasNext()){
			String line   = scanner.nextLine();
			String[] info = line.trim().split(",");
			if(info.length != 3){
				continue;
			}
			stuName 	  = info[0].trim();
			courseName 	  = info[1].trim();
			grade		  = new Double(info[2]);
			stu			  = GradingSystem.get_stu_by_name(stuName);
			if(stu == null){
				stu = new Student(stuName);	
				GradingSystem.studentList.add(stu);
			}
			stu.insert_course_info(courseName, grade);
		}
		GradingSystem.save();
		System.out.println("insert successfully!");
	}
	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		Insert ist = new Insert();
		if(args.length == 1){
			ist.insert(args[0]);
		}else if(args.length == 3){
			ist.insert(args);
		}else{
			System.out.println("Error: please input proper args!");
		}
	}

}
