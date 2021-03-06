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

package org.activiti.cloud.alfresco.data.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.hateoas.PagedResources;

public class ExtendedPageMetadata extends PagedResources.PageMetadata {

    @JsonIgnore
    private long skipCount;

    public ExtendedPageMetadata(long skipCount,
                                long size,
                                long number,
                                long totalElements,
                                long totalPages) {
        super(size,
              number,
              totalElements,
              totalPages);
        this.skipCount = skipCount;
    }

    public long getSkipCount() {
        return skipCount;
    }

}
