package com.marks.design_pattern._02_observer.use_java_api;

import com.marks.design_pattern._02_observer.DisplayElement;
import com.marks.design_pattern._02_observer.Observer;
import com.marks.design_pattern._02_observer.Subject;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: WeatherData </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/4/13 15:07
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class WeatherData implements Subject {

    @Override
    public boolean registerObserver(Observer o) {
        return false;
    }

    @Override
    public boolean removeObserver(Observer o) {
        return false;
    }

    @Override
    public void notifyObservers() {

    }
}
