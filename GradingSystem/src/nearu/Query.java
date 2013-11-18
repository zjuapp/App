package nearu;

import java.io.IOException;
import java.util.ArrayList;

public class Query {
	Query(){
		try {
			GradingSystem.init();
		} catch (IOException e) {
			System.out.println("Error: grading system failed init!");
		}
	}
	
	void query_stu_name(String stuName){
		Student stu = GradingSystem.get_stu_by_name(stuName);
		System.out.println("===================");
		if(stu == null ){
			System.out.println("Sorry, this student does not exist in the database!");
		}else{
			System.out.println("course name |  grade");
			stu.print_course_info();
		}
		System.out.println("===================");
	}
	
	void query_course(String courseName){
		ArrayList<Student> stuList = GradingSystem.studentList;
		int sumStu = 0;
		double sumMarks = 0.0;
		Student stu;
		System.out.println("===================");
		System.out.println("Student Name: ");
		for(int i = 0; i < stuList.size(); ++i){
			 stu = stuList.get(i);
			 double marks = stu.get_course_mark(courseName);
			 if(marks > 0){
				 sumMarks += marks;
				 sumStu++;
				 System.out.println("	" + stu.stuName);
			 }
		}
		System.out.println("total student(s): \t" + sumStu);
		System.out.println("avg marks:        \t" + sumMarks/sumStu);
		System.out.println("===================");
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Query q = new Query();
		if(args.length != 2){
			System.out.println("Error: Please input proper args");
		}else{
			if(args[0].equals("course")){
				q.query_course(args[1]);
			}else if(args[0].equals("student")){
				q.query_stu_name(args[1]);
			}else{
				System.out.println("Error: Undefined arg " + args[1] );
			}
		}

	}

}
