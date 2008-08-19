package is.idega.idegaweb.egov.company.data;

import is.idega.idegaweb.egov.application.data.Application;

import com.idega.block.process.data.Case;
import com.idega.data.IDOEntity;

//	TODO: make it "real" entity
public interface CompanyApplication extends IDOEntity, Case, is.idega.idegaweb.egov.fsk.data.Application/*TODO: is this needed?*/, Application {

}
