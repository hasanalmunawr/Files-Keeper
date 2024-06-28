package com.hasanalmunawr.files_keeper.code;

import com.hasanalmunawr.files_keeper.user.Auditable;
import com.hasanalmunawr.files_keeper.user.UserEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "codes")
public class CodeEntity extends Auditable {

    @Column(unique = true)
    private Integer tokenCode;
    private LocalDateTime expiresAt;
    private LocalDateTime validatedAt;
    private String type;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;
}
