package sourceminer.utilities;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Log {

	private static PrintWriter pr = null;
	private static FileWriter f = null;

    private static SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss");
    
   static {
	   
	   
    	 try{
    	//create the file object to receive the log records 
         	f = new FileWriter("SourceMiner Log.txt",true);
        // direct output to the file object
         	pr = new PrintWriter(f,true);
         	
         }catch(Exception e){
         	e.printStackTrace();
         }
    }
    
	public static void print(String s){
		pr.println("("+sdf.format(new Date())+")"+" "+s);
		System.out.println(s);
	}
	
	public static void changeColorationMode(String viewName,String colorationMode){
		print("CHANGE COLORATION MODE ON "+viewName+". COLORATION MODE: "+colorationMode);
	}
	
	public static void concernChange(String concernName,String selectedColor){
		print("CONCERN CHANGE: "+concernName+".COLOR: "+selectedColor);
	}
	
	public static void concernDeselected(String concernName){
		print("CONCERN DELESECTED: "+concernName);
	}
}
