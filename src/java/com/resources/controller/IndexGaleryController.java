package com.resources.controller;

import com.resources.facade.GaleryFacade;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;


@Controller
@RequestMapping(value = "/Galery")
public class IndexGaleryController {

    @RequestMapping(value = "")
    public ModelAndView getNewsView(ModelMap mm, HttpSession session) {
        List galeries = new GaleryFacade().findAll();
        mm.put("INDEX_GALERIES", galeries);
        return new ModelAndView("/galery");
    }

    @RequestMapping(value = "/{id}")
    public ModelAndView getNewsDetailView(@PathVariable(value = "id") Integer id, ModelMap mm, HttpSession session) {
        session.setAttribute("INDEX_GALERIES_DETAIL", new GaleryFacade().findEager(id));
        return new ModelAndView("/galery_detail");
    }
}
