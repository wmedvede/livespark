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

import javax.inject.Inject;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import org.jboss.errai.common.client.dom.Anchor;
import org.jboss.errai.common.client.dom.DOMUtil;
import org.jboss.errai.common.client.dom.Div;
import org.jboss.errai.common.client.dom.Span;
import org.jboss.errai.ui.shared.api.annotations.DataField;
import org.jboss.errai.ui.shared.api.annotations.Templated;

@Templated
public class DocumentWidgetViewImpl extends Composite implements DocumentWidgetView {

    @Inject
    @DataField
    private Div inputContainer;

    @Inject
    @DataField
    private Div anchorContainer;

    @Inject
    @DataField
    private Span documentIcon;

    @Inject
    @DataField
    private Anchor downloadAnchor;

    private Presenter presenter;

    @Override
    public void setPresenter(Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void render(String documentId,
                       String anchorText,
                       String iconClassName) {
        downloadAnchor.setHref("rest/documents/download/" + documentId);
        downloadAnchor.setTextContent(anchorText);
        DOMUtil.removeCSSClass(anchorContainer,
                               "hidden");
        documentIcon.setClassName(iconClassName);
        showUploadForm(presenter.isEnabled());
    }

    @Override
    public void showUploadForm(boolean show) {
        DOMUtil.removeAllChildren(inputContainer);
        if (show) {
            inputContainer.appendChild(presenter.getUploadForm().getElement());
        }
    }

    @Override
    public void uploadError(String message) {
        Window.alert(message);
    }
}
