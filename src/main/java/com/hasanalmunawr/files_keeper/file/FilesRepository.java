package com.hasanalmunawr.files_keeper.file;

import com.hasanalmunawr.files_keeper.user.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FilesRepository extends JpaRepository<FileEntity, Integer> {

    Page<FileEntity> findAllByUser(UserEntity user, Pageable pageable);
}
