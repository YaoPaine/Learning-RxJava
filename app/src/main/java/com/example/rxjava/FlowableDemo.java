package com.example.rxjava;

import android.util.Log;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by heyao on 2017/6/27.
 */

public class FlowableDemo {

    private static String TAG = "Flowable";

    public static void test1() {
        Flowable<Integer> flowable = Flowable.create(new FlowableOnSubscribe<Integer>() {
            @Override
            public void subscribe(@NonNull FlowableEmitter<Integer> emitter) throws Exception {
                Log.w(TAG, "emit 1");
                emitter.onNext(1);
                Log.w(TAG, "emit 2");
                emitter.onNext(2);
                Log.w(TAG, "emit 3");
                emitter.onNext(3);
                Log.w(TAG, "emit complete");
                emitter.onComplete();
            }
        }, BackpressureStrategy.ERROR);

        Subscriber<Integer> subscriber = new Subscriber<Integer>() {
            @Override
            public void onSubscribe(Subscription s) {
                Log.e(TAG, "onSubscribe: ");
                s.request(Long.MAX_VALUE);  //注意这句代码
            }

            @Override
            public void onNext(Integer integer) {
                Log.e(TAG, "onNext: " + integer);
            }

            @Override
            public void onError(Throwable t) {
                Log.e(TAG, "onError: ", t);
            }

            @Override
            public void onComplete() {
                Log.e(TAG, "onComplete: ");
            }
        };

        flowable.subscribe(subscriber);
    }

    public static void test2() {

        Flowable<Integer> flowable = Flowable.create(new FlowableOnSubscribe<Integer>() {
            @Override
            public void subscribe(@NonNull FlowableEmitter<Integer> emitter) throws Exception {
                Log.w(TAG, "emit 1");
                emitter.onNext(1);
                Log.w(TAG, "emit 2");
                emitter.onNext(2);
                Log.w(TAG, "emit 3");
                emitter.onNext(3);
                Log.w(TAG, "emit complete");
                emitter.onComplete();
            }
        }, BackpressureStrategy.ERROR);

        Subscriber<Integer> subscriber = new Subscriber<Integer>() {
            @Override
            public void onSubscribe(Subscription s) {
                Log.e(TAG, "onSubscribe: ");
//                s.request(Long.MAX_VALUE);  //注意这句代码
            }

            @Override
            public void onNext(Integer integer) {
                Log.e(TAG, "onNext: " + integer);
            }

            @Override
            public void onError(Throwable t) {
                Log.e(TAG, "onError: ", t);
            }

            @Override
            public void onComplete() {
                Log.e(TAG, "onComplete: ");
            }
        };

        flowable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);

    }

    public static void test3() {
        Flowable<Integer> flowable = Flowable.create(new FlowableOnSubscribe<Integer>() {
            @Override
            public void subscribe(@NonNull FlowableEmitter<Integer> emitter) throws Exception {
                Log.w(TAG, "emit 1");
                emitter.onNext(1);
                Log.w(TAG, "emit 2");
                emitter.onNext(2);
                Log.w(TAG, "emit 3");
                emitter.onNext(3);
                Log.w(TAG, "emit complete");
                emitter.onComplete();
            }
        }, BackpressureStrategy.ERROR);

        Subscriber<Integer> subscriber = new Subscriber<Integer>() {
            @Override
            public void onSubscribe(Subscription s) {
                Log.e(TAG, "onSubscribe: ");
//                s.request(Long.MAX_VALUE);  //注意这句代码
            }

            @Override
            public void onNext(Integer integer) {
                Log.e(TAG, "onNext: " + integer);
            }

            @Override
            public void onError(Throwable t) {
                Log.e(TAG, "onError: ", t);
            }

            @Override
            public void onComplete() {
                Log.e(TAG, "onComplete: ");
            }
        };

        flowable.subscribe(subscriber);
    }

    public static void test4() {
        Flowable.create(new FlowableOnSubscribe<Integer>() {
            @Override
            public void subscribe(@NonNull FlowableEmitter<Integer> e) throws Exception {
                for (int i = 0; i < 129; i++) {
                    Log.w(TAG, "subscribe: " + i);
                    e.onNext(i);
                }
                Log.e(TAG, "subscribe: onComplete: ");
                e.onComplete();
            }
        }, BackpressureStrategy.ERROR).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onSubscribe(Subscription s) {
                        Log.e(TAG, "onSubscribe: ");
//                s.request(Long.MAX_VALUE);  //注意这句代码
                    }

                    @Override
                    public void onNext(Integer integer) {
                        Log.e(TAG, "onNext: " + integer);
                    }

                    @Override
                    public void onError(Throwable t) {
                        Log.e(TAG, "onError: ", t);
                    }

                    @Override
                    public void onComplete() {
                        Log.e(TAG, "onComplete: ");
                    }
                });


    }

}
