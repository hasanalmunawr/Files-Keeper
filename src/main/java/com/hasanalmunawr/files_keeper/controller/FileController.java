package com.hasanalmunawr.files_keeper.controller;

import com.hasanalmunawr.files_keeper.dto.response.FileResponse;
import com.hasanalmunawr.files_keeper.file.FileEntity;
import com.hasanalmunawr.files_keeper.service.FileService;
import com.hasanalmunawr.files_keeper.user.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@RestController
@RequestMapping("/api/v1/file")
@RequiredArgsConstructor
public class FileController {

private final FileService fileService;


    @PostMapping(path = "/upload")
    public void upload(
            MultipartFile file,
            @AuthenticationPrincipal UserEntity user
    ) {
        fileService.uploadFile(file);
    }

    @GetMapping("/download/{fileId}")
    public ResponseEntity<Resource> downloadFile(
            @PathVariable int fileId,
            @AuthenticationPrincipal UserEntity currentUser
    ) {
        var file = fileService.downloadFile(fileId);

        // Create the resource from the file bytes
        ByteArrayResource resource = new ByteArrayResource(file.getFile());

        // Set the headers for file download
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=\"" + file.getName() + "\"")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .contentLength(file.getFile().length)
                .body(resource);

    }

    @GetMapping()
    public ResponseEntity<org.springframework.data.domain.Page<FileResponse>> getAllFiles(
            @RequestParam(name = "page", defaultValue = "0", required = false) int page,
            @RequestParam(name = "size", defaultValue = "10", required = false) int size,
            @AuthenticationPrincipal UserEntity currentUser
    ) {
        Page<FileResponse> files = fileService.getAllFile(currentUser, page, size);

        return ResponseEntity.ok(files);
    }

//    private final FileService fileService;
//
//    @GetMapping(path = "/download")
//    public ResponseEntity<?> getEbookFile(
//            @RequestParam("isbn") String isbn,
//            @AuthenticationPrincipal UserEntity user
//    ) {
//        String ebookFile = String.valueOf(fileService.getEbookFile(isbn));
//
//        return ResponseEntity.ok()
//                .body(ebookFile);
//    }

//    @GetMapping(path = "/cover")
//    public ResponseEntity<?> getCoverFile(
//            @RequestParam("isbn") String isbn,
//            @AuthenticationPrincipal UserEntity user
//    ) {
//         String ebookFile = String.valueOf(fileService.getCoverFile(isbn));
//
//        return ResponseEntity.ok()
//                .body(ebookFile);
//    }


}

