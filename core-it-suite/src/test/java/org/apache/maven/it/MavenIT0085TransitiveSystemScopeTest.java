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
import java.util.Collection;

public class MavenIT0085TransitiveSystemScopeTest
    extends AbstractMavenIntegrationTestCase
{
    public MavenIT0085TransitiveSystemScopeTest()
    {
        super( ALL_MAVEN_VERSIONS );
    }

    /**
     * Verify that system-scoped dependencies get resolved with system scope
     * when they are resolved transitively via another (non-system)
     * dependency. Inherited scope should not apply in the case of
     * system-scoped dependencies, no matter where they are.
     */
    public void testit0085()
        throws Exception
    {
        File testDir = ResourceExtractor.simpleExtractResources( getClass(), "/it0085" );

        Verifier verifier = new Verifier( testDir.getAbsolutePath() );
        verifier.setAutoclean( false );
        verifier.deleteDirectory( "target" );
        verifier.deleteArtifacts( "org.apache.maven.its.it0085" );
        verifier.getSystemProperties().setProperty( "test.home", testDir.getAbsolutePath() );
        verifier.filterFile( "settings-template.xml", "settings.xml", "UTF-8", verifier.newDefaultFilterProperties() );
        verifier.getCliOptions().add( "--settings" );
        verifier.getCliOptions().add( "settings.xml" );
        verifier.executeGoal( "validate" );
        verifier.verifyErrorFreeLog();
        verifier.resetStreams();

        Collection lines = verifier.loadLines( "target/test.txt", "UTF-8" );
        assertTrue( lines.toString(), lines.contains( "system.jar" ) );
    }

}