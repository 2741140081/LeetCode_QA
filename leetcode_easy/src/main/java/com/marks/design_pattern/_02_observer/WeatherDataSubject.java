package com.marks.design_pattern._02_observer;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: WeatherDataSubject </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/4/13 15:06
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class WeatherDataSubject implements Subject {
    private float temperature;
    private float humidity;
    private float pressure;

    private List<Observer> observers;

    public WeatherDataSubject() {
        this.observers = new ArrayList<>();
    }

    @Override
    public boolean registerObserver(Observer o) {
        if (!observers.contains(o)) {
            observers.add(o);
            return true;
        }
        return false;
    }

    @Override
    public boolean removeObserver(Observer o) {
        if (observers.contains(o)) {
            observers.remove(o);
            return true;
        }
        return false;
    }

    @Override
    public void notifyObservers() {
        observers.forEach(observer -> observer.update(temperature, humidity, pressure));
    }

    private float getTemperature() {
        return 0;
    }

    private float getHumidity() {
        return 0;
    }

    private float getPressure() {
        return 0;
    }

    public void measurementsChanged() {
        notifyObservers();
    }

    /**
     * FixMe:
     * 暂时使用这个方法来模拟气象数据更新，以测试布告板（可以从网站上抓取真实的观测值）
     */
    public void setMeasurements(float temperature, float humidity, float pressure) {
        this.temperature = temperature;
        this.humidity = humidity;
        this.pressure = pressure;
        measurementsChanged();
    }
}
