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
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.client.Constants;
import com.dingziran.effective.client.ContentWidget;
import com.dingziran.effective.client.ContentWidgetView;
import com.dingziran.effective.client.Showcase;
import com.dingziran.effective.shared.Factory.GoalRequest;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.web.bindery.requestfactory.shared.Receiver;

/**
 * Example file.
 */
public class CwBasicButton extends ContentWidget {
  /**
   * The constants used in this Content Widget.
   */
  public static interface CwConstants extends Constants {
    String cwBasicButtonClickMessage();

    String cwBasicButtonDescription();

    String cwBasicButtonDisabled();

    String cwBasicButtonName();

    String cwBasicButtonNormal();
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
  public CwBasicButton(CwConstants constants) {
    super(constants.cwBasicButtonName(), constants.cwBasicButtonDescription());
    this.constants = constants;
    view = new ContentWidgetView(hasMargins(), hasScrollableContent());
    view.setName(getName());
    view.setDescription(getDescription());
    setWidget(view);
    // Create a panel to align the widgets
    HorizontalPanel hPanel = new HorizontalPanel();
    hPanel.setSpacing(10);
    final String msg=constants.cwBasicButtonClickMessage();
    // Add a normal button
    Button normalButton = new Button(
        constants.cwBasicButtonNormal(), new ClickHandler() {
          public void onClick(ClickEvent event) {
            Window.alert(msg);
          }
        });
    normalButton.ensureDebugId("cwBasicButton-normal");
    hPanel.add(normalButton);

    // Add a disabled button
    Button disabledButton = new Button(constants.cwBasicButtonDisabled());
    disabledButton.ensureDebugId("cwBasicButton-disabled");
    disabledButton.setEnabled(false);
    hPanel.add(disabledButton);
    view.setExample(hPanel);
  }


}
