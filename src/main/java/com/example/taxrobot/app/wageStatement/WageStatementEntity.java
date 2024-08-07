package com.example.taxrobot.app.wageStatement;

import jakarta.persistence.*;
import lombok.*;

@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "wage_statement")
public class WageStatementEntity {
    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private Long personalDataid;

    @Column(nullable = false)
    private String von;
    private String bis;
    private String arbeitGebeter;
    private String nettolohn;
}
