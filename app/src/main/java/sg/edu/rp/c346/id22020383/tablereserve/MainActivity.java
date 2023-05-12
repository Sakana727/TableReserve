package sg.edu.rp.c346.id22020383.tablereserve;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.time.LocalDate;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    TextView nameTV;
    EditText nameET;

    TextView mobileTV;
    EditText mobileET;

    TextView sizeofgroupTV;
    EditText sizeofgroupET;

    DatePicker date;
    TimePicker time;

    CheckBox smoking;

    Button confirm;
    Button reset;

    TextView outputTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nameTV = findViewById(R.id.nameTV);
        nameET = findViewById(R.id.nameET);
        mobileTV = findViewById(R.id.mobileTV);
        mobileET = findViewById(R.id.mobileET);
        sizeofgroupTV = findViewById(R.id.amountTV);
        sizeofgroupET = findViewById(R.id.amountET);
        date = findViewById(R.id.datePicker);
        time = findViewById(R.id.timePicker);
        smoking = findViewById(R.id.smokingcb);
        confirm = findViewById(R.id.confirmbtn);
        reset = findViewById(R.id.resetbtn);
        outputTV = findViewById(R.id.outputTV);

        date.updateDate(2023, 5, 1);
        time.setCurrentHour(19);
        time.setCurrentMinute(30);

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = nameET.getText().toString().trim();
                String mobile = mobileET.getText().toString().trim();
                String size = sizeofgroupET.getText().toString().trim();

                if (name.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Please enter a Name", Toast.LENGTH_LONG).show();
                } else if (mobile.isEmpty() || mobile.length() != 8) {
                    Toast.makeText(MainActivity.this, "Please enter a valid Mobile Number", Toast.LENGTH_LONG).show();
                } else if (size.isEmpty() || size.equals("0")) {
                    Toast.makeText(MainActivity.this, "Please enter a valid Group size", Toast.LENGTH_LONG).show();
                } else {
                    String sittingArea = smoking.isChecked() ? "Smoking Area" : "Non-Smoking Area";
                    String text = "Reservation Successful" + "\n" + "Name: " + name + "\n" + "Contact Number: " + mobile + "\n" + "Group size: " + size + "\n" +
                            "Sitting Area: " + sittingArea + "\n" + "Date: " + date.getDayOfMonth() + "/" + date.getMonth() + "/"
                            + date.getYear() + "\n" + "Time: " + time.getCurrentHour() + ":" + time.getCurrentMinute();
                    Toast.makeText(MainActivity.this, text, Toast.LENGTH_LONG).show();
                }

            }
        });

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                date.updateDate(2023, 6, 1);
                time.setCurrentHour(19);
                time.setCurrentMinute(30);
                nameET.setText("");
                sizeofgroupET.setText("");
                mobileET.setText("");
                smoking.setChecked(true);

            }
        });

        time.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                if (time.getCurrentHour() < 8 || time.getCurrentHour() >= 21) {
                    Toast.makeText(MainActivity.this, "You can only reserve slots between 8AM to 8:59PM", Toast.LENGTH_SHORT).show();
                }
            }
        });

        date.init(date.getYear(), date.getMonth(), date.getDayOfMonth(), (view, year, monthOfYear, dayOfMonth) -> {
            Calendar selectedDateTime = Calendar.getInstance();
            selectedDateTime.set(year, monthOfYear, dayOfMonth);

            Calendar currentDateTime = Calendar.getInstance();
            Calendar maxDateTime = Calendar.getInstance();
            maxDateTime.add(Calendar.MONTH, 6);

            if (selectedDateTime.before(currentDateTime)) {
                Toast.makeText(MainActivity.this, "Selected date is in the past", Toast.LENGTH_LONG).show();
            } else if (selectedDateTime.after(maxDateTime)) {
                Toast.makeText(MainActivity.this, "Selected date is more than 6 months in the future", Toast.LENGTH_LONG).show();
            }
        });

    }
}