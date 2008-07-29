package is.idega.idegaweb.egov.company.data;


import javax.ejb.CreateException;
import javax.ejb.FinderException;
import com.idega.user.data.User;
import com.idega.data.IDOEntity;
import com.idega.data.IDOFactory;

public class ParticipantDiscountHomeImpl extends IDOFactory implements ParticipantDiscountHome {

	public Class getEntityInterfaceClass() {
		return ParticipantDiscount.class;
	}

	public ParticipantDiscount create() throws CreateException {
		return (ParticipantDiscount) super.createIDO();
	}

	public ParticipantDiscount findByPrimaryKey(Object pk) throws FinderException {
		return (ParticipantDiscount) super.findByPrimaryKeyIDO(pk);
	}

	public ParticipantDiscount findByParticipantAndCourse(User user, Course course) throws FinderException {
		IDOEntity entity = this.idoCheckOutPooledEntity();
		Object pk = ((ParticipantDiscountBMPBean) entity).ejbFindByParticipantAndCourse(user, course);
		this.idoCheckInPooledEntity(entity);
		return this.findByPrimaryKey(pk);
	}
}