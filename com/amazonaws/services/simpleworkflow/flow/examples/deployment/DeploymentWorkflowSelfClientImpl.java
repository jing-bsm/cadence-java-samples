/*
 * This code was generated by AWS Flow Framework Annotation Processor.
 * Refer to Amazon Simple Workflow Service documentation at http://aws.amazon.com/documentation/swf 
 *
 * Any changes made directly to this file will be lost when 
 * the code is regenerated.
 */
 package com.amazonaws.services.simpleworkflow.flow.examples.deployment;

import com.amazonaws.services.simpleworkflow.flow.core.AndPromise;
import com.amazonaws.services.simpleworkflow.flow.core.Promise;
import com.amazonaws.services.simpleworkflow.flow.core.Task;
import com.amazonaws.services.simpleworkflow.flow.DataConverter;
import com.amazonaws.services.simpleworkflow.flow.StartWorkflowOptions;
import com.amazonaws.services.simpleworkflow.flow.WorkflowSelfClientBase;
import com.amazonaws.services.simpleworkflow.flow.generic.ContinueAsNewWorkflowExecutionParameters;
import com.amazonaws.services.simpleworkflow.flow.generic.GenericWorkflowClient;

public class DeploymentWorkflowSelfClientImpl extends WorkflowSelfClientBase implements DeploymentWorkflowSelfClient {

    public DeploymentWorkflowSelfClientImpl() {
        this(null, new com.amazonaws.services.simpleworkflow.flow.JsonDataConverter(), null);
    }

    public DeploymentWorkflowSelfClientImpl(GenericWorkflowClient genericClient) {
        this(genericClient, new com.amazonaws.services.simpleworkflow.flow.JsonDataConverter(), null);
    }

    public DeploymentWorkflowSelfClientImpl(GenericWorkflowClient genericClient, 
            DataConverter dataConverter, StartWorkflowOptions schedulingOptions) {
            
        super(genericClient, dataConverter, schedulingOptions);
    }

    @Override
    public final void deploy(String springTemplate) { 
        deployImpl(Promise.asPromise(springTemplate), (StartWorkflowOptions)null);
    }

    @Override
    public final void deploy(String springTemplate, Promise<?>... waitFor) { 
        deployImpl(Promise.asPromise(springTemplate), (StartWorkflowOptions)null, waitFor);
    }
    
    @Override
    public final void deploy(String springTemplate, StartWorkflowOptions optionsOverride, Promise<?>... waitFor) {
        deployImpl(Promise.asPromise(springTemplate), optionsOverride, waitFor);
    }
    
    @Override
    public final void deploy(Promise<String> springTemplate) {
        deployImpl(springTemplate, (StartWorkflowOptions)null);
    }

    @Override
    public final void deploy(Promise<String> springTemplate, Promise<?>... waitFor) {
        deployImpl(springTemplate, (StartWorkflowOptions)null, waitFor);
    }

    @Override
    public final void deploy(Promise<String> springTemplate, StartWorkflowOptions optionsOverride, Promise<?>... waitFor) {
        deployImpl(springTemplate, optionsOverride, waitFor);
    }
    
    protected void deployImpl(final Promise<String> springTemplate, final StartWorkflowOptions schedulingOptionsOverride, Promise<?>... waitFor) {
        new Task(new Promise[] { springTemplate, new AndPromise(waitFor) }) {
    		@Override
			protected void doExecute() throws Throwable {
                ContinueAsNewWorkflowExecutionParameters _parameters_ = new ContinueAsNewWorkflowExecutionParameters();
                Object[] _input_ = new Object[1];
                _input_[0] = springTemplate.get();
                String _stringInput_ = dataConverter.toData(_input_);
				_parameters_.setInput(_stringInput_);
				_parameters_ = _parameters_.createContinueAsNewParametersFromOptions(schedulingOptions, schedulingOptionsOverride);
                
                if (genericClient == null) {
                    genericClient = decisionContextProvider.getDecisionContext().getWorkflowClient();
                }
                genericClient.continueAsNewOnCompletion(_parameters_);
			}
		};
    }
}