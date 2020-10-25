package com.ttu.parser.covid19;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

import com.google.gson.JsonArray;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.ttu.config.Config;
import com.ttu.main.ParseJson;

public class GetIncubationData {
	
	public static int s_counter[][] = new int[3][31];
	public static int c_counter[][] = new int[3][31];
	
	public void run() {
		File file = new File(Config.FilePathStr);
		File[] fs = file.listFiles();
		for(File f:fs){
			if(!f.isDirectory() && f.toString().indexOf("DS")==-1) {
				System.out.println(f.toString());
				Parse(f.toString());
			}
		}
		
//		for(int i=0;i<counter.length;i++) {
//			int acc = 0;
//			for(int j=0;j<counter[i].length;j++) {
//				for(int k=0;k<counter[i][j].length;k++) {
//					acc+=counter[i][j][k];
//					counter[i][j][k] = acc;
//				}
//			}
//		}
		
		for(int i=0;i<s_counter.length;i++) {
			for(int j=0;j<s_counter[i].length;j++) {
				System.out.print(s_counter[i][j]+",");
			}
			System.out.println();
		}
		
		System.out.println("----------------------");
		
		for(int i=0;i<c_counter.length;i++) {
			for(int j=0;j<c_counter[i].length;j++) {
				System.out.print(c_counter[i][j]+",");
			}
			System.out.println();
		}
		
		for(int i=0;i<31;i++) {
			System.out.println(1+"-"+(i+1)+","+s_counter[0][i]+","+c_counter[0][i]+",");
		}
		for(int i=0;i<31;i++) {
			System.out.println(2+"-"+(i+1)+","+s_counter[1][i]+","+c_counter[1][i]+",");
		}
		
	}
	
	public void Parse(String file) {
		JsonParser parser = new JsonParser();
	    try {
	        JsonObject object = (JsonObject) parser.parse(new FileReader(file));
	        JsonObject data = object.get("data").getAsJsonObject();
	        JsonArray array = data.get("list").getAsJsonArray();
	        for (int i = 0; i < array.size(); i++) {
	            JsonObject subObject = array.get(i).getAsJsonObject();
	            
	            if (subObject.get("symptom_date") != null && subObject.get("confirm_date") != null) {
	            	boolean s_hasDate = false;
	            	int s_month = 0;
	            	int s_day = 0;
	            	
	            	boolean c_hasDate = false;
	            	int c_month = 0;
	            	int c_day = 0;
	            	
	            	String symptom_date_str = subObject.get("symptom_date").getAsString();
					String confirm_date_str = subObject.get("confirm_date").getAsString();
	                
	            	if (symptom_date_str.indexOf("#") == -1 && confirm_date_str.indexOf("#") == -1) {
//						System.out.println(symptom_date_str+" "+confirm_date_str);
//						System.out.println(subObject.get("hashtag").getAsString());
//						System.out.println(symptom_date_str);
//						System.out.println(confirm_date_str);
	            		String hash_str = subObject.get("hashtag").getAsString();
						
	            		try {
	            			if(symptom_date_str.indexOf("-") != -1) {
			                	String tem[] = ParseJson.parseStrInfo(symptom_date_str, "-");
			                	s_hasDate = true;
			                	s_month = Integer.parseInt(tem[1]);
			                	s_day = Integer.parseInt(tem[2]);
			                } else if(symptom_date_str.indexOf("#") == -1) {
			                	String confirm_arr[] = symptom_date_str.split(",");
			                	s_hasDate = true;
			                	s_month = Integer.parseInt(confirm_arr[0]);
			                	s_day = Integer.parseInt(confirm_arr[1]);
			                }
	            			
	            			if(confirm_date_str.indexOf("-") != -1) {
			                	String tem[] = ParseJson.parseStrInfo(confirm_date_str, "-");
			                	c_hasDate = true;
			                	c_month = Integer.parseInt(tem[1]);
			                	c_day = Integer.parseInt(tem[2]);
			                } else if(confirm_date_str.indexOf("#") == -1) {
			                	String confirm_arr[] = confirm_date_str.split(",");
			                	c_hasDate = true;
			                	c_month = Integer.parseInt(confirm_arr[0]);
			                	c_day = Integer.parseInt(confirm_arr[1]);
			                }
	            		}catch(Exception e) {
	            			System.out.println(hash_str);
	            		}
	            		
	            		if(c_hasDate && s_hasDate) {
	            			s_counter[s_month-1][s_day-1]++;
	            			c_counter[c_month-1][c_day-1]++;
	            		}
						
					}
	            	
	            }
	            
	        }
	    } catch (JsonIOException | JsonSyntaxException | FileNotFoundException e) {
	        e.printStackTrace();
	    }
	}
	
	private void print(int[][] counter) {
		for(int i=0;i<31;i++) {
			System.out.println(1+"-"+(i+1)+","+counter[0][i]+",");
		}
		for(int i=0;i<31;i++) {
			System.out.println(2+"-"+(i+1)+","+counter[1][i]+",");
		}
	}
}
