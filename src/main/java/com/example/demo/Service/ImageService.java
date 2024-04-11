package com.example.demo.Service;

import java.io.IOException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.Model.Image;

public interface ImageService {
Image uploadImage(MultipartFile file) throws IOException;
Image getImageDetails(Long id) throws IOException;
ResponseEntity<byte[]>getImage(Long id) throws IOException;
void deleteImage(Long id);
}
