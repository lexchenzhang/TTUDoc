package com.ttu.config;

public class Config {
	
	public static String FilePathStr = "/Users/lex/TTU/PaperRelated/DataSets/ProcessedDataSets/CVData";
	
	public static String provinceArr[] = {
			"SiChuan","ZheJiang","GanSu","AnHui","XinJiang","JiangXi","ShanXi","FuJian","GuangXi","ChongQing",
			"GuiZhou","HeNan","NeiMonggol","ShaanXi","JiangSu","HeiLongJiang","ShanDong","BeiJing","GuangDong",
			"TianJin","JiLin","HaiNan","Hongkong","ShangHai","YunNan","NingXia","HeBei","HuNan","LiaoNing"
	};
	
	public static String[] p_name = {
			"Anhui",//1
			"Beijing",//2
			"Chongqing",//3
			"Fujian",//4
			"Gansu",//5
			"Guangdong",//6
			"Guangxi",//7
			"Guizhou",//8
			"Hainan",//9
			"Hebei",//10
			"Heilongjiang",//11
			"Henan",//12
			"Hubei",//13
			"Hunan",//14
			"Jiangsu",//15
			"Jiangxi",//16
			"Jilin",//17
			"Liaoning",//18
			"Neimenggu",//19
			"Ningxia",//20
			"Qinghai",//21
			"Shaanxi",//22
			"Shandong",//23
			"Shanghai",//24
			"Shanxi",//25
			"Sichuan",//26
			"Tianjin",//27
			"Xinjiang",//28
			"Xizang",//29
			"Yunnan",//30
			"Zhejiang"//31
	};
	
//	public static int section_num[][] = {
//	{1,3,11,4,61,4,3,6,2,5,3,3,3,4,2,2,7,3,3,8,3,11,7,3,1,4,1,4,1,2,1,2,1,2,2,2,43,1,4,1,4,1,1,1,4,7,5,1,1,1,1,2,1,2,1,2,2,2,1,2,1,4,2,3,1,1,1,2,2,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
//	{3,8,32,4,4,6,2,1,6,46,3,4,1,5,2,33,11,8,4,8,2,4,2,2,2,2,5,1,1,3,1,1,4,1,1,2,2,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
//	{20,7,2,8,1,1,4,3,3,1,1,1,1,9,1,1,1,2,1,5,2,1,1,1,1,1,1,2,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
//	{1,8,4,9,10,3,8,6,9,3,2,3,4,6,10,1,21,12,17,18,2,2,3,3,4,1,1,3,3,4,1,2,1,2,6,2,1,1,2,1,1,1,5,2,1,2,3,1,1,1,1,3,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
//	{4,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
//	{4,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
//	{34,3,3,3,4,10,2,7,3,7,1,6,2,8,3,2,1,2,3,1,1,1,1,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
//	{2,1,2,1,2,7,1,1,5,2,1,1,4,1,10,3,2,2,2,7,4,1,1,1,4,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
//	{12,5,2,9,4,9,17,3,1,7,2,1,2,1,4,1,1,1,1,1,1,2,1,1,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
//	{391,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
//	{1,6,10,24,3,4,8,17,2,3,1,1,3,1,1,1,8,1,3,13,2,2,6,2,2,4,6,4,4,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
//	{4,22,4,1,2,3,3,3,1,11,9,6,1,12,1,2,1,2,2,4,2,2,1,8,3,2,2,17,8,5,7,4,3,1,4,2,8,4,19,2,1,1,16,21,4,11,5,10,10,7,6,5,2,1,2,2,21,2,2,9,8,11,15,7,2,4,23,8,3,2,9,2,4,3,5,5,2,3,1,1,1,2,6,1,2,9,3,1,2,1,1,2,2,11,1,1,1,1,1,3,1,1,1,1,3,1,1,1,2,0},
//	{1,1,1,5,1,5,2,1,1,6,1,1,2,1,2,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
//	{2,1,5,5,2,2,1,6,2,3,5,1,1,1,4,2,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
//	{26,22,1,1,2,1,1,8,2,1,1,1,1,1,1,1,3,1,1,8,1,8,24,16,3,2,3,3,1,2,1,3,4,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
//	{5,20,40,29,14,5,1,28,15,50,11,2,36,8,24,2,3,21,1,3,1,4,11,1,7,5,6,3,3,1,1,5,6,2,5,5,5,2,1,2,4,1,7,1,4,1,1,1,2,1,2,1,1,1,1,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
//	{1,13,3,1,11,2,2,7,3,1,1,10,5,6,1,2,6,2,2,3,2,8,1,1,4,1,6,2,2,3,3,2,2,1,1,3,13,1,3,3,3,5,2,1,1,5,1,1,2,2,1,2,1,3,1,2,3,2,5,2,1,1,3,1,1,1,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
//	{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
//	{1,5,1,1,20,1,2,14,4,1,1,1,2,22,2,11,5,5,1,1,1,2,3,3,5,4,5,1,4,1,3,2,3,7,2,1,3,1,2,2,2,2,2,3,2,1,3,2,2,1,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
//	{2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
//	{1,6,8,10,1,8,12,2,5,1,2,1,2,2,7,1,1,1,5,2,2,2,2,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
//	{167,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
//	{2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
//	{9,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
//	{2,3,1,4,3,1,1,4,2,1,1,1,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
//	{2,12,3,4,3,1,2,1,2,3,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
//	{7,3,30,3,11,11,2,13,3,5,6,4,2,1,20,1,6,3,6,12,1,2,11,4,2,2,7,2,1,4,1,1,3,1,5,11,3,1,1,1,2,11,4,2,1,1,3,7,3,5,1,3,1,1,1,1,6,2,2,1,1,1,1,1,1,1,1,2,2,1,2,1,1,2,2,1,3,1,1,4,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
//	{9,3,2,9,1,2,1,5,3,4,20,1,1,2,1,2,1,1,2,1,1,27,8,2,3,1,10,6,2,1,2,1,9,3,1,8,5,10,22,8,2,1,5,1,3,1,6,3,2,1,11,4,1,4,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
//	{3,1,4,2,8,2,1,3,1,1,3,3,2,5,6,1,1,2,1,1,2,3,1,2,2,1,1,2,2,3,1,1,4,1,1,2,4,1,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
//	{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}
//};

//public static String section_label[][] = {
//	{"昭觉县","道孚县人","开江县","安岳县","道孚县","温江区","大邑县","泸县","新都区","阆中市","顺庆区","叙州区","市东区","西昌市","锦江区","蓬溪县","金堂县","天府新区","西充县","武侯区","金牛区","巴州区","渠县","大竹县","青川县","绵竹市","雨城区","龙马潭区","隆昌市","宣汉县","东兴区","岳池县","仁寿县","江阳区","高坪区","古蔺县","","恩阳区","射洪县","南部县","旌阳区","武胜县","雁江区","新津县","龙泉驿区","广安区","江油市","翠屏区","筠连县","江安县","珙县","高新区","简阳市","仪陇县","万源市","天全县","色达县","东坡区","营山县","市中区","威远县","苍溪县","芦山县","广汉市","石棉县","嘉陵区","通川区","康定市","蓬安县","甘洛县","稻城县"},
//	{"临海市","宁海县","","桐庐县","江干区","余杭区","上城区","萧山区","慈溪市","海曙区","北仑区","余姚市","鄞州区","鹿城区","龙湾区","乐清市","瑞安市","永嘉县","文成县","平阳县","泰顺县","秀洲区","义乌市","浦江县","江山市","椒江区","温岭市","三门县","天台县","仙居县","拱墅区","西湖区","江北区","洞头区","吴兴区","南浔区","东阳市","黄岩区","青田县","庆元县"},
//	{"城关区","华亭市","","西固区","庄浪县","庆城县","七里河区","合作市","陇西县","皋兰县","榆中县","安宁区","红古区","麦积区","临潭县","迭部县","卓尼县","白银区","靖远县","未知","宁县","秦州区","崇信县","通渭县","清水县","甘州区","张家川回族自治县","民乐县","金川区"},
//	{"无为市","和县","包河区","龙子湖区","颍上县","霍邱县","肥东县","蜀山区","瑶海区","庐阳区","新站区","怀远县","蚌山区","禹会区","涡阳县","田家庵区","","阜南县","临泉县","利辛县","巢湖市","蒙城县","埇桥区","砀山县","颍州区","东至县","淮上区","金寨县","泗县","颍东区","无为县","义安区","怀宁县","望江县","相山区","谯城区","枞阳县","宁国市","霍山县","天长市","肥西县","颍泉区","庐江县","濉溪县","烈山区","灵璧县","雨山区","八公山区","潘集区","含山县","宜秀区","桐城市","岳西县","迎江区","潜山市"},
//	{"未知","库尔勒市"},
//	{"大余县"},
//	{"平遥县","晋源区","城区","朔城区","杏花岭区","尖草坪区","迎泽区","五台县","平陆县","平城区","清徐县","沁水县","盂县","未知","小店区","吉县","河津市","泽州县","应县","怀仁市","阳城县","北石店镇","陵川县","浑源县","天镇县","忻府区"},
//	{"荔城区","翔安区","鼓楼区","三元区","永安市","沙县","尤溪县","将乐县","古田县","长泰县","宁化县","建阳区","松溪县","海沧区","连江县","新罗区","永定区","闽侯县","城厢区","长乐区","","武夷山市","丰泽区","芗城区","思明区","集美区"},
//	{"都安县","西乡塘区","临桂区","港口区","玉州区","马山县","","柳南区","鱼峰区","青秀区","柳北区","恭城县","桂平市","南丹县","兴宾区","良庆区","七星区","象州县","邕宁区","柳东新区","港北区","都安瑶族自治县","兴安县","海城区","平南县","鹿寨县","大化瑶族自治县"},
//	{""},
//	{"清镇市","云岩区","汇川区","道真仡佬族苗族自治县","观山湖区","","南明区","未知","花溪区","玉屏侗族自治县","石阡县","碧江区","凯里市","都匀市","惠水县","长顺县","贵定县","织金县","凤冈县","黔西县","钟山区","江口县","天柱县","白云区","仁怀市","七星关区","大方县","纳雍县","赫章县","锦屏县"},
//	{"殷都区","文峰区","内黄县","滑县","林州市","北关区","安阳县","汤阴县","龙安区","建安区","魏都区","襄城县","禹州市","长垣县","长葛市","渑池县","灵宝市","义马市","获嘉县","卫辉市","卫滨区","封丘县","红旗区","辉县市","延津县","原阳县","牧野区","平桥区","浉河区","商城县","潢川县","光山县","方城县","淅川县","华龙区","南乐县","中原区","虞城县","梁园区","确山县","漯河市","新安县","新华区","罗山县","川汇区","西华县","郸城县","太康县","沈丘县","淮阳区","项城市","鹿邑县","商水县","城乡一体化示范区","淮阳县","武陟县","柘城县","民权县","睢县","睢阳区","宁陵县","夏邑县","永城市","解放区","西平县","惠济区","金水区","管城回族区","尉氏县","鹤山区","山阳区","济源市","固始县","息县","中牟县","兰考县","老城区","涧西区","马村区","沁阳市","温县","瀍河回族区","未知","偃师市","新县","卫东区","鲁山县","清丰县","汝南县","新蔡县","驿城区","上蔡县","宛城区","二七区","新乡县","西工区","洛龙区","信阳市","登封市","通许县","嵩县","宝丰县","湛河区","叶县","杞县","荥阳市","上街区","新郑市","巩义市"},
//	{"松山区","翁牛特旗","锡林浩特市","乌拉特中旗","东河区","土默特右旗","玉泉区","临河区","回民区","达拉特旗","化德县","海勃湾区","未知","五原县","新城区","包头市"},
//	{"灞桥区","汉滨区","洋县","雁塔区","碑林区","旬阳县","富平县","临渭区","临潼区","未央区","新城区","周至县","汉阴县","蓝田县","未知","鄠邑区","高陵区"},
//	{"睢宁县","","盐南新区","丰县","泗阳县","沭阳县","经开区","邳州市","建邺区","溧水区","雨花台区","栖霞区","江宁区","滨海县","盐都区","大丰区","云龙区","泉山区","通州区","吴江区","京口区","吴中区","工业园区","昆山市","常熟市","太仓市","高新区","沛县","武进区","天宁区","贾汪区","相城区","姑苏区"},
//	{"西安区","道外区","南岗区","五常市","让胡路区","勃利县","东安区","道里区","绥棱县","友谊县","建华区","阳明区","香坊区","方正县","恒山区","肇源县","鸡冠区","尚志市","红岗区","宝清县","金山屯区","兰西县","桃山区","拜泉县","肇州县","五大连池市","北林区","鸡东县","甘南县","讷河市","龙凤区","阿城区","桦川县","向阳区","富锦市","依安县","龙沙区","龙江县","前进区","工农区","爱民区","肇东市","巴彦县","兴安区","安达市","木兰县","双城市","尖山区","四方台区","集贤县","庆安县","松北区","梅里斯达斡尔族区","双城区","新兴区","麻山区","密山区","海林市"},
//	{"徂汶景区","泰山区","张店区","沂水县","市北区","市南区","莒南县","芝罘区","昌乐县","邹平市","高密市","任城区","武城县","淄川区","东港区","滕州市","平邑县","坊子区","诸城市","无棣县","博山区","历下区","文登区","郯城县","阳谷县","郓城县","黄岛区","山亭区","牟平区","海阳市","奎文区","曲阜市","岱岳区","临港区","乳山市","兰山区","东昌府区","牡丹区","招远市","莱西市","李沧区","新泰市","环翠区","台儿庄区","德城区","长清区","章丘区","平阴县","天桥区","莱阳市","历城区","肥城市","宁阳县","即墨区","巨野县","市中区","东平县","槐荫区","福山区","峄城区","茌平县","邹平县","莱州市","乐陵市","薛城区","滨城区","胶州市","城阳区","惠民县"},
//	{""},
//	{"阳春市","端州区","沙田镇","大亚湾区","惠东县","潮安区","乐昌市","金湾区","惠阳区","端州市","高州市","三角镇","源城区","香洲区","台山市","惠城区","斗门区","封开县","恩平市","新丰县","信宜市","电白区","沙溪镇","三水区","南海区","顺德区","石岐区","阜沙镇","黄圃镇","大岭山镇","蓬江区","普宁市","鹤山市","江海区","南澳县","潮南区","虎门镇","东源县","新会区","陆丰县","三乡镇","赤坎区","鼎湖区","陆河县","坡头区","揭东区","坦洲镇","博罗县","塘厦镇","石碣镇","东坑镇","望牛墩镇","开平市"},
//	{"天津市"},
//	{"集安市","双辽市","朝阳区","二道区","东辽县","铁西区","南关区","宽城区","公主岭市","梅河口市","东昌区","德惠市","九台区","绿园区","","镇赉县","铁东区","龙井市","龙山区","延吉市","丰满区","东丰县","乾安县","昌邑区","和龙市","图们市"},
//	{"","琼山区","天涯区"},
//	{""},
//	{""},
//	{"蒙自市","","绿春县","未知","五华区","红塔区","官渡区","腾冲市","盘龙区","瑞丽市","景洪市","江川区","弥渡县","易门县","文山市"},
//	{"红寺堡区","同心县","隆德县","青铜峡市","西夏区","中宁县","兴庆区","永宁县","利通区","金凤区"},
//	{"张北县","魏县","迁安市","长安区","康保县","南皮县","威县","迁西县","正定县","献县","固安县","丛台区","易县","卢龙县","河间市","临城县","任丘市","冀南新区","霸州市","宣化区","滦州市","抚宁区","桥西区","竞秀区","高阳县","莲池区","文安县","阜平县","望都县","尚义县","肃宁县","黄骅市","运河区","泊头市","香河县","涿州市","海港区","北戴河区","双桥区","内丘县","邢台县","临漳县","路北区","路南区","玉田县","永年区","磁县","遵化市","围场满族蒙古族自治县","裕华区","新华区","邯山区","峰峰矿区","曲周","大名县","成安县","清河县","宁晋县","桃城区","安平县","深州市","枣强县","柏乡县","广宗县","宽城满族自治县","丰宁满族自治县","广阳区","大城县","三河市","永清县","藁城区","赵县","高邑县","安国市","徐水区","高碑店","高碑店市","万全区","崇礼区","隆尧县","新河县","临西县","巨鹿县"},
//	{"茶陵县","安化县","安仁县","安乡县","北湖区","茶陵","辰溪县","鼎城区","芙蓉区","汉寿县","荷塘区","赫山区","衡阳市","华容县","嘉禾县","津市市","靖州苗族侗族自治县","君山区","开福区","冷水江市","冷水滩区","澧县","醴陵市","涟源市","临澧县","临武县","芦淞区","南县","祁东县","祁阳县","汝城县","韶山市","石峰区","石门县","双峰县","苏仙区","桃源县","天元区","武陵区","湘潭县","湘乡市","湘阴县","新化县","新田县","宜章县","永兴县","攸县","雨湖区","沅江市","岳麓区","岳塘区","岳阳楼区","岳阳县","云溪区","资兴市"},
//	{"昌图县","宽甸满族自治县","调兵山市","苏家屯区","义县","彰武县","浑南区","黑山县","新民市","甘井子区","辽阳县","南票区","沙河口区","沈河区","铁西区","沈北新区","于洪区","台安县","振安区","凤城市","细河区","双台子区","法库县","海州区","连山区","龙港区","兴城市","大洼区","凌海","大东区","皇姑区","岫岩满族自治县","凌源市","双塔区","朝阳县","东港市","宽甸县","辽中区","平山区","古塔区","西丰县"}
//};
}
