//package org.esf.data;
//                                         T i l e
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Scanner;
import java.text.DateFormat;
import java.text.SimpleDateFormat;



public class periMoveData{
	
	public static void main(String args[]) throws IOException{
	/* review C:\\Temp\\outfile    
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
	02/28/11 Tile data
	insert into `Jerry`.`ESFperiphyton` (CDateID,P_DateTime,ElapsedTime,SiteID,LongID,CrossID,Rep,Matrix,pType,emptyWt_g, WeightwithSample_g,SlurryVolume_mL,ChlaSample_mL,AFDMSample_mL,
                                             1      2           3         4       5      6     7    8      9     10             11                12             13            14
UreaseSample_mL,UreaseDup_mL,UreaseControl_mL,WashVolume, PreDilutionAliquot, FirstDilution, FirstSubSample, SecondDilution, SecondSubSample, ThirdSubSample,  47mmFilterVolume,
    15            16             17              18            19               20                21               22            23                24               25     
 25mmFilterVolume,  ChlaFilterVolume,47mmBoatFilterWeight, 47mmWeightAfterDrying, 47mmDriedSampleWeight, 47mmafterAshing,    47mmAshedWT, AFDM, AFDMQA,TitleArea_cm2,TotalBP_mg_cm2,
 26                      27               28                   29                         30                   31                32        33     34      35           36 
 AFDM_mg_cm2,25mmBoatFilterWeight, 25mmDryWeight, 25mmDrySampleWeight, DryWeightQA, FoilWeight, FoilName, FoilAreaWeightSlope,HalfFoilArea, FoilAreaQA,  BPDW,   AFDM_A,
    37           38                    39               40                   41        42         43          44                  45            46         47      48     
 PercentOrganicMatter,Chla_ug,ChlaQA,Chla_A,Comments,EnterBy,EnterDate,QA_DUP,Depth,SheetTitle,SubmitedBy_File) values
        49               50     51     52       53       54       55     56     57      58         59
 (timestamp '2009-05-19 12:45:00',timestamp '0000-00-00 00:00:00', timestamp '0000-00-00 00:00:00',  'E01', 'T18.1',   'R',     '1', 'BP', 'UKN' ,'208',    '1763', '1650', '8','8', '0.5', null,null,null,null,null,null,null,null,null,null,null,null,'1.1964','1.2127','0.0163','1.2104','0.0140','0.0023',null,'110.85','30.33','4.28',null,null,null, null,null,null,null,null,null,null,null,'14.11','13.0243',null ,'24.23', 'Time is mid-way between 8:00 am and time filters went in oven.','JDW', timestamp '2011-02-28',null  ,null ,'Tile','Don 11-12 update atrazine periphyton summary');
	*/
    File file = new File("C:\\Temp\\testetile.txt");
    Scanner fileIn = new Scanner(file);
//   String insertfields = "insert into `jerry`.`ESFperiphyton` (sysID,CDateID,P_DateTime,ElapsedTime,SiteID,LongID,CrossID,Rep,Matrix,Type,emptyWt_g,WeightwithSample_g, SlurryVolume_mL,ChlaSample_mL,  AFDMSample_mL,UreaseSample_mL,UreaseDup_mL,UreaseControl_mL,WashVolume,PreDilutionAliquot,FirstDilution,FirstSubSample,SecondDilution,SecondSubSample,ThirdSubSample,47mmFilterVolume,25mmFilterVolume,ChlaFilterVolume,47mmBoatFilterWeight," +
//                                                                0     1        2           3          4      5      6     7   8        9   10             11               12              13                14            15       16                    17           18           19              20             21                22         23              24              25                  26                27             28                         
//            "47mmWeightAfterDrying,47mmDriedSampleWeight,47mmafterAshing,47mmAshedWT,AFDM,AFDMQA, TitleArea_cm2,TotalBP_mg/cm2,AFDM_mg/cm2,25mmBoatFilterWeight,25mmDryWeight,25mmDrySampleWeight,DryWeightQA,FoilWeight,FoilName," +
//              29                     30                      31           32        33   34       35            36            37            38                   39                40               41         42         43
//            "FoilAreaWeightSlope,HalfFoilArea,FoilAreaQA,BPDW,AFDM_A,PercentOrganicMatter,Chla_ug,ChlaQA,Chla_A,Comments,EnterBy,EnterDate,QA_DUP,Depth,SheetTitle,SubmitedBy_File)" +
//               44                   45         46       47   48             49            50       51    52     53        54    55         56    57       58            59
  
    String insertfields = "insert into `Jerry`.`ESFperiphyton` (CDateID,P_DateTime,ElapsedTime,SiteID,      LongID,          CrossID,      Rep,   Matrix,  pType,      emptyWt_g,  WeightwithSample_g,   SlurryVolume_mL,  ChlaSample_mL,AFDMSample_mL,    UreaseSample_mL, UreaseDup_mL, UreaseControl_mL, WashVolume, PreDilutionAliquot, FirstDilution, FirstSubSample, SecondDilution, SecondSubSample, ThirdSubSample,  47mmFilterVolume, 25mmFilterVolume,  ChlaFilterVolume,47mmBoatFilterWeight, 47mmWeightAfterDrying, 47mmDriedSampleWeight, 47mmafterAshing,    47mmAshedWT, AFDM,        AFDMQA,  TitleArea_cm2,  TotalBP_mg_cm2, AFDM_mg_cm2,25mmBoatFilterWeight, 25mmDryWeight, 25mmDrySampleWeight, DryWeightQA, FoilWeight, FoilName, FoilAreaWeightSlope,HalfFoilArea, FoilAreaQA,  BPDW,   AFDM_A, PercentOrganicMatter,Chla_ug, ChlaQA, Chla_A,Comments,EnterBy,EnterDate,QA_DUP,Depth,SheetTitle,SubmitedBy_File)"+ 
    		" values  \r\n";
  //         
    String insertdata   = null;
    while (fileIn.hasNext()){
	    String cLine= fileIn.nextLine();
	    String str = cLine;                                               //"2008-11-22,2006-07-15,esf";
	   	String vtemp = "";
	   	String vs1  = "null"; //
	   	String vs2  = "null"; //
	   	//String vs2a = "null,"; //
	   	String vs3  = "null"; //
	   	String vs4  = "null"; //
	   	String vs5  = "null"; //
	   	String vs6  = "null"; //
	   	String vs7  = "null"; //
	   	String vs8  = "null"; //
	   	String vs9  = "null"; //
	   	String vs10 = "null"; //
	   	String vs11 = "null"; //
	   	String vs12 = "null"; //
	   	String vs13 = "null"; //
	   	String vs14 = "null"; //
	   	String vs15 = "null"; //
	   	String vs16 = "null"; //
	   	String vs17 = "null"; //
	   	String vs18 = "null"; //
	   	String vs19 = "null"; //   	
	   	String vs20 = "null"; //
	   	String vs21 = "null"; //
	   	String vs22 = "null"; //
	   	String vs23 = "null"; // 
	   	String vs24 = "null"; // 
	   	String vs25 = "null"; //
	   	String vs26 = "null"; // 
	   	String vs27 = "null"; // 
	   	String vs28 = "null"; //
	   	String vs29 = "null"; //  
	   	String vs30 = "null";
		String vs31 = "null";
		String vs32 = "null";
		String vs33 = "null";
		String vs34 = "null";
		String vs35 = "null";
		String vs36 = "null";
		String vs37 = "null";
		String vs38 = "null";
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
	   	String[] temp;
	   	/* delimeter */
	   	String delimeter = ",";
	   	/* given string will be splitted by the argument delimeter provided. */
	   	temp = str.split(delimeter);
	   	/* print splitted substrings */
	   	for(int i =0; i < temp.length ; i++) {
	   		//System.out.println("temp.length = " + temp.length);
	   		vtemp = temp[i];
	   		vnext++;
	   		if ((v1 == 0) && (vnext == 1)){
	   			//System.out.println("str1 "+vtemp.substring(0, 4)+"-"+vtemp.substring(4,6)+"-"+vtemp.substring(6, 8));
	   			vs1 = "timestamp '"+(vtemp.substring(0, 4)+"-"+vtemp.substring(4,6)+"-"+vtemp.substring(6, 8));
	   			v1 = 1;
	   			//System.out.println("vs1 = "+vs1);
    		}
	   		//(timestamp '2009-05-19 12:45:00',timestamp '0000-00-00 00:00:00', timestamp '0000-00-00 00:00:00',  'E01', 'T18.1',   'R',     '1', 'BP', 'UKN' ,'208',    '1763', '1650', '8','8', '0.5', null,null,null,null,null,null,null,null,null,null,null,null,'1.1964','1.2127','0.0163','1.2104','0.0140','0.0023',null,'110.85','30.33','4.28',null,null,null, null,null,null,null,null,null,null,null,'14.11','13.0243',null ,'24.23', 'Time is mid-way between 8:00 am and time filters went in oven.','JDW', timestamp '2011-02-28',null  ,null ,'Tile','Don 11-12 update atrazine periphyton summary');
	   		if ((v2 == 0) && (vnext == 2)){
	   			v2 = 1;
	   			if (vtemp.length() == 0){
	   	    		vs2 = "null,";
	   	  		}else{
	   				vs2 = vs1+" " + vtemp + "',";  // vs1 and vs2 are together
	   			} 	
	   		}
	   		if ((v3 == 0) && (vnext == 3)){
	   			v3 = 1;
	   			if (vtemp.length() == 0){
	   	    		vs3 = "timestamp '0000-00-00 00:00:00',"; //"null,";
	   	  		}else{
	   				vs3 = "timestamp '"+(vtemp.substring(0, 4)+"-"+vtemp.substring(4,6)+"-"+vtemp.substring(6, 8)); //"'" + vtemp + "',";
	   				System.out.println("vs3 = "+vs3); 	   	  		
	   	  		}	
	   		}
	   		if ((v4 == 0) && (vnext == 4)){
	   			v4 = 1;
	   			if (vtemp.length() == 0){
	   	    		vs4 = "null,";
	   	    	}else{
	   				vs4 = vs3+ "'" + vtemp + "',";
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
	   			System.out.println("vs7"+vs7);
	   		}
	   		if ((v8 == 0) && (vnext == 8)){
	   			v8 = 1;
	   			if (vtemp.length() == 0){
	   				vs8 = "null,";
	   			}else{
	   				vs8 = "'"+vtemp + "',";
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
	   				System.out.println("vs16 = "+vs16); 
	   			}else{
	   				vs16 = "'"+vtemp + "',";
	   				System.out.println("vs16 = "+vs16); 
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
	   			//System.out.println("vs18 = "+vs18); 
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
	   				vs29 = "'"+vtemp + "',";
	   			} 	
	   		}
	   		if ((v30 == 0) && (vnext == 30)){
	   			v30 = 1;
	   			if (vtemp.length() == 0){
	   				vs30 = "null,";
	   			}else{
	   				vs30 = "'"+vtemp + "',";
	   			} 	
	   		}
	   		if ((v31 == 0) && (vnext == 31)){
	   			v31 = 1;
	   			if (vtemp.length() == 0){
	   				vs31 = "null,";
	   			}else{
	   				vs31 = "'"+vtemp + "',";
	   			} 	
	   			//System.out.println("vs31 = "+vs31); 
	   		}
	   		if ((v32 == 0) && (vnext == 32)){
	   			v32 = 1;
	   			if (vtemp.length() == 0){
	   				vs32 = "null,";
	   			}else{
	   				vs32 = "'"+vtemp + "',";
	   			}
	   			System.out.println("vs32 = "+vs32); 
	   		}
	   		// JDW, timestamp 2011-02-24,null,null,Tile,Don 11-12 update atrazine periphyton summary
	   		//  33            34          35   36   37   38
	   		vs33 = "'JDW',";
	   		//java.util.Date today = Calendar.getInstance().getTime();
	   		//SimpleDateFormat today = new SimpleDateFormat("yyyy-MM-dd hh.mm.ss");
	   		Calendar calendar = Calendar.getInstance();
	        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	   		vs34 = " timestamp '"+ dateFormat.format(calendar.getTime())+ " 00:00:00', ";
	   		System.out.println("vs34 = "+vs34);
	   		
	   		vs35 = null+",";
	   		vs36 = null+",";
	   		vs37 = "'Tile',";
	   		vs38 = "'Don 11-12 update atrazine periphyton summary'";
	   		
	   		//vs33 = "'vs33 = "'vs33 = "'vs33 = "'
	   		
	   		 
	   		 
	   		 /* 
	   		 * if ((v33 == 0) && (vnext == 33)){
	   			v33 = 1;
	   			if (vtemp.length() == 0){
	   				vs33 = "'JDW',";
	   			}else{
	   				vs33 = "'"+vtemp + "',";
	   			} 	
	   			System.out.println("vs33 = "+vs33); 
	   		}
	   		if ((v34 == 0) && (vnext == 34)){
	   			v34 = 1;
	   			if (vtemp.length() == 0){
	   				vs34 = "null,";
	   			}else{
	   				vs34 = "'"+vtemp + "',";
	   			} 	
	   			System.out.println("vs34 = "+vs34); 
	   		}
	   		if ((v35 == 0) && (vnext == 35)){
	   			v35 = 1;
	   			if (vtemp.length() == 0){
	   				vs35 = "null,";
	   			}else{
	   				vs35 = "'"+vtemp + "',";
	   			} 	
	   		}
	   		if ((v36 == 0) && (vnext == 36)){
	   			v36 = 1;
	   			if (vtemp.length() == 0){
	   				vs36 = "null,";
	   			}else{
	   				vs36 = "'"+vtemp + ",";
	   			} 	
	   		}
	   		if ((v37 == 0) && (vnext == 37)){
	   			v37 = 1;
	   			if (vtemp.length() == 0){
	   				vs37 = "null,";
	   			}else{
	   				vs37 = "'"+vtemp + "',";
	   			} 
	   			System.out.println("vs37 = "+vs37);
	   		}*/
	   	} //for
	   	insertdata = "("+vs2+vs3+vs4+vs5+vs6+vs7+vs8+vs9+vs10+vs11+vs12+vs13+vs14+vs15+vs16+vs17+vs18+vs19+vs20+vs21+vs22+vs23+vs24+vs25+vs26+vs27+vs28+vs29+vs30+vs31+vs32+vs33+vs34+vs35+vs36+vs37+vs38+
	   	")," + "\r\n";
	   	System.out.println("vinsert is:    " + insertdata);
	   	Writer output = new BufferedWriter(new FileWriter("C:\\Temp\\ESFoutfile.txt",true));
	    try {
	      if (insertfields != null ){
	    	  output.append(insertfields);
	    	  insertfields = null;
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

		// private static String getDateTime() {         DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");         Date date = new Date();         return dateFormat.format(date);} 
}
/*
IMPORTANT : Some special characters needs to be escaped while providing them as delimeters like "." and "|".
*/
//System.out.println("");
//str = "one,two,three";
//delimeter = "\\.";
//temp = str.split(delimeter);
//for(int i =0; i < temp.length ; i++)
//System.out.println(temp[i]);
/*
Using second argument in the String.split() method, we can control the   maximum number of substrings generated by splitting a string.
*/
//System.out.println("");
//temp = str.split(delimeter,2);
//for(int i =0; i < temp.length ; i++)
//System.out.println(temp[i]);
//System.out.println("temp = " + temp[i] +"  vtemp = " + vtemp);
	//System.out.println("vd1=" +vd1 + "  vd2="+vd2);




