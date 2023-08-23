package com.br.service.DAO;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import com.br.messageService.util.CFUtil;

public abstract class XmlAbstractDAO {
	
	private Node nodeSemiSynchronous;
	private String FILE_PERSISTENCE_PATH;
	private Document document;
	
	public Node getNodeSemiSynchronous() {
		return nodeSemiSynchronous;
	}

	void initFields()
	{
		if (CFUtil.isEmpty(FILE_PERSISTENCE_PATH)){
			this.initDataPath();
		}
		
		try{
			DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			document = db.parse(FILE_PERSISTENCE_PATH);
		}
		catch (Exception e){
			e.printStackTrace();
		}
		
		Element elementRoot = document.getDocumentElement();
		nodeSemiSynchronous = elementRoot.getFirstChild();
	}

	//TODO
	private void initDataPath()
	{
		FILE_PERSISTENCE_PATH = XmlDAOAccess.class.getResource("").getPath();
		
		if (System.getProperty("os.name").indexOf("Windows") != -1 )
		{
			FILE_PERSISTENCE_PATH = FILE_PERSISTENCE_PATH.replace("/", "\\");
			FILE_PERSISTENCE_PATH = FILE_PERSISTENCE_PATH.substring(1);
			
			FILE_PERSISTENCE_PATH = FILE_PERSISTENCE_PATH.substring(0, FILE_PERSISTENCE_PATH.indexOf("WEB-INF")).concat("dataBase/persistence.xml");
		}
		else
		{
			FILE_PERSISTENCE_PATH = FILE_PERSISTENCE_PATH.substring(0, FILE_PERSISTENCE_PATH.indexOf("WEB-INF")).concat("dataBase/persistence.xml");
		}
		
//		FILE_PERSISTENCE_PATH = "C:\\Trabalho\\Servers\\jboss-5.0.0.GA\\server\\default\\deploy\\CollaborationBusinessServiceEAR.ear\\CollaborationBusinessService.war\\dataBase\\persistence.xml";
	}
	
	void performPersistence(Document document) 
	{
	    try {
	    	DOMSource source = new DOMSource(document);
	    	
	    	TransformerFactory transFactory = TransformerFactory.newInstance();
	    	Transformer transformer = transFactory.newTransformer();
	    	
	    	StreamResult result = new StreamResult(FILE_PERSISTENCE_PATH);
	    	
	    	transformer.transform(source, result);
	    	
	    }catch (TransformerConfigurationException e) {
			e.printStackTrace();
		} catch (TransformerException e) {
			e.printStackTrace();
		}
	}

	public Document getDocument() {
		return document;
	}

}
