package com.example.demo.util;

public interface EmailSender {
	void sendEmail(String toEmail,String body);
	  void emailSend(String toEmail, String subject, String body);
}
