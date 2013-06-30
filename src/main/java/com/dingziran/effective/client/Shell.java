/*
 * Copyright 2010 Google Inc.
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
package com.dingziran.effective.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Display;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.dom.client.TableCellElement;
import com.google.gwt.dom.client.TableElement;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.http.client.UrlBuilder;
import com.google.gwt.i18n.client.HasDirection.Direction;
import com.google.gwt.i18n.client.LocaleInfo;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.cellview.client.CellTree;
import com.google.gwt.user.cellview.client.HasKeyboardSelectionPolicy.KeyboardSelectionPolicy;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.Window.Location;
import com.google.gwt.user.client.ui.AbstractImagePrototype;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.ResizeComposite;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.SimpleLayoutPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.TreeViewModel;

import java.util.Date;
import java.util.List;

/**
 * Application shell for Showcase sample.
 */
public class Shell extends ResizeComposite {

  interface ShowcaseShellUiBinder extends UiBinder<Widget, Shell> {
  }

  private static ShowcaseShellUiBinder uiBinder = GWT.create(
      ShowcaseShellUiBinder.class);

  /**
   * The panel that holds the content.
   */
  @UiField
  SimpleLayoutPanel contentPanel;

  /**
   * The container around the links at the top of the app.
   */
  @UiField
  TableElement linkCell;

  /**
   * A drop box used to change the locale.
   */
  @UiField
  ListBox localeBox;

  /**
   * The container around locale selection.
   */
  @UiField
  TableCellElement localeSelectionCell;

  /**
   * The main menu used to navigate to examples.
   */
//  @UiField(provided = true)
//  CellTree mainMenu;

  /**
   * The button used to show the example.
   */
  @UiField
  Anchor tabExample;

  /**
   * The button used to show the source CSS style.
   */
  @UiField
  Anchor tabStyle;

  /**
   * The button used to show the source code.
   */
  @UiField
  Anchor tabSource;

  /**
   * The list of available source code.
   */
  @UiField
  ListBox tabSourceList;

  /**
   * The current {@link ContentWidget} being displayed.
   */
//  private ContentWidget content;

  /**
   * The handler used to handle user requests to view raw source.
   */
  private HandlerRegistration contentSourceHandler;

  /**
   * The widget that holds CSS or source code for an example.
   */
  private HTML contentSource = new HTML();

  /**
   * The html used to show a loading icon.
   */
//  private final String loadingHtml;

  /**
   * Construct the {@link Shell}.
   *
   * @param treeModel the treeModel that backs the main menu
   */
  public Shell() {
    initWidget(uiBinder.createAndBindUi(this));
  }

 
}
