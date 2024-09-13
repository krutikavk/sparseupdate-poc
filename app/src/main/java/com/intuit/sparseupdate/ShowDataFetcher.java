package com.intuit.sparseupdate;

import com.intuit.sparseupdate.generated.types.Show;
import com.intuit.sparseupdate.generated.types.UpdateShowInput;
import com.intuit.sparseupdate.generated.types.UpdateShowInputInvocationHandler;
import com.intuit.sparseupdate.generated.types.IUpdateShowInputProxy;
import com.netflix.graphql.dgs.*;
import com.netflix.graphql.dgs.exceptions.DgsBadRequestException;
import com.netflix.graphql.dgs.exceptions.DgsEntityNotFoundException;
import graphql.schema.DataFetchingEnvironment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
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
    public Show updateShow(@InputArgument UpdateShowInput input, DgsDataFetchingEnvironment dfe) throws NoSuchMethodException, IllegalAccessException, NoSuchFieldException {

        if (input != null && input.getId() == null) {
            throw new DgsBadRequestException("Invalid ID");
        }
        Show show = data.get(input.getId());

        UpdateShowInput targetObject = new UpdateShowInput();
        IUpdateShowInputProxy proxyObject = (IUpdateShowInputProxy) Proxy.newProxyInstance(
                IUpdateShowInputProxy.class.getClassLoader(),
                new Class<?>[] { IUpdateShowInputProxy.class },
                new UpdateShowInputInvocationHandler(targetObject)
        );

        System.out.println("targetObject.getTitle(): " + targetObject.getTitle());
        System.out.println("targetObject.getReleaseYear(): " + targetObject.getTitle());

        return show;
    }

}