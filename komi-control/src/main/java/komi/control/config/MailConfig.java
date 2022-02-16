package komi.control.config;

import java.util.Optional;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import komi.control.model.Parameter;
import komi.control.service.ParameterService;


@Configuration 
public class MailConfig {
	
	private String host;
	private String port;
	private String auth;
	private String startTls;
	private String username;
	private String password;
	private String ehlo;
	
	@Autowired
    ParameterService parameterService;
	
    @Bean
    public JavaMailSender javaMailService() {
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
        
        /*
        Optional<Parameter> hostP = parameterService.findByParamname("EMAIL.SMTP.HOST");
		this.host = new String(hostP.get().getParamvalua());
		
		Optional<Parameter> portP = parameterService.findByParamname("EMAIL.SMTP.PORT");
		this.port = new String(portP.get().getParamvalua());
		
		Optional<Parameter> usernameP = parameterService.findByParamname("EMAIL.SMTP.USERNAME");
		this.username = new String(usernameP.get().getParamvalua());
		
		Optional<Parameter> passwordP = parameterService.findByParamname("EMAIL.SMTP.PASSWORD");
		this.password = new String(passwordP.get().getParamvalua());*/
		
        host = "smtp.gmail.com";
		port =  "587";
		username =  "pamtestemail01@gmail.com";
		password =  "united1213";
        
        javaMailSender.setHost(host);
        javaMailSender.setPort(Integer.valueOf(port));
        javaMailSender.setUsername(username);
        javaMailSender.setPassword(password);

        
        javaMailSender.setJavaMailProperties(getMailProperties());

        return javaMailSender;
    }

    private Properties getMailProperties() {
    	
    	//Parameter  p = parameterService.getByKode("EMAIL_SMTP_HOST");
        
		auth =  "TRUE";
		startTls =  "TRUE";
		ehlo =  "TRUE";
    	
        Properties properties= new Properties();
        
        properties.setProperty("mail.transport.protocol", "smtp");
        properties.setProperty("mail.smtp.auth", auth);
        properties.setProperty("mail.smtp.starttls.enable", startTls);
		properties.setProperty("mail.smtp.ehlo", ehlo);

		
        return properties;
    }
}