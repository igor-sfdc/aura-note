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
package org.auraframework.demo.notes.controllers;

import java.math.BigDecimal;

import org.auraframework.demo.notes.DataStore;
import org.auraframework.demo.notes.Note;
import org.auraframework.ds.log.AuraDSLogService;
import org.auraframework.system.Annotations.AuraEnabled;
import org.auraframework.system.Annotations.Key;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;

import aQute.bnd.annotation.component.Activate;
import aQute.bnd.annotation.component.Component;
import aQute.bnd.annotation.component.Reference;
import ui.aura.servicecomponent.Annotations.ServiceComponentController;

@ServiceComponentController
@Component
public class NoteEditController implements org.auraframework.ds.servicecomponent.Controller {

    private AuraDSLogService logService;

    @Reference
    protected void setLogService(AuraDSLogService logServiceValue) {
        logService = logServiceValue;
    }

    @Activate
    protected void activate() {
        logService.debug("Activated new instance of: " + this.getClass().getName() + this);
    }

    @AuraEnabled
    public Note saveNote(@Key("id") Long id,
            @Key("title") String title,
            @Key("body") String body,
            @Key("sort") String sort,
            @Key("latitude") BigDecimal latitude,
            @Key("longitude") BigDecimal longitude) throws Exception {
        Dao<Note, Long> noteDao = DaoManager.createDao(DataStore.getInstance().getConnectionSource(), Note.class);
        Note note;

        if (id != null) {
            note = noteDao.queryForId(id);
        } else {
            note = new Note();
        }

        if (title == null) {
            title = "";
        }

        if (body == null) {
            body = "";
        }

        note.setTitle(title);
        note.setBody(body);
        if (latitude != null && longitude != null) {
            note.setLatitude(latitude.doubleValue());
            note.setLongitude(longitude.doubleValue());
        }

        if (id != null) {
            noteDao.update(note);
        } else {
            noteDao.create(note);
        }

        return note;
    }
}
