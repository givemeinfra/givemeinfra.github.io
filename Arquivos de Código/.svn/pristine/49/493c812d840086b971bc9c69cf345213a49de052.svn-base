package givemetrace.implementations;

import japa.parser.ast.CompilationUnit;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.NoHeadException;
import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.diff.DiffFormatter;
import org.eclipse.jgit.errors.IncorrectObjectTypeException;
import org.eclipse.jgit.errors.MissingObjectException;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectLoader;
import org.eclipse.jgit.lib.ObjectReader;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevCommitList;
import org.eclipse.jgit.revwalk.RevTree;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.eclipse.jgit.treewalk.AbstractTreeIterator;
import org.eclipse.jgit.treewalk.CanonicalTreeParser;
import org.eclipse.jgit.treewalk.TreeWalk;
import org.eclipse.jgit.treewalk.filter.PathFilter;




/**
 * mostrar os metodos que foram alterados - Repositorio GIT
 * 
 * @author claudio
 */
public class TraceGit {
	
	static private ArrayList<String> resultMethods;
	public TraceGit(){}
    
	public boolean preCoreToLogMethod(String urlRepositorio,String filename,String[] Revisions){
		
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
        int count = getCountCommit(urlRepositorio);
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
        	endRevision=1;
        }else{
        	endRevision=count-endRevision;
        }
        if(startRevision==0){//if == HEAD
        	startRevision=1;
        }else{
        	startRevision=count-startRevision;
        }
        
        TraceGit git = new TraceGit();
        for ( int i = startRevision;i>=endRevision;i--){
        	System.out.println("Valor de i: "+i);
        	result = git.coreToLogMethod(urlRepositorio, i);
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
	private ArrayList<String> coreToLogMethod(String urlRepositorio, int Revision){
        int count =getCountCommit(urlRepositorio); 
        resultMethods=new ArrayList<String>();
        ArrayList<String> result=new ArrayList<String>();
        
        FileRepositoryBuilder builder = new FileRepositoryBuilder();
        Repository repository = null;
		try {
			repository = builder.setGitDir(new File(urlRepositorio))
				  	.setMustExist(true)
			        .readEnvironment() // scan environment GIT_* variables
			        .findGitDir() // scan up the file system tree
			        .build();
		} catch (IOException e2) {
			JOptionPane.showMessageDialog(null, "There is a problem with Repository URL. Repository does not exist.");
			return result;
		}
		
        ///////WALK_ALL_COMMITS.JAVA//////////////
        Git git = new Git(repository);
        Iterable<RevCommit> commits = null;
		try {
			commits = git.log().all().call();
		} catch (NoHeadException e1) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(null, "There is a problem with Repository URL. Please check and try again.");
			return result;
		} catch (GitAPIException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "There was a problem to get Repository Log Information.");
			return result;
		}
        RevCommitList<RevCommit> ids = new RevCommitList<RevCommit>();
        //int count = 0;
        for(RevCommit rev:commits){
        	ids.add(rev);
        }
        //for (int i=1;i<ids.size();i++) {
        System.out.println("CommitNew: "+ids.get(Revision-1).getName()+"\nDateNew: "+ids.get(Revision-1).getAuthorIdent().getWhen());
        System.out.println("CommitOld: "+ids.get(Revision).getName()+"\nDateOld: "+ids.get(Revision).getAuthorIdent().getWhen());
        	result.add("Commit: " + ids.get(Revision-1).getName()  + "\nAutor: " + ids.get(Revision-1).getAuthorIdent().getName() + "\nDate: " + ids.get(Revision-1).getAuthorIdent().getWhen()+"\n\n Mensagem: "+ids.get(Revision-1).getFullMessage() );            
        	MemoryOutput.setStringCase("Start Commit: "+(count-Revision) ); 
        	
        	ObjectId id = ids.get(Revision-1).getId();
        	ObjectId id2 = ids.get(Revision).getId();
        	/////////////////////////////////////////        
        // the diff works on TreeIterators, we prepare two for the two branches
        AbstractTreeIterator oldTreeParser = null;
		AbstractTreeIterator newTreeParser = null;
		try {
			oldTreeParser = prepareTreeParser(repository, id2.getName() );
			newTreeParser = prepareTreeParser(repository, id.getName() );
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "There is a problem with Repository URL. Please check and try again.");
			return result;
		}

        // then the procelain diff-command returns a list of diff entries
        List<DiffEntry> diff = null;
		try {
			diff = new Git(repository).diff().
			        setOldTree(oldTreeParser).
			        setNewTree(newTreeParser).
			        //setPathFilter(PathFilter.create("README.md")).
			        call();
		} catch (GitAPIException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		for (DiffEntry entry : diff) {
	          if (entry.getChangeType().name().equals("MODIFY")){
	            if(Utils.validFile( entry.getNewPath() )){
	        	String path = "M "+entry.getNewPath();  
	        	System.out.println(path);
	            //em caso de erro troca o encode//
	            for(String ENCODING:Utils.ENCODECONTROL){
	        	
	            /////////BLOCO DO NEW//////////////
	            	///////////////////
	        	OutputStream diffOutStream;
				// and then one can the loader to read the file
				ByteArrayOutputStream readNewFileOutStream;
				try {
					diffOutStream = new ByteArrayOutputStream();
					DiffFormatter formatter = new DiffFormatter(diffOutStream);
					formatter.setRepository(repository);
					formatter.format(entry);
					        /////////////////////////////////////READ_FILE_FROM_COMMIT///////////////////////
					// a RevWalk allows to walk over commits based on some filtering that is defined
					RevWalk revWalk = new RevWalk(repository);
					RevCommit commit = revWalk.parseCommit(id);//?????? interesse pelo commit mais novo do qual retiraremos os files para comparar com o q vem do diff com o daquele commit;
					// and using commit's tree find the path
					//RevTree tree = commitnew.getTree();
					//System.out.println("Having tree: " + tree);

					// now try to find a specific file
					TreeWalk treeWalk = new TreeWalk(repository);
					treeWalk.addTree(commit.getTree());
					treeWalk.setRecursive(true);
					treeWalk.setFilter(PathFilter.create(entry.getNewPath() ) );
					if (!treeWalk.next()) {
					    break;
						//throw new IllegalStateException("Did not find expected file "+entry.getNewPath());
					}

					ObjectId objectId = treeWalk.getObjectId(0);
					ObjectLoader loader = repository.open(objectId);

					readNewFileOutStream = new ByteArrayOutputStream();
					loader.copyTo(readNewFileOutStream);
				} catch (IOException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
					break;
				}
	            //Biblioteca Externa JAVAPARSER
	            
	            InputStream solutionNew = new ByteArrayInputStream(readNewFileOutStream.toByteArray());

	            CompilationUnit cu = null;
				cu = Utils.testecompilationUnit(solutionNew, ENCODING);
				if(cu==null){
					System.out.println(" Pulando ENCODE "+ENCODING+"...");
				}else{
					System.out.println("Encode correto: "+ENCODING);
					try {
						solutionNew.close();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
	            
	            // visit and print the methods names
	            MethodVisitor MV = new MethodVisitor(new ArrayList<String>(),new ArrayList<Range>());
	            MV.visit(cu,null);
	            List<String> methods1 = MV.getMethodsVisitor();
	            List<Range> ranges1 = MV.getRangesVisitor();

	            //////////////////////////////
	            ////////////BLOCO DO OLD////////
		        /////////////////////////////////////READ_FILE_FROM_COMMIT///////////////////////
	    // a RevWalk allows to walk over commits based on some filtering that is defined
	    // and then one can the loader to read the file
		ByteArrayOutputStream readOldFileOutStream = null;
		try {
			RevWalk revWalkOld = new RevWalk(repository);
			RevCommit commitOld = revWalkOld.parseCommit(id2);//?????? interesse pelo commit mais novo do qual retiraremos os files para comparar com o q vem do diff com o daquele commit;
			// and using commit's tree find the path
			//RevTree treeOld = commitold.getTree();

			// now try to find a specific file
			TreeWalk treeWalkOld = new TreeWalk(repository);
			treeWalkOld.addTree(commitOld.getTree());
			treeWalkOld.setRecursive(true);
			treeWalkOld.setFilter(PathFilter.create(entry.getNewPath() ) );
			if (!treeWalkOld.next()) {
			    break;
				//throw new IllegalStateException("Did not find expected file "+entry.getNewPath());
			}

			ObjectId objectIdOld = treeWalkOld.getObjectId(0);
			ObjectLoader loaderOld = repository.open(objectIdOld);

			readOldFileOutStream = new ByteArrayOutputStream();
			loaderOld.copyTo(readOldFileOutStream);
			//Biblioteca Externa JAVAPARSER
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		
	    InputStream solutionOld = new ByteArrayInputStream(readOldFileOutStream.toByteArray());

		    CompilationUnit cuOld = null;
			cuOld = Utils.testecompilationUnit(solutionOld, ENCODING);
			if(cuOld==null){
				System.out.println(" Pulando ENCODE "+ENCODING+"...");
			}else{
				System.out.println("Encode correto: "+ENCODING);
				try {
					solutionOld.close();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
	    
	    
	    MethodVisitor MVOld = new MethodVisitor(new ArrayList<String>(),new ArrayList<Range>());
	    MVOld.visit(cuOld,null);
	    List<String> methodsOld = MVOld.getMethodsVisitor();
	    //////////////////////     FIM    ///////////////////////

	         ///////////////////////////////
	         List<String> aux = DiffParser.parserDiffGIT(diffOutStream.toString());
		 	 List<Integer> lines = DiffParser.parser(readNewFileOutStream.toString(), aux);
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
		   		 			result.add("\n"+path+"|"+methodsTemp.get(rangesTemp.indexOf(range))  );
		   		 			String[] className = entry.getNewPath().split("/");
		   		 			MemoryOutput.setStringCase((count-Revision)+" "+className[className.length-1]+"|"+methodsTemp.get(rangesTemp.indexOf(range)) );
		   		 			
			 				}
	   		 			}
	   		 		}
	   		 	}
	   		 	break;//em caso de sucesso com o encode escolhido ele segue a análise com as demais classes;
				}//if cuOld!=null
				}//if CompilationUnit !=null
	            }//for que troca os encodes para cada tentativa de parse;
	          	}//if tipo == java ou groovy
	           }//if MODIFY
	        }//for DiffEntry
        result.add("\n\n");
        MemoryOutput.setStringCase("End Commit: "+(count-Revision) );
        resultMethods= new ArrayList<String>();
        
        //}
		
       
       repository.close();
       return result; 
    }
    
	public boolean preCoreToLogClass(String urlRepositorio,String filename,String[] Revisions){
		
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
        int count = getCountCommit(urlRepositorio);
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
        	endRevision=1;
        }else{
        	endRevision=count-endRevision;
        }
        if(startRevision==0){//if == HEAD
        	startRevision=1;
        }else{
        	startRevision=count-startRevision;
        }
        
        TraceGit git = new TraceGit();
        for ( int i = startRevision;i>=endRevision;i--){
        	System.out.println("Valor de i: "+i);
        	result = git.coreToLogClass(urlRepositorio, i);
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
	private ArrayList<String> coreToLogClass(String urlRepositorio, int Revision){
        int count = getCountCommit(urlRepositorio);
		ArrayList<String> result=new ArrayList<String>();
        
        FileRepositoryBuilder builder = new FileRepositoryBuilder();
        Repository repository = null;
		try {
			repository = builder.setGitDir(new File(urlRepositorio))
				  	.setMustExist(true)
			        .readEnvironment() // scan environment GIT_* variables
			        .findGitDir() // scan up the file system tree
			        .build();
		} catch (IOException e2) {
			JOptionPane.showMessageDialog(null, "There is a problem with Repository URL. Repository does not exist.");
			return result;
		}
		Git git = new Git(repository);
        Iterable<RevCommit> commits = null;
		try {
			commits = git.log().all().call();
		} catch (NoHeadException e1) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(null, "There is a problem with Repository URL. Please check and try again.");
			return result;
		} catch (GitAPIException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "There was a problem to get Repository Log Information.");
			return result;
		}
        RevCommitList<RevCommit> ids = new RevCommitList<RevCommit>();
        for(RevCommit rev:commits){
        	ids.add(rev);
        }
        
        System.out.println("CommitNew: "+ids.get(Revision-1).getName()+"\nDateNew: "+ids.get(Revision-1).getAuthorIdent().getWhen());
        System.out.println("CommitOld: "+ids.get(Revision).getName()+"\nDateOld: "+ids.get(Revision).getAuthorIdent().getWhen());
        	result.add("Commit: " + ids.get(Revision-1).getName()  + "\nAutor: " + ids.get(Revision-1).getAuthorIdent().getName() + "\nDate: " + ids.get(Revision-1).getAuthorIdent().getWhen()+"\n\n Mensagem: "+ids.get(Revision-1).getFullMessage() );            
        	MemoryOutput.setStringCase("Start Commit: "+(count-Revision) );
        	
        	ObjectId id = ids.get(Revision-1).getId();
        	ObjectId id2 = ids.get(Revision).getId();
        	/////////////////////////////////////////        
        // the diff works on TreeIterators, we prepare two for the two branches
        AbstractTreeIterator oldTreeParser = null;
		AbstractTreeIterator newTreeParser = null;
		try {
			oldTreeParser = prepareTreeParser(repository, id2.getName() );
			newTreeParser = prepareTreeParser(repository, id.getName() );
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "There is a problem with Repository URL. Please check and try again.");
			return result;
		}

        // then the procelain diff-command returns a list of diff entries
        List<DiffEntry> diff = null;
		try {
			diff = new Git(repository).diff().
			        setOldTree(oldTreeParser).
			        setNewTree(newTreeParser).
			        //setPathFilter(PathFilter.create("README.md")).
			        call();
		} catch (GitAPIException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		for (DiffEntry entry : diff) {
	          if (entry.getChangeType().name().equals("MODIFY")){
	            if(Utils.validFile( entry.getNewPath() )){
	        	String path = "M "+entry.getNewPath();  
	        	System.out.println(path);
	        	result.add("\n"+path);
	        	String[] className = entry.getNewPath().split("/");
	 			MemoryOutput.setStringCase((count-Revision)+" "+className[className.length-1] );
	          	}//if tipo == java ou groovy
	           }//if MODIFY
	        }//for DiffEntry
        result.add("\n\n");
        MemoryOutput.setStringCase("End Commit: "+(count-Revision));
        repository.close();
       return result; 
    }
     
	public ArrayList<String> preCoreToArrayMethod(String urlRepositorio,String[] Revisions){
		
		ArrayList<String> result=new ArrayList<String>();
		 TraceGit git = new TraceGit();
        ArrayList<String> resultAllCommit = new ArrayList<String>();
        int count = getCountCommit(urlRepositorio);
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
        	endRevision=1;
        }else{
        	endRevision=count-endRevision;
        }
        if(startRevision==0){//if == HEAD
        	startRevision=1;
        }else{
        	startRevision=count-startRevision;
        }
        
        
        for ( int i = startRevision;i>=endRevision;i--){
        	
        	result = git.coreToArrayMethod(urlRepositorio, i);
        	
        	resultAllCommit.addAll(result);
        	result.removeAll(result);
        	result = null;
        	result = new ArrayList<String>();
        	System.gc();
        }
        }
    	return resultAllCommit;
	}
	private ArrayList<String> coreToArrayMethod(String urlRepositorio, int Revision){
        int count =getCountCommit(urlRepositorio); 
        resultMethods=new ArrayList<String>();
        ArrayList<String> result=new ArrayList<String>();
        
        FileRepositoryBuilder builder = new FileRepositoryBuilder();
        Repository repository = null;
		try {
			repository = builder.setGitDir(new File(urlRepositorio))
				  	.setMustExist(true)
			        .readEnvironment() // scan environment GIT_* variables
			        .findGitDir() // scan up the file system tree
			        .build();
		} catch (IOException e2) {
			JOptionPane.showMessageDialog(null, "There is a problem with Repository URL. Repository does not exist.");
			return result;
		}
		
        ///////WALK_ALL_COMMITS.JAVA//////////////
        Git git = new Git(repository);
        Iterable<RevCommit> commits = null;
		try {
			commits = git.log().all().call();
		} catch (NoHeadException e1) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(null, "There is a problem with Repository URL. Please check and try again.");
			return result;
		} catch (GitAPIException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "There was a problem to get Repository Log Information.");
			return result;
		}
        RevCommitList<RevCommit> ids = new RevCommitList<RevCommit>();
        
        for(RevCommit rev:commits){
        	ids.add(rev);
        }
        
        System.out.println("CommitNew: "+ids.get(Revision-1).getName()+"\nDateNew: "+ids.get(Revision-1).getAuthorIdent().getWhen());
        System.out.println("CommitOld: "+ids.get(Revision).getName()+"\nDateOld: "+ids.get(Revision).getAuthorIdent().getWhen());
        	//result.add("Commit: " + ids.get(Revision-1).getName()  + "\nAutor: " + ids.get(Revision-1).getAuthorIdent().getName() + "\nDate: " + ids.get(Revision-1).getAuthorIdent().getWhen()+"\n\n Mensagem: "+ids.get(Revision-1).getFullMessage() );            
                
        	ObjectId id = ids.get(Revision-1).getId();
        	ObjectId id2 = ids.get(Revision).getId();
        	/////////////////////////////////////////        
        // the diff works on TreeIterators, we prepare two for the two branches
        AbstractTreeIterator oldTreeParser = null;
		AbstractTreeIterator newTreeParser = null;
		try {
			oldTreeParser = prepareTreeParser(repository, id2.getName() );
			newTreeParser = prepareTreeParser(repository, id.getName() );
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "There is a problem with Repository URL. Please check and try again.");
			return result;
		}

        // then the procelain diff-command returns a list of diff entries
        List<DiffEntry> diff = null;
		try {
			diff = new Git(repository).diff().
			        setOldTree(oldTreeParser).
			        setNewTree(newTreeParser).
			        //setPathFilter(PathFilter.create("README.md")).
			        call();
		} catch (GitAPIException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		for (DiffEntry entry : diff) {
	          if (entry.getChangeType().name().equals("MODIFY")){
	            if(Utils.validFile( entry.getNewPath() )){
	        	String path = "M "+entry.getNewPath();  
	        	System.out.println(path);
	            //em caso de erro troca o encode//
	            for(String ENCODING:Utils.ENCODECONTROL){
	        	
	            /////////BLOCO DO NEW//////////////
	            	///////////////////
	        	OutputStream diffOutStream;
				// and then one can the loader to read the file
				ByteArrayOutputStream readNewFileOutStream;
				try {
					diffOutStream = new ByteArrayOutputStream();
					DiffFormatter formatter = new DiffFormatter(diffOutStream);
					formatter.setRepository(repository);
					formatter.format(entry);
					        /////////////////////////////////////READ_FILE_FROM_COMMIT///////////////////////
					// a RevWalk allows to walk over commits based on some filtering that is defined
					RevWalk revWalk = new RevWalk(repository);
					RevCommit commit = revWalk.parseCommit(id);//?????? interesse pelo commit mais novo do qual retiraremos os files para comparar com o q vem do diff com o daquele commit;
					// and using commit's tree find the path
					//RevTree tree = commitnew.getTree();
					//System.out.println("Having tree: " + tree);

					// now try to find a specific file
					TreeWalk treeWalk = new TreeWalk(repository);
					treeWalk.addTree(commit.getTree());
					treeWalk.setRecursive(true);
					treeWalk.setFilter(PathFilter.create(entry.getNewPath() ) );
					if (!treeWalk.next()) {
					    break;
						//throw new IllegalStateException("Did not find expected file "+entry.getNewPath());
					}

					ObjectId objectId = treeWalk.getObjectId(0);
					ObjectLoader loader = repository.open(objectId);

					readNewFileOutStream = new ByteArrayOutputStream();
					loader.copyTo(readNewFileOutStream);
				} catch (IOException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
					break;
				}
	            //Biblioteca Externa JAVAPARSER
	            
	            InputStream solutionNew = new ByteArrayInputStream(readNewFileOutStream.toByteArray());

	            CompilationUnit cu = null;
				cu = Utils.testecompilationUnit(solutionNew, ENCODING);
				if(cu==null){
					System.out.println(" Pulando ENCODE "+ENCODING+"...");
				}else{
					System.out.println("Encode correto: "+ENCODING);
					try {
						solutionNew.close();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
	            
	            // visit and print the methods names
	            MethodVisitor MV = new MethodVisitor(new ArrayList<String>(),new ArrayList<Range>());
	            MV.visit(cu,null);
	            List<String> methods1 = MV.getMethodsVisitor();
	            List<Range> ranges1 = MV.getRangesVisitor();

	            //////////////////////////////
	            ////////////BLOCO DO OLD////////
		        /////////////////////////////////////READ_FILE_FROM_COMMIT///////////////////////
	    // a RevWalk allows to walk over commits based on some filtering that is defined
	    // and then one can the loader to read the file
		ByteArrayOutputStream readOldFileOutStream = null;
		try {
			RevWalk revWalkOld = new RevWalk(repository);
			RevCommit commitOld = revWalkOld.parseCommit(id2);//?????? interesse pelo commit mais novo do qual retiraremos os files para comparar com o q vem do diff com o daquele commit;
			// and using commit's tree find the path
			//RevTree treeOld = commitold.getTree();

			// now try to find a specific file
			TreeWalk treeWalkOld = new TreeWalk(repository);
			treeWalkOld.addTree(commitOld.getTree());
			treeWalkOld.setRecursive(true);
			treeWalkOld.setFilter(PathFilter.create(entry.getNewPath() ) );
			if (!treeWalkOld.next()) {
			    break;
				//throw new IllegalStateException("Did not find expected file "+entry.getNewPath());
			}

			ObjectId objectIdOld = treeWalkOld.getObjectId(0);
			ObjectLoader loaderOld = repository.open(objectIdOld);

			readOldFileOutStream = new ByteArrayOutputStream();
			loaderOld.copyTo(readOldFileOutStream);
			//Biblioteca Externa JAVAPARSER
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		
	    InputStream solutionOld = new ByteArrayInputStream(readOldFileOutStream.toByteArray());

		    CompilationUnit cuOld = null;
			cuOld = Utils.testecompilationUnit(solutionOld, ENCODING);
			if(cuOld==null){
				System.out.println(" Pulando ENCODE "+ENCODING+"...");
			}else{
				System.out.println("Encode correto: "+ENCODING);
				try {
					solutionOld.close();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
	    
	    
	    MethodVisitor MVOld = new MethodVisitor(new ArrayList<String>(),new ArrayList<Range>());
	    MVOld.visit(cuOld,null);
	    List<String> methodsOld = MVOld.getMethodsVisitor();
	    //////////////////////     FIM    ///////////////////////

	         ///////////////////////////////
	         List<String> aux = DiffParser.parserDiffGIT(diffOutStream.toString());
		 	 List<Integer> lines = DiffParser.parser(readNewFileOutStream.toString(), aux);
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
	   		 				String[] className = entry.getNewPath().split("/");
     		 				result.add(className[className.length-1]+"|"+methodsTemp.get(rangesTemp.indexOf(range)) );
	   		 				}
	   		 			}
	   		 		}
	   		 	}
	   		 	break;//em caso de sucesso com o encode escolhido ele segue a análise com as demais classes;
				}//if cuOld!=null
				}//if CompilationUnit !=null
	            }//for que troca os encodes para cada tentativa de parse;
	          	}//if tipo == java ou groovy
	           }//if MODIFY
	        }//for DiffEntry
        
       repository.close();
       return result; 
    }
    
	public ArrayList<String> preCoreToArrayClass(String urlRepositorio,String[] Revisions){
		
		ArrayList<String> result=new ArrayList<String>();
		TraceGit git = new TraceGit();
        ArrayList<String> resultAllCommit = new ArrayList<String>();
        int count = getCountCommit(urlRepositorio);
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
        	endRevision=1;
        }else{
        	endRevision=count-endRevision;
        }
        if(startRevision==0){//if == HEAD
        	startRevision=1;
        }else{
        	startRevision=count-startRevision;
        }
        
        
        for ( int i = startRevision;i>=endRevision;i--){
        	
        	result = git.coreToArrayClass(urlRepositorio, i);
        	
        	resultAllCommit.addAll(result);
        	result.removeAll(result);
        	result = null;
        	result = new ArrayList<String>();
        	System.gc();
        }
        }
    	return resultAllCommit;
	}
	private ArrayList<String> coreToArrayClass(String urlRepositorio, int Revision){
        int count = getCountCommit(urlRepositorio);
		ArrayList<String> result=new ArrayList<String>();
        
        FileRepositoryBuilder builder = new FileRepositoryBuilder();
        Repository repository = null;
		try {
			repository = builder.setGitDir(new File(urlRepositorio))
				  	.setMustExist(true)
			        .readEnvironment() // scan environment GIT_* variables
			        .findGitDir() // scan up the file system tree
			        .build();
		} catch (IOException e2) {
			JOptionPane.showMessageDialog(null, "There is a problem with Repository URL. Repository does not exist.");
			return result;
		}
		
        ///////WALK_ALL_COMMITS.JAVA//////////////
        Git git = new Git(repository);
        Iterable<RevCommit> commits = null;
		try {
			commits = git.log().all().call();
		} catch (NoHeadException e1) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(null, "There is a problem with Repository URL. Please check and try again.");
			return result;
		} catch (GitAPIException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "There was a problem to get Repository Log Information.");
			return result;
		}
        RevCommitList<RevCommit> ids = new RevCommitList<RevCommit>();
        
        for(RevCommit rev:commits){
        	ids.add(rev);
        }
        
        System.out.println("CommitNew: "+ids.get(Revision-1).getName()+"\nDateNew: "+ids.get(Revision-1).getAuthorIdent().getWhen());
        System.out.println("CommitOld: "+ids.get(Revision).getName()+"\nDateOld: "+ids.get(Revision).getAuthorIdent().getWhen());
        	//result.add("Commit: " + ids.get(Revision-1).getName()  + "\nAutor: " + ids.get(Revision-1).getAuthorIdent().getName() + "\nDate: " + ids.get(Revision-1).getAuthorIdent().getWhen()+"\n\n Mensagem: "+ids.get(Revision-1).getFullMessage() );            
                
        	ObjectId id = ids.get(Revision-1).getId();
        	ObjectId id2 = ids.get(Revision).getId();
        	/////////////////////////////////////////        
        // the diff works on TreeIterators, we prepare two for the two branches
        AbstractTreeIterator oldTreeParser = null;
		AbstractTreeIterator newTreeParser = null;
		try {
			oldTreeParser = prepareTreeParser(repository, id2.getName() );
			newTreeParser = prepareTreeParser(repository, id.getName() );
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "There is a problem with Repository URL. Please check and try again.");
			return result;
		}

        // then the procelain diff-command returns a list of diff entries
        List<DiffEntry> diff = null;
		try {
			diff = new Git(repository).diff().
			        setOldTree(oldTreeParser).
			        setNewTree(newTreeParser).
			        //setPathFilter(PathFilter.create("README.md")).
			        call();
		} catch (GitAPIException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		for (DiffEntry entry : diff) {
	          if (entry.getChangeType().name().equals("MODIFY")){
	            if(Utils.validFile( entry.getNewPath() )){
	        	
	        	String[] className = entry.getNewPath().split("/");
	 			result.add((count-Revision)+" "+className[className.length-1] );
	 			}//if tipo == java ou groovy
	           }//if MODIFY
	        }//for DiffEntry
        
       repository.close();
       return result; 
    }
    
	
    private static AbstractTreeIterator prepareTreeParser(Repository repository, String objectId) throws IOException,
            MissingObjectException,
            IncorrectObjectTypeException {
        // from the commit we can build the tree which allows us to construct the TreeParser
        RevWalk walk = new RevWalk(repository);
        RevCommit commit = walk.parseCommit(ObjectId.fromString(objectId));
        RevTree tree = walk.parseTree(commit.getTree().getId());

        CanonicalTreeParser oldTreeParser = new CanonicalTreeParser();
        ObjectReader oldReader = repository.newObjectReader();
        try {
            oldTreeParser.reset(oldReader, tree.getId());
        } finally {
            oldReader.release();
        }
        
        walk.dispose();

        return oldTreeParser;
    }


    private static int getCountCommit(String urlRepositorio){
       int i=0;
		FileRepositoryBuilder builder = new FileRepositoryBuilder();
        Repository repository;
		try {
			repository = builder.setGitDir(new File(urlRepositorio))
				  	.setMustExist(true)
			        .readEnvironment() // scan environment GIT_* variables
			        .findGitDir() // scan up the file system tree
			        .build();
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
			return i;
		}
        ///////WALK_ALL_COMMITS.JAVA//////////////
        Git git = new Git(repository);
        Iterable<RevCommit> commits = null;
		try {
			commits = git.log().all().call();
		} catch (NoHeadException e1) {
			JOptionPane.showMessageDialog(null, "There is a problem with Repository URL. Please change and try again.");
			return i;
		} catch (GitAPIException | IOException e1) {
			e1.printStackTrace();
			return i;
		}
		for(@SuppressWarnings("unused") RevCommit rev:commits){
			i++;
		}
		return i;
	}

}