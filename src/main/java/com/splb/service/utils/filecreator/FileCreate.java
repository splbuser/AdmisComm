package com.splb.service.utils.filecreator;

import com.splb.model.entity.Enrollment;
import com.splb.service.utils.filecreator.exceptions.FileCreateException;
import jakarta.servlet.http.HttpServletResponse;

import java.util.List;

/**
 * common interface for file-creating classes
 */
public interface FileCreate {

    void setList(List<Enrollment> list);

    void setResponse(HttpServletResponse response);

    void createFile() throws FileCreateException;

}
