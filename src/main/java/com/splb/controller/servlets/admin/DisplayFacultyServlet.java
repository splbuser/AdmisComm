package com.splb.controller.servlets.admin;

import com.splb.controller.pages.Pages;
import com.splb.model.dao.constant.Fields;
import com.splb.model.entity.Faculty;
import com.splb.service.FacultyService;
import com.splb.service.exceptions.FacultyServiceException;
import com.splb.service.sorting.SortFacultyImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.nonNull;

public class DisplayFacultyServlet extends HttpServlet {

    private static final Logger log = LogManager.getLogger(DisplayFacultyServlet.class);

    @Override
    public void init() throws ServletException {
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        String type = request.getParameter(Fields.TYPE);
        String sortBy = request.getParameter(Fields.SORT_BY);
        String search = request.getParameter(Fields.SEARCH);
        FacultyService srv = new FacultyService();
        List<Faculty> faculty = new ArrayList<>();
        Faculty getFaculty = null;

                /* получаем сортированый список согласно требуемыым параметрам
        сперва проверяем реквест, замет сессию. все пришедшее с реквеста кидаем
        в сессию чтобы при следующем запросе оставаля порядок сортировки
         */


        if (nonNull(search) && !search.isEmpty()) {
            try {
                if (srv.checkByName(search)) {
                    getFaculty = srv.getByName(search);
                    faculty.add(getFaculty);
                    log.info("Case 1:");

                } else {
                    faculty = srv.getList();
                    log.info("Case 2:");
                }
                request.setAttribute("faculty", faculty);
                request.getRequestDispatcher(Pages.MANAGE_FACULTY)
                        .forward(request, response);
            } catch (FacultyServiceException e) {
                log.error("could not find faculty: {}", e.getMessage());
            }
        } else {
            try {
                if (type == null || sortBy == null) {
                    type = (String) session.getAttribute(Fields.TYPE);
                    sortBy = (String) session.getAttribute(Fields.SORT_BY);
                    if (type == null || sortBy == null) {
                        faculty = srv.getList();
                        log.info("Case 3:");
                    } else {
                        SortFacultyImpl sf = new SortFacultyImpl();
                        faculty = sf.getSortedList(
                                type,
                                sortBy,
                                srv.getList());
                        log.info("Case 4:");
                    }
                } else {
                    SortFacultyImpl sf = new SortFacultyImpl();
                    faculty = sf.getSortedList(
                            type,
                            sortBy,
                            srv.getList());
                    session.setAttribute(Fields.TYPE, type);
                    session.setAttribute(Fields.SORT_BY, sortBy);
                    log.info("Case 5:");
                }
            } catch (Exception e) {
                log.error(e.getMessage());
                request.getRequestDispatcher(Pages.ERROR)
                        .forward(request, response);
            }
            log.info("Case FINAL: ");
            request.setAttribute("faculty", faculty);
            request.getRequestDispatcher(Pages.MANAGE_FACULTY)
                    .forward(request, response);
        }
    }
}