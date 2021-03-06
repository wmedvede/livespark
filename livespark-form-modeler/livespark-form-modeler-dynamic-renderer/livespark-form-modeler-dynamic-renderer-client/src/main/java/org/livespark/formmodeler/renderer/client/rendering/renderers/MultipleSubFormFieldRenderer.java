/*
 * Copyright 2016 Red Hat, Inc. and/or its affiliates.
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
package org.livespark.formmodeler.renderer.client.rendering.renderers;

import javax.enterprise.context.Dependent;

import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.IsWidget;
import org.livespark.formmodeler.model.impl.relations.MultipleSubFormFieldDefinition;
import org.livespark.formmodeler.renderer.client.rendering.FieldRenderer;

/**
 * Created by pefernan on 9/21/15.
 */
@Dependent
public class MultipleSubFormFieldRenderer extends FieldRenderer<MultipleSubFormFieldDefinition> {

    private HTML htmlContent = new HTML();

    @Override
    public String getName() {
        return "Multiple SubForm";
    }

    @Override
    public void initInputWidget() {
    }

    @Override
    public IsWidget getInputWidget() {
        return htmlContent;
    }

    @Override
    public String getSupportedFieldDefinitionCode() {
        return MultipleSubFormFieldDefinition._CODE;
    }

    public void setHtmlContent( String content ) {
        htmlContent.setHTML( content );
    }
}
