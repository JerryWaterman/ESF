public class stringDemo{
  public static void main(String[] args) throws Exception{
    String color = "bluegrey";
    int index = color.indexOf('b');
    int indx  = color.indexOf("gr", 2);
    System.out.println(index);
    System.out.println(indx);
  }
}