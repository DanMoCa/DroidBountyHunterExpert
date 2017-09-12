package training.edu.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import training.edu.data.DBProvider;
import training.edu.droidbountyhunter.DetalleLogEliminacion;
import training.edu.droidbountyhunter.R;
import training.edu.interfaces.OnLogListener;

/**
 * Created by Dan14z on 06/09/2017.
 */

public class LogEliminacion extends Fragment {
    private ArrayAdapter<String> mAdapter;
    private ArrayList<String[]> mLogs;
    private OnLogListener mListener;
    private boolean mIsTablet = false;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mIsTablet = getActivity().getSupportFragmentManager().findFragmentById(R.id.fragmentDetalleLogEliminacion) != null;
        if(mIsTablet){
            mListener = (OnLogListener) getActivity().getSupportFragmentManager().findFragmentById(R.id.fragmentDetalleLogEliminacion);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_log_eliminacion,container,false);

        DBProvider db = new DBProvider(getContext());
        mLogs = db.ObtenerLogsEliminacion();
        String[] logsRow = new String[mLogs.size()];
        for(int i = 0; i < mLogs.size(); i++){
            logsRow[i] = mLogs.get(i)[0] + " --> " + mLogs.get(i)[1];
        }
        ListView listView = (ListView) view.findViewById(R.id.lista_logs);
        mAdapter = new ArrayAdapter<String>(getContext(),android.R.layout.simple_list_item_1,logsRow);
        listView.setAdapter(mAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String date = mLogs.get(position)[1];
                String status = mLogs.get(position)[2];

                Toast.makeText(getContext(),String.valueOf(position) + " " + mAdapter.getItem(position),Toast.LENGTH_SHORT).show();
                if(mIsTablet){
                    mListener.OnLogItemList(date,status);
                }else{
                    Intent intent = new Intent(getContext(),training.edu.droidbountyhunter.DetalleLogEliminacion.class);
                    intent.putExtra("isTablet",mIsTablet);
                    intent.putExtra("status",status);
                    intent.putExtra("date",date);
                    startActivity(intent);
                }
            }
        });

        return view;
    }
}
