package com.hasanalmunawr.files_keeper.code;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CodeRepository extends JpaRepository<CodeEntity, Integer> {

    Optional<CodeEntity> findByTokenCode(Integer tokenCode);

}
