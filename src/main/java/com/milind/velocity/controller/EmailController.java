package com.milind.velocity.controller;



import java.util.HashMap;
import java.util.Map;

import javax.mail.internet.MimeMessage;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.milind.velocity.helper.TemplateGenerator;
import com.milind.velocity.model.EmailDTO;

@RestController
@RequestMapping("/v1/email")
public class EmailController {
	
	@Autowired
	private JavaMailSender javaMailSender;
	
	@Autowired
	TemplateGenerator templateGenerator;
	
	@GetMapping("/version")
	@ResponseStatus(HttpStatus.OK)
	public String version() {
		return "[OK] Welcome to Email Restful version 1.0";
	}
	
	@PostMapping(value = "send", produces = { "application/xml", "application/json" })
	public ResponseEntity<EmailDTO> sendMail(@RequestBody EmailDTO email) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom(email.getMailFrom());
		message.setTo(email.getMailTo());
		message.setSubject(email.getMailSubject());
		message.setText(email.getMailContent());
		javaMailSender.send(message);
		email.setStatus(true);

		return new ResponseEntity<EmailDTO>(email, HttpStatus.OK);
	}
	
	@PostMapping(value = "template", produces = { "application/xml", "application/json" })
	public ResponseEntity<EmailDTO> template(@PathVariable String templateName, @RequestBody EmailDTO email) throws Exception {

		Map<String, Object> model = new HashMap<String, Object>();
		model.put("title", email.getMailSubject());
		model.put("body", email.getMailContent());
		
		
		String text = TemplateGenerator.generateTemplate(templateName, email);

		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		
		MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
		mimeMessageHelper.setFrom(email.getMailFrom());
		mimeMessageHelper.setTo(email.getMailTo());
		mimeMessageHelper.setSubject(email.getMailSubject());
		mimeMessageHelper.setText(text, true);

		javaMailSender.send(mimeMessage);
		
		email.setStatus(true);

		return new ResponseEntity<EmailDTO>(email, HttpStatus.OK);
	}
	

}
