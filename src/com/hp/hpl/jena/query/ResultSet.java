/*
 * (c) Copyright 2004, 2005, 2006, 2007 Hewlett-Packard Development Company, LP
 * [See end of file]
 */


package com.hp.hpl.jena.query;

import java.util.* ;

/** Results from a query in a table-like manner for SELECT queries.
 *  Each row corresponds to a set of bindings which fulfil the conditions
 *  of the query.  Access to the results is by variable name.
 *
 * @see Query
 * @see QueryExecution
 * @see QuerySolution
 * @see ResultSet
 * 
 * @author   Andy Seaborne
 * @version  $Id: ResultSet.java,v 1.13 2007/01/02 11:20:16 andy_seaborne Exp $
 */

public interface ResultSet extends Iterator
{
    /**
     * Is there another possibility?
     */
    public boolean hasNext() ;

    /** Moves onto the next result possibility.
     *  The returned object should be of class QuerySolution
     */
    
    public Object next() ;

    /** Moves onto the next result possibility.
     */
    
    public QuerySolution nextSolution() ;
    
    /** Return the "row" number for the current iterator item */
    public int getRowNumber() ;
    
    /** Get the variable names for the projection
     */
    public List getResultVars() ;

    /** Is this ResultSet known to be ordered? 
     * Usually, this means a query involved ORDER BY or a ResultSet read
     * from a serialization had indexing information.
     * (The ordering does not necessaryly have to be total) */
    public boolean isOrdered() ;
    
    /** Is this ResultSet known to be distinct? 
     * Usually, this means a query involved DISTINCT
     */
    public boolean isDistinct() ;
}

/*
 *  (c) Copyright 2004, 2005, 2006, 2007 Hewlett-Packard Development Company, LP
 *  All rights reserved.
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

