package com.kontro.beans;

import java.util.ArrayList;
import java.util.List;

import com.kontro.enums.TargetExt;

public class ExcelExport<T> {
	
	private List<T> sheet = new ArrayList<T>();

	public List<T> getSheet() {
		return sheet;
	}

	public void setSheet(List<T> sheet) {
		this.sheet = sheet;
	}

}
