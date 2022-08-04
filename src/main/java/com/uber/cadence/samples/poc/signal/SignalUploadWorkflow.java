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

import com.uber.cadence.samples.poc.UploadActivityImpl;
import com.uber.cadence.worker.Worker;

public class SignalUploadWorkflow {
  public static final String SIGNAL_WORKFLOW = "POC-SIGNAL-WORKFLOW";

  public static void main(String[] args) {
    Worker.Factory factory = new Worker.Factory(DOMAIN);
    Worker worker = factory.newWorker(SIGNAL_WORKFLOW);

    worker.registerWorkflowImplementationTypes(ComboWorkflowSignalImpl.class);
    worker.registerActivitiesImplementations(new UploadActivityImpl());
    factory.start();
    System.out.println("Signal Upload workflow started");
  }
}