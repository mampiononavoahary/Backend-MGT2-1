package com.mgt2.backendproject.service;

import com.mgt2.backendproject.model.entity.Document;
import com.mgt2.backendproject.repository.DocumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DocumentService {

    @Autowired
    private DocumentRepository documentRepository;

    @Value("${spring.servlet.multipart.location}")
    private String uploadDirectory;

    public List<Document> getAllDocuments() throws IOException {
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        Resource[] resources = resolver.getResources("file:" + uploadDirectory + "/*");

        List<Document> fileResponses = Arrays.stream(resources)
                .map(resource -> {
                    String filename = resource.getFilename();
                    String filePath;
                    try {
                        filePath = resource.getURI().getPath();
                        return new Document(filename, filePath);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return null;
                })
                .collect(Collectors.toList());
        return fileResponses;
    }

    @SuppressWarnings("null")
    public Optional<Document> getDocumentById(Integer id) {
        return documentRepository.findById(id);
    }

    public Document createDocument(MultipartFile document) {
        try {
            Path uploadPath = Paths.get(uploadDirectory);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            String fileName = document.getOriginalFilename();
            Path filePath = uploadPath.resolve(fileName);
            Files.copy(document.getInputStream(), filePath);
            
            Document doc = new Document();
            doc.setTitle(fileName);
            doc.setContent(filePath.toString());
            doc.setCreation_date(Timestamp.valueOf(LocalDateTime.now()));
            return documentRepository.save(doc);
        } catch (IOException e) {
            throw new RuntimeException("Erreur lors de l'enregistrement du fichier.", e);
        }
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
