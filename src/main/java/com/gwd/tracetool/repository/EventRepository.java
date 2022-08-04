package com.gwd.tracetool.repository;

import com.gwd.tracetool.domain.EventModel;

public interface EventRepository {

    EventModel save(EventModel eventModel);

}
