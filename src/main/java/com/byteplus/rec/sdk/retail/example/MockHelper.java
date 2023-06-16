package com.byteplus.rec.sdk.retail.example;

import com.byteplus.rec.sdk.retail.example.entity.DemoProduct;
import com.byteplus.rec.sdk.retail.example.entity.DemoUser;
import com.byteplus.rec.sdk.retail.example.entity.DemoUserEvent;
import com.byteplus.rec.sdk.retail.protocol.ByteplusSaasRetail.Device;
import com.byteplus.rec.sdk.retail.protocol.ByteplusSaasRetail.Product;
import com.byteplus.rec.sdk.retail.protocol.ByteplusSaasRetail.Product.Brand;
import com.byteplus.rec.sdk.retail.protocol.ByteplusSaasRetail.Product.Category;
import com.byteplus.rec.sdk.retail.protocol.ByteplusSaasRetail.Product.Display;
import com.byteplus.rec.sdk.retail.protocol.ByteplusSaasRetail.Product.Price;
import com.byteplus.rec.sdk.retail.protocol.ByteplusSaasRetail.Product.ProductSpec;
import com.byteplus.rec.sdk.retail.protocol.ByteplusSaasRetail.Product.Seller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.byteplus.rec.sdk.retail.protocol.ByteplusSaasRetail.Product.Category.*;

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
        user.setActivationChannel("AppStore");
        user.setMembershipLevel("silver");
        user.setRegistrationTimestamp(1623593487);
        user.setCity("Kirkland");
        user.setCountry("USA");
        user.setDistrict("King County");
        user.setProvince("98033");
        user.setLanguage("English");

        // your custom field
        // user.setCustomField("custom");
        return user;
    }

    public static List<DemoProduct> mockProducts(int count) {
        List<DemoProduct> products = new ArrayList<>(count);
        for (int i = 0; i < count; i++) {
            DemoProduct product = mockProduct();
            product.setProductId(product.getProductId() + i);
            products.add(product);
        }
        return products;
    }

    public static DemoProduct mockProduct() {
        DemoProduct product = new DemoProduct();
        product.setProductId("632461");
        product.setCategories("[{\"category_depth\":1,\"category_nodes\":[{\"id_or_name\":\"Shoes\"}]},{\"category_depth\":2,\"category_nodes\":[{\"id_or_name\":\"Men's Shoes\"}]}]");
        product.setBrands("Adidas");
        product.setCurrentPrice(49.99f);
        product.setOriginalPrice(69.98f);
        product.setIsRecommendable(1);
        product.setTitle("adidas Men's Yeezy Boost 350 V2 Grey/Borang/Dgsogr");
        product.setTags("[\"New Product\",\"Summer Product\"]");
        product.setDisplayCoverMultimediaUrl("[\"https://images-na.ssl-images-amazon.com/images/I/81WmojBxvbL._AC_UL1500_.jpg\"]");
        product.setProductGroupId("1356");
        product.setCommentCount(100);
        product.setSoldCount(60);
        product.setPublishTimestamp(1623193487);
        product.setSource("self");
        product.setUserRating(0.25f);
        product.setSellerId("43485");
        product.setSellerLevel("1");
        product.setSellerRating(3.5f);

        // your custom field
        // product.setCustomField("custom");
        return product;
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
        userEvent.setUserId("1457789");
        userEvent.setEventType("purchase");
        userEvent.setEventTimestamp(1686883465);
        userEvent.setSceneName("product detail page");
        userEvent.setPageNumber(2);
        userEvent.setOffset(10);
        userEvent.setProductId("632461");
        userEvent.setPlatform("app");
        userEvent.setOsType("android");
        userEvent.setAppVersion("9.2.0");
        userEvent.setDeviceModel("huawei-mate30");
        userEvent.setOsVersion("10");
        userEvent.setNetwork("3g");
        userEvent.setQuery("iPad");
        userEvent.setParentProductId("441356");
        userEvent.setAttributionToken("eyJpc3MiOiJuaW5naGFvLm5ldCIsImV4cCI6IjE0Mzg5NTU0NDUiLCJuYW1lIjoid2FuZ2hhbyIsImFkbWluIjp0cnVlfQ");
        userEvent.setTrafficSource("self");
        userEvent.setPurchaseCount(20);
        userEvent.setPaidPrice(12.23f);
        userEvent.setCurrency("USD");
        userEvent.setCity("Kirkland");
        userEvent.setCountry("USA");
        userEvent.setDistrict("King County");
        userEvent.setProvince("98033");

        // your custom field
        // userEvent.setCustomField("custom");
        return userEvent;
    }

    public static Product mockPredictProduct() {
        CategoryNode category1Node1 = CategoryNode.newBuilder()
                .setIdOrName("Shoes")
                .build();
        Category category1 = newBuilder()
                .setCategoryDepth(1)
                .addCategoryNodes(category1Node1)
                .build();
        CategoryNode category2Node1 = CategoryNode.newBuilder()
                .setIdOrName("Men's Shoes")
                .build();
        Category category2 = newBuilder()
                .setCategoryDepth(2)
                .addCategoryNodes(category2Node1)
                .build();

        Brand brand1 = Brand.newBuilder()
                .setBrandDepth(1)
                .setIdOrName("Adidas")
                .build();
        Brand brand2 = Brand.newBuilder()
                .setBrandDepth(2)
                .setIdOrName("Yeezy")
                .build();

        Price price = Price.newBuilder()
                .setCurrentPrice(49900)
                .setOriginPrice(69900)
                .build();

        Display display = Display.newBuilder()
                .addAllDetailPageDisplayTags(Arrays.asList("FreeShipping", "Return in 7 days without any reasons"))
                .addAllListingPageDisplayTags(Arrays.asList("best seller", "hot sales"))
                .setListingPageDisplayType("image")
                .setCoverMultimediaUrl("https://images-na.ssl-images-amazon.com/images/I/81WmojBxvbL._AC_UL1500_.jpg")
                .build();

        ProductSpec spec = ProductSpec.newBuilder()
                .setProductGroupId("1356")
                .setUserRating(0.25)
                .setCommentCount(100)
                .setSource("self")
                .setPublishTimestamp(1623193487)
                .build();

        Seller seller = Seller.newBuilder()
                .setId("43485")
                .setSellerLevel("1")
                .setSellerRating(3.5)
                .build();

        return Product.newBuilder()
                .setProductId("632461")
                .addCategories(category1)
                .addCategories(category2)
                .addBrands(brand1)
                .addBrands(brand2)
                .setPrice(price)
                .setIsRecommendable(1)
                .setTitle("adidas Men's Yeezy Boost 350 V2 Grey/Borang/Dgsogr")
                .setQualityScore(4.4)
                .addAllTags(Arrays.asList("New Product", "Summer Product"))
                .setDisplay(display)
                .setProductSpec(spec)
                .setSeller(seller)
                // .putExtra("count", "20")
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
