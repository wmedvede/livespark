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

package org.kie.appformer.formmodeler.uploads.backend.impl;

import java.io.File;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;

import org.kie.appformer.formmodeler.uploads.backend.UploadedDocumentManager;

@ApplicationScoped
public class UploadedDocumentManagerImpl implements Serializable,
                                                    UploadedDocumentManager {

    protected Map<String, File> uploadedFiles = new HashMap<>();

    @Override
    public void storeField(String id,
                           File file) {
        uploadedFiles.put(id,
                          file);
    }

    @Override
    public File getFile(String id) {
        return uploadedFiles.get(id);
    }

    @Override
    public void removeFile(String id) {
        File file = uploadedFiles.remove(id);
        if (file != null) {
            file.delete();
            File parent = file.getParentFile();
            if (parent != null && (parent.listFiles() == null || parent.listFiles().length == 0)) {
                parent.delete();
            }
        }
    }

    @PreDestroy
    public void destroy() {
        uploadedFiles.keySet().forEach(id -> removeFile(id));
    }
}
