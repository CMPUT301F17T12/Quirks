package cmput301f17t12.quirks.Activities;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import cmput301f17t12.quirks.Controllers.ElasticSearchQuirkController;
import cmput301f17t12.quirks.Controllers.ElasticSearchUserController;

import cmput301f17t12.quirks.Enumerations.Day;
import cmput301f17t12.quirks.Helpers.HelperFunctions;
import cmput301f17t12.quirks.Models.Inventory;
import cmput301f17t12.quirks.Models.Quirk;
import cmput301f17t12.quirks.Models.QuirkList;
import cmput301f17t12.quirks.Models.User;
import cmput301f17t12.quirks.R;

public class AddQuirkActivity extends AppCompatActivity {

    private static final String TAG = "AddQuirkActivity" ;
    private String type;
    private String title;
    private String goal;
    public static String date2;
    private Date startDate;
    private String reason;
    private ArrayList<Day> occurence;
    private TextView SelectDate;
    private DatePickerDialog.OnDateSetListener SelectDateListener;
    public RadioButton radButMon;
    public RadioButton radButTue;
    public RadioButton radButWed;
    public RadioButton radButThur;
    public RadioButton radButFri;
    public RadioButton radButSat;
    public RadioButton radButSun;
    private AlertDialog.Builder builder;
    public String query;
    public String jestID;
    public User currentlylogged;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_quirk);
        Button Save_but = (Button)findViewById(R.id.SaveBut);
        Button Cancel_but = (Button)findViewById(R.id.Cancelbut);
        radButMon = (RadioButton)findViewById(R.id.radioButMonday);
        radButTue = (RadioButton)findViewById(R.id.radioButTuesday);
        radButWed = (RadioButton)findViewById(R.id.radioButWednesday);
        radButThur = (RadioButton)findViewById(R.id.radioButThursday);
        radButFri = (RadioButton)findViewById(R.id.radioButFriday);
        radButSat = (RadioButton)findViewById(R.id.radioButSaturday);
        radButSun = (RadioButton)findViewById(R.id.radioButSunday);
        SelectDate = (TextView)findViewById(R.id.textViewSelDate);
      
        jestID = "AV-xx8ahi8-My2t7XP4j";
        ElasticSearchUserController.GetSingleUserTask getSingleUserTask = new ElasticSearchUserController.GetSingleUserTask();
        getSingleUserTask.execute(jestID);

        currentlylogged = HelperFunctions.getUserObject(jestID);

        if (currentlylogged != null) {
            QuirkList quirks = currentlylogged.getQuirks();
        }


        SelectDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Calendar cal  = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);
               DatePickerDialog dialog = new DatePickerDialog(AddQuirkActivity.this, android.R.style.Theme_Holo_Dialog_MinWidth,SelectDateListener,year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();

           }

        });
        SelectDateListener = new DatePickerDialog.OnDateSetListener(){
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                date2 = month + "/" + day + "/" + year;
                SelectDate.setText(date2);
                Log.d(TAG, "onDateSet: the date is now   " + date2);
                 startDate = new Date(year,month,day);

            }
        };

    }

    // TODO:
    // Save button clicked -> create and save new quirk and return to the previous activity

    public void saveButtonClicked(View v){

        type = ((EditText)findViewById(R.id.editTextType)).getText().toString();
        title = ((EditText)findViewById(R.id.editTitle)).getText().toString();
        reason = ((EditText)findViewById(R.id.editTextReason)).getText().toString();
        goal = ((EditText)findViewById(R.id.editTextGoal)).getText().toString();
        ArrayList<Day> QuirkOccurence = new ArrayList<Day>();



        if(type.equals("")||(title.equals(""))||(goal.equals(""))||reason.equals("")){
          // emptyFieldsDialog();
        }
        /*
        else if (title.length() > 20 || reason.length() > 30){
            titleReasonLengthDialog();
        }
        */

        else {

            QuirkOccurence = occurenceItemSelected();
            //Intent intent = new Intent();
            Date DatetoTest = new Date();
            int Quirk_goal = Integer.parseInt(goal);

            //The user in here should be the one query from db
            Log.d(TAG, "saveButtonClicked: The dateinput is now  " + startDate);
            Quirk QuirkCreated = new Quirk(title,type,startDate,QuirkOccurence,Quirk_goal,currentlylogged.getUsername());
            Log.d(TAG, "saveButtonClicked: The QuirkCreated is the title is  " + QuirkCreated.getTitle() );
            Log.d(TAG, "saveButtonClicked: The QuirkCreated is the type is  " + QuirkCreated.getType() );
            Log.d(TAG, "saveButtonClicked: The QuirkCreated is the Date is  " + QuirkCreated.getDate() );
            Log.d(TAG, "saveButtonClicked: The QuirkCreated is the Occurence Date is  " + QuirkCreated.getOccDate());
            Log.d(TAG, "saveButtonClicked: The QuirkCreated is the Goal is  " + QuirkCreated.getGoalValue());

            currentlylogged.addQuirk(QuirkCreated);
            ElasticSearchUserController.UpdateUserTask updateUserTask = new ElasticSearchUserController.UpdateUserTask();
            updateUserTask.execute(currentlylogged);


            /*
            String query = user.getId();
            ElasticSearchUserController.GetSingleUserTask getSingleUserTask = new ElasticSearchUserController.GetSingleUserTask();
            getSingleUserTask.execute(query);
            user.addQuirk(QuirkCreated);
            ElasticSearchUserController.UpdateUserTask updateUserTask = new ElasticSearchUserController.UpdateUserTask();
            updateUserTask.execute(user);

            //intent.putExtra("Quirk_Created", QuirkCreated);
            setResult(1, intent);
            */

            finish();
        }

    }

    // TODO:
    // Cancel button clicked -> disregard selected options and return to the previous activity
    public void cancelButtonClicked(View v){
        Intent intent = new Intent(AddQuirkActivity.this,MainActivity.class);
    //    intent.putExtra("Cancel",1);
     //   setResult(1,intent);
        finish();
    }

    // TODO:
    // an occurence item was sl
    private ArrayList<Day> occurenceItemSelected(){
        ArrayList<Day> Day = new ArrayList<Day>();
        if(radButMon.isChecked()){
            Day.add(cmput301f17t12.quirks.Enumerations.Day.MONDAY);
        }
        if(radButTue.isChecked()){
            Day.add(cmput301f17t12.quirks.Enumerations.Day.TUESDAY);
        }
        if(radButWed.isChecked()){
            Day.add(cmput301f17t12.quirks.Enumerations.Day.WEDNESDAY);
        }
        if(radButThur.isChecked()){
            Day.add(cmput301f17t12.quirks.Enumerations.Day.THURSDAY);
        }
        if(radButFri.isChecked()){
            Day.add(cmput301f17t12.quirks.Enumerations.Day.FRIDAY);
        }
        if(radButSat.isChecked()){
            Day.add(cmput301f17t12.quirks.Enumerations.Day.SATURDAY);
        }
        if(radButSun.isChecked()){
            Day.add(cmput301f17t12.quirks.Enumerations.Day.SUNDAY);
        }
     return Day;
    }


    public void emptyFieldsDialog() {

        builder.setMessage("All blanks must be filled out.")
                .setNegativeButton("Return", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        ;
                    }
                })
                .setTitle("Missing Fields");

        builder.show();
    }

    public void titleReasonLengthDialog() {

        builder.setMessage("Title can be no longer than 20 characters. Reason can be no longer than 30 characters.")
                .setNegativeButton("Return", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        ;
                    }
                })
                .setTitle("Title/Reason too long");

        builder.show();
    }

}
