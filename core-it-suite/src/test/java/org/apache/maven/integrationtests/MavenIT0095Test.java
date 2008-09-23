package org.apache.maven.integrationtests;

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
import java.util.ArrayList;
import java.util.List;

public class MavenIT0095Test
    extends AbstractMavenIntegrationTestCase
{
    public MavenIT0095Test()
    {
        super( "[,2.99.99)" );
    }

    /**
     * Test URL calculation when modules are in sibling dirs of parent. (MNG-2006)
     */
    public void testit0095()
        throws Exception
    {
        // TODO: This is WRONG! Need to run only sub1 to effective-pom, then run all to verify.
        File testDir = ResourceExtractor.simpleExtractResources( getClass(), "/it0095" );
        File sub1 = new File( testDir, "sub1" );

        Verifier verifier = new Verifier( sub1.getAbsolutePath() );

        List options = new ArrayList();
        options.add( "-Doutput=\"" + new File( sub1, "target/effective-pom.xml" ).getAbsolutePath() + "\"" );

        verifier.setCliOptions( options );

        List goals = new ArrayList();
        goals.add( "org.apache.maven.plugins:maven-help-plugin:2.0.2:effective-pom" );
        goals.add( "verify" );

        verifier.executeGoals( goals );
        verifier.verifyErrorFreeLog();
        verifier.resetStreams();

    }
}
