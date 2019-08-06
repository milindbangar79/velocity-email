package com.milind.velocity.service;

import com.milind.velocity.model.EmailDTO;

public interface EmailService {
	
	public void sendEmail(String templateId, EmailDTO email);

}
