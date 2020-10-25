package com.ttu.main;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.*;
import com.ttu.config.Config;
import com.ttu.parser.covid19.GetAffectionTypeByDate;
import com.ttu.parser.covid19.GetAffectionTypeDetailsByDate;
import com.ttu.parser.covid19.GetDemoData;
import com.ttu.parser.covid19.GetIncubationData;

import java.io.*;

public class main {
	
	public static int section_counter[][] = new int[30][110];
	
	public static void main(String[] args) {
//		doTravelBool();
//		genTreeJson();
//		getNumbers();
//		printSymptoms();
//		printSourceWord();
//		Date d = new Date(2020,02,13);
//		System.out.println(d.getDate());
//		parseSectionJson();
//		getAffectionTypeDetailsByDate();
		genTreeJson();
	}
	
	public static void getAffectionTypeDetailsByDate() {
		GetAffectionTypeDetailsByDate m_gatbd = new GetAffectionTypeDetailsByDate();
		m_gatbd.run();
	}
	
	public static void getAffectionTypeByDate() {
		GetAffectionTypeByDate m_gatbd = new GetAffectionTypeByDate();
		m_gatbd.run();
	}
	
	public static void getIncubationData() {
		GetIncubationData m_gid = new GetIncubationData();
		m_gid.run();
	}
	
	public static void getDemoData() {
		GetDemoData m_gdd = new GetDemoData();
		m_gdd.run();
	}
	
	public static void genTreeJson() {
		File file = new File(Config.FilePathStr);
		File[] fs = file.listFiles();
		JsonObject root = new JsonObject();
		JsonObject p_hubei = new JsonObject();
		int counter = 0;
		
		p_hubei.addProperty("id", "HuBei");
		root.add("data", p_hubei);
		
		JsonArray array = new JsonArray();
		root.add("children", array);
		int t_counter = 0;
		
		for(File f:fs){
			if(!f.isDirectory() && f.toString().indexOf("DS")==-1) {
				String province_name = f.toString().substring(f.toString().lastIndexOf("/")+1, f.toString().lastIndexOf("."));
				
				JsonObject p_province = new JsonObject();
				JsonObject p_child = new JsonObject();
				p_province.addProperty("id", Config.provinceArr[counter]);
				p_child.add("data", p_province);
				JsonArray sub_array = new JsonArray();
				p_child.add("children", sub_array);
				array.add(p_child);
				
				for (int i=0;i<Config.section_label[counter].length;i++) {
					JsonObject p_pationt = new JsonObject();
        			p_pationt.addProperty("id", t_counter%10==0?Config.section_label[counter][i]:"");
        			JsonObject p_sub_child = new JsonObject();
        			p_sub_child.add("data", p_pationt);
        			JsonArray x_array = new JsonArray();
        			p_sub_child.add("children", x_array);
        			
        			for (int j=0;j<Config.section_num[counter][i];j++) {
        				JsonObject x_pationt = new JsonObject();
    					x_pationt.addProperty("id", "case["+j+"]");
    					JsonObject x_sub_child = new JsonObject();
    					x_sub_child.add("data", x_pationt);
    					x_array.add(x_sub_child);
        			}
        			
        			sub_array.add(p_sub_child);
        			t_counter++;
				}
		        
				counter++;
			}
		}
		
		main.Save(root, "TreeChartData.json");
	}
	
	public static void parseSectionJson() {
		File file = new File(Config.FilePathStr);
		File[] fs = file.listFiles();
		int counter = 0;
		for(File f:fs){
			if(!f.isDirectory() && f.toString().indexOf("DS")==-1) {
				System.out.println(Config.provinceArr[counter]);
				System.out.println(f.toString());
				ParseSectionData(f.toString());
				counter++;
			}
		}
	}
	
	public static void printSourceWord() {
		String[] labels = {"hostipal","public transit","supermarket","restaurant","shopping mall","nursing home","residential","family","hotel","airport","travel","unknown"};
		File file = new File(Config.FilePathStr);
		File[] fs = file.listFiles();
		for(File f:fs){
			if(!f.isDirectory() && f.toString().indexOf("DS")==-1) {
//				System.out.println(f.toString());
				ParseSourceWord(f.toString());
			}
		}
		for (int i=0;i<ParseJson.infect_source_counter.length;i++) {
			System.out.println(labels[i]+","+ParseJson.infect_source_counter[i]);
		}
	}
	
//	public static void getIncubationData() {
//		int t_arr[] = new int[30];
//		File file = new File(Config.FilePathStr);
//		File[] fs = file.listFiles();
//		for(File f:fs){
//			if(!f.isDirectory() && f.toString().indexOf("DS")==-1) {
//				int t_a[] = ParseIncubationData(f.toString());
//				for(int i=0;i<t_a.length;i++) {
//					t_arr[i] += t_a[i];
//				}
//			}
//		}
//		for(int i=0;i<t_arr.length;i++) {
//			System.out.println(t_arr[i]);
//		}
//	}
	
	public static void genNetMapData() {
		File file = new File(Config.FilePathStr);
		File[] fs = file.listFiles();
		int counter = 0;
		List<Map<String,String>> list = new ArrayList<Map<String,String>>();
		
		for(File f:fs){
			if(!f.isDirectory() && f.toString().indexOf("DS")==-1) {
				ParseNetMapData(counter, list, f.toString());
				counter++;
			}
		}
		
		LinkedHashMap map = new LinkedHashMap();
        map.put("1", "counter");
        map.put("2", "parent");
        map.put("3", "child");
        
        String path = "/Users/lex/Desktop/After/";
        String fileName = "overall.csv";
        CSVUtils.createCSVFile(list, map, path, fileName);
	}
	
	/**
	 * print all the symptoms for generate word cloud
	 */
	public static void printSymptoms() {
		File file = new File(Config.FilePathStr);
		File[] fs = file.listFiles();
		for(File f:fs){
			if(!f.isDirectory() && f.toString().indexOf("DS")==-1) {
//				System.out.println(f.toString());
				ParseSymptoms(f.toString());
			}
		}
	}
	
	public static void getNumbers() {
		int total_num = 0;
		File file = new File(Config.FilePathStr);
		File[] fs = file.listFiles();
		for(File f:fs){
			if(!f.isDirectory() && f.toString().indexOf("DS")==-1) {
				System.out.println(f.toString());
				int num = ParseNumber(f.toString());
				System.out.println(num);
				total_num += num;
			}
		}
		System.out.println(total_num);
	}
	
	public static void doTemplate() {
		String path = "/Users/lex/TTU/PaperRelated/DataSets/RawDataSets/疫情数据汇总省份";
		File file = new File(path);
		File[] fs = file.listFiles();
		for(File f:fs){
			if(!f.isDirectory() && f.toString().indexOf("DS")==-1) {
				ParseTemplateJson(f.toString());
			}
		}
	}
	
	public static void doGender() {
		int t_arr[][] = new int[Config.provinceArr.length][2];
		File file = new File(Config.FilePathStr);
		File[] fs = file.listFiles();
		int counter = 0;
		for(File f:fs){
			if(!f.isDirectory() && f.toString().indexOf("DS")==-1) {
				System.out.println(f.toString());
				int[] t_a = ParseGenderJson(f.toString());
				for(int i=0;i<t_a.length;i++) {
					t_arr[counter][i] += t_a[i];
				}
				for(int i=0;i<t_a.length;i++) {
					System.out.println(t_a[i]);
				}
				counter++;
			}
		}
		for(int i=0;i<t_arr.length;i++) {
//			System.out.println(t_arr[i]);
		}
		for(int i=0;i<t_arr.length;i++) {
			System.out.print(Config.provinceArr[i]+",");
			for(int j=0;j<t_arr[i].length;j++) {
				System.out.print(t_arr[i][j]+",");
			}
			System.out.println();
		}
	}
	
	public static void doTestAge() {
		File file = new File("/Users/lex/TTU/PaperRelated/DataSets/RawDataSets/疫情数据汇总省份");
		File[] fs = file.listFiles();
		for(File f:fs){
			if(!f.isDirectory() && f.toString().indexOf("DS")==-1) {
				System.out.println(f.toString());
				ParseAgeJson(f.toString());
			}
		}
	}
	
	public static void doAge() {
		int t_arr[][] = new int[Config.provinceArr.length][100];
		int range_arr[][] = new int[Config.provinceArr.length][10];
		File file = new File(Config.FilePathStr);
		File[] fs = file.listFiles();
		int counter = 0;
		for(File f:fs){
			if(!f.isDirectory() && f.toString().indexOf("DS")==-1) {
				System.out.println(f.toString());
				int[] t_a = ParseAgeJson(f.toString());
				for(int i=0;i<t_a.length;i++) {
					t_arr[counter][i] += t_a[i];
				}
				counter++;
			}
		}
		for(int i=0;i<t_arr.length;i++) {
			for(int j=0;j<t_arr[i].length;j++) {
				range_arr[i][j/10] += t_arr[i][j];
			}
			System.out.println();
		}
		
		for(int i=0;i<range_arr.length;i++) {
			System.out.print(Config.provinceArr[i]+",");
			for(int j=0;j<range_arr[i].length;j++) {
				System.out.print(range_arr[i][j]+",");
			}
			System.out.println();
		}
	}
	
	public static void doTravelBool() {
		int t_arr[][] = new int[Config.provinceArr.length][2];
		File file = new File(Config.FilePathStr);
		File[] fs = file.listFiles();
		int counter = 0;
		for(File f:fs){
			if(!f.isDirectory() && f.toString().indexOf("DS")==-1) {
				System.out.println(f.toString());
				int[] t_a = ParseTravelBoolJson(f.toString());
				for(int i=0;i<t_a.length;i++) {
					t_arr[counter][i] += t_a[i];
				}
				for(int i=0;i<t_a.length;i++) {
					System.out.println(t_a[i]);
				}
				counter++;
			}
		}
		for(int i=0;i<t_arr.length;i++) {
//			System.out.println(t_arr[i]);
		}
		for(int i=0;i<t_arr.length;i++) {
			System.out.print(Config.provinceArr[i]+",");
			for(int j=0;j<t_arr[i].length;j++) {
				System.out.print(t_arr[i][j]+",");
			}
			System.out.println();
		}
	}
	
	public static void doAffectType() {
		int t_arr[][] = new int[Config.provinceArr.length][4];
		File file = new File(Config.FilePathStr);
		File[] fs = file.listFiles();
		int counter = 0;
		for(File f:fs){
			if(!f.isDirectory() && f.toString().indexOf("DS")==-1) {
				System.out.println(f.toString());
				int[] t_a = ParseAffectJson(f.toString());
				for(int i=0;i<t_a.length;i++) {
					t_arr[counter][i] += t_a[i];
				}
				for(int i=0;i<t_a.length;i++) {
//					System.out.println(t_a[i]);
				}
				counter++;
			}
		}
		for(int i=0;i<t_arr.length;i++) {
//			System.out.println(t_arr[i]);
		}
		for(int i=0;i<t_arr.length;i++) {
			System.out.print((i+1)+","+Config.provinceArr[i]+",");
			for(int j=0;j<t_arr[i].length;j++) {
				System.out.print(t_arr[i][j]+",");
			}
			System.out.println();
		}
	}
	
	public static void doConfirmJson() {
		int t_arr[][] = new int[13][32];
		String path = "/Users/lex/Desktop/After";		//要遍历的路径
		File file = new File(path);		//获取其file对象
		File[] fs = file.listFiles();	//遍历path下的文件和目录，放在File数组中
		for(File f:fs){					//遍历File[]数组
			if(!f.isDirectory() && f.toString().indexOf("DS")==-1) {
				int[][] t_a = ParseConfirmJson(f.toString());
				for(int i=0;i<t_a.length;i++) {
					for(int j=0;j<t_a[i].length;j++) {
						t_arr[i][j] += t_a[i][j];
					}
				}
			}
		}
		SaveOverallConfirmData(t_arr);
	}
	
//	public static void genTravelTreeJson() {
//		File file = new File(FilePathStr);
//		File[] fs = file.listFiles();
//		JsonObject root = new JsonObject();
//		JsonObject p_hubei = new JsonObject();
//		int counter = 0;
//		
//		p_hubei.addProperty("id", "HuBei");
//		root.add("data", p_hubei);
//		
//		JsonArray array = new JsonArray();
//		root.add("children", array);
//		
//		for(File f:fs){
//			if(!f.isDirectory() && f.toString().indexOf("DS")==-1) {
//				String province_name = f.toString().substring(f.toString().lastIndexOf("/")+1, f.toString().lastIndexOf("."));
//				ParseTreeChartJson(array, f.toString(), province_name, counter);
//				counter++;
//			}
//		}
//		
//		main.Save(root, "TreeChartData.json");
//		System.out.println(ParseJson.travel_total_num);
//	}
	
	public static void ParseAllJsonFiles() {
		String path = "/Users/lex/Desktop/target";		//要遍历的路径
		File file = new File(path);		//获取其file对象
		File[] fs = file.listFiles();	//遍历path下的文件和目录，放在File数组中
		for(File f:fs){					//遍历File[]数组
			if(!f.isDirectory() && f.toString().indexOf("DS")==-1)		//若非目录(即文件)，则打印
				ParseJson(f.toString());
		}
	}
	
	public static void ParseTemplateJson(String file) {
		JsonParser parser = new JsonParser();
	    try {
	        JsonObject object = (JsonObject) parser.parse(new FileReader(file));
	        ParseJson pj = new ParseJson();
	        pj.getTemplate(object);
	        main.Save(object, file);
	    } catch (JsonIOException | JsonSyntaxException | FileNotFoundException e) {
	        e.printStackTrace();
	    }
	}
	
	public static void ParseNetMapData(int counter, List<Map<String,String>> list, String file) {
		JsonParser parser = new JsonParser();
	    try {
	        JsonObject object = (JsonObject) parser.parse(new FileReader(file));
	        ParseJson pj = new ParseJson();
	        pj.genNetMapData(counter, list, object);
	    } catch (JsonIOException | JsonSyntaxException | FileNotFoundException e) {
	        e.printStackTrace();
	    }
	}
	
	public static void ParseSectionData(String file) {
		JsonParser parser = new JsonParser();
	    try {
	        JsonObject object = (JsonObject) parser.parse(new FileReader(file));
	        ParseJson pj = new ParseJson();
	        pj.genSectionData(object);
	    } catch (JsonIOException | JsonSyntaxException | FileNotFoundException e) {
	        e.printStackTrace();
	    }
	}
	
	public static int[] ParseIncubationData(String file) {
		int[] t_arr = null;
		JsonParser parser = new JsonParser();
	    try {
	        JsonObject object = (JsonObject) parser.parse(new FileReader(file));
	        ParseJson pj = new ParseJson();
	        t_arr = pj.genIncubationData(object);
	    } catch (JsonIOException | JsonSyntaxException | FileNotFoundException e) {
	        e.printStackTrace();
	    }
	    return t_arr;
	}
	
	public static void ParseSourceWord(String file) {
		JsonParser parser = new JsonParser();
	    try {
	        JsonObject object = (JsonObject) parser.parse(new FileReader(file));
	        ParseJson pj = new ParseJson();
	        pj.getSourceWord(object);
	    } catch (JsonIOException | JsonSyntaxException | FileNotFoundException e) {
	        e.printStackTrace();
	    }
	}
	
	public static void ParseSymptoms(String file) {
		JsonParser parser = new JsonParser();
	    try {
	        JsonObject object = (JsonObject) parser.parse(new FileReader(file));
	        ParseJson pj = new ParseJson();
	        pj.getSymptoms(object);
	    } catch (JsonIOException | JsonSyntaxException | FileNotFoundException e) {
	        e.printStackTrace();
	    }
	}
	
	public static int ParseNumber(String file) {
		int num = 0;
		JsonParser parser = new JsonParser();
	    try {
	        JsonObject object = (JsonObject) parser.parse(new FileReader(file));
	        ParseJson pj = new ParseJson();
	        num = pj.getNumbers(object);
	    } catch (JsonIOException | JsonSyntaxException | FileNotFoundException e) {
	        e.printStackTrace();
	    }
	    return num;
	}
	
	public static int[] ParseGenderJson(String file) {
		int[] t_arr = null;
		JsonParser parser = new JsonParser();
	    try {
	        JsonObject object = (JsonObject) parser.parse(new FileReader(file));
	        ParseJson pj = new ParseJson();
	        t_arr = pj.getGenderArray(object);
	    } catch (JsonIOException | JsonSyntaxException | FileNotFoundException e) {
	        e.printStackTrace();
	    }
	    return t_arr;
	}
	
	public static int[] ParseAgeJson(String file) {
		int[] t_arr = null;
		JsonParser parser = new JsonParser();
	    try {
	        JsonObject object = (JsonObject) parser.parse(new FileReader(file));
	        ParseJson pj = new ParseJson();
	        t_arr = pj.getAgeArray(object);
//	        pj.getTestAge(object);
	    } catch (JsonIOException | JsonSyntaxException | FileNotFoundException e) {
	        e.printStackTrace();
	    }
	    return t_arr;
	}
	
	public static void ParseJson(String file) {
		System.out.println(file);
		JsonParser parser = new JsonParser();
	    try {
	        JsonObject object = (JsonObject) parser.parse(new FileReader(file));
	        ParseJson pj = new ParseJson();
	        pj.addTravelHubeiBool(object);
	        pj.addInfectType(object);
	        pj.addComfirmDate(object);
	        main.Save(object, file);
	    } catch (JsonIOException | JsonSyntaxException | FileNotFoundException e) {
	        e.printStackTrace();
	    }
	}
	
	public static int[] ParseTravelBoolJson(String file) {
		int[] t_arr = null;
		JsonParser parser = new JsonParser();
	    try {
	        JsonObject object = (JsonObject) parser.parse(new FileReader(file));
	        ParseJson pj = new ParseJson();
	        t_arr = pj.getTravelBool(object);
	    } catch (JsonIOException | JsonSyntaxException | FileNotFoundException e) {
	        e.printStackTrace();
	    }
	    return t_arr;
	}
	
	public static int[] ParseAffectJson(String file) {
		int[] t_arr = null;
		JsonParser parser = new JsonParser();
	    try {
	        JsonObject object = (JsonObject) parser.parse(new FileReader(file));
	        ParseJson pj = new ParseJson();
	        t_arr = pj.getAffectArray(object);
	    } catch (JsonIOException | JsonSyntaxException | FileNotFoundException e) {
	        e.printStackTrace();
	    }
	    return t_arr;
	}
	
	public static int[][] ParseConfirmJson(String file) {
		int[][] t_arr = null;
		JsonParser parser = new JsonParser();
	    try {
	        JsonObject object = (JsonObject) parser.parse(new FileReader(file));
	        ParseJson pj = new ParseJson();
	        t_arr = pj.getConfirmArray(object);
	    } catch (JsonIOException | JsonSyntaxException | FileNotFoundException e) {
	        e.printStackTrace();
	    }
	    System.out.println(summation(t_arr));
	    return t_arr;
	}
	
//	public static void ParseTreeChartJson(JsonArray arr, String file, String province, int counter) {
//		JsonParser parser = new JsonParser();
//	    try {
//	        JsonObject object = (JsonObject) parser.parse(new FileReader(file));
//	        ParseJson pj = new ParseJson();
//	        pj.genTreeChart(arr, object, province, counter);
//	    } catch (JsonIOException | JsonSyntaxException | FileNotFoundException e) {
//	        e.printStackTrace();
//	    }
//	}
	
	public static int summation(int[][] arr) {
		int num = 0;
		for(int i=0;i<arr.length;i++) {
			for(int j=0;j<arr[i].length;j++) {
				num += arr[i][j];
			}
		}
		return num;
	}
	
	public static void ParseExcel() {
		try {
			String filepath = "/Users/lex/Desktop/weibo/file_1.xlsx";
			ExcelParser excelReader = new ExcelParser(filepath);
			
			Map<Integer, Map<Integer,Object>> map = excelReader.readExcelContent();
			JsonObject obj = new JsonObject();
			JsonArray arr = new JsonArray();
			for (int i = 1; i <= map.size(); i++) {
//				JsonObject lan1 = new JsonObject();
//			    lan1.addProperty("name", map.get(i).get(0).toString());
//			    lan1.addProperty("text", map.get(i).get(1).toString());
//			    lan1.addProperty("time", map.get(i).get(2).toString());
//			    lan1.addProperty("m_forward_num", map.get(i).get(3).toString());
//			    lan1.addProperty("m_common_num", map.get(i).get(4).toString());
//			    lan1.addProperty("m_like_num", map.get(i).get(5).toString());
//			    lan1.addProperty("from", map.get(i).get(6).toString());
//			    lan1.addProperty("url_icon", map.get(i).get(7).toString());
//			    lan1.addProperty("title", map.get(i).get(8).toString());
//			    arr.add(lan1);
				
				String text = map.get(i).get(1).toString();
				if(text.indexOf("】") != -1) {
					
					JsonObject lan1 = new JsonObject();
				    lan1.addProperty("name", map.get(i).get(0).toString());
				    lan1.addProperty("text", map.get(i).get(1).toString());
				    lan1.addProperty("time", map.get(i).get(2).toString());
				    lan1.addProperty("m_forward_num", map.get(i).get(3).toString());
				    lan1.addProperty("m_common_num", map.get(i).get(4).toString());
				    lan1.addProperty("m_like_num", map.get(i).get(5).toString());
				    lan1.addProperty("from", map.get(i).get(6).toString());
				    lan1.addProperty("url_icon", map.get(i).get(7).toString());
				    lan1.addProperty("title", map.get(i).get(8).toString());
				    
				    JsonObject detail = new JsonObject();
					
					String[] parse_arr = parseStrInfo(text);
					for(int j=0;j<parse_arr.length;j++) {
						if (parse_arr[j].indexOf("姓名") != -1) {
							if (parse_arr[j].length() < parse_arr[j].indexOf("】") + 2)
								break;
							detail.addProperty("name", trimEnding(parse_arr[j]));
						}else if (parse_arr[j].indexOf("年龄") != -1) {
							if (parse_arr[j].length() < parse_arr[j].indexOf("】") + 2)
								break;
							detail.addProperty("age", trimEnding(parse_arr[j]));
						}else if (parse_arr[j].indexOf("城市") != -1) {
							if (parse_arr[j].length() < parse_arr[j].indexOf("】") + 2)
								break;
							detail.addProperty("city", trimEnding(parse_arr[j]));
						}else if (parse_arr[j].indexOf("小区") != -1) {
							if (parse_arr[j].length() < parse_arr[j].indexOf("】") + 2)
								break;
							detail.addProperty("address", trimEnding(parse_arr[j]));
						}else if (parse_arr[j].indexOf("时间") != -1) {
							if (parse_arr[j].length() < parse_arr[j].indexOf("】") + 2)
								break;
							detail.addProperty("time", trimEnding(parse_arr[j]));
						}else if (parse_arr[j].indexOf("联系方式") != -1) {
							if (parse_arr[j].length() < parse_arr[j].indexOf("】") + 2)
								break;
							detail.addProperty("phone_num", trimEnding(parse_arr[j]));
						}else if (parse_arr[j].indexOf("联系人") != -1) {
							if (parse_arr[j].length() < parse_arr[j].indexOf("】") + 2)
								break;
							detail.addProperty("contact", trimEnding(parse_arr[j]));
						}else if (parse_arr[j].indexOf("描述") != -1) {
							if (parse_arr[j].length() < parse_arr[j].indexOf("】") + 2)
								break;
							detail.addProperty("des", trimEnding(parse_arr[j]));
						}
					}
					
					lan1.add("detail", detail);
					
					arr.add(lan1);
					
				}
				
			}
			obj.add("list", arr);
		} catch (FileNotFoundException e) {
			System.out.println("未找到指定路径的文件!");
			e.printStackTrace();
		}catch (Exception e) {
			e.printStackTrace();
		}
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
	
	public static void Save(JsonObject o, String file) {
		String f_name = file.substring(file.lastIndexOf("/")+1);
		f_name = "A_" + f_name;
//		System.out.println(f_name);
		FileWriter fileWriter;
		try {
			fileWriter = new FileWriter("/Users/lex/Desktop/After/"+f_name);
			Gson gson = new Gson();
			JsonElement e = gson.fromJson(o, JsonElement.class);
			gson.toJson(e, fileWriter);
			fileWriter.close();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	public static void SaveOverallConfirmData(int[][] t_arr) {
		List exportData = new ArrayList<Map>();
		
		for(int i=0;i<t_arr.length;i++) {
			for(int j=0;j<t_arr[i].length;j++) {
				if(t_arr[i][j]!=0) {
					Map row1 = new LinkedHashMap<String, String>();
					String t_date = "2020-"+i+"-"+j;
					row1.put("1", t_date);
			        row1.put("2", t_arr[i][j]);
			        exportData.add(row1);
				}
			}
		}
		
        LinkedHashMap map = new LinkedHashMap();
        map.put("1", "Time");
        map.put("2", "ConfirmCount");
        
        String path = "/Users/lex/Desktop/After/";
        String fileName = "overall.csv";
        File file = CSVUtils.createCSVFile(exportData, map, path, fileName);
        String fileName2 = file.getName();
	}
	
	public static String readJsonFile(String fileName) {
        String jsonStr = "";
        try {
            File jsonFile = new File(fileName);
            FileReader fileReader = new FileReader(jsonFile);

            Reader reader = new InputStreamReader(new FileInputStream(jsonFile),"utf-8");
            int ch = 0;
            StringBuffer sb = new StringBuffer();
            while ((ch = reader.read()) != -1) {
                sb.append((char) ch);
            }
            fileReader.close();
            reader.close();
            jsonStr = sb.toString();
            return jsonStr;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}
