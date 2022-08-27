package com.splb.service.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Calendar;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

public class MyTagHandler extends TagSupport {

    private final Logger log = LogManager.getLogger(MyTagHandler.class);

    @Override
    public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();
        try {
            out.print(Calendar.getInstance().getTime());
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new JspException(e.getMessage());
        }
        return SKIP_BODY;
    }
}
