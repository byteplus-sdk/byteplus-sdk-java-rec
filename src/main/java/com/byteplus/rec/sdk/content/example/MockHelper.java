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
        user.setUserIdType("system_generated");
        user.setGender("male");
        user.setAge("23");
        user.setTags("[\"new user\",\"low purchasing power\",\"bargain seeker\"]");
        user.setLanguage("English");
        user.setSubscriberType("free");
        user.setNetwork("4g");
        user.setPlatform("app");
        user.setOsType("ios");
        user.setAppVersion("1.0.1");
        user.setDeviceModel("iPhone10");
        user.setOsVersion("14.4.2");
        user.setMembershipLevel("silver");
        user.setRegistrationTimestamp(1659958007);
        user.setUpdateTimestamp(1659958007);
        user.setLastLoginTimestamp(1659958207);
        user.setCountry("USA");
        user.setProvince("Texas");
        user.setCity("Kirkland");
        user.setDistrict("King County");
        user.setArea("Neighborhood #1");

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
        content.setContentOwner("1457789");
        content.setContentOwnerFollowers(25);
        content.setContentOwnerRating(4.5f);
        content.setContentOwnerName("comedy movie commentary");
        content.setCollectionId("1342");
        content.setTags("[\"New\",\"Trending\"]");
        content.setTopicTags("[\"Political\",\"Latest\"]");
        content.setImageUrls("[\"https://images-na.ssl-images-amazon.com/images/I/81WmojBxvbL._AC_UL1500_.jpg\"]");
        content.setDetailPicNum(5);
        content.setVideoUrls("[\"https://test_video.mov\"]");
        content.setUserRating(4.9f);
        content.setViewsCount(10000);
        content.setCommentsCount(100);
        content.setLikesCount(10);
        content.setSharesCount(50);
        content.setSaveCount(50);
        content.setCurrentPrice(1300);
        content.setOriginalPrice(1600);
        content.setAvailableLocation("[\"Cafe 101\"]");
        content.setPublishTimestamp(1660035734);
        content.setUpdateTimestamp(1660035734);
        content.setCopyrightStartTimestamp(1660035734);
        content.setCopyrightEndTimestamp(1760035734);
        content.setPaidContent(true);
        content.setLanguage("English");
        content.setRelatedContentIds("[\"632462\",\"632463\"]");
        content.setSoldCount(60);
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
        userEvent.setEventType("impression");
        userEvent.setEventTimestamp(1660036970);
        userEvent.setContentId("632461");
        userEvent.setTrafficSource("byteplus");
        userEvent.setRequestId("67a9fcf74a82fdc55a26ab4ee12a7b96890407fc0042f8cc014e07a4a560a9ac");
        userEvent.setRecInfo("CiRiMjYyYjM1YS0xOTk1LTQ5YmMtOGNkNS1mZTVmYTczN2FkNDASJAobcmVjZW50X2hvdF9jbGlja3NfcmV0cmlldmVyFQAAAAAYDxoKCgNjdHIdog58PBoKCgNjdnIdANK2OCIHMjcyNTgwMg==");
        userEvent.setAttributionToken("eyJpc3MiOiJuaW5naGFvLm5ldCIsImV4cCI6IjE0Mzg5NTU0NDUiLCJuYW1lIjoid2FuZ2hhbyIsImFkbWluIjp0cnVlfQ");
        userEvent.setSceneName("Home page");
        userEvent.setPageNumber(2);
        userEvent.setOffset(10);
        userEvent.setPlayDuration(150000);
        userEvent.setVideoDuration(1200000);
        userEvent.setStartTime(150000);
        userEvent.setEndTime(300000);
        userEvent.setParentContentId("632431");
        userEvent.setContentOwnerId("1457789");
        userEvent.setDetailStayTime(10);
        userEvent.setDislikeType("content_id");
        userEvent.setDislikeValue("675411");
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
        userEvent.setArea("Neighborhood #1");

        // your custom field
        // userEvent.setCustomField("custom");
        return userEvent;
    }

    public static Content mockPredictContent() {
        return Content.newBuilder()
                .setContentId("632461")
                .setIsRecommendable(1)
                .setCategories("[{\"category_depth\":1,\"category_nodes\":[{\"id_or_name\":\"Movie\"}]},{\"category_depth\":2,\"category_nodes\":[{\"id_or_name\":\"Comedy\"}]}]")
                .setContentType("video")
                .setVideoDuration(120000)
                .setContentTitle("Green Book Movie Explanation")
                .setDescription("A brief summary of the main content of the Green Book movie")
                .setContentOwner("1457789")
                .setContentOwnerFollowers(25)
                .setContentOwnerRating(4.5f)
                .setContentOwnerName("comedy movie commentary")
                .setCollectionId("1342")
                .setTags("[\"New\",\"Trending\"]")
                .setTopicTags("[\"Political\",\"Latest\"]")
                .setImageUrls("[\"https://images-na.ssl-images-amazon.com/images/I/81WmojBxvbL._AC_UL1500_.jpg\"]")
                .setDetailPicNum(5)
                .setVideoUrls("[\"https://test_video.mov\"]")
                .setUserRating(4.9f)
                .setViewsCount(10000)
                .setCommentsCount(100)
                .setLikesCount(10)
                .setSharesCount(50)
                .setSaveCount(50)
                .setCurrentPrice(1300)
                .setOriginalPrice(1600)
                .setAvailableLocation("[\"Cafe 101\"]")
                .setPublishTimestamp(1660035734)
                .setUpdateTimestamp(1660035734)
                .setCopyrightStartTimestamp(1660035734)
                .setCopyrightEndTimestamp(1760035734)
                .setIsPaidContent(true)
                .setLanguage("English")
                .setRelatedContentIds("[\"632462\",\"632463\"]")
                .setSoldCount(60)
                .setSource("self")
                // .putExtra("additionalField", "additionalValue")
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
