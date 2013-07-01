/*
 * Copyright 2008 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.dingziran.effective.client.content.widgets;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.RunAsyncCallback;
import com.google.gwt.i18n.client.Constants;
import com.dingziran.effective.client.ContentWidget;
import com.dingziran.effective.client.ContentWidgetView;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.user.client.ui.FileUpload;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * Example file.
 */
public class CwFileUpload extends ContentWidget {
  /**
   * The constants used in this Content Widget.
   */
  public static interface CwConstants extends Constants {
    String cwFileUploadButton();

    String cwFileUploadDescription();

    String cwFileUploadName();

    String cwFileUploadNoFileError();

    String cwFileUploadSelectFile();

    String cwFileUploadSuccessful();
  }

  /**
   * An instance of the constants.
   */
  private final CwConstants constants;

  /**
   * Constructor.
   *
   * @param constants the constants
   */
  public CwFileUpload(CwConstants constants) {
    super(constants.cwFileUploadName(), constants.cwFileUploadDescription());
    this.constants = constants;
    view = new ContentWidgetView(hasMargins(), hasScrollableContent());
    view.setName(getName());
    view.setDescription(getDescription());
    setWidget(view);
    // Create a vertical panel to align the content
    VerticalPanel vPanel = new VerticalPanel();

    // Add a label
    vPanel.add(new HTML(constants.cwFileUploadSelectFile()));

    // Add a file upload widget
    final FileUpload fileUpload = new FileUpload();
    fileUpload.ensureDebugId("cwFileUpload");
    vPanel.add(fileUpload);
    final String msg1=constants.cwFileUploadNoFileError();
    final String msg2=constants.cwFileUploadSuccessful();

    // Add a button to upload the file
    Button uploadButton = new Button(constants.cwFileUploadButton());
    uploadButton.addClickHandler(new ClickHandler() {
      public void onClick(ClickEvent event) {
        String filename = fileUpload.getFilename();
        if (filename.length() == 0) {
          Window.alert(msg1);
        } else {
          Window.alert(msg2);
        }
      }
    });
    vPanel.add(new HTML("<br>"));
    vPanel.add(uploadButton);

    view.setExample(vPanel);
  }

}
