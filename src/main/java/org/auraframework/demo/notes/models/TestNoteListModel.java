/*
 * Copyright (C) 2014 salesforce.com, inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.auraframework.demo.notes.models;

import java.util.concurrent.atomic.AtomicLong;

import org.auraframework.demo.notes.DataStore;
import org.auraframework.demo.notes.Note;
import org.auraframework.ds.log.AuraDSLogService;
import org.auraframework.system.Annotations.AuraEnabled;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;

import aQute.bnd.annotation.component.Activate;
import aQute.bnd.annotation.component.Reference;
import ui.aura.servicecomponent.Annotations.ServiceComponentModelInstance;

@ServiceComponentModelInstance
public class TestNoteListModel implements org.auraframework.ds.servicecomponent.ModelInstance {

    private transient AuraDSLogService logService;

    @Reference
    protected void setLogService(AuraDSLogService logServiceValue) {
        logService = logServiceValue;
    }

    @Activate
    protected void activate() {
        logService.debug("Activated new instance of: " + this.getClass().getName() + this);
    }

    private static AtomicLong count = new AtomicLong();
    private final String key = "test" + count.getAndIncrement() + System.currentTimeMillis();

    public TestNoteListModel() throws Exception {
        createNote("created first", key);
        Thread.sleep(100);
        createNote("created second", key);
        Thread.sleep(100);
        createNote("created third", key);
        Thread.sleep(100);
        createNote("created absolutely last", key);
    }

    @AuraEnabled
    public String getKey() throws Exception {
        return key;
    }

    private void createNote(String title, String body) throws Exception {
        Dao<Note, Long> noteDao = DaoManager.createDao(DataStore.getInstance().getConnectionSource(), Note.class);
        Note note = new Note();
        note.setTitle(title);
        note.setBody(body);
        noteDao.create(note);
    }

}
