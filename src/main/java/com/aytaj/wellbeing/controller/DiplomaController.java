package com.aytaj.wellbeing.controller;

import com.aytaj.wellbeing.service.DiplomaFileService;


import com.aytaj.wellbeing.service.ResponseService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/diplomas")
public class DiplomaController {
    private final DiplomaFileService diplomaFileService;
    private final ResponseService responseService;

    @GetMapping("/dowload/{fileName:.+}")
    public ResponseEntity<Resource> downloadDiploma(@PathVariable String fileName){
        Resource resource = diplomaFileService.loadDiplomaFile(fileName);

        return responseService.prepareDownloadResponse(resource);
    }

}
