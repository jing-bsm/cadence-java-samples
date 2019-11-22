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

import static com.uber.cadence.samples.common.SampleConstants.DOMAIN;

import com.uber.cadence.WorkflowIdReusePolicy;
import com.uber.cadence.client.WorkflowClient;
import com.uber.cadence.client.WorkflowOptions;
import java.util.UUID;

public class SignalStarter {

  public static void main(String[] args) throws InterruptedException {
    WorkflowClient workflowClient = WorkflowClient.newInstance(DOMAIN);
    String randomWorkflowId = "13123123123+" + UUID.randomUUID();

    WorkflowOptions options =
        new WorkflowOptions.Builder()
            .setWorkflowId(randomWorkflowId)
            .setWorkflowIdReusePolicy(WorkflowIdReusePolicy.AllowDuplicateFailedOnly)
            .build();

    ComboWorkflowSignal workflow =
        workflowClient.newWorkflowStub(ComboWorkflowSignal.class, options);
    System.out.println("Executing ComboWorkflow");
    // following is async version
    WorkflowClient.start(workflow::execute, "start");
    // User will update the columns on UI stepper
    Thread.sleep(20000);
    System.out.println("Signal sent for workflowId " + randomWorkflowId);
    workflow.completeManualTask(true);
  }
}
