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

import java.util.HashMap;
import java.util.Map;

import org.gwtbootstrap3.client.ui.constants.IconType;
import org.kie.appformer.formmodeler.uploads.shared.model.DocumentUpload;

public class FileIconProvider {

    public static final IconType DEFAULT_ICON = IconType.FILE_O;

    protected static Map<String, IconType> fileIcons = new HashMap<>();

    static {
        // Audio icons
        fileIcons.put("aac",
                      IconType.FILE_AUDIO_O);
        fileIcons.put("mp3",
                      IconType.FILE_AUDIO_O);
        fileIcons.put("m4a",
                      IconType.FILE_AUDIO_O);
        fileIcons.put("ogg",
                      IconType.FILE_AUDIO_O);
        fileIcons.put("wav",
                      IconType.FILE_AUDIO_O);
        fileIcons.put("wma",
                      IconType.FILE_AUDIO_O);

        // Code icons
        fileIcons.put("bat",
                      IconType.FILE_CODE_O);
        fileIcons.put("java",
                      IconType.FILE_CODE_O);
        fileIcons.put("c",
                      IconType.FILE_CODE_O);
        fileIcons.put("class",
                      IconType.FILE_CODE_O);
        fileIcons.put("cpp",
                      IconType.FILE_CODE_O);
        fileIcons.put("h",
                      IconType.FILE_CODE_O);
        fileIcons.put("cmd",
                      IconType.FILE_CODE_O);
        fileIcons.put("cmp",
                      IconType.FILE_CODE_O);
        fileIcons.put("js",
                      IconType.FILE_CODE_O);
        fileIcons.put("html",
                      IconType.FILE_CODE_O);
        fileIcons.put("htmls",
                      IconType.FILE_CODE_O);
        fileIcons.put("xml",
                      IconType.FILE_CODE_O);
        fileIcons.put("css",
                      IconType.FILE_CODE_O);
        fileIcons.put("less",
                      IconType.FILE_CODE_O);

        // Spreadsheet icons
        fileIcons.put("xlt",
                      IconType.FILE_EXCEL_O);
        fileIcons.put("xls",
                      IconType.FILE_EXCEL_O);
        fileIcons.put("xlsx",
                      IconType.FILE_EXCEL_O);
        fileIcons.put("csv",
                      IconType.FILE_EXCEL_O);
        fileIcons.put("ods",
                      IconType.FILE_EXCEL_O);
        fileIcons.put("ots",
                      IconType.FILE_EXCEL_O);

        // Image icons
        fileIcons.put("bmp",
                      IconType.FILE_PHOTO_O);
        fileIcons.put("jpeg",
                      IconType.FILE_PHOTO_O);
        fileIcons.put("jpg",
                      IconType.FILE_PHOTO_O);
        fileIcons.put("png",
                      IconType.FILE_PHOTO_O);
        fileIcons.put("gif",
                      IconType.FILE_PHOTO_O);
        fileIcons.put("ico",
                      IconType.FILE_PHOTO_O);
        fileIcons.put("tiff",
                      IconType.FILE_PHOTO_O);
        fileIcons.put("raw",
                      IconType.FILE_PHOTO_O);

        // Presentation icons
        fileIcons.put("odt",
                      IconType.FILE_POWERPOINT_O);
        fileIcons.put("otp",
                      IconType.FILE_POWERPOINT_O);
        fileIcons.put("odg",
                      IconType.FILE_POWERPOINT_O);
        fileIcons.put("ppt",
                      IconType.FILE_POWERPOINT_O);
        fileIcons.put("pptx",
                      IconType.FILE_POWERPOINT_O);
        fileIcons.put("ppsx",
                      IconType.FILE_POWERPOINT_O);
        fileIcons.put("potm",
                      IconType.FILE_POWERPOINT_O);

        // Video icons
        fileIcons.put("avi",
                      IconType.FILE_VIDEO_O);
        fileIcons.put("mov",
                      IconType.FILE_VIDEO_O);
        fileIcons.put("mp4",
                      IconType.FILE_VIDEO_O);
        fileIcons.put("wmv",
                      IconType.FILE_VIDEO_O);

        // TextProcessor icon
        fileIcons.put("odt",
                      IconType.FILE_WORD_O);
        fileIcons.put("ott",
                      IconType.FILE_WORD_O);
        fileIcons.put("doc",
                      IconType.FILE_WORD_O);
        fileIcons.put("docx",
                      IconType.FILE_WORD_O);
        fileIcons.put("dot",
                      IconType.FILE_WORD_O);

        // Archive icon
        fileIcons.put("zip",
                      IconType.FILE_ARCHIVE_O);
        fileIcons.put("rar",
                      IconType.FILE_ARCHIVE_O);
        fileIcons.put("tar",
                      IconType.FILE_ARCHIVE_O);
        fileIcons.put("z7",
                      IconType.FILE_ARCHIVE_O);
        fileIcons.put("gz",
                      IconType.FILE_ARCHIVE_O);

        // Misc icons
        fileIcons.put("txt",
                      IconType.FILE_TEXT_O);
        fileIcons.put("pdf",
                      IconType.FILE_PDF_O);
    }

    public static String getSmallIconStyleForDocument(DocumentUpload document) {
        return getIconStyleForDocument(document,
                                       "");
    }

    public static String getNormalIconStyleForDocument(DocumentUpload document) {
        return getIconStyleForDocument(document,
                                       "fa-2x");
    }

    protected static String getIconStyleForDocument(DocumentUpload document,
                                                    String size) {
        if (document == null) {
            throw new IllegalArgumentException("Document cannot be null");
        }
        String extension = "";

        if (document.getName().indexOf(".") != -1) {
            extension = document.getName().substring(document.getName().lastIndexOf(".") + 1);
        }

        IconType icon = fileIcons.get(extension.toLowerCase());
        if (icon == null) {
            icon = DEFAULT_ICON;
        }

        String className = "fa " + size + " " + icon.getCssName();
        return className;
    }
}
