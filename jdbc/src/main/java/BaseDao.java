import java.util.List;
import java.util.Map;

/**
 * @author xiaopantx
 * @version 1.0
 * @description TODO 查询类
 */
public class BaseDao<T> {

    /**
     * 查询列表
     * @param param 查询参数
     * @return 封装后的结果集列表
     */
    public List<T> queryList(T param) {
        Class<?> clazz = param.getClass();



        return null;
    }


}
