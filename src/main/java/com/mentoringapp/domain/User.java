package com.mentoringapp.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "users")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class User {
  @Id
  @Column(name = "id", nullable = false, updatable = false)
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @NotBlank(message = "Name cannot be blank")
  @Column(name = "name", nullable = false, length = 100)
  private String name;

  @NotBlank(message = "Email cannot be blank")
  @Email(message = "Email must be valid")
  @Column(name = "email", nullable = false, updatable = false, unique = true, length = 100)
  private String email;

  @NotBlank(message = "Role cannot be blank")
  @Column(name = "role", nullable = false, updatable = false, length = 20)
  private String role;

  @NotNull(message = "Total XP cannot be null")
  @Column(name = "total_xp", nullable = false)
  private Integer totalXp = 0;

  @NotNull(message = "Current level cannot be null")
  @Column(name = "current_level", nullable = false)
  private Integer currentLevel = 1;

  @OneToMany(mappedBy = "mentor", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Mentorship> mentorLinks = new ArrayList<>();

  @OneToMany(mappedBy = "mentee", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Mentorship> menteeLinks = new ArrayList<>();

  @OneToMany(mappedBy = "mentor", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Resource> resources = new ArrayList<>();

  @OneToMany(mappedBy = "mentor", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Problem> problems = new ArrayList<>();

  @OneToMany(mappedBy = "mentor", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Assignment> mentorAssignments = new ArrayList<>();

  @OneToMany(mappedBy = "mentee", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Assignment> menteeAssignments = new ArrayList<>();

  @OneToMany(mappedBy = "mentee", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
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
    User user = (User) o;
    return Objects.equals(id, user.id);
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(id);
  }
}
