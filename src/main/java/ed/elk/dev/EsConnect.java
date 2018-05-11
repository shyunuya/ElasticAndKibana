package ed.elk.dev;

import org.apache.http.HttpHost;
import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.DocWriteResponse;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.admin.indices.rollover.RolloverResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.json.simple.JSONObject;

import java.io.IOException;

public class EsConnect {
    public static void main(String[] args) throws IOException {
        JsonTest jt = new JsonTest();
        RestHighLevelClient client = new RestHighLevelClient(
                RestClient.builder(
                        new HttpHost("ed4.cdh.dev", 9200, "http")
                )
        );

        CreateIndexRequest request = new CreateIndexRequest("bitcoin");
        CreateIndexResponse createIndexResponse = client.indices().create(request);

        boolean acknowledged = createIndexResponse.isAcknowledged();
        if (acknowledged != false) {
            System.out.println("good");
        } else {
            System.out.println("bad");
        }

        IndexRequest indexRequest = new IndexRequest("bitcoin", "coinPrice").source(jt.readJsonFromUrl("https://crix-api-endpoint.upbit.com/v1/crix/candles/minutes/1?code=CRIX.UPBIT.KRW-XRP&count=10&to"));

        IndexResponse indexResponse = client.index(indexRequest);

        String index = indexResponse.getIndex();
        String type = indexResponse.getType();
        String id = indexResponse.getId();
        long version = indexResponse.getVersion();
        if (indexResponse.getResult() == DocWriteResponse.Result.CREATED) {
            System.out.println("Created successfully");

        } else if (indexResponse.getResult() == DocWriteResponse.Result.UPDATED) {
            System.out.println("Updated successfully");
        }


//        IndexRequest request = new IndexRequest("bitcoin","coin");
//        request.source(jt.readJsonFromUrl("https://crix-api-endpoint.upbit.com/v1/crix/candles/minutes/1?code=CRIX.UPBIT.KRW-XRP&count=1&to"), XContentType.JSON);


    }
}
