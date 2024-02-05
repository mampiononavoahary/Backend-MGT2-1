package com.mgt2.backendproject.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.sql.Timestamp;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "document_version")
public class DocumentVersion implements Serializable {
    @Id
    @Column(name = "id_document_version", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id_document_version;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "last_update", nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Timestamp last_update;

    @ManyToOne
    @JoinColumn(name = "id_user", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "id_document", nullable = false)
    private Document document;
}
