/*
 *  Copyright 2012-2016 Amazon.com, Inc. or its affiliates. All Rights Reserved.
 *
 *  Modifications copyright (C) 2017 Uber Technologies, Inc.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License"). You may not
 *  use this file except in compliance with the License. A copy of the License is
 *  located at
 *
 *  http://aws.amazon.com/apache2.0
 *
 *  or in the "license" file accompanying this file. This file is distributed on
 *  an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 *  express or implied. See the License for the specific language governing
 *  permissions and limitations under the License.
 */

package com.uber.cadence.samples.poc;

import com.uber.cadence.activity.Activity;

public class UploadActivityImpl implements UploadActivity {
  @Override
  public String createDraft(String name) {
    return name + " createDraft() ";
  }

  @Override
  public String validateAndSaveMasterData(String name) {
    return name + " validateAndSaveMasterData() ";
  }

  @Override
  public String decomposeComboCSV(String name) {
    System.out.println("Workflow Id " + Activity.getWorkflowExecution().workflowId);
    System.out.println("Run Id " + Activity.getWorkflowExecution().runId);
    System.out.println("Activity Id " + Activity.getTask().getActivityId());
    Activity.doNotCompleteOnReturn();

    // following is ignored
    return " ¯\\_(ツ)_/¯ ";
  }

  @Override
  public String signalDecomposeComboCSV(String name) {

    return name + " signalDecomposeComboCSV() ";
  }

  @Override
  public String updateDecomposedData(String input) {
    return input + " updateDecomposedData() ";
  }
}
