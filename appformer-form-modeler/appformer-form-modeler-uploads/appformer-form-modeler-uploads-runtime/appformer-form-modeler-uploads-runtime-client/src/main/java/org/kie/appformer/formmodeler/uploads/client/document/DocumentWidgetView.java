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

import com.google.gwt.user.client.ui.IsWidget;
import org.kie.appformer.formmodeler.uploads.client.document.upload.UploadForm;

public interface DocumentWidgetView extends IsWidget {

    interface Presenter {

        boolean isEnabled();

        boolean isReadOnly();

        UploadForm getUploadForm();
    }

    void showUploadForm(boolean enabled);

    void setPresenter(Presenter presenter);

    void render(String documentId,
                String anchorText,
                String iconClassName);

    void uploadError(String message);
}
