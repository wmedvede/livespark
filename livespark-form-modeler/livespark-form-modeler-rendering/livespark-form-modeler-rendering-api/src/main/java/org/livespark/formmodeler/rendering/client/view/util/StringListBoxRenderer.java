/*
 * Copyright 2015 Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.livespark.formmodeler.rendering.client.view.util;

import java.io.IOException;
import java.util.Map;

import com.google.gwt.text.shared.Renderer;

public class StringListBoxRenderer implements Renderer<String> {
    private Map<String, String> values;

    public void setValues( Map<String, String> values ) {
        this.values = values;
    }

    @Override
    public String render( String value ) {
        if ( value == null ) return "";
        if ( values == null || !values.containsKey( value )) return "";

        return values.get( value );
    }

    @Override
    public void render( String s, Appendable appendable ) throws IOException {
        appendable.append( render( s ) );
    }
}
