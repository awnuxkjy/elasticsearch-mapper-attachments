/*
 * Licensed to Elasticsearch under one or more contributor
 * license agreements. See the NOTICE file distributed with
 * this work for additional information regarding copyright
 * ownership. Elasticsearch licenses this file to you under
 * the Apache License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.elasticsearch.index.mapper.xcontent;

import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.index.Index;
import org.elasticsearch.index.analysis.AnalysisService;
import org.elasticsearch.index.mapper.DocumentMapper;
import org.elasticsearch.index.mapper.DocumentMapperParser;
import org.elasticsearch.index.mapper.attachment.AttachmentMapper;
import org.elasticsearch.index.mapper.core.StringFieldMapper;
import org.elasticsearch.test.ElasticsearchTestCase;
import org.junit.Before;
import org.junit.Test;

import static org.elasticsearch.common.io.Streams.copyToStringFromClasspath;
import static org.hamcrest.Matchers.instanceOf;

/**
 *
 */
public class DateAttachmentMapperTests extends ElasticsearchTestCase {

    private DocumentMapperParser mapperParser;

    @Before
    public void setupMapperParser() {
        mapperParser = new DocumentMapperParser(new Index("test"), ImmutableSettings.EMPTY, new AnalysisService(new Index("test")), null, null, null);
        mapperParser.putTypeParser(AttachmentMapper.CONTENT_TYPE, new AttachmentMapper.TypeParser());
    }

    @Test
    public void testSimpleMappings() throws Exception {
        String mapping = copyToStringFromClasspath("/org/elasticsearch/index/mapper/date/date-mapping.json");
        DocumentMapper docMapper = mapperParser.parse(mapping);

        // Our mapping should be kept as a String
        assertThat(docMapper.mappers().fullName("file.date").mapper(), instanceOf(StringFieldMapper.class));
    }
}
