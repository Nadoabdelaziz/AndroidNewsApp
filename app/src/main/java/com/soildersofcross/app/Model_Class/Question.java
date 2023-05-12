package com.soildersofcross.app.Model_Class;

import java.util.List;

public class Question {

     String Title,Answer;
     List<String> Choices;

         public Question(String Title,List<String> Choices,String Answer){
             this.Answer=Answer;
             this.Title=Title;
             this.Choices=Choices;
         }


        public String getTitle() {
            return Title;
        }

        public void setTitle(String Title) {
            this.Title = Title;
        }

        public List<String> getChoices() {
            return Choices;
        }

        public void setChoices(List<String> Choices) {
            this.Choices = Choices;
        }

        public String getAnswer() {
            return Answer;
        }

        public void setAnswer(String Answer) {
        this.Answer = Answer;
    }

}

