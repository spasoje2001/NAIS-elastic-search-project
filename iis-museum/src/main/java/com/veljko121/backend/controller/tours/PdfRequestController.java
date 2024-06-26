package com.veljko121.backend.controller.tours;

import com.itextpdf.text.DocumentException;
import com.veljko121.backend.core.service.IJwtService;
import com.veljko121.backend.service.IPdfService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayInputStream;
import java.io.IOException;

@RestController
@RequestMapping("/api/pdfRequest")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ORGANIZER')")
public class PdfRequestController {

    private final IPdfService pdfService;
    private final IJwtService jwtService;

    @GetMapping("/generate-pdf")
    public ResponseEntity<InputStreamResource> generatePdf() throws DocumentException, IOException {
        Integer userId = jwtService.getLoggedInUserId();
        ByteArrayInputStream bis = pdfService.generateRequestsPdf(userId);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=cleansed-items.pdf");

        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(bis));
    }

    @GetMapping("/generate-pdf-personal")
    public ResponseEntity<InputStreamResource> generatePdfPersonal() throws DocumentException, IOException {
        Integer userId = jwtService.getLoggedInUserId();
        ByteArrayInputStream bis = pdfService.generateHandledRequestsPdf(userId);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=cleansed-items.pdf");

        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(bis));
    }

    @GetMapping("/save-pdf")
    public ResponseEntity<String> savePdf() throws DocumentException, IOException {
        Integer userId = jwtService.getLoggedInUserId();
        pdfService.saveRequestsPdf(userId);
        return ResponseEntity.ok("PDF saved to Desktop with a unique name.");
    }

}
