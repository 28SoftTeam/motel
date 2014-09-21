package com.example.motel;
import org.ksoap2.SoapEnvelope; 
import org.ksoap2.serialization.PropertyInfo; 
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

public class classWebServiceCallers {

	public final String SOAP_ACTION = "http://nyd.com.tr/WebServices/AddUser";
	public  final String OPERATION_NAME = "AddUser"; 
	public  final String WSDL_TARGET_NAMESPACE = "http://nyd.com.tr/WebServices";
	//public  final String SOAP_ADDRESS = "http://192.168.2.73/addUserWebService.asmx";
	//public  final String SOAP_ADDRESS = "http://172.17.196.73/addUserWebService.asmx";
	final String SOAP_ADDRESS = "http://coeus.28soft.org/addUserWebService.asmx";
	
	
	public String CallAddUser(String email, String nameSurname,String password)
	{
	SoapObject request = new SoapObject(WSDL_TARGET_NAMESPACE,OPERATION_NAME);
	PropertyInfo pi=new PropertyInfo();
	 request.addProperty("email",email);
	 request.addProperty("nameSurname",nameSurname);
	 request.addProperty("password",password);

	SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER10);
	envelope.dotNet = true;

	envelope.setOutputSoapObject(request);

	HttpTransportSE httpTransport = new HttpTransportSE(SOAP_ADDRESS);
	Object response=null;
	try
	{
		httpTransport.call(SOAP_ACTION, envelope);
		response = envelope.getResponse();
	}
	catch (Exception exception)
	{
		response=exception.toString();
	}
	return  response.toString();
	}
	}
