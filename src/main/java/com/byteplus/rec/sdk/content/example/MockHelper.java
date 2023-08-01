package com.byteplus.rec.sdk.content.example;

import com.byteplus.rec.sdk.content.example.entity.DemoContent;
import com.byteplus.rec.sdk.content.example.entity.DemoUser;
import com.byteplus.rec.sdk.content.example.entity.DemoUserEvent;
import com.byteplus.rec.sdk.content.protocol.ByteplusSaasContent.Content;
import com.byteplus.rec.sdk.content.protocol.ByteplusSaasContent.Device;

import java.util.ArrayList;
import java.util.List;


public class MockHelper {
    public static List<DemoUser> mockUsers(int count) {
        List<DemoUser> users = new ArrayList<>(count);
        for (int i = 0; i < count; i++) {
            DemoUser user = mockUser();
            user.setUserId(user.getUserId() + i);
            users.add(user);
        }
        return users;
    }

    public static DemoUser mockUser() {
        DemoUser user = new DemoUser();
        user.setUserId("1457789");
        user.setGender("male");
        user.setAge("23");
        user.setTags("[\"new user\",\"low purchasing power\",\"bargain seeker\"]");
        user.setLanguage("English");
        user.setSubscriberType("free");
        user.setMembershipLevel("silver");
        user.setRegistrationTimestamp(1659958007);
        user.setCountry("USA");
        user.setProvince("Texas");
        user.setCity("Kirkland");
        user.setDistrict("King County");

        // your custom field
        // user.setCustomField("custom");
        return user;
    }

    public static List<DemoContent> mockContents(int count) {
        List<DemoContent> contents = new ArrayList<>(count);
        for (int i = 0; i < count; i++) {
            DemoContent content = mockContent();
            content.setContentId(content.getContentId() + i);
            contents.add(content);
        }
        return contents;
    }

    public static DemoContent mockContent() {
        DemoContent content = new DemoContent();
        content.setContentId("632461");
        content.setIsRecommendable(1);
        content.setCategories("[{\"category_depth\":1,\"category_nodes\":[{\"id_or_name\":\"Movie\"}]},{\"category_depth\":2,\"category_nodes\":[{\"id_or_name\":\"Comedy\"}]}]");
        content.setContentType("video");
        content.setVideoDuration(120000);
        content.setContentTitle("Green Book Movie Explanation");
        content.setDescription("A brief summary of the main content of the Green Book movie");
        content.setContentOwnerId("1457789");
        content.setCollectionId("1342");
        content.setTags("[\"New\",\"Trending\"]");
        content.setImageUrls("[\"https://images-na.ssl-images-amazon.com/images/I/81WmojBxvbL._AC_UL1500_.jpg\"]");
        content.setVideoUrls("[\"https://test_video.mov\"]");
        content.setUserRating(4.9f);
        content.setCurrentPrice(1300.12f);
        content.setOriginalPrice(1600.12f);
        content.setPublishTimestamp(1660035734);
        content.setPaidContent(true);
        content.setLanguage("English");
        content.setLinkedProductId("[\"632462\",\"632463\"]");
        content.setSource("self");

        // your custom field
        // content.setCustomField("custom");
        return content;
    }

    public static List<DemoUserEvent> mockUserEvents(int count) {
        List<DemoUserEvent> userEvents = new ArrayList<>(count);
        for (int i = 0; i < count; i++) {
            DemoUserEvent userEvent = mockUserEvent();
            userEvents.add(userEvent);
        }
        return userEvents;
    }

    public static DemoUserEvent mockUserEvent() {
        DemoUserEvent userEvent = new DemoUserEvent();
        userEvent.setUserId("1457787");
        userEvent.setEventType("purchase");
        userEvent.setEventTimestamp(1686883465);
        userEvent.setContentId("632461");
        userEvent.setTrafficSource("byteplus");
        userEvent.setAttributionToken("eyJpc3MiOiJuaW5naGFvLm5ldCIsImV4cCI6IjE0Mzg5NTU0NDUiLCJuYW1lIjoid2FuZ2hhbyIsImFkbWluIjp0cnVlfQ");
        userEvent.setSceneName("Home page");
        userEvent.setPageNumber(2);
        userEvent.setOffset(10);
        userEvent.setStayDuration(150000);
        userEvent.setParentContentId("632431");
        userEvent.setContentOwnerId("1457789");
        userEvent.setQuery("comedy");
        userEvent.setPlatform("app");
        userEvent.setOsType("ios");
        userEvent.setAppVersion("1.0.1");
        userEvent.setDeviceModel("iPhone10");
        userEvent.setOsVersion("14.4.2");
        userEvent.setNetwork("4g");
        userEvent.setCountry("USA");
        userEvent.setProvince("Texas");
        userEvent.setCity("Kirkland");
        userEvent.setDistrict("King County");
        userEvent.setPurchaseCount(20);
        userEvent.setPaidPrice(12.23f);
        userEvent.setCurrency("USD");

        // your custom field
        // userEvent.setCustomField("custom");
        return userEvent;
    }

    public static Content mockPredictContent() {
        return Content.newBuilder()
                .setContentId("632461")
                .build();
    }

    public static Device mockDevice() {
        return Device.newBuilder()
                .setPlatform("app")
                .setOsType("android")
                .setAppVersion("9.2.0")
                .setDeviceModel("huawei-mate30")
                .setDeviceBrand("huawei")
                .setOsVersion("10")
                .setBrowserType("chrome")
                .setUserAgent("Mozilla/5.0 (Linux; Android 10; TAS-AN00; HMSCore 5.3.0.312) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/83.0.4103.106 HuaweiBrowser/11.0.8.303 Mobile Safari/537.36")
                .setNetwork("3g")
                .build();
    }
}
