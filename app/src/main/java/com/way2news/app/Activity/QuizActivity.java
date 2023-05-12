package com.way2news.app.Activity;

import androidx.appcompat.app.AppCompatActivity;

import com.way2news.app.Model_Class.Question;
import com.way2news.app.R;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class QuizActivity extends AppCompatActivity {

    Button c1,c2,c3,c4;
    TextView Q;
    int i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        i=0;


        Q = findViewById(R.id.triviaQuestion);
        c1=findViewById(R.id.buttonA);
        c2=findViewById(R.id.buttonB);
        c3=findViewById(R.id.buttonC);
        c4=findViewById(R.id.buttonD);

        List<String> theChoices = new ArrayList<String>();

        if(i==0) {
            theChoices.add("42.195 kilometres");
            theChoices.add("32.395 kilometres");
            theChoices.add("54.125 kilometres");
            theChoices.add("66.495 kilometres");
            Question q1 = new Question("How long is a marathon?", theChoices, "42.195 kilometres");


            if (!q1.equals(null) || !q1.equals("")) {
                Log.d("TAG", "onCreate: " + q1);

                Q.setText(q1.getTitle());
                c1.setText(q1.getChoices().get(0));
                c2.setText(q1.getChoices().get(1));
                c3.setText(q1.getChoices().get(2));
                c4.setText(q1.getChoices().get(3));
            }

            if (!q1.getAnswer().isEmpty()) {
                c1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (q1.getAnswer().equals(c1.getText().toString())) {

                            Drawable img = c1.getContext().getResources().getDrawable(R.drawable.correctans);
                            //img.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);
                            Bitmap bitmap = ((BitmapDrawable) img).getBitmap();
                            Drawable d = new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(bitmap, 100, 100, true));
                            c1.setCompoundDrawablesWithIntrinsicBounds(null, null, d, null);
                            c1.setPadding(0, 0, 25, 0);


                            //eText(QuizActivity.this, "here 1", Toast.LENGTH_SHORT).show();
                        } else {
                            Drawable img = c1.getContext().getResources().getDrawable(R.drawable.wrongans);
                            //img.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);
                            Bitmap bitmap = ((BitmapDrawable) img).getBitmap();
                            Drawable d = new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(bitmap, 100, 100, true));
                            c1.setCompoundDrawablesWithIntrinsicBounds(null, null, d, null);
                            c1.setPadding(0, 0, 25, 0);

                        }
                    }
                });

                c2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (q1.getAnswer().equals(c2.getText().toString())) {

                            Drawable img = c2.getContext().getResources().getDrawable(R.drawable.correctans);
                            //img.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);
                            Bitmap bitmap = ((BitmapDrawable) img).getBitmap();
                            Drawable d = new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(bitmap, 100, 100, true));
                            c2.setCompoundDrawablesWithIntrinsicBounds(null, null, d, null);
                            c2.setPadding(0, 0, 25, 0);


                            //eText(QuizActivity.this, "here 2", Toast.LENGTH_SHORT).show();
                        } else {
                            Drawable img = c2.getContext().getResources().getDrawable(R.drawable.wrongans);
                            //img.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);
                            Bitmap bitmap = ((BitmapDrawable) img).getBitmap();
                            Drawable d = new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(bitmap, 100, 100, true));
                            c2.setCompoundDrawablesWithIntrinsicBounds(null, null, d, null);
                            c2.setPadding(0, 0, 25, 0);

                        }
                    }
                });

                c3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (q1.getAnswer().equals(c3.getText().toString())) {

                            Drawable img = c3.getContext().getResources().getDrawable(R.drawable.correctans);
                            //img.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);
                            Bitmap bitmap = ((BitmapDrawable) img).getBitmap();
                            Drawable d = new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(bitmap, 100, 100, true));
                            c3.setCompoundDrawablesWithIntrinsicBounds(null, null, d, null);
                            c3.setPadding(0, 0, 25, 0);


                            //eText(QuizActivity.this, "here 3", Toast.LENGTH_SHORT).show();
                        } else {
                            Drawable img = c3.getContext().getResources().getDrawable(R.drawable.wrongans);
                            //img.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);
                            Bitmap bitmap = ((BitmapDrawable) img).getBitmap();
                            Drawable d = new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(bitmap, 100, 100, true));
                            c3.setCompoundDrawablesWithIntrinsicBounds(null, null, d, null);
                            c3.setPadding(0, 0, 25, 0);

                        }
                    }
                });

                c4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (q1.getAnswer().equals(c4.getText().toString())) {

                            Drawable img = c4.getContext().getResources().getDrawable(R.drawable.correctans);
                            //img.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);
                            Bitmap bitmap = ((BitmapDrawable) img).getBitmap();
                            Drawable d = new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(bitmap, 100, 100, true));
                            c4.setCompoundDrawablesWithIntrinsicBounds(null, null, d, null);
                            c4.setPadding(0, 0, 25, 0);


                            //eText(QuizActivity.this, "here 4", Toast.LENGTH_SHORT).show();
                        } else {
                            Drawable img = c4.getContext().getResources().getDrawable(R.drawable.wrongans);
                            //img.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);
                            Bitmap bitmap = ((BitmapDrawable) img).getBitmap();
                            Drawable d = new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(bitmap, 100, 100, true));
                            c4.setCompoundDrawablesWithIntrinsicBounds(null, null, d, null);
                            c4.setPadding(0, 0, 25, 0);

                        }
                    }
                });
            }
        }
        else if(i==1) {
            theChoices.add("10 players");
            theChoices.add("7 players");
            theChoices.add("9 players");
            theChoices.add("8 players");
            Question q1 = new Question("How many players are there on a baseball team ?", theChoices, "9 players");


            if (!q1.equals(null) || !q1.equals("")) {
                Log.d("TAG", "onCreate: " + q1);

                Q.setText(q1.getTitle());
                c1.setText(q1.getChoices().get(0));
                c2.setText(q1.getChoices().get(1));
                c3.setText(q1.getChoices().get(2));
                c4.setText(q1.getChoices().get(3));
            }

            if (!q1.getAnswer().isEmpty()) {
                c1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (q1.getAnswer().equals(c1.getText().toString())) {

                            Drawable img = c1.getContext().getResources().getDrawable(R.drawable.correctans);
                            //img.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);
                            Bitmap bitmap = ((BitmapDrawable) img).getBitmap();
                            Drawable d = new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(bitmap, 100, 100, true));
                            c1.setCompoundDrawablesWithIntrinsicBounds(null, null, d, null);
                            c1.setPadding(0, 0, 25, 0);


                            //eText(QuizActivity.this, "here 1", Toast.LENGTH_SHORT).show();
                        } else {
                            Drawable img = c1.getContext().getResources().getDrawable(R.drawable.wrongans);
                            //img.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);
                            Bitmap bitmap = ((BitmapDrawable) img).getBitmap();
                            Drawable d = new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(bitmap, 100, 100, true));
                            c1.setCompoundDrawablesWithIntrinsicBounds(null, null, d, null);
                            c1.setPadding(0, 0, 25, 0);

                        }
                    }
                });

                c2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (q1.getAnswer().equals(c2.getText().toString())) {

                            Drawable img = c2.getContext().getResources().getDrawable(R.drawable.correctans);
                            //img.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);
                            Bitmap bitmap = ((BitmapDrawable) img).getBitmap();
                            Drawable d = new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(bitmap, 100, 100, true));
                            c2.setCompoundDrawablesWithIntrinsicBounds(null, null, d, null);
                            c2.setPadding(0, 0, 25, 0);


                            //eText(QuizActivity.this, "here 2", Toast.LENGTH_SHORT).show();
                        } else {
                            Drawable img = c2.getContext().getResources().getDrawable(R.drawable.wrongans);
                            //img.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);
                            Bitmap bitmap = ((BitmapDrawable) img).getBitmap();
                            Drawable d = new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(bitmap, 100, 100, true));
                            c2.setCompoundDrawablesWithIntrinsicBounds(null, null, d, null);
                            c2.setPadding(0, 0, 25, 0);

                        }
                    }
                });

                c3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (q1.getAnswer().equals(c3.getText().toString())) {

                            Drawable img = c3.getContext().getResources().getDrawable(R.drawable.correctans);
                            //img.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);
                            Bitmap bitmap = ((BitmapDrawable) img).getBitmap();
                            Drawable d = new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(bitmap, 100, 100, true));
                            c3.setCompoundDrawablesWithIntrinsicBounds(null, null, d, null);
                            c3.setPadding(0, 0, 25, 0);


                            //eText(QuizActivity.this, "here 3", Toast.LENGTH_SHORT).show();
                        } else {
                            Drawable img = c3.getContext().getResources().getDrawable(R.drawable.wrongans);
                            //img.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);
                            Bitmap bitmap = ((BitmapDrawable) img).getBitmap();
                            Drawable d = new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(bitmap, 100, 100, true));
                            c3.setCompoundDrawablesWithIntrinsicBounds(null, null, d, null);
                            c3.setPadding(0, 0, 25, 0);

                        }
                    }
                });

                c4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (q1.getAnswer().equals(c4.getText().toString())) {

                            Drawable img = c4.getContext().getResources().getDrawable(R.drawable.correctans);
                            //img.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);
                            Bitmap bitmap = ((BitmapDrawable) img).getBitmap();
                            Drawable d = new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(bitmap, 100, 100, true));
                            c4.setCompoundDrawablesWithIntrinsicBounds(null, null, d, null);
                            c4.setPadding(0, 0, 25, 0);


                            //eText(QuizActivity.this, "here 4", Toast.LENGTH_SHORT).show();
                        } else {
                            Drawable img = c4.getContext().getResources().getDrawable(R.drawable.wrongans);
                            //img.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);
                            Bitmap bitmap = ((BitmapDrawable) img).getBitmap();
                            Drawable d = new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(bitmap, 100, 100, true));
                            c4.setCompoundDrawablesWithIntrinsicBounds(null, null, d, null);
                            c4.setPadding(0, 0, 25, 0);

                        }
                    }
                });
            }

        }
        else if(i==2) {
            theChoices.add("Brazil");
            theChoices.add("Germany");
            theChoices.add("France");
            theChoices.add("Argentina");
            Question q1 = new Question(" Which country won the World Cup 2018 ?", theChoices, "France");


            if (!q1.equals(null) || !q1.equals("")) {
                Log.d("TAG", "onCreate: " + q1);

                Q.setText(q1.getTitle());
                c1.setText(q1.getChoices().get(0));
                c2.setText(q1.getChoices().get(1));
                c3.setText(q1.getChoices().get(2));
                c4.setText(q1.getChoices().get(3));
            }

            if (!q1.getAnswer().isEmpty()) {
                c1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (q1.getAnswer().equals(c1.getText().toString())) {

                            Drawable img = c1.getContext().getResources().getDrawable(R.drawable.correctans);
                            //img.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);
                            Bitmap bitmap = ((BitmapDrawable) img).getBitmap();
                            Drawable d = new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(bitmap, 100, 100, true));
                            c1.setCompoundDrawablesWithIntrinsicBounds(null, null, d, null);
                            c1.setPadding(0, 0, 25, 0);


                            //eText(QuizActivity.this, "here 1", Toast.LENGTH_SHORT).show();
                        } else {
                            Drawable img = c1.getContext().getResources().getDrawable(R.drawable.wrongans);
                            //img.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);
                            Bitmap bitmap = ((BitmapDrawable) img).getBitmap();
                            Drawable d = new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(bitmap, 100, 100, true));
                            c1.setCompoundDrawablesWithIntrinsicBounds(null, null, d, null);
                            c1.setPadding(0, 0, 25, 0);

                        }
                    }
                });

                c2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (q1.getAnswer().equals(c2.getText().toString())) {

                            Drawable img = c2.getContext().getResources().getDrawable(R.drawable.correctans);
                            //img.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);
                            Bitmap bitmap = ((BitmapDrawable) img).getBitmap();
                            Drawable d = new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(bitmap, 100, 100, true));
                            c2.setCompoundDrawablesWithIntrinsicBounds(null, null, d, null);
                            c2.setPadding(0, 0, 25, 0);


                            //eText(QuizActivity.this, "here 2", Toast.LENGTH_SHORT).show();
                        } else {
                            Drawable img = c2.getContext().getResources().getDrawable(R.drawable.wrongans);
                            //img.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);
                            Bitmap bitmap = ((BitmapDrawable) img).getBitmap();
                            Drawable d = new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(bitmap, 100, 100, true));
                            c2.setCompoundDrawablesWithIntrinsicBounds(null, null, d, null);
                            c2.setPadding(0, 0, 25, 0);

                        }
                    }
                });

                c3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (q1.getAnswer().equals(c3.getText().toString())) {

                            Drawable img = c3.getContext().getResources().getDrawable(R.drawable.correctans);
                            //img.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);
                            Bitmap bitmap = ((BitmapDrawable) img).getBitmap();
                            Drawable d = new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(bitmap, 100, 100, true));
                            c3.setCompoundDrawablesWithIntrinsicBounds(null, null, d, null);
                            c3.setPadding(0, 0, 25, 0);


                            //eText(QuizActivity.this, "here 3", Toast.LENGTH_SHORT).show();
                        } else {
                            Drawable img = c3.getContext().getResources().getDrawable(R.drawable.wrongans);
                            //img.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);
                            Bitmap bitmap = ((BitmapDrawable) img).getBitmap();
                            Drawable d = new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(bitmap, 100, 100, true));
                            c3.setCompoundDrawablesWithIntrinsicBounds(null, null, d, null);
                            c3.setPadding(0, 0, 25, 0);

                        }
                    }
                });

                c4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (q1.getAnswer().equals(c4.getText().toString())) {

                            Drawable img = c4.getContext().getResources().getDrawable(R.drawable.correctans);
                            //img.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);
                            Bitmap bitmap = ((BitmapDrawable) img).getBitmap();
                            Drawable d = new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(bitmap, 100, 100, true));
                            c4.setCompoundDrawablesWithIntrinsicBounds(null, null, d, null);
                            c4.setPadding(0, 0, 25, 0);


                            //eText(QuizActivity.this, "here 4", Toast.LENGTH_SHORT).show();
                        } else {
                            Drawable img = c4.getContext().getResources().getDrawable(R.drawable.wrongans);
                            //img.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);
                            Bitmap bitmap = ((BitmapDrawable) img).getBitmap();
                            Drawable d = new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(bitmap, 100, 100, true));
                            c4.setCompoundDrawablesWithIntrinsicBounds(null, null, d, null);
                            c4.setPadding(0, 0, 25, 0);

                        }
                    }
                });
            }

        }


    }

    public void Inc_I(View v){
        Log.d("TAG", "Inc_I Before: "+i);
        if(i < 2) {
            i++;
        }
        Log.d("TAG", "Inc_I After: "+i);

        c1.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
        c2.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
        c3.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
        c4.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);

        List<String> theChoices = new ArrayList<String>();

        if(i==0) {
            theChoices.add("42.195 kilometres");
            theChoices.add("32.395 kilometres");
            theChoices.add("54.125 kilometres");
            theChoices.add("66.495 kilometres");
            Question q1 = new Question("How long is a marathon?", theChoices, "42.195 kilometres");


            if (!q1.equals(null) || !q1.equals("")) {
                Log.d("TAG", "onCreate: " + q1);

                Q.setText(q1.getTitle());
                c1.setText(q1.getChoices().get(0));
                c2.setText(q1.getChoices().get(1));
                c3.setText(q1.getChoices().get(2));
                c4.setText(q1.getChoices().get(3));
            }

            if (!q1.getAnswer().isEmpty()) {
                c1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (q1.getAnswer().equals(c1.getText().toString())) {

                            Drawable img = c1.getContext().getResources().getDrawable(R.drawable.correctans);
                            //img.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);
                            Bitmap bitmap = ((BitmapDrawable) img).getBitmap();
                            Drawable d = new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(bitmap, 100, 100, true));
                            c1.setCompoundDrawablesWithIntrinsicBounds(null, null, d, null);
                            c1.setPadding(0, 0, 25, 0);


                            //eText(QuizActivity.this, "here 1", Toast.LENGTH_SHORT).show();
                        } else {
                            Drawable img = c1.getContext().getResources().getDrawable(R.drawable.wrongans);
                            //img.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);
                            Bitmap bitmap = ((BitmapDrawable) img).getBitmap();
                            Drawable d = new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(bitmap, 100, 100, true));
                            c1.setCompoundDrawablesWithIntrinsicBounds(null, null, d, null);
                            c1.setPadding(0, 0, 25, 0);

                        }
                    }
                });

                c2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (q1.getAnswer().equals(c2.getText().toString())) {

                            Drawable img = c2.getContext().getResources().getDrawable(R.drawable.correctans);
                            //img.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);
                            Bitmap bitmap = ((BitmapDrawable) img).getBitmap();
                            Drawable d = new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(bitmap, 100, 100, true));
                            c2.setCompoundDrawablesWithIntrinsicBounds(null, null, d, null);
                            c2.setPadding(0, 0, 25, 0);


                            //eText(QuizActivity.this, "here 2", Toast.LENGTH_SHORT).show();
                        } else {
                            Drawable img = c2.getContext().getResources().getDrawable(R.drawable.wrongans);
                            //img.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);
                            Bitmap bitmap = ((BitmapDrawable) img).getBitmap();
                            Drawable d = new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(bitmap, 100, 100, true));
                            c2.setCompoundDrawablesWithIntrinsicBounds(null, null, d, null);
                            c2.setPadding(0, 0, 25, 0);

                        }
                    }
                });

                c3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (q1.getAnswer().equals(c3.getText().toString())) {

                            Drawable img = c3.getContext().getResources().getDrawable(R.drawable.correctans);
                            //img.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);
                            Bitmap bitmap = ((BitmapDrawable) img).getBitmap();
                            Drawable d = new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(bitmap, 100, 100, true));
                            c3.setCompoundDrawablesWithIntrinsicBounds(null, null, d, null);
                            c3.setPadding(0, 0, 25, 0);


                            //eText(QuizActivity.this, "here 3", Toast.LENGTH_SHORT).show();
                        } else {
                            Drawable img = c3.getContext().getResources().getDrawable(R.drawable.wrongans);
                            //img.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);
                            Bitmap bitmap = ((BitmapDrawable) img).getBitmap();
                            Drawable d = new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(bitmap, 100, 100, true));
                            c3.setCompoundDrawablesWithIntrinsicBounds(null, null, d, null);
                            c3.setPadding(0, 0, 25, 0);

                        }
                    }
                });

                c4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (q1.getAnswer().equals(c4.getText().toString())) {

                            Drawable img = c4.getContext().getResources().getDrawable(R.drawable.correctans);
                            //img.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);
                            Bitmap bitmap = ((BitmapDrawable) img).getBitmap();
                            Drawable d = new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(bitmap, 100, 100, true));
                            c4.setCompoundDrawablesWithIntrinsicBounds(null, null, d, null);
                            c4.setPadding(0, 0, 25, 0);


                            //eText(QuizActivity.this, "here 4", Toast.LENGTH_SHORT).show();
                        } else {
                            Drawable img = c4.getContext().getResources().getDrawable(R.drawable.wrongans);
                            //img.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);
                            Bitmap bitmap = ((BitmapDrawable) img).getBitmap();
                            Drawable d = new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(bitmap, 100, 100, true));
                            c4.setCompoundDrawablesWithIntrinsicBounds(null, null, d, null);
                            c4.setPadding(0, 0, 25, 0);

                        }
                    }
                });
            }
        }
        else if(i==1) {
            theChoices.add("10 players");
            theChoices.add("7 players");
            theChoices.add("9 players");
            theChoices.add("8 players");
            Question q1 = new Question("How many players are there on a baseball team ?", theChoices, "9 players");


            if (!q1.equals(null) || !q1.equals("")) {
                Log.d("TAG", "onCreate: " + q1);

                Q.setText(q1.getTitle());
                c1.setText(q1.getChoices().get(0));
                c2.setText(q1.getChoices().get(1));
                c3.setText(q1.getChoices().get(2));
                c4.setText(q1.getChoices().get(3));
            }

            if (!q1.getAnswer().isEmpty()) {
                c1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (q1.getAnswer().equals(c1.getText().toString())) {

                            Drawable img = c1.getContext().getResources().getDrawable(R.drawable.correctans);
                            //img.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);
                            Bitmap bitmap = ((BitmapDrawable) img).getBitmap();
                            Drawable d = new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(bitmap, 100, 100, true));
                            c1.setCompoundDrawablesWithIntrinsicBounds(null, null, d, null);
                            c1.setPadding(0, 0, 25, 0);


                            //eText(QuizActivity.this, "here 1", Toast.LENGTH_SHORT).show();
                        } else {
                            Drawable img = c1.getContext().getResources().getDrawable(R.drawable.wrongans);
                            //img.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);
                            Bitmap bitmap = ((BitmapDrawable) img).getBitmap();
                            Drawable d = new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(bitmap, 100, 100, true));
                            c1.setCompoundDrawablesWithIntrinsicBounds(null, null, d, null);
                            c1.setPadding(0, 0, 25, 0);

                        }
                    }
                });

                c2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (q1.getAnswer().equals(c2.getText().toString())) {

                            Drawable img = c2.getContext().getResources().getDrawable(R.drawable.correctans);
                            //img.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);
                            Bitmap bitmap = ((BitmapDrawable) img).getBitmap();
                            Drawable d = new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(bitmap, 100, 100, true));
                            c2.setCompoundDrawablesWithIntrinsicBounds(null, null, d, null);
                            c2.setPadding(0, 0, 25, 0);


                            //eText(QuizActivity.this, "here 2", Toast.LENGTH_SHORT).show();
                        } else {
                            Drawable img = c2.getContext().getResources().getDrawable(R.drawable.wrongans);
                            //img.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);
                            Bitmap bitmap = ((BitmapDrawable) img).getBitmap();
                            Drawable d = new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(bitmap, 100, 100, true));
                            c2.setCompoundDrawablesWithIntrinsicBounds(null, null, d, null);
                            c2.setPadding(0, 0, 25, 0);

                        }
                    }
                });

                c3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (q1.getAnswer().equals(c3.getText().toString())) {

                            Drawable img = c3.getContext().getResources().getDrawable(R.drawable.correctans);
                            //img.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);
                            Bitmap bitmap = ((BitmapDrawable) img).getBitmap();
                            Drawable d = new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(bitmap, 100, 100, true));
                            c3.setCompoundDrawablesWithIntrinsicBounds(null, null, d, null);
                            c3.setPadding(0, 0, 25, 0);


                            //eText(QuizActivity.this, "here 3", Toast.LENGTH_SHORT).show();
                        } else {
                            Drawable img = c3.getContext().getResources().getDrawable(R.drawable.wrongans);
                            //img.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);
                            Bitmap bitmap = ((BitmapDrawable) img).getBitmap();
                            Drawable d = new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(bitmap, 100, 100, true));
                            c3.setCompoundDrawablesWithIntrinsicBounds(null, null, d, null);
                            c3.setPadding(0, 0, 25, 0);

                        }
                    }
                });

                c4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (q1.getAnswer().equals(c4.getText().toString())) {

                            Drawable img = c4.getContext().getResources().getDrawable(R.drawable.correctans);
                            //img.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);
                            Bitmap bitmap = ((BitmapDrawable) img).getBitmap();
                            Drawable d = new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(bitmap, 100, 100, true));
                            c4.setCompoundDrawablesWithIntrinsicBounds(null, null, d, null);
                            c4.setPadding(0, 0, 25, 0);


                            //eText(QuizActivity.this, "here 4", Toast.LENGTH_SHORT).show();
                        } else {
                            Drawable img = c4.getContext().getResources().getDrawable(R.drawable.wrongans);
                            //img.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);
                            Bitmap bitmap = ((BitmapDrawable) img).getBitmap();
                            Drawable d = new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(bitmap, 100, 100, true));
                            c4.setCompoundDrawablesWithIntrinsicBounds(null, null, d, null);
                            c4.setPadding(0, 0, 25, 0);

                        }
                    }
                });
            }

        }
        else if(i==2) {
            theChoices.add("Brazil");
            theChoices.add("Germany");
            theChoices.add("France");
            theChoices.add("Argentina");
            Question q1 = new Question(" Which country won the World Cup 2018 ?", theChoices, "France");


            if (!q1.equals(null) || !q1.equals("")) {
                Log.d("TAG", "onCreate: " + q1);

                Q.setText(q1.getTitle());
                c1.setText(q1.getChoices().get(0));
                c2.setText(q1.getChoices().get(1));
                c3.setText(q1.getChoices().get(2));
                c4.setText(q1.getChoices().get(3));
            }

            if (!q1.getAnswer().isEmpty()) {
                c1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (q1.getAnswer().equals(c1.getText().toString())) {

                            Drawable img = c1.getContext().getResources().getDrawable(R.drawable.correctans);
                            //img.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);
                            Bitmap bitmap = ((BitmapDrawable) img).getBitmap();
                            Drawable d = new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(bitmap, 100, 100, true));
                            c1.setCompoundDrawablesWithIntrinsicBounds(null, null, d, null);
                            c1.setPadding(0, 0, 25, 0);


                            //eText(QuizActivity.this, "here 1", Toast.LENGTH_SHORT).show();
                        } else {
                            Drawable img = c1.getContext().getResources().getDrawable(R.drawable.wrongans);
                            //img.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);
                            Bitmap bitmap = ((BitmapDrawable) img).getBitmap();
                            Drawable d = new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(bitmap, 100, 100, true));
                            c1.setCompoundDrawablesWithIntrinsicBounds(null, null, d, null);
                            c1.setPadding(0, 0, 25, 0);

                        }
                    }
                });

                c2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (q1.getAnswer().equals(c2.getText().toString())) {

                            Drawable img = c2.getContext().getResources().getDrawable(R.drawable.correctans);
                            //img.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);
                            Bitmap bitmap = ((BitmapDrawable) img).getBitmap();
                            Drawable d = new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(bitmap, 100, 100, true));
                            c2.setCompoundDrawablesWithIntrinsicBounds(null, null, d, null);
                            c2.setPadding(0, 0, 25, 0);


                            //eText(QuizActivity.this, "here 2", Toast.LENGTH_SHORT).show();
                        } else {
                            Drawable img = c2.getContext().getResources().getDrawable(R.drawable.wrongans);
                            //img.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);
                            Bitmap bitmap = ((BitmapDrawable) img).getBitmap();
                            Drawable d = new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(bitmap, 100, 100, true));
                            c2.setCompoundDrawablesWithIntrinsicBounds(null, null, d, null);
                            c2.setPadding(0, 0, 25, 0);

                        }
                    }
                });

                c3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (q1.getAnswer().equals(c3.getText().toString())) {

                            Drawable img = c3.getContext().getResources().getDrawable(R.drawable.correctans);
                            //img.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);
                            Bitmap bitmap = ((BitmapDrawable) img).getBitmap();
                            Drawable d = new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(bitmap, 100, 100, true));
                            c3.setCompoundDrawablesWithIntrinsicBounds(null, null, d, null);
                            c3.setPadding(0, 0, 25, 0);


                            //eText(QuizActivity.this, "here 3", Toast.LENGTH_SHORT).show();
                        } else {
                            Drawable img = c3.getContext().getResources().getDrawable(R.drawable.wrongans);
                            //img.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);
                            Bitmap bitmap = ((BitmapDrawable) img).getBitmap();
                            Drawable d = new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(bitmap, 100, 100, true));
                            c3.setCompoundDrawablesWithIntrinsicBounds(null, null, d, null);
                            c3.setPadding(0, 0, 25, 0);

                        }
                    }
                });

                c4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (q1.getAnswer().equals(c4.getText().toString())) {

                            Drawable img = c4.getContext().getResources().getDrawable(R.drawable.correctans);
                            //img.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);
                            Bitmap bitmap = ((BitmapDrawable) img).getBitmap();
                            Drawable d = new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(bitmap, 100, 100, true));
                            c4.setCompoundDrawablesWithIntrinsicBounds(null, null, d, null);
                            c4.setPadding(0, 0, 25, 0);


                            //eText(QuizActivity.this, "here 4", Toast.LENGTH_SHORT).show();
                        } else {
                            Drawable img = c4.getContext().getResources().getDrawable(R.drawable.wrongans);
                            //img.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);
                            Bitmap bitmap = ((BitmapDrawable) img).getBitmap();
                            Drawable d = new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(bitmap, 100, 100, true));
                            c4.setCompoundDrawablesWithIntrinsicBounds(null, null, d, null);
                            c4.setPadding(0, 0, 25, 0);

                        }
                    }
                });
            }

        }

    }

    public void Dec_I(View v){
        Log.d("TAG", "Inc_I Before: "+i);
        if(i > 0) {
            i--;
        }
        Log.d("TAG", "Inc_I After: "+i);

        c1.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
        c2.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
        c3.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
        c4.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);


        List<String> theChoices = new ArrayList<String>();

        if(i==0) {
            theChoices.add("42.195 kilometres");
            theChoices.add("32.395 kilometres");
            theChoices.add("54.125 kilometres");
            theChoices.add("66.495 kilometres");
            Question q1 = new Question("How long is a marathon?", theChoices, "42.195 kilometres");


            if (!q1.equals(null) || !q1.equals("")) {
                Log.d("TAG", "onCreate: " + q1);

                Q.setText(q1.getTitle());
                c1.setText(q1.getChoices().get(0));
                c2.setText(q1.getChoices().get(1));
                c3.setText(q1.getChoices().get(2));
                c4.setText(q1.getChoices().get(3));
            }

            if (!q1.getAnswer().isEmpty()) {
                c1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (q1.getAnswer().equals(c1.getText().toString())) {

                            Drawable img = c1.getContext().getResources().getDrawable(R.drawable.correctans);
                            //img.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);
                            Bitmap bitmap = ((BitmapDrawable) img).getBitmap();
                            Drawable d = new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(bitmap, 100, 100, true));
                            c1.setCompoundDrawablesWithIntrinsicBounds(null, null, d, null);
                            c1.setPadding(0, 0, 25, 0);


                            //eText(QuizActivity.this, "here 1", Toast.LENGTH_SHORT).show();
                        } else {
                            Drawable img = c1.getContext().getResources().getDrawable(R.drawable.wrongans);
                            //img.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);
                            Bitmap bitmap = ((BitmapDrawable) img).getBitmap();
                            Drawable d = new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(bitmap, 100, 100, true));
                            c1.setCompoundDrawablesWithIntrinsicBounds(null, null, d, null);
                            c1.setPadding(0, 0, 25, 0);

                        }
                    }
                });

                c2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (q1.getAnswer().equals(c2.getText().toString())) {

                            Drawable img = c2.getContext().getResources().getDrawable(R.drawable.correctans);
                            //img.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);
                            Bitmap bitmap = ((BitmapDrawable) img).getBitmap();
                            Drawable d = new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(bitmap, 100, 100, true));
                            c2.setCompoundDrawablesWithIntrinsicBounds(null, null, d, null);
                            c2.setPadding(0, 0, 25, 0);


                            //eText(QuizActivity.this, "here 2", Toast.LENGTH_SHORT).show();
                        } else {
                            Drawable img = c2.getContext().getResources().getDrawable(R.drawable.wrongans);
                            //img.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);
                            Bitmap bitmap = ((BitmapDrawable) img).getBitmap();
                            Drawable d = new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(bitmap, 100, 100, true));
                            c2.setCompoundDrawablesWithIntrinsicBounds(null, null, d, null);
                            c2.setPadding(0, 0, 25, 0);

                        }
                    }
                });

                c3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (q1.getAnswer().equals(c3.getText().toString())) {

                            Drawable img = c3.getContext().getResources().getDrawable(R.drawable.correctans);
                            //img.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);
                            Bitmap bitmap = ((BitmapDrawable) img).getBitmap();
                            Drawable d = new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(bitmap, 100, 100, true));
                            c3.setCompoundDrawablesWithIntrinsicBounds(null, null, d, null);
                            c3.setPadding(0, 0, 25, 0);


                            //eText(QuizActivity.this, "here 3", Toast.LENGTH_SHORT).show();
                        } else {
                            Drawable img = c3.getContext().getResources().getDrawable(R.drawable.wrongans);
                            //img.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);
                            Bitmap bitmap = ((BitmapDrawable) img).getBitmap();
                            Drawable d = new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(bitmap, 100, 100, true));
                            c3.setCompoundDrawablesWithIntrinsicBounds(null, null, d, null);
                            c3.setPadding(0, 0, 25, 0);

                        }
                    }
                });

                c4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (q1.getAnswer().equals(c4.getText().toString())) {

                            Drawable img = c4.getContext().getResources().getDrawable(R.drawable.correctans);
                            //img.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);
                            Bitmap bitmap = ((BitmapDrawable) img).getBitmap();
                            Drawable d = new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(bitmap, 100, 100, true));
                            c4.setCompoundDrawablesWithIntrinsicBounds(null, null, d, null);
                            c4.setPadding(0, 0, 25, 0);


                            //eText(QuizActivity.this, "here 4", Toast.LENGTH_SHORT).show();
                        } else {
                            Drawable img = c4.getContext().getResources().getDrawable(R.drawable.wrongans);
                            //img.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);
                            Bitmap bitmap = ((BitmapDrawable) img).getBitmap();
                            Drawable d = new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(bitmap, 100, 100, true));
                            c4.setCompoundDrawablesWithIntrinsicBounds(null, null, d, null);
                            c4.setPadding(0, 0, 25, 0);

                        }
                    }
                });
            }
        }
        else if(i==1) {
            theChoices.add("10 players");
            theChoices.add("7 players");
            theChoices.add("9 players");
            theChoices.add("8 players");
            Question q1 = new Question("How many players are there on a baseball team ?", theChoices, "9 players");

            if (!q1.equals(null) || !q1.equals("")) {
                Log.d("TAG", "onCreate: " + q1);

                Q.setText(q1.getTitle());
                c1.setText(q1.getChoices().get(0));
                c2.setText(q1.getChoices().get(1));
                c3.setText(q1.getChoices().get(2));
                c4.setText(q1.getChoices().get(3));
            }

            if (!q1.getAnswer().isEmpty()) {
                c1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (q1.getAnswer().equals(c1.getText().toString())) {

                            Drawable img = c1.getContext().getResources().getDrawable(R.drawable.correctans);
                            //img.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);
                            Bitmap bitmap = ((BitmapDrawable) img).getBitmap();
                            Drawable d = new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(bitmap, 100, 100, true));
                            c1.setCompoundDrawablesWithIntrinsicBounds(null, null, d, null);
                            c1.setPadding(0, 0, 25, 0);


                            //eText(QuizActivity.this, "here 1", Toast.LENGTH_SHORT).show();
                        } else {
                            Drawable img = c1.getContext().getResources().getDrawable(R.drawable.wrongans);
                            //img.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);
                            Bitmap bitmap = ((BitmapDrawable) img).getBitmap();
                            Drawable d = new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(bitmap, 100, 100, true));
                            c1.setCompoundDrawablesWithIntrinsicBounds(null, null, d, null);
                            c1.setPadding(0, 0, 25, 0);

                        }
                    }
                });

                c2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (q1.getAnswer().equals(c2.getText().toString())) {

                            Drawable img = c2.getContext().getResources().getDrawable(R.drawable.correctans);
                            //img.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);
                            Bitmap bitmap = ((BitmapDrawable) img).getBitmap();
                            Drawable d = new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(bitmap, 100, 100, true));
                            c2.setCompoundDrawablesWithIntrinsicBounds(null, null, d, null);
                            c2.setPadding(0, 0, 25, 0);


                            //eText(QuizActivity.this, "here 2", Toast.LENGTH_SHORT).show();
                        } else {
                            Drawable img = c2.getContext().getResources().getDrawable(R.drawable.wrongans);
                            //img.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);
                            Bitmap bitmap = ((BitmapDrawable) img).getBitmap();
                            Drawable d = new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(bitmap, 100, 100, true));
                            c2.setCompoundDrawablesWithIntrinsicBounds(null, null, d, null);
                            c2.setPadding(0, 0, 25, 0);

                        }
                    }
                });

                c3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (q1.getAnswer().equals(c3.getText().toString())) {

                            Drawable img = c3.getContext().getResources().getDrawable(R.drawable.correctans);
                            //img.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);
                            Bitmap bitmap = ((BitmapDrawable) img).getBitmap();
                            Drawable d = new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(bitmap, 100, 100, true));
                            c3.setCompoundDrawablesWithIntrinsicBounds(null, null, d, null);
                            c3.setPadding(0, 0, 25, 0);


                            //eText(QuizActivity.this, "here 3", Toast.LENGTH_SHORT).show();
                        } else {
                            Drawable img = c3.getContext().getResources().getDrawable(R.drawable.wrongans);
                            //img.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);
                            Bitmap bitmap = ((BitmapDrawable) img).getBitmap();
                            Drawable d = new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(bitmap, 100, 100, true));
                            c3.setCompoundDrawablesWithIntrinsicBounds(null, null, d, null);
                            c3.setPadding(0, 0, 25, 0);

                        }
                    }
                });

                c4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (q1.getAnswer().equals(c4.getText().toString())) {

                            Drawable img = c4.getContext().getResources().getDrawable(R.drawable.correctans);
                            //img.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);
                            Bitmap bitmap = ((BitmapDrawable) img).getBitmap();
                            Drawable d = new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(bitmap, 100, 100, true));
                            c4.setCompoundDrawablesWithIntrinsicBounds(null, null, d, null);
                            c4.setPadding(0, 0, 25, 0);


                            //eText(QuizActivity.this, "here 4", Toast.LENGTH_SHORT).show();
                        } else {
                            Drawable img = c4.getContext().getResources().getDrawable(R.drawable.wrongans);
                            //img.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);
                            Bitmap bitmap = ((BitmapDrawable) img).getBitmap();
                            Drawable d = new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(bitmap, 100, 100, true));
                            c4.setCompoundDrawablesWithIntrinsicBounds(null, null, d, null);
                            c4.setPadding(0, 0, 25, 0);

                        }
                    }
                });
            }

        }
        else if(i==2) {
            theChoices.add("Brazil");
            theChoices.add("Germany");
            theChoices.add("France");
            theChoices.add("Argentina");
            Question q1 = new Question(" Which country won the World Cup 2018 ?", theChoices, "France");


            if (!q1.equals(null) || !q1.equals("")) {
                Log.d("TAG", "onCreate: " + q1);

                Q.setText(q1.getTitle());
                c1.setText(q1.getChoices().get(0));
                c2.setText(q1.getChoices().get(1));
                c3.setText(q1.getChoices().get(2));
                c4.setText(q1.getChoices().get(3));
            }

            if (!q1.getAnswer().isEmpty()) {
                c1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (q1.getAnswer().equals(c1.getText().toString())) {

                            Drawable img = c1.getContext().getResources().getDrawable(R.drawable.correctans);
                            //img.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);
                            Bitmap bitmap = ((BitmapDrawable) img).getBitmap();
                            Drawable d = new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(bitmap, 100, 100, true));
                            c1.setCompoundDrawablesWithIntrinsicBounds(null, null, d, null);
                            c1.setPadding(0, 0, 25, 0);


                            //eText(QuizActivity.this, "here 1", Toast.LENGTH_SHORT).show();
                        } else {
                            Drawable img = c1.getContext().getResources().getDrawable(R.drawable.wrongans);
                            //img.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);
                            Bitmap bitmap = ((BitmapDrawable) img).getBitmap();
                            Drawable d = new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(bitmap, 100, 100, true));
                            c1.setCompoundDrawablesWithIntrinsicBounds(null, null, d, null);
                            c1.setPadding(0, 0, 25, 0);

                        }
                    }
                });

                c2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (q1.getAnswer().equals(c2.getText().toString())) {

                            Drawable img = c2.getContext().getResources().getDrawable(R.drawable.correctans);
                            //img.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);
                            Bitmap bitmap = ((BitmapDrawable) img).getBitmap();
                            Drawable d = new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(bitmap, 100, 100, true));
                            c2.setCompoundDrawablesWithIntrinsicBounds(null, null, d, null);
                            c2.setPadding(0, 0, 25, 0);


                            //eText(QuizActivity.this, "here 2", Toast.LENGTH_SHORT).show();
                        } else {
                            Drawable img = c2.getContext().getResources().getDrawable(R.drawable.wrongans);
                            //img.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);
                            Bitmap bitmap = ((BitmapDrawable) img).getBitmap();
                            Drawable d = new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(bitmap, 100, 100, true));
                            c2.setCompoundDrawablesWithIntrinsicBounds(null, null, d, null);
                            c2.setPadding(0, 0, 25, 0);

                        }
                    }
                });

                c3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (q1.getAnswer().equals(c3.getText().toString())) {

                            Drawable img = c3.getContext().getResources().getDrawable(R.drawable.correctans);
                            //img.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);
                            Bitmap bitmap = ((BitmapDrawable) img).getBitmap();
                            Drawable d = new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(bitmap, 100, 100, true));
                            c3.setCompoundDrawablesWithIntrinsicBounds(null, null, d, null);
                            c3.setPadding(0, 0, 25, 0);


                            //eText(QuizActivity.this, "here 3", Toast.LENGTH_SHORT).show();
                        } else {
                            Drawable img = c3.getContext().getResources().getDrawable(R.drawable.wrongans);
                            //img.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);
                            Bitmap bitmap = ((BitmapDrawable) img).getBitmap();
                            Drawable d = new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(bitmap, 100, 100, true));
                            c3.setCompoundDrawablesWithIntrinsicBounds(null, null, d, null);
                            c3.setPadding(0, 0, 25, 0);

                        }
                    }
                });

                c4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (q1.getAnswer().equals(c4.getText().toString())) {

                            Drawable img = c4.getContext().getResources().getDrawable(R.drawable.correctans);
                            //img.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);
                            Bitmap bitmap = ((BitmapDrawable) img).getBitmap();
                            Drawable d = new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(bitmap, 100, 100, true));
                            c4.setCompoundDrawablesWithIntrinsicBounds(null, null, d, null);
                            c4.setPadding(0, 0, 25, 0);


                            //eText(QuizActivity.this, "here 4", Toast.LENGTH_SHORT).show();
                        } else {
                            Drawable img = c4.getContext().getResources().getDrawable(R.drawable.wrongans);
                            //img.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);
                            Bitmap bitmap = ((BitmapDrawable) img).getBitmap();
                            Drawable d = new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(bitmap, 100, 100, true));
                            c4.setCompoundDrawablesWithIntrinsicBounds(null, null, d, null);
                            c4.setPadding(0, 0, 25, 0);

                        }
                    }
                });
            }

        }



    }
}