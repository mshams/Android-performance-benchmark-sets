package it.tadbir.benchmarks;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

// Author: Mohammad Shams
// Website: http://www.mshams.ir


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btnMainHandlers).setOnClickListener(onClick);
        findViewById(R.id.btnMainConditions).setOnClickListener(onClick);
    }

    private View.OnClickListener onClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v == findViewById(R.id.btnMainHandlers)) {
                startActivity(new Intent(getBaseContext(), HandlersActivity.class));
            } else if (v == findViewById(R.id.btnMainConditions)) {
                startActivity(new Intent(getBaseContext(), ConditionsActivity.class));
            }
        }
    };
}
