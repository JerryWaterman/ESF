import java.sql.Types;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;


public class EnterValuesChecker {
	
	// holds columns's types and precision or 0 if precision is not applicable
	private int[] columnType, columnPrecision;
	
	
	
	public EnterValuesChecker(int [] aColumnType, int [] aColumnPrecision){
		this.columnType = aColumnType;
		this.columnPrecision = aColumnPrecision;
	}
	/**
	 * 
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
		if(valueToCheck == null)
			return !returnCodeValue;
	try{	
		switch(columnType[columnToCheck]){
		
case Types.INTEGER:{
			
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
	
			Double.valueOf(valueToCheck);
			
			returnCodeValue = true;
	
}
		break;
		
case Types.VARCHAR:{
	
			int numberOfDigits = valueToCheck.length();
				if(numberOfDigits > columnPrecision[columnToCheck])
				returnCodeValue = false;
					else
						returnCodeValue = true;
	
		}
		break;
		
case Types.DATE:{
	
	DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
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


