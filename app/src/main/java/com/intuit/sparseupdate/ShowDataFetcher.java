package com.intuit.sparseupdate;

import com.intuit.sparseupdate.generated.DgsConstants;
import com.intuit.sparseupdate.generated.types.Show;
import com.intuit.sparseupdate.generated.types.UpdateShowInput;
import com.intuit.sparseupdate.modified.UpdateShowInputForSparseUpdate;
import com.intuit.sparseupdate.modified.UpdateShowInputInvocationHandler;
import com.intuit.sparseupdate.modified.IUpdateShowInput;
import com.netflix.graphql.dgs.*;
import com.netflix.graphql.dgs.exceptions.DgsBadRequestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Proxy;
import java.util.*;

@DgsComponent
public class ShowDataFetcher {

    private static final Logger LOGGER = LoggerFactory.getLogger(ShowDataFetcher.class);
    private final Map<String,Show> data = new HashMap<>();

    public ShowDataFetcher() {
        Show one = createShow("one", "Show One", 2023);
        data.put(one.getId(), one);
    }

    @DgsQuery
    public Show show(String id) {
        return data.get(id);
    }

    private Show createShow(String id, String title, Integer releaseYear) {
        if (id == null) {
            id = UUID.randomUUID().toString();
        }
        Show show = Show.newBuilder()
                .id(id)
                .title(title)
                .releaseYear(releaseYear)
                .build();
        return show;
    }

    @DgsMutation
    public Show updateShow(@InputArgument UpdateShowInput input, DgsDataFetchingEnvironment dfe) throws InvocationTargetException, NoSuchMethodException, IllegalAccessException, NoSuchFieldException {

        if (input != null && input.getId() == null) {
            throw new DgsBadRequestException("Invalid ID");
        }
        Show show = data.get(input.getId());

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
             --> Also intercept beginDataFetcher()
         */
//        Map<String, Object> rawVariables = dfe.getGraphQlContext().get("rawVariables");

        Map<String,Object> rawArgumentsMap = dfe.getExecutionStepInfo().getArgument(DgsConstants.MUTATION.UPDATESHOW_INPUT_ARGUMENT.Input);

//        dummyHandler = customPlugin.getHandler();
//        dummyProxy = customPlugin.getProxyObject();

        UpdateShowInputForSparseUpdate updateShowInputForSparseUpdate = new UpdateShowInputForSparseUpdate(input.getId(), input.getReleaseYear(), input.getTitle());

        // handler on input object
        UpdateShowInputInvocationHandler handler = new UpdateShowInputInvocationHandler(updateShowInputForSparseUpdate, rawArgumentsMap);
        IUpdateShowInput proxyObject = (IUpdateShowInput) Proxy.newProxyInstance(
                IUpdateShowInput.class.getClassLoader(),
                // array of 2 interfaces: DGS generated, one we create with "isFieldSet"
                new Class<?>[] { IUpdateShowInput.class },
                handler
        );

        // proxy.isisFieldPresent("title")



        //proxyObject invocation handler
        if(proxyObject.isTitleSet()) {
            show.setTitle(proxyObject.getTitle());
        }

        if(proxyObject.isReleaseYearSet()) {
            show.setReleaseYear(proxyObject.getReleaseYear());
        }

        return show;
    }

}