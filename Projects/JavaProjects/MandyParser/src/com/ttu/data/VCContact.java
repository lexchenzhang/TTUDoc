package com.ttu.data;

public class VCContact {
	public String m_company;
	public String m_name;
	public String m_position;
	public String m_phone;
	public String m_email;
	
	public void print()
	{
		System.out.println(
				m_company+" "+
				m_name+" "+
				m_position+" "+
				m_phone+" "+
				m_email);
	}
}
