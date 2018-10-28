package tw.alex.phoneinfo;

import android.Manifest;
import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;

public class MainActivity extends AppCompatActivity {
    private TelephonyManager tmgr;
    private AccountManager amgr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.GET_ACCOUNTS)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_PHONE_STATE,
                            Manifest.permission.READ_PHONE_NUMBERS,
                            Manifest.permission.GET_ACCOUNTS,
                            Manifest.permission.ACCOUNT_MANAGER,},
                    123);

        }else{
            init();
        }


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        init();
    }

    private void init(){
        tmgr = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
        String num =  tmgr.getLine1Number();
        Log.v("alex", "num: " +num);
        String did = tmgr.getDeviceId();//IMEI
        Log.v("alex", "IMEI: " +did);
        String imsi = tmgr.getSubscriberId(); //IMSI
        Log.v("alex", "IMSI: " + imsi);

        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.N) {

            amgr = (AccountManager) getSystemService(Context.ACCOUNT_SERVICE);
            //amgr = AccountManager.get(this); for android 8?
            Account[] accounts = amgr.getAccounts();
            Log.v("alex", "count:" + accounts.length);
            for (Account account : accounts) {
                Log.v("alex", account.name + " : " + account.type);
            }
        }else{

        }
    }
}
