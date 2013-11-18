package nearu;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;



public class Student {
	String stuName;
	HashMap<String, Double> courseGrade;
	Collection<Double> marks;
	Student(String _stuName){
		stuName = new String(_stuName);
		courseGrade = new HashMap<String, Double>();
		marks = courseGrade.values();
	}
	
	public void insert_course_info(String courseName, double grade){
		courseGrade.put(courseName, new Double(grade));
	}
	
	public void print_course_info(){
		String course = new String(courseGrade.toString());
		course = course.substring(1, course.length()-1);
		String[] courseList = course.split(",");
		for(int i = 0; i < courseList.length; ++i){
			System.out.println(courseList[i].trim());
		}
		System.out.println("Total marks: " + get_sum_marks());
		System.out.println("average marks: " + get_avg_marks());
	}
	
	public double get_sum_marks(){
		double sum = 0.0;
		for(Iterator iter = marks.iterator();iter.hasNext();  )
		{
			sum += (Double)iter.next();
		}
		return sum;
	}
	
	public double get_avg_marks(){
		return get_sum_marks() / marks.size();
	}
	
	public int get_course_count(){
		return marks.size();
	}
	
	public ArrayList<String> get_course_info_list(){
		ArrayList<String> returnArray = new ArrayList<String>(); 
		String courseInfo;
		for(Iterator iter = courseGrade.entrySet().iterator(); iter.hasNext(); ){
			Entry entry   = (Entry)iter.next();
			courseInfo    = (entry.getKey() + " " + entry.getValue());
			returnArray.add(courseInfo) ;
		}
		return returnArray;
	}
	public double get_course_mark(String courseName){
		if(courseGrade.containsKey(courseName)){
			return courseGrade.get(courseName);
		}else return -1;
	}
	
	
	
}
