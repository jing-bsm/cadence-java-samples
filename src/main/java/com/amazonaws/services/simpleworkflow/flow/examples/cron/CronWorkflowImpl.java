/*
 * Copyright 2012-2016 Amazon.com, Inc. or its affiliates. All Rights Reserved.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"). You may not
 * use this file except in compliance with the License. A copy of the License is
 * located at
 * 
 * http://aws.amazon.com/apache2.0
 * 
 * or in the "license" file accompanying this file. This file is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */
package com.amazonaws.services.simpleworkflow.flow.examples.cron;

import java.util.Date;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

import com.amazonaws.services.simpleworkflow.flow.DecisionContextProviderImpl;
import com.amazonaws.services.simpleworkflow.flow.DynamicActivitiesClient;
import com.amazonaws.services.simpleworkflow.flow.DynamicActivitiesClientImpl;
import com.amazonaws.services.simpleworkflow.flow.WorkflowClock;
import com.amazonaws.services.simpleworkflow.flow.spring.CronDecorator;
import com.uber.cadence.workflow.Workflow;

/**
 * Demonstrates how to create workflow that executes an activity on schedule
 * specified as a "cron" string. Activity name and version are passed as input
 * arguments of the workflow. In case of activity failures it is retried
 * according to retry options passed as arguments of the workflow.
 * 
 * @author fateev
 */
public class CronWorkflowImpl implements CronWorkflow {

    /**
     * Used to create new run of the Cron workflow to reset history. This allows
     * "infinite" workflows.
     */
    private final CronWorkflow continueAsNewClient;


    public CronWorkflowImpl() {
        continueAsNewClient = Workflow.newContinueAsNewClient(CronWorkflow.class);

    }

    @Override
    public void startCron(final CronWorkflowOptions options) {
        long startTime = Workflow.currentTimeMillis();
        Date expiration = new Date(startTime + TimeUnit.SECONDS.toMillis(options.getContinueAsNewAfterSeconds()));
        TimeZone tz = TimeZone.getTimeZone(options.getTimeZone());
        CronDecorator cronDecorator = new CronDecorator(options.getCronExpression(), expiration, tz, clock);
        DynamicActivitiesClient cronDecoratedActivities = cronDecorator.decorate(DynamicActivitiesClient.class, activities);
        
        cronDecoratedActivities.scheduleActivity(options.getActivity(), options.getActivityArguments(), options.getOptions(), Void.class);

        // Start new workflow run as soon as cron decorator exits due to expiration.
        // The call to self client indicates the desire to start the new run.
        // It is started only after all other tasks in the given run are completed.
        continueAsNewClient.startCron(options);
    }

}
