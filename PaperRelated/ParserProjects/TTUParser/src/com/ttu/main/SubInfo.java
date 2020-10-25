package com.ttu.main;


public class SubInfo {
	String name;
	String text;
	String time;
	String m_forward_num;
	String m_common_num;
	String m_like_num;
	String from;
	String url_icon;
	String title;
	
	public SubInfo() {
		
	}
	
	public SubInfo(String name, String text, String time, String m_forward_num, String m_common_num, String m_like_num, String from, String url_icon, String title) {
		this.name = name;
		this.text = text;
		this.time = time;
		this.m_forward_num = m_forward_num;
		this.m_common_num = m_common_num;
		this.m_like_num = m_like_num;
		this.from = from;
		this.url_icon = url_icon;
		this.title = title;
	}
	
	public void setName(String name)
	{
		this.name = name;
	}
	
	public void setText(String text)
	{
		this.text = text;
	}
	
	public void setTime(String time)
	{
		this.time = time;
	}
	
	public void setForwardNum(String num)
	{
		this.m_forward_num = num;
	}
	
	public void setCommonNum(String num)
	{
		this.m_common_num = num;
	}
	
	public void setLikeNum(String num)
	{
		this.m_like_num = num;
	}
	
	public void setFrom(String from)
	{
		this.from = from;
	}
	
	public void setIcon(String icon)
	{
		this.url_icon = icon;
	}
	
	public void setTitle(String title)
	{
		this.title = title;
	}
}
