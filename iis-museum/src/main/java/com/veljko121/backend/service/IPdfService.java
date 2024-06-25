package com.veljko121.backend.service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import com.itextpdf.text.DocumentException;



public interface IPdfService {
    ByteArrayInputStream generateCleansedItemsPdf(Integer requestedBy) throws DocumentException, IOException;
    void saveCleansedItemsPdf(Integer requestedBy) throws DocumentException, IOException;
    void saveRequestsPdf(Integer organizerId) throws DocumentException, IOException;
    ByteArrayInputStream generateCleansedItemsPdfForPersonal(Integer requestedBy) throws DocumentException, IOException;
    ByteArrayInputStream generateRequestsPdf(Integer organizerId) throws DocumentException, IOException;
    ByteArrayInputStream generateHandledRequestsPdf(Integer requestedBy) throws DocumentException, IOException;
    ByteArrayInputStream generateExhibitionReport(Integer organizerId) throws DocumentException, IOException;
    ByteArrayInputStream generateCuratorExhibitionReport(Integer curatorId) throws DocumentException, IOException;

}
