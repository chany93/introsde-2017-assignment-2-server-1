# introsde-2017-assignment-2-server

## 1. Identification
* Perini Raffaele 196339
* raffaele.perini@studenti.unitn.it
* __My Partner__: Giovanni Rafael Vuolo
* __My Server URL__: https://intro2sdeass2.herokuapp.com/sdelab/
* __His Server URL__: https://assignsde2.herokuapp.com/assign2/
* __His Repo Git__: https://github.com/chany93/introsde-2017-assignment-2-server

## 2. Project
This is a REST server. The main parts of this server are People and Activities. A student can have a different number of Activities(0 to n), that be stored as the preferred activities of this student. In this server it is possible to perform all the CRUD operations for people, and manage their preferred activities.

#### 2.1. Code
The logical part of the server is divided in __model__, which is where the entities are placed, the __dao__ which handles with the entities and acts as link between model and the __resources__ which are exposed through out the system.
* __model__: In this package are defined the classes __People__, __Activity__, __ActivityType__. Within all these classes are defined the parameters and  some useful methods for handling with them. All these are mapped in a SQLite database using the __Java Persistence Api__. The __ActivityTypes__ class is used in order to make simple handling with activityType objects, and it not mapped on the database.
* __Dao__: In this package we have __PersonActivityDao__ which provides the interface to the model, whereby the classes written in the Persistence.xml.
* __Resources__: The package where the are contained the classes using to get the requests and retrive the responses to/from the client.
   * The __Databaseinit__ class is mapped by _/database_ path.
   * The __PersonCollectionResource__ class is mapped on _/person_ path and contains the methods for getting all people and for posting a new person.
   * The __PersonResource__ class contatins all the methods (GET, POST, DELETE, PUT) for managing a person and its for handling with its activities.
   * The __ActivityTypeResource__ class is mapped on the _/activity_types_ path and is used for get activity types.

#### 2.2. Task
this server can do the following tasks both in __JSON__ and __XML__ format:
 * 0#: __POST /databaseinit__, creates 5 new people.
 * 1#: __GET /person__, lists all the people in the database (wrapped under the root element "people")
 * 2#: __GET /person/{id}__, gives all the personal information plus related information
 * 3#: __PUT /person/{id}__,updates the personal information of the person identified by {id} (only the person's information, not activity preferences)
 * 4#: __POST /person__, creates a new person and returns the newly created person with its assigned id (if a activity profile is included, create them for the new person)
 * 5#: __DELETE /person/{id}__,deletes the person identified by {id} from the system
 * 6#: __GET /activity_types__,returns the list of activity_types your model supports in the following formats:
 ```
<activityTypes>
    <activity_type>Sport</activity_type>
 <activity_type>Social</activity_type>
</activityTypes>
```
```
{
 "activityTypes": [
      "Sport",
      "Social"
  ]
}
```
 * 7#: __GET /person/{id}/{activity_type}__,returns the list of activities of {activity_type} (e.g. Social) for person identified by {id}
 * 8#: __GET /person/{id}/{activity_type}/{activity_id}__, returns the activities of {activity_type} (e.g. Social) identified by {activity_id} for person identified by {id}
 * 10#: __PUT /person/{id}/{activity_type}/{activity_id}__, updates the value for the {activity_type} (e.g., Social) identified by {activity_id}, related to the person identified by {id}
 * 11#: __GET /person/{id}/{activity_type}?before={beforeDate}&after={afterDate}__,returns the activities of {activity_type} (e.g., Social) for person {id} which {start_date} is in the specified range of date. (1 point)

## 3. Execution
It is possible perform all the request above on the following heroku server

  ```
   https://intro2sdeass2.herokuapp.com/sdelab/
  ```
## 4. Additional Notes
Actually the request#9 is not implemented
