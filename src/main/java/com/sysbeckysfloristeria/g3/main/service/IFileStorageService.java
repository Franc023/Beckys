package com.sysbeckysfloristeria.g3.main.service;

import org.springframework.web.multipart.MultipartFile;

public interface IFileStorageService {
    String storeImage(MultipartFile file);
}
