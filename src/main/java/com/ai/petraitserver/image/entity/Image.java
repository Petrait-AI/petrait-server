package com.ai.petraitserver.image.entity;

import com.ai.petraitserver.common.entity.TimeStamp;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "images")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Image extends TimeStamp {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String imageUrl;
    private String status;

    @Builder
    public Image(String imageUrl, String status) {
        this.imageUrl = imageUrl;
        this.status = status;
    }
}
