package com.gradu.infou.logstashTest;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.elasticsearch.annotations.Document;

@Document(indexName = "elastic_test")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class ElasticSearchItems {

    @Id
    private Long id;
    private String student;
    private String professor;

}
