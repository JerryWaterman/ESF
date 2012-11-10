package jerry;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Scanner;

public class chn1{
	
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
     -------------------------------F I E L D S------D A T A------------------------------
   CREATE TABLE  `test`.`jerrychn` (
  `sysid` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `cdate` datetime DEFAULT NULL,
  `siteid` varchar(5) DEFAULT NULL,
  `longid` varchar(5) DEFAULT NULL,
  `crossid` varchar(5) DEFAULT NULL,
  `rep#` varchar(4) DEFAULT NULL,
  `Matrix` varchar(4) DEFAULT NULL,
  `ctype` varchar(10) DEFAULT NULL,
  `cname` varchar(5) DEFAULT NULL,
  `subwt_ug` int(11) DEFAULT NULL,
  `treatment` varchar(20) DEFAULT NULL,
  `runnumber_cup` varchar(4) DEFAULT NULL,
  `kc` double DEFAULT NULL,
  `bic` int(11) DEFAULT NULL,
  `kh` double DEFAULT NULL,
  `bih` int(11) DEFAULT NULL,
  `kn` double DEFAULT NULL,
  `bin` int(11) DEFAULT NULL,
  `weighdate1` datetime DEFAULT NULL,
  `weighdate2` datetime DEFAULT NULL,
  `weighdate3` datetime DEFAULT NULL,
  `weighdate4` datetime DEFAULT NULL,
  `rundate` datetime DEFAULT NULL,
  `EnteredWt_ug` int(11) DEFAULT NULL,
  `filltime` int(11) DEFAULT NULL,
  `cz` int(11) DEFAULT NULL,
  `hz` int(11) DEFAULT NULL,
  `nz` int(11) DEFAULT NULL,
  `cr` int(11) DEFAULT NULL,
  `hr` int(11) DEFAULT NULL,
  `nr` int(11) DEFAULT NULL,
  `eid` varchar(45) DEFAULT NULL,
  `cmass_ug` double DEFAULT NULL,
  `hmass_ug` double DEFAULT NULL,
  `nmass_ug` double DEFAULT NULL,
  `c_%` double DEFAULT NULL,
  `h_%` double DEFAULT NULL,
  `n_%` double DEFAULT NULL,
  `molar_cn_ratio` double DEFAULT NULL,
  `hrec` double DEFAULT NULL,
  `nrec` double DEFAULT NULL,
  `cnrpd` double DEFAULT NULL,
  `qccheckCarbon` varchar(15) DEFAULT NULL,
  `qccheckHydrogen` varchar(15) DEFAULT NULL,
  `qccheckNitrogen` varchar(15) DEFAULT NULL,
  `comments` varchar(245) DEFAULT NULL,
  `kC_cal_Exeter` double DEFAULT NULL,
  `kH_cal_Exeter` double DEFAULT NULL,
  `kN_cal_Exeter` double DEFAULT NULL,
  PRIMARY KEY (`sysid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1; 
                                          1     2    3    4    5    6       7      8        9    
     insert into test.jerrychn (         cdate, siteid 
     
     comments)  
     values ('2008-04-15', 'E01', 'G02', 'R', 1, 'BP', 'GVL', 'UKN', 1, 0, 1600, 1, 1, 1,  1, 55,
     null, 55, 1.1816, 1.1977, 1.1954, null, null,  0.4660, 'Target', 0.0040855, 
     18.58320, 'test for comments');
     -------------------------------F I E L D S------------------------------------     
	*/
    File file = new File("C:\\Temp\\Exeter CHN Master Feb09d.csv");
    Scanner fileIn = new Scanner(file);
    String insertfields = "insert into test.jerrychn ( cdate,siteid ) values \r\n";
    String insertdata   = null;
    while (fileIn.hasNext()){
	    String cLine= fileIn.nextLine();
	    String str = cLine;                                               //
	   	String vtemp = "";
	   	String vs1  = "null"; //cdate
	   	String vs2  = "null"; //siteid
	   	String vs3  = "null"; //sampleID
	   	String vs4  = "null"; //elapsetimedays
	   	String vs5  = "null"; //channeltype
	   	String vs6  = "null"; //channelid
	   	String vs7  = "null"; //analyticalbase
	   	String vs8  = "null"; //longchannelid
	   	String vs9  = "null"; //crosschannelid
	   	String vs10 = "null"; //cobblessamplertray
	   	String vs11 = "null"; //porosityfinalwet
	   	String vs12 = "null"; //porosityfinaldry
	   	String vs13 = "null"; //porosityporocity
	   	String vs14 = "null"; //gt2mmfractionperiphyt
	   	String vs15 = "null"; //gtprocessedtraycobble,
	   	String vs17 = "null"; //gt2mm
	   	String vs16 = "null"; //f255um2mmsedweight
	   	String vs18 = "null"; //f255um2mmweighboat
	   	String vs19 = "null"; //f255um2mm   	
	   	String vs20 = "null"; //f255um2mmloi,
	   	String vs21 = "null"; //f12um250wettedbucketsed,
	   	String vs22 = "null"; //f12um250mwettedbucket
	   	String vs23 = "null"; //f12um250msubsamplecapped 
	   	String vs24 = "null"; //f12um250mcappedbottle 
	   	String vs25 = "null"; //f12um250mtotalcupfiltersed
	   	String vs26 = "null"; //f12um250mtotalcupfilter 
	   	String vs27 = "null"; //f12um250um 
	   	String vs28 = "null"; //f12um250loi
	   	//String vs29 = "null"; //f12um250mtotalfinefraction  
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
	   	//int v29 = 0;
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
	   		System.out.println("vnext="+vnext);
	   		if ((v1 == 0) && (vnext == 1)){
	   			v1 = 1;
	   			if (vtemp.length() == 0){
	   				vs1 = "null,";
	   			}else{
	   			    vs1 = "'"+vtemp+"',";
	   			}
	   		}
	   		if ((v2 == 0) && (vnext == 2)){
	   			v2 = 1;
	   			if (vtemp.length() == 0){
	   	    		vs2 = "null,";
	   	  		}else{
	   				vs2 = "'" + vtemp + "',";
	   			} 	
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
	   				vs5 = vtemp + ",";
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
	   		if ((v8 == 0) && (vnext == 8)){ //
	   			v8 = 1;
	   			if (vtemp.length() == 0){
	   				vs8 = "null,";
	   			}else{
	   				vs8 = "'" + vtemp + "',";
	   			} 	
	   		}
	   		if ((v9 == 0) && (vnext == 9)){
	   			v9 = 1;
	   			if (vtemp.length() == 0){
	   				vs9 = "null,";
	   			}else{
	   				vs9 =  vtemp + ",";
	   			} 	
	   		}
	   		if ((v10 == 0) && (vnext == 10)){
	   			v10 = 1;
	   			if (vtemp.length() == 0){
	   				vs10 = "null,";
	   			}else{
	   				vs10 = vtemp + ",";
	   			} 	
	   		}
	   		if ((v11 == 0) && (vnext == 11)){
	   			v11 = 1;
	   			if (vtemp.length() == 0){
	   				vs11 = "null,";
	   			}else{
	   				vs11 = vtemp + ",";
	   			} 	
	   		}
	   		if ((v12 == 0) && (vnext == 12)){
	   			v12 = 1;
	   			if (vtemp.length() == 0){
	   				vs12 = "null,";
	   			}else{
	   				vs12 =  vtemp + ",";
	   			} 	
	   		}
	   		if ((v13 == 0) && (vnext == 13)){
	   			v13 = 1;
	   			if (vtemp.length() == 0){
	   				vs13 = "null,";
	   			}else{
	   				vs13 = vtemp + ",";
	   			} 	
	   		}
	   		if ((v14 == 0) && (vnext == 14)){
	   			v14 = 1;
	   			if (vtemp.length() == 0){
	   				vs14 = "null,";
	   			}else{
	   				vs14 = vtemp + ",";
	   			} 	
	   		}
	   		if ((v15 == 0) && (vnext == 15)){
	   			v15 = 1;
	   			if (vtemp.length() == 0){
	   				vs15 = "null,";
	   			}else{
	   				vs15 = vtemp + ",";
	   			} 	
	   		}
	   		if ((v16 == 0) && (vnext == 16)){
	   			v16 = 1;
	   			if (vtemp.length() == 0){
	   				vs16 = "null,";
	   			}else{
	   				vs16 = vtemp + ",";
	   			} 	
	   		}
	   		if ((v17 == 0) && (vnext == 17)){
	   			v17 = 1;
	   			if (vtemp.length() == 0){
	   				vs17= "null,";
	   			}else{
	   				vs17 = vtemp + ",";
	   			} 	
	   		}
	   		if ((v18 == 0) && (vnext == 18)){ 
	   			v18 = 1;
	   			if (vtemp.length() == 0){
	   				vs18 = "null,";
	   			}else{
	   				int vlen = vtemp.length();
	   				System.out.println(" v len = "+vlen);
	   				String vstr = vtemp.substring(4,7);
	   				vs18 = vtemp + ",";
	   				System.out.println("vtemp = "+vtemp+ " vstr="+ vstr);  //vnext=18	vtemp = 9-10apr07
	   			}
	   		}
	   		if ((v19 == 0) && (vnext == 19)){
	   			v19 = 1;
	   			if (vtemp.length() == 0){
	   				vs19 = "null,";
	   			}else{
	   				vs19 = vtemp + ",";
	   			} 	
	   		}
	   		if ((v20 == 0) && (vnext == 20)){
	   			v20 = 1;
	   			if (vtemp.length() == 0){
	   				vs20 = "null,";
	   			}else{
	   				vs20 = vtemp + ",";
	   			} 	
	   		}
	   		if ((v21 == 0) && (vnext == 21)){
	   			v21 = 1;
	   			if (vtemp.length() == 0){
	   				vs21 = "null,";
	   			}else{
	   				vs21 = vtemp + ",";
	   			} 	
	   		}
	   		if ((v22 == 0) && (vnext == 22)){
	   			v22 = 1;
	   			if (vtemp.length() == 0){
	   				vs22 = "null,";
	   			}else{
	   				vs22 = vtemp + ",";
	   			} 	
	   		}
	   		if ((v23 == 0) && (vnext == 23)){
	   			v23 = 1;
	   			if (vtemp.length() == 0){
	   				vs23 = "null,";
	   			}else{
	   				vs23 = vtemp + ",";
	   			} 	
	   		}
	   		if ((v24 == 0) && (vnext == 24)){
	   			v24 = 1;
	   			if (vtemp.length() == 0){
	   				vs24 = "null,";
	   			}else{
	   				vs24 = vtemp + ",";
	   			} 	
	   		}
	   		if ((v25 == 0) && (vnext == 25)){
	   			v25 = 1;
	   			if (vtemp.length() == 0){
	   				vs25 = "null,";
	   			}else{
	   				vs25 = "'" + vtemp + "',";
	   			} 	
	   		}
	   		if ((v26 == 0) && (vnext == 26)){
	   			v26 = 1;
	   			if (vtemp.length() == 0){
	   				vs26 = "null,";
	   			}else{
	   				vs26 = vtemp + ",";
	   			} 	
	   		}
	   		if ((v27 == 0) && (vnext == 27)){
	   			v27 = 1;
	   			if (vtemp.length() == 0){
	   				vs27 = "null,";
	   			}else{
	   				vs27 =  vtemp + ",";
	   			} 	
	   		}
	   		if ((v28 == 0) && (vnext == 28)){
	   			v28 = 1;
	   			if (vtemp.length() == 0){
	   				vs28 = "null,";
	   			}else{
	   				vs28 = "'" + vtemp + "',";
	   			} 	
	   		}
	   		//if ((v29 == 0) && (vnext == 29)){
	   		//	v29 = 1;
	   		//	if (vtemp.length() == 0){
	   		//		vs29 = "null,";
	   		//	}else{
	   		//		vs29 = "'" + vtemp + "'";
	   		//	} 	
	   		//}
	   	} //for
	   	insertdata = "("+vs1+vs2+vs3+vs4+vs5+vs6+vs7+vs8+vs9+vs10+vs11+vs12+vs13+vs14+vs15+vs16+vs17+vs18+vs19+vs20+vs21+vs22+vs23+vs24+vs25+vs26+vs27+vs28+
	   	")," + "\r\n";
	   	System.out.println("vinsert is:    " + insertdata);
	   	Writer output = new BufferedWriter(new FileWriter("C:\\Temp\\chn.txt",true));
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





