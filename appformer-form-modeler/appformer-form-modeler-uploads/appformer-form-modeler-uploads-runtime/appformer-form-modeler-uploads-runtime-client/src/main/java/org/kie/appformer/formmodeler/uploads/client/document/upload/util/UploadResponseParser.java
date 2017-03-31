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

package org.kie.appformer.formmodeler.uploads.client.document.upload.util;

import javax.enterprise.context.Dependent;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsonUtils;
import com.google.gwt.json.client.JSONObject;
import org.kie.appformer.formmodeler.uploads.shared.model.DocumentUpload;

@Dependent
public class UploadResponseParser {

    public DocumentUpload parseUploadResponse(String response) throws Exception {

        if (response != null) {
            JavaScriptObject jsResponse = JsonUtils.safeEval(response);

            if (jsResponse != null) {
                JSONObject jsonResponse = new JSONObject(jsResponse);
                if (jsonResponse.get("document") != null) {
                    JSONObject document = jsonResponse.get("document").isObject();

                    return new DocumentUpload(document.get("documentId").isString().stringValue(),
                                              document.get("name").isString().stringValue(),
                                              new Double(document.get("size").isNumber().doubleValue()).longValue());
                }
            }
        }
        throw new Exception("Something wrong happened while uploading document");
    }
}
