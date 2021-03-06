package logic;

import java.util.Date;
import java.util.List;
import util.EntityProvider;

public abstract class CompanyUser extends BasicUser{
    String companyName;

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
    
    public abstract List<Event> getOpenEvents();
    
    public Event createEvent(String description, Date expires) {
        Event event = EntityProvider.getBusinessFactories().getEventInstance(getDataProvider());
        event.setCompanyUser(this);
        event.setDescription(description);
        event.setExpirationTime(expires);
        event.setStatus(Event.Status.Open);
        event.save();
        return event;
    }   
}
