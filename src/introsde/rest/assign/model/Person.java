package introsde.rest.assign.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import introsde.rest.assign.dao.PersonActivityDao;

@Entity  // indicates that this class is an entity to persist in DB
@XmlRootElement
@Table(name="\"Person\"")
@NamedQuery(name="Person.findAll", query="SELECT p FROM Person p")
public class Person implements Serializable{
	
	private static final long serialVersionUID = 1682135546287986546L;
	
	@Id // defines this attributed as the one that identifies the entity
    @GeneratedValue(strategy=GenerationType.AUTO) 
    @Column(name="\"idPerson\"") // maps the following attribute to a column
    private int idPerson;
	
	private String firstname;
	private String lastname;
	private String birthdate;
	
	@ManyToMany(cascade=CascadeType.ALL)
	@JoinTable(name="\"Activity_Preferences\"", joinColumns=@JoinColumn(name="\"idPerson\""), inverseJoinColumns=@JoinColumn(name="\"idActivity\""))
	private List<Activity> preferences;
	
	public Person() {
		
	}
	
	public int getIdPerson() {
		return idPerson;
	}

	public void setIdPerson(int idPerson) {
		this.idPerson = idPerson;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getBirthdate() {
		return birthdate;
	}

	public void setBirthdate(String birthdate) {
		this.birthdate = birthdate;
	}

	public List<Activity> getPreferences() {
		return preferences;
	}

	public void setPreferences(List<Activity> preferences) {
		this.preferences = preferences;
	}
	
	
	public static Person getPersonById(int personId) {
        EntityManager em = PersonActivityDao.instance.createEntityManager();
        Person p = em.find(Person.class, personId);
        PersonActivityDao.instance.closeConnections(em);
        return p;
    }

    public static List<Person> getAll() {
        EntityManager em = PersonActivityDao.instance.createEntityManager();
        List<Person> list = em.createNamedQuery("Person.findAll", Person.class)
            .getResultList();
        PersonActivityDao.instance.closeConnections(em);
        return list;
    }

    public static Person savePerson(Person p) {
        EntityManager em = PersonActivityDao.instance.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        p = em.merge(p);
        tx.commit();
        PersonActivityDao.instance.closeConnections(em);
        System.out.println(p.getIdPerson());
        return p;
    } 

    public static Person updatePerson(Person p) {
        EntityManager em = PersonActivityDao.instance.createEntityManager(); 
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        p=em.merge(p);
        tx.commit();
        PersonActivityDao.instance.closeConnections(em);
        return p;
    }

    public static void removePerson(Person p) {
        EntityManager em = PersonActivityDao.instance.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        p=em.merge(p);
        em.remove(p);
        tx.commit();
        PersonActivityDao.instance.closeConnections(em);
    }
    
}
