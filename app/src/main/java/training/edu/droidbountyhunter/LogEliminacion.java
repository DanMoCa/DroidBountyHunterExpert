package training.edu.droidbountyhunter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import training.edu.data.DBProvider;

public class LogEliminacion extends AppCompatActivity {

    ArrayAdapter<String> mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_log_eliminacion);

//        DBProvider db = new DBProvider(this);
//        ArrayList<String[]> logs = db.ObtenerLogsEliminacion();
//        String[] logsRow = new String[logs.size()];
//        for(int i = 0; i < logs.size(); i++){
//            logsRow[i] = logs.get(i)[0] + " ---> " + logs.get(i)[1];
//        }
//        ListView listViewLogs = (ListView)findViewById(R.id.list);
//        mAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,logsRow);
//        listViewLogs.setAdapter(mAdapter);
//
//        listViewLogs.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Toast.makeText(LogEliminacion.this,String.valueOf(position) + " " +mAdapter.getItem(position).toString(),Toast.LENGTH_SHORT).show();
//            }
//        });
    }
}
