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
import javax.inject.Inject;

import com.google.gwt.user.client.Event;
import org.gwtbootstrap3.client.ui.Form;
import org.jboss.errai.common.client.dom.Div;
import org.jboss.errai.common.client.dom.Input;
import org.jboss.errai.common.client.dom.Span;
import org.jboss.errai.common.client.dom.TextInput;
import org.jboss.errai.common.client.ui.ElementWrapperWidget;
import org.jboss.errai.ui.client.local.api.IsElement;
import org.jboss.errai.ui.shared.api.annotations.DataField;
import org.jboss.errai.ui.shared.api.annotations.EventHandler;
import org.jboss.errai.ui.shared.api.annotations.SinkNative;
import org.jboss.errai.ui.shared.api.annotations.Templated;

@Templated
public class UploadFormViewImpl implements UploadFormView,
                                           IsElement {

    private static final String FAKEPATH = "c:\\fakepath\\";

    private Presenter presenter;

    @DataField
    private Form form = new Form();

    @Inject
    @DataField
    private Input previousFile;

    @Inject
    @DataField
    private Input fileInput;

    @Inject
    @DataField
    private Div inputGroup;

    @Inject
    @DataField
    private TextInput input;

    @Inject
    @DataField
    private Span inputButton;

    @PostConstruct
    public void init() {
        form.clear();
        form.add(ElementWrapperWidget.getWidget(fileInput));
        form.add(ElementWrapperWidget.getWidget(inputGroup));
        form.add(ElementWrapperWidget.getWidget(previousFile));
        form.addSubmitCompleteHandler(event -> {
            presenter.onSubmit(event.getResults());
            input.setValue("");
            fileInput.setValue("");
        });
    }

    @Override
    public void setLastUploadId(String id) {
        previousFile.setValue(id);
    }

    @Override
    public void setPresenter(Presenter presenter) {
        this.presenter = presenter;
    }

    @SinkNative(Event.ONCHANGE)
    @EventHandler("fileInput")
    public void uploadFile(Event event) {
        if (presenter.isEnabled()) {
            String fileName = fileInput.getValue();
            if (fileName.toLowerCase().startsWith(FAKEPATH)) {
                fileName = fileName.substring(FAKEPATH.length());
            }
            input.setValue(fileName);
            form.submit();
        }
    }

    @SinkNative(Event.ONCLICK)
    @EventHandler("inputButton")
    public void onButtonClick(Event event) {
        if (presenter.isEnabled()) {
            fileInput.click();
        }
    }
}
