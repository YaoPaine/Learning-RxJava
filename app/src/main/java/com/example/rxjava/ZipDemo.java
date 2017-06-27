package com.example.rxjava;

import android.util.Log;

import java.io.InterruptedIOException;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by heyao on 2017/6/27.
 */

public class ZipDemo {

    private static String TAG = "ZipDemo";

    static {
        RxJavaPlugins.setErrorHandler(new Consumer(){
            @Override
            public void accept(@NonNull Object o) throws Exception {
                if (o instanceof InterruptedIOException) {
                    Log.d(TAG, "Io interrupted==="+((InterruptedIOException) o).getMessage());
                }
            }
        });
    }

    public static void zipOperator() {
        Observable<Integer> observable1 = Observable.create(new ObservableOnSubscribe<Integer>() {

            @Override
            public void subscribe(@NonNull ObservableEmitter<Integer> e) throws Exception {
                Log.e(TAG, "subscribe: " + 1);
                e.onNext(1);
                Thread.sleep(1000);
                Log.e(TAG, "subscribe: " + 2);
                e.onNext(2);
                Thread.sleep(1000);
                Log.e(TAG, "subscribe: " + 3);
                e.onNext(3);
                Thread.sleep(1000);
                Log.e(TAG, "subscribe: " + 4);
                e.onNext(4);
                Thread.sleep(1000);
                Log.e(TAG, "onComplete1()");
                e.onComplete();
            }
        }).subscribeOn(Schedulers.io());

        Observable<String> observable2 = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<String> e) throws Exception {
                Log.e(TAG, "subscribe: " + "A");
                e.onNext("A");
                Thread.sleep(1000);
                Log.e(TAG, "subscribe: " + "B");
                e.onNext("B");
                Thread.sleep(1000);
                Log.e(TAG, "subscribe: " + "C");
                e.onNext("C");
                Thread.sleep(1000);
                Log.e(TAG, "subscribe: " + "D");
                e.onNext("D");
                Thread.sleep(1000);
                Log.e(TAG, "subscribe: " + "E");
                e.onNext("E");
                Thread.sleep(1000);
                Log.e(TAG, "onComplete2()");
                e.onComplete();
                Thread.sleep(1000);
                Log.e(TAG, "subscribe: " + "F");
                e.onNext("F");
            }
        }).subscribeOn(Schedulers.io());

        Observable.zip(observable1, observable2, new BiFunction<Integer, String, String>() {
            @Override
            public String apply(@NonNull Integer integer, @NonNull String s) throws Exception {
                return s + integer;
            }
        }).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        Log.e(TAG, "onSubscribe: ");
                    }

                    @Override
                    public void onNext(@NonNull String s) {
                        Log.e(TAG, "onNext: " + s);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.e(TAG, "onError: ");
                    }

                    @Override
                    public void onComplete() {
                        Log.e(TAG, "onComplete: ");
                    }
                });

    }



}
