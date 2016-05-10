package it.tadbir.benchmarks;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.Locale;

public class HandlersActivity extends AppCompatActivity {

    private TextView txt;
    private ViewGroup grp;
    private int count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handlers);

        txt = (TextView) findViewById(R.id.txtHandResult);
        grp = (ViewGroup) findViewById(R.id.grpHandlers);


        findViewById(R.id.btnHandSingle).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count = Integer.parseInt(((TextView) findViewById(R.id.txtHandCount)).getText().toString());

                for (int i = 0; i <= count; i++) {
                    Button btn = new Button(getBaseContext());
                    btn.setText("Button" + String.valueOf(i));

                    View.OnClickListener onClick = new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            txt.append("\n" + ((Button) v).getText() + " Clicked!");
                        }
                    };

                    btn.setOnClickListener(onClick);
                    grp.addView(btn);
                }

                txt.append(String.format(Locale.US, "Add %d buttons, Single Event handler\n", count));
            }
        });

        findViewById(R.id.btnHandMulti).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count = Integer.parseInt(((TextView) findViewById(R.id.txtHandCount)).getText().toString());

                for (int i = 0; i <= count; i++) {
                    Button btn = new Button(getBaseContext());
                    btn.setText("Button" + String.valueOf(i));

                    btn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            txt.append("\n" + ((Button) v).getText() + " Clicked!");
                        }
                    });
                    grp.addView(btn);
                }

                txt.append(String.format(Locale.US, "Add %d buttons, Multiple Event handlers\n", count));
            }
        });

        findViewById(R.id.btnHandClear).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                grp.removeAllViews();
                System.gc();
            }
        });

        findViewById(R.id.btnHandMem).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addMemoryUsage();
            }
        });
    }

    private void addMemoryUsage() {
        txt.append(String.format(Locale.US, "Used Memory: %d\n", getUsedMemorySize()));

    }

    public static long getUsedMemorySize() {
        long freeSize = 0L;
        long totalSize = 0L;
        long usedSize = -1L;
        try {
            Runtime info = Runtime.getRuntime();
            freeSize = info.freeMemory();
            totalSize = info.totalMemory();
            usedSize = totalSize - freeSize;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return usedSize;
    }
}
