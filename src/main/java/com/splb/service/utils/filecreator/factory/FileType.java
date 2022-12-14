package com.splb.service.utils.filecreator.factory;

import com.splb.service.utils.filecreator.DOCReportCreate;
import com.splb.service.utils.filecreator.FileCreate;
import com.splb.service.utils.filecreator.PDFReportCreate;
import com.splb.service.utils.filecreator.XLSReportCreate;

public enum FileType {

    CREATE_DOC(new DOCReportCreate()),
    CREATE_PDF(new PDFReportCreate()),
    CREATE_XLS(new XLSReportCreate());

    private final FileCreate fileCreate;

    FileType(FileCreate fileCreate) {
        this.fileCreate = fileCreate;
    }

    public FileCreate getFileCreate() {
        return fileCreate;
    }
}
