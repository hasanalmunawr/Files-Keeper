package com.hasanalmunawr.files_keeper.service.impl;

import com.hasanalmunawr.files_keeper.dto.response.FileResponse;
import com.hasanalmunawr.files_keeper.file.FileEntity;
import com.hasanalmunawr.files_keeper.file.FilesRepository;
import com.hasanalmunawr.files_keeper.service.FileService;
import com.hasanalmunawr.files_keeper.user.UserEntity;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FIleServiceImpl implements FileService {

    private final FilesRepository filesRepository;

    @Override
    public void uploadFile(MultipartFile file) {
        try {
            FileEntity fileEntity = new FileEntity();
            fileEntity.setName(file.getOriginalFilename());
            fileEntity.setFile(file.getBytes());

            filesRepository.save(fileEntity);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public FileResponse downloadFile(int fileId) {
        Optional<FileEntity> fileEntity = filesRepository.findById(fileId);
        if (fileEntity.isPresent()) {
            return FileResponse.builder()
                    .file(fileEntity.get().getFile())
                    .name(fileEntity.get().getName())
                    .build();
        } else {
            throw new RuntimeException("File not found with id " + fileId);
        }
    }

    @Override
    public Page<FileResponse> getAllFile(UserEntity currentUser, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<FileEntity> allFilesByUser = filesRepository.findAllByUser(currentUser, pageable);

        return allFilesByUser.map(file -> {
            FileResponse fileResponse = new FileResponse();
            fileResponse.setName(file.getName());
            fileResponse.setFile(file.getFile());
            return fileResponse;
        });
    }

    @Override
    public void deleteFile(String fileName) {

    }
}
