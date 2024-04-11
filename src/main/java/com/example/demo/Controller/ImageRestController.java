package com.example.demo.Controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.Model.Image;
import com.example.demo.Service.ImageService;

@RestController
@RequestMapping("/api/image")
@CrossOrigin("*")
public class ImageRestController {
@Autowired
ImageService imageService;
@PostMapping("/upload")
public Image uploadImage(@RequestParam("image")MultipartFile file)throws IOException{
			return imageService.uploadImage(file);
}
@GetMapping("/get/info/{id}")
public Image getImageDetails(@PathVariable("id") Long id)throws IOException{
	return imageService.getImageDetails(id);
}
@GetMapping("/load/{id}")
public ResponseEntity<byte[]>getImage(@PathVariable("id") Long id) throws IOException{
	return imageService.getImage(id);
}
@DeleteMapping("/delete/{id}")
public void deleteImage(@PathVariable("id") Long id) {
	imageService.deleteImage(id);
}
@PutMapping("/update")
public Image UpdateImage(@RequestParam("image")MultipartFile file) throws IOException{
	return imageService.uploadImage(file);
}
}
