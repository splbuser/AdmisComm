package com.splb.service.utils.filecreator.factory;

import com.splb.service.utils.filecreator.FileCreate;

public class FileFactory {
    public static FileCreate getFileCreate(FileType fileType) {
        return fileType.getFileCreate();
    }

    private FileFactory() {
    }
}
