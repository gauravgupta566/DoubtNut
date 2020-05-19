package com.example.doubtnut.ui.main;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doubtnut.responsemodel.Article;
import com.example.doubtnut.utils.RecyclerItemClickListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class Hello extends AppCompatActivity {

    List<Article> list=new ArrayList<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);




    }


    private Observer<String> getAnimalsObserver() {
        return new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {
            }

            @Override
            public void onNext(String s) {
            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onComplete() {

            }
        };
    }
}
