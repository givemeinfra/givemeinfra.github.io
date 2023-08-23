package implementacoes.radial;

import java.io.*;  
import java.net.*;  
import java.util.*;  
import javax.xml.parsers.*;  
import org.w3c.dom.*;  
  
public class NodeXMLReader 
{
	  // caminho (path) do arquivo XML  
	  private String xmlPathname;  
	  public NodeXMLReader(String pPath)
	  {
		  xmlPathname = pPath;
	  }
	  
	  public Vector lerNodes() throws Exception 
	  {
		    DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		    dbf.setNamespaceAware(false);
		    DocumentBuilder docBuilder = dbf.newDocumentBuilder();
		    Document doc = docBuilder.parse(new File(xmlPathname));
		    Element elem = doc.getDocumentElement();
		    
		    // pega todos os elementos usuario do XML  
		    NodeList nl = elem.getElementsByTagName( "node" );  
		    NodeList ne = elem.getElementsByTagName( "edge" ); 
		      
		    // prepara o vetor      
		    Vector listNodes = new Vector(); 
		    Vector listEdges = new Vector();
		    for( int i=0; i<nl.getLength(); i++ ) {  
		        Element tagNode = (Element) nl.item( i );  
		       
		        // pega os dados cadastrado para o usuario atual  
		        int id = Integer.parseInt( tagNode.getAttribute( "id" ) ); 
		        String nome = getChildTagValue( tagNode, "data" ); 
		        //String nome = tagNode.getAttributeNode("nome").getValue();      
		       
		        // cria uma nova instancia do UsuarioGuj com os dados do xml  
		        
		        node nosXML = new node( id, nome);  
		       
		        // adiciona o usuario na coleção (vector) de usuários do guj  
		        listNodes.addElement( nosXML );  
		    } 
		    
		    
		    return listNodes;
	  }
	  
	  public Vector lerEdges() throws Exception
	  {
		    DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();  
		    DocumentBuilder db = dbf.newDocumentBuilder();  
		    Document doc = db.parse( xmlPathname );  
		    Element elem = doc.getDocumentElement();  
		    // pega todos os elementos usuario do XML  
		    NodeList ne = elem.getElementsByTagName( "edge" ); 
		      
		    // prepara o vetor
		    Vector listEdges = new Vector();
		   for( int i=0; i<ne.getLength(); i++ ) {  
		        Element tagNode = (Element) ne.item( i );  
		       
		        // pega os dados cadastrado para o usuario atual  
		        int idSource = Integer.parseInt( tagNode.getAttribute( "source" ) ); 
		        int idTarget = Integer.parseInt( tagNode.getAttribute( "target" ) );
		        
		        edge arestasXML = new edge( idSource, idTarget);  
		       
		        // adiciona o usuario na coleção (vector) de usuários do guj  
		        listEdges.addElement( arestasXML );  
		    }
		   return listEdges;
	  }
	  
		  
	  private String getChildTagValue( Element elem, String tagName ) throws Exception 
	  {  
	        NodeList children = elem.getElementsByTagName( tagName );  
	        if( children == null ) return null;  
	            Element child = (Element) children.item(0);  
	        if( child == null ) return null;  
	        return child.getFirstChild().getNodeValue();  
	  }  
}
