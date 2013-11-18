import java.util.Enumeration;
import gnu.io.*;
import java.io.*;
import java.util.*;

public class SerialPortManager {
	@SuppressWarnings("unchecked")
	static Enumeration portList; 
	static Map<String,CommPortIdentifier> portIdMap;
	SerialPort serialPort;
	OutputStream portOutStream;
	SerialPortManager(){
		portIdMap = new HashMap<String, CommPortIdentifier>();
	}

	/**
	* find all serial ports and print them
	*/
	void get_serial_ports(){
		CommPortIdentifier portId = null;
		try{
			portList = CommPortIdentifier.getPortIdentifiers();
			while(portList.hasMoreElements()){
				portId = (CommPortIdentifier) portList.nextElement();
				if (portId.getPortType() == CommPortIdentifier.PORT_SERIAL) {
					portIdMap.put(portId.getName(), portId);
					System.out.println(portId.getName());
				}
			}
		}catch (Exception e){
			e.printStackTrace();
		}
	}

	void select_port() throws Exception{	
		Scanner scanner 		  = new Scanner(System.in);
		String portName 		  = scanner.next();
		CommPortIdentifier portId = portIdMap.get(portName);
		serialPort 				  = (SerialPort)portId.open(portName, 2000);
		serialPort.setSerialPortParams(38400, 8, 1, 0);
		portOutStream = serialPort.getOutputStream();
	}
	void start_read_thread(){
		CommPortIdentifier portId;
		SerialPort serialPort;
		portId 		= portIdMap.get("COM2");
		serialPort 	= (SerialPort)portId.open("COM2",2000);
		serialPort.setSerialPortParams(38400, 8, 1, 0);
		Thread readThread = new Thread(new Runnable(){
			
		});
		readThread.start();
	}
	public static void main(String[] args) throws IOException{
		SerialPortManager spm = new SerialPortManager();
		spm.get_serial_ports();
		System.out.println("Please input a port name~");
		try{
			spm.select_port();
		}catch(Exception e){
			e.printStackTrace();
		}
		Scanner scanner = new Scanner(System.in);
		while(scanner.hasNext()){
			String text = scanner.next();
			if(text.equals("end")){
				break;
			}
			spm.portOutStream.write(text.getBytes());
			spm.portOutStream.flush();
		}
	}
}
