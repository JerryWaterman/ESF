
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Scanner;

public class WQtoOracle{
	
	public static void main(String args[]) throws IOException{
	/*
	TO DO  read in file    12-08-08
	       next line \r\n   
	       save each to field name
	       if temp = ,,  add null for MySQL
	       write to output file, last line, last char need to be ";" 
	 
	       try{
    		File file = new File("C:\\Temp\\textfile.txt"); 
    		Scanner scanner = new Scanner(file);
    		while (scanner.hasNext()){
    			 System.out.println(scanner.next());
    			 String cLine= scanner.next(); 
   				 System.out.println("cLine = " + cLine);
    		}
    		scanner.close();
    	} catch (FileNotFoundException e){
    		e.printStackTrace();
          }
     -------------------------------F I E L D S------------------------------------     
	*/
    File file = new File("C:\\Temp\\FertilizerStudy1.txt");
    Scanner fileIn = new Scanner(file);
    //  -------------------------------F I E L D S------------------------------------
    /*       1           2                    3          4    5    6    7     8      9    10      11          12      13        14             15     16     17      18       19       20       21       22               
      SYSID,NUM       ESF_DATE     ,      ESF_TIME, ESF_DAY, ETD, TSD, THS, HIGHLM, LM2,  LM3,  LL_AVE, "LL_COND",  OUTLM,  PRECIPITATION,  RINTEN,  S1_DO, S1_PH, S1_COND, S1_TEMP, S1_ORP , S1_TURB, S1_FLOW,
              1,  20080405:00:01:02,        0     ,   0.00 ,0.00,    ,    ,0 0.69 ,1.01, 1.62,  1.32  ,  "DARK"  , -9.91 ,   1.979       ,  0.000 ,  10.52,  7.19, 260.47 ,  8.96  , 164.20 , 54.99  ,  29.02 ,
  23     24     25       26       27      28       29       30     31     32        33       34      35       36       37     38     39       40      41       42      43          
 S2_DO,S2_PH, S2_COND, S2_TEMP, S2_ORP, S2_TURB, S2_FLOW, S3_DO, S3_PH, S3_COND, S3_TEMP,  S3_ORP, S3_TURB, S3_FLOW, S4_DO, S4_PH, S4_COND, S4_TEMP, S4_ORP, S4_TURB, S4_FLOW,
10.55 , 7.69, 184.70,  9.82   , 208.33,  67.10 ,  28.29 , 10.43,  7.32, 181.98 , 9.14   ,  326.06, 105.22 ,  0.00  , 11.14,  7.43, -2.28  ,  9.18  , 166.00,  87.44 , 0.00   ,
  44    45      46       47      48       49       50       51     52     53        54       55       56       57      58     59      60      61       62      63       64
S5_DO, S5_PH, S5_COND, S5_TEMP, S5_ORP, S5_TURB, S5_FLOW, S6_DO, S6_PH, S6_COND,  S6_TEMP,  S6_ORP,	S6_TURB, S6_FLOW, S7_DO, S7_PH, S7_COND, S7_TEMP, S7_ORP, S7_TURB, S7_FLOW,
10.68,  5.55, -0.97  , 9.49   , 264.07, 78.03  , 0.00   , 11.20, 7.24 , 168.67 ,  10.00  ,  182.16,  68.27 , 0.00   ,10.13 , 8.20 , 110.29 , 10.25  , -2.32 , 44.75  ,  0.00  ,
  65       66    67       68        69      70       71       72    73      74       75       76     77
S8_DO,  S8_PH, S8_COND, S8_TEMP,  S8_ORP, S8_TURB, S8_FLOW, RW_DO, RW_PH, RW_COND, RW_TEMP, RW_ORP, RW_TURB 
10.25,  7.40 , 155.75 , 10.23  , 150.43 , 85.28  ,  0.00  , 11.49 ,7.03 , 166.33 ,  8.75  , 465.49,   41.40       ,,,,,,

*
****************************** 10/05/10**********************************
                                                               0    1     2        3        4     5   6   7    8     9   10   11     12      13     14           15     16    17    18      19      20     21      22      23    24    25      26      27     28      29      30    31    32      33      34     35       36       37     38     39       40       41      42       43       44     45     46       47       48      49       50       51     52     53        54        55        56      57       58     59     60       61       62      63        64     65    66    67      68      69     70      71      72    73    74      75      76     77               0                1     2                             3     4     5     6      7       8      9      10     11     12      13      14      15       16      17      18     19       20      21      22      23     24       25     26       27      28      29      30     31       32     33       34       35     36      37     38      39     40       41      42     43      44     45      46     47       48      49     50      51     52       53      54       55      56     57      58     59       60      61      62     63      64     65     66       67      68       69      70     71      72     73       74     75       76      77
                           insert into "ESF"."WaterQuality" (sysid,NUM,ESF_DATE,ESF_Time,ESF_Day,ETD,TSD,THS,HIGHLM,LM2,LM3,LL_AVE,LL_COND,OUTLM,PRECIPITATION,RINTEN,S1_DO,S1_PH,S1_COND,S1_TEMP,S1_ORP,S1_TURB,S1_FLOW,S2_DO,S2_PH,S2_COND,S2_TEMP,S2_ORP,S2_TURB,S2_FLOW,S3_DO,S3_PH,S3_COND,S3_TEMP,S3_ORP,S3_TURB, S3_FLOW, S4_DO, S4_PH, S4_COND, S4_TEMP, S4_ORP, S4_TURB, S4_FLOW, S5_DO, S5_PH, S5_COND, S5_TEMP, S5_ORP, S5_TURB, S5_FLOW, S6_DO, S6_PH, S6_COND,  S6_TEMP,  S6_ORP,  S6_TURB, S6_FLOW, S7_DO, S7_PH, S7_COND, S7_TEMP, S7_ORP, S7_TURB, S7_FLOW,S8_DO,S8_PH,S8_COND,S8_TEMP,S8_ORP,S8_TURB,S8_FLOW,RW_DO,RW_PH,RW_COND,RW_TEMP,RW_ORP,RW_TURB) values (sequence1.nextval,'3',timestamp '2008-04-05 00:01:02','0','0.00','0.00',null  ,null  ,'0.69','1.01','1.62','1.32','DARK','-9.91' ,'1.979','0.000','10.52','7.19','260.47','8.96','164.20','54.99','29.02','10.55','7.69','184.70','9.82','208.33','67.10','28.29','10.43','7.32','181.98','9.14','326.06','105.22','0.00','11.14','7.43','-2.28','9.18','166.00','87.44','0.00','10.68','5.55','-0.97','9.49','264.07','78.03','0.00','11.20','7.24','168.67','10.00','182.16','68.27','0.00','10.13','8.20','110.29','10.25','-2.32','44.75','0.00','10.25','7.40','155.75','10.23','150.43','85.28','0.00','11.49','7.03','166.33','8.75','465.49','41.40');

     * /
     */
    //                                                         0    1     2        3        4     5   6   7    8     9   10   11     12      13     14           15     16    17    18      19      20     21      22      23    24    25      26      27     28      29    30     31     32      33       34       35     36     37       38       39     40        41       42     43     44       45       46      47       48      49      50     51        52        53       54       55       56     57     58       59       60      61       62      63    64    65      66      67     68      69      70    71    72      73      74     75      76     77
    String insertfields = "insert into 'ESF'.'WaterQuality' (sysid,NUM,ESF_DATE,ESF_Time,ESF_Day,ETD,TSD,THS,HIGHLM,LM2,LM3,LL_AVE,LL_COND,OUTLM,PRECIPITATION,RINTEN,S1_DO,S1_PH,S1_COND,S1_TEMP,S1_ORP,S1_TURB,S1_FLOW,S2_DO,S2_PH,S2_COND,S2_TEMP,S2_ORP,S2_TURB,S2_FLOW,S3_DO,S3_PH,S3_COND,S3_TEMP,S3_ORP,S3_TURB, S3_FLOW, S4_DO, S4_PH, S4_COND, S4_TEMP, S4_ORP, S4_TURB, S4_FLOW, S5_DO, S5_PH, S5_COND, S5_TEMP, S5_ORP, S5_TURB, S5_FLOW, S6_DO, S6_PH, S6_COND,  S6_TEMP,  S6_ORP,  S6_TURB, S6_FLOW, S7_DO, S7_PH, S7_COND, S7_TEMP, S7_ORP, S7_TURB, S7_FLOW,S8_DO,S8_PH,S8_COND,S8_TEMP,S8_ORP,S8_TURB,S8_FLOW,RW_DO,RW_PH,RW_COND,RW_TEMP,RW_ORP,RW_TURB) values ";
  //         
    String insertdata   = null;
    while (fileIn.hasNext()){
	    String cLine= fileIn.nextLine();
	    String str = cLine;                                               //"2008-11-22,2006-07-15,esf";
	   	String vtemp = "";
	    String vs0  = "sequence1.nextval,";
	   	String vs1  = "null"; //NUM
	   	String vs2  = "null"; //ESF_DATE	   	//String vs2a = "null,"; //
	   	String vs3  = "null"; //ESF_Time
	   	String vs4  = "null"; //ESF_Day
	   	String vs5  = "null"; //ETD
	   	String vs6  = "null"; //TSD
	   	String vs7  = "null"; //THS
	   	String vs8  = "null"; //HIGHLM
	   	String vs9  = "null"; //LM2
	   	String vs10 = "null"; //LM3
	   	String vs11 = "null"; //LL_AVE
	   	String vs12 = "null"; //LL_COND
	   	String vs13 = "null"; //OUTLM
	   	String vs14 = "null"; //PRECIPITATION
	   	String vs15 = "null"; //RINTEN
	   	String vs16 = "null"; //S1_DO
	   	String vs17 = "null"; //S1_PH
	   	String vs18 = "null"; //S1_COND
	   	String vs19 = "null"; //S1_TEMP   	
	   	String vs20 = "null"; //S1_ORP
	   	String vs21 = "null"; //S1_TURB
	   	String vs22 = "null"; //S1_FLOW
	   	String vs23 = "null"; //S2_DO 
	   	String vs24 = "null"; //S2_PH
	   	String vs25 = "null"; //S2_COND
	   	String vs26 = "null"; //S2_TEMP 
	   	String vs27 = "null"; //S2_ORP 
	   	String vs28 = "null"; //S2_TURB
	   	String vs29 = "null"; //S2_FLOW
	   	String vs30 = "null"; //S3_DO 
	   	String vs31 = "null"; //S3_PH
	   	String vs32 = "null"; //S3_COND
	   	String vs33 = "null"; //S3_TEMP 
	   	String vs34 = "null"; //S3_ORP 
	   	String vs35 = "null"; //S3_TURB
	   	String vs36 = "null"; //S3_FLOW
	   	String vs37 = "null"; //S4_DO 
	   	String vs38 = "null"; //S4_PH
	   	String vs39 = "null"; //S4_COND
	   	String vs40 = "null"; //S4_TEMP 
	   	String vs41 = "null"; //S4_ORP 
	   	String vs42 = "null"; //S4_TURB
	   	String vs43 = "null"; //S4_FLOW
	   	String vs44 = "null"; //S5_DO 
	   	String vs45 = "null"; //S5_PH
	   	String vs46 = "null"; //S5_COND
	   	String vs47 = "null"; //S5_TEMP 
	   	String vs48 = "null"; //S5_ORP 
	   	String vs49 = "null"; //S5_TURB
	   	String vs50 = "null"; //S5_FLOW
		String vs51 = "null"; //S6_DO 
	   	String vs52 = "null"; //S6_PH
	   	String vs53 = "null"; //S6_COND
	   	String vs54 = "null"; //S6_TEMP 
	   	String vs55 = "null"; //S6_ORP 
	   	String vs56 = "null"; //S6_TURB
	   	String vs57 = "null"; //S6_FLOW
		String vs58 = "null"; //S7_DO 
	   	String vs59 = "null"; //S7_PH
	   	String vs60 = "null"; //S7_COND
	   	String vs61 = "null"; //S7_TEMP 
	   	String vs62 = "null"; //S7_ORP 
	   	String vs63 = "null"; //S7_TURB
	   	String vs64 = "null"; //S7_FLOW
		String vs65 = "null"; //S8_DO 
	   	String vs66 = "null"; //S8_PH
	   	String vs67 = "null"; //S8_COND
	   	String vs68 = "null"; //S8_TEMP 
	   	String vs69 = "null"; //S8_ORP 
	   	String vs70 = "null"; //S8_TURB
	   	String vs71 = "null"; //S8_FLOW
		String vs72 = "null"; //S_DO 
	   	String vs73 = "null"; //S_PH
	   	String vs74 = "null"; //S_COND
	   	String vs75 = "null"; //S_TEMP 
	   	String vs76 = "null"; //S_ORP 
	   	String vs77 = "null"; //S_TURB
	   	int vnext = 0;
	   	int v1  = 0;
	   	int v2  = 0;
	   	int v3  = 0;
	   	int v4  = 0;
	   	int v5  = 0;
	   	int v6  = 0;
	   	int v7  = 0;
	   	int v8  = 0;
	   	int v9  = 0;
	   	int v10 = 0;
	   	int v11 = 0;
	   	int v12 = 0;
	   	int v13 = 0;
	   	int v14 = 0;
	   	int v15 = 0;
	   	int v16 = 0;
	   	int v17 = 0;
	   	int v18 = 0;
	   	int v19 = 0;
	   	int v20 = 0;
	   	int v21 = 0;
	   	int v22 = 0;
	   	int v23 = 0;
	   	int v24 = 0;
	   	int v25 = 0;
	   	int v26 = 0;
	   	int v27 = 0;
	   	int v28 = 0;
	   	int v29 = 0;
	   	int v30 = 0;
		int v31 = 0;
		int v32 = 0;
		int v33 = 0;
		int v34 = 0;
		int v35 = 0;
		int v36 = 0;
		int v37 = 0;
		int v38 = 0;
		int v39 = 0;
		int v40 = 0;
		int v41 = 0;
		int v42 = 0;
		int v43 = 0;
		int v44 = 0;
		int v45 = 0;
		int v46 = 0;
		int v47 = 0;
		int v48 = 0;
		int v49 = 0;
		int v50 = 0;
		int v51 = 0;
		int v52 = 0;
		int v53 = 0;
		int v54 = 0;
		int v55 = 0;
		int v56 = 0;
		int v57 = 0;
		int v58 = 0;
		int v59 = 0;
		int v60 = 0;
		int v61 = 0;
		int v62 = 0;
		int v63 = 0;
		int v64 = 0;
		int v65 = 0;
		int v66 = 0;
		int v67 = 0;
		int v68 = 0;
		int v69 = 0;
		int v70 = 0;
		int v71 = 0;
		int v72 = 0;
		int v73 = 0;
		int v74 = 0;
		int v75 = 0;
		int v76 = 0;
		int v77 = 0;
	   	String[] temp;
	   	/* delimeter */
	   	String delimeter = ",";
	   	/* given string will be splitted by the argument delimeter provided. */
	   	temp = str.split(delimeter);
	   	/* print splitted substrings */
	   	for(int i =0; i < temp.length ; i++) {
	   		System.out.println("temp.length = " + temp.length);
	   		vtemp = temp[i];
	   		vnext++;
	   		if ((v1 == 0) && (vnext == 1)){
	   			vs1 = "'"+vtemp+"',";
	   			v1 = 1;
	   		}
	   		if ((v2 == 0) && (vnext == 2)){    //timestamp '2008-04-05 00:01:02',
	   			v2 = 1;                        //20080405:00:01:02
	   			if (vtemp.length() == 0){      //012345678 
	   	    		vs2 = "null,";
	   	  		}else{
	   	  			System.out.println(" time stamp year = "+vtemp.substring(0, 4));
	   	  	    	System.out.println(" time stamp month= "+vtemp.substring(4, 6));
	   				vs2 = "timestamp '" + vtemp.substring(0, 4)+"-"+vtemp.substring(4, 6)+"-"
	   				+vtemp.substring(6, 8)+" "+vtemp.substring(9, vtemp.length())+"',";
	   				System.out.println("v2="+vs2);
	   			} 	
	   			//vs2 = "'" + vtemp + "',";
	   		}
	   		if ((v3 == 0) && (vnext == 3)){
	   			v3 = 1;
	   			if (vtemp.length() == 0){
	   	    		vs3 = "null,";
	   	  		}else{
	   				vs3 = "'" + vtemp + "',";
	   	  		}	
	   		}
	   		if ((v4 == 0) && (vnext == 4)){
	   			v4 = 1;
	   			if (vtemp.length() == 0){
	   	    		vs4 = "null,";
	   	    	}else{
	   				vs4 = "'" + vtemp + "',";
	   			} 	
	   		}
	   		if ((v5 == 0) && (vnext == 5)){
	   			v5 = 1;
	   			if (vtemp.length() == 0){
	   				vs5 = "null,";
	   			}else{
	   				vs5 = "'" + vtemp + "',";
	   			} 	
	   		}
	   		if ((v6 == 0) && (vnext == 6)){
	   			v6 = 1;
	   			if (vtemp.length() == 0){
	   				vs6 = "null,";
	   			}else{
	   				vs6 = "'" + vtemp + "',";
	   			} 	
	   		}
	   		if ((v7 == 0) && (vnext == 7)){
	   			v7 = 1;
	   			if (vtemp.length() == 0){
	   				vs7 = "null,";
	   			}else{
	   				vs7 = "'" + vtemp + "',";
	   			} 	
	   		}
	   		if ((v8 == 0) && (vnext == 8)){
	   			v8 = 1;
	   			if (vtemp.length() == 0){
	   				vs8 = "null,";
	   			}else{
	   				vs8 = "'"+ vtemp + "',";
	   			} 	
	   		}
	   		if ((v9 == 0) && (vnext == 9)){
	   			v9 = 1;
	   			if (vtemp.length() == 0){
	   				vs9 = "null,";
	   			}else{
	   				vs9 = "'" + vtemp + "',";
	   			} 	
	   		}
	   		if ((v10 == 0) && (vnext == 10)){
	   			v10 = 1;
	   			if (vtemp.length() == 0){
	   				vs10 = "null,";
	   			}else{
	   				vs10 = "'"+vtemp + "',";
	   			} 	
	   		}
	   		if ((v11 == 0) && (vnext == 11)){
	   			v11 = 1;
	   			if (vtemp.length() == 0){
	   				vs11 = "null,";
	   			}else{
	   				vs11 = "'"+vtemp + "',";
	   			} 	
	   		}
	   		if ((v12 == 0) && (vnext == 12)){
	   			v12 = 1;
	   			if (vtemp.length() == 0){
	   				vs12 = "null,";
	   			}else{
	   				vs12 = "'"+vtemp + "',";
	   			} 	
	   		}
	   		if ((v13 == 0) && (vnext == 13)){
	   			v13 = 1;
	   			if (vtemp.length() == 0){
	   				vs13 = "null,";
	   			}else{
	   				vs13 = "'"+vtemp + "',";
	   			} 	
	   		}
	   		if ((v14 == 0) && (vnext == 14)){
	   			v14 = 1;
	   			if (vtemp.length() == 0){
	   				vs14 = "null,";
	   			}else{
	   				vs14 =  "'"+vtemp + "',";
	   			} 	
	   		}
	   		if ((v15 == 0) && (vnext == 15)){
	   			v15 = 1;
	   			if (vtemp.length() == 0){
	   				vs15 = "null,";
	   			}else{
	   				vs15 = "'"+vtemp + "',";
	   			} 	
	   		}
	   		if ((v16 == 0) && (vnext == 16)){
	   			v16 = 1;
	   			if (vtemp.length() == 0){
	   				vs16 = "null,";
	   			}else{
	   				vs16 = "'"+vtemp + "',";
	   			} 	
	   		}
	   		if ((v17 == 0) && (vnext == 17)){
	   			v17 = 1;
	   			if (vtemp.length() == 0){
	   				vs17= "null,";
	   			}else{
	   				vs17 = "'"+vtemp + "',";
	   			} 	
	   		}
	   		if ((v18 == 0) && (vnext == 18)){
	   			v18 = 1;
	   			if (vtemp.length() == 0){
	   				vs18 = "null,";
	   			}else{
	   				vs18 = "'"+vtemp + "',";
	   			} 	
	   		}
	   		if ((v19 == 0) && (vnext == 19)){
	   			v19 = 1;
	   			if (vtemp.length() == 0){
	   				vs19 = "null,";
	   			}else{
	   				vs19 = "'"+vtemp + "',";
	   			} 	
	   		}
	   		if ((v20 == 0) && (vnext == 20)){
	   			v20 = 1;
	   			if (vtemp.length() == 0){
	   				vs20 = "null,";
	   			}else{
	   				vs20 = "'"+vtemp + "',";
	   			} 	
	   		}
	   		if ((v21 == 0) && (vnext == 21)){
	   			v21 = 1;
	   			if (vtemp.length() == 0){
	   				vs21 = "null,";
	   			}else{
	   				vs21 = "'"+vtemp + "',";
	   			} 	
	   		}
	   		if ((v22 == 0) && (vnext == 22)){
	   			v22 = 1;
	   			if (vtemp.length() == 0){
	   				vs22 = "null,";
	   			}else{
	   				vs22 = "'"+vtemp + "',";
	   			} 	
	   		}
	   		if ((v23 == 0) && (vnext == 23)){
	   			v23 = 1;
	   			if (vtemp.length() == 0){
	   				vs23 = "null,";
	   			}else{
	   				vs23 = "'"+vtemp + "',";
	   			} 	
	   		}
	   		if ((v24 == 0) && (vnext == 24)){
	   			v24 = 1;
	   			if (vtemp.length() == 0){
	   				vs24 = "null,";
	   			}else{
	   				vs24 = "'"+vtemp + "',";
	   			} 	
	   		}
	   		if ((v25 == 0) && (vnext == 25)){
	   			v25 = 1;
	   			if (vtemp.length() == 0){
	   				vs25 = "null,";
	   			}else{
	   				vs25 = "'"+vtemp + "',";
	   			} 	
	   		}
	   		if ((v26 == 0) && (vnext == 26)){
	   			v26 = 1;
	   			if (vtemp.length() == 0){
	   				vs26 = "null,";
	   			}else{
	   				vs26 = "'"+vtemp + "',";
	   			} 	
	   		}
	   		if ((v27 == 0) && (vnext == 27)){
	   			v27 = 1;
	   			if (vtemp.length() == 0){
	   				vs27 = "null,";
	   			}else{
	   				vs27 = "'"+vtemp + "',";
	   			} 	
	   		}
	   		if ((v28 == 0) && (vnext == 28)){
	   			v28 = 1;
	   			if (vtemp.length() == 0){
	   				vs28 = "null,";
	   			}else{
	   				vs28 = "'"+vtemp + "',";
	   			} 	
	   		}
	   		if ((v29 == 0) && (vnext == 29)){
	   			v29 = 1;
	   			if (vtemp.length() == 0){
	   				vs29 = "null,";
	   			}else{
	   				vs29 = "'"+vtemp+"',";
	   			} 	
	   		}
	   		if ((v30 == 0) && (vnext ==30 )){
	   			v30 = 1;
	   			if (vtemp.length() == 0){
	   				vs30 = "null,";
	   			}else{
	   				vs30 = "'"+vtemp+"',";
	   			} 	
	   		}
	   		if ((v31 == 0) && (vnext ==31 )){
	   			v31 = 1;
	   			if (vtemp.length() == 0){
	   				vs31 = "null,";
	   			}else{
	   				vs31 = "'"+vtemp+"',";
	   			} 	
	   		}
	   		if ((v32 == 0) && (vnext ==32 )){
	   			v32 = 1;
	   			if (vtemp.length() == 0){
	   				vs32 = "null,";
	   			}else{
	   				vs32 = "'"+vtemp+"',";
	   			} 	
	   		}if ((v33 == 0) && (vnext == 33 )){
	   			v33 = 1;
	   			if (vtemp.length() == 0){
	   				vs33 = "null,";
	   			}else{
	   				vs33 = "'"+vtemp+"',";
	   			} 	
	   		}if ((v34 == 0) && (vnext == 34)){
	   			v34 = 1;
	   			if (vtemp.length() == 0){
	   				vs34 = "null,";
	   			}else{
	   				vs34 = "'"+vtemp+"',";
	   			} 	
	   		}
	   		if ((v35 == 0) && (vnext == 35)){
	   			v35 = 1;
	   			if (vtemp.length() == 0){
	   				vs35 = "null,";
	   			}else{
	   				vs35 = "'"+vtemp+"',";
	   			} 	
	   		}
	   		if ((v36 == 0) && (vnext == 36)){
	   			v36 = 1;
	   			if (vtemp.length() == 0){
	   				vs36 = "null,";
	   			}else{
	   				vs36 = "'"+vtemp+"',";
	   			} 	
	   		}
	   		if ((v37 == 0) && (vnext == 37)){
	   			v37 = 1;
	   			if (vtemp.length() == 0){
	   				vs37 = "null,";
	   			}else{
	   				vs37 = "'"+vtemp+"',";
	   			} 	
	   		}
	   		if ((v38 == 0) && (vnext == 38)){
	   			v38 = 1;
	   			if (vtemp.length() == 0){
	   				vs38 = "null,";
	   			}else{
	   				vs38 = "'"+vtemp+"',";
	   			} 	
	   		}
	   		if ((v39 == 0) && (vnext == 39)){
	   			v39 = 1;
	   			if (vtemp.length() == 0){
	   				vs39 = "null,";
	   			}else{
	   				vs39 = "'"+vtemp+"',";
	   			} 	
	   		}
	   		if ((v40 == 0) && (vnext == 40)){
	   			v40 = 1;
	   			if (vtemp.length() == 0){
	   				vs40 = "null,";
	   			}else{
	   				vs40 = "'"+vtemp+"',";
	   			} 	
	   		}
	   		if ((v41 == 0) && (vnext == 41)){
	   			v41 = 1;
	   			if (vtemp.length() == 0){
	   				vs41 = "null,";
	   			}else{
	   				vs41 = "'"+vtemp+"',";
	   			} 	
	   		}
	   		if ((v42 == 0) && (vnext == 42)){
	   			v42 = 1;
	   			if (vtemp.length() == 0){
	   				vs42 = "null,";
	   			}else{
	   				vs42 = "'"+vtemp+"',";
	   			} 	
	   		}
	   		if ((v43 == 0) && (vnext == 43)){
	   			v43 = 1;
	   			if (vtemp.length() == 0){
	   				vs43 = "null,";
	   			}else{
	   				vs43 = "'"+vtemp+"',";
	   			} 	
	   		}
	   		if ((v44 == 0) && (vnext == 44)){
	   			v44 = 1;
	   			if (vtemp.length() == 0){
	   				vs44 = "null,";
	   			}else{
	   				vs44 = "'"+vtemp+"',";
	   			} 	
	   		}
	   		if ((v45 == 0) && (vnext == 45)){
	   			v45 = 1;
	   			if (vtemp.length() == 0){
	   				vs45 = "null,";
	   			}else{
	   				vs45 = "'"+vtemp+"',";
	   			} 	
	   		}
	   		if ((v46 == 0) && (vnext == 46)){
	   			v46 = 1;
	   			if (vtemp.length() == 0){
	   				vs46 = "null,";
	   			}else{
	   				vs46 = "'"+vtemp+"',";
	   			} 	
	   		}
	   		if ((v47 == 0) && (vnext == 47)){
	   			v47 = 1;
	   			if (vtemp.length() == 0){
	   				vs47 = "null,";
	   			}else{
	   				vs47 = "'"+vtemp+"',";
	   			} 	
	   		}
	   		if ((v48 == 0) && (vnext == 48)){
	   			v48 = 1;
	   			if (vtemp.length() == 0){
	   				vs48 = "null,";
	   			}else{
	   				vs48 = "'"+vtemp+"',";
	   			} 	
	   		}
	   		if ((v49 == 0) && (vnext == 49)){
	   			v49 = 1;
	   			if (vtemp.length() == 0){
	   				vs49 = "null,";
	   			}else{
	   				vs49 = "'"+vtemp+"',";
	   			} 	
	   		}
	   		if ((v50 == 0) && (vnext == 50)){
	   			v50 = 1;
	   			if (vtemp.length() == 0){
	   				vs50 = "null,";
	   			}else{
	   				vs50 = "'"+vtemp+"',";
	   			} 	
	   		}
	   		if ((v51 == 0) && (vnext == 51)){
	   			v51 = 1;
	   			if (vtemp.length() == 0){
	   				vs51 = "null,";
	   			}else{
	   				vs51 = "'"+vtemp+"',";
	   			} 	
	   		}
	   		if ((v52 == 0) && (vnext == 52)){
	   			v52 = 1;
	   			if (vtemp.length() == 0){
	   				vs52 = "null,";
	   			}else{
	   				vs52 = "'"+vtemp+"',";
	   			} 	
	   		}
	   		if ((v53 == 0) && (vnext == 53)){
	   			v53 = 1;
	   			if (vtemp.length() == 0){
	   				vs53 = "null,";
	   			}else{
	   				vs53 = "'"+vtemp+"',";
	   			} 	
	   		}if ((v54 == 0) && (vnext == 54)){
	   			v54 = 1;
	   			if (vtemp.length() == 0){
	   				vs54 = "null,";
	   			}else{
	   				vs54 = "'"+vtemp+"',";
	   			} 	
	   		}if ((v55 == 0) && (vnext == 55)){
	   			v55 = 1;
	   			if (vtemp.length() == 0){
	   				vs55 = "null,";
	   			}else{
	   				vs55 = "'"+vtemp+"',";
	   			} 	
	   		}if ((v56 == 0) && (vnext == 56)){
	   			v56 = 1;
	   			if (vtemp.length() == 0){
	   				vs56 = "null,";
	   			}else{
	   				vs56 = "'"+vtemp+"',";
	   			} 	
	   		}if ((v57 == 0) && (vnext == 57)){
	   			v57 = 1;
	   			if (vtemp.length() == 0){
	   				vs57 = "null,";
	   			}else{
	   				vs57 = "'"+vtemp+"',";
	   			} 	
	   		}if ((v58 == 0) && (vnext == 58)){
	   			v58 = 1;
	   			if (vtemp.length() == 0){
	   				vs58 = "null,";
	   			}else{
	   				vs58 = "'"+vtemp+"',";
	   			} 	
	   		}if ((v59 == 0) && (vnext == 59)){
	   			v59 = 1;
	   			if (vtemp.length() == 0){
	   				vs59 = "null,";
	   			}else{
	   				vs59 = "'"+vtemp+"',";
	   			} 	
	   		}
	   		if ((v60 == 0) && (vnext == 60)){
	   			v60 = 1;
	   			if (vtemp.length() == 0){
	   				vs60 = "null,";
	   			}else{
	   				vs60 = "'"+vtemp+"',";
	   			} 	
	   		}
	   		if ((v61 == 0) && (vnext == 61)){
	   			v61 = 1;
	   			if (vtemp.length() == 0){
	   				vs61 = "null,";
	   			}else{
	   				vs61 = "'"+vtemp+"',";
	   			} 	
	   		}
	   		if ((v62 == 0) && (vnext == 62)){
	   			v62 = 1;
	   			if (vtemp.length() == 0){
	   				vs62 = "null,";
	   			}else{
	   				vs62 = "'"+vtemp+"',";
	   			} 	
	   		}
	   		if ((v63 == 0) && (vnext == 63)){
	   			v63 = 1;
	   			if (vtemp.length() == 0){
	   				vs63 = "null,";
	   			}else{
	   				vs63 = "'"+vtemp+"',";
	   			} 	
	   		}
	   		if ((v64 == 0) && (vnext == 64)){
	   			v64 = 1;
	   			if (vtemp.length() == 0){
	   				vs64 = "null,";
	   			}else{
	   				vs64 = "'"+vtemp+"',";
	   			} 	
	   		}
	   		if ((v65 == 0) && (vnext == 65)){
	   			v65 = 1;
	   			if (vtemp.length() == 0){
	   				vs65 = "null,";
	   			}else{
	   				vs65 = "'"+vtemp+"',";
	   			} 	
	   		}
	   		if ((v66 == 0) && (vnext == 66)){
	   			v66 = 1;
	   			if (vtemp.length() == 0){
	   				vs66 = "null,";
	   			}else{
	   				vs66 = "'"+vtemp+"',";
	   			} 	
	   		}
	   		if ((v67 == 0) && (vnext == 67)){
	   			v67 = 1;
	   			if (vtemp.length() == 0){
	   				vs67 = "null,";
	   			}else{
	   				vs67 = "'"+vtemp+"',";
	   			} 	
	   		}
	   		if ((v68 == 0) && (vnext == 68)){
	   			v68 = 1;
	   			if (vtemp.length() == 0){
	   				vs68 = "null,";
	   			}else{
	   				vs68 = "'"+vtemp+"',";
	   			} 	
	   		}
	   		if ((v69 == 0) && (vnext == 69)){
	   			v69 = 1;
	   			if (vtemp.length() == 0){
	   				vs69 = "null,";
	   			}else{
	   				vs69 = "'"+vtemp+"',";
	   			} 	
	   		}
	   		if ((v70 == 0) && (vnext == 70)){
	   			v70 = 1;
	   			if (vtemp.length() == 0){
	   				vs70 = "null,";
	   			}else{
	   				vs70 = "'"+vtemp+"',";
	   			} 	
	   		}
	   		if ((v71 == 0) && (vnext == 71)){
	   			v71 = 1;
	   			if (vtemp.length() == 0){
	   				vs71 = "null,";
	   			}else{
	   				vs71 = "'"+vtemp+"',";
	   			} 	
	   		}
	   		if ((v72 == 0) && (vnext == 72)){
	   			v72 = 1;
	   			if (vtemp.length() == 0){
	   				vs72 = "null,";
	   			}else{
	   				vs72 = "'"+vtemp+"',";
	   			} 	
	   		}
	   		if ((v73 == 0) && (vnext == 73)){
	   			v73 = 1;
	   			if (vtemp.length() == 0){
	   				vs73 = "null,";
	   			}else{
	   				vs73 = "'"+vtemp+"',";
	   			} 	
	   		}
	   		if ((v74 == 0) && (vnext == 74)){
	   			v74 = 1;
	   			if (vtemp.length() == 0){
	   				vs74 = "null,";
	   			}else{
	   				vs74 = "'"+vtemp+"',";
	   			} 	
	   		}
	   		if ((v75 == 0) && (vnext == 75)){
	   			v75 = 1;
	   			if (vtemp.length() == 0){
	   				vs75 = "null,";
	   			}else{
	   				vs75 = "'"+vtemp+"',";
	   			} 	
	   		}
	   		if ((v76 == 0) && (vnext == 76)){
	   			v76 = 1;
	   			if (vtemp.length() == 0){
	   				vs76 = "null,";
	   			}else{
	   				vs76 = "'"+vtemp+"',";
	   			} 	
	   		}
	   		if ((v77 == 0) && (vnext == 77)){
	   			v77 = 1;
	   			if (vtemp.length() == 0){
	   				vs77 = "null,";
	   			}else{
	   				vs77 = "'"+vtemp+"'";
	   			} 	
	   		}
	
	   	} //for
	   	insertdata = insertfields+" ("+vs0+vs1+vs2+vs3+vs4+vs5+vs6+vs7+vs8+vs9+vs10+vs11+vs12+vs13+vs14+vs15+vs16+vs17+vs18+vs19+vs20+vs21+vs22+vs23+vs24+vs25+vs26+vs27+vs28+vs29+vs30+vs31+vs32+vs33+vs34+vs35+vs36+vs37+vs38+vs39+vs40+vs41+vs42+vs43+vs44+vs45+vs46+vs47+vs48+vs49+vs50+vs51+vs52+vs53+vs54+vs55+vs56+vs57+vs58+vs59+vs60+vs61+vs62+vs63+vs64+vs65+vs66+vs67+vs68+vs69+vs70+vs71+vs72+vs73+vs74+vs75+vs76+vs77+");\r\n";
	   	System.out.println("vinsert is:" + insertdata);
	   	Writer output = new BufferedWriter(new FileWriter("C:\\Temp\\outfile.txt",true));
	    try {
	      if (insertfields != null ){
	    	  //output.append(insertfields);
	    	  //insertfields = null;
	      }
	      //FileWriter always assumes default encoding is OK! //output.write( vinsert );
	      //
	      output.append(insertdata);
	    }
	    finally {
	      output.close();
	    }
	   	insertdata = null;
    } //while
      //final Line   find ), change to );
    insertdata = insertdata + ";";
    }
}
