/*-- $Id$ -- 

 ============================================================================
                   The Apache Software License, Version 1.1
 ============================================================================
 
    Copyright (C) 1999 The Apache Software Foundation. All rights reserved.
 
 Redistribution and use in source and binary forms, with or without modifica-
 tion, are permitted provided that the following conditions are met:
 
 1. Redistributions of  source code must  retain the above copyright  notice,
    this list of conditions and the following disclaimer.
 
 2. Redistributions in binary form must reproduce the above copyright notice,
    this list of conditions and the following disclaimer in the documentation
    and/or other materials provided with the distribution.
 
 3. The end-user documentation included with the redistribution, if any, must
    include  the following  acknowledgment:  "This product includes  software
    developed  by the  Apache Software Foundation  (http://www.apache.org/)."
    Alternately, this  acknowledgment may  appear in the software itself,  if
    and wherever such third-party acknowledgments normally appear.
 
 4. The names "FOP" and  "Apache Software Foundation"  must not be used to
    endorse  or promote  products derived  from this  software without  prior
    written permission. For written permission, please contact
    apache@apache.org.
 
 5. Products  derived from this software may not  be called "Apache", nor may
    "Apache" appear  in their name,  without prior written permission  of the
    Apache Software Foundation.
 
 THIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESSED OR IMPLIED WARRANTIES,
 INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 FITNESS  FOR A PARTICULAR  PURPOSE ARE  DISCLAIMED.  IN NO  EVENT SHALL  THE
 APACHE SOFTWARE  FOUNDATION  OR ITS CONTRIBUTORS  BE LIABLE FOR  ANY DIRECT,
 INDIRECT, INCIDENTAL, SPECIAL,  EXEMPLARY, OR CONSEQUENTIAL  DAMAGES (INCLU-
 DING, BUT NOT LIMITED TO, PROCUREMENT  OF SUBSTITUTE GOODS OR SERVICES; LOSS
 OF USE, DATA, OR  PROFITS; OR BUSINESS  INTERRUPTION)  HOWEVER CAUSED AND ON
 ANY  THEORY OF LIABILITY,  WHETHER  IN CONTRACT,  STRICT LIABILITY,  OR TORT
 (INCLUDING  NEGLIGENCE OR  OTHERWISE) ARISING IN  ANY WAY OUT OF THE  USE OF
 THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 
 This software  consists of voluntary contributions made  by many individuals
 on  behalf of the Apache Software  Foundation and was  originally created by
 James Tauber <jtauber@jtauber.com>. For more  information on the Apache 
 Software Foundation, please see <http://www.apache.org/>.
 
 */

package org.apache.fop.fo;

// FOP
import org.apache.fop.layout.Area;
import org.apache.fop.apps.FOPException;
import org.apache.fop.datatypes.IDReferences;

// Java
import java.util.Hashtable;
import java.util.Enumeration;

/**
 * base class for representation of formatting objects and their processing
 */
public class FObj extends FONode {

    public static class Maker {
	public FObj make(FObj parent, PropertyList propertyList)
	    throws FOPException {
	    return new FObj(parent, propertyList);
	}
    }

    public static Maker maker() {
	return new Maker();
    }

//    protected PropertyList properties;
    public PropertyList properties;

    protected String name;

    protected FObj(FObj parent, PropertyList propertyList) {
	super(parent);
	this.properties = propertyList;
	this.name = "default FO";
    }

    protected void addCharacters(char data[], int start, int length) {
	// ignore
    }

    public Status layout(Area area) throws FOPException {
	// should always be overridden
	return new Status(Status.OK);
    }

    public String getName() {
	return this.name;
    }

    protected void start() {
	// do nothing by default
    }

    protected void end() {
	// do nothing by default
    }
    
    /**
    * lets outside sources access the property list
    * first used by PageNumberCitation to find the "id" property
    *@param name - the name of the desired property to obtain
    * @returns the property 
    */
    public Property getProperty(String name)
    {
    	return(properties.get(name));
    }

    

    public void removeID(IDReferences idReferences)
    {
        idReferences.removeID( ((FObj)this).properties.get("id").getString());                            
        int numChildren = this.children.size();
	for (int i = 0; i < numChildren; i++) 
        {            
            FONode child= (FONode)children.elementAt(i);
            if ((child instanceof FObj))   
            {
                ((FObj)child).removeID(idReferences);            
            }	
	}
    }


}

