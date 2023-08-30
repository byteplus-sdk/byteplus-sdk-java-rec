## byteplus rec java sdk

#### Install the client library
```xml
<!-- https://mvnrepository.com/artifact/com.byteplus/byteplus-sdk-java-rec -->
<dependency>
    <groupId>com.byteplus</groupId>
    <artifactId>byteplus-sdk-java-rec</artifactId>
    <version>${latest}</version>
</dependency>
```

#### Saas E-Commerce Example
```java
import com.byteplus.rec.sdk.region.Region;
import com.byteplus.rec.sdk.retail.RetailClient;

public class Main {
    private static RetailClient client;

    static {
        try {
            client = new RetailClientBuilder()
                    .AccountID("***********")  // Required
                    .projectID("***********")
                    .region(Region.SG)  // Required
                    .authAK("*********")  // Required. Access Key, used to generate request signature. Saas Standard projects should use.
                    .authSK("*********")  // Required. Secure key, used to generate request signature. Saas Standard projects should use.
//                    .airAuthToken("*********") // Required. The token of this project. Saas Premium projects should use.
//                    .Schema("http") // Optional
//                    .Hosts(Collections.singletonList("rec-api-sg1.recplusapi.com")) // Optional
                    .build();
        } catch (BizException e) {
            log.error("fail to create byteplus rec client", e);
        }
    }
    public static void main(String[] args) {
        client.writeUsers();
        client.writeProducts();
        client.writeUserEvents();
        client.predict();
    }
}
```

#### Saas Content(Short-Video/Image/Doc) Example

```java
import com.byteplus.rec.sdk.content.ContentClientBuilder;
import com.byteplus.rec.sdk.region.Region;
import com.byteplus.rec.sdk.content.ContentClient;

public class Main {
    private static ContentClient client;

    static {
        try {
            client = new ContentClientBuilder()
                    .AccountID("***********")  // Required
                    .projectID("***********")
                    .region(Region.SG)  // Required
                    .authAK("*********")  // Required. Access Key, used to generate request signature. Saas Standard projects should use.
                    .authSK("*********")  // Required. Secure key, used to generate request signature. Saas Standard projects should use.
//                    .airAuthToken("*********") // Required. The token of this project. Saas Premium projects should use.
//                    .Schema("http") // Optional
//                    .Hosts(Collections.singletonList("rec-api-sg1.recplusapi.com")) // Optional
                    .build();
        } catch (BizException e) {
            log.error("fail to create byteplus rec client", e);
        }
    }

    public static void main(String[] args) {
        client.writeUsers();
        client.writeContents();
        client.writeUserEvents();
        client.predict();
    }
}
```

#### How to run example
Take the E-commerce industry as an example:
* Clone the project.
* enter the example directory.
* fill necessary parameters.
* run main.java.

```shell
git clone https://github.com/byteplus-sdk/byteplus-sdk-java-rec.git
cd byteplus-sdk-java-rec/src/main/java/com/byteplus/rec/sdk/retail/example
# In the main.java file, fill in projectID, modelID, tenantID, AK, SK and other parameters, and then run main.java.
```

#### For more details
* [Saas E-Commerce Code Sample](https://docs.byteplus.com/en/recommend/samples/retail_code_samples)
* [Saas E-Commerce API Reference](https://docs.byteplus.com/en/recommend/reference/retail_saas_writeusers)
* [Saas Content Code Sample](https://docs.byteplus.com/en/recommend/samples/content_code_samples)
* [Saas Content API Reference](https://docs.byteplus.com/en/recommend/reference/content_saas_writeusers)