package localapp.zingohotels.com.localapp.Activty;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import localapp.zingohotels.com.localapp.Model.PackageDetails;
import localapp.zingohotels.com.localapp.R;
import localapp.zingohotels.com.localapp.Util.Constants;

public class PackageDetailsActivity extends AppCompatActivity {
    TableLayout t1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_package_details);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        t1 = (TableLayout) findViewById(R.id.acivity_package_description);
        setTitle("Package Details");

        Bundle bundle = getIntent().getExtras();
        if(bundle != null)
        {
            PackageDetails packageDetails = (PackageDetails) bundle.getSerializable(Constants.PACKAGE_DESCRIPTION);
            if(packageDetails != null)
            {
                setviews(packageDetails);
            }
        }
        //setviews(null);
    }

    private void setviews(PackageDetails packageDetails) {

        String descr = packageDetails.getDescription();

        String[] splitedString = descr.split(",");

        if(splitedString.length != 0)
        {
            for (int i=0;i<splitedString.length;i++)
            {
                if(splitedString[i] != null && !splitedString[i].isEmpty())
                {
                    createView(splitedString[i]);
                }
                //createView("");
            }
        }
    }

    private void createView(String s) {

        TableRow tr_head = new TableRow(this);
       // tr_head.setId(10);
        TableRow.LayoutParams params = new TableRow.LayoutParams(
                TableRow.LayoutParams.FILL_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT);
        tr_head.setBackgroundColor(Color.GRAY);
        tr_head.setLayoutParams(params);

        TextView label_date = new TextView(this);
        //label_date.setId(20);
        label_date.setText("\u2022");

        label_date.setGravity(Gravity.CENTER);

        label_date.setTextColor(Color.WHITE);
        label_date.setPadding(5, 5, 5, 5);
        //label_date.

        tr_head.addView(label_date);// add the column to the table row here
        setMargins(label_date,10,5,10,5);

        TextView label_weight_kg = new TextView(this);
        //label_weight_kg.setId(21);// define id that must be unique
        label_weight_kg.setText(s);
        label_weight_kg.setGravity(Gravity.CENTER);// set the text for the header
        label_weight_kg.setTextColor(Color.WHITE); // set the color
        setMargins(label_weight_kg,10,5,10,5);
        label_weight_kg.setPadding(5, 5, 5, 5); // set the padding (if required)
        tr_head.addView(label_weight_kg); // add the column to the table row here

        t1.addView(tr_head, new TableLayout.LayoutParams(
                TableLayout.LayoutParams.FILL_PARENT,
                TableLayout.LayoutParams.WRAP_CONTENT));
    }
    private void setMargins (View view, int left, int top, int right, int bottom) {
        if (view.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
            p.setMargins(left, top, right, bottom);
            view.requestLayout();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id)
        {
            case android.R.id.home:
                PackageDetailsActivity.this.finish();
                break;
        }


        return super.onOptionsItemSelected(item);
    }
}
