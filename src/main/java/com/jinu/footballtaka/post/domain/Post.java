package com.jinu.footballtaka.post.domain;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder(toBuilder=true)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name="post")
@Entity
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private int userId;
    
    private String title;
    private String contents;
    
    private String boardType;
    
    @Column(name = "imagePath")
    private String imagePath;

    @CreationTimestamp
    @Column(name = "createdAt")
    private LocalDateTime createdAt;
    
    @UpdateTimestamp
    @Column(name = "updatedAt")
    private LocalDateTime updatedAt;

}
