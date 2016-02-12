package com.pinelet.utp.manage.form;

public class ModelImpl {

	/**
	 * easyUI datagrid控件的请求记录数
	 */
	private int rows;
	
	/**
	 * easyUI datagrid控件的总记录数（目前暂不上）
	 */
	private int total;
	
	/**
	 * easyUI datagrid控件的请求页数
	 */
	private int page;
	
	/**
	 * easyUI datagrid控件的排序字段
	 */
	private String sort;
	
	/**
	 * easyUI datagrid控件的排序方式（desc/asc）
	 */
	private String order;

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

}
