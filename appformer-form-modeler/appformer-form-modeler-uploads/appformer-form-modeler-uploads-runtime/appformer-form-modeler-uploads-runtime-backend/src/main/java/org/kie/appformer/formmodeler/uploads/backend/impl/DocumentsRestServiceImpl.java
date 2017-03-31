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
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.ws.rs.core.Response;

import com.google.common.io.Files;
import com.google.gson.Gson;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;
import org.kie.appformer.formmodeler.uploads.backend.DocumentsRestService;
import org.kie.appformer.formmodeler.uploads.backend.UploadedDocumentManager;
import org.kie.appformer.formmodeler.uploads.shared.model.DocumentUpload;

@Dependent
public class DocumentsRestServiceImpl implements DocumentsRestService {

    @Inject
    private UploadedDocumentManager manager;

    public static final String STORAGE_FOLDER = "docs";

    private File rootStorage;

    public DocumentsRestServiceImpl() {
        rootStorage = new File(System.getProperty("java.io.tmpdir") + "/" + STORAGE_FOLDER);
    }

    @Override
    public Response downloadDocument(String documentId) {
        File content = manager.getFile(documentId);

        Response.ResponseBuilder response;
        if (content != null) {
            response = Response.ok(content);
            response.header("Content-Disposition",
                            "attachment; filename=" + content.getName());
        } else {
            response = Response.noContent();
        }
        return response.build();
    }

    @Override
    public String uploadDocument(MultipartFormDataInput multipart) {
        String response = "";
        InputStream in = null;
        try {
            in = multipart.getFormDataPart("fileInput",
                                           InputStream.class,
                                           null);

            String fileName = multipart.getFormDataPart("fileName",
                                                        String.class,
                                                        null);

            String previousFile = multipart.getFormDataPart("previousFile",
                                                            String.class,
                                                            null);

            if (in != null) {
                String id = UUID.randomUUID().toString();

                File file = new File(rootStorage,
                                     id + "/" + fileName);

                file.deleteOnExit();

                Files.createParentDirs(file);

                file.createNewFile();

                byte[] buffer = IOUtils.toByteArray(in);

                Files.write(buffer,
                            file);

                manager.storeField(id,
                                   file);

                DocumentUpload documentUpload = new DocumentUpload(
                        id,
                        fileName,
                        file.length()
                );

                if (!StringUtils.isEmpty(previousFile)) {
                    manager.removeFile(previousFile);
                }

                Map<String, DocumentUpload> responseMap = new HashMap<>();

                responseMap.put("document",
                                documentUpload);
                Gson gson = new Gson();

                response = gson.toJson(responseMap);
            }
        } catch (IOException e) {
            response = e.getMessage();
        } finally {
            IOUtils.closeQuietly(in);
        }
        return response;
    }
}
