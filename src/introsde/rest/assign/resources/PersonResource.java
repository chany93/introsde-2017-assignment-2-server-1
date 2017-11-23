package introsde.rest.assign.resources;

import introsde.rest.assign.model.Activity;
import introsde.rest.assign.model.ActivityType;
import introsde.rest.assign.model.Person;

import java.text.ParseException;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;


@Stateless
@LocalBean
public class PersonResource {
    @Context
    UriInfo uriInfo;
    @Context
    Request request;
    int id;

    EntityManager entityManager; // only used if the application is deployed in a Java EE container

    public PersonResource(UriInfo uriInfo, Request request,int id, EntityManager em) {
        this.uriInfo = uriInfo;
        this.request = request;
        this.id = id;
        this.entityManager = em;
    }

    public PersonResource(UriInfo uriInfo, Request request,int id) {
        this.uriInfo = uriInfo;
        this.request = request;
        this.id = id;
    }

    // Application integration
    @GET
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public Person getPerson() {
        Person person = this.getPersonById(id);
        if (person == null)
            throw new RuntimeException("Get: Person with " + id + " not found");
        return person;
    }

    // for the browser
    @GET
    @Produces(MediaType.TEXT_XML)
    public Person getPersonHTML() {
        Person person = this.getPersonById(id);
        if (person == null)
            throw new RuntimeException("Get: Person with " + id + " not found");
        System.out.println("Returning person... " + person.getIdPerson());
        return person;
    }

    @PUT
    @Path("{activity_type}/{activity_id}")
    @Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public Response putPersonActivityType(ActivityType newActivityType ,
    		@PathParam("activity_type") String activity_type,
    		@PathParam("activity_id") int activity_id ) {
    	
        System.out.println("--> Updating Activity("+activity_id+"), Person("+this.id+"), type("+ newActivityType.getTypeOf()+")");
        Person p = Person.getPersonById(id);
        List<Activity> aList = Activity.getPersonActivitiesByTypeAndActivityId(id, activity_type, activity_id);
        for(Activity a : aList) {
        	a.setType(newActivityType);
            Activity.updateActivity(a);
        }
        Response res;
        Activity existing = Activity.getActivityById(this.id);
        
        if (existing == null) {
            res = Response.noContent().build();
        } else {
            res = Response.created(uriInfo.getAbsolutePath()).build();
            p.setIdPerson(this.id);
            Person.updatePerson(p);
        }
        return res;
    }
    
    @PUT
    @Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public Response putPerson(Person person) {
        System.out.println("--> Updating Person... " +this.id);
        System.out.println("--> "+person.toString());
        /*reuse the old person*/
        person.setPreferences(getPersonById(this.id).getPreferences());
        Person.updatePerson(person);
        Response res;
        Person existing = getPersonById(this.id);
        
        if (existing == null) {
            res = Response.noContent().build();
        } else {
            res = Response.created(uriInfo.getAbsolutePath()).build();
            person.setIdPerson(this.id);
        }
        return res;
    }
    
    @DELETE
    public void deletePerson() {
        Person c = getPersonById(id);
        if (c == null)
            throw new RuntimeException("Delete: Person with " + id
                    + " not found");
        Person.removePerson(c);
    }
    
    @GET
    @Path("{activity_type}/{activity_id}")
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public List<Activity> getActivityTypesByPersonAndTypesAndId
    					(@PathParam("activity_type") String activity_type, 
    					 @PathParam("activity_id") int activity_id) {
    	
    	System.out.println("Getting " + activity_type + " activities from person id: " + id + " and activity id: " + activity_id);
        return Activity.getPersonActivitiesByTypeAndActivityId(id, activity_type, activity_id);
    }
    
    @GET
    @Path("{activity_type}")
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public List<Activity> getActivityTypesByPersonAndTypes(
    		@PathParam("activity_type") String activity_type,
    		@QueryParam("before") String before, 
    		@QueryParam("after") String after) {
    	if(before != null && after != null) {
    		try {
    	    	System.out.println("Getting " + activity_type + " activities from person id: " + id +""
    	    			+ " from:" + before + " to:" + after);
				return Activity.getRangedActivityByPersonId(id, activity_type, before, after);
			} catch (ParseException e) {
				System.out.println("something worngs with dates....");
				e.printStackTrace();
				return null;
			}
    	}
    	else {
	    	System.out.println("Getting " + activity_type + " activities from person id: " + id);
	        return Activity.getPersonActivitiesByType(id, activity_type);
    	}
    }
    

    public Person getPersonById(int personId) {
        System.out.println("Reading person from DB with id: "+personId);

        // this will work within a Java EE container, where not DAO will be needed
        //Person person = entityManager.find(Person.class, personId); 

        Person person = Person.getPersonById(personId);
        System.out.println("Person: "+person.toString());
        return person;
    }
}