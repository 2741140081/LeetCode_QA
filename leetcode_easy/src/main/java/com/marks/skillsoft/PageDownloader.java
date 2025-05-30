package com.marks.skillsoft;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/5/27 10:04
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class PageDownloader implements Runnable{

    private String[] urlLists;

    public PageDownloader(String[] urlLists) {
        this.urlLists = urlLists;
    }

    /**
     * When an object implementing interface {@code Runnable} is used
     * to create a thread, starting the thread causes the object's
     * {@code run} method to be called in that separately executing
     * thread.
     * <p>
     * The general contract of the method {@code run} is that it may
     * take any action whatsoever.
     *
     * @see Thread#run()
     */
    @Override
    public void run() {


        for (String url : urlLists) {
            try {
                URL pageUrl = new URL(url);
                BufferedReader reader = new BufferedReader(new InputStreamReader(pageUrl.openStream()));

                StringBuilder strBuilder = new StringBuilder();
                String line;

                while ((line = reader.readLine()) != null) {
                    strBuilder.append(line).append("\n");
                }

                reader.close();
            } catch (MalformedURLException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }

    }

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(2);
    }
}
