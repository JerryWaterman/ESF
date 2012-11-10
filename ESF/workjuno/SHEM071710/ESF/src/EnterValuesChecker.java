import java.sql.Types;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.sql.ResultSet;

public class EnterValuesChecker {
	
	// holds columns's types and precision or 0 if precision is not applicable
	private int[] columnType, columnPrecision;
	public String vType;
	
	
	public EnterValuesChecker(int [] aColumnType, int [] aColumnPrecision){
		this.columnType = aColumnType;
		this.columnPrecision = aColumnPrecision;
		//System.out.println("Line 17,EnterValuesChecker "+"      columnType "+columnType+
			//	"   this"+this.toString()+"   Precision = "+this.columnPrecision);
	}
	/**
	 * 
	 * @param avType 
	 * @param valueToCheck 
	 * @param columnToCheck
	 * @return false if wrong value and true if entered value is OK
	 * 
	 *  
	 */
	public boolean checkEnteredData(String aValueToCheck, int aColumnToCheck){
		
		boolean returnCodeValue = false; 
		int columnToCheck = aColumnToCheck + 1;
		String valueToCheck = aValueToCheck;
		if(valueToCheck == null){
			return !returnCodeValue;}
	try{	
		//System.out.println("line 38,      before switch = "+columnType[columnToCheck]);
		switch(columnType[columnToCheck]){
		
case Types.INTEGER:{
			vType = "Integer";
			double d = Double.valueOf(valueToCheck);
				if (Math.floor(d) == d) {
					
				//System.out.println("Integer");
				// computes number of digits in positive integer
				int numberOfDigits =	1 + (int) (Math.log(Integer.valueOf(valueToCheck)) / (Math.log(10)));
					if(numberOfDigits > columnPrecision[columnToCheck])
						returnCodeValue = false;
					else
						returnCodeValue = true;
				
				
				}
				else{
					returnCodeValue = false;
					//System.out.println("Not Integer");
					}
			
			
		}
		break;
		
case Types.DOUBLE:{
	        vType = "Double Integer";
			Double.valueOf(valueToCheck);
			
			returnCodeValue = true;
	
}
		break;
		
case Types.VARCHAR:{
        	vType = "Character";
			int numberOfDigits = valueToCheck.length();
				if(numberOfDigits > columnPrecision[columnToCheck])
				returnCodeValue = false;
					else
						returnCodeValue = true;
	
		}
		break;
		
case Types.TIMESTAMP:{
	vType = "Date";
	DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
	//System.out.println("  ********** inside Types.DATE"+df.format(df));
	
	df.setLenient(false);// ! this is important !
	df.parse(valueToCheck);
	
	returnCodeValue = true;
}

		break;		
		}
		
			
	}catch(ParseException DateException){
		returnCodeValue = false;
	}
	catch(NumberFormatException intOrDouble){
		returnCodeValue = false;
	}
	catch(NullPointerException intOrDouble_2){
		returnCodeValue = true;
	}
	
	
	return returnCodeValue;
	//end of the method
	}
	
}


