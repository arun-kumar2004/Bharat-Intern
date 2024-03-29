package com.azamovhudstc.quizapp.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.TextUtils;
import android.util.Patterns;


import com.azamovhudstc.quizapp.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

public class Utils {



    public static String formatDate(long time){
        @SuppressLint({"NewApi", "LocalSuppress"}) SimpleDateFormat formatter = new SimpleDateFormat(Constants.DATE_FORMAT, Locale.getDefault());

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time);
        return formatter.format(calendar.getTime());
    }

    public static Map<String,String> getMathQuestions(){
        HashMap<String,String> questions = new HashMap<>();
        questions.put("4*3","12");
        questions.put("5*12","60");
        questions.put("127-45","82");
        questions.put("y=−6x+8","78");
        questions.put("43*27","1161");
        questions.put("175/5","35");
        questions.put("20/2(5+5) ","100");
        questions.put("134/2+(12*4)","115");
        questions.put("56*31","1736");
        questions.put("1748/76","23");
        questions.put("3456/432","8");
        questions.put("23/77","1771");
        questions.put("32/4","8");
        questions.put("2892/723","4");
        questions.put("55/5","11");

        return questions;
    }

    public static Map<String,String> getRandomMathQuestions(int SIZE){
        HashMap<String,String> questionsMap = new HashMap<>();
        Map<String,String> originalQuestion = getMathQuestions();
        int originalSize =  originalQuestion.size();
        ArrayList<String> keyList = new ArrayList<String>(originalQuestion.keySet());

        while (questionsMap.size()<=SIZE){
            Random random = new Random();
            int randomNumber = random.nextInt(originalSize);
            String question = keyList.get(randomNumber);
            if (!questionsMap.containsKey(question)){
                questionsMap.put(question,originalQuestion.get(question));
            }
        }
        return questionsMap;
    }



    public static Map<String,Map<String,Boolean>> getLiteratureQuestions(){
        HashMap<String,Map<String,Boolean>> questions = new HashMap<>();

        HashMap<String,Boolean> answer1 = new HashMap<>();
        answer1.put("The Happy Clone",false);
        answer1.put("The Happy Prince",true);
        answer1.put("The Happy Queen ",false);
        answer1.put("The Happy King",false);
        questions.put("Which of the following is a short story collection by 'Oscar Wilde' ?",answer1);

        HashMap<String,Boolean> answer2 = new HashMap<>();
        answer2.put("John Keats ",false);
        answer2.put("Solan",false);
        answer2.put("Sappho",false);
        answer2.put("Lord Byron",true);
        questions.put("Which poet is considered a national hero in Greece ?",answer2);

        HashMap<String,Boolean> answer3 = new HashMap<>();
        answer3.put("The great drought",false);
        answer3.put("The great flood",false);
        answer3.put("The great plague",true);
        answer3.put("The great revolt",false);
        questions.put("The Black Death is also called?",answer3);

        HashMap<String,Boolean> answer4 = new HashMap<>();
        answer4.put("Henrik Ibsen",true);
        answer4.put("John Galsworthy",false);
        answer4.put("Samuel Becket",false);
        answer4.put("George Bernard Shaw",false);
        questions.put("Who is known as the father of modern drama?",answer4);

        HashMap<String,Boolean> answer5 = new HashMap<>();
        answer5.put("Boer War",false);
        answer5.put("First World War",true);
        answer5.put("Korean War",false);
        answer5.put("America",false);
        questions.put("Rupert Brooke wrote his poetry during which conflict?",answer5);

        HashMap<String,Boolean> answer6 = new HashMap<>();
        answer6.put("6",false);
        answer6.put("9",false);
        answer6.put("4",false);
        answer6.put("5",true);
        questions.put("How many oceans are there on Earth?",answer6);

        HashMap<String,Boolean> answer7 = new HashMap<>();
        answer7.put("The Homeric epic",true);
        answer7.put("The Hesiodic epic",false);
        answer7.put("The Deluge epic",false);
        answer7.put("The Gilgamesh epic",false);
        questions.put("What is the earliest surviving European poem?",answer7);

        HashMap<String,Boolean> answer8 = new HashMap<>();
        answer8.put("Coleridge",true);
        answer8.put("Wordsworth",false);
        answer8.put("Lamb",false);
        answer8.put("Shelley",false);
        questions.put("Who said ‘Keats was a Greek’?",answer8);

        HashMap<String,Boolean> answer9 = new HashMap<>();
        answer9.put("Gertrude",true);
        answer9.put("Beatrice",false);
        answer9.put("Margaret",false);
        answer9.put("Rosalind",false);
        questions.put("Which of the following is Hamlet’s mother?",answer9);

        HashMap<String,Boolean> answer10 = new HashMap<>();
        answer10.put("Stingy",true);
        answer10.put("Rude",false);
        answer10.put("Unintelligent",false);
        answer10.put("Fanatic",false);
        questions.put("Which of the following was Elizabeth known as?",answer10);

        HashMap<String,Boolean> answer11 = new HashMap<>();
        answer11.put("A poem of fourteen lines",true);
        answer11.put("A poem of six lines",false);
        answer11.put("A poem of eight lines",false);
        answer11.put("A poem of twelve lines",false);
        questions.put("What is a Sonnet?:",answer11);

        HashMap<String,Boolean> answer12 = new HashMap<>();
        answer12.put("5",false);
        answer12.put("3",false);
        answer12.put("6",false);
        answer12.put("4",true);
        questions.put("How many languages did chaucer know?",answer12);

        HashMap<String,Boolean> answer13 = new HashMap<>();
        answer13.put("Palindrome",false);
        answer13.put("Sextet",false);
        answer13.put("Quartet",false);
        answer13.put("Limerick",true);
        questions.put("What is a funny poem of five lines called?",answer13);

        HashMap<String,Boolean> answer14 = new HashMap<>();
        answer14.put("King Richard",false);
        answer14.put("Geoffrey  Chaucer ",true);
        answer14.put("Dick Whittington",false);
        answer14.put("Thomas Lancaster",false);
        questions.put("Who wrote Canterbury Tales?",answer14);

        HashMap<String,Boolean> answer15 = new HashMap<>();
        answer15.put("War poems",true);
        answer15.put("Light verse",false);
        answer15.put("Romantic",false);
        answer15.put("Political Satire",false);
        questions.put("Sassoon and Brooke wrote what kind of poetry?",answer15);

        return questions;
    }

    public static Map<String,Map<String,Boolean>> getGeographyQuestions(){
        HashMap<String,Map<String,Boolean>> questions = new HashMap<>();

        HashMap<String,Boolean> answer1 = new HashMap<>();
        answer1.put("3",false);
        answer1.put("6",false);
        answer1.put("12",true);
        answer1.put("1",false);
        questions.put("4*3 ?",answer1);

        HashMap<String,Boolean> answer2 = new HashMap<>();
        answer2.put("60",true);
        answer2.put("12",false);
        answer2.put("53",false);
        answer2.put("14",false);
        questions.put("5*12 ?",answer2);

        HashMap<String,Boolean> answer3 = new HashMap<>();
        answer3.put("116",false);
        answer3.put("115",true);
        answer3.put("112",false);
        answer3.put("113",false);
        questions.put("134/2+(12*4) ?",answer3);

        HashMap<String,Boolean> answer4 = new HashMap<>();
        questions.put("2892/723 ?",answer4);
        answer4.put("5",false);
        answer4.put("16",true);
        answer4.put("4",false);
        answer4.put("15",false);

        HashMap<String,Boolean> answer5 = new HashMap<>();
        answer5.put("13",false);
        answer5.put("12",false);
        answer5.put("5",false);
        answer5.put("11",true);
        questions.put("55/5 ? ",answer5);

        HashMap<String,Boolean> answer6 = new HashMap<>();
        answer6.put("101",false);
        answer6.put("131",false);
        answer6.put("100",true);
        answer6.put("112",false);
        questions.put("20/2(5+5) ?",answer6);

        HashMap<String,Boolean> answer7 = new HashMap<>();
        answer7.put("1161",true);
        answer7.put("123",false);
        answer7.put("1421",false);
        answer7.put("1612",false);
        questions.put("43*27?",answer7);

        HashMap<String,Boolean> answer8 = new HashMap<>();
        answer8.put("82",true);
        answer8.put("83",false);
        answer8.put("84",false);
        answer8.put("85",false);
        questions.put("127-45?",answer8);
//
        HashMap<String,Boolean> answer9 = new HashMap<>();
        answer9.put("-4a + 22b",true);
        answer9.put("4a + 22b",false);
        answer9.put("28a + 30b",false);
        answer9.put("-28a + 30b",false);
        questions.put("12a + 26b -4b – 16a ?",answer9);

        HashMap<String,Boolean> answer10 = new HashMap<>();
        answer10.put(" -1",false);
        answer10.put("2",true);
        answer10.put("–2",false);
        answer10.put("6",false);
        questions.put("(4 – 5) – (13 – 18 + 2) ?",answer10);


        HashMap<String,Boolean> answer12 = new HashMap<>();
        answer12.put("25",false);
        answer12.put("24",false);
        answer12.put("17",false);
        answer12.put("13",true);
        questions.put("3 + 2 * (8 – 3)?",answer12);

        HashMap<String,Boolean> answer13 = new HashMap<>();
        answer13.put("7",false);
        answer13.put("5",false);
        answer13.put("9",true);
        answer13.put("8",false);
        questions.put("3^(4)÷3^(2) ?",answer13);



        return questions;
    }

    public static Map<String,Map<String,Boolean>> getRandomLiteratureAndGeographyQuestions(Context context, String subject, int SIZE){
        Map<String,Map<String,Boolean>> questionsMap = new HashMap<>();
        Map<String, Map<String, Boolean>> originalQuestion;
        if (subject.equals(context.getString(R.string.geography))){
            originalQuestion = getGeographyQuestions();
        }else{
            originalQuestion = getLiteratureQuestions();
        }

        int originalSize =  originalQuestion.size();
        ArrayList<String> keyList = new ArrayList<String>(originalQuestion.keySet());

        while (questionsMap.size()<=SIZE){
            Random random = new Random();
            int randomNumber = random.nextInt(originalSize);
            String question = keyList.get(randomNumber);
            if (!questionsMap.containsKey(question)){
                questionsMap.put(question,originalQuestion.get(question));
            }
        }
        return questionsMap;
    }

}
