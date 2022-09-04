package com.splb.service.utils.filecreator;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.splb.model.entity.Enrollment;
import com.splb.service.utils.filecreator.exceptions.FileCreateException;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.List;
import java.util.stream.Stream;

public class PDFReportCreate implements FileCreate {

    private static final Logger log = LogManager.getLogger(PDFReportCreate.class);
    public static final String FACULTY = "Faculty";
    public static final String APPLICANT = "Applicant";
    public static final String STATUS = "Enrollment status";
    private static final BaseFont baseFont = loadBaseFont("fonts/JetBrainsMono-Regular.ttf");
    private static final Font font = new Font(baseFont, 13, Font.NORMAL, BaseColor.WHITE);
    private List<Enrollment> list;
    private HttpServletResponse response;

    public void setResponse(HttpServletResponse response) {
        this.response = response;
    }

    public void setList(List<Enrollment> list) {
        this.list = list;
    }

    @Override
    public void createFile() throws FileCreateException {
        Document document = new Document(PageSize.A4, 10.0f, 10.0f, 50.0f, 10.0f);
        try {
            PdfWriter.getInstance(document, response.getOutputStream());
            document.open();
            PdfPTable table = new PdfPTable(3);
            addTableHeader(table);
            addRows(table);
            document.add(table);
            document.close();
        } catch (DocumentException | IOException e) {
            log.error(e.getMessage());
            throw new FileCreateException(e.getMessage());
        }
    }

    private void addTableHeader(PdfPTable table) {
        Stream.of(FACULTY, APPLICANT, STATUS)
                .forEach(columnTitle -> {
                    PdfPCell header = new PdfPCell();
                    header.setBackgroundColor(BaseColor.BLUE);
                    header.setHorizontalAlignment(Element.ALIGN_CENTER);
                    header.setBorderWidth(1);
                    Phrase phrase = new Phrase(columnTitle, font);
                    header.setPhrase(phrase);
                    table.addCell(header);
                });
    }

    private void addRows(PdfPTable table) {

        list.forEach(el -> {
            String title1 = el.getFaculty().getName();
            String title2 = String.format("%s %s", el.getApplicant().getLastName(),
                    el.getApplicant().getFirstName());
            String title3 = el.getStatus().toString();
            Paragraph paragraph1 = new Paragraph(title1, new Font(baseFont, 12));
            Paragraph paragraph2 = new Paragraph(title2, new Font(baseFont, 12));
            Paragraph paragraph3 = new Paragraph(title3, new Font(baseFont, 12));
            table.addCell(paragraph1);
            table.addCell(paragraph2);
            table.addCell(paragraph3);
        });
    }

    private static BaseFont loadBaseFont(String fontName) {
        BaseFont baseFont= null;
        try {
            baseFont = BaseFont.createFont(fontName, BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        } catch (DocumentException | IOException e) {
            e.printStackTrace();
        }
        return baseFont;
    }
}