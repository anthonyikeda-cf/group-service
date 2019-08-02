package com.example.composertestapi;


import com.example.composertestapi.dto.GroupDTO;
import com.github.javafaker.Faker;
import com.github.javafaker.service.FakeValuesService;
import com.github.javafaker.service.RandomService;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.Locale;
import java.util.stream.IntStream;

public class GroupControllerIntegrationTest {

    @Test
    @Ignore
    public void createGroups() {
        Faker faker = new Faker();

        RestTemplate template = new RestTemplate();
        IntStream.range(1, 100).forEach(index -> {
            GroupDTO dto = new GroupDTO();
            dto.setGroupName(faker.name().fullName());
            dto.setDescription(faker.address().fullAddress());
            template.postForObject(URI.create("http://localhost:8080/group/v1"), dto, String.class);
        });

    }
}

