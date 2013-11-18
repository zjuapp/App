package nearu;

public class LogRecord {
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
	LogRecord(String _remoteHost,String _logname, String _remoteUser, String _accTime, String _timeZone, String _method, String _pageAddr, String _protocol, String _returnCode, String _size){
		remoteHost 	= _remoteHost;
		logname		= _logname;
		remoteUser	= _remoteUser;
		accTime  	= _accTime;
		timeZone 	= _timeZone;
		method  	= _method;
		pageAddr 	= _pageAddr;
		protocol 	= _protocol;
		returnCode  = _returnCode;
		size 		= _size;
	}
	

}
