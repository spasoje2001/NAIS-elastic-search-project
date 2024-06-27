package rs.ac.uns.acs.nais.exhibition_service.service.impl;

import com.itextpdf.text.*;

import com.itextpdf.text.pdf.draw.LineSeparator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.ac.uns.acs.nais.exhibition_service.dto.OrganizerAverageRatingDTO;
import rs.ac.uns.acs.nais.exhibition_service.model.Exhibition;
import rs.ac.uns.acs.nais.exhibition_service.model.Item;
import rs.ac.uns.acs.nais.exhibition_service.model.MuseumEvent;
import rs.ac.uns.acs.nais.exhibition_service.model.Review;
import rs.ac.uns.acs.nais.exhibition_service.service.IPdfService;

import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.*;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class PdfService implements IPdfService {
    
    @Autowired
    private ExhibitionService exhibitionService;

    @Autowired
    private EventService eventService;

    public ByteArrayInputStream generatePdfReport(int query1MinTicketsSold, int query2MinDailyAverage, String query1Theme, int query2MinPrice, String query2SearchText) throws DocumentException, IOException {
        Document document = new Document(PageSize.A4);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PdfWriter.getInstance(document, out);
        document.open();

        // Section 1: Simple Section - Exhibitions
        addFilteredExhibitionsSection(document);

        // Section 2: Simple Section - Events
        addFilteredEventsSection(document);

        // Section 3: Complex Section - Open Exhibitions with High Attendance
        addHighAttendanceOpenExhibitionsSection(document, query1MinTicketsSold, query2MinDailyAverage, query1Theme);

        // Section 4: Complex Section - Average Rating by Organizer
        addAverageRatingByOrganizerSection(document, query2MinPrice, query2SearchText);

        document.close();
        return new ByteArrayInputStream(out.toByteArray());
    }

    private void addFilteredExhibitionsSection(Document document) throws DocumentException {
        List<Exhibition> exhibitions = convertToExhibitionList(exhibitionService.findAll());
        exhibitions = exhibitions.subList(0, Math.min(exhibitions.size(), 3));

        Font sectionFont = new Font(Font.FontFamily.TIMES_ROMAN, 16, Font.BOLD);
        Paragraph sectionTitle = new Paragraph("Exhibitions", sectionFont);
        sectionTitle.setAlignment(Element.ALIGN_CENTER);
        document.add(sectionTitle);

        document.add(Chunk.NEWLINE);

        Font defaultFont = new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.NORMAL);
        Font boldFont = new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.BOLD);
        Font itemReviewFont = new Font(Font.FontFamily.TIMES_ROMAN, 8, Font.NORMAL);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy.");

        for (Exhibition exhibition : exhibitions) {
            // Main exhibition details
            Paragraph exhibitionDetails = new Paragraph();
            exhibitionDetails.add(new Chunk("Exhibition Name: ", boldFont));
            exhibitionDetails.add(new Chunk(exhibition.getName(), defaultFont));
            document.add(exhibitionDetails);

            exhibitionDetails = new Paragraph();
            exhibitionDetails.add(new Chunk("Theme: ", boldFont));
            exhibitionDetails.add(new Chunk(exhibition.getTheme().toString(), defaultFont));
            document.add(exhibitionDetails);

            exhibitionDetails = new Paragraph();
            exhibitionDetails.add(new Chunk("Start Date: ", boldFont));
            exhibitionDetails.add(new Chunk(exhibition.getStartDate().format(formatter), defaultFont));
            document.add(exhibitionDetails);

            exhibitionDetails = new Paragraph();
            exhibitionDetails.add(new Chunk("End Date: ", boldFont));
            exhibitionDetails.add(new Chunk(exhibition.getEndDate().format(formatter), defaultFont));
            document.add(exhibitionDetails);

            exhibitionDetails = new Paragraph();
            exhibitionDetails.add(new Chunk("Price: ", boldFont));
            exhibitionDetails.add(new Chunk("€"+exhibition.getPrice().toString(), defaultFont));
            document.add(exhibitionDetails);

            exhibitionDetails = new Paragraph();
            exhibitionDetails.add(new Chunk("Total number of tickets sold: ", boldFont));
            exhibitionDetails.add(new Chunk(exhibition.getTicketsSold().toString(), defaultFont));
            document.add(exhibitionDetails);

            exhibitionDetails = new Paragraph();
            exhibitionDetails.add(new Chunk("Total revenue: ", boldFont));
            Integer revenue = exhibition.getPrice() * exhibition.getTicketsSold();
            exhibitionDetails.add(new Chunk("€"+revenue, defaultFont));
            document.add(exhibitionDetails);


            exhibitionDetails = new Paragraph();
            exhibitionDetails.add(new Chunk("Organizer: ", boldFont));
            exhibitionDetails.add(new Chunk(exhibition.getOrganizer().getFirstName() + " " + exhibition.getOrganizer().getLastName(), defaultFont));
            document.add(exhibitionDetails);

            exhibitionDetails = new Paragraph();
            exhibitionDetails.add(new Chunk("Curator: ", boldFont));
            exhibitionDetails.add(new Chunk(exhibition.getCurator().getFirstName() + " " + exhibition.getCurator().getLastName(), defaultFont));
            document.add(exhibitionDetails);

            if (exhibition.getRoom() != null) {
                exhibitionDetails = new Paragraph();
                exhibitionDetails.add(new Chunk("Room: ", boldFont));
                exhibitionDetails.add(new Chunk(exhibition.getRoom().getNumber() + ", " +  exhibition.getRoom().getFloor() +". floor ", defaultFont));
                document.add(exhibitionDetails);
            }

            // Items
            if (exhibition.getItems().isEmpty()) {
                exhibitionDetails = new Paragraph();
                exhibitionDetails.add(new Chunk("Items: ", boldFont));
                exhibitionDetails.add(new Chunk("This exhibition has no items on record.", itemReviewFont));
                document.add(exhibitionDetails);
            } else {
                exhibitionDetails = new Paragraph();
                exhibitionDetails.add(new Chunk("Items: ", boldFont));
                document.add(exhibitionDetails);

                List<Item> items = new ArrayList<>(exhibition.getItems());
                for (Item item : items) {
                    Paragraph itemDetails = new Paragraph();
                    itemDetails.add(new Chunk("Name: ", itemReviewFont));
                    itemDetails.add(new Chunk(item.getName(), itemReviewFont));
                    document.add(itemDetails);

                    itemDetails = new Paragraph();
                    itemDetails.add(new Chunk("Description: ", itemReviewFont));
                    itemDetails.add(new Chunk(item.getDescription(), itemReviewFont));
                    document.add(itemDetails);

                    itemDetails = new Paragraph();
                    itemDetails.add(new Chunk("Authors Name: ", itemReviewFont));
                    itemDetails.add(new Chunk(item.getAuthorsName(), itemReviewFont));
                    document.add(itemDetails);

                    itemDetails = new Paragraph();
                    itemDetails.add(new Chunk("Year of Creation: ", itemReviewFont));
                    itemDetails.add(new Chunk(item.getYearOfCreation(), itemReviewFont));
                    document.add(itemDetails);

                    itemDetails = new Paragraph();
                    itemDetails.add(new Chunk("Period: ", itemReviewFont));
                    itemDetails.add(new Chunk(item.getPeriod(), itemReviewFont));
                    document.add(itemDetails);

                    itemDetails = new Paragraph();
                    itemDetails.add(new Chunk("Category: ", itemReviewFont));
                    itemDetails.add(new Chunk(item.getCategory().toString(), itemReviewFont));
                    document.add(itemDetails);

                    document.add(new Paragraph(" ", itemReviewFont));
                }
            }

            // Reviews
            if (exhibition.getReviews().isEmpty()) {
                exhibitionDetails = new Paragraph();
                exhibitionDetails.add(new Chunk("Reviews: ", boldFont));
                exhibitionDetails.add(new Chunk("This exhibition has no reviews on record.", itemReviewFont));
                document.add(exhibitionDetails);
            } else {
                exhibitionDetails = new Paragraph();
                exhibitionDetails.add(new Chunk("Reviews: ", boldFont));
                document.add(exhibitionDetails);

                List<Review> reviews = new ArrayList<>(exhibition.getReviews());
                for (Review review : reviews) {
                    Paragraph reviewDetails = new Paragraph();
                    reviewDetails.add(new Chunk("Reviewer: ", itemReviewFont));
                    reviewDetails.add(new Chunk(review.getReviewer(), itemReviewFont));
                    document.add(reviewDetails);

                    reviewDetails = new Paragraph();
                    reviewDetails.add(new Chunk("Rating: ", itemReviewFont));
                    reviewDetails.add(new Chunk(review.getRating().toString(), itemReviewFont));
                    document.add(reviewDetails);

                    reviewDetails = new Paragraph();
                    reviewDetails.add(new Chunk("Text: ", itemReviewFont));
                    reviewDetails.add(new Chunk(review.getText(), itemReviewFont));
                    document.add(reviewDetails);

                    document.add(new Paragraph(" ", itemReviewFont)); // Add subtle space between reviews
                }
            }

            document.add(Chunk.NEWLINE);
            document.add(new LineSeparator());
            document.add(Chunk.NEWLINE);
        }

        document.add(Chunk.NEWLINE);
    }

    private void addFilteredEventsSection(Document document) throws DocumentException {
        List<MuseumEvent> events = convertToEventList(eventService.findAll());
        events = events.subList(0, Math.min(events.size(), 3));

        Font sectionFont = new Font(Font.FontFamily.TIMES_ROMAN, 16, Font.BOLD);
        Paragraph sectionTitle = new Paragraph("Events", sectionFont);
        sectionTitle.setAlignment(Element.ALIGN_CENTER);
        document.add(sectionTitle);

        document.add(Chunk.NEWLINE);

        Font defaultFont = new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.NORMAL);
        Font boldFont = new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.BOLD);
        Font itemReviewFont = new Font(Font.FontFamily.TIMES_ROMAN, 8, Font.NORMAL);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy.");

        for (MuseumEvent event : events) {
            // Main event details
            Paragraph eventDetails = new Paragraph();
            eventDetails.add(new Chunk("Event Name: ", boldFont));
            eventDetails.add(new Chunk(event.getName(), defaultFont));
            document.add(eventDetails);

            eventDetails = new Paragraph();
            eventDetails.add(new Chunk("Description: ", boldFont));
            eventDetails.add(new Chunk(event.getDescription(), defaultFont));
            document.add(eventDetails);

            eventDetails = new Paragraph();
            eventDetails.add(new Chunk("Date: ", boldFont));
            eventDetails.add(new Chunk(event.getStartDateTime().format(formatter), defaultFont));
            document.add(eventDetails);

            eventDetails = new Paragraph();
            eventDetails.add(new Chunk("Duration (minutes): ", boldFont));
            eventDetails.add(new Chunk(event.getDurationMinutes().toString(), defaultFont));
            document.add(eventDetails);

            eventDetails = new Paragraph();
            eventDetails.add(new Chunk("Price: ", boldFont));
            eventDetails.add(new Chunk("€"+event.getPrice().toString(), defaultFont));
            document.add(eventDetails);

            eventDetails = new Paragraph();
            eventDetails.add(new Chunk("Organizer: ", boldFont));
            eventDetails.add(new Chunk(event.getOrganizer().getFirstName() + " " + event.getOrganizer().getLastName(), defaultFont));
            document.add(eventDetails);

            if (event.getRoom() != null) {
                eventDetails = new Paragraph();
                eventDetails.add(new Chunk("Room: ", boldFont));
                eventDetails.add(new Chunk(event.getRoom().getNumber() + ", " +  event.getRoom().getFloor() +". floor ", defaultFont));
                document.add(eventDetails);
            }

            // Reviews
            if (event.getReviews().isEmpty()) {
                eventDetails = new Paragraph();
                eventDetails.add(new Chunk("Reviews: ", boldFont));
                eventDetails.add(new Chunk("This event has no reviews on record.", itemReviewFont));
                document.add(eventDetails);
            } else {
                eventDetails = new Paragraph();
                eventDetails.add(new Chunk("Reviews: ", boldFont));
                document.add(eventDetails);

                List<Review> reviews = new ArrayList<>(event.getReviews());
                for (Review review : reviews) {
                    Paragraph reviewDetails = new Paragraph();
                    reviewDetails.add(new Chunk("Reviewer: ", itemReviewFont));
                    reviewDetails.add(new Chunk(review.getReviewer(), itemReviewFont));
                    document.add(reviewDetails);

                    reviewDetails = new Paragraph();
                    reviewDetails.add(new Chunk("Rating: ", itemReviewFont));
                    reviewDetails.add(new Chunk(review.getRating().toString(), itemReviewFont));
                    document.add(reviewDetails);

                    reviewDetails = new Paragraph();
                    reviewDetails.add(new Chunk("Text: ", itemReviewFont));
                    reviewDetails.add(new Chunk(review.getText(), itemReviewFont));
                    document.add(reviewDetails);

                    document.add(new Paragraph(" ", itemReviewFont));
                }
            }

            // Separator for the next event
            document.add(Chunk.NEWLINE);
            document.add(new LineSeparator());
            document.add(Chunk.NEWLINE);
        }

        document.add(Chunk.NEWLINE);
    }

    private void addHighAttendanceOpenExhibitionsSection(Document document, int minTicketsSold, int minDailyAverage, String theme) throws DocumentException {
        List<Exhibition> exhibitions = exhibitionService.findOpenExhibitionsWithHighAttendance(minTicketsSold, minDailyAverage, theme);
        exhibitions = exhibitions.subList(0, Math.min(exhibitions.size(), 3));

        Font sectionFont = new Font(Font.FontFamily.TIMES_ROMAN, 16, Font.BOLD);
        Paragraph sectionTitle = new Paragraph("High Attendance Open Exhibitions", sectionFont);
        sectionTitle.setAlignment(Element.ALIGN_CENTER);
        document.add(sectionTitle);

        document.add(Chunk.NEWLINE);

        Font introFont = new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.ITALIC);
        Paragraph intro = new Paragraph();
        intro.add(new Chunk("The following exhibitions are OPEN exhibitions with more than " + minTicketsSold + " tickets sold, a daily average of more than " + minDailyAverage + " tickets sold, and themed around \"" + theme + "\". Exhibitions are sorted by End Date.", introFont));
        document.add(intro);

        document.add(Chunk.NEWLINE);

        Font defaultFont = new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.NORMAL);
        Font boldFont = new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.BOLD);
        Font itemReviewFont = new Font(Font.FontFamily.TIMES_ROMAN, 8, Font.NORMAL);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy.");

        for (Exhibition exhibition : exhibitions) {
            // Main exhibition details
            Paragraph exhibitionDetails = new Paragraph();
            exhibitionDetails.add(new Chunk("Exhibition Name: ", boldFont));
            exhibitionDetails.add(new Chunk(exhibition.getName(), defaultFont));
            document.add(exhibitionDetails);

            exhibitionDetails = new Paragraph();
            exhibitionDetails.add(new Chunk("Theme: ", boldFont));
            exhibitionDetails.add(new Chunk(exhibition.getTheme().toString(), defaultFont));
            document.add(exhibitionDetails);

            exhibitionDetails = new Paragraph();
            exhibitionDetails.add(new Chunk("Start Date: ", boldFont));
            exhibitionDetails.add(new Chunk(exhibition.getStartDate().format(formatter), defaultFont));
            document.add(exhibitionDetails);

            exhibitionDetails = new Paragraph();
            exhibitionDetails.add(new Chunk("End Date: ", boldFont));
            exhibitionDetails.add(new Chunk(exhibition.getEndDate().format(formatter), defaultFont));
            document.add(exhibitionDetails);

            exhibitionDetails = new Paragraph();
            exhibitionDetails.add(new Chunk("Price: ", boldFont));
            exhibitionDetails.add(new Chunk("€"+exhibition.getPrice().toString(), defaultFont));
            document.add(exhibitionDetails);

            exhibitionDetails = new Paragraph();
            exhibitionDetails.add(new Chunk("Total number of tickets sold: ", boldFont));
            exhibitionDetails.add(new Chunk(exhibition.getTicketsSold().toString(), defaultFont));
            document.add(exhibitionDetails);

            exhibitionDetails = new Paragraph();
            exhibitionDetails.add(new Chunk("Total revenue: ", boldFont));
            Integer revenue = exhibition.getPrice() * exhibition.getTicketsSold();
            exhibitionDetails.add(new Chunk("€"+revenue, defaultFont));
            document.add(exhibitionDetails);

            exhibitionDetails = new Paragraph();
            exhibitionDetails.add(new Chunk("Organizer: ", boldFont));
            exhibitionDetails.add(new Chunk(exhibition.getOrganizer().getFirstName() + " " + exhibition.getOrganizer().getLastName(), defaultFont));
            document.add(exhibitionDetails);

            exhibitionDetails = new Paragraph();
            exhibitionDetails.add(new Chunk("Curator: ", boldFont));
            exhibitionDetails.add(new Chunk(exhibition.getCurator().getFirstName() + " " + exhibition.getCurator().getLastName(), defaultFont));
            document.add(exhibitionDetails);

            if (exhibition.getRoom() != null) {
                exhibitionDetails = new Paragraph();
                exhibitionDetails.add(new Chunk("Room: ", boldFont));
                exhibitionDetails.add(new Chunk("floor: " + exhibition.getRoom().getFloor() + " room: " + exhibition.getRoom().getNumber(), defaultFont));
                document.add(exhibitionDetails);
            }

            // Display items
            if (exhibition.getItems().isEmpty()) {
                exhibitionDetails = new Paragraph();
                exhibitionDetails.add(new Chunk("Items: ", boldFont));
                exhibitionDetails.add(new Chunk("This exhibition has no items on record.", itemReviewFont));
                document.add(exhibitionDetails);
            } else {
                exhibitionDetails = new Paragraph();
                exhibitionDetails.add(new Chunk("Items: ", boldFont));
                document.add(exhibitionDetails);

                List<Item> items = new ArrayList<>(exhibition.getItems());
                for (Item item : items) {
                    Paragraph itemDetails = new Paragraph();
                    itemDetails.add(new Chunk("Name: ", itemReviewFont));
                    itemDetails.add(new Chunk(item.getName(), itemReviewFont));
                    document.add(itemDetails);

                    itemDetails = new Paragraph();
                    itemDetails.add(new Chunk("Description: ", itemReviewFont));
                    itemDetails.add(new Chunk(item.getDescription(), itemReviewFont));
                    document.add(itemDetails);

                    itemDetails = new Paragraph();
                    itemDetails.add(new Chunk("Authors Name: ", itemReviewFont));
                    itemDetails.add(new Chunk(item.getAuthorsName(), itemReviewFont));
                    document.add(itemDetails);

                    itemDetails = new Paragraph();
                    itemDetails.add(new Chunk("Year of Creation: ", itemReviewFont));
                    itemDetails.add(new Chunk(item.getYearOfCreation(), itemReviewFont));
                    document.add(itemDetails);

                    itemDetails = new Paragraph();
                    itemDetails.add(new Chunk("Period: ", itemReviewFont));
                    itemDetails.add(new Chunk(item.getPeriod(), itemReviewFont));
                    document.add(itemDetails);

                    itemDetails = new Paragraph();
                    itemDetails.add(new Chunk("Category: ", itemReviewFont));
                    itemDetails.add(new Chunk(item.getCategory().toString(), itemReviewFont));
                    document.add(itemDetails);

                    document.add(new Paragraph(" ", itemReviewFont)); // Add subtle space between items
                }
            }

            // Display reviews
            if (exhibition.getReviews().isEmpty()) {
                exhibitionDetails = new Paragraph();
                exhibitionDetails.add(new Chunk("Reviews: ", boldFont));
                exhibitionDetails.add(new Chunk("This exhibition has no reviews on record.", itemReviewFont));
                document.add(exhibitionDetails);
            } else {
                exhibitionDetails = new Paragraph();
                exhibitionDetails.add(new Chunk("Reviews: ", boldFont));
                document.add(exhibitionDetails);

                List<Review> reviews = new ArrayList<>(exhibition.getReviews());
                for (Review review : reviews) {
                    Paragraph reviewDetails = new Paragraph();
                    reviewDetails.add(new Chunk("Reviewer: ", itemReviewFont));
                    reviewDetails.add(new Chunk(review.getReviewer(), itemReviewFont));
                    document.add(reviewDetails);

                    reviewDetails = new Paragraph();
                    reviewDetails.add(new Chunk("Rating: ", itemReviewFont));
                    reviewDetails.add(new Chunk(review.getRating().toString(), itemReviewFont));
                    document.add(reviewDetails);

                    reviewDetails = new Paragraph();
                    reviewDetails.add(new Chunk("Text: ", itemReviewFont));
                    reviewDetails.add(new Chunk(review.getText(), itemReviewFont));
                    document.add(reviewDetails);

                    document.add(new Paragraph(" ", itemReviewFont)); // Add subtle space between reviews
                }
            }

            document.add(Chunk.NEWLINE);
            document.add(new LineSeparator());
            document.add(Chunk.NEWLINE);
        }

        document.add(Chunk.NEWLINE);
    }

    private void addAverageRatingByOrganizerSection(Document document, int minPrice, String searchText) throws DocumentException {
        List<OrganizerAverageRatingDTO> organizerRatings = eventService.findAverageRatingByOrganizer(minPrice, searchText);

        Font sectionFont = new Font(Font.FontFamily.TIMES_ROMAN, 16, Font.BOLD);
        Paragraph sectionTitle = new Paragraph("Average Ratings by Organizer", sectionFont);
        sectionTitle.setAlignment(Element.ALIGN_CENTER);
        document.add(sectionTitle);

        document.add(Chunk.NEWLINE);

        Font introFont = new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.ITALIC);
        Paragraph intro = new Paragraph();
        intro.add(new Chunk("The following displays average ratings by organizers for events with a minimum ticket price of €" + minPrice + " and including reviews containing word(s): \"" + searchText + "\".", introFont));
        document.add(intro);

        document.add(Chunk.NEWLINE);

        Font defaultFont = new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.NORMAL);
        Font boldFont = new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.BOLD);

        for (OrganizerAverageRatingDTO ratingDTO : organizerRatings) {
            // Organizer details
            Paragraph organizerDetails = new Paragraph();
            organizerDetails.add(new Chunk("Organizer: ", boldFont));
            organizerDetails.add(new Chunk(ratingDTO.getOrganizerFirstName() + " " + ratingDTO.getOrganizerLastName(), defaultFont));
            document.add(organizerDetails);

            // Rating details
            Paragraph ratingDetails = new Paragraph();
            ratingDetails.add(new Chunk("Total Reviews: ", boldFont));
            ratingDetails.add(new Chunk(String.valueOf(ratingDTO.getTotalReviews()), defaultFont));
            document.add(ratingDetails);

            ratingDetails = new Paragraph();
            ratingDetails.add(new Chunk("Average Rating: ", boldFont));
            ratingDetails.add(new Chunk(String.valueOf(ratingDTO.getAverageRating()), defaultFont));
            document.add(ratingDetails);

            document.add(Chunk.NEWLINE); // Add space between organizer ratings
        }

        document.add(Chunk.NEWLINE);
    }

    private List<Exhibition> convertToExhibitionList(Iterable<Exhibition> exhibitions) {
        List<Exhibition> exhibitionsList = new ArrayList<>();
        exhibitions.forEach(exhibitionsList::add);
        return exhibitionsList;
    }

    private List<MuseumEvent> convertToEventList(Iterable<MuseumEvent> events) {
        List<MuseumEvent> eventsList = new ArrayList<>();
        events.forEach(eventsList::add);
        return eventsList;
    }

}
