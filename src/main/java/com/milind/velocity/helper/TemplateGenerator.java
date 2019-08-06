package com.milind.velocity.helper;

import java.io.StringWriter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;

import com.milind.velocity.model.EmailDTO;

public class TemplateGenerator {

	private static final Logger log = LogManager.getLogger(TemplateGenerator.class);

	/**
	 * Empty public constructor
	 */
	public TemplateGenerator() {
		throw new IllegalStateException("Helper class");
		}

	public static String generateTemplate(String templateId, EmailDTO emailDTO) {
		StringWriter writer = new StringWriter();

		try {

			VelocityEngine velocityEngine = new VelocityEngine();
			velocityEngine.setProperty("runtime.log.logsystem.class",
					"org.apache.velocity.runtime.log.SimpleLog4JLogSystem");
			velocityEngine.setProperty("runtime.log.logsystem.log4j.category", "velocity");
			velocityEngine.setProperty("runtime.log.logsystem.log4j.logger", "velocity");
			velocityEngine.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
			velocityEngine.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());

			velocityEngine.init();

			VelocityContext context = new VelocityContext();

			// load the template based on templateId

			Template template;

			template = getMailTemplateBasedOnTemplateId(templateId, velocityEngine);

			writer = populateTemplateWithRelevantData(context, templateId, template, emailDTO);

		} catch (Exception e) {
			log.error("Exception in TemplateGenerator ::: {}", e.getCause());
		}

		return writer.toString();
	}

	private static StringWriter populateTemplateWithRelevantData(VelocityContext context, String templateId,
			Template template, EmailDTO emailDTO) {
		
		StringWriter writer = new StringWriter();

		switch (templateId) {
		case "Employee":
			context.put("title", emailDTO.getEmailTitle());
			context.put("body", emailDTO.getMailContent());
			template.merge(context, writer);
			break;
		case "Manager":
			context.put("title", emailDTO.getEmailTitle());
			context.put("body", emailDTO.getMailContent());
			context.put("approvedStatus", emailDTO.isStatus());
			template.merge(context, writer);
			break;
		default:
			context.put("title", emailDTO.getEmailTitle());
			context.put("body", emailDTO.getMailContent());
			template.merge(context, writer);
			break;

		}

		return writer;
	}

	private static Template getMailTemplateBasedOnTemplateId(String templateId, VelocityEngine velocityEngine) {
		Template template = null;

		switch (templateId) {
		case "Employee":
			template = velocityEngine.getTemplate("/templates/email-employee.vm");
			break;
		case "Manager":
			template = velocityEngine.getTemplate("/templates/email-manager.vm");
			break;
		default:
			template = velocityEngine.getTemplate("/templates/email-employee.vm");
			break;
		}

		return template;
	}

}
