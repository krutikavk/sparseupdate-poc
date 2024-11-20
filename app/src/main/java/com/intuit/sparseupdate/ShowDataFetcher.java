package com.intuit.sparseupdate;

import com.intuit.sparseupdate.generated.DgsConstants;
import com.intuit.sparseupdate.generated.types.Show;
import com.intuit.sparseupdate.generated.types.UpdateShowInput;
import com.intuit.sparseupdate.modified.IPresenceFields;
import com.intuit.sparseupdate.modified.SparseUpdateInvocationHandler;
import com.intuit.sparseupdate.generated.types.IUpdateShowInput;
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

        Map<String,Object> rawArgumentsMap = dfe.getExecutionStepInfo().getArgument(DgsConstants.MUTATION.UPDATESHOW_INPUT_ARGUMENT.Input);

        // ProxyObject
        IUpdateShowInput updateShowInput = (IUpdateShowInput) SparseUpdateInvocationHandler.getDynamicProxy(input, rawArgumentsMap);

        // To do: Add wrapper to handler for this functionality
        if(((IPresenceFields)updateShowInput).isFieldPresent("title")) {
            show.setTitle(updateShowInput.getTitle());
        }

        if(((IPresenceFields)updateShowInput).isFieldPresent("releaseYear")) {
            show.setReleaseYear(updateShowInput.getReleaseYear());
        }

        return show;
    }

}