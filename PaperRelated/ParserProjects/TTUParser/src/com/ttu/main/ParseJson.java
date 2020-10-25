package com.ttu.main;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class ParseJson {
	
	public static int travel_total_num = 0;
	
	public static int[] infect_source_counter = new int[12];
	
	public static final int I_S_TYPE_HOSPITAL = 0;
	public static final int I_S_TYPE_TRANS = 1;
	public static final int I_S_TYPE_SUPERMARKET = 2;
	public static final int I_S_TYPE_RESTAURANT = 3;
	public static final int I_S_TYPE_SHOPPING = 4;
	public static final int I_S_TYPE_NRSING = 5;
	public static final int I_S_TYPE_RESIDENTIAL = 6;
	public static final int I_S_TYPE_RELATIVE = 7;
	public static final int I_S_TYPE_HOTEL = 8;
	public static final int I_S_TYPE_AIRPLANE = 9;
	public static final int I_S_TYPE_TRAVEL = 10;
	public static final int I_S_TYPE_UNKNOWN = 11;
	
	public void genSectionData(JsonObject o) {
		JsonObject data = o.get("data").getAsJsonObject();
        JsonArray array = data.get("list").getAsJsonArray();
        List<String> list = new ArrayList<String>();
        for (int i = 0; i < array.size(); i++) {
            JsonObject subObject = array.get(i).getAsJsonObject();
            
            if(subObject.get("county") != null) {
            	String county = subObject.get("county").getAsString();
                if(county.indexOf("#") == -1) {
                	if(!list.contains(county)) {
                		list.add(county);
                	}
                }
            }
            
        }
        
        for(String str:list){
            String value=str;
            System.out.print("\""+value+"\",");
        }
        System.out.println();
	}
	
	public int[] genIncubationData(JsonObject o) {
		int tem_arr[] = new int[30];
		JsonObject data = o.get("data").getAsJsonObject();
        JsonArray array = data.get("list").getAsJsonArray();
        for (int i = 0; i < array.size(); i++) {
            JsonObject subObject = array.get(i).getAsJsonObject();
            if (subObject.get("symptom_date") != null && subObject.get("confirm_date") != null) {
				String symptom_date_str = subObject.get("symptom_date").getAsString();
				String confirm_date_str = subObject.get("confirm_date").getAsString();
				if (
						symptom_date_str.indexOf("#") == -1 
						&& 
						confirm_date_str.indexOf("#") == -1 
						) {
//					System.out.println(symptom_date_str+" "+confirm_date_str);
//					System.out.println(subObject.get("hashtag").getAsString());
//					System.out.println(symptom_date_str);
//					System.out.println(confirm_date_str);
					
					String symptom_arr[] = symptom_date_str.split(",");
					String confirm_arr[] = confirm_date_str.split(",");
					if (symptom_arr.length == confirm_arr.length) {
						if (Integer.parseInt(symptom_arr[0]) == Integer.parseInt(confirm_arr[0]) && Integer.parseInt(symptom_arr[0]) == 2) {
							int offset = Integer.parseInt(confirm_arr[1]) - Integer.parseInt(symptom_arr[1]);
							if(offset>=0 && offset < 30) {
								tem_arr[offset]++;
							}
						}
					}
				}
            }
        }
        return tem_arr;
	}
	
	/**
	 * 
	 * @param list
	 * @param o
	 */
	public void genNetMapData(int counter, List<Map<String,String>> list, JsonObject o) {
		JsonObject data = o.get("data").getAsJsonObject();
        JsonArray array = data.get("list").getAsJsonArray();
        for (int i = 0; i < array.size(); i++) {
            JsonObject subObject = array.get(i).getAsJsonObject();
            if (subObject.get("relate_uid") != null) {
				String relate_uid_arr = subObject.get("relate_uid").getAsString();
				String uid_arr[] = relate_uid_arr.split(",");
				String hash_tag_str = subObject.get("hashtag").getAsString();
				if(uid_arr.length > 1) {
					for(int j=0;j<uid_arr.length;j++) {
						Map<String,String> row = new LinkedHashMap<String, String>();
						row.put("1", counter+"");
						row.put("2", hash_tag_str);
						row.put("3", uid_arr[j]);
						list.add(row);
					}
				}
            }
        }
	}
	
	public void getSourceWord(JsonObject o) {
		JsonObject data = o.get("data").getAsJsonObject();
        JsonArray array = data.get("list").getAsJsonArray();
        for (int i = 0; i < array.size(); i++) {
            JsonObject subObject = array.get(i).getAsJsonObject();
            if (subObject.get("infect_source") != null) {
            	String str_infect_source = subObject.get("infect_source").getAsString();
            	if (subObject.get("confirm_date") != null) {
                	String date = subObject.get("confirm_date").getAsString();
                	String hashtag = subObject.get("hashtag").getAsString();
                    if(date.indexOf(",") != -1) {
                    	
                    	try {
                    		String tem[] = parseStrInfo(date, ",");
                        	Date d = new Date(2020,Integer.parseInt(tem[0]),Integer.parseInt(tem[1]));
                        	if (d.getMonth() == 2 && d.getDate() > 22 && d.getDate() < 29) {
                        		
                        		if (subObject.get("s_travel_hubei") != null) {
                                	String travel_flag = subObject.get("s_travel_hubei").getAsString();
                                	if(travel_flag.indexOf("是") != -1) {
                                		infect_source_counter[I_S_TYPE_TRAVEL]++;
                                		continue;
                                	}
                                }
                        		
                        		if(str_infect_source.indexOf("#") == -1) {
                            		
                            		if (str_infect_source.indexOf("医院") != -1 || str_infect_source.indexOf("卫生") != -1) {
                            			infect_source_counter[I_S_TYPE_HOSPITAL]++;
                            		} else if (str_infect_source.indexOf("老年") != -1) {
                            			infect_source_counter[I_S_TYPE_NRSING]++;
                            		} else if (str_infect_source.indexOf("世贸") != -1) {
                            			infect_source_counter[I_S_TYPE_SHOPPING]++;
                            		} else if (
                            				str_infect_source.indexOf("机场") != -1
                            				|| str_infect_source.indexOf("航班") != -1) {
                            			infect_source_counter[I_S_TYPE_AIRPLANE]++;
                            		} else if (
                            				str_infect_source.indexOf("湖北") != -1
                            				|| str_infect_source.indexOf("武汉") != -1
                            				|| str_infect_source.indexOf("旅") != -1
                            				|| str_infect_source.indexOf("乘") != -1
                            				|| str_infect_source.indexOf("车") != -1
                            				|| str_infect_source.indexOf("高铁") != -1
                            				|| str_infect_source.indexOf("驾") != -1
                            				|| str_infect_source.indexOf("站") != -1
                            				|| str_infect_source.indexOf("外出") != -1
                            				|| str_infect_source.indexOf("到") != -1) {
                            			infect_source_counter[I_S_TYPE_TRANS]++;
                            		} else if (
                            				str_infect_source.indexOf("患者") != -1
                            				|| str_infect_source.indexOf("亲属") != -1
                            				|| str_infect_source.indexOf("接触") != -1) {
                            			infect_source_counter[I_S_TYPE_RELATIVE]++;
                            		} else if (
                            				str_infect_source.indexOf("聚会") != -1
                            				|| str_infect_source.indexOf("聚餐") != -1) {
                            			infect_source_counter[I_S_TYPE_RESTAURANT]++;
                            		} else if (
                            				str_infect_source.indexOf("村") != -1
                            				|| str_infect_source.indexOf("区") != -1
                            				|| str_infect_source.indexOf("市") != -1
                            				|| str_infect_source.indexOf("县") != -1
                            				|| str_infect_source.indexOf("街道") != -1
                            				|| str_infect_source.indexOf("镇") != -1) {
                            			infect_source_counter[I_S_TYPE_RESTAURANT]++;
                            		} else if (str_infect_source.indexOf("酒店") != -1 || str_infect_source.indexOf("饭店") != -1) {
                            			infect_source_counter[I_S_TYPE_HOTEL]++;
                            		} else if (str_infect_source.indexOf("花园") != -1) {
                            			infect_source_counter[I_S_TYPE_RESIDENTIAL]++;
                            		} else if (str_infect_source.indexOf("店") != -1) {
                            			infect_source_counter[I_S_TYPE_SUPERMARKET]++;
                            		} else if (str_infect_source.indexOf("未知") != -1 || str_infect_source.indexOf("未外出") != -1) {
                            			infect_source_counter[I_S_TYPE_UNKNOWN]++;
                            		} else {
//                            			System.out.print(str_infect_source+" ");
                            		}
                            	}
                        		
                        	}
                    	}catch(Exception e) {
                    		System.err.println("!!"+hashtag);
                    	}
                    	
                    }
                }
            	
            	
            }
        }
	}
	
	/**
	 * 
	 * @param o
	 */
	public void getSymptoms(JsonObject o) {
		JsonObject data = o.get("data").getAsJsonObject();
        JsonArray array = data.get("list").getAsJsonArray();
        for (int i = 0; i < array.size(); i++) {
            JsonObject subObject = array.get(i).getAsJsonObject();
            if (subObject.get("symptoms") != null) {
            	String str_symptoms = subObject.get("symptoms").getAsString();
            	if(str_symptoms.indexOf("#") == -1) {
            		System.out.print(str_symptoms+" ");
            	}
            }
        }
	}
	
//	public void genTreeChart(JsonArray arr, JsonObject o, String province, int counter) {
//		JsonObject p_province = new JsonObject();
//		JsonObject p_child = new JsonObject();
//		p_province.addProperty("id", province);
//		p_child.add("data", p_province);
//		JsonArray sub_array = new JsonArray();
//		p_child.add("children", sub_array);
//		arr.add(p_child);
//		
//		JsonObject data = o.get("data").getAsJsonObject();
//        JsonArray array = data.get("list").getAsJsonArray();
//        for (int i = 0; i < array.size(); i++) {
//            JsonObject subObject = array.get(i).getAsJsonObject();
//            
//            if(subObject.get("county") != null) {
//            	String county = subObject.get("county").getAsString();
//                if(county.indexOf("#") == -1) {
//                	for(int j=0;j<main.section_label[counter].length;j++) {
//                		if(county.equals(main.section_label[counter][j])) {
//                			main.section_counter[counter][j]++;
//                		}
//                	}
//                }
//            }
//            
//        }
//	}
	
	public void genTreeChartEx(JsonArray arr, JsonObject o, String province, int counter) {
		JsonObject p_province = new JsonObject();
		JsonObject p_child = new JsonObject();
		p_province.addProperty("id", province);
		p_child.add("data", p_province);
		JsonArray sub_array = new JsonArray();
		p_child.add("children", sub_array);
		arr.add(p_child);
		
		JsonObject data = o.get("data").getAsJsonObject();
        JsonArray array = data.get("list").getAsJsonArray();
        for (int i = 0; i < array.size(); i++) {
            JsonObject subObject = array.get(i).getAsJsonObject();
            if (subObject.get("s_travel_hubei") != null) {
            	String travel_flag = subObject.get("s_travel_hubei").getAsString();
            	if(travel_flag.indexOf("是") != -1) {
            		if (subObject.get("confid") != null) {
            			String content = subObject.get("confid").getAsString();
            			JsonObject p_pationt = new JsonObject();
            			p_pationt.addProperty("id", content);
            			JsonObject p_sub_child = new JsonObject();
            			p_sub_child.add("data", p_pationt);
            			JsonArray x_array = new JsonArray();
            			p_sub_child.add("children", x_array);
            			
            			if (subObject.get("relate_uid") != null) {
            				String relate_uid_arr = subObject.get("relate_uid").getAsString();
            				String uid_arr[] = relate_uid_arr.split(",");
            				if(uid_arr.length > 1) {
            					for(int j=0;j<uid_arr.length;j++) {
            						JsonObject x_pationt = new JsonObject();
                					x_pationt.addProperty("id", "case["+j+"]");
                					JsonObject x_sub_child = new JsonObject();
                					x_sub_child.add("data", p_pationt);
                					x_array.add(x_sub_child);
            					}
            				}
            				travel_total_num += uid_arr.length;
            			}
            			
            			sub_array.add(p_sub_child);
            		}
            	}
            }
        }
	}
	
	public int[][] getConfirmArray(JsonObject o) {
		int[][] tem_arr = new int[13][32];
		JsonObject data = o.get("data").getAsJsonObject();
        JsonArray array = data.get("list").getAsJsonArray();
        for (int i = 0; i < array.size(); i++) {
            JsonObject subObject = array.get(i).getAsJsonObject();
            if (subObject.get("confirm_date") != null) {
            	String date = subObject.get("confirm_date").getAsString();
//            	System.out.println(date);
                if(date.indexOf("-") != -1) {
                	String tem[] = parseStrInfo(date, "-");
//                	printStrArr(tem);
                	tem_arr[Integer.parseInt(tem[1])][Integer.parseInt(tem[2])]++;
                }
            }
        }
        return tem_arr;
	}
	
	public void getTemplate(JsonObject o) {
		JsonObject data = o.get("data").getAsJsonObject();
        JsonArray array = data.get("list").getAsJsonArray();
        for (int i = 0; i < array.size(); i++) {
            JsonObject subObject = array.get(i).getAsJsonObject();
            
            if (subObject.get("user_name") != null) {
            	String str_info = subObject.get("user_name").getAsString();
            	if(str_info.indexOf("男") != -1) {
            		subObject.addProperty("gender", "男");
            	}else if(str_info.indexOf("女") != -1){
            		subObject.addProperty("gender", "女");
            	}else {
            		subObject.addProperty("gender", "#");
            	}
            	
            	if(str_info.indexOf("岁") != -1) {
                	String str_age = str_info.substring(str_info.indexOf("岁")-2, str_info.indexOf("岁"));
                	if(
                			str_age.indexOf("，") == -1
                			&& str_age.indexOf("、") == -1
                			&& str_age.indexOf("余") == -1
                			&& str_age.indexOf("是") == -1
                			&& str_age.indexOf(",") == -1
                			&& str_age.indexOf("周") == -1
                			&& str_age.indexOf(" ") == -1
                			) {
                		int age = Integer.parseInt(str_age);
                		subObject.addProperty("age", age);
                	}else {
                		subObject.addProperty("age", "#");
                	}
            	}
            	
            	String track = subObject.get("track").getAsString();
                String tem_arr[] = parseStrInfo(track, "}");
                if(tem_arr[tem_arr.length-2].indexOf("日") != -1 && tem_arr[tem_arr.length-2].indexOf("月") != -1) {
                	String tem = tem_arr[tem_arr.length-2].substring(0, tem_arr[tem_arr.length-2].indexOf("action"));
                	if(tem.lastIndexOf("日") != -1 && tem.lastIndexOf("月") != -1) {
                	tem = tem.substring(tem.lastIndexOf("月")-1, tem.lastIndexOf("日")+1);
                	if(tem.length() < 6) {
                		subObject.addProperty("confirm_date", parseDate(tem));
                	}
                	}
                }
                
                String other_info = subObject.get("other_info").getAsString();
                boolean b = false;
                if (other_info.indexOf("无湖北") != -1 || track.indexOf("无湖北") != -1) {
                	b = false;
                }else if (other_info.indexOf("湖北") != -1 || track.indexOf("湖北") != -1) {
                	b = true;
                }
                subObject.addProperty("s_travel_hubei", b?"是":"否");
            }
            
            
            subObject.addProperty("name", "#");
            
            subObject.addProperty("city", "#");
            subObject.addProperty("province", "#");
            subObject.addProperty("s_infect_type", "#");
            subObject.addProperty("s_infect_method", "#");
            subObject.addProperty("symptoms", "发热");
            subObject.addProperty("symptom_date", "#");
            subObject.addProperty("chronic_disease", "#");
            subObject.addProperty("severe_date", "#");
            subObject.addProperty("relate_uid", "#");
            subObject.addProperty("infect_source", "#");
            subObject.addProperty("data_source", "#");
        }
	}
	
	public int getNumbers(JsonObject o) {
		JsonObject data = o.get("data").getAsJsonObject();
        JsonArray array = data.get("list").getAsJsonArray();
        return array.size();
	}
	
	public int[] getGenderArray(JsonObject o) {
		int[] tem_arr = new int[2];
		JsonObject data = o.get("data").getAsJsonObject();
        JsonArray array = data.get("list").getAsJsonArray();
        for (int i = 0; i < array.size(); i++) {
            JsonObject subObject = array.get(i).getAsJsonObject();
            if (subObject.get("gender") != null) {
            	String str_gender = subObject.get("gender").getAsString();
            	if(str_gender.indexOf("男") != -1) {
            		tem_arr[0]++;
            	}else if(str_gender.indexOf("女") != -1){
            		tem_arr[1]++;
            	}
            }
        }
        return tem_arr;
	}
	
	public void getTestAge(JsonObject o) {
		JsonObject data = o.get("data").getAsJsonObject();
        JsonArray array = data.get("list").getAsJsonArray();
        for (int i = 0; i < array.size(); i++) {
            JsonObject subObject = array.get(i).getAsJsonObject();
            if (subObject.get("user_name") != null) {
            	String str_info = subObject.get("user_name").getAsString();
            	if(str_info.indexOf("岁") != -1) {
                	String str_age = str_info.substring(str_info.indexOf("岁")-2, str_info.indexOf("岁"));
                	if(
                			str_age.indexOf("，") == -1
                			&& str_age.indexOf("、") == -1
                			&& str_age.indexOf("余") == -1
                			&& str_age.indexOf("是") == -1
                			&& str_age.indexOf(",") == -1
                			&& str_age.indexOf("周") == -1
                			&& str_age.indexOf(" ") == -1
                			) {
                		int age = Integer.parseInt(str_age);
                    	System.out.println(age);
                	}else {
                		System.out.println("att  ------- "+str_age);
                	}
                	
            	}
            	
            }
        }
	}
	
	public int[] getAgeArray(JsonObject o) {
		int[] tem_arr = new int[100];
		JsonObject data = o.get("data").getAsJsonObject();
        JsonArray array = data.get("list").getAsJsonArray();
        for (int i = 0; i < array.size(); i++) {
            JsonObject subObject = array.get(i).getAsJsonObject();
            if (subObject.get("age") != null) {
            	String str_age = subObject.get("age").getAsString();
            	String str_hashtag = subObject.get("hashtag").getAsString();
            	System.out.println(str_age);
            	System.out.println(str_hashtag);
            	if (str_age.indexOf("#") != -1) {
            		continue;
            	}
            	int age = Integer.parseInt(str_age);
        		tem_arr[age]++;
            }
        }
        return tem_arr;
	}
	
	public int[] getAffectArray(JsonObject o) {
		int[] tem_arr = new int[4];
		JsonObject data = o.get("data").getAsJsonObject();
        JsonArray array = data.get("list").getAsJsonArray();
        for (int i = 0; i < array.size(); i++) {
            JsonObject subObject = array.get(i).getAsJsonObject();
            if (subObject.get("s_infect_type") != null) {
            	String s_infect_type = subObject.get("s_infect_type").getAsString();
            	int type = -1;//0:unknown 1:social 2:relative 3:travel
            	if(s_infect_type.indexOf("亲属") != -1) {
            		type = 2;
            	}else if(s_infect_type.indexOf("社区") != -1) {
            		type = 1;
            	}else if(s_infect_type.indexOf("旅行") != -1) {
            		type = 3;
            	}
            	
            	if (subObject.get("s_travel_hubei") != null) {
	            	String travel_flag = subObject.get("s_travel_hubei").getAsString();
	            	if(travel_flag.indexOf("是") != -1) {
	            		type = 0;
	            	}
	            }
            	
//            	System.out.println(date);
            	if(type != -1)
            		tem_arr[type]++;
            }
        }
        return tem_arr;
	}
	
	public int[] getTravelBool(JsonObject o) {
		int[] tem_arr = new int[2];
		JsonObject data = o.get("data").getAsJsonObject();
        JsonArray array = data.get("list").getAsJsonArray();
        for (int i = 0; i < array.size(); i++) {
            JsonObject subObject = array.get(i).getAsJsonObject();
            if (subObject.get("s_travel_hubei") != null) {
            	String travel_flag = subObject.get("s_travel_hubei").getAsString();
            	if(travel_flag.indexOf("否") != -1) {
            		tem_arr[0]++;
            	}else if(travel_flag.indexOf("是") != -1) {
            		tem_arr[1]++;
            	}
            }
        }
        return tem_arr;
	}
	
	public void addComfirmDate(JsonObject o) {
		JsonObject data = o.get("data").getAsJsonObject();
        JsonArray array = data.get("list").getAsJsonArray();
        for (int i = 0; i < array.size(); i++) {
            JsonObject subObject = array.get(i).getAsJsonObject();
            String track = subObject.get("track").getAsString();
            String tem_arr[] = parseStrInfo(track, "}");
            if(tem_arr[tem_arr.length-2].indexOf("日") != -1 && tem_arr[tem_arr.length-2].indexOf("月") != -1) {
            	String tem = tem_arr[tem_arr.length-2].substring(0, tem_arr[tem_arr.length-2].indexOf("action"));
//            	System.out.println(tem);
            	if(tem.lastIndexOf("日") != -1 && tem.lastIndexOf("月") != -1) {
            	tem = tem.substring(tem.lastIndexOf("月")-1, tem.lastIndexOf("日")+1);
            	if(tem.length() < 6) {
//            		System.out.println(parseDate(tem));
            		subObject.addProperty("confirm_date", parseDate(tem));
            	}
            	}
            }
        }
	}
	
	public void addTravelHubeiBool(JsonObject o) {
		JsonObject data = o.get("data").getAsJsonObject();
        JsonArray array = data.get("list").getAsJsonArray();
        for (int i = 0; i < array.size(); i++) {
            JsonObject subObject = array.get(i).getAsJsonObject();
            String other_info = subObject.get("other_info").getAsString();
            String track = subObject.get("track").getAsString();
            boolean b = false;
            if (other_info.indexOf("无湖北") != -1 || track.indexOf("无湖北") != -1) {
            	b = false;
            }else if (other_info.indexOf("湖北") != -1 || track.indexOf("湖北") != -1) {
            	b = true;
            }
            subObject.addProperty("b_travel_hubei", b);
        }
	}
	
	public void addInfectType(JsonObject o) {
		JsonObject data = o.get("data").getAsJsonObject();
        JsonArray array = data.get("list").getAsJsonArray();
        for (int i = 0; i < array.size(); i++) {
            JsonObject subObject = array.get(i).getAsJsonObject();
            String other_info = subObject.get("other_info").getAsString();
            String track = subObject.get("track").getAsString();
            int itype = 0;//未知
            if(
            		other_info.indexOf("亲戚") != -1 || track.indexOf("亲戚") != -1
            		|| other_info.indexOf("家属") != -1 || track.indexOf("家属") != -1
            		) {
            	itype = 1;//Relative
            }else if (
            		other_info.indexOf("无湖北") != -1 || track.indexOf("无湖北") != -1
            		|| other_info.indexOf("接触") != -1 || track.indexOf("接触") != -1
            		) {
            	itype = 2;//Social
            }else if (
            		track.indexOf("高铁") != -1 || track.indexOf("驾车") != -1
            		|| track.indexOf("飞机") != -1 || track.indexOf("旅游") != -1
            		|| track.indexOf("湖北") != -1 || track.indexOf("湖北") != -1
            		) {
            	itype = 3;//Travel
            }
            subObject.addProperty("i_infect_type", itype);
        }
	}
	
	public static String[] parseStrInfo(String text, String spliter) {
		String t_arr[] = text.split(spliter);
		return t_arr;
	}
	
	public String parseDate(String src) {
		String ret = "2020-" + src.substring(0, src.indexOf("月")) + "-" + src.substring(src.indexOf("月") + 1, src.indexOf("日"));
		return ret;
	}
	
	public static void printStrArr(String arr[]) {
		for (int i=0;i<arr.length;i++) {
			System.out.println(arr[i]);
		}
	}
	
	public void printIntArr(int arr[][]) {
		for (int i=0;i<arr.length;i++) {
			System.out.println("------------------------");
			for (int j=0;j<arr[i].length;j++) {
				System.out.println(arr[i][j]);
			}
		}
	}
}
