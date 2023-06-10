package io.desofme.msfileprocessing.repository;

import io.desofme.msfileprocessing.entity.FileData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FileDataRepository extends JpaRepository<FileData, Long> {

    Optional<FileData> findByUniqueFileId(String fileId);

}
