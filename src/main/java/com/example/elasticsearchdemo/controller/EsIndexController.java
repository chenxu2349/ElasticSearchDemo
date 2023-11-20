package com.example.elasticsearchdemo.controller;

import org.elasticsearch.action.admin.indices.alias.IndicesAliasesRequest;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.admin.indices.open.OpenIndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CloseIndexRequest;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * @ClassName EsController
 * @Description
 * @Author chenxu
 * @Date 2023/11/16 19:51
 **/

@RestController
public class EsIndexController {

    @Autowired
    private RestHighLevelClient client;

    // 此段代码定义一个接口，根据请求参数值，创建对应名称的索引
    @RequestMapping("/createIndex")
    public Boolean createIndex(String indexName) {

        CreateIndexRequest request = new CreateIndexRequest(indexName);

        request.mapping(
                "{\n" +
                        "  \"properties\": {\n" +
                        "    \"message\": {\n" +
                        "      \"type\": \"text\"\n" +
                        "    }\n" +
                        "  }\n" +
                        "}",
                XContentType.JSON);
        try {
            client.indices().create(request, RequestOptions.DEFAULT);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    // 删除索引
    @RequestMapping("/deleteIndex")
    public Boolean deleteIndex(String indexName) {

        DeleteIndexRequest request = new DeleteIndexRequest(indexName);

        try {
            client.indices().delete(request, RequestOptions.DEFAULT);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    // 判断索引存在
    @RequestMapping("/existsIndex")
    public Boolean existsIndex(String indexName) {

        GetIndexRequest request = new GetIndexRequest(indexName);

        try {
            return client.indices().exists(request, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    // 关闭索引
    @RequestMapping("/closeIndex")
    public Boolean closeIndex(String indexName) {

        CloseIndexRequest request = new CloseIndexRequest(indexName);

        try {
            client.indices().close(request, RequestOptions.DEFAULT);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    // 开启索引
    @RequestMapping("/openIndex")
    public Boolean openIndex(String indexName) {

        OpenIndexRequest request = new OpenIndexRequest(indexName);

        try {
            client.indices().open(request, RequestOptions.DEFAULT);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    // 添加别名
    @RequestMapping("/addAlias")
    public Boolean addAlias(String indexName, String aliasName) {
        IndicesAliasesRequest indicesAliasesRequest = new IndicesAliasesRequest();
        IndicesAliasesRequest.AliasActions aliasActions = new IndicesAliasesRequest.AliasActions(IndicesAliasesRequest.AliasActions.Type.ADD);
        aliasActions.index(indexName).alias(aliasName);
        indicesAliasesRequest.addAliasAction(aliasActions);
        try {
            client.indices().updateAliases(indicesAliasesRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    // 删除别名
    @RequestMapping("/removeAlias")
    public Boolean removeAlias(String indexName, String aliasName) {
        IndicesAliasesRequest indicesAliasesRequest = new IndicesAliasesRequest();
        IndicesAliasesRequest.AliasActions aliasActions = new IndicesAliasesRequest.AliasActions(IndicesAliasesRequest.AliasActions.Type.REMOVE);
        aliasActions.index(indexName).alias(aliasName);
        indicesAliasesRequest.addAliasAction(aliasActions);
        try {
            client.indices().updateAliases(indicesAliasesRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    // 切换别名
    @RequestMapping("/changeAlias")
    public Boolean changeAlias() {
        String aliasName = "indexname_alias";
        IndicesAliasesRequest indicesAliasesRequest = new IndicesAliasesRequest();
        IndicesAliasesRequest.AliasActions addAliasActions = new IndicesAliasesRequest.AliasActions(IndicesAliasesRequest.AliasActions.Type.ADD);
        addAliasActions.index("indexname1").alias(aliasName);
        IndicesAliasesRequest.AliasActions removeAliasActions = new IndicesAliasesRequest.AliasActions(IndicesAliasesRequest.AliasActions.Type.REMOVE);
        removeAliasActions.index("indexname2").alias(aliasName);
        indicesAliasesRequest.addAliasAction(addAliasActions);
        indicesAliasesRequest.addAliasAction(removeAliasActions);
        try {
            client.indices().updateAliases(indicesAliasesRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    // 查看别名
}
