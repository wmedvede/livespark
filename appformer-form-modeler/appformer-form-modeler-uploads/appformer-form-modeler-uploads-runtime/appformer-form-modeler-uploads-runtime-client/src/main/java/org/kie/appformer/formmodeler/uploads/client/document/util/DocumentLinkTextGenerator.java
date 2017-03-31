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

package org.kie.appformer.formmodeler.uploads.client.document.util;

import com.google.gwt.i18n.client.NumberFormat;
import org.kie.appformer.formmodeler.uploads.shared.model.DocumentUpload;

public class DocumentLinkTextGenerator {

    public final static String SIZE_UNITS[] = new String[]{"b", "Kb", "Mb"};

    public static String generateDownloadLinkText(DocumentUpload document) {
        if (document == null) {
            throw new IllegalArgumentException("Document cannot be null");
        }

        String result = document.getName() + " (";

        double size = document.getSize();
        int position;
        for (position = 0; position < SIZE_UNITS.length && size > 1024; position++) {
            size = size / 1024;
        }

        result += NumberFormat.getDecimalFormat().format(size) + " " + SIZE_UNITS[position] + ")";

        return result;
    }
}
