package givemetrace.implementations;

import japa.parser.JavaParser;
import japa.parser.ParseException;
import japa.parser.ast.CompilationUnit;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;

public class Utils {
final static public int MEMORYCONTROL = 5;
	
	/*Outras opcoes de codificacao: Windows-1252 ou CP-1252(acho que este é o padrão do eclipse);
	 * 								 ISO-8859-1
	 * 				não aceitou UCS-2,				 */
	
	final static public String[] ENCODECONTROL = new String[]{"cp1252","UTF-8",
				"ISO-8859-15",
				"IBM00858",
				"IBM437",
				"IBM775",
				"IBM850",
				"IBM852",
				"IBM855",
				"IBM857",
				"IBM862",
				"IBM866",
				"ISO-8859-1",
				"ISO-8859-2",
				"ISO-8859-4",
				"ISO-8859-5",
				"ISO-8859-7",
				"ISO-8859-9",
				"ISO-8859-13",
				"KOI8-R",
				"KOI8-U",
				"US-ASCII",
				"UTF-8",
				"UTF-16",
				"x-UTF-32BE-BOM",
				"x-UTF-32LE-BOM",
				"windows-1250",
				"windows-1251",
				"windows-1252",
				"windows-1253",
				"windows-1254",
				"windows-1257",
				"x-IBM737",
				"x-IBM874",
				"x-UTF-16LE-BOM",
				"Big5",
				"Big5-HKSCS",
				"EUC-JP",
				"EUC-KR",
				"GB18030",
				"GB2312",
				"GBK",
				"IBM-Thai",
				"IBM01140",
				"IBM01141",
				"IBM01142",
				"IBM01143",
				"IBM01144",
				"IBM01145",
				"IBM01146",
				"IBM01147",
				"IBM01148",
				"IBM01149",
				"IBM037",
				"IBM1026",
				"IBM1047",
				"IBM273",
				"IBM277",
				"IBM278",
				"IBM280",
				"IBM284",
				"IBM285",
				"IBM297",
				"IBM420",
				"IBM424",
				"IBM500",
				"IBM860",
				"IBM861",
				"IBM863",
				"IBM864",
				"IBM865",
				"IBM868",
				"IBM869",
				"IBM870",
				"IBM871",
				"IBM918",
				"ISO-2022-CN",
				"ISO-2022-JP",
				"ISO-2022-KR",
				"ISO-8859-3",
				"ISO-8859-6",
				"ISO-8859-8",
				"JIS_X0201",
				"JIS_X0212-1990",
				"Shift_JIS",
				"TIS-620",
				"windows-1255",
				"windows-1256",
				"windows-1258",
				"windows-31j",
				"x-Big5-Solaris",
				"x-euc-jp-linux",
				"x-EUC-TW",
				"x-eucJP-Open",
				"x-IBM1006",
				"x-IBM1025",
				"x-IBM1046",
				"x-IBM1097",
				"x-IBM1098",
				"x-IBM1112",
				"x-IBM1122",
				"x-IBM1123",
				"x-IBM1124",
				"x-IBM1381",
				"x-IBM1383",
				"x-IBM33722",
				"x-IBM834",
				"x-IBM856",
				"x-IBM875",
				"x-IBM921",
				"x-IBM922",
				"x-IBM930",
				"x-IBM933",
				"x-IBM935",
				"x-IBM937",
				"x-IBM939",
				"x-IBM942",
				"x-IBM942C",
				"x-IBM943",
				"x-IBM943C",
				"x-IBM948",
				"x-IBM949",
				"x-IBM949C",
				"x-IBM950",
				"x-IBM964",
				"x-IBM970",
				"x-ISCII91",
				"x-iso-8859-11",
				"x-JIS0208",
				"x-JISAutoDetect",
				"x-Johab",
				"x-MacArabic",
				"x-MacCentralEurope",
				"x-MacCroatian",
				"x-MacCyrillic",
				"x-MacDingbat",
				"x-MacGreek",
				"x-MacHebrew",
				"x-MacIceland",
				"x-MacRoman",
				"x-MacRomania",
				"x-MacSymbol",
				"x-MacThai",
				"x-MacTurkish",
				"x-MacUkraine",
				"x-MS950-HKSCS",
				"x-mswin-936",
				"x-PCK",
				"x-SJIS_0213",
				"x-windows-50220",
				"x-windows-50221",
				"x-windows-874",
				"x-windows-949",
				"x-windows-950",
				"x-windows-iso2022jp"
};
	
	
	static public CompilationUnit testecompilationUnit(InputStream stream, String encode){
		
		
		CompilationUnit cu;
			try {
			 cu = JavaParser.parse(stream,encode);
		 	if(cu.getBeginColumn()==0 && cu.getBeginLine()==0 && (cu.getEndColumn()==0||cu.getEndColumn()==1) && (cu.getEndLine()==1||cu.getEndLine()==0) &&cu.getData()==null ){
		 		System.out.println("Erro ao testar ENCODE "+encode);
		 		return null;
		 	}
			} catch (ParseException e) {
			// TODO Auto-generated catch block
			System.out.println("Erro ao testar ENCODE "+encode+" STACKTRACE: "+e.getStackTrace());
			return null;
		 	}
		return cu;
		
	}
//		gitRepo.setText("C:/Users/USUARIO/Dropbox/TCC/ArquivosTCC-Cláudio/java-dependency-extractor/.git");
		
	//	svnRepo.setText("file:///C:/Users/USUARIO/Dropbox/Repositório/Implementações");//("file:///C:/Users/USUARIO/Dropbox/TCC/ArquivosTCC-Cláudio/GiveMeTraceRepo");//("file:///C:/Users/USUARIO/Documents/javaparserRepo");

	public static boolean sucessCreateFile(String filePath, String dirPath){
		File dir = new File(dirPath);
		File f = new File(filePath);
		if(!dir.exists()){dir.mkdirs();} 
		try {
			f.createNewFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			return false;
		}

		Writer writer = null;

		try {
		    writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filePath), "ISO-8859-1"));
		    
		    writer.write("<?xml version='1.0' encoding='ISO-8859-1'?>");
		    writer.write("\n<GiveMeTrace>");
			writer.write("\n<!--comentário-->");
			writer.write("\n<!-- Example Model\n");
			writer.write("\n<ProjectX id='ProjectX Unique Identifier'>");
			writer.write("\n<URL+id>RepositoryURL</URL+id>");
			writer.write("\n<repositoryType+id>GITorSVN</repositoryType+id>");
			writer.write("\n</ProjetoX>");
			writer.write("\n-->");
			writer.write("</GiveMeTrace>");
		} catch (IOException ex) {
		  return false;
		} finally {
		   try {
			writer.close();
		} catch (IOException e) {
			return false;
		}
		}

		return true;
	}
	//para escrever 
		public static String escreverArquivo(String caminho, ArrayList<String> texto,boolean append) throws IOException{
			    File arquivo = new File(caminho);
			    if(arquivo.isFile()){
			        arquivo.createNewFile();
			    }//se jÃ¡ existir, serÃ¡ sobreescrito  
			    BufferedWriter bw = new BufferedWriter( new FileWriter(arquivo,append) );  
			    for(String linha:texto){
			    bw.write(linha);  
			    }
			    bw.flush();  
			    bw.close();
			    
			    arquivo=null;
			    return caminho;
		}
			
	public static boolean validFile(String pathToClass){
    	
		String[] dirs = pathToClass.split("/");
		if(dirs.length==0){
			return false;
		}else{
		String className = dirs[dirs.length-1];
		String[] typeClass = className.split("\\.");
        return typeClass.length > 1 && (typeClass[typeClass.length - 1].equals("java"));
		}
    }
	
}