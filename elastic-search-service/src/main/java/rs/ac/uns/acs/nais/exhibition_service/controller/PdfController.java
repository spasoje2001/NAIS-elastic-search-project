package rs.ac.uns.acs.nais.exhibition_service.controller;

import com.itextpdf.text.DocumentException;

import org.apache.pdfbox.io.IOUtils;
import org.springframework.http.HttpStatus;
import rs.ac.uns.acs.nais.exhibition_service.service.IExhibitionService;
import rs.ac.uns.acs.nais.exhibition_service.service.IPdfService;
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

    private final IPdfService pdfService;


    public PdfController(PdfService pdfService) {
        this.pdfService = pdfService;
    }
    @GetMapping("/generateMuseumReport")
    public ResponseEntity<byte[]> generateMuseumReport(
            @RequestParam int query1MinTicketsSold,
            @RequestParam int query2MinDailyAverage,
            @RequestParam String query1Theme,
            @RequestParam int query2MinPrice,
            @RequestParam String query2SearchText
    ) {
        try {
            ByteArrayInputStream bis = pdfService.generatePdfReport(query1MinTicketsSold, query2MinDailyAverage, query1Theme, query2MinPrice, query2SearchText);
            byte[] contents = IOUtils.toByteArray(bis);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("inline", "museumReport.pdf");

            return new ResponseEntity<>(contents, headers, HttpStatus.OK);
        } catch (IOException | DocumentException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
