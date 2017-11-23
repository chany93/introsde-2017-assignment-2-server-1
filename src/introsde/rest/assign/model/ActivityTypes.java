package introsde.rest.assign.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class ActivityTypes {
	
	private List<String> activity_type;
	
	public ActivityTypes() {
		
	}

	public List<String> getActivity_type() {
		return activity_type;
	}

	public void setActivity_type(List<String> activity_type) {
		this.activity_type = activity_type;
	}
	
	public static ActivityTypes getActivityTypes() {
		List<ActivityType> aclist = ActivityType.getAllTypes();
		List<String> tmp = new ArrayList<String>();
		for(ActivityType ac : aclist) {
			tmp.add(ac.getTypeOf());
		}
		ActivityTypes ret = new ActivityTypes();
		ret.setActivity_type(tmp);		
		return ret;
	}
	

}
