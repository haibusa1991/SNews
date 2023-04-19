//package com.snews.server.controllers;
//
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.ResultMatcher;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//
//import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
//import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//
//@SpringBootTest
//@AutoConfigureMockMvc
//class ArticleControllerTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @Test
//    void newArticleReturns403OnInvalidUser() {
//
//    }
//
//    @Test
//    void getArticleCategories() throws Exception {
//        String expectedJson = "[\"BUSINESS\",\"ANALYSIS\",\"SPORT\",\"POLITICS\"]";
//
//        mockMvc.perform(MockMvcRequestBuilders
//                .get("/article/article-categories"))
//                .andExpect(status().isOk())
//                .andExpect((ResultMatcher) content().json(expectedJson));
//
//    }
//
//    @Test
//    void getArticle() {
//    }
//
//    @Test
//    void getHomepageArticles() {
//    }
//
//    @Test
//    void getToday() {
//    }
//
//    @Test
//    void getRelatedArticles() {
//    }
//}