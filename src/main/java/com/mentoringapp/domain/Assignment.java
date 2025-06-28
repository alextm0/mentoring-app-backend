package com.mentoringapp.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "assignments")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Assignment {
  @Id
  @Column(name = "id", nullable = false, updatable = false)
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @Column(name = "title", nullable = false, length = 100)
  private String title;

  @Column(name = "description")
  private String description;

  @Column(name = "status", nullable = false, length = 20)
  @Enumerated(EnumType.STRING)
  private AssignmentStatus status;

  @Column(name = "xp_reward", nullable = false)
  private Integer xpReward = 0;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "mentor_id", nullable = false)
  private User mentor;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "mentee_id", nullable = false)
  private User mentee;

  @OneToMany(mappedBy = "assignment", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Submission> submissions = new ArrayList<>();

  @CreatedDate
  @Column(name = "created_at", nullable = false, updatable = false)
  private LocalDateTime createdAt;

  @LastModifiedDate
  @Column(name = "updated_at", nullable = false)
  private LocalDateTime updatedAt;

  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) return false;
    Assignment that = (Assignment) o;
    return Objects.equals(id, that.id);
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(id);
  }
}
