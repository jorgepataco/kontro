package com.kontro.services.interfaces;

import org.springframework.web.servlet.ModelAndView;

import com.kontro.beans.Dialog;
import com.kontro.enums.DirecionamentoModal;

public interface ModalService {
	
	ModelAndView openDialog(DirecionamentoModal dir);

}
