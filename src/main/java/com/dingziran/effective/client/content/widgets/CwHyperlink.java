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
import com.dingziran.effective.client.Showcase;
import com.dingziran.effective.client.ShowcaseConstants;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * Example file.
 */
public class CwHyperlink extends ContentWidget {
  /**
   * The constants used in this Content Widget.
   */
  public static interface CwConstants extends Constants {
    String cwHyperlinkChoose();

    String cwHyperlinkDescription();

    String cwHyperlinkName();
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
  public CwHyperlink(CwConstants constants) {
    super(
        constants.cwHyperlinkName(), constants.cwHyperlinkDescription());
    this.constants = constants;
    view = new ContentWidgetView(hasMargins(), hasScrollableContent());
    view.setName(getName());
    view.setDescription(getDescription());
    setWidget(view);
    // Add a label
    VerticalPanel vPanel = new VerticalPanel();
    vPanel.add(new HTML(constants.cwHyperlinkChoose()));
    vPanel.setSpacing(5);

    // Add a hyper link to each section in the Widgets category
    ShowcaseConstants allConstants = (ShowcaseConstants) constants;
    vPanel.add(getHyperlink(CwCheckBox.class, allConstants.cwCheckBoxName()));
    vPanel.add(
        getHyperlink(CwRadioButton.class, allConstants.cwRadioButtonName()));
    vPanel.add(
        getHyperlink(CwBasicButton.class, allConstants.cwBasicButtonName()));
    vPanel.add(
        getHyperlink(CwCustomButton.class, allConstants.cwCustomButtonName()));
    vPanel.add(
        getHyperlink(CwFileUpload.class, allConstants.cwFileUploadName()));
    vPanel.add(
        getHyperlink(CwDatePicker.class, allConstants.cwDatePickerName()));

    view.setExample(vPanel);
  }


  /**
   * Get a {@link Hyperlink} to a section based on the name of the
   * {@link ContentWidget} example.
   *
   * @param cwClass the {@link ContentWidget} class
   * @param name the name to display for the link
   * @return a {@link Hyperlink}
   */
  private <C extends ContentWidget> Hyperlink getHyperlink(
      Class<C> cwClass, String name) {
    Hyperlink link = new Hyperlink(
        name, Showcase.getContentWidgetToken(cwClass));
    link.ensureDebugId("cwHyperlink-" + cwClass.getName());
    return link;
  }
}
