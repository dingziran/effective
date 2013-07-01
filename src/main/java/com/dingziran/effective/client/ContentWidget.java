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
package com.dingziran.effective.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.HasValueChangeHandlers;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.i18n.client.LocaleInfo;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.SimpleLayoutPanel;
import com.google.gwt.user.client.ui.Widget;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * A widget used to show GWT examples in the ContentPanel.
 * </p>
 * <p>
 * This {@link Widget} uses a lazy initialization mechanism so that the content
 * is not rendered until the onInitialize method is called, which happens the
 * first time the {@link Widget} is added to the page. The data in the source
 * and css tabs are loaded using an RPC call to the server.
 * </p>
 */
public abstract class ContentWidget extends SimpleLayoutPanel implements
    HasValueChangeHandlers<String> {



  /**
   * Get the simple filename of a class.
   * 
   * @param c the class
   */
  protected static String getSimpleName(Class<?> c) {
    String name = c.getName();
    return name.substring(name.lastIndexOf(".") + 1);
  }

  /**
   * A description of the example.
   */
  private final SafeHtml description;

  /**
   * True if this example has associated styles, false if not.
   */
  //private final boolean hasStyle;

  /**
   * The name of the example.
   */
  private final String name;

  /**
   * The view that holds the name, description, and example.
   */
  protected ContentWidgetView view;



  /**
   * Construct a {@link ContentWidget}.
   * 
   * @param name the text name of the example
   * @param description a text description of the example
   * @param hasStyle true if the example has associated styles
   * @param rawSourceFiles the list of raw source files to include
   */
  public ContentWidget(String name, String description) {
    this(name, SafeHtmlUtils.fromString(description));
  }

  /**
   * Construct a {@link ContentWidget}.
   * 
   * @param name the text name of the example
   * @param description a safe html description of the example
   * @param hasStyle true if the example has associated styles
   * @param rawSourceFiles the list of raw source files to include
   */
  public ContentWidget(String name, SafeHtml description) {
    this.name = name;
    this.description = description;
  }

  public HandlerRegistration addValueChangeHandler(ValueChangeHandler<String> handler) {
    return addHandler(handler, ValueChangeEvent.getType());
  }

  /**
   * Get the description of this example.
   * 
   * @return a description for this example
   */
  public final SafeHtml getDescription() {
    return description;
  }

  /**
   * Get the name of this example to use as a title.
   * 
   * @return a name for this example
   */
  public final String getName() {
    return name;
  }

  /**
   * Check if the widget should have margins.
   * 
   * @return true to use margins, false to flush against edges
   */
  public boolean hasMargins() {
    return true;
  }

  /**
   * Check if the widget should be wrapped in a scrollable div.
   * 
   * @return true to use add scrollbars, false not to
   */
  public boolean hasScrollableContent() {
    return true;
  }



}
