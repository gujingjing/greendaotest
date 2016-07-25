package greendaodemo.greendaotest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.greendao.Model1;
import com.example.greendao.Model1Dao;

import java.util.List;

import de.greenrobot.dao.query.QueryBuilder;
import de.greenrobot.dao.query.WhereCondition;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button add, query, delet,change_user;
    boolean user=true;
    private String user_name="13916539504";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ((BaseApplication)getApplication()).setupDatabase(user_name+"beizhi");
//        ((BaseApplication)getApplication()).setupDatabase("18682010401"+"beizhi");

        add = (Button) findViewById(R.id.add);
        query = (Button) findViewById(R.id.query);
        delet = (Button) findViewById(R.id.delet);
        change_user= (Button) findViewById(R.id.change_user);

        add.setOnClickListener(this);
        query.setOnClickListener(this);
        delet.setOnClickListener(this);
        change_user.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add:
                Model1Dao dao = ((BaseApplication) getApplication()).getDaoSession().getModel1Dao();

                dao.deleteAll();

                for (int i = 0; i < 10; i++) {
                    dao.insert(new Model1((long) i, user_name +"test"+ i, 0));
                }
                List<Model1> listadd=dao.queryBuilder().list();
                for (int i=0;i<listadd.size();i++){

                    Log.e("lists===add===", listadd.get(i).toString());
                }

                break;
            case R.id.query:
                QueryBuilder queryBuilder = ((BaseApplication) getApplication()).getDaoSession().getModel1Dao().queryBuilder();
                queryBuilder.where(Model1Dao.Properties.Ints.eq(0));
                queryBuilder.where(Model1Dao.Properties.Id.ge(5));
                queryBuilder.where(Model1Dao.Properties.Name.isNotNull());

                List<Model1> listquery = queryBuilder.list();
                for (int i=0;i<listquery.size();i++){

                    Log.e("lists===query===", listquery.get(i).toString());
                }
                break;
            case R.id.delet:
                Model1Dao daod = ((BaseApplication) getApplication()).getDaoSession().getModel1Dao();
                daod.deleteByKeyInTx((long) 1, (long) 2, (long) 3);

                List<Model1> listdelet = daod.queryBuilder().list();
                for (int i=0;i<listdelet.size();i++){

                    Log.e("lists===delet===", listdelet.get(i).toString());
                }
                break;
            case R.id.change_user:
                if(user){
                    user=false;
                    user_name="18682010401";
                    ((BaseApplication)getApplication()).setupDatabase("18682010401"+"beizhi");
                }else{
                    user=true;
                    user_name="13916539504";
                    ((BaseApplication)getApplication()).setupDatabase("13916539504"+"beizhi");
                }
                break;
        }
    }
}
