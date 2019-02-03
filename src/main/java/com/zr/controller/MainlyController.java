package com.zr.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zr.cmpt.SerialComDemon;

@Controller
@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})
public class MainlyController {

	@Autowired
	public SerialComDemon serialComDemon;
	
	

	@RequestMapping("/sendSerial")
	@ResponseBody
	public Map<String,Object> sendSerial(@RequestParam("msgs") String msg, Model model, HttpSession session) {
		Map<String, Object> map = new HashMap<>();
	
			System.out.println(msg);
			serialComDemon.sendData(msg);
	
		return map;
	}

	

	
    
    


    @RequestMapping("/connect")
	public String connect(HttpServletRequest request,
			HttpSession session) {

    	
    	serialComDemon.closePort();
    	String port = request.getParameter("port");
    	System.out.println(port);
    	String rate = request.getParameter("rate");
    	System.out.println(rate);
		serialComDemon.openPort(port,Integer.parseInt(rate));
		
		
		

		
	
		return "forward:/";
	}
    
    

	
	@RequestMapping("/")
	public String index(String account, Model model,
			HttpSession session) {

		String portArr =serialComDemon.getPorts();
		
		
		if(serialComDemon.getSerialPort()==null)
			
		{
			session.setAttribute("condition", "No Connection");
			session.setAttribute("Connector", "None");
			session.setAttribute("ph", "Input ComPort");
			}
		else {
			session.setAttribute("condition", "Connecting");
			String comName=serialComDemon.getSerialPort().getName();
			session.setAttribute("Connector", comName);
			session.setAttribute("ph", "Input Msg");
			
		}
		
		
		session.setAttribute("Ports", portArr);
		
		return "index";
	}

}
