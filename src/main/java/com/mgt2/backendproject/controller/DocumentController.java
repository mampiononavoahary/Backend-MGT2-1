package com.mgt2.backendproject.controller;

import com.mgt2.backendproject.model.entity.Document;
import com.mgt2.backendproject.service.DocumentService;

import jakarta.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
public class DocumentController {

    @Autowired
    private DocumentService documentService;

    @GetMapping("/ping")
    public String pingPong() {
        return "pong";
    }

    @GetMapping("/Documents")
    public List<Document> getAllDocument() throws IOException {
        return documentService.getAllDocuments();
    }

    @GetMapping("/Document/{id}")
    public Optional<Document> getDocumentById(@PathVariable Integer id){
        return documentService.getDocumentById(id);
    }

    @PostMapping("/Document")
    public ResponseEntity<Document> createDocument(
        @RequestParam("file") MultipartFile document,
        @RequestParam("userId") Integer userId
        ) {
        Document createdDocument = documentService.createDocument(document, userId);
        return ResponseEntity.ok().body(createdDocument);
    }

    @PutMapping("/Document/update/{id}")
    public ResponseEntity<Document> updateDocument(
        @PathVariable Integer id,
        @PathParam("file") MultipartFile file,
        @PathParam("title") String title 
        ) {
        try {
            Document documentUpdate = documentService.updateDocument(id, title, file);
            return ResponseEntity.ok(documentUpdate);
        } catch (IOException e) {
            throw new RuntimeException("Erreur lors de l'update du fichier.", e);
        }
    }

    @DeleteMapping("/Document/Delete/{id}")
    public void deleteDocumentById(@PathVariable Integer id) {
        documentService.deleteDocumentById(id);
    }
}
