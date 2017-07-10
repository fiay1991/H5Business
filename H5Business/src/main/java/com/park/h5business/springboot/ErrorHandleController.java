package com.park.h5business.springboot;

import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.web.bind.annotation.RequestMapping;

public class ErrorHandleController implements ErrorController{

	@Override
	public String getErrorPath() {
		System.out.println("进入error...........");
		return "index";  
	}
	
	@RequestMapping  
    public String errorHandle(){  
        return getErrorPath();  
    }  
}
