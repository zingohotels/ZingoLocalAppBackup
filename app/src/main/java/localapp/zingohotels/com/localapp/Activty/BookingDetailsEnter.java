package localapp.zingohotels.com.localapp.Activty;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import localapp.zingohotels.com.localapp.Model.Bookings;
import localapp.zingohotels.com.localapp.R;
import localapp.zingohotels.com.localapp.Util.Constants;
import localapp.zingohotels.com.localapp.Util.PreferenceHandler;
import localapp.zingohotels.com.localapp.Util.Util;

public class BookingDetailsEnter extends AppCompatActivity {

    RadioButton mPerson,mBusiness;
    LinearLayout mPersonLay,mBusinessLay;
    TextView mBookingDate,mBookingTime,mBookingDuration,mPersonCount,mChildCount,mDisplayPrice,mDiscountPrice,mTotalPrice,mTotal;
    ImageView mActivityImage;
    EditText userName,mUserPhoneNumber,mUserEmail;

    Bookings bookings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_details_enter);

        mPerson = (RadioButton)findViewById(R.id.booking_personal);
        mBusiness = (RadioButton)findViewById(R.id.booking_company);
        mPersonLay = (LinearLayout)findViewById(R.id.personal_layout);
        mBusinessLay = (LinearLayout)findViewById(R.id.business_layout);
        mBookingDate = (TextView) findViewById(R.id.booking_detils_date);
        mBookingTime = (TextView) findViewById(R.id.booking_detils_time);
        mBookingDuration = (TextView) findViewById(R.id.booking_details_duration);
        mPersonCount = (TextView) findViewById(R.id.person_count);
        mChildCount = (TextView) findViewById(R.id.child_count);
        mDisplayPrice = (TextView) findViewById(R.id.display_price);
        mDiscountPrice = (TextView) findViewById(R.id.discount_price);
        mTotalPrice = (TextView) findViewById(R.id.booking_total);
        mTotal = (TextView) findViewById(R.id.total_amount);
        userName = (EditText) findViewById(R.id.booking_user_name);
        mUserPhoneNumber = (EditText) findViewById(R.id.booking_user_phone_number);
        mUserEmail = (EditText) findViewById(R.id.booking_user_email);
        mActivityImage = (ImageView) findViewById(R.id.activity_name_image);

        mPerson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPersonLay.setVisibility(View.VISIBLE);
                mBusinessLay.setVisibility(View.GONE);
            }
        });

        mBusiness.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBusinessLay.setVisibility(View.VISIBLE);
                mPersonLay.setVisibility(View.GONE);
            }
        });

        Bundle bundle = getIntent().getExtras();
        if(bundle != null)
        {
            bookings = (Bookings) bundle.getSerializable(Constants.ACTIVITYBOOKING);
            /*System.out.println(bookings.getActivities().getActivityName());
            System.out.println(bookings.getBookingDate());
            System.out.println(bookings.getTotalAmount());*/
            System.out.println(bookings.getBookingTimeSlot());
            fillFields(bookings);
        }


    }

    private void fillFields(Bookings bookings) {

        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE, dd MMM");
            mBookingDate.setText(simpleDateFormat.format(new SimpleDateFormat("MM/dd/yyyy").parse(bookings.getActivityDate())));
            mBookingTime.setText(bookings.getBookingTimeSlot());
            mPersonCount.setText(bookings.getNoOfAdults()+" Adults");
            if(bookings.getActivities().getActivityImages().size() != 0) {
                mActivityImage.setImageBitmap(Util.convertToBitMap(bookings.getActivities().getActivityImages().get(0).getImages()));
            }
            mDisplayPrice.setText("₹ "+bookings.getSellRate());
            mDiscountPrice.setText("₹ "+bookings.getActivities().getDiscountPrice());
            mTotalPrice.setText("₹ "+bookings.getTotalAmount());
            mTotal.setText("₹ "+bookings.getTotalAmount());
            userName.setText(PreferenceHandler.getInstance(BookingDetailsEnter.this).getUserFullName());
            mUserEmail.setText(PreferenceHandler.getInstance(BookingDetailsEnter.this).getUserEmail());
            mUserPhoneNumber.setText(PreferenceHandler.getInstance(BookingDetailsEnter.this).getUserPhone());

            //SimpleDateFormat _12HourSDF = new SimpleDateFormat("hh:mm a");

        }
        catch (ParseException ex)
        {
            ex.printStackTrace();
            System.out.println(ex.toString());
        }
    }
}
