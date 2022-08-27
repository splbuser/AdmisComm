package com.splb.model.dao;

import com.splb.model.dao.exception.RegisterDAOException;
import com.splb.model.entity.Applicant;

public interface RegisterDAO {

    boolean insert(Applicant applicant) throws RegisterDAOException;
}
