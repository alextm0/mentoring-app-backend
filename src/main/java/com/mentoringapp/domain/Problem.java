package com.mentoringapp.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "problems")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Problem {
  @Id
  @Column(name = "id", nullable = false, updatable = false)
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @Column(name = "title", nullable = false, length = 100)
  private String title;

  @Column(name = "description")
  private String description;

  @Column(name = "difficulty", nullable = false, length = 20)
  @Enumerated(EnumType.STRING)
  private ProblemDifficulty difficulty;

  @Column(name = "external_url", nullable = false)
  private String externalUrl;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "mentor_id", nullable = false)
  private User mentor;

  @OneToMany(mappedBy = "problem", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Submission> submissions = new ArrayList<>();

  @Column(name = "in_bank", nullable = false)
  private Boolean inBank;

  @CreatedDate
  @Column(name = "created_at", nullable = false, updatable = false)
  private LocalDateTime createdAt;

  @LastModifiedDate
  @Column(name = "updated_at", nullable = false)
  private LocalDateTime updatedAt;

  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) return false;
    Problem problem = (Problem) o;
    return Objects.equals(id, problem.id);
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(id);
  }
}
