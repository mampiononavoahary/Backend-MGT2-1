package com.mgt2.backendproject.service;

import com.mgt2.backendproject.exceptions.DocumentNotFoundException;
import com.mgt2.backendproject.model.entity.Document;
import com.mgt2.backendproject.model.entity.DocumentVersion;
import com.mgt2.backendproject.model.entity.Role;
import com.mgt2.backendproject.model.entity.User;
import com.mgt2.backendproject.repository.DocumentRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class DocumentService {

    @Autowired
    private DocumentRepository documentRepository;

    @Autowired
    private DocumentVersionService documentVersionService;

    @Value("${spring.servlet.multipart.location}")
    private String uploadDirectory;

    public List<Document> getAllDocuments() throws IOException {
        return documentRepository.findAll();
    }

    @SuppressWarnings("null")
    public Optional<Document> getDocumentById(Integer id) {
        return documentRepository.findById(id);
    }

    @SuppressWarnings("null")
    public Document createDocument(MultipartFile document, Integer userId) {
        try {
            Path uploadPath = Paths.get(uploadDirectory);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            String originalFilename = document.getOriginalFilename();
            String fileName = originalFilename;
            Path filePath = uploadPath.resolve(fileName);

            int counter = 2;
            while (Files.exists(filePath)) {
                String fileNameWithoutExtension = originalFilename;
                String fileExtension = "";
                int lastDotIndex = originalFilename.lastIndexOf('.');
                if (lastDotIndex > 0) {
                    fileNameWithoutExtension = originalFilename.substring(0, lastDotIndex);
                    fileExtension = originalFilename.substring(lastDotIndex);
                }
                String newFileName = fileNameWithoutExtension + "-" + counter + fileExtension;
                filePath = uploadPath.resolve(newFileName);
                fileName = newFileName;
                counter++;
            }
            Files.copy(document.getInputStream(), filePath);
            
            User user = new User();
            user.setId_user(userId);
            user.setRole(Role.USER);
            
            Document doc = new Document();
            doc.setTitle(fileName);
            doc.setContent(filePath.toString());
            doc.setCreation_date(Timestamp.valueOf(LocalDateTime.now()));
            doc.setUser(user);
            return documentRepository.save(doc);
        } catch (IOException e) {
            throw new RuntimeException("Erreur lors de l'enregistrement du fichier.", e);
        }
    }

    @SuppressWarnings("null")
    public Document updateDocument(Integer id, String title, MultipartFile file) throws IOException {
        Optional<Document> existingDocument = documentRepository.findById(id);

        Path uploadPath = Paths.get(uploadDirectory);
        
        try {
            if (existingDocument.isPresent()) {
                Document documentToUpdate = existingDocument.get();

                if (!Files.exists(uploadPath)) {
                    Files.createDirectories(uploadPath);
                }

                String originalFilename = file.getOriginalFilename();
                String fileName = originalFilename;
                Path filePath = uploadPath.resolve(fileName);

                int counter = 2;
                while (Files.exists(filePath)) {
                    String fileNameWithoutExtension = originalFilename;
                    String fileExtension = "";
                    int lastDotIndex = originalFilename.lastIndexOf('.');
                    if (lastDotIndex > 0) {
                        fileNameWithoutExtension = originalFilename.substring(0, lastDotIndex);
                        fileExtension = originalFilename.substring(lastDotIndex);
                    }
                    String newFileName = fileNameWithoutExtension + "-" + counter + fileExtension;
                    filePath = uploadPath.resolve(newFileName);
                    fileName = newFileName;
                    counter++;
                }
        
                Files.copy(file.getInputStream(), filePath);
                            
                Timestamp dateTime = Timestamp.valueOf(LocalDateTime.now());
                
                documentToUpdate.setTitle(title);
                documentToUpdate.setContent(filePath.toString());
                documentToUpdate.setCreation_date(dateTime);
                User user = documentToUpdate.getUser();
                user.setRole(Role.USER);
                documentToUpdate.setUser(user);

                DocumentVersion documentVersion = new DocumentVersion();
                documentVersion.setDocument(documentToUpdate);
                documentVersion.setLast_update(dateTime);
                documentVersion.setUser(documentToUpdate.getUser());

                documentVersionService.createDocumentVersion(documentVersion);
                
                return documentRepository.save(documentToUpdate);
            } else {
                throw new DocumentNotFoundException("Document not found with id: " + id);
            }
        } catch (IOException e) {
            throw new RuntimeException("Erreur lors de l'update du fichier.", e);
        }
    }

    @SuppressWarnings("null")
    public void deleteDocumentById(Integer id) {
        documentRepository.deleteById(id);
    }
}
