import java.io.*;

class PrimeNumber {
  public static void main(String[] args) throws Exception{
    int i;
    BufferedReader bf = new BufferedReader(
              new InputStreamReader(System.in));
    System.out.println("Enter number:");
    int num = Integer.parseInt(bf.readLine());
    //System.out.println("Prime number: ");
    for (i=1; i < num; i++ ){
      //System.out.println("i  = "+i);	
      int j;
      for (j=2; j<i; j++){
        int n = i%j;
        //System.out.println("j = "+i);
        if (n==0){
        	//System.out.println("i before break = "+(i-1));999,999,999
          break;
            
        }
      }
      if(i == j){
        System.out.print("Prime Number = "+i+"\n");
      }
    }
    System.out.println("The END!!!");
  }
} 