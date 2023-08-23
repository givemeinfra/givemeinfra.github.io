/* ConcernMapper - A concern modeling plug-in for Eclipse
 * Copyright (C) 2006  McGill University (http://www.cs.mcgill.ca/~martin/cm)
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * $Revision: 1.30 $
 */

package sourceminer.concerns.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.eclipse.jdt.core.IJavaProject;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import sourceminer.concerns.ConcernModel;
import sourceminer.concerns.ConcernModelException;

 
/**
 * Reads in a Concern model stored in XML. Elements that cannot be converter are
 * stored in the model as SerializedElements.
 */
public class ModelReader {
	
	private ConcernModel aModel;	

	/**
	 * Reads a file into a Concern Model. The model will be reset.
	 * 
	 * @param pModel The concern model to read into.
	 */
	public ModelReader(ConcernModel pModel) {
		aModel = pModel;
	}

	/**
	 * Builds a Concern Model by loading it from a file.
	 * 
	 * @param pFile The file to load the model from
	 * @param pMonitor The progress monitor for this task. Should not be null.
	 * @return The number of elements skipped while reading this file.
	 * @throws ModelIOException if there is any problem reading the file.
	 */
	public synchronized ConcernModel read(File file, IJavaProject lJavaProject)
			throws ModelIOException {
 
		if (lJavaProject != null) {
			Converter.setJavaProject(lJavaProject);
		}

		Document lDocument = null;
		InputStream lContents = null;
		try {

			lContents = new FileInputStream(file);
			DocumentBuilderFactory lFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder lBuilder = lFactory.newDocumentBuilder();
			lDocument = lBuilder.parse(lContents);

		} catch (ParserConfigurationException lException) {
			throw new ModelIOException("Parser configuration problem. "+ lException.getMessage());
			
		} catch (SAXException lException) {
			throw new ModelIOException("Could not parse input. "+ lException.getMessage());
			
		} catch (IOException lException) {
			throw new ModelIOException("IO Exception while parsing input. "+ lException.getMessage());
			
		} finally {
			try {
				if (lContents != null) {
					lContents.close();
				}
			} catch (IOException lException) {
				throw new ModelIOException("IO Exception while parsing input. "+ lException.getMessage());
			}
		}
		
		buildModel(lDocument.getDocumentElement());
		return aModel;
	}
    
    private void buildModel( Node pNode ) throws ModelIOException
	{	
		if( !isElementNode( pNode, XMLTags.Elements.MODEL.toString() ) )
		{
			throw new ModelIOException("Document node is not a <" + XMLTags.Elements.MODEL.toString() + "> node" );
		}
		
		NodeList lChildren = pNode.getChildNodes();
		for( int lI = 0 ; lI < lChildren.getLength(); lI++ )
		{
			if( isElementNode( lChildren.item( lI ), XMLTags.Elements.CONCERN.toString() ) )
			{
				buildConcern( lChildren.item( lI ) );
			}
			else
			{
			    throw new ModelIOException("Invalid node. Expecting <concern> node but got: " + lChildren.item( lI ).getNodeName() );
			}
		}
	}
    
    /**
     * @param pNode
     * @throws ModelIOException
     */
    private void buildConcern( Node pNode ) throws ModelIOException
	{
		String lConcern = pNode.getAttributes().getNamedItem( XMLTags.Attributes.NAME.toString() ).getNodeValue();
		aModel.newConcern( lConcern );
		
		Node lCommentNode = pNode.getAttributes().getNamedItem( XMLTags.Attributes.COMMENT.toString() );
		if( lCommentNode != null )
		{
			aModel.setConcernComment( lConcern, lCommentNode.getNodeValue() );
		}
		
		NodeList lChildren = pNode.getChildNodes();
				
		for( int lI = 0; lI < lChildren.getLength(); lI++ )
		{
		    if( isElementNode( lChildren.item( lI ), XMLTags.Elements.ELEMENT.toString() ))
			{
				Node lNode = lChildren.item( lI ).getAttributes().getNamedItem( XMLTags.Attributes.TYPE.toString() );
				if( lNode == null )
				{
					throw new ModelIOException( "Could not build concern model. Missing attribute " + 
							XMLTags.Attributes.TYPE.toString() + " in XML element type <" + lChildren.item( lI ).getNodeName()+">");
				}
				String lType = lNode.getNodeValue();
				lNode = lChildren.item( lI ).getAttributes().getNamedItem( XMLTags.Attributes.ID.toString() );
				if( lNode == null )
				{
					throw new ModelIOException( "Could not build concern model. Missing attribute " + 
							XMLTags.Attributes.ID.toString() + " in XML element type <" + lChildren.item( lI ).getNodeName()+">");
				}
				String lId = lNode.getNodeValue();
				lNode = lChildren.item( lI ).getAttributes().getNamedItem( XMLTags.Attributes.DEGREE.toString() );
				if( lNode == null )
				{
					throw new ModelIOException( "Could not build concern model. Missing attribute " + 
							XMLTags.Attributes.DEGREE.toString() + " in XML element type <" + lChildren.item( lI ).getNodeName()+">");
				}
				int lDegree = Integer.valueOf( lNode.getNodeValue() ).intValue();
				
				try
				{
				    Object lElement = null;
				    if( lType.equals( XMLTags.Values.FIELD.toString() ))
				    {
				        lElement = Converter.getInstance().toField( lId );
				    }
				    else if( lType.equals( XMLTags.Values.METHOD.toString() ))
				    {
				    	lElement = Converter.getInstance().toMethod( lId );
				    }
				    else
				    {
				        throw new ModelIOException( "Invalid element type: " + lType );
				    }
				    aModel.addElement( lConcern, lElement, lDegree);
				    lNode = lChildren.item( lI ).getAttributes().getNamedItem( XMLTags.Attributes.COMMENT.toString() );
					if( lNode != null )
					{
						aModel.setElementComment( lConcern, lElement, lNode.getNodeValue() );
					}
				    
				}
				catch( ConversionException lException )
				{
					
				}
				catch( ConcernModelException lException )
				{
					throw new ModelIOException( "ConcernModelException with message: " + lException.getMessage() );
				}
			}
			else
			{
				throw new ModelIOException( "Could not build concern model. Invalid XML element type: <" + lChildren.item( lI ).getNodeName()+">");
			}
		}
	}
    
    private boolean isElementNode( Node pNode, String pElementName )
	{
		boolean lReturn = true;
		if( pNode.getNodeType() != Node.ELEMENT_NODE )
		{
			lReturn = false;
		}
		else
		{
			lReturn = pNode.getNodeName().equals( pElementName );
		}
		
		return lReturn;
	}
}
