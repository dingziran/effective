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

import com.dingziran.effective.client.MainMenuTreeViewModel.MenuConstants;
import com.dingziran.effective.client.content.widgets.*;

/**
 * Constants used throughout the showcase.
 */
public interface ShowcaseConstants extends MenuConstants, CwCheckBox.CwConstants,
    CwRadioButton.CwConstants, CwBasicButton.CwConstants, CwCustomButton.CwConstants,
     CwFileUpload.CwConstants, CwHyperlink.CwConstants, CwDatePicker.CwConstants,CwPerson.CwConstants  {

  /**
   * The path to source code for examples, raw files, and style definitions.
   */
  String DST_SOURCE = "gwtShowcaseSource/";

  /**
   * The destination folder for parsed source code from Showcase examples.
   */
  String DST_SOURCE_EXAMPLE = DST_SOURCE + "java/";

  /**
   * The destination folder for raw files that are included in entirety.
   */
  String DST_SOURCE_RAW = DST_SOURCE + "raw/";

  /**
   * The destination folder for parsed CSS styles used in Showcase examples.
   */
  String DST_SOURCE_STYLE = DST_SOURCE + "css/";
}
