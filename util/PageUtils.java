
public class PageUtils {
	
	public static final Integer pageSize = 10;//默认每页数据量

	/**
	 * 根据页数和每页数据量，取得开始索引
	 * 
	 * @param pageNo 第几页
	 * @param pageSize 每页数据量
	 * @return 开始索引
	 */
	public static Integer getStartNum(Integer pageNo, Integer pageSize) {
		if (pageNo != null) {
			return (pageNo - 1) * pageSize;
		}
		return 0;
	}
	
	public static Integer getStartNum(Integer pageNo) {
		return getStartNum(pageNo,pageSize);
	}

	/**
	 * 根据数据总量和每页数据量，取得总页数
	 * 
	 * @param count 数据总量
	 * @param pageSize 每页数据量
	 * @return 总页数
	 */
	public static Integer getTotalPage(Integer count, Integer pageSize) {
		if (count % pageSize == 0) {
			return count / pageSize;
		} else {
			return count / pageSize + 1;
		}
	}

	public static Integer getTotalPage(Integer count) {
		return getTotalPage(count, pageSize);
	}
	
}
