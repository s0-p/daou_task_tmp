package com.gwd.traceTool.repository;

import com.gwd.traceTool.domain.EventModel;

public interface EventRepository {

    EventModel save(EventModel eventModel);

}
