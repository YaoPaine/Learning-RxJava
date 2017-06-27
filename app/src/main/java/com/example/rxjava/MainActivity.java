package com.example.rxjava;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

public class MainActivity extends AppCompatActivity {

    private String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /**
         * 上游   Observable
         */

        //创建一个上游
        Observable<Integer> observable = Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Integer> e) throws Exception {
                e.onNext(1);
                e.onNext(2);
                e.onNext(3);
                e.onNext(4);
                e.onComplete();
            }
        });

        /**
         * 下游   Observer
         */

        Observer<Integer> observer = new Observer<Integer>() {

            @Override
            public void onSubscribe(@NonNull Disposable d) {
                Log.e(TAG, "onSubscribe");
            }

            @Override
            public void onNext(@NonNull Integer integer) {
                Log.e(TAG, "onNext: " + integer);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.e(TAG, "onError");
            }

            @Override
            public void onComplete() {
                Log.e(TAG, "onComplete");
            }
        };

        //建立连接
        observable.subscribe(observer);

        chain();

        disposable();
    }

    /**
     * ObservableEmitter： Emitter是发射器的意思，那就很好猜了，这个就是用来发出事件的，它可以发出三种类型的事件，
     * 通过调用emitter的onNext(T value)、onComplete()和onError(Throwable error)就可以分别发出next事件、complete事件和error事件。
     * 但是，请注意，并不意味着你可以随意乱七八糟发射事件，需要满足一定的规则：
     * 1、上游可以发送无限个onNext, 下游也可以接收无限个onNext.
     * 2、当上游发送了一个onComplete后, 上游onComplete之后的事件将会继续发送, 而下游收到onComplete事件之后将不再继续接收事件.
     * 3、当上游发送了一个onError后, 上游onError之后的事件将继续发送, 而下游收到onError事件之后将不再继续接收事件.
     * 4、上游可以不发送onComplete或onError.
     * 5、最为关键的是onComplete和onError必须唯一并且互斥, 即不能发多个onComplete, 也不能发多个onError, 也不能先发一个onComplete, 然后再发一个onError, 反之亦然
     */
    public void chain() {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Integer> e) throws Exception {
                e.onNext(4);
                e.onNext(3);
                e.onNext(2);
                e.onNext(1);
                e.onComplete();
            }
        }).subscribe(new Observer<Integer>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                Log.e(TAG, "onSubscribe");
            }

            @Override
            public void onNext(@NonNull Integer integer) {
                Log.e(TAG, "onNext: " + integer);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.e(TAG, "onError");
            }

            @Override
            public void onComplete() {
                Log.e(TAG, "onComplete");
            }
        });
    }

    public void disposable() {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Integer> e) throws Exception {
                Log.e(TAG, "subscribe: " + 4);
                e.onNext(4);
                Log.e(TAG, "subscribe: " + 3);
                e.onNext(3);
                Log.e(TAG, "subscribe: " + 2);
                e.onNext(2);
                Log.e(TAG, "subscribe: " + 1);
                e.onNext(1);
                Log.e(TAG, "subscribe: onComplete()" );
                e.onComplete();
                Log.e(TAG, "subscribe: " + 0);
                e.onNext(0);
            }
        }).subscribe(new Observer<Integer>() {
            public Disposable disposable;

            @Override
            public void onSubscribe(@NonNull Disposable d) {
                Log.e(TAG, "onSubscribe");
                this.disposable=d;
            }

            @Override
            public void onNext(@NonNull Integer integer) {
                Log.e(TAG, "onNext: " + integer);
                if (integer==2){
                    Log.e(TAG, "dispose(): ");
                    disposable.dispose();
                    Log.e(TAG, "isDisposed(): "+disposable.isDisposed() );
                }

            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.e(TAG, "onError");
            }

            @Override
            public void onComplete() {
                Log.e(TAG, "onComplete");
            }
        });
    }
}
