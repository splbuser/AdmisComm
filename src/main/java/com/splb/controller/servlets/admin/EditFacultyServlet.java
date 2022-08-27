package com.splb.controller.servlets.admin;

import com.splb.controller.pages.Pages;
import com.splb.model.dao.implementation.FacultyDAOImpl;
import com.splb.model.dao.constant.Fields;
import com.splb.model.entity.Faculty;
import com.splb.service.FacultyService;
import com.splb.service.utils.FacultyDataValidator;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;


/**
 * В методе doGet сервлет получает параметр id и по нему извлекает из базы данных соответствующий
 * объект. Если объект найден, то он передается на страницу faculty-edit.jsp. Здесь определена форма,
 * которая выводит редактируемые данные в поля формы. И по нажатию на кнопку данные уходят обратно
 * сервлету EditServlet. Если объект не найден или произошла какая-нибудь ошибка, то возвращается
 * страница error-page.jsp. В методе doPost получаем отправленные данные и через метод update()
 * передаем их в базу данных.
 */

public class EditFacultyServlet extends HttpServlet {

    private static final Logger log = LogManager.getLogger(EditFacultyServlet.class);


    @Override
    public void init() throws ServletException {

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        FacultyService srv = new FacultyService();
        Faculty faculty = null;
        int id = 0;

        try {
            if (Integer.parseInt(request.getParameter(Fields.ID)) != 0) {
                id = Integer.parseInt(request.getParameter(Fields.ID));
                faculty = srv.getById(id);

                request.setAttribute("faculty", faculty);
                getServletContext()
                        .getRequestDispatcher(Pages.EDIT_FACULTY)
                        .forward(request, response);
            } else {
                getServletContext()
                        .getRequestDispatcher(Pages.ERROR)
                        .forward(request, response);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            getServletContext()
                    .getRequestDispatcher(Pages.ERROR)
                    .forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        FacultyService srv = new FacultyService();
        Faculty faculty = null;

        try {

            int id = Integer.parseInt(request.getParameter(Fields.ID));
            String name = request.getParameter(Fields.FACULTY_NAME);
            int tp = Integer.parseInt(request.getParameter(Fields.FACULTY_TOTAL_PLACES));
            int bp = Integer.parseInt(request.getParameter(Fields.FACULTY_BUDGET_PLACES));
            String so = request.getParameter(Fields.SUBJECT_ONE);
            String st = request.getParameter(Fields.SUBJECT_TWO);

            if (
                    id > 0 &&
                            FacultyDataValidator.validateName(name) &&
                            FacultyDataValidator.validateCapacity(bp, tp) &&
                            FacultyDataValidator.validateName(so) &&
                            FacultyDataValidator.validateName(st)
            ) {
                faculty = new Faculty(id, name, bp, tp, so, st);
                if (srv.update(faculty)) {
                    log.info("Faculty info updated successfully");
                    response.sendRedirect(request.getContextPath() + Pages.FACULTY_TABLE);
                } else {
                    log.error("Error while updating");
                    response.sendRedirect(request.getContextPath() + Pages.ERROR);
                }
            } else {
                response.sendRedirect(request.getContextPath() + Pages.ERROR);
                log.error("Wrong input data");
            }

        } catch (Exception e) {
            log.error(e.getMessage());
            getServletContext().getRequestDispatcher(Pages.ERROR)
                    .forward(request, response);
        }
    }
}