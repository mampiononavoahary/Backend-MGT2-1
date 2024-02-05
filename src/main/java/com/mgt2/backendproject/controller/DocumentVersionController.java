package com.mgt2.backendproject.controller;

import com.mgt2.backendproject.model.entity.DocumentVersion;
import com.mgt2.backendproject.service.DocumentVersionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin // makes it easier for the frontend to receive urls from another server (backend server)
@RestController
public class DocumentVersionController {

    @Autowired
    private DocumentVersionService documentVersionService;

    @GetMapping("/DocumentVersions")
    public List<DocumentVersion> getAllDocumentVersion() {
        return documentVersionService.getAllDocumentVersion();
    }

    @GetMapping("/DocumentVersion/{id}")
    public Optional<DocumentVersion> getDocumentVersionById(@PathVariable Integer id){
        return documentVersionService.getDocumentVersionById(id);
    }

    @PostMapping("/Save/Historique")
    public DocumentVersion createDocumentVersion(@RequestBody DocumentVersion documentVersion) {
        return documentVersionService.createDocumentVersion(documentVersion);
    }

    @PutMapping("/DocumentVersion/update/{id}")
    public DocumentVersion updateDocumentVersion(@PathVariable Integer id, @RequestBody DocumentVersion updateDocumentVersion) {
        return documentVersionService.updateDocumentVersion(id, updateDocumentVersion);
    }

    @DeleteMapping("/DocumentVersion/Delete/{id}")
    public void deleteDocumentVersionById(@PathVariable Integer id) {
        documentVersionService.deleteDocumentVersionById(id);
    }
}
