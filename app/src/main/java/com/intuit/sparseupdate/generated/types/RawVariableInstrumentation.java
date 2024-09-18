package com.intuit.sparseupdate.generated.types;

import graphql.ExecutionResult;
import graphql.GraphQLContext;
import graphql.execution.instrumentation.InstrumentationContext;
import graphql.execution.instrumentation.InstrumentationState;
import graphql.execution.instrumentation.SimpleInstrumentation;
import graphql.execution.instrumentation.SimpleInstrumentationContext;
import graphql.execution.instrumentation.parameters.InstrumentationExecutionParameters;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class RawVariableInstrumentation extends SimpleInstrumentation {

//    private final RawVariableInstrumentation rawVariableInstrumentation = new RawVariableInstrumentation();

    @Override
    public InstrumentationContext<ExecutionResult> beginExecution(InstrumentationExecutionParameters parameters, InstrumentationState state) {
//        return rawVariableInstrumentation.instrumentExecutionInput(parameters, super.beginExecution(parameters));
        Map<String, Object> rawVariables = (Map<String, Object>) parameters.getVariables().get("input");

        GraphQLContext ctx = parameters.getExecutionInput().getGraphQLContext();
        ctx.put("rawVariables", rawVariables);

        return super.beginExecution(parameters, state);
    }
}



