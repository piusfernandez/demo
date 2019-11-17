package net.prcins.services.model;

/*
 * Temporary file added to push data in prod redis for rmv
 * delete it.. 
 * */
public class Company {
	private String companyCode;
	private String companyName;

	public Company() {
	}

	public String getCompanyCode() {
		return companyCode;
	}

	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode.replaceAll("\"", "");
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName.replaceAll("\"", "");
	}

	@Override
	public String toString() {
		return "Company [companyCode=" + companyCode + ", companyName=" + companyName + "]";
	}

	
}
