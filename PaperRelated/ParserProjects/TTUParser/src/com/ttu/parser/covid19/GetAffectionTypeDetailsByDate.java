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

public class GetAffectionTypeDetailsByDate {
	
	public static int counter[][][] = new int[14][3][31];
	
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
		
//		for(int i=0;i<31;i++) {
//			System.out.println(
//					1+"-"+(i+1)+","+counter[0][0][i]+","+counter[1][0][i]+","+counter[2][0][i]+","+counter[3][0][i]+","
//							+counter[4][0][i]+","+counter[5][0][i]+","+counter[6][0][i]+","+counter[7][0][i]+","+counter[8][0][i]+","
//									+counter[9][0][i]+","+counter[10][0][i]+","+counter[11][0][i]+","+counter[12][0][i]+","+counter[13][0][i]+","
//					);
//		}
//		
//		for(int i=0;i<28;i++) {
//			System.out.println(
//					2+"-"+(i+1)+","+counter[0][1][i]+","+counter[1][1][i]+","+counter[2][1][i]+","+counter[3][1][i]+","
//							+counter[4][1][i]+","+counter[5][1][i]+","+counter[6][1][i]+","+counter[7][1][i]+","+counter[8][1][i]+","
//									+counter[9][1][i]+","+counter[10][1][i]+","+counter[11][1][i]+","+counter[12][1][i]+","+counter[13][1][i]+","
//					);
//		}
		
		for(int i=0;i<31;i++) {
			System.out.println(
					counter[6][0][i]+","+counter[11][0][i]+","+counter[7][0][i]+","
					);
		}
		
		for(int i=0;i<28;i++) {
			System.out.println(
					counter[6][0][i]+","+counter[11][1][i]+","+counter[7][1][i]+","
					);
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
	            
	            if (subObject.get("s_infect_type") != null && subObject.get("confirm_date") != null && subObject.get("infect_source") != null) {
	            	String date = subObject.get("confirm_date").getAsString();
	            	String str_infect_source = subObject.get("infect_source").getAsString();
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
	                	
	                	if(str_infect_source.indexOf("#") == -1) {
	                		
			            	int type = -1;//0:unknown 1:social 2:relative 3:travel
                    		
                    		if (str_infect_source.indexOf("医院") != -1 || str_infect_source.indexOf("卫生") != -1) {
                    			type = 0;
                    		} else if (str_infect_source.indexOf("老年") != -1) {
                    			type = 1;
                    		} else if (str_infect_source.indexOf("世贸") != -1) {
                    			type = 2;
                    		} else if (str_infect_source.indexOf("机场") != -1 || str_infect_source.indexOf("航班") != -1 || str_infect_source.indexOf("飞机") != -1 || str_infect_source.indexOf("乘机") != -1) {
                    			type = 3;
                    		} else if (str_infect_source.indexOf("乘车") != -1 || str_infect_source.indexOf("驾车") != -1 || str_infect_source.indexOf("自驾") != -1) {
                    			type = 4;
                    		} else if (str_infect_source.indexOf("高铁") != -1 || str_infect_source.indexOf("动车") != -1 || str_infect_source.indexOf("列车") != -1 || str_infect_source.indexOf("火车") != -1) {
                    			type = 5;
                    		} else if (
                    				str_infect_source.indexOf("患者") != -1
                    				|| str_infect_source.indexOf("亲属") != -1
                    				|| str_infect_source.indexOf("接触") != -1) {
                    			type = 6;
                    		} else if (
                    				str_infect_source.indexOf("聚会") != -1
                    				|| str_infect_source.indexOf("聚餐") != -1) {
                    			type = 7;
                    		} else if (
                    				str_infect_source.indexOf("村") != -1
                    				|| str_infect_source.indexOf("区") != -1
                    				|| str_infect_source.indexOf("市") != -1
                    				|| str_infect_source.indexOf("县") != -1
                    				|| str_infect_source.indexOf("街道") != -1
                    				|| str_infect_source.indexOf("镇") != -1) {
                    			type = 8;
                    		} else if (str_infect_source.indexOf("酒店") != -1 || str_infect_source.indexOf("饭店") != -1) {
                    			type = 9;
                    		} else if (str_infect_source.indexOf("花园") != -1) {
                    			type = 10;
                    		} else if (str_infect_source.indexOf("店") != -1) {
                    			type = 11;
                    		} else if (str_infect_source.indexOf("旅行") != -1) {
                    			type = 12;
                    		} else if (str_infect_source.indexOf("未知") != -1 || str_infect_source.indexOf("未外出") != -1) {
                    			type = 13;
                    		} else {
//                    			System.out.println(str_infect_source+" ");
                    		}
                    		
                    		if(type != -1)
    		            		counter[type][month-1][day-1]++;
                    		
                    	}
//		            	
//		            	if (subObject.get("s_travel_hubei") != null) {
//			            	String travel_flag = subObject.get("s_travel_hubei").getAsString();
//			            	if(travel_flag.indexOf("是") != -1) {
//			            		counter[0][month-1][day-1]++;
//			            	}
//			            }
	                }
	            }
	            
	        }
	    } catch (JsonIOException | JsonSyntaxException | FileNotFoundException e) {
	        e.printStackTrace();
	    }
	}
}
