/*
 * (c) Copyright 2005, 2006, 2007 Hewlett-Packard Development Company, LP
 * All rights reserved.
 * [See end of file]
 */

package com.hp.hpl.jena.query.syntax;

import java.util.Collection;
import java.util.Map;

import com.hp.hpl.jena.query.core.LabelMap;
import com.hp.hpl.jena.query.engine.binding.Binding;
import com.hp.hpl.jena.query.serializer.FmtTemplateARQ;


/**
 * Templates : patterns in the CONSTRUCT clause 
 * 
 * @author Andy Seaborne
 * @version $Id: Template.java,v 1.13 2007/02/06 17:05:56 andy_seaborne Exp $
 */

public abstract class Template
{
    public abstract void subst(Collection s, Map bNodeMap, Binding b) ;
    public abstract void visit(TemplateVisitor v) ;
    
    public abstract int hashCode() ;
    
    public abstract boolean equalTo(Object temp2, LabelMap labelMap) ;
    
    final public boolean equals(Object temp2)
    { return equalTo(temp2, null) ; }
    
    public String toString()
    {
        return FmtTemplateARQ.asString(this) ;
    }
    
    static final int HashTemplateGroup     = 0xB1 ;
}

/*
 * (c) Copyright 2005, 2006, 2007 Hewlett-Packard Development Company, LP
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in the
 *    documentation and/or other materials provided with the distribution.
 * 3. The name of the author may not be used to endorse or promote products
 *    derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE AUTHOR ``AS IS'' AND ANY EXPRESS OR
 * IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
 * IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY DIRECT, INDIRECT,
 * INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT
 * NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF
 * THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */