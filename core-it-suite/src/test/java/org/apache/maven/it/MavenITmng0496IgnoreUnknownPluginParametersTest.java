package org.apache.maven.it;

/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

import org.apache.maven.it.Verifier;
import org.apache.maven.it.util.ResourceExtractor;

import java.io.File;

/**
 * This is a test set for <a href="http://jira.codehaus.org/browse/MNG-496">MNG-496</a>.
 * 
 * @author John Casey
 * @version $Id$
 */
public class MavenITmng0496IgnoreUnknownPluginParametersTest
    extends AbstractMavenIntegrationTestCase
{

    /**
     * Test that unused configuration parameters from the POM don't cause the
     * mojo to fail...they will show up as warnings in the -X output instead.
     */
    public void testitMNG496()
        throws Exception
    {
        File testDir = ResourceExtractor.simpleExtractResources( getClass(), "/mng-0496" );

        Verifier verifier = new Verifier( testDir.getAbsolutePath() );
        verifier.executeGoal( "org.apache.maven.its.plugins:maven-it-plugin-file:2.1-SNAPSHOT:file" );
        verifier.assertFilePresent( "target/file.txt" );
        verifier.verifyErrorFreeLog();
        verifier.resetStreams();
    }

}