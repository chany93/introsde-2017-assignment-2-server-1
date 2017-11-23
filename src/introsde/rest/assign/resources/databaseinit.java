package introsde.rest.assign.resources;

import java.util.ArrayList;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.PersistenceUnit;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import introsde.rest.assign.model.Activity;
import introsde.rest.assign.model.ActivityType;
import introsde.rest.assign.model.Person;

@Stateless
@LocalBean//Will map the resource to the URL 
@Path("/databaseinit")
public class databaseinit {
	// Allows to insert contextual objects into the class,
    // e.g. ServletContext, Request, Response, UriInfo
    @Context
    UriInfo uriInfo;
    @Context
    Request request;

    // will work only inside a Java EE application
    @PersistenceUnit(unitName="introsde-jpa")
    EntityManager entityManager;

    // will work only inside a Java EE application
    @PersistenceContext(unitName = "introsde-jpa",type=PersistenceContextType.TRANSACTION)
    private EntityManagerFactory entityManagerFactory;
    
 
	@SuppressWarnings("serial")
	@POST
    @Produces({MediaType.TEXT_XML,  MediaType.APPLICATION_JSON ,  MediaType.APPLICATION_XML })
    public Response getActivityTypesBrowser() {
		Response res;
       System.out.println("Init database...");       
       /*
        *  Activity Types
        */
       ActivityType social = new ActivityType();
       social.setTypeOf("Social");
       ActivityType sport = new ActivityType();
       sport.setTypeOf("Sport");
       ActivityType cultural = new ActivityType();
       cultural.setTypeOf("Cultural");
       /*
        *  Activities
        */
       Activity killing_Kakarrot = new Activity();
       killing_Kakarrot.setName("killing Kakarrot");
       killing_Kakarrot.setDescription("killing Kakarrot as soon as possible");
       killing_Kakarrot.setPlace("Universe 18");
       killing_Kakarrot.setStartdate("0000-00-00T00:00:00.0");        
       killing_Kakarrot.setType(social);
       Activity swimming = new Activity();
       swimming.setName("Swimming");
       swimming.setDescription("Swimming in the river");
       swimming.setPlace("Adige River");
       swimming.setStartdate("2017-12-28T08:50:00.0");        
       swimming.setType(sport);
       Activity running = new Activity();
       running.setName("Running");
       running.setDescription("Running with friends");
       running.setPlace("City Center Trento");
       running.setStartdate("2018-10-13T09:50:00.0");        
       running.setType(sport);
       Activity dancing = new Activity();
       dancing.setName("Dancing");
       dancing.setDescription("Dancing Group Dances");
       dancing.setPlace("Irish Pub");
       dancing.setStartdate("2017-12-10T09:50:00.0");        
       dancing.setType(social);
       Activity quidditch = new Activity();
       quidditch.setName("Quidditch");
       quidditch.setDescription("Playing quidditch with friends");
       quidditch.setPlace("Hogwarts");
       quidditch.setStartdate("2017-11-01T09:50:00.0");        
       quidditch.setType(social);
       Activity afternoon_tea = new Activity();
       afternoon_tea.setName("Afternoon_tea");
       afternoon_tea.setDescription("Drinking tea with friends");
       afternoon_tea.setPlace("Bar Centrale");
       afternoon_tea.setStartdate("2017-11-30T17:50:00.0");        
       afternoon_tea.setType(cultural);
       Activity doing_assignments = new Activity();
       doing_assignments.setName("Doing assignments");
       doing_assignments.setDescription("Doing introSDE assignments");
       doing_assignments.setPlace("Everywhere");
       doing_assignments.setStartdate("2017-09-30T17:50:00.0");        
       doing_assignments.setType(cultural);
       Activity sleeping = new Activity();
       sleeping.setName("Slepping");
       sleeping.setDescription("Sleeping in the bed");
       sleeping.setPlace("Own bed");
       sleeping.setStartdate("2017-12-07T21:50:00.0");        
       sleeping.setType(cultural);
       /*
        * People
        */
       Person broly = new Person();
       broly.setBirthdate("0000-01-10");
       broly.setFirstname("Broly");
       broly.setLastname("Legendary Super Sayan");
       broly.setPreferences(new ArrayList<Activity>() {{add(killing_Kakarrot);add(afternoon_tea);}});
       Person sandrone = new Person();
       sandrone.setBirthdate("1994-11-20");
       sandrone.setFirstname("Sandone");
       sandrone.setLastname("Pazzo");
       sandrone.setPreferences(new ArrayList<Activity>() {{add(swimming);}});
       Person giovannathan = new Person();
       giovannathan.setBirthdate("1994-11-20");
       giovannathan.setFirstname("Giovannathan");
       giovannathan.setLastname("Donnola");
       giovannathan.setPreferences(new ArrayList<Activity>() {{add(running);}});
       Person majin = new Person();
       majin.setBirthdate("0123-03-03");
       majin.setFirstname("Majin");
       majin.setLastname("Bu");
       majin.setPreferences(new ArrayList<Activity>() {{add(doing_assignments);}});
       Person billy= new Person();
       billy.setBirthdate("1791-11-20");
       billy.setFirstname("Billy");
       billy.setLastname("The Kid");
       billy.setPreferences(new ArrayList<Activity>() {{add(dancing);add(quidditch);add(sleeping);}});
       
       Person.savePerson(broly);
       Person.savePerson(sandrone);
       Person.savePerson(giovannathan);
       Person.savePerson(majin);
       Person.savePerson(billy);
       
       res = Response.noContent().build();
       return res;
    }
}
