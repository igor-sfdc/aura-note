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

import org.auraframework.ds.log.AuraDSLogService;
import org.auraframework.ds.servicecomponent.ModelInitializationException;

import aQute.bnd.annotation.component.Activate;
import aQute.bnd.annotation.component.Component;
import aQute.bnd.annotation.component.Reference;

@Component
public class TestNoteListModelFactory implements org.auraframework.ds.servicecomponent.ModelFactory<TestNoteListModel> {

    private transient AuraDSLogService logService;

    @Reference
    protected void setLogService(AuraDSLogService logServiceValue) {
        logService = logServiceValue;
    }

    @Activate
    void activate() throws Exception {
        logService.debug("Activated new instance of: " + this.getClass().getName() + this);
    }

    @Override
    public TestNoteListModel modelInstance() throws ModelInitializationException {
        try {
            return new TestNoteListModel();
        } catch (Exception e) {
            throw new ModelInitializationException("Failed to create component instance", e);
        }
    }

}
