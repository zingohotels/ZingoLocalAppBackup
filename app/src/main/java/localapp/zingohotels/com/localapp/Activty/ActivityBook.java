package localapp.zingohotels.com.localapp.Activty;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import localapp.zingohotels.com.localapp.ListOfEventsActivity;
import localapp.zingohotels.com.localapp.Model.ActivityModel;
import localapp.zingohotels.com.localapp.Model.Bookings;
import localapp.zingohotels.com.localapp.R;
import localapp.zingohotels.com.localapp.Util.Constants;
import localapp.zingohotels.com.localapp.Util.PreferenceHandler;

public class ActivityBook extends AppCompatActivity {

    ImageView mAdultAdd,mAdultRem,mChildAdd,mChildRem;
    TextView mChildCount,mAdultCount,mBookDate,mBookTime,mTotalBookingAmount,mBookingAdultPrice;
    Button mNext;


    int childCount = 0,adultCount = 1;
    int bookingAmount = 0;
    ActivityModel activity;
    SimpleDateFormat simpleDateFormat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        setTitle("Booking");

        final Bundle bundle = getIntent().getExtras();
        if(bundle!=null){
            activity = (ActivityModel)bundle.getSerializable(Constants.ACTIVITY);
        }

        mAdultAdd = (ImageView)findViewById(R.id.adult_add);
        mAdultRem = (ImageView)findViewById(R.id.adult_remove);
        mChildAdd = (ImageView)findViewById(R.id.child_add);
        mChildRem = (ImageView)findViewById(R.id.child_remove);
        mChildCount = (TextView)findViewById(R.id.book_child_count);
        mAdultCount = (TextView)findViewById(R.id.book_adult_count);
        mBookDate = (TextView)findViewById(R.id.book_date);
        mBookTime = (TextView)findViewById(R.id.book_time);
        mBookingAdultPrice = (TextView)findViewById(R.id.booking_adult_price);
        mTotalBookingAmount = (TextView)findViewById(R.id.book_total_amount);
        mNext = (Button) findViewById(R.id.next_btn);

        //bookingAmount = Integer.parseInt(mTotalBookingAmount.getText().toString().replace("₹","").trim());
        mAdultAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // int adult = Integer.parseInt(mAdultCount.getText().toString());
                if(adultCount<10){
                    mAdultCount.setText((adultCount+1)+"");
                    adultCount++;
                    mTotalBookingAmount.setText("₹ "+(bookingAmount*adultCount)+"");
                }else{
                    Toast.makeText(ActivityBook.this,"You can only add 10 adults per booking",Toast.LENGTH_LONG).show();
                }


            }
        });
        mAdultRem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // int adult = Integer.parseInt(mAdultCount.getText().toString());
                if(adultCount>1){
                    mAdultCount.setText((adultCount-1)+"");
                    adultCount--;
                    mTotalBookingAmount.setText("₹ "+(bookingAmount*adultCount)+"");
                }else{
                    Toast.makeText(ActivityBook.this,"One adult is minimum for booking",Toast.LENGTH_LONG).show();
                }


            }
        });


        mNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /*Intent summary = new Intent(ActivityBook.this,BookingDetailsEnter.class);
                startActivity(summary);*/
                validateFields();
            }
        });

        mBookDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDatePicker(mBookDate);
            }
        });

        mBookTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openTimePicker(mBookTime);
            }
        });

        if(activity != null)
        {
            mTotalBookingAmount.setText("₹ "+activity.getSellingPrice());
            mBookingAdultPrice.setText("₹ "+activity.getSellingPrice());
            bookingAmount = activity.getSellingPrice();
        }
    }

    private void validateFields() {

        simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy");
        String date = mBookDate.getText().toString();
        String time = mBookTime.getText().toString();

        if(date == null || date.isEmpty())
        {
            mBookDate.setError("Please Select Date");
            mBookDate.requestFocus();
        }
        else if(time == null || time.isEmpty())
        {
            mBookTime.setError("Please Select Time");
            mBookTime.requestFocus();
        }
        else
        {
            Bookings bookings = new Bookings();
            bookings.setBookingDate(simpleDateFormat.format(new Date()));
            bookings.setBookingTimeSlot(time);
            bookings.setActivityDate(date);
            bookings.setProfileId(PreferenceHandler.getInstance(ActivityBook.this).getUserId());
            bookings.setTravellerId(PreferenceHandler.getInstance(ActivityBook.this).getUserId());
            bookings.setTotalAmount(Integer.parseInt(mTotalBookingAmount.getText().toString().replace("₹ ","").trim()));
            bookings.setNoOfAdults(Integer.parseInt(mAdultCount.getText().toString()));
            bookings.setActivities(activity);
            bookings.setSellRate(activity.getSellingPrice());
            bookings.setDiscount((int)activity.getDiscountPercentage());
            bookings.setDiscountAmount(Integer.parseInt(mAdultCount.getText().toString()) * activity.getDiscountPrice());


            Intent summary = new Intent(ActivityBook.this,BookingDetailsEnter.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable(Constants.ACTIVITYBOOKING,bookings);
            summary.putExtras(bundle);
            startActivity(summary);
        }
    }

    public void openDatePicker(final TextView tv) {
        // Get Current Date
        final Calendar c = Calendar.getInstance();
       int mYear  = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay   = c.get(Calendar.DAY_OF_MONTH);
        //launch datepicker modal
        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                        String from,to;
                        Log.d("Date", "DATE SELECTED "+dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                        Calendar newDate = Calendar.getInstance();
                        newDate.set(year,monthOfYear,dayOfMonth);


                        String date1 = (monthOfYear + 1)  + "/" + dayOfMonth + "/" + year;
                        //String date2 = (monthOfYear + 1)  + "/" + (dayOfMonth+1) + "/" + year;

                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy");


                        if (tv.equals(mBookDate)){

                            try {
                                Date fdate = simpleDateFormat.parse(date1);
                               // Date tdate = simpleDateFormat.parse(date2);

                                from = simpleDateFormat.format(fdate);
                               // to = simpleDateFormat.format(tdate);

                                System.out.println("To = "+from);
                                tv.setText(from);
                                //mTo.setText(to);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            //


                        }/*else {
                            //to = date1;
                            try {
                                Date tdate = simpleDateFormat.parse(date1);
                                to = simpleDateFormat.format(tdate);
                                System.out.println("To = "+to);
                                tv.setText(to);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                        }*/



                    }
                }, mYear, mMonth, mDay);


        datePickerDialog.show();

    }

    public void openTimePicker(final TextView tv){

        final Calendar mcurrentTime = Calendar.getInstance();
        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mcurrentTime.get(Calendar.MINUTE);
        TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(ActivityBook.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
                //tv.setText( selectedHour + ":" + selectedMinute);



                System.out.println("time==="+mcurrentTime);
                if (tv.equals(mBookTime)){
                   String ft = String.format("%02d:%02d:00",hourOfDay,minute);
                    boolean isPM =(hourOfDay >= 12);
                    mBookTime.setText( String.format("%02d:%02d %s ", (hourOfDay == 12 || hourOfDay == 0) ? 12 : hourOfDay % 12, minute, isPM ? "PM" : "AM"));
                }/*else if (tv.equals(cout_time_tv)) {
                    tt = String.format("%02d:%02d:00",hourOfDay,minute);
                    boolean isPM =(hourOfDay >= 12);
                    cout_time_tv.setText( String.format("%02d:%02d %s", (hourOfDay == 12 || hourOfDay == 0) ? 12 : hourOfDay % 12, minute, isPM ? "PM" : "AM"));

                }*/
            }
        }, hour, minute, false);//Yes 24 hour time
        mTimePicker.show();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id)
        {
            case android.R.id.home:
                ActivityBook.this.finish();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

}
