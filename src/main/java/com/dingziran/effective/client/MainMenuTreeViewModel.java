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

import com.dingziran.effective.client.content.widgets.CwBasicButton;
import com.dingziran.effective.client.content.widgets.CwCheckBox;
import com.dingziran.effective.client.content.widgets.CwCustomButton;
import com.dingziran.effective.client.content.widgets.CwDatePicker;
import com.dingziran.effective.client.content.widgets.CwFileUpload;
import com.dingziran.effective.client.content.widgets.CwHyperlink;
import com.dingziran.effective.client.content.widgets.CwPerson;
import com.dingziran.effective.client.content.widgets.CwRadioButton;
import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.core.client.prefetch.RunAsyncCode;
import com.google.gwt.i18n.client.Constants;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.SelectionModel;
import com.google.gwt.view.client.TreeViewModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The {@link TreeViewModel} used by the main menu.
 */
public class MainMenuTreeViewModel implements TreeViewModel {

  /**
   * The constants used in the menu.
   */
  public static interface MenuConstants extends Constants {

    String categoryCells();

    String categoryI18N();

    String categoryLists();

    String categoryOther();

    String categoryPanels();

    String categoryPopups();

    String categoryTables();

    String categoryTextInput();

    String categoryWidgets();
  }

  /**
   * The cell used to render categories.
   */
  private static class CategoryCell extends AbstractCell<Category> {
    @Override
    public void render(Context context, Category value, SafeHtmlBuilder sb) {
      if (value != null) {
        sb.appendEscaped(value.getName());
      }
    }
  }

  /**
   * The cell used to render examples.
   */
  private static class ContentWidgetCell extends AbstractCell<ContentWidget> {
    @Override
    public void render(Context context, ContentWidget value, SafeHtmlBuilder sb) {
      if (value != null) {
        sb.appendEscaped(value.getName());
      }
    }
  }

  /**
   * A top level category in the tree.
   */
  public class Category {

    private final ListDataProvider<ContentWidget> examples =
        new ListDataProvider<ContentWidget>();
    private final String name;
    private NodeInfo<ContentWidget> nodeInfo;

    public Category(String name) {
      this.name = name;
    }

    public void addExample(ContentWidget example) {
      examples.getList().add(example);
      contentCategory.put(example, this);
      contentToken.put(Showcase.getContentWidgetToken(example), example);
    }

    public String getName() {
      return name;
    }

    /**
     * Get the node info for the examples under this category.
     * 
     * @return the node info
     */
    public NodeInfo<ContentWidget> getNodeInfo() {
      if (nodeInfo == null) {
        nodeInfo = new DefaultNodeInfo<ContentWidget>(examples,
            contentWidgetCell, selectionModel, null);
      }
      return nodeInfo;
    }

  }

  /**
   * The top level categories.
   */
  private final ListDataProvider<Category> categories = new ListDataProvider<Category>();

  /**
   * A mapping of {@link ContentWidget}s to their associated categories.
   */
  private final Map<ContentWidget, Category> contentCategory = new HashMap<ContentWidget, Category>();

  /**
   * The cell used to render examples.
   */
  private final ContentWidgetCell contentWidgetCell = new ContentWidgetCell();

  /**
   * A mapping of history tokens to their associated {@link ContentWidget}.
   */
  private final Map<String, ContentWidget> contentToken = new HashMap<String, ContentWidget>();

  /**
   * The selection model used to select examples.
   */
  private final SelectionModel<ContentWidget> selectionModel;

  public MainMenuTreeViewModel(ShowcaseConstants constants,
      SelectionModel<ContentWidget> selectionModel) {
    this.selectionModel = selectionModel;
    initializeTree(constants);
  }

  /**
   * Get the {@link Category} associated with a widget.
   * 
   * @param widget the {@link ContentWidget}
   * @return the associated {@link Category}
   */
  public Category getCategoryForContentWidget(ContentWidget widget) {
    return contentCategory.get(widget);
  }

  /**
   * Get the content widget associated with the specified history token.
   * 
   * @param token the history token
   * @return the associated {@link ContentWidget}
   */
  public ContentWidget getContentWidgetForToken(String token) {
    return contentToken.get(token);
  }

  public <T> NodeInfo<?> getNodeInfo(T value) {
    if (value == null) {
      // Return the top level categories.
      return new DefaultNodeInfo<Category>(categories, new CategoryCell());
    } else if (value instanceof Category) {
      // Return the examples within the category.
      Category category = (Category) value;
      return category.getNodeInfo();
    }
    return null;
  }

  public boolean isLeaf(Object value) {
    return value != null && !(value instanceof Category);
  }

  /**
   * Get the set of all {@link ContentWidget}s used in the model.
   * 
   * @return the {@link ContentWidget}s
   */
  Set<ContentWidget> getAllContentWidgets() {
    Set<ContentWidget> widgets = new HashSet<ContentWidget>();
    for (Category category : categories.getList()) {
      for (ContentWidget example : category.examples.getList()) {
        widgets.add(example);
      }
    }
    return widgets;
  }

  /**
   * Initialize the top level categories in the tree.
   */
  private void initializeTree(ShowcaseConstants constants) {
    List<Category> catList = categories.getList();

    // Widgets.
    {
      Category category = new Category(constants.categoryWidgets());
      catList.add(category);
      // CwCheckBox is the default example, so don't prefetch it.
      category.addExample(new CwCheckBox(constants));
      category.addExample(new CwRadioButton(constants));
      category.addExample(new CwBasicButton(constants));
      category.addExample(new CwCustomButton(constants));
      category.addExample(new CwFileUpload(constants));
      category.addExample(new CwDatePicker(constants));
      category.addExample(new CwHyperlink(constants));
      category.addExample(new CwPerson(constants));
    }

  }
}
