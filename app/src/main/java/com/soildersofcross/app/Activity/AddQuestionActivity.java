package com.soildersofcross.app.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.soildersofcross.app.Model_Class.Question;
import com.soildersofcross.app.R;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


public class AddQuestionActivity extends AppCompatActivity {

    Button submit,addnewquest;
    ImageView deleteQuest,back1,back2;
    EditText Q1,Ans,c1,c2,c3,c4;

    LinearLayout allquestionslayout,addquestionlayout;

    ListView QuestionsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_question);


        back1 = findViewById(R.id.backbtn);
        back2 = findViewById(R.id.backbtn2);



        addnewquest = findViewById(R.id.addnewquestion);

        allquestionslayout = findViewById(R.id.allquestionslayout);
        addquestionlayout = findViewById(R.id.addquestionlayout);

        QuestionsList = findViewById(R.id.questionsList);

        deleteQuest = findViewById(R.id.deleteQuest);
        submit = findViewById(R.id.submitQuest);
        Q1 = findViewById(R.id.questiontitle);

        c1 = findViewById(R.id.choiceA);
        c2 = findViewById(R.id.choiceB);
        c3 = findViewById(R.id.choiceC);
        c4 = findViewById(R.id.choiceD);

        Ans = findViewById(R.id.answer);


        back1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        back2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                allquestionslayout.setVisibility(View.VISIBLE);
                addquestionlayout.setVisibility(View.GONE);
            }
        });

        List<Question> arrayItems;
        SharedPreferences sharedPreferences = this.getSharedPreferences("Question", Context.MODE_PRIVATE);
        String serializedObject = sharedPreferences.getString("Questions", null);
        List<String> Questions = new ArrayList<String>();

        if (serializedObject != null) {
            Gson gson = new Gson();
            Type type = new TypeToken<List<Question>>(){}.getType();
            arrayItems = gson.fromJson(serializedObject, type);

            for (int i=0;i<arrayItems.size();i++) {
                Questions.add("- "+arrayItems.get(i).getTitle());
            }

            Log.d("HERE", String.valueOf(arrayItems.size()));




            ArrayAdapter adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, Questions);
            QuestionsList.setAdapter(adapter);



            QuestionsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    allquestionslayout.setVisibility(View.GONE);
                    addquestionlayout.setVisibility(View.VISIBLE);


                    deleteQuest.setVisibility(View.VISIBLE);
 //                   submit.setVisibility(View.GONE);

                    Q1.setText(arrayItems.get(i).getTitle());
                    c1.setText(arrayItems.get(i).getChoices().get(0));
                    c2.setText(arrayItems.get(i).getChoices().get(1));
                    c3.setText(arrayItems.get(i).getChoices().get(2));
                    c4.setText(arrayItems.get(i).getChoices().get(3));
                    Ans.setText(arrayItems.get(i).getAnswer());

                    Q1.setEnabled(false);


                    deleteQuest.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view1) {
                            arrayItems.remove(i);
                            Toast.makeText(AddQuestionActivity.this, String.valueOf(arrayItems.size()), Toast.LENGTH_SHORT).show();
                            adapter.remove(adapter.getItem(i));
                            adapter.notifyDataSetChanged();
                            allquestionslayout.setVisibility(View.VISIBLE);
                            addquestionlayout.setVisibility(View.GONE);


                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            String json = gson.toJson(arrayItems);
                            editor.putString("Questions", json);
                            editor.commit();
                            Log.d("HERE", "onClick: added : "+json);
                        }
                    });
                }
            });


        }



        addnewquest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                allquestionslayout.setVisibility(View.GONE);
                addquestionlayout.setVisibility(View.VISIBLE);
                deleteQuest.setVisibility(View.GONE);
                submit.setVisibility(View.VISIBLE);

                Q1.setEnabled(true);


                Q1.setText("");
                c1.setText("");
                c2.setText("");
                c3.setText("");
                c4.setText("");
                Ans.setText("");
            }
        });






    }

    public void AddnewQuest(View v){


        List<Question> arrayItems;
        List<Question> Questions = new ArrayList<Question>();
        List<String> theChoices = new ArrayList<String>();
        SharedPreferences sharedPreferences = this.getSharedPreferences("Question", Context.MODE_PRIVATE);


        if(!Q1.getText().toString().isEmpty()
                && !c1.getText().toString().isEmpty()
                && !c2.getText().toString().isEmpty()
                && !c3.getText().toString().isEmpty()
                && !c4.getText().toString().isEmpty()
                && !Ans.getText().toString().isEmpty())
        {

            theChoices.add(c1.getText().toString());
            theChoices.add(c2.getText().toString());
            theChoices.add(c3.getText().toString());
            theChoices.add(c4.getText().toString());
            Question q1 = new Question(Q1.getText().toString(), theChoices, Ans.getText().toString());




            String serializedObject = sharedPreferences.getString("Questions", null);
            if (serializedObject != null) {
                Gson gson = new Gson();
                Type type = new TypeToken<List<Question>>() {
                }.getType();
                arrayItems = gson.fromJson(serializedObject, type);

                Log.d("HERE", "AddnewQuest: Already exists");


                for (int i=0;i<arrayItems.size();i++){
                    if(arrayItems.get(i).getTitle().equals(q1.getTitle())){
                        Toast.makeText(this, "updating", Toast.LENGTH_SHORT).show();
                        arrayItems.remove(i);

                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        String json = gson.toJson(arrayItems);
                        editor.putString("Questions", json);
                        editor.commit();
                        Log.d("HERE", "onClick: added : "+json);
                    }
                }

                arrayItems.add(q1);

                SharedPreferences.Editor editor = sharedPreferences.edit();
                String json = gson.toJson(arrayItems);
                editor.putString("Questions", json);
                editor.commit();
                Log.d("HERE", "onClick: added : "+json);
            }
            else {
                Log.d("HERE", "AddnewQuest: New Quest Array Created");

                Questions.add(q1);

                SharedPreferences.Editor editor = sharedPreferences.edit();
                Gson gson = new Gson();
                String json = gson.toJson(Questions);
                editor.putString("Questions", json);
                editor.commit();
                Log.d("HERE", "onClick: added : "+json);
            }


            Toast.makeText(this, "Done Created Success", Toast.LENGTH_SHORT).show();
//            new SweetAlertDialog(CreateCommentActivity.this, SweetAlertDialog.SUCCESS_TYPE)
//                    .setTitleText("Congratulations")
//                    .setContentText("Your Comment is Created successfully")
//                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
//                        @Override
//                        public void onClick(SweetAlertDialog sweetAlertDialog) {
//                            Intent intent = new Intent(CreateCommentActivity.this, TheFragmnetsActivity.class);
//                            startActivity(intent);
//                        }
//                    })
//                    .show();

            Intent intent = new Intent(AddQuestionActivity.this,MainActivity.class);
            startActivity(intent);
        }

        else {
            Toast.makeText(this, "Please Enter All Data", Toast.LENGTH_SHORT).show();
        }







        }

}