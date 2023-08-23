package givemetrace.implementations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DiffParser {
	
	static public List<Integer> parser(String completeClass, List<String> linesChanged){
		List<String> linesClass = Arrays.asList(completeClass.split("\n"));
		List<Integer> numLines = new ArrayList<Integer>();
		int j=0;
		for(int i=0;i<linesClass.size();i++){
			//int i = linesClass.indexOf(lineChanged);//PROBLEMA!! indexOf pega a primeira ocorrencia, desconsidera linhas novas iguais porém em metodos diferentes
			String lclass = linesClass.get(i);
			if(j==linesChanged.size()){
				break;
			}
			String lchanged = linesChanged.get(j);
			if(lclass.equals(lchanged)){
				j++;
				numLines.add(i+1);//tem este +1 pois o Range é contato a partir da primeira linha como sendo 1 e os manipuladores iniciam com 0.
			}
		}
		return numLines;
		
	}

	static public List<String> parserDiffSVN(String path, String DIFF){
		//DIFF é arquivo inteiro de diferenças
		List<String> linesDiff = Arrays.asList(DIFF.split(System.getProperty("line.separator")));
		path="Index: "+path.substring(1);
		//System.out.print(path+"   path com + index\n");
		int i = linesDiff.indexOf(path);
		if(i==-1){//windows
			i = linesDiff.indexOf(path+"\r");
		}
		//System.out.print(i);
		//tratar depois se i=-1...
		List<String> linesChanged=new ArrayList<String>();
		for(i++;i<linesDiff.size();i++){
			if(!linesDiff.get(i).startsWith("Index: ")){
			while(linesDiff.get(i).startsWith("===")||linesDiff.get(i).startsWith("+++")||linesDiff.get(i).startsWith("---")||linesDiff.get(i).startsWith("@@")){
				i++;
			}
			if(linesDiff.get(i).startsWith("+")){
				linesChanged.add(linesDiff.get(i).substring(1));
			}
			}
		}
		return linesChanged;
	}
	
	
	static public List<String> parserDiffGIT(String DIFF){
		//DIFF é arquivo inteiro de diferenças
		List<String> linesDiff = Arrays.asList(DIFF.split("\n"));
		List<String> linesChanged=new ArrayList<String>();
		for(int i=0;i<linesDiff.size();i++){
			
			while(linesDiff.get(i).startsWith("diff")||linesDiff.get(i).startsWith("index")||linesDiff.get(i).startsWith("Index:")||linesDiff.get(i).startsWith("+++")||linesDiff.get(i).startsWith("---")||linesDiff.get(i).startsWith("@@")){
				i++;
			}
			if(linesDiff.get(i).startsWith("+")){
				linesChanged.add(linesDiff.get(i).substring(1));
			}
			
		}
		return linesChanged;
	}
	
	static public List<String> parserSvnDiffWcHead(String DIFF){
		//DIFF é arquivo inteiro de diferenças
		List<String> linesDiff = Arrays.asList(DIFF.split("\n"));
		List<String> indexPaths=new ArrayList<String>();
		for(int i=0;i<linesDiff.size();i++){
			if(linesDiff.get(i).startsWith("Index: ")){
				if(linesDiff.get(i+2).startsWith("---")){
					indexPaths.add(linesDiff.get(i).replace("Index: ", "").replaceAll("\r", ""));
				}
			}
		}
		return indexPaths;
	}

}
