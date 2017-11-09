
import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

/**
 * Created by jiangtao7 on 2017/11/6.
 */
public class ConCurrentUtil {
    /**
     * 获取指定数量线程池的executor
     * @param threadPoolNumber
     * @return
     */
    public static ExecutorService getExecutorService(int threadPoolNumber)
    {
        ThreadFactory namedTheadFactory = new ThreadFactoryBuilder()
                .setNameFormat("user-pool-%d").build();

        // common thread pool
        ExecutorService pool = new ThreadPoolExecutor(threadPoolNumber,200,
                0L,TimeUnit.MILLISECONDS,
                new LinkedBlockingDeque<Runnable>(1024),namedTheadFactory,new ThreadPoolExecutor.AbortPolicy());
        return pool;
    }
}
