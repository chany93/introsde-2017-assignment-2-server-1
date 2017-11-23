package introsde.rest.assign.model;

import java.io.Serializable;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import introsde.rest.assign.dao.PersonActivityDao;


@Entity  // indicates that this class is an entity to persist in DB
@XmlRootElement
@Table(name="\"Activity\"")
public class Activity implements Serializable{
	
	private static final long serialVersionUID = 16543543216541354L;

	@Id // defines this attributed as the one that identifies the entity
    @GeneratedValue(strategy=GenerationType.AUTO) 
    @Column(name="\"idActivity\"") // maps the following attribute to a column
    private int idActivity;
	
	private String name;
	private String description;
	private String place;
	private String startdate;
	
	@ManyToOne(cascade=CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name="\"type\"", referencedColumnName="\"activity_type\"")
	private ActivityType type;	
	
	public Activity() {
		
	}
	
	public int getIdActivity() {
		return idActivity;
	}
	public void setIdActivity(int idActivity) {
		this.idActivity = idActivity;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getPlace() {
		return place;
	}
	public void setPlace(String place) {
		this.place = place;
	}
	public String getStartdate() {
		return startdate;
	}
	public void setStartdate(String startdate) {
		this.startdate = startdate;
	}
	public ActivityType getType() {
		return type;
	}
	public void setType(ActivityType type) {
		this.type = type;
	}
	
	public static Activity getActivityById(int activityId) {
        EntityManager em = PersonActivityDao.instance.createEntityManager();
        Activity a = em.find(Activity.class, activityId);
        PersonActivityDao.instance.closeConnections(em);
        return a;
    }
	
	public static void updateActivity(Activity a) {
        EntityManager em = PersonActivityDao.instance.createEntityManager(); 
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        a=em.merge(a);
        tx.commit();
        PersonActivityDao.instance.closeConnections(em);
    }
	
	public static List<Activity> getPersonActivitiesByType(int idPerson, String type){
		Person p = Person.getPersonById(idPerson);
		List<Activity> personActivities = p.getPreferences();
		List<Activity> ret = new ArrayList<>();
		for(Activity a : personActivities) {
			if(a.getType().getTypeOf().equals(type)) {
				ret.add(a);
			}
		}
		return ret;
	}
	
	public static List<Activity> getPersonActivitiesByTypeAndActivityId(int idPerson, String type, int activityId){
		
		List<Activity> personActivities = Activity.getPersonActivitiesByType(idPerson, type);
		List<Activity> ret = new ArrayList<>();
		for(Activity a : personActivities) {
			if(a.getIdActivity() == activityId) {
				ret.add(a);
			}
		}
		return ret;
	}
	
	public static List<Activity> getRangedActivityByPersonId(int idPerson,String activity_type, String before, String after) throws ParseException{
		SimpleDateFormat sDF = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.S");
		Date beforeDate = sDF.parse(before);
		Date afterDate = sDF.parse(after);
		
		List<Activity> personActivities = Activity.getPersonActivitiesByType(idPerson, activity_type);
		List<Activity> ret = new ArrayList<>();
		
		for(Activity a : personActivities) {
			Date tmpDate = sDF.parse(a.getStartdate());
			if(tmpDate.before(beforeDate) && tmpDate.after(afterDate)) {
				ret.add(a);
			}
		}
		return ret;
	}
}
