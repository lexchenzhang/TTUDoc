package com.ttu.main;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.ttu.config.Config;
import com.ttu.data.VCCompany;
import com.ttu.data.VCContact;
import com.ttu.parser.covid19.GetDemoData;
import com.ttu.util.HttpPost;
import com.ttu.util.Utils;

import java.io.*;

public class main {
	
	public static List<VCCompany> c_list;
	public static List<VCContact> m_list;
	
	public static void main(String[] args) {
		ParseExcel();
//		for(int i=0;i<100;i++) {
//			Utils u = new Utils();
//			System.out.println(u.getSaltString());
//		}
	}
	
	public static void ParseExcel() {
		try {
			String filepath = "/Users/lex/Desktop/data.xlsx";
			ExcelParser excelReader = new ExcelParser(filepath);
			c_list = new ArrayList<VCCompany>();
			m_list = new ArrayList<VCContact>();
			HttpPost m_post = new HttpPost();
			
			Map<Integer, Map<Integer,Object>> map = excelReader.readExcelContent();
			for (int i = 1; i <= map.size(); i++) {
				VCCompany c = new VCCompany();
				c.c_name = map.get(i).get(0).toString();
				c.c_type = map.get(i).get(1).toString();
				c.c_relate_company = map.get(2).get(0).toString();
				c.c_currency = map.get(i).get(3).toString();
				c.c_capital_src = map.get(i).get(4).toString();
				c.c_appeal = map.get(i).get(5).toString();
				c.c_preference = map.get(i).get(6).toString();
				c.c_cop = map.get(i).get(7).toString();
				c.c_p_appeal = map.get(i).get(8).toString();
				c.c_industry_preference = map.get(i).get(9).toString();
				c.c_address = map.get(i).get(14).toString();
				c.c_c_list = "";
				c_list.add(c);
				
				VCContact m = new VCContact();
				m.m_company = c.c_name;
				m.m_name = map.get(i).get(10).toString();
				m.m_position = map.get(i).get(11).toString();
				m.m_phone = map.get(i).get(12).toString();
				m.m_email = map.get(i).get(13).toString();
				m_list.add(m);
			}
			
			//extract unique company from company list
			List<VCCompany> tem = new ArrayList<VCCompany>();
			for(VCCompany element:c_list) {
			    boolean has = false;
			    for(VCCompany i:tem) {
			    	if(i.c_name.equals(element.c_name)) {
			    		has = true;
			    	}
			    }
			    if(!has) {
			    	tem.add(element);
			    }
			}
			
//			for(VCContact element:m_list) {
//				element.print();
//			}
//			System.out.println(m_list.size());
			
//			for(VCCompany c:tem) {
//				c.print();
//			}
			
			//add contact info to company object
			for(VCContact element:m_list) {
				for(VCCompany c:tem) {
					if(element.m_company.equals(c.c_name)) {
						if(c.c_c_list.length()<1)
							c.c_c_list+=(element.m_phone);
						else
							c.c_c_list+="#"+(element.m_phone);
					}
				}
			}
			
//			for(VCCompany element:tem) {
//			    element.printContact();
//			    System.out.println("--------------------");
//			}
			
			//insert contact into database
			for(VCContact element:m_list) {
				if(!m_post.insertContact("http://39.107.240.174/api/mandy/icontact", element)) {
					element.print();
				}
			}
			
			//insert company into database
//			for(VCCompany element:tem) {
//				if(!m_post.insertCompany("http://39.107.240.174/api/mandy/icompany", element)) {
//					element.print();
//				}
//			}
			
		} catch (FileNotFoundException e) {
			System.out.println("未找到指定路径的文件!");
			e.printStackTrace();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void getDemoData() {
		GetDemoData m_gdd = new GetDemoData();
		m_gdd.run();
	}
	
	public static String trimEnding(String text) {
		if (text.length() < 4) {
			return "";
		}
		String ret = text.substring(text.indexOf("】")+1);
		return ret;
	}
	
	public static String[] parseStrInfo(String text) {
		String t_arr[] = text.split("【");
		for(int i=0;i<t_arr.length;i++) {
			System.out.println(t_arr[i]);
		}
		return t_arr;
	}

}
