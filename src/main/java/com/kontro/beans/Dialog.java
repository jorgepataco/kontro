package com.kontro.beans;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.kontro.enums.DirecionamentoModal;

public class Dialog {
	
	private DirecionamentoModal dir;
	private Map<String, Object> modalAtributes = new HashMap<>();
	
	@JsonAnySetter
    public void addModalAtributes(String type, Object objValue) {
		if(type.equals("dir")){
			this.dir = DirecionamentoModal.valueOf(String.valueOf(objValue));
		}else{
			modalAtributes.put(type, objValue);
		}
    }

	public DirecionamentoModal getDir() {
		return dir;
	}

	public Map<String, Object> getModalAtributes() {
		return modalAtributes;
	}

	@Override
	public String toString() {
		return "Dialog [dir=" + dir + ", modalAtributes=" + modalAtributes + "]";
	}
	
}
