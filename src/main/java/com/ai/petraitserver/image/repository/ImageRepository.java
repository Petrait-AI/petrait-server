package com.ai.petraitserver.image.repository;

import com.ai.petraitserver.image.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Long> {
}
