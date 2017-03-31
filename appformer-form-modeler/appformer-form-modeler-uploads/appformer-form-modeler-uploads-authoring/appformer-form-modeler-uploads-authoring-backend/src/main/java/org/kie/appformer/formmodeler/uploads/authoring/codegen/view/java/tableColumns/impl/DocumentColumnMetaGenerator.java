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

package org.kie.appformer.formmodeler.uploads.authoring.codegen.view.java.tableColumns.impl;

import org.apache.commons.lang3.StringUtils;
import org.kie.appformer.formmodeler.codegen.SourceGenerationContext;
import org.kie.appformer.formmodeler.codegen.view.impl.java.tableColumns.impl.AbstractColumnMetaGenerator;
import org.kie.appformer.formmodeler.uploads.shared.model.DocumentUpload;

public class DocumentColumnMetaGenerator extends AbstractColumnMetaGenerator {

    @Override
    public String getSupportedType() {
        return DocumentUpload.class.getName();
    }

    @Override
    public String[] getImports() {
        return new String[]{"org.kie.appformer.formmodeler.uploads.client.document.util.DocumentColumn", "com.google.gwt.safehtml.shared.SafeHtml", Override.class.getName()};
    }

    @Override
    protected String generateNewColumnSource(String property,
                                             String modelTypeName,
                                             SourceGenerationContext context) {
        StringBuffer out = new StringBuffer();

        out.append("new DocumentColumn<")
                .append(modelTypeName)
                .append(">() { ")
                .append("@Override\n")
                .append("public SafeHtml getValue( ")
                .append(modelTypeName)
                .append(" model ) {")
                .append("return render(model.get")
                .append(StringUtils.capitalize(property))
                .append("());}}");

        return out.toString();
    }

    @Override
    protected String getInitializerCode(String property,
                                        SourceGenerationContext context) {
        return "";
    }
}
