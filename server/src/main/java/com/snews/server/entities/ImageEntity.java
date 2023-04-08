package com.snews.server.entities;


import jakarta.persistence.*;

import java.util.UUID;

@Entity()
@Table(name = "images")
public class ImageEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(columnDefinition = "MEDIUMBLOB")
    private byte[] data;

    public byte[] getData() {
        return data;
    }

    public ImageEntity setData(byte[] data) {
        this.data = data;
        return this;
    }

    public UUID getId() {
        return id;
    }
}
