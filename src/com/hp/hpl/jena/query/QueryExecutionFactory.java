/*
 * (c) Copyright 2004, 2005, 2006, 2007 Hewlett-Packard Development Company, LP
 * [See end of file]
 */

package com.hp.hpl.jena.query;
import java.util.List;

import com.hp.hpl.jena.rdf.model.Model ;
import com.hp.hpl.jena.util.FileManager;
import com.hp.hpl.jena.query.core.DataSourceImpl;

import com.hp.hpl.jena.query.engine.QueryEngineFactory;
import com.hp.hpl.jena.query.engine.QueryEngineRegistry;
import com.hp.hpl.jena.query.engine.http.QueryEngineHTTP;

import org.apache.commons.logging.*;


/** Place to make QueryExecution objects from Query objects or a string.   
 *  
 * @author     Andy Seaborne
 * @version    $Id: QueryExecutionFactory.java,v 1.19 2007/01/02 11:20:16 andy_seaborne Exp $
 */
 
public class QueryExecutionFactory
{
    private QueryExecutionFactory() {}
    
    // ---------------- Query
    
    /** Create a QueryExecution
     * 
     * @param query Query
     * @return QueryExecution
     */
    static public QueryExecution create(Query query)
    {
        checkArg(query) ;
        return make(query) ;
    }

    /** Create a QueryExecution
     * 
     * @param queryStr Query string
     * @return QueryExecution
     */
    static public QueryExecution create(String queryStr)
    {
        checkArg(queryStr) ;
        return create(makeQuery(queryStr)) ;
    }

    /** Create a QueryExecution
     * 
     * @param queryStr Query string
     * @param syntax   Query syntax
     * @return QueryExecution
     */
    static public QueryExecution create(String queryStr, Syntax syntax)
    {
        checkArg(queryStr) ;
        return create(makeQuery(queryStr, syntax)) ;
    }
    
    // ---------------- Query + Dataset
    
    /** Create a QueryExecution to execute over the Dataset.
     * 
     * @param query     Query
     * @param dataset   Target of the query
     * @return QueryExecution
     */
    static public QueryExecution create(Query query, Dataset dataset)
    {
        //checkArg(dataset) ; // Allow null
        return make(query, dataset) ;
    }

    /** Create a QueryExecution to execute over the Dataset.
     * 
     * @param queryStr     Query string
     * @param dataset      Target of the query
     * @return QueryExecution
     */
    static public QueryExecution create(String queryStr, Dataset dataset)
    {
        checkArg(queryStr) ;
        //checkArg(dataset) ; // Allow null
        return make(makeQuery(queryStr), dataset) ;
    }

    /** Create a QueryExecution to execute over the Dataset.
     * 
     * @param queryStr     Query string
     * @param syntax       Query language
     * @param dataset      Target of the query
     * @return QueryExecution
     */
    static public QueryExecution create(String queryStr, Syntax syntax, Dataset dataset)
    {
        checkArg(queryStr) ;
        //checkArg(dataset) ; // Allow null
        return make(makeQuery(queryStr, syntax), dataset) ;
    }

    /** Create a QueryExecution : the file manager will be used to load
     *  URIs in the query decription. 
     * 
     * @param query Query
     * @param fm    FileManager 
     * @return QueryExecution
     */

    static public QueryExecution create(Query query, FileManager fm)
    {
        checkArg(query) ;
        QueryExecution qe = make(query) ;
        if ( fm != null )
            qe.setFileManager(fm) ;
        return qe ;
    }

    /** Create a QueryExecution : the file manager will be used to load
     *  URIs in the query decription. 
     * 
     * @param queryStr Query string
     * @param fm       FileManager 
     * @return QueryExecution
     */

    static public QueryExecution create(String queryStr, FileManager fm)
    {
        checkArg(queryStr) ;
        return create(makeQuery(queryStr), fm) ;
    }

    /** Create a QueryExecution : the file manager will be used to load
     *  URIs in the query decription. 
     * 
     * @param queryStr Query string
     * @param syntax   Syntax
     * @param fm       FileManager 
     * @return QueryExecution
     */

    static public QueryExecution create(String queryStr, Syntax syntax, FileManager fm)
    {
        checkArg(queryStr) ;
        return create(makeQuery(queryStr, syntax), fm) ;
    }

    // ---------------- Query + Model
    
    /** Create a QueryExecution to execute over the Model.
     * 
     * @param query     Query
     * @param model     Target of the query
     * @return QueryExecution
     */
    static public QueryExecution create(Query query, Model model)
    {
        checkArg(query) ;
        checkArg(model) ;
        return make(query, new DataSourceImpl(model)) ;
    }

    /** Create a QueryExecution to execute over the Model.
     * 
     * @param queryStr     Query string
     * @param model     Target of the query
     * @return QueryExecution
     */
    static public QueryExecution create(String queryStr, Model model)
    {
        checkArg(queryStr) ;
        checkArg(model) ;
        return create(makeQuery(queryStr), model) ;
    }

    /** Create a QueryExecution to execute over the Model.
     * 
     * @param queryStr     Query string
     * @param lang         Query language
     * @param model        Target of the query
     * @return QueryExecution
     */
    static public QueryExecution create(String queryStr, Syntax lang, Model model)
    {
        checkArg(queryStr) ;
        checkArg(model) ;
        return create(makeQuery(queryStr, lang), model) ;
    }

    static public QueryExecution create(Query query, QuerySolution initialBinding)
    {
        checkArg(query) ;
        QueryExecution qe = make(query) ;
        if ( initialBinding != null )
            qe.setInitialBinding(initialBinding) ;
        return qe ;
    }

    /** Create a QueryExecution given some initial values of variables.
     * 
     * @param queryStr          QueryString
     * @param initialBinding    Any initial binding of variables
     * @return QueryExecution
     */
    static public QueryExecution create(String queryStr, QuerySolution initialBinding)
    {
        checkArg(queryStr) ;
        return create(makeQuery(queryStr), initialBinding) ; 
    }

    /** Create a QueryExecution given some initial values of variables.
     * 
     * @param queryStr          QueryString
     * @param syntax            Query language syntax
     * @param initialBinding    Any initial binding of variables
     * @return QueryExecution
     */
    static public QueryExecution create(String queryStr, Syntax syntax, QuerySolution initialBinding)
    {
        checkArg(queryStr) ;
        return create(makeQuery(queryStr, syntax), initialBinding) ; 
    }


    /** Create a QueryExecution to execute over the Model, 
     * given some initial values of variables.
     * 
     * @param query            Query
     * @param model            Target of the query
     * @param initialBinding    Any initial binding of variables
     * @return QueryExecution
     */
    static public QueryExecution create(Query query, Model model, QuerySolution initialBinding)
    {
        checkArg(model) ;
        return create(query, new DataSourceImpl(model), initialBinding) ;
    }
    
    /** Create a QueryExecution to execute over the Model, 
     * given some initial values of variables.
     * 
     * @param queryStr         Query string
     * @param model            Target of the query
     * @param initialBinding    Any initial binding of variables
     * @return QueryExecution
     */
    static public QueryExecution create(String queryStr, Model model, QuerySolution initialBinding)
    {
        checkArg(queryStr) ;
        checkArg(model) ;
        return create(makeQuery(queryStr), model, initialBinding) ;
    }
    
    /** Create a QueryExecution to execute over the Model, 
     * given some initial values of variables.
     * 
     * @param queryStr         Query string
     * @param syntax           Query language
     * @param model            Target of the query
     * @param initialBinding    Any initial binding of variables
     * @return QueryExecution
     */
    static public QueryExecution create(String queryStr, Syntax syntax, Model model, QuerySolution initialBinding)
    {
        checkArg(queryStr) ;
        return create(makeQuery(queryStr, syntax), model, initialBinding) ;
    }
    
    /** Create a QueryExecution over a Dataset given some initial values of variables.
     * 
     * @param query            Query
     * @param dataset          Target of the query
     * @param initialBinding    Any initial binding of variables
     * @return QueryExecution
     */
    static public QueryExecution create(Query query, Dataset dataset, QuerySolution initialBinding)
    {
        checkArg(query) ;
        QueryExecution qe = make(query, dataset) ;
        if ( initialBinding != null )
            qe.setInitialBinding(initialBinding) ;
        return qe ;
    }

    /** Create a QueryExecution over a Dataset given some initial values of variables.
     * 
     * @param queryStr         Query string
     * @param dataset          Target of the query
     * @param initialBinding    Any initial binding of variables
     * @return QueryExecution
     */
    static public QueryExecution create(String queryStr, Dataset dataset, QuerySolution initialBinding)
    {
        checkArg(queryStr) ;
        return create(makeQuery(queryStr), dataset, initialBinding) ;
    }

    /** Create a QueryExecution over a Dataset given some initial values of variables.
     * 
     * @param queryStr         Query string
     * @param dataset          Target of the query
     * @param initialBinding    Any initial binding of variables
     * @return QueryExecution
     */
    static public QueryExecution create(String queryStr, Syntax syntax, Dataset dataset, QuerySolution initialBinding)
    {
        checkArg(queryStr) ;
        return create(makeQuery(queryStr, syntax), dataset, initialBinding) ;
    }

    // ---------------- Remote query execution
    
    /** Create a QueryExecution that will access a SPARQL service over HTTP
     * @param service   URL of the remote service 
     * @param query     Query to execute 
     * @return QueryExecution
     */ 
     
    static public QueryExecution sparqlService(String service, Query query)
    {
        checkNotNull(service, "URL for service is null") ;
        checkArg(query) ;
        return makeServiceRequest(service, query) ;
    }

    /** Create a QueryExecution that will access a SPARQL service over HTTP
     * @param service       URL of the remote service 
     * @param query         Query to execute
     * @param defaultGraph  URI of the default graph
     * @return QueryExecution
     */ 
     
    static public QueryExecution sparqlService(String service, Query query, String defaultGraph)
    {
        checkNotNull(service, "URL for service is null") ;
        //checkNotNull(defaultGraph, "IRI for default graph is null") ;
        checkArg(query) ;
        QueryEngineHTTP qe = makeServiceRequest(service, query) ;
        qe.addDefaultGraph(defaultGraph) ;
        return qe ;
    }

    /** Create a QueryExecution that will access a SPARQL service over HTTP
     * @param service           URL of the remote service 
     * @param query             Query to execute
     * @param defaultGraphURIs  List of URIs to make up the default graph
     * @param namedGraphURIs    List of URIs to make up the named graphs
     * @return QueryExecution
     */ 
    static public QueryExecution sparqlService(String service, Query query, List defaultGraphURIs, List namedGraphURIs)
    {
        checkNotNull(service, "URL for service is null") ;
        //checkNotNull(defaultGraphURIs, "List of default graph URIs is null") ;
        //checkNotNull(namedGraphURIs, "List of named graph URIs is null") ;
        checkArg(query) ;

        QueryEngineHTTP qe = makeServiceRequest(service, query) ;
        
        if ( defaultGraphURIs != null )
            qe.setDefaultGraphURIs(defaultGraphURIs) ;
        if ( namedGraphURIs != null )
            qe.setNamedGraphURIs(namedGraphURIs) ;
        return qe ;
    }
    
    // ---------------- Internal routines
    
    // Make query
    
    static private Query makeQuery(String queryStr)
    {
        return QueryFactory.create(queryStr) ;
    }


    static private Query makeQuery(String queryStr, Syntax syntax)
    {
        return QueryFactory.create(queryStr, syntax);
    }
    
    // ---- Make executions
    
    static private QueryExecution make(Query query)
    {
        return make(query, null) ;
    }

    static private QueryExecution make(Query query, Dataset dataset)
    {
        QueryEngineFactory f = QueryEngineRegistry.get().find(query, dataset);
        if ( f == null )
        {
            LogFactory
                .getLog(QueryExecutionFactory.class)
                .warn("Failed to find a QueryEngineFactory for query: "+query) ;
            return null ;
        }
        return f.create(query, dataset) ;
    }
    
    static private QueryEngineHTTP makeServiceRequest(String service, Query query)
    {
        return new QueryEngineHTTP(service, query) ;
    }
    
    // Checking
    
    static private void checkNotNull(Object obj, String msg)
    {
        if ( obj == null )
            throw new IllegalArgumentException(msg) ;
    }
    
    static private void checkArg(Model model)
    { checkNotNull(model, "Model is a null pointer") ; }

//    static private void checkArg(Dataset dataset)
//    { checkNotNull(dataset, "Dataset is a null pointer") ; }

    static private void checkArg(String queryStr)
    { checkNotNull(queryStr, "Query string is null") ; }

    static private void checkArg(Query query)
    { checkNotNull(query, "Query is null") ; }
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
