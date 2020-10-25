package com.ttu.data;

import java.util.List;

public class VCCompany {
	public String c_name;
	public String c_type;
	public String c_relate_company;
	public String c_currency;
	public String c_capital_src;
	public String c_appeal;
	public String c_preference;
	public String c_cop;
	public String c_p_appeal;
	public String c_industry_preference;
	public String c_address;
	public String c_c_list;
	
	public void print()
	{
		System.out.println(
				c_name+" "+
				c_type+" "+
				c_relate_company+" "+
				c_currency+" "+
				c_capital_src+" "+
				c_appeal+" "+
				c_preference+" "+
				c_cop+c_p_appeal+" "+
				c_industry_preference+" "+
				c_address);
	}
	
	public void printContact()
	{
		System.out.println(c_c_list);
	}
}
