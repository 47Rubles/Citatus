package org.example.citatus;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "citations")
public class Citation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    private String author;
    private String text;

    public Citation(String author, String text) {
        this.author = author;
        this.text = text;
    }

    public Citation() {

    }
}
// ПОдЖОджо класс