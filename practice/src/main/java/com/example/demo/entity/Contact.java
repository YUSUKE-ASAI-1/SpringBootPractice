package com.example.demo.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;

import lombok.Data;

@Entity
@Data
@Table(name = "contacts")
public class Contact {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Long id;

	@Column(name = "last_name", nullable = false)
	private String lastName;

	@Column(name = "first_name", nullable = false)
	private String firstName;

	@Column(name = "email", nullable = false)
	private String email;

	@Column(name = "phone", nullable = false)
	private String phone;

	@Column(name = "zip_code", nullable = false)
	private String zipCode;

	@Column(name = "address", nullable = false)
	private String address;

	@Column(name = "building_name", nullable = false)
	private String buildingName;

	@Column(name = "contact_type", nullable = false)
	private String contactType;

	@Column(name = "body", nullable = false)
	private String body;

	@Column(name = "created_at", nullable = false)
	private LocalDateTime createdAt; // 作成日時

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @PrePersist
    public void prePersist() {
        LocalDateTime now = LocalDateTime.now();
        this.updatedAt = now;  // 新規作成時に現在時刻をセット
        this.createdAt = now;  // もし createdAt も同様に必要なら
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();  // 更新時に現在時刻をセット
    }
}
