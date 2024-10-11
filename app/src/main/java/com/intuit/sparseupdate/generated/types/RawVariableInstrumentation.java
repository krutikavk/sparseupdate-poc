package com.intuit.sparseupdate.generated.types;

import graphql.ExecutionResult;
import graphql.GraphQLContext;
import graphql.execution.instrumentation.InstrumentationContext;
import graphql.execution.instrumentation.InstrumentationState;
import graphql.execution.instrumentation.SimpleInstrumentation;
import graphql.execution.instrumentation.SimpleInstrumentationContext;
import graphql.execution.instrumentation.parameters.InstrumentationExecutionParameters;
import graphql.execution.instrumentation.parameters.InstrumentationFieldFetchParameters;
import graphql.schema.DataFetcher;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class RawVariableInstrumentation extends SimpleInstrumentation {

//    private final RawVariableInstrumentation rawVariableInstrumentation = new RawVariableInstrumentation();

    @Override
    public InstrumentationContext<ExecutionResult> beginExecution(InstrumentationExecutionParameters parameters, InstrumentationState state) {
//        return rawVariableInstrumentation.instrumentExecutionInput(parameters, super.beginExecution(parameters));
        /*
            mutation updateShow {
              updateShow(id: "one", releaseYear: 1985) {
                id
                title
                releaseYear
              }
            }
             No variables supplied.

             ==> Value of rawVariables is undefined/not extracted for inline variables
                 or client input values in variable other than set key "input" in RawVariableInstrumentation class

             Better way is to get raw variable map as on line 75.
         */
        Map<String, Object> rawVariables = (Map<String, Object>) parameters.getVariables().get("input");

        GraphQLContext ctx = parameters.getExecutionInput().getGraphQLContext();
        System.out.println("===>context: " + parameters.getExecutionInput().getGraphQLContext().getClass());
        ctx.put("rawVariables", rawVariables);

        return super.beginExecution(parameters, state);
    }

    // Transform InstrumentationFieldFetchParameters Supplier<DataFetchingEnvironment> environment;
    @Override
    public @Nullable InstrumentationContext<Object> beginFieldFetch(InstrumentationFieldFetchParameters parameters, InstrumentationState state) {
        new InstrumentationFieldFetchParameters(parameters.getExecutionContext(), parameters.)
        return super.beginFieldFetch(parameters, state);
    }

    @Override
    public @NotNull DataFetcher<?> instrumentDataFetcher(DataFetcher<?> dataFetcher, InstrumentationFieldFetchParameters parameters, InstrumentationState state) {

        return super.instrumentDataFetcher(dataFetcher, parameters, state);
    }
}



