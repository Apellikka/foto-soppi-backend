package com.apellikka.fotosoppi.foto_soppi.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.apellikka.fotosoppi.foto_soppi.entity.Image;
import com.apellikka.fotosoppi.foto_soppi.repository.ImageRepository;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;




@RestController
@RequestMapping("/images")
public class ImageController {

    private final ImageRepository imageRepository;

    @Value("${spring.profiles.active}")
    private String activeProfile;

    @Value("${IMAGE_BASE_URL_DEV}")
    private String baseDirectoryDev;

    @Value("${IMAGE_BASE_URL_PROD}")
    private String baseDirectoryProd;

    public ImageController(ImageRepository imageRepository) 
    {
        this.imageRepository = imageRepository;
    }

    @GetMapping("/{id}")
    public HttpEntity<Image> getImageById(@PathVariable Integer id) {
        
        Image image = imageRepository.findById(id).orElseThrow(() ->
            new ResponseStatusException(HttpStatus.NOT_FOUND, "Image ID not found from database!"));

        image.add(linkTo(methodOn(ImageController.class).getImageById(id)).withSelfRel());
        image.add(linkTo(methodOn(ImageController.class).serveImage(id)).withRel("image"));
        return new ResponseEntity<Image>(image, HttpStatus.OK);
    }

    @GetMapping("/{id}/raw")
    public ResponseEntity<Resource> serveImage(@PathVariable Integer id)  {
        Image image = imageRepository.findById(id).orElseThrow(() -> 
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Image ID not found from database!"));
    
        if(activeProfile.equals("dev")) {
            Path imagePath = Paths.get(baseDirectoryDev + image.getPath());
            Resource resource = getResourceFromPath(imagePath);
            String contentType = getContentType(imagePath);

            return ResponseEntity
                .ok()
                .contentType(MediaType.parseMediaType(contentType))
                .body(resource);
        }
        
        // For production environment
        else {
            String imagePath = baseDirectoryProd + image.getPath();
            Resource resource = getResourceFromPath(imagePath);
            
            return ResponseEntity
                .ok()
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .body(resource);
        }
    }


    @GetMapping("/all")
    public HttpEntity<PagedModel<EntityModel<Image>>> findAllImages(
        @PageableDefault(page=0, size=20) Pageable pageable, 
        PagedResourcesAssembler<Image> assembler)
    {
        Page<Image> images = imageRepository.findAll(pageable);
        images.forEach(image -> {
            image.add(linkTo(methodOn(ImageController.class).getImageById(image.getId())).withSelfRel());
            image.add(linkTo(methodOn(ImageController.class).serveImage(image.getId())).withRel("image"));
        }
        );
        PagedModel<EntityModel<Image>> pagedModel = assembler.toModel(images);
        
        return ResponseEntity.ok(pagedModel);
    }

    
    
    // Utils

    // For production
    private Resource getResourceFromPath(String imagePath) {
        try {
            Resource resource = new UrlResource(imagePath);
            if (!resource.exists()) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Image file not found!");
            }
            return resource;
        } catch (MalformedURLException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Malformed URL for image resource", e);
        }
    }

    // For development
    private Resource getResourceFromPath(Path imagePath) {
        try {
            Resource resource = new UrlResource(imagePath.toUri());
            if (!resource.exists()) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Image file not found!");
            }
            return resource;
        } catch (MalformedURLException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Malformed URL for image resource", e);
        }
    }
    // For development
    private String getContentType(Path imagePath) {
        try {
            String contentType = Files.probeContentType(imagePath);
            return contentType != null ? contentType : "application/octet-stream";
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to determine content type", e);
        }
    }
}