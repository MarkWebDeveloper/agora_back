package de.stella.agora_web.replys.model;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;

import de.stella.agora_web.posts.model.Post;
import de.stella.agora_web.user.model.User;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;



    @Setter
    @Getter
    @Entity(name = "Reply")
   
   
    @RequiredArgsConstructor
    @ToString
    @Table(name = "replys")
    public class Reply {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;
         @JsonProperty("title")
        private String title;
        @JsonProperty("message")
        private String message;
        private LocalDateTime creationDate = LocalDateTime.now();

        
        public Reply(Long id, String title, String message, LocalDateTime creationDate, User author, Post posts) {
            this.id = id;
            this.title = title;
            this.message = message;
            this.creationDate = creationDate;
            this.author = author;
            this.posts = posts;
        }

        @ManyToOne
        @JoinColumn(name = "user_id")
        private User author;

        @ManyToOne
        @JoinColumn(name = "post_id")
        public Post  posts;
    }

