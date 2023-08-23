package givemeviews.model;

import java.util.Comparator;  

public class SortModules implements Comparator<SetModules>{  
      
       public int compare(SetModules a1, SetModules a2){  
            int nomeComparacao = Integer.compare(a2.getOcorrencia(), a1.getOcorrencia()); 
            if (nomeComparacao != 0){  
                return nomeComparacao;  
            }  
            return nomeComparacao;   
        }
		
} 
