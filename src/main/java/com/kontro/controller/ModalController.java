package com.kontro.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.kontro.beans.Dialog;
import com.kontro.enums.DirecionamentoModal;
import com.kontro.services.interfaces.ModalService;
import com.kontro.utils.urls.Paths;
import com.kontro.utils.urls.Views;

@Controller
@RequestMapping(Paths.OPENMODAL)
public class ModalController {

	@Autowired
	private ModalService modalService;
	
	@GetMapping(Paths.DIR)
	public ModelAndView openModal(@PathVariable(required = true) DirecionamentoModal dir) throws Exception {
//		ModelAndView mav = new ModelAndView(Views.MODAL);
//		mav.setStatus(HttpStatus.ACCEPTED);
//		return mav;
		return modalService.openDialog(dir);
	}
}
