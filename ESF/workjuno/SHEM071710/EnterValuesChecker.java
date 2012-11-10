import java.sql.Types;


public class EnterValuesChecker {
	
	private int[] columnType;
	private final int VARCHAR_LENGTH_5 = 5;
	private final int VARCHAR_LENGTH_10 = 10;
	private final int VARCHAR_LENGTH_200 = 200;
	
	
	public EnterValuesChecker(int [] aColumnType){
		this.columnType = aColumnType;
	}
	/**
	 * 
	 * @param valueToCheck 
	 * @param columnToCheck
	 * @return -1 if wrong value and 1 if entered value is OK
	 * 
	 * columnToCheck = columnType[columnToCheck + 1] 
	 */
	public int checkEnteredData(String valueToCheck, int columnToCheck){
		
		int returnCodeValue = 0;
		
		try{
			switch(columnToCheck){
			case 0:{
				java.sql.Date.valueOf(valueToCheck.trim());
				returnCodeValue = 1;
				
			}break;
				
			
			case 1:
			case 2:
			case 3:
			case 5:
			case 6:{
				if(valueToCheck.length() <= VARCHAR_LENGTH_5)
					returnCodeValue = 1;
				else
					returnCodeValue = -1;
			}
			break;
			
			case 24:
			case 7:{
				if(valueToCheck.length() <= VARCHAR_LENGTH_10)
					returnCodeValue = 1;
				else
					returnCodeValue = -1;
				
			}
			break;
			
			case 4:
			case 8:
			case 9:
			case 10:
			case 11:
			case 12:
			case 13:
			case 14:
			case 15:
			case 16:
			case 17:{
				
				double d = Double.valueOf(valueToCheck);
				if (Math.floor(d) == d) {
					
				//System.out.println("Integer");
				// computes number of digits in positive integer
				int numberOfDigits =	1 + (int) (Math.log(Integer.valueOf(valueToCheck)) / (Math.log(10)));
							
				//System.out.println("Number of digits "+numberOfDigits);
				if(numberOfDigits <= 5)
					returnCodeValue = 1;
				else
					returnCodeValue = -1;
					
				
				}
				else{
					returnCodeValue = -1;
					//System.out.println("Not Integer");
					}
				
				}
			break;
			
			case 18:
			case 19:
			case 20:
			case 21:
			case 22:
			case 23:
			case 25:
			case 26:{
				
				double d = Double.valueOf(valueToCheck);
				if (Math.floor(d) == d){
					
					returnCodeValue = -1;
				
				}else{
					
						returnCodeValue = 1;
				}						
			}
			break;
			
			case 27:{
				if(valueToCheck.length() <= VARCHAR_LENGTH_200)
					returnCodeValue = 1;
				else
					returnCodeValue = -1;
				
			}
			break;
			
			
			}
			
			
		}catch(NumberFormatException exc){
			
			returnCodeValue = -1;
		}
		catch (IllegalArgumentException badTimeFormat){
			
			returnCodeValue = -1;
		}
		
		
		return returnCodeValue;
	}

}


