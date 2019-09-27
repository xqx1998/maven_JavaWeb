import java.lang.reflect.Method;

/**
 * @author：xingquanxiang createTime：2019/9/24 22:53
 * description:
 */
public class Test {
    public static void main(String[] args) {

        Method[] methods = System.class.getMethods();
        for (Method method : methods) {
            System.out.println(method.getName());
        }
        //获取当前时间  开始时间
        long begin = System.currentTimeMillis();
        for (int i = 0; i < 10000000; i++) {

        }
        // 获取当前时间 结束时间
        long end = System.currentTimeMillis();
        //计算耗时
        System.out.println(end-begin);
    }
}
