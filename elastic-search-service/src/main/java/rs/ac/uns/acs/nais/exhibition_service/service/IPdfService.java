package rs.ac.uns.acs.nais.exhibition_service.service;

import com.itextpdf.text.DocumentException;

import java.io.ByteArrayInputStream;
import java.io.IOException;

public interface IPdfService {
    public ByteArrayInputStream generatePdfReport(int query1MinTicketsSold, int query2MinDailyAverage, String query1Theme, int query2MinPrice, String query2SearchText) throws DocumentException, IOException;
}
