package com.example.demo.Service.execption;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorDetails {
	private LocalDateTime timestamp;
	private String message;
	private String path;
	private String errorCode;
}
