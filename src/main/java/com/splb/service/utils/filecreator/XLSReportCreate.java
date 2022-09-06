package com.splb.service.utils.filecreator;

import com.splb.model.entity.Enrollment;
import com.splb.service.utils.filecreator.exceptions.FileCreateException;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.OutputStream;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

/**
 * class for generating XLS-report of enrollment results
 */
public class XLSReportCreate implements FileCreate {

    private static final Logger log = LogManager.getLogger(XLSReportCreate.class);
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
        try (HSSFWorkbook document = new HSSFWorkbook();
             OutputStream out = response.getOutputStream()) {
            HSSFSheet sheet = document.createSheet(FileConstants.ENROLLMENT);
            createHeader(sheet);
            createBody(sheet);
            response.setHeader(FileConstants.CONTENT_DISPOSITION,
                    FileConstants.ATTACHMENT_FILENAME + FileConstants.FILENAME_XLS);
            document.write(out);
            log.info("{} written successfully", FileConstants.FILENAME_XLS);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void createHeader(HSSFSheet sheet) {
        HSSFRow hearRow = sheet.createRow(0);
        AtomicInteger i = new AtomicInteger();
        Stream.of(FileConstants.FACULTY, FileConstants.APPLICANT, FileConstants.STATUS)
                .forEach(title -> hearRow.createCell(i.getAndIncrement()).setCellValue(title));
    }

    private void createBody(HSSFSheet sheet) {
        AtomicInteger i = new AtomicInteger(1);
        list.forEach(el -> {
            HSSFRow row = sheet.createRow(i.getAndIncrement());
            String title1 = el.getFaculty().getName();
            String title2 = String.format("%s %s", el.getApplicant().getLastName(),
                    el.getApplicant().getFirstName());
            String title3 = el.getStatus().toString();
            row.createCell(0).setCellValue(title1);
            row.createCell(1).setCellValue(title2);
            row.createCell(2).setCellValue(title3);
        });
    }
}
