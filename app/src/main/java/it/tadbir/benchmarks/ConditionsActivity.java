package it.tadbir.benchmarks;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import java.util.Locale;

public class ConditionsActivity extends AppCompatActivity {

    private TextView txt;
    private int count;
    private AsyncTest test;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conditions);

        txt = (TextView) findViewById(R.id.txtCondResult);


        findViewById(R.id.btnCondRun).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count = Integer.parseInt(((TextView) findViewById(R.id.txtCondCount)).getText().toString());

                test = new AsyncTest();
                test.execute();
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (test != null)
            test.cancel(true);
    }


    class AsyncTest extends AsyncTask {
        private final int rounds = 100;

        private long[] tEndIf = new long[rounds];
        private long[] tEndSwitch = new long[rounds];
        private long[] tEndIif = new long[rounds];

        @Override
        protected Object doInBackground(Object[] params) {
            int dummy = 0;

            for (int r = 0; r < rounds && !isCancelled(); r++) {
                publishProgress(String.format(Locale.US, "Round %d started.\n", r + 1));

                //if-else test
                long tStartIf = System.currentTimeMillis();
                for (int i = 0; i <= count && !isCancelled(); i++) {
                    if (dummy == 0) {
                        dummy = 1;
                    } else {
                        dummy = 0;
                    }
                }
                tEndIf[r] = System.currentTimeMillis() - tStartIf;


                //switch test
                long tStartSwitch = System.currentTimeMillis();
                for (int i = 0; i <= count && !isCancelled(); i++) {
                    switch (dummy) {
                        case 0:
                            dummy = 1;
                            break;

                        default:
                            dummy = 0;
                    }
                }
                tEndSwitch[r] = System.currentTimeMillis() - tStartSwitch;

                //ternary test
                long tStartIif = System.currentTimeMillis();
                for (int i = 0; i <= count && !isCancelled(); i++) {

                    dummy = dummy == 0 ? 1 : 0;

                }
                tEndIif[r] = System.currentTimeMillis() - tStartIif;
            }

            return null;
        }

        @Override
        protected void onProgressUpdate(Object[] values) {
            super.onProgressUpdate(values);

            txt.append((String) values[0]);
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);

            long meanIf = mean(tEndIf), meanSwitch = mean(tEndSwitch), meanIif = mean(tEndIif);

            txt.append(String.format(Locale.US,
                    "Elapsed time for %d iterations:\nIF:\t\t\t\t%dms\nSwitch:\t\t%dms\nIif:\t\t\t\t%dms\n",
                    count, meanIf, meanSwitch, meanIif));

            long max = Math.max(Math.max(meanIf, meanSwitch), meanIif);

            txt.append(String.format(Locale.US,
                    "IF faster than Max:\t\t\t%%%d\nSwitch faster than Max:\t%%%d\nIif faster than Max:\t\t\t%%%d\n",
                    (max - meanIf) * 100 / max, (max - meanSwitch) * 100 / max, (max - meanIif) * 100 / max));
        }

        public long mean(long[] p) {
            double sum = 0;
            for (int i = 0; i < p.length; i++) {
                sum += p[i];
            }
            return (long) Math.floor(sum / p.length);
        }
    }


}
