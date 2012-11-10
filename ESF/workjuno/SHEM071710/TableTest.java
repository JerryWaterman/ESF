import javax.swing.*;                                               
    import javax.swing.table.*;                                         
    import java.awt.*;                                                   
import java.util.*;                                                 
   public class TableTest extends JFrame{                               
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private MyTableModel tableModel;                                     
    private boolean runnerStopped;                                       
    private int firstRow,lastRow=9;                                     
  TableTest(){                                                         
    super("table");                                                     
    Container contentPane=getContentPane();                             
    tableModel=new MyTableModel();                                       
    JTable table=new JTable(tableModel);                                 
    JScrollPane jScrollPane=new JScrollPane(table);                     
    contentPane.add(jScrollPane);                                       
    new Runner().start();                                               
    new Manager().start();                                               
  }                                                                   
  synchronized void updateTable(){                                     
    if(runnerStopped){                                                   
    //i commented the following line in the second trial                 
      tableModel.fireTableRowsInserted(firstRow,lastRow);  
      firstRow+=10000;                                                       
      lastRow+=10000;                                                         
      System.out.println("below fireTableRowsInserted = "+lastRow);
      runnerStopped=false;                                                 
      notify();                                                           
    }                                                                   
    else{                                                               
    runnerStopped=true;                                                 
    try{                                                                 
    wait();                                                             
    }                                                                   
    catch(InterruptedException e){                                       
    }                                                                   
    }                                                                   
    }                                                                   
    class Runner extends Thread{                                         
    private int row,column;                                             
    public void run(){                                                   
    while(true){                                                         
    Random rnd=new Random();                                             
    byte[] bytes=new byte[100];                                           
    rnd.nextBytes(bytes);                                               
    String datum=new String(bytes);                                     
    tableModel.setValueAt(datum,row,column++);                           
    if(column==10){                                                     
    column=0;                                                           
    row++;                                                               
    }                                                                   
    if((row%10==1)&(column==0)){                                         
    updateTable();                                                       
    try{                                                                 
    sleep(1000);                                                       
    }                                                                   
    catch(InterruptedException e){                                       
    }                                                                   
    }                                                                   
    }                                                                   
    }                                                                   
    }                                                                   
    class Manager extends Thread{                                       
    public void run(){                                                   
    while(true){                                                         
    if(runnerStopped){                                                   
    updateTable();                                                       
    }                                                                   
    }                                                                   
    }                                                                   
    }                                                                   
  public static void main(String[] args){                             
    TableTest table=new TableTest();                                     
    table.setSize(600,600);                                             
    table.setVisible(true);                                             
  }                                                                   
}                                                                   

 class MyTableModel extends AbstractTableModel{                       
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ArrayList<String[]> data;                                             
      MyTableModel(){                                                     
      data=new ArrayList<String[]>();                                               
    }  
      
    public int getRowCount(){return data.size();}                       
    public int getColumnCount(){return 10;}                             
    public Object getValueAt(int row,int column){                       
    String[] arr=(String[])data.get(row);                               
    return arr[column];                                                 
    }                                                                   
    public void setValueAt(Object value,int row,int column){             
    String[] arr;                                                       
    if(column==0){                                                       
    arr=new String[10];                                                 
    }                                                                   
    else{                                                               
    arr=(String[])data.get(row);                                         
    }                                                                   
    arr[column]=(String)value;                                           
    data.add(row,arr);                                                   
    }                                                                   
    public String getColumnName(int column){                             
      return "column"+(column+1);                                         
    }                                                                   
    } 