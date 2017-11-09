
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by jiangtao7 on 2017/10/26.
 * 分页通用类
 */
public class PagingUtil {
    public static Map cacheDataMap;
    public static int totalItems;
    public static int pageSize;
    /**
     * 总共查询的条数
     */
    public static int totalRows;
    public static int startNum;
    public static int currentPage;

    /**
     * 根据开始页返回剩余的查询结果List
     *
     * @param startNum
     * @param resultMap
     * @return
     */
    public static List getNextIndexData(int startNum, Map resultMap) {
        List subList = new ArrayList();
        List adInfoList;
        String key = Constant.AD_INFO_LIST;
        if (resultMap.containsKey(key)) {
            adInfoList = (List) resultMap.get(key);
        } else {
            return subList;
        }
        totalItems = adInfoList.size();
        // 超过列表长度
        if ((startNum + pageSize) > totalItems) {
            subList = adInfoList.subList(startNum, totalItems);
        } else {
            subList = adInfoList.subList(startNum, pageSize + startNum);
        }

        ListSortUtil.sortList(subList, "adPv", "DESC");
        return subList;
    }

    /**
     * 设置 分页参数
     *
     * @param currentPage
     * @param perPageSize
     */
    public static void setPageingParameter(Integer currentPage, Integer perPageSize) {
        // 设置每页显示好多条数据
        if (perPageSize == null) {
            pageSize = 10;
        } else {
            pageSize = perPageSize;
        }
        // 设置当前显示的页面
        if (currentPage == null) {
            PagingUtil.currentPage = 1;
        } else {
            PagingUtil.currentPage = currentPage;
        }
        /**
         * 设置的开始页数
         */
        startNum = (currentPage - 1) * pageSize;
    }
}
