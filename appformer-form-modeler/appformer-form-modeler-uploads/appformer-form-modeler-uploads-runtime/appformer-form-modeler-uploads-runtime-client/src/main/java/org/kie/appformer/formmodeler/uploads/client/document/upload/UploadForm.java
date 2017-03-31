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

package org.kie.appformer.formmodeler.uploads.client.document.upload;

import javax.annotation.PostConstruct;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

import org.jboss.errai.common.client.api.IsElement;
import org.jboss.errai.common.client.dom.HTMLElement;
import org.kie.appformer.formmodeler.uploads.client.document.upload.util.UploadResponseParser;
import org.kie.appformer.formmodeler.uploads.shared.model.DocumentUpload;

@Dependent
public class UploadForm implements UploadFormView.Presenter,
                                   IsElement {

    private UploadFormView view;

    private UploadResponseParser uploadResponseParser;

    private DocumentUploadListener uploadListener;
    private boolean enabled = true;

    @Inject
    public UploadForm(UploadFormView view,
                      UploadResponseParser uploadResponseParser) {
        this.view = view;
        this.uploadResponseParser = uploadResponseParser;
    }

    @PostConstruct
    public void init() {
        view.setPresenter(this);
    }

    public void init(DocumentUploadListener uploadListener) {
        this.uploadListener = uploadListener;
    }

    @Override
    public HTMLElement getElement() {
        return view.getElement();
    }

    @Override
    public void onSubmit(String response) {
        try {
            DocumentUpload document = uploadResponseParser.parseUploadResponse(response);
            view.setLastUploadId(document.getDocumentId());
            if (uploadListener != null) {
                uploadListener.onSuccess(document);
            }
        } catch (Exception ex) {
            notifyUploadError();
        }
    }

    public void notifyUploadError() {
        if (uploadListener != null) {
            uploadListener.onError();
        }
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    public interface DocumentUploadListener {

        void onSuccess(DocumentUpload document);

        void onError();
    }
}
