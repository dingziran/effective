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
package com.dingziran.effective.client.content.widgets.person;

import com.dingziran.effective.shared.Factory;
import com.dingziran.effective.shared.GoalProxy;
import com.google.gwt.cell.client.TextCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.DataGrid;
import com.google.gwt.user.cellview.client.HasKeyboardSelectionPolicy.KeyboardSelectionPolicy;
import com.google.gwt.user.cellview.client.SimplePager;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.Range;
import com.google.gwt.view.client.RangeChangeEvent;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SingleSelectionModel;
import com.google.web.bindery.requestfactory.shared.EntityProxyChange;
import com.google.web.bindery.requestfactory.shared.EntityProxyId;
import com.google.web.bindery.requestfactory.shared.Receiver;
import com.google.web.bindery.requestfactory.shared.WriteOperation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A paging table with summaries of all known people.
 */
public class PersonSummaryWidget extends Composite {

  interface Binder extends UiBinder<Widget, PersonSummaryWidget> {
  }

  private class DescriptionColumn extends Column<GoalProxy, String> {
    public DescriptionColumn() {
      super(new TextCell());
    }

    @Override
    public String getValue(GoalProxy object) {
      return object.getDescription();
    }
  }

  private class NameColumn extends Column<GoalProxy, String> {
    public NameColumn() {
      super(new TextCell());
    }

    @Override
    public String getValue(GoalProxy object) {
      return object.getName();
    }
  }

  @UiField
  DockLayoutPanel dock;

  @UiField(provided = true)
  SimplePager pager = new SimplePager();

  @UiField(provided = true)
  DataGrid<GoalProxy> table;

  private final EventBus eventBus;
  private int lastFetch;
  private final int numRows;
  private boolean pending;
  private final Factory requestFactory;
  private final SingleSelectionModel<GoalProxy> selectionModel = new SingleSelectionModel<GoalProxy>();

  public PersonSummaryWidget(EventBus eventBus,
      Factory requestFactory, int numRows) {
    this.eventBus = eventBus;
    this.requestFactory = requestFactory;
    this.numRows = numRows;

    table = new DataGrid<GoalProxy>(numRows);
    initWidget(GWT.<Binder> create(Binder.class).createAndBindUi(this));

    Column<GoalProxy, String> nameColumn = new NameColumn();
    table.addColumn(nameColumn, "Name");
    table.setColumnWidth(nameColumn, "25ex");
    Column<GoalProxy, String> descriptionColumn = new DescriptionColumn();
    table.addColumn(descriptionColumn, "Description");
    table.setColumnWidth(descriptionColumn, "40ex");
    table.setRowCount(numRows, false);
    table.setSelectionModel(selectionModel);
    table.setKeyboardSelectionPolicy(KeyboardSelectionPolicy.DISABLED);

    EntityProxyChange.registerForProxyType(eventBus, GoalProxy.class,
        new EntityProxyChange.Handler<GoalProxy>() {
          @Override
          public void onProxyChange(EntityProxyChange<GoalProxy> event) {
            PersonSummaryWidget.this.onPersonChanged(event);
          }
        });


    selectionModel.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {
      @Override
      public void onSelectionChange(SelectionChangeEvent event) {
        PersonSummaryWidget.this.refreshSelection();
      }
    });

    fetch(0);
  }

/*  @UiHandler("create")
  void onCreate(ClickEvent event) {
    PersonRequest context = requestFactory.personRequest();
    AddressProxy address = context.create(AddressProxy.class);
    ScheduleProxy schedule = context.create(ScheduleProxy.class);
    schedule.setTimeSlots(new ArrayList<TimeSlotProxy>());
    PersonProxy person = context.edit(context.create(PersonProxy.class));
    person.setAddress(address);
    person.setClassSchedule(schedule);
    context.persist().using(person);
    eventBus.fireEvent(new EditPersonEvent(person, context));
  }
*/
  void onPersonChanged(EntityProxyChange<GoalProxy> event) {
    if (WriteOperation.PERSIST.equals(event.getWriteOperation())) {
      // Re-fetch if we're already displaying the last page
      if (table.isRowCountExact()) {
        fetch(lastFetch + 1);
      }
    }
    if (WriteOperation.UPDATE.equals(event.getWriteOperation())) {
      EntityProxyId<GoalProxy> personId = event.getProxyId();

      // Is the changing record onscreen?
      int displayOffset = offsetOf(personId);
      if (displayOffset != -1) {
        // Record is onscreen and may differ from our data
        requestFactory.find(personId).fire(new Receiver<GoalProxy>() {
          @Override
          public void onSuccess(GoalProxy person) {
            // Re-check offset in case of changes while waiting for data
            int offset = offsetOf(person.stableId());
            if (offset != -1) {
              table.setRowData(table.getPageStart() + offset,
                  Collections.singletonList(person));
            }
          }
        });
      }
    }
  }

  @UiHandler("table")
  void onRangeChange(RangeChangeEvent event) {
    Range r = event.getNewRange();
    int start = r.getStart();

    fetch(start);
  }

  void refreshSelection() {
	  GoalProxy person = selectionModel.getSelectedObject();
    if (person == null) {
      return;
    }
    //eventBus.fireEvent(new EditPersonEvent(person));
    selectionModel.setSelected(person, false);
  }

  private void fetch(final int start) {
    lastFetch = start;
    requestFactory.createGoalRequest().findGoalEntries(start, numRows).fire(
            new Receiver<List<GoalProxy>>() {
                @Override
                public void onSuccess(List<GoalProxy> response) {
                  if (lastFetch != start) {
                    return;
                  }

                  int responses = response.size();
                  table.setRowData(start, response);
                  pager.setPageStart(start);
                  if (start == 0 || !table.isRowCountExact()) {
                    table.setRowCount(start + responses, responses < numRows);
                  }
                }
              });
  }

  private int offsetOf(EntityProxyId<GoalProxy> personId) {
    List<GoalProxy> displayedItems = table.getVisibleItems();
    for (int offset = 0, j = displayedItems.size(); offset < j; offset++) {
      if (personId.equals(displayedItems.get(offset).stableId())) {
        return offset;
      }
    }
    return -1;
  }
}
