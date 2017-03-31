/*
 * Copyright 2017 Red Hat, Inc. and/or its affiliates.
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

package org.kie.appformer.formmodeler.uploads.authoring.client;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

import com.google.gwt.user.client.ui.IsWidget;
import org.kie.appformer.formmodeler.uploads.authoring.model.DocumentUploadFieldDefinition;
import org.kie.appformer.formmodeler.uploads.client.document.DocumentWidget;
import org.kie.workbench.common.forms.dynamic.client.rendering.FieldRenderer;
import org.kie.workbench.common.forms.dynamic.service.shared.RenderMode;
import org.kie.workbench.common.forms.editor.service.shared.FormEditorRenderingContext;

@Dependent
public class DocumentUploadFieldRenderer extends FieldRenderer<DocumentUploadFieldDefinition> {

    private DocumentWidget documentWidget;

    @Inject
    public DocumentUploadFieldRenderer(DocumentWidget documentWidget) {
        this.documentWidget = documentWidget;
    }

    @Override
    public String getName() {
        return "DocumentUpload";
    }

    @Override
    public void initInputWidget() {
        if (field.getReadOnly() || renderingContext.getRenderMode().equals(RenderMode.PRETTY_MODE)) {
            documentWidget.setReadOnly(true);
        } else {
            documentWidget.setReadOnly(false);
        }
        if (renderingContext.getRenderMode().equals(RenderMode.READ_ONLY_MODE) || renderingContext instanceof FormEditorRenderingContext) {
            documentWidget.setEnabled(false);
        } else {
            documentWidget.setEnabled(true);
        }
    }

    @Override
    public IsWidget getInputWidget() {
        return documentWidget;
    }

    @Override
    public IsWidget getPrettyViewWidget() {
        return documentWidget;
    }

    @Override
    public String getSupportedCode() {
        return DocumentUploadFieldDefinition.FIELD_TYPE.getTypeName();
    }

    @Override
    protected void setReadOnly(boolean readOnly) {
        documentWidget.setEnabled(!readOnly);
    }
}
