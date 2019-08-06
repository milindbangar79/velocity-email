package com.milind.velocity.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@ToString
public class EmailDTO {
	
	@Setter
	@Getter
	private String mailFrom;
	
	@Setter
	@Getter
	private String mailTo;
	
	@Setter
	@Getter
	private String mailCc;
	
	@Setter
	@Getter
	private String mailBcc;
 
	@Setter
	@Getter
	private String mailSubject;
	
	@Setter
	@Getter
	private String mailContent;
	
	@Setter
	@Getter
	private String templateName;
	
	@Setter
	private String contentType;
	
	@Setter
	@Getter
	private boolean status;
	
	@Getter
	private String emailTitle;
 
	
}
