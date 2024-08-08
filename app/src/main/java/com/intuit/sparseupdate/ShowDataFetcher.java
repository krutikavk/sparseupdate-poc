package com.intuit.sparseupdate;

import com.intuit.sparseupdate.generated.DgsConstants;
import com.intuit.sparseupdate.generated.types.Show;
import com.intuit.sparseupdate.generated.types.UpdateShowInput;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsMutation;
import com.netflix.graphql.dgs.DgsQuery;
import com.netflix.graphql.dgs.InputArgument;
import com.netflix.graphql.dgs.exceptions.DgsBadRequestException;
import com.netflix.graphql.dgs.exceptions.DgsEntityNotFoundException;
import graphql.schema.DataFetchingEnvironment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
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
    public Show updateShow(@InputArgument UpdateShowInput input, DataFetchingEnvironment dfe) throws NoSuchMethodException, IllegalAccessException {



        if (input != null && input.getId() == null) {
            throw new DgsBadRequestException("Invalid ID");
        }

        Show show = data.get(input.getId());
        if (show == null) {
            String msg = String.format("Show with id %s not found", input.getId());
            throw new DgsEntityNotFoundException(msg);
        }

        System.out.println("Input Argument:" + input);
        System.out.println("Raw variables: " + dfe.getGraphQlContext().get("rawVariables"));

        Map<String, Object> rawVariables = dfe.getGraphQlContext().get("rawVariables");

        Show updatedShow = mapperWithReflection(show, input, rawVariables);
        data.put(updatedShow.getId(), updatedShow);
        return updatedShow;
    }

    // Using Boolean field for data field with default value "title"
    private Show mapperWithReflection(Show show, UpdateShowInput input, Map<String, Object> rawVariables) throws NoSuchMethodException, IllegalAccessException {

        System.out.println("show before: " + show);
//        Constructor<Show> ctor = ReflectionUtils.accessibleConstructor(Show.class);
//        ReflectionUtils.makeAccessible(ctor);

        Field[] fields = show.getClass().getDeclaredFields();

        for(Field field: fields) {
            System.out.println("field: " + field);
            if(rawVariables.containsKey(field.getName())) {
                field.setAccessible(true);
                Type type = field.getType();
                Object value = rawVariables.get(field.getName());

                field.set(this, value);
            }
            System.out.println("-->show after: " + show);
        }

        System.out.println("show after outside loop: " + show);
        return show;
    }
}