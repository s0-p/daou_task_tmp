package repository;

import domain.EventModel;

public interface EventRepository {

    EventModel save(EventModel eventModel);

}
