
import io.druid.jackson.DefaultObjectMapper;
import io.druid.query.Druids;
import io.druid.query.Query;
import io.druid.query.aggregation.CountAggregatorFactory;
import io.druid.query.aggregation.LongSumAggregatorFactory;
import io.druid.query.aggregation.cardinality.CardinalityAggregatorFactory;
import io.druid.query.metadata.metadata.SegmentMetadataQuery;
import io.druid.query.select.PagingSpec;
import io.druid.query.select.SelectQuery;
import io.druid.query.timeseries.TimeseriesQuery;
import io.druid.query.topn.TopNQuery;
import io.druid.query.topn.TopNQueryBuilder;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;

import java.io.InputStream;
import java.text.ParseException;
import java.util.Arrays;

/**
 * Created by jiangtao7 on 2017/6/28.
 */
public class QueryUtil {

    private static String url= PropertyHolder.values().getDuridUrl();
    /**
     * define app reference source
     */
    public final static String APP_REQUEST = "";

    public static DefaultObjectMapper mapper = new DefaultObjectMapper();

    public static String exec(final Query query) {
        HttpPost request = new HttpPost(url);
        try {
            // HttpClientBuilder builder = HttpClientBuilder.create();
            // builder.setDefaultRequestConfig(
            // RequestConfig.custom().setConnectTimeout(500000)
            // .setAuthenticationEnabled(false)
            // .setConnectionRequestTimeout(500000).build());
            HttpClient httpClient = HttpClients.createDefault();

            String jsonString = "";
            jsonString = mapper.writeValueAsString(query);

            request.addHeader("accept", "*/*");
            request.addHeader("content-type", "application/json");
            request.setEntity(new StringEntity(jsonString));

            HttpResponse response =  httpClient.execute(request);

            if ((response).getStatusLine().getStatusCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : " + response.getStatusLine().getStatusCode());
            }

            HttpEntity entity = response.getEntity();
            InputStream is = entity.getContent();
            byte[] responseBody = IOUtils.toByteArray(is);
            String result = new String(responseBody);

            return result;

        } catch (Throwable t) {
            t.printStackTrace();
        } finally {
            request.releaseConnection();
        }
        //return null;
        return "[]";
    }

    public static void main1(String[] args) throws ParseException {
        //DruidClient client = new DruidClient("http://192.168.1.10:9080/druid/v2/?pretty");


        TimeseriesQuery timeseriesQuery1 = Druids.newTimeseriesQueryBuilder().dataSource("pageRequest")
                //.filters(new AndDimFilter(Arrays.asList(new SelectorFilter("dimension1", "value1"),)))
                .granularity("day").intervals("2015-08-17/2015-08-18")
                .aggregators(Arrays.asList(new CountAggregatorFactory("count"),
                        new CardinalityAggregatorFactory("uv", Arrays.asList("cookie"), false)))
                //.postAggregators(Arrays.asList(new ArithmeticPostAggregator("avg", "/", Arrays.asList(new FieldAccessPostAggregator("count", "count"),new FieldAccessPostAggregator("total_count", "count")))))
                .build();

        //System.out.println(DuridUtil.exec(timeseriesQuery1));

        TopNQuery topNQuery = new TopNQueryBuilder().dataSource("pageRequest").granularity("all")
                // .filters()
                .intervals("2013-01-01/2020-01-02").dimension("osname").metric("count")
                .aggregators(Arrays.asList(new CountAggregatorFactory("count"),
                        new CardinalityAggregatorFactory("value", Arrays.asList("cookie"), false)))
                .threshold(5).build();

        //client.exec(topNQuery);
        //System.out.println(DuridUtil.exec(topNQuery));

        // 选择记录
        SelectQuery selectQuery = Druids.newSelectQueryBuilder().dataSource("pageRequest")
                .granularity("all").intervals("2013-01-01/2020-01-02")
                .pagingSpec(new PagingSpec(null, 300)).build();

        //client.exec(selectQuery);
        //System.out.println(DuridUtil.exec(selectQuery));



        SegmentMetadataQuery sm = Druids.newSegmentMetadataQueryBuilder().dataSource("pageRequest").intervals("2013-01-01/2020-01-02").build();
        //System.out.println(DuridUtil.exec(sm));


        TimeseriesQuery today_timeseriesQuery = Druids.newTimeseriesQueryBuilder().dataSource("pageRequest")
                .granularity("day").intervals("2013-01-01/2020-01-02")
                .aggregators(Arrays.asList(new LongSumAggregatorFactory("pv", "count"),
                        new CardinalityAggregatorFactory("uv", Arrays.asList("cookie","province"), true)))
                .build();
        //System.out.println(DuridUtil.exec(today_timeseriesQuery));


    }

}
