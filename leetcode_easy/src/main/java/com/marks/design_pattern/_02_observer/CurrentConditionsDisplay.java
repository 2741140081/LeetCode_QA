package com.marks.design_pattern._02_observer;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: CurrentConditionsDisplay </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/4/13 15:05
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class CurrentConditionsDisplay implements Observer, DisplayElement {
    private float temperature;
    private float humidity;
    private Subject subject;

    public CurrentConditionsDisplay(Subject subject) {
        this.subject = subject;
        subject.registerObserver(this);
    }



    @Override
    public void display() {
        System.out.println("当前天气: 温度: " + temperature + "度, 湿度:" + humidity);
    }

    @Override
    public void update(float temp, float humidity, float pressure) {
        this.temperature = temp;
        this.humidity = humidity;
    }
}
