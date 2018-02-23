package grupp07.bars;

import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class BarInfoActivity extends AppCompatActivity {





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bar_info);
        Resources res = getResources();
        Bundle extras = getIntent().getExtras();
        int FROM_ACTIVITY = extras.getInt("fromfragment");
        ImageView imageView = (ImageView) findViewById(R.id.barinfoimg);
        String imgsrc = "imgcard" + FROM_ACTIVITY;
        int resID = res.getIdentifier(imgsrc, "drawable", "grupp07.bars");
        imageView.setImageResource(resID);

        TextView textView = (TextView) findViewById(R.id.shortinfo);
        String stres= "section_format" + FROM_ACTIVITY;

        int strID = res.getIdentifier(stres,"string","grupp07.bars");
        textView.setText(getString(strID));

        TextView textView2 = (TextView) findViewById(R.id.biginfo);
        String fetstring="fetstring"+FROM_ACTIVITY;
        int strID2 = res.getIdentifier(fetstring,"string","grupp07.bars");
        textView2.setText(getString(strID2));
        textView2.setMovementMethod(LinkMovementMethod.getInstance());

        ImageView mapButton = (ImageView) findViewById(R.id.locationlogo);
        mapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(BarInfoActivity.this, MapsActivity.class);
                startActivity(i);
            }
        });

    }
}
