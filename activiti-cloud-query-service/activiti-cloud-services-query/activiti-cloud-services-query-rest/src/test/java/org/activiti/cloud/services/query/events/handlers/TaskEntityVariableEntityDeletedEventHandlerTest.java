/*
 * Copyright 2018 Alfresco, Inc. and/or its affiliates.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.activiti.cloud.services.query.events.handlers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import java.util.Optional;
import java.util.UUID;

import com.querydsl.core.types.Predicate;
import org.activiti.api.runtime.model.impl.VariableInstanceImpl;
import org.activiti.api.task.model.Task.TaskStatus;
import org.activiti.cloud.api.model.shared.impl.events.CloudVariableDeletedEventImpl;
import org.activiti.cloud.services.query.app.repository.EntityFinder;
import org.activiti.cloud.services.query.app.repository.TaskRepository;
import org.activiti.cloud.services.query.app.repository.TaskVariableRepository;
import org.activiti.cloud.services.query.model.TaskEntity;
import org.activiti.cloud.services.query.model.TaskVariableEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

public class TaskEntityVariableEntityDeletedEventHandlerTest {

    @InjectMocks
    private TaskVariableDeletedEventHandler handler;

    @Mock
    private TaskVariableRepository variableRepository;

    @Mock
    private EntityFinder entityFinder;

    @Mock
    private TaskRepository taskRepository;

    @BeforeEach
    public void setUp() {
        initMocks(this);
    }

    @Test
    public void handleShouldDeleteIt() {
        //given
        VariableInstanceImpl<String> variableInstance = new VariableInstanceImpl<>("var",
                                                                                   "string",
                                                                                   "v1",
                                                                                   UUID.randomUUID().toString());
        variableInstance.setTaskId(UUID.randomUUID().toString());
        CloudVariableDeletedEventImpl event = new CloudVariableDeletedEventImpl(variableInstance);

        TaskVariableEntity variableEntity = new TaskVariableEntity();
        TaskEntity taskEntity = new TaskEntity();
        taskEntity.setStatus(TaskStatus.CREATED);
        Optional<TaskEntity> optional = Optional.of(taskEntity);

        when(taskRepository.findById(anyString())).thenReturn(optional);
        given(entityFinder.findOne(eq(variableRepository), any(Predicate.class), anyString())).willReturn(variableEntity);

        //when
        handler.handle(event);

        //then
        verify(variableRepository).delete(variableEntity);

    }

}
