package nearu;

import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Scanner;

public class GradingSystem {
	static ArrayList<Student> studentList = new ArrayList<Student>();
	
	/**
	 *  the pattern of grading_system.txt file : 
	 *  stuName  N(num of grade info records)  (followed by several grade info)
	 *	courseName grade
	 *  ...
	 * @throws IOException 
	 */
	public static void init() throws IOException{
		OutputStream emptyFile;
		
		try {
			InputStream dataStream = new FileInputStream("C:\\Users\\Administrator\\workspace\\GradingSystem\\bin\\nearu\\grading_system.txt");
			Scanner scanner = new Scanner(dataStream);
			String stuName;
			String courseName;
			double grade;
			int num;
			while(scanner.hasNext()){
			    stuName = scanner.next();
				num     = scanner.nextInt(); 
				Student stu = new Student(stuName);
				studentList.add(stu);
				for(int i = 0; i < num; ++i){
					courseName = scanner.next();
					grade = scanner.nextDouble();
					stu.insert_course_info(courseName, grade);					
				}					
			}
			dataStream.close();
			System.out.println("init....");
		} catch (FileNotFoundException e) {
			try {
				emptyFile = new FileOutputStream("C:\\Users\\Administrator\\workspace\\GradingSystem\\bin\\nearu\\grading_system.txt");
				emptyFile.close();
				System.out.println("Create a new database!");
			} catch (FileNotFoundException e1) {
				System.out.println("Error: can't create txt file->grading_system");
			}
		}
	}
	public static void save() throws IOException{
		FileOutputStream out = new FileOutputStream("C:\\Users\\Administrator\\workspace\\GradingSystem\\bin\\nearu\\grading_system.txt");
		DataOutputStream dataOut = new DataOutputStream(out);
		Student stu;
		int num;
		ArrayList<String> courseInfo;
		for(int i = 0; i < studentList.size(); ++i){
			stu = studentList.get(i);
			num = stu.get_course_count();
			dataOut.writeBytes(stu.stuName + " " + num + "\r\n");
			courseInfo  = stu.get_course_info_list();
			for(int j = 0; j < num; ++j){
				dataOut.writeBytes(courseInfo.get(j) + "\r\n");
			}		
		}
		dataOut.close();
		out.close();
	}
	public static Student get_stu_by_name(String stuName){
		for(int i = 0; i < studentList.size(); ++i){
			if(stuName.equals(studentList.get(i).stuName))
				return studentList.get(i);
		}
		return null;
	}
	public static void main(String[] args) {
		try {
			GradingSystem.init();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
