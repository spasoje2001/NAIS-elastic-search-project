package com.veljko121.backend.controller;

import com.itextpdf.text.DocumentException;
import com.veljko121.backend.core.service.IJwtService;
import com.veljko121.backend.service.IPdfService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayInputStream;
import java.io.IOException;

@RestController
@RequestMapping("/api/pdfExhibitions")
@RequiredArgsConstructor
public class PdfExhibitionController {
    private final IPdfService pdfExhibitionsService;
    private final IJwtService jwtService;

    @GetMapping("/generate-organizer-report")
    public ResponseEntity<InputStreamResource> generateOrganizerReport() throws DocumentException, IOException {
        Integer userId = jwtService.getLoggedInUserId();
        ByteArrayInputStream bis = pdfExhibitionsService.generateExhibitionReport(userId);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=exhibition-organizer-report.pdf");

        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(bis));
    }

    @GetMapping("/generate-curator-report")
    public ResponseEntity<InputStreamResource> generateCuratorReport() throws DocumentException, IOException {
        Integer userId = jwtService.getLoggedInUserId();
        ByteArrayInputStream bis = pdfExhibitionsService.generateCuratorExhibitionReport(userId);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=exhibition-curator-report.pdf");

        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(bis));
    }

}
