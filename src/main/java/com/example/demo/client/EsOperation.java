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
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

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
        UriTemplate template = new UriTemplate("http://localhost:9200/website/blog/{id}?pretty");
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


    public static void deleteBlog(Long id) {
        UriTemplate template = new UriTemplate("http://localhost:9200/website/blog/{id}");
        URI expand = template.expand(id);
        RequestEntity<Void> build = RequestEntity.delete(expand).build();
        RestTemplate restTemplate = RestTemplateFactory.create();
        ResponseEntity<Object> forEntity = restTemplate.exchange(build, Object.class);
        JsonUtils.print(forEntity.getBody());
    }

    public static void update(Blog blog) {
        UriTemplate template = new UriTemplate("http://localhost:9200/website/blog/{id}/_update?retry_on_conflict=5");
        URI expand = template.expand(blog.getId());
        RequestEntity<Map<String, Blog>> body = RequestEntity.post(expand)
                .accept(MediaType.ALL)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .body(Collections.singletonMap("doc", blog));
        RestTemplate restTemplate = RestTemplateFactory.create();
        ResponseEntity<Object> forEntity = restTemplate.exchange(body, Object.class);
        JsonUtils.print(forEntity.getBody());
    }

    public static void updateWithScript(String script, Blog upsert) {
        UriTemplate template = new UriTemplate("http://localhost:9200/website/blog/{id}/_update");
        URI expand = template.expand(upsert.getId());
        Map params = new HashMap();
        params.put("script", script);
        params.put("upsert", upsert);
        RequestEntity<Map> body = RequestEntity.post(expand)
                .accept(MediaType.ALL)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .body(params);
        RestTemplate restTemplate = RestTemplateFactory.create();
        ResponseEntity<Object> forEntity = restTemplate.exchange(body, Object.class);
        JsonUtils.print(forEntity.getBody());
    }

    public static void mGet(String index, String type, Long... id) {
        UriTemplate template = new UriTemplate("http://localhost:9200/{index}/{type}/_mget?pretty");
        URI expand = template.expand(index, type);
        RequestEntity<Map<String, Long[]>> body = RequestEntity.post(expand)
                .accept(MediaType.ALL)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .body(Collections.singletonMap("ids", id));
        RestTemplate restTemplate = RestTemplateFactory.create();
        ResponseEntity<Object> forEntity = restTemplate.exchange(body, Object.class);
        JsonUtils.print(forEntity.getBody());
    }

    public static void mget2(Identity... identities) {
        Map<String, Identity[]> docs = Collections.singletonMap("docs", identities);
        RequestEntity<Map<String, Identity[]>> body = RequestEntity.post(URI.create("http://localhost:9200/website/blog/_mget"))
                .accept(MediaType.ALL)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .body(docs);
        RestTemplate restTemplate = RestTemplateFactory.create();
        ResponseEntity<Object> forEntity = restTemplate.exchange(body, Object.class);
        JsonUtils.print(forEntity.getBody());
    }

    public static void main(String[] args) {
//        for (long i = 0; i < 10; i++) {
//            Blog blog = Blog.builder().id(i)
//                    .title("title " + i)
//                    .text("text " + i)
//                    .build();
//            save(blog);
//        }
        Long[] longs = {1L, 2L, 3L, 4L};
        Identity[] objects = Stream.of(longs).map(x -> Identity.builder()._id(String.valueOf(x)).build()).toArray(Identity[]::new);
        mget2(objects);
    }
}
