package givemetrace.implementations;

import japa.parser.ast.CompilationUnit;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.tmatesoft.svn.core.SVNDepth;
import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.SVNLogEntry;
import org.tmatesoft.svn.core.SVNLogEntryPath;
import org.tmatesoft.svn.core.SVNProperties;
import org.tmatesoft.svn.core.SVNURL;
import org.tmatesoft.svn.core.auth.ISVNAuthenticationManager;
import org.tmatesoft.svn.core.internal.io.dav.DAVRepositoryFactory;
import org.tmatesoft.svn.core.internal.io.fs.FSRepositoryFactory;
import org.tmatesoft.svn.core.internal.io.svn.SVNRepositoryFactoryImpl;
import org.tmatesoft.svn.core.io.SVNRepository;
import org.tmatesoft.svn.core.io.SVNRepositoryFactory;
import org.tmatesoft.svn.core.wc.SVNClientManager;
import org.tmatesoft.svn.core.wc.SVNDiffClient;
import org.tmatesoft.svn.core.wc.SVNRevision;
import org.tmatesoft.svn.core.wc.SVNWCUtil;
public class TraceSvn {
	
	public TraceSvn(){}
	private ArrayList<String> resultMethods;
 	private static int getCommitCount(String url, String name, String password){
    	SVNRepository repository = null;
        int count=0;
             try {
				repository = SVNRepositoryFactory.create( SVNURL.parseURIEncoded( url ) );
				 ISVNAuthenticationManager authManager = SVNWCUtil.createDefaultAuthenticationManager( name, password );
				 repository.setAuthenticationManager( authManager );
				 Collection<?> logEntries = null;
        
				 logEntries = repository.log( new String[] { "" } , null , 1 , -1 , true , true );
				 for(@SuppressWarnings("unused") Object logE:logEntries){
					 count++;
					 }
			} catch (SVNException e) {
				e.printStackTrace();
				return count;
			}
         return count;
    }

    public boolean preCoreToLogClass(String urlRepositorio,String filename,String[] Revisions, String name,String password){
			
    		ArrayList<String> result=new ArrayList<String>();
    		//inicializa o arquivo de log
        	result.add("");
            try {
    			Utils.escreverArquivo(filename, result,false);
    			
    		} catch (IOException e3) {
    			// TODO Auto-generated catch block
    			e3.printStackTrace();
    			//JOptionPane.showMessageDialog(null, "Some error occurred:\n 1-Permission denied to create the File "+filename+"\n 2-Invalid URL to Repository.");
    			return false;//JOptionPane.showMessageDialog(null, "File "+filename+" successfully created!");
    		}
            int count = getCommitCount(urlRepositorio, name, password);
            if(count==0){
            	return false;
            }
            int startRevision=0;
            int endRevision=0;
            
            for(int k=0;k<Revisions.length;k++){
            	if(Revisions[k].contains("-")){
            		String[] rangeRevisions = Revisions[k].split("-");
            		startRevision = Integer.parseInt(rangeRevisions[0]);
            		endRevision = Integer.parseInt(rangeRevisions[1]);
            		}else{
            			startRevision = Integer.parseInt(Revisions[k]);
            			endRevision = Integer.parseInt(Revisions[k]);
            		}
            if(endRevision==0){//if == HEAD
            	endRevision=count;
            }
            if(startRevision==0){//if == HEAD
            	startRevision=count;
            }
            
            TraceSvn svn = new TraceSvn();
            for ( long i = startRevision;i<=endRevision;i++){
            	
            	result = svn.coreToLogClass(urlRepositorio,name,password, i);
            	try {
    				Utils.escreverArquivo(filename, result,true);
    			} catch (IOException e) {
    				// TODO Auto-generated catch block
    				e.printStackTrace();
    				return false;
    			}
            	result.removeAll(result);
            	result = null;
            	result = new ArrayList<String>();
            	System.gc();
            }
            }
        	return true;
    	}
    private ArrayList<String> coreToLogClass(String url, String name, String password,long revision) {
            
            ArrayList<String> result = new ArrayList<String>();
            setupLibrary();        
            SVNRepository repository = null;
            
            try
            {
                repository = SVNRepositoryFactory.create(SVNURL.parseURIEncoded(url));
            } 
            catch (SVNException svne) {
            	System.err.println("error while creating an SVNRepository for the location '" + url + "': " + svne.getMessage());
                return result;
            }
            
            ISVNAuthenticationManager authManager = SVNWCUtil.createDefaultAuthenticationManager(name, password);
            repository.setAuthenticationManager(authManager);

            Collection<?> logEntries = null;
            try 
            {
                logEntries = repository.log(new String[] {""}, null,revision, revision, true, true);
            } 
            catch (SVNException svne) 
            {
                System.out.println("error while collecting log information for '"+ url + "': " + svne.getMessage());
                return result;
            }
            
            //int jump = progressBar.getMaximum()/logEntries.size();
            for (Iterator<?> entries = logEntries.iterator(); entries.hasNext();) 
            {
                SVNLogEntry logEntry = (SVNLogEntry) entries.next();
                result.add("Commit: " + logEntry.getRevision()+"\nAutor: "+logEntry.getAuthor()+"\nDate: "+logEntry.getDate()+"\n\n Mensagem: "+logEntry.getMessage()+"\n");
                MemoryOutput.setStringCase("Start Commit: "+logEntry.getRevision());
                
                System.out.println("\nCommit: " + logEntry.getRevision()); 
                if (logEntry.getChangedPaths().size() > 0) {
                    
                    Set<?> changedPathsSet = logEntry.getChangedPaths().keySet();

                    for (Iterator<?> changedPaths = changedPathsSet.iterator(); changedPaths.hasNext();) 
                    {
                        SVNLogEntryPath entryPath = (SVNLogEntryPath) logEntry.getChangedPaths().get(changedPaths.next());
                        //Seleciona apenas as classes que foram modificadas
                        if(entryPath.getType()=="M".charAt(0)){
                        if(Utils.validFile(  entryPath.getPath()  )){
                        	//pega o caminho para o arquivo	
                    	result.add( "\n"+entryPath.getType()+" "+ entryPath.getPath() );
                    	String[] className = entryPath.getPath().split("/");
                    	MemoryOutput.setStringCase(logEntry.getRevision()+" "+className[className.length-1] );
                    	
            		 }//verifica tipo de arquivo modificado; classe java ou groovy;	
                    }//fim do if (key==M)
                    //////////////////////BLOCO DE EXECUÇÃO DEVE VIR ATÈ AQUI///////////////////
                    }
                }
                result.add("\n\n");
                MemoryOutput.setStringCase("End Commit: "+logEntry.getRevision());
                //vpGiveMeTraceMain.setConsole("End Commit: "+logEntry.getRevision());
            }
            return result;
        }

    public ArrayList<String> preCoreToArrayClass(String urlRepositorio,String[] Revisions, String name,String password){
    		ArrayList<String> result=new ArrayList<String>();
            TraceSvn svn = new TraceSvn();
            ArrayList<String> resultAllCommit = new ArrayList<String>();
            int count = getCommitCount(urlRepositorio, name, password);
            if(count==0){
            	return result;
            }
            int startRevision=0;
            int endRevision=0;
            
            for(int k=0;k<Revisions.length;k++){
            	if(Revisions[k].contains("-")){
            		String[] rangeRevisions = Revisions[k].split("-");
            		startRevision = Integer.parseInt(rangeRevisions[0]);
            		endRevision = Integer.parseInt(rangeRevisions[1]);
            		}else{
            			startRevision = Integer.parseInt(Revisions[k]);
            			endRevision = Integer.parseInt(Revisions[k]);
            		}
            if(endRevision==0){//if == HEAD
            	endRevision=count;
            }
            if(startRevision==0){//if == HEAD
            	startRevision=count;
            }

            
            for ( long i = startRevision;i<=endRevision;i++){
            	
            	result = svn.coreToArrayClass(urlRepositorio,name,password, i);

            	resultAllCommit.addAll(result);
            	result.removeAll(result);
            	result = null;
            	result = new ArrayList<String>();
            	System.gc();
            }
    		}
        	return resultAllCommit;
       }
    private ArrayList<String> coreToArrayClass(String url, String name, String password,long revision) {
                
                ArrayList<String> result = new ArrayList<String>();
                setupLibrary();        
                SVNRepository repository = null;
                
                try
                {
                    repository = SVNRepositoryFactory.create(SVNURL.parseURIEncoded(url));
                } 
                catch (SVNException svne) {
                	System.err.println("error while creating an SVNRepository for the location '" + url + "': " + svne.getMessage());
                    return result;
                }
                
                ISVNAuthenticationManager authManager = SVNWCUtil.createDefaultAuthenticationManager(name, password);
                repository.setAuthenticationManager(authManager);

                Collection<?> logEntries = null;
                try 
                {
                    logEntries = repository.log(new String[] {""}, null,revision, revision, true, true);
                } 
                catch (SVNException svne) 
                {
                    System.out.println("error while collecting log information for '"+ url + "': " + svne.getMessage());
                    return result;
                }
                
                for (Iterator<?> entries = logEntries.iterator(); entries.hasNext();) 
                {
                    SVNLogEntry logEntry = (SVNLogEntry) entries.next();
                    System.out.println("\nCommit: " + logEntry.getRevision()); 
                    if (logEntry.getChangedPaths().size() > 0) {
                        
                        Set<?> changedPathsSet = logEntry.getChangedPaths().keySet();

                        for (Iterator<?> changedPaths = changedPathsSet.iterator(); changedPaths.hasNext();) 
                        {
                            SVNLogEntryPath entryPath = (SVNLogEntryPath) logEntry.getChangedPaths().get(changedPaths.next());
                            //Seleciona apenas as classes que foram modificadas
                            if(entryPath.getType()=="M".charAt(0)){
                            if(Utils.validFile(  entryPath.getPath()  )){
                            	String[] className = entryPath.getPath().split("/");
	           		 			result.add(logEntry.getRevision()+" "+className[className.length-1] );
      		 				}//verifica tipo de arquivo modificado; classe java ou groovy;	
                        }//fim do if (key==M)
                        //////////////////////BLOCO DE EXECUÇÃO DEVE VIR ATÈ AQUI///////////////////
                        }
                    }
                    
                }
                return result;
            }
        
	public ArrayList<String> preCoreToArrayMethod(String urlRepositorio,String[] Revisions, String name,String password){
    		ArrayList<String> result=new ArrayList<String>();
            TraceSvn svn = new TraceSvn();
            ArrayList<String> resultAllCommit = new ArrayList<String>();
            int count = getCommitCount(urlRepositorio, name, password);
            if(count==0){
            	return result;
            }
            int startRevision=0;
            int endRevision=0;
            
            for(int k=0;k<Revisions.length;k++){
            	if(Revisions[k].contains("-")){
            		String[] rangeRevisions = Revisions[k].split("-");
            		startRevision = Integer.parseInt(rangeRevisions[0]);
            		endRevision = Integer.parseInt(rangeRevisions[1]);
            		}else{
            			startRevision = Integer.parseInt(Revisions[k]);
            			endRevision = Integer.parseInt(Revisions[k]);
            		}
            if(endRevision==0){//if == HEAD
            	endRevision=count;
            }
            if(startRevision==0){//if == HEAD
            	startRevision=count;
            }

            
            for ( long i = startRevision;i<=endRevision;i++){
            	
            	result = svn.coreToArrayMethod(urlRepositorio,name,password, i);

            	resultAllCommit.addAll(result);
            	result.removeAll(result);
            	result = null;
            	result = new ArrayList<String>();
            	System.gc();
            }
            }
        	return resultAllCommit;
       }    
    private ArrayList<String> coreToArrayMethod(String url, String name, String password,long revision) {
        resultMethods=null;  
    	resultMethods = new ArrayList<String>();
          ArrayList<String> result = new ArrayList<String>();
          setupLibrary();        
          SVNRepository repository = null;
          Collection<?> logEntries = null;
          SVNProperties fileProperties = new SVNProperties();
          /*Variaveis
           * NEW
           * */
          List<String> methods1 = new ArrayList<String>();
			List<Range> ranges1 = new ArrayList<Range>();
			ByteArrayInputStream solutionNew;
			ByteArrayOutputStream readNewFileOutStream = new ByteArrayOutputStream();
			CompilationUnit cu = null;
			/*Variaveis
	           * OLD
	           * */
			List<String> methodsOld = new ArrayList<String>();
			ByteArrayInputStream solutionOld;
			ByteArrayOutputStream readOldFileOutStream = new ByteArrayOutputStream();
			CompilationUnit cuOld = null;
			
			/*Variaveis
			 * Diff
			 * */
			OutputStream diffOutstream = new ByteArrayOutputStream();
          try
          {
              repository = SVNRepositoryFactory.create(SVNURL.parseURIEncoded(url));
          } 
          catch (SVNException svne) {
          	System.err.println("error while creating an SVNRepository for the location '" + url + "': " + svne.getMessage());
              return result;
          }
          
          ISVNAuthenticationManager authManager = SVNWCUtil.createDefaultAuthenticationManager(name, password);
          repository.setAuthenticationManager(authManager);
  
          
          try 
          {
              logEntries = repository.log(new String[] {""}, null,revision, revision, true, true);
          } 
          catch (SVNException svne) 
          {
              System.out.println("error while collecting log information for '"+ url + "': " + svne.getMessage());
              return result;
          }
          
          
          for (Iterator<?> entries = logEntries.iterator(); entries.hasNext();) 
          {
              SVNLogEntry logEntry = (SVNLogEntry) entries.next();
              System.out.println("\nCommit: " + logEntry.getRevision()); 
              //vpGiveMeTraceMain.setConsole("Start Commit: "+logEntry.getRevision());
              if (logEntry.getChangedPaths().size() > 0) {
                  
                  Set<?> changedPathsSet = logEntry.getChangedPaths().keySet();
  
                  for (Iterator<?> changedPaths = changedPathsSet.iterator(); changedPaths.hasNext();) 
                  {
                      SVNLogEntryPath entryPath = (SVNLogEntryPath) logEntry.getChangedPaths().get(changedPaths.next());
                      //Seleciona apenas as classes que foram modificadas
                      if(entryPath.getType()=="M".charAt(0)){
                      if(Utils.validFile(  entryPath.getPath()  )){
                      	//pega o caminho para o arquivo	
                      //entryPath.getType()+" "+ entryPath.getPath();
                      
                      System.out.println(entryPath.getType()+" "+ entryPath.getPath());
                      
                      //em caso de erro troca o encode//
                      for(String ENCODING:Utils.ENCODECONTROL){
                      //////////////////////////////////////////////////
                      //   BLOCO DO NEW
                      /////////////////////////////////////////////////////////////
                    try {
  							repository.getFile(entryPath.getPath(), logEntry.getRevision(), fileProperties, readNewFileOutStream);
  						} catch (SVNException e1) {
  							// TODO Auto-generated catch block
  							e1.printStackTrace();
  						}
  						
  						solutionNew = new ByteArrayInputStream(readNewFileOutStream.toByteArray());
  						
  							cu = Utils.testecompilationUnit(solutionNew, ENCODING);
  							if(cu==null){
  								System.out.println(" Pulando ENCODE "+ENCODING+"...");
  								try {readNewFileOutStream.close();readNewFileOutStream=new ByteArrayOutputStream();
  								solutionNew.close();} catch (IOException e1) {e1.printStackTrace();}
  							}else{
  								String newFileToString = readNewFileOutStream.toString();
  								
  								System.out.println("Encode correto: "+ENCODING);
  								try {readNewFileOutStream.close();readNewFileOutStream=new ByteArrayOutputStream();
  								solutionNew.close();} catch (IOException e1) {e1.printStackTrace();}
  						
  						MethodVisitor MV = new MethodVisitor(new ArrayList<String>(),new ArrayList<Range>());
  						MV.visit(cu,null);
  						methods1 = MV.getMethodsVisitor();
  						ranges1 = MV.getRangesVisitor();
  					//////////////////////////				
  					//  BLOCO DO OLD
  					/////////////////////////////////////////////////////////
  						try {
  							repository.getFile(entryPath.getPath(), logEntry.getRevision()-1, fileProperties, readOldFileOutStream);
  						} catch (SVNException e1) {
  							// TODO Auto-generated catch block
  							e1.printStackTrace();
  						}
  						solutionOld = new ByteArrayInputStream(readOldFileOutStream.toByteArray());
  							cuOld = Utils.testecompilationUnit(solutionOld, ENCODING);
  							if(cuOld==null){
  								System.out.println(" Pulando ENCODE "+ENCODING+"...");
  								try {readOldFileOutStream.close();readOldFileOutStream=new ByteArrayOutputStream();
  								solutionOld.close();} catch (IOException e1) {e1.printStackTrace();}
  							}else{
  								System.out.println("Encode correto: "+ENCODING);
  								try {readOldFileOutStream.close();readOldFileOutStream=new ByteArrayOutputStream();
  								solutionOld.close();} catch (IOException e1) {e1.printStackTrace();}
  						
  						MethodVisitor MVOld = new MethodVisitor(new ArrayList<String>(),new ArrayList<Range>());
  						MVOld.visit(cuOld,null);
  						methodsOld = MVOld.getMethodsVisitor();
  					/////////////////////////////////
                      //agora aciona o método DIFF passando como parametro o repositório e o path da classe//
                      SVNClientManager clientManager = SVNClientManager.newInstance(SVNWCUtil.createDefaultOptions(true),SVNWCUtil.createDefaultAuthenticationManager("login", "pass"));
             		  SVNDiffClient diffClient = clientManager.getDiffClient();
             		  
             		 	try {
  						diffClient.doDiff(SVNURL.parseURIEncoded(url+entryPath.getPath()),SVNRevision.create(logEntry.getRevision()-1), SVNRevision.create(logEntry.getRevision()-1),SVNRevision.create(logEntry.getRevision()), SVNDepth.INFINITY, true, diffOutstream);
  					} catch (SVNException e) {
  						// TODO Auto-generated catch block
  						System.out.println("Erro no DIFF da classe: "+entryPath.getPath()+"\n");
  						break;
  					}
             		 	List<String> aux = DiffParser.parserDiffGIT(diffOutstream.toString());
             		 	List<Integer> lines = DiffParser.parser(newFileToString, aux);
             		 
             		 	List<String> methodsTemp=new ArrayList<String>();
             		 	List<Range> rangesTemp=new ArrayList<Range>();
          		 	
             		//procura por metodos q foram adicionados para retirar da lista; methods como referencia de comparação
             		 	for(String method:methods1 ){
             		 		if(methodsOld.indexOf(method)!=-1){
             		 			methodsTemp.add(method);
             		 			rangesTemp.add(ranges1.get(methods1.indexOf(method)));
             		 		}else{
             		 			System.out.println("ADICIONADO: "+method+"\n");
             		 		}
             		 	}
             		 //procura por metodos q foram deletados para retirar da lista; 
             		 	for(String methodold:methodsOld ){
             		 		if(methods1.indexOf(methodold)==-1){
             		 			System.out.println("DELETADO: "+methodold+"\n");
             		 		}
             		 	}
             		//verifica a qual range(método) a linha pertence
             		 	
             		 	for(int line:lines){
             		 		for(Range range:rangesTemp){
             		 			
             		 			if(range.inRange(line)){
             		 				String method = methodsTemp.get(rangesTemp.indexOf(range));
             		 				if(resultMethods.indexOf(method)==-1){
  	           		 				resultMethods.add(methodsTemp.get(rangesTemp.indexOf(range)));
  			       		 			String[] className = entryPath.getPath().split("/");
  	           		 				result.add(className[className.length-1]+"|"+methodsTemp.get(rangesTemp.indexOf(range)) );
          		 				
             		 				}
             		 			}
             		 		}
             		 	}
                      break;//em caso de sucesso com o encode escolhido ele segue a análise com as demais classes;
  					}//if cuOld!=null
  					}//if CompilationUnit !=null
                      }//for que troca os encodes para cada tentativa de parse;
                    }//verifica tipo de arquivo modificado; classe java ou groovy;	
                  }//fim do if (key==M)
                  //////////////////////BLOCO DE EXECUÇÃO DEVE VIR ATÈ AQUI///////////////////
                  }
              }
            //vpGiveMeTraceMain.setConsole("End Commit: "+logEntry.getRevision());  
          }
          
          //Fechando as variaveis//
          repository.closeSession();repository = null;
          logEntries.clear();logEntries=null;
          fileProperties.clear();fileProperties=null;
          /*Variaveis
           * NEW
           * */
          	methods1.clear();methods1=null;
			ranges1.clear();ranges1=null;
			//ByteArrayInputStream solutionNew;
			try{readNewFileOutStream.flush();readNewFileOutStream.close();}catch(IOException e){e.printStackTrace();}
			cu = null;
			/*Variaveis
	           * OLD
	           * */
			methodsOld.clear();methodsOld=null;
			//solutionOld;
			try{readOldFileOutStream.flush();readOldFileOutStream.close();}catch(IOException e){e.printStackTrace();}
			cuOld = null;
			
			/*Variaveis
			 * Diff
			 * */
			try{diffOutstream.flush();diffOutstream.close();}catch(IOException e){e.printStackTrace();}
			
          return result;
      }

	public boolean preCoreToLogMethod(String urlRepositorio,String filename,String[] Revisions, String name,String password){
		
		ArrayList<String> result=new ArrayList<String>();
		//inicializa o arquivo de log
    	result.add("");
        try {
			Utils.escreverArquivo(filename, result,false);
			
		} catch (IOException e3) {
			// TODO Auto-generated catch block
			e3.printStackTrace();
			//JOptionPane.showMessageDialog(null, "Some error occurred:\n 1-Permission denied to create the File "+filename+"\n 2-Invalid URL to Repository.");
			return false;//JOptionPane.showMessageDialog(null, "File "+filename+" successfully created!");
		}
        int count = getCommitCount(urlRepositorio, name, password);
        if(count==0){
        	return false;
        }
        int startRevision=0;
        int endRevision=0;
        
        for(int k=0;k<Revisions.length;k++){
        	if(Revisions[k].contains("-")){
        		String[] rangeRevisions = Revisions[k].split("-");
        		startRevision = Integer.parseInt(rangeRevisions[0]);
        		endRevision = Integer.parseInt(rangeRevisions[1]);
        		}else{
        			startRevision = Integer.parseInt(Revisions[k]);
        			endRevision = Integer.parseInt(Revisions[k]);
        		}
        if(endRevision==0){//if == HEAD
        	endRevision=count;
        }
        if(startRevision==0){//if == HEAD
        	startRevision=count;
        }

        
        TraceSvn svn = new TraceSvn();
        for ( long i = startRevision;i<=endRevision;i++){
        	
        	result = svn.coreToLogMethod(urlRepositorio,name,password, i);
        	try {
				Utils.escreverArquivo(filename, result,true);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			}
        	result.removeAll(result);
        	result = null;
        	result = new ArrayList<String>();
        	System.gc();
        }
        }
    	return true;
	}
	private ArrayList<String> coreToLogMethod(String url, String name, String password,long revision) {
	resultMethods=null;  
	resultMethods = new ArrayList<String>();
	  ArrayList<String> result = new ArrayList<String>();
	  setupLibrary();        
	  SVNRepository repository = null;
	  Collection<?> logEntries = null;
	  SVNProperties fileProperties = new SVNProperties();
	  /*Variaveis
	   * NEW
	   * */
	  List<String> methods1 = new ArrayList<String>();
		List<Range> ranges1 = new ArrayList<Range>();
		ByteArrayInputStream solutionNew;
		ByteArrayOutputStream readNewFileOutStream = new ByteArrayOutputStream();
		CompilationUnit cu = null;
		/*Variaveis
	       * OLD
	       * */
		List<String> methodsOld = new ArrayList<String>();
		ByteArrayInputStream solutionOld;
		ByteArrayOutputStream readOldFileOutStream = new ByteArrayOutputStream();
		CompilationUnit cuOld = null;
		
		/*Variaveis
		 * Diff
		 * */
		OutputStream diffOutstream = new ByteArrayOutputStream();
	  try
	  {
	      repository = SVNRepositoryFactory.create(SVNURL.parseURIEncoded(url));
	  } 
	  catch (SVNException svne) {
	  	System.err.println("error while creating an SVNRepository for the location '" + url + "': " + svne.getMessage());
	      return result;
	  }
	  
	  ISVNAuthenticationManager authManager = SVNWCUtil.createDefaultAuthenticationManager(name, password);
	  repository.setAuthenticationManager(authManager);
	
	  
	  try 
	  {
	      logEntries = repository.log(new String[] {""}, null,revision, revision, true, true);
	  } 
	  catch (SVNException svne) 
	  {
	      System.out.println("error while collecting log information for '"+ url + "': " + svne.getMessage());
	      return result;
	  }
	  
	  
	  for (Iterator<?> entries = logEntries.iterator(); entries.hasNext();) 
	  {
	      SVNLogEntry logEntry = (SVNLogEntry) entries.next();
	      result.add("Commit: " + logEntry.getRevision()+"\nAutor: "+logEntry.getAuthor()+"\nDate: "+logEntry.getDate()+"\n\n Mensagem: "+logEntry.getMessage()+"\n");
	      MemoryOutput.setStringCase("Start Commit: "+logEntry.getRevision());
	      
	      System.out.println("\nCommit: " + logEntry.getRevision()); 
	      if (logEntry.getChangedPaths().size() > 0) {
	          
	          Set<?> changedPathsSet = logEntry.getChangedPaths().keySet();
	
	          for (Iterator<?> changedPaths = changedPathsSet.iterator(); changedPaths.hasNext();) 
	          {
	              SVNLogEntryPath entryPath = (SVNLogEntryPath) logEntry.getChangedPaths().get(changedPaths.next());
	              //Seleciona apenas as classes que foram modificadas
	              if(entryPath.getType()=="M".charAt(0)){
	              if(Utils.validFile(  entryPath.getPath()  )){
	              	//pega o caminho para o arquivo	
	              //entryPath.getType()+" "+ entryPath.getPath();
	              System.out.println(entryPath.getType()+" "+ entryPath.getPath());
	              
	              //em caso de erro troca o encode//
	              for(String ENCODING:Utils.ENCODECONTROL){
	              //////////////////////////////////////////////////
	              //   BLOCO DO NEW
	              /////////////////////////////////////////////////////////////
	            try {
							repository.getFile(entryPath.getPath(), logEntry.getRevision(), fileProperties, readNewFileOutStream);
						} catch (SVNException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						
						solutionNew = new ByteArrayInputStream(readNewFileOutStream.toByteArray());
						
							cu = Utils.testecompilationUnit(solutionNew, ENCODING);
							if(cu==null){
								System.out.println(" Pulando ENCODE "+ENCODING+"...");
								try {readNewFileOutStream.close();readNewFileOutStream=new ByteArrayOutputStream();
								solutionNew.close();} catch (IOException e1) {e1.printStackTrace();}
							}else{
								String newFileToString = readNewFileOutStream.toString();
								
								System.out.println("Encode correto: "+ENCODING);
								try {readNewFileOutStream.close();readNewFileOutStream=new ByteArrayOutputStream();
								solutionNew.close();} catch (IOException e1) {e1.printStackTrace();}
						
						MethodVisitor MV = new MethodVisitor(new ArrayList<String>(),new ArrayList<Range>());
						MV.visit(cu,null);
						methods1 = MV.getMethodsVisitor();
						ranges1 = MV.getRangesVisitor();
					//////////////////////////				
					//  BLOCO DO OLD
					/////////////////////////////////////////////////////////
						try {
							repository.getFile(entryPath.getPath(), logEntry.getRevision()-1, fileProperties, readOldFileOutStream);
						} catch (SVNException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						solutionOld = new ByteArrayInputStream(readOldFileOutStream.toByteArray());
							cuOld = Utils.testecompilationUnit(solutionOld, ENCODING);
							if(cuOld==null){
								System.out.println(" Pulando ENCODE "+ENCODING+"...");
								try {readOldFileOutStream.close();readOldFileOutStream=new ByteArrayOutputStream();
								solutionOld.close();} catch (IOException e1) {e1.printStackTrace();}
							}else{
								System.out.println("Encode correto: "+ENCODING);
								try {readOldFileOutStream.close();readOldFileOutStream=new ByteArrayOutputStream();
								solutionOld.close();} catch (IOException e1) {e1.printStackTrace();}
						
						MethodVisitor MVOld = new MethodVisitor(new ArrayList<String>(),new ArrayList<Range>());
						MVOld.visit(cuOld,null);
						methodsOld = MVOld.getMethodsVisitor();
					/////////////////////////////////
	              //agora aciona o método DIFF passando como parametro o repositório e o path da classe//
	              SVNClientManager clientManager = SVNClientManager.newInstance(SVNWCUtil.createDefaultOptions(true),SVNWCUtil.createDefaultAuthenticationManager("login", "pass"));
	     		  SVNDiffClient diffClient = clientManager.getDiffClient();
	     		  
	     		 	try {
						diffClient.doDiff(SVNURL.parseURIEncoded(url+entryPath.getPath()),SVNRevision.create(logEntry.getRevision()-1), SVNRevision.create(logEntry.getRevision()-1),SVNRevision.create(logEntry.getRevision()), SVNDepth.INFINITY, true, diffOutstream);
					} catch (SVNException e) {
						// TODO Auto-generated catch block
						System.out.println("Erro no DIFF da classe: "+entryPath.getPath()+"\n");
						break;
					}
	     		 	List<String> aux = DiffParser.parserDiffGIT(diffOutstream.toString());
	     		 	List<Integer> lines = DiffParser.parser(newFileToString, aux);
	     		 
	     		 	List<String> methodsTemp=new ArrayList<String>();
	     		 	List<Range> rangesTemp=new ArrayList<Range>();
	  		 	
	     		//procura por metodos q foram adicionados para retirar da lista; methods como referencia de comparação
	     		 	for(String method:methods1 ){
	     		 		if(methodsOld.indexOf(method)!=-1){
	     		 			methodsTemp.add(method);
	     		 			rangesTemp.add(ranges1.get(methods1.indexOf(method)));
	     		 		}else{
	     		 			System.out.println("ADICIONADO: "+method+"\n");
	     		 		}
	     		 	}
	     		 //procura por metodos q foram deletados para retirar da lista; 
	     		 	for(String methodold:methodsOld ){
	     		 		if(methods1.indexOf(methodold)==-1){
	     		 			System.out.println("DELETADO: "+methodold+"\n");
	     		 		}
	     		 	}
	     		//verifica a qual range(método) a linha pertence
	     		 	
	     		 	for(int line:lines){
	     		 		for(Range range:rangesTemp){
	     		 			
	     		 			if(range.inRange(line)){
	     		 				String method = methodsTemp.get(rangesTemp.indexOf(range));
	     		 				if(resultMethods.indexOf(method)==-1){
	         		 				resultMethods.add(methodsTemp.get(rangesTemp.indexOf(range)));
			       		 			
	         		 				result.add( "\n"+entryPath.getType()+" "+ entryPath.getPath()+"|"+methodsTemp.get(rangesTemp.indexOf(range)) );
	         		 				String[] className = entryPath.getPath().split("/");
		           		 			MemoryOutput.setStringCase(logEntry.getRevision()+" "+className[className.length-1]+"|"+methodsTemp.get(rangesTemp.indexOf(range)) );
	      		 				
	     		 				}
	     		 			}
	     		 		}
	     		 	}
	              break;//em caso de sucesso com o encode escolhido ele segue a análise com as demais classes;
					}//if cuOld!=null
					}//if CompilationUnit !=null
	              }//for que troca os encodes para cada tentativa de parse;
	            }//verifica tipo de arquivo modificado; classe java ou groovy;	
	          }//fim do if (key==M)
	          //////////////////////BLOCO DE EXECUÇÃO DEVE VIR ATÈ AQUI///////////////////
	              
	          }
	      }
	      
	      MemoryOutput.setStringCase("End Commit: "+logEntry.getRevision() );
	      result.add("\n\n");
	      
	  }
	  //Fechando as variaveis//
	  repository.closeSession();repository = null;
	  logEntries.clear();logEntries=null;
	  fileProperties.clear();fileProperties=null;
	  /*Variaveis
	   * NEW
	   * */
	  	methods1.clear();methods1=null;
		ranges1.clear();ranges1=null;
		//ByteArrayInputStream solutionNew;
		try{readNewFileOutStream.flush();readNewFileOutStream.close();}catch(IOException e){e.printStackTrace();}
		cu = null;
		/*Variaveis
	       * OLD
	       * */
		methodsOld.clear();methodsOld=null;
		//solutionOld;
		try{readOldFileOutStream.flush();readOldFileOutStream.close();}catch(IOException e){e.printStackTrace();}
		cuOld = null;
		
		/*Variaveis
		 * Diff
		 * */
		try{diffOutstream.flush();diffOutstream.close();}catch(IOException e){e.printStackTrace();}
		
	  return result;
	}
	
	
	//SVN core especial para tratar diff entre o HEAD e o workcopy	
    public ArrayList<String> coreToWcHeadArrayMethod(String wcRoot, String url, String name, String password) {
    	File wcRoot2 = new File(wcRoot);
    	resultMethods=null;  
    	resultMethods = new ArrayList<String>();
          ArrayList<String> result = new ArrayList<String>();
          setupLibrary();        
          SVNRepository repository = null;
          //Collection<?> logEntries = null;
          SVNProperties fileProperties = new SVNProperties();
          /*Variaveis
           * NEW
           * */
          List<String> methods1 = new ArrayList<String>();
			List<Range> ranges1 = new ArrayList<Range>();
			FileInputStream solutionNew = null;
			ByteArrayOutputStream readNewFileOutStream = new ByteArrayOutputStream();
			CompilationUnit cu = null;
			/*Variaveis
	           * OLD
	           * */
			List<String> methodsOld = new ArrayList<String>();
			ByteArrayInputStream solutionOld;
			ByteArrayOutputStream readOldFileOutStream = new ByteArrayOutputStream();
			CompilationUnit cuOld = null;
			
			/*Variaveis
			 * Diff
			 * */
			OutputStream diffOutstream = new ByteArrayOutputStream();
          try
          {
              repository = SVNRepositoryFactory.create(SVNURL.parseURIEncoded(url));
          } 
          catch (SVNException svne) {
          	System.err.println("error while creating an SVNRepository for the location '" + url + "': " + svne.getMessage());
              return result;
          }
          
          ISVNAuthenticationManager authManager = SVNWCUtil.createDefaultAuthenticationManager(name, password);
          repository.setAuthenticationManager(authManager);
          //Perform diff WC-HEAD
          try {
        	  SVNClientManager clientManager = SVNClientManager.newInstance();
              SVNDiffClient diffClient = clientManager.getDiffClient();
              diffClient.doDiff(wcRoot2, SVNRevision.UNDEFINED, SVNRevision.WORKING, SVNRevision.HEAD,
                      SVNDepth.INFINITY, true, diffOutstream, null);
              
          } catch (SVNException svne) {
              System.out.println(svne.getErrorMessage());
              System.err.println("error while creating Diff Log for the location '" + url + "': " + svne.getMessage());
              return result;
          }
          
          List<String> diffWcHead = DiffParser.parserSvnDiffWcHead(diffOutstream.toString());
          try {
			diffOutstream.close();
		} catch (IOException e2) {
			System.err.println("error while creating Diff Log for the location '" + url + "': " + e2.getMessage());
            return result;
		}
          diffOutstream=null;
          diffOutstream = new ByteArrayOutputStream();
          
          for (String s : diffWcHead){             
              if(Utils.validFile(  s  )){
                      
              System.out.println(s);
                      
                      for(String ENCODING:Utils.ENCODECONTROL){
                      //////////////////////////////////////////////////
                      //   BLOCO DO NEW///WORK-COPY
                      /////////////////////////////////////////////////////////////
  						String newFileToString = "";
                    	try {
							//FileOutputStream TTTreadNewFileOutStream = new FileOutputStream(new File(s));
							File file = new File(s);
                    		solutionNew = new FileInputStream(file);
							byte fileContent[] = new byte[(int)file.length()];
				
				            // Reads up to certain bytes of data from this input stream into an array of bytes.
				
				            solutionNew.read(fileContent);
				
				            //create string from byte array
				
				            newFileToString = new String(fileContent);
				            solutionNew.close();solutionNew = new FileInputStream(file);
						} catch (IOException e2) {
							// TODO Auto-generated catch block
							e2.printStackTrace();
							
						}
  						//solutionNew = new ByteArrayInputStream(TTTreadNewFileOutStream.toByteArray());
  							cu = Utils.testecompilationUnit(solutionNew, ENCODING);
  							if(cu==null){
  								System.out.println(" Pulando ENCODE "+ENCODING+"...");
  								try {readNewFileOutStream.close();readNewFileOutStream=new ByteArrayOutputStream();
  								solutionNew.close();} catch (IOException e1) {e1.printStackTrace();}
  							}else{
  								
  								System.out.println("Encode correto: "+ENCODING);
  								try {readNewFileOutStream.close();readNewFileOutStream=new ByteArrayOutputStream();
  								solutionNew.close();} catch (IOException e1) {e1.printStackTrace();}
  						
  						MethodVisitor MV = new MethodVisitor(new ArrayList<String>(),new ArrayList<Range>());
  						MV.visit(cu,null);
  						methods1 = MV.getMethodsVisitor();
  						ranges1 = MV.getRangesVisitor();
  					//////////////////////////				
  					//  BLOCO DO OLD
  					/////////////////////////////////////////////////////////
  						try {
  							repository.getFile(s.substring(wcRoot.length(),s.length()), getCommitCount(url, name, password), fileProperties, readOldFileOutStream);
  						} catch (SVNException e1) {
  							// TODO Auto-generated catch block
  							e1.printStackTrace();
  						}
  						solutionOld = new ByteArrayInputStream(readOldFileOutStream.toByteArray());
  							cuOld = Utils.testecompilationUnit(solutionOld, ENCODING);
  							if(cuOld==null){
  								System.out.println(" Pulando ENCODE "+ENCODING+"...");
  								try {readOldFileOutStream.close();readOldFileOutStream=new ByteArrayOutputStream();
  								solutionOld.close();} catch (IOException e1) {e1.printStackTrace();}
  							}else{
  								System.out.println("Encode correto: "+ENCODING);
  								try {readOldFileOutStream.close();readOldFileOutStream=new ByteArrayOutputStream();
  								solutionOld.close();} catch (IOException e1) {e1.printStackTrace();}
  						
  						MethodVisitor MVOld = new MethodVisitor(new ArrayList<String>(),new ArrayList<Range>());
  						MVOld.visit(cuOld,null);
  						methodsOld = MVOld.getMethodsVisitor();
  					/////////////////////////////////
                      //agora aciona o método DIFF passando como parametro o repositório e o path da classe//
  					//Perform diff WC-HEAD on specific File
  			          try {
  			        	  File sDiff= new File(s);
  			        	  SVNClientManager clientManager = SVNClientManager.newInstance();
  			              SVNDiffClient diffClient = clientManager.getDiffClient();
  			              diffClient.doDiff(sDiff, SVNRevision.UNDEFINED, SVNRevision.HEAD, SVNRevision.WORKING,
  			                      SVNDepth.INFINITY, true, diffOutstream, null);
  			          } catch (SVNException svne) {
  			              System.out.println(svne.getErrorMessage());
  			              break;
  			          }
             		 	List<String> aux = DiffParser.parserDiffGIT(diffOutstream.toString());
             		 	List<Integer> lines = DiffParser.parser(newFileToString, aux);
             		 	
             		 	try {
							diffOutstream.close();
						} catch (IOException e) {
							System.err.println("error while creating Diff Log for the location '" + url + "': " + e.getMessage());
				            return result;
						}
             		 	diffOutstream=null;
             		 	diffOutstream = new ByteArrayOutputStream();
             		 	
             		 	List<String> methodsTemp=new ArrayList<String>();
             		 	List<Range> rangesTemp=new ArrayList<Range>();
          		 	
             		//procura por metodos q foram adicionados para retirar da lista; methods como referencia de comparação
             		 	for(String method:methods1 ){
             		 		if(methodsOld.indexOf(method)!=-1){
             		 			methodsTemp.add(method);
             		 			rangesTemp.add(ranges1.get(methods1.indexOf(method)));
             		 		}else{
             		 			System.out.println("ADICIONADO: "+method+"\n");
             		 		}
             		 	}
             		 //procura por metodos q foram deletados para retirar da lista; 
             		 	for(String methodold:methodsOld ){
             		 		if(methods1.indexOf(methodold)==-1){
             		 			System.out.println("DELETADO: "+methodold+"\n");
             		 		}
             		 	}
             		//verifica a qual range(método) a linha pertence
             		 	
             		 	for(int line:lines){
             		 		for(Range range:rangesTemp){
             		 			
             		 			if(range.inRange(line)){
             		 				String method = methodsTemp.get(rangesTemp.indexOf(range));
             		 				if(resultMethods.indexOf(method)==-1){
  	           		 				resultMethods.add(methodsTemp.get(rangesTemp.indexOf(range)));
  			       		 			String[] className = s.split("/");
  	           		 				result.add(className[className.length-1]+"|"+methodsTemp.get(rangesTemp.indexOf(range)) );
  	           		 				System.out.println(result);
          		 					}
             		 			}
             		 		}
             		 	}
                      break;//em caso de sucesso com o encode escolhido ele segue a análise com as demais classes;
  					}//if cuOld!=null
  					}//if CompilationUnit !=null
                      }//for que troca os encodes para cada tentativa de parse;
                    }//verifica tipo de arquivo modificado; classe java ou groovy;	
                  }//fim do for para os todos os arquivos DiffWcHead 
                  //////////////////////BLOCO DE EXECUÇÃO DEVE VIR ATÈ AQUI///////////////////
                  
              
          
          //Fechando as variaveis//
          repository.closeSession();repository = null;
          //logEntries.clear();logEntries=null;
          fileProperties.clear();fileProperties=null;
          /*Variaveis
           * NEW
           * */
          	methods1.clear();methods1=null;
			ranges1.clear();ranges1=null;
			//ByteArrayInputStream solutionNew;
			try{readNewFileOutStream.flush();readNewFileOutStream.close();}catch(IOException e){e.printStackTrace();}
			cu = null;
			/*Variaveis
	           * OLD
	           * */
			methodsOld.clear();methodsOld=null;
			//solutionOld;
			try{readOldFileOutStream.flush();readOldFileOutStream.close();}catch(IOException e){e.printStackTrace();}
			cuOld = null;
			
			/*Variaveis
			 * Diff
			 * */
			try{diffOutstream.flush();diffOutstream.close();}catch(IOException e){e.printStackTrace();}
			
          return result;
      }

	public ArrayList<String> DiffWCToRepository(String wcRoot, String url, String name, String password) {
		   setupLibrary();
		      ArrayList<String> result = new ArrayList<String>();
	       	/*Variaveis
				 * Diff
				 * */
				//OutputStream diffOutstream = new ByteArrayOutputStream();
			
	          SVNRepository repository;
			try
	          {
	              repository = SVNRepositoryFactory.create(SVNURL.parseURIEncoded(url));
	          } 
	          catch (SVNException svne) {
	          	System.err.println("error while creating an SVNRepository for the location '" + url + "': " + svne.getMessage());
	              return result;
	          }
	          
	          ISVNAuthenticationManager authManager = SVNWCUtil.createDefaultAuthenticationManager(name, password);
	          repository.setAuthenticationManager(authManager);
	  
	       File wcRoot2 = new File(wcRoot);   
		   
		   try {
		               //1. first create a repository and fill it with data
//		               SamplesUtility.createRepository(reposRoot);
//		               //2. populate the repository with the greek tree in a single transaction
//		  51             SVNCommitInfo info = SamplesUtility.createRepositoryTree(reposRoot);
//		  52             //pring out the commit information
//		  53             System.out.println(info);
//		  54 
		               //3. checkout the entire repository tree
//		               SVNURL reposURL = SVNURL.fromFile(reposRoot);
//		               SamplesUtility.checkOutWorkingCopy(reposURL, wcRoot);
		   
		               //4. now make some changes to the working copy
//		              SamplesUtility.writeToFile(new File(wcRoot, "iota"), "New text appended to 'iota'", true);
//		               SamplesUtility.writeToFile(new File(wcRoot, "A/mu"), "New text in 'mu'", false);
//		 
		               SVNClientManager clientManager = SVNClientManager.newInstance();
		             //  SVNWCClient wcClient = SVNClientManager.newInstance().getWCClient();
		             //set some property on a working copy directory
		               //wcClient.doSetProperty(new File(wcRoot, "A/B"), "spam", SVNPropertyValue.create("egg"), false,
		                       //SVNDepth.EMPTY, null, null);
		   
		               //5. now run diff the working copy against the repository
		               SVNDiffClient diffClient = clientManager.getDiffClient();
		               /*
		                * This corresponds to 'svn diff -rHEAD'.
		                */
		               diffClient.doDiff(wcRoot2, SVNRevision.UNDEFINED, SVNRevision.WORKING, SVNRevision.HEAD,
		                       SVNDepth.INFINITY, true, System.out, null);
		           } catch (SVNException svne) {
		               System.out.println(svne.getErrorMessage());
		               System.exit(1);
		           }
		return result;
		       }
    
    /*
     * Initializes the library to work with a repository via 
     * different protocols.
     */
    private static void setupLibrary() {
        /*
         * For using over http:// and https://
         */
        DAVRepositoryFactory.setup();
        /*
         * For using over svn:// and svn+xxx://
         */
        SVNRepositoryFactoryImpl.setup();
        
        /*
         * For using over file:///
         */
        FSRepositoryFactory.setup();
    }

}