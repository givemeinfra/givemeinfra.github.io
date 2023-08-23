package givemeviews.exportation;

import givemeviews.metrics.Metric;
import givemeviews.model.MaintenanceType;
import givemeviews.model.Projeto;
import givemeviews.model.SetModules;
import givemeviews.persistence.MemoryApplication;
import givemeviews.views.provider.OutputProvider;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.smartcardio.ATR;

import jxl.CellView;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import org.jdom2.Attribute;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
 
public class Report 
{ 
	private WritableCellFormat times;	
	private WritableCellFormat timesBoldUnderline;
	
	private void addLabel(WritableSheet planilha, int coluna, int linha, String s)
	throws WriteException, RowsExceededException 
	{
		Label label;
		label = new Label(coluna, linha, s, times);
		planilha.addCell(label);
	}
	
	private void buildLabel(WritableSheet sheet) throws WriteException 
	{
		// Cria o tipo de fonte como TIMES e tamanho
		WritableFont times10pt = new WritableFont(WritableFont.TIMES, 12);
		 
		// Define o formato da célula
		times = new WritableCellFormat(times10pt);
		 
		// Cria a fonte em negrito com underlines
		WritableFont times10ptBoldUnderline = new WritableFont(
		WritableFont.ARIAL, 12, WritableFont.BOLD, false);
		timesBoldUnderline = new WritableCellFormat(times10ptBoldUnderline);
		 
	    CellView cv = new CellView();
		cv.setFormat(times);
		cv.setFormat(timesBoldUnderline);
		cv.setAutosize(true);	
	}	
	 
	private void writeOnMetricReport(WritableSheet sheet, ArrayList<Metric> listMetrics) throws WriteException, RowsExceededException 
	{ 
		addLabel(sheet, 0, 0, "Metrics");
		addLabel(sheet, 0, 1, "Description");
		addLabel(sheet, 1, 1, "Value");
		
		for (int i = 2; i < listMetrics.size() + 2; i++) 
		{
			addLabel(sheet, 0, i, listMetrics.get(i - 2).getDescricao());
			addLabel(sheet, 1, i, listMetrics.get(i - 2).getMetrica());
		}
	}
	
	private void writeOnFrequencieReport(WritableSheet sheet, ArrayList<SetModules> listModules, Projeto module) throws WriteException, RowsExceededException 
	{ 
		addLabel(sheet, 1, 0, module.getNomeProjeto() + "/" + module.getNomeComponente());
		
		for (int i = 2; i < listModules.size() + 2; i++) 
		{
			addLabel(sheet, 0, i, listModules.get(i -2).getNome() + "/" + listModules.get(i - 2).getComponenteAssociado());
			if(String.valueOf(listModules.get(i -2).getPorcentagemAssociada()).equals("0.0"))
				addLabel(sheet, 1, i, "--");
			else
				addLabel(sheet, 1, i, String.valueOf(listModules.get(i -2).getPorcentagemAssociada()) + "%");
		}
	}
	
	public void buildMetricReport(String pathToSave, ArrayList<Metric> listMetrics) throws IOException, WriteException 
	{
		// Cria um novo arquivo
		File arquivo = new File(pathToSave);
		WorkbookSettings wbSettings = new WorkbookSettings();
		 
		wbSettings.setLocale(new Locale("pt", "BR"));
		 
		WritableWorkbook workbook = Workbook.createWorkbook(arquivo, wbSettings);
		// Define um nome para a planilha
		workbook.createSheet("Metrics Report", 0);
		WritableSheet excelSheet = workbook.getSheet(0);
		buildLabel(excelSheet);
		writeOnMetricReport(excelSheet, listMetrics);
		
		workbook.write();
		workbook.close();
	}
	
	public void buildFrequenciesReport(String pathToSave, ArrayList<SetModules> listFrequencies)throws IOException, WriteException
	{
		// Cria um novo arquivo
		File arquivo = new File(pathToSave);
		WorkbookSettings wbSettings = new WorkbookSettings();
		 
		wbSettings.setLocale(new Locale("pt", "BR"));
		 
		WritableWorkbook workbook = Workbook.createWorkbook(arquivo, wbSettings);
		// Define um nome para a planilha
		workbook.createSheet("Frequencies Report", 0);
		WritableSheet excelSheet = workbook.getSheet(0);
		buildLabel(excelSheet);
		OutputProvider provider = new OutputProvider();
		writeOnFrequencieReport(excelSheet, listFrequencies, provider.providerSelectedModule());
		
		workbook.write();
		workbook.close();
	}
	
	public Boolean createMaintenanceTypeReport(String pathToSave, ArrayList<MaintenanceType> listTypes)
	{   	
	   	try
	   	{    		
   		   Element type = new Element(MemoryApplication.getProject().getNomeProjeto());// Root
           Document xmlDocument = new Document(type);  // Document 
           
           Element general = new Element("General");  // General Type
           general.setAttribute("id","0");         
           for(int i = 0; i < listTypes.size(); i++)
           {
	   	        if(listTypes.get(i).getType() == 0)
	   	        {
	        	    //Elements
		   	        Element name = new Element("name" + i);  
		   	        byte[] bytes = listTypes.get(i).getName().getBytes("ISO-8859-1");
		   	        String newText = new String(bytes);
		   	        name.setText(newText);       
		   	        general.addContent(name);  
	   	        }
           }
           type.addContent(general); // Insert on xml Document	           
       
           Element corrective = new Element("Corrective");  // General Type
           corrective.setAttribute("id","1");         
           for(int i = 0; i < listTypes.size(); i++)
           {
        	    if(listTypes.get(i).getType() == 1)
	   	        {
		   	        //Elements
		   	        Element name = new Element("name" + i);  
		   	        byte[] bytes = listTypes.get(i).getName().getBytes("ISO-8859-1");
		   	        String newText = new String(bytes);
		   	        name.setText(newText);      
		   	        corrective.addContent(name);  
	   	        }
           }
           type.addContent(corrective); // Insert on xml Document
           
           Element adaptative = new Element("Adaptative");  // General Type
           adaptative.setAttribute("id","2");         
           for(int i = 0; i < listTypes.size(); i++)
           {
        	    if(listTypes.get(i).getType() == 2)
	   	        {
		   	        //Elements
		   	        Element name = new Element("name" + i);  
		   	        byte[] bytes = listTypes.get(i).getName().getBytes("ISO-8859-1");
		   	        String newText = new String(bytes);
		   	        name.setText(newText);        
		   	        adaptative.addContent(name);  
	   	        }
           }
           type.addContent(adaptative); // Insert on xml Document
           
           Element evolutionary = new Element("Evolutive");  // General Type
           evolutionary.setAttribute("id","3");         
           for(int i = 0; i < listTypes.size(); i++)
           {
        	    if(listTypes.get(i).getType() == 3)
	   	        {
		   	        //Elements
		   	        Element name = new Element("name" + i);  
		   	        byte[] bytes = listTypes.get(i).getName().getBytes("ISO-8859-1");
		   	        String newText = new String(bytes);
		   	        name.setText(newText);        
		   	        evolutionary.addContent(name);  
	   	        }
           }
           type.addContent(evolutionary); // Insert on xml Document
           
           //Building XML
           Format format = Format.getPrettyFormat();
           format.setEncoding("ISO-8859-1");
           XMLOutputter xout = new XMLOutputter(format);  
           FileWriter arquivo = new FileWriter(new File(pathToSave));  
           xout.output(xmlDocument, arquivo);  
	   	   return true;    		
	   	}
	   	catch(Exception e)
	   	{
	   		return false;
	   	}
	 }
	
	public Boolean createCompareViewXML(String pathToSave, String[] item)
	{   	
	   	try
	   	{    		
   		   Element type = new Element("compareview");// Root
           Document xmlDocument = new Document(type);  // Document           
           
           Element period = new Element("period");  // General Type
    	   period.setAttribute("id", "0"); // first period tag         
               
    	   	   //Elements
	   	       Element date = new Element("date");
	   	       date.setText(item[0]);      
	   	       period.addContent(date);
	   	       
	   	       Element modification = new Element("modification");
	   	       modification.setText(item[1]);      
	   	       period.addContent(modification);
	   	       
	   	       Element indication = new Element("indication");
	   	       indication.setText(item[2]);      
	   	       period.addContent(indication);
	   	       
           type.addContent(period); // Insert on xml Document 
           
           //Building XML
           Format format = Format.getPrettyFormat();
           format.setEncoding("ISO-8859-1");
           XMLOutputter xout = new XMLOutputter(format);  
           FileWriter arquivo = new FileWriter(new File(pathToSave));  
           xout.output(xmlDocument, arquivo);  
	   	   return true; 
              		
	   	}
	   	catch(Exception e)
	   	{
	   		return false;
	   	}
	 }

	public Boolean updateCompareViewXML(String pathToSave, String[] item) 
	{
		try {
			 
			SAXBuilder builder = new SAXBuilder();
			File xmlFile = new File(pathToSave);
	 
			Document doc = (Document) builder.build(xmlFile);
			Element rootNode = doc.getRootElement();			
			
			// get last id			
			List<Element> list = rootNode.getChildren();
			int lastId = 0;
			for(int i = 0; i < list.size(); i++)
			{
				Attribute attribAux = list.get(i).getAttribute("id");
				if(lastId < attribAux.getIntValue())
					lastId = attribAux.getIntValue();
			}
			lastId = lastId + 1;
	 	 
			// add new element
			Element period = new Element("period");  // General Type
	    	period.setAttribute("id", Integer.toString(lastId)); // first period tag         
	               
    	   	   //Elements
	   	       Element date = new Element("date");
	   	       date.setText(item[0]);      
	   	       period.addContent(date);
	   	       
	   	       Element modification = new Element("modification");
	   	       modification.setText(item[1]);      
	   	       period.addContent(modification);
	   	       
	   	       Element indication = new Element("indication");
	   	       indication.setText(item[2]);      
	   	       period.addContent(indication);
		   	       
	        rootNode.addContent(period); // Insert on xml Document 
	 
			XMLOutputter xmlOutput = new XMLOutputter();
	 
			// display nice nice
			xmlOutput.setFormat(Format.getPrettyFormat());
			xmlOutput.output(doc, new FileWriter(pathToSave));
	 
			return true;	 
			
		  } catch (IOException io) {
			return false;
		  } catch (JDOMException e) {
			return false;
		  }
	}
	
	
}