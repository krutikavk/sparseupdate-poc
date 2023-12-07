package com.intuit.sparseupdate;

import com.fasterxml.jackson.databind.ObjectMapper;
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

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@DgsComponent
public class ShowDataFetcher {

    private Map<String,Show> data = new HashMap<>();

    public ShowDataFetcher() {
        Show one = createShow("one", "Show One", 2023);
        data.put(one.getId(), one);
    }

    @DgsQuery
    public Show show(String id) {
        return data.get(id);
    }

//    @DgsMutation
//    public Show updateShow(UpdateShowInput input) {
//        if (input != null && input.getId() == null) {
//            throw new DgsBadRequestException("Invalid ID");
//        }
//        Show show = data.get(input.getId());
//        if (show == null) {
//            String msg = String.format("Show with id %s not found", input.getId());
//            throw new DgsEntityNotFoundException(msg);
//        }
//
//        Show updatedShow = simpleMapper(input);
////        Show updatedShow = ignoreNullMapper(show, input);
//        data.put(input.getId(), updatedShow);
//        return updatedShow;
//    }

    @DgsMutation(field = DgsConstants.MUTATION.UpdateShow)
    public Show updateShowManualDeserialization(DataFetchingEnvironment dfe) {
        Map<String,Object> inputArgument = dfe.getArgument(DgsConstants.MUTATION.UPDATESHOW_INPUT_ARGUMENT.Input);
        UpdateShowInput manuallyDeserializedInput = new ObjectMapper().convertValue(inputArgument, UpdateShowInput.class);

        if (manuallyDeserializedInput != null && manuallyDeserializedInput.getId() == null) {
            throw new DgsBadRequestException("Invalid ID");
        }
        Show show = data.get(manuallyDeserializedInput.getId());
        if (show == null) {
            String msg = String.format("Show with id %s not found", manuallyDeserializedInput.getId());
            throw new DgsEntityNotFoundException(msg);
        }

        Show updatedShow = advancedMapper(show, manuallyDeserializedInput);
        data.put(manuallyDeserializedInput.getId(), updatedShow);
        return updatedShow;
    }

    /**
     * Map values from UpdateShowInput object as is to Show object
     * @param input
     * @return
     */
    @SuppressWarnings("unused")
    private Show simpleMapper(UpdateShowInput input) {
        Show show = Show.newBuilder()
                .id(input.getId())
                .title(input.getTitle())
                .releaseYear(input.getReleaseYear())
                .build();
        return show;
    }

    /**
     * Map values from UpdateShowInput object as is to Show object IF IT IS NOT NULL
     * If the value from UpdateShowInput object is null, retain values before update
     * @param input
     * @return
     */
    @SuppressWarnings("unused")
    private Show ignoreNullMapper(Show beforeUpdate, UpdateShowInput input) {
        String title = input.getTitle() != null ? input.getTitle() : beforeUpdate.getTitle();
        Integer releaseYear = input.getReleaseYear() != null ? input.getReleaseYear(): beforeUpdate.getReleaseYear();
        Show show = Show.newBuilder()
                .id(input.getId())
                .title(title)
                .releaseYear(releaseYear)
                .build();
        return show;
    }

    private Show advancedMapper(Show beforeUpdate, UpdateShowInput input) {
        String title = beforeUpdate.getTitle();
        if (input.isSet(UpdateShowInput.Field.TITLE)) {
            title = input.getTitle();
        }
        Integer releaseYear = beforeUpdate.getReleaseYear();
        if (input.isSet(UpdateShowInput.Field.RELEASE_YEAR)) {
            releaseYear = input.getReleaseYear();
        }

        Show show = Show.newBuilder()
                .id(input.getId())
                .title(title)
                .releaseYear(releaseYear)
                .build();
        return show;
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

}