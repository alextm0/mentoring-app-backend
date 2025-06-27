package com.mentoringapp.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "mentor_mentee_link",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"mentor_id", "mentee_id"}),
                @UniqueConstraint(columnNames = {"mentee_id"})
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MentorMenteeLink {
  @Id
  @Column(name = "id", nullable = false, updatable = false)
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "mentor_id", nullable = false)
  private User mentor;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "mentee_id", nullable = false, unique = true)
  private User mentee;

  @Column(name = "is_active", nullable = false)
  private Boolean isActive;

  @CreatedDate
  @Column(name = "created_at", nullable = false, updatable = false)
  private LocalDateTime createdAt;
}
