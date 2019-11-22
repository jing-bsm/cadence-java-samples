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

package com.uber.cadence.samples.poc.manual;

import static com.uber.cadence.samples.common.SampleConstants.DOMAIN;

import com.uber.cadence.WorkflowExecution;
import com.uber.cadence.WorkflowIdReusePolicy;
import com.uber.cadence.client.ActivityCompletionClient;
import com.uber.cadence.client.WorkflowClient;
import com.uber.cadence.client.WorkflowOptions;
import java.util.UUID;

public class ManualStarter {

  public static void main(String[] args) throws InterruptedException {
    WorkflowClient workflowClient = WorkflowClient.newInstance(DOMAIN);
    String randomWorkflowId = "418144+" + UUID.randomUUID();

    WorkflowOptions options =
        new WorkflowOptions.Builder()
            .setWorkflowId(randomWorkflowId)
            .setWorkflowIdReusePolicy(WorkflowIdReusePolicy.AllowDuplicateFailedOnly)
            .build();

    ComboWorkflow workflow = workflowClient.newWorkflowStub(ComboWorkflow.class, options);
    System.out.println("Executing ComboWorkflow");
    // following is async version
    WorkflowClient.start(workflow::execute, "start");
    Thread.sleep(20000);
    WorkflowExecution workflowExecution = new WorkflowExecution();
    workflowExecution.setWorkflowId(randomWorkflowId);

    ActivityCompletionClient completionClient = workflowClient.newActivityCompletionClient();
    //Bug in Cadence - https://github.com/uber/cadence-java-client/issues/419
    //We cannot hardcode Activity Ids like - "0" its not deterministic
    completionClient.complete(workflowExecution, "0", " completedManualTask() ");
  }
}
