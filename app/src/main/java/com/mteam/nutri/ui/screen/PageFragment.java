package com.mteam.nutri.ui.screen;


public class PageFragment extends AbstractFragment {

	protected int pageNumber;
	
	protected String name;
	
	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public int getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

}
