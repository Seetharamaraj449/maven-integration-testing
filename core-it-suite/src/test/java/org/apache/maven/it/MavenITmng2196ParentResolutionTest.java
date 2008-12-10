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
import org.apache.maven.it.VerificationException;
import org.apache.maven.it.util.ResourceExtractor;

import java.io.File;

public class MavenITmng2196ParentResolutionTest
    extends AbstractMavenIntegrationTestCase
{

    public MavenITmng2196ParentResolutionTest()
    {
        super( "(,2.0.2)(2.0.2,)" );
    }

    /**
     * Verify that multimodule builds where one project references another as
     * a parent can build, even if that parent is not correctly referenced by
     * &lt;relativePath/&gt; and is not in the local repository. [MNG-2196]
     */
    public void testitMNG2196()
        throws Exception
    {
        File testDir = ResourceExtractor.simpleExtractResources( getClass(), "/mng-2196" );
        Verifier verifier = new Verifier( testDir.getAbsolutePath() );

        
        if ( matchesVersionRange( "(, 2.99.99)" ) )
        {
            verifier.executeGoal( "package" );
            verifier.verifyErrorFreeLog();
            verifier.resetStreams();
        }
        else
        {
            try
            {
                verifier.executeGoal( "package" );
                verifier.verifyErrorFreeLog();
            }
            catch ( VerificationException e )
            {
                verifier.verifyTextInLog( "java.io.IOException" );
                verifier.resetStreams();
                return;
            }
            throw new VerificationException( "Build should have failed with java.io.IOException" );           
        }
    }
}

