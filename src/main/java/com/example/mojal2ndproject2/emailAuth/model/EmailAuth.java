package com.example.mojal2ndproject2.emailAuth.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@Builder //엔티티 생성을 위해
@NoArgsConstructor
@AllArgsConstructor
public class EmailAuth {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long idx;
    String email;
    String uuid;

}
