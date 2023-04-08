package com.snews.server.repositories;

import com.snews.server.entities.ImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ImageRepository extends JpaRepository<ImageEntity, UUID> {

    ImageEntity getImageEntityById(UUID id);
}
