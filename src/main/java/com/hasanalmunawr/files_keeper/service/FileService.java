package com.hasanalmunawr.files_keeper.service;

import com.hasanalmunawr.files_keeper.dto.response.FileResponse;
import com.hasanalmunawr.files_keeper.file.FileEntity;
import com.hasanalmunawr.files_keeper.user.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

public interface FileService {

    void uploadFile(MultipartFile file);

    FileResponse downloadFile(int fileId);

    Page<FileResponse> getAllFile(UserEntity user, int page, int size);

    void deleteFile(String fileName);
}
