package com.zr.service;
 
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.TooManyListenersException;

import org.springframework.stereotype.Service;

import gnu.io.CommPort;
import gnu.io.CommPortIdentifier;
import gnu.io.NoSuchPortException;
import gnu.io.PortInUseException;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;
import gnu.io.UnsupportedCommOperationException;
 
/**
 * TODO
 * @author XWF
 */

@Service
public final class RXTXService {
 
	
	
	@SuppressWarnings("unchecked")
	public static String getSystemPort(){
		List<String> systemPorts = new ArrayList<>();
		StringBuffer sb = new StringBuffer();
		Enumeration<CommPortIdentifier> portList = CommPortIdentifier.getPortIdentifiers();
		while(portList.hasMoreElements()) {
			String portName = portList.nextElement().getName();
			
			systemPorts.add(portName);
			sb.append(portName).append(" ");
		}
		System.out.println("Find COMPORT :"+systemPorts);
		return sb.toString() ;
	}
	
	
	public static SerialPort openSerialPort(String serialPortName,int baudRate) {
		try {
			
			CommPortIdentifier portIdentifier = CommPortIdentifier.getPortIdentifier(serialPortName);
		
			CommPort commPort = portIdentifier.open(serialPortName, 2222);
			
            if (commPort instanceof SerialPort) {
                SerialPort serialPort = (SerialPort) commPort;
              
                serialPort.setSerialPortParams(baudRate, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);                              
                System.out.println("Open Port:"+serialPortName);
                return serialPort;
            }        
            else {
               
                throw new NoSuchPortException();
            }
		} catch (NoSuchPortException e) {
			e.printStackTrace();
		} catch (PortInUseException e) {
			e.printStackTrace();
		} catch (UnsupportedCommOperationException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * �رմ���
	 * @param serialPort Ҫ�رյĴ��ڶ���
	 */
	public static void closeSerialPort(SerialPort serialPort) {
		if(serialPort != null) {
			serialPort.close();
			System.out.println("Close "+serialPort.getName());
			serialPort = null;
		}
	}
 
	/**
	 * �򴮿ڷ�������
	 * @param serialPort ���ڶ��� 
	 * @param data ���͵�����
	 */
	public static void sendData(SerialPort serialPort, byte[] data) {
		OutputStream os = null;
        try {
        	os = serialPort.getOutputStream();//��ô��ڵ������
        	os.write(data);
        	os.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (os != null) {
                	os.close();
                	os = null;
                }                
            } catch (IOException e) {
            	e.printStackTrace();
            }
        }
	}
	
	/**
	 * �Ӵ��ڶ�ȡ����
	 * @param serialPort Ҫ��ȡ�Ĵ���
	 * @return ��ȡ������
	 */
	public static byte[] readData(SerialPort serialPort) {
		InputStream is = null;
        byte[] bytes = null;
        try {
        	is = serialPort.getInputStream();//��ô��ڵ�������
            int bufflenth = is.available();//������ݳ���
            while (bufflenth != 0) {                             
                bytes = new byte[bufflenth];//��ʼ��byte����
                is.read(bytes);
                bufflenth = is.available();
            } 
        } catch (IOException e) {
        	e.printStackTrace();
        } finally {
            try {
                if (is != null) {
                	is.close();
                	is = null;
                }
            } catch(IOException e) {
            	e.printStackTrace();
            }
        }
        return bytes;
	}
	
	/**
	 * ���������ü���
	 * @param serialPort
	 * @param listener
	 */
	public static void setListenerToSerialPort(SerialPort serialPort, SerialPortEventListener listener) {
		try {
			//����������¼�����
			serialPort.addEventListener(listener);
		} catch (TooManyListenersException e) {
			e.printStackTrace();
		}
		serialPort.notifyOnDataAvailable(true);//���������ݼ���
		serialPort.notifyOnBreakInterrupt(true);//�ж��¼�����
	}
	
}