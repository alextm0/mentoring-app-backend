package com.mentoringapp.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "resources")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Resource {
  @Id
  @Column(name = "id", nullable = false, updatable = false)
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @Column(name = "title", nullable = false, length = 100)
  private String title;

  @Column(name = "description")
  private String description;

  @Column(name = "external_url", nullable = false)
  private String externalUrl;

  @Column(name = "in_bank", nullable = false)
  private Boolean inBank = false;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "mentor_id", nullable = false)
  private User mentor;

  @CreatedDate
  @Column(name = "created_at", nullable = false, updatable = false)
  private LocalDateTime createdAt;

  @LastModifiedDate
  @Column(name = "updated_at", nullable = false)
  private LocalDateTime updatedAt;

  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) return false;
    Resource resource = (Resource) o;
    return Objects.equals(id, resource.id);
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(id);
  }
}
