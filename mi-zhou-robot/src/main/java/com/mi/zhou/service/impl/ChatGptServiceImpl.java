//package com.mi.zhou.service.impl;
//
//import com.mi.zhou.service.ChatGptService;
//import com.plexpt.chatgpt.ChatGPT;
//import com.plexpt.chatgpt.entity.chat.ChatCompletion;
//import com.plexpt.chatgpt.entity.chat.ChatCompletionResponse;
//import com.plexpt.chatgpt.entity.chat.Message;
//import com.plexpt.chatgpt.util.Proxys;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.stereotype.Service;
//
//import java.net.Proxy;
//import java.util.Arrays;
//import java.util.List;
//
///**
// * @author miZhou
// * @version 1.0
// * @date 2023/4/8
// */
//@Service
//public class ChatGptServiceImpl implements ChatGptService {
//    private final static Logger log = LoggerFactory.getLogger(ChatGptServiceImpl.class);
//
//    @Override
//    public Message sendMessage(String text) {
//        Proxy proxy = Proxys.http("127.0.0.1", 7890);
//
//        ChatGPT chatGPT = ChatGPT.builder()
//                .apiKey("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")
//                .proxy(proxy)
//                .timeout(900)
//                .apiHost("https://api.openai.com/") //反向代理地址
//                .build()
//                .init();
//
//        Message system = Message.ofSystem(text);
//        ChatCompletion chatCompletion = ChatCompletion.builder()
//                .model(ChatCompletion.Model.GPT_3_5_TURBO.getName())
//                .messages(List.of(system))
//                .maxTokens(3000)
//                .temperature(0.9)
//                .build();
//        ChatCompletionResponse response = chatGPT.chatCompletion(chatCompletion);
//        return response.getChoices().get(0).getMessage();
//    }
//}
