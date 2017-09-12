package training.edu.droidbountyhunter;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import training.edu.data.DBProvider;
import training.edu.interfaces.OnTaskListener;
import training.edu.models.Fugitivo;
import training.edu.network.NetServices;

/**
 * @author Giovani González
 * Created by darkgeat on 09/08/2017.
 */

public class Agregar extends AppCompatActivity{

    DocumentBuilderFactory mFactory;
    DocumentBuilder mBuilder;
    Document mDom;
    Element mRoot;
    NodeList mItems;
    String mValor;
    int mContadorPorcentaje;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar);
    }

    public void OnSaveClick(View view){
        TextView name = (TextView) findViewById(R.id.editTextName);
        String fugitivoName = name.getText().toString().trim();
//        Toast.makeText(Agregar.this,TextUtils.isEmpty(fugitivoName)+"",Toast.LENGTH_SHORT).show();
        if(!TextUtils.isEmpty(fugitivoName)){
            DBProvider database = new DBProvider(this);
            database.InsertFugitivo(new Fugitivo(0,name.getText().toString(),"0",""));
            setResult(0);
            finish();
        }else{
            new AlertDialog.Builder(this)
                    .setTitle("Alerta")
                    .setMessage("Favor de capturar el nombre del fugitivo.")
                    .show();
        }
    }

    public void OnWebServiceClick(View view){
        final DBProvider db = new DBProvider(this);

        if(db.ContarFugitivos() == 0){
            NetServices apiCall = new NetServices(new OnTaskListener() {
                @Override
                public void OnTaskCompleted(String jsonString) {
                    try {
                        JSONArray array = new JSONArray(jsonString);
                        for(int i = 0; i< array.length(); i++){
                            JSONObject object = array.getJSONObject(i);
                            String nameFugitivo = object.optString("name","");
                            db.InsertFugitivo(new Fugitivo(0,nameFugitivo,"0",""));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }finally {
                     setResult(0);
                        finish();
                    }
                }

                @Override
                public void OnTaskError(int code, String message, String error) {
                    Toast.makeText(Agregar.this,"Ocurrio un problema con el Web Service",Toast.LENGTH_SHORT).show();
                }
            });
            apiCall.execute("Fugitivos");
        }else{
            Toast.makeText(this,"No se puede hacer la carga remota ya que se tiene al menos un fugitivo"
            +" en la base de datos",Toast.LENGTH_SHORT).show();
        }
    }

    public void importarXML(){
        try{
            InputStream inputStream = getResources().openRawResource(R.raw.fugitivos);
            mDom = mBuilder.parse(inputStream);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void retardo(){
        try{
            Thread.sleep(1000);
        }catch(InterruptedException e){
            e.printStackTrace();
        }
    }

    public void insertarFugitivo(String nameFugitivo){
        DBProvider db = new DBProvider(this);
        db.InsertFugitivo(new Fugitivo(0,nameFugitivo,"0",""));
    }

    public void OnXMLClick(View view){
        DBProvider db = new DBProvider(this);
        if(db.ContarFugitivos() <= 0){
            Button botonXML = (Button) findViewById(R.id.buttonAddXML);
            botonXML.setVisibility(View.GONE);
            Button botonSave = (Button) findViewById(R.id.buttonSave);
            botonSave.setVisibility(View.GONE);
            Button botonWebService = (Button) findViewById(R.id.buttonAddWS);
            botonWebService.setVisibility(View.GONE);

            final ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar);
            final TextView statusProgress = (TextView) findViewById(R.id.txtProgreso);

            try{
                mFactory = DocumentBuilderFactory.newInstance();
                mBuilder = mFactory.newDocumentBuilder();
                importarXML();
                mRoot = mDom.getDocumentElement();
                mItems = mRoot.getElementsByTagName("fugitivo");
            }catch (Exception e){
                e.printStackTrace();
            }

            new Thread(){
                @Override
                public void run() {
                    progressBar.post(new Runnable() {
                        @Override
                        public void run() {
                            progressBar.setProgress(0);
                        }
                    });
                    for(int i = 0; i < mItems.getLength(); i++){
                        mValor = mItems.item(i).getFirstChild().getNodeValue();

                        mContadorPorcentaje = (i+1)*mItems.getLength();
                        retardo();
                        insertarFugitivo(mValor);
                        progressBar.post(new Runnable() {
                            @Override
                            public void run() {
                                statusProgress.setText(getString(R.string.progreso) + " " + mContadorPorcentaje + "%");
                                progressBar.incrementProgressBy(10);
                            }
                        });
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),"Importacion de Fugitivos finalizada!",Toast.LENGTH_LONG).show();
                            setResult(0);
                            finish();
                        }
                    });
                }
            }.start();
        }else{
            Toast.makeText(getApplicationContext(),"No es posible solicitar carga via XML ya que se tiene al menos un fugitivo" +
                    "en la base de datos",Toast.LENGTH_LONG).show();
        }
    }
}
