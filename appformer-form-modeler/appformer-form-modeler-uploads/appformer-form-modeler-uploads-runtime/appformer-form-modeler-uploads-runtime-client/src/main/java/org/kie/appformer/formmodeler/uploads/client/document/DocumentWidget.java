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

package org.kie.appformer.formmodeler.uploads.client.document;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import org.jboss.errai.ui.client.local.spi.TranslationService;
import org.kie.appformer.formmodeler.uploads.client.document.upload.UploadForm;
import org.kie.appformer.formmodeler.uploads.client.document.util.DocumentLinkTextGenerator;
import org.kie.appformer.formmodeler.uploads.client.document.util.FileIconProvider;
import org.kie.appformer.formmodeler.uploads.shared.model.DocumentUpload;

import static org.kie.appformer.formmodeler.uploads.client.document.resources.i18n.DocumentUploadConstants.DOCUMENT_UPLOAD_ERROR_MESSAGE;

@Dependent
public class DocumentWidget implements DocumentWidgetView.Presenter,
                                       IsWidget,
                                       HasValue<DocumentUpload> {

    private UploadForm uploadForm;

    private DocumentWidgetView view;

    private TranslationService translationService;

    private DocumentUpload value;

    private boolean readOnly = false;

    private boolean enabled = true;

    @Inject
    public DocumentWidget(UploadForm uploadForm,
                          DocumentWidgetView view,
                          TranslationService translationService) {
        this.uploadForm = uploadForm;
        this.view = view;
        this.translationService = translationService;
        uploadForm.init(new UploadForm.DocumentUploadListener() {
            @Override
            public void onSuccess(DocumentUpload document) {
                setValue(document,
                         true);
            }

            @Override
            public void onError() {
                view.uploadError(DocumentWidget.this.translationService.getTranslation(DOCUMENT_UPLOAD_ERROR_MESSAGE));
            }
        });
        view.setPresenter(this);
        refreshUploadForm();
    }

    @Override
    public DocumentUpload getValue() {
        return value;
    }

    @Override
    public void setValue(DocumentUpload value) {
        setValue(value,
                 false);
    }

    @Override
    public void setValue(DocumentUpload value,
                         boolean fireEvents) {
        if (value == null) {
            return;
        }

        if (this.value == null && value != null || !value.equals(this.value)) {
            this.value = value;

            String iconStyle = FileIconProvider.getNormalIconStyleForDocument(value);

            view.render(value.getDocumentId(),
                        getAnchorText(),
                        iconStyle);

            if (fireEvents) {
                ValueChangeEvent.fire(this,
                                      value);
            }
        }
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
        uploadForm.setEnabled(enabled);
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    public void setReadOnly(boolean readOnly) {
        this.readOnly = readOnly;
        refreshUploadForm();
    }

    protected void refreshUploadForm() {
        view.showUploadForm(!readOnly);
    }

    @Override
    public boolean isReadOnly() {
        return readOnly;
    }

    @Override
    public UploadForm getUploadForm() {
        return uploadForm;
    }

    protected String getAnchorText() {
        if (value == null) {
            return "";
        }

        return DocumentLinkTextGenerator.generateDownloadLinkText(value);
    }

    @Override
    public Widget asWidget() {
        return view.asWidget();
    }

    @Override
    public void fireEvent(GwtEvent<?> event) {
        view.asWidget().fireEvent(event);
    }

    @Override
    public HandlerRegistration addValueChangeHandler(ValueChangeHandler<DocumentUpload> valueChangeHandler) {
        return view.asWidget().addHandler(valueChangeHandler,
                                          ValueChangeEvent.getType());
    }
}
