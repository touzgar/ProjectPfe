package com.example.demo.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
@Service
@AllArgsConstructor
public class EmailService implements EmailSender {

	@Autowired
	private final JavaMailSender mailSender;

	@Override
	public void sendEmail(String to, String email) {
		try {
			MimeMessage mimeMessage=mailSender.createMimeMessage();
			MimeMessageHelper helper=new MimeMessageHelper(mimeMessage,"utf-8");
			helper.setText(email,true);
			helper.setTo(to);
			helper.setSubject("Confirm your email");
			helper.setFrom("ghaithslama115@gmail.com");
			mailSender.send(mimeMessage);
		}catch (MessagingException e) {
			throw new IllegalStateException("failed to send email");
			
		}
		
	}
}
