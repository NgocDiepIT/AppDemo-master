package com.example.appdemo.dbcontext;

import com.example.appdemo.json_models.response.UserInfor;

import io.realm.Realm;
import io.realm.RealmResults;

public class RealmContext {
    private Realm realm;

    private static RealmContext instance;

    private RealmContext() {
        realm = Realm.getDefaultInstance();
    }

    public static RealmContext getInstance() {
        if (instance == null) {
            instance = new RealmContext();
        }
        return instance;
    }

    public void addUser(UserInfor userInfor) {
        deleteAllUser();

        realm.beginTransaction();
        UserInfor user = realm.createObject(UserInfor.class);

        user.setUserId(userInfor.getUserId());
        user.setAvatar(userInfor.getAvatar());
        user.setFullName(userInfor.getFullName());
        user.setUsername(userInfor.getUsername());

        realm.copyFromRealm(user);
        realm.commitTransaction();
    }

    public UserInfor getUser() {
        return realm.where(UserInfor.class).findFirst();
    }

    public void deleteAllUser() {
        RealmResults<UserInfor> userInfoList = realm.where(UserInfor.class).findAll();

        realm.beginTransaction();
        userInfoList.deleteAllFromRealm();
        realm.commitTransaction();
    }

}
