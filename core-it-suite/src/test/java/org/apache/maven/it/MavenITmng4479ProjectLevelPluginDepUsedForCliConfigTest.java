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
import org.apache.maven.it.util.FileUtils;
import org.apache.maven.it.util.ResourceExtractor;

import java.io.File;
import java.util.Properties;

/**
 * This is a test set for <a href="http://jira.codehaus.org/browse/MNG-4479">MNG-4479</a>.
 * 
 * @author Benjamin Bentmann
 */
public class MavenITmng4479ProjectLevelPluginDepUsedForCliConfigTest
    extends AbstractMavenIntegrationTestCase
{

    public MavenITmng4479ProjectLevelPluginDepUsedForCliConfigTest()
    {
        super( "[2.0.3,2.0.99),[3.0-alpha-2,)" );
    }

    /**
     * Verify that project-level plugin dependencies are used for direct invocations of the plugin and that they
     * can contribute classes required for the plugin configuration when the plugin is declared using properties
     * for its key.
     */
    public void testit()
        throws Exception
    {
        File testDir = ResourceExtractor.simpleExtractResources( getClass(), "/mng-4479" );

        Verifier verifier = new Verifier( testDir.getAbsolutePath() );
        verifier.setAutoclean( false );
        verifier.deleteDirectory( "target" );
        verifier.deleteArtifacts( "org.apache.maven.its.mng4479" );
        verifier.filterFile( "settings-template.xml", "settings.xml", "UTF-8", verifier.newDefaultFilterProperties() );
        verifier.getCliOptions().add( "-s" );
        verifier.getCliOptions().add( "settings.xml" );
        verifier.executeGoal( "org.apache.maven.its.plugins:maven-it-plugin-parameter-implementation:2.1-SNAPSHOT:param-implementation" );
        verifier.verifyErrorFreeLog();
        verifier.resetStreams();

        Properties props = verifier.loadProperties( "target/param.properties" );
        assertEquals( "org.apache.maven.plugin.coreit.ItImpl-passed", props.getProperty( "theParameter.string" ) );
        assertEquals( "org.apache.maven.plugin.coreit.ItImpl", props.getProperty( "theParameter.class" ) );
    }

}