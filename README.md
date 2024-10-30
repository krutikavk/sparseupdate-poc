# GraphQL API Sparse Update: Runtime POC with Dynamic Proxy
This project is a demonstration of this issue: [dgs-codegen-issue-609](https://github.com/Netflix/dgs-codegen/issues/609)


## How to run the app
* Clone [this](https://github.com/ramapalani/sparseupdate) git repository 
* Start the DGS/Spring boot app using this command
```
cd app
./mvnw clean spring-boot:run
```

## Demonstrate Sparse Update Issue

```shell
# Install Java 17 or above
# Checkout the reproducing issue tag
git checkout dynamic-proxy-updated
cd app
mvn -N wrapper:wrapper -Dmaven=3.9.5
# Build and run the DGS springboot app
./mvnw clean spring-boot:run
```

### Get details of a Show with ID "One"
* Open GraphiQL at http://localhost:8080/graphiql
* Copy this query and execute it
```graphql
query getShow {
  show(id: "one") {
    title
    releaseYear
  }
}
```
![getShow GraphQL Query](./images/getShow.png)
* Equivalent curl command
```shell
curl 'http://localhost:8080/graphql' -X POST \
  -H 'Accept: application/json' \
  -H 'Content-Type: application/json' \
  --data-raw '{"query":"query getShow {\n  show(id: \"one\") {\n    title\n    releaseYear\n  }\n}","variables":{"input":{"id":"one","releaseYear":2023}},"operationName":"getShow"}'
{"data":{"show":{"title":"Show One","releaseYear":2023}}}
```

### Update "One" Show's release year to 2022
Call mutation `updateShow` and try to update Show with ID "One"'s `releaseYear` to 2022.  This will set the field `title` to null. 

* Open GraphiQL at http://localhost:8080/graphiql
* Copy this mutation
```graphql
mutation updateShow($input: UpdateShowInput!) {
  updateShow(input: $input) {
    id
    title
    releaseYear
  }
}
```
* Copy this input and execute it
```json
{
  "input": {
    "id": "one",
    "releaseYear": null
  }
}
```

Output: only releaseYear is updated, title is unchanged
```json
{
  "data": {
    "updateShow": {
      "id": "one",
      "title": "Show One",
      "releaseYear": null
    }
  }
}
```

Title is null: 
```graphql
mutation updateShow($input: UpdateShowInput!) {
  updateShow(input: $input) {
    id
    title
    releaseYear
  }
}
```
* Copy this input and execute it
```json
{
  "input": {
    "id": "one",
    "title": null
  }
}
```

Output: only title is updated, releaseYear is unchanged
```json
{
  "data": {
    "updateShow": {
      "id": "one",
      "title": null,
      "releaseYear": null
    }
  }
}
```

Update title:
```graphql
mutation updateShow($input: UpdateShowInput!) {
  updateShow(input: $input) {
    id
    title
    releaseYear
  }
}
```
* Copy this input and execute it
```json
{
  "input": {
    "id": "one",
    "title": "Silo",
    "releaseYear": 2024
  }
}
```

Output: Both title and releaseYear are updated
```json
{
  "data": {
    "updateShow": {
      "id": "one",
      "title": "Silo",
      "releaseYear": 2024
    }
  }
}
```
