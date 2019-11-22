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

package com.uber.cadence.samples.poc.signal;

import static com.uber.cadence.samples.poc.signal.SignalUploadWorkflow.SIGNAL_WORKFLOW;

import com.uber.cadence.workflow.SignalMethod;
import com.uber.cadence.workflow.WorkflowMethod;

public interface ComboWorkflowSignal {

  @WorkflowMethod(executionStartToCloseTimeoutSeconds = 3600, taskList = SIGNAL_WORKFLOW)
  void execute(String input);

  @SignalMethod
  void completeManualTask(boolean complete);
}
