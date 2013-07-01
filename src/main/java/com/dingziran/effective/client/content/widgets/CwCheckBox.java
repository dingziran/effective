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

import com.google.gwt.i18n.client.Constants;
import com.dingziran.effective.client.ContentWidget;
import com.dingziran.effective.client.ContentWidgetView;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * Example file.
 */
public class CwCheckBox extends ContentWidget {
  /**
   * The constants used in this Content Widget.
   */
  public static interface CwConstants extends Constants {
    String cwCheckBoxCheckAll();

    String[] cwCheckBoxDays();

    String cwCheckBoxDescription();

    String cwCheckBoxName();
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
  public CwCheckBox(CwConstants constants) {
    super(constants.cwCheckBoxName(), constants.cwCheckBoxDescription());
    this.constants = constants;

    view = new ContentWidgetView(hasMargins(), hasScrollableContent());
    view.setName(getName());
    view.setDescription(getDescription());
    setWidget(view);
    // Create a vertical panel to align the check boxes
    VerticalPanel vPanel = new VerticalPanel();
    HTML label = new HTML(constants.cwCheckBoxCheckAll());
    label.ensureDebugId("cwCheckBox-label");
    vPanel.add(label);

    // Add a checkbox for each day of the week
    String[] daysOfWeek = constants.cwCheckBoxDays();
    for (int i = 0; i < daysOfWeek.length; i++) {
      String day = daysOfWeek[i];
      CheckBox checkBox = new CheckBox(day);
      checkBox.ensureDebugId("cwCheckBox-" + day);

      // Disable the weekends
      if (i >= 5) {
        checkBox.setEnabled(false);
      }

      vPanel.add(checkBox);
    }
    view.setExample(vPanel);
  }

}
