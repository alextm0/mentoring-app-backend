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
@Table(name = "submissions")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Submission {
  @Id
  @Column(name = "id", nullable = false, updatable = false)
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "mentee_id", nullable = false)
  private User mentee;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "assignment_id", nullable = false)
  private Assignment assignment;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "problem_id", nullable = false)
  private Problem problem;

  @Column(name = "content", columnDefinition = "TEXT", nullable = false)
  private String content;

  @Column(name = "feedback", columnDefinition = "TEXT")
  private String feedback;

  @CreatedDate
  @Column(name = "created_at", nullable = false, updatable = false)
  private LocalDateTime createdAt;

  @LastModifiedDate
  @Column(name = "updated_at", nullable = false)
  private LocalDateTime updatedAt;

  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) return false;
    Submission that = (Submission) o;
    return Objects.equals(id, that.id);
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(id);
  }
}
