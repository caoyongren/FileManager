package com.jiepier.filemanager.sqlite;

import android.util.Log;

import com.jiepier.filemanager.Constant.AppConstant;
import com.jiepier.filemanager.event.TypeEvent;
import com.jiepier.filemanager.preview.MimeTypes;
import com.jiepier.filemanager.sqlite.DataManager;
import com.jiepier.filemanager.util.RxBus.RxBus;
import com.jiepier.filemanager.util.ToastUtil;

import java.io.File;
import java.util.ArrayList;

import rx.Observable;
import rx.functions.Action1;

/**
 * Created by panruijie on 17/1/4.
 * Email : zquprj@gmail.com
 */

public class SqlUtil {

    public static void insert(String path){

        String type = getType(path);
        if (type != null)
            DataManager.getInstance()
                    .insertSQLUsingObservable(type,path)
                    .subscribe(aBoolean -> {

                    });
    }

    public static void update(String orignalPath,String path){

        String type = getType(path);
        if (type != null) {
            DataManager.getInstance()
                    .updateSQLUsingObservable(type, orignalPath, path)
                    .subscribe(aBoolean -> {

                    });
        }
    }

    public static void delete(String path){

        String type = getType(path);
        if (type != null)
            DataManager.getInstance()
                    .deleteSQLUsingObservable(type,path)
                    .subscribe(aBoolean -> {

                    });

    }

    //查找结果是不同类型，故不在此做查询
    public static Observable<ArrayList<String>> select(String type){

        return DataManager.getInstance()
                .selectUsingObservable(type);
    }

    private static String getType(String path){

        File f = new File(path);
        if (MimeTypes.isApk(f)){
            return DataManager.APK;
        }else if (MimeTypes.isDoc(f)){
            return DataManager.DOC;
        }else if (MimeTypes.isZip(f)){
            return DataManager.ZIP;
        }else if (MimeTypes.isMusic(f)){
            return DataManager.MUSIC;
        }

        return null;
    }

}
