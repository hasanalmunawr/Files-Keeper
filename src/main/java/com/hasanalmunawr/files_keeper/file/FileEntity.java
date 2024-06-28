package com.hasanalmunawr.files_keeper.file;


import com.hasanalmunawr.files_keeper.user.Auditable;
import com.hasanalmunawr.files_keeper.user.UserEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "files")
public class FileEntity extends Auditable implements Serializable {

    private String name;

    @Lob
    private byte[] file;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private UserEntity user;

}
