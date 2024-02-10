package com.mgt2.backendproject.service;

import com.mgt2.backendproject.model.entity.Document;
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

    @SuppressWarnings("null")
    public Optional<Document> getDocumentById(Integer id) {
        return documentRepository.findById(id);
    }

    @SuppressWarnings("null")
    public Document createDocument(Document document) {
        return documentRepository.save(document);
    }

    @SuppressWarnings("null")
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

    @SuppressWarnings("null")
    public void deleteDocumentById(Integer id) {
        documentRepository.deleteById(id);
    }
}
