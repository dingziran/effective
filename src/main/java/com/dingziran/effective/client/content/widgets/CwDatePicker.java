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
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.i18n.client.Constants;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.dingziran.effective.client.ContentWidget;
import com.dingziran.effective.client.ContentWidgetView;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.datepicker.client.DateBox;
import com.google.gwt.user.datepicker.client.DatePicker;

import java.util.Date;

/**
 * Example file.
 */
public class CwDatePicker extends ContentWidget {

  /**
   * The constants used in this Content Widget.
   */
  public static interface CwConstants extends Constants {
    String cwDatePickerBoxLabel();

    String cwDatePickerDescription();

    String cwDatePickerLabel();

    String cwDatePickerName();
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
  public CwDatePicker(CwConstants constants) {
    super(constants.cwDatePickerName(), constants.cwDatePickerDescription());
    this.constants = constants;
    view = new ContentWidgetView(hasMargins(), hasScrollableContent());
    view.setName(getName());
    view.setDescription(getDescription());
    setWidget(view);
    // Create a basic date picker
    DatePicker datePicker = new DatePicker();
    final Label text = new Label();

    // Set the value in the text box when the user selects a date
    datePicker.addValueChangeHandler(new ValueChangeHandler<Date>() {
      public void onValueChange(ValueChangeEvent<Date> event) {
        Date date = event.getValue();
        String dateString = DateTimeFormat.getMediumDateFormat().format(date);
        text.setText(dateString);
      }
    });

    // Set the default value
    datePicker.setValue(new Date(), true);

    // Create a DateBox
    DateTimeFormat dateFormat = DateTimeFormat.getLongDateFormat();
    DateBox dateBox = new DateBox();
    dateBox.setFormat(new DateBox.DefaultFormat(dateFormat));

    // Combine the widgets into a panel and return them
    VerticalPanel vPanel = new VerticalPanel();
    vPanel.add(new HTML(constants.cwDatePickerLabel()));
    vPanel.add(text);
    vPanel.add(datePicker);
    vPanel.add(new HTML(constants.cwDatePickerBoxLabel()));
    vPanel.add(dateBox);
    view.setExample(vPanel);
  }

}
