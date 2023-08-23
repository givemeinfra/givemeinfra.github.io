/* ConcernMapper - A concern modeling plug-in for Eclipse
 * Copyright (C) 2006  McGill University (http://www.cs.mcgill.ca/~martin/cm)
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * $Revision: 1.7 $
 */

package sourceminer.concerns;

/**
 * Something wrong in the ConcernModel
 */
/**
 * @author martin
 *
 */ 
public class ConcernModelException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

	/**
	 * Creates a new ConcernModelException.
	 * @param pMessage A message for the exception.
	 */
	ConcernModelException( String pMessage ) 
	{ super( pMessage ); }
}
