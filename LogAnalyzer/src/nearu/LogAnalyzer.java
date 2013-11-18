package nearu;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class LogAnalyzer {
	ArrayList<LogRecord> logRecordList;
	ArrayList<Map<String,String>>   timeTable;
	int []dayRecord;
	int []monthRecord;
	String []month = {
			"Jan",
			"Feb",
			"Mar",
			"Apr",
			"May",
			"Jun",
			"Jul",
			"Aug",
			"Sep",
			"Oct",
			"Nov",
			"Dec"
			};
	LogAnalyzer(){
		logRecordList = new ArrayList<LogRecord>();
		timeTable	  = new ArrayList<Map<String,String>>();
		dayRecord 	  = new int[48];
		for(int i = 0; i < 48; ++i){
			dayRecord[i] = 0;
		}
		monthRecord   = new int[31];
		for(int i = 0; i < 31; ++i){
			monthRecord[i] = 0;
		}
	}
	
	void read_log(String fileName){
		String line = "";
		int lineNum = 0;
		try {
			InputStream logStream = new FileInputStream(fileName);
			Scanner scanner 	  = new Scanner(logStream);
			
			String[] items;
			String remoteHost;
			String logname;
			String remoteUser;
			String accTime;
			String timeZone;
			String method;
			String pageAddr;
			String protocol;
			String returnCode;
			String size;
			while(scanner.hasNext()){
				lineNum++;
				line  		= scanner.nextLine();
				items = line.split(" ");
				remoteHost	= items[0]; 
				logname  	= items[1];
				remoteUser	= items[2];
				timeZone	= items[4].substring(0, items[4].length()-1);
				accTime 	= time_process(items[3].substring(1),Integer.parseInt(timeZone));
				method		= items[5].substring(1);
				pageAddr 	= items[6];
				protocol	= items[7].substring(0, items[7].length());
				returnCode  = items[8];
				size		= items[9];
				logRecordList.add(new LogRecord(remoteHost,logname,remoteUser,accTime,timeZone, method, pageAddr, protocol, returnCode, size));
			}
		} catch (FileNotFoundException e) {
			System.out.println("Error: cannot open log file " + fileName );
		}catch (Exception e){
			e.printStackTrace();
			System.out.println();
			System.out.println(lineNum + "	" + line);
		}

	}
	/**
	 * 
	 * @param time_input e.g. 18/Mar/2012:17:37:24
	 * @return yyyy-mm-dd hh:mm:ss
	 */
	String time_process(String time_input, int timezone){
		String Y,M,D,H,Min,S;
		String []itemsSlash;
		String []itemsComm;
		itemsSlash 	= time_input.split("/");
		D 			= itemsSlash[0];
		M 			= itemsSlash[1];
		M			= get_month(M);
		itemsComm 	= itemsSlash[2].split(":");
		Y 			= itemsComm[0];
		H			= itemsComm[1];
		Min			= itemsComm[2];
		S			= itemsComm[3];
		Map<String,String> time = new HashMap<String, String>();
		time.put("year", Y);
		time.put("month", M);
		time.put("day", D);
		time.put("hour", H);
		time.put("minute", Min);
		time.put("second", S);
		timeTable.add(time);
		dayRecord[Integer.parseInt(H) * 2]++;
		monthRecord[Integer.parseInt(D)]++;
		return Y + "-" + M +"-" + D +" " + H + ":" + Min + ":" + S; 
	}
	
	String get_month(String mon){
		for(int i = 0; i < 12; ++i){
			if(month[i].equals(mon)){
				return new String(new Integer(i+1).toString());
			}
		}
		return null;
	}
	
	/**
	 * 
	 * @return the address of the most popular page
	 */
	String get_most_popular_page(){
		Map<String, Integer>pageMap = new HashMap<String, Integer>();
		String result = "";
		int max = 0;
		for(LogRecord record  : logRecordList){
			String addr = record.pageAddr;
			if(!pageMap.containsKey(addr)){
				pageMap.put(addr, 1);
			}else{
				int cur = pageMap.get(addr);
				pageMap.put(addr, cur+1);
			}
			int now = pageMap.get(addr);
			if(now > max){
				max = now;
				result = addr;
			}
		}
		return result + " was the most popular page, with " + max + " accesses";
	}
	
	String get_ip_get_most_pages(){
		Map<String, Integer>pageMap = new HashMap<String, Integer>();
		String result = "";
		int max = 0;
		for(LogRecord record  : logRecordList){
			String addr = record.remoteHost;
			if(!pageMap.containsKey(addr)){
				pageMap.put(addr, 1);
			}else{
				int cur = pageMap.get(addr);
				pageMap.put(addr, cur+1);
			}
			int now = pageMap.get(addr);
			if(now > max){
				max = now;
				result = addr;
			}
		}
		return result + " get most pages which is " + max;
	}
	
	String get_most_busy_in_one_day(){
		int max = 0;
		int maxHalfHour = 0;
		for(int i = 0; i < 48; ++i){
			if(dayRecord[i] > max) {
				max = dayRecord[i];
				maxHalfHour = i + 1;
			}
		}
		return "the busyest time in a day is at " + (maxHalfHour / 2.0) + " 'clock, with " + max + " accesses";  
	}
	
	String get_most_busy_in_one_month(){
		int max = 0;
		int maxDay = 0;
		for(int i = 0; i < 31; ++i){
			if(monthRecord[i] > max) {
				max = monthRecord[i];
				maxDay = i + 1;
			}
		}
		return "the most busy day in a month is at day " + maxDay + " with " + max + " accesses";  
	}
	
	String get_total_data(){
		int result = 0;
		for(LogRecord l : logRecordList){
			if(l.returnCode.equals("200") && !l.size.equals("-")){
				result += Integer.parseInt(l.size);
			}
		}
		return "the total data delivered is " + result  + " bytes";
	}
	
	void get_broken_link(){
		ArrayList<String> brokenLink = new ArrayList<String>();
		for(LogRecord l: logRecordList){
			if(l.returnCode.equals("404")){
				brokenLink.add(l.pageAddr);
			}
		}
		System.out.println("the broken link from other websites are :");
		for(String s : brokenLink){
			System.out.println(s);
		}
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		LogAnalyzer l = new LogAnalyzer();
		l.read_log("C:\\Users\\Administrator\\workspace\\LogAnalyzer\\bin\\nearu\\access.log");
		System.out.println(l.get_most_popular_page());
		System.out.println(l.get_ip_get_most_pages());
		System.out.println(l.get_most_busy_in_one_day());
		System.out.println(l.get_most_busy_in_one_month());
		System.out.println(l.get_total_data());
		//l.get_broken_link();
	}

}

