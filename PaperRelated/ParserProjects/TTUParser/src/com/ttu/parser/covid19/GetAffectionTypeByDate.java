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

public class GetAffectionTypeByDate {
	
	public static int counter[][][] = new int[4][3][31];
	
	public void run() {
		File file = new File(Config.FilePathStr);
		File[] fs = file.listFiles();
		for(File f:fs){
			if(!f.isDirectory() && f.toString().indexOf("DS")==-1) {
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
		
//		for(int i=0;i<counter.length;i++) {
//			System.out.println("------------");
//			for(int j=0;j<counter[i].length;j++) {
//				System.out.println();
//				for(int k=0;k<counter[i][j].length;k++) {
//					System.out.print(counter[i][j][k]+",");
//				}
//			}
//			System.out.println();
//		}
		
		for(int i=0;i<31;i++) {
			System.out.println(1+"-"+(i+1)+",Travel Hubei,"+counter[0][0][i]+",");
			System.out.println(1+"-"+(i+1)+",Relative,"+counter[1][0][i]+",");
			System.out.println(1+"-"+(i+1)+",Social,"+counter[2][0][i]+",");
			System.out.println(1+"-"+(i+1)+",Public Transit,"+counter[3][0][i]+",");
		}
		for(int i=0;i<31;i++) {
			System.out.println(2+"-"+(i+1)+",Travel Hubei,"+counter[0][1][i]+",");
			System.out.println(2+"-"+(i+1)+",Relative,"+counter[1][1][i]+",");
			System.out.println(2+"-"+(i+1)+",Social,"+counter[2][1][i]+",");
			System.out.println(2+"-"+(i+1)+",Public Transit,"+counter[3][1][i]+",");
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
	            
	            if (subObject.get("s_infect_type") != null && subObject.get("confirm_date") != null) {
	            	String date = subObject.get("confirm_date").getAsString();
	            	boolean hasDate = false;
	            	int month = 0;
	            	int day = 0;
	                if(date.indexOf("-") != -1) {
	                	String tem[] = ParseJson.parseStrInfo(date, "-");
	                	hasDate = true;
	                	month = Integer.parseInt(tem[1]);
	                	day = Integer.parseInt(tem[2]);
	                } else if(date.indexOf("#") == -1) {
	                	String confirm_arr[] = date.split(",");
	                	hasDate = true;
	                	month = Integer.parseInt(confirm_arr[0]);
	                	day = Integer.parseInt(confirm_arr[1]);
	                }
	                if(hasDate) {
	                	String s_infect_type = subObject.get("s_infect_type").getAsString();
		            	int type = 0;//0:unknown 1:social 2:relative 3:travel
		            	if(s_infect_type.indexOf("亲属") != -1) {
		            		type = 1;
		            	}else if(s_infect_type.indexOf("社区") != -1) {
		            		type = 2;
		            	}else if(s_infect_type.indexOf("旅行") != -1) {
		            		type = 3;
		            	}
		            	if(type != 0)
		            		counter[type][month-1][day-1]++;
		            	
		            	if (subObject.get("s_travel_hubei") != null) {
			            	String travel_flag = subObject.get("s_travel_hubei").getAsString();
			            	if(travel_flag.indexOf("是") != -1) {
			            		counter[0][month-1][day-1]++;
			            	}
			            }
	                }
	            }
	            
	        }
	    } catch (JsonIOException | JsonSyntaxException | FileNotFoundException e) {
	        e.printStackTrace();
	    }
	}
}
