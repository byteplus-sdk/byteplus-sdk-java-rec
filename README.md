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

#### Example
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
                    .authAK("***********")  // Required
                    .authSK("***********")  // Required
//                    .Schema("http") // Optional
//                    .Hosts(Collections.singletonList("rec-api-sg1.recplusapi.com")) // Optional
                    .build();
        } catch (BizException e) {
            log.error("fail to create byteplus rec client", e);
        }
    }
    public static void main(String[] args) {
        client.writeUsers();
        client.predict();
    }
}
```

#### How to run example
* Clone the project.
* enter the example directory.
* fill necessary parameters.
* run main.java.

```shell
git clone https://github.com/byteplus-sdk/byteplus-sdk-java-rec.git
cd byteplus-sdk-java-rec/src/main/java/com/byteplus/rec/sdk/retail/example
```
In the main.java file, fill in projectID, modelID, tenantID, AK, SK and other parameters, and then run main.java.

#### For more details:
* [code sample](https://docs.byteplus.com/recommend/docs/code-samplesaas)