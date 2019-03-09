package com.graphql.second.controller;

import com.graphql.second.pojo.UserPo;
import com.graphql.second.service.UserService;
import graphql.GraphQL;
import graphql.schema.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

import static graphql.Scalars.*;
import static graphql.schema.GraphQLArgument.newArgument;
import static graphql.schema.GraphQLFieldDefinition.newFieldDefinition;
import static graphql.schema.GraphQLInputObjectField.newInputObjectField;
import static graphql.schema.GraphQLInputObjectType.newInputObject;
import static graphql.schema.GraphQLObjectType.newObject;

@RestController
@RequestMapping("/second")
public class SecondController {


    @Autowired
    private UserService userService;


    @RequestMapping("/test")
    public Object test(String query){
        UserPo userPo=new UserPo();
        userPo.setUsername("zhangsan4");
        userPo.setPassword("123456a");
        userService.saveUser(userPo);
        return "success";
    }

    @RequestMapping(value = "/list")
    public Object list( String query){
//        List<UserPo> userPoList = userService.findAll();
        //定义GraphQL类型
        GraphQLObjectType userType=GraphQLObjectType.newObject()
                .name("user")
                .field(newFieldDefinition().name("username").type(GraphQLString))
                .field(newFieldDefinition().name("password").type(GraphQLString))
                .field(newFieldDefinition().name("id").type(GraphQLLong))
                .build();

        GraphQLInputType userInput = newInputObject()//定于输入类型
                .name("UserInput")
                .field(newInputObjectField().name("username").type(GraphQLString))
                .field(newInputObjectField().name("id").type(GraphQLLong))
                .build();
        //定义暴露给客户端的查询 query api
        GraphQLObjectType queryType = newObject()//定义暴露给客户端的查询query api
                .name("userQuery")
                .field(newFieldDefinition()
                        .type(new GraphQLList(userType))
                        .name("user1")
                        .argument(newArgument()
                                .name("username")
                                .type(new GraphQLNonNull(GraphQLString)))
                        .dataFetcher(environment -> {
                            String username=environment.getArgument("username");
                            Page<UserPo> page = userService.findByExample(username);
                            if(page.isEmpty()){
                                return null;
                            }
                            return page.getContent();
                        }))
                .field(newFieldDefinition()
                        .type(userType)
                        .name("user2")
                        .argument(newArgument()
                                .name("id")
                                .type(new GraphQLNonNull(GraphQLLong)))
                        .dataFetcher(environment -> {
                            Long id=environment.getArgument("id");
                            return userService.findById(id);
                        }))
                .field(newFieldDefinition()
                        .type(userType)
                        .name("user3")
                        .argument(newArgument()
                                .name("param")
                                .type(new GraphQLNonNull(userInput)))
                        .dataFetcher(environment -> {
                            Map<String,Object> userInfoMap=environment.getArgument("param");
                            Long id=Long.valueOf(userInfoMap.get("id").toString());
                            String username=userInfoMap.get("username").toString();
                            return userService.findByIdAndUsername(id,username);
                        }))
                .build();

        //创建Schema
        GraphQLSchema schema = GraphQLSchema.newSchema()
                .query(queryType)
                .build();
        //传入schema，执行查询
        GraphQL graphQL = GraphQL.newGraphQL(schema).build();

        //返回查询结果集
        Object data = graphQL.execute(query).getData();
        return data;
    }
}
