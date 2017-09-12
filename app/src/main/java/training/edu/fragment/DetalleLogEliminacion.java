package training.edu.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import training.edu.droidbountyhunter.R;
import training.edu.interfaces.OnLogListener;

/**
 * Created by Dan14z on 06/09/2017.
 */

public class DetalleLogEliminacion extends Fragment implements OnLogListener {

    private boolean mIsTablet = false;
    private TextView mStatusTextView;
    private TextView mDateTextView;
    private String mStatus;
    private String mDate;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mIsTablet = getActivity().getIntent().getBooleanExtra("isTablet",false);
        if(!mIsTablet){
            mStatus = getActivity().getIntent().getStringExtra("status");
            mDate = getActivity().getIntent().getStringExtra("date");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detalle_log_de_eliminacion,container,false);
        mStatusTextView = (TextView) view.findViewById(R.id.txtEstatus);
        mDateTextView = (TextView) view.findViewById(R.id.txtFecha);

        if(!mIsTablet){
            UpdateData();
        }

        return view;
    }

    public void UpdateData(){
        if (mStatus != null || mDate != null){
            mStatusTextView.setText(mStatus.equals("0") ? "Fugitivo" : "Atrapado");
            mDateTextView.setText(mDate);
        }
    }

    @Override
    public void OnLogItemList(String date, String status) {
        mStatus = status;
        mDate = date;
        UpdateData();
    }
}
