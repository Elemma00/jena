/**
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 *  See the NOTICE file distributed with this work for additional
 *  information regarding copyright ownership.
 */

package org.seaborne.tdb2.store.nodetable ;

import org.apache.jena.atlas.logging.Log ;
import org.apache.jena.graph.Node ;
import org.apache.jena.riot.thrift.RiotThriftException ;
import org.apache.jena.riot.thrift.TRDF ;
import org.apache.jena.riot.thrift.ThriftConvert ;
import org.apache.jena.riot.thrift.wire.RDF_Term ;
import org.apache.thrift.TException ;
import org.apache.thrift.protocol.TProtocol ;
import org.apache.thrift.transport.TTransport ;
import org.seaborne.dboe.index.Index ;
import org.seaborne.tdb2.TDBException ;
import org.seaborne.tdb2.store.NodeId ;
import tdbdev.binarydatafile.BinaryDataFile ;

/** NodeTable using Thrist for the I/O and storage. */

public class NodeTableTRDF extends NodeTableNative {
    // Write buffering is done in the underlying BinaryDataFile
    BinaryDataFile diskFile ;
    private TTransport transport ;
    private final TProtocol protocol ;

    public NodeTableTRDF(Index nodeToId, BinaryDataFile objectFile) {
        super(nodeToId) ;
        try {
            this.diskFile = objectFile ;
            transport = new TReadAppendFileTransport(diskFile) ;
            transport.open(); 
            // Does not seem to affect write speed.
            //transport = new TFastFramedTransport(transport) ;
            this.protocol = TRDF.protocol(transport) ;
        }
        catch (Exception ex) {
            throw new TDBException("NodeTableTRDF", ex) ;
        }
    }

    @Override
    protected NodeId writeNodeToTable(Node node) {
        RDF_Term term = ThriftConvert.convert(node, true) ;
        try {
            long x = diskFile.length() ;
            NodeId nid = NodeId.create(x) ;
            term.write(protocol) ;
            //transport.flush() ;
            return nid ;
        }
        catch (Exception ex) {
            throw new TDBException("NodeTableThrift/Write", ex) ;
        }
    }

    @Override
    protected Node readNodeFromTable(NodeId id) {
        try {
            long x = id.getId() ;
            diskFile.position(x) ;
            RDF_Term term = new RDF_Term() ;
            term.read(protocol) ;
            Node n = ThriftConvert.convert(term) ;
            return n ;
        }
        catch (TException ex) {
            throw new TDBException("NodeTableTRDF/Read", ex) ;
        }
        catch (RiotThriftException ex) {
            Log.fatal(this, "Bad encoding: NodeId = "+id) ;
            throw ex ;
        }
    }

    @Override
    protected void syncSub() {
        try { transport.flush(); }
        catch (Exception ex) { throw new TDBException("NodeTableTRDF", ex) ; }
    }

    @Override
    protected void closeSub() {
        try { transport.close() ; }
        catch (Exception ex) { throw new TDBException("NodeTableTRDF", ex) ; }
    }
}