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

package org.kie.appformer.formmodeler.uploads.authoring.codegen.view.html;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

import org.kie.appformer.formmodeler.codegen.view.impl.html.inputs.AbstractTemplateProvider;
import org.kie.appformer.formmodeler.uploads.authoring.model.DocumentUploadFieldDefinition;
import org.kie.workbench.common.forms.service.FieldManager;

@Dependent
public class DocumentUploadTemplateProvider extends AbstractTemplateProvider {

    @Inject
    public DocumentUploadTemplateProvider(FieldManager fieldManager) {
        super(fieldManager);
    }

    @Override
    protected String[] getSupportedFieldCodes() {
        return new String[]{
                DocumentUploadFieldDefinition.FIELD_TYPE.getTypeName()};
    }

    @Override
    protected String getTemplateForFieldTypeCode(String fieldCode) {
        return "/org/kie/appformer/formmodeler/uploads/authoring/codegen/view/html/documentupload.mv";
    }
}
