# GraphQL API Sparse Update
This project is a demonstration of this issue: [dgs-codegen-issue-609](https://github.com/Netflix/dgs-codegen/issues/609)


## How to run the app
* Clone [this](https://github.com/ramapalani/sparseupdate) git repository 
* Start the DGS/Spring boot app using this command
```
cd app
./mvnw clean spring-boot:run
```

## Demonstrate Sparse Update Issue
* Get details of a Show with ID "One"
![getShow GraphQL Query](./images/getShow.png)

* Call mutation `updateShow` and try to update Show with ID "One"'s `releaseYear` to 2022.  This will set the field `title` to null. 
![updateShow Mutation using simple mapper](./images/updateShowSimpleMapper.png)

* To fix the null problem, update mapper to retain field values if the input is null.  Though it would fix the above issue, but if I want to set a field explicitly to `null`, I won't be able to do it. 
![updateShow Mutation using ignore Null mapper](./images/updateShowIgnoreNullMapper.png)

* Ideally the behavior should be something like this
![Correct Behavior](./images/correctBehavior.png)
  * Maintain a flag when some field is set
  * In the mutation method, check whether the flag is set, if set, honor the value that was set.  If not, get it from previously stored state or default it.