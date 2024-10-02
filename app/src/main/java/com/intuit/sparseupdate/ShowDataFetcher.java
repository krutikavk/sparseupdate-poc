package com.intuit.sparseupdate;

import com.intuit.sparseupdate.generated.types.Show;
import com.intuit.sparseupdate.generated.types.UpdateShowInput;
import com.intuit.sparseupdate.generated.types.UpdateShowInputInvocationHandler;
import com.intuit.sparseupdate.generated.types.IUpdateShowInput;
import com.netflix.graphql.dgs.*;
import com.netflix.graphql.dgs.exceptions.DgsBadRequestException;
import com.netflix.graphql.dgs.exceptions.DgsEntityNotFoundException;
import graphql.schema.DataFetchingEnvironment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Proxy;
import java.lang.reflect.UndeclaredThrowableException;
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

        Map<String, Object> rawVariables = dfe.getGraphQlContext().get("rawVariables");
        //Get input variables as kay
//        Map<String, Object> rawVar = dfe.getVariables();

        UpdateShowInput targetObject = new UpdateShowInput();
        UpdateShowInputInvocationHandler handler = new UpdateShowInputInvocationHandler(targetObject);
        IUpdateShowInput proxyObject = (IUpdateShowInput) Proxy.newProxyInstance(
                IUpdateShowInput.class.getClassLoader(),
                new Class<?>[] { IUpdateShowInput.class },
                handler
        );


        //proxyObject
        if(rawVariables.containsKey("title")) {
            proxyObject.setIsTitleSet();
        }

        if(rawVariables.containsKey("releaseYear")) {
            proxyObject.setIsReleaseYearSet();
        }

//        System.out.println("proxyObject title presence field: " + proxyObject.isTitleSet() + " releaseYear presence field: " + proxyObject.isReleaseYearSet());
//
//        System.out.println("targetObject.getTitle(): " + proxyObject.getTitle());
//        System.out.println("targetObject.getReleaseYear(): " + proxyObject.getTitle());

        return show;
    }

}