package com.splb.service.utils.filecreator;

import java.io.OutputStream;
import java.util.List;

import com.splb.model.entity.Enrollment;
import com.splb.service.utils.filecreator.exceptions.FileCreateException;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.xwpf.usermodel.*;

/**
 * class for generating DOC-report of enrollment results
 */
public class DOCReportCreate implements FileCreate {

    private static final Logger log = LogManager.getLogger(DOCReportCreate.class);
    public static final String REPORT = "Automatically generated report";
    private List<Enrollment> list;
    private HttpServletResponse response = null;

    @Override
    public void setList(List<Enrollment> list) {
        this.list = list;
    }

    @Override
    public void setResponse(HttpServletResponse response) {
        this.response = response;
    }

    @Override
    public void createFile() throws FileCreateException {
        try (XWPFDocument document = new XWPFDocument();
             OutputStream out = response.getOutputStream();
        ) {
            XWPFParagraph paragraph = document.createParagraph();
            XWPFRun run = paragraph.createRun();
            run.setFontFamily("Arial");
            run.setBold(true);
            run.setFontSize(16);
            run.setText(REPORT);
            XWPFTable table = document.createTable();
            createHeader(table);
            createBody(table);
            response.setHeader(FileConstants.CONTENT_DISPOSITION,
                    FileConstants.ATTACHMENT_FILENAME + FileConstants.FILENAME_DOC);
            document.write(out);
            log.info("{} written successfully", FileConstants.FILENAME_DOC);
        } catch (Exception e) {
            throw new FileCreateException(e.getMessage());
        }
    }

    private void createHeader(XWPFTable table) {
        XWPFTableRow tableRowOne = table.getRow(0);
        tableRowOne.getCell(0).setText(FileConstants.FACULTY);
        tableRowOne.addNewTableCell().setText(FileConstants.APPLICANT);
        tableRowOne.addNewTableCell().setText(FileConstants.STATUS);
    }

    private void createBody(XWPFTable table) {
        list.forEach(el -> {
            XWPFTableRow tableRowTwo = table.createRow();
            String title1 = el.getFaculty().getName();
            String title2 = String.format("%s %s", el.getApplicant().getLastName(),
                    el.getApplicant().getFirstName());
            String title3 = el.getStatus().toString();
            tableRowTwo.getCell(0).setText(title1);
            tableRowTwo.getCell(1).setText(title2);
            tableRowTwo.getCell(2).setText(title3);
        });
    }
}