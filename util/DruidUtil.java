
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

public class DruidUtil {

	private static String url= PropertyHolder.values().getDuridUrl();
	//private static String url="http://hadoop01:9080/druid/v2/?pretty";
	public final static String pageRequest = "pageRequest";
	public final static String shopping = "shopping";
	public final static String pageIM = "pageIM";
	public static final String SALEGOODS="saleGoods";
	public static final String SALESTOCK="saleStock";
	public static final String SALEPROTOTYPE="salePrototype";
	public final static String sysRoleExportFileName = "权限列表    ";
	public final static int pageTotal = 10;

	/**
	 * define app reference source
	 */
	public final static String PAGE_YUN_ZHI_APP = "pageYunZhiApp800";
	public final static String UNIQUE_REQUEST_YUNZHI = "uniqueRequestYunZhi800";
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
			request.addHeader("content-type","application/json;charset=UTF-8");
			request.setEntity(new StringEntity(jsonString,"UTF-8"));

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
}
