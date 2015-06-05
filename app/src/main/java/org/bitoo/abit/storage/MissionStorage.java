package org.bitoo.abit.storage;

import org.bitoo.abit.mission.image.Mission;

/**
 * The way to handle storage of mission.
 */
public interface MissionStorage {
    /**
     * Add a new mission into storage.In the new item the mission can
     * be created and the face
     * @param mission to be inserted.
     * @return id of new mission
     */
    long addMission(Mission mission);

}
