package Week_01;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author bran.chen
 * @description
 */
public class HelloClassLoader extends ClassLoader {

    public static void main(String[] args) {
        try {
            HelloClassLoader helloClassLoader = new HelloClassLoader();
            Class helloClass = helloClassLoader.findClass("Hello");
            System.out.println(helloClass.getName());
            // 生成实例
            Object instance = helloClass.newInstance();
            // 反射获取hello方法
            Method helloMethod = helloClass.getMethod("hello");
            // 执行hello方法
            helloMethod.invoke(instance);

        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException | NoSuchMethodException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    /**
     * 自定义加载类
     *
     * @param name
     * @return
     * @throws ClassNotFoundException
     */
    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        try {
            String path = "D:\\workspaces\\JAVA-000\\Week_01\\Hello.xlass".replace("\\", "/");
            // 读取Hello.xclass文件到Byte数组
            byte[] bytes = getByteByFile(path);
            // 加载自定义Hello类
            return defineClass(name, bytes, 0, bytes.length);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return super.findClass(name);
    }

    /**
     * 读取文件转化为字节流
     *
     * @param filePath
     * @return
     * @throws IOException
     */
    public static byte[] getByteByFile(String filePath) throws IOException {
        File file = new File(filePath);
        long fileSize = file.length();
        if (fileSize > Integer.MAX_VALUE) {
            return null;
        }
        FileInputStream fi = new FileInputStream(file);
        byte[] buffer = new byte[(int) fileSize];
        int offset = 0;
        int numRead = 0;
        while (offset < buffer.length
                && (numRead = fi.read(buffer, offset, buffer.length - offset)) >= 0) {
            offset += numRead;
        }
        // 将读取到的字节（x=255-x）
        for (int i = 0; i < buffer.length; i++) {
            buffer[i] = (byte) (255 - buffer[i]);
        }
        fi.close();
        return buffer;
    }
}
