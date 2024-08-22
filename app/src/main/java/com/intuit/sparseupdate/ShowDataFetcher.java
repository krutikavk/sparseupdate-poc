package com.intuit.sparseupdate;

import com.intuit.sparseupdate.generated.types.Show;
import com.intuit.sparseupdate.generated.types.UpdateShowInput;
import com.netflix.graphql.dgs.*;
import com.netflix.graphql.dgs.exceptions.DgsBadRequestException;
import com.netflix.graphql.dgs.exceptions.DgsEntityNotFoundException;
import graphql.schema.DataFetchingEnvironment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
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
        if (show == null) {
            String msg = String.format("Show with id %s not found", input.getId());
            throw new DgsEntityNotFoundException(msg);
        }

        System.out.println("\nCoerced InputArgument: " + input);

        if(dfe.isArgumentSet("input")) {
            System.out.println("Input given by customer");
        }

        if(dfe.isArgumentSet("title")) {
            System.out.println("\nTitle set by customer: " + input.getTitle());
        }

        if(dfe.isArgumentSet("releaseYear")) {
            System.out.println("ReleaseYear set by customer: " + input.getReleaseYear());
        }

        if(dfe.isArgumentSet("id")) {
            System.out.println("ID set by customer: " + input.getId());
        }


        //Nested
        if(dfe.isNestedArgumentSet("input.title")) {
            System.out.println("\nNested Title set by customer: " + input.getTitle());
        }

        if(dfe.isNestedArgumentSet("input.releaseYear")) {
            System.out.println("Nested ReleaseYear set by customer: " + input.getReleaseYear());
        }

        if(dfe.isNestedArgumentSet("input.id")) {
            System.out.println("Nested ID set by customer: " + input.getId());
        }

        return show;
    }

    // Using Boolean field for data field with default value "title"
    private void mapperWithReflection(Show show, UpdateShowInput input, Map<String, Object> rawVariables) throws NoSuchMethodException, IllegalAccessException, NoSuchFieldException {
        Field[] fields = show.getClass().getDeclaredFields();

        for(Field field: fields) {
            if(rawVariables.containsKey(field.getName())) {
                Field showField = show.getClass().getDeclaredField(field.getName());
                showField.setAccessible(true);
                showField.set(show, rawVariables.get(field.getName()));
            }
        }

        System.out.println("show updated: " + show);
    }
}