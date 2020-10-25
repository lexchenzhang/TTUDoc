package com.ttu.util;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;

import com.ttu.data.VCCompany;
import com.ttu.data.VCContact;

public class HttpPost {
	
	public boolean insertContact(String URL, VCContact m) {
		DataOutputStream out = null;
        BufferedReader in = null;
        StringBuilder result = new StringBuilder();
        HttpURLConnection conn = null;
        Utils u = new Utils();
        
        try{
            URL url = new URL(URL);
            conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setRequestMethod("POST");
            conn.setUseCaches(false);
            conn.setInstanceFollowRedirects(true);
            conn.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
            conn.connect();
            
            JSONObject info = new JSONObject();
            info.put("m_company", 	m.m_company);
            info.put("m_name", 		m.m_name);
            info.put("m_position", 	m.m_position);
            info.put("m_phone", 	m.m_phone);
            info.put("m_email", 	m.m_email);
            
            Map<String, String> parameters = new HashMap<>();
            parameters.put("appid", 		"mandy");
            parameters.put("uid", 			u.getSaltString());
            parameters.put("access_token", 	"fdfsfsafdsafd");
            parameters.put("sign", 			"e2c1d2e6394ac66bda6a3ede05d7d2e0cfa66085");
            parameters.put("info", 			info.toString());
            
            out = new DataOutputStream(conn.getOutputStream());
            out.write(asUrlParams(parameters).getBytes("UTF-8"));
            out.flush();
            out.close();
            
            if (200 == conn.getResponseCode()){
                in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
                String line;
                while ((line = in.readLine()) != null){
                    result.append(line);
                    System.out.println(line);
                    return (line.indexOf("OK") != -1);
                }
            }else{
                System.out.println("ResponseCode is an error code:" + conn.getResponseCode());
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try{
                if(out != null){
                    out.close();
                }
                if(in != null){
                    in.close();
                }
            }catch (IOException ioe){
                ioe.printStackTrace();
            }
        }
		return false;
	}
	
	public boolean insertCompany(String URL, VCCompany c) {
		DataOutputStream out = null;
        BufferedReader in = null;
        StringBuilder result = new StringBuilder();
        HttpURLConnection conn = null;
        Utils u = new Utils();
        
        try{
            URL url = new URL(URL);
            conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setRequestMethod("POST");
            conn.setUseCaches(false);
            conn.setInstanceFollowRedirects(true);
            conn.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
            conn.connect();
            
            JSONObject info = new JSONObject();
            info.put("c_name", 			c.c_name);
            info.put("c_type", 			c.c_type);
            info.put("c_relate", 		c.c_relate_company);
            info.put("c_currency", 		c.c_currency);
            info.put("c_capital_src", 	c.c_capital_src);
            info.put("c_appeal", 		c.c_appeal);
            info.put("c_preference", 	c.c_preference);
            info.put("c_cop", 			c.c_cop);
            info.put("c_p_appeal", 		c.c_p_appeal);
            info.put("c_industry_p", 	c.c_industry_preference);
            info.put("c_address", 		c.c_address);
            info.put("c_con_list", 		c.c_c_list);
            
            Map<String, String> parameters = new HashMap<>();
            parameters.put("appid", 		"mandy");
            parameters.put("uid", 			u.getSaltString());
            parameters.put("access_token", 	"fdfsfsafdsafd");
            parameters.put("sign", 			"e2c1d2e6394ac66bda6a3ede05d7d2e0cfa66085");
            parameters.put("info", 			info.toString());
            
            out = new DataOutputStream(conn.getOutputStream());
            out.write(asUrlParams(parameters).getBytes("UTF-8"));
            out.flush();
            out.close();
            
            if (200 == conn.getResponseCode()){
                in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
                String line;
                while ((line = in.readLine()) != null){
                    result.append(line);
                    System.out.println(line);
                    return (line.indexOf("OK") != -1);
                }
            }else{
                System.out.println("ResponseCode is an error code:" + conn.getResponseCode());
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try{
                if(out != null){
                    out.close();
                }
                if(in != null){
                    in.close();
                }
            }catch (IOException ioe){
                ioe.printStackTrace();
            }
        }
		return false;
	}
	
	public static String asUrlParams(Map<String, String> source){
	    Iterator<String> it = source.keySet().iterator();
	    StringBuilder paramStr = new StringBuilder();
	    while (it.hasNext()){
	        String key = it.next();
	        String value = source.get(key);
	        if (StringUtils.isBlank(value)){
	            continue;
	        }
	        try {
	            value = URLEncoder.encode(value, "utf-8");
	        } catch (UnsupportedEncodingException e) {
	            // do nothing
	        }
	        paramStr.append("&").append(key).append("=").append(value);
	    }
	    return paramStr.substring(1);
	}
}
