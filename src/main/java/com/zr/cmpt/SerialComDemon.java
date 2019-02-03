package com.zr.cmpt;

import java.io.IOException;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.zr.service.RXTXService;

import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;

@Component
public class SerialComDemon {
	
	@Autowired
	public RXTXService rxtxService;
	
	@Autowired
	public WebSocket webSocket;

	String ports = null;
	
	SerialPort serialPort =null;
	
	 public String getPorts() {
		return ports;
	}


	public void setPorts(String ports) {
		this.ports = ports;
	}


	private SerialComDemon() {
		// TODO Auto-generated constructor stub
	}
	 
	 
	 public SerialPort getSerialPort() {
		return serialPort;
	}


	public void setSerialPort(SerialPort serialPort) {
		this.serialPort = serialPort;
	}


	@PostConstruct
	 public void init() {
		ports = rxtxService.getSystemPort();
		//openPort(ports.split(" ")[0],9600);
	 }
	
	
	public void openPort(String port,int portNum) {
		 serialPort = rxtxService.openSerialPort(port,portNum);
		 
		  RXTXService.setListenerToSerialPort(serialPort, new SerialPortEventListener() {
				@Override
				public void serialEvent(SerialPortEvent arg0) {
					if(arg0.getEventType() == SerialPortEvent.DATA_AVAILABLE) {//����֪ͨ
						byte[] bytes = RXTXService.readData(serialPort);
				
						try {
							webSocket.sendInfo("R-"+new String(bytes));
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			});
	}
	
	
	public void sendData(String data) {
		byte[] bytes = data.getBytes();
		rxtxService.sendData(serialPort, bytes);
		
		
		try {
			webSocket.sendInfo("S-"+data);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public void closePort() {
		if(serialPort!=null)
		rxtxService.closeSerialPort(serialPort);
	}
}
