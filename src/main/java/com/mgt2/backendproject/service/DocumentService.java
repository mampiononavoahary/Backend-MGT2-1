package com.mgt2.backendproject.service;

import com.mgt2.backendproject.model.entity.Document;
import com.mgt2.backendproject.model.entity.User;
import com.mgt2.backendproject.repository.DocumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DocumentService {

    @Autowired
    private DocumentRepository documentRepository;

    public List<Document> getAllDocuments() { return documentRepository.findAll(); }

    public Optional<Document> getDocumentById(Integer id) {
        return documentRepository.findById(id);
    }

    public Document createDocument(Document document) {
        return documentRepository.save(document);
    }

    public Document updateDocument(Integer id, Document updatedDocument) {
        Optional<Document> existingDocument = documentRepository.findById(id);

        if (existingDocument.isPresent()) {
            Document documentToUpdate = existingDocument.get();

            documentToUpdate.setTitle(updatedDocument.getTitle());
            documentToUpdate.setContent(updatedDocument.getContent());
            documentToUpdate.setCreation_date(updatedDocument.getCreation_date());

            return documentRepository.save(documentToUpdate);
        } else {
            return null;
        }
    }

    public void deleteDocumentById(Integer id) {
        documentRepository.deleteById(id);
    }
}
