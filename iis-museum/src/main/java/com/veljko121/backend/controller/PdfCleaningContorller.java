package com.veljko121.backend.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import com.itextpdf.text.DocumentException;
import com.veljko121.backend.service.IPdfService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/pdfCleaning")
@RequiredArgsConstructor
public class PdfCleaningContorller {
    
    private final IPdfService pdfCleaningService;

    @GetMapping("/generate-pdf/{userId}")
    public ResponseEntity<InputStreamResource> generatePdf(@PathVariable Integer userId) throws DocumentException, IOException {
        ByteArrayInputStream bis = pdfCleaningService.generateCleansedItemsPdf(userId);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=cleansed-items.pdf");
    
        return ResponseEntity
            .ok()
            .headers(headers)
            .contentType(MediaType.APPLICATION_PDF)
            .body(new InputStreamResource(bis));
    }

    @GetMapping("/save-pdf/{userId}")
    public ResponseEntity<String> savePdf(@PathVariable Integer userId) throws DocumentException, IOException {
        pdfCleaningService.saveCleansedItemsPdf(userId);
        return ResponseEntity.ok("PDF saved to Desktop with a unique name.");
    }

    @GetMapping("/generate-pdf-personal/{userId}")
    public ResponseEntity<InputStreamResource> generatePdfPersonal(@PathVariable Integer userId) throws DocumentException, IOException {
        ByteArrayInputStream bis = pdfCleaningService.generateCleansedItemsPdfForPersonal(userId);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=cleansed-items.pdf");
    
        return ResponseEntity
            .ok()
            .headers(headers)
            .contentType(MediaType.APPLICATION_PDF)
            .body(new InputStreamResource(bis));
    }

}
