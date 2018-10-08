package kyh.org.train.view.wigets;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

public class Progressbar01MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button bRun , bCancel;
    TextView textView;
    ProgressBar progressbar;
    BackgroundTask task;
    int value;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progressbar01_main);

        //xml내의 뷰들참조
        bRun = (Button) findViewById(R.id.button_run);
        bCancel = (Button) findViewById(R.id.button_cancel);
        textView = (TextView) findViewById(R.id.textview_1);
        progressbar = (ProgressBar) findViewById(R.id.progressbar_horizontal);

        //버트이벤
        bRun.setOnClickListener(this);
        bCancel.setOnClickListener(this);



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main,menu);
        return true;
    }

    @Override
    public void onClick(View view) {
        if(view == bRun) {
            task = new BackgroundTask();
            task.execute(100);
        } else if(view == bCancel) {
            task.cancel(true);
        }
    }
    //비동기식 작업 구현
    class BackgroundTask extends AsyncTask<Integer,Integer,Integer> {

        @Override
        protected void onPreExecute() {
            value = 0;
            progressbar.setProgress(value);
        }

        @Override
        protected Integer doInBackground(Integer... integers) {
            while (isCancelled() == false) {
                value++;
                if(value >= 100){
                    break;
                } else {
                    publishProgress(value);
                }
                try {
                    Thread.sleep(300);

                } catch (InterruptedException ex) { }
            }
            return value;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            progressbar.setProgress(values[0].intValue());
            textView.setText("진행율 " + values[0].toString() + "%");
        }

        @Override
        protected void onPostExecute(Integer integer) {
            progressbar.setProgress(0);
            textView.setText("완료");
        }

        @Override
        protected void onCancelled(Integer integer) {
            progressbar.setProgress(0);
            textView.setText("취소");
        }
    }
}
