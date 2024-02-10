package com.mgt2.backendproject.service;

import com.mgt2.backendproject.model.entity.DocumentVersion;
import com.mgt2.backendproject.repository.DocumentVersionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DocumentVersionService {

    @Autowired
    private DocumentVersionRepository documentVersionRepository;

    public List<DocumentVersion> getAllDocumentVersion() { return documentVersionRepository.findAll(); }

    @SuppressWarnings("null")
    public Optional<DocumentVersion> getDocumentVersionById(Integer id) {
        return documentVersionRepository.findById(id);
    }

    @SuppressWarnings("null")
    public DocumentVersion createDocumentVersion(DocumentVersion documentVersion) {
        return documentVersionRepository.save(documentVersion);
    }

    @SuppressWarnings("null")
    public DocumentVersion updateDocumentVersion(Integer id, DocumentVersion updatedDocumentVersion) {
        Optional<DocumentVersion> existingDocumentVersion = documentVersionRepository.findById(id);

        if (existingDocumentVersion.isPresent()) {
            DocumentVersion documentVersionToUpdate = existingDocumentVersion.get();

            documentVersionToUpdate.setLast_update(updatedDocumentVersion.getLast_update());

            return documentVersionRepository.save(documentVersionToUpdate);
        } else {
            return null;
        }
    }

    @SuppressWarnings("null")
    public void deleteDocumentVersionById(Integer id) {
        documentVersionRepository.deleteById(id);
    }
}
