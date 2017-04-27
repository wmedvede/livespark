/*
 * Copyright 2016 Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.kie.appformer.provisioning.service;

import org.guvnor.ala.ui.model.ITestConnectionResult;
import org.jboss.errai.common.client.api.annotations.MapsTo;
import org.jboss.errai.common.client.api.annotations.Portable;

@Portable
public class TestConnectionResult
        implements ITestConnectionResult {

    private boolean httpConnectionError;

    private boolean managementConnectionError;

    private String httpConnectionMessage;

    private String managementConnectionMessage;

    public TestConnectionResult( ) {
    }

    public TestConnectionResult( @MapsTo( "httpConnectionError" ) boolean httpConnectionError,
                                 @MapsTo( "httpConnectionMessage" ) String httpConnectionMessage,
                                 @MapsTo( "managementConnectionError" ) boolean managementConnectionError,
                                 @MapsTo( "managementConnectionMessage" ) String managementConnectionMessage ) {
        this.httpConnectionError = httpConnectionError;
        this.managementConnectionError = managementConnectionError;
        this.httpConnectionMessage = httpConnectionMessage;
        this.managementConnectionMessage = managementConnectionMessage;
    }

    public boolean getHttpConnectionError( ) {
        return httpConnectionError;
    }

    public void setHttpConnectionError( boolean httpConnectionError ) {
        this.httpConnectionError = httpConnectionError;
    }

    public String getHttpConnectionMessage( ) {
        return httpConnectionMessage;
    }

    public void setHttpConnectionMessage( String httpConnectionMessage ) {
        this.httpConnectionMessage = httpConnectionMessage;
    }

    public boolean getManagementConnectionError( ) {
        return managementConnectionError;
    }

    public void setManagementConnectionError( boolean managementConnectionError ) {
        this.managementConnectionError = managementConnectionError;
    }

    public String getManagementConnectionMessage( ) {
        return managementConnectionMessage;
    }

    public void setManagementConnectionMessage( String managementConnectionMessage ) {
        this.managementConnectionMessage = managementConnectionMessage;
    }
}