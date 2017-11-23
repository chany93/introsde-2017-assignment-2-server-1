package introsde.rest.assign.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import introsde.rest.assign.dao.PersonActivityDao;

@Entity  // indicates that this class is an entity to persist in DB
@Table(name="\"ActivityType\"")
@NamedQuery(name="ActivityType.findAll", query="SELECT ac FROM ActivityType ac")
@XmlRootElement(name="activity_type")
public class ActivityType implements Serializable{

	private static final long serialVersionUID = 1684365465465L;

	@Id // defines this attributed as the one that identifies the entity
	@Column(name="\"activity_type\"")
	private String typeOf;
	
	public ActivityType() {
		
	}

	public String getTypeOf() {
		return typeOf;
	}

	public void setTypeOf(String typeOf) {
		this.typeOf = typeOf;
	}
	
	public static List<ActivityType> getAllTypes() {
        EntityManager em = PersonActivityDao.instance.createEntityManager();
        List<ActivityType> list = em.createNamedQuery("ActivityType.findAll", ActivityType.class)
            .getResultList();
        PersonActivityDao.instance.closeConnections(em);
        return list;
    }

}
