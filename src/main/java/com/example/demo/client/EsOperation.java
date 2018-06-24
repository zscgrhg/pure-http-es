package com.example.demo.client;

import com.example.demo.doc.Blog;
import com.example.demo.utils.JsonUtils;
import com.example.demo.utils.RestTemplateFactory;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriTemplate;

import java.net.URI;
import java.util.Date;

public class EsOperation {

    public static void createIndex(String indexName, String settings) {


        RestTemplate restTemplate = RestTemplateFactory.create();
        UriTemplate template = new UriTemplate("http://localhost:9200/{indexName}");
        URI expand = template.expand(indexName);

        RequestEntity<String> body = RequestEntity.put(expand)
                .accept(MediaType.ALL)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .body(settings);
        ResponseEntity<Object> exchange = restTemplate.exchange(body, Object.class);
        JsonUtils.print(exchange.getBody());
    }


    public static void create(Blog blog) {
        RestTemplate restTemplate = RestTemplateFactory.create();
        UriTemplate template = new UriTemplate("http://localhost:9200/{index}/{type}/{id}/_create");
        URI expand = template.expand("website", "blog", blog.getId());

        RequestEntity<String> body = RequestEntity.put(expand)
                .accept(MediaType.ALL)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .body(JsonUtils.write(blog));
        ResponseEntity<Object> exchange = restTemplate.exchange(body, Object.class);
        JsonUtils.print(exchange.getBody());
    }

    public static void save(Blog blog) {
        RestTemplate restTemplate = RestTemplateFactory.create();
        UriTemplate template = new UriTemplate("http://localhost:9200/{index}/{type}/{id}");
        URI expand = template.expand("website", "blog", blog.getId());

        RequestEntity<String> body = RequestEntity.put(expand)
                .accept(MediaType.ALL)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .body(JsonUtils.write(blog));
        ResponseEntity<Object> exchange = restTemplate.exchange(body, Object.class);
        JsonUtils.print(exchange.getBody());
    }

    public static void find(Long id) {
        UriTemplate template = new UriTemplate("http://localhost:9200/website/blog/{id}?pretty&_source=title,text");
        URI expand = template.expand(id);
        RestTemplate restTemplate = RestTemplateFactory.create();
        ResponseEntity<Object> forEntity = restTemplate.getForEntity(expand, Object.class);
        JsonUtils.print(forEntity.getBody());
    }

    public static void sourceOf(Long id) {
        UriTemplate template = new UriTemplate("http://localhost:9200/website/blog/{id}/_source?pretty");
        URI expand = template.expand(id);
        RestTemplate restTemplate = RestTemplateFactory.create();
        ResponseEntity<Object> forEntity = restTemplate.getForEntity(expand, Object.class);
        JsonUtils.print(forEntity.getBody());
    }

    public static void main(String[] args) {
        Blog nihao = Blog.builder().id(3L).title("nihao")
                .text("nihao?")
                .date(new Date())
                .build();
        save(nihao);
        create(nihao);
        sourceOf(1L);
    }
}
