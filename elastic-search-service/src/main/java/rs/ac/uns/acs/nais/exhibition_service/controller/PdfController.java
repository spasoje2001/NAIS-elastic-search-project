package rs.ac.uns.acs.nais.exhibition_service.controller;

import com.itextpdf.text.DocumentException;

import rs.ac.uns.acs.nais.exhibition_service.service.impl.PdfService;

import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;

@RestController
@RequestMapping("/pdfs.json")
public class PdfController {

    private final PdfService pdfService;

    public PdfController(PdfService pdfService) {
        this.pdfService = pdfService;
    }

    // 10 - 30
    // @GetMapping("/in-range-pdf")
    // public ResponseEntity<InputStreamResource> generateToursInPriceRangePdf(@RequestParam("requestedById") String requestedById,
    //                                                                         @RequestParam("min") Integer minPrice,
    //                                                                         @RequestParam("max") Integer maxPrice) throws DocumentException, IOException {
    //     ByteArrayInputStream bis = pdfService.generateToursInPriceRangePdf(Integer.valueOf(requestedById), minPrice, maxPrice);
    //     HttpHeaders headers = new HttpHeaders();
    //     headers.add("Content-Disposition", "inline; filename=cleansed-items.pdf");

    //     return ResponseEntity
    //             .ok()
    //             .headers(headers)
    //             .contentType(MediaType.APPLICATION_PDF)
    //             .body(new InputStreamResource(bis));
    // }

    // // ID 7
    // @GetMapping("/category-pdf/{id}")
    // public ResponseEntity<InputStreamResource> generateTourByMostFrequentCategoryPdf(@PathVariable String id) throws DocumentException, IOException {
    //     ByteArrayInputStream bis = pdfService.generateToursByMostFrequentCategoryPdf(Integer.valueOf(id));
    //     HttpHeaders headers = new HttpHeaders();
    //     headers.add("Content-Disposition", "inline; filename=cleansed-items.pdf");

    //     return ResponseEntity
    //             .ok()
    //             .headers(headers)
    //             .contentType(MediaType.APPLICATION_PDF)
    //             .body(new InputStreamResource(bis));
    // }

    // // ID 7
    // @GetMapping("/others-category-pdf/{id}")
    // public ResponseEntity<InputStreamResource> generateToursByOthersPurchasesAndCategoryPdf(@PathVariable String id) throws DocumentException, IOException {
    //     ByteArrayInputStream bis = pdfService.generateToursByOthersPurchasesAndCategoryPdf(Integer.valueOf(id));
    //     HttpHeaders headers = new HttpHeaders();
    //     headers.add("Content-Disposition", "inline; filename=cleansed-items.pdf");

    //     return ResponseEntity
    //             .ok()
    //             .headers(headers)
    //             .contentType(MediaType.APPLICATION_PDF)
    //             .body(new InputStreamResource(bis));
    // }

    // @GetMapping("/generate-pdf")
    // public ResponseEntity<InputStreamResource> generateToursByOthersPurchasesAndCategoryPdf(@RequestParam("requestedById") String requestedById,
    //                                                                                         @RequestParam("min") Integer minPrice,
    //                                                                                         @RequestParam("max") Integer maxPrice) throws DocumentException, IOException {
    //     ByteArrayInputStream bis = pdfService.generatePdf(Integer.valueOf(requestedById), minPrice, maxPrice);
    //     HttpHeaders headers = new HttpHeaders();
    //     headers.add("Content-Disposition", "inline; filename=cleansed-items.pdf");

    //     return ResponseEntity
    //             .ok()
    //             .headers(headers)
    //             .contentType(MediaType.APPLICATION_PDF)
    //             .body(new InputStreamResource(bis));
    // }

}
