package com.doday.rxjavaeventbussample.rxjavaeventbus;

import rx.Observable;
import rx.subjects.PublishSubject;
import rx.subjects.Subject;

/**
 * Created by sessi on 24/10/16.
 */

public class EventBus<T> {

    public static final EventBus<Object> sAppBus = EventBus.createSimple();//singleton bus
    private final Subject<T, T> subject;

    public EventBus() {
        this(PublishSubject.<T>create());
    }

    public EventBus(Subject<T, T> subject) {
        this.subject = subject;
    }

    public <E extends T> void post(E event) {
        subject.onNext(event);
    }

    public Observable<T> observe() {
        return subject;
    }

    public <E extends T> Observable<E> observeEvents(Class<E> eventClass) {
        return subject.ofType(eventClass);//pass only events of specified type, filter all other
    }

    public static <T> EventBus<T> createSimple() {
        return new EventBus<>();//PublishSubject is created in constructor
    }


}
